class Customer extends User {
    private String name;

    public Customer(String username, String password) {
        super(username, password, "Customer");
        this.name = username;
    }

    public String getName() {
        return name;
    }
}
