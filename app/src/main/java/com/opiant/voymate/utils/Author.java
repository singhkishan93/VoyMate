package com.opiant.voymate.utils;

/**
 * Created by user on 18-01-2016.
 */
public class Author {

    String FullName;
    String Thumbnailurl;
    long AuthorType_ID;

//Setters
    public void setAuthorType_ID(long AuthorType_ID) {

        this.AuthorType_ID = AuthorType_ID;
    }


    public void setFullName(String fullname){

        this.FullName=fullname;
    }

    public void setThumbnailurl(String thumbnailurl){

        this.Thumbnailurl=thumbnailurl;
    }

    //Getters

    public long getId()
    {
        return this.AuthorType_ID;
    }

    public String getThumbnailurl(){

        return this.Thumbnailurl;
    }

    public String getFullName(){

        return this.FullName;
    }
}
