package com.hecorp.api.dao;

import java.util.Date;

public interface BOM {

	public void setCreatedBy(String userName);
	public void setCreatedOn(Date date);
	public void setModifiedBy(String userName);
	public void setModifiedOn(Date date);
	
}
