
package DAO;

import DTO.ThuocTinhSanPham.XuatXuDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XuatXuDAO implements DAOinterface<XuatXuDTO> {
    public static XuatXuDAO getInstance() {
        return new XuatXuDAO();
    }

    @Override
    public int insert(XuatXuDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "INSERT INTO `xuatxu`(`maxuatxu`, `tenxuatxu`,`trangthai`) VALUES (?,?,1)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMaxuatxu());
            pst.setString(2, t.getTenxuatxu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(XuatXuDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `xuatxu` SET `tenxuatxu`=? WHERE `maxuatxu`=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenxuatxu());
            pst.setInt(2, t.getMaxuatxu());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            // Mở kết nối đến cơ sở dữ liệu
            Connection con = (Connection) JDBCUtil.getConnection();

            // Sử dụng câu lệnh DELETE để xóa bản ghi hoàn toàn
            String sql = "DELETE FROM `xuatxu` WHERE `maxuatxu` = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

            // Thiết lập tham số cho câu truy vấn
            pst.setString(1, t);

            // Thực thi câu truy vấn
            result = pst.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            // In ra lỗi nếu có ngoại lệ xảy ra
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<XuatXuDTO> selectAll() {
        ArrayList<XuatXuDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM xuatxu WHERE trangthai = 1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mahdh = rs.getInt("maxuatxu");
                String tenxuatxu = rs.getString("tenxuatxu");
                XuatXuDTO ms = new XuatXuDTO(mahdh, tenxuatxu);
                result.add(ms);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public XuatXuDTO selectById(String t) {
        XuatXuDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM xuatxu WHERE maxuatxu=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int mahdh = rs.getInt("maxuatxu");
                String tenxuatxu = rs.getString("tenxuatxu");
                result = new XuatXuDTO(mahdh, tenxuatxu);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND   TABLE_NAME   = 'xuatxu'";
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
            Logger.getLogger(XuatXuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
