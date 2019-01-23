package ro.siit.teo.booking.dao.sql;

import ro.siit.teo.booking.dao.RoomsDAO;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.BookingsDb;
import ro.siit.teo.booking.model.RoomFair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLRoomsDAO implements RoomsDAO {

    private BookingsDb db;

    public SQLRoomsDAO(BookingsDb db) {
        this.db = db;
    }

    @Override
    public List<RoomFair> getAll() throws Exception, BookigsDbException {
        try (Connection conn = db.connect()) {
/*            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from room_fair;");
            ArrayList<RoomFair> roomFairs = new ArrayList<>();*/

            PreparedStatement selectPs = null;

            try {
                selectPs = conn.prepareStatement("SELECT * from room_fair;");
                ResultSet resultSet = selectPs.executeQuery();
                ArrayList<RoomFair> roomFairs = new ArrayList<>();

                while (resultSet.next()) {
                    RoomFair roomFair = mapResultSetToClient(resultSet);
                    roomFairs.add(roomFair);
                }
                return roomFairs;
            }catch (SQLException e) {
                System.err.println("Cannot retrieve all Room Fairs: " + e.getMessage());
            } finally {
                if (selectPs != null) {
                    try {
                        selectPs.close();
                    } catch (SQLException e) {
                        System.out.println("Prepared Statement could not be closed: " + e.getMessage());
                    }
                }
            }
            return null;
        }
    }

    private RoomFair mapResultSetToClient(ResultSet resultSet) throws SQLException {
        RoomFair roomFair = new RoomFair();
        roomFair.setId(resultSet.getInt("id"));
        roomFair.setValue(resultSet.getDouble("value"));
        roomFair.setSeason(resultSet.getString("season"));
        return roomFair;
    }

    @Override
    public void add(RoomFair roomFair) throws BookigsDbException, SQLException {

/*        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO roomfairs(value, season) values(" + roomFair.getValue() + ", '" + roomFair.getSeason() + "')");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRVAL('room_fairs')");
            resultSet.next();
            roomFair.setId(resultSet.getInt(1));
        }*/

        try (Connection connection = db.connect()) {
            PreparedStatement insertionPs = null;
            PreparedStatement crtValPs = null;

            try {
                insertionPs = connection.prepareStatement("INSERT INTO room_fair(value, season) values( ?, ?)");
                insertionPs.setDouble(1, roomFair.getValue());
                insertionPs.setString(2, roomFair.getSeason());
                insertionPs.executeUpdate();

                crtValPs = connection.prepareStatement("SELECT CURRVAL('room_fair_ids')");
                ResultSet resultSet = crtValPs.executeQuery();
                resultSet.next();
                roomFair.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                System.err.println("Cannot insert Room Fair: " + e.getMessage());
            } finally {
                if (insertionPs != null && crtValPs!= null) {
                    try {
                        insertionPs.close();
                        crtValPs.close();
                    } catch (SQLException e) {
                        System.out.println("Prepared Statement could not be closed: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void delete(RoomFair roomFair) throws BookigsDbException, SQLException {
//todo
    }

    @Override
    public void update(RoomFair roomFair) throws BookigsDbException, SQLException {
//todo
    }
}
