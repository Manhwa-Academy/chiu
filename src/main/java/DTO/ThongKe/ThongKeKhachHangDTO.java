
package DTO.ThongKe;

import java.util.Objects;

public class ThongKeKhachHangDTO {
    int makh;
    String tenkh;
    int soluongphieu;
    long tongtien;

    public ThongKeKhachHangDTO() {
    }

    public ThongKeKhachHangDTO(int makh, String tenkh, int soluongphieu, long tongtien) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.soluongphieu = soluongphieu;
        this.tongtien = tongtien;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public int getSoluongphieu() {
        return soluongphieu;
    }

    public void setSoluongphieu(int soluongphieu) {
        this.soluongphieu = soluongphieu;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ThongKeKhachHangDTO other = (ThongKeKhachHangDTO) obj;
        return this.makh == other.makh;
    }

    @Override
    public int hashCode() {
        return Objects.hash(makh);
    }

    @Override
    public String toString() {
        return "ThongKeKhachHangDTO{" + "makh=" + makh + ", tenkh=" + tenkh + ", soluongphieu=" + soluongphieu
                + ", tongtien=" + tongtien + '}';
    }

}
