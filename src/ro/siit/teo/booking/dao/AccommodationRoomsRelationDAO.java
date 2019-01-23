package ro.siit.teo.booking.dao;

import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.model.AccommodationRoomRelation;

import java.sql.SQLException;
import java.util.List;

public interface AccommodationRoomsRelationDAO {

    List<AccommodationRoomRelation> getAll() throws Exception, BookigsDbException;

    void add(AccommodationRoomRelation relation) throws BookigsDbException, SQLException;

    void delete(AccommodationRoomRelation relation) throws BookigsDbException, SQLException;

    void update(AccommodationRoomRelation relation) throws BookigsDbException, SQLException;
}
