package dummyUASLabPBOB_3;

import java.awt.*;

public class CustomerLoginRegisterPanel extends Panel {
    public CustomerLoginRegisterPanel(AppFrame app) {
        setLayout(new GridBagLayout());
        setBackground(AppFrame.RED_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Label header = new Label("Customer Access", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.setForeground(AppFrame.GOLD);

        TextField tfId = new TextField(20);
        TextField tfNama = new TextField(20);
        TextField tfPass = new TextField(20);
        tfPass.setEchoChar('*');

        Button btnLogin = new Button("Login");
        Button btnRegister = new Button("Daftar");
        Button btnBack = new Button("Kembali");
        style(btnLogin); style(btnRegister); style(btnBack);

        Label msg = new Label("", Label.CENTER);
        msg.setForeground(Color.yellow);

        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                Akun akun = app.getSystem().login(id, tfPass.getText().trim());
                if (!(akun instanceof Customer)) {
                    msg.setText("Login gagal. Bukan akun customer.");
                    return;
                }
                app.setCurrentCustomer((Customer) akun);
                app.showPage("CUSTOMER");
            } catch (Exception ex){
                msg.setText("Error: " + ex.getMessage());
            }
        });

        btnRegister.addActionListener(e -> {
            try {
                Customer c = app.getSystem().registerCustomer(
                        tfNama.getText().trim(),
                        tfPass.getText().trim()
                );
                msg.setText("Daftar berhasil. ID kamu: " + c.getId());
            } catch (Exception ex){
                msg.setText("Error: " + ex.getMessage());
            }
        });

        btnBack.addActionListener(e -> app.showPage("HOME"));

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        add(header, gbc);

        gbc.gridwidth=1; gbc.gridy++;
        add(label("ID (untuk login):"), gbc);
        gbc.gridx=1; add(tfId, gbc);

        gbc.gridx=0; gbc.gridy++;
        add(label("Nama (untuk daftar):"), gbc);
        gbc.gridx=1; add(tfNama, gbc);

        gbc.gridx=0; gbc.gridy++;
        add(label("Password:"), gbc);
        gbc.gridx=1; add(tfPass, gbc);

        gbc.gridx=0; gbc.gridy++; gbc.gridwidth=2;
        add(btnLogin, gbc);
        gbc.gridy++; add(btnRegister, gbc);
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
