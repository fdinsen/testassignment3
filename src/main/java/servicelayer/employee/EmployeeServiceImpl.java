package servicelayer.employee;

import datalayer.booking.BookingStorage;
import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;
import servicelayer.customer.CustomerServiceException;

import java.sql.SQLException;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeStorage employeeStorage;
    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName, birthdate));
        }catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException {
        return employeeStorage.getEmployeeWithId(employeeId);
    }
}
