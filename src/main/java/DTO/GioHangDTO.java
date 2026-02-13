package DTO;

public class GioHangDTO {

    private int id;
    private int manv;
    private int maphienbansp;
    private int soluong;

    public GioHangDTO() {
    }

    public GioHangDTO(int id, int manv, int maphienbansp, int soluong) {
        this.id = id;
        this.manv = manv;
        this.maphienbansp = maphienbansp;
        this.soluong = soluong;
    }

    public GioHangDTO(int manv, int maphienbansp, int soluong) {
        this.manv = manv;
        this.maphienbansp = maphienbansp;
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public int getManv() {
        return manv;
    }

    public int getMaphienbansp() {
        return maphienbansp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public void setMaphienbansp(int maphienbansp) {
        this.maphienbansp = maphienbansp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
