package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.List;

public class ChefPanel extends Panel {
    private java.awt.List cookList;
    private TextArea detailArea;

    public ChefPanel(AppFrame app){
        setLayout(new BorderLayout());
        setBackground(AppFrame.RED_DARK);

        Label header = new Label("Dashboard Koki", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(AppFrame.GOLD);
        add(header, BorderLayout.NORTH);

        cookList = new java.awt.List();
        detailArea = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        detailArea.setEditable(false);

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(cookList);
        center.add(detailArea);
        add(center, BorderLayout.CENTER);

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);

        Button btnDone = new Button("Tandai Selesai Dimasak");
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        style(btnDone); style(btnRefresh); style(btnLogout);

        bottom.add(btnDone); bottom.add(btnRefresh); bottom.add(btnLogout);
        add(bottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> load(app));
        btnLogout.addActionListener(e -> app.showPage("HOME"));
        cookList.addItemListener(e -> showDetail(app));

        btnDone.addActionListener(e -> {
            int idx = cookList.getSelectedIndex();
            if (idx < 0) return;
            Pesanan p = getList(app).get(idx);
            p.setStatus("Selesai Dimasak");
            load(app);
        });

        load(app);
    }

    private List<Pesanan> getList(AppFrame app){
        return app.getSystem().getDaftarPesananByStatus("Dipesan");
    }

    private void load(AppFrame app){
        cookList.removeAll();
        for (Pesanan p : getList(app)){
            cookList.add("ID#" + p.getIdPesanan()
                    + " | Meja " + p.getMeja().getNomor());
        }
        detailArea.setText("Pilih pesanan untuk detail.");
    }

    private void showDetail(AppFrame app){
        int idx = cookList.getSelectedIndex();
        if (idx < 0) return;
        Pesanan p = getList(app).get(idx);

        StringBuilder sb = new StringBuilder();
        sb.append("Pesanan ID: ").append(p.getIdPesanan()).append("\n");
        sb.append("Status: ").append(p.getStatus()).append("\n\n");
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append("- ").append(d.getItem().getNama())
              .append(" x").append(d.getJumlah()).append("\n");
        }
        detailArea.setText(sb.toString());
    }

    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
}
