package com.epf.rentmanager.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.DeleteDbFiles;
import org.springframework.stereotype.Component;

@Component
public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "RentManagerDatabase", true);
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;

        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Client(id INT primary key auto_increment, client_id INT, nom VARCHAR(100), prenom VARCHAR(100), email VARCHAR(100), naissance DATETIME)");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Vehicle(id INT primary key auto_increment, constructeur VARCHAR(100),modele VARCHAR(100), nb_places TINYINT(255))");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Reservation(id INT primary key auto_increment, client_id INT, foreign key(client_id) REFERENCES Client(id), vehicle_id INT, foreign key(vehicle_id) REFERENCES Vehicle(id), debut DATETIME, fin DATETIME)");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
                createPreparedStatement = connection.prepareStatement(createQuery);
                createPreparedStatement.executeUpdate();
                createPreparedStatement.close();
            }

            // Remplissage de la base avec des Vehicules et des Clients
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Renault','Arkana', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Peugeot','P208', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Seat','Leon', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Nissan','CHR', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Toyota','Corolla', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Ford','Mustang', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('BMW','X5', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Audi','A4', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Mercedes','C-Class', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Volkswagen','Golf', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Hyundai','Elantra', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Kia','Sportage', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Honda','Civic', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Mazda','CX-5', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Subaru','Outback', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Lexus','RX', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Jeep','Wrangler', 4)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Land Rover','Discovery', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Tesla','Model S', 5)");
            stmt.execute("INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES('Porsche','911', 2)");

            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Dubois', 'Marie', 'marie.dubois@email.com', '1990-05-15')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Martin', 'Pierre', 'pierre.martin@email.com', '1985-08-10')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Lefebvre', 'Sophie', 'sophie.lefebvre@email.com', '1983-11-25')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Garcia', 'Antonio', 'antonio.garcia@email.com', '1987-03-18')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Martinez', 'Isabelle', 'isabelle.martinez@email.com', '1978-09-20')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Perez', 'Julien', 'julien.perez@email.com', '1980-12-30')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Sanchez', 'Caroline', 'caroline.sanchez@email.com', '1975-07-05')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Gonzalez', 'Thierry', 'thierry.gonzalez@email.com', '1982-04-08')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Rodriguez', 'Sophie', 'sophie.rodriguez@email.com', '1984-06-17')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Lopez', 'Alexandre', 'alexandre.lopez@email.com', '1979-10-12')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Thomas', 'Christine', 'christine.thomas@email.com', '1986-02-28')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Petit', 'Nicolas', 'nicolas.petit@email.com', '1977-04-11')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Robert', 'Sandrine', 'sandrine.robert@email.com', '1989-07-14')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Durand', 'Fabien', 'fabien.durand@email.com', '1981-09-09')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Simon', 'Julie', 'julie.simon@email.com', '1984-12-23')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Leroy', 'David', 'david.leroy@email.com', '1983-03-19')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Moreau', 'Catherine', 'catherine.moreau@email.com', '1980-06-08')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Lefevre', 'François', 'francois.lefevre@email.com', '1978-10-02')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Mercier', 'Carole', 'carole.mercier@email.com', '1976-05-31')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Dupuis', 'Thomas', 'thomas.dupuis@email.com', '1982-08-15')");


            connection.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}