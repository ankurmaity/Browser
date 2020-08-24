package com.example.browser.models;

import java.io.Serializable;

/**
 * @author ankur
 */

public class DDModel implements Serializable {

    private String title;
    private String imageUrl;
    private Object data;

    public DDModel() {
    }

    public DDModel(String title, String imageUrl, Object data) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}