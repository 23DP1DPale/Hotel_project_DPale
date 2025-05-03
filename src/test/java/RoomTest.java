import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RoomTest {
    @Test
    public void testTotalCost() {
        Room room1 = new Room(null, null, 0, 150, 0, null, null, null);
        int discount = 25;
        room1.setDiscount(discount);
        assertTrue(room1.getCostWithDiscount() == 112.5);
    }
}
