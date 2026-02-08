package GUI.Dialog.ThuocTinhSanPham;

import BUS.DacDiemSanPhamBUS;
import BUS.NhomQuyenBUS;
import DAO.DacDiemSanPhamDAO;
import DTO.ThuocTinhSanPham.DacDiemSanPhamDTO;
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

import javax.swing.table.DefaultTableModel;

public class DacDiemSanPhamDialog extends JDialog implements MouseListener {

    HeaderTitle header;
    InputForm txtName;
    JTable table;
    DefaultTableModel tblModel;
    ButtonCustom btnAdd, btnDel, btnUpdate;

    DacDiemSanPhamBUS dacDiemSanPhamBUS = new DacDiemSanPhamBUS();
    ArrayList<DacDiemSanPhamDTO> list = dacDiemSanPhamBUS.getAll();
    NhomQuyenBUS nhomquyenBus = new NhomQuyenBUS();

    public DacDiemSanPhamDialog(JFrame owner, QuanLyThuocTinhSP qltt,
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

        header = new HeaderTitle("ĐẶC ĐIỂM SẢN PHẨM");
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        txtName = new InputForm("Tên đặc điểm sản phẩm");
        center.add(txtName);

        tblModel = new DefaultTableModel(
                new String[] { "Mã Đặc điểm", "Tên Đặc điểm" }, 0);
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

    private void loadData(ArrayList<DacDiemSanPhamDTO> list) {
        tblModel.setRowCount(0);
        for (DacDiemSanPhamDTO p : list) {
            tblModel.addRow(new Object[] { p.getMadacdiem(), p.getTendacdiem() });
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == btnAdd) {
            String name = txtName.getText().trim();
            if (Validation.isEmpty(name))
                return;

            if (!dacDiemSanPhamBUS.checkDup(name)) {
                JOptionPane.showMessageDialog(this, "Đặc điểm sản phẩm đã tồn tại!");
                return;
            }

            int id = DacDiemSanPhamDAO.getInstance().getAutoIncrement();
            dacDiemSanPhamBUS.add(new DacDiemSanPhamDTO(id, name));
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == btnDel) {
            int row = table.getSelectedRow();
            if (row == -1)
                return;
            dacDiemSanPhamBUS.delete(list.get(row), row);
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == btnUpdate) {
            int row = table.getSelectedRow();
            if (row == -1)
                return;

            String name = txtName.getText().trim();
            if (Validation.isEmpty(name))
                return;

            dacDiemSanPhamBUS.update(
                    new DacDiemSanPhamDTO(list.get(row).getMadacdiem(), name));
            loadData(list);
            txtName.setText("");

        } else if (e.getSource() == table) {
            int row = table.getSelectedRow();
            txtName.setText(list.get(row).getTendacdiem());
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
