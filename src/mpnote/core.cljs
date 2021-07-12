(ns mpnote.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [mpnote.events :as events]
   [mpnote.views :as views]
   [mpnote.config :as config]
   [mpnote.audio]
   [day8.re-frame.http-fx]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch [::events/load-index])
  (dev-setup)
  (mount-root))
