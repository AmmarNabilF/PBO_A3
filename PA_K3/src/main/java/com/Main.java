package com;
import com.auth.auth;
import com.control.BahanBakuControl;

import com.control.PasokanController;
import com.control.PemakaianControl;
import com.model.Pemakaian;
import java.util.Scanner;
import com.model.Pemasok;
import com.model.Pengguna;
import com.control.PesanControl;
import com.control.ProdukControl;

public class Main {
    static void cs() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Jika gagal, tampilkan baris kosong sebagai fallback
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    public static void main(String[] args) {
        DB db = new DB();
        auth auth = new auth(db.conn);
        Scanner input = new Scanner(System.in);
        PasokanController crudpa = new PasokanController();
        int login = 3;
        if (db.conn != null){
            System.out.println("Koneksi ke database berhasil!");
        } else {
            System.out.println("Koneksi ke database gagal!");
            return;
        }
        
        cs();
        menu:
        while (true) {
            System.out.println("""
                                \n==============================
                                   SELAMAT DATANG DI MARTSA
                                ==============================
                                 [1] Login
                                 [2] Daftar
                                 [0] Keluar
                                ==============================""");
            System.out.print("Pilih menu: ");
            int menu = input.nextInt();
            input.nextLine(); 
            if (menu == 1) {
                cs();
                while (true) {
                    System.out.println("""
                                       \n===== LOGIN =====
                                        [1] Pemasok
                                        [2] Pengguna
                                        [0] Kembali
                                       =================""");
                    System.out.print("Pilih menu: ");
                    int pilihanLogin = input.nextInt();
                    input.nextLine();
                    if (pilihanLogin == 1) {
                        while (login > 0) {
                            System.out.println("\n>>> MASUKKAN AKUN PEMASOK <<<");
                            String notelp;
                            do {
                                System.out.print("> Nomor Telepon: ");
                                notelp = input.nextLine();
                                if (notelp.trim().isEmpty() || !notelp.matches("\\d{10,14}")) {
                                    System.out.println("Nomor telepon tidak valid.  Harus terdiri dari 10 hingga 14 digit angka.");
                                }
                            } while (notelp.isEmpty() || !notelp.matches("\\d{10,14}"));
                            String password;
                            do {
                                System.out.print("> Password     : ");
                                password = input.nextLine();
                                if (password.trim().isEmpty() || password.length() < 6) {
                                    System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                                }
                            } while (password.trim().isEmpty() || password.length() < 6);
                            try {
                                Pemasok pemasok = (Pemasok) auth.login(notelp, password);
                                if (pemasok != null) {
                                    String namaPemasok = pemasok.getNamaPemasok();
                                    cs();
                                    menuPasokan(input, crudpa, auth.getCurrentUserId(), namaPemasok);
                                    login = 3;
                                    continue menu;  
                                } else {
                                    login--;
                                    System.out.println("Login gagal! Nomor telepon atau password salah.");
                                    if (login > 0) {
                                        System.out.println("Sisa percobaan login: " + login);
                                    } else {
                                        System.out.println("Anda telah mencoba login 3 kali. Silakan coba lagi nanti.");
                                        System.exit(0);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Koneksi Terputus");
                            }
                        }
                    } else if (pilihanLogin == 2) {
                        while (login > 0) {
                            System.out.println("\n>>> MASUKKAN AKUN PENGGUNA <<<");
                            String notelp;
                            do {
                                System.out.print("> Nomor Telepon: ");
                                notelp = input.nextLine();
                                if (notelp.trim().isEmpty() || !notelp.matches("\\d{10,14}")) {
                                    System.out.println("Nomor telepon tidak valid.  Harus terdiri dari 10 hingga 14 digit angka.");
                                }
                            } while (notelp.trim().isEmpty() || !notelp.matches("\\d{10,14}"));
                            String password;
                            do {
                                System.out.print("> Password     : ");
                                password = input.nextLine();
                                if (password.trim().isEmpty() || password.length() < 6) {
                                    System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                                }
                            } while (password.trim().isEmpty() || password.length() < 6);
                            try {
                                Pengguna pengguna = (Pengguna) auth.login(notelp, password);
                                if (pengguna != null) {
                                    cs();
                                    System.out.println("Login berhasil! Selamat datang, " + pengguna.getNamaPengguna() + "!");
                                    menuPengguna(input, auth.getCurrentUserId());
                                    login = 3;
                                    continue menu;
                                } else {
                                    login--;
                                    System.out.println("Login gagal! Nomor telepon atau password salah.");
                                    if (login > 0) {
                                        System.out.println("Sisa percobaan login: " + login);
                                    } else {
                                        System.out.println("Anda telah mencoba login 3 kali. Silakan coba lagi nanti.");
                                        System.exit(0);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Koneksi Terputus");
                            }
                        }
                    } else if (pilihanLogin == 0) {
                        cs();
                        continue menu;
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                }
            }
            if (menu == 2) {
                cs();
                System.out.println("""
                                   \n===== DAFTAR =====
                                    [1] Pemasok
                                    [2] Pengguna
                                    [0] Kembali
                                   ==================""");
                System.out.print("Pilih menu: ");
                int pilihanDaftar = input.nextInt();
                input.nextLine();
                if (pilihanDaftar == 1) {
                    System.out.println("\n>>> DAFTAR AKUN PEMASOK <<<");
                    String idPemasok;
                    try {
                        idPemasok = auth.generateIdPemasok();
                    } catch (Exception e) {
                        System.out.println("Gagal mendapatkan ID Pemasok");
                        return;
                    }
                    String namaPemasok;
                    do{
                        System.out.print("> Nama Pemasok: ");
                        namaPemasok = input.nextLine();
                        if (namaPemasok.trim().isEmpty()|| !namaPemasok.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Nama pengguna tidak sesuai. Silakan coba lagi.");
                        }
                    }while(namaPemasok.trim().isEmpty()||!namaPemasok.matches("[a-zA-Z\\s]+"));
                    String nomorTelepon;
                    do{
                        System.out.print("> Nomor Telepon: ");
                        nomorTelepon = input.nextLine();
                        if (nomorTelepon.trim().isEmpty() || !nomorTelepon.matches("\\d{10,14}")) {
                            System.out.println("Nomor telepon tidak valid.  Harus terdiri dari 10 hingga 14 digit angka.");
                        }
                    }while (nomorTelepon.trim().isEmpty() || !nomorTelepon.matches("\\d{10,14}"));
                
                    String password;
                    do{
                        System.out.print("> Password: ");
                        password = input.nextLine();
                        if (password.trim().isEmpty() || password.length() < 6) {
                            System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                        }
                    }while (password.trim().isEmpty() || password.length() < 6);
                    Pemasok pemasok = new Pemasok(idPemasok, namaPemasok, nomorTelepon, password);
                    try {
                        auth.register(pemasok);
                        System.out.println("Akun berhasil didaftarkan!");
                    } catch (Exception e) {
                        if (e.getMessage().contains("duplikat")) {
                            System.out.println("Nomor telepon sudah digunakan.");
                        } else {
                            System.out.println("Gagal mendaftar akun");
                        }
                        continue menu;
                    }
                } else if (pilihanDaftar == 2) {
                    System.out.println("\n>>> DAFTAR AKUN PENGGUNA <<<");
                    String idPengguna;
                    try {
                        idPengguna = auth.generateIdPengguna();
                    } catch (Exception e) {
                        System.out.println("Gagal mendapatkan ID Pengguna");
                        return;
                    }
                    String namaPengguna;
                    do{
                        System.out.print("> Nama Pengguna: ");
                        namaPengguna = input.nextLine();
                        if (namaPengguna.trim().isEmpty()|| !namaPengguna.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Nama pengguna tidak sesuai. Silakan coba lagi.");
                        }
                    }while(namaPengguna.trim().isEmpty()||!namaPengguna.matches("[a-zA-Z\\s]+"));
                    String nomorTelepon;
                    do{
                        System.out.print("> Nomor Telepon: ");
                        nomorTelepon = input.nextLine();
                        if (nomorTelepon.trim().isEmpty() || !nomorTelepon.matches("\\d{10,14}")) {
                            System.out.println("Nomor telepon tidak valid.  Harus terdiri dari 10 hingga 14 digit angka.");
                        }
                    }while(nomorTelepon.trim().isEmpty() || !nomorTelepon.matches("\\d{10,14}"));

                    String password;
                    do{
                        System.out.print("> Password: ");
                        password = input.nextLine();
                        if (password.trim().isEmpty() || password.length() < 6) {
                            System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                        }
                    }while(password.trim().isEmpty() || password.length() < 6);
                    Pengguna pengguna = new Pengguna(idPengguna, namaPengguna, nomorTelepon, password);
                    try {
                        auth.register(pengguna);
                        System.out.println("Akun berhasil didaftarkan!");
                    } catch (Exception e) {
                        if (e.getMessage().contains("duplikat")) {
                            System.out.println("Nomor telepon sudah digunakan.");
                        } else {
                            System.out.println("Gagal mendaftar akun");
                        }
                        return;
                    }
                }else if (pilihanDaftar == 0) {
                    continue menu;
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            }
            else if (menu == 0) {
                System.out.println("Terima kasih telah menggunakan aplikasi ini!");
                input.close();
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void menuPasokan(Scanner input, PasokanController crudpa, String idPemasok, String namaPemasok){
        int pilih;
        boolean back = false;
        System.out.println("Selamat Datang, " + namaPemasok + "!");
        while (!back) {
            System.out.println("""
                                \n===== MENU PASOKAN =====
                                  [1] Tambah Pasokan
                                  [2] Lihat Pasokan
                                  [3] Ubah Pasokan
                                  [4] Hapus Pasokan
                                  [0] Kembali
                                ========================""");
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            input.nextLine();
    
            switch (pilih) {
                case 1:
                    String namaBahan;
                    do {
                        System.out.print("Nama Bahan: ");
                        namaBahan = input.nextLine();
                        if (namaBahan.trim().isEmpty() || !namaBahan.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Nama bahan tidak sesuai. Silakan coba lagi.");
                        }
                    } while (namaBahan.trim().isEmpty() || !namaBahan.matches("[a-zA-Z\\s]+"));
                    double hargaSatuan = -1;
                    String inputStr;
                    
                    do {
                        System.out.print("Harga Satuan: ");
                        inputStr = input.nextLine().trim(); 
                    
                        if (inputStr.isEmpty()) {
                            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                            continue;
                        }
                    
                        if (inputStr.matches("^[0-9]+(\\.[0-9]+)?$")) {
                            hargaSatuan = Double.parseDouble(inputStr);
                            if (hargaSatuan <= 0) {
                                System.out.println("Harga harus lebih besar dari 0.");
                            }
                        } else {
                            System.out.println("Input tidak valid. Masukkan angka desimal (misal: 1500.00).");
                        }
                    } while (hargaSatuan <= 0);
                    int stok = -1;
                    do {
                        System.out.print("Stok: ");
                        inputStr = input.nextLine().trim();
                    
                        if (inputStr.isEmpty()) {
                            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                            continue;
                        }
                    
                        if (inputStr.matches("\\d+")) { 
                            stok = Integer.parseInt(inputStr);
                            if (stok < 0) {
                                System.out.println("Stok tidak boleh negatif.");
                            }
                        } else {
                            System.out.println("Input tidak valid. Masukkan angka bulat (misal: 10).");
                        }
                    } while (stok < 0);
                    
                    crudpa.tambahPasokan(idPemasok, namaBahan, hargaSatuan, stok);
                    System.out.print("\nTekan enter untuk melanjutkan...");
                    input.nextLine();
                    cs();
                    break;
                case 2:
                    crudpa.lihatPasokan();
                    System.out.print("\nTekan enter untuk melanjutkan...");
                    input.nextLine();
                    cs();
                    break;
                case 3:
                    crudpa.lihatPasokan();
                    System.out.println("\n=== UBAH PASOKAN ===");
                    System.out.print("Masukkan ID Pasokan yang ingin diubah: ");
                    String idUpdate = input.nextLine();
                    String newNama;
                    do{
                        System.out.print("Nama Pasokan baru: ");
                        newNama = input.nextLine();
                        if(newNama.trim().isEmpty() || !newNama.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Nama pasokan tidak sesuai. Silakan coba lagi.");
                        }
                    }while (newNama.trim().isEmpty() || !newNama.matches("[a-zA-Z\\s]+"));
                    double newHarga = -1; 
                    do {
                        System.out.print("Harga Satuan: ");
                        inputStr = input.nextLine().trim(); 
                    
                        if (inputStr.isEmpty()) {
                            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                            continue;
                        }
                    
                        if (inputStr.matches("^[0-9]+(\\.[0-9]+)?$")) {
                            newHarga = Double.parseDouble(inputStr);
                            if (newHarga <= 0) {
                                System.out.println("Harga harus lebih besar dari 0.");
                            }
                        } else {
                            System.out.println("Input tidak valid. Masukkan angka desimal (misal: 1500.00).");
                        }
                    } while (newHarga <= 0);
                   int newStok = -1;
                    do {
                        System.out.print("Stok: ");
                        inputStr = input.nextLine().trim();
                    
                        if (inputStr.isEmpty()) {
                            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                            continue;
                        }
                    
                        if (inputStr.matches("\\d+")) { 
                            newStok = Integer.parseInt(inputStr);
                            if (newStok < 0) {
                                System.out.println("Stok tidak boleh negatif.");
                            }
                        } else {
                            System.out.println("Input tidak valid. Masukkan angka bulat (misal: 10).");
                        }
                    } while (newStok < 0);
                    crudpa.updatePasokan(idUpdate, newNama, newHarga, newStok);
                    System.out.print("\nTekan enter untuk melanjutkan...");
                    input.nextLine();
                    cs();
                    break;
                case 4:
                    crudpa.lihatPasokan();
                    System.out.println("\n=== HAPUS PASOKAN ===");
                    System.out.print("Masukkan ID Pasokan yang ingin dihapus: ");
                    String idDelete = input.nextLine();
                    crudpa.hapusPasokan(idDelete);
                    System.out.print("\nTekan enter untuk melanjutkan...");
                    input.nextLine();
                    cs();
                    break;
                case 0:
                    back = true;
                    cs();
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    public static void menuPengguna(Scanner input, String idPengguna){
        // DB db = new DB();
        // CrudProduk crudp = new CrudProduk();
        PesanControl pesanControl = new PesanControl();
        ProdukControl produkControl = new ProdukControl();
        PemakaianControl pemakaianControl = new PemakaianControl();
        int pilih;
        String idProduk;
        boolean run = true;
        do {
            System.out.println("");
            System.out.println("""
                               ====== MENU PENGGUNA ======
                                [1] Lihat Bahan Baku 
                                [2] Kelola Produk     
                                [3] Pesan Bahan Baku  
                                [4] Lihat Riwayat Pesanan 
                                [5] Lihat Pemakaian Bahan
                                [0] Keluar            
                               ===========================""");
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            switch (pilih){
                case 1:
                    BahanBakuControl crudb = new BahanBakuControl();
                    crudb.tampilkanBahanBaku();
                    System.out.print("\nTekan enter untuk melanjutkan...");
                    input.nextLine();
                    break;
                case 2:
                    while (true) {
                        System.out.println("\n=== MENU PRODUK ===");
                        System.out.println("[1] Tambah Produk");
                        System.out.println("[2] Lihat Produk");
                        System.out.println("[3] Ubah Jumlah Produk");
                        System.out.println("[4] Hapus Produk");
                        System.out.println("[0] Kembali");
                        System.out.print("Pilih menu: ");
                        int pilihan = input.nextInt();
                        input.nextLine(); // Clear the newline character
                        
                        if (pilihan == 0) {
                            break; // Kembali ke menu utama
                        }
                        
                        switch (pilihan) {
                            case 1:
                                produkControl.buatProduk(idPengguna, input);
                                break;
                            case 2:
                                produkControl.tampilkanProduk();
                                break;
                            case 3:
                                System.out.print("Masukkan ID Produk yang ingin diubah: ");
                                idProduk = input.nextLine();
                                System.out.println("Masukkan perubahan jumlah:");
                                int jumlahBaru = input.nextInt();
                                produkControl.ubahJumlahProduk(idProduk, jumlahBaru);
                                break;
                            case 4:
                                System.out.print("Masukkan ID Produk yang ingin diubah: ");
                                idProduk = input.nextLine();
                                produkControl.hapusProduk(idProduk);
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n=== PESAN BAHAN BAKU ===");
                    String idPesanan;
                    try {
                        idPesanan = pesanControl.generateIdPesanan();
                    } catch (Exception e) {
                        System.out.println("Gagal mendapatkan ID Pesanan");
                        return;
                    }
                    pesanControl.tampilkanDaftarPasokan();
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Masukkan ID Pasokan yang ingin dipesan: ");
                    String idPasokan = sc.nextLine();
                    int jumlahPesan = -1;
                    do{
                        System.out.print("Jumlah Pesan: ");
                        String inputStr = sc.nextLine().trim();
                        
                        if (inputStr.isEmpty()) {
                            System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                            continue;
                        }
                        
                        if (inputStr.matches("\\d+")) { 
                            jumlahPesan = Integer.parseInt(inputStr);
                            if (jumlahPesan < 0) {
                                System.out.println("Jumlah pesan harus lebih besar dari 0.");
                            }
                        } else {
                            System.out.println("Input tidak valid. Masukkan angka bulat (misal: 10).");
                        }
                    }while(jumlahPesan <= 0);
                    try {
                        pesanControl.buatPesanan(idPesanan, idPengguna, idPasokan, jumlahPesan);        
                    } catch (Exception e) {
                        System.out.println("Gagal membuat pesanan");
                    }
                    break;
                case 4:
                    System.out.println("\n=== RIWAYAT PESANAN ===");
                    pesanControl.tampilkanRiwayatPesanan(idPengguna);
                    break;
                case 5:
                    System.out.println("\n=== RIWAYAT PEMAKAIAN ===");
                    pemakaianControl.lihatRiwayatPemakaian();
                    for (Pemakaian pemakaian : pemakaianControl.lihatRiwayatPemakaian()) {
                        System.out.println("Nama Bahan: " + pemakaian.getNamaBahan() + ", Jumlah: " + pemakaian.getJumlah());
                    }
                case 0:
                    run = false;
                    break;
                default:
                    System.out.println("Inputan tidak sesuai");
            }
        } while(run);
    }
}