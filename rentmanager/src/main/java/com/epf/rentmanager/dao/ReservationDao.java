package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY, statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, reservation.getClient_id());
			preparedStatement.setInt(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
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
	
	public long delete(Reservation reservation) throws DaoException {
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement=connexion.createStatement();
			PreparedStatement preparedStatement=connexion.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getId());
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

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> ListResa = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();

			preparedStatement.setInt(1, (int) clientId);
			while (resultSet.next()){
			int id=resultSet.getInt("id");
			int vehicleId=resultSet.getInt("id_vehicle");
			LocalDate debut = resultSet.getDate("debut").toLocalDate();
			LocalDate fin = resultSet.getDate("fin").toLocalDate();
			connexion.close();
			resultSet.close();
			ListResa.add(new Reservation(id, (int) clientId, vehicleId, debut, fin));}

		} catch (SQLException e){
			throw new DaoException(e);
		}
		return ListResa;
	}
	public Reservation findResaById(long id) throws DaoException {
		ArrayList<Reservation> ListResa = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();

			preparedStatement.setInt(1, (int) id);
			while (resultSet.next()){
				int clientId=resultSet.getInt("id_client");
				int vehicleId=resultSet.getInt("id_vehicle");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				connexion.close();
				resultSet.close();
				return (new Reservation((int) id, clientId, vehicleId, debut, fin));}

		} catch (SQLException e){
			throw new DaoException(e);
		}
		return null;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> ListResa = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();

			preparedStatement.setInt(1, (int) vehicleId);
			while (resultSet.next()){
				int id=resultSet.getInt("id");
				int clientId=resultSet.getInt("id_client");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				connexion.close();
				resultSet.close();
				ListResa.add(new Reservation(id, clientId, (int) vehicleId, debut, fin));}

		} catch (SQLException e){
			throw new DaoException(e);
		}
		return ListResa;
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> ListResa = new ArrayList<>();
		try{
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "user", "password");
			Statement statement = connexion.createStatement();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet resultSet= preparedStatement.executeQuery();
			while (resultSet.next()){
				int id=resultSet.getInt("id");
				int clientId=resultSet.getInt("id_client");
				int vehicleId=resultSet.getInt("id_vehicle");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				connexion.close();
				ListResa.add(new Reservation(id, clientId, vehicleId, debut, fin));

			}

		} catch (SQLException e){
			throw new DaoException(e);
		}

		return ListResa;

	}
}
