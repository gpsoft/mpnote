(ns mpnote.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(defn note-with-dummies
  [tick-no {:keys [note-no hand finger-no length] :as note}]
  (let [main-note (assoc note :tick-no tick-no)]
    (if (> length 1)
      (conj
        (map #(assoc {:hand hand} :tick-no (+ tick-no (inc %))) (range (dec length)))
        main-note)
      [main-note])))

(defn my-notes
  [my-note-no {:keys [tick-no notes]}]
  (let [mines (filter #(= (:note-no %) my-note-no) notes)]
    #_(js/console.log (clj->js mines))
    (mapcat #(note-with-dummies tick-no %) mines)))

(re-frame/reg-sub
 ::notes
 (fn [db [_ note-no]]
   (mapcat #(my-notes note-no %) (get-in db [:score :steps] []))))
