package test.pl;

import be.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTypeTest {

    @Test
    public void testGetValue() {
        AccountType ceo = AccountType.CEO;
        AccountType technician = AccountType.TECHNICIAN;

        int ceoValue = ceo.getValue();
        int technicianValue = technician.getValue();

        Assertions.assertEquals(0, ceoValue);
        Assertions.assertEquals(1, technicianValue);
    }

    @Test
    public void testFromValue() {
        int ceoValue = 0;
        int technicianValue = 1;
        int invalidValue = 99;

        AccountType ceoType = AccountType.fromValue(ceoValue);
        AccountType technicianType = AccountType.fromValue(technicianValue);
        AccountType invalidType = AccountType.fromValue(invalidValue);

        Assertions.assertEquals(AccountType.CEO, ceoType);
        Assertions.assertEquals(AccountType.TECHNICIAN, technicianType);
        Assertions.assertNull(invalidType);
    }
}