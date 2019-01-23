package ro.siit.teo.booking.db;

public class BookigsDbException extends Throwable {
    public BookigsDbException(String s, Exception e) {
        super(s, e);
    }

    public BookigsDbException(String s) {
        super(s);
    }
}
