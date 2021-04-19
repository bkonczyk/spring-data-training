package pl.sdacademy.spring.springdatatraining.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvFile implements Iterable<CsvLine> {

    private final List<CsvLine> lines;

    CsvFile() {
        lines = new ArrayList<>();
    }

    void addLine(CsvLine line) {
        lines.add(line);
    }

    public static CsvFile fromFile(File inputLocation) {
        CsvFile convertedFile = new CsvFile();
        try (FileReader inputStream = new FileReader(inputLocation)) {
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                convertedFile.addLine(CsvLine.fromTextLine(readLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

    @Override
    public Iterator<CsvLine> iterator() {
        return lines.iterator();
    }
}
