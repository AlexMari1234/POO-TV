package datainput;

public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials() {

    }

    public Credentials(final String name, final String password, final String accountType,
                       final String country, final String balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    /**
     * return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * return account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * set account type
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * set country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * return balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * set balance
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }
}
