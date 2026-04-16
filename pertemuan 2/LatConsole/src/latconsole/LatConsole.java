package latconsole;

import java.util.ArrayList;
import java.util.Scanner;

public class LatConsole {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<Mahasiswa> dataMhs = new ArrayList<>();

        int pilihan;

        do {

            System.out.println("\n===== MENU PROGRAM =====");
            System.out.println("1. Input Data Mahasiswa");
            System.out.println("2. Tampil Data Mahasiswa");
            System.out.println("3. Keluar");
            System.out.print("Pilih Menu : ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:

                    System.out.print("Jumlah data yang diinput : ");
                    int jumlah = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < jumlah; i++) {

                        System.out.println("\nData ke-" + (i + 1));

                        System.out.print("NIM  : ");
                        String nim = input.nextLine();

                        System.out.print("Nama : ");
                        String nama = input.nextLine();
                        
                        System.out.print("Jurusan : ");
                        String jurusan = input.nextLine();

                        Mahasiswa mhs = new Mahasiswa(nim, nama, jurusan);
                        dataMhs.add(mhs);
                    }

                    break;


                case 2:
                    System.out.println("Program selesai...");
                    break;

                default:
                    System.out.println("Menu tidak tersedia!");

            }

        } while (pilihan != 3);

    }
}