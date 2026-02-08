package GUI.Dialog;

import java.awt.BorderLayout;
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
import BUS.CategoryBUS;
import BUS.DacDiemSanPhamBUS;
import BUS.PhienBanSanPhamBUS;
import BUS.PhieuKiemKeBUS;
import BUS.SanPhamBUS;
import DAO.NhanVienDAO;
import DTO.ChiTietKiemKeDTO;
import DTO.ChiTietSanPhamDTO;
import DTO.PhienBanSanPhamDTO;
import DTO.PhieuKiemKeDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import helper.Formater;

public final class ChiTietPhieuKiemKeDialog extends JDialog implements ActionListener {

    // ===== UI =====
    HeaderTitle titlePage;
    JPanel pnMain, pnTop, pnBottom, pnBtn;
    JPanel pnBottomLeft, pnBottomRight;

    InputForm txtMaPhieu, txtNhanVien, txtNhaCungCap, txtThoiGian;

    JTable tblChiTiet, tblImei;
    DefaultTableModel modelChiTiet, modelImei;

    JScrollPane scrollChiTiet, scrollImei;

    ButtonCustom btnPdf, btnDong;

    // ===== DATA =====
    PhieuKiemKeDTO phieuKiemKe;
    ArrayList<ChiTietKiemKeDTO> chiTietPhieu;
    HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> chiTietSanPham;

    // ===== BUS =====
    PhieuKiemKeBUS phieuKiemKeBUS = new PhieuKiemKeBUS();
    PhienBanSanPhamBUS phienBanBUS = new PhienBanSanPhamBUS();
    ChiTietSanPhamBUS ctspBUS = new ChiTietSanPhamBUS();
    SanPhamBUS sanPhamBUS = new SanPhamBUS();
    CategoryBUS ramBUS = new CategoryBUS();
    CategoryBUS romBUS = new CategoryBUS();
    DacDiemSanPhamBUS dacDiemBUS = new DacDiemSanPhamBUS();

    public ChiTietPhieuKiemKeDialog(JFrame owner, String title, boolean modal, PhieuKiemKeDTO dto) {
        super(owner, title, modal);
        this.phieuKiemKe = dto;

        chiTietPhieu = phieuKiemKeBUS.getChitietTiemKe(dto.getMaphieukiemke());
        chiTietSanPham = ctspBUS.getChiTietSanPhamFromMaPN(dto.getMaphieukiemke());

        initComponent(title);
        fillThongTin();
        loadTableChiTiet();

        this.setVisible(true);
    }

    // ===== INIT UI =====
    private void initComponent(String title) {
        this.setSize(1100, 520);
        this.setLayout(new BorderLayout(0, 0));
        this.setLocationRelativeTo(null);

        titlePage = new HeaderTitle(title.toUpperCase());
        this.add(titlePage, BorderLayout.NORTH);

        pnMain = new JPanel(new BorderLayout(5, 5));
        pnMain.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(pnMain, BorderLayout.CENTER);

        // ===== TOP =====
        pnTop = new JPanel(new GridLayout(1, 3, 10, 0));
        txtMaPhieu = new InputForm("Mã phiếu");
        txtNhanVien = new InputForm("Người tạo");
        txtThoiGian = new InputForm("Thời gian");

        txtMaPhieu.setEditable(false);
        txtNhanVien.setEditable(false);
        txtThoiGian.setEditable(false);

        pnTop.add(txtMaPhieu);
        pnTop.add(txtNhanVien);
        pnTop.add(txtThoiGian);

        pnMain.add(pnTop, BorderLayout.NORTH);

        // ===== BOTTOM =====
        pnBottom = new JPanel(new BorderLayout(5, 5));
        pnMain.add(pnBottom, BorderLayout.CENTER);

        // ----- TABLE CHI TIẾT -----
        pnBottomLeft = new JPanel(new BorderLayout());
        modelChiTiet = new DefaultTableModel(
                new String[] {
                        "STT",
                        "Mã mô hình",
                        "Tên mô hình",
                        "Phiên bản",
                        "Số lượng"
                },
                0);
        tblChiTiet = new JTable(modelChiTiet);
        centerTable(tblChiTiet);

        scrollChiTiet = new JScrollPane(tblChiTiet);
        pnBottomLeft.add(scrollChiTiet, BorderLayout.CENTER);

        pnBottom.add(pnBottomLeft, BorderLayout.CENTER);

        // ----- TABLE IMEI -----
        pnBottomRight = new JPanel(new BorderLayout());
        pnBottomRight.setPreferredSize(new Dimension(260, 10));

        modelImei = new DefaultTableModel(
                new String[] { "STT", "IMEI" },
                0);
        tblImei = new JTable(modelImei);
        centerTable(tblImei);

        scrollImei = new JScrollPane(tblImei);
        pnBottomRight.add(scrollImei, BorderLayout.CENTER);

        pnBottom.add(pnBottomRight, BorderLayout.EAST);

        // ===== BUTTON =====
        pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPdf = new ButtonCustom("Xuất PDF", "success", 14);
        btnDong = new ButtonCustom("Đóng", "danger", 14);

        btnPdf.addActionListener(this);
        btnDong.addActionListener(this);

        pnBtn.add(btnPdf);
        pnBtn.add(btnDong);

        pnMain.add(pnBtn, BorderLayout.SOUTH);

        // ===== CLICK TABLE =====
        tblChiTiet.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tblChiTiet.getSelectedRow();
                if (row != -1) {
                    int maPB = chiTietPhieu.get(row).getMaphienban();
                    loadTableImei(chiTietSanPham.get(maPB));
                }
            }
        });
    }

    // ===== DATA =====
    private void fillThongTin() {
        txtMaPhieu.setText("PKK" + phieuKiemKe.getMaphieukiemke());

        txtNhanVien.setText(
                NhanVienDAO.getInstance()
                        .selectById(String.valueOf(phieuKiemKe.getNguoitao()))
                        .getHoten());

        txtThoiGian.setText(
                Formater.FormatTime(phieuKiemKe.getThoigiantao()));
    }

    private void loadTableChiTiet() {
        modelChiTiet.setRowCount(0);
        int i = 1;

        for (ChiTietKiemKeDTO ct : chiTietPhieu) {
            PhienBanSanPhamDTO pb = phienBanBUS.getByMaPhienBan(ct.getMaphienban());
            var sp = sanPhamBUS.getByMaSP(pb.getMasp());

            modelChiTiet.addRow(new Object[] {
                    i++,
                    sp.getMasp(),
                    sp.getTensp(),
                    pb.getTenphienban(), // Regular / Limited / DX
                    ct.getSoluong()
            });
        }
    }

    private void loadTableImei(ArrayList<ChiTietSanPhamDTO> list) {
        modelImei.setRowCount(0);
        if (list == null)
            return;
        int i = 1;
        for (ChiTietSanPhamDTO ct : list) {
            modelImei.addRow(new Object[] { i++, ct.getImei() });
        }
    }

    private void centerTable(JTable table) {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);
        table.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDong) {
            dispose();
        }
        if (e.getSource() == btnPdf) {
            JOptionPane.showMessageDialog(this, "Chức năng PDF chưa cài 😅");
        }
    }
}
