package booking_gamecenter;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserFrame extends javax.swing.JFrame {

    private final Member member;
    private final ArrayList<LocalDate> listTanggal = DataStore.pilihanTanggal();

    public UserFrame(Member member) {
        this.member = member;
        initComponents();
        lblSapaan.setFont(lblSapaan.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        lblTotal.setFont(lblTotal.getFont().deriveFont(java.awt.Font.BOLD));
        lblSapaan.setText("Halo, " + member.getNama());
        muatPilihan();
        perbaruiInfoKonsol();
        perbaruiJamTersedia();
        refreshTabel();
    }

    private void muatPilihan() {
        for (Konsol k : DataStore.daftarJenisKonsol()) {
            cmbKonsol.addItem(k);
        }
        for (LocalDate t : listTanggal) {
            cmbTanggal.addItem(t.format(DataStore.FORMAT_TANGGAL));
        }
    }

    private void perbaruiInfoKonsol() {
        Konsol konsol = (Konsol) cmbKonsol.getSelectedItem();
        if (konsol == null) {
            return;
        }
        lblHarga.setText(DataStore.formatRupiah(konsol.getHargaPerJam()) + " / jam");
        lblDeskripsi.setText(konsol.deskripsi());
        cmbStation.removeAllItems();
        for (Station s : DataStore.stationDenganKonsol(konsol)) {
            cmbStation.addItem(s);
        }
        perbaruiTotal();
    }

    private void perbaruiTotal() {
        Station station = (Station) cmbStation.getSelectedItem();
        if (station == null) {
            lblTotal.setText("Total: -");
            return;
        }
        int durasi = (Integer) spnDurasi.getValue();
        lblTotal.setText("Total: " + DataStore.formatRupiah(station.hitungBiaya(durasi)));
    }

    private void perbaruiJamTersedia() {
        Station station = (Station) cmbStation.getSelectedItem();
        if (station == null || cmbTanggal.getSelectedIndex() == -1) {
            return;
        }
        LocalDate tanggal = listTanggal.get(cmbTanggal.getSelectedIndex());
        int durasi = (Integer) spnDurasi.getValue();
        int pilihanSebelumnya = cmbJam.getSelectedIndex();
        cmbJam.removeAllItems();
        for (int jam = DataStore.JAM_BUKA; jam < DataStore.JAM_TUTUP; jam++) {
            boolean bisa = jam + durasi <= DataStore.JAM_TUTUP
                    && DataStore.jamTersedia(station, tanggal, jam, durasi);
            cmbJam.addItem(jam + ":00 - " + (bisa ? "Tersedia" : "Tidak Tersedia"));
        }
        if (pilihanSebelumnya >= 0) {
            cmbJam.setSelectedIndex(pilihanSebelumnya);
        }
    }

    private void refreshTabel() {
        String[] kolom = {"ID", "Station", "Console", "Tanggal", "Jam", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int baris, int kolomKe) {
                return false;
            }
        };
        for (Booking b : DataStore.bookingMilik(member)) {
            model.addRow(new Object[]{
                b.getId(),
                b.getStation().getKode(),
                b.getStation().getKonsol().getNama(),
                b.getTanggal().format(DataStore.FORMAT_TANGGAL),
                b.getJamMulai() + ":00-" + b.getJamSelesai() + ":00",
                DataStore.formatRupiah(b.getTotalBiaya()),
                b.getStatus().toString()
            });
        }
        tblBooking.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSapaan = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        tabPanel = new javax.swing.JTabbedPane();
        panelBooking = new javax.swing.JPanel();
        lblKonsol = new javax.swing.JLabel();
        cmbKonsol = new javax.swing.JComboBox<>();
        lblHarga = new javax.swing.JLabel();
        lblDeskripsi = new javax.swing.JLabel();
        lblStation = new javax.swing.JLabel();
        cmbStation = new javax.swing.JComboBox<>();
        lblTanggal = new javax.swing.JLabel();
        cmbTanggal = new javax.swing.JComboBox<>();
        lblJam = new javax.swing.JLabel();
        cmbJam = new javax.swing.JComboBox<>();
        lblDurasi = new javax.swing.JLabel();
        spnDurasi = new javax.swing.JSpinner();
        lblTotal = new javax.swing.JLabel();
        btnBooking = new javax.swing.JButton();
        panelRiwayat = new javax.swing.JPanel();
        scrBooking = new javax.swing.JScrollPane();
        tblBooking = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameZone Center - Booking");

        lblSapaan.setText("Halo");

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblKonsol.setText("Console:");

        cmbKonsol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKonsolActionPerformed(evt);
            }
        });

        lblHarga.setText("-");

        lblDeskripsi.setText("-");

        lblStation.setText("Station:");

        cmbStation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStationActionPerformed(evt);
            }
        });

        lblTanggal.setText("Tanggal:");

        cmbTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTanggalActionPerformed(evt);
            }
        });

        lblJam.setText("Jam Mulai:");

        lblDurasi.setText("Durasi (jam):");

        spnDurasi.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));
        spnDurasi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnDurasiStateChanged(evt);
            }
        });

        lblTotal.setText("Total: -");

        btnBooking.setText("Booking Sekarang");
        btnBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBookingLayout = new javax.swing.GroupLayout(panelBooking);
        panelBooking.setLayout(panelBookingLayout);
        panelBookingLayout.setHorizontalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBookingLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDeskripsi)
                    .addComponent(lblTotal)
                    .addComponent(btnBooking)
                    .addGroup(panelBookingLayout.createSequentialGroup()
                        .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKonsol)
                            .addComponent(lblStation)
                            .addComponent(lblTanggal)
                            .addComponent(lblJam)
                            .addComponent(lblDurasi))
                        .addGap(18, 18, 18)
                        .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBookingLayout.createSequentialGroup()
                                .addComponent(cmbKonsol, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHarga))
                            .addComponent(cmbStation, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbJam, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnDurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelBookingLayout.setVerticalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBookingLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKonsol)
                    .addComponent(cmbKonsol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDeskripsi)
                .addGap(18, 18, 18)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStation)
                    .addComponent(cmbStation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTanggal)
                    .addComponent(cmbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJam)
                    .addComponent(cmbJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDurasi)
                    .addComponent(spnDurasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblTotal)
                .addGap(12, 12, 12)
                .addComponent(btnBooking)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabPanel.addTab("Booking Baru", panelBooking);

        scrBooking.setViewportView(tblBooking);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnBatal.setText("Batalkan Booking");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRiwayatLayout = new javax.swing.GroupLayout(panelRiwayat);
        panelRiwayat.setLayout(panelRiwayatLayout);
        panelRiwayatLayout.setHorizontalGroup(
            panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrBooking, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addGroup(panelRiwayatLayout.createSequentialGroup()
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelRiwayatLayout.setVerticalGroup(
            panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrBooking, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        tabPanel.addTab("Booking Saya", panelRiwayat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSapaan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSapaan)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        new LoginFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void cmbKonsolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKonsolActionPerformed
        perbaruiInfoKonsol();
    }//GEN-LAST:event_cmbKonsolActionPerformed

    private void cmbStationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStationActionPerformed
        perbaruiTotal();
        perbaruiJamTersedia();
    }//GEN-LAST:event_cmbStationActionPerformed

    private void cmbTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTanggalActionPerformed
        perbaruiJamTersedia();
    }//GEN-LAST:event_cmbTanggalActionPerformed

    private void spnDurasiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnDurasiStateChanged
        perbaruiTotal();
        perbaruiJamTersedia();
    }//GEN-LAST:event_spnDurasiStateChanged

    private void btnBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingActionPerformed
        Station station = (Station) cmbStation.getSelectedItem();
        if (station == null) {
            JOptionPane.showMessageDialog(this, "Pilih station terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        LocalDate tanggal = listTanggal.get(cmbTanggal.getSelectedIndex());
        int jamMulai = DataStore.JAM_BUKA + cmbJam.getSelectedIndex();
        int durasi = (Integer) spnDurasi.getValue();

        if (jamMulai + durasi > DataStore.JAM_TUTUP) {
            JOptionPane.showMessageDialog(this,
                    "Sesi melewati jam tutup (24:00). Kurangi durasi atau majukan jam mulai.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rincian = "Console : " + station.getKonsol().getNama() + "\n"
                + "Station : " + station.getKode() + "\n"
                + "Tanggal : " + tanggal.format(DataStore.FORMAT_TANGGAL) + "\n"
                + "Jam     : " + jamMulai + ":00 - " + (jamMulai + durasi) + ":00\n"
                + "Biaya   : " + durasi + " jam x "
                + DataStore.formatRupiah(station.getKonsol().getHargaPerJam()) + "\n"
                + "Total   : " + DataStore.formatRupiah(station.hitungBiaya(durasi));

        int pilihan = JOptionPane.showConfirmDialog(this, rincian + "\n\nLanjutkan booking?",
                "Konfirmasi Booking", JOptionPane.YES_NO_OPTION);
        if (pilihan != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Booking booking = DataStore.buatBooking(member, station, tanggal, jamMulai, durasi);
            JOptionPane.showMessageDialog(this,
                    "BOOKING BERHASIL!\n\nNomor Booking: #" + booking.getId() + "\n\n" + rincian
                    + "\n\nTunjukkan struk ini ke kasir saat datang.",
                    "Struk Booking", JOptionPane.INFORMATION_MESSAGE);
            refreshTabel();
            perbaruiJamTersedia();
        } catch (BentrokException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Jadwal Bentrok", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBookingActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshTabel();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        int baris = tblBooking.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih booking yang mau dibatalkan.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (Integer) tblBooking.getValueAt(baris, 0);
        Booking dipilih = null;
        for (Booking b : DataStore.bookingMilik(member)) {
            if (b.getId() == id) {
                dipilih = b;
                break;
            }
        }
        if (dipilih == null) {
            return;
        }
        if (dipilih.getStatus() != StatusBooking.AKTIF) {
            JOptionPane.showMessageDialog(this, "Booking ini sudah dibatalkan.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Batalkan Booking #" + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            DataStore.batalkanBooking(dipilih);
            refreshTabel();
            perbaruiJamTersedia();
        }
    }//GEN-LAST:event_btnBatalActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBooking;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cmbJam;
    private javax.swing.JComboBox<Konsol> cmbKonsol;
    private javax.swing.JComboBox<Station> cmbStation;
    private javax.swing.JComboBox<String> cmbTanggal;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblDurasi;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblKonsol;
    private javax.swing.JLabel lblSapaan;
    private javax.swing.JLabel lblStation;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelBooking;
    private javax.swing.JPanel panelRiwayat;
    private javax.swing.JScrollPane scrBooking;
    private javax.swing.JSpinner spnDurasi;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JTable tblBooking;
    // End of variables declaration//GEN-END:variables
}
