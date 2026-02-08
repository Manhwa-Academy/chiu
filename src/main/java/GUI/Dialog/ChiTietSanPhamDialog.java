package GUI.Dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.PhienBanSanPhamBUS;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.SelectForm;

public class ChiTietSanPhamDialog extends JDialog implements ItemListener {

    // ===== UI =====
    HeaderTitle titlePage;
    JPanel pnMain, pnTop, pnBottom;
    JPanel pnTopLeft;

    SelectForm cbxPhienBan;
    InputForm txtSoLuong;

    JTable table;
    DefaultTableModel tblModel;
    JScrollPane scrollTable;

    // ===== DATA =====
    SanPhamDTO sanPham;
    ArrayList<PhienBanSanPhamDTO> listPhienBan = new ArrayList<>();

    // ===== BUS =====
    PhienBanSanPhamBUS phienBanBUS = new PhienBanSanPhamBUS();

    // ===== CONSTRUCTOR =====
    public ChiTietSanPhamDialog(JFrame owner, String title, boolean modal, SanPhamDTO sp) {
        super(owner, title, modal);
        this.sanPham = sp;

        initComponent(title);

        // Load dữ liệu phiên bản
        listPhienBan = phienBanBUS.getAll(sp.getMasp());
        cbxPhienBan.setArr(getTenPhienBan());
        loadTable(listPhienBan);

        this.setVisible(true);
    }

    // ===== INIT UI =====
    private void initComponent(String title) {
        this.setSize(new Dimension(800, 420));
        this.setLayout(new BorderLayout(0, 0));
        this.setLocationRelativeTo(null);

        titlePage = new HeaderTitle(title.toUpperCase());
        this.add(titlePage, BorderLayout.NORTH);

        pnMain = new JPanel(new BorderLayout(5, 5));
        pnMain.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(pnMain, BorderLayout.CENTER);

        // ===== TOP =====
        pnTop = new JPanel(new BorderLayout());
        pnTopLeft = new JPanel(new GridLayout(1, 2, 10, 0));

        cbxPhienBan = new SelectForm("Phiên bản", new String[] {});
        cbxPhienBan.cbb.addItemListener(this);

        txtSoLuong = new InputForm("Số lượng phiên bản");
        txtSoLuong.setEditable(false);

        pnTopLeft.add(cbxPhienBan);
        pnTopLeft.add(txtSoLuong);

        pnTop.add(pnTopLeft, BorderLayout.WEST);
        pnMain.add(pnTop, BorderLayout.NORTH);

        // ===== TABLE =====
        pnBottom = new JPanel(new BorderLayout());
        tblModel = new DefaultTableModel(
                new String[] {
                        "STT",
                        "Tên phiên bản",
                        "Giá nhập",
                        "Giá bán",
                        "Tồn kho"
                },
                0);

        table = new JTable(tblModel);
        centerTable(table);

        scrollTable = new JScrollPane(table);
        pnBottom.add(scrollTable, BorderLayout.CENTER);

        pnMain.add(pnBottom, BorderLayout.CENTER);
    }

    // ===== LOAD TABLE =====
    private void loadTable(ArrayList<PhienBanSanPhamDTO> list) {
        tblModel.setRowCount(0);
        int i = 1;

        for (PhienBanSanPhamDTO pb : list) {
            tblModel.addRow(new Object[] {
                    i++,
                    pb.getTenphienban(),
                    pb.getGianhap(),
                    pb.getGiaxuat(),
                    pb.getSoluongton() > 0 ? "Còn hàng" : "Hết hàng"
            });
        }

        txtSoLuong.setText(String.valueOf(list.size()));
    }

    // ===== COMBO DATA =====
    private String[] getTenPhienBan() {
        String[] arr = new String[listPhienBan.size()];
        for (int i = 0; i < listPhienBan.size(); i++) {
            arr[i] = listPhienBan.get(i).getTenphienban();
        }
        return arr;
    }

    // ===== EVENT =====
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbxPhienBan.cbb && e.getStateChange() == ItemEvent.SELECTED) {
            int index = cbxPhienBan.cbb.getSelectedIndex();
            if (index >= 0 && index < listPhienBan.size()) {
                ArrayList<PhienBanSanPhamDTO> one = new ArrayList<>();
                one.add(listPhienBan.get(index));
                loadTable(one);
            }
        }
    }

    // ===== CENTER TABLE =====
    private void centerTable(JTable table) {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);
        table.setFocusable(false);
    }
}
