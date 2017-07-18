package com.eliftech.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_company")
public class Company implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "earnings")
    private Long earnings;

    @Column(name = "estimated_earnings")
    private Long estimatedEarnings;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

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

    public Long getEstimatedEarnings() {
        return estimatedEarnings;
    }
    public void setEstimatedEarnings(Long estimatedEarnings) {
        this.estimatedEarnings = estimatedEarnings;
    }
}