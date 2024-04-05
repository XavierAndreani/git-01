package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/vehicles/create")

public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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
        response.sendRedirect(request.getContextPath() + "/vehicles/list");

    }
}
