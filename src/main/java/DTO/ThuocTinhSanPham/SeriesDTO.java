package DTO.ThuocTinhSanPham;

public class SeriesDTO { // Đổi từ HeDieuHanhDTO thành SeriesDTO
    private int maSeries; // Đổi từ mahdh thành maSeries
    private String tenSeries; // Đổi từ tenhdh thành tenSeries

    public SeriesDTO() { // Đổi từ HeDieuHanhDTO thành SeriesDTO
    }

    public SeriesDTO(int maSeries, String tenSeries) { // Đổi từ mahdh, tenhdh thành maSeries, tenSeries
        this.maSeries = maSeries;
        this.tenSeries = tenSeries;
    }

    public int getMaSeries() { // Đổi từ getMahdh thành getMaSeries
        return maSeries;
    }

    public void setMaSeries(int maSeries) { // Đổi từ setMahdh thành setMaSeries
        this.maSeries = maSeries;
    }

    public String getTenSeries() { // Đổi từ getTenhdh thành getTenSeries
        return tenSeries;
    }

    public void setTenSeries(String tenSeries) { // Đổi từ setTenhdh thành setTenSeries
        this.tenSeries = tenSeries;
    }

    @Override
    public String toString() { // Đổi từ HeDieuHanhDTO thành SeriesDTO
        return "SeriesDTO{" + "maSeries=" + maSeries + ", tenSeries=" + tenSeries + '}';
    }
}
