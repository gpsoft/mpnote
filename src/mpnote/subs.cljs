(ns mpnote.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::cur-step
 (fn [db]
   (:cur-step-ix db)))

(re-frame/reg-sub
  ::bar-tops
  (fn [db]
    (->> (get-in db [:score :steps] [])
         (map-indexed #(if (:bar-top? %2) %1 nil))
         (filter some?))))

(re-frame/reg-sub
  ::pedals
  (fn [db]
    (->> (get-in db [:score :steps] [])
         (map-indexed #(if (nil? (:pedal %2)) nil [%1 (= (:pedal %2) :on)]))
         (filter some?))))

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
