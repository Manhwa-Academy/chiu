package DTO;

import java.util.Objects;

public class SanPhamDTO {

    private int masp;
    private String tensp;
    private String hinhanh;
    private int thuonghieu;
    private String series;
    private String nhanvat;
    private String tyle;
    private String chatlieu;
    private int xuatxu;
    private int khuvuckho;
    private String loaiSanPham; // Thuộc tính mới
    private int soluongton;
    private int trangthai;
    private String ngaytao; // Thêm trường ngaytao
    private String mota;

    public SanPhamDTO() {
    }

    // Constructor có tham số ngày tạo
    public SanPhamDTO(
            int masp,
            String tensp,
            String hinhanh,
            int thuonghieu,
            String series,
            String nhanvat,
            String tyle,
            String chatlieu,
            int xuatxu,
            int khuvuckho,
            String loaisanpham,
            String mota, // 👈 BẮT BUỘC PHẢI CÓ
            int soluongton,
            int trangthai,
            String ngaytao) {

        this.masp = masp;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.thuonghieu = thuonghieu;
        this.series = series;
        this.nhanvat = nhanvat;
        this.tyle = tyle;
        this.chatlieu = chatlieu;
        this.xuatxu = xuatxu;
        this.khuvuckho = khuvuckho;
        this.loaiSanPham = loaisanpham;
        this.mota = mota; // 👈 GÁN
        this.soluongton = soluongton;
        this.trangthai = trangthai;
        this.ngaytao = ngaytao;
    }

    // ===== GETTER / SETTER =====

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(int thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    // Getter và Setter cho ngaytao
    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    // Getter và Setter cho loaiSanPham
    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public String getNhanvat() {
        return nhanvat;
    }

    public void setNhanvat(String nhanvat) {
        this.nhanvat = nhanvat;
    }

    public String getTyle() {
        return tyle;
    }

    public void setTyle(String tyle) {
        this.tyle = tyle;
    }

    public String getChatlieu() {
        return chatlieu;
    }

    public void setChatlieu(String chatlieu) {
        this.chatlieu = chatlieu;
    }

    public int getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(int xuatxu) {
        this.xuatxu = xuatxu;
    }

    public int getKhuvuckho() {
        return khuvuckho;
    }

    public void setKhuvuckho(int khuvuckho) {
        this.khuvuckho = khuvuckho;
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

    // ===== HASH / EQUALS =====

    @Override
    public int hashCode() {
        return Objects.hash(masp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof SanPhamDTO))
            return false;
        SanPhamDTO other = (SanPhamDTO) obj;
        return masp == other.masp;
    }

    @Override
    public String toString() {
        return "SanPhamDTO{" +
                "masp=" + masp +
                ", tensp='" + tensp + '\'' +
                ", series='" + series + '\'' +
                ", nhanvat='" + nhanvat + '\'' +
                ", tyle='" + tyle + '\'' +
                ", chatlieu='" + chatlieu + '\'' +
                ", soluongton=" + soluongton +
                ", loaiSanPham='" + loaiSanPham + '\'' + // Thêm loaiSanPham vào toString
                ", ngaytao='" + ngaytao + '\'' + // Thêm ngaytao vào toString
                '}';
    }
}
