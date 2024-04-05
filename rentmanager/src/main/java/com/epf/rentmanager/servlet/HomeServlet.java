package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	@Autowired
	VehicleService vehicleService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	ClientService clientService;
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			int nb_vehicles=vehicleService.count();
			request.setAttribute("nb_vehicles", nb_vehicles);
			int nb_reservation=reservationService.count();
			request.setAttribute("nb_reservation", nb_reservation);
			int nb_clients=clientService.count();
			request.setAttribute("nb_clients", nb_clients);
		}
        catch (ServiceException e){
			throw new RuntimeException(e);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
