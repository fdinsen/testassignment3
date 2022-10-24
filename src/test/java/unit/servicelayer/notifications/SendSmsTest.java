package unit.servicelayer.notifications;

import datalayer.booking.BookingStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceException;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class SendSmsTest {

    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;
    private SmsService notificationMock;


    @BeforeAll
    public void beforeAll(){
        notificationMock = mock(SmsService.class);
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock, notificationMock);
    }

    @Test
    public void mustCallStorageWhenCreatingBooking() throws CustomerServiceException, SQLException, BookingServiceException {
        // Arrange
        // Act
        var customerId = 0;
        var employeeId = 0;
        var date = new Date(123456789l);
        var start = new Time(0);
        var end = new Time(0);

        bookingService.createBooking(customerId, employeeId, date, start, end);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(notificationMock, times(1))
                .sendSms(
                        argThat(x -> x.equals("12345678")
                        ));
    }
}
