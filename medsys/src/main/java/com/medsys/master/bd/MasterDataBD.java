/*
 * 
 */
package com.medsys.master.bd;

import java.util.List;

import com.medsys.master.model.MasterData;

import com.medsys.common.model.Response;


/**
 * The Interface MasterConfigBD.
 */
public interface MasterDataBD {
    
	/**
	 * Adds the config para.
	 *
	 * @param configPara the config para
	 */
	public Response add(MasterData masterData);
	 
    /**
     * Gets the config para.
     *
     * @param paraname the paraname
     * @return the config para
     */
    public MasterData get(Class subClass,String id);
 
    /**
     * Update config para.
     *
     * @param configPara the config para
     */
    public Response update(Class subClass,MasterData masterData);
 
    /**
     * Delete config para.
     *
     * @param paraname the paraname
     */
    public Response delete(Class subClass,String id);
 
    /**
     * Gets the config paras.
     *
     * @return the config paras
     */
    public List<MasterData> getAll(String modelName);
	
	
}
