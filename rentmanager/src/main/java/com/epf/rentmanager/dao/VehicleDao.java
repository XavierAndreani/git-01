package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setInt(2, vehicle.getNb_places());
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

	public long delete(Vehicle vehicle) throws DaoException {
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement=connexion.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getId());
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

	public Vehicle findById(long id) throws DaoException {
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_VEHICLE_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();
			preparedStatement.setInt(1, (int) id);
			String constructeur=resultSet.getString("constructeur");
			String modele=resultSet.getString("modele");
			int nb_place=resultSet.getInt("nb_place");
			connexion.close();
			resultSet.close();
			return new Vehicle((int) id, constructeur, modele, nb_place);

		} catch (SQLException e){
			throw new DaoException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> ListVehicle = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_VEHICLES_QUERY);

			ResultSet resultSet= preparedStatement.executeQuery();
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String constructeur=resultSet.getString("constructeur");
				String modele=resultSet.getString("modele");
				int nb_place=resultSet.getInt("nb_place");
				connexion.close();
				ListVehicle.add(new Vehicle(id, constructeur, modele, nb_place));

			}

		} catch (SQLException e){
			throw new DaoException(e);
		}

		return ListVehicle;
		
	}
	

}
