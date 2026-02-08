package DTO.ThuocTinhSanPham;

import java.util.Objects;

public class CharacterDTO {

    private int macharacter;
    private String tencharacter;
    private String kichThuoc;  // Nếu bạn có thuộc tính Kích thước

    // Constructor không tham số
    public CharacterDTO() {
    }

    // Constructor với 2 tham số
    public CharacterDTO(int macharacter, String tencharacter) {
        this.macharacter = macharacter;
        this.tencharacter = tencharacter;
    }

    // Constructor với 3 tham số (bao gồm Kích thước)
    public CharacterDTO(int macharacter, String tencharacter, String kichThuoc) {
        this.macharacter = macharacter;
        this.tencharacter = tencharacter;
        this.kichThuoc = kichThuoc;
    }

    public int getMacharacter() {
        return macharacter;
    }

    public void setMacharacter(int macharacter) {
        this.macharacter = macharacter;
    }

    public String getTencharacter() {
        return tencharacter;
    }

    public void setTencharacter(String tencharacter) {
        this.tencharacter = tencharacter;
    }

    // Getter và Setter cho kichThuoc
    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(macharacter, tencharacter, kichThuoc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CharacterDTO other = (CharacterDTO) obj;
        return macharacter == other.macharacter &&
                Objects.equals(tencharacter, other.tencharacter) &&
                Objects.equals(kichThuoc, other.kichThuoc);
    }

    @Override
    public String toString() {
        return "CharacterDTO{" +
                "macharacter=" + macharacter +
                ", tencharacter='" + tencharacter + '\'' +
                ", kichThuoc='" + kichThuoc + '\'' +
                '}';
    }
}
