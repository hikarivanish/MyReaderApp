package me.s4h.myreaderapp.entity;


import java.util.Date;

public class RssItem {

    Long id;


//    RssChannel channel;

    String author;

    String description;

    String content;

    String link;

    Date publishedDate;

    String title;

    Date updatedDate;

    String uri;


    public RssItem() {
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", publishedDate=" + publishedDate +
                ", title='" + title + '\'' +
                ", updatedDate=" + updatedDate +
                ", uri='" + uri + '\'' +
                '}';
    }

/*
    public RssChannel getChannel() {
        return channel;
    }

    public void setChannel(RssChannel channel) {
        this.channel = channel;
    }
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
