
(ns FeedTutorial.core
  ( :use net.cgrand.moustache
         net.cgrand.enlive-html
         ring.util.response
         [ring.adapter.jetty :only [run-jetty]]))



(def bookmarks [ {:title "Something happend!" :url "www.someurl.com"}
                  {:title "More news." :url "www.newssite.com"}
                  {:title "Very cool stuff" :url "www.verycoolsite.com"}])


(deftemplate index "FeedTutorial/index.html"
  [items]
  [:div#bookmarks] (content "I changed the html")
  [:table#items :tr.bookmark] (clone-for [item items]
                                [:td.title] (content (:title item))
                                [:td.url] (content (:url item))))



(def my-app-handler
     (app
      [""] (-> (index bookmarks) response constantly)
      ["hello"] (-> "My own page!" response constantly)))




(run-jetty #'my-app-handler {:port 8080
                                  :join? false})

