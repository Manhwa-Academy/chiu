package GUI.Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.net.URI;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import DAO.ChiTietQuyenDAO;
import DAO.NhanVienDAO;
import DAO.NhomQuyenDAO;
import DTO.ChiTietQuyenDTO;
import DTO.NhanVienDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.Log_In;
import GUI.Main;
import GUI.Dialog.MyAccount;
import GUI.Panel.KhachHang;
import GUI.Panel.KhuVucKho;
import GUI.Panel.NhaCungCap;
import GUI.Panel.NhanVien;
import GUI.Panel.PhanQuyen;
import GUI.Panel.PhieuNhap;
import GUI.Panel.PhieuXuat;
import GUI.Panel.QuanLyThuocTinhSP;
import GUI.Panel.SanPham;
import GUI.Panel.TaiKhoan;
import GUI.Panel.TrangChu;
import GUI.Panel.ThongKe.ThongKe;

//import GUI.Component.itemTaskbar;
public class MenuTaskbar extends JPanel {
    private JPanel infoPanel;
    TrangChu trangChu;
    SanPham sanPham;
    QuanLyThuocTinhSP quanLyThuocTinhSP;
    KhuVucKho quanLyKho;
    PhieuNhap phieuNhap;
    PhieuXuat phieuXuat;
    KhachHang khachHang;
    NhaCungCap nhacungcap;
    NhanVien nhanVien;
    TaiKhoan taiKhoan;
    PhanQuyen phanQuyen;
    ThongKe thongKe;
    String[][] getSt = {
            { "Trang chủ", "home.svg", "trangchu" },
            { "Sản phẩm", "product.svg", "sanpham" },
            { "Thuộc tính", "brand.svg", "thuoctinh" },
            { "Khu vực kho", "area.svg", "khuvuckho" },
            { "Phiếu nhập", "import.svg", "nhaphang" },
            { "Phiếu xuất", "export.svg", "xuathang" },
            { "Khách hàng", "customer.svg", "khachhang" },
            { "Nhà cung cấp", "supplier.svg", "nhacungcap" },
            { "Nhân viên", "staff.svg", "nhanvien" },
            { "Tài khoản", "account.svg", "taikhoan" },
            { "Thống kê", "statistical.svg", "thongke" },
            { "Phân quyền", "permission.svg", "nhomquyen" },
            { "Đăng xuất", "log_out.svg", "dangxuat" },
    };

    Main main;
    TaiKhoanDTO user;
    public itemTaskbar[] listitem;

    JLabel lblTenNhomQuyen, lblUsername;
    JScrollPane scrollPane;

    // tasbarMenu chia thành 3 phần chính là pnlCenter, pnlTop, pnlBottom
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);
    private ArrayList<ChiTietQuyenDTO> listQuyen;
    NhomQuyenDTO nhomQuyenDTO;
    public NhanVienDTO nhanVienDTO;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);

    public MenuTaskbar(Main main) {
        this.main = main;
        initComponent();
    }

    public MenuTaskbar(Main main, TaiKhoanDTO tk) {
        this.main = main;
        this.user = tk;
        this.nhomQuyenDTO = NhomQuyenDAO.getInstance().selectById(Integer.toString(tk.getManhomquyen()));
        this.nhanVienDTO = NhanVienDAO.getInstance().selectById(Integer.toString(tk.getManv()));
        listQuyen = ChiTietQuyenDAO.getInstance().selectAll(Integer.toString(tk.getManhomquyen()));
        initComponent();
    }

    private void initComponent() {
        listitem = new itemTaskbar[getSt.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        // bar1, bar là các đường kẻ mỏng giữa taskbarMenu và MainContent
        pnlTop = new JPanel();
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(DefaultColor);
        pnlTop.setLayout(new BorderLayout(0, 0));
        this.add(pnlTop, BorderLayout.NORTH);

        infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout(0, 0));
        pnlTop.add(infoPanel, BorderLayout.CENTER);

        in4(infoPanel);

        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        pnlTop.add(bar2, BorderLayout.SOUTH);

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        // pnlCenter.setBorder(new EmptyBorder(0,15,0,35));
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));

        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        this.add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < getSt.length; i++) {
            if (i + 1 == getSt.length) {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlBottom.add(listitem[i]);
            } else {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlCenter.add(listitem[i]);
                if (i != 0) {
                    if (!checkRole(getSt[i][2])) {
                        listitem[i].setVisible(false);
                    }
                }
            }
        }

        listitem[0].setBackground(HowerBackgroundColor);
        listitem[0].setForeground(HowerFontColor);
        listitem[0].isSelected = true;

        for (int i = 0; i < getSt.length; i++) {
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }

        listitem[0].addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                trangChu = new TrangChu();
                main.setPanel(trangChu);
            }
        });

        listitem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                sanPham = new SanPham(main);
                main.setPanel(sanPham);

            }
        });
        listitem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                quanLyThuocTinhSP = new QuanLyThuocTinhSP(main);
                main.setPanel(quanLyThuocTinhSP);
            }
        });
        listitem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                quanLyKho = new KhuVucKho(main);
                main.setPanel(quanLyKho);
            }
        });
        // listitem[4].addMouseListener(new MouseAdapter() {
        // @Override
        // public void mousePressed(MouseEvent evt) {
        // PhieuKiemKe kiemKe = new PhieuKiemKe(main, nhanVienDTO);
        // main.setPanel(kiemKe);
        // }
        // });

        listitem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                phieuNhap = new PhieuNhap(main, nhanVienDTO);
                main.setPanel(phieuNhap);
            }
        });
        listitem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                phieuXuat = new PhieuXuat(main, user);
                main.setPanel(phieuXuat);
            }
        });
        listitem[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                khachHang = new KhachHang(main);
                main.setPanel(khachHang);
            }
        });
        listitem[7].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                nhacungcap = new NhaCungCap(main);
                main.setPanel(nhacungcap);
            }
        });

        listitem[8].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                nhanVien = new NhanVien(main);
                main.setPanel(nhanVien);
            }
        });
        listitem[9].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                taiKhoan = new TaiKhoan(main);
                main.setPanel(taiKhoan);
            }
        });
        listitem[10].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                thongKe = new ThongKe();
                main.setPanel(thongKe);
            }
        });

        listitem[11].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {

                phanQuyen = new PhanQuyen(main);
                main.setPanel(phanQuyen);
            }
        });

        listitem[12].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {

                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    Log_In login = new Log_In();
                    main.dispose();
                    login.setVisible(true);
                }
            }
        });
    }

    public boolean checkRole(String s) {
        boolean check = false;
        for (int i = 0; i < listQuyen.size(); i++) {
            if (listQuyen.get(i).getHanhdong().equals("view")) {
                if (s.equals(listQuyen.get(i).getMachucnang())) {
                    check = true;
                    return check;
                }
            }
        }
        return check;
    }

    public void refreshUserInfo() {
        infoPanel.removeAll(); // Xóa thông tin cũ
        this.nhanVienDTO = new NhanVienDAO().selectById(String.valueOf(nhanVienDTO.getManv())); // Cập nhật lại thông
                                                                                                // tin nhân viên mới
        in4(infoPanel); // Gọi lại phương thức in4 để hiển thị thông tin mới
        infoPanel.revalidate(); // Cập nhật lại giao diện
        infoPanel.repaint(); // Vẽ lại giao diện sau khi cập nhật
    }

    public void pnlMenuTaskbarMousePress(MouseEvent evt) {

        for (int i = 0; i < getSt.length; i++) {
            if (evt.getSource() == listitem[i]) {
                listitem[i].isSelected = true;
                listitem[i].setBackground(HowerBackgroundColor);
                listitem[i].setForeground(HowerFontColor);
            } else {
                listitem[i].isSelected = false;
                listitem[i].setBackground(DefaultColor);
                listitem[i].setForeground(FontColor);
            }
        }
    }

    public void resetChange() {
        this.nhanVienDTO = new NhanVienDAO().selectById(String.valueOf(nhanVienDTO.getManv()));
    }

    public BufferedImage makeRoundedImage(Image image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(circle);

        g2.drawImage(image, 0, 0, diameter, diameter, null);
        g2.dispose();

        return output;
    }

    public void in4(JPanel info) {
        JPanel pnlIcon = new JPanel(new GridBagLayout());
        pnlIcon.setPreferredSize(new Dimension(60, 0));
        pnlIcon.setOpaque(false);

        info.add(pnlIcon, BorderLayout.WEST);

        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(50, 50));

        // ====== ƯU TIÊN AVATAR ======
        String avatar = nhanVienDTO.getAvatar();
        if (avatar != null && !avatar.trim().isEmpty()) {
            try {
                // Chuyển chuỗi thành URI rồi chuyển sang URL
                URI uri = new URI(avatar);
                URL url = uri.toURL(); // Chuyển URI thành URL
                ImageIcon icon = new ImageIcon(url); // Sử dụng ImageIcon cho ảnh GIF hoặc ảnh bất kỳ

                // Kích thước nhỏ hơn cho avatar, bạn có thể thay đổi giá trị này
                int avatarSize = 50; // Kích thước nhỏ hơn cho avatar

                // Bo tròn hình ảnh avatar
                BufferedImage bufferedImage = new BufferedImage(avatarSize, avatarSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Tính toán hình tròn dựa trên kích thước mới
                g2.setClip(new Ellipse2D.Double(0, 0, avatarSize, avatarSize)); // Bo tròn ở đây
                g2.drawImage(icon.getImage(), 0, 0, avatarSize, avatarSize, null); // Vẽ ảnh đã bo tròn và chỉnh kích
                                                                                   // thước nhỏ hơn
                g2.dispose();

                // Đặt ảnh bo tròn vào JLabel
                lblIcon.setIcon(new ImageIcon(bufferedImage));
            } catch (Exception e) {
                // Nếu có lỗi trong việc tải ảnh từ URL, bạn có thể thêm một ảnh mặc định
                lblIcon.setIcon(new FlatSVGIcon("img/login1.gif"));
            }
        } else {
            // Nếu không có avatar, sử dụng ảnh mặc định theo giới tính
            if (nhanVienDTO.getGioitinh() == 1) {
                lblIcon.setIcon(new FlatSVGIcon("icon/man_50px.svg"));
            } else {
                lblIcon.setIcon(new FlatSVGIcon("icon/woman_50px.svg"));
            }
        }

        pnlIcon.add(lblIcon);

        // ====== THÔNG TIN TÊN + QUYỀN ======
        JPanel pnlInfo = new JPanel();
        pnlInfo.setOpaque(false);
        pnlInfo.setLayout(new FlowLayout(0, 10, 5));
        pnlInfo.setBorder(new EmptyBorder(15, 0, 0, 0));
        info.add(pnlInfo, BorderLayout.CENTER);

        lblUsername = new JLabel(nhanVienDTO.getHoten());
        lblUsername.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");
        pnlInfo.add(lblUsername);

        lblTenNhomQuyen = new JLabel(nhomQuyenDTO.getTennhomquyen());
        lblTenNhomQuyen.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        lblTenNhomQuyen.setForeground(Color.GRAY);
        pnlInfo.add(lblTenNhomQuyen);

        // ====== CLICK AVATAR → MỞ MY ACCOUNT ======
        lblIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                new MyAccount(owner, MenuTaskbar.this,
                        "Chỉnh sửa thông tin tài khoản", true);
            }
        });
    }
}