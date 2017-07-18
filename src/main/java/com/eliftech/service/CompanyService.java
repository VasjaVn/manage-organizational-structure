package com.eliftech.service;

import com.eliftech.entity.Company;

import java.util.List;

public interface CompanyService {
    Company add(Company company);
    Company update(Company company, boolean isUpdateEstimatedEarnings, Long deltaEarnings);
    Company getById(String id);
    List<Company> getAll();
    void removeById(String id);
}
