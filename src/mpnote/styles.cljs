(ns mpnote.styles
  (:require-macros
    [garden.def :refer [defcssfn]]
    garden.selectors)
  (:require
    [spade.core   :refer [defglobal defclass]]
    [garden.units :refer [deg px]]
    [garden.color :refer [rgba]]))

(def step-height 22)
(def note-line-height 18)
(def note-height (+ note-line-height 2 2)) ;padding and border
(def key-height 60)
(def pedal-height 32)
(defn step-top [step-ix] (+ (* step-ix step-height) 0))

(def indicator-width 60)
(def annotation-width 60)

(def color-main-text  "hsl(24deg 90% 20%)")
(def color-light-main "hsl(24deg 70% 90%)")
(def color-lighter-main "hsl(24deg 100% 93%)")
(def color-white :#f5f5f5)
(def color-black :#333333)
(def color-black-timeline :#c8c8c8)
(def color-note-fg :#333333)
(def color-left-note-bg "hsl(210deg 80% 80%)")
(def color-left-note-bd "hsl(210deg 80% 70%)")
(def color-right-note-bg "hsl(118deg 80% 80%)")
(def color-right-note-bd "hsl(118deg 80% 70%)")

; (defcssfn linear-gradient
;  ([c1 p1 c2 p2]
;   [[c1 p1] [c2 p2]])
;  ([dir c1 p1 c2 p2]
;   [dir [c1 p1] [c2 p2]]))

(defglobal defaults
  [:body
   {:color               color-main-text
    :background-color    color-light-main
    :font-size :16px
    :min-height :500px
    ; :background-image    [(linear-gradient :white (px 2) :transparent (px 2))
    ;                       (linear-gradient (deg 90) :white (px 2) :transparent (px 2))
    ;                       (linear-gradient (rgba 255 255 255 0.3) (px 1) :transparent (px 1))
    ;                       (linear-gradient (deg 90) (rgba 255 255 255 0.3) (px 1) :transparent (px 1))]
    ; :background-size     [[(px 100) (px 100)] [(px 100) (px 100)] [(px 20) (px 20)] [(px 20) (px 20)]]
    ; :background-position [[(px -2) (px -2)] [(px -2) (px -2)] [(px -1) (px -1)] [(px -1) (px -1)]]
    }]
  [:.app
   {:position :relative
    :display :flex
    :flex-direction :column
    :height :100vh}]
  [:.header
   {:display :flex
    :align-items :center
    :margin-bottom :0.5rem
    :border (str "1px solid " color-main-text)}
   [:.brand
    {:font-size :1.2rem
     :line-height :1.8rem
     :padding "0 4px"
     :color color-white
     :background-color color-main-text}]
   [:a.title-link
    {:flex-grow 1
     :text-decoration :none
     :padding-left :4px
     :line-height :1.8rem
     :color color-main-text
     :background-color color-lighter-main}
    [:&:visited
     {:color :inherit}]]
   [:.btn.read-score
    {:width :28px
     :height :28px
     :background "url('img/clef.png') no-repeat center"
     :background-size :cover
     :pointer :cursor}
    [:&:active
     {:opacity 0.8}]]
   ]
  [:.main-container
   {:display :flex
    :flex-grow 1}
   [:.indicator-col
    {:width (px indicator-width)
     :position :relative
     :margin-top (px key-height)
     :overflow :hidden}
    [:.pedal
     {:width (px pedal-height)
      :height (px pedal-height)
      :position :absolute
      :right :4px
      :transform (str "translateY(" (/ (- note-height pedal-height) 2) "px)")
      }
     [:&.pedal-on
      {:background "url('img/ped.png') no-repeat"
       :background-size :contain}]
     [:&.pedal-off
      {:background "url('img/senza.png') no-repeat"
       :background-size :contain}]]]
   [:.annotation-col
    {:width (px annotation-width)
     :position :relative
     :margin-top (px key-height)
     :overflow :hidden}
    [:.bar-no
     {:position :absolute
      :left :4px}]]
   [:.main-col
    {:display :flex
     :flex-direction :column
     :flex-grow 1}
    [:.keys-88 :.timeline
     {:display :flex}
     [:.keys-12 {:flex-grow 12}]
     [:.keys-3 {:flex-grow 3}]
     [:.keys-1 {:flex-grow 1}]]
    [:.keys-88
     {:height (px key-height)}
     [:.key-1
      [(garden.selectors/& (garden.selectors/attr= :data-note-no :60) (garden.selectors/before))
       {:content "''"
        :display :inline-block
        :width :10px
        :height :10px
        :position :absolute
        :bottom :4px
        :left :50%
        :transform "translateX(-50%)"
        :border-radius :50%
        :background-color :red}]]]
    [:.timeline
     {:min-height :400px    ;See min-height of body
      :position :relative
      :overflow :hidden
      :flex-grow 1}]

    [:.octave
     {:display :flex
      :border "1px solid #666666"}
     [:.key-1
      {:flex-grow 1
       :position :relative
       ; :overflow :hidden
       }]
     [(garden.selectors/+ :.key-1 :.key-1)
      {:border-left "1px solid #bbbbbb"}]]
    [:.keys-88 {}
     [:.white-key {:background-color color-white}]
     [:.black-key {:background-color color-black}]]
    [:.timeline {}
     [:.white-key {:background-color color-white}]
     [:.black-key {:background-color color-black-timeline}]]

    [:.timeline
     {:position :relative}
     [:.note :.dummy-note
      {:position :absolute
       :left :50%
       :transform "translateX(-50%)"
       :line-height (px note-line-height)
       :z-index 2}]
     [:.note
      {:padding "1px 2px"
       :border "1px solid white"
       :border-radius :50%
       :min-width :16px
       :text-align :center}
      [:&.left-note
       {:color color-note-fg
        :background-color color-left-note-bg
        :border-color color-left-note-bd}]
      [:&.right-note
       {:color color-note-fg
        :background-color color-right-note-bg
        :border-color color-right-note-bd}]]
     [:.dummy-note
      {:width :6px
       :height (px (- (* step-height 2) note-height))
       :transform (str "translate(-50%, " (- note-height step-height) "px)")}
      [:&.left-note
       {:background-color color-left-note-bd}]
      [:&.right-note
       {:background-color color-right-note-bd}]]
     [:.cur-step
      {:position :absolute
       :left 0
       :width :100%
       :border-top "3px solid #ff0000"
       :transform (str "translateY(" (- (/ note-height 2) 1) "px)")}]
     [:.bar-top
      {:position :absolute
       :left 0
       :width :100%
       :border-top "1px solid #666666"}]
     ]
    ]]
  [:.control-panel
   {:position :absolute
    :right (px (+ annotation-width 16))
    :top (px (+ key-height 60))
    :cursor :pointer
    :padding :2px
    :padding-top :18px
    :background "hsl(210deg 30% 90%) url('img/staff.png') repeat-x top"
    :border (str "3px solid " color-left-note-bd)
    :border-bottom-left-radius :6px
    :border-bottom-right-radius :6px
    :z-index 3}
   [:&::before
    {:content "'\\1d122'"         ;"'𝄞\\1d11e'"
     :font-size :16px
     :position :absolute
     :display :inline-block
     :line-height :16px
     :top :2px
     :left 0
     }]
   [:.btn
    {:display :block
     :width :64px
     :height :64px
     :background "no-repeat center"
     :background-size "contain"}
    [:&:active
     {:opacity 0.8}]
    [:&.rewind
     {:background-image "url('img/rewind.png')"}]
    [:&.fast-forward
     {:background-image "url('img/fast-forward.png')"}]
    [:&.play-pause
     {:background-image "url('img/play.png')"}]
    [:&.play-pause.playing
     {:background-image "url('img/pause.png')"}]
    [:&.play-faster
     {:background-image "url('img/faster.png')"
      :top :-10px}]
    [:&.play-slower
     {:background-image "url('img/slower.png')"
      :bottom :-10px}]
    ]
   [:.player
    {:display :flex
     :align-items :flex-start
     :width :120px}
    [:.play-speed-panel
     {:position :relative
      :height :64px}
     [:.btn
      {:position :absolute
       :width :52px
       :height :40px
       :background-color "hsl(24deg 100% 67%)"
       :border-radius :50%}]]]
   #_[(garden.selectors/> :.player :div)
    {:display :div}]]
  [:.dialog-overlay
   {:position :absolute
    :width :100vw
    :height :100vh
    :z-index 2
    :display :flex
    :visibility :hidden
    :opacity 0
    :justify-content :center
    :align-items :center
    :background-color "rgb(0,0,0,0.6)"}
   [:&.dialog-open
    {:visibility :visible
     :opacity 1
     :transition "opacity 0.5s ease"}]
   [:.dialog-wrap
    {:background-color color-white
     :border (str "6px solid " color-main-text)
     :border-radius :4px
     :box-shadow "0 0 8px #ffffff"
     :width :50%
     :min-width :500px
     :max-width :700px
     :padding :1rem
     }
    [:.dialog-title
     {:font-weight :bold
      :font-size :1.2rem
      :border-bottom (str "2px solid " color-main-text)
      :margin-bottom :8px}]
    [:.dialog-desc
     {:font-family :serif
      :font-size :0.9rem
      :margin-left :1em
      :margin-bottom :8px}]
    [(garden.selectors/> :.dialog-form :div)
     {:margin-top :8px}]
    [:.form-input
     {:color :black
      :margin-left :3em
      :margin-right :1em
      :max-width :80%}
     [#_(garden.selectors/& (garden.selectors/attr= :type :text))
      :&.input-text
      {:border (str "1px solid " color-main-text)
       :border-radius :3px
       :padding "4px 8px"
       :size 50}]
     [:&.input-select
      {:border (str "1px solid " color-main-text)
       :border-radius :3px
       :background-color :#ffffff
       :padding "4px 8px"
       }
      ]]
    [:.dialog-panel
     [:.btn
      {:font-size :1rem
       :border "1px solid #aaaaaa"
       ; :border-radius :3px
       :margin-right :1rem
       :padding "4px 16px"}
      [:&:active
       {:opacity 0.8}]
      [:&.ok
       {:background-color color-left-note-bg}]]]]]
  )
