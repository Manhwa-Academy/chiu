
package DTO.ThuocTinhSanPham;


public class XuatXuDTO {
    private int maxuatxu;
    private String tenxuatxu;
    public XuatXuDTO(){
        
    }

    public XuatXuDTO(int maxuatxu, String tenxuatxu) {
        this.maxuatxu = maxuatxu;
        this.tenxuatxu = tenxuatxu;
    }

    public int getMaxuatxu() {
        return maxuatxu;
    }

    public void setMaxuatxu(int maxuatxu) {
        this.maxuatxu = maxuatxu;
    }

    public String getTenxuatxu() {
        return tenxuatxu;
    }

    public void setTenxuatxu(String tenxuatxu) {
        this.tenxuatxu = tenxuatxu;
    }

    @Override
    public String toString() {
        return "XuatXuDTO{" + "maxuatxu=" + maxuatxu + ", tenxuatxu=" + tenxuatxu + '}';
    }
    
}
