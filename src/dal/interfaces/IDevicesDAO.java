package dal.interfaces;

import be.Devices;

import java.util.List;

public interface IDevicesDAO {
    void createDevice(Devices devices);
    void removeDevice(int id);
    int  getMaxIdForDevice();
    List<Devices> getAllDevices();
}
