class OrderItem {
    MenuItem item;
    int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }

    public String toString() {
        return item.toString() + " x " + quantity + " = Rp" + getSubtotal();
    }
}
