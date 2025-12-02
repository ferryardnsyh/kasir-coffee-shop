package APP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainCashierView extends JFrame {

    // --- Data ---
    private final List<Product> menu = new ArrayList<>();
    private final Order currentOrder = new Order();

    // --- Komponen GUI ---
    private final DefaultTableModel cartTableModel;
    private final JTable cartTable;
    private final JLabel totalLabel;

    public MainCashierView() {
        setupMenu();

        // --- Pengaturan Dasar Jendela ---
        setTitle("Sistem Kasir Coffee Shop - [Standalone Mode]");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. Panel Menu (WEST) ---
        JPanel menuPanel = new JPanel(new GridLayout(menu.size(), 1, 5, 5));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu (Poin 2)"));
        for (Product product : menu) {
            JButton productButton = new JButton(product.toString());
            productButton.setFont(new Font("Arial", Font.PLAIN, 16));
            
            productButton.addActionListener(e -> addItemToCart(product));
            menuPanel.add(productButton);
        }
        add(menuPanel, BorderLayout.WEST);

        // --- 2. Panel Keranjang (CENTER) ---
        String[] columnNames = {"Nama Item", "Harga", "Jumlah", "Subtotal"};
        cartTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Keranjang (Poin 3)"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Panel Aksi (SOUTH) ---
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalLabel = new JLabel("Total: Rp 0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        southPanel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton removeItemButton = new JButton("Hapus Item Terpilih");
        removeItemButton.setBackground(Color.RED);
        removeItemButton.setForeground(Color.WHITE);
        
        removeItemButton.addActionListener(e -> handleRemoveItem());
        buttonPanel.add(removeItemButton);

        JButton checkoutButton = new JButton("Lanjut ke Pembayaran");
        checkoutButton.setBackground(Color.GREEN);
        checkoutButton.setForeground(Color.WHITE);
        
        checkoutButton.addActionListener(e -> handleCheckout());
        buttonPanel.add(checkoutButton);

        southPanel.add(buttonPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void setupMenu() {
        menu.add(new Product("K01", "Espresso", 18000));
        menu.add(new Product("K02", "Americano", 20000));
        menu.add(new Product("K03", "Cafe Latte", 25000));
        menu.add(new Product("M01", "Croissant", 22000));
        menu.add(new Product("M02", "Donat Coklat", 15000));
    }

    // --- Logika Inti (Aksi dari Tombol) ---

    private void addItemToCart(Product product) {
        OrderItem existingItem = currentOrder.findItemByProduct(product);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            existingItem = new OrderItem(product, 1);
            currentOrder.addItem(existingItem);
        }
        refreshCartTable();
    }

    private void handleRemoveItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item di keranjang yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentOrder.removeItem(selectedRow);
        refreshCartTable();
    }

    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        for (OrderItem item : currentOrder.getItems()) {
            Product p = item.getProduct();
            cartTableModel.addRow(new Object[]{
                p.name(), // Menggunakan getter dari record
                String.format("Rp %.0f", p.price()),
                item.getQuantity(),
                String.format("Rp %.0f", item.getSubtotal())
            });
        }
        totalLabel.setText(String.format("Total: Rp %.0f", currentOrder.getTotal()));
    }

    private void handleCheckout() {
        if (currentOrder.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double total = currentOrder.getTotal();
        String message = String.format("Total belanja: Rp %.0f\n(Checkout Selesai)", total);
        JOptionPane.showMessageDialog(this, message, "Checkout Selesai", JOptionPane.INFORMATION_MESSAGE);
        
        resetCashier();
    }

    private void resetCashier() {
        currentOrder.clear(); // Method baru di 'Order' agar lebih jelas
        refreshCartTable();
    }

    //======================================================================
    // --- MAIN METHOD ---
    //======================================================================
    public static void main(String[] args) {
        // Menjalankan GUI
        SwingUtilities.invokeLater(() -> new MainCashierView().setVisible(true));
    }

    //======================================================================
    // --- KELAS MODEL DATA (NESTED) ---
    //======================================================================
    /**
     * constructor, private final fields, getters (spt. name()),
     * equals(), hashCode(), dan toString() dasar.
     */
    static record Product(String code, String name, double price) {
        // Kita override 'toString' agar formatnya sesuai keinginan kita
        @Override
        public String toString() {
            return String.format("%s (Rp %.0f)", this.name, this.price);
        }
    }

    /**
     * Nested Class untuk OrderItem.
     * (Tidak bisa jadi record karena 'quantity' perlu diubah (mutable))
     */
    static class OrderItem {
        private final Product product;
        private int quantity;

        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getSubtotal() { return product.price() * quantity; }
    }

    /**
     * Nested Class untuk Order.
     * (Tidak bisa jadi record karena state-nya (list) berubah-ubah)
     */
    static class Order {
        private final List<OrderItem> items = new ArrayList<>();

        public void addItem(OrderItem item) { this.items.add(item); }
        public void removeItem(int index) {
            if (index >= 0 && index < items.size()) { items.remove(index); }
        }
        public List<OrderItem> getItems() { return items; }
        public void clear() { items.clear(); } // Method baru untuk 'resetCashier'
        public boolean isEmpty() { return items.isEmpty(); }

        public double getTotal() {
            // Cara fungsional untuk menghitung total
            return items.stream()
                        .mapToDouble(OrderItem::getSubtotal)
                        .sum();
        }

        public OrderItem findItemByProduct(Product product) {
            // Cara fungsional untuk mencari item
            return items.stream()
                        .filter(item -> item.getProduct().equals(product))
                        .findFirst()
                        .orElse(null);
        }
    }
}
