package dto;

import java.sql.Time;
import java.util.Date;

public class BookingCreation {

    public final int customerId, employeeId;
    public final Date date;
    public final Time start, end;

    public BookingCreation(int customerId, int employeeId, Date date, Time start, Time end) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;
    }
}
