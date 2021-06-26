(ns mpnote.styles
  (:require-macros
    [garden.def :refer [defcssfn]]
    garden.selectors)
  (:require
    [spade.core   :refer [defglobal defclass]]
    [garden.units :refer [deg px]]
    [garden.color :refer [rgba]]))

(def step-height 30)
(def note-line-height 20)
(def note-height (+ note-line-height 2 2)) ;padding and border
(def key-height 60)
(def timeline-height-vh 75)
(def pedal-height 32)
(defn step-top [step-ix] (+ (* step-ix step-height) 0))

(def color-main-text  "hsl(24deg 90% 20%)")
(def color-light-main "hsl(24deg 70% 90%)")
(def color-white :#f5f5f5)
(def color-black :#333333)
(def color-black-timeline :#dddddd)
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
    :padding :0.5rem
    :min-height :500px
    ; :background-image    [(linear-gradient :white (px 2) :transparent (px 2))
    ;                       (linear-gradient (deg 90) :white (px 2) :transparent (px 2))
    ;                       (linear-gradient (rgba 255 255 255 0.3) (px 1) :transparent (px 1))
    ;                       (linear-gradient (deg 90) (rgba 255 255 255 0.3) (px 1) :transparent (px 1))]
    ; :background-size     [[(px 100) (px 100)] [(px 100) (px 100)] [(px 20) (px 20)] [(px 20) (px 20)]]
    ; :background-position [[(px -2) (px -2)] [(px -2) (px -2)] [(px -1) (px -1)] [(px -1) (px -1)]]
    }]
  [:.main-container
   {:display :flex}
   [:.indicator-col
    {:width "60px"
     :position :relative
     :margin-top (px key-height)
     :overflow :hidden
     ; :height "100vh"
     ; :background-color color-white
     }
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
    {:width "60px"
     ; :height "100vh"
     ; :background-color :red
     }]
   [:.main-col
    {:flex-grow 1
     ; :height "100vh"
     ; :background-color :blue
     }
    [:.keys-88 :.timeline
     {:display :flex}
     [:.keys-12 {:flex-grow 12}]
     [:.keys-3 {:flex-grow 3}]
     [:.keys-1 {:flex-grow 1}]]
    [:.keys-88
     {:height (px key-height)}]
    [:.timeline
     {:height (str timeline-height-vh "vh")
      :min-height :400px    ;See min-height of body
      :position :relative
      :overflow :hidden
      }]

    [:.octave
     {:display :flex
      :border "1px solid #666666"}
     [:.key-1
      {:flex-grow 1
       :position :relative
       ; :overflow :hidden
       }]
     [(garden.selectors/+ :.key-1 :.key-1)
      {:border-left "1px solid #dddddd"}]]
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
    ]])

(defclass brand
  []
  {:font-size :1.2rem
   :margin-bottom :0.5rem
   :color color-main-text})
