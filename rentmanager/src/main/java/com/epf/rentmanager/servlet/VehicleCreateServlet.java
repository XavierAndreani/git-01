package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/vehicles/create")

public class VehicleCreateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    public VehicleCreateServlet(){this.vehicleService = VehicleService.getInstance();}
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nb_places = Integer.parseInt(request.getParameter("nb_places"));
        try {
            vehicleService.create(new Vehicle(0,constructeur, modele, nb_places));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
