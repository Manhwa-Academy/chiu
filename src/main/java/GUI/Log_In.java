package GUI;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.Component.InputForm;
import GUI.Dialog.QuenMatKhau;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;

import java.sql.Date;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import helper.BCrypt;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Log_In extends JFrame implements KeyListener {

    JPanel pnlMain, pnlLogIn;
    JLabel lblImage, lbl3, lbl6, lbl7;
    InputForm txtUsername, txtPassword;
    Color FontColor = new Color(96, 125, 139);

    public Log_In() {
        initComponent();
        changeTextColor(); // Bắt đầu thay đổi màu sắc cầu vồng cho chữ

    }

    private void initComponent() {
        this.setSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Đăng nhập");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame jf = this;

        imgIntro();

        // Khởi tạo pnlMain
        pnlMain = new JPanel();
        pnlMain.setBackground(Color.white);
        pnlMain.setBorder(new EmptyBorder(20, 0, 0, 0));
        pnlMain.setPreferredSize(new Dimension(500, 740));
        pnlMain.setLayout(new FlowLayout(1, 0, 10));

        // Khởi tạo JLabel với HTML để thay đổi màu sắc
        lbl3 = new JLabel(
                "<html><font color='red'>ĐĂNG</font> <font color='blue'>NHẬP</font> <font color='green'>VÀO</font> <font color='purple'>HỆ</font> <font color='orange'>THỐNG</font></html>");
        lbl3.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        pnlMain.add(lbl3);

        JPanel paneldn = new JPanel();
        paneldn.setBackground(Color.BLACK);
        paneldn.setPreferredSize(new Dimension(400, 200));
        paneldn.setLayout(new GridLayout(2, 1));

        txtUsername = new InputForm("Tên đăng nhập");
        paneldn.add(txtUsername);
        txtPassword = new InputForm("Mật khẩu", "password");
        paneldn.add(txtPassword);

        txtUsername.getTxtForm().addKeyListener(this);
        txtPassword.getTxtPass().addKeyListener(this);

        pnlMain.add(paneldn);

        // Khởi tạo pnlLogIn
        pnlLogIn = new JPanel();
        pnlLogIn.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setPreferredSize(new Dimension(380, 45));
        pnlLogIn.setLayout(new FlowLayout(1, 0, 15));

        lbl6 = new JLabel("ĐĂNG NHẬP");
        lbl6.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lbl6.setForeground(Color.white);

        pnlLogIn.add(lbl6);

        pnlLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlLogIn.setBackground(FontColor);
                pnlLogIn.setForeground(Color.black);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    checkLogin();
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Log_In.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                pnlLogIn.setBackground(Color.BLACK);
                pnlLogIn.setForeground(Color.white);
            }
        });

        // Thêm pnlLogIn vào pnlMain
        pnlMain.add(pnlLogIn);
        // Thêm nút "Quên mật khẩu" dưới nút đăng nhập
        lbl7 = new JLabel("Quên mật khẩu", JLabel.CENTER); // Thay JLabel.RIGHT thành JLabel.CENTER để căn giữa
        lbl7.setPreferredSize(new Dimension(200, 50));
        lbl7.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        lbl7.setForeground(Color.BLUE); // Thêm màu xanh cho chữ "Quên mật khẩu"
        lbl7.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thêm hiệu ứng con trỏ tay khi hover vào

        // Thêm MouseListener chỉ cho phép mở form khi click vào chữ
        lbl7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Mở form Quên mật khẩu khi click vào chữ
                QuenMatKhau qmk = new QuenMatKhau(jf, true);
                qmk.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lbl7.setForeground(Color.CYAN); // Thay đổi màu chữ khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lbl7.setForeground(Color.BLUE); // Đặt lại màu chữ khi không hover
            }
        });

        pnlMain.add(lbl7); // Thêm vào panel chính

        // Tạo nút Đăng nhập với Google
        ImageIcon googleIcon = new ImageIcon(getClass().getResource("/img/google.png")); // Đảm bảo rằng đường dẫn tới
                                                                                         // ảnh là đúng

        // Kiểm tra nếu đường dẫn không đúng, bạn có thể dùng file thực tế như sau
        // ImageIcon googleIcon = new
        // ImageIcon("C:\\Users\\vuliz\\Music\\quan_ly_ban_mo_hinh\\img\\google.png");

        // Scale icon cho đẹp (24x24)
        Image img = googleIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        googleIcon = new ImageIcon(img);

        // Tạo nút với cả chữ và icon
        JButton btnGoogle = new JButton("Đăng nhập với", googleIcon);
        btnGoogle.setPreferredSize(new Dimension(380, 45));
        btnGoogle.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 14));
        btnGoogle.setFocusPainted(false);
        btnGoogle.setBackground(Color.WHITE);
        btnGoogle.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Căn chỉnh chữ và icon cho đẹp
        btnGoogle.setHorizontalTextPosition(SwingConstants.LEFT);
        btnGoogle.setIconTextGap(12); // Khoảng cách giữa chữ và icon

        // Thêm nút vào panel
        btnGoogle.addActionListener(e -> loginWithGoogle()); // Sử dụng hàm loginWithGoogle() đã viết
        pnlMain.add(btnGoogle); // Thêm vào panel

        // Thêm panel chính vào frame
        this.add(pnlMain, BorderLayout.EAST);
    }

    public void changeTextColor() {
        Timer timer = new Timer(100, new ActionListener() { // Thay đổi màu sắc mỗi 100ms
            int colorIndex = 0;
            String[] colors = { "red", "orange", "yellow", "green", "blue", "indigo", "violet" }; // Màu sắc cầu vồng

            @Override
            public void actionPerformed(ActionEvent e) {
                // Cập nhật màu sắc của từng phần trong văn bản
                lbl3.setText("<html><font color='" + colors[colorIndex] + "'>ĐĂNG</font> " +
                        "<font color='" + colors[(colorIndex + 1) % colors.length] + "'>NHẬP</font> " +
                        "<font color='" + colors[(colorIndex + 2) % colors.length] + "'>VÀO</font> " +
                        "<font color='" + colors[(colorIndex + 3) % colors.length] + "'>HỆ</font> " +
                        "<font color='" + colors[(colorIndex + 4) % colors.length] + "'>THỐNG</font></html>");

                // Cập nhật chỉ số màu sắc để di chuyển qua các màu tiếp theo
                colorIndex = (colorIndex + 1) % colors.length; // Di chuyển đến màu tiếp theo
            }
        });
        timer.start(); // Bắt đầu chạy Timer
    }

    private void loginWithGoogle() {
        try {
            // Lấy thông tin người dùng từ Google
            String result = GoogleLoginUtil.login();
            if (result == null || result.isEmpty())
                return;

            String[] data = result.split("\\|");
            String name = data[0]; // Tên người dùng
            String email = data[1]; // Email người dùng
            String pictureUrl = data[2]; // URL ảnh đại diện từ Google

            // Kiểm tra xem email đã tồn tại trong hệ thống chưa
            NhanVienDTO nv = NhanVienDAO.getInstance().selectByEmail(email);

            if (nv == null) {
                // Nếu email chưa tồn tại, tạo mới nhân viên
                NhanVienDTO newNhanVien = new NhanVienDTO();
                newNhanVien.setHoten(name); // Cập nhật tên nhân viên
                newNhanVien.setEmail(email); // Cập nhật email
                newNhanVien.setTrangthai(1); // Trạng thái: hoạt động

                // Gán giá trị mặc định cho số điện thoại nếu không có từ Google
                newNhanVien.setSdt("0000000000");

                // Gán giá trị mặc định cho ngày sinh nếu không có từ Google
                newNhanVien.setNgaysinh(Date.valueOf("2000-01-01"));

                // Lưu URL ảnh đại diện từ Google
                newNhanVien.setAvatar(pictureUrl);

                // Thêm nhân viên mới vào cơ sở dữ liệu
                int insertResult = NhanVienDAO.getInstance().insert(newNhanVien);
                if (insertResult == 0) {
                    JOptionPane.showMessageDialog(this, "Không thể tạo tài khoản nhân viên mới", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Sau khi tạo nhân viên, tiếp tục tạo tài khoản
                nv = NhanVienDAO.getInstance().selectByEmail(email);

                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setManv(nv.getManv());
                tk.setUsername(email);
                tk.setMatkhau(BCrypt.hashpw("GOOGLE_LOGIN", BCrypt.gensalt())); // Mật khẩu mặc định
                tk.setManhomquyen(1); // Quyền mặc định
                tk.setTrangthai(1); // Tài khoản hoạt động
                tk.setOtp(null); // Không cần OTP

                TaiKhoanDAO.getInstance().insert(tk); // Thêm tài khoản vào cơ sở dữ liệu
            }

            // Kiểm tra trạng thái tài khoản
            TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByManv(nv.getManv());
            if (tk.getTrangthai() == 0) {
                JOptionPane.showMessageDialog(this, "Tài khoản của bạn đang bị khóa", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Đăng nhập thành công, chuyển đến trang chính
            this.dispose();
            Main main = new Main(tk);
            main.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đăng nhập Google thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void checkLogin() throws UnsupportedLookAndFeelException {
        String usernameCheck = txtUsername.getText();
        String passwordCheck = txtPassword.getPass();
        if (usernameCheck.equals("") || passwordCheck.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ", "Cảnh báo! ",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            TaiKhoanDTO tk = TaiKhoanDAO.getInstance().selectByUser(usernameCheck);
            if (tk == null) {
                JOptionPane.showMessageDialog(this, "Tài khoản của bạn không tồn tại trên hệ thống", "Cảnh báo! ",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                if (tk.getTrangthai() == 0) {
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn đang bị khóa", "Cảnh báo! ",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if (BCrypt.checkpw(passwordCheck, tk.getMatkhau())) {
                        try {
                            this.dispose();
                            Main main = new Main(tk);
                            main.setVisible(true);
                        } catch (UnsupportedLookAndFeelException ex) {
                            Logger.getLogger(Log_In.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Mật khẩu không khớp", "Cảnh báo! ",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        UIManager.put("PasswordField.showRevealButton", true);
        Log_In login = new Log_In();
        login.setVisible(true);
    }

    public void imgIntro() {
        JPanel bo = new JPanel();
        bo.setBorder(new EmptyBorder(3, 10, 5, 5));
        bo.setPreferredSize(new Dimension(520, 500)); // Đảm bảo kích thước panel đủ lớn
        bo.setBackground(Color.white);
        this.add(bo, BorderLayout.WEST);

        lblImage = new JLabel();
        // Đảm bảo rằng GIF chiếm diện tích lớn hơn
        ImageIcon gifIcon = new ImageIcon(Log_In.class.getResource("/img/login1.gif"));
        lblImage.setIcon(gifIcon);
        bo.add(lblImage);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkLogin();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Log_In.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
