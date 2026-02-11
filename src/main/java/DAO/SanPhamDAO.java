package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.SanPhamDTO;

public class SanPhamDAO implements DAOinterface<SanPhamDTO> {

    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }

    @Override
    public int insert(SanPhamDTO t) {

        int result = 0;

        try {

            Connection con = JDBCUtil.getConnection();

            String sql = """
                        INSERT INTO sanpham
                        (masp, tensp, hinhanh, thuonghieu, series, nhanvat, tyle, chatlieu,
                         xuatxu, khuvuckho, loaisanpham, soluongton, trangthai, ngaytao)
                        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                    """;

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, t.getMasp());
            pst.setString(2, t.getTensp());
            pst.setString(3, t.getHinhanh());
            pst.setInt(4, t.getThuonghieu());
            pst.setString(5, t.getSeries());
            pst.setString(6, t.getNhanvat());
            pst.setString(7, t.getTyle());
            pst.setString(8, t.getChatlieu());
            pst.setInt(9, t.getXuatxu());
            pst.setInt(10, t.getKhuvuckho());
            pst.setString(11, t.getLoaiSanPham()); // 👈 THÊM DÒNG NÀY
            pst.setInt(12, t.getSoluongton());
            pst.setInt(13, t.getTrangthai());
            pst.setString(14, t.getNgaytao());

            result = pst.executeUpdate();

            JDBCUtil.closeConnection(con);

        } catch (SQLException e) {
            Logger.getLogger(SanPhamDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        }

        return result;
    }

    @Override
    public int update(SanPhamDTO t) {

        int result = 0;

        String sql = """
                    UPDATE sanpham SET
                        tensp = ?,
                        hinhanh = ?,
                        soluongton = ?,
                        thuonghieu = ?,
                        series = ?,
                        nhanvat = ?,
                        tyle = ?,
                        chatlieu = ?,
                        xuatxu = ?,
                        khuvuckho = ?,
                        loaisanpham = ?,
                        trangthai = ?,
                        ngaytao = ?
                    WHERE masp = ?
                """;

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, t.getTensp());
            pst.setString(2, t.getHinhanh());
            pst.setInt(3, t.getSoluongton());
            pst.setInt(4, t.getThuonghieu());
            pst.setString(5, t.getSeries());
            pst.setString(6, t.getNhanvat());
            pst.setString(7, t.getTyle());
            pst.setString(8, t.getChatlieu());
            pst.setInt(9, t.getXuatxu());
            pst.setInt(10, t.getKhuvuckho());

            // ⚠️ tránh null
            pst.setString(11, t.getLoaiSanPham() == null ? "" : t.getLoaiSanPham());

            pst.setInt(12, t.getTrangthai());
            pst.setString(13, t.getNgaytao());
            pst.setInt(14, t.getMasp());

            result = pst.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(SanPhamDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        }

        return result;
    }

    @Override
    public int delete(String id) {

        int result = 0;

        try (Connection con = JDBCUtil.getConnection()) {

            con.setAutoCommit(false); // Bắt đầu transaction

            // 1️⃣ Xóa toàn bộ phiên bản thuộc sản phẩm
            String sqlPhienBan = "DELETE FROM phienbansanpham WHERE masp = ?";
            PreparedStatement pstPB = con.prepareStatement(sqlPhienBan);
            pstPB.setString(1, id);
            pstPB.executeUpdate();

            // 2️⃣ Xóa sản phẩm
            String sqlSanPham = "DELETE FROM sanpham WHERE masp = ?";
            PreparedStatement pstSP = con.prepareStatement(sqlSanPham);
            pstSP.setString(1, id);
            result = pstSP.executeUpdate();

            con.commit(); // Thành công

        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE trangthai=1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getInt("masp"),
                        rs.getString("tensp"),
                        rs.getString("hinhanh"),
                        rs.getInt("thuonghieu"),
                        rs.getString("series"),
                        rs.getString("nhanvat"),
                        rs.getString("tyle"),
                        rs.getString("chatlieu"),
                        rs.getInt("xuatxu"),
                        rs.getInt("khuvuckho"),
                        rs.getString("loaisanpham"),
                        rs.getInt("soluongton"),
                        rs.getInt("trangthai"),
                        rs.getString("ngaytao") // Thêm ngày tạo
                );

                list.add(sp);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SanPhamDTO selectById(String id) {
        SanPhamDTO sp = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE masp=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                sp = new SanPhamDTO(
                        rs.getInt("masp"),
                        rs.getString("tensp"),
                        rs.getString("hinhanh"),
                        rs.getInt("thuonghieu"),
                        rs.getString("series"),
                        rs.getString("nhanvat"),
                        rs.getString("tyle"),
                        rs.getString("chatlieu"),
                        rs.getInt("xuatxu"),
                        rs.getInt("khuvuckho"),
                        rs.getString("loaisanpham"),
                        rs.getInt("soluongton"),
                        rs.getInt("trangthai"),
                        rs.getString("ngaytao"));

            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    public SanPhamDTO selectByPhienBan(String maphienban) {
        SanPhamDTO result = null;
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = """
                        SELECT sp.*
                        FROM sanpham sp
                        JOIN phienbansanpham pb ON sp.masp = pb.masp
                        WHERE pb.maphienbansp = ?
                          AND sp.trangthai = 1
                          AND pb.trangthai = 1
                    """;

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maphienban);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = new SanPhamDTO(
                        rs.getInt("masp"),
                        rs.getString("tensp"),
                        rs.getString("hinhanh"),
                        rs.getInt("thuonghieu"),
                        rs.getString("series"),
                        rs.getString("nhanvat"),
                        rs.getString("tyle"),
                        rs.getString("chatlieu"),
                        rs.getInt("xuatxu"),
                        rs.getInt("khuvuckho"),
                        rs.getString("loaisanpham"),
                        rs.getInt("soluongton"),
                        rs.getInt("trangthai"),
                        rs.getString("ngaytao"));

            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = """
                        SELECT AUTO_INCREMENT
                        FROM INFORMATION_SCHEMA.TABLES
                        WHERE TABLE_SCHEMA = 'quanlikhohang'
                        AND TABLE_NAME = 'sanpham'
                    """;

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(); // ✅ bỏ truyền sql

            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }

            JDBCUtil.closeConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int updateSoLuongTon(int masp, int soluong) {
        int quantity_current = this.selectById(Integer.toString(masp)).getSoluongton();
        int result = 0;
        int quantity_change = quantity_current + soluong;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `sanpham` SET `soluongton`=? WHERE masp = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, quantity_change);
            pst.setInt(2, masp);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
