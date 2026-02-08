package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import DTO.ThuocTinhSanPham.CategoryDTO;
import config.JDBCUtil;

public class CategoryDAO implements DAOinterface<CategoryDTO> {

    public static CategoryDAO getInstance() {
        return new CategoryDAO();
    }

    @Override
    public int insert(CategoryDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO category(macategory, tencategory, trangthai) VALUES (?,?,1)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMacategory());
            pst.setString(2, t.getTencategory());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(CategoryDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE category SET tencategory=? WHERE macategory=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTencategory());
            pst.setInt(2, t.getMacategory());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String id) {
        int result = 0;
        try {
            // Mở kết nối
            Connection con = JDBCUtil.getConnection();

            // Câu lệnh DELETE thay vì UPDATE
            String sql = "DELETE FROM `category` WHERE `macategory` = ?";

            // Chuẩn bị câu lệnh SQL
            PreparedStatement pst = con.prepareStatement(sql);

            // Gán giá trị cho tham số SQL
            pst.setString(1, id);

            // Thực thi câu lệnh DELETE
            result = pst.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            // Log lỗi nếu có
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result; // Trả về kết quả
    }

    @Override
    public ArrayList<CategoryDTO> selectAll() {
        ArrayList<CategoryDTO> result = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM category WHERE trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("macategory");
                String name = rs.getString("tencategory");
                result.add(new CategoryDTO(id, name));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public CategoryDTO selectById(String id) {
        CategoryDTO result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM category WHERE macategory=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = new CategoryDTO(
                        rs.getInt("macategory"),
                        rs.getString("tencategory"));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
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
                      AND TABLE_NAME = 'category'
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
