package GUI.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.KhachHangBUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.KhachHangDialog;
import helper.JTableExporter;
import helper.Validation;
import java.sql.Timestamp;
public class KhachHang extends JPanel implements ActionListener, ItemListener {

    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableKhachHang;
    JScrollPane scrollTableKhachHang;
    MainFunction mainFunction;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    IntegratedSearch search;
    DefaultTableModel tblModel;
    public KhachHangBUS khachhangBUS = new KhachHangBUS();
    public ArrayList<KhachHangDTO> listkh = khachhangBUS.getAll();
    KhachHangDTO kh = new KhachHangDTO();
    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tableKhachHang = new JTable();
        scrollTableKhachHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại",
                "Ngày tham gia" };
        tblModel.setColumnIdentifiers(header);
        tableKhachHang.setModel(tblModel);
        tableKhachHang.setFocusable(false);
        scrollTableKhachHang.setViewportView(tableKhachHang);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableKhachHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        tableKhachHang.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableKhachHang, 0, TableSorter.INTEGER_COMPARATOR);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở
        // giữa mà contentCenter không bị dính sát vào các thành phần khác
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

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm
        // kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "delete", "detail", "import", "export" };
        mainFunction = new MainFunction(m.user.getManhomquyen(), "khachhang", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(
                new String[] { "Tất cả", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại" });
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String type = (String) search.cbxChoose.getSelectedItem();
                String txt = search.txtSearchForm.getText();
                listkh = khachhangBUS.search(txt, type);
                loadDataTable(listkh);
            }
        });

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            listkh = khachhangBUS.getAll();
            loadDataTable(listkh);
        });
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        // main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrollTableKhachHang);
    }

    public KhachHang(Main m) {
        this.m = m;
        initComponent();
        tableKhachHang.setDefaultEditor(Object.class, null);
        loadDataTable(listkh);
    }

    public void loadDataTable(ArrayList<KhachHangDTO> result) {
        tblModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Định dạng ngày giờ

        for (KhachHangDTO khachHang : result) {
            tblModel.addRow(new Object[] {
                    khachHang.getMaKH(),
                    khachHang.getHoten(),
                    khachHang.getDiachi(),
                    khachHang.getSdt(),
                    sdf.format(khachHang.getNgaythamgia()) // Định dạng ngày tham gia
            });
        }
    }

    public int getRowSelected() {
        int index = tableKhachHang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
        }
        return index;
    }

    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    XSSFRow excelRow = excelSheet.getRow(row);
                    int id = KhachHangDAO.getInstance().getAutoIncrement();
                    String tenkh = excelRow.getCell(0).getStringCellValue();
                    String sdt = excelRow.getCell(1).getStringCellValue();
                    String diachi = excelRow.getCell(2).getStringCellValue();

                    // Kiểm tra tính hợp lệ
                    if (Validation.isEmpty(tenkh) || Validation.isEmpty(sdt) || !isPhoneNumber(sdt)
                            || sdt.length() != 10 || Validation.isEmpty(diachi)) {
                        check = 0;
                    }

                    // Thêm khách hàng vào danh sách nếu hợp lệ
                    if (check == 1) {
                        // Tạo Timestamp cho ngày tham gia
                        Timestamp ngaythamgia = new Timestamp(System.currentTimeMillis());
                        // Dùng constructor có Timestamp
                        khachhangBUS.add(new KhachHangDTO(id, tenkh, sdt, diachi, ngaythamgia));
                    } else {
                        k += 1;
                    }
                }
                JOptionPane.showMessageDialog(this, "Nhập thành công");
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }
        if (k != 0) {
            JOptionPane.showMessageDialog(this, "Những dữ liệu không hợp lệ không được thêm vào");
        }
        loadDataTable(listkh);
    }

    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) { // Kiểm tra số điện thoại 10 chữ số
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            // Mở dialog thêm khách hàng
            new KhachHangDialog(this, owner, "Thêm khách hàng", true, "create");

        } else if (e.getSource() == mainFunction.btn.get("update")) {
            // Chỉnh sửa khách hàng
            int index = getRowSelected();
            if (index != -1) {
                new KhachHangDialog(this, owner, "Chỉnh sửa khách hàng", true, "update", listkh.get(index));
            }

        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            // Xóa nhiều khách hàng
            int[] selectedRows = tableKhachHang.getSelectedRows(); // Lấy tất cả các dòng đã chọn
            ArrayList<KhachHangDTO> selectedCustomers = new ArrayList<>();

            for (int row : selectedRows) {
                int customerId = (int) tableKhachHang.getValueAt(row, 0); // Lấy mã khách hàng từ cột đầu tiên
                KhachHangDTO customer = khachhangBUS.getById(customerId); // Lấy đối tượng khách hàng từ mã
                selectedCustomers.add(customer); // Thêm khách hàng vào danh sách chọn
            }

            if (!selectedCustomers.isEmpty()) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa " + selectedCustomers.size() + " khách hàng?", "Xóa khách hàng",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (input == JOptionPane.OK_OPTION) {
                    // Xóa nhiều khách hàng
                    int result = khachhangBUS.deleteMultiple(selectedCustomers); // Gọi phương thức xóa nhiều khách hàng
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, result + " khách hàng đã được xóa.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // Cập nhật lại bảng sau khi xóa
                    listkh = khachhangBUS.getAll();
                    loadDataTable(listkh); // Cập nhật lại bảng
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một khách hàng để xóa!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                new KhachHangDialog(this, owner, "Xem khách hàng", true, "view", listkh.get(index));
            }

        } else if (e.getSource() == mainFunction.btn.get("import")) {
            // Nhập dữ liệu từ Excel
            importExcel();

        } else if (e.getSource() == mainFunction.btn.get("export")) {
            // Xuất dữ liệu ra Excel
            try {
                JTableExporter.exportJTableToExcel(tableKhachHang);
            } catch (IOException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        listkh = khachhangBUS.search(txt, type);
        loadDataTable(listkh);
    }
}
