package dummyUASLabPBOB_3;

import java.awt.*;

public class EmployeeLoginPanel extends Panel {
    public EmployeeLoginPanel(AppFrame app) {
        setLayout(new GridBagLayout());
        setBackground(AppFrame.RED_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Label header = new Label("Login Pegawai", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.setForeground(AppFrame.GOLD);

        TextField tfId = new TextField(20);
        TextField tfPass = new TextField(20);
        tfPass.setEchoChar('*');

        Choice roleChoice = new Choice();
        roleChoice.add("Pelayan");
        roleChoice.add("Koki");
        roleChoice.add("Kasir");

        Button btnLogin = new Button("Login");
        Button btnBack = new Button("Kembali");
        style(btnLogin); style(btnBack);

        Label msg = new Label("", Label.CENTER);
        msg.setForeground(Color.yellow);

        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                String pass = tfPass.getText().trim();
                String peranDipilih = roleChoice.getSelectedItem();

                Akun akun = app.getSystem().login(id, pass);
                if (!(akun instanceof Pegawai)) {
                    msg.setText("Login gagal. Bukan akun pegawai.");
                    return;
                }

                Pegawai p = (Pegawai) akun;
                if (!p.getPeran().equalsIgnoreCase(peranDipilih)) {
                    msg.setText("Peran tidak sesuai.");
                    return;
                }

                app.setCurrentPegawai(p);

                if (peranDipilih.equalsIgnoreCase("Pelayan")) app.showPage("WAITER");
                else if (peranDipilih.equalsIgnoreCase("Koki")) app.showPage("CHEF");
                else app.showPage("CASHIER");

            } catch (NumberFormatException ex){
                msg.setText("ID harus angka.");
            } catch (Exception ex){
                msg.setText("Error: " + ex.getMessage());
            }
        });

        btnBack.addActionListener(e -> app.showPage("HOME"));

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        add(header, gbc);

        gbc.gridwidth=1; gbc.gridy++;
        add(label("ID Pegawai:"), gbc);
        gbc.gridx=1; add(tfId, gbc);

        gbc.gridx=0; gbc.gridy++;
        add(label("Password:"), gbc);
        gbc.gridx=1; add(tfPass, gbc);

        gbc.gridx=0; gbc.gridy++;
        add(label("Peran:"), gbc);
        gbc.gridx=1; add(roleChoice, gbc);

        gbc.gridx=0; gbc.gridy++; gbc.gridwidth=2;
        add(btnLogin, gbc);
        gbc.gridy++; add(btnBack, gbc);
        gbc.gridy++; add(msg, gbc);
    }

    private Label label(String t){
        Label l = new Label(t);
        l.setForeground(AppFrame.WHITE);
        l.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return l;
    }
    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
    }
}
