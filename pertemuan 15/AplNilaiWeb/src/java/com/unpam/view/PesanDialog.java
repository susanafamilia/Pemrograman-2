package com.unpam.view;

/**
 * PesanDialog - Digunakan sebagai stub untuk kompatibilitas dengan kode model.
 * Di aplikasi web, konfirmasi dilakukan langsung tanpa dialog,
 * sehingga class ini hanya sebagai pengganti agar kode model tidak berubah.
 */
public class PesanDialog {

    public static final int YES_OPTION = 0;
    public static final int NO_OPTION  = 1;

    /**
     * Selalu mengembalikan YES_OPTION karena di aplikasi web
     * tidak ada dialog konfirmasi — data langsung diproses.
     */
    public int tampilkanKonfirmasi(String pesan) {
        return YES_OPTION;
    }

    /**
     * Menampilkan pesan informasi (di web tidak ditampilkan, hanya placeholder).
     */
    public void tampilkanPesan(String pesan) {
        // Tidak ada aksi di aplikasi web
    }
}
