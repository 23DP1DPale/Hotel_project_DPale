import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class BookingTest {
    @Test
    public void testBookCost() throws Exception {
        Booking book1 = new Booking(null, "3", 3, null);
        assertTrue(book1.getTotalCost() == 690);
        
    }
    
    @Test
    public void testIfBooked() throws Exception{
        String roomNumber = "5";
        Guest guest = new Guest(null, 0, null);
        assertEquals(false, Booking.checkIfBooked(roomNumber, guest));
    }
}