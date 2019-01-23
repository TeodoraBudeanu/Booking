package ro.siit.teo.booking.db;

import java.sql.*;

public class TestBookingsDb extends BookingsDb {

    private Connection connectToPostgreSQL() throws SQLException, BookigsDbException {
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
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("postgres10").toString();
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BookigsDbException("Could not load DB driver.", e);
        }
    }

    @Override
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
                    .append("bookings_test")
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("postgres10").toString();
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BookigsDbException("Could not load DB driver.", e);
        }
    }

    public static void setUpTestDB() throws BookigsDbException, SQLException {
        TestBookingsDb tdb = new TestBookingsDb();
        try (Connection connection = tdb.connectToPostgreSQL()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE DATABASE bookings_test;");
        }

        try (Connection connection = tdb.connect()) {
            StringBuilder builder = new StringBuilder();
            builder.append("CREATE SEQUENCE accomodation_ids;");
            builder.append("CREATE TABLE accomodation(id INT PRIMARY KEY DEFAULT NEXTVAL('accomodation_ids'), " +
                    "type VARCHAR(32), bed_type VARCHAR(32), max_guests INT, description VARCHAR(512));");
            builder.append("CREATE SEQUENCE room_fair_ids;");
            builder.append("CREATE TABLE room_fair(id INT PRIMARY KEY DEFAULT NEXTVAL('room_fair_ids'), " +
                    "value DOUBLE PRECISION, season VARCHAR(32));");
            builder.append("CREATE SEQUENCE accomodation_fair_relation_ids;");
            builder.append("CREATE TABLE accomodation_fair_relation(id INT PRIMARY KEY DEFAULT NEXTVAL('accomodation_fair_relation_ids'), " +
                    " id_accomodation INT REFERENCES accomodation(id), id_room_fair INT REFERENCES room_fair(id));");

            Statement statement = connection.createStatement();
            statement.execute(builder.toString());
        }
    }

    public static void dropTestDB() throws BookigsDbException, SQLException {
        TestBookingsDb tdb = new TestBookingsDb();
        try (Connection connection = tdb.connectToPostgreSQL()) {
            Statement statement = connection.createStatement();
            statement.execute("DROP DATABASE bookings_test;");
        }
    }

    public void dropDataFromTables() throws BookigsDbException, SQLException {

        try (Connection connection = connect()) {
            PreparedStatement deletionPs = null;

            try {
                deletionPs = connection.prepareStatement("DELETE FROM accomodation_fair_relation");
                deletionPs.executeUpdate();
                deletionPs = connection.prepareStatement("DELETE FROM accomodation");
                deletionPs.executeUpdate();
                deletionPs = connection.prepareStatement("DELETE FROM room_fair");
                deletionPs.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Cannot delete tables: " + e.getMessage());

            } finally {
                if (deletionPs != null) {
                    try {
                        deletionPs.close();

                    } catch (SQLException e) {
                        System.out.println("Prepared Statement could not be closed: " + e.getMessage());
                    }
                }
            }
        }
    }

}
