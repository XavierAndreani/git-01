package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
        try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
            Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY, statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			preparedStatement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()){
				connexion.close();
				return resultSet.getInt(1);
			}
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return 0;
	}
	
	public long delete(Client client) throws DaoException {
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement=connexion.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId());
			preparedStatement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()){
				return resultSet.getInt(1);
			}
			connexion.close();
			resultSet.close();

		} catch (SQLException e){
			throw new DaoException(e);
		}
		return 0;
	}

	public Client findById(long id) throws DaoException {
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_CLIENT_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();
			preparedStatement.setInt(1, (int) id);
			String nom=resultSet.getString("nom");
			String prenom=resultSet.getString("prenom");
			String email=resultSet.getString("email");
			LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
			connexion.close();
			resultSet.close();
			return new Client((int) id, nom, prenom, email, naissance);
			
		} catch (SQLException e){
			throw new DaoException(e);
		}
    }

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> ListClient = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_CLIENTS_QUERY);

			ResultSet resultSet= preparedStatement.executeQuery();
			while (resultSet.next()){
				int id =resultSet.getInt("id");
				String nom=resultSet.getString("nom");
				String prenom=resultSet.getString("prenom");
				String email=resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				connexion.close();
				ListClient.add(new Client(id, nom, prenom, email, naissance));

			}

		} catch (SQLException e){
			throw new DaoException(e);
		}

		return ListClient;
	}

}
