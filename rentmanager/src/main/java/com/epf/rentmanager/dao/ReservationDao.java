package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS nb_reservation FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)){
			System.out.println(reservation);
			preparedStatement.setInt(1, reservation.getClient_id());
			preparedStatement.setInt(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.execute();
			try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
				if (resultSet.next()){
					return resultSet.getInt(1);
				}
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


	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			preparedStatement.setLong(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, clientId, vehicle_id, debut, fin));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return reservations;
	}
	public Reservation findResaById(int resaId) throws DaoException {
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY)) {
			preparedStatement.setLong(1, resaId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int client_id = resultSet.getInt("client_id");
				int vehicle_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut") != null ? resultSet.getDate("debut").toLocalDate() : null;
				LocalDate fin = resultSet.getDate("fin") != null ? resultSet.getDate("fin").toLocalDate() : null;
				return new Reservation(resaId, client_id, vehicle_id, debut, fin);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
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
				int clientId=resultSet.getInt("client_id");
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
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement= connexion.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();

		){
			while (resultSet.next()){
				System.out.println("1");
				int id=resultSet.getInt("id");
				System.out.println("2");
				int clientId=resultSet.getInt("client_id");
				System.out.println("3");
				int vehicleId=resultSet.getInt("vehicle_id");
				System.out.println("4");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				System.out.println("5");
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				System.out.println("6");
				ListResa.add(new Reservation(id, clientId, vehicleId, debut, fin));
				System.out.println("7");
			}

		} catch (SQLException e){
			throw new DaoException(e.getMessage(),e.getCause());
		}

		return ListResa;

	}
	public int count() throws DaoException{
		try(Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement= connexion.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet resultSet= preparedStatement.executeQuery();){
			if (resultSet.next()) {
				return resultSet.getInt("nb_reservation");
			}else{
				return (0);
			}
		}
		catch (SQLException e){
			throw new DaoException(e);
		}

	}
}
