package DTO;

public class PhienBanSanPhamDTO {
    private int maphienbansp;
    private int masp;
    private String tenphienban;
    private int gianhap;
    private int giaxuat;
    private int soluongton;
    private int trangthai;

    // Constructor với 7 tham số
    public PhienBanSanPhamDTO(int maphienbansp, int masp, String tenphienban, int gianhap, int giaxuat, int soluongton,
            int trangthai) {
        this.maphienbansp = maphienbansp;
        this.masp = masp;
        this.tenphienban = tenphienban;
        this.gianhap = gianhap;
        this.giaxuat = giaxuat;
        this.soluongton = soluongton;
        this.trangthai = trangthai;
    }

    // Getter and Setter methods
    public int getMaphienbansp() {
        return maphienbansp;
    }

    public void setMaphienbansp(int maphienbansp) {
        this.maphienbansp = maphienbansp;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTenphienban() {
        return tenphienban;
    }

    public void setTenphienban(String tenphienban) {
        this.tenphienban = tenphienban;
    }

    public int getGianhap() {
        return gianhap;
    }

    public void setGianhap(int gianhap) {
        this.gianhap = gianhap;
    }

    public int getGiaxuat() {
        return giaxuat;
    }

    public void setGiaxuat(int giaxuat) {
        this.giaxuat = giaxuat;
    }

    public int getSoluongton() {
        return soluongton;
    }

    public void setSoluongton(int soluongton) {
        this.soluongton = soluongton;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return "PhienBanSanPhamDTO{" +
                "maphienbansp=" + maphienbansp +
                ", masp=" + masp +
                ", tenphienban='" + tenphienban + '\'' +
                ", gianhap=" + gianhap +
                ", giaxuat=" + giaxuat +
                ", soluongton=" + soluongton +
                ", trangthai=" + trangthai +
                '}';
    }
}
