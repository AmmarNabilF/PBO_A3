package com;
import com.auth.auth;
import com.control.CrudBahan;
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
        
        while (true) {
            System.out.println("=== SELAMAT DATANG DI MARTSA ===");
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
                            System.out.print("> NomorTelp.: ");
                            String notelp = input.nextLine();
                            System.out.print("> Password: ");
                            String password = input.nextLine();
                            try {
                                Pemasok pemasok = (Pemasok) auth.login(notelp, password);
                                if (pemasok != null) {
                                    System.out.println("Login berhasil! Selamat datang, " + pemasok.getNamaPemasok() + "!");
                                    menuPasokan(input, crudpa, auth.getCurrentUserId());
                                    break;
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
                            System.out.print("> NomorTelp.: ");
                            String notelp = input.nextLine();
                            System.out.print("> Password: ");
                            String password = input.nextLine();
                            try {
                                Pengguna pengguna = (Pengguna) auth.login(notelp, password);
                                if (pengguna != null) {
                                    System.out.println("Login berhasil! Selamat datang, " + pengguna.getNamaPengguna() + "!");
                                    menuUtama(input);
                                    break;
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
                    System.out.print("> Nama Pemasok: ");
                    String namaPemasok = input.nextLine();
                    System.out.print("> Nomor Telepon: ");
                    String nomorTelepon = input.nextLine();
                    System.out.print("> Password: ");
                    String password = input.nextLine();
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
                    System.out.print("> Nama Pengguna: ");
                    String namaPengguna = input.nextLine();
                    System.out.print("> Nomor Telepon: ");
                    String nomorTelepon = input.nextLine();
                    System.out.print("> Password: ");
                    String password = input.nextLine();
                    Pengguna pengguna = new Pengguna(idPengguna, namaPengguna, nomorTelepon, password);
                    try {
                        auth.register(pengguna);
                        System.out.println("Akun berhasil didaftarkan!");
                    } catch (Exception e) {
                        System.out.println("Gagal mendaftar akun");
                        return;
                    }
                } else if (pilihanDaftar == 0) {
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
            System.out.println("1. Tambah Pasokan");
            System.out.println("2. Lihat Semua Pasokan");
            System.out.println("3. Update Pasokan");
            System.out.println("4. Hapus Pasokan");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            input.nextLine();
    
            switch (pilih) {
                case 1:
                    System.out.print("Nama Bahan: ");
                    String namaBahan = input.nextLine();
                    System.out.print("Harga Satuan: ");
                    double hargaSatuan = input.nextDouble();
                    System.out.print("Stok: ");
                    int stok = input.nextInt();
                    input.nextLine(); // consume newline
                    crudpa.tambahPasokan(idPemasok, namaBahan, hargaSatuan, stok);
                    break;
                case 2:
                    crudpa.lihatPasokan();
                    break;
                case 3:
                    System.out.print("Masukkan ID Pasokan yang ingin diubah: ");
                    String idUpdate = input.nextLine();
                    System.out.print("Nama Bahan Baru: ");
                    String newNama = input.nextLine();
                    System.out.print("Harga Satuan Baru: ");
                    double newHarga = input.nextDouble();
                    System.out.print("Stok Baru: ");
                    int newStok = input.nextInt();
                    input.nextLine(); // consume newline
                    crudpa.updatePasokan(idUpdate, newNama, newHarga, newStok);
                    break;
                case 4:
                    System.out.print("Masukkan ID Pasokan yang ingin dihapus: ");
                    String idDelete = input.nextLine();
                    crudpa.hapusPasokan(idDelete);
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    public static void menuUtama(Scanner input){
        CrudProduk crudp = new CrudProduk();
        CrudBahan crudb = new CrudBahan();
        CrudResep crudr = new CrudResep();
        int pilih;
        boolean run = true;
        do {
            System.out.println("");
            System.out.println("""
                               ======= MENU UTAMA =======
                               :: 1. Kelola Bahan Baku ::
                               :: 2. Kelola Produk     ::
                               :: 3. Kelola Resep      ::
                               :: 4. Pesan Bahan Baku  ::
                               :: 5. Keluar            ::
                               ==========================
                               """);
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            
            switch (pilih){
                case 1:
                    menuBahanBaku(input, crudb);
                    break;
                case 2:
                    menuProduk(input, crudp);
                    break;
                case 3:
                    menuResep(input, crudr);
                    break;
                case 4:
                    pesanBahan(input);
                    break;
                case 5:
                    run = false;
                    break;
                default:
                    System.out.println("Inputan tidak sesuai");
            }
        
        }while(run);
        input.close();
    }
    
    
    public static void menuBahanBaku(Scanner input, CrudBahan crudb){
        int pilih;
        boolean back = true;
        do {
            System.out.println("\n===== CRUD BAHAN BAKU =====");
            System.out.println("1. Tambah Bahan");
            System.out.println("2. Tampilkan Bahan");
            System.out.println("3. Hapus Bahan");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu: ");
            pilih = input.nextInt();
            input.nextLine(); 

            switch (pilih) {
                case 1:
                    System.out.print("ID Bahan: ");
                    String idBahan = input.nextLine();
                    System.out.print("Nama Bahan: ");
                    String namaBahan = input.nextLine();
                    System.out.print("Kategori: ");
                    String kategori = input.nextLine();
                    System.out.print("Stok: ");
                    int stok = input.nextInt();
                    input.nextLine();
                    System.out.print("Satuan: ");
                    String satuan = input.nextLine();
                    
                    System.out.print("Tanggal Expired (YYYY-MM-DD): ");
                    LocalDate tanggalexp = inputTanggal(input, "Tanggal expired");
                    
                    BahanBaku bahan = new BahanBaku(idBahan, namaBahan, kategori, stok, satuan, tanggalexp);
                    crudb.tambahBahan(bahan);
                    System.out.println("Bahan berhasil ditambahkan!");
                    break;
                case 2:
                    crudb.showBahan();
                    break;
                case 3:
                    System.out.print("Masukkan ID bahan yang akan dihapus: ");
                    String idHapus = input.nextLine();
                    if (crudb.delBahan(idHapus)) {
                        System.out.println("Bahan berhasil dihapus.");
                    } else {
                        System.out.println("ID tidak ditemukan.");
                    }
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }while (!back);
    }
        
    public static void menuProduk(Scanner input, CrudProduk crudp){
        int pilih;
        boolean back = true;
        do {
            System.out.println(" ");
            System.out.println("MENU PRODUK");
            System.out.println("1. Tambah produk");
            System.out.println("2. Lihat produk");
            System.out.println("3. Cari produk");
            System.out.println("4. Perbarui produk");
            System.out.println("5. Hapus produk");
            System.out.println("6. Kembali");
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
                
                case 6:
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
            System.out.println("1. Buat resep");
            System.out.println("2. Lihat resep");
            System.out.println("3. Hapus resep");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu: ");
            int pilih = input.nextInt();
            input.nextLine();
            
            switch(pilih){
                case 1:
                    getInsertQuery(crudr, input);
                    break;
                case 2:
                    getSelectAllQuery(crudr, input);
                    break;
                case 3:
                    getDeleteQuery(crudr, input);
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Inputan tidak tepat");
            }
        }
    }
    
    public static void getInsertQuery(CrudResep crudr, Scanner input){
        System.out.print("ID Resep: ");
        String idResep = input.nextLine();
        System.out.print("ID Bahan: ");
        String idBahan = input.nextLine();
        System.out.print("Nama Resep: ");
        String namaResep = input.nextLine();
        System.out.print("Jumlah Digunakan: ");
        int jmlhDigunakan = input.nextInt();
        input.nextLine(); // consume newline
        
        Resep resep = new Resep(idResep, namaResep, idBahan, jmlhDigunakan);
        crudr.tambah(resep);
        System.out.println("Resep berhasil ditambahkan!");
    }
    
    public static void getSelectAllQuery(CrudResep crudr, Scanner input){
        System.out.println("RESEP PRODUK MARTSA");
        crudr.show();
    }

    public static void getDeleteQuery(CrudResep crudr, Scanner input){
        System.out.print("Masukkan ID Resep yang ingin dihapus: ");
        String idHapus = input.nextLine();
        Resep resep = new Resep(idHapus, "", "", 0);
        
        if (crudr.hapus(resep)){
            System.out.println("Resep berhasil dihapus.");
        } else {
            System.out.println("ID tidak ditemukan.");
        }
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