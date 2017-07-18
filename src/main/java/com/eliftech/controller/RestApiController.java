package com.eliftech.controller;

import com.eliftech.common.GeneratorUUID;
import com.eliftech.common.ResponseStatus;
import com.eliftech.dto.jstree.JsTreeDataResponse;
import com.eliftech.dto.jstree.JsTreeDataItem;
import com.eliftech.dto.request.AddCompanyRequest;
import com.eliftech.dto.request.EditCompanyRequest;
import com.eliftech.dto.request.RemoveCompanyRequest;
import com.eliftech.dto.response.AddCompanyResponse;
import com.eliftech.dto.response.EditCompanyResponse;
import com.eliftech.dto.response.RemoveCompanyResponse;
import com.eliftech.entity.Company;
import com.eliftech.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Long ESTIMATED_EARNINGS_ZERO = Long.valueOf(0);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/add_company",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public AddCompanyResponse addCompany(@RequestBody AddCompanyRequest request) {
        AddCompanyResponse response = new AddCompanyResponse();
        try {
            String id = GeneratorUUID.generate();

            Company company = new Company();
            company.setId(id);
            company.setParentId( request.getParentId() );
            company.setName( request.getName() );
            company.setEarnings( request.getEarnings() );
            company.setEstimatedEarnings( ESTIMATED_EARNINGS_ZERO );

            companyService.add(company);

            response.setChildId(id);
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setStatus( ResponseStatus.FAILURE );
        }

        return response;
    }

    @RequestMapping(value = "remove_company",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public RemoveCompanyResponse removeCompany(@RequestBody RemoveCompanyRequest request) {
        RemoveCompanyResponse response = new RemoveCompanyResponse();

        try {
            companyService.removeById( request.getId() );
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
        }

        return response;
    }

    @RequestMapping(value = "edit_company",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public EditCompanyResponse editCompany(@RequestBody EditCompanyRequest request) {
        EditCompanyResponse response = new EditCompanyResponse();

        try {
            Long deltaEarnings = ESTIMATED_EARNINGS_ZERO;
            String id = request.getId();
            Company company = companyService.getById(id);
            Boolean isEarningsChanged = request.isEarningsChanged();

            if ( request.isNameChanged() ) {
                company.setName( request.getName() );
            }

            if ( isEarningsChanged ) {

                Long newEarnings = request.getEarnings();
                Long oldEarnings = company.getEarnings();
                deltaEarnings = newEarnings - oldEarnings;

                Long estimatedEarnings = company.getEstimatedEarnings();
                Long newEstimatedEarnings = estimatedEarnings + deltaEarnings;

                company.setEarnings( newEarnings );
                if ( !ESTIMATED_EARNINGS_ZERO.equals(estimatedEarnings) ) {
                    company.setEstimatedEarnings( newEstimatedEarnings );
                }
            }

            companyService.update(company, isEarningsChanged, deltaEarnings);

            response.setStatus( ResponseStatus.SUCCESS );
        } catch (Exception e) {
            response.setStatus( ResponseStatus.FAILURE );
        }

        return response;
    }

    @RequestMapping(value = "/jstree_data",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public JsTreeDataResponse jsTreeData() {
        JsTreeDataResponse response = new JsTreeDataResponse();

        try {
            List<Company> companies = companyService.getAll();

            List<JsTreeDataItem> data = new ArrayList<>();
            for ( Company company : companies ) {
                Long earnings = company.getEarnings();

                Long estimatedEarnings = company.getEstimatedEarnings();

                JsTreeDataItem dataItem = new JsTreeDataItem();
                dataItem.setId( company.getId() );
                dataItem.setParent( company.getParentId() );
                String text = company.getName() +
                              " | " +
                              earnings.toString() +
                              (estimatedEarnings.equals(ESTIMATED_EARNINGS_ZERO) ? "" : " | " + estimatedEarnings.toString() );
                dataItem.setText( text );

                data.add( dataItem );
            }

            response.setData( data );
            response.setStatus( ResponseStatus.SUCCESS );

        } catch (Exception e) {
            response.setStatus( ResponseStatus.FAILURE );
        }

        return response;
    }
}