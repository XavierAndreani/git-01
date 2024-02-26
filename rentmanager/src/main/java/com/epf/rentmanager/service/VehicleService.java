package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
        try {
            if(vehicle.getConstructeur().isEmpty()){
                throw new ServiceException("Pas de constructeur");
            }
            if(vehicle.getNb_places()<=1){
                throw new ServiceException("Nombre de place inférieur ou égal à 1");
            }
            vehicleDao.create(vehicle);
			return vehicle.getId();
        } catch (DaoException e) {
            throw new RuntimeException(e);}
	}

    public long delete(Vehicle vehicle) throws ServiceException{
        try {
            vehicleDao.delete(vehicle);
            return  vehicle.getId();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

	public Vehicle findById(long id) throws ServiceException {
        try {
            return(vehicleDao.findById(id));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

	public List<Vehicle> findAll() throws ServiceException {
        try {
            return(vehicleDao.findAll());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
	
}
