package DAO;

import DTO.ThuocTinhSanPham.DacDiemSanPhamDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DacDiemSanPhamDAO implements DAOinterface<DacDiemSanPhamDTO> {

    public static DacDiemSanPhamDAO getInstance() {
        return new DacDiemSanPhamDAO();
    }

    @Override
    public int insert(DacDiemSanPhamDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = """
                        INSERT INTO dacdiemsanpham(madacdiem, tendacdiem, trangthai)
                        VALUES (?, ?, 1)
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMadacdiem());
            pst.setString(2, t.getTendacdiem());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DacDiemSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(DacDiemSanPhamDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE dacdiemsanpham SET tendacdiem=? WHERE madacdiem=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTendacdiem());
            pst.setInt(2, t.getMadacdiem());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DacDiemSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String id) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM dacdiemsanpham WHERE madacdiem = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DacDiemSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<DacDiemSanPhamDTO> selectAll() {
        ArrayList<DacDiemSanPhamDTO> result = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM dacdiemsanpham";
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DacDiemSanPhamDTO dto = new DacDiemSanPhamDTO();
                    dto.setMadacdiem(rs.getInt("madacdiem"));
                    dto.setTendacdiem(rs.getString("tendacdiem"));
                    result.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public DacDiemSanPhamDTO selectById(String id) {
        DacDiemSanPhamDTO result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM dacdiemsanpham WHERE madacdiem=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = new DacDiemSanPhamDTO(
                        rs.getInt("madacdiem"),
                        rs.getString("tendacdiem"));
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DacDiemSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                        WHERE TABLE_SCHEMA='quanlikhohang'
                        AND TABLE_NAME='dacdiemsanpham'
                    """;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                result = rs.getInt("AUTO_INCREMENT");
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DacDiemSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
