import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CausesOfDeath {
    public static void main(String[] args) {
        String fileName = "CausesOfDeath_France_2001-2008.csv";
        DecimalFormat decf = new DecimalFormat("#, ###");

        ArrayList<Death> list = new ArrayList<>(100000);

        long startTime = System.nanoTime();
        readBuffer(fileName, list);
        long endTime = System.nanoTime();

        System.out.println("Read buffer : " + decf.format(endTime - startTime));
        System.out.println(list.get(0).toString());

        //Write file
        fileName = "myCauseOfDeath.raf";
        writeRandomAccessFile(fileName, list);


        // Filter Cause death by YEAR
        ArrayList<Death> list2 = new ArrayList<>(100000);

        int year = 2001;
        readRandomAccessFileByYear(fileName, list2, year);
        System.out.println("Type of IDC10 in year: " + year);

        for (Death d: list2) {
            System.out.println(d.getICD10());
        }
    }


    public static void readBuffer(String fileName, ArrayList list) {
        FileReader fr;
        try {
            fr = new FileReader(fileName);
            BufferedReader bufR = new BufferedReader(fr);
            String line = "";
            while ((line = bufR.readLine()) != null) {
                if (!line.contains("TIME")) {
                    processLine(line, list);
                }
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CausesOfDeath.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CausesOfDeath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void processLine(String s, List list) {

        String str = s.replaceAll("\"", "");

        String[] dataInLine = str.split(",");

        Death death = new Death();
        if (dataInLine.length >= 5) {
            death = new Death(Integer.parseInt(dataInLine[0]), dataInLine[1], dataInLine[3], dataInLine[5]);

//            System.out.println("year" + death.getYear() +
//                    "country" + death.getCountry() +
//                    "sex" + death.getSex() +
//                    "cause" + death.getICD10());

        }
        list.add(death);
    }

    public static void  writeRandomAccessFile(String fileName, ArrayList<Death> list) {

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            for (Death d: list) {
                raf.writeInt(d.getYear());
                raf.write(d.getSexInBytes());
                raf.write(d.getCountryInBytes());
                raf.write(d.getICD10InBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readRandomAccessFileByYear(String fileName, ArrayList list, int searchYear) {

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int year = raf.readInt();

                if (year == searchYear) {

                    byte[] sex = new byte[Death.SEX_SIZE];
                    raf.readFully(sex);

                    byte[] country = new byte[Death.COUNTRY_SIZE];
                    raf.read(country);

                    byte[] icd = new byte[Death.ICD10_SIZE];
                    raf.read(icd);

                    Death d = new Death(year, new String(country), new String(sex), new String(icd));

                    list.add(d);
                }
            }

            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

