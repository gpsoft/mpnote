(ns mpnote.views
  (:require
   [re-frame.core :as re-frame]
   [mpnote.styles :as styles]
   [mpnote.subs :as subs]
   [mpnote.events :as events]
   ))

(defn score-info []
  (let [info (re-frame/subscribe [::subs/score-info])
        [title url] @info]
    (when title
      [:a.title-link
       {:href url
        :target "_blank"}
       title])))

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
    {:class (str (name black-white) "-key")
     :key no
     :data-note-no no}
    (when tl? (key-1-for-tl no))]))

(defn keys-3
  [no tl?]
  [:div.octave.keys-3
   (key-1 :white (+ no 0) tl?)
   (key-1 :black (+ no 1) tl?)
   (key-1 :white (+ no 2) tl?)
   ])

(defn keys-1 [no tl?]
  [:div.octave.keys-1
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

(defn keys-88
  ([] (keys-88 false))
  ([tl?]
   [:div
    {:class (if tl? [:timeline :jsTimeline] :keys-88)}
    (keys-3 21 tl?)
    (doall (map #(keys-12 (+ (* % 12) 24) tl?) (range 7)))
    (keys-1 108 tl?)
    (when tl? (cur-step))
    (when tl? (bar-tops))
    ]))
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

(defn move-step [ev ff?]
  (.preventDefault ev)
  (re-frame/dispatch [::events/move-step ff?]))

(defn play-pause [ev]
  (.preventDefault ev)
  (re-frame/dispatch [::events/play-pause]))

(defn control-panel []
  (let [playing? (re-frame/subscribe [::subs/playing?])
        control-panel-pos (re-frame/subscribe [::subs/control-panel-pos])
        [control-panel-x control-panel-y] @control-panel-pos]
    [:div.control-panel
     {:style {:transform (str "translate(" control-panel-x "px, " control-panel-y "px)")}
      :on-drag-start #(re-frame/dispatch [::events/drag-control-panel %])
      :on-drag-end #(re-frame/dispatch [::events/drag-control-panel %])
      :on-drag #(re-frame/dispatch [::events/drag-control-panel %])}
     [:a.btn.rewind
      {:href :#
       :on-click #(move-step % false)}
      ""]
     [:a.btn.play-pause
      {:href :#
       :class (if @playing? :playing "")
       :on-click #(play-pause %)}
      ""]
     [:a.btn.fast-forward
      {:href :#
       :on-click #(move-step % true)}
      ""]])
  )

(defn main-panel []
  [:div.app
   [:header.header
    [:h1.brand
     "ピアノ教室のおと"]
    (score-info)]
   [:div.main-container
    (indicator)
    [:div.main-col
     (keys-88)
     (timeline)]
    [:div.annotation-col]]
   (control-panel)
   ])
