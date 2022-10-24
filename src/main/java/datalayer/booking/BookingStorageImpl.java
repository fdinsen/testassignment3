package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage{
    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass){
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingToCreate.customerId);
            stmt.setInt(2, bookingToCreate.employeeId);
            stmt.setDate(3, new java.sql.Date(bookingToCreate.date.getTime()));
            stmt.setTime(4, bookingToCreate.start);
            stmt.setTime(5, bookingToCreate.end);

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int cid) throws SQLException {
        var sql = "select ID, customerId, employeeId, date, start, end from Bookings where customerId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cid);
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking c = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(c);
                }
            }

            return results;
        }
    }

    @Override
    public Collection<Booking> getBookings() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking c = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(c);
                }
            }

            return results;
        }
    }
}
