(ns mpnote.db
  (:require
    [mpnote.utils :as u]))

(def initial-scroll-top 20)

(def moonlight
  {:title "ベートーヴェン - Op.27.No.2 -「月光 - 第1楽章」 Lesson1"
   :url "https://www.youtube.com/watch?v=S73r-xb1d6c"
   :tempo 120

   ;; stepは見た目上の1行に相当。
   ;; tickは時刻を反映。
   :steps
   [
    {:bar-top? true, :notes []} {:notes []} {:notes []} {:notes []}
    {
     ;; :step-ix       to be enriched
     ;; :tickstamp     to be enriched
     ;; :bar-top?      小節の先頭か?
     ;; :pedal         :on or :off
     ;; :weight        デフォルトで1、単位はtick
     ;; :notes
     :bar-top? true
     :pedal :on
     :notes
     [   ;; :type          :press or :hold デフォルトで:press
         ;; :note-no       21-108
         ;; :hand          :left or :right
         ;; :finger-no     (only for :press) 0, 1/2/3/4/5, 12/54/15...
         ;; :length        (only for :press) デフォルトで1、単位はtick
      {:note-no 37 :hand :left :finger-no 4 :length 12}
      {:note-no 49 :hand :left :finger-no 1 :length 12}
      {:note-no 56 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 35 :hand :left :finger-no 54 :length 12}
      {:note-no 47 :hand :left :finger-no 1 :length 12}
      {:note-no 56 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 33 :hand :left :finger-no 5 :length 6}
      {:note-no 45 :hand :left :finger-no 1 :length 6}
      {:note-no 57 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 57 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 30 :hand :left :finger-no 5 :length 6}
      {:note-no 42 :hand :left :finger-no 1 :length 6}
      {:note-no 57 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 62 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 5}]}
    {
     :notes
     [{:note-no 57 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 62 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 5}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 32 :hand :left :finger-no 4 :length 6}
      {:note-no 44 :hand :left :finger-no 1 :length 6}
      {:note-no 56 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 60 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 5}]}
    {
     :pedal :on
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 5}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 32 :hand :left :finger-no 5 :length 6}
      {:note-no 44 :hand :left :finger-no 12 :length 6}
      {:note-no 56 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 4}]}
    {
     :pedal :on
     :notes
     [{:note-no 54 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 60 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 5}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 37 :hand :left :finger-no 5 :length 12}
      {:note-no 44 :hand :left :finger-no 2 :length 12}
      {:note-no 49 :hand :left :finger-no 1 :length 12}
      {:note-no 52 :hand :right :finger-no 1}
      ]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}
      {:note-no 68 :hand :right :finger-no 5 :length 2.5}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :weight 0.5
     :notes
     [{:note-no 64 :hand :right :finger-no 3}]}
    {
     :weight 0.5
     :notes
     [{:note-no 68 :hand :right :finger-no 5 :length 0.5}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 36 :hand :left :finger-no 5 :length 12}
      {:note-no 44 :hand :left :finger-no 2 :length 12}
      {:note-no 48 :hand :left :finger-no 1 :length 12}
      {:note-no 56 :hand :right :finger-no 1}
      {:note-no 68 :hand :right :finger-no 5 :length 9}
      ]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}
      {:note-no 68 :hand :right :finger-no 5 :length 2.5}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 2}]}
    {
     :weight 0.5
     :notes
     [{:note-no 66 :hand :right :finger-no 3}]}
    {
     :weight 0.5
     :notes
     [{:note-no 68 :hand :right :finger-no 4 :length 0.5}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 37 :hand :left :finger-no 5 :length 6}
      {:note-no 49 :hand :left :finger-no 1 :length 6}
      {:note-no 56 :hand :right :finger-no 1}
      {:note-no 68 :hand :right :finger-no 5 :length 6}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 3}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 30 :hand :left :finger-no 5 :length 6}
      {:note-no 42 :hand :left :finger-no 1 :length 6}
      {:note-no 57 :hand :right :finger-no 1}
      {:note-no 69 :hand :right :finger-no 5 :length 6}
      ]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 57 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 61 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 66 :hand :right :finger-no 4}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 35 :hand :left :finger-no 5 :length 6}
      {:note-no 47 :hand :left :finger-no 1 :length 6}
      {:note-no 56 :hand :right :finger-no 1}
      {:note-no 68 :hand :right :finger-no 5 :length 6}
      ]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 35 :hand :left :finger-no 5 :length 6}
      {:note-no 47 :hand :left :finger-no 1 :length 6}
      {:note-no 57 :hand :right :finger-no 1}
      {:note-no 66 :hand :right :finger-no 5 :length 3}
      ]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 3}]}
    {
     :notes
     [{:note-no 57 :hand :right :finger-no 1}
      {:note-no 71 :hand :right :finger-no 5 :length 3}]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 63 :hand :right :finger-no 2}]}
    {
     :bar-top? true
     :pedal :on
     :notes
     [
      {:note-no 40 :hand :left :finger-no 5 :length 12}
      {:note-no 52 :hand :left :finger-no 1 :length 12}
      {:note-no 56 :hand :right :finger-no 1}
      {:note-no 64 :hand :right :finger-no 4}
      ]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    {
     :notes
     [{:note-no 56 :hand :right :finger-no 1}]}
    {
     :notes
     [{:note-no 59 :hand :right :finger-no 2}]}
    {
     :notes
     [{:note-no 64 :hand :right :finger-no 4}]}
    ]
   })

(defn long-note
  [weight {:keys [length] :or {length 1}}]
  (> length weight))

(defn long-notes
  [steps]
  (mapcat (fn [{:keys [step-ix tickstamp weight notes]}]
            (->> (filter #(long-note weight %) notes)
                (map #(assoc % :tickstamp tickstamp)) ))
          steps))

(defn in-step
  [tick {:keys [tickstamp length] :or {length 1}}]
  (< tickstamp tick (+ tickstamp length)))

(defn make-dummy-note
  [{:keys [note-no hand]}]
  {:type :hold
   :note-no note-no
   :hand hand})

(defn make-dummy-notes
  [lnotes {:keys [tickstamp notes] :as step}]
  (let [notes-for-step (filter #(in-step tickstamp %) lnotes)
        notes-for-step (map make-dummy-note notes-for-step)]
    (assoc step :notes (concat notes notes-for-step))))

(defn append-dummy-notes
  [steps]
  (let [lnotes (long-notes steps)]
    (map #(make-dummy-notes lnotes %) steps)))

(comment
  (let [steps (:steps moonlight)
        steps (first (reduce enrich-step [[] 0 0] steps))
        steps (append-dummy-notes steps) #_(long-notes steps)]
    steps)
  )

(defn enrich-step
  [[steps ix tick] {:keys [weight] :or {weight 1} :as step}]
  (let [step (assoc step
                    :step-ix ix
                    :weight weight
                    :tickstamp tick)]
    [(conj steps step)
     (inc ix)
     (+ tick weight)]))

(defn all-notes
  [score]
  (->> (:steps score)
       (mapcat (fn [step]
                 (:notes step)))))

(defn enrich-score
  [{:keys [steps] :as score}]
  (let [steps (first (reduce enrich-step [[] 0 0] steps))
        steps (append-dummy-notes steps)
        all-note-nos (->> score
                          all-notes
                          (map #(:note-no %)))]
    (assoc score
           :steps steps
           :max-note-no (apply max all-note-nos)
           :min-note-no (apply min all-note-nos))))

(defn audio?
  [db]
  (not= (:audio db) :off))

(defn rotate-audio
  [audio]
  (let [m {:off :on
           :on :left
           :left :right
           :right :off}]
    (audio m)))

(defn calclated-tempo
  ([db]
   (let [tempo (get-in db [:score :tempo] 120)
         tempo-bias (:tempo-bias db)]
     (+ tempo tempo-bias))))

(def default-db
  {
   :cur-step-ix 0
   :playing? false
   ; :audio? false
   :audio :off
   :full-keys? false
   :tempo-bias 0
   :dialog-state :close
   :scroll-top initial-scroll-top
   :control-panel-pos [0 0]
   :dragging-control-panel-from nil
   :score-index []
   :score (enrich-score moonlight)})

(comment
  (require '[cljs-bach.synthesis :as bach])
  (defonce context (cljs-bach.synthesis/audio-context))
  (identity context)
  (defn ping [freq]
    (cljs-bach.synthesis/connect->
      (cljs-bach.synthesis/square freq)
      (cljs-bach.synthesis/percussive 0.01 0.4)
      (cljs-bach.synthesis/gain 0.1)))
  (-> (ping 440)
      (cljs-bach.synthesis/connect-> cljs-bach.synthesis/destination)
      (cljs-bach.synthesis/run-with context (cljs-bach.synthesis/current-time context) 1.0))
  )
