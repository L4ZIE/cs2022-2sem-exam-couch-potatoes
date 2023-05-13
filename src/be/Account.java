package be;

public class Account {
    private int id;
    private String name;
    private String password;
    private AccountType type;

    public Account(int id, String name, String password, AccountType type){
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;

    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public AccountType getType() {
        return type;
    }
}
