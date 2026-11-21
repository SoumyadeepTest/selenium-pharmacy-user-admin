package utils;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

    public static Object[][] readExcelData(
            String filePath, String sheetName) throws IOException {

        List<Object[]> data = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();

        try (InputStream input = Files.newInputStream(Path.of(filePath)); Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName
                );
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String mobileNumber = formatter.formatCellValue(
                        row.getCell(0)
                ).trim();

                String otp = formatter.formatCellValue(
                        row.getCell(1)
                ).trim();

                if (mobileNumber.isEmpty() || otp.isEmpty()) {
                    continue;
                }

                data.add(new Object[]{
                    mobileNumber,
                    otp
                });
            }
        }

        return data.toArray(new Object[0][]);
    }
}
