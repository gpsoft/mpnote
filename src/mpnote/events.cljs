(ns mpnote.events
  (:require
   [clojure.edn :as edn]
   [re-frame.core :as re-frame]
   [mpnote.db :as db]
   [mpnote.styles :as styles]
   [mpnote.utils :as u]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [ajax.core :as ajax]
   ))

(defonce interval-handler
  (let [live-intervals (atom {})]
    (fn handler [{:keys [action id frequency event]}]
      (condp = action
        :clean (doall
                 (map #(handler {:action :cancel :id  %1})
                      (keys @live-intervals)))
        :start (swap! live-intervals
                      assoc id (js/setInterval #(re-frame/dispatch event) frequency))
        :cancel (do (js/clearInterval (get @live-intervals id))
                    (swap! live-intervals dissoc id))))))

;; for hot-reload
(interval-handler {:action :clean})

(re-frame/reg-fx
  :interval
  interval-handler)

(re-frame/reg-fx
  :read-file
  (fn [{:keys [selector event]}]
    (u/upload! selector
               #(re-frame/dispatch (conj event %)))))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(defn scroll-top
  [cur-top cur-step-ix]
  (let [step-top (styles/step-top cur-step-ix)
        vh (-> (u/dom ".jsTimeline")
               (.getBoundingClientRect)
               (.-height))
        min-top db/initial-scroll-top
        max-top (/ vh 2)]
    (- (max (min (+ cur-top step-top) max-top) min-top) step-top)))

(defn move-to
  [db step-ix]
  (let [top (:scroll-top db)]
    (assoc db
           :cur-step-ix step-ix
           :scroll-top (scroll-top top step-ix))))

(re-frame/reg-event-db
  ::move-step
  (fn-traced
    [db [_ ff?]]
    (let [step-ix (:cur-step-ix db)
          last-step-ix (dec (count (get-in db [:score :steps])))
          step-ix (min last-step-ix (max 0 (+ step-ix (if ff? 1 -1))))
          ; step-ix (if (> step-ix last-step-ix) 0 step-ix)
          ]
      (move-to db step-ix))))

(re-frame/reg-event-db
  ::seek-bar
  (fn-traced
    [db [_ ff?]]
    (let [step-ix (:cur-step-ix db)
          tops (->> (get-in db [:score :steps])
                    (filter #(:bar-top? %))
                    (map #(:step-ix %)))
          candidates (filter (fn [ix] (if ff? (> ix step-ix) (< ix step-ix))) tops)
          step-ix (if (empty? candidates)
                    (if ff? 0 (or (last tops) 0))
                    (if ff? (first candidates) (last candidates)))]
      (move-to db step-ix))))

(comment
  (nth [1 2 3] 0)
  (nth [1 2 3] -1)
  (first [])
  (last nil)
  )

(re-frame/reg-event-fx
  ::play-pause
  (fn
    [{:keys [db]} _]
    (let [playing? (:playing? db)
          tempo (get-in db [:score :tempo] 120)]
      (if playing?
        {:db (assoc db :playing? false)
         :interval {:action :cancel
                    :id :play}}
        {:db (assoc db :playing? true)
         :interval {:action :start
                    :id :play
                    :frequency (max 300 (/ 60000 tempo))
                    :event [::move-step true]}}))))

(defn ignore-move?
  [from-x from-y to-x to-y]
  (or
    (nil? from-x)
    (nil? from-y)
    #_(and (zero? to-x) (zero? to-y))  ;; なぜか、drop時にdrag 0 0イベントが来る
    (and (< (.abs js/Math (- to-x from-x)) 8)
         (< (.abs js/Math (- to-y from-y)) 8))))

(defn move-control-panel
  [{[x y] :control-panel-pos
    [from-x from-y] :dragging-control-panel-from
    :as db}
   to-x to-y]
  (if (ignore-move? from-x from-y to-x to-y)
    db
    (let [new-x (+ x (- to-x from-x))
          new-y (+ y (- to-y from-y))]
      (assoc db :control-panel-pos [new-x new-y]
             :dragging-control-panel-from [to-x to-y]))))

(defn mouse-pos
  [ev]
  [(.-pageX ev) (.-pageY ev)])

(defn touch-pos
  [ev]
  (let [touch (aget (.-changedTouches ev) 0)]
    [(.-pageX touch) (.-pageY touch)]))

(defn parse-drag-event
  [ev]
  (let [ev-type (.-type ev)]
    (condp = ev-type
        "mousedown" [:start (mouse-pos ev)]
        "mousemove" [:drag (mouse-pos ev)]
        "mouseup" [:end (mouse-pos ev)]
        "mouseleave" [:end (mouse-pos ev)]
        "touchstart" [:start (touch-pos ev)]
        "touchmove" [:drag (touch-pos ev)]
        "touchend" [:end (touch-pos ev)]
        "touchcancel" [:end (touch-pos ev)]
        [:none])))

(defn drag-target?
  [ev]
  (let [target (-> ev .-target .-tagName)]
    (not= target "A")))

(re-frame/reg-event-db
  ::drag-control-panel
  (fn
    [db [_ ev]]
    (let [[t [x y]] (parse-drag-event ev)]
      (condp = t
        :start (if (drag-target? ev)
                 (assoc db :dragging-control-panel-from [x y])
                 db)
        :drag (do (.preventDefault ev)
                  (move-control-panel db x y))
        :end (-> db
                 (move-control-panel x y)
                 (assoc :dragging-control-panel-from nil))
        db))))

(re-frame/reg-event-db
  ::toggle-dialog
  (fn
    [db [_ open?]]
    (assoc db :dialog-state (if open? :open :close))))

(re-frame/reg-event-db
  ::load-score
  (fn-traced
    [db [_ score-edn]]
    (try
      (let [score (edn/read-string score-edn)]
        (assoc db :score (db/enrich-score score)
               :dialog-state :close))
      (catch :default e
        (js/alert (str "読み込みに失敗しました。"
                       \newline
                       "レッスンメモの内容を確認してください。"
                       \newline
                       (.-message e)
                       \newline
                       (ex-data e)))
        (assoc db :dialog-state :open)))))

(re-frame/reg-event-db
  ::load-score-url-ng
  (fn-traced
    [db [_ a]]
    (js/alert (str "読み込みに失敗しました。"
                   \newline
                   "URLを確認してください。"
                   ))
    (assoc db :dialog-state :open)))

(re-frame/reg-event-fx
  ::load-score-file
  (fn-traced
    [{:keys [db]} [_ selector]]
    {:db (assoc db :dialog-state :busy)
     :read-file {:selector selector
                 :event [::load-score]}}))

(re-frame/reg-event-fx
  ::load-score-url
  (fn-traced
    [{:keys [db]} [_ url]]
    {:db (assoc db :dialog-state :busy)
     :http-xhrio {:method :get
                  :uri url
                  :timeout 10000
                  :response-format (ajax/raw-response-format)
                  :on-success [::load-score]
                  :on-failure [::load-score-url-ng]}}))

