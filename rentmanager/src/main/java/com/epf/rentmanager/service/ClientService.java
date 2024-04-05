package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ClientService {

	private ClientDao clientDao;
@Autowired
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}


	public int count() throws ServiceException{
		try{
			return(clientDao.count());
		}
		catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long create(Client client) throws ServiceException {
        try {
			if (client.getNom().isEmpty()||client.getPrenom().isEmpty()){
				throw new ServiceException("Client sans nom ou prénom");
			}
			client.setNom(client.getNom().toUpperCase());
            clientDao.create(client);
			return client.getId();
        } catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long delete(Client client) throws ServiceException{
        try {
            clientDao.delete(client);
			return client.getId();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

	}

	public Client findById(long id) throws ServiceException {
        try {
            return (clientDao.findById(id));
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

	public List<Client> findAll() throws ServiceException {
        try {
            return (clientDao.findAll());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
	
}
