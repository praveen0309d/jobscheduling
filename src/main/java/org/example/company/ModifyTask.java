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

@WebServlet("/modifytask")
public class ModifyTask extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id=request.getParameter("id");
    var out=response.getWriter();
    if(id==null)
    {
        response.sendError(400,"Task is required");
        return;
    }
    String sql="select * from task where id=?";
    try(Connection connection=DBConnection.getConnection();
    PreparedStatement statement= connection.prepareStatement(sql))
    {
        statement.setInt(1,Integer.parseInt(id));

        try(ResultSet resultSet=statement.executeQuery())
        {

            if(resultSet.next())
            {
                String title=resultSet.getString("title");
                String descr=resultSet.getString("descr");
                String sched_at=resultSet.getString("sched_at");
                String priority=resultSet.getString("priority");
                 response.getWriter().println("<html><body>");
                 response.getWriter().println("MODIFY TASK");
                 response.getWriter().println("<form action='modifytask' method='post'>");
                 response.getWriter().println("<br><br><input type='hidden' name='id' value='"+id+"'> ");
                 response.getWriter().println("<br><br>Title:<input type='text' name='title' value='"+title+"'> ");
                 response.getWriter().println("<br><br>Description:<input type='text' name='descr' value='"+descr+"'> ");
                 response.getWriter().println("<br><br>Scheduled At:<input type='datetime-local' name='sched_at' value='"+sched_at+"'> ");
                 response.getWriter().println("<br><br>Priority:<select name=\"priority\" required>\n" +
                         "            <option value=\"LOW\">Low</option>\n" +
                         "            <option value=\"MEDIUM\">Medium</option>\n" +
                         "            <option value=\"HIGH\">High</option>\n" +
                         "        </select> ");
                 response.getWriter().println("<br><br><button type='submit'>Update</button></form></body></html>");
            }
            else
            {
                 response.getWriter().println("Task not found");
            }
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
        response.setContentType("text/html");
        response.getWriter().println("Data base error");
        response.getWriter().println(e.getMessage());
    }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            int id=Integer.parseInt(request.getParameter("id"));
            String title=request.getParameter("title");
            String descr=request.getParameter("descr");
            String priority=request.getParameter("priority");
            String sched_at=request.getParameter("sched_at");
            String sql="update task set title=?,descr=?,priority=?,sched_at=? where id=?";
            try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(sql))
            {
                statement.setString(1,title);
                statement.setString(2,descr);
                statement.setString(3,priority);
                statement.setString(4,sched_at);
                statement.setInt(5, id);
                statement.executeUpdate();
                response.sendRedirect(request.getContextPath()+"/viewtask");


            }
            catch (SQLException e) {
                e.printStackTrace();
                response.setContentType("text/html");
                response.getWriter().println("Data base error");
                response.getWriter().println(e.getMessage());
            }
        }}
}
