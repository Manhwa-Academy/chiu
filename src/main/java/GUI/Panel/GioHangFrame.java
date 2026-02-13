package GUI.Panel;

import DTO.*;
import BUS.*;
import DAO.PhieuXuatDAO;
import helper.writePDF;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GioHangFrame extends JFrame {

    private TaiKhoanDTO user;
    private JPanel cartPanel;
    private JLabel lblTotal;
    private JCheckBox chkPrint;

    private final PhieuXuatBUS pxBUS = new PhieuXuatBUS();
    private final SanPhamBUS spBUS = new SanPhamBUS();
    private final GioHangBUS ghBUS = new GioHangBUS();

    public GioHangFrame(TaiKhoanDTO user) {

        this.user = user;

        setTitle("Giỏ hàng");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        initUI();
        loadCart();

        setVisible(true);
    }

    private void initUI() {

        // ===== LEFT CART =====
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(new Color(245, 245, 245));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Quan trọng: neo item lên trên
        cartPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // ===== RIGHT ORDER INFO =====
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(320, 0));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Thông tin đơn hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblTotal = new JLabel("Tổng tiền: 0 ₫");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(Color.RED);

        chkPrint = new JCheckBox("Xuất hóa đơn");
        chkPrint.setBackground(Color.WHITE);

        JButton btnPay = new JButton("THANH TOÁN NGAY");
        btnPay.setBackground(Color.BLACK);
        btnPay.setForeground(Color.WHITE);
        btnPay.setFocusPainted(false);
        btnPay.setPreferredSize(new Dimension(260, 45));
        btnPay.addActionListener(e -> thanhToan());

        rightPanel.add(lblTitle);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(lblTotal);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(chkPrint);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(btnPay);

        add(rightPanel, BorderLayout.EAST);
    }

    private void loadCart() {

        cartPanel.removeAll();

        ArrayList<GioHangDTO> cart = ghBUS.getByManv(user.getManv());

        if (cart.isEmpty()) {

            JLabel empty = new JLabel("Giỏ hàng của bạn đang trống.");
            empty.setFont(new Font("Segoe UI", Font.BOLD, 20));
            empty.setHorizontalAlignment(JLabel.CENTER);
            empty.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

            cartPanel.add(empty);
            lblTotal.setText("Tổng tiền: 0 ₫");
            return;
        }

        long total = 0;

        for (GioHangDTO gh : cart) {

            SanPhamDTO sp = spBUS.getSp(gh.getMaphienbansp());
            int gia = new PhienBanSanPhamBUS()
                    .getGiaByMaPhienBan(gh.getMaphienbansp());

            long thanhTien = (long) gia * gh.getSoluong();
            total += thanhTien;

            cartPanel.add(createItem(sp, gh, gia));
            cartPanel.add(Box.createVerticalStrut(15));
            ;
        }

        lblTotal.setText("Tổng tiền: " + String.format("%,d ₫", total));

        cartPanel.add(Box.createVerticalGlue());
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private JPanel createItem(SanPhamDTO sp, GioHangDTO gh, int gia) {

        JPanel item = new JPanel(new BorderLayout(15, 10));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        item.setPreferredSize(new Dimension(0, 120));
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // ===== IMAGE =====
        JLabel lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(90, 90));

        try {
            String path = "img/products/" + sp.getHinhanh().trim();
            ImageIcon icon = new ImageIcon(
                    getClass().getClassLoader().getResource(path));

            Image img = icon.getImage()
                    .getScaledInstance(90, 90, Image.SCALE_SMOOTH);

            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImage.setText("No Image");
        }

        item.add(lblImage, BorderLayout.WEST);

        // ===== CENTER =====
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(sp.getTensp());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel lblPrice = new JLabel(String.format("%,d ₫", gia));
        lblPrice.setForeground(Color.RED);
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));

        center.add(lblName);
        center.add(Box.createVerticalStrut(8));
        center.add(lblPrice);

        item.add(center, BorderLayout.CENTER);

        // ===== RIGHT =====
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.WHITE);

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        qtyPanel.setBackground(Color.WHITE);

        JButton btnMinus = new JButton("-");
        JButton btnPlus = new JButton("+");
        JLabel lblQty = new JLabel(String.valueOf(gh.getSoluong()));
        lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnMinus.addActionListener(e -> {
            if (gh.getSoluong() > 1) {
                ghBUS.addOrUpdate(user.getManv(),
                        gh.getMaphienbansp(), -1);
                loadCart();
            }
        });

        btnPlus.addActionListener(e -> {
            ghBUS.addOrUpdate(user.getManv(),
                    gh.getMaphienbansp(), 1);
            loadCart();
        });

        qtyPanel.add(btnMinus);
        qtyPanel.add(lblQty);
        qtyPanel.add(btnPlus);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setForeground(Color.GRAY);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(false);

        btnDelete.addActionListener(e -> {
            ghBUS.delete(user.getManv(), gh.getMaphienbansp());
            loadCart();
        });

        right.add(qtyPanel);
        right.add(Box.createVerticalStrut(10));
        right.add(btnDelete);

        item.add(right, BorderLayout.EAST);

        return item;
    }

    private void thanhToan() {

        ArrayList<GioHangDTO> cart = ghBUS.getByManv(user.getManv());

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }

        try {

            int maphieu = PhieuXuatDAO.getInstance().getAutoIncrement();
            Timestamp time = new Timestamp(System.currentTimeMillis());

            long tongTien = 0;
            ArrayList<ChiTietPhieuDTO> ctList = new ArrayList<>();

            PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
            SanPhamBUS spBUS = new SanPhamBUS();

            for (GioHangDTO gh : cart) {

                // 1️⃣ Trừ tồn phiên bản
                pbBUS.updateSoLuongTon(
                        gh.getMaphienbansp(),
                        -gh.getSoluong());

                // 2️⃣ Lấy masp từ maphienbansp
                SanPhamDTO sp = spBUS.getSp(gh.getMaphienbansp());

                // 3️⃣ Trừ tồn sản phẩm
                spBUS.updateSoLuongTon(
                        sp.getMasp(),
                        -gh.getSoluong());
            }

            PhieuXuatDTO px = new PhieuXuatDTO(
                    1,
                    maphieu,
                    user.getManv(),
                    time,
                    tongTien,
                    1);

            pxBUS.insert(px, ctList);

            ghBUS.clear(user.getManv());

            if (chkPrint.isSelected()) {
                writePDF pdf = new writePDF();
                pdf.writePX(maphieu);
            }

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thanh toán!");
        }
    }
}
