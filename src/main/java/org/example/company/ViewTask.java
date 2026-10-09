package org.example.company;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/viewtask")
public class ViewTask extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Edit Task</h1><br>");
        //        if(session==null||!"ADMIN".equalsIgnoreCase((String)session.getAttribute("role")))
        //        {
        //            response.sendRedirect("index.html");
        //            return;
        //        }
        String sql="select * from task";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet resultSet=statement.executeQuery()
        )
        {
            response.setContentType("text/html");
            response.getWriter().println("<table border=\"1\">" +
                    "<tbody>" +
                    "<thead>" +
                    "<br><br><b>Task list</b><br><br>"+
                    "<td> TaskId </td>" +
                    "<td> Title </td>" +
                    "<td> Description </td>" +
                    "<td> Schrduled At </td>" +
                    "<td> Status </td>" +
                    "<td> Create AT </td>" +
                    "<td> priority </td>" +
                    "<td> Modify </td>" +

                    "</thead>");
            while(resultSet.next())
            {
                int taskid=resultSet.getInt("id");
                String title= resultSet.getString("title");
                String descr=resultSet.getString("descr");
                String sched_at=resultSet.getString("sched_at");
                String status=resultSet.getString("status");
                String creat_at=resultSet.getString("creat_at");
                String priority=resultSet.getString("priority");

                response.getWriter().println("<td>"+taskid+"</td>");
                response.getWriter().println("<td>"+title+"</td>");
                response.getWriter().println("<td>"+descr+"</td>");
                response.getWriter().println("<td>"+sched_at+"</td>");
                response.getWriter().println("<td>"+status+"</td>");
                response.getWriter().println("<td>"+creat_at+"</td>");
                response.getWriter().println("<td>"+priority+"</td>");
                response.getWriter().println("<td>"+"<a href='modifytask?id="+taskid+"'>Modify</a></td>");
                response.getWriter().println("</tbody>");

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
