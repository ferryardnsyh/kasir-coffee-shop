package APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUpView extends JFrame {

    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    public SignUpView() {
        setTitle("Sign Up");
        setSize(705, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout utama 2 panel (kiri-kanan)
        setLayout(new GridLayout(1, 2));
        add(createImagePanel());
        add(createSignUpFormPanel());
    }

    // Panel kiri
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 245, 255));

        String imagePath = "C:\\Informatika 23\\Semester 5\\IMPL\\Kasir_CoffeeShop\\src\\APP\\coffee.png";
        int desiredWidth = 350;
        int desiredHeight = 400;

        JLabel imageLabel;

        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);

            if (originalIcon.getIconWidth() == -1) {
                throw new Exception("File gambar tidak ditemukan di: " + imagePath);
            }

            Image image = originalIcon.getImage();
            Image scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel = new JLabel(scaledIcon);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            imageLabel = new JLabel("[ GAGAL MEMUAT GAMBAR ]");
            imageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            imageLabel.setForeground(Color.RED);
        }

        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        panel.add(imageLabel);
        return panel;
    }

    // Panel kanan form sign up
    private JPanel createSignUpFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SIGN UP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 20, 8);
        panel.add(titleLabel, gbc);

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Full name:"), gbc);

        // Full name field
        fullNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Email:"), gbc);

        // Email field
        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Password:"), gbc);

        // Password field
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(passwordField, gbc);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(39, 174, 96));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 8, 15, 8);
        panel.add(signUpButton, gbc);

        // ActionListener Sign Up
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 8, 0, 8);

        JLabel haveAccountLabel = new JLabel("Saya memiliki akun");
        haveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        haveAccountLabel.setForeground(Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(haveAccountLabel, gbc);

        // LOGIN BUTTON
        final Color loginNormal = new Color(43, 146, 251);
        final Color loginHover  = loginNormal.darker();

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setFocusPainted(false);
        loginButton.setBackground(loginNormal);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(loginHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(loginNormal);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(loginButton, gbc);

        // ActionListener kembali ke LoginView
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new LoginView().setVisible(true);
                    }
                });
            }
        });
        return panel;
    }

    // Logic saat button Sign Up diklik
    private void handleSignUp() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field harus diisi.",
                    "Sign Up Gagal",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ganti dengan simpan ke database
        JOptionPane.showMessageDialog(this,
                "Sign up berhasil! Silakan login.",
                "Sign Up Sukses",
                JOptionPane.INFORMATION_MESSAGE);

        // Kembali ke LoginView
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpView().setVisible(true);
            }
        });
    }
}