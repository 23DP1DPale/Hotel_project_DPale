import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GuestTest {
    @Test
    public void testCheckIfPasswordExists() throws Exception {
        String name = "test";
        String password = "Dav123";
        assertEquals(false, Guest.checkIfPasswordExists(name, password));
    }

    @Test
    public void testCheckIfGuestExists() throws Exception {
        String name = "Dave";
        String password = "Dav123";
        assertEquals(false, Guest.checkIfGuestExists(name, password));
    }
}
