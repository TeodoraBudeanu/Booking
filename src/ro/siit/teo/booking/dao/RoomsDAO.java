package ro.siit.teo.booking.dao;

import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.model.RoomFair;

import java.sql.SQLException;
import java.util.List;

public interface RoomsDAO {

    List<RoomFair> getAll() throws Exception, BookigsDbException;

    void add(RoomFair roomFair) throws BookigsDbException, SQLException;

    void delete(RoomFair roomFair) throws BookigsDbException, SQLException;

    void update(RoomFair roomFair) throws BookigsDbException, SQLException;
}
