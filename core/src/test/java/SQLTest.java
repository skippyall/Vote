import java.sql.*;

public class SQLTest {
    static String path = "/home/falko/Dokumente/Programmieren/test.db";
    public static void main(String[] args) {
        String url = "jdbc:sqlite:" + path;

        try(Connection connection = DriverManager.getConnection(url)) {
            /*connection.createStatement().execute("DROP TABLE IF EXISTS Benutzer");
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS Benutzer (\n"+
                    "BenutzerId INTEGER PRIMARY KEY,\n" +
                    "Benutzername TEXT NOT NULL" +
                    ");");
            connection.createStatement().execute("INSERT INTO Benutzer (Benutzername, BenutzerId) " +
                    " VALUES('Falko', 1)");
            connection.createStatement().execute("INSERT INTO Benutzer (Benutzername, BenutzerId) " +
                    " VALUES('Benjamin', 2)");*/
            Statement query = connection.createStatement();
            ResultSet set = query.executeQuery("SELECT Benutzername FROM Benutzer WHERE BenutzerId = 1");
            System.out.println(set.getString("Benutzername"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
