package DAO;

import DTO.GioHangDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class GioHangDAO {

    public ArrayList<GioHangDTO> selectByManv(int manv) {

        ArrayList<GioHangDTO> list = new ArrayList<>();

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giohang WHERE manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, manv);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                GioHangDTO gh = new GioHangDTO(
                        rs.getInt("id"),
                        rs.getInt("manv"),
                        rs.getInt("maphienbansp"),
                        rs.getInt("soluong"));
                list.add(gh);
            }

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insert(GioHangDTO gh) {

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO giohang(manv, maphienbansp, soluong) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, gh.getManv());
            pst.setInt(2, gh.getMaphienbansp());
            pst.setInt(3, gh.getSoluong());

            int result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int updateQuantity(int manv, int maphienbansp, int soluong) {

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE giohang SET soluong = ? WHERE manv = ? AND maphienbansp = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, soluong);
            pst.setInt(2, manv);
            pst.setInt(3, maphienbansp);

            int result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int delete(int manv, int maphienbansp) {

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM giohang WHERE manv = ? AND maphienbansp = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, manv);
            pst.setInt(2, maphienbansp);

            int result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int clear(int manv) {

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM giohang WHERE manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, manv);

            int result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public GioHangDTO findItem(int manv, int maphienbansp) {

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM giohang WHERE manv = ? AND maphienbansp = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, manv);
            pst.setInt(2, maphienbansp);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new GioHangDTO(
                        rs.getInt("id"),
                        rs.getInt("manv"),
                        rs.getInt("maphienbansp"),
                        rs.getInt("soluong"));
            }

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
