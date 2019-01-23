package ro.siit.teo.booking.dao;

import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.model.Accommodation;

import java.sql.SQLException;
import java.util.List;

public interface AccommodationDAO {

    List<Accommodation> getAll() throws Exception, BookigsDbException;

    void add(Accommodation accommodation) throws BookigsDbException, SQLException;

    void delete(Accommodation accommodation) throws BookigsDbException, SQLException;

    void update(Accommodation accommodation) throws BookigsDbException, SQLException;
}
