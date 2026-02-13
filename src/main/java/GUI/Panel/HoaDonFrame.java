package GUI.Panel;

import DTO.PhieuXuatDTO;
import DTO.ChiTietPhieuDTO;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import BUS.PhienBanSanPhamBUS;
import BUS.SanPhamBUS;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HoaDonFrame extends JFrame {

    public HoaDonFrame(PhieuXuatDTO px, ArrayList<ChiTietPhieuDTO> list) {

        setTitle("HÓA ĐƠN");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("HÓA ĐƠN THANH TOÁN", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        PhienBanSanPhamBUS pbBUS = new PhienBanSanPhamBUS();
        SanPhamBUS spBUS = new SanPhamBUS();

        NumberFormat formatter = NumberFormat.getInstance(Locale.of("vi", "VN"));

        StringBuilder sb = new StringBuilder();

        sb.append("Mã phiếu: PX").append(px.getMaphieu()).append("\n");
        sb.append("Ngày: ").append(px.getThoigiantao()).append("\n");
        sb.append("-------------------------------------------------\n");

        long tong = 0;

        for (ChiTietPhieuDTO ct : list) {

            PhienBanSanPhamDTO pb = pbBUS.getByMaPhienBan(ct.getMaphienbansp());
            SanPhamDTO sp = spBUS.getByMaSP(pb.getMasp());

            long thanhTien = (long) ct.getDongia() * ct.getSoluong();
            tong += thanhTien;

            sb.append("Sản phẩm: ").append(sp.getTensp()).append("\n");
            sb.append("Phiên bản: ").append(pb.getTenphienban()).append("\n");
            sb.append("Số lượng: ").append(ct.getSoluong()).append("\n");
            sb.append("Đơn giá: ").append(formatter.format(ct.getDongia())).append(" VND\n");
            sb.append("Thành tiền: ").append(formatter.format(thanhTien)).append(" VND\n");
            sb.append("-------------------------------------------------\n");
        }

        sb.append("TỔNG TIỀN: ").append(formatter.format(tong)).append(" VND\n");

        area.setText(sb.toString());

        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        JButton btnPrint = new JButton("In hóa đơn");
        btnPrint.addActionListener(e -> JOptionPane.showMessageDialog(this, "Đã gửi lệnh in!"));

        panel.add(btnPrint, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
