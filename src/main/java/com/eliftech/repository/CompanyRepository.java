package com.eliftech.repository;

import com.eliftech.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String> {
    List<Company> findByParentId(String parentId);
}
