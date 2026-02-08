package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietSanPhamBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.PhieuNhapBUS;
import BUS.PhieuXuatBUS;

import DAO.KhachHangDAO;
import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietPhieuDTO;
import DTO.ChiTietSanPhamDTO;
import DTO.PhienBanSanPhamDTO;
import DTO.PhieuNhapDTO;
import DTO.PhieuXuatDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.Formater;
import helper.writePDF;

public final class ChiTietPhieuDialog extends JDialog implements ActionListener {

    HeaderTitle titlePage;
    JPanel pnmain, pnmain_top, pnmain_bottom, pnmain_bottom_right, pnmain_bottom_left, pnmain_btn;
    InputForm txtMaPhieu, txtNhanVien, txtNhaCungCap, txtThoiGian;
    DefaultTableModel tblModel, tblModelImei;
    JTable table, tblImei;
    JScrollPane scrollTable, scrollTableImei;

    PhieuNhapDTO phieunhap;
    PhieuXuatDTO phieuxuat;
    PhienBanSanPhamBUS phienbanBus = new PhienBanSanPhamBUS();
    ChiTietSanPhamBUS ctspBus = new ChiTietSanPhamBUS();
    PhieuNhapBUS phieunhapBus;
    PhieuXuatBUS phieuxuatBus;

    ButtonCustom btnPdf, btnHuyBo;

    ArrayList<ChiTietPhieuDTO> chitietphieu;

    HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham = new HashMap<>();

    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, PhieuNhapDTO phieunhapDTO) {
        super(owner, title, modal);

        // Kiểm tra nếu phieunhapDTO là null
        if (phieunhapDTO == null) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng lại nếu phieunhapDTO là null
        }

        this.phieunhap = phieunhapDTO;
        phieunhapBus = new PhieuNhapBUS();
        chitietphieu = phieunhapBus.getChiTietPhieu_Type(phieunhapDTO.getMaphieu());

        // Kiểm tra nếu chitietphieu trả về null
        if (chitietphieu == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết phiếu nhập!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        chitietsanpham = ctspBus.getChiTietSanPhamFromMaPN(phieunhapDTO.getMaphieu());

        // Kiểm tra nếu chitietsanpham trả về null
        if (chitietsanpham == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        initComponent(title);
        initPhieuNhap();
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }

    public ChiTietPhieuDialog(JFrame owner, String title, boolean modal, PhieuXuatDTO phieuxuatDTO) {
        super(owner, title, modal);

        if (phieuxuatDTO == null) {
            System.out.println("PhieuXuatDTO is null");
            return;
        }

        this.phieuxuat = phieuxuatDTO;
        phieuxuatBus = new PhieuXuatBUS();

        // Chỉ rõ kiểu cho ArrayList
        chitietphieu = phieuxuatBus.selectCTP(phieuxuatDTO.getMaphieu());
        if (chitietphieu == null) {
            System.out.println("Chitietphieu is null for maphieu: " + phieuxuatDTO.getMaphieu());
            chitietphieu = new ArrayList<ChiTietPhieuDTO>(); // Khởi tạo lại
        }

        // Nếu bạn muốn sử dụng HashMap
        HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chitietsanpham = ctspBus
                .getChiTietSanPhamFromMaPX(phieuxuatDTO.getMaphieu());

        if (chitietsanpham == null) {
            System.out.println("Chitietsanpham is null for maphieu: " + phieuxuatDTO.getMaphieu());
            chitietsanpham = new HashMap<Integer, ArrayList<ChiTietSanPhamDTO>>(); // Khởi tạo lại
        }

        initComponent(title);
        initPhieuXuat();
        loadDataTableChiTietPhieu(chitietphieu);
        this.setVisible(true);
    }

    public void initPhieuNhap() {
        txtMaPhieu.setText("PN" + Integer.toString(this.phieunhap.getMaphieu()));
        txtNhaCungCap.setText(NhaCungCapDAO.getInstance().selectById(phieunhap.getManhacungcap() + "").getTenncc());
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieunhap.getManguoitao() + "").getHoten());
        txtThoiGian.setText(Formater.FormatTime(phieunhap.getThoigiantao()));
    }

    public void initPhieuXuat() {
        txtMaPhieu.setText("PX" + Integer.toString(this.phieuxuat.getMaphieu()));
        txtNhaCungCap.setTitle("Khách hàng");
        txtNhaCungCap.setText(KhachHangDAO.getInstance().selectById(phieuxuat.getMakh() + "").getHoten());
        txtNhanVien.setText(NhanVienDAO.getInstance().selectById(phieuxuat.getManguoitao() + "").getHoten());
        txtThoiGian.setText(Formater.FormatTime(phieuxuat.getThoigiantao()));
    }

    public void loadDataTableChiTietPhieu(ArrayList<ChiTietPhieuDTO> ctPhieu) {
        tblModel.setRowCount(0);

        for (int i = 0; i < ctPhieu.size(); i++) {
            ChiTietPhieuDTO ct = ctPhieu.get(i);

            // Kiểm tra null cho đối tượng PhienBanSanPhamDTO
            PhienBanSanPhamDTO pb = phienbanBus.getByMaPhienBan(ct.getMaphienbansp());
            if (pb != null) {
                // Kiểm tra nếu pb không null, tiếp tục lấy thông tin sản phẩm
                var sp = SanPhamDAO.getInstance().selectById(pb.getMasp() + "");

                // Thêm dòng vào bảng nếu tìm thấy thông tin hợp lệ
                tblModel.addRow(new Object[] {
                        i + 1,
                        sp.getMasp(),
                        sp.getTensp(),
                        pb.getTenphienban(), // Regular / Limited / DX
                        Formater.FormatVND(ct.getDongia()),
                        ct.getSoluong()
                });
            } else {
                // Nếu pb là null, có thể in ra thông báo hoặc bỏ qua dòng này
                System.out.println("Không tìm thấy phiên bản sản phẩm với mã: " + ct.getMaphienbansp());
            }
        }
    }

    public void loadDataTableImei(ArrayList<ChiTietSanPhamDTO> dssp) {
        tblModelImei.setRowCount(0);
        int size = dssp.size();
        for (int i = 0; i < size; i++) {
            tblModelImei.addRow(new Object[] {
                    i + 1, dssp.get(i).getImei()
            });
        }
    }

    public void initComponent(String title) {
        this.setSize(new Dimension(1100, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new BorderLayout());

        pnmain_top = new JPanel(new GridLayout(1, 4));
        txtMaPhieu = new InputForm("Mã phiếu");
        txtNhanVien = new InputForm("Nhân viên nhập");
        txtNhaCungCap = new InputForm("Nhà cung cấp");
        txtThoiGian = new InputForm("Thời gian tạo");

        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtNhaCungCap.setEditable(false);
        txtThoiGian.setEditable(false);

        pnmain_top.add(txtMaPhieu);
        pnmain_top.add(txtNhanVien);
        pnmain_top.add(txtNhaCungCap);
        pnmain_top.add(txtThoiGian);

        pnmain_bottom = new JPanel(new BorderLayout(5, 5));
        pnmain_bottom.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnmain_bottom.setBackground(Color.WHITE);

        pnmain_bottom_left = new JPanel(new GridLayout(1, 1));
        table = new JTable();
        scrollTable = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = {
                "STT",
                "Mã mô hình",
                "Tên mô hình",
                "Phiên bản",
                "Giá bán",
                "Số lượng"
        };

        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        table.setFocusable(false);
        scrollTable.setViewportView(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index != -1) {
                    loadDataTableImei(chitietsanpham.get(chitietphieu.get(index).getMaphienbansp()));
                }
            }
        });
        pnmain_bottom_left.add(scrollTable);

        pnmain_bottom_right = new JPanel(new GridLayout(1, 1));
        pnmain_bottom_right.setPreferredSize(new Dimension(200, 10));
        tblImei = new JTable();
        scrollTableImei = new JScrollPane();
        tblModelImei = new DefaultTableModel();
        tblModelImei.setColumnIdentifiers(new String[] { "STT", "Mã Imei" });
        tblImei.setModel(tblModelImei);
        tblImei.setFocusable(false);
        tblImei.setDefaultRenderer(Object.class, centerRenderer);
        tblImei.getColumnModel().getColumn(1).setPreferredWidth(170);
        scrollTableImei.setViewportView(tblImei);
        pnmain_bottom_right.add(scrollTableImei);

        pnmain_bottom.add(pnmain_bottom_left, BorderLayout.CENTER);
        pnmain_bottom.add(pnmain_bottom_right, BorderLayout.EAST);

        pnmain_btn = new JPanel(new FlowLayout());
        pnmain_btn.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnmain_btn.setBackground(Color.white);
        btnPdf = new ButtonCustom("Xuất file PDF", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnPdf.addActionListener(this);
        btnHuyBo.addActionListener(this);
        pnmain_btn.add(btnPdf);
        pnmain_btn.add(btnHuyBo);

        pnmain.add(pnmain_top, BorderLayout.NORTH);
        pnmain.add(pnmain_bottom, BorderLayout.CENTER);
        pnmain.add(pnmain_btn, BorderLayout.SOUTH);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnHuyBo) {
            dispose();
        }

        if (source == btnPdf) {
            writePDF w = new writePDF();

            // Kiểm tra nếu phieuxuat hoặc phieunhap không phải là null và mã phiếu hợp lệ
            // (không phải -1)
            if (this.phieuxuat != null && this.phieuxuat.getMaphieu() != -1) {
                w.writePX(phieuxuat.getMaphieu()); // Gọi phương thức writePX() nếu phieuxuat có mã phiếu hợp lệ
            } else if (this.phieunhap != null && this.phieunhap.getMaphieu() != -1) {
                w.writePN(phieunhap.getMaphieu()); // Gọi phương thức writePN() nếu phieunhap có mã phiếu hợp lệ
            } else {
                // Thông báo nếu cả phieuxuat và phieunhap đều không có mã phiếu hợp lệ
                JOptionPane.showMessageDialog(this,
                        "Cả Phiếu Xuất và Phiếu Nhập đều không có dữ liệu hoặc mã phiếu không hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
