package com.project.passwordSecurity.myUtil;
import com.project.passwordSecurity.__db.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PasswordManager {

    private static java.util.Scanner scanner = new java.util.Scanner(System.in);

    /**
     * Method yang berfungsi untuk menampilkan menu password manager
     */
    public static void menuPasswordManager() {
        while (true) {
            int ans;
            boolean continueProgram;
            System.out.println("Selamat datang ke Program Password Manager");
            System.out.println("1. Buat Password");
            System.out.println("2. Lihat Password");
            System.out.println("3. Ubah Password");
            System.out.println("4. Hapus Password");
            System.out.println("0. Berhenti");
            System.out.print("Masukan input anda > ");
            ans = MenuGeneratorPassword.setDigit();
            // System.out.println(ans);
            // scanner.nextLine();
            if (ans == 1) menuAddPassword();
            if (ans == 2) menuReadPassswordByNamePassword();
            if (ans == 3) menuChangePassword();
            if (ans == 4) menuDeletePassword();
            if (ans == 0) break;
            if (ans > 4) {
                System.out.println("input yang anda masukan tidak valid!!.\n\n");
            System.out.println("\nApakah anda ingin lanjut menggunakan program ini lagi ? (y/n)");
            System.out.print("> ");
            continueProgram = MenuGeneratorPassword.isValidChoice();
            if (continueProgram == false) break;
            }
        }
    }

    /**
     * Method yang berfungsi untuk menampilkan menu penambahan password
     */
    public static void menuAddPassword() {
        System.out.println( "\n=================================================================================");
        String title, email, userName, password, notes;
        title = setTitle();
        System.out.println("Masukan email : ");
        System.out.print("> ");
        email = scanner.nextLine();
        System.out.println("Masukan nama pengguna : ");
        System.out.print("> ");
        userName = scanner.nextLine();
        password = setPassword();
        System.out.println("Masukan Catatan : ");
        System.out.print("> ");
        notes = scanner.nextLine();
        Data.toSecure(false, true);
        Data.createDataBase(title, email, userName, password, notes, getCurrentDateTime());
        Data.toSecure(true, false);
        System.out.print( "=================================================================================\n");
    }

    /**
     * Method yang berfungsi untuk menampilkan menu membaca password
     */
    public static void menuReadPassswordByNamePassword() {
        Data.readDatabaseByTitleAndAccount();
        String findPassword;
        System.out.println("Masukan nama password yang ingin anda lihat datanya :");
        System.out.print("> ");
        findPassword = checkName();
        Data.toSecure(false, true);
        System.out.print( "\n=================================================================================");
        Data.readDataPasswordByName(findPassword);
        System.out.println( "=================================================================================");
        Data.toSecure(true, false);
    }

    /**
     * Method yang berfungsi untuk menampilkan menu ganti password
     */
    public static void menuChangePassword() {
        Data.readDatabaseByTitleAndAccount();
        System.out.print( "===========================================================\n");
        String changePassword, passwordName;
        System.out.println("Masukan nama password yang ingin anda ubah passwordnya :");
        System.out.print("> ");
        passwordName = checkName();
        Data.toSecure(false, true);
        Data.readDataPasswordByName(passwordName);
        Data.toSecure(true, false);
        System.out.print( "=================================================================================\n\n");
        System.out.println("Masukan password baru anda : ");
        System.out.print("> ");
        Data.toSecure(false, true);
        changePassword = setPassword();
        Data.updateDatabase(changePassword, passwordName);
        Data.toSecure(true, false);
        System.out.println("Data berhasil diubah");
        System.out.print( "=================================================================================\n");
    }

    /**
     * Method yang berfungsi untuk menampilkan menu hapus password
     */
    public static void menuDeletePassword() {
        Data.toSecure(false, true);
        Data.readDatabaseByTitleAndAccount();
        String passwordName;
        System.out.println("Masukan nama password yang anda ingin hapus datanya?");
        System.out.print("> ");
        passwordName = checkName();
        Data.deleteDatabaseByTitle(passwordName);
        Data.toSecure(true, false);
    }

    /**
     * Method yang berfungsi untuk memberi judul password sekaligus memvalidasi apakah
     * nama password sudah ada di database atau belum
     * @return sebuah nama password
     */
    public static String setTitle() {
        String title;
        System.out.println("Masukan nama password: ");
        System.out.print("> ");
        title = scanner.nextLine();
        while (Data.isNamePasswordFound(title)) {
           System.out.print("nama password telah diguankan!!!.\n");
           System.out.print("> ");
           title = scanner.nextLine();
        }
        return title;
    }

    /**
     * Method yang berfungsi untuk mendapatkan data waktu dan tanggal
     * @return data waktu dan tanggal
     */
    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d - yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        return "Last updated on " + formattedDateTime;
    }

    /**
     * Method yang berfungsi untuk membuat password
     * @return password
     */
    public static String setPassword() {
        int ans;
        String password;
        System.out.println("Pilih menu berikut sebelum lanjut : ");
        System.out.println("1. Saya ingin membuat password sendiri.");
        System.out.println("2. Saya ingin password hasil generator.");
        System.out.print("> ");
        while (true) {
            ans = MenuGeneratorPassword.setDigit();
            // scanner.nextLine();
            if (ans == 1) {
                System.out.println("Masukan kata sandi yang ingin anda buat : ");
                System.out.print("> ");
                password = scanner.nextLine();
                return password;
            }
            if (ans == 2) {
                return password = MenuGeneratorPassword.passwordGeneratorMenu();
            }
            System.out.println("input anda salah, silahkan coba lagi!!");
        }
    }

    /**
     * Method yang berfungsi untuk megvalidasi ketika sebuah nama yang sudah ada di database
     * maka user harus memasukan nama lagi
     * @return name
     */
    public static String checkName() {
        String name = scanner.nextLine();
        boolean result = Data.isNamePasswordFound(name);
        while (result == false) {
        System.out.println("Nama yang anda inputkan salah!!, coba lagi");
        System.out.print("> ");
        name = scanner.nextLine();
        result = Data.isNamePasswordFound(name);
       }
       return name;
    }
}
