
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import DTO.KhachHangDTO;
import java.sql.Timestamp; // Thêm import cho Timestamp
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class KhachHangDAO implements DAOinterface<KhachHangDTO> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int insert(KhachHangDTO t) {
        int result = 0;
        try {
            // Lấy kết nối đến cơ sở dữ liệu
            Connection con = (Connection) JDBCUtil.getConnection();

            // Câu lệnh SQL để chèn khách hàng vào bảng khachhang
            String sql = "INSERT INTO `khachhang`(`makh`, `tenkhachhang`, `diachi`, `sdt`, `ngaythamgia`, `trangthai`) VALUES (?,?,?,?,?,1)";

            // Chuẩn bị câu lệnh SQL để thực thi
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);

            // Gán giá trị cho các tham số trong câu lệnh SQL
            pst.setInt(1, t.getMaKH()); // Mã khách hàng
            pst.setString(2, t.getHoten()); // Tên khách hàng
            pst.setString(3, t.getDiachi()); // Địa chỉ
            pst.setString(4, t.getSdt()); // Số điện thoại

            // Lấy thời gian hiện tại theo múi giờ Việt Nam (Asia/Ho_Chi_Minh)
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            Timestamp timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime()); // Chuyển từ ZonedDateTime sang
                                                                                      // Timestamp

            // Gán ngày tham gia cho tham số 5
            pst.setTimestamp(5, timestamp); // Gán Timestamp thay vì Date

            // Thực thi câu lệnh SQL
            result = pst.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(KhachHangDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "UPDATE `khachhang` SET `makh`=?,`tenkhachhang`=?,`diachi`=?,`sdt`=? WHERE makh=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMaKH());
            pst.setString(2, t.getHoten());
            pst.setString(3, t.getDiachi());
            pst.setString(4, t.getSdt());
            pst.setInt(5, t.getMaKH());

            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            // Mở kết nối với cơ sở dữ liệu
            Connection con = (Connection) JDBCUtil.getConnection();

            // Câu lệnh SQL xóa khách hàng vĩnh viễn
            String sql = "DELETE FROM `khachhang` WHERE `makh` = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t); // Tham số là mã khách hàng

            // Thực thi câu lệnh
            result = pst.executeUpdate();

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> result = new ArrayList<KhachHangDTO>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khachhang WHERE trangthai=1";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int makh = rs.getInt("makh");
                String tenkhachhang = rs.getString("tenkhachhang");
                String diachi = rs.getString("diachi");
                String sdt = rs.getString("sdt");
                Timestamp ngaythamgia = rs.getTimestamp("ngaythamgia"); // Sử dụng Timestamp thay vì Date
                KhachHangDTO kh = new KhachHangDTO(makh, tenkhachhang, sdt, diachi, ngaythamgia);
                result.add(kh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
public KhachHangDTO selectByEmail(String email) {
    KhachHangDTO result = null;
    try {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM khachhang WHERE email=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            result = new KhachHangDTO(
                    rs.getInt("makh"),
                    rs.getString("tenkhachhang"),
                    rs.getString("sdt"),
                    rs.getString("diachi"),
                    rs.getTimestamp("ngaythamgia")
            );
        }

        JDBCUtil.closeConnection(con);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}

    public int deleteMultiple(ArrayList<KhachHangDTO> customers) {
        int result = 0;
        try {
            // Mở kết nối với cơ sở dữ liệu
            Connection con = (Connection) JDBCUtil.getConnection();

            // Câu lệnh SQL xóa khách hàng vĩnh viễn
            String sql = "DELETE FROM `khachhang` WHERE makh = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            // Lặp qua danh sách khách hàng và xóa từng khách hàng
            for (KhachHangDTO customer : customers) {
                pst.setInt(1, customer.getMaKH()); // Gán mã khách hàng vào câu lệnh
                result += pst.executeUpdate(); // Cộng dồn số khách hàng đã xóa
            }

            // Đóng kết nối
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public KhachHangDTO selectById(String t) {
        KhachHangDTO result = null;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM khachhang WHERE makh=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int makh = rs.getInt("makh");
                String tenkhachhang = rs.getString("tenkhachhang");
                String diachi = rs.getString("diachi");
                String sdt = rs.getString("sdt");
                Timestamp ngaythamgia = rs.getTimestamp("ngaythamgia");
                result = new KhachHangDTO(makh, tenkhachhang, sdt, diachi, ngaythamgia);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'quanlikhohang' AND   TABLE_NAME   = 'khachhang'";
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
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
