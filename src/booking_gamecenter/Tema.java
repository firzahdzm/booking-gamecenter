package booking_gamecenter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class Tema {
    public static final Color BG          = new Color(0x0D0F13);
    public static final Color PERMUKAAN   = new Color(0x161A21);
    public static final Color PERMUKAAN2  = new Color(0x1B212B);
    public static final Color BORDER      = new Color(0x272D38);
    public static final Color CYAN        = new Color(0x22D3EE);
    public static final Color BIRU        = new Color(0x2D7DF6);
    public static final Color HIJAU       = new Color(0x22C55E);
    public static final Color MERAH       = new Color(0xEF4444);
    public static final Color TEKS        = new Color(0xE6E8EB);
    public static final Color TEKS_REDUP  = new Color(0x8B919B);
    public static final Color PUTIH       = new Color(0xF8FAFC);
    public static final Color SELEKSI     = new Color(0x21303B);

    private Tema() {
    }

    public static void pasang() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println("Gagal memasang Nimbus: " + ex.getMessage());
        }

        UIManager.put("control", BG);
        UIManager.put("background", BG);
        UIManager.put("nimbusBase", new Color(0x10141A));
        UIManager.put("nimbusBlueGrey", PERMUKAAN2);
        UIManager.put("nimbusLightBackground", PERMUKAAN);
        UIManager.put("nimbusBorder", BORDER);
        UIManager.put("nimbusFocus", CYAN);
        UIManager.put("nimbusSelectionBackground", SELEKSI);
        UIManager.put("nimbusSelection", SELEKSI);
        UIManager.put("nimbusSelectedText", TEKS);
        UIManager.put("nimbusDisabledText", TEKS_REDUP);
        UIManager.put("info", PERMUKAAN);
        UIManager.put("text", TEKS);
        UIManager.put("controlText", TEKS);
        UIManager.put("menuText", TEKS);
        UIManager.put("infoText", TEKS);
        UIManager.put("textForeground", TEKS);

        UIManager.put("defaultFont", new Font("SansSerif", Font.PLAIN, 13));
    }

    public static void latar(Component c) {
        c.setBackground(BG);
    }

    public static void judul(JLabel l, float ukuran) {
        l.setFont(l.getFont().deriveFont(Font.BOLD, ukuran));
        l.setForeground(CYAN);
    }

    public static void redup(JLabel l) {
        l.setForeground(TEKS_REDUP);
    }

    public static void terang(JLabel l) {
        l.setForeground(TEKS);
    }

    public static void aksenTeks(JLabel l) {
        l.setForeground(CYAN);
    }

    private static void tombol(AbstractButton b, Color isi, Color teks) {
        b.setBackground(isi);
        b.setForeground(teks);
        b.setFocusable(false);
        b.setMargin(new Insets(7, 18, 7, 18));
    }

    public static void tombolPrimer(AbstractButton b) {
        tombol(b, BIRU, PUTIH);
    }

    public static void tombolAksen(AbstractButton b) {
        tombol(b, CYAN, new Color(0x06232A));
    }

    public static void tombolBahaya(AbstractButton b) {
        tombol(b, MERAH, PUTIH);
    }

    public static void tombolNetral(AbstractButton b) {
        tombol(b, PERMUKAAN2, TEKS);
    }

    public static void gayaTabel(JTable t) {
        t.setBackground(PERMUKAAN);
        t.setForeground(TEKS);
        t.setGridColor(BORDER);
        t.setRowHeight(28);
        t.setSelectionBackground(SELEKSI);
        t.setSelectionForeground(CYAN);
        t.setShowVerticalLines(false);
        t.setFillsViewportHeight(true);

        JTableHeader header = t.getTableHeader();
        header.setBackground(PERMUKAAN2);
        header.setForeground(TEKS_REDUP);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 12f));
        header.setReorderingAllowed(false);
    }

    public static void warnaiKolomStatus(JTable t, int kolom) {
        if (kolom < t.getColumnModel().getColumnCount()) {
            t.getColumnModel().getColumn(kolom).setCellRenderer(new RendererStatus());
        }
    }

    private static final class RendererStatus extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable tabel, Object nilai,
                boolean terpilih, boolean fokus, int baris, int kolom) {
            super.getTableCellRendererComponent(tabel, nilai, terpilih, fokus, baris, kolom);
            String teks = String.valueOf(nilai);
            setFont(getFont().deriveFont(Font.BOLD));
            setHorizontalAlignment(CENTER);
            if (!terpilih) {
                if ("AKTIF".equals(teks)) {
                    setForeground(HIJAU);
                } else if ("DIBATALKAN".equals(teks)) {
                    setForeground(MERAH);
                } else {
                    setForeground(TEKS);
                }
            }
            return this;
        }
    }
}
