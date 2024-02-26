package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epf.rentmanager.utils.IOUtils.print;

public class ClientCLI {
    private ClientService clientService;
    private VehicleService vehicleService;

    private ReservationService reservationService;
    public void creerClient() throws Exception{
    String nom = IOUtils.readString("Saisir nom :", true);
    String prenom = IOUtils.readString("Saisir prenom :", true);
    String email = IOUtils.readString("Saisir mail :", true);
    LocalDate naissance = IOUtils.readDate("Saisir date de naissance", true);


    Client client = new Client(0, nom, prenom, email, naissance);
    clientService.create(client);
    //Faire une classe / méthode ?
        //Comment récupérer l'id ?
        //Que faire du client ?
    }

    public void listClient() throws Exception{
        List<Client> ClientList = clientService.findAll();
        for (Client client : ClientList){
            print(client.toString());
        }
    }

    public void delClient() throws Exception{
        int id = IOUtils.readInt("Saisir l'id du client à supprimer");
        Client client = clientService.findById(id);
        clientService.delete(client);
    }


    public void creerVehicle() throws Exception{
        String constructeur = IOUtils.readString("Saisir constructeur :", true);
        String modele = IOUtils.readString("Saisir modele :", true);
        int nb_places = IOUtils.readInt("Saisir le nombre de places :");
        Vehicle vehicle = new Vehicle(0, constructeur,modele,nb_places);
        vehicleService.create(vehicle);
    }

    public void listVehicle() throws Exception{
        List<Vehicle> VehicleList = vehicleService.findAll();
        for(Vehicle vehicle: VehicleList){
            print(vehicle.toString());
        }
    }

    public void delVehicle() throws Exception{
        int id = IOUtils.readInt("Saisir l'id du véhicule à supprimer");
        Vehicle vehicle = vehicleService.findById(id);
        vehicleService.delete(vehicle);
    }

    public void creerResa() throws Exception{
        int id_client = IOUtils.readInt("Saisir l'id du client concerné :");
        int id_voiture = IOUtils.readInt("Saisir l'id du véhicule :");
        LocalDate debut = IOUtils.readDate("Saisir date de début de réservation", true);
        LocalDate fin = IOUtils.readDate("Saisir date de fin de réservation", true);
        Reservation reservation = new Reservation(0, id_client, id_voiture, debut, fin);
        reservationService.create(reservation);
    }

    public void ListResa() throws Exception{
        List<Reservation> ResaList = reservationService.findAll();
        for(Reservation reservation: ResaList){
            print(reservation.toString());
        }

    }

    public void delResa() throws Exception{
        int id = IOUtils.readInt("Saisir l'id de la réservation à supprimer");
        Reservation reservation = reservationService.findById(id); //CREER FINDBYID RESA
        reservationService.delete(reservation);
    }
}
