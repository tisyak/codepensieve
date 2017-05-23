package com.medsys.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medsys.adminuser.model.Roles;
import com.medsys.common.model.Response;
import com.medsys.company.bd.CompanyBD;
import com.medsys.company.model.Company;
import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
import com.medsys.ui.util.jqgrid.JqgridResponse;

@Controller
@Secured(Roles.MASTER_ADMIN)
public class CompanyController {

	static Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyBD companyBD;
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.LIST_ALL_COMPANIES, produces = "application/json")
	public @ResponseBody JqgridResponse<?> records(@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "companyId", required = false) Integer companyId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord) {

		// Pageable pageRequest = new PageRequest(page-1, rows);
		logger.debug("list all comapnies / search in system.");
		if (search == true) {
			// TODO: enable search??
			// return getFilteredRecords(filters, pageRequest);

		}

		List<Company> companies = companyBD.getAllCompanies();

		logger.debug("Companies in inventory: " + companies);

			JqgridResponse<Company> response = new JqgridResponse<Company>();
			response.setRows(companies);

			response.setRecords(Integer.valueOf(companies.size()).toString());
			// Single page to display all products part of the set chosen for
			// inventory.
			response.setTotal(Integer.valueOf(1).toString());
			response.setPage(Integer.valueOf(1).toString());

			logger.debug("Companies already exist in inventory. Loading response from Inventory Company List: " + response);

			return response;
		

	}

	@RequestMapping(value = UIActions.GET_COMPANY, produces = "application/json")
	public @ResponseBody Company get(@RequestBody Company company) {
		logger.debug("Getting the product in inventory: " + company);

		return companyBD.getCompany(company.getCompanyId());
	}
	
	@RequestMapping(value = UIActions.FORWARD_SLASH + UIActions.MANAGE_COMPANIES, method = RequestMethod.GET)
	public String loadManageCompanyPage(Model model) {
		logger.info("IN: Company/manage-GET: ");
		
		return MedsysUITiles.MANAGE_COMPANY.getTile();
	}

	@RequestMapping(value = UIActions.EDIT_COMPANY, produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Response update(@RequestParam(value = "id", required = false) String companyId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "pincode", required = false) String pincode,
			@RequestParam(value = "shopEstLcNo", required = false) String shopEstLcNo,
			@RequestParam(value = "vatTinNo", required = false) String vatTinNo,
			@RequestParam(value = "cstNo", required = false) String cstNo,
			@RequestParam(value = "fda20bLcNo", required = false) String fda20bLcNo,
			@RequestParam(value = "fda21bLcNo", required = false) String fda21bLcNo,
			HttpServletResponse httpServletResponse) {

		
			Company toBeUpdatedCompany = new Company();
			toBeUpdatedCompany.setCompanyId(UUID.fromString(companyId));
			toBeUpdatedCompany.setName(name);
			toBeUpdatedCompany.setEmail(email);
			toBeUpdatedCompany.setMobileNo(mobileNo);
			toBeUpdatedCompany.setAddress(address);
			toBeUpdatedCompany.setCity(city);
			toBeUpdatedCompany.setPincode(pincode);
			toBeUpdatedCompany.setShopEstLcNo(shopEstLcNo);
			toBeUpdatedCompany.setVatTinNo(vatTinNo);
			toBeUpdatedCompany.setCstNo(cstNo);
			toBeUpdatedCompany.setFda20bLcNo(fda20bLcNo);
			toBeUpdatedCompany.setFda21bLcNo(fda21bLcNo);
			logger.debug("Updating company information: " + toBeUpdatedCompany);
			//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			//TODO: UpdateBY and UpdateTimestamp
			//toBeUpdatedCompany.setUpdateBy(auth.getName());
			//toBeUpdatedCompany.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
			Response response = companyBD.updateCompany(toBeUpdatedCompany);
			if(!response.isStatus()){
				httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			return response;
	
	}

	public static List<com.medsys.company.model.Company> map(Page<Company> pageOfCompanies) {
		List<Company> companies = new ArrayList<Company>();
		for (Company company : pageOfCompanies) {
			companies.add(company);
		}
		return companies;
	}
}