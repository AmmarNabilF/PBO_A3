package com;
import com.auth.auth;
import com.control.BahanBakuControl;
import com.control.CrudProduk;
import com.control.CrudResep;
import com.control.PasokanController;
import com.model.BahanBaku;
import com.model.Pasokan;
import com.model.Produk;
import com.model.Resep;
import com.model.TransaksiMasuk;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.model.Transaksi;
import com.model.Pemasok;
import com.model.Pengguna;
import com.model.Pesan;
import com.control.PesanControl;

public class Main {
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
        
        menu:
        while (true) {
            System.out.println("\n=== SELAMAT DATANG DI MARTSA ===");
            System.out.println("[1] Login");
            System.out.println("[2] Daftar");
            System.out.println("[0] Keluar");
            System.out.print("Pilih menu: ");
            int menu = input.nextInt();
            input.nextLine(); 
            if (menu == 1) {
                while (true) {
                    System.out.println("\n>>> !!LOGIN AKUN PEMASOK!! <<<");
                    System.out.println("[1] Pemasok");
                    System.out.println("[2] Pengguna");
                    System.out.println("[0] Kembali");
                    System.out.print("Pilih menu: ");
                    int pilihanLogin = input.nextInt();
                    input.nextLine();
                    if (pilihanLogin == 1) {
                        while (login > 0) {
                            System.out.println("\n>>> !!Masukkan Akun Pemasok!! <<<");
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
                                System.out.print("> Password    : ");
                                password = input.nextLine();
                                if (password.trim().isEmpty() || password.length() < 6) {
                                    System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                                }
                            } while (password.trim().isEmpty() || password.length() < 6);
                            try {
                                Pemasok pemasok = (Pemasok) auth.login(notelp, password);
                                if (pemasok != null) {
                                    System.out.println("Login berhasil! Selamat datang, " + pemasok.getNamaPemasok() + "!");
                                    menuPasokan(input, crudpa, auth.getCurrentUserId());
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
                            System.out.println("\n>>> !!Masukkan Akun Pengguna!! <<<");
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
                                System.out.print("> Password    : ");
                                password = input.nextLine();
                                if (password.trim().isEmpty() || password.length() < 6) {
                                    System.out.println("Password tidak valid. Harus terdiri dari minimal 6 karakter.");
                                }
                            } while (password.trim().isEmpty() || password.length() < 6);
                            try {
                                Pengguna pengguna = (Pengguna) auth.login(notelp, password);
                                if (pengguna != null) {
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
                        System.out.println("Kembali ke menu utama.");
                        break;
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                }
            }
            if (menu == 2) {
                System.out.println("\n>>> !!DAFTAR AKUN!! <<<");
                System.out.println("[1] Pemasok");
                System.out.println("[2] Pengguna");
                System.out.println("[0] Kembali");
                System.out.print("Pilih menu: ");
                int pilihanDaftar = input.nextInt();
                input.nextLine();
                if (pilihanDaftar == 1) {
                    System.out.println("\n>>> !!DAFTAR AKUN PEMASOK!! <<<");
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
                        System.out.println("Gagal mendaftar akun");
                        return;
                    }
                } else if (pilihanDaftar == 2) {
                    System.out.println("\n>>> !!DAFTAR AKUN PENGGUNA!! <<<");
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
                        System.out.println("Gagal mendaftar akun");
                        return;
                    }
                }else if (pilihanDaftar == 0) {
                    System.out.println("Kembali ke menu utama.");
                    break;
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

    public static void menuPasokan(Scanner input, PasokanController crudpa, String idPemasok){
        int pilih;
        boolean back = false;
        while (!back) {
            System.out.println("\nMENU PASOKAN BAHAN BAKU");
            System.out.println("[1] Tambah Pasokan");
            System.out.println("[2] Lihat Semua Pasokan");
            System.out.println("[3] Update Pasokan");
            System.out.println("[4] Hapus Pasokan");
            System.out.println("[0] Keluar");
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
                        inputStr = input.nextLine().trim(); // hapus spasi
                    
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
                    break;
                case 2:
                    crudpa.lihatPasokan();
                    break;
                case 3:
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
                        inputStr = input.nextLine().trim(); // hapus spasi
                    
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
                    break;
                case 4:
                    System.out.print("Masukkan ID Pasokan yang ingin dihapus: ");
                    String idDelete = input.nextLine();
                    crudpa.hapusPasokan(idDelete);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    public static void menuPengguna(Scanner input, String idPengguna){
        CrudProduk crudp = new CrudProduk();
        CrudResep crudr = new CrudResep();
        PesanControl pesanControl = new PesanControl();
        int pilih;
        boolean run = true;
        do {
            System.out.println("");
            System.out.println("""
                               ======= MENU PENGGUNA ====
                                [1] Kelola Bahan Baku 
                                [2] Kelola Produk     
                                [3] Kelola Resep      
                                [4] Pesan Bahan Baku  
                                [5] Lihat Riwayat Pesanan 
                                [0] Keluar            
                               ==========================
                               """);
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            
            switch (pilih){
                case 1:
                    menuBahanBaku(input);
                    break;
                case 2:
                    menuProduk(input, crudp);
                    break;
                case 3:
                    menuResep(input, crudr);
                    break;
                case 4:
                    pesanBahan(input);
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
                        String inputStr = sc.nextLine().trim(); // hapus spasi
                        
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
                        System.out.println("Gagal membuat pesanan: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("\n=== RIWAYAT PESANAN ===");
                    pesanControl.tampilkanRiwayatPesanan(idPengguna);
                    break;
                case 0:
                    run = false;
                    break;
                default:
                    System.out.println("Inputan tidak sesuai");
            }
        } while(run);
    }

    public static void menuBahanBaku(Scanner input) {
        BahanBakuControl crudb = new BahanBakuControl();
        boolean back = false;
        while (!back) {
            System.out.println("\n===== MENU BAHAN BAKU =====");
            System.out.println("[1] Lihat Daftar Bahan Baku");
            System.out.println("[2] Hapus Bahan Baku");
            System.out.println("[3] Kembali");
            System.out.print("Pilih menu: ");
            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    crudb.tampilkanBahanBaku();
                    break;
                case 2:
                    System.out.print("Masukkan ID bahan yang akan dihapus: ");
                    String idHapus = input.nextLine();
                    crudb.hapusBahanBaku(idHapus);
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
        
    public static void menuProduk(Scanner input, CrudProduk crudp){
        int pilih;
        boolean back = true;
        do {
            System.out.println(" ");
            System.out.println("MENU PRODUK");
            System.out.println("[1] Tambah produk");
            System.out.println("[2] Lihat produk");
            System.out.println("[3] Cari produk");
            System.out.println("[4] Perbarui produk");
            System.out.println("[5] Hapus produk");
            System.out.println("[0] Kembali");
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            input.nextLine();
            
            switch (pilih){
                case 1:
                    System.out.print("ID Produk: ");
                    String idProduk = input.nextLine();
                    System.out.print("Nama Produk: ");
                    String nama = input.nextLine();                    
                    System.out.print("Kategori Produk: ");
                    String kategori = input.nextLine();
                    System.out.print("Harga: ");
                    Double harga = input.nextDouble();
                    String idResep = "RE"; // id resep nanti diubah
                    
                    Produk produk = new Produk(idProduk, idResep, nama, kategori, harga);
                    crudp.simpanProduk(produk);
                    System.out.println("Produk berhasil ditambahkan");
                    break;
                    
                case 2:
                    System.out.println("DAFTAR PRODUK MARTSA");
                    crudp.showProduk();
                    break;
                    
                case 3:
                    System.out.println("\nCARI PRODUK DENGAN NAMA ATAU ID\n");
                    System.out.print("Cari: ");
                    String keyword = input.nextLine();
                    
                    Produk hasil = crudp.searchProduk(keyword);
                    
                    if (hasil == null){
                        hasil = crudp.searchProduk(keyword, true);
                    }
                    if (hasil != null){
                        System.out.println("Produk ditemukan");
                        System.out.println(hasil);
                    }
                    else{
                        System.out.println("Produk tidak ditemukan");
                    }
                    
                    break;
                    
                case 4:
                    System.out.print("Masukkan ID produk yang ingin diubah: ");
                    String newID = input.nextLine();
                    System.out.print("Nama baru: ");
                    String newNama = input.nextLine();
                    System.out.print("Kategori: ");
                    String newKategori = input.nextLine();
                    System.out.print("Harga baru: ");
                    Double newHarga = input.nextDouble();
                    
                    if (crudp.updateProduk(newID, newNama, newKategori, newHarga)){
                        System.out.println("Produk telah diperbarui");
                    }
                    else {
                        System.out.println("Produk tidak ditemukan");
                    }
                    break;
                    
                case 5:
                    System.out.print("Masukkan ID produk yang ingin dihapus: ");
                    String delID = input.nextLine();
                    
                    if (crudp.delProduk(delID)){
                        System.out.println("Produk dihapus!!");
                    }
                    else {
                        System.out.println("Produk gagal dihapus :( ");
                    }
                    break;
                
                case 0:
                    back = true;
                    break;
                    
                default:
                    System.out.println("Inputan pilih tidak tepat");
            }
        } while (!back);
    }
    
    public static void menuResep(Scanner input, CrudResep crudr){
        boolean back = false;
        
        while (!back){
            System.out.println("\nMENU RESEP");
            System.out.println("[1] Buat resep");
            System.out.println("[2] Lihat resep");
            System.out.println("[0] Kembali");
            System.out.print("Pilih menu: ");
            int pilih = input.nextInt();
            input.nextLine();
            
            switch(pilih){
                case 1:
                    buatResep(crudr, input);
                    break;
                case 2:
                    lihatResep(crudr, input);
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Inputan tidak tepat");
            }
        }
    }
    
    public static void buatResep(CrudResep crudr, Scanner input){
        System.out.print("Masukkan ID Resep: ");
        String idResep = input.nextLine();
        System.out.print("Masukkan ID Produk: ");
        String idProduk = input.nextLine();
        
        boolean tambahBahan = true;
        while(tambahBahan){
            System.out.print("Masukkan ID Bahan: ");
            String idBahan = input.nextLine();
            System.out.print("Jumlah Digunakan: ");
            int jumlahDigunakan = input.nextInt();
            input.nextLine();
            System.out.print("Satuan: ");
            String satuan = input.nextLine();
            
            Resep resep = new Resep(idResep, idProduk, idBahan, jumlahDigunakan, satuan);
            crudr.catatResep(resep);
            
            System.out.print("Tambah lagi?: y/n: ");
            String lanjut = input.nextLine();
            if (!lanjut.equals("y")){
                tambahBahan = false;
            }
            System.out.println("");
        }
        System.out.println("Resep sudah dibuat!!");
    }
    
    public static void lihatResep(CrudResep crudr, Scanner input){
        System.out.println("RESEP PRODUK MARTSA");
        crudr.showResep();
    }
    
    public static void pesanBahan(Scanner input){
        input.nextLine();
        
        String idTransaksi = Transaksi.generateId("TR");

        System.out.print("ID Bahan: ");
        String idBahan = input.nextLine();
        System.out.print("ID Pemasok: ");
        String idPemasok = input.nextLine();
        System.out.print("Tanggal masuk(YYYY-MM-DD): ");
        String tanggal = input.nextLine();
        LocalDate tanggalMasuk = LocalDate.parse(tanggal);
        System.out.print("Jumlah: ");
        int jumlah = input.nextInt();
        System.out.print("Harga per unit: ");
        double hargaUnit = input.nextDouble();
        System.out.println("");
        TransaksiMasuk transaksi = new TransaksiMasuk(idTransaksi, idBahan, idPemasok, tanggalMasuk, jumlah, hargaUnit);
        transaksi.cetakResi();
        transaksi.informasi();
    }
    
    public static LocalDate inputTanggal(Scanner input, String teks){
        while(true){
            System.out.print(teks + " (YYYY-MM-DD): ");
            String tanggal = input.nextLine();
            try{
                return LocalDate.parse(tanggal);
            } catch(DateTimeParseException e){
                System.out.println("Format tidak sesuai, coba lagi!!");
            }  
        }
    }
}