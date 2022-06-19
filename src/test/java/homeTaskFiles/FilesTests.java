package homeTaskFiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.zip.ZipFile;

public class FilesTests {

    //classloader который загружает класс
        ClassLoader classLoader = FilesTests.class.getClassLoader();

    @DisplayName("CSV file in zip")
        @Test
        void csvTest() throws Exception {
        //с помощью класс лоудера ищем зип
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("tests-zip.zip")).getFile());
        //достаем нужный файл в зипе
        ZipEntry entry = zipFile.getEntry("example.csv");
        List<String[]> list;
        InputStream inputStream = zipFile.getInputStream(entry);
        //читаем csv
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        list = csvReader.readAll();
        assertThat(list).contains(
                new String[] {"teacher","lesson","date"},
                new String[] {"Tuchs","junit","03.06"},
                new String[] {"Eroshenko","allure","07.06"}
        );
    }

    @DisplayName("PDF file in zip")
    @Test
    void pdfTest() throws Exception {
        //с помощью класс лоудера ищем зип
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("tests-zip.zip")).getFile());

        //достаем пдф файл в зипе
        ZipEntry entry = zipFile.getEntry("Poetry Foundation.pdf");
        PDF pdf;
        InputStream inputStream = zipFile.getInputStream(entry);
        pdf = new PDF(inputStream);
        assertThat(pdf.text).contains("To be, or not to be, that is the question:");

    }

    @DisplayName("XLSX file in zip")
    @Test
    void checkXLSTest() throws Exception {
        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("tests-zip.zip")).getFile());
        ZipEntry entry = zipFile.getEntry("example-x.xlsx");
        XLS xlsx;
        InputStream inputStream = zipFile.getInputStream(entry);
        xlsx = new XLS(inputStream);
        assertThat(xlsx.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue())
                .contains("lesson");
    }
}
