package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS nb_vehicles FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 Statement statement=connexion.createStatement();
			 PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY, statement.RETURN_GENERATED_KEYS);){

			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()){
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
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

	public Vehicle findById(int id) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_places = resultSet.getInt("nb_places");
				return new Vehicle((int) id, constructeur, modele, nb_places);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> ListVehicle = new ArrayList<>();
		try (
				Connection connexion = ConnectionManager.getConnection();
				PreparedStatement preparedStatement= connexion.prepareStatement(FIND_VEHICLES_QUERY);
				ResultSet resultSet= preparedStatement.executeQuery();
        ) {
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String constructeur=resultSet.getString("constructeur");
				String modele=resultSet.getString("modele");
				int nb_place=resultSet.getInt("nb_places");
				ListVehicle.add(new Vehicle(id, constructeur, modele, nb_place));
			}


		} catch (SQLException e){
			throw new DaoException(e);


		}

		return ListVehicle;
		
	}
	public int count() throws DaoException{
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement= connexion.prepareStatement(COUNT_VEHICLES_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();){
			if (resultSet.next()) {
				return resultSet.getInt("nb_vehicles");
			}else{
				return (0);
			}
		}
		catch (SQLException e){
			throw new DaoException(e);
		}

	}
	

}
