package com.medsys.company.bd;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.company.dao.CompanyDAO;
import com.medsys.company.model.Company;
 
@Service
@Transactional
public class CompanyBDImpl implements CompanyBD {
    static Logger logger = LoggerFactory.getLogger(CompanyBDImpl.class);
     
    @Autowired
    private CompanyDAO companyDAO;
 
    @Override
    public Company getCompany(UUID companyId)  {
        return companyDAO.getCompany(companyId);
    }
 
    @Override
    public Response updateCompany(Company company)  {
    	logger.debug("CompanyBD: Updating company.");
        return companyDAO.updateCompany(company);
    }
 
    @Override
    public List<Company> getAllCompanies() {
        return companyDAO.getAllCompanies();
    }

	@Override
	public List<Company> searchForCompanies(Company company) {
		 return companyDAO.searchForCompanies(company);
	}
   
}