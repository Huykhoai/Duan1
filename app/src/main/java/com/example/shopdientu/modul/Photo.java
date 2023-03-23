package com.example.shopdientu.modul;

import java.io.Serializable;

public class Photo implements Serializable {
    public int resourceId;

    public Photo(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
