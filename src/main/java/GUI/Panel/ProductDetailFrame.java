package GUI.Panel;

import DTO.SanPhamDTO;
import BUS.PhienBanSanPhamBUS;
import BUS.SanPhamBUS;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import BUS.ThuongHieuBUS;

public class ProductDetailFrame extends JFrame {

    private SanPhamBUS spBUS = new SanPhamBUS();

    public ProductDetailFrame(SanPhamDTO sp) {

        setTitle("Chi tiết sản phẩm");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(40, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        mainPanel.setBackground(Color.WHITE);

        // ===== IMAGE =====
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        try {
            String resourcePath = "img/products/" + sp.getHinhanh().trim();
            URL url = getClass().getClassLoader().getResource(resourcePath);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            }
        } catch (Exception ignored) {
        }

        mainPanel.add(lblImage, BorderLayout.WEST);

        // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(sp.getTensp());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 28));

        ThuongHieuBUS thBUS = new ThuongHieuBUS();
        String tenThuongHieu = thBUS.getTenThuongHieu(sp.getThuonghieu());

        JLabel lblBrand = new JLabel("Thương hiệu: " + tenThuongHieu);
        JLabel lblLoai = new JLabel("Loại sản phẩm: " + sp.getLoaiSanPham());
        JLabel lblSeries = new JLabel("Series: " + sp.getSeries());

        // ===== GIÁ =====
        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        int gia = pbBUS.getGiaThapNhatByMaSP(sp.getMasp());

        JLabel lblPrice = new JLabel("Giá: " + String.format("%,d ₫", gia));
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblPrice.setForeground(new Color(255, 66, 78));

        JLabel lblStock = new JLabel("Còn " + sp.getSoluongton() + " sản phẩm");
        lblStock.setForeground(new Color(0, 150, 0));

        // ===== QUANTITY =====
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, sp.getSoluongton(), 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(80, 35));

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qtyPanel.setBackground(Color.WHITE);
        qtyPanel.add(new JLabel("Số lượng: "));
        qtyPanel.add(spinner);

        // ===== BUTTON =====
        JButton btnBuy = new JButton("THANH TOÁN");
        btnBuy.setBackground(new Color(255, 0, 0));
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnBuy.setFocusPainted(false);
        btnBuy.setPreferredSize(new Dimension(400, 55));

        btnBuy.addActionListener(e -> {

            int soLuong = (int) spinner.getValue();

            if (soLuong > sp.getSoluongton()) {
                JOptionPane.showMessageDialog(this, "Không đủ hàng!");
                return;
            }

            boolean success = spBUS.updateSoLuongTon(sp.getMasp(), -soLuong);

            if (success) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật kho!");
            }
        });

        rightPanel.add(lblName);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(lblBrand);
        rightPanel.add(lblLoai);
        rightPanel.add(lblSeries);
        rightPanel.add(Box.createVerticalStrut(25));
        rightPanel.add(lblPrice);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblStock);
        rightPanel.add(Box.createVerticalStrut(25));
        rightPanel.add(qtyPanel);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(btnBuy);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
