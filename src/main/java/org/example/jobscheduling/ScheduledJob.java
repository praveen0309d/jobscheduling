package org.example.jobscheduling;
import org.example.company.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ScheduledJob implements Runnable {
    private final int taskid;
    private final String title;
    private final int assignedTo;

    public ScheduledJob(int id, String title, int assignTo) {
        this.taskid=id;
        this.title=title;
        this.assignedTo=assignTo;
        }
        @Override
    public void run()
        {
            System.out.println("Schedulejob is running");
            long startTime=System.currentTimeMillis();
            String status="SUCCESS";
            try {
                    System.out.println("TaskID:"+taskid);
                    System.out.println("Task"+title);
                    System.out.println("Assigned to:"+assignedTo);
                    System.out.println("Worker executing taskk....");

            } catch (Exception e) {
                status="FAILED";
                e.printStackTrace();
            }
            finally {
                long endTime=System.currentTimeMillis();
                long duration=endTime-startTime;
                String sql="update task set status=? where id=?";
                try(Connection connection=DBConnection.getConnection();
                    PreparedStatement statement=connection.prepareStatement(sql))
                {
                    statement.setString(1,status);
                    statement.setInt(2,taskid);
                    statement.executeUpdate();
                    System.out.println("Status"+status);
                    System.out.println("Status"+duration);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
}
