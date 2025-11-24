package dummyUASLabPBOB_3;

import java.awt.*;

public class PaymentInputDialog {

    // Cash: minta input uang tunai
    // Card/QRIS: cukup OK
    // Batal: return null
    public static String askInput(Frame parent, String metode, int total) {
        Dialog dlg = new Dialog(parent, "Pembayaran - " + metode, true);
        dlg.setSize(420, 260);
        dlg.setLayout(new BorderLayout());
        dlg.setBackground(AppFrame.RED_DARK);

        Label info = new Label("", Label.CENTER);
        info.setForeground(AppFrame.GOLD);
        info.setFont(new Font("SansSerif", Font.BOLD, 14));

        TextField tfCash = new TextField(20);
        Panel center = new Panel(new FlowLayout());
        center.setBackground(AppFrame.RED_DARK);

        if (metode.equalsIgnoreCase("Cash")) {
            info.setText("Total tagihan: Rp " + total + ". Masukkan uang tunai:");
            center.add(tfCash);
        } else if (metode.equalsIgnoreCase("Card")) {
            info.setText("Pembayaran Card Rp " + total + ". Klik OK untuk lanjut.");
        } else {
            info.setText("Pembayaran QRIS Rp " + total + ". Klik OK setelah scan.");
        }

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);

        Button ok = new Button("OK");
        Button cancel = new Button("Batal");
        ok.setBackground(AppFrame.RED);
        ok.setForeground(AppFrame.WHITE);
        cancel.setBackground(AppFrame.RED);
        cancel.setForeground(AppFrame.WHITE);

        final String[] result = new String[1];

        ok.addActionListener(e -> {
            if (metode.equalsIgnoreCase("Cash")) {
                String val = tfCash.getText().trim();
                if (val.isEmpty()) val = "0";
                result[0] = val + "\n";
            } else {
                result[0] = "0\n"; // dummy utk Scanner
            }
            dlg.setVisible(false);
            dlg.dispose();
        });

        cancel.addActionListener(e -> {
            result[0] = null;
            dlg.setVisible(false);
            dlg.dispose();
        });

        bottom.add(ok); bottom.add(cancel);

        dlg.add(info, BorderLayout.NORTH);
        dlg.add(center, BorderLayout.CENTER);
        dlg.add(bottom, BorderLayout.SOUTH);

        dlg.setLocationRelativeTo(parent);
        dlg.setVisible(true);
        return result[0];
    }
}
