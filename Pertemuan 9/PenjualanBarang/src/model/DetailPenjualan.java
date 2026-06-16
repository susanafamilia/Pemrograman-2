package model;

public class DetailPenjualan {
    private String noFaktur;
    private String kodeBarang;
    private int qty;          // Sesuai tabel detail_penjualan
    private double harga;      // Sesuai tabel detail_penjualan
    private double subtotal;

    // Getter dan Setter
    public String getNoFaktur() { return noFaktur; }
    public void setNoFaktur(String noFaktur) { this.noFaktur = noFaktur; }

    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}