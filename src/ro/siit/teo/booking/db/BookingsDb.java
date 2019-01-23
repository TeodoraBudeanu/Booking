package ro.siit.teo.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookingsDb {

    public Connection connect() throws BookigsDbException, SQLException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            Connection connection = null;
            DriverManager.setLoginTimeout(60);
            String url = new StringBuilder()
                    .append("jdbc:")
                    .append("postgresql")       // “mysql” / “db2” / “mssql” / “oracle” / ...
                    .append("://")
                    .append("localhost")
                    .append(":")
                    .append(5432)
                    .append("/")
                    .append("bookings")
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("postgres10").toString();
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BookigsDbException("Could not load DB driver.", e);
        }
    }
}
