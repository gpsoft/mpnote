(ns mpnote.events
  (:require
   [re-frame.core :as re-frame]
   [mpnote.db :as db]
   [mpnote.styles :as styles]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(defn scroll-top
  [cur-top cur-step-ix]
  (let [step-top (styles/step-top cur-step-ix)
        vh (-> (.querySelector js/document ".jsTimeline")
               (.getBoundingClientRect)
               (.-height))
        min-top db/initial-scroll-top
        max-top (/ vh 2)]
    (- (max (min (+ cur-top step-top) max-top) min-top) step-top)))

(re-frame/reg-event-db
  ::move-step
  (fn-traced
    [db [_ ff?]]
    (let [top (:scroll-top db)
          cur (:cur-step-ix db)
          las (dec (count (get-in db [:score :steps])))
          cur (min las (max 0 (+ cur (if ff? 1 -1))))]
      (assoc db
             :cur-step-ix cur
             :scroll-top (scroll-top top cur)))))
