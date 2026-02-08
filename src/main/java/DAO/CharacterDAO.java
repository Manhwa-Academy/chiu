package DAO;

import DTO.ThuocTinhSanPham.CharacterDTO;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CharacterDAO implements DAOinterface<CharacterDTO> {

    public static CharacterDAO getInstance() {
        return new CharacterDAO();
    }

    @Override
    public int insert(CharacterDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO character_sp (macharacter, tencharacter, trangthai) VALUES (?,?,1)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMacharacter());
            pst.setString(2, t.getTencharacter());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(CharacterDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE character_sp SET tencharacter=? WHERE macharacter=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTencharacter());
            pst.setInt(2, t.getMacharacter());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "DELETE FROM `character_sp` WHERE `macharacter` = ?";

            // Chuẩn bị câu lệnh SQL
            PreparedStatement pst = con.prepareStatement(sql);

            // Gán giá trị cho tham số SQL
            pst.setString(1, id);

            // Thực thi câu lệnh DELETE
            result = pst.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            // Log lỗi nếu có
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result; // Trả về kết quả
    }

    @Override
    public CharacterDTO selectById(String id) {
        CharacterDTO result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM character_sp WHERE macharacter=? AND trangthai=1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int ma = rs.getInt("macharacter");
                String ten = rs.getString("tencharacter");
                result = new CharacterDTO(ma, ten);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<CharacterDTO> selectAll() {
        ArrayList<CharacterDTO> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM character_sp WHERE trangthai=1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("macharacter");
                String ten = rs.getString("tencharacter");
                list.add(new CharacterDTO(id, ten));
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                        SELECT AUTO_INCREMENT
                        FROM INFORMATION_SCHEMA.TABLES
                        WHERE TABLE_SCHEMA='quanlikhohang'
                        AND TABLE_NAME='character_sp'
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
