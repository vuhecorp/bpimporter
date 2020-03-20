package com.hersa.bp.importer.bom.bpreading;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hecorp.api.dao.AbstractBaseManager;
import com.hecorp.api.dao.ApplicationException;
import com.hersa.bp.importer.dao.bpreading.BpReadingCreateException;
import com.hersa.bp.importer.dao.bpreading.BpReadingDTO;

public class BloodPressureManager extends AbstractBaseManager {

	public void create(BloodPressure bp, Connection connection) throws ApplicationException{
		try {
			this.getBloodPressureDAO(connection).createBpReading(bp.getDto());
		} catch (BpReadingCreateException e) {
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public List<BloodPressure> retrieveAllBloodPressure(){
		return convert(this.getBloodPressureDAO().listAllBpReading());
	}
	
	private List<BloodPressure> convert(BpReadingDTO[] dtos){
		List<BloodPressure> list = new ArrayList<BloodPressure>();
		
		if (dtos == null) {
			return list;
		}
		
		for (BpReadingDTO dto : dtos) {
			list.add(new BloodPressure(dto));
		}
		
		return list;
	}
}
