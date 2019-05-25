package com.example.deltasp.databasetesting;

public class contacts {

    private String title,description,img;

    public contacts(String t,String d,String i)
    {
        this.setTitle(t);
        this.setImg(i);
        this.setDescription(d);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
