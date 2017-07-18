package com.eliftech.dto.request;

import java.io.Serializable;

public class EditCompanyRequest implements Serializable {
    private String id;
    private String name;
    private Long earnings;
    private Boolean isNameChanged;
    private Boolean isEarningsChanged;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public Boolean isNameChanged() {
        return isNameChanged;
    }
    public void setNameChanged(Boolean nameChanged) {
        isNameChanged = nameChanged;
    }

    public Boolean isEarningsChanged() {
        return isEarningsChanged;
    }
    public void setEarningsChanged(Boolean earningsChanged) {
        isEarningsChanged = earningsChanged;
    }
}