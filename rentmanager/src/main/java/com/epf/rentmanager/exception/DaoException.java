package com.epf.rentmanager.exception;

public class DaoException extends Exception{
    public DaoException(Exception e) {
        super();
    }
    public DaoException(String e,Throwable f){super();}
}
