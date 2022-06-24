(ns yummy-commerce.mongodb
  (:require [monger.core :as mg]
            [monger.collection :as mc]))


(def connection-string "mongodb+srv://leo:vd6QwddBt2Tl88y2@cluster0.huek09w.mongodb.net/products?retryWrites=true&w=majority")

(def db
  (:db
    (mg/connect-via-uri connection-string)))

(defn confiture-by-id [id]
  (dissoc 
    (mc/find-one-as-map db "confitures" {:id id})
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

(confiture-by-id "peche")
(sucette-by-id "sucette-coeur")
(type (confitures))
(nth (confitures) 1)

(:description (sucette-by-id "sucette-coeur"))


