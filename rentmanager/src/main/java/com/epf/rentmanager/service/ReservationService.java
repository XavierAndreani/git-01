package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            reservationDao.create(reservation);
            return reservation.getId();
        } catch (DaoException e) {
            throw new RuntimeException(e);}
    }

    public long delete(Reservation reservation) throws ServiceException{
        try {
            reservationDao.delete(reservation);
            return  reservation.getId();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reservation> findByIdClient(long id) throws ServiceException {
        try {
            return(reservationDao.findResaByClientId(id));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Reservation> findByIdVehicle(long id) throws ServiceException {
        try {
            return(reservationDao.findResaByVehicleId(id));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
    public Reservation findById(long id) throws ServiceException {
        try {
            return(reservationDao.findResaById(id));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Reservation> findAll() throws ServiceException {
        try {
            return(reservationDao.findAll());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }



}
