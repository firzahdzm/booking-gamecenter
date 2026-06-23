package booking_gamecenter;

import javax.swing.table.DefaultTableModel;

public class AdminFrame extends javax.swing.JFrame {
    public AdminFrame() {
        initComponents();
        refreshTabel();
        gayaUi();
    }

    private void gayaUi() {
        Tema.latar(getContentPane());
        Tema.judul(lblJudul, 18f);
        Tema.redup(lblInfo);
        Tema.tombolNetral(btnLogout);
        Tema.tombolPrimer(btnRefresh);
        Tema.gayaTabel(tblSemua);
        scrSemua.getViewport().setBackground(Tema.PERMUKAAN);
        scrSemua.setBorder(javax.swing.BorderFactory.createLineBorder(Tema.BORDER));
        pack();
        setLocationRelativeTo(null);
    }

    private void refreshTabel() {
        String[] kolom = {"ID", "Member", "Station", "Console", "Tanggal", "Jam", "Durasi", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int baris, int kolomKe) {
                return false;
            }
        };
        for (Booking b : DataStore.semuaBooking()) {
            model.addRow(new Object[]{
                b.getId(),
                b.getPemesan().getNama(),
                b.getStation().getKode(),
                b.getStation().getKonsol().getNama(),
                b.getTanggal().format(DataStore.FORMAT_TANGGAL),
                b.getJamMulai() + ":00-" + b.getJamSelesai() + ":00",
                b.getDurasiJam() + " jam",
                DataStore.formatRupiah(b.getTotalBiaya()),
                b.getStatus().toString()
            });
        }
        tblSemua.setModel(model);
        Tema.warnaiKolomStatus(tblSemua, 8);
        lblInfo.setText("Total booking: " + DataStore.semuaBooking().size()
                + " | Aktif: " + DataStore.jumlahBookingAktif());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblJudul = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        scrSemua = new javax.swing.JScrollPane();
        tblSemua = new javax.swing.JTable();
        lblInfo = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameZone Center - Admin");

        lblJudul.setText("Semua Booking");

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        scrSemua.setViewportView(tblSemua);

        lblInfo.setText("Total booking: 0 | Aktif: 0");

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrSemua, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblJudul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJudul)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrSemua, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInfo)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginFrame().setVisible(true);
        dispose();
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        refreshTabel();
    }

    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JScrollPane scrSemua;
    private javax.swing.JTable tblSemua;
}
