package com.eliftech.service;

import com.eliftech.entity.Company;
import com.eliftech.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String MAIN_COMPANY_ID = "#";

    private static final Long ESTIMATED_EARNINGS_ZERO = Long.valueOf(0);

    @Autowired
    private CompanyRepository companyRepository;

    public Company add(Company company) {
        String parentId = company.getParentId();
        Long earnings = company.getEarnings();
        updateEstimatedEarningsOfCompany(parentId, earnings);

        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company, boolean isUpdateEstimatedEarnings, Long deltaEarnings) {
        String parentId = company.getParentId();
        if ( isUpdateEstimatedEarnings ) {
            updateEstimatedEarningsOfCompany(parentId, deltaEarnings);
        }

        return companyRepository.save(company);
    }

    public Company getById(String id) {
        return companyRepository.findOne(id);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public void removeById(String companyId) {
        Company company = getById(companyId);

        Long estimatedEarnings = company.getEstimatedEarnings();
        if ( ESTIMATED_EARNINGS_ZERO.equals(estimatedEarnings) ) {
            estimatedEarnings = company.getEarnings();
        }
        updateEstimatedEarningsOfCompany(companyId, -1 * estimatedEarnings);

        removeByIdHelper(companyId);
    }

    private void removeByIdHelper(String companyId) {
        List<Company> childCompanies = companyRepository.findByParentId(companyId);
        for ( Company childCompany : childCompanies ) {
            String childId = childCompany.getId();
            removeByIdHelper( childId );
        }
        companyRepository.delete(companyId);
    }

    private void updateEstimatedEarningsOfCompany(String companyId, Long earnings) {

        if ( MAIN_COMPANY_ID.equals( companyId ) ) {
            return;
        }

        Company company = getById(companyId);

        /* IF ADD: estimated earnings */
        Long estimatedEarnings = company.getEstimatedEarnings();
        if ( ESTIMATED_EARNINGS_ZERO.equals( estimatedEarnings ) ) {
            estimatedEarnings = company.getEarnings();
        }

        /* IF SUB: estimated earnings */
        Long newEstimatedEarnings = estimatedEarnings + earnings;
        if ( newEstimatedEarnings.equals( company.getEarnings() ) ) {
            newEstimatedEarnings = ESTIMATED_EARNINGS_ZERO;
        }

        company.setEstimatedEarnings( newEstimatedEarnings );

        updateCompany( company );

        String nextParentId = company.getParentId();
        updateEstimatedEarningsOfCompany(nextParentId, earnings);
    }

    private void updateCompany(Company company) {
        companyRepository.save(company);
    }
}