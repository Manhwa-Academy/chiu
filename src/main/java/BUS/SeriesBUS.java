package BUS;

import DAO.SeriesDAO; // Đổi từ HeDieuHanhDAO thành SeriesDAO
import DTO.ThuocTinhSanPham.SeriesDTO; // Đổi từ HeDieuHanhDTO thành SeriesDTO
import java.util.ArrayList;

public class SeriesBUS {

    private SeriesDAO seriesDAO = new SeriesDAO(); // Đổi từ HeDieuHanhDAO thành SeriesDAO
    private ArrayList<SeriesDTO> listSeries = new ArrayList<>(); // Đổi từ listHeDieuHanh thành listSeries

    public SeriesBUS() {
        this.listSeries = seriesDAO.selectAll(); // Đổi từ hdhDAO.selectAll() thành seriesDAO.selectAll()
    }

    public ArrayList<SeriesDTO> getAll() {
        return this.listSeries;
    }

    public String[] getArrTenSeries() { // Đổi từ getArrTenHeDieuHanh thành getArrTenSeries
        String[] result = new String[listSeries.size()];
        for (int i = 0; i < listSeries.size(); i++) {
            result[i] = listSeries.get(i).getTenSeries(); // Đổi từ getTenhdh thành getTenSeries
        }
        return result;
    }

    public SeriesDTO getByIndex(int index) {
        return this.listSeries.get(index); // Đổi từ listHeDieuHanh thành listSeries
    }

    public int getIndexByTenSeries(String tenSeries) {

        ArrayList<SeriesDTO> ds = this.getAll();

        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getTenSeries().equalsIgnoreCase(tenSeries)) {
                return i;
            }
        }

        return -1;
    }

    public boolean add(SeriesDTO series) { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        boolean check = seriesDAO.insert(series) != 0; // Đổi từ hdhDAO.insert thành seriesDAO.insert
        if (check) {
            this.listSeries.add(series); // Đổi từ listHeDieuHanh thành listSeries
        }
        return check;
    }

    public boolean delete(SeriesDTO series, int index) { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        boolean check = seriesDAO.delete(Integer.toString(series.getMaSeries())) != 0; // Đổi từ hdhDAO.delete thành
                                                                                       // seriesDAO.delete
        if (check) {
            this.listSeries.remove(index); // Đổi từ listHeDieuHanh thành listSeries
        }
        return check;
    }

    public int getIndexByMaSeries(int maSeries) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSeries.size() && vitri == -1) {
            if (listSeries.get(i).getMaSeries() == maSeries) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public int getIndexByMaMau(int maMau) { // Đổi từ getIndexByMaMau thành getIndexByMaMau
        int i = 0;
        int vitri = -1;
        while (i < this.listSeries.size() && vitri == -1) { // Đổi từ listHeDieuHanh thành listSeries
            if (listSeries.get(i).getMaSeries() == maMau) { // Đổi từ getMahdh thành getMaSeries
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public String getTenSeries(int maSeries) { // Đổi từ getTenHdh thành getTenSeries
        int index = this.getIndexByMaMau(maSeries);
        return this.listSeries.get(index).getTenSeries(); // Đổi từ getTenhdh thành getTenSeries
    }

    public boolean update(SeriesDTO series) { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        boolean check = seriesDAO.update(series) != 0; // Đổi từ hdhDAO.update thành seriesDAO.update
        if (check) {
            this.listSeries.set(getIndexByMaMau(series.getMaSeries()), series); // Đổi từ listHeDieuHanh thành
                                                                                // listSeries
        }
        return check;
    }

    public boolean checkDup(String name) {
        boolean check = true;
        int i = 0;
        while (i < this.listSeries.size() && check == true) { // Đổi từ listHeDieuHanh thành listSeries
            if (this.listSeries.get(i).getTenSeries().toLowerCase().contains(name.toLowerCase())) { // Đổi từ getTenhdh
                                                                                                    // thành
                                                                                                    // getTenSeries
                check = false;
            } else {
                i++;
            }
        }
        return check;
    }

}
