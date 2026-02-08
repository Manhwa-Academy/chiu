package DTO.ThuocTinhSanPham;

import java.util.Objects;

public class DacDiemSanPhamDTO {

    private int madacdiem; // Mã đặc điểm sản phẩm
    private String tendacdiem; // Tên đặc điểm sản phẩm
    private int trangthai; // Trạng thái của đặc điểm sản phẩm (ví dụ: 1: hoạt động, 0: không hoạt động)

    // Constructor không tham số
    public DacDiemSanPhamDTO() {
    }

    // Constructor với 2 tham số
    public DacDiemSanPhamDTO(int madacdiem, String tendacdiem) {
        this.madacdiem = madacdiem;
        this.tendacdiem = tendacdiem;
    }

    // Constructor với 3 tham số (bao gồm trạng thái)
    public DacDiemSanPhamDTO(int madacdiem, String tendacdiem, int trangthai) {
        this.madacdiem = madacdiem;
        this.tendacdiem = tendacdiem;
        this.trangthai = trangthai;
    }

    // Getter và Setter cho thuộc tính
    public int getMadacdiem() {
        return madacdiem;
    }

    public void setMadacdiem(int madacdiem) {
        this.madacdiem = madacdiem;
    }

    public String getTendacdiem() {
        return tendacdiem;
    }

    public void setTendacdiem(String tendacdiem) {
        this.tendacdiem = tendacdiem;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(madacdiem, tendacdiem, trangthai);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DacDiemSanPhamDTO other = (DacDiemSanPhamDTO) obj;
        return madacdiem == other.madacdiem &&
                Objects.equals(tendacdiem, other.tendacdiem) &&
                trangthai == other.trangthai;
    }

    @Override
    public String toString() {
        // Sử dụng StringBuilder để tối ưu hiệu suất khi đối tượng phức tạp
        StringBuilder sb = new StringBuilder();
        sb.append("DacDiemSanPhamDTO{");
        sb.append("madacdiem=").append(madacdiem);
        sb.append(", tendacdiem='").append(tendacdiem).append('\'');
        sb.append(", trangthai=").append(trangthai);
        sb.append('}');
        return sb.toString();
    }
}
