package com.samuraireader.katana.models;

/**
 * This class is designed to store the basic structure of an articles
 * that later will be used by the adapter to insert it in a list, depending
 * on the section selected
 */
public class ArticlesEntry {
    String link, title, description;

    public ArticlesEntry(){
        // This is done for avoiding errors in the empty declaration
    }

    public ArticlesEntry(String link, String title, String description){
        this.link = link;
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
