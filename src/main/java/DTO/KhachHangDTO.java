
package DTO;

import java.util.Objects;
import java.sql.Timestamp; // Thêm import cho Timestamp

public class KhachHangDTO {
    private int maKH;
    private String hoten;
    private String sdt;
    private String diachi;
    private Timestamp ngaythamgia; // Sử dụng Timestamp thay vì Date

    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKH, String hoten, String sdt, String diachi, Timestamp ngaythamgia) {
        this.maKH = maKH;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.ngaythamgia = ngaythamgia;
    }

    public Timestamp getNgaythamgia() {
        return ngaythamgia;
    }

    public void setNgaythamgia(Timestamp ngaythamgia) {
        this.ngaythamgia = ngaythamgia;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.maKH);
        hash = 79 * hash + Objects.hashCode(this.hoten);
        hash = 79 * hash + Objects.hashCode(this.sdt);
        hash = 79 * hash + Objects.hashCode(this.diachi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        KhachHangDTO other = (KhachHangDTO) obj;
        return this.maKH == other.maKH;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKH=" + maKH + ", hoten=" + hoten + ", sdt=" + sdt + ", diachi=" + diachi + '}';
    }

}
