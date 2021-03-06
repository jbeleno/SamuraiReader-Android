/**
 * Title: SamuraiReader Android
 * Version: 1.0
 * Author: Juan Sebastián Beleño Díaz
 * Email: jsbeleno@gmail.com
 * Date: 27/02/2016
 *
 * This class is designed to store the basic structure of an articles
 * that later will be used by the adapter to insert it in a list, depending
 * on the section selected
 */
package com.samuraireader.katana.models;

public class ArticlesEntry {
    String link, title, description, journal, tag;

    public ArticlesEntry(){
        // This is done for avoiding errors in the empty declaration
    }

    public ArticlesEntry(String link, String title, String description, String journal, String tag){
        this.link = link;
        this.title = title;
        this.description = description;
        this.journal = journal;
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getJournal() {
        return journal;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
