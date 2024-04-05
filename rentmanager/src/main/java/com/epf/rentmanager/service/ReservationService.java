package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

@Autowired
    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
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

    public List<Reservation> findByIdClient(int id) throws ServiceException {
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
    public Reservation findById(int id) throws ServiceException {
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
    public int count() throws ServiceException{
        try{
            return(reservationDao.count());
        }
        catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }



}
