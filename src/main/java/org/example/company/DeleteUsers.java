package org.example.company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/deleteuser")
public class DeleteUsers extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Users tables TO Delete Users</h1><br><br>");
        response.getWriter().println("<a href=Admindash.html>Back to Dashboard</a><br><br>");
        //        if(session==null||!"ADMIN".equalsIgnoreCase((String)session.getAttribute("role")))
        //        {
        //            response.sendRedirect("index.html");
        //            return;
        //        }
int id=Integer.parseInt(request.getParameter("id"));
String sql="delete from users where id=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql))
        {
            statement.setInt(1,id);
            if(statement.executeUpdate()>0)
            {
                response.sendRedirect("viewusers");
            }
            else{
                response.setContentType("text/html");
                response.getWriter().println("user not found");
            }



        }
        catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("Data base error");
            response.getWriter().println(e.getMessage());
        }

    }
}
