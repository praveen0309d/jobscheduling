package org.example.company;
import com.mysql.cj.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/useradd")
public class AddUsers extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
//        if(session==null||!"ADMIN".equalsIgnoreCase((String)session.getAttribute("role")))
//        {
//            response.sendRedirect("index.html");
//            return;
//        }

        String username=request.getParameter("username");
        String email=request.getParameter("email");
        String age=request.getParameter("age");
        String role=request.getParameter("role");
        String sql="insert into users(username,email,age,role,password)value(?,?,?,?,?)";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql))
        {
            statement.setString(1,username);
            statement.setString(2,email);
            statement.setString(3,age);
            statement.setString(4,role);
            statement.setString(5,"Pass@123");
            if(statement.executeUpdate()>0)
            {
                response.setContentType("text/html");
//                response.getWriter().println("Added successfully");
//                response.getWriter().println("<a href=Admindash.html>Back to Dashboard</a>");
                response.sendRedirect("viewusers");
            }
            else
            {
                response.setContentType("text/html");
                response.getWriter().println("Adding users is failed");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");

            response.getWriter().println("Data base error");
            response.getWriter().println(e.getMessage());
        }
    }
}
