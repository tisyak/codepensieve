package com.medsys.master.dao;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.medsys.master.model.MasterData;

public interface MasterDataDAO {

	public void add(MasterData masterData) throws DuplicateKeyException;

	public MasterData get(Class<? extends MasterData> subClass, Integer id) throws EmptyResultDataAccessException;

	public void update(Class<? extends MasterData> subClass, MasterData masterData) throws EmptyResultDataAccessException;

	public void delete(Class<? extends MasterData> subClass, Integer id) throws EmptyResultDataAccessException;

	public MasterData getbyCode(Class<? extends MasterData> subClass, String code);

	public List<MasterData> getAll(Class<? extends MasterData> subClass);

}
