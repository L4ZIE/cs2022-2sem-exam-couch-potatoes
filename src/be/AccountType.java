package be;

public enum AccountType {
    CEO(0),
    TECHNICIAN(1),
    PROJECTMANAGER(2),
    SALESPERSON(3);

    private int id;
    private final int value;
    private AccountType(int value) {
        this.value = value;
        this.id = id;
    }

    public int getValue() {
        return value;
    }
    public static AccountType fromId(int id) {
        for (AccountType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
    private int getId() {
        return id;
    }
}
