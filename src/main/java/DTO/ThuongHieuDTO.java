package DTO;

public class ThuongHieuDTO {

    private int mathuonghieu;
    private String tenthuonghieu;
    private int trangthai;

    public ThuongHieuDTO() {
    }

    public ThuongHieuDTO(int mathuonghieu, String tenthuonghieu, int trangthai) {
        this.mathuonghieu = mathuonghieu;
        this.tenthuonghieu = tenthuonghieu;
        this.trangthai = trangthai;
    }

    public int getMathuonghieu() {
        return mathuonghieu;
    }

    public String getTenthuonghieu() {
        return tenthuonghieu;
    }

    public int getTrangthai() {
        return trangthai;
    }
}
