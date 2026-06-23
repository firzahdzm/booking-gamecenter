package booking_gamecenter;

import javax.swing.JOptionPane;

public class RegisterFrame extends javax.swing.JFrame {
    public RegisterFrame() {
        initComponents();
        gayaUi();
    }

    private void gayaUi() {
        Tema.latar(getContentPane());
        Tema.judul(lblJudul, 20f);
        Tema.terang(lblNama);
        Tema.terang(lblUsername);
        Tema.terang(lblPassword);
        Tema.terang(lblKonfirmasi);
        Tema.tombolPrimer(btnDaftar);
        Tema.tombolAksen(btnKembali);
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblJudul = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblKonfirmasi = new javax.swing.JLabel();
        txtKonfirmasi = new javax.swing.JPasswordField();
        btnDaftar = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameZone Center - Daftar Akun");
        setResizable(false);

        lblJudul.setText("Daftar Akun Baru");

        lblNama.setText("Nama Lengkap:");

        lblUsername.setText("Username:");

        lblPassword.setText("Password:");

        lblKonfirmasi.setText("Konfirmasi Password:");

        btnDaftar.setText("Daftar");
        btnDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali ke Login");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudul)
                    .addComponent(lblNama)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKonfirmasi)
                    .addComponent(txtKonfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKembali)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblJudul)
                .addGap(20, 20, 20)
                .addComponent(lblNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblKonfirmasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKonfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDaftar)
                    .addComponent(btnKembali))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {
        String nama = txtNama.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String konfirmasi = new String(txtKonfirmasi.getPassword());

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || konfirmasi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (DataStore.usernameDipakai(username)) {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan, pilih yang lain.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password minimal 4 karakter.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(konfirmasi)) {
            JOptionPane.showMessageDialog(this, "Konfirmasi password tidak sama.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DataStore.daftarMember(nama, username, password);
        JOptionPane.showMessageDialog(this, "Akun berhasil dibuat, silakan login.",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        new LoginFrame().setVisible(true);
        dispose();
    }

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginFrame().setVisible(true);
        dispose();
    }

    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnKembali;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKonfirmasi;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtKonfirmasi;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;

}
