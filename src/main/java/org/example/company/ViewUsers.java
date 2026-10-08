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

@WebServlet("/viewusers")
public class ViewUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Users Tables To View Users</h1><br><br>");
        response.getWriter().println("<a href=Admindash.html>Back to Dashboard</a><br><br>");
        //        if(session==null||!"ADMIN".equalsIgnoreCase((String)session.getAttribute("role")))
        //        {
        //            response.sendRedirect("index.html");
        //            return;
        //        }
        String sql="select id,username,email,age,password,role from users";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery())
        {
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<table border=\"1\">" +
                    "<tbody>" +
                    "<thead>" +
                    "<td> ID </td>" +
                    "<td> NAME </td>" +
                    "<td> EMAIL </td>" +
                    "<td> AGE </td>" +
                    "<td> ROLE </td>" +
                    "<td> DELETE </td>" +
                    "</thead>");
            while (resultSet.next())
            {
                int id=resultSet.getInt("id");
                String username=resultSet.getString("username");
                String email=resultSet.getString("email");
                int age=resultSet.getInt("age");
                String role=resultSet.getString("role");


                response.getWriter().println("<td>"+id+"</td>");
                response.getWriter().println("<td>"+username+"</td>");
                response.getWriter().println("<td>"+email+"</td>");
                response.getWriter().println("<td>"+age+"</td>");
                response.getWriter().println("<td>"+role+"</td>");
                response.getWriter().println("<td>"+"<form action='deleteuser' method='post'>"
                        +"<input type='hidden' name='id' value='"+id+"'>" +
                        "<button type='submit'>Delete</button></form>"+"</td>");
                response.getWriter().println("</tbody>");

//                response.getWriter().println("id"+id);
//                response.getWriter().println("username"+username);
//                response.getWriter().println("email"+email);
//                response.getWriter().println("age"+email);
//                response.getWriter().println("role"+email);

            }
            response.getWriter().println("</table>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("Data base error");
            response.getWriter().println(e.getMessage());
        }

    }
}
