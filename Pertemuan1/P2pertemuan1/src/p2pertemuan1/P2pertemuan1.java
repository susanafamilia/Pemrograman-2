package p2pertemuan1;

import java.util.ArrayList;
import java.util.Scanner;

class Mahasiswa {
    String nim;
    String nama;
    double uts;
    double uas;
    double nilaiAkhir;
    char grade;

    public Mahasiswa(String nim, String nama, double uts, double uas) {
        this.nim = nim;
        this.nama = nama;
        this.uts = uts;
        this.uas = uas;
        hitungNilai();
    }

    private void hitungNilai() {
        nilaiAkhir = (0.4 * uts) + (0.6 * uas);

        if (nilaiAkhir >= 85)
            grade = 'A';
        else if (nilaiAkhir >= 70)
            grade = 'B';
        else if (nilaiAkhir >= 60)
            grade = 'C';
        else if (nilaiAkhir >= 50)
            grade = 'D';
        else
            grade = 'E';
    }

    public void tampilData() {
        System.out.println("----------------------------");
        System.out.println("NIM         : " + nim);
        System.out.println("Nama        : " + nama);
        System.out.println("Nilai UTS   : " + uts);
        System.out.println("Nilai UAS   : " + uas);
        System.out.println("Nilai Akhir : " + nilaiAkhir);
        System.out.println("Grade       : " + grade);
    }
}

public class P2pertemuan1 {

    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();
            
            int pilihan;
            
            do {
                System.out.println("\n=== MENU PROGRAM ===");
                System.out.println("1. Input Data");
                System.out.println("2. Tampil Data");
                System.out.println("3. Keluar");
                System.out.print("Pilih menu: ");
                pilihan = input.nextInt();
                input.nextLine();
                
                switch (pilihan) {
                    
                    case 1 -> {
                        System.out.print("Berapa data yang ingin diinput? ");
                        int jumlah = input.nextInt();
                        input.nextLine();
                        
                        for (int i = 0; i < jumlah; i++) {
                            System.out.println("\nData ke-" + (i + 1));
                            System.out.print("NIM        : ");
                            String nim = input.nextLine();
                            System.out.print("Nama       : ");
                            String nama = input.nextLine();
                            System.out.print("Nilai UTS  : ");
                            double uts = input.nextDouble();
                            System.out.print("Nilai UAS  : ");
                            double uas = input.nextDouble();
                            input.nextLine();
                            
                            Mahasiswa mhs = new Mahasiswa(nim, nama, uts, uas);
                            daftarMahasiswa.add(mhs);
                        }
                    }
                        
                    case 2 -> {
                        if (daftarMahasiswa.isEmpty()) {
                            System.out.println("Belum ada data!");
                        } else {
                            System.out.println("\n=== DATA MAHASISWA ===");
                            for (Mahasiswa mhs : daftarMahasiswa) {
                                mhs.tampilData();
                            }
                        }
                    }
                        
                    case 3 -> System.out.println("Program selesai. Terima kasih!");
                        
                    default -> System.out.println("Pilihan tidak tersedia!");
                }
                
            } while (pilihan != 3);
        }
    }
}