/*
 * 
 */
package com.medsys.master.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.master.model.MasterData;


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
    public MasterData get(Class subClass,Integer id);
 
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
    public Response delete(Class subClass,Integer id);
 
    /**
     * Gets the config paras.
     *
     * @return the config paras
     */
    public List<MasterData> getAll(String modelName);
	
	
}
