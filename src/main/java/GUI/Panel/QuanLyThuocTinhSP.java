package GUI.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.itemTaskbar;
import GUI.Dialog.ThuocTinhSanPham.CategoryDialog;
import GUI.Dialog.ThuocTinhSanPham.CharacterDialog;
import GUI.Dialog.ThuocTinhSanPham.SeriesDialog;
import GUI.Dialog.ThuocTinhSanPham.DacDiemSanPhamDialog;
import GUI.Dialog.ThuocTinhSanPham.ThuongHieuDialog;
import GUI.Dialog.ThuocTinhSanPham.XuatXuDialog;

public class QuanLyThuocTinhSP extends JPanel {

    private final int n = 20;
    JPanel box[], pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    JTable tableSanPham;
    JScrollPane scrollTableSanPham;
    MainFunction mainFunction;
    IntegratedSearch search;
    JLabel lbl1, lblImage;
    JLabel lbl[], lblIcon[], info;
    JScrollPane scrPane;
    ThuongHieuDialog th;
    XuatXuDialog xs;
    SeriesDialog series;
    CategoryDialog category;
    CharacterDialog character;
    DacDiemSanPhamDialog dacdiem;
    Main m;
    public itemTaskbar[] listitem;

    String iconst[] = { "brand_100px.svg", "global.svg", "series.svg", "box.svg", "character.svg",
            "feature.svg" };

    String header[] = {
            "Thương hiệu",
            "Xuất xứ",
            "Series",
            "Loại sản phẩm",
            "Nhân vật",
            "Đặc điểm"
    };
    Color BackgroundColor = new Color(240, 247, 250);
    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);

    public QuanLyThuocTinhSP(Main m) {
        this.m = m;
        initComponent();
    }

    private void initComponent() {
        listitem = new itemTaskbar[header.length];

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new GridLayout(3, 2, 20, 20));

        // scrPane = new JScrollPane(contentCenter);
        // scrPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(contentCenter, BorderLayout.CENTER);

        box = new JPanel[n];
        lbl = new JLabel[n];
        lblIcon = new JLabel[n];
        for (int i = 0; i < header.length; i++) {
            listitem[i] = new itemTaskbar(iconst[i], header[i], header[i]);
            contentCenter.add(listitem[i]);
        }

        listitem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                th = new ThuongHieuDialog(owner, QuanLyThuocTinhSP.this, "Quản lý thương hiệu", true,
                        m.user.getManhomquyen());
                th.setVisible(true);
            }
        });
        listitem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                xs = new XuatXuDialog(owner, QuanLyThuocTinhSP.this, "Quản lý xuất xứ sản phẩm", true,
                        m.user.getManhomquyen());
                xs.setVisible(true);
            }
        });
        listitem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                series = new SeriesDialog(owner, QuanLyThuocTinhSP.this, "Quản lý series", true,
                        m.user.getManhomquyen());
                series.setVisible(true);
            }
        });

        listitem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                category = new CategoryDialog(owner, QuanLyThuocTinhSP.this, "Quản lý loại sản phẩm", true,
                        m.user.getManhomquyen());
                category.setVisible(true);
            }
        });

        listitem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                character = new CharacterDialog(owner, QuanLyThuocTinhSP.this, "Quản lý nhân vật", true,
                        m.user.getManhomquyen());
                character.setVisible(true);
            }
        });
        listitem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                dacdiem = new DacDiemSanPhamDialog(owner, QuanLyThuocTinhSP.this, "Quản lý đặc điểm sản phẩm", true,
                        m.user.getManhomquyen());
                dacdiem.setVisible(true);
            }
        });
    }

    public void Mouseopress(MouseEvent evt) {
        for (int i = 0; i < listitem.length; i++) {
            if (evt.getSource() == listitem[i]) {

            }
        }
    }

    public void initPadding() {

        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 40));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 40));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(40, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(40, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

    }

}
