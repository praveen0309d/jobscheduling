package org.example.jobscheduling;
import org.example.company.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public class jobscheduling {
    private static final ScheduledExecutorService scheduler= Executors.newSingleThreadScheduledExecutor();
    private static final ExecutorService workers=Executors.newFixedThreadPool(3);
    public static void start()
    {
        scheduler.scheduleAtFixedRate(jobscheduling::checkTasks,0,5,TimeUnit.SECONDS);
        System.out.println("Job Scheduler Started");
    }

    private static void checkTasks() {
        System.out.println("checking pending task...");
        String sql="select id,title,assign_to_id from task where status='PENDING' and sched_at<=now() and assign_to_id is not null";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            ResultSet resultset=statement.executeQuery())
        {
            while(resultset.next()) {
                int id = resultset.getInt("id");
                String title=resultset.getString("title");
                int assign_to_id= resultset.getInt("assign_to_id");
                String update="update task set status='RUNNING' where id=? and status='PENDING'";
                try(Connection updateCon=DBConnection.getConnection();
                PreparedStatement updatePs= updateCon.prepareStatement(update))
                {
                    updatePs.setInt(1,id);
                    if(updatePs.executeUpdate()==1)
                    {
                        workers.submit(new ScheduledJob(id,title,assign_to_id));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void stop()
    {
        scheduler.shutdown();
        workers.shutdown();
    }
}

