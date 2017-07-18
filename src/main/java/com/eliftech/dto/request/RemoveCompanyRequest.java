package com.eliftech.dto.request;

import java.io.Serializable;

public class RemoveCompanyRequest implements Serializable {
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}