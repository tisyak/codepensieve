package com.medsys.company.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.company.model.Company;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Company getCompany(UUID companyId) throws UsernameNotFoundException {
		logger.debug("CompanyDAOImpl.getCompany() - [" + companyId + "]");
		Query query = getCurrentSession().createQuery(
				"from Company where company_id = '"+companyId.toString() + "'");
		//query.setParameter("companyId", companyId.toString());

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No user found.");
			throw new UsernameNotFoundException("Company [" + companyId
					+ "] not found");
		} else {
			
			logger.debug("Company List Size: " + query.list().size());
			List<Company> list = (List<Company>) query.list();
			Company userObject = (Company) list.get(0);

			return userObject;
		}
	}

	@Override
	public void deleteCompany(UUID companyId) throws UsernameNotFoundException {
		Company company = getCompany(companyId);
		if (company != null) {
			getCurrentSession().delete(company);
		} else {
			throw new UsernameNotFoundException("Company Username : [" + companyId
					+ "] not found");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Company> getAllCompanys() {
		return getCurrentSession().createQuery("from Company order by name asc").list();
	}

	@Override
	public void updateCompany(Company company) throws UsernameNotFoundException {
		Company companyToUpdate = getCompany(company.getCompanyId());
		companyToUpdate.setEmail(company.getEmail());
		companyToUpdate.setMobileNo(company.getMobileNo());
		companyToUpdate.setName(company.getName());
		companyToUpdate.setAddress(company.getAddress());
		companyToUpdate.setCity(company.getCity());
	
		companyToUpdate.setPincode(company.getPincode());
		getCurrentSession().update(companyToUpdate);
	}

	
	
	@Override
	public List<Company> searchForCompanys(Company company) {
		logger.debug("CompanyDAOImpl.searchForCompanys() - [" + company.toString() + "]");
		Query query = getCurrentSession().createQuery(
				"from Company where lower(name) like :name OR mobileNo like :mobileNo order by name asc");

		if(company.getName()!=null){
			query.setString("name", "%"+company.getName().toLowerCase()+"%");
		}else{
			query.setString("name", company.getName());
		}
	
		if(company.getMobileNo()!=null){
			query.setString("mobileNo", "%"+company.getMobileNo().toLowerCase()+"%");
		}else{
			query.setString("mobileNo", company.getMobileNo());
		}
		
		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No companys found matching current search criteria.");
			return null;
		} else {
			
			logger.debug("Search Company List Size: " + query.list().size());
			List<Company> list = (List<Company>) query.list();
			return list;
		}
	}
	

	/*@Override
	public List<Company> listCompanyswithAvailableDSCs() {
		logger.debug("CompanyDAOImpl.listCompanyswithAvailableDSCs()");
		Query query = getCurrentSession().createQuery(
				"select cdi.companyInfo from CompanyDSCInfo cdi where cdi.dscAvailable='true' order by cdi.companyInfo.companyName asc ");

		logger.debug(query.toString());
		if (query.list().size() == 0) {
			logger.debug("No companys found matching current search criteria.");
			return null;
		} else {
			
			logger.debug("Search Company List Size: " + query.list().size());
			@SuppressWarnings("unchecked")
			List<Company> list = (List<Company>) query.list();
			return list;
		}
	}
	
	@Override
	public List<Company> monthlyCompanyListHavingInwardDSCs() {
		logger.debug("CompanyDAOImpl.monthlyInwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Company> companys =  getCurrentSession().createQuery("select companyDSCInfo.companyInfo from DscTransferInfo dti "
				+ " where dti.companyDSCInfo.dscAvailable=true"
				+ " and to_char(dti.transferDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by companyDSCInfo.companyInfo.companyName asc ")
				.list();
		logger.debug("Result: " + companys);
		return companys;
	}

	
	@Override
	public List<Company> monthlyCompanyListHavingOutwardDSCs() {
		logger.debug("CompanyDAOImpl.monthlyOutwardDSCs()");
		@SuppressWarnings("unchecked")
		List<Company> companys =   getCurrentSession().createQuery("select companyDSCInfo.companyInfo from DscTransferInfo dti "
				+ " where dti.companyDSCInfo.dscAvailable=false"
				+ " and to_char(dti.dscReturnDate,'YYYY/MM') = '"+ 
				Calendar.getInstance().get(Calendar.YEAR) +"/"+ (Calendar.getInstance().get(Calendar.MONTH)+1) +"'  order by companyDSCInfo.companyInfo.companyName asc ")
				.list();
		logger.debug("Result: " + companys);
		return companys;
	}
*/
}