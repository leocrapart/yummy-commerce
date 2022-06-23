(ns yummy-commerce.mongodb
  (:require [monger.core :as mg]
            [monger.collection :as mc]))


(def connection-string "mongodb+srv://leo:vd6QwddBt2Tl88y2@cluster0.huek09w.mongodb.net/products?retryWrites=true&w=majority")

(def db
  (:db
    (mg/connect-via-uri connection-string)))

(defn confiture-by-id [id]
  (mc/find-one-as-map db "confitures" {:id id}))

(defn sucette-by-id [id]
  (mc/find-one-as-map db "sucettes" {:id id}))

(confiture-by-id "peche")
(sucette-by-id "sucette-coeur")