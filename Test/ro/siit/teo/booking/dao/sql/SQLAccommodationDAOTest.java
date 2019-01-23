package ro.siit.teo.booking.dao.sql;

import org.junit.*;
import ro.siit.teo.booking.db.BookigsDbException;
import ro.siit.teo.booking.db.TestBookingsDb;
import ro.siit.teo.booking.model.Accommodation;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLAccommodationDAOTest {


    private TestBookingsDb db;
    private SQLAccommodationDAO accommodationDAO;

    @BeforeClass
    public static void initTests() throws BookigsDbException, SQLException {
        TestBookingsDb.setUpTestDB();
    }

    @AfterClass
    public static void discardTests() throws BookigsDbException, SQLException {
        TestBookingsDb.dropTestDB();
    }

    @Before
    public void setUp() {
        db = new TestBookingsDb();
        accommodationDAO = new SQLAccommodationDAO(db);
    }

    @After
    public void tearDown() throws BookigsDbException, SQLException {
        db.dropDataFromTables();
    }

    @Test
    public void whenNewClientsInsertedIntoDB_getReturnsThem() throws BookigsDbException, Exception {
        Accommodation acc1 = new Accommodation("hotel");
        Accommodation acc2 = new Accommodation("lodge");

        accommodationDAO.add(acc1);
        accommodationDAO.add(acc2);

        List<Accommodation> all = accommodationDAO.getAll();

        assertThat(all, notNullValue());
        assertThat(all.size(), is(2));
        assertThat(new Accommodation[]{acc1, acc2}, is(all.toArray()));
    }

    public void whenRelationAreInsertedIntoDB_listAllPrintsThem(){

    }
}
