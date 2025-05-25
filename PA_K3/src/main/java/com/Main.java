package com;
import com.auth.auth;
import com.crud.CrudProduk;
import com.crud.CrudBahan;
import com.crud.CrudResep;
import com.model.BahanBaku;
import com.model.Produk;
import com.model.Resep;
import com.model.TransaksiMasuk;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.model.Transaksi;
import com.model.Pemasok;

public class Main {
    public static void main(String[] args) {
        // cek koneksi database
        DB db = new DB();
        if (db.conn != null){
            System.out.println("Koneksi ke database berhasil!");
        } else {
            System.out.println("Koneksi ke database gagal!");
            return;
        }
        Scanner input = new Scanner(System.in);
        int login = 3;

        System.out.println("=== SELAMAT DATANG DI MARTSA ===");
        System.out.println("[1] Login");
        System.out.println("[2] Daftar Akun Pemasok");

        System.out.print("Pilih menu: ");
        int menu = input.nextInt();
        input.nextLine(); // Clear the newline character
        if (menu == 1){
            System.out.println("\n>>> !!MASUKKAN AKUN PEMASOK!! <<<");
            System.out.print("> Nomor telepon: ");
            String nomorTelepon = input.nextLine();
            System.out.print("> Password: ");
            String password = input.nextLine();
            
            auth auth = new auth(db.conn);
            Pemasok pemasok;
            try {
                pemasok = auth.getAkun(nomorTelepon);
                if (pemasok != null && pemasok.getPassword().equals(password)) {
                    System.out.println("Login berhasil!");
                    menuUtama(input);
                } else {
                    System.out.println("nomor tidak tepat atau Password salah!");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Gagal login: " + e.getMessage());
                return;
            }

        }
        if (menu == 2){
            System.out.println("\n>>> !!DAFTAR AKUN PEMASOK!! <<<");
            System.out.print("> ID Pemasok: ");
            String idPemasok = input.nextLine();
            System.out.print("> Nama Pemasok: ");
            String namaPemasok = input.nextLine();
            System.out.print("> Nomor Telepon: ");
            int nomorTelepon = input.nextInt();
            input.nextLine(); // Clear the newline character
            System.out.print("> Password: ");
            String password = input.nextLine();
            
            Pemasok pemasok = new Pemasok(idPemasok, namaPemasok, nomorTelepon, password);
            
            auth auth = new auth(db.conn);
            try {
                auth.register(pemasok);
                System.out.println("Akun berhasil didaftarkan!");
            } catch (Exception e) {
                System.out.println("Gagal mendaftar akun: " + e.getMessage());
                return;
            }
        }
        
        // while (login > 0){
        //     System.out.println("\n>>> !!Masukkan Akun!! <<<");
        //     System.out.print("> Email: ");
        //     String email = input.nextLine();
        //     System.out.print("> Password: ");
        //     String password = input.nextLine();
            
        //     if (auth.signIn(email, password)){
        //         System.out.println(auth.getAdminData());
        //         menuUtama(input);
        //         break;
        //     }
        //     else {
        //         login--;
        //         System.out.println("!!Email atau Password tidak tepat!!");
        //         System.out.println("Kesempatan login tersisa: " + login);
        //     }
        // }
        if (login == 0){
            System.out.println("!!Program Berhenti!!");
        }
    input.close();
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
                    
                    Produk produk = new Produk(idProduk, nama, kategori, harga);
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
            System.out.println("3. Kembali");
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