package com.medsys.company.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medsys.company.model.Company;
 
public interface CompanyDAO {
 
    public Company getCompany(UUID companyId) throws UsernameNotFoundException;
 
    public void updateCompany(Company company) throws UsernameNotFoundException;
 
    public void deleteCompany(UUID companyId) throws UsernameNotFoundException;
 
    public List<Company> getAllCompanys();

	public List<Company> searchForCompanys(Company company);
	
	
	
	
}