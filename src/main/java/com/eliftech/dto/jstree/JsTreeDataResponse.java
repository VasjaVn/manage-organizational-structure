package com.eliftech.dto.jstree;

import java.io.Serializable;
import java.util.List;

public class JsTreeDataResponse implements Serializable {

    private String status;

    private List<JsTreeDataItem> data;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public List<JsTreeDataItem> getData() {
        return data;
    }
    public void setData(List<JsTreeDataItem> data) {
        this.data = data;
    }
}