package com.medsys.company.bd;

import java.util.List;
import java.util.UUID;

import com.medsys.company.model.Company;

public interface CompanyBD {


	public Company getCompany(UUID companyId);

	public void updateCompany(Company client);

	public void deleteCompany(UUID companyId);

	public List<Company> getAllCompanys();
	
	public List<Company> searchForCompanys(Company client);

}