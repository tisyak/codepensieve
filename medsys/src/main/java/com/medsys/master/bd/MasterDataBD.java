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
   
	public Response add(MasterData masterData);
	
    public MasterData get(Class<? extends MasterData> subClass,Integer id);
 
    public Response update(Class<? extends MasterData>  subClass,MasterData masterData);
 
    public Response delete(Class<? extends MasterData>  subClass,Integer id);
 
 
    public List<MasterData> getAll(String modelName);
  
    public MasterData getbyCode(Class<? extends MasterData>  subClass,String code);
	
	
}
