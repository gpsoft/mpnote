(ns mpnote.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::score-info
 (fn [db]
   (let [title (get-in db [:score :title])
         url (get-in db [:score :url])
         min-note-no (get-in db [:score :min-note-no])
         max-note-no (get-in db [:score :max-note-no])]
     [title url [min-note-no max-note-no]])))

(re-frame/reg-sub
 ::cur-step
 (fn [db]
   (:cur-step-ix db)))

(re-frame/reg-sub
 ::playing?
 (fn [db]
   (:playing? db)))

(re-frame/reg-sub
 ::full-keys?
 (fn [db]
   (:full-keys? db)))

(re-frame/reg-sub
 ::audio?
 (fn [db]
   (:audio? db)))

(re-frame/reg-sub
 ::dialog-info
 (fn [db]
   [(:dialog-state db)]))

(re-frame/reg-sub
 ::score-index
 (fn [db]
   (:score-index db)))

(re-frame/reg-sub
 ::scroll-top
 (fn [db]
   (:scroll-top db)))

(re-frame/reg-sub
 ::control-panel-info
 (fn [db]
   [(some? (:dragging-control-panel-from db))
    (:control-panel-pos db)]))

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
