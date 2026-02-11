package BUS;

import DAO.DacDiemSanPhamDAO;
import DTO.ThuocTinhSanPham.DacDiemSanPhamDTO;
import java.util.ArrayList;

public class DacDiemSanPhamBUS {

    private DacDiemSanPhamDAO dacDiemSanPhamDAO = new DacDiemSanPhamDAO();
    private ArrayList<DacDiemSanPhamDTO> listDacDiemSanPham = new ArrayList<>();

    public DacDiemSanPhamBUS() {
        this.listDacDiemSanPham = dacDiemSanPhamDAO.selectAll();
    }

    public ArrayList<DacDiemSanPhamDTO> getAll() {
        return this.listDacDiemSanPham;
    }

    public int getIndexByTen(String ten) {

        ArrayList<DacDiemSanPhamDTO> ds = this.getAll();

        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getTendacdiem().equalsIgnoreCase(ten)) {
                return i;
            }
        }

        return -1;
    }

    public String[] getArrTenDacDiemSanPham() {
        String[] result = new String[listDacDiemSanPham.size()];
        for (int i = 0; i < listDacDiemSanPham.size(); i++) {
            result[i] = listDacDiemSanPham.get(i).getTendacdiem();
        }
        return result;
    }

    public DacDiemSanPhamDTO getByIndex(int index) {
        return this.listDacDiemSanPham.get(index);
    }

    public boolean add(DacDiemSanPhamDTO dacDiemSanPham) {
        boolean check = dacDiemSanPhamDAO.insert(dacDiemSanPham) != 0;
        if (check) {
            this.listDacDiemSanPham.add(dacDiemSanPham);
        }
        return check;
    }

    public boolean delete(DacDiemSanPhamDTO dacDiemSanPham, int index) {
        boolean check = dacDiemSanPhamDAO.delete(Integer.toString(dacDiemSanPham.getMadacdiem())) != 0;
        if (check) {
            this.listDacDiemSanPham.remove(index);
        }
        return check;
    }

    public int getIndexByMaDacDiem(int madacdiem) {
        int i = 0;
        int vitri = -1;
        while (i < this.listDacDiemSanPham.size() && vitri == -1) {
            if (listDacDiemSanPham.get(i).getMadacdiem() == madacdiem) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public String getTenDacDiem(int madacdiem) {
        int index = this.getIndexByMaDacDiem(madacdiem);
        return this.listDacDiemSanPham.get(index).getTendacdiem();
    }

    public boolean update(DacDiemSanPhamDTO dacDiemSanPham) {
        boolean check = dacDiemSanPhamDAO.update(dacDiemSanPham) != 0;
        if (check) {
            this.listDacDiemSanPham.set(getIndexByMaDacDiem(dacDiemSanPham.getMadacdiem()), dacDiemSanPham);
        }
        return check;
    }

    public boolean checkDup(String name) {
        boolean check = true;
        int i = 0;
        while (i < this.listDacDiemSanPham.size() && check) {
            if (this.listDacDiemSanPham.get(i).getTendacdiem().toLowerCase().contains(name.toLowerCase())) {
                check = false;
            } else {
                i++;
            }
        }
        return check;
    }

}
