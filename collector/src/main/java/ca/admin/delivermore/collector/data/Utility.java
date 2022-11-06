package ca.admin.delivermore.collector.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utility {

    public static Utility instance = null;

    public static Utility getInstance() {
        if (Utility.instance == null) {
            Utility.instance = new Utility();
        }
        return Utility.instance;
    }

    public Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("M");
    private static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("d");
    private static final DateTimeFormatter DAY_MONTH_FORMAT = DateTimeFormatter.ofPattern("M/d");
    private static final String DASH = "-";

    public static String dateRangeFormatted(LocalDate startDate, LocalDate endDate) {
        String formattedDateRange;

        // Get a comparison result to determine if the Months are the same
        String startDateMonth = MONTH_FORMAT.format(startDate);
        String endDateMonth = MONTH_FORMAT.format(endDate);

        if (startDateMonth.equals(endDateMonth))
        {
            formattedDateRange = DAY_MONTH_FORMAT.format(startDate) + DASH + DAY_FORMAT.format(endDate);
        }
        else
        {
            // Months don't match, split the string across both months
            formattedDateRange = DAY_MONTH_FORMAT.format(startDate) + DASH + DAY_MONTH_FORMAT.format(endDate);
        }
        return formattedDateRange;
    }

    public static void emptyDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    emptyDir(f);
                }
            }
        }
        file.delete();
    }

    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }


}
