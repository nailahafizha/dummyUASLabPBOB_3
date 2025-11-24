package dummyUASLabPBOB_3;

import java.awt.*;

public class PatternCanvas extends Panel {

    public PatternCanvas() {
        setBackground(AppFrame.RED_DARK);
        setLayout(new GridBagLayout()); // sekarang boleh
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int w = getWidth();
        int h = getHeight();

        g.setColor(new Color(218,165,32,80)); // GOLD translucent

        // kiri atas motif lingkaran
        drawRing(g, 80, 80, 140);
        drawRing(g, 80, 80, 90);

        // kanan bawah motif lingkaran
        drawRing(g, w-140, h-140, 140);
        drawRing(g, w-140, h-140, 90);

        // emblem tengah kecil
        g.setColor(new Color(218,165,32,160));
        g.drawOval(w/2 - 50, 60, 100, 100);
        g.setFont(FontLoader.loadNotoSerifSC(40, Font.BOLD));
        g.drawString("\u798F", w/2 - 12, 125); // Fu character
    }

    private void drawRing(Graphics g, int cx, int cy, int r){
        g.drawOval(cx - r/2, cy - r/2, r, r);
    }
}
