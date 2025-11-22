package dummyUASLabPBOB_3;

public abstract class MenuItem {
    private String nama;
    private int harga; 

    // Konstruktor MenuItem
    public MenuItem(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    // Mengembalikan nama item
    public String getNama() { return nama; }
    // Mengembalikan harga item
    public int getHarga() { return harga; }

    // Metode abstrak. Wajib diimplementasikan oleh subclass untuk memberikan detail info item
    public abstract String getInfo();

    // Mengembalikan string yang berisi detail info item
    @Override
    public String toString() {
        return getInfo();
    }
}
