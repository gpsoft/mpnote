(ns mpnote.db)

(def default-db
  {:name "re-frame"
   :cur-step-ix 2
   :score
   {:title "ベートーヴェン - Op.27.No.2 -「月光 - 第1楽章」"
    :url "https://www.youtube.com/watch?v=S73r-xb1d6c"
    :ticks-per-bar 12
    :steps
    [{:type :tick
      :tick-no 1
      :bar-top? true
      :pedal :on
      :notes
      [  ;; :note-no       21-108
         ;; :hand          :left or :right
         ;; :finger-no     0, 1/2/3/4/5, 12/54/15...
         ;; :length        デフォルトで1、単位はtick
       {:note-no 37
        :hand :left
        :finger-no 4
        :length 12}
       {:note-no 49
        :hand :left
        :finger-no 1
        :length 12}
       {:note-no 56
        :hand :right
        :finger-no 1}]}
     {:type :tick
      :tick-no 2
      :notes
      [{:note-no 61
        :hand :right
        :finger-no 2}]}
     {:type :tick
      :tick-no 3
      :notes
      [{:note-no 64
        :hand :right
        :finger-no 4}]}
     {:type :tick
      :tick-no 4
      :notes
      [{:note-no 56
        :hand :right
        :finger-no 1}]}
     {:type :tick
      :tick-no 5
      :notes
      [{:note-no 61
        :hand :right
        :finger-no 2}]}
     {:type :tick
      :tick-no 6
      :notes
      [{:note-no 64
        :hand :right
        :finger-no 4}]}
     {:type :tick
      :tick-no 7
      :notes
      [{:note-no 56
        :hand :right
        :finger-no 1}]}
     {:type :tick
      :tick-no 8
      :notes
      [{:note-no 61
        :hand :right
        :finger-no 2}]}
     {:type :tick
      :tick-no 9
      :notes
      [{:note-no 64
        :hand :right
        :finger-no 4}]}
     {:type :tick
      :tick-no 10
      :notes
      [{:note-no 56
        :hand :right
        :finger-no 1}]}
     {:type :tick
      :tick-no 11
      :notes
      [{:note-no 61
        :hand :right
        :finger-no 2}]}
     {:type :tick
      :tick-no 12
      :notes
      [{:note-no 64
        :hand :right
        :finger-no 4}]}
     {:type :tick
      :tick-no 13
      :bar-top? true
      :pedal :on
      :notes
      [{:note-no 35
        :hand :left
        :finger-no 54
        :length 12}
       {:note-no 47
        :hand :left
        :finger-no 1
        :length 12}
       {:note-no 56
        :hand :right
        :finger-no 1}]}]
    }})
