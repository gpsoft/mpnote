(ns mpnote.db)

(def moonlight
  {:title "ベートーヴェン - Op.27.No.2 -「月光 - 第1楽章」"
   :url "https://www.youtube.com/watch?v=S73r-xb1d6c"
   :ticks-per-bar 12

   ;; stepは見た目上の1行に相当。
   ;; tickは時刻を反映。
   :steps
   [{
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
    {
     :notes
     [{:note-no 61
       :hand :right
       :finger-no 2}]}
    {
     :notes
     [{:note-no 64
       :hand :right
       :finger-no 4}]}
    {
     :notes
     [{:note-no 56
       :hand :right
       :finger-no 1}]}
    {
     :notes
     [{:note-no 61
       :hand :right
       :finger-no 2}]}
    {
     :notes
     [{:note-no 64
       :hand :right
       :finger-no 4}]}
    {
     :notes
     [{:note-no 56
       :hand :right
       :finger-no 1}]}
    {
     :notes
     [{:note-no 61
       :hand :right
       :finger-no 2}]}
    {
     :notes
     [{:note-no 64
       :hand :right
       :finger-no 4}]}
    {
     :notes
     [{:note-no 56
       :hand :right
       :finger-no 1}]}
    {
     :notes
     [{:note-no 61
       :hand :right
       :finger-no 2}]}
    {
     :notes
     [{:note-no 64
       :hand :right
       :finger-no 4}]}
    {
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
       :finger-no 1}]}
    {
     :notes
     [{:note-no 61
       :hand :right
       :finger-no 2}]}]
   })

(defn long-note
  [{:keys [length] :or {length 1}}]
  (> length 1))

(defn long-notes
  [steps]
  (mapcat (fn [{:keys [step-ix tickstamp notes]}]
            (->> (filter long-note notes)
                (map #(assoc % :tickstamp tickstamp)) ))
          steps))

(defn in-step
  [tick {:keys [tickstamp length]}]
  (and (> tick tickstamp) (< tick (+ tickstamp length))))

(defn make-dummy-note
  [{:keys [note-no hand]}]
  {:type :hold
   :note-no note-no
   :hand hand})

(defn a
  [lnotes {:keys [tickstamp notes] :as step}]
  (let [notes-for-step (filter #(in-step tickstamp %) lnotes)
        notes-for-step (map make-dummy-note notes-for-step)]
    (assoc step :notes (concat notes notes-for-step))))

(defn append-dummy-notes
  [steps]
  (let [lnotes (long-notes steps)]
    (map #(a lnotes %) steps)))

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
                    :tickstamp tick)]
    [(conj steps step)
     (inc ix)
     (+ tick weight)]))

(defn enrich-score
  [{:keys [steps] :as score}]
  (let [steps (first (reduce enrich-step [[] 0 0] steps))
        steps (append-dummy-notes steps)]
    (assoc score :steps steps)))

(def default-db
  {
   :cur-step-ix 0
   :score (enrich-score moonlight)})
