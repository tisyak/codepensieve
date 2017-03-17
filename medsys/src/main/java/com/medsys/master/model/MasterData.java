package com.medsys.master.model;

import java.sql.Timestamp;

public abstract class MasterData {

	public abstract Integer getUniqueId();

	public abstract String getKeyColumnName();

	public abstract String getUpdateBy();

	public abstract void setUpdateBy(String updateBy);

	public abstract Timestamp getUpdateTimestamp();

	public abstract void setUpdateTimestamp(Timestamp updateTimestamp);

}
