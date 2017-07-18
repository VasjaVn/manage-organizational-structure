package com.eliftech.dto.response;

public class AddCompanyResponse extends CompanyResponse {
    private String childId;

    public String getChildId() {
        return childId;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }
}