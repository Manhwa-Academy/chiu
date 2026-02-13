package GUI.Panel;

import GUI.Main;
import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;
import BUS.PhienBanSanPhamBUS;
import BUS.SanPhamBUS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.net.URL;

public class SanPhamKhachHang extends JPanel {

    private SanPhamBUS spBUS;
    private JPanel productContainer;
    private TaiKhoanDTO user;


    public SanPhamKhachHang(Main main, TaiKhoanDTO user) {

        this.spBUS = new SanPhamBUS();
        this.user = user;

        initComponent();
        loadProducts();
    }

    private void initComponent() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("DANH SÁCH SẢN PHẨM", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnCart = new JButton("GIỎ HÀNG");
        btnCart.setBackground(new Color(0, 150, 255));
        btnCart.setForeground(Color.WHITE);
        btnCart.setFocusPainted(false);

        btnCart.addActionListener(e -> {
            new GioHangFrame(user);
            refreshProducts(); // 🔥 sau khi thanh toán refresh lại tồn kho
        });

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(btnCart, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== PRODUCT CONTAINER =====
        productContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        productContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productContainer);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshProducts() {
        this.spBUS = new SanPhamBUS(); // reload lại dữ liệu từ DB
        loadProducts();
    }

    private void loadProducts() {

        productContainer.removeAll();

        ArrayList<SanPhamDTO> list = spBUS.getAll();

        for (SanPhamDTO sp : list) {
            if (sp.getTrangthai() == 1 && sp.getSoluongton() > 0) {
                productContainer.add(createProductCard(sp));
            }
        }

        productContainer.revalidate();
        productContainer.repaint();
    }

    private JPanel createProductCard(SanPhamDTO sp) {

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 300));
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // ===== IMAGE =====
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        try {
            String resourcePath = "img/products/" + sp.getHinhanh().trim();
            URL url = getClass().getClassLoader().getResource(resourcePath);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            } else {
                lblImage.setText("No Image");
            }

        } catch (Exception e) {
            lblImage.setText("No Image");
        }

        card.add(lblImage, BorderLayout.NORTH);

        // ===== INFO PANEL =====
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblName = new JLabel(sp.getTensp());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblSeries = new JLabel(sp.getSeries());
        lblSeries.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // ===== GIÁ =====
        int gia = 0;
        try {
            PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
            gia = pbBUS.getGiaThapNhatByMaSP(sp.getMasp());
        } catch (Exception ignored) {
        }

        JLabel lblPrice = new JLabel("Giá: " + String.format("%,d VNĐ", gia));
        lblPrice.setForeground(Color.RED);
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblStock = new JLabel("Còn: " + sp.getSoluongton());
        lblStock.setForeground(new Color(0, 150, 0));

        // ===== BUTTON DETAIL =====
        JButton btnDetail = new JButton("Xem chi tiết");
        btnDetail.setBackground(new Color(255, 87, 34));
        btnDetail.setForeground(Color.WHITE);
        btnDetail.setFocusPainted(false);

        btnDetail.addActionListener(e -> {
            new ProductDetailFrame(sp, user);
        });

        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblSeries);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblPrice);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblStock);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(btnDetail);

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }
}
