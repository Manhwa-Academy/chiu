package GUI.Dialog.ThuocTinhSanPham;

import BUS.CharacterBUS;
import BUS.NhomQuyenBUS;
import DAO.CharacterDAO;
import DTO.ThuocTinhSanPham.CharacterDTO;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Panel.QuanLyThuocTinhSP;
import helper.Validation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class CharacterDialog extends JDialog implements MouseListener {

    HeaderTitle header;
    InputForm txtName;
    JTable table;
    DefaultTableModel tblModel;
    ButtonCustom btnAdd, btnDel, btnUpdate;

    CharacterBUS characterBUS = new CharacterBUS();
    ArrayList<CharacterDTO> list = characterBUS.getAll();
    NhomQuyenBUS nhomquyenBus = new NhomQuyenBUS();

    public CharacterDialog(JFrame owner, QuanLyThuocTinhSP qltt,
            String title, boolean modal, int nhomquyen) {
        super(owner, title, modal);
        initUI();
        loadPermission(nhomquyen);
        loadData(list);
    }

    private void initUI() {
        setSize(420, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        header = new HeaderTitle("CHARACTER");
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        txtName = new InputForm("Tên Character");
        center.add(txtName);

        tblModel = new DefaultTableModel(
                new String[] { "Mã Character", "Tên Character" }, 0);
        table = new JTable(tblModel);
        table.addMouseListener(this);

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(380, 260));
        center.add(sp);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        btnAdd = new ButtonCustom("Thêm", "success", 14);
        btnDel = new ButtonCustom("Xóa", "danger", 14);
        btnUpdate = new ButtonCustom("Sửa", "warning", 14);

        btnAdd.addMouseListener(this);
        btnDel.addMouseListener(this);
        btnUpdate.addMouseListener(this);

        bottom.add(btnAdd);
        bottom.add(btnDel);
        bottom.add(btnUpdate);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadPermission(int nq) {
        if (!nhomquyenBus.checkPermisson(nq, "thuoctinh", "create"))
            btnAdd.setVisible(false);
        if (!nhomquyenBus.checkPermisson(nq, "thuoctinh", "delete"))
            btnDel.setVisible(false);
        if (!nhomquyenBus.checkPermisson(nq, "thuoctinh", "update"))
            btnUpdate.setVisible(false);
    }

    private void loadData(ArrayList<CharacterDTO> list) {
        tblModel.setRowCount(0);
        for (CharacterDTO c : list) {
            tblModel.addRow(new Object[] { c.getMacharacter(), c.getTencharacter() });
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == btnAdd) {
            String name = txtName.getText().trim();
            if (Validation.isEmpty(name))
                return;

            if (!characterBUS.checkDup(name)) {
                JOptionPane.showMessageDialog(this, "Character đã tồn tại!");
                return;
            }

            int id = CharacterDAO.getInstance().getAutoIncrement();
            characterBUS.add(new CharacterDTO(id, name));
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == btnDel) {
            int row = table.getSelectedRow();
            if (row == -1)
                return;
            characterBUS.delete(list.get(row), row);
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == btnUpdate) {
            int row = table.getSelectedRow();
            if (row == -1)
                return;

            String name = txtName.getText().trim();
            if (Validation.isEmpty(name))
                return;

            characterBUS.update(
                    new CharacterDTO(list.get(row).getMacharacter(), name));
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == table) {
            int row = table.getSelectedRow();
            txtName.setText(list.get(row).getTencharacter());
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
