package com.medsys.company.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.medsys.common.model.Response;
import com.medsys.company.model.Company;
import com.medsys.util.EpSystemError;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Company getCompany(UUID companyId) throws UsernameNotFoundException {
		logger.debug("CompanyDAOImpl.getCompany() - [" + companyId + "]");
		Query<Company> query = getCurrentSession()
				.createQuery("from Company where company_id = '" + companyId.toString() + "'", Company.class);
		// query.setParameter("companyId", companyId.toString());

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No user found.");
			throw new UsernameNotFoundException("Company [" + companyId + "] not found");
		} else {

			logger.debug("Company List Size: " + query.getResultList().size());
			List<Company> list = (List<Company>) query.getResultList();
			Company userObject = (Company) list.get(0);

			return userObject;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Company> getAllCompanies() {
		return getCurrentSession().createQuery("from Company order by name asc").getResultList();
	}

	@Override
	public Response updateCompany(Company company) throws UsernameNotFoundException {
		Company companyToUpdate = getCompany(company.getCompanyId());
		companyToUpdate.setEmail(company.getEmail());
		companyToUpdate.setMobileNo(company.getMobileNo());
		companyToUpdate.setName(company.getName());
		companyToUpdate.setAddress(company.getAddress());
		companyToUpdate.setCity(company.getCity());
		companyToUpdate.setPincode(company.getPincode());
		companyToUpdate.setShopEstLcNo(company.getShopEstLcNo());
		companyToUpdate.setVatTinNo(company.getVatTinNo());
		companyToUpdate.setCstNo(company.getCstNo());
		companyToUpdate.setFda20bLcNo(company.getFda20bLcNo());
		companyToUpdate.setFda21bLcNo(company.getFda21bLcNo());
		try {
			getCurrentSession().update(companyToUpdate);
		} catch (HibernateException he) {
			logger.debug("Unable to save company details. Exception: " + he.getMessage());
			return new Response(false, EpSystemError.DB_EXCEPTION);
		}
		logger.debug("Saved company: " + company);
		return new Response(true, null);
	}

	@Override
	public List<Company> searchForCompanies(Company company) {
		logger.debug("CompanyDAOImpl.searchForCompanies() - [" + company.toString() + "]");
		Query<Company> query = getCurrentSession()
				.createQuery("from Company where lower(name) like :name order by name asc", Company.class);

		if (company.getName() != null) {
			query.setParameter("name", "%" + company.getName().toLowerCase() + "%");
		} else {
			query.setParameter("name", company.getName());
		}

		logger.debug(query.toString());
		if (query.getResultList().size() == 0) {
			logger.debug("No companys found matching current search criteria.");
			return null;
		} else {

			logger.debug("Search Company List Size: " + query.getResultList().size());
			List<Company> list = (List<Company>) query.getResultList();
			return list;
		}
	}

	/*
	 * @Override public List<Company> monthlyCompanyListHavingInwardDSCs() {
	 * logger.debug("CompanyDAOImpl.monthlyInwardDSCs()");
	 * 
	 * @SuppressWarnings("unchecked") List<Company> companys =
	 * getCurrentSession().
	 * createQuery("select companyDSCInfo.companyInfo from DscTransferInfo dti "
	 * + " where dti.companyDSCInfo.dscAvailable=true" +
	 * " and to_char(dti.transferDate,'YYYY/MM') = '"+
	 * Calendar.getInstance().get(Calendar.YEAR) +"/"+
	 * (Calendar.getInstance().get(Calendar.MONTH)+1)
	 * +"'  order by companyDSCInfo.companyInfo.companyName asc ") .list();
	 * logger.debug("Result: " + companys); return companys; }
	 */
}