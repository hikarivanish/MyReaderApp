package me.s4h.myreaderapp.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RssChannel {

    Long id;

    Set<RssItem> items = new HashSet<RssItem>();

    String url; //url to feed xml


//    @Column(nullable = false, unique = true)
    String link; 

    String uri;
    String title;
    String author;
//    String imgLink;
    String description;

    Date publishedDate;


    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    Date lastUpdate;

    public void addRssItem(RssItem item){
        this.items.add(item);
    }

    public RssChannel() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<RssItem> getItems() {
        return items;
    }

    public void setItems(Set<RssItem> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    public String getImgLink() {
//        return imgLink;
//    }
//
//    public void setImgLink(String imgLink) {
//        this.imgLink = imgLink;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
