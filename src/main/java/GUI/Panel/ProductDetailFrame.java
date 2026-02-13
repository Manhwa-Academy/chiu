package GUI.Panel;


import DTO.SanPhamDTO;
import DTO.TaiKhoanDTO;

import BUS.GioHangBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.ThuongHieuBUS;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductDetailFrame extends JFrame {

    private final PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
    GioHangBUS ghBUS = new GioHangBUS();

    public ProductDetailFrame(SanPhamDTO sp, TaiKhoanDTO user) {

        setTitle("Chi tiết sản phẩm");
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ===== WRAPPER =====
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout(40, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));
        mainPanel.setBackground(Color.WHITE);
        // ===== IMAGE =====
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.LEFT);
        try {
            String resourcePath = "img/products/" + sp.getHinhanh().trim();
            URL url = getClass().getClassLoader().getResource(resourcePath);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(430, 430, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            }
        } catch (Exception ignored) {
        }

        mainPanel.add(lblImage, BorderLayout.WEST);

        // ===== RIGHT CONTENT =====
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel lblName = new JLabel(sp.getTensp());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        ThuongHieuBUS thBUS = new ThuongHieuBUS();
        String tenThuongHieu = thBUS.getTenThuongHieu(sp.getThuonghieu());

        JLabel lblBrand = new JLabel("Thương hiệu: " + tenThuongHieu);
        lblBrand.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblLoai = new JLabel("Loại sản phẩm: " + sp.getLoaiSanPham());
        lblLoai.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblSeries = new JLabel("Series: " + sp.getSeries());
        lblSeries.setAlignmentX(Component.LEFT_ALIGNMENT);

        int gia = pbBUS.getGiaThapNhatByMaSP(sp.getMasp());
        JLabel lblPrice = new JLabel(String.format("%,d ₫", gia));
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblPrice.setForeground(new Color(255, 66, 78));
        lblPrice.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblStock = new JLabel("Còn " + sp.getSoluongton() + " sản phẩm");
        lblStock.setForeground(new Color(0, 150, 0));
        lblStock.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ===== SỐ LƯỢNG =====
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, sp.getSoluongton(), 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(80, 35));

        JPanel qtyPanel = new JPanel();
        qtyPanel.setLayout(new BoxLayout(qtyPanel, BoxLayout.X_AXIS));
        qtyPanel.setBackground(Color.WHITE);
        qtyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        qtyPanel.setMaximumSize(new Dimension(150, 40)); // 👈 THÊM DÒNG NÀY

        qtyPanel.add(new JLabel("Số lượng: "));
        qtyPanel.add(Box.createHorizontalStrut(10));
        qtyPanel.add(spinner);

        // ===== BUTTON ADD TO CART =====
        JButton btnAdd = new JButton("THÊM VÀO GIỎ");
        btnAdd.setBackground(new Color(255, 87, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnAdd.setPreferredSize(new Dimension(260, 45));
        btnAdd.setMaximumSize(new Dimension(260, 45));
        btnAdd.setFocusPainted(false);

        btnAdd.addActionListener(e -> {

            int soLuong = (int) spinner.getValue();

            int mapb = pbBUS.getPhienBanDauTien(sp.getMasp())
                    .getMaphienbansp();

            ghBUS.addOrUpdate(user.getManv(), mapb, soLuong);

            JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ hàng!");
        });

        // ===== ADD CONTENT =====
        content.add(lblName);
        content.add(Box.createVerticalStrut(12));
        content.add(lblBrand);
        content.add(lblLoai);
        content.add(lblSeries);
        content.add(Box.createVerticalStrut(20));
        content.add(lblPrice);
        content.add(Box.createVerticalStrut(10));
        content.add(lblStock);
        content.add(Box.createVerticalStrut(20));
        content.add(qtyPanel);
        content.add(Box.createVerticalStrut(25));
        content.add(btnAdd);

        mainPanel.add(content, BorderLayout.CENTER);

        // ================= DESCRIPTION =================
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.WHITE);
        descPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        JLabel lblTitleDesc = new JLabel("MÔ TẢ SẢN PHẨM");
        lblTitleDesc.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JTextArea txtDesc = new JTextArea(
                sp.getMota() == null ? "Chưa có mô tả." : sp.getMota());
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDesc.setBackground(Color.WHITE);
        txtDesc.setBorder(null);

        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setBorder(null);

        descPanel.add(lblTitleDesc, BorderLayout.NORTH);
        descPanel.add(scrollDesc, BorderLayout.CENTER);

        wrapper.add(mainPanel, BorderLayout.CENTER);
        wrapper.add(descPanel, BorderLayout.SOUTH);

        add(wrapper);
        setVisible(true);
    }
}
