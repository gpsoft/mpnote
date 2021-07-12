(ns mpnote.views
  (:require
   [re-frame.core :as re-frame]
   [mpnote.styles :as styles]
   [mpnote.subs :as subs]
   [mpnote.events :as events]
   [mpnote.utils :as u]
   ))

(def c4-note-no 60)
(def left-end-note-no 21)
(def right-end-note-no 108)
(defn c4? [no] (= no c4-note-no))

(defn score-info []
  (let [info (re-frame/subscribe [::subs/score-info])
        [title url] @info]
    (when title
      [:a.title-link
       {:href url
        :target "_blank"}
       title])))

(defn read-score []
  [:div.btn.read-score
   {:on-click #(re-frame/dispatch [::events/toggle-dialog true])}])

(defn top-in-tl
  [step-ix]
  (let [scroll-top (re-frame/subscribe [::subs/scroll-top])]
    (+ (styles/step-top step-ix) @scroll-top)))

(defn vnote [{:keys [step-ix hand finger-no]}]
  (let [dummy? (nil? finger-no)
        top (top-in-tl step-ix)
        klass (str (name hand) "-note")]
    [:div
     {:class (str (if dummy? "dummy-note" "note") " " klass)
      :style {:top top}
      :key step-ix
      }
     (when
       (and (not dummy?) (pos? finger-no))
       finger-no)]))

(defn key-1-for-tl
  [no]
  (let [notes (re-frame/subscribe [::subs/notes no])]
    (when (not-empty @notes)
      (doall (map vnote @notes)))))

(defn key-1
  ([black-white no tl?]
   [:div.key-1
    {:class (str (name black-white) "-key" (when (c4? no) " c4-key"))
     :key no
     :data-note-no no
     :on-click #(when (c4? no) (re-frame/dispatch [::events/toggle-full-keys]))}
    (when tl? (key-1-for-tl no))]))

(defn keys-3
  [no tl?]
  [:div.octave.keys-3
   {:key no}
   (key-1 :white (+ no 0) tl?)
   (key-1 :black (+ no 1) tl?)
   (key-1 :white (+ no 2) tl?)
   ])

(defn keys-1 [no tl?]
  [:div.octave.keys-1
   {:key no}
   (key-1 :white (+ no 0) tl?)
   ]
  )

(defn keys-12 [no tl?]
  [:div.octave.keys-12
   {:key no}
   (key-1 :white (+ no 0) tl?)
   (key-1 :black (+ no 1) tl?)
   (key-1 :white (+ no 2) tl?)
   (key-1 :black (+ no 3) tl?)
   (key-1 :white (+ no 4) tl?)
   (key-1 :white (+ no 5) tl?)
   (key-1 :black (+ no 6) tl?)
   (key-1 :white (+ no 7) tl?)
   (key-1 :black (+ no 8) tl?)
   (key-1 :white (+ no 9) tl?)
   (key-1 :black (+ no 10) tl?)
   (key-1 :white (+ no 11) tl?)
   ])

(defn cur-step []
  (let [cur-step-ix (re-frame/subscribe [::subs/cur-step])
        top (top-in-tl @cur-step-ix)]
    [:div.cur-step
      {:style {:top top}}]))

(defn vbar-top
  [step-ix]
  (let [top (top-in-tl step-ix)]
    [:div.bar-top
     {:style {:top top}
      :key step-ix}]))

(defn bar-tops []
  (let [tops (re-frame/subscribe [::subs/bar-tops])]
    (doall (map vbar-top @tops))))

(defn octave-nos []
  (map #(+ (* % 12) 24) (range 7)))

(defn octaves []
  (concat [[left-end-note-no 3]]
          (map (fn [no] [no 12]) (octave-nos))
          [[right-end-note-no 1]]))

(defn last-no-in-octave
  [no]
  (let [octave-no (quot no 12)]
    (->> (range 12)
         (map #(+ no %))
         (take-while #(= (quot % 12) octave-no))
         last)))

(defn keys-in-range
  ;; keys-12が、曲のレンジ内に含まれるか?
  ;;  - 左右、どちらかに余白を入れる
  ;;  - C4のオクターブは、必ず入れる
  [[min-no max-no] first-key-no]
  (let [
        max-no (+ max-no 12)     ;; 右側に余白
        min-no (min min-no c4-note-no)
        max-no (max max-no c4-note-no)
        last-key-no (last-no-in-octave first-key-no)]
    (and (<= first-key-no max-no)
         (>= last-key-no min-no))))

(defn add-margin-min-max
  [[min-no max-no]]
  (if (> (- min-no left-end-note-no)
         (- right-end-note-no max-no))
    [(- min-no 12) max-no]
    [min-no (+ max-no 12)]))
(defn optimize-min-max
  ;; 曲のレンジを尊重しつつ、表示する鍵盤を最適化
  ;;  - 左右、どちらかに余白を入れる
  ;;  - C4のオクターブは、必ず入れる
  [[min-no max-no :as min-max]]
  (let [[min-no max-no] (add-margin-min-max min-max)
        min-no (max left-end-note-no (min min-no c4-note-no))
        max-no (min right-end-note-no (max max-no c4-note-no))]
    [min-no max-no]))

(defn used-octave?
  ;; first-key-noから始まるkeys-12が、曲のレンジ内に含まれるか?
  [[min-no max-no] [first-key-no]]
  (let [last-key-no (last-no-in-octave first-key-no)]
    (and (<= first-key-no max-no)
         (>= last-key-no min-no))))

(defn keys-88
  ([] (keys-88 false))
  ([tl?]
   (let [full? (re-frame/subscribe [::subs/full-keys?])
         info (re-frame/subscribe [::subs/score-info])
         [_ _ min-max] @info
         min-max (optimize-min-max min-max)]
     [:div
      {:class (if tl? [:timeline :jsTimeline] :keys-88)}
      (let [octs (octaves)
            octs (if @full?
                   octs
                   (filter #(used-octave? min-max %) octs))]
        (doall (map (fn [[no num-keys]]
                      (case num-keys
                        1 (keys-1 no tl?)
                        3 (keys-3 no tl?)
                        12 (keys-12 no tl?))) octs)))
      (when tl? (cur-step))
      (when tl? (bar-tops))
      ])))
(defn timeline [] (keys-88 true))

(defn vpedal
  [[step-ix on?]]
  (let [top (top-in-tl step-ix)
        klass (if on? :pedal-on :pedal-off)]
    [:div.pedal
     {:class klass
      :style {:top top}
      :key step-ix}]))

(defn indicator []
  (let [pedals (re-frame/subscribe [::subs/pedals])]
    [:div.indicator-col
     (doall (map vpedal @pedals))]))

(defn vbarno
  [ix step-ix]
  (let [top (top-in-tl step-ix)]
    [:div.bar-no
     {:style {:top top}
      :key ix}
     (inc ix)]))

(defn annotation []
  (let [tops (re-frame/subscribe [::subs/bar-tops])]
    [:div.annotation-col
    (doall (map-indexed vbarno @tops))]))

(defn seek-bar [ev ff?]
  (.preventDefault ev)
  (re-frame/dispatch [::events/seek-bar ff?]))

(defn play-pause [ev]
  (.preventDefault ev)
  (re-frame/dispatch [::events/play-pause]))

(defn play-speed [ev faster?]
  (.preventDefault ev)
  (re-frame/dispatch [::events/play-speed faster?]))

(defn control-panel-dragger
  [ev]
  (re-frame/dispatch [::events/drag-control-panel ev]))

(defn control-panel []
  (let [playing? (re-frame/subscribe [::subs/playing?])
        info (re-frame/subscribe [::subs/control-panel-info])
        [dragging-control-panel? control-panel-pos] @info
        [control-panel-x control-panel-y] control-panel-pos]
    [:div.control-panel
     {:style {:transform (str "translate(" control-panel-x "px, " control-panel-y "px)")}
      :on-mouse-down #(when-not dragging-control-panel? (control-panel-dragger %))
      :on-touch-start #(when-not dragging-control-panel? (control-panel-dragger %))
      }
     [:a.btn.rewind
      {:href :#
       :on-click #(seek-bar % false)}
      ""]
     [:div.player
      [:div
       [:a.btn.play-pause
        {:href :#
         :class (if @playing? :playing "")
         :on-click #(play-pause %)}
        ""]]
      [:div.play-speed-panel
       [:a.btn.play-faster
        {:href :#
         :on-click #(play-speed % true)}
        ""]
       [:a.btn.play-slower
        {:href :#
         :on-click #(play-speed % false)}
        ""]]]
     [:a.btn.fast-forward
      {:href :#
       :on-click #(seek-bar % true)}
      ""]])
  )

(defn submit-dialog []
  (let [selector "input[name=from-fs]"
        has-file? (u/uploading-filename selector)
        url (.-value (u/dom "input[name=from-net]"))
        index-url (if-let [sel (u/dom "select[name=from-index]")]
                    (.-value sel) "")]
    (if has-file?
      (re-frame/dispatch [::events/load-score-file selector])
      (if (not-empty url)
        (re-frame/dispatch [::events/load-score-url url])
        (if (not-empty index-url)
          (re-frame/dispatch [::events/load-score-url index-url])
          (js/alert "読み込みたいレッスンメモを指定してくださいな。"))))))

(defn score-options [scores]
  (doall (map-indexed
           (fn [ix {:keys [name url]}]
             [:option
              {:value url
               :key ix}
              name]) scores)))

(defn dialog []
  (let [dialog-info (re-frame/subscribe [::subs/dialog-info])
        [dialog-state] @dialog-info
        score-index (re-frame/subscribe [::subs/score-index])]
    [:div.dialog-overlay
     {:class (if (= dialog-state :close) "" :dialog-open)}
     [:div.dialog-wrap
      [:div.dialog-title
       "レッスンメモ読み込み♬"]
      [:p.dialog-desc
       "下記の、いずれかの方法でレッスンメモを読み込みます。"]
      [:div.dialog-form
       [:div.dialog-item
        [:div.form-label "手元のファイルから選ぶ:"]
        [:input.form-input.input-file
         {:type :file
          :name :from-fs}]]
       [:div.dialog-item
        [:div.form-label "ネット上のファイルを読む(URL):"]
        [:input.form-input.input-text
         {:type :text
          :name :from-net
          :size 300}]]
       (if (not-empty @score-index)
         [:div.dialog-item
          [:div.form-label "用意されたメモの中から選ぶ:"]
          [:select.form-input.input-select
           {:name :from-index}
           [:option]
           (score-options @score-index)]])
       [:div.dialog-panel
        [:button.btn.ok
         {:on-click submit-dialog
          :disabled (= dialog-state :busy)}
         "決定"]
        [:button.btn.cancel
         {:on-click #(re-frame/dispatch [::events/toggle-dialog false])
          :disabled (= dialog-state :busy)}
         "キャンセル"]]]]]))

(defn main-panel []
  (let [info (re-frame/subscribe [::subs/control-panel-info])
        [dragging-control-panel?] @info
        audio? (re-frame/subscribe [::subs/audio?])]
    [:div.app
     {:on-mouse-move #(when dragging-control-panel? (control-panel-dragger %))
      :on-mouse-up #(when dragging-control-panel? (control-panel-dragger %))
      :on-mouse-leave #(when dragging-control-panel? (control-panel-dragger %))
      :on-touch-move #(when dragging-control-panel? (control-panel-dragger %))
      :on-touch-end #(when dragging-control-panel? (control-panel-dragger %))
      :on-touch-cancel #(when dragging-control-panel? (control-panel-dragger %))}
     [:header.header
      [:h1.brand
       "ピアノ教室のおと"]
      (read-score)
      (score-info)
      [:div.btn.speaker
       {:class (when @audio? :speaker-on)
        :on-click #(re-frame/dispatch [::events/toggle-audio])}]]
     [:div.main-container
      (indicator)
      [:div.main-col
       (keys-88)
       (timeline)]
      (annotation)]
     (control-panel)
     (dialog)
     ]))
