
package DAO;

import DTO.ThuocTinhSanPham.ThuongHieuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThuongHieuDAO implements DAOinterface<ThuongHieuDTO> {
    public static ThuongHieuDAO getInstance() {
        return new ThuongHieuDAO();
    }

    @Override
    public int insert(ThuongHieuDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `thuonghieu`(`tenthuonghieu`) VALUES (?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenthuonghieu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(ThuongHieuDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE `thuonghieu` SET `tenthuonghieu`=? WHERE `mathuonghieu`=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTenthuonghieu()); // Cập nhật tên thương hiệu
            pst.setInt(2, t.getMathuonghieu()); // Cập nhật mã thương hiệu
            result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bản ghi để cập nhật.");
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật", ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "DELETE FROM `thuonghieu` WHERE `mathuonghieu` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(t)); // Thay vì setString, ta dùng setInt
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<ThuongHieuDTO> selectAll() {
        ArrayList<ThuongHieuDTO> result = new ArrayList<ThuongHieuDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM thuonghieu WHERE `trangthai`=1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mathuonghieu = rs.getInt("mathuonghieu");
                String tenthuonghieu = rs.getString("tenthuonghieu");

                ThuongHieuDTO lh = new ThuongHieuDTO(mathuonghieu, tenthuonghieu);
                result.add(lh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public ThuongHieuDTO selectById(String t) {
        ThuongHieuDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM thuonghieu WHERE mathuonghieu=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mathuonghieu = rs.getInt("mathuonghieu");
                String tenloaihang = rs.getString("tenthuonghieu");
                result = new ThuongHieuDTO(mathuonghieu, tenloaihang);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND   TABLE_NAME   = 'thuonghieu'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs2 = pst.executeQuery(sql);
            if (!rs2.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs2.next()) {
                    result = rs2.getInt("AUTO_INCREMENT");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThuongHieuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
