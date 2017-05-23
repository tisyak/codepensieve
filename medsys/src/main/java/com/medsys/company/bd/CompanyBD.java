package com.medsys.company.bd;

import java.util.List;
import java.util.UUID;

import com.medsys.common.model.Response;
import com.medsys.company.model.Company;

public interface CompanyBD {


	public Company getCompany(UUID companyId);

	public Response updateCompany(Company company);

	public List<Company> getAllCompanies();
	
	public List<Company> searchForCompanies(Company company);

}