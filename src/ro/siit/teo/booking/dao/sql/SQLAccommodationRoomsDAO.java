package ro.siit.teo.booking.dao.sql;

import ro.siit.teo.booking.dao.AccommodationRoomsRelationDAO;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.BookingsDb;
import ro.siit.teo.booking.model.AccommodationRoomRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAccommodationRoomsDAO implements AccommodationRoomsRelationDAO {

    private BookingsDb db;

    public SQLAccommodationRoomsDAO(BookingsDb db) {
        this.db = db;
    }

    @Override
    public List<AccommodationRoomRelation> getAll() throws Exception, BookigsDbException {
        try (Connection conn = db.connect()) {
            PreparedStatement selectPs = null;

            try {
                selectPs = conn.prepareStatement("SELECT * from accomodation_fair_relation;");
                ResultSet resultSet = selectPs.executeQuery();
                ArrayList<AccommodationRoomRelation> accommodationRoomRelations = new ArrayList<>();

                while (resultSet.next()) {
                    AccommodationRoomRelation accommodationRoomRelation = mapResultSetToClient(resultSet);
                    accommodationRoomRelations.add(accommodationRoomRelation);
                }
                return accommodationRoomRelations;
            } catch (SQLException e) {
                System.err.println("Cannot retrieve all Accommodation - Room Relations: " + e.getMessage());
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

    private AccommodationRoomRelation mapResultSetToClient(ResultSet resultSet) throws SQLException {
        AccommodationRoomRelation accommodationRoomRelation = new AccommodationRoomRelation();
        accommodationRoomRelation.setId(resultSet.getInt("id"));
        accommodationRoomRelation.setAccommodationId(resultSet.getInt("id_accomodation"));
        accommodationRoomRelation.setRoomFairId(resultSet.getInt("id_room_fair"));
        return accommodationRoomRelation;
    }

    @Override
    public void add(AccommodationRoomRelation relation) throws BookigsDbException, SQLException {
        try (Connection connection = db.connect()) {
            PreparedStatement insertionPs = null;
            PreparedStatement crtValPs = null;

            try {
                insertionPs = connection.prepareStatement("INSERT INTO  accomodation_fair_relation(id_accomodation, id_room_fair) values( ?, ?)");
                insertionPs.setInt(1, relation.getAccommodationId());
                insertionPs.setInt(2, relation.getRoomFairId());
                insertionPs.executeUpdate();

                crtValPs = connection.prepareStatement("SELECT CURRVAL('accomodation_fair_relation_ids')");
                ResultSet resultSet = crtValPs.executeQuery();
                resultSet.next();
                relation.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                System.err.println("Cannot insert Accommodation - Room relation: " + e.getMessage());
            } finally {
                if (insertionPs != null && crtValPs != null) {
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

    public void printAllEntriesDetailed() throws Exception, BookigsDbException {
        try (Connection connection = db.connect()) {
            PreparedStatement selectionPs = null;
            try {
                selectionPs = connection.prepareStatement("SELECT * FROM accomodation ac\n" +
                        "        INNER JOIN accomodation_fair_relation afr ON ac.id = afr.id_accomodation\n" +
                        "        INNER JOIN room_fair r ON r.id = afr.id_room_fair;");
                ResultSet resultSet = selectionPs.executeQuery();

                while(resultSet.next()){
                    printEntry(resultSet);
                }


            } catch (SQLException e) {
                System.err.println("Cannot retrieve Accommodation - Room relation: " + e.getMessage());
            } finally {
                if (selectionPs != null) {
                    try {
                        selectionPs.close();
                    } catch (SQLException e) {
                        System.out.println("Prepared Statement could not be closed: " + e.getMessage());
                    }
                }

            }
        }
    }

    private void printEntry(ResultSet resultSet) throws Exception, BookigsDbException {
        System.out.println("Accommodation " +
                "id=" + resultSet.getInt(1) +
                ", type='" + resultSet.getString(2) + '\'' +
                ", bedType='" + resultSet.getString(3) + '\'' +
                ", maxGuests=" + resultSet.getString(4) +
                ", description='" + resultSet.getString(5) + '\'' +
                ", value=" + resultSet.getString(10) +
                ", season='" + resultSet.getString(11) + '\'');

    }

    @Override
    public void delete(AccommodationRoomRelation relation) throws BookigsDbException, SQLException {

    }

    @Override
    public void update(AccommodationRoomRelation relation) throws BookigsDbException, SQLException {

    }
}
