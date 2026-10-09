package org.example.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/addtask")
public class AddTask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title=request.getParameter("title");
        String descr=request.getParameter("descr");
        String sched_at=request.getParameter("sched_at");
        String priority=request.getParameter("priority");
        String assign_to_id=request.getParameter("assign_to_id");

        String sql="insert into task(title,descr,sched_at,status,priority,assign_to_id)values(?,?,?,?,?,?)";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql)) {
            statement.setString(1,title);
            statement.setString(2,descr);
            statement.setString(3,sched_at);
            statement.setString(4,"PENDING");
            statement.setString(5,priority);
            statement.setString(6,assign_to_id);
            if(statement.executeUpdate()>0)
            {
                response.sendRedirect("viewtask");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("Data base error\n");
            response.getWriter().println(e.getMessage());
        }
    }
}
