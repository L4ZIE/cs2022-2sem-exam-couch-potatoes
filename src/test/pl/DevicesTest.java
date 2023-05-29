package test.pl;

import be.Devices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DevicesTest {

    @Test
    public void testDevices() {
        Devices devices = new Devices(1, "Device 1", "username", "password", "REF001");

        Assertions.assertEquals(1, devices.getId());
        Assertions.assertEquals("Device 1", devices.getDeviceName());
        Assertions.assertEquals("username", devices.getUName());
        Assertions.assertEquals("password", devices.getUPassword());
        Assertions.assertEquals("REF001", devices.getRefNumber());

        devices.setDeviceName("Device 2");
        devices.setUName("newusername");
        devices.setUPassword("newpassword");
        devices.setRefNumber("REF002");

        Assertions.assertEquals("Device 2", devices.getDeviceName());
        Assertions.assertEquals("newusername", devices.getUName());
        Assertions.assertEquals("newpassword", devices.getUPassword());
        Assertions.assertEquals("REF002", devices.getRefNumber());
    }

    @Test
    public void testToString() {
        Devices devices = new Devices(1, "Device 1", "username", "password", "REF001");

        Assertions.assertEquals("1 Device 1 username password REF001", devices.toString());
    }

    @Test
    public void testSettersAndGetters() {
        Devices devices = new Devices(1, "Device 1", "username", "password", "REF001");


        devices.setDeviceName("Device 3");
        devices.setUName("newusername2");
        devices.setUPassword("newerpassword");
        devices.setRefNumber("REF002");

        Assertions.assertEquals("Device 3", devices.getDeviceName());
        Assertions.assertEquals("newusername2", devices.getUName());
        Assertions.assertEquals("newerpassword", devices.getUPassword());
        Assertions.assertEquals("REF002", devices.getRefNumber());
    }
}
