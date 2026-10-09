package org.example.company;
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
import java.util.Locale;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class Login extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String sql="select id,username,email,password,role from users where email=? and password=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql))
        {
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()) {
                HttpSession session= request.getSession();
                if(email.endsWith("@xyz.com"))
                {
                    response.sendRedirect("Admindash.html");
                }
                else if("viewers".toLowerCase().equals(resultSet.getString("role")))
                {
                    response.sendRedirect("viewerdash");
                }
                else if("operator".toLowerCase().equals(resultSet.getString("role")))
                {
                    response.sendRedirect("operatordash");
                }


            }
            else {
                response.setContentType("text/html");

                response.getWriter().println("Invalid User id or password");

            }
        } catch (SQLException e) {
e.printStackTrace();
response.setContentType("text/html");

response.getWriter().println("Data base error");
response.getWriter().println(e.getMessage());
        }
    }
}
