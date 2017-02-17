/*
 * 
 */
package com.medsys.master.bd;

import java.util.List;

import com.medsys.common.model.Response;
import com.medsys.master.model.ConfigPara;


/**
 * The Interface MasterConfigBD.
 */
public interface ConfigParaBD {
    
	/**
	 * Adds the config para.
	 *
	 * @param configPara the config para
	 */
	public Response addConfigPara(ConfigPara configPara);
	 
    /**
     * Gets the config para.
     *
     * @param paraname the paraname
     * @return the config para
     */
    public ConfigPara getConfigPara(String paraname);
 
    /**
     * Update config para.
     *
     * @param configPara the config para
     */
    public Response updateConfigPara(ConfigPara configPara);
 
    /**
     * Delete config para.
     *
     * @param paraname the paraname
     */
    public Response deleteConfigPara(String paraname);
 
    /**
     * Gets the config paras.
     *
     * @return the config paras
     */
    public List<ConfigPara> getConfigParas();
	
	
}
