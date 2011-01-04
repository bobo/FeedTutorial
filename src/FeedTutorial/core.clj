(ns FeedTutorial.core
  ( :use net.cgrand.moustache
         net.cgrand.enlive-html
         ring.util.response
         ring.middleware.params
         [ring.adapter.jetty :only [run-jetty]]))



(def bookmarks (agent [{:title "Something happend!" :url "www.someurl.com"}
                       {:title "More news." :url "www.newssite.com"}
                       {:title "Very cool stuff" :url "www.verycoolsite.com"}]))




(deftemplate index "FeedTutorial/index.html"
  [items]
  [:div#bookmarks :*] (content "My own bookmarkingpage!")
  [:table#items :tr.bookmark] (clone-for [item items]
                                [:td.title] (content (:title item))
                                [:td.url] (content (:url item))))

(defn add-bookmark [{params :params}]
  (send bookmarks conj {:title (params "title") :url (params "url")})
  (response (str params)))



(def my-app-handler
     (app
            wrap-params
      ["addBookmark"] add-bookmark
      [""] (-> (index @bookmarks) response constantly)
      ["hello"] (-> "My own page!" response constantly)))

(run-jetty #'my-app-handler {:port 8080
                                  :join? false})

