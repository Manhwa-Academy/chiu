package BUS;

import DAO.GioHangDAO;
import DTO.GioHangDTO;

import java.util.ArrayList;

public class GioHangBUS {

    private final GioHangDAO dao = new GioHangDAO();

    public ArrayList<GioHangDTO> getByManv(int manv) {
        return dao.selectByManv(manv);
    }

    public void addOrUpdate(int manv, int maphienbansp, int soluong) {

        GioHangDTO existing = dao.findItem(manv, maphienbansp);

        if (existing != null) {
            int newQty = existing.getSoluong() + soluong;
            dao.updateQuantity(manv, maphienbansp, newQty);
        } else {
            dao.insert(new GioHangDTO(manv, maphienbansp, soluong));
        }
    }

    public void delete(int manv, int maphienbansp) {
        dao.delete(manv, maphienbansp);
    }

    public void clear(int manv) {
        dao.clear(manv);
    }
}
