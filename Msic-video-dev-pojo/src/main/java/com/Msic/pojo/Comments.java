package com.Msic.pojo;

import javax.persistence.*;

public class Comments {
    @Id
    private String id;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}