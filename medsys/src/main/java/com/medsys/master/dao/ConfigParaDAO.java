package com.medsys.master.dao;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medsys.master.model.ConfigPara;

public interface ConfigParaDAO {
	
	public void addConfigPara(ConfigPara configPara) throws DuplicateKeyException;
	 
    public ConfigPara getConfigPara(String paraname) throws EmptyResultDataAccessException;
 
    public void updateConfigPara(ConfigPara configPara) throws EmptyResultDataAccessException;
 
    public void deleteConfigPara(String paraname) throws EmptyResultDataAccessException;
 
    public List<ConfigPara> getConfigParas();
    
}
