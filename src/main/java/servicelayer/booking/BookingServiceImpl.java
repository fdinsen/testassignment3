package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;
    public BookingServiceImpl(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) {
        return 0;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) {
        return null;
    }
}
