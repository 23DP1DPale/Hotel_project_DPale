import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BalanceTest {
    @Test
    public void testAddMoney() {
        Guest guest1 = new Guest(null, 0, null);
        Double addMoney1 = 25.5;
        guest1.deposit(addMoney1);
        assertTrue(guest1.getBalance() == 25.5);

        Guest guest2 = new Guest(null, 0, null);
        Double addMoney2 = -5.0;
        guest2.deposit(addMoney2);
        assertTrue(guest2.getBalance() == 0.0);

        Guest guest3 = new Guest(null, 10, null);
        Double addMoney3 = -2.5;
        guest3.deposit(addMoney3);
        assertTrue(guest3.getBalance() == 10);
    }

    @Test
    public void testWithdrawMoney() {
        Guest guest1 = new Guest(null, 50, null);
        Double withdraw1 = 10.5;
        guest1.withdraw(withdraw1);
        assertTrue(guest1.getBalance() == 39.5);

        Guest guest2 = new Guest(null, 50, null);
        Double withdraw2 = -10.5;
        guest2.withdraw(withdraw2);
        assertTrue(guest2.getBalance() == 50);

        Guest guest3 = new Guest(null, 20, null);
        Double withdraw3 = 20.0;
        guest3.withdraw(withdraw3);
        assertTrue(guest3.getBalance() == 0);

        Guest guest4 = new Guest(null, 20, null);
        Double withdraw4 = 30.0;
        guest4.withdraw(withdraw4);
        assertTrue(guest4.getBalance() == 20);
    }

    @Test
    public void testGetBalanceFromExistingGuest() throws Exception {
        String name = "Michael";
        String password = "Mich#123";
        assertTrue(Guest.getBalanceFromExistingGuest(name, password) == 45);
    }   
}
