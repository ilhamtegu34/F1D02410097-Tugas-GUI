import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Biodata extends JFrame implements ActionListener {

    private JLabel lblJudul, lblNama, lblNim, lblProdi, lblStatus;
    private JTextField txtNama, txtNim;
    private JComboBox<String> cmbProdi;
    private JCheckBox chkSetuju;
    private JButton btnSimpan, btnReset, btnKeluar;
    private JTextArea areaHasil;

    public Biodata() {
        // Top-level Container pake JFrame
        super("Form Biodata Mahasiswa");
        setSize(520, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 

        setLayout(new BorderLayout(10, 10));

        // Intermediate Container pake FlowLayout
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelHeader.setBackground(new Color(30, 80, 160));
        lblJudul = new JLabel("FORM BIODATA MAHASISWA");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);
        panelHeader.add(lblJudul);

        // Intermediate Container pake GridLayout
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        lblNama = new JLabel("Nama Lengkap");
        txtNama = new JTextField();
        lblNim = new JLabel("NIM");
        txtNim = new JTextField();
        lblProdi = new JLabel("Program Studi");
        cmbProdi = new JComboBox<>(new String[]{
                "Teknik Informatika", "Sistem Informasi", "Ilmu Komputer", "Teknik Komputer"});
        chkSetuju = new JCheckBox("Data yang diisi sudah benar");

        panelForm.add(lblNama);
        panelForm.add(txtNama);
        panelForm.add(lblNim);
        panelForm.add(txtNim);
        panelForm.add(lblProdi);
        panelForm.add(cmbProdi);
        panelForm.add(new JLabel("Konfirmasi"));
        panelForm.add(chkSetuju);

        // Intermediate Container pake FlowLayout
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnKeluar = new JButton("Keluar");
        panelTombol.add(btnSimpan);
        panelTombol.add(btnReset);
        panelTombol.add(btnKeluar);

        areaHasil = new JTextArea(6, 20);
        areaHasil.setEditable(false);
        JScrollPane scrollHasil = new JScrollPane(areaHasil);
        scrollHasil.setBorder(BorderFactory.createTitledBorder("Hasil Input"));

        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.add(panelForm, BorderLayout.NORTH);
        panelTengah.add(panelTombol, BorderLayout.CENTER);
        panelTengah.add(scrollHasil, BorderLayout.SOUTH);

        lblStatus = new JLabel(" Siap");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());

        add(panelHeader, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        // Event Listener
        // ActionListener
        btnSimpan.addActionListener(this);
        btnReset.addActionListener(this);
        btnKeluar.addActionListener(this);

        // MouseAdapter
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton b = (JButton) e.getSource();
                b.setBackground(new Color(200, 225, 255));
                lblStatus.setText(" Klik untuk: " + b.getText());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton b = (JButton) e.getSource();
                b.setBackground(UIManager.getColor("Button.background"));
                lblStatus.setText(" Siap");
            }
        };
        btnSimpan.addMouseListener(hoverEffect);
        btnReset.addMouseListener(hoverEffect);
        btnKeluar.addMouseListener(hoverEffect);

        // KeyAdapter
        txtNim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetterOrDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        // WindowAdapter
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konfirmasiKeluar();
            }
        });
    }

    // Override method dari interface ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        Object sumber = e.getSource();

        if (sumber == btnSimpan) {
            String nama = txtNama.getText().trim();
            String nim = txtNim.getText().trim();

            if (nama.isEmpty() || nim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan NIM wajib diisi!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else if (!chkSetuju.isSelected()) {
                JOptionPane.showMessageDialog(this, "Centang konfirmasi terlebih dahulu.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                areaHasil.append("Nama : " + nama + "\n"
                        + "NIM  : " + nim + "\n"
                        + "Prodi: " + cmbProdi.getSelectedItem() + "\n"
                        + "-----------------------------\n");
                lblStatus.setText(" Data berhasil disimpan");
            }

        } else if (sumber == btnReset) {
            txtNama.setText("");
            txtNim.setText("");
            cmbProdi.setSelectedIndex(0);
            chkSetuju.setSelected(false);
            areaHasil.setText("");
            lblStatus.setText(" Form direset");

        } else if (sumber == btnKeluar) {
            konfirmasiKeluar();
        }
    }

    private void konfirmasiKeluar() {
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Yakin ingin keluar dari aplikasi?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Biodata().setVisible(true));
    }
}
