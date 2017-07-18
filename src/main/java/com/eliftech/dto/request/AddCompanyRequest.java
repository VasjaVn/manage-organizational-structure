package com.eliftech.dto.request;

import java.io.Serializable;

public class AddCompanyRequest implements Serializable {
    private String parentId;
    private String name;
    private Long earnings;

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getEarnings() {
        return earnings;
    }
    public void setEarnings(Long earnings) {
        this.earnings = earnings;
    }
}