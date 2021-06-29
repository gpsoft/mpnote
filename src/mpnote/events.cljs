(ns mpnote.events
  (:require
   [re-frame.core :as re-frame]
   [mpnote.db :as db]
   [mpnote.styles :as styles]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
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

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(defn scroll-top
  [cur-top cur-step-ix]
  (let [step-top (styles/step-top cur-step-ix)
        vh (-> (.querySelector js/document ".jsTimeline")
               (.getBoundingClientRect)
               (.-height))
        min-top db/initial-scroll-top
        max-top (/ vh 2)]
    (- (max (min (+ cur-top step-top) max-top) min-top) step-top)))

(re-frame/reg-event-db
  ::move-step
  (fn-traced
    [db [_ ff?]]
    (let [top (:scroll-top db)
          cur (:cur-step-ix db)
          las (dec (count (get-in db [:score :steps])))
          cur (max 0 (+ cur (if ff? 1 -1)))
          cur (if (> cur las) 0 cur)]
      (assoc db
             :cur-step-ix cur
             :scroll-top (scroll-top top cur)))))

(re-frame/reg-event-fx
  ::play-pause
  (fn-traced
    [{:keys [db]} _]
    (let [playing? (:playing? db)]
      (if playing?
        {:db (assoc db :playing? false)
         :interval {:action :cancel
                    :id :play}}
        {:db (assoc db :playing? true)
         :interval {:action :start
                    :id :play
                    :frequency 1500
                    :event [::move-step true]}}))))
