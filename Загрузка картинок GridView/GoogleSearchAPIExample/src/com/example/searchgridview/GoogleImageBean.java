package com.example.searchgridview;

/**
 * Created by Андрей on 12.11.13.
 */
public class GoogleImageBean
{
    String thumbUrl;
    String title;
    String url;

    public void setUrl(String url) {this.url = url; }

    public String getUrl() {return url;}

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public void setThumbUrl(String url)
    {
        this.thumbUrl = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}