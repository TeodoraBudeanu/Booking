package ro.siit.teo.booking.dao.sql;

import ro.siit.teo.booking.dao.AccommodationDAO;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.BookingsDb;
import ro.siit.teo.booking.model.Accommodation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAccommodationDAO implements AccommodationDAO {

    private BookingsDb db;

    public SQLAccommodationDAO(BookingsDb db) {
        this.db = db;
    }

    @Override
    public List<Accommodation> getAll() throws Exception, BookigsDbException {
        try (Connection conn = db.connect()) {
            PreparedStatement selectPs = null;
            try {
                selectPs = conn.prepareStatement("SELECT * from accomodation;");
                ResultSet resultSet = selectPs.executeQuery();
                ArrayList<Accommodation> accommodations = new ArrayList<>();
                while (resultSet.next()) {
                    Accommodation accommodation = mapResultSetToClient(resultSet);
                    accommodations.add(accommodation);
                }
                return accommodations;
            } catch (SQLException e) {
                System.err.println("Cannot retrieve all Accommodation types: " + e.getMessage());
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

    private Accommodation mapResultSetToClient(ResultSet resultSet) throws SQLException {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(resultSet.getInt("id"));
        accommodation.setBedType(resultSet.getString("bed_type"));
        accommodation.setDescription(resultSet.getString("description"));
        accommodation.setMaxGuests(resultSet.getInt("max_guests"));
        accommodation.setType(resultSet.getString("type"));
        return accommodation;
    }

    @Override
    public void add(Accommodation accommodation) throws BookigsDbException, SQLException {
        try (Connection connection = db.connect()) {
            PreparedStatement insertionPs = null;
            PreparedStatement crtValPs = null;

            try {
                insertionPs = connection.prepareStatement("INSERT INTO accomodation(type, bed_type, max_guests, description) values( ?, ?, ?, ?)");
                insertionPs.setString(1, accommodation.getType());
                insertionPs.setString(2, accommodation.getBedType());
                insertionPs.setInt(3, accommodation.getMaxGuests());
                insertionPs.setString(4, accommodation.getDescription());
                insertionPs.executeUpdate();

                crtValPs = connection.prepareStatement("SELECT CURRVAL('accomodation_ids')");
                ResultSet resultSet = crtValPs.executeQuery();
                resultSet.next();
                accommodation.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                System.err.println("Cannot insert Accommodation: " + e.getMessage());
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
    public void delete(Accommodation accommodation) throws BookigsDbException, SQLException {

    }

    @Override
    public void update(Accommodation accommodation) throws BookigsDbException, SQLException {

    }
}
