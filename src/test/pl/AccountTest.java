package test.pl;

import be.Account;
import be.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testGetId() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        int id = account.getId();

        Assertions.assertEquals(1, id);
    }

    @Test
    public void testGetName() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        String name = account.getName();

        Assertions.assertEquals("JohnDoe", name);
    }

    @Test
    public void testGetPassword() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        String password = account.getPassword();

        Assertions.assertEquals("password123", password);
    }

    @Test
    public void testGetType() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        AccountType type = account.getType();

        Assertions.assertEquals(AccountType.TECHNICIAN, type);
    }

    @Test
    public void testSetName() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        account.setName("JaneSmith");

        Assertions.assertEquals("JaneSmith", account.getName());
    }

    @Test
    public void testSetPassword() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        account.setPassword("newPassword123");

        Assertions.assertEquals("newPassword123", account.getPassword());
    }

    @Test
    public void testSetType() {
        Account account = new Account(1, "JohnDoe", "password123", AccountType.TECHNICIAN);

        account.setType(AccountType.CEO);

        Assertions.assertEquals(AccountType.CEO, account.getType());
    }
}
