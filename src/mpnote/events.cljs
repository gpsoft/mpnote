(ns mpnote.events
  (:require
   [re-frame.core :as re-frame]
   [mpnote.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::move-step
 (fn-traced [db [_ ff?]]
            (let [cur (:cur-step-ix db)
                  las (dec (count (get-in db [:score :steps])))
                  cur (min las (max 0 (+ cur (if ff? 1 -1))))]
              (assoc db :cur-step-ix cur))))
