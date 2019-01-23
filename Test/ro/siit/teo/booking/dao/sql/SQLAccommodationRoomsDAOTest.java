package ro.siit.teo.booking.dao.sql;

import org.junit.*;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.TestBookingsDb;
import ro.siit.teo.booking.model.Accommodation;
import ro.siit.teo.booking.model.AccommodationRoomRelation;
import ro.siit.teo.booking.model.RoomFair;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLAccommodationRoomsDAOTest {

    private TestBookingsDb db;

    private SQLAccommodationDAO accommodationDAO;
    private Accommodation acc1;
    private Accommodation acc2;

    private SQLRoomsDAO roomsDAO;
    private RoomFair roomFair1;
    private RoomFair roomFair2;

    private SQLAccommodationRoomsDAO accommodationRoomRelationDAO;
    private AccommodationRoomRelation relation1;
    private AccommodationRoomRelation relation2;


    @BeforeClass
    public static void initTests() throws BookigsDbException, SQLException {
        TestBookingsDb.setUpTestDB();
    }

    @AfterClass
    public static void discardTests() throws BookigsDbException, SQLException {
        TestBookingsDb.dropTestDB();
    }

    @Before
    public void setUp() throws SQLException, BookigsDbException {
        db = new TestBookingsDb();

        acc1 = new Accommodation("hotel", "king size", 3, "asd");
        acc2 = new Accommodation("lodge", "single", 1, "qwe");
        accommodationDAO = new SQLAccommodationDAO(db);
        accommodationDAO.add(acc1);
        accommodationDAO.add(acc2);

        roomFair1 = new RoomFair(100d, "summer");
        roomFair2 = new RoomFair(200d, "winter");
        roomsDAO = new SQLRoomsDAO(db);
        roomsDAO.add(roomFair1);
        roomsDAO.add(roomFair2);


        relation1 = new AccommodationRoomRelation(acc1.getId(), roomFair1.getId());
        relation2 = new AccommodationRoomRelation(acc2.getId(), roomFair1.getId());
        accommodationRoomRelationDAO = new SQLAccommodationRoomsDAO(db);
        accommodationRoomRelationDAO.add(relation1);
        accommodationRoomRelationDAO.add(relation2);

    }

    @After
    public void tearDown() throws BookigsDbException, SQLException {
        db.dropDataFromTables();
    }

    @Test
    public void whenNewAccommodationRoomRelationsInsertedIntoDB_getReturnsThem() throws BookigsDbException, Exception {

        List<AccommodationRoomRelation> all = accommodationRoomRelationDAO.getAll();

        assertThat(all, notNullValue());
        assertThat(all.size(), is(2));
        assertThat(new AccommodationRoomRelation[]{relation1, relation2}, is(all.toArray()));
    }

    @Test
    public void whenNewAccommodationRoomRelationsInsertedIntoDB_listAllReturnsThemWithEachOnesCharacteristics() throws BookigsDbException, Exception {

        accommodationRoomRelationDAO.printAllEntriesDetailed();
    }

}
