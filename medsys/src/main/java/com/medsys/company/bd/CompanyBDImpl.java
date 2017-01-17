package com.medsys.company.bd;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updateCompany(Company user)  {
    	logger.debug("CompanyBD: Updating company.");
        companyDAO.updateCompany(user);
    }
 
    @Override
    public void deleteCompany(UUID companyId)  {
        companyDAO.deleteCompany(companyId);
    }
 
    @Override
    public List<Company> getAllCompanys() {
        return companyDAO.getAllCompanys();
    }

	@Override
	public List<Company> searchForCompanys(Company company) {
		 return companyDAO.searchForCompanys(company);
	}

	/*@Override
	public List<Company> listCompanyswithAvailableDSCs() {
		return companyDAO.listCompanyswithAvailableDSCs();
	}
	
	@Override
	public List<Company>  monthlyCompanyListHavingInwardDSCs(){
		return companyDAO.monthlyCompanyListHavingInwardDSCs();
	}
	
	@Override
	public List<Company>  monthlyCompanyListHavingOutwardDSCs(){
		return companyDAO.monthlyCompanyListHavingOutwardDSCs();
	}
   
*/
   
}