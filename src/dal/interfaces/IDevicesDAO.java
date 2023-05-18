package dal.interfaces;

import be.Devices;

public interface IDevicesDAO {
    void createDevice(Devices devices);
    int  getMaxIdForDevice();
}
