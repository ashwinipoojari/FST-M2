import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    public void notEnoughFunds()
    {
        BankAccount bankAc = new BankAccount(10);
        assertThrows(NotEnoughFundsException.class, () -> bankAc.withdraw(11));
    }

    @Test
    public void enoughFunds()
    {
        BankAccount bankAc = new BankAccount(100);
        assertDoesNotThrow(() -> bankAc.withdraw(100));
    }
}
