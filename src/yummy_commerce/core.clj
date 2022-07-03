(ns yummy-commerce.core
  (:gen-class)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [org.httpkit.server :as server]
            [ring.util.codec :as codec]
            [yummy-commerce.mongodb :as db]))
            

(def html-header
  {"Content-Type" "text/html"})

(def json-header
  {"Content-Type" "text/json"})


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


(defn is-nil-or-empty-string [value]
  (if value
    (if (= "" value)
      true
      false)
    true))

; (is-nil-or-empty-string "")
; (is-nil-or-empty-string nil)
; (is-nil-or-empty-string "season")

(defn decoded-query-string [query-string]
  (clojure.walk/keywordize-keys (codec/form-decode query-string)))

; (decoded-query-string "id=2&name=fraise&camion=tchoutchou")
; (decoded-query-string "season=été")

(defn params [query-string]
  (if (is-nil-or-empty-string query-string)
    {}
    (decoded-query-string query-string)))

(params "id=2&name=fraise&camion=tchoutchou")
(params "season=été")
(params "")
(params nil)
    

(defn get-confitures-res [req]
  (let [query-string (req :query-string)
        params (params query-string)]
    {:status 200
     :headers json-header
     :body (json/write-str 
              (db/confitures-with-query params))}))

; (db/confitures-with-query (params "season=été"))


;; dev - see req 
; (defn get-confitures-res [req]
;   {:status 200
;    :headers json-header
;    :body (json/write-str 
;               (dissoc req :async-channel))})


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
(-main)


