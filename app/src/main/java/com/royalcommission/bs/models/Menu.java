package com.royalcommission.bs.models;

/**
 * Created by Prashant on 10/17/2018.
 */
public class Menu {

    private int imageId;
    private String name;

    public Menu(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
