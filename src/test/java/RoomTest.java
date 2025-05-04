import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoomTest {
    @Test
    public void testTotalCost() {
        Room room = new Room(null, null, 0, 150, 0, null, null, null);
        assertTrue(room.getCostWithDiscount() == 150);

        int discount = 25;
        room.setDiscount(discount);
        assertTrue(room.getCostWithDiscount() == 112.5);
        
        discount = 100;
        room.setDiscount(discount);
        assertTrue(room.getCostWithDiscount() == 0);
    }
}