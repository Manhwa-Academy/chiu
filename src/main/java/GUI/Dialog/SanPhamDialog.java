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
import DTO.ThuocTinhSanPham.SeriesDTO;
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
import java.util.Arrays;
import java.util.Random;
import javax.swing.BoxLayout;
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

    private JComboBox<String> loaiSanPhamComboBox;
    private JPanel pninfosanpham, pnbottom, pnCenter, pninfosanphamright, pnmain, pncard2;
    private ButtonCustom btnThemCHMS, btnHuyBo, btnAddCauHinh, btnEditCTCauHinh, btnDeleteCauHinh, btnResetCauHinh,
            btnAddSanPham, btnBack, btnViewCauHinh;
    InputForm tenSP, chipxuly, dungluongpin, kichthuocman, thoigianbaohanh, phienbanhdh, camerasau, cameratruoc;
    InputForm txtgianhap, txtgiaxuat;
    SelectForm cbxRom, cbxRam, cbxMausac, hedieuhanh, xuatxu;
    SelectForm thuonghieu, khuvuc;
    InputImage hinhanh;
    JTable tblcauhinh;
    JScrollPane scrolltblcauhinh;
    DefaultTableModel tblModelch;
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    GUI.Panel.SanPham jpSP;

    CategoryBUS ramBus = new CategoryBUS();
    CharacterBUS romBus = new CharacterBUS();
    DacDiemSanPhamBUS dacDiemBus = new DacDiemSanPhamBUS();
    ThuongHieuBUS thuonghieuBus = new ThuongHieuBUS();
    SeriesBUS seriesBUS = new SeriesBUS();
    KhuVucKhoBUS kvkhoBus = new KhuVucKhoBUS();
    XuatXuBUS xuatXuBUS = new XuatXuBUS();
    CategoryBUS mausacBus = new CategoryBUS();
    ArrayList<PhienBanSanPhamDTO> listch = new ArrayList<>();
    SanPhamDTO sp;
    String[] arrkhuvuc;
    String[] arrthuonghieu;
    String[] arrhHDH;
    String[] arrXX;
    SelectForm cbxTyle, cbxChatlieu;
    InputForm nhanvat, tyle, chatlieu;
    // Lấy danh sách các loại sản phẩm từ CategoryBUS
    CategoryBUS categoryBUS = new CategoryBUS();
    String[] arrCategoryArray = categoryBUS.getArrCategory(); // Lấy mảng String[]
    ArrayList<String> arrCategory = new ArrayList<>(Arrays.asList(arrCategoryArray)); // Chuyển đổi thành ArrayList
    DacDiemSanPhamBUS dacDiemSanPhamBUS = new DacDiemSanPhamBUS();
    int masp;
    int mach;
    private ButtonCustom btnEditCT;
    private ButtonCustom btnSaveCH;
    private ButtonCustom btnAddCauHinhEdit;
    private ButtonCustom btnEditCTCauHinhEdit;
    private ButtonCustom btnDeleteCauHinhEdit;
    private ButtonCustom btnResetCauHinhEdit;

    public void init(SanPham jpSP) {
        this.jpSP = jpSP;

        // Khởi tạo mã sản phẩm (masp) và mã cấu hình (mach) tự động từ database
        masp = jpSP.spBUS.spDAO.getAutoIncrement();
        mach = PhienBanSanPhamDAO.getInstance().getAutoIncrement();

        // Khởi tạo pninfosanpham và các panel khác
        pninfosanpham = new JPanel(new GridLayout(3, 4, 0, 0)); // Khởi tạo với layout GridLayout
        pninfosanpham.setBackground(Color.WHITE);

        // Tiến hành các bước còn lại sau khi khởi tạo panel
        arrkhuvuc = kvkhoBus.getArrTenKhuVuc();
        arrthuonghieu = thuonghieuBus.getArrTenThuongHieu();
        arrhHDH = seriesBUS.getArrTenSeries(); // Hệ điều hành hoặc Series / Anime
        arrXX = xuatXuBUS.getArrTenXuatXu(); // Xuất xứ

        // Các combobox và input form
        cbxTyle = new SelectForm("Tỷ lệ mô hình", dacDiemSanPhamBUS.getArrTenDacDiemSanPham()); // Tỷ lệ mô hình
        cbxChatlieu = new SelectForm("Chất liệu", dacDiemSanPhamBUS.getArrTenDacDiemSanPham()); // Chất liệu

        // Các trường nhập liệu cho giá nhập và giá xuất
        txtgianhap = new InputForm("Giá nhập");
        txtgiaxuat = new InputForm("Giá xuất");

        // Khởi tạo các đối tượng cần thiết như bảng cấu hình sản phẩm, model dữ liệu,
        // renderer
        tblModelch = new DefaultTableModel();
        tblModelch.setColumnIdentifiers(new String[] {
                "Màu sắc", "Tỷ lệ", "Chất liệu", "Giá nhập", "Giá xuất"
        });
        tblcauhinh = new JTable(tblModelch);
        scrolltblcauhinh = new JScrollPane(tblcauhinh);
        tblcauhinh.setDefaultRenderer(Object.class, centerRenderer);

        // Tạo các button tương tác với cấu hình sản phẩm
        btnAddCauHinh = new ButtonCustom("Thêm mô hình", "success", 14);
        btnEditCTCauHinh = new ButtonCustom("Sửa mô hình", "warning", 14);
        btnDeleteCauHinh = new ButtonCustom("Xoá mô hình", "danger", 14);
        btnResetCauHinh = new ButtonCustom("Làm mới", "excel", 14);

        // Khởi tạo các button chức năng
        btnThemCHMS = new ButtonCustom("Tạo mô hình", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnSaveCH = new ButtonCustom("Lưu mô hình", "success", 14);

        // Set các action listener cho button
        btnAddCauHinh.addActionListener(this);
        btnEditCTCauHinh.addActionListener(this);
        btnDeleteCauHinh.addActionListener(this);
        btnResetCauHinh.addActionListener(this);
        btnSaveCH.addActionListener(this);
        btnThemCHMS.addActionListener(this);
        btnHuyBo.addActionListener(this);

        // Khởi tạo lại các combobox, input form cho các thông tin sản phẩm
        hinhanh = new InputImage("Hình minh họa");
        tenSP = new InputForm("Tên sản phẩm");
        xuatxu = new SelectForm("Xuất xứ", arrXX);
        thuonghieu = new SelectForm("Thương hiệu", arrthuonghieu);
        hedieuhanh = new SelectForm("Series / Anime", arrhHDH); // Đổi thành Series / Anime
        khuvuc = new SelectForm("Khu vực kho", arrkhuvuc);

        // Cập nhật lại combobox Loại sản phẩm
        JComboBox<String> loaiSanPhamComboBox = new JComboBox<>(arrCategory.toArray(new String[0]));
        pninfosanpham.add(loaiSanPhamComboBox); // Thêm vào panel

        // Thêm ComboBox cho Chất liệu
        SelectForm loaiSanPham = new SelectForm("Loại sản phẩm", arrCategory.toArray(new String[0]));
        pninfosanpham.add(loaiSanPham); // Thêm vào panel
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        init(jpSP);
        initComponents(title, type);
    }

    public SanPhamDialog(SanPham jpSP, JFrame owner, String title, boolean modal, String type, SanPhamDTO sp) {
        super(owner, title, modal);
        init(jpSP);
        this.sp = sp;
        this.listch = jpSP.spBUS.cauhinhBus.getAll(sp.getMasp());
        initComponents(title, type);
    }

    public void initCardOne(String type) {
        pnCenter = new JPanel(new BorderLayout());
        pninfosanpham = new JPanel(new GridLayout(3, 4, 0, 0));
        pninfosanpham.setBackground(Color.WHITE);
        pnCenter.add(pninfosanpham, BorderLayout.CENTER);

        pninfosanphamright = new JPanel();
        pninfosanphamright.setBackground(Color.WHITE);
        pninfosanphamright.setPreferredSize(new Dimension(300, 600));
        pninfosanphamright.setBorder(new EmptyBorder(0, 10, 0, 10));
        pnCenter.add(pninfosanphamright, BorderLayout.WEST);

        // Khởi tạo các đối tượng InputForm và SelectForm cho các trường sản phẩm
        tenSP = new InputForm("Tên mô hình");
        xuatxu = new SelectForm("Xuất xứ", arrXX); // Cập nhật với danh sách xuất xứ phù hợp

        // Các trường nhập liệu chỉ dành cho mô hình sản phẩm như Nhân vật, Tỷ lệ, Chất
        // liệu
        nhanvat = new InputForm("Nhân vật");
        tyle = new InputForm("Tỷ lệ mô hình"); // Ví dụ: 1/7, 1/8...
        chatlieu = new InputForm("Chất liệu");

        // Các combobox như hệ điều hành, thương hiệu, khu vực kho sẽ được giữ lại
        hedieuhanh = new SelectForm("Anime / Series", arrhHDH); // Series / Anime
        thuonghieu = new SelectForm("Thương hiệu", arrthuonghieu); // Thương hiệu sản phẩm
        khuvuc = new SelectForm("Khu vực kho", arrkhuvuc); // Khu vực kho lưu trữ sản phẩm
        // Thêm SelectForm cho "Loại sản phẩm"
        SelectForm loaiSanPham = new SelectForm("Loại sản phẩm", arrCategory.toArray(new String[0]));
        pninfosanpham.add(loaiSanPham); // Thêm vào panel
        // Các trường nhập liệu cho giá nhập và giá xuất
        txtgianhap = new InputForm("Giá nhập");
        txtgiaxuat = new InputForm("Giá xuất");

        // Nhập ảnh cho mô hình sản phẩm
        hinhanh = new InputImage("Hình minh họa");

        // Thêm các InputForm và SelectForm vào panel pninfosanpham để hiển thị trên
        // giao diện
        pninfosanpham.add(tenSP);
        pninfosanpham.add(xuatxu);
        pninfosanpham.add(nhanvat);
        pninfosanpham.add(tyle);
        pninfosanpham.add(chatlieu);
        pninfosanpham.add(hedieuhanh);
        pninfosanpham.add(thuonghieu);
        pninfosanpham.add(khuvuc);
        pninfosanphamright.add(hinhanh);

        pnbottom = new JPanel(new FlowLayout());
        pnbottom.setBorder(new EmptyBorder(20, 0, 10, 0));
        pnbottom.setBackground(Color.white);
        switch (type) {
            case "view" -> {
                btnViewCauHinh = new ButtonCustom("Xem mô hình", "warning", 14);
                btnViewCauHinh.addActionListener(this);
                pnbottom.add(btnViewCauHinh);
            }
            case "update" -> {
                btnSaveCH = new ButtonCustom("Lưu thông tin", "success", 14);
                btnEditCT = new ButtonCustom("Sửa mô hình", "warning", 14);
                btnSaveCH.addActionListener(this);
                btnEditCT.addActionListener(this);
                pnbottom.add(btnSaveCH);
                pnbottom.add(btnEditCT);
            }
            case "create" -> {
                btnThemCHMS = new ButtonCustom("Tạo mô hình", "success", 14);
                btnThemCHMS.addActionListener(this);
                pnbottom.add(btnThemCHMS);
            }
        }

        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnHuyBo.addActionListener(this);
        pnbottom.add(btnHuyBo);
        pnCenter.add(pnbottom, BorderLayout.SOUTH);
    }

    public void initCardTwo(String type) {
        pncard2 = new JPanel(new BorderLayout());
        JPanel cauhinhtop = new JPanel(new GridLayout(1, 5));
        cbxRom = new SelectForm("Mô hình", romBus.getArrKichThuoc());
        cbxRam = new SelectForm("Category", ramBus.getArrTenCategory());
        cbxMausac = new SelectForm("Đặc điểm", dacDiemSanPhamBUS.getArrTenDacDiemSanPham());

        txtgianhap = new InputForm("Giá nhập");
        PlainDocument nhap = (PlainDocument) txtgianhap.getTxtForm().getDocument();
        nhap.setDocumentFilter((new NumericDocumentFilter()));

        txtgiaxuat = new InputForm("Giá xuất");
        PlainDocument xuat = (PlainDocument) txtgiaxuat.getTxtForm().getDocument();
        xuat.setDocumentFilter((new NumericDocumentFilter()));

        cauhinhtop.add(cbxRom);
        cauhinhtop.add(cbxRam);
        cauhinhtop.add(cbxMausac);
        cauhinhtop.add(txtgianhap);
        cauhinhtop.add(txtgiaxuat);

        JPanel cauhinhcenter = new JPanel(new BorderLayout());

        JPanel cauhinhcenter_left = new JPanel();
        BoxLayout bl = new BoxLayout(cauhinhcenter_left, BoxLayout.Y_AXIS);
        cauhinhcenter_left.setPreferredSize(new Dimension(100, 226));
        cauhinhcenter_left.setBorder(new EmptyBorder(10, 10, 10, 10));
        cauhinhcenter_left.setLayout(bl);
        cauhinhcenter_left.setBackground(Color.WHITE);
        tblcauhinh = new JTable();
        tblcauhinh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = getRowCauHinh();
                if (index != -1) {
                    setInfoCauHinh(listch.get(index));
                }
            }
        });

        // Kiểm tra xem có dấu đóng ngoặc thừa ở đây không
        // Dấu đóng ngoặc này có thể bị thiếu hoặc thừa

        new JScrollPane(tblcauhinh);
        tblModelch = new DefaultTableModel();

        // Cập nhật lại header của bảng sao cho phù hợp với mô hình sản phẩm
        String[] header = new String[] {
                "STT", // Số thứ tự
                "Mã mô hình", // Mã mô hình (nếu cần)
                "Tên mô hình", // Tên mô hình sản phẩm
                "Nhân vật", // Nhân vật (từ series hoặc anime)
                "Tỷ lệ", // Tỷ lệ mô hình (1/7, 1/8, ...)
                "Chất liệu", // Chất liệu mô hình
                "Giá nhập", // Giá nhập vào của mô hình
                "Giá xuất" // Giá bán mô hình
        };

        // Đảm bảo khi thiết lập lại bảng, header này được sử dụng
        tblModelch.setColumnIdentifiers(header);

        tblcauhinh.setModel(tblModelch);
        scrolltblcauhinh.setViewportView(tblcauhinh);
        tblcauhinh.setDefaultRenderer(Object.class, centerRenderer);
        cauhinhcenter_left.add(scrolltblcauhinh);

        JPanel cauhinhcenter_right = new JPanel(
                new FlowLayout());
        cauhinhcenter_right.setPreferredSize(new Dimension(180, 10));
        cauhinhcenter_right.setBackground(Color.white);
        cauhinhcenter_right.setBorder(new EmptyBorder(0, 0, 0, 10));
        if (!type.equals("update")) {
            btnAddCauHinh = new ButtonCustom("Thêm mô hình", "success", 14);
            btnEditCTCauHinh = new ButtonCustom("Sửa mô hình", "warning", 14);
            btnDeleteCauHinh = new ButtonCustom("Xoá mô hình", "danger", 14);
            btnResetCauHinh = new ButtonCustom("Làm mới", "excel", 14);

            btnAddCauHinh.addActionListener(this);
            btnEditCTCauHinh.addActionListener(this);
            btnDeleteCauHinh.addActionListener(this);
            btnResetCauHinh.addActionListener(this);
            cauhinhcenter_right.add(btnAddCauHinh);
            cauhinhcenter_right.add(btnEditCTCauHinh);
            cauhinhcenter_right.add(btnDeleteCauHinh);
            cauhinhcenter_right.add(btnResetCauHinh);
        } else {
            btnAddCauHinhEdit = new ButtonCustom("Thêm mô hình", "success", 14);
            btnEditCTCauHinhEdit = new ButtonCustom("Sửa mô hình", "warning", 14);
            btnDeleteCauHinhEdit = new ButtonCustom("Xoá mô hình", "danger", 14);
            btnResetCauHinhEdit = new ButtonCustom("Làm mới", "excel", 14);

            btnAddCauHinhEdit.addActionListener(this);
            btnEditCTCauHinhEdit.addActionListener(this);
            btnDeleteCauHinhEdit.addActionListener(this);
            btnResetCauHinhEdit.addActionListener(this);

            cauhinhcenter_right.add(btnAddCauHinhEdit);
            cauhinhcenter_right.add(btnEditCTCauHinhEdit);
            cauhinhcenter_right.add(btnDeleteCauHinhEdit);
            cauhinhcenter_right.add(btnResetCauHinhEdit);
        }

        cauhinhcenter.add(cauhinhcenter_left, BorderLayout.CENTER);
        cauhinhcenter.add(cauhinhcenter_right, BorderLayout.EAST);

        JPanel cauhinhbottom = new JPanel(
                new FlowLayout());
        cauhinhbottom.setBackground(Color.white);
        cauhinhbottom.setBorder(new EmptyBorder(0, 0, 10, 0));

        switch (type) {
            case "view" -> {
                loadDataToTableCauHinh(listch);
                btnAddCauHinh.setVisible(false);
                btnEditCTCauHinh.setVisible(false);
                btnDeleteCauHinh.setVisible(false);
                btnResetCauHinh.setVisible(false);
                cauhinhcenter.remove(cauhinhcenter_right);
            }
            case "update" -> loadDataToTableCauHinh(listch);
            case "create" -> {
                btnAddSanPham = new ButtonCustom("Thêm sản phẩm", "success", 14);
                btnAddSanPham.addActionListener(this);
                cauhinhbottom.add(btnAddSanPham);
            }
        }

        btnBack = new ButtonCustom("Quay lại trang trước", "warning", 14);
        btnBack.addActionListener(this);
        cauhinhbottom.add(btnBack);

        pncard2.add(cauhinhtop, BorderLayout.NORTH);
        pncard2.add(cauhinhcenter, BorderLayout.CENTER);
        pncard2.add(cauhinhbottom, BorderLayout.SOUTH);

    }

    public void initComponents(String title, String type) {
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.setSize(new Dimension(1150, 480));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());

        pnmain = new JPanel(new CardLayout());

        initCardOne(type);
        initCardTwo(type);

        pnmain.add(pnCenter);
        pnmain.add(pncard2);

        switch (type) {
            case "view" -> setInfo(sp);
            case "update" -> setInfo(sp);
            default -> {
            }
        }
        // throw new AssertionError();

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnmain, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String addImage(String urlImg) {
        Random randomGenerator = new Random();
        int ram = randomGenerator.nextInt(1000);
        File sourceFile = new File(urlImg);
        String destPath = "/img/img_product";
        File destFolder = new File(destPath);
        String newName = ram + sourceFile.getName();
        try {
            Path dest = Paths.get(destFolder.getPath(), newName);
            Files.copy(sourceFile.toPath(), dest);
        } catch (IOException e) {
        }
        return newName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnThemCHMS && validateCardOne()) {
            CardLayout c = (CardLayout) pnmain.getLayout();
            c.next(pnmain);
        } else if (source == btnBack) {
            CardLayout c = (CardLayout) pnmain.getLayout();
            c.previous(pnmain);
        } else if (source == btnAddCauHinh) {
            if (validateCardTwo() && checkTonTai()) {
                listch.add(getCauHinh());
                loadDataToTableCauHinh(this.listch);
                resetFormCauHinh();
            }
        } else if (source == btnResetCauHinh) {
            resetFormCauHinh();
            loadDataToTableCauHinh(this.listch);
        } else if (source == btnDeleteCauHinh) {
            int index = getRowCauHinh();
            this.listch.remove(index);
            loadDataToTableCauHinh(this.listch);
            resetFormCauHinh();
        } else if (source == btnEditCTCauHinh) {
            eventEditCauHinh();
            loadDataToTableCauHinh(this.listch);
        } else if (source == btnAddSanPham) {
            eventAddSanPham();
        } else if (source == btnViewCauHinh) {
            CardLayout c = (CardLayout) pnmain.getLayout();
            c.next(pnmain);
        } else if (source == btnEditCT) {
            CardLayout c = (CardLayout) pnmain.getLayout();
            c.next(pnmain);
        } else if (source == btnSaveCH) {
            SanPhamDTO snNew = getInfo();
            if (!snNew.getHinhanh().equals(this.sp.getHinhanh())) {
                snNew.setHinhanh(addImage(snNew.getHinhanh()));
            }
            snNew.setMasp(this.sp.getMasp());
            SanPhamDAO.getInstance().update(sp);
            this.jpSP.spBUS.update(snNew);
            this.jpSP.loadDataTalbe(this.jpSP.spBUS.getAll());
            int input = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn chỉnh sửa chi tiết sản phẩm?", "Chỉnh sửa chi tiết",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            // 0=ok, 2=cancel
            if (input == 0) {
                CardLayout c = (CardLayout) pnmain.getLayout();
                c.next(pnmain);
            }
        }
        if (source == btnEditCTCauHinhEdit) {
            if (validateCardTwo()) {
                int index = getRowCauHinh();
                if (index < 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn mô hình");
                } else {
                    // Tạo đối tượng DacDiemSanPhamBUS thay vì gọi phương thức tĩnh
                    DacDiemSanPhamBUS dacDiemSanPhamBUS = new DacDiemSanPhamBUS();

                    // Lấy thông tin từ combobox
                    String tyle = dacDiemSanPhamBUS.getByIndex(cbxTyle.getSelectedIndex()).getTendacdiem();

                    // Cập nhật thông tin cho listch mà không thay đổi PhienBanSanPhamDTO
                    listch.get(index).setTenphienban(tyle); // Chỉ thay đổi tên phiên bản theo tỷ lệ
                    // Hoặc nếu bạn cần lưu trữ thêm các thông tin tạm thời, có thể sử dụng
                    // đối tượng lưu trữ ngoài (ví dụ: một lớp tạm thời).

                    // Cập nhật thông tin giá nhập, giá xuất
                    listch.get(index).setGianhap(Integer.parseInt(txtgianhap.getText()));
                    listch.get(index).setGiaxuat(Integer.parseInt(txtgiaxuat.getText()));

                    // Cập nhật lại cơ sở dữ liệu
                    PhienBanSanPhamDAO.getInstance().update(listch.get(index));

                    // Tải lại bảng và reset form
                    loadDataToTableCauHinh(this.listch);
                    resetFormCauHinh();
                }
            }
        }

        if (source == btnDeleteCauHinhEdit)

        {
            int index = getRowCauHinh();
            // Gọi phương thức delete từ DAO để đánh dấu phiên bản sản phẩm là đã xóa (trạng
            // thái = 0)
            int result = PhienBanSanPhamDAO.getInstance()
                    .delete(String.valueOf(this.listch.get(index).getMaphienbansp()));
            if (result > 0) { // Nếu xóa thành công
                this.listch.remove(index);
                loadDataToTableCauHinh(this.listch); // Cập nhật lại bảng
                resetFormCauHinh(); // Reset form
            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công!");
            }
        }
        if (source == btnAddCauHinhEdit) {
            if (validateCardTwo() && checkTonTai()) {
                PhienBanSanPhamDAO.getInstance().insert(getCauHinh(sp.getMasp()));
                loadDataToTableCauHinh(this.listch);
                resetFormCauHinh();
            }
        }
        if (source == btnResetCauHinhEdit) {
            resetFormCauHinh();
            loadDataToTableCauHinh(this.listch);
        }
        if (source == btnHuyBo) {
            dispose();
        }
    }

    public void eventAddSanPham() {
        SanPhamDTO sp = getInfo();
        sp.setHinhanh(addImage(sp.getHinhanh()));
        if (jpSP.spBUS.add(sp, listch)) {
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công !");
            jpSP.loadDataTalbe(jpSP.listSP);
            dispose();
        }
    }

    public void eventEditCauHinh() {
        if (validateCardTwo()) { // Kiểm tra các trường dữ liệu
            int index = getRowCauHinh(); // Lấy dòng được chọn trong bảng cấu hình
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mô hình");
            } else {
                // Lấy giá trị từ các trường nhập liệu
                String tyleText = tyle.getText(); // Lấy tỷ lệ mô hình từ giao diện
                String chatlieuText = chatlieu.getText(); // Lấy chất liệu mô hình từ giao diện

                // Kiểm tra nếu các trường tỷ lệ và chất liệu trống
                if (Validation.isEmpty(tyleText) || Validation.isEmpty(chatlieuText)) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    return; // Dừng lại nếu thông tin không hợp lệ
                }

                // Cập nhật các thông tin khác từ các trường nhập liệu
                listch.get(index).setGianhap(Integer.parseInt(txtgianhap.getText())); // Lấy giá nhập
                listch.get(index).setGiaxuat(Integer.parseInt(txtgiaxuat.getText())); // Lấy giá xuất

                // Cập nhật vào database
                PhienBanSanPhamDAO.getInstance().update(listch.get(index));

                // Cập nhật lại bảng với thông tin mới
                loadDataToTableCauHinh(this.listch);

                // Reset lại form nhập liệu
                resetFormCauHinh();
            }
        }
    }

    public int getRowCauHinh() {
        int index = tblcauhinh.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mô hình !");
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
        // Lấy thông tin từ giao diện
        String hinhanh = this.hinhanh.getUrl_img(); // Lấy URL của hình ảnh
        String tensp = tenSP.getText(); // Tên sản phẩm
        int xuatxu = xuatXuBUS.getAll().get(this.xuatxu.getSelectedIndex()).getMaxuatxu(); // Xuất xứ
        int thuonghieu = thuonghieuBus.getAll().get(this.thuonghieu.getSelectedIndex()).getMathuonghieu(); // Thương
                                                                                                           // hiệu
        int khuvuckho = kvkhoBus.getAll().get(this.khuvuc.getSelectedIndex()).getMakhuvuc(); // Khu vực kho

        // Lấy thông tin từ các trường nhập liệu
        String series = this.hedieuhanh.getName(); // Lấy giá trị từ InputForm hedieuhanh (hệ điều hành/series)
        String nhanvat = this.nhanvat.getText(); // Nhân vật
        String tyle = this.tyle.getText(); // Tỷ lệ mô hình (1/7, 1/8, ...)
        String chatlieu = this.chatlieu.getText(); // Chất liệu mô hình
        String loaiSanPham = (String) loaiSanPhamComboBox.getSelectedItem(); // Lấy giá trị "Loại sản phẩm" từ JComboBox
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Định dạng ngày theo "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String ngaytao = currentDate.format(formatter); // Sẽ trả về ngày hiện tại theo định dạng mong muốn

        // Trả về đối tượng SanPhamDTO với thông tin đã lấy
        return new SanPhamDTO(
                masp, // Mã sản phẩm
                tensp, // Tên sản phẩm
                hinhanh, // Hình ảnh
                thuonghieu, // Thương hiệu
                series, // Series (hoặc Anime)
                nhanvat, // Nhân vật
                tyle, // Tỷ lệ mô hình
                chatlieu, // Chất liệu mô hình
                xuatxu, // Xuất xứ
                khuvuckho, // Khu vực kho
                loaiSanPham, // Loại sản phẩm
                0, // Số lượng tồn (mặc định là 0)
                1, // Trạng thái (mặc định là 1)
                ngaytao // Ngày tạo
        );
    }

    public void setInfo(SanPhamDTO sp) {
        // Đảm bảo rằng bạn thiết lập thông tin cho các trường trong form dựa trên dữ
        // liệu trong sp

        // Cập nhật hình ảnh
        hinhanh.setUrl_img(sp.getHinhanh()); // Hình ảnh (Lấy URL của hình ảnh)

        // Cập nhật tên sản phẩm
        tenSP.setText(sp.getTensp()); // Tên sản phẩm

        // Cập nhật xuất xứ
        xuatxu.setSelectedIndex(xuatXuBUS.getIndexByMaXX(sp.getXuatxu())); // Xuất xứ (chọn theo index từ danh sách xuất
                                                                           // xứ)

        // Cập nhật thương hiệu
        thuonghieu.setSelectedIndex(thuonghieuBus.getIndexByMaLH(sp.getThuonghieu())); // Thương hiệu (chọn theo index
                                                                                       // từ danh sách thương hiệu)

        // Cập nhật hệ điều hành/series (series hoặc anime)
        String seriesName = sp.getSeries(); // Giả sử sp.getSeries() trả về tên series (String)
        int seriesIndex = -1;
        for (SeriesDTO series : seriesBUS.getAll()) {
            if (series.getTenSeries().equalsIgnoreCase(seriesName)) {
                seriesIndex = series.getMaSeries();
                break; // Dừng khi tìm thấy
            }
        }

        // Nếu tìm thấy series, cập nhật chỉ mục cho hedieuhanh
        if (seriesIndex != -1) {
            hedieuhanh.setSelectedIndex(seriesBUS.getIndexByMaSeries(seriesIndex));
        } else {
            // Xử lý khi không tìm thấy series
            System.out.println("Không tìm thấy series với tên: " + seriesName);
        }

        // Cập nhật khu vực kho
        khuvuc.setSelectedIndex(jpSP.spBUS.getIndexByMaSP(sp.getKhuvuckho())); // Khu vực kho (chọn theo index từ danh
                                                                               // sách khu vực kho)

        // Các thuộc tính mô hình khác như Nhân vật, Tỷ lệ, Chất liệu
        nhanvat.setText(sp.getNhanvat()); // Nhân vật (có thể là tên nhân vật trong series hoặc anime)
        tyle.setText(sp.getTyle()); // Tỷ lệ mô hình (ví dụ: 1/7, 1/8)
        chatlieu.setText(sp.getChatlieu()); // Chất liệu mô hình
    }

    public PhienBanSanPhamDTO getCauHinh() {
        // Lấy thông tin từ các ComboBox và TextField
        String tenphienban = this.tenSP.getText(); // Lấy tên phiên bản từ trường nhập liệu (InputForm)

        int gianhap = Integer.parseInt(txtgianhap.getText()); // Giá nhập
        int giaban = Integer.parseInt(txtgiaxuat.getText()); // Giá bán
        int soluongton = 0; // Bạn có thể gán số lượng tồn mặc định là 0 hoặc lấy từ một trường nhập liệu
                            // khác nếu có

        // Tạo đối tượng PhienBanSanPhamDTO với thông tin mới
        PhienBanSanPhamDTO chsp = new PhienBanSanPhamDTO(mach, masp, tenphienban, gianhap, giaban, soluongton, 1);
        mach++; // Tăng giá trị cho mã cấu hình sản phẩm

        return chsp; // Trả về đối tượng PhienBanSanPhamDTO mới
    }

    public PhienBanSanPhamDTO getCauHinh(int masanpham) {
        String tenphienban = "Phiên bản X"; // Ví dụ, bạn có thể lấy tên phiên bản từ một trường nhập liệu
        int gianhap = Integer.parseInt(txtgianhap.getText()); // Giá nhập
        int giaban = Integer.parseInt(txtgiaxuat.getText()); // Giá bán
        int soluongton = 0; // Giả sử bạn để mặc định là 0

        // Tạo đối tượng PhienBanSanPhamDTO với thông tin đã lấy
        PhienBanSanPhamDTO chsp = new PhienBanSanPhamDTO(
                PhienBanSanPhamDAO.getInstance().getAutoIncrement(), // Tự động tăng mã cấu hình sản phẩm
                masanpham, // Mã sản phẩm
                tenphienban, // Tên phiên bản
                gianhap, // Giá nhập
                giaban, // Giá bán
                soluongton, // Số lượng tồn
                1 // Trạng thái
        );

        // Thêm cấu hình sản phẩm vào danh sách nếu cần
        this.listch.add(chsp);

        return chsp; // Trả về đối tượng PhienBanSanPhamDTO mới
    }

    public boolean validateCardOne() {
        boolean check = true;
        if (Validation.isEmpty(tenSP.getText()) || Validation.isEmpty((String) xuatxu.getValue())
                || Validation.isEmpty(chipxuly.getText()) || Validation.isEmpty(dungluongpin.getText())
                || Validation.isEmpty(kichthuocman.getText()) || Validation.isEmpty(hedieuhanh.getValue())
                || Validation.isEmpty(camerasau.getText()) || Validation.isEmpty(cameratruoc.getText())
                || Validation.isEmpty(thoigianbaohanh.getText()) || Validation.isEmpty(phienbanhdh.getText())) {
            check = false;
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
        } else {
            // Check number
        }
        return check;
    }

    public boolean validateCardTwo() {
        boolean check = true;
        if (Validation.isEmpty(txtgianhap.getText()) && Validation.isEmpty(txtgiaxuat.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin !");
            check = false;
        } else {

        }
        return check;
    }

    public boolean checkTonTai() {
        boolean check = true;

        // Tạo đối tượng PhienBanSanPhamBUS (nếu chưa có đối tượng)
        PhienBanSanPhamBUS phienBanSanPhamBUS = new PhienBanSanPhamBUS();

        // Kiểm tra sự trùng lặp của các cấu hình dựa trên các thuộc tính liên quan đến
        // figure
        if (phienBanSanPhamBUS.checkDuplicate(listch, getCauHinh())) { // Sử dụng đối tượng để gọi phương thức
            JOptionPane.showMessageDialog(this, "Mô hình đã tồn tại !");
            check = false;
        }

        return check;
    }

    public void loadDataToTableCauHinh(ArrayList<PhienBanSanPhamDTO> ch) {
        tblModelch.setRowCount(0); // Xóa hết dữ liệu cũ trong bảng
        for (int i = 0; i < ch.size(); i++) {
            // Lấy thông tin từ đối tượng PhienBanSanPhamDTO
            int gianhap = ch.get(i).getGianhap(); // Giá nhập
            int giaxuat = ch.get(i).getGiaxuat(); // Giá xuất

            // Thêm thông tin vào bảng (bỏ mausac, tyle và chatlieu)
            tblModelch.addRow(new Object[] {
                    gianhap,
                    giaxuat
            });
        }
    }

    public void resetFormCauHinh() {
        cbxMausac.setSelectedIndex(0); // Đặt lại màu sắc mặc định
        cbxTyle.setSelectedIndex(0); // Đặt lại tỷ lệ mặc định
        cbxChatlieu.setSelectedIndex(0); // Đặt lại chất liệu mặc định
        txtgianhap.setText(""); // Đặt lại giá nhập
        txtgiaxuat.setText(""); // Đặt lại giá xuất
    }

    public void setInfoCauHinh(PhienBanSanPhamDTO ch) {
        // Lấy và thiết lập màu sắc, tỷ lệ và chất liệu từ dữ liệu cấu hình
        txtgianhap.setText(Integer.toString(ch.getGianhap())); // Giá nhập
        txtgiaxuat.setText(Integer.toString(ch.getGiaxuat())); // Giá xuất
    }

}
