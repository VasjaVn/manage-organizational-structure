package com.eliftech.dto.response;

import java.io.Serializable;

public class CompanyResponse implements Serializable {
    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
