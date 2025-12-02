package APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        setTitle("Login");
        setSize(705, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menggunakan GridLayout 1 baris, 2 kolom untuk membagi jendela 50/50
        setLayout(new GridLayout(1, 2));
        add(createImagePanel());
        add(createLoginFormPanel());
    }
    
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 245, 255));

        String imagePath = "C:\\Informatika 23\\Semester 5\\IMPL\\Kasir_CoffeeShop\\src\\APP\\coffee.png";
        int desiredWidth = 350;  // Lebar gambar
        int desiredHeight = 400; // Tinggi gambar

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

    //Membuat panel kanan untuk form login
    private JPanel createLoginFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 20, 8);
        panel.add(titleLabel, gbc);
        gbc.insets = new Insets(8, 8, 8, 8);

        // Label Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Email:"), gbc);

        // Field Username
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(usernameField, gbc);

        // Label Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Password:"), gbc);

        // Field Password
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(passwordField, gbc);

        // Tombol Login
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(39, 174, 96));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Action button login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(15, 8, 0, 8);

        JLabel noAccountLabel = new JLabel("Tidak memiliki akun?");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        noAccountLabel.setForeground(Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(noAccountLabel, gbc);

        // Button sign up
        final Color signUpNormal = new Color(43, 146, 251);
        final Color signUpHover  = signUpNormal.darker();

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 12));
        signUpButton.setFocusPainted(false);
        signUpButton.setBackground(signUpNormal);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBorderPainted(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // hover
        signUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                signUpButton.setBackground(signUpHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                signUpButton.setBackground(signUpNormal);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(signUpButton, gbc);

        // Action button sign up
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SignUpView().setVisible(true);
                    }
                });
            }
        });

        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {

            JOptionPane.showMessageDialog(this,
                    "Login berhasil! Selamat datang, Admin.",
                    "Login Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();

            // Window kasir utama
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainCashierView().setVisible(true);
                }
            });

        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah.",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}