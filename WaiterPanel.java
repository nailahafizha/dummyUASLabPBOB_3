package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaiterPanel extends Panel {
    private java.awt.List pesananList;
    private TextArea detailArea;

    public WaiterPanel(AppFrame app) {
        setLayout(new BorderLayout());
        setBackground(AppFrame.RED_DARK);

        Label header = new Label("Dashboard Pelayan", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(AppFrame.GOLD);
        add(header, BorderLayout.NORTH);

        pesananList = new java.awt.List();
        detailArea = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        detailArea.setEditable(false);

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(pesananList);
        center.add(detailArea);
        add(center, BorderLayout.CENTER);

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);
        Button btnNew = new Button("Pesanan Baru");
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        style(btnNew); style(btnRefresh); style(btnLogout);
        bottom.add(btnNew); bottom.add(btnRefresh); bottom.add(btnLogout);
        add(bottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadPesanan(app));
        btnLogout.addActionListener(e -> app.showPage("HOME"));
        btnNew.addActionListener(e -> openCreateOrderDialog(app));

        pesananList.addItemListener(e -> showPesananDetail(app));

        loadPesanan(app);
    }

    private void loadPesanan(AppFrame app){
        pesananList.removeAll();
        List<Pesanan> list = app.getSystem().getDaftarPesanan();
        for (Pesanan p : list){
            pesananList.add("ID#" + p.getIdPesanan()
                    + " | Meja " + p.getMeja().getNomor()
                    + " | " + p.getStatus());
        }
        detailArea.setText("Klik pesanan untuk detail.");
    }

    private void showPesananDetail(AppFrame app){
        int idx = pesananList.getSelectedIndex();
        if (idx < 0) return;
        Pesanan p = app.getSystem().getDaftarPesanan().get(idx);

        StringBuilder sb = new StringBuilder();
        sb.append("Pesanan ID: ").append(p.getIdPesanan()).append("\n");
        sb.append("Meja: ").append(p.getMeja().getNomor()).append("\n");
        sb.append("Status: ").append(p.getStatus()).append("\n\n");
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append("- ").append(d.getItem().getNama())
              .append(" x").append(d.getJumlah())
              .append(" (").append(d.getCatatan()).append(")\n");
        }
        sb.append("\nTOTAL: Rp ").append(p.hitungTotal());
        detailArea.setText(sb.toString());
    }

    private void openCreateOrderDialog(AppFrame app){
        Dialog dlg = new Dialog((Frame)app, "Buat Pesanan Baru", true);
        dlg.setSize(720, 520);
        dlg.setLayout(new BorderLayout());
        dlg.setBackground(AppFrame.RED_DARK);

        List<MenuItem> menus = app.getSystem().getDaftarMenu();

        java.awt.List menuList = new java.awt.List();
        for (MenuItem m : menus){
            menuList.add(m.getNama() + " - Rp" + m.getHarga());
        }

        java.awt.List draftList = new java.awt.List();
        TextField tfJumlah = new TextField("1", 5);
        TextField tfCatatan = new TextField("", 12);

        Panel top = new Panel(new FlowLayout());
        top.setBackground(AppFrame.RED_DARK);

        TextField tfMeja = new TextField("1", 5);
        TextField tfCustomerId = new TextField("101", 8);

        top.add(label("No Meja:")); top.add(tfMeja);
        top.add(label("ID Customer:")); top.add(tfCustomerId);

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(menuList);
        center.add(draftList);

        Panel controls = new Panel(new FlowLayout());
        controls.setBackground(AppFrame.RED_DARK);

        Button btnAdd = new Button("Tambah Item");
        Button btnSave = new Button("Simpan Pesanan");
        Button btnCancel = new Button("Batal");
        style(btnAdd); style(btnSave); style(btnCancel);

        controls.add(label("Jumlah:")); controls.add(tfJumlah);
        controls.add(label("Catatan:")); controls.add(tfCatatan);
        controls.add(btnAdd); controls.add(btnSave); controls.add(btnCancel);

        dlg.add(top, BorderLayout.NORTH);
        dlg.add(center, BorderLayout.CENTER);
        dlg.add(controls, BorderLayout.SOUTH);

        List<DetailPesanan> draft = new ArrayList<>();

        btnAdd.addActionListener(e -> {
            int idx = menuList.getSelectedIndex();
            if (idx < 0) return;

            MenuItem m = menus.get(idx);
            int jumlah = Integer.parseInt(tfJumlah.getText().trim());
            String cat = tfCatatan.getText().trim();

            DetailPesanan d = new DetailPesanan(m, jumlah, cat);
            draft.add(d);

            draftList.add(m.getNama() + " x" + jumlah + (cat.isEmpty()? "" : " ["+cat+"]"));
            tfJumlah.setText("1");
            tfCatatan.setText("");
        });

        btnSave.addActionListener(e -> {
            try {
                int nomorMeja = Integer.parseInt(tfMeja.getText().trim());
                int idCustomer = Integer.parseInt(tfCustomerId.getText().trim());

                Meja meja = new Meja(nomorMeja);

                Customer c = app.getSystem().findCustomerById(idCustomer);
                if (c == null) throw new RuntimeException("Customer tidak ditemukan.");

                int newId = app.getSystem().generateIdPesananBaru();
                Pesanan pesanan = c.buatPesanan(newId, meja);

                for (DetailPesanan d : draft) pesanan.tambahItem(d);
                app.getSystem().tambahPesanan(pesanan);

                dlg.setVisible(false); dlg.dispose();
                loadPesanan(app);

            } catch (Exception ex){
                System.out.println("Gagal simpan: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });

        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    private Label label(String t){
        Label l = new Label(t);
        l.setForeground(AppFrame.WHITE);
        return l;
    }
    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
}
