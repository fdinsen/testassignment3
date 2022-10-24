package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateBookingTest {

    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;

    @BeforeAll
    public void beforeAll(){
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);
    }

    @Test
    public void mustCallStorageWhenCreatingBooking() throws CustomerServiceException, SQLException {
        // Arrange
        // Act
//        var firstName = "a";
//        var lastName = "b";
//        var birthdate = new Date(123456789l);

//        bookingService.createCustomer(firstName, lastName, birthdate);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
//        verify(storageMock, times(1))
//                .createBooking(
//                        argThat(x -> x.firstname.equals(firstName) &&
//                                x.lastname.equals(lastName)));
    }
}
