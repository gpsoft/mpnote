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

(defn my-note
  ;; 1step内に、my noteは高々一つだけのはず。
  [my-note-no {:keys [step-ix notes]}]
  (let [my-note (->> notes
                     (filter #(= (:note-no %) my-note-no))
                     (first))]
    (when my-note
      (assoc my-note :step-ix step-ix))))

(re-frame/reg-sub
  ::notes
  (fn [db [_ note-no]]
    (let [steps (get-in db [:score :steps] [])]
      (->> steps
           (map #(my-note note-no %))
           (filter some?)))))
