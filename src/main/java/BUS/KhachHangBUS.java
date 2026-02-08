package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.sql.Timestamp;

public class KhachHangBUS {

    private final KhachHangDAO khDAO = new KhachHangDAO();
    public ArrayList<KhachHangDTO> listKhachHang = new ArrayList<>();

    public KhachHangBUS() {
        listKhachHang = khDAO.selectAll();
    }

    // Thêm khách hàng với ngày tham gia là thời gian hiện tại
    public Boolean add(KhachHangDTO kh) {
        // Đặt ngày tham gia là ngày hiện tại nếu không có giá trị
        if (kh.getNgaythamgia() == null) {
            // Lấy thời gian hiện tại theo múi giờ Việt Nam
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            // Chuyển đổi từ ZonedDateTime sang LocalDateTime để giữ thời gian đầy đủ
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            // Chuyển LocalDateTime thành Timestamp để lưu vào cơ sở dữ liệu
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            kh.setNgaythamgia(timestamp); // Gán giá trị vào ngày tham gia
        }

        boolean check = khDAO.insert(kh) != 0;
        if (check) {
            this.listKhachHang.add(kh);
        }
        return check;
    }

    public ArrayList<KhachHangDTO> getAll() {
        return this.listKhachHang;
    }

    public KhachHangDTO getByIndex(int index) {
        return this.listKhachHang.get(index);
    }

    public int getIndexByMaDV(int makhachhang) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKhachHang.size() && vitri == -1) {
            if (listKhachHang.get(i).getMaKH() == makhachhang) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean delete(KhachHangDTO kh) {
        boolean check = khDAO.delete(Integer.toString(kh.getMaKH())) != 0;
        if (check) {
            this.listKhachHang.remove(getIndexByMaDV(kh.getMaKH()));
        }
        return check;
    }

    public Boolean update(KhachHangDTO kh) {
        boolean check = khDAO.update(kh) != 0;
        if (check) {
            this.listKhachHang.set(getIndexByMaDV(kh.getMaKH()), kh);
        }
        return check;
    }

    public ArrayList<KhachHangDTO> search(String text, String type) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (Integer.toString(i.getMaKH()).toLowerCase().contains(text)
                            || i.getHoten().toLowerCase().contains(text) || i.getDiachi().toLowerCase().contains(text)
                            || i.getSdt().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã khách hàng" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (Integer.toString(i.getMaKH()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên khách hàng" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getHoten().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Địa chỉ" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getDiachi().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (KhachHangDTO i : this.listKhachHang) {
                    if (i.getSdt().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
        }

        return result;
    }

    public KhachHangDTO getById(int maKH) {
        return KhachHangDAO.getInstance().selectById(String.valueOf(maKH));
    }

    public int deleteMultiple(ArrayList<KhachHangDTO> selectedCustomers) {
        int result = 0;
        for (KhachHangDTO customer : selectedCustomers) {
            // Gọi phương thức xóa khách hàng trong KhachHangDAO
            boolean check = khDAO.delete(String.valueOf(customer.getMaKH())) != 0;
            if (check) {
                this.listKhachHang.remove(customer); // Loại bỏ khách hàng khỏi danh sách
                result++; // Tăng kết quả thành công
            }
        }
        return result; // Trả về số khách hàng đã bị xóa
    }

    public String getTenKhachHang(int makh) {
        String name = "";
        for (KhachHangDTO khachHangDTO : listKhachHang) {
            if (khachHangDTO.getMaKH() == makh) {
                name = khachHangDTO.getHoten();
            }
        }
        return name;
    }

    public String[] getArrTenKhachHang() {
        int size = listKhachHang.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listKhachHang.get(i).getHoten();
        }
        return result;
    }

    public KhachHangDTO selectKh(int makh) {
        return khDAO.selectById(makh + "");
    }

}
