package GUI.Panel;

import BUS.SanPhamBUS;

import DAO.KhuVucKhoDAO;
import DAO.ThuongHieuDAO;
import DAO.XuatXuDAO;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.ChiTietSanPhamDialog;
import GUI.Dialog.SanPhamDialog;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public final class SanPham extends JPanel implements ActionListener {
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    JTable tableSanPham;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    Main m;
    public SanPhamBUS spBUS = new SanPhamBUS();

    public ArrayList<DTO.SanPhamDTO> listSP = spBUS.getAll();

    Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);
        tableSanPham = new JTable();
        tableSanPham.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        scrollTableSanPham = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] {
                "Mã mô hình",
                "Tên mô hình",
                "Số lượng tồn",
                "Hãng sản xuất",
                "Series / Anime",
                "Nhân vật",
                "Tỷ lệ",
                "Chất liệu",
                "Xuất xứ",
                "Kho lưu trữ"
        };
        tblModel.setColumnIdentifiers(header);
        tableSanPham.setModel(tblModel);
        scrollTableSanPham.setViewportView(tableSanPham);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = tableSanPham.getColumnModel();
        for (int i = 0; i < 10; i++) {
            if (i != 1) {
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
        tableSanPham.setFocusable(false);
        tableSanPham.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableSanPham, 2, TableSorter.INTEGER_COMPARATOR);
        tableSanPham.setDefaultEditor(Object.class, null);
        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm
        // kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "delete", "detail", "phone", "export" };
        mainFunction = new MainFunction(m.user.getManhomquyen(), "sanpham", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(new String[] { "Tất cả" });
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.txtSearchForm.getText();
                listSP = spBUS.search(txt);
                loadDataTalbe(listSP);
            }

        });

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listSP = spBUS.getAll();
            loadDataTalbe(listSP);
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableSanPham);
    }

    public SanPham(Main m) {
        this.m = m;
        initComponent();
        loadDataTalbe(listSP);
    }

    public void loadDataTalbe(ArrayList<DTO.SanPhamDTO> result) {
        tblModel.setRowCount(0);

        for (DTO.SanPhamDTO sp : result) {
            tblModel.addRow(new Object[] {
                    sp.getMasp(), // Mã mô hình
                    sp.getTensp(), // Tên mô hình
                    sp.getSoluongton(), // Số lượng tồn

                    // Hãng sản xuất (ID → tên)
                    ThuongHieuDAO.getInstance()
                            .selectById(String.valueOf(sp.getThuonghieu()))
                            .getTenthuonghieu(),

                    sp.getSeries(), // Series / Anime (STRING)

                    sp.getNhanvat(), // Nhân vật
                    sp.getTyle(), // Tỷ lệ (vd: 1/7, 1/8)
                    sp.getChatlieu(), // Chất liệu

                    // Xuất xứ
                    XuatXuDAO.getInstance()
                            .selectById(String.valueOf(sp.getXuatxu()))
                            .getTenxuatxu(),

                    // Kho
                    KhuVucKhoDAO.getInstance()
                            .selectById(String.valueOf(sp.getKhuvuckho()))
                            .getTenkhuvuc()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mainFunction.btn.get("create")) {
            new SanPhamDialog(this, owner, "Thêm sản phẩm mới", true, "create");

        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                new SanPhamDialog(this, owner, "Chỉnh sửa sản phẩm", true, "update", listSP.get(index));
            }

        } else if (e.getSource() == mainFunction.btn.get("delete")) {

            int[] rows = tableSanPham.getSelectedRows();

            if (rows.length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn ít nhất một sản phẩm",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String message = rows.length == 1
                    ? "Bạn có chắc chắn muốn xóa sản phẩm này?"
                    : "Bạn có chắc chắn muốn xóa " + rows.length + " sản phẩm đã chọn?";

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Xác nhận xóa",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.OK_OPTION) {

                // ✅ XÓA DỰA TRÊN DỮ LIỆU TRONG TABLE (AN TOÀN 100%)
                for (int row : rows) {
                    int modelIndex = tableSanPham.convertRowIndexToModel(row);
                    int maSP = (int) tblModel.getValueAt(modelIndex, 0);
                    spBUS.deleteById(maSP); // ❗ dùng ID
                }

                // ✅ LOAD LẠI TỪ DB → KHÔNG BAO GIỜ SAI
                listSP = spBUS.getAll();
                loadDataTalbe(listSP);

                JOptionPane.showMessageDialog(
                        this,
                        "Xóa sản phẩm thành công",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                new SanPhamDialog(this, owner, "Xem chi tiết sản phẩm", true, "view", listSP.get(index));
            }

        } else if (e.getSource() == mainFunction.btn.get("phone")) {
            int index = getRowSelected();
            if (index != -1) {
                new ChiTietSanPhamDialog(owner, "Tất cả sản phẩm", true, listSP.get(index));
            }

        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableSanPham);
            } catch (IOException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getRowSelected() {
        int index = tableSanPham.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
        }
        return index;
    }

    private void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);
    }

}
