package ro.siit.teo.booking.dao.sql;

import org.junit.*;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.TestBookingsDb;
import ro.siit.teo.booking.model.RoomFair;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLRoomsDAOTest {

    private TestBookingsDb db;
    private SQLRoomsDAO roomsDAO;

    @BeforeClass
    public static void initTests() throws BookigsDbException, SQLException {
        TestBookingsDb.setUpTestDB();
    }

    @AfterClass
    public static void discardTests() throws BookigsDbException, SQLException {
        TestBookingsDb.dropTestDB();
    }

    @Before
    public void setUp(){
        db = new TestBookingsDb();
        roomsDAO = new SQLRoomsDAO(db);
    }

    @After
    public void tearDown() throws BookigsDbException, SQLException {
        db.dropDataFromTables();
    }

    @Test
    public void whenNewClientsInsertedIntoDB_getReturnsThem() throws BookigsDbException, Exception {
        RoomFair roomFair1 = new RoomFair();
        roomFair1.setSeason("winter");
        roomFair1.setValue(100d);

        RoomFair roomFair2 = new RoomFair();
        roomFair2.setSeason("summer");
        roomFair2.setValue(200d);

        roomsDAO.add(roomFair1);
        roomsDAO.add(roomFair2);

        List<RoomFair> all = roomsDAO.getAll();

        assertThat(all, notNullValue());
        assertThat(all.size(), is(2));
        assertThat(new RoomFair[]{roomFair1, roomFair2}, is(all.toArray()));
    }


}
