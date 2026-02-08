package DTO.ThuocTinhSanPham;

public class CategoryDTO {

    private int macategory;
    private String tencategory;

    public CategoryDTO() {
    }

    public CategoryDTO(int macategory, String tencategory) {
        this.macategory = macategory;
        this.tencategory = tencategory;
    }

    public int getMacategory() {
        return macategory;
    }

    public void setMacategory(int macategory) {
        this.macategory = macategory;
    }

    public String getTencategory() {
        return tencategory;
    }

    public void setTencategory(String tencategory) {
        this.tencategory = tencategory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.macategory;
        hash = 59 * hash + (this.tencategory != null ? this.tencategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CategoryDTO other = (CategoryDTO) obj;
        if (this.macategory != other.macategory) return false;
        return this.tencategory != null
                ? this.tencategory.equalsIgnoreCase(other.tencategory)
                : other.tencategory == null;
    }

    @Override
    public String toString() {
        return tencategory;
    }
}
