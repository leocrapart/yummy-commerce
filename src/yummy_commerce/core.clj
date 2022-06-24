(ns yummy-commerce.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [yummy-commerce.mongodb :as db]))


(def html-header
  {"Content-Type" "text/html"})

(def json-header
  {"Content-Type" "text/json"})


;;


(defn get-confiture-res [req]
  (let [id (get-in req [:params :id])
        confiture (db/confiture-by-id id)]
    {:status 200
     :headers json-header
     :body (json/write-str confiture)}))

(defn get-sucette-res [req]
  (let [id (get-in req [:params :id])
        sucette (db/sucette-by-id id)]
    {:status 200
     :headers json-header
     :body (json/write-str sucette)}))

(defn get-confitures-res [req]
  {:status 200
   :headers json-header
   :body (json/write-str (db/confitures))})

(defn get-sucettes-res [req]
  {:status 200
   :headers json-header
   :body (json/write-str (db/sucettes))})



(defroutes api-routes
  (GET "/confitures/:id" [] get-confiture-res)
  (GET "/sucettes/:id" [] get-sucette-res)
  (GET "/confitures" [] get-confitures-res)
  (GET "/sucettes" [] get-sucettes-res))


(defn -main
  "App entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (server/run-server api-routes {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))

;;launch server from repl
; (-main)


