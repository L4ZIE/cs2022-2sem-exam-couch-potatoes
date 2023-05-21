/*package test.pl;

import org.junit.Before;
import org.junit.Test;
import pl.controllers.LoginController;

import static org.junit.Assert.*;

public class LoginTest{
    private LoginController controller;

    @Before
    public void setUp() {
        controller = new LoginController();
    }

    @Test
    public void testLoginWithCorrectCredentials() {
        String username = "Bob";
        String password = "123";

        controller.txfUsername.setText(username);
        controller.pwfPassword.setText(password);

        controller.login();

        assertEquals(username, LoginController.getUsername());

    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        String username = "Bobby";
        String password = "1234";

        controller.txfUsername.setText(username);
        controller.pwfPassword.setText(password);

        controller.login();

        assertNull(LoginController.getUsername());
    }
}
*/
