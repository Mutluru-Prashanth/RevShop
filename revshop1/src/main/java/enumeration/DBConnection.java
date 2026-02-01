package enumeration;


public enum DBConnection {

    URL("jdbc:oracle:thin:@localhost:1521/XEPDB1"),USERNAME("revshop1"),PASSWORD("revshop1");
    private final String value;
    private DBConnection(String value)
    {
        this.value=value;
    }
    public String getValue() {
        return value;
    }

}
