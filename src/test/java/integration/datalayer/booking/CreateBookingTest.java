package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;
    private EmployeeStorage employeeStorage;
    private CustomerStorage customerStorage;

    private int employeeId;
    private int customerId;
    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(3);

        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        var numEmployees = employeeStorage.getEmployees().size();
        employeeId = addFakeEmployee();
        customerId = addFakeCustomer();

        var numBookings = bookingStorage.getBookings().size();
        if (numBookings < 100) {
            addFakeBookings(100 - numBookings);
        }
    }

    private void addFakeBookings(int numBookings) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numBookings; i++) {
            BookingCreation e = new BookingCreation(customerId, employeeId, faker.date().birthday(), new Time(0), new Time(0));
            bookingStorage.createBooking(e);
        }
    }

    private int addFakeEmployee() throws SQLException {
        Faker faker = new Faker();
        EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), faker.date().birthday());
        return employeeStorage.createEmployee(e);
    }

    private int addFakeCustomer() throws SQLException {
        Faker faker = new Faker();
        CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
        return customerStorage.createCustomer(c);
    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateBooking() throws SQLException, ParseException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(customerId,employeeId, new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996"), new Time(0), new Time(0)));

        // Assert
        var bookings = bookingStorage.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        {
                            try {
                                return x.getCustomerId() == customerId &&
                                x.getEmployeeId() == employeeId &&
                                x.getDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996")) &&
                                x.getStart().equals(new Time(0)) &&
                                x.getEnd().equals(new Time(0));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }

    @Test
    public void mustReturnLatestId() throws SQLException, ParseException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(customerId, employeeId, new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996"), new Time(0), new Time(0)));
        var id2 = bookingStorage.createBooking(new BookingCreation(customerId, employeeId, new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996"), new Time(0), new Time(0)));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
