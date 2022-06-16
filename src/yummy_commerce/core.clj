(ns yummy-commerce.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-postgresql.core :as pg]
            [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]))


; (defn open-db []
;   (pg/pool :host "dumbo.db.elephantsql.com" 
;            :user "dqgqrqev" 
;            :dbname "dqgqrqev" 
;            :password "rtDGlzTFU7lzeZHrMdQTMIZiFMjr4unD"))

; (defn single-query [query-string]
;   (let [db (open-db)
;         query-result (jdbc/query db [query-string])
;         close-db (pg/close! db)]
;     query-result))

; ;; get

; (defn get-product [id]
;   (let [query-result (single-query (str "SELECT * FROM products WHERE id =" id))
;         first-element (first query-result)]
;     first-element))


; ;; post 

; (defn insert-product [product]
;   (single-query (str "INSERT INTO products(id, name, price_230, price_340, quantity, description) 
;     VALUES(" (product :id) "," (product :name) "," (product :price_230) "," (product :price_340) "," (product :quantity) "," (product :description) ");")))


; (defn insert-product-magie []
;   (insert-product
;     {:id "'magie2'"
;      :name "''"
;      :price_230 4
;      :price_340 5
;      :quantity 0
;      :description "'super description2'"}))

; (insert-product-magie)


; (def fraise (get-product "'fraise'"))
; (def cerise (get-product "'cerise'"))
; cerise
; fraise



; (defn fps-handler [req]
;   {:status 200
;    :headers {"Content-Type" "text/html"}
;    :body "Pew pew!"})


; (defn fraise []
;   (get-product "'fraise'"))

; (defn fraise-str []
;   (json/write-str (fraise)))

; (defn fraise-handler [req]
;   {:status 200
;    :headers {"Content-Type" "text/json"}
;    :body (fraise-str)})


; (defn product [id]
;   (get-product (str "'" id "'")))
 
; (defn product-str [id]
;   (json/write-str (product id)))

; (product-str "coing-vanille")


; (defn product-not-found-str [product-id]
;   (json/write-str {:error (str "Product '" product-id "' not found in database.")}))

; (defn product-not-found-res [product-id]
;   {:status 404
;    :headers {"Content-Type" "text/json"}
;    :body (product-not-found-str product-id)})

; (defn product-found-res [product-string]
;   {:status 200
;    :headers {"Content-Type" "text/json"}
;    :body product-string})

; (defn product-res [req]
;   (let [product-id ((req :params) :product-id)
;         product-string (product-str product-id)]
;     (if (= product-string "null")
;       (product-not-found-res product-id)
;       (product-found-res product-string))))
      

; (defn mail-man []
;   "{\"Spongebob Narrator\": \"5 years later...\"}")

; (defn mail-handler [req]
;   {:status 200
;    :headers {"Content-Type" "text/json"}
;    :body (mail-man)})

; (defn general-handler [req]
;   {:status 200
;    :headers {"Content-Type" "text/html"}
;    :body "All hail General Zod!"})

; (defroutes app-routes
;   (GET "/" [] fps-handler)
;   (GET "/:product-id" [] product-res)
;   (POST "/postoffice" [] mail-handler)
;   (ANY "/anything-goes" [] general-handler)
;   (route/not-found "You Must Be New Here"))


(def html-header
  {"Content-Type" "text/html"})

(def json-header
  {"Content-Type" "text/html"})



(defn get-product [product-id]
  {:id product-id
   :name "product-name"
   :description "description"
   :price "price"})

(defn product-found-res [product]
  {:status 200
   :headers json-header
   :body (json/write-str product)})

(defn product-not-found-res [product-id]
  {:status 404
   :headers json-header
   :body (json/write-str {:msg (str "product " product-id " not found")})})
  
(defn get-product-res [req]
  (let [product-id ((req :params) :product-id)
        product (get-product product-id)]
    (if product
      (product-found-res product)
      (product-not-found-res product-id))))
      
; (if (get-product "fraise")
;   true
;   false)

(defn create-product [product-id]
  {:id product-id
   :name "product-name"
   :description "description"
   :price "price"})

(defn create-product-res [req]
  {:status 201
   :headers json-header
   :body (json/write-str (create-product "fraise"))})

(defn update-product [product-id]
  {:id product-id
   :name "product-name"
   :description "description"
   :price "price"})

(defn update-product-res [req]
  {:status 200
   :headers json-header
   :body (json/write-str (update-product "fraise"))})

(defn delete-product-res [req]
  {:status 200})

(defn get-all-products-res [req]
  req)

(defn delete-all-products-res [req]
  req)

(defn get-season-products-res [req]
  req)

(defn process-payment-res [req]
  req)


(defroutes api-routes
  (GET "/product/:product-id" [] get-product-res)
  (POST "/product/:product-id" [] create-product-res)
  (PUT "/product/:product-id" [] update-product-res)
  (DELETE "/product/:product-id" [] delete-product-res)
  (GET "/products" [] get-all-products-res)
  (DELETE "/products" [] delete-all-products-res)
  (GET "/products/:season" [] get-season-products-res)
  (POST "/payment" [] process-payment-res))
  


(defn -main
  "App entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (server/run-server api-routes {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))

;;launch server from repl
; (-main)


