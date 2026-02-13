package BUS;

import DAO.ChiTietSanPhamDAO;
import DTO.ChiTietSanPhamDTO;
import DTO.PhienBanSanPhamDTO;
import helper.ImeiGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class ChiTietSanPhamBUS {

    private final ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    public PhienBanSanPhamBUS pbspbus = new PhienBanSanPhamBUS();

    public ArrayList<ChiTietSanPhamDTO> listctsp = new ArrayList<>();

    public ChiTietSanPhamBUS() {
    }

    /*
     * ==========================
     * LẤY DỮ LIỆU
     * ==========================
     */

    public ArrayList<ChiTietSanPhamDTO> getAllByMaPBSP(int pbsp) {
        return ctspDAO.selectbyPb(pbsp);
    }

    public ArrayList<ChiTietSanPhamDTO> getAllCTSPbyMasp(int masp) {
        ArrayList<ChiTietSanPhamDTO> result = new ArrayList<>();
        ArrayList<PhienBanSanPhamDTO> listPB = pbspbus.getAll(masp);

        for (PhienBanSanPhamDTO pb : listPB) {
            result.addAll(getAllByMaPBSP(pb.getMaphienbansp()));
        }
        return result;
    }

    public ArrayList<ChiTietSanPhamDTO> selectAllByMaPhieuXuat(int maphieu) {
        return ctspDAO.selectAllByMaPhieuXuat(maphieu);
    }

    /*
     * ==========================
     * TỰ ĐỘNG TẠO IMEI
     * ==========================
     */

    public ArrayList<ChiTietSanPhamDTO> generateChiTietSanPham(
            int maphienban,
            int soluong,
            int maphieunhap) {

        ArrayList<ChiTietSanPhamDTO> list = new ArrayList<>();

        while (list.size() < soluong) {

            String imei = ImeiGenerator.generateIMEI();

            boolean existsInDB = ctspDAO.checkImeiExists(imei);

            boolean existsInList = list.stream()
                    .anyMatch(ct -> ct.getImei().equals(imei));

            if (!existsInDB && !existsInList) {

                ChiTietSanPhamDTO ct = new ChiTietSanPhamDTO(
                        imei,
                        maphienban,
                        maphieunhap,
                        0, // chưa xuất
                        1 // còn hàng
                );

                list.add(ct);
            }
        }

        return list;
    }

    /*
     * ==========================
     * INSERT DANH SÁCH IMEI
     * ==========================
     */

    public void insertList(ArrayList<ChiTietSanPhamDTO> list) {
        ctspDAO.insert_mutiple(list);
    }

    /*
     * ==========================
     * CẬP NHẬT KHI XUẤT
     * ==========================
     */

    public void updateXuat(ArrayList<ChiTietSanPhamDTO> list) {
        for (ChiTietSanPhamDTO ct : list) {
            ctspDAO.updateXuat(ct);
        }
    }

    /*
     * ==========================
     * FILTER
     * ==========================
     */

    public ArrayList<ChiTietSanPhamDTO> FilterPBvaTT(
            String text,
            int masp,
            int phienban,
            int tinhtrang) {

        ArrayList<ChiTietSanPhamDTO> list = getAllCTSPbyMasp(masp);
        ArrayList<ChiTietSanPhamDTO> result = new ArrayList<>();

        for (ChiTietSanPhamDTO i : list) {
            if (i.getMaphienbansp() == phienban
                    && i.getTinhtrang() == tinhtrang
                    && i.getImei().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public ArrayList<ChiTietSanPhamDTO> getImeiByPhienBan(int mapb, int soLuong) {

        ArrayList<ChiTietSanPhamDTO> all = ctspDAO.selectbyPb(mapb);
        ArrayList<ChiTietSanPhamDTO> result = new ArrayList<>();

        for (int i = 0; i < soLuong && i < all.size(); i++) {
            result.add(all.get(i));
        }

        return result;
    }

    public ArrayList<ChiTietSanPhamDTO> FilterPBvaAll(
            String text,
            int masp,
            int phienban) {

        ArrayList<ChiTietSanPhamDTO> list = getAllCTSPbyMasp(masp);
        ArrayList<ChiTietSanPhamDTO> result = new ArrayList<>();

        for (ChiTietSanPhamDTO i : list) {
            if (i.getMaphienbansp() == phienban
                    && i.getImei().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean checkImeiExists(ArrayList<ChiTietSanPhamDTO> list) {

        for (ChiTietSanPhamDTO ct : list) {

            // Nếu IMEI đã tồn tại trong DB
            if (ctspDAO.checkImeiExists(ct.getImei())) {
                return false;
            }
        }

        return true; // Tất cả IMEI đều chưa tồn tại
    }

    /*
     * ==========================
     * GROUP THEO PHIẾU
     * ==========================
     */

    public HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> getChiTietSanPhamFromMaPN(int maphieunhap) {

        ArrayList<ChiTietSanPhamDTO> list = ctspDAO.selectAllByMaPhieuNhap(maphieunhap);

        HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> result = new HashMap<>();

        for (ChiTietSanPhamDTO i : list) {
            result
                    .computeIfAbsent(i.getMaphienbansp(), k -> new ArrayList<>())
                    .add(i);
        }

        return result;
    }

    public HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> getChiTietSanPhamFromMaPX(int maphieuxuat) {

        ArrayList<ChiTietSanPhamDTO> list = ctspDAO.selectAllByMaPhieuXuat(maphieuxuat);

        HashMap<Integer, ArrayList<ChiTietSanPhamDTO>> result = new HashMap<>();

        for (ChiTietSanPhamDTO ct : list) {

            if (!result.containsKey(ct.getMaphienbansp())) {
                result.put(ct.getMaphienbansp(), new ArrayList<>());
            }

            result.get(ct.getMaphienbansp()).add(ct);
        }

        return result;
    }

}
