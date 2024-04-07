package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
        try {
            if(vehicle.getConstructeur().isEmpty()){
                throw new ServiceException("Pas de constructeur");
            }
            if (vehicle.getModele().isEmpty()) {
                throw new ServiceException("Le véhicule doit avoir un modèle.");
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

	public Vehicle findById(int id) throws ServiceException {
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
            throw new ServiceException(e.getMessage());
        }
    }
    public int count() throws ServiceException{
        try{
            return(vehicleDao.count());
        }
        catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
	
}
