package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@WebServlet("/rents/create")

public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            List<Client> clients = clientService.findAll();
            request.setAttribute("clients", clients);
            List<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("vehicles", vehicles);
        }
        catch (ServiceException e){
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int client_id = Integer.parseInt(request.getParameter("client_id"));
        int vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
        LocalDate debut = LocalDate.parse(request.getParameter("debut"), formatter);
        LocalDate fin = LocalDate.parse(request.getParameter("fin"), formatter);
        System.out.println(client_id+""+vehicle_id+debut.toString()+fin.toString());
        try {
            reservationService.create(new Reservation(0,client_id, vehicle_id, debut, fin));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/rents/list");

    }
}
