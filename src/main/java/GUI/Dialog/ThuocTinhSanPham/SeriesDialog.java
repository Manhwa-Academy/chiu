package GUI.Dialog.ThuocTinhSanPham;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import BUS.SeriesBUS; // Đổi từ HeDieuHanhBUS thành SeriesBUS
import DAO.SeriesDAO; // Đổi từ HeDieuHanhDAO thành SeriesDAO
import DTO.ThuocTinhSanPham.SeriesDTO; // Đổi từ HeDieuHanhDTO thành SeriesDTO
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Panel.QuanLyThuocTinhSP;
import helper.Validation;


public class SeriesDialog extends JDialog implements MouseListener {

    HeaderTitle headTite;
    JPanel top, main, bottom;
    InputForm ms;
    DefaultTableModel tblModel;
    JTable table;
    JScrollPane scrollTable;
    ButtonCustom add, del, update;
    SeriesBUS msBUS = new SeriesBUS(); // Đổi từ HeDieuHanhBUS thành SeriesBUS
    ArrayList<SeriesDTO> list = msBUS.getAll(); // Đổi từ HeDieuHanhDTO thành SeriesDTO
    QuanLyThuocTinhSP qltt;
  

    public SeriesDialog(JFrame owner, QuanLyThuocTinhSP qltt, String title, boolean modal, int nhomquyen) {
        super(owner, title, modal);
        initComponent(qltt);
        loadQuyen(nhomquyen);
        loadDataTable(list);
    }

    public void loadQuyen(int nhomquyen) {
        // Thêm logic quyền ở đây nếu cần
        // Ví dụ: kiểm tra quyền của người dùng cho các thao tác như thêm, xóa, sửa
    }

    public void initComponent(QuanLyThuocTinhSP qltt) {
        this.qltt = qltt;
        this.setSize(new Dimension(425, 500));
        this.setLayout(new BorderLayout(0, 0));
        this.setResizable(false);
        headTite = new HeaderTitle("SERIES"); // Thay đổi tiêu đề thành Series
        this.setBackground(Color.white);
        top = new JPanel();
        main = new JPanel();
        bottom = new JPanel();

        top.setLayout(new GridLayout(1, 1));
        top.setBackground(Color.WHITE);
        top.setPreferredSize(new Dimension(0, 70));
        top.add(headTite);

        main.setBackground(Color.WHITE);
        main.setPreferredSize(new Dimension(420, 200));
        ms = new InputForm("Tên Series"); // Thay đổi "Tên hệ điều hành" thành "Tên Series"
        ms.setPreferredSize(new Dimension(150, 70));
        table = new JTable();
        table.setBackground(Color.WHITE);
        table.addMouseListener(this);
        scrollTable = new JScrollPane(table);
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã Series", "Tên Series" }; // Thay đổi tên cột
        tblModel.setColumnIdentifiers(header);
        table.setModel(tblModel);
        scrollTable.setViewportView(table);
        scrollTable.setPreferredSize(new Dimension(420, 250));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        main.add(ms);
        main.add(scrollTable);

        add = new ButtonCustom("Thêm", "excel", 15, 100, 40);
        add.addMouseListener(this);
        del = new ButtonCustom("Xóa", "danger", 15, 100, 40);
        del.addMouseListener(this);
        update = new ButtonCustom("Sửa", "success", 15, 100, 40);
        update.addMouseListener(this);
        bottom.setBackground(Color.white);
        bottom.setLayout(new FlowLayout(1, 20, 20));
        bottom.add(add);
        bottom.add(del);
        bottom.add(update);
        bottom.setPreferredSize(new Dimension(0, 70));

        this.add(top, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
    }

    public void loadDataTable(ArrayList<SeriesDTO> list) {
        tblModel.setRowCount(0);
        for (SeriesDTO series : list) {
            tblModel.addRow(new Object[]{series.getMaSeries(), series.getTenSeries()});
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == add) {
            if (Validation.isEmpty(ms.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên series mới");
            } else {
                String tenSeries = ms.getText(); // Thay đổi "tenmau" thành "tenSeries"
                if (msBUS.checkDup(tenSeries)) {
                    int id = SeriesDAO.getInstance().getAutoIncrement(); // Đổi từ HeDieuHanhDAO thành SeriesDAO
                    msBUS.add(new SeriesDTO(id, tenSeries)); // Đổi từ HeDieuHanhDTO thành SeriesDTO
                    loadDataTable(list);
                    ms.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Series đã tồn tại !");
                }
            }
        } else if (e.getSource() == del) {
            int index = getRowSelected();
            if (index != -1) {
                msBUS.delete(list.get(index), index);
                loadDataTable(list);
                ms.setText("");
            }
        } else if (e.getSource() == update) {
            int index = getRowSelected();
            if (index != -1) {
                if (Validation.isEmpty(ms.getText())) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên series");
                } else {
                    String tenSeries = ms.getText(); // Thay đổi "tenmau" thành "tenSeries"
                    if (msBUS.checkDup(tenSeries)) {
                        msBUS.update(new SeriesDTO(list.get(index).getMaSeries(), tenSeries)); // Đổi từ HeDieuHanhDTO thành SeriesDTO
                        loadDataTable(list);
                        ms.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Series đã tồn tại !");
                    }
                }
            }
        } else if (e.getSource() == table) {
            int index = table.getSelectedRow();
            ms.setText(list.get(index).getTenSeries()); // Đổi từ HeDieuHanhDTO thành SeriesDTO
        }
    }

    public int getRowSelected() {
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Series!");
        }
        return index;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }
}
