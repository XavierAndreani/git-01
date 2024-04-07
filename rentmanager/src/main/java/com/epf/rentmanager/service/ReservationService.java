package com.epf.rentmanager.service;

import java.time.LocalDate;
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
            LocalDate debut = reservation.getDebut();
            LocalDate fin = reservation.getFin();
            if (debut.plusDays(7).isBefore(fin)) {
                throw new ServiceException("Une reservation ne peut pas durer plus d'une semaine");
            }
            if (fin.isBefore(debut)) {
                throw new ServiceException("Le début ne peut pas être après la fin");
            }
            reservationDao.create(reservation);
            return reservation.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());}
    }

    public long delete(Reservation reservation) throws ServiceException{
        try {
            reservationDao.delete(reservation);
            return  reservation.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findByIdClient(int id) throws ServiceException {
        try {
            return(reservationDao.findResaByClientId(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public List<Reservation> findByIdVehicle(long id) throws ServiceException {
        try {
            return(reservationDao.findResaByVehicleId(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }
    public Reservation findById(int id) throws ServiceException {
        try {
            return(reservationDao.findResaById(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }
    public List<Reservation> findAll() throws ServiceException {
        try {
            return(reservationDao.findAll());
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
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
