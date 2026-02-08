package DAO;

import DTO.ThuocTinhSanPham.SeriesDTO; // Đổi từ HeDieuHanhDTO thành SeriesDTO
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class SeriesDAO implements DAOinterface<SeriesDTO> { // Đổi từ HeDieuHanhDAO thành SeriesDAO

    public static SeriesDAO getInstance() {
        return new SeriesDAO();
    }

    @Override
    public int insert(SeriesDTO t) { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        int result = 0;
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO `series`(`maSeries`, `tenSeries`, `trangthai`) VALUES (?, ?, 1)")) {
            pst.setInt(1, t.getMaSeries()); // Đổi từ getMahdh thành getMaSeries
            pst.setString(2, t.getTenSeries()); // Đổi từ getTenhdh thành getTenSeries
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(SeriesDTO t) { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        int result = 0;
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement("UPDATE `series` SET `tenSeries`=? WHERE `maSeries`=?")) {
            pst.setString(1, t.getTenSeries()); // Đổi từ getTenhdh thành getTenSeries
            pst.setInt(2, t.getMaSeries()); // Đổi từ getMahdh thành getMaSeries
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con
                        .prepareStatement("UPDATE `series` SET `trangthai` = 0 WHERE maSeries = ?")) {
            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<SeriesDTO> selectAll() {
        ArrayList<SeriesDTO> result = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM series WHERE trangthai = 1");
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int maSeries = rs.getInt("maSeries"); // Đổi từ mahedieuhanh thành maSeries
                String tenSeries = rs.getString("tenSeries"); // Đổi từ tenhedieuhanh thành tenSeries
                SeriesDTO ms = new SeriesDTO(maSeries, tenSeries);
                result.add(ms);
            }
        } catch (SQLException e) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public SeriesDTO selectById(String t) {
        SeriesDTO result = null;
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM series WHERE maSeries=?")) {
            pst.setString(1, t);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int maSeries = rs.getInt("maSeries"); // Đổi từ mahedieuhanh thành maSeries
                    String tenSeries = rs.getString("tenSeries"); // Đổi từ tenhdh thành tenSeries
                    result = new SeriesDTO(maSeries, tenSeries);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement pst = con.prepareStatement(
                        "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND TABLE_NAME = 'series'");
                ResultSet rs2 = pst.executeQuery()) {
            if (rs2.next()) {
                result = rs2.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
