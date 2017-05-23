package com.medsys.company.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medsys.common.model.Response;
import com.medsys.company.model.Company;
 
public interface CompanyDAO {
 
    public Company getCompany(UUID companyId) throws UsernameNotFoundException;
 
    public Response updateCompany(Company company) throws UsernameNotFoundException;
 
    public List<Company> getAllCompanies();

	public List<Company> searchForCompanies(Company company);
	
	
	
	
}