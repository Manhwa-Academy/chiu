package DAO;

import DTO.ChiTietPhieuDTO;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuXuatDAO {

    private static ChiTietPhieuXuatDAO instance;

    private ChiTietPhieuXuatDAO() {
    }

    public static ChiTietPhieuXuatDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietPhieuXuatDAO();
        }
        return instance;
    }
    // Reset phương thức để xóa chi tiết phiếu xuất (hoặc thực hiện hành động khác, tùy vào yêu cầu)
    public int reset(ArrayList<ChiTietPhieuDTO> chiTietPhieu) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM ctphieuxuat WHERE maphieuxuat = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                for (ChiTietPhieuDTO ct : chiTietPhieu) {
                    pst.setInt(1, ct.getMaphieu());  // Xóa chi tiết theo maphieu
                    result += pst.executeUpdate(); // Tính tổng số dòng xóa thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    // Thêm chi tiết phiếu xuất vào bảng ctphieuxuat
    public int insert(ArrayList<ChiTietPhieuDTO> ct) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO ctphieuxuat (maphieuxuat, maphienbansp, soluong, dongia) VALUES (?,?,?,?)"; // Bỏ
                                                                                                                  // trường
                                                                                                                  // hinhthucnhap
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                for (ChiTietPhieuDTO chiTiet : ct) {
                    pst.setInt(1, chiTiet.getMaphieu()); // Mã phiếu xuất
                    pst.setInt(2, chiTiet.getMaphienbansp()); // Mã phiên bản sản phẩm
                    pst.setInt(3, chiTiet.getSoluong()); // Số lượng
                    pst.setInt(4, chiTiet.getDongia()); // Đơn giá
                    result += pst.executeUpdate(); // Cộng kết quả các insert thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy tất cả chi tiết phiếu xuất theo mã phiếu
    public ArrayList<ChiTietPhieuDTO> selectAll(String maphieu) {
        ArrayList<ChiTietPhieuDTO> list = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM ctphieuxuat WHERE maphieuxuat = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, maphieu);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        ChiTietPhieuDTO ct = new ChiTietPhieuDTO(
                                rs.getInt("maphieuxuat"), // Mã phiếu xuất
                                rs.getInt("maphienbansp"), // Mã phiên bản sản phẩm
                                rs.getInt("soluong"), // Số lượng
                                rs.getInt("dongia") // Đơn giá
                        );
                        list.add(ct);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa chi tiết phiếu xuất theo mã phiếu xuất
    public int delete(String maphieu) {
        int result = 0;
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM ctphieuxuat WHERE maphieuxuat = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, maphieu);
                result = pst.executeUpdate(); // Xóa các bản ghi có maphieuxuat tương ứng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
