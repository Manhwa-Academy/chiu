package DAO;

import DTO.PhienBanSanPhamDTO;
import config.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhienBanSanPhamDAO {

    public static PhienBanSanPhamDAO getInstance() {
        return new PhienBanSanPhamDAO();
    }

    /* ================= INSERT ================= */
    public int insert(PhienBanSanPhamDTO pb) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                        INSERT INTO phienbansanpham
                        (maphienbansp, masp, tenphienban, gianhap, giaxuat, soluongton, trangthai)
                        VALUES (?,?,?,?,?,?,?)
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, pb.getMaphienbansp());
            pst.setInt(2, pb.getMasp());
            pst.setString(3, pb.getTenphienban());
            pst.setInt(4, pb.getGianhap());
            pst.setInt(5, pb.getGiaxuat());
            pst.setInt(6, pb.getSoluongton());
            pst.setInt(7, pb.getTrangthai()); // Chỉ cập nhật các trường có trong bảng

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(PhienBanSanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /* ================= UPDATE ================= */

    public int update(PhienBanSanPhamDTO pb) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                        UPDATE phienbansanpham SET
                            tenphienban=?,
                            gianhap=?,
                            giaxuat=?,
                            soluongton=?
                        WHERE maphienbansp=?
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, pb.getTenphienban());
            pst.setInt(2, pb.getGianhap());
            pst.setInt(3, pb.getGiaxuat());
            pst.setInt(4, pb.getSoluongton());
            pst.setInt(5, pb.getMaphienbansp());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(PhienBanSanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    /* ================= SELECT BY MASP ================= */

    public ArrayList<PhienBanSanPhamDTO> selectAll(String masp) {
        ArrayList<PhienBanSanPhamDTO> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM phienbansanpham WHERE masp=? AND trangthai=1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, masp);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                PhienBanSanPhamDTO pb = new PhienBanSanPhamDTO(
                        rs.getInt("maphienbansp"),
                        rs.getInt("masp"),
                        rs.getString("tenphienban"),
                        rs.getInt("gianhap"),
                        rs.getInt("giaxuat"),
                        rs.getInt("soluongton"),
                        rs.getInt("trangthai"));
                list.add(pb);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= SELECT BY ID ================= */

    public PhienBanSanPhamDTO selectById(int mapb) {
        PhienBanSanPhamDTO pb = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM phienbansanpham WHERE maphienbansp=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, mapb);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                pb = new PhienBanSanPhamDTO(
                        rs.getInt("maphienbansp"),
                        rs.getInt("masp"),
                        rs.getString("tenphienban"),
                        rs.getInt("gianhap"),
                        rs.getInt("giaxuat"),
                        rs.getInt("soluongton"),
                        rs.getInt("trangthai"));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pb;
    }

    /* ================= AUTO INCREMENT ================= */

    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                        SELECT AUTO_INCREMENT
                        FROM INFORMATION_SCHEMA.TABLES
                        WHERE TABLE_SCHEMA='quanlikhohang'
                        AND TABLE_NAME='phienbansanpham'
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                result = rs.getInt("AUTO_INCREMENT");
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateSoLuongTon(int maphienbansp, int soLuongThayDoi) {

        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "UPDATE phienbansanpham " +
                    "SET soluongton = soluongton + ? " +
                    "WHERE maphienbansp = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, soLuongThayDoi);
            pst.setInt(2, maphienbansp);

            int result = pst.executeUpdate();

            JDBCUtil.closeConnection(con);
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getMinPriceByMaSP(int masp) {

        int result = 0;

        String sql = """
                    SELECT MIN(giaxuat) AS minPrice
                    FROM phienbansanpham
                    WHERE masp = ?
                    AND trangthai = 1
                """;

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, masp);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                result = rs.getInt("minPrice");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getGiaByMaPhienBan(int mapb) {

        int gia = 0;

        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT giaxuat FROM phienbansanpham WHERE maphienbansp = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, mapb);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                gia = rs.getInt("giaxuat");

            }

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gia;
    }

    /* ================= DELETE ================= */

    public int delete(String maphienbansp) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM phienbansanpham WHERE maphienbansp = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maphienbansp);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
