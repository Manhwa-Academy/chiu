
package GUI.Dialog;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.MenuTaskbar;
import GUI.Component.NumericDocumentFilter;
import helper.BCrypt;
import helper.Validation;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.Image;
import java.io.File;
public class MyAccount extends JDialog implements ActionListener {

    CardLayout card;
    ButtonCustom save, cancel;
    HeaderTitle title;
    JPanel top, center, top_center, main_center, bottom;
    InputForm current_pwd, phone, email, new_pwd, confirm;
    NhanVienDTO nv;
    TaiKhoanBUS tkbus;
    NhanVienBUS nvbus;
    MenuTaskbar menuTaskbar;
    JRadioButton[] jbr;
    JPanel[] panel;
    JLabel lblAvatar;
    ButtonCustom btnChooseImg;
    String avatarPath = null;

    public MyAccount(JFrame owner, MenuTaskbar menutaskbar, String title, boolean modal) {
        super(owner, title, modal);
        initComponent(menutaskbar);
        this.setLocationRelativeTo(null);
    }

    public void initComponent(MenuTaskbar menutaskbar) {
        tkbus = new TaiKhoanBUS();
        nvbus = new NhanVienBUS();
        this.menuTaskbar = menutaskbar;
        this.setSize(400, 300);
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(Color.WHITE);
        this.setResizable(false);
        nv = menuTaskbar.nhanVienDTO;
        top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new FlowLayout(0, 0, 0));
        title = new HeaderTitle("CHỈNH SỬA THÔNG TIN");
        top.add(title);
        this.add(top, BorderLayout.NORTH);

        top_center = new JPanel(new GridLayout(1, 4, 10, 0));

        top_center.setBorder(new EmptyBorder(20, 0, 0, 0));
        top_center.setBackground(Color.WHITE);
        main_center = new JPanel();
        main_center.setBorder(new EmptyBorder(0, 20, 0, 20));
        main_center.setBackground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();
        String opt[] = { "Số điện thoại", "Email", "Mật khẩu", "Ảnh đại diện" };
        jbr = new JRadioButton[4];

        for (int i = 0; i < jbr.length; i++) {
            jbr[i] = new JRadioButton();
            jbr[i].addActionListener(this);
            jbr[i].setText(opt[i]);
            top_center.add(jbr[i]);
            bg.add(jbr[i]);
        }
        jbr[0].setSelected(true);

        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(top_center, BorderLayout.NORTH);
        center.add(main_center, BorderLayout.CENTER);

        panel = new JPanel[4];
        panel[0] = new JPanel(new GridLayout(1, 1));
        panel[0].setPreferredSize(new Dimension(400, 100));
        phone = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) phone.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        phone.setText(nv.getSdt());
        panel[0].add(phone);

        panel[1] = new JPanel(new GridLayout(1, 1));
        panel[1].setPreferredSize(new Dimension(400, 100));
        email = new InputForm("Email");
        email.setText(nv.getEmail());
        panel[1].add(email);
        main_center.add(panel[0]);

        panel[2] = new JPanel(new GridLayout(3, 1));
        panel[2].setPreferredSize(new Dimension(400, 300));
        current_pwd = new InputForm("Mật khẩu hiện tại", "password");
        new_pwd = new InputForm("Mật khẩu mới", "password");
        confirm = new InputForm("Nhập lại mật khẩu mới", "password");
        panel[2].add(current_pwd);
        panel[2].add(new_pwd);
        panel[2].add(confirm);
        panel[3] = new JPanel(new FlowLayout(1, 10, 10));
        panel[3].setPreferredSize(new Dimension(400, 250));

        // Avatar
        lblAvatar = new JLabel();
        lblAvatar.setPreferredSize(new Dimension(120, 120));
        lblAvatar.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Nếu đã có avatar thì load
        if (nv.getAvatar() != null) {
            lblAvatar.setIcon(new ImageIcon(
                    new ImageIcon("img/avatar/" + nv.getAvatar())
                            .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        }

        btnChooseImg = new ButtonCustom("Chọn ảnh", "primary", 15);

        btnChooseImg.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                avatarPath = fc.getSelectedFile().getAbsolutePath();
                lblAvatar.setIcon(new ImageIcon(
                        new ImageIcon(avatarPath)
                                .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
            }
        });

        panel[3].add(lblAvatar);
        panel[3].add(btnChooseImg);

        this.add(center, BorderLayout.CENTER);

        bottom = new JPanel(new FlowLayout(1, 20, 10));
        bottom.setBackground(Color.WHITE);

        cancel = new ButtonCustom("Hủy", "danger", 15);
        cancel.addActionListener(this);
        bottom.add(cancel);
        save = new ButtonCustom("Lưu", "success", 15);
        save.addActionListener(this);
        bottom.add(save);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            this.dispose();
        }
        for (int i = 0; i < 4; i++) {
            if (e.getSource() == jbr[i]) {
                if (i == 2 || i == 3) { // mật khẩu hoặc ảnh
                    this.setSize(new Dimension(400, 500));
                    this.setLocationRelativeTo(null);
                } else {
                    this.setSize(400, 300);
                }
                main_center.removeAll();
                main_center.add(panel[i]);
                main_center.repaint();
                main_center.validate();
            }
        }

        if (jbr[0].isSelected()) {
            if (e.getSource() == save) {
                if (Validation.isEmpty(phone.getText()) || phone.getText().length() != 10) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải có 10 ký tự sô",
                            "Chỉnh sửa số điện thoại", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sdt = phone.getText();
                    NhanVienDTO nvdto = new NhanVienDTO(nv.getManv(), nv.getHoten(), nv.getGioitinh(), nv.getNgaysinh(),
                            sdt, nv.getTrangthai(), nv.getEmail());
                    NhanVienDAO.getInstance().update(nvdto);
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
            }
        }

        if (jbr[1].isSelected()) {
            if (e.getSource() == save) {
                if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
                    JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng định dạng",
                            "Chỉnh sửa email", JOptionPane.WARNING_MESSAGE);
                } else {
                    String emailString = email.getText();
                    NhanVienDTO nvdto = new NhanVienDTO(nv.getManv(), nv.getHoten(), nv.getGioitinh(), nv.getNgaysinh(),
                            nv.getSdt(), nv.getTrangthai(), emailString);
                    NhanVienDAO.getInstance().update(nvdto);
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");

                }
            }
        }
        if (jbr[2].isSelected()) {
            if (e.getSource() == save) {

                TaiKhoanDTO tkdto = tkbus.getTaiKhoan(tkbus.getTaiKhoanByMaNV(nv.getManv()));
                if (Validation.isEmpty(current_pwd.getPass())) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không được rỗng", "Cảnh báo!",
                            JOptionPane.WARNING_MESSAGE);
                } else if (Validation.isEmpty(new_pwd.getPass()) || new_pwd.getPass().length() < 6) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự",
                            "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                } else if (Validation.isEmpty(confirm.getPass())) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không được rỗng", "Cảnh báo!",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (new_pwd.getPass().equals(confirm.getPass()) == false) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    if (BCrypt.checkpw(current_pwd.getPass(), tkdto.getMatkhau())) {
                        String pass = BCrypt.hashpw(confirm.getPass(), BCrypt.gensalt(12));
                        TaiKhoanDAO.getInstance().updatePass(nv.getEmail(), pass);
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        current_pwd.setPass("");
                        new_pwd.setPass("");
                        confirm.setPass("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Cảnh báo!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        if (jbr[3].isSelected() && e.getSource() == save) {

            if (avatarPath == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn ảnh đại diện",
                        "Thiếu ảnh",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                File avatarDir = new File("avatar");
                if (!avatarDir.exists()) {
                    avatarDir.mkdirs(); // 🔥 tạo thư mục nếu chưa có
                }

                String fileName = nv.getManv() + ".png";
                File destFile = new File(avatarDir, fileName);

                java.nio.file.Files.copy(
                        new File(avatarPath).toPath(),
                        destFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // update DB
                NhanVienDAO.getInstance().updateAvatar(nv.getManv(), fileName);

                JOptionPane.showMessageDialog(this, "Lưu ảnh đại diện thành công 🎉");

                menuTaskbar.resetChange();
                this.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Không thể lưu ảnh.\nHãy kiểm tra quyền ghi thư mục.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        menuTaskbar.resetChange();
    }
}
