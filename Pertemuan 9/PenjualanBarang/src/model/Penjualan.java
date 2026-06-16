package model;

import java.util.ArrayList;
import java.util.List;

public class Penjualan {
    private String noFaktur;
    private String tanggal;
    private String idCustomer;
    private double total; // Sesuai tabel penjualan
    private List<DetailPenjualan> detailItem;

    public Penjualan() {
        this.detailItem = new ArrayList<>();
    }

    // Getter dan Setter
    public String getNoFaktur() { return noFaktur; }
    public void setNoFaktur(String noFaktur) { this.noFaktur = noFaktur; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getIdCustomer() { return idCustomer; }
    public void setIdCustomer(String idCustomer) { this.idCustomer = idCustomer; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<DetailPenjualan> getDetailItem() { return detailItem; }
    public void setDetailItem(List<DetailPenjualan> detailItem) { this.detailItem = detailItem; }
    
    public void tambahDetail(DetailPenjualan item) { this.detailItem.add(item); }
}