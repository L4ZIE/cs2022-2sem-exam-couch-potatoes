package be;

public class Devices {
    private int id;
    private String deviceName;
    private String uName;
    private String uPassword;
    private String refNumber;

    public Devices(int id, String deviceName, String uName, String uPassword, String refNumber) {
        this.id = id;
        this.deviceName = deviceName;
        this.uName = uName;
        this.uPassword = uPassword;
        this.refNumber = refNumber;
    }

    public int getId() {
        return id;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }
    @Override
    public String toString() {
        return id + " " + deviceName + " " + uName + " " + uPassword + " " + refNumber;
}
}
