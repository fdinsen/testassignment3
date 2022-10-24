package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import dto.BookingCreation;
import dto.EmployeeCreation;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;
    private SmsService smsService;
    public BookingServiceImpl(BookingStorage bookingStorage, SmsService smsService) {
        this.bookingStorage = bookingStorage;
        this.smsService = smsService;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            var createdId = bookingStorage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
            return createdId;
        }catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        return bookingStorage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        return bookingStorage.getBookingsForEmployee(employeeId);
    }
}
