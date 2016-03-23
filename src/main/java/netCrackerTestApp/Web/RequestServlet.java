package netCrackerTestApp.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Artemiy on 23.03.2016.
 */

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {


    void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchText = request.getParameter("searchRequestText");
        System.out.println("searchText = " + searchText);
        }
    }



