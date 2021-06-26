(ns mpnote.styles
  (:require-macros
    [garden.def :refer [defcssfn]]
    garden.selectors)
  (:require
    [spade.core   :refer [defglobal defclass]]
    [garden.units :refer [deg px]]
    [garden.color :refer [rgba]]))

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

(defcssfn linear-gradient
 ([c1 p1 c2 p2]
  [[c1 p1] [c2 p2]])
 ([dir c1 p1 c2 p2]
  [dir [c1 p1] [c2 p2]]))

(defglobal defaults
  [:body
   {:color               color-main-text
    :background-color    color-light-main
    :font-size :16px
    :padding :0.5rem
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
    {:width "80px"
     :position :relative
     :margin-top :10vh
     ; :height "100vh"
     :background-color color-white
     }
    [:.pedal
     {:width :32px
      :height :32px
      :position :absolute
      :right :4px
      :transform "translateY(-8px)"}
     [:&.pedal-on
      {:background "url('img/ped.png') no-repeat"
       :background-size :contain}]
     [:&.pedal-off
      {:background "url('img/senza.png') no-repeat"
       :background-size :contain}]]]
   [:.annotation-col
    {:width "80px"
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
     {:height :10vh}]
    [:.timeline
     {:height :75vh
      :position :relative}]

    [:.octave
     {:display :flex
      :border "1px solid #666666"}
     [:.key-1
      {:flex-grow 1
       :position :relative}]
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
       :line-height :16px
       :z-index 2}]
     [:.note
      {:padding :1px
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
       :height :40px
       :transform "translate(-50%, -20px)"}
      [:&.left-note
       {:background-color color-left-note-bd}]
      [:&.right-note
       {:background-color color-right-note-bd}]]
     [:.cur-step
      {:position :absolute
       :left 0
       :width :100%
       :border-top "3px solid #ff0000"
       :transform "translateY(10px)"}]
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
