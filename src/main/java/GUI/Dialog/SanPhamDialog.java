package GUI.Dialog;

import BUS.PhienBanSanPhamBUS;
import BUS.CategoryBUS;
import BUS.CharacterBUS;
import BUS.SeriesBUS;
import BUS.KhuVucKhoBUS;
import BUS.DacDiemSanPhamBUS;
import BUS.ThuongHieuBUS;
import BUS.XuatXuBUS;
import DAO.PhienBanSanPhamDAO;
import DAO.SanPhamDAO;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;

import GUI.Component.InputImage;
import GUI.Component.NumericDocumentFilter;
import GUI.Panel.SanPham;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;

public final class SanPhamDialog extends JDialog implements ActionListener {

    private HeaderTitle titlePage;

    // ===== ComboBox / Form =====
    private SelectForm cbxLoaiMoHinh;
    private SelectForm cbxSeries;
    private SelectForm cbxXuatXu;
    private SelectForm cbxThuongHieu;
    private SelectForm cbxKhuVucKho;
    private SelectForm cbxMauSac;
    private SelectForm cbxTyLe;
    private SelectForm cbxChatLieu;
    private SelectForm cbxNhanVat;

    // ===== Panel =====
    private JPanel pnThongTinMoHinh;
    private JPanel pnBottom;
    private JPanel pnCenter;
    private JPanel pnHinhAnh;
    private JPanel pnMain;
    private JPanel pnPhienBan;

    // ===== Button =====
    private ButtonCustom btnTaoMoHinh;
    private ButtonCustom btnHuy;
    private ButtonCustom btnThemPhienBan;
    private ButtonCustom btnSuaPhienBan;
    private ButtonCustom btnXoaPhienBan;
    private ButtonCustom btnResetPhienBan;
    private ButtonCustom btnThemSanPham;
    private ButtonCustom btnQuayLai;
    private ButtonCustom btnXemPhienBan;
    private ButtonCustom btnSuaChiTiet;
    private ButtonCustom btnLuuThongTin;
    private ButtonCustom btnThemPhienBanEdit;
    private ButtonCustom btnSuaPhienBanEdit;
    private ButtonCustom btnXoaPhienBanEdit;
    private ButtonCustom btnResetPhienBanEdit;

    // ===== Input =====
    private InputForm txtTenMoHinh;
    private InputForm txtTyLe;
    private InputForm txtChatLieu;
    private InputForm txtGiaNhap;
    private InputForm txtGiaBan;

    // ===== Image =====
    private InputImage inputHinhAnh;

    // ===== Table =====
    private JTable tblPhienBan;
    private JScrollPane scrollPhienBan;
    private DefaultTableModel modelPhienBan;
    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    // ===== BUS =====
    private CategoryBUS loaiMoHinhBus = new CategoryBUS();
    private CharacterBUS nhanVatBus = new CharacterBUS();
    private DacDiemSanPhamBUS dacDiemBus = new DacDiemSanPhamBUS();
    private ThuongHieuBUS thuongHieuBus = new ThuongHieuBUS();
    private SeriesBUS seriesBus = new SeriesBUS();
    private KhuVucKhoBUS khuVucKhoBus = new KhuVucKhoBUS();
    private XuatXuBUS xuatXuBus = new XuatXuBUS();

    // ===== Data =====
    private ArrayList<PhienBanSanPhamDTO> danhSachPhienBan = new ArrayList<>();
    private SanPhamDTO sanPham;

    private String[] dsKhuVuc;
    private String[] dsThuongHieu;
    private String[] dsSeries;
    private String[] dsXuatXu;
    private String[] dsLoaiMoHinh;
    private GUI.Panel.SanPham jpSP;
    private int maSanPham;
    private int maPhienBan;

    public void init(SanPham jpSP) {
        this.jpSP = jpSP;

        // ===== Auto increment =====
        maSanPham = jpSP.spBUS.spDAO.getAutoIncrement();
        maPhienBan = PhienBanSanPhamDAO.getInstance().getAutoIncrement();

        // ===== Panel chính =====
        pnThongTinMoHinh = new JPanel(new GridLayout(3, 4, 10, 10));
        pnThongTinMoHinh.setBackground(Color.WHITE);

        // ===== Load dữ liệu từ BUS =====
        dsKhuVuc = khuVucKhoBus.getArrTenKhuVuc();
        dsThuongHieu = thuongHieuBus.getArrTenThuongHieu();
        dsSeries = seriesBus.getArrTenSeries();
        dsXuatXu = xuatXuBus.getArrTenXuatXu();
        dsLoaiMoHinh = loaiMoHinhBus.getArrCategory();

        // ===== Form nhập liệu chính =====
        inputHinhAnh = new InputImage("Hình minh họa");

        txtTenMoHinh = new InputForm("Tên mô hình");
        cbxNhanVat = new SelectForm(
                "Nhân vật",
                nhanVatBus.getArrTenNhanVat());

        txtTyLe = new InputForm("Tỷ lệ mô hình");
        txtChatLieu = new InputForm("Chất liệu");

        cbxLoaiMoHinh = new SelectForm("Loại sản phẩm", dsLoaiMoHinh);
        cbxXuatXu = new SelectForm("Xuất xứ", dsXuatXu);
        cbxThuongHieu = new SelectForm("Thương hiệu", dsThuongHieu);
        cbxSeries = new SelectForm("Anime / Series", dsSeries);
        cbxKhuVucKho = new SelectForm("Khu vực kho", dsKhuVuc);

        // ===== Giá =====
        txtGiaNhap = new InputForm("Giá nhập");
        txtGiaBan = new InputForm("Giá bán");

        // ===== Table phiên bản =====
        modelPhienBan = new DefaultTableModel();

        modelPhienBan.setColumnIdentifiers(new String[] {
                "STT",
                "Mã phiên bản",
                "Tên phiên bản",
                "Nhân vật",
                "Tỷ lệ",
                "Chất liệu",
                "Giá nhập",
                "Giá bán"
        });

        tblPhienBan = new JTable(modelPhienBan);
        tblPhienBan.setDefaultRenderer(Object.class, centerRenderer);
        scrollPhienBan = new JScrollPane(tblPhienBan);

        // ===== Button =====
        btnThemPhienBan = new ButtonCustom("Thêm phiên bản", "success", 14);
        btnSuaPhienBan = new ButtonCustom("Sửa phiên bản", "warning", 14);
        btnXoaPhienBan = new ButtonCustom("Xoá phiên bản", "danger", 14);
        btnResetPhienBan = new ButtonCustom("Làm mới", "excel", 14);

        btnTaoMoHinh = new ButtonCustom("Tạo mô hình", "success", 14);
        btnHuy = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnLuuThongTin = new ButtonCustom("Lưu thông tin", "success", 14);

        // ===== Add listener =====
        btnThemPhienBan.addActionListener(this);
        btnSuaPhienBan.addActionListener(this);
        btnXoaPhienBan.addActionListener(this);
        btnResetPhienBan.addActionListener(this);
        btnTaoMoHinh.addActionListener(this);
        btnHuy.addActionListener(this);
        btnLuuThongTin.addActionListener(this);
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        init(jpSP);
        initComponents(title, type);
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner,
            String title, boolean modal,
            String type, SanPhamDTO sp) {

        super(owner, title, modal);

        init(jpSP); // 👈 BẮT BUỘC PHẢI CÓ

        this.sanPham = sp;

        this.danhSachPhienBan = this.jpSP.spBUS.cauhinhBus.getAll(sp.getMasp());

        initComponents(title, type);
    }

    public void initCardOne(String type) {

        pnCenter = new JPanel(new BorderLayout());

        // ===== Panel thông tin =====
        pnThongTinMoHinh = new JPanel(new GridLayout(3, 4, 10, 10));
        pnThongTinMoHinh.setBackground(Color.WHITE);
        pnCenter.add(pnThongTinMoHinh, BorderLayout.CENTER);

        // ===== Panel hình ảnh =====
        pnHinhAnh = new JPanel();
        pnHinhAnh.setBackground(Color.WHITE);
        pnHinhAnh.setPreferredSize(new Dimension(300, 600));
        pnHinhAnh.setBorder(new EmptyBorder(0, 10, 0, 10));
        pnCenter.add(pnHinhAnh, BorderLayout.WEST);

        // ===== Input =====
        txtTenMoHinh = new InputForm("Tên mô hình");

        txtTyLe = new InputForm("Tỷ lệ mô hình");
        txtChatLieu = new InputForm("Chất liệu");

        // ===== ComboBox =====
        cbxLoaiMoHinh = new SelectForm("Loại sản phẩm", dsLoaiMoHinh);
        cbxSeries = new SelectForm("Anime / Series", dsSeries);
        cbxThuongHieu = new SelectForm("Thương hiệu", dsThuongHieu);
        cbxXuatXu = new SelectForm("Xuất xứ", dsXuatXu);
        cbxKhuVucKho = new SelectForm("Khu vực kho", dsKhuVuc);

        // ===== Image =====
        inputHinhAnh = new InputImage("Hình minh họa");

        // ===== Add vào panel =====
        pnThongTinMoHinh.add(cbxLoaiMoHinh);
        pnThongTinMoHinh.add(txtTenMoHinh);
        pnThongTinMoHinh.add(cbxNhanVat);

        pnThongTinMoHinh.add(txtTyLe);
        pnThongTinMoHinh.add(txtChatLieu);
        pnThongTinMoHinh.add(cbxSeries);
        pnThongTinMoHinh.add(cbxThuongHieu);
        pnThongTinMoHinh.add(cbxXuatXu);
        pnThongTinMoHinh.add(cbxKhuVucKho);

        pnHinhAnh.add(inputHinhAnh);

        // ===== Bottom =====
        pnBottom = new JPanel(new FlowLayout());
        pnBottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnBottom.setBackground(Color.WHITE);

        switch (type) {

            case "view" -> {
                btnXemPhienBan = new ButtonCustom("Xem mô hình", "warning", 14);
                btnXemPhienBan.addActionListener(this);
                pnBottom.add(btnXemPhienBan);
            }

            case "update" -> {
                btnLuuThongTin = new ButtonCustom("Lưu thông tin", "success", 14);
                btnSuaChiTiet = new ButtonCustom("Sửa mô hình", "warning", 14);

                btnLuuThongTin.addActionListener(this);
                btnSuaChiTiet.addActionListener(this);

                pnBottom.add(btnLuuThongTin);
                pnBottom.add(btnSuaChiTiet);
            }

            case "create" -> {
                btnTaoMoHinh = new ButtonCustom("Tạo mô hình", "success", 14);
                btnTaoMoHinh.addActionListener(this);
                pnBottom.add(btnTaoMoHinh);
            }
        }

        btnHuy = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnHuy.addActionListener(this);
        pnBottom.add(btnHuy);

        pnCenter.add(pnBottom, BorderLayout.SOUTH);
    }

    public void initCardTwo(String type) {

        pnPhienBan = new JPanel(new BorderLayout());

        // ===== Top: nhập giá =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        txtGiaNhap = new InputForm("Giá nhập");
        txtGiaBan = new InputForm("Giá bán");

        PlainDocument nhapDoc = (PlainDocument) txtGiaNhap.getTxtForm().getDocument();
        nhapDoc.setDocumentFilter(new NumericDocumentFilter());

        PlainDocument banDoc = (PlainDocument) txtGiaBan.getTxtForm().getDocument();
        banDoc.setDocumentFilter(new NumericDocumentFilter());

        topPanel.add(txtGiaNhap);
        topPanel.add(txtGiaBan);

        // ===== Table =====
        modelPhienBan = new DefaultTableModel();

        modelPhienBan.setColumnIdentifiers(new String[] {
                "STT",
                "Mã phiên bản",
                "Tên phiên bản",
                "Nhân vật",
                "Tỷ lệ",
                "Chất liệu",
                "Giá nhập",
                "Giá bán"
        });

        tblPhienBan = new JTable(modelPhienBan);
        tblPhienBan.setDefaultRenderer(Object.class, centerRenderer);

        scrollPhienBan = new JScrollPane(tblPhienBan);
        // 👇 THÊM ĐOẠN NÀY Ở ĐÂY
        tblPhienBan.getColumnModel().getColumn(0).setPreferredWidth(40); // STT
        tblPhienBan.getColumnModel().getColumn(1).setPreferredWidth(90); // Mã phiên bản
        tblPhienBan.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên phiên bản
        tblPhienBan.getColumnModel().getColumn(3).setPreferredWidth(120); // Nhân vật
        tblPhienBan.getColumnModel().getColumn(4).setPreferredWidth(80); // Tỷ lệ
        tblPhienBan.getColumnModel().getColumn(5).setPreferredWidth(100); // Chất liệu
        tblPhienBan.getColumnModel().getColumn(6).setPreferredWidth(100); // Giá nhập
        tblPhienBan.getColumnModel().getColumn(7).setPreferredWidth(100); // Giá bán
        tblPhienBan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tblPhienBan.getSelectedRow();
                if (index != -1) {
                    setInfoCauHinh(danhSachPhienBan.get(index));
                }
            }
        });

        // ===== Nút thao tác =====
        JPanel rightPanel = new JPanel(new FlowLayout());

        if (!type.equals("update")) {

            btnThemPhienBan = new ButtonCustom("Thêm mô hình", "success", 14);
            btnSuaPhienBan = new ButtonCustom("Sửa mô hình", "warning", 14);
            btnXoaPhienBan = new ButtonCustom("Xoá mô hình", "danger", 14);
            btnResetPhienBan = new ButtonCustom("Làm mới", "excel", 14);

            btnThemPhienBan.addActionListener(this);
            btnSuaPhienBan.addActionListener(this);
            btnXoaPhienBan.addActionListener(this);
            btnResetPhienBan.addActionListener(this);

            rightPanel.add(btnThemPhienBan);
            rightPanel.add(btnSuaPhienBan);
            rightPanel.add(btnXoaPhienBan);
            rightPanel.add(btnResetPhienBan);

        } else {

            btnThemPhienBanEdit = new ButtonCustom("Thêm mô hình", "success", 14);
            btnSuaPhienBanEdit = new ButtonCustom("Sửa mô hình", "warning", 14);
            btnXoaPhienBanEdit = new ButtonCustom("Xoá mô hình", "danger", 14);
            btnResetPhienBanEdit = new ButtonCustom("Làm mới", "excel", 14);

            btnThemPhienBanEdit.addActionListener(this);
            btnSuaPhienBanEdit.addActionListener(this);
            btnXoaPhienBanEdit.addActionListener(this);
            btnResetPhienBanEdit.addActionListener(this);

            rightPanel.add(btnThemPhienBanEdit);
            rightPanel.add(btnSuaPhienBanEdit);
            rightPanel.add(btnXoaPhienBanEdit);
            rightPanel.add(btnResetPhienBanEdit);
        }

        // ===== Center layout =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPhienBan, BorderLayout.CENTER);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        // ===== Bottom =====
        JPanel bottomPanel = new JPanel(new FlowLayout());

        switch (type) {

            case "view" -> {
                loadDataToTableCauHinh(danhSachPhienBan);
                rightPanel.setVisible(false);
            }

            case "update" -> loadDataToTableCauHinh(danhSachPhienBan);

            case "create" -> {
                btnThemSanPham = new ButtonCustom("Thêm sản phẩm", "success", 14);
                btnThemSanPham.addActionListener(this);
                bottomPanel.add(btnThemSanPham);
            }
        }

        btnQuayLai = new ButtonCustom("Quay lại trang trước", "warning", 14);
        btnQuayLai.addActionListener(this);
        bottomPanel.add(btnQuayLai);

        pnPhienBan.add(topPanel, BorderLayout.NORTH);
        pnPhienBan.add(centerPanel, BorderLayout.CENTER);
        pnPhienBan.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void initComponents(String title, String type) {

        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        this.setSize(new Dimension(1150, 480));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        pnMain = new JPanel(new CardLayout());

        // Khởi tạo 2 card
        initCardOne(type);
        initCardTwo(type);

        pnMain.add(pnCenter, "card1");
        pnMain.add(pnPhienBan, "card2");

        // Nếu là view hoặc update thì load dữ liệu lên form
        if (type.equals("view") || type.equals("update")) {
            setInfo(sanPham);
        }

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnMain, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String addImage(String urlImg) {

        Random randomGenerator = new Random();
        int ram = randomGenerator.nextInt(1000);

        File sourceFile = new File(urlImg);

        String destPath = "src/img/img_product"; // sửa lại
        File destFolder = new File(destPath);

        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }

        String newName = ram + sourceFile.getName();

        try {
            Path dest = Paths.get(destFolder.getPath(), newName);
            Files.copy(sourceFile.toPath(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        // ===== CHUYỂN CARD =====
        if (source == btnTaoMoHinh && validateCardOne()) {
            CardLayout cl = (CardLayout) pnMain.getLayout();
            cl.next(pnMain);
        }

        else if (source == btnQuayLai) {
            CardLayout cl = (CardLayout) pnMain.getLayout();
            cl.previous(pnMain);
        }

        // ===== THÊM PHIÊN BẢN =====
        else if (source == btnThemPhienBan) {
            if (validateCardTwo() && checkTonTai()) {
                danhSachPhienBan.add(getCauHinh());
                loadDataToTableCauHinh(danhSachPhienBan);
                resetFormCauHinh();
            }
        }

        else if (source == btnResetPhienBan) {
            resetFormCauHinh();
            loadDataToTableCauHinh(danhSachPhienBan);
        }

        else if (source == btnXoaPhienBan) {
            int index = getRowCauHinh();
            if (index >= 0) {
                danhSachPhienBan.remove(index);
                loadDataToTableCauHinh(danhSachPhienBan);
                resetFormCauHinh();
            }
        }

        else if (source == btnSuaPhienBan) {
            eventEditCauHinh();
            loadDataToTableCauHinh(danhSachPhienBan);
        }

        // ===== THÊM SẢN PHẨM =====
        else if (source == btnThemSanPham) {
            eventAddSanPham();
        }

        // ===== XEM / SỬA CHI TIẾT =====
        else if (source == btnXemPhienBan || source == btnSuaChiTiet) {
            CardLayout cl = (CardLayout) pnMain.getLayout();
            cl.next(pnMain);
        }

        // ===== LƯU THÔNG TIN =====
        else if (source == btnLuuThongTin) {

            SanPhamDTO spMoi = getInfo();

            if (!spMoi.getHinhanh().equals(sanPham.getHinhanh())) {
                spMoi.setHinhanh(addImage(spMoi.getHinhanh()));
            }

            spMoi.setMasp(sanPham.getMasp());

            SanPhamDAO.getInstance().update(spMoi);
            jpSP.spBUS.update(spMoi);
            jpSP.loadDataTalbe(jpSP.spBUS.getAll());

            int input = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn chỉnh sửa phiên bản sản phẩm?",
                    "Chỉnh sửa chi tiết",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (input == JOptionPane.OK_OPTION) {
                CardLayout cl = (CardLayout) pnMain.getLayout();
                cl.next(pnMain);
            }
        }

        // ===== SỬA PHIÊN BẢN (EDIT MODE) =====
        else if (source == btnSuaPhienBanEdit) {

            if (validateCardTwo()) {
                int index = getRowCauHinh();

                if (index >= 0) {

                    String tyLe = txtTyLe.getText();

                    danhSachPhienBan.get(index).setTenphienban(tyLe);
                    danhSachPhienBan.get(index)
                            .setGianhap(Integer.parseInt(txtGiaNhap.getText()));
                    danhSachPhienBan.get(index)
                            .setGiaxuat(Integer.parseInt(txtGiaBan.getText()));

                    PhienBanSanPhamDAO.getInstance()
                            .update(danhSachPhienBan.get(index));

                    loadDataToTableCauHinh(danhSachPhienBan);
                    resetFormCauHinh();
                }
            }
        }

        // ===== XOÁ PHIÊN BẢN EDIT =====
        else if (source == btnXoaPhienBanEdit) {

            int index = getRowCauHinh();

            if (index >= 0) {

                int result = PhienBanSanPhamDAO.getInstance()
                        .delete(String.valueOf(
                                danhSachPhienBan.get(index)
                                        .getMaphienbansp()));

                if (result > 0) {
                    danhSachPhienBan.remove(index);
                    loadDataToTableCauHinh(danhSachPhienBan);
                    resetFormCauHinh();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa không thành công!");
                }
            }
        }

        // ===== THÊM PHIÊN BẢN EDIT =====
        else if (source == btnThemPhienBanEdit) {

            if (validateCardTwo() && checkTonTai()) {

                PhienBanSanPhamDAO.getInstance()
                        .insert(getCauHinh(sanPham.getMasp()));

                loadDataToTableCauHinh(danhSachPhienBan);
                resetFormCauHinh();
            }
        }

        else if (source == btnResetPhienBanEdit) {
            resetFormCauHinh();
            loadDataToTableCauHinh(danhSachPhienBan);
        }

        // ===== HỦY =====
        else if (source == btnHuy) {
            dispose();
        }
    }

    public void eventAddSanPham() {

        SanPhamDTO sanPhamMoi = getInfo();

        sanPhamMoi.setHinhanh(
                addImage(sanPhamMoi.getHinhanh()));

        if (jpSP.spBUS.add(sanPhamMoi, danhSachPhienBan)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm mô hình thành công!");

            jpSP.loadDataTalbe(jpSP.spBUS.getAll());

            dispose();
        }
    }

    public void eventEditCauHinh() {

        if (validateCardTwo()) {

            int index = getRowCauHinh();

            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiên bản");
                return;
            }

            String tyLeText = txtTyLe.getText();
            String chatLieuText = txtChatLieu.getText();

            if (Validation.isEmpty(tyLeText) ||
                    Validation.isEmpty(chatLieuText)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            PhienBanSanPhamDTO phienBan = danhSachPhienBan.get(index);

            phienBan.setTenphienban(tyLeText + " - " + chatLieuText);
            phienBan.setGianhap(
                    Integer.parseInt(txtGiaNhap.getText()));
            phienBan.setGiaxuat(
                    Integer.parseInt(txtGiaBan.getText()));

            PhienBanSanPhamDAO
                    .getInstance()
                    .update(phienBan);

            loadDataToTableCauHinh(danhSachPhienBan);

            resetFormCauHinh();
        }
    }

    public int getRowCauHinh() {

        int index = tblPhienBan.getSelectedRow();

        if (index == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn phiên bản mô hình!");
        }

        return index;
    }

    public class InputForm extends JPanel {
        private JTextField textField;

        public InputForm(String label) {
            textField = new JTextField(20); // Tạo một JTextField với số cột mặc định
            textField.setPreferredSize(new Dimension(250, 30)); // Đặt kích thước cho JTextField
            add(new JLabel(label)); // Thêm label
            add(textField); // Thêm trường nhập liệu
        }

        // Phương thức để lấy giá trị của trường nhập liệu
        public JTextField getTxtForm() {
            return textField; // Trả về JTextField
        }

        // Phương thức để lấy giá trị nhập vào
        public String getText() {
            return textField.getText(); // Trả về giá trị nhập vào
        }

        // Phương thức để đặt giá trị cho JTextField
        public void setText(String text) {
            textField.setText(text); // Đặt giá trị cho JTextField
        }
    }

    public class SelectForm extends JPanel {
        private JComboBox<String> comboBox;

        public SelectForm(String label, String[] items) {
            comboBox = new JComboBox<>(items);
            comboBox.setPreferredSize(new Dimension(250, 30)); // Đặt kích thước cho JComboBox
            add(new JLabel(label)); // Thêm label
            add(comboBox); // Thêm combo box
        }

        public JComboBox<String> getComboBox() {
            return comboBox;
        }

        // Phương thức để lấy giá trị của item được chọn trong JComboBox
        public String getValue() {
            return (String) comboBox.getSelectedItem();

        }

        // Phương thức để lấy chỉ số của item được chọn
        public int getSelectedIndex() {
            return comboBox.getSelectedIndex();
        }

        // Phương thức để thay đổi chỉ mục đã chọn
        public void setSelectedIndex(int index) {
            comboBox.setSelectedIndex(index);
        }
    }

    public SanPhamDTO getInfo() {

        String hinhAnh = inputHinhAnh.getUrl_img();
        String tenMoHinh = txtTenMoHinh.getText();

        int maXuatXu = xuatXuBus.getAll()
                .get(cbxXuatXu.getSelectedIndex())
                .getMaxuatxu();

        int maThuongHieu = thuongHieuBus.getAll()
                .get(cbxThuongHieu.getSelectedIndex())
                .getMathuonghieu();

        int maKhuVucKho = khuVucKhoBus.getAll()
                .get(cbxKhuVucKho.getSelectedIndex())
                .getMakhuvuc();

        String series = cbxSeries.getValue();
        String nhanVat = cbxNhanVat.getValue();

        String tyLe = txtTyLe.getText();
        String chatLieu = txtChatLieu.getText();
        String loaiMoHinh = cbxLoaiMoHinh.getValue();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String ngayTao = currentDate.format(formatter);

        return new SanPhamDTO(
                maSanPham,
                tenMoHinh,
                hinhAnh,
                maThuongHieu,
                series,
                nhanVat,
                tyLe,
                chatLieu,
                maXuatXu,
                maKhuVucKho,
                loaiMoHinh,
                0,
                1,
                ngayTao);
    }

    public void setInfo(SanPhamDTO sp) {

        this.sanPham = sp;

        // Hình ảnh
        inputHinhAnh.setUrl_img(sp.getHinhanh());

        // Tên mô hình
        txtTenMoHinh.setText(sp.getTensp());

        // Xuất xứ
        cbxXuatXu.setSelectedIndex(
                xuatXuBus.getIndexByMaXX(sp.getXuatxu()));

        // Thương hiệu
        cbxThuongHieu.setSelectedIndex(
                thuongHieuBus.getIndexByMaLH(sp.getThuonghieu()));

        // Series
        int indexSeries = seriesBus.getIndexByTenSeries(sp.getSeries());
        if (indexSeries >= 0) {
            cbxSeries.setSelectedIndex(indexSeries);
        }

        // Khu vực kho
        cbxKhuVucKho.setSelectedIndex(
                khuVucKhoBus.getIndexByMaKhuVuc(sp.getKhuvuckho()));

        // Thông tin mô hình
        int indexNV = nhanVatBus.getIndexByTen(sp.getNhanvat());
        if (indexNV >= 0) {
            cbxNhanVat.setSelectedIndex(indexNV);
        }

        txtTyLe.setText(sp.getTyle());
        txtChatLieu.setText(sp.getChatlieu());
    }

    public PhienBanSanPhamDTO getCauHinh() {

        String tyLe = txtTyLe.getText();
        String chatLieu = txtChatLieu.getText();

        String tenPhienBan = tyLe + " - " + chatLieu;

        int giaNhap = Integer.parseInt(txtGiaNhap.getText());
        int giaBan = Integer.parseInt(txtGiaBan.getText());

        int soLuongTon = 0;

        PhienBanSanPhamDTO phienBan = new PhienBanSanPhamDTO(
                maPhienBan,
                maSanPham,
                tenPhienBan,
                giaNhap,
                giaBan,
                soLuongTon,
                1);

        maPhienBan++;

        return phienBan;
    }

    public PhienBanSanPhamDTO getCauHinh(int maSanPham) {

        String tyLe = txtTyLe.getText();
        String chatLieu = txtChatLieu.getText();

        String tenPhienBan = tyLe + " - " + chatLieu;

        int giaNhap = Integer.parseInt(txtGiaNhap.getText());
        int giaBan = Integer.parseInt(txtGiaBan.getText());

        int soLuongTon = 0;

        PhienBanSanPhamDTO phienBan = new PhienBanSanPhamDTO(
                PhienBanSanPhamDAO.getInstance().getAutoIncrement(),
                maSanPham,
                tenPhienBan,
                giaNhap,
                giaBan,
                soLuongTon,
                1);

        danhSachPhienBan.add(phienBan);

        return phienBan;
    }

    public boolean validateCardOne() {

        if (Validation.isEmpty(txtTenMoHinh.getText()) ||
                cbxNhanVat.getSelectedIndex() < 0 ||
                Validation.isEmpty(txtTyLe.getText()) ||
                Validation.isEmpty(txtChatLieu.getText()) ||
                cbxSeries.getSelectedIndex() < 0 ||
                cbxXuatXu.getSelectedIndex() < 0 ||
                cbxThuongHieu.getSelectedIndex() < 0 ||
                cbxKhuVucKho.getSelectedIndex() < 0 ||
                cbxLoaiMoHinh.getSelectedIndex() < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin mô hình!");
            return false;
        }

        return true;
    }

    public boolean validateCardTwo() {
        boolean check = true;
        if (Validation.isEmpty(txtGiaNhap.getText()) ||
                Validation.isEmpty(txtGiaBan.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
            check = false;
        } else {

        }
        return check;
    }

    public boolean checkTonTai() {

        PhienBanSanPhamBUS phienBanBUS = new PhienBanSanPhamBUS();

        PhienBanSanPhamDTO phienBanMoi = getCauHinh();

        if (phienBanMoi == null) {
            return false;
        }

        if (phienBanBUS.checkDuplicate(danhSachPhienBan, phienBanMoi)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Phiên bản mô hình đã tồn tại!");
            return false;
        }

        return true;
    }

    public void loadDataToTableCauHinh(ArrayList<PhienBanSanPhamDTO> ds) {

        modelPhienBan.setRowCount(0);

        for (int i = 0; i < ds.size(); i++) {

            PhienBanSanPhamDTO pb = ds.get(i);

            modelPhienBan.addRow(new Object[] {
                    i + 1,
                    pb.getMaphienbansp(),
                    pb.getTenphienban(),
                    cbxNhanVat.getValue(),
                    txtTyLe.getText(),
                    txtChatLieu.getText(),
                    pb.getGianhap(),
                    pb.getGiaxuat()
            });

        }
    }

    public void resetFormCauHinh() {

        if (cbxMauSac != null)
            cbxMauSac.setSelectedIndex(0);
        if (cbxTyLe != null)
            cbxTyLe.setSelectedIndex(0);
        if (cbxChatLieu != null)
            cbxChatLieu.setSelectedIndex(0);

        if (txtGiaNhap != null)
            txtGiaNhap.setText("");
        if (txtGiaBan != null)
            txtGiaBan.setText("");

        tblPhienBan.clearSelection();
    }

    public void setInfoCauHinh(PhienBanSanPhamDTO pb) {

        if (pb == null)
            return;

        txtGiaNhap.setText(String.valueOf(pb.getGianhap()));
        txtGiaBan.setText(String.valueOf(pb.getGiaxuat()));

        // Nếu phiên bản lưu tên theo tỷ lệ
        if (cbxTyLe != null) {
            int index = dacDiemBus.getIndexByTen(pb.getTenphienban());
            if (index >= 0) {
                cbxTyLe.setSelectedIndex(index);
            }
        }
    }

}
