package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateEmployeeTest extends ContainerizedDbIntegrationTest {
    private EmployeeStorage employeeStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(2);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }
    }

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), faker.date().birthday());
            employeeStorage.createEmployee(e);
        }
    }



    @Test
    public void mustSaveEmployeeInDatabaseWhenCallingCreateEmployee() throws SQLException, ParseException {
        // Arrange
        // Act
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn", new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996")));

        // Assert
        var customers = employeeStorage.getEmployees();
        assertTrue(
                customers.stream().anyMatch(x ->
                        {
                            try {
                                return x.getFirstname().equals("John") &&
                                x.getLastname().equals("Carlssonn") &&
                                x.getBirthdate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996"));
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
        var id1 = employeeStorage.createEmployee(new EmployeeCreation("a", "b", new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996")));
        var id2 = employeeStorage.createEmployee(new EmployeeCreation("c", "d", new SimpleDateFormat("dd/MM/yyyy").parse("01/08/1996")));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
