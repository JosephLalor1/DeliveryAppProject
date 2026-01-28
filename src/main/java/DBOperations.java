import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DBOperations {

    static Connection connection = null;
    public static void Connect()
        {
            Dotenv dotenv = Dotenv.load();
            final String DATABASE_URL = dotenv.get("DATABASE_URL");
            final String DATABASE_USERNAME = dotenv.get("DATABASE_USER");
            final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
            try 
                {
                    connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
        }
    public static void Clear(String table)
        {
            PreparedStatement pstat = null;
            try 
                {
                    pstat = connection.prepareStatement("DELETE FROM " + table);
                    pstat.execute();
                    System.out.println("Cleared table");
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            finally 
                {
                    try 
                        {
                            pstat.close();
                        }
                    catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                }
        }
    public static String Display(String table)
        {
            String outp = "";
            PreparedStatement pstat = null;
            ResultSet resultSet = null;
            try 
                {
                    pstat = connection.prepareStatement("SELECT * FROM " + table);
                    resultSet = pstat.executeQuery();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int numberOfColumns = metaData.getColumnCount();
                    outp = outp + "Orders Database:\n";

                    for ( int i = 1; i <= numberOfColumns; i++ )
                        {
                            outp = outp + metaData.getColumnName( i ) + "\t";
                            outp = outp + "\n";
                        }

                    while( resultSet .next() )
                        {
                            for ( int i = 1; i <= numberOfColumns; i++ )
                                {
                                    outp = outp + resultSet .getObject( i ) + "\t\t";
                                    outp = outp + "\n";
                                }
                        }
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            finally 
                {
                    try 
                        {
                            pstat.close();
                        }
                    catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                }
            return outp;
        }
    public static void Insert(String table, String obj)
        {

            PreparedStatement pstat = null;
            int i = 0;
            try 
                {
                    pstat = connection.prepareStatement("INSERT INTO " + table + " (name) VALUES (?)");
                    pstat.setString (1, obj );
                    i = pstat .executeUpdate();
                    System.out.println(i + " record successfully added to the table .");
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            finally 
                {
                    try 
                        {
                            pstat.close();
                        }
                    catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                }
        }
    public static int count(String table)
        {
            int outp = 0;
            PreparedStatement pstat = null;
            ResultSet resultSet = null;
            try 
                {
                    pstat = connection.prepareStatement("SELECT COUNT(*) FROM " + table);
                    resultSet = pstat.executeQuery();
                    if (resultSet.next()) 
                        {
                            return resultSet.getInt(1);
                        }
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            finally 
                {
                    try 
                        {
                            pstat.close();
                        }
                    catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                }
            return outp;
        }

    public static int countFood(int restaurantid)
        {
            int outp = 0;
            PreparedStatement pstat = null;
            ResultSet resultSet = null;
            try 
                {
                    pstat = connection.prepareStatement("SELECT COUNT(restaurantid = " + restaurantid + ") FROM menuItems");
                    resultSet = pstat.executeQuery();
                    if (resultSet.next()) 
                        {
                            return resultSet.getInt(1);
                        }
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            finally 
                {
                    try 
                        {
                            pstat.close();
                        }
                    catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                }
            return outp;
        }
    public static ResultSet dbResults(String table)
        {
            PreparedStatement pstat = null;
            ResultSet resultSet = null;
            try 
                {
                    pstat = connection.prepareStatement("SELECT * FROM " + table);
                    resultSet = pstat.executeQuery();
                    if (resultSet.next()) 
                        {
                            return resultSet;
                        }
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }

            return resultSet;
        }
    public static ResultSet dbResults(String table, int row)
        {
            PreparedStatement pstat = null;
            ResultSet resultSet = null;
            try 
                {
                    pstat = connection.prepareStatement("SELECT * FROM " + table + " WHERE restaurantid = " + row);
                    resultSet = pstat.executeQuery();
                    if (resultSet.next()) 
                        {
                            return resultSet;
                        }
                }
            catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }

            return resultSet;
        }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception exception)
                {
                    exception.printStackTrace();
                }
        }
    }
}
