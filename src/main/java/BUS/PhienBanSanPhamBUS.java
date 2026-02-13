package BUS;

import DAO.PhienBanSanPhamDAO; // Thêm dòng import này
import DTO.PhienBanSanPhamDTO;
import java.util.ArrayList;

public class PhienBanSanPhamBUS {

    private final PhienBanSanPhamDAO pbDAO = PhienBanSanPhamDAO.getInstance();

    public PhienBanSanPhamBUS() {
    }

    /* ================= LẤY TẤT CẢ PHIÊN BẢN THEO SẢN PHẨM ================= */

    public ArrayList<PhienBanSanPhamDTO> getAll(int masp) {
        return pbDAO.selectAll(String.valueOf(masp));
    }

    public int getGiaThapNhatByMaSP(int masp) {
        return pbDAO.getMinPriceByMaSP(masp);
    }

    /* ================= LẤY PHIÊN BẢN THEO MÃ ================= */

    public PhienBanSanPhamDTO getByMaPhienBan(int mapb) {
        return pbDAO.selectById(mapb);
    }

    /* ================= TÌM INDEX ================= */

    public int getIndexByMaPhienBan(ArrayList<PhienBanSanPhamDTO> list, int mapb) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaphienbansp() == mapb) {
                return i;
            }
        }
        return -1;
    }

    public PhienBanSanPhamDTO getPhienBanDauTien(int masp) {
        ArrayList<PhienBanSanPhamDTO> list = getAll(masp);
        if (list == null || list.isEmpty())
            return null;
        return list.get(0);
    }

    public int getGiaByMaPhienBan(int mapb) {
        return pbDAO.getGiaByMaPhienBan(mapb);
    }

    /* ================= CHECK TRÙNG PHIÊN BẢN ================= */

    public boolean checkDuplicate(
            ArrayList<PhienBanSanPhamDTO> list,
            PhienBanSanPhamDTO pb) {

        for (PhienBanSanPhamDTO item : list) {
            if (item.equals(pb)) {
                return true;
            }
        }
        return false;
    }

    /* ================= THÊM NHIỀU PHIÊN BẢN ================= */

    public boolean add(ArrayList<PhienBanSanPhamDTO> listPB) {
        boolean success = true;
        for (PhienBanSanPhamDTO pb : listPB) {
            if (pbDAO.insert(pb) == 0) {
                success = false;
            }
        }
        return success;
    }

    public boolean updateSoLuongTon(int maphienbansp, int soLuongThayDoi) {
        return new PhienBanSanPhamDAO().updateSoLuongTon(maphienbansp, soLuongThayDoi);
    }
    /* ================= LẤY SỐ LƯỢNG ================= */

    public int getSoluong(int maphienban) {
        PhienBanSanPhamDTO pb = pbDAO.selectById(maphienban);
        return pb != null ? pb.getSoluongton() : 0;
    }

    /* ================= CẬP NHẬT TỒN ================= */

}
