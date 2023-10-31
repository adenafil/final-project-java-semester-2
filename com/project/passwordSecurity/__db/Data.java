package com.project.passwordSecurity.__db;
import com.project.passwordSecurity.myUtil.PasswordManager;
import com.project.passwordSecurity.security.SecurityHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Data {

    private static final String FILEPATH = "com/project/passwordSecurity/__db/data.csv";

    /**
     * method untuk membuat database
     * @param title untuk memasukan nama ke database
     * @param email untuk memasukan email ke database
     * @param userName untuk memasukan userName ke database
     * @param password untuk memasukan password ke database
     * @param notes untuk memasukan catatan ke database
     * @param setLastUpdated memasukan tanggal pembuatan/perubahan password 
     */
    public static void createDataBase(String title, String email, String userName, String password, String notes, String setLastUpdated) {
        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[] { title, email, userName, password, notes, setLastUpdated });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            for (String[] body : data) {
                writer.write(String.join(",", body));
                writer.newLine();
            }
            System.out.println("Data berhasil dibuat!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method yang berfungsi membaca database dengan menampilkan output
     * title atau judul nama password, email dan last updated
     */
    public static void readDatabaseByTitleAndAccount() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            int indexData = 0;
            boolean printFirst = true;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int i = 0;
                for (String db : data) {
                    if (printFirst == false && i == 0) {
                        System.out.println( "=================================================================================");
                        System.out.println("title : " + db + "\t\tData Modified : " + data[5]);
                    }
                    if (printFirst == false && i == 1) {
                        System.out.println("email : " + db);
                        System.out.println("=================================================================================");
                    }
                    if (printFirst && i == 1)
                        printFirst = false;
                    i++;
                    indexData++;
                }
            }
            emmptyDataHandler(indexData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method yang berfungsi membaca database password terentu
     * @param passwordName nama dari password akun
     */
    public static void readDataPasswordByName(String passwordName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            int indexData = 0;
            boolean printFirst = true;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int i = 0;
                for (String db : data) {
                    //System.out.println("data "+ data[3]);
                    if (printFirst == false && i == 0 && data[0].equals(passwordName)) {
                        System.out.println("\nData akun berhasil dibaca.");
                        System.out.println("nama password    : " + db);
                    }
                    if (printFirst == false && i == 1  && data[0].equals(passwordName))
                        System.out.println("email            : " + db);
                    if (printFirst == false && i == 2  && data[0].equals(passwordName))
                        System.out.println("username         : " + db);
                    if (printFirst == false && i == 3  && data[0].equals(passwordName))
                        System.out.println("password         : " + db);
                    if (printFirst == false && i == 4  && data[0].equals(passwordName))
                        System.out.println("notes            : " + db);
                    if (printFirst == false && i == 5  && data[0].equals(passwordName))
                        System.out.println(db);
                    if (printFirst && i == 5)
                        printFirst = false;
                    i++;
                    indexData++;
                }
            }
            emmptyDataHandler(indexData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method yang brfungsi untuk mengubah isi database
     * @param writeData adalah password yang baru
     * @param dataSearched adalah password yang lama dan akan diganti dengan writeData
     */
    public static void updateDatabase(String writeData, String dataSearched) {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String[] rowData : data) {
            for (int j = 0; j < rowData.length; j++) {
                if (rowData[j].equals(dataSearched)) {
                    rowData[3] = writeData;
                    rowData[5] = PasswordManager.getCurrentDateTime();
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            for (String[] rowData : data) {
                writer.write(String.join(",", rowData));
                writer.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method ini berfungsi untuk mengenkripsi dan dekripsi database
     * @param encrypted adalah parameter yang berfunsi untuk mengenkripsi data melalui nilai boolean
     * @param decrypted adalah parameter yang berfunsi untuk mengdekripsi data melalui nilai boolean
     */
    public static void toSecure(boolean encrypted, boolean decrypted) {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean table = true;
        for (String[] rowData : data) {
            if (table == false && encrypted) {
                rowData[1] = SecurityHandler.ToEncrypt(rowData[1]);
                rowData[2] = SecurityHandler.ToEncrypt(rowData[2]);
                rowData[3] = SecurityHandler.ToEncrypt(rowData[3]);
            }
            table = false;
        }

        table = true;
        for (String[] rowData : data) {
            if (table == false && decrypted) {
                rowData[1] = SecurityHandler.ToDecrypt(rowData[1]);
                rowData[2] = SecurityHandler.ToDecrypt(rowData[2]);
                rowData[3] = SecurityHandler.ToDecrypt(rowData[3]);
            }
            table = false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            for (String[] rowData : data) {
                writer.write(String.join(",", rowData));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Method yang berfungsi untuk menghapus database
     * @param deletedName adalah parameter yang harus diisi nama akun yang akan dihapus pada database
     */
    public static void deleteDatabaseByTitle(String deletedName) {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String[]> newData = new ArrayList<>();
        for (String[] rowData : data) {
            if (rowData.length >= 1 && !rowData[0].equals(deletedName)) {
                newData.add(rowData);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            for (String[] rowData : newData) {
                writer.write(String.join(",", rowData));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data berhasil dihapus");
    }

    /**
     * Method yang akan mengecek atau memvalidasi apakah nama akun yang sudah diinput oleh pengguna ada dan juga
     * memvalidasi bahwasanya didalam 1 database sebuah nama akun harus bersifat unique
     * @param passwordName adalah parameter yang akakn digunakan untuk mengecek nama password
     * @return apakah nama password ada di database atau tidak
     */
    public static boolean isNamePasswordFound(String passwordName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                for (var check : data) {
                    if (check.equals(passwordName)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method yang berfungsi memberitahukan pengguna bahwa data di database kosong
     * @param indexData adalah parameter jumlah index yang ada, jika 6 berarti masih kosong.
     */
    public static void emmptyDataHandler(int indexData) {
        if (indexData == 6) {
            System.out.print( "\n=================================================================================\n");
            System.out.println("        Karena data anda masih kosong, anda akan dialihkan ke menu awal.");
            System.out.print( "=================================================================================\n\n");
            PasswordManager.menuPasswordManager();
        }
    }
    

}
