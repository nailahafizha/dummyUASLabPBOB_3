package dummyUASLabPBOB_3;

public abstract class MenuItem {
    private String nama;
    private int harga;
    private String deskripsi; 

    //constructer menuitem
    public MenuItem(String nama, int harga, String deskripsi) {
        this.nama = nama;
        this.harga = harga;
        this.deskripsi = (deskripsi == null || deskripsi.trim().isEmpty())
                ? "Menu spesial"
                : deskripsi.trim();
    }

    public MenuItem(String nama, int harga) {
        this(nama, harga, "Menu spesial");
    }

    public String getNama() { 
        return nama; 
    }
    public int getHarga() { 
        return harga; 
    }

    public String getDeskripsi() { 
        return deskripsi; 
    }

    public abstract String getInfo();

    @Override
    public String toString() {
        return getInfo();
    }
}
