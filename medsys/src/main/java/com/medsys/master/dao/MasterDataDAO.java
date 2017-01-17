package com.medsys.master.dao;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medsys.master.model.MasterData;

public interface MasterDataDAO {
	
	public void add(MasterData masterData) throws DuplicateKeyException;
	 
    public MasterData get(Class subClass,String id) throws EmptyResultDataAccessException;
 
    public void update(Class subClass,MasterData masterData) throws EmptyResultDataAccessException;
 
    public void delete(Class subClass,String id) throws EmptyResultDataAccessException;
 
    public List<MasterData> getAll(String modelName);
    
}
