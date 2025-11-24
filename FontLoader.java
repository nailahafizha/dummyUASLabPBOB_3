package dummyUASLabPBOB_3;

import java.awt.Font;
import java.io.File;

public class FontLoader {

    public static Font loadNotoSerifSC(float size, int style) {
        try {
            File f = new File("NotoSerifSC-Regular.ttf");
            if (!f.exists()) return new Font("Serif", style, (int) size);

            Font base = Font.createFont(Font.TRUETYPE_FONT, f);
            return base.deriveFont(style, size);
        } catch (Exception e) {
            return new Font("Serif", style, (int) size);
        }
    }
}
