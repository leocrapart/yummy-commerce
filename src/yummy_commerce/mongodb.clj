(ns yummy-commerce.mongodb
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(def connection-string "")


(def db
  (:db
    (mg/connect-via-uri connection-string)))


;; url <-> id
;; :id not detected by mongo driver
(defn confiture-by-id [id]
  (dissoc 
    (mc/find-one-as-map db "confitures" {:url id})
    :_id))

(defn sucette-by-id [id]
  (dissoc 
    (mc/find-one-as-map db "sucettes" {:id id})
    :_id))

(defn dissoc-oid [document]
  (dissoc document :_id))

(defn confitures []
  (map dissoc-oid
    (mc/find-maps db "confitures")))

(defn sucettes []
  (map dissoc-oid
    (mc/find-maps db "sucettes")))


(defn confitures-by-season [season]
  (map dissoc-oid
    (mc/find-maps db "confitures" {:season season})))

(defn confitures-with-query [query]
  (map dissoc-oid
    (mc/find-maps db "confitures" query)))

(confiture-by-id "fraise")
(sucette-by-id "sucette-coeur")
(confitures)
(sucettes)
(confitures-by-season "été")
; (map :url (confitures-by-season "été"))
(confitures-with-query {:season "été"})

(type (confitures))
(nth (confitures) 1)

(:description (sucette-by-id "sucette-coeur"))


