package pl.sdacademy.spring.springdatatraining.csv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvLine implements Iterable<String> {

    private final List<String> elements;

    CsvLine() {
        this.elements = new ArrayList<>();
    }

    void addElement(String value) {
        elements.add(value);
    }

    public String getElementAt(int position) {
        if (position < elements.size()) {
            return elements.get(position);
        }
        return null;
    }

    static CsvLine fromTextLine(String textLine) {
        String[] splitLine = textLine.split(",");
        CsvLine csvLine = new CsvLine();
        for (String lineElement : splitLine) {
            csvLine.addElement(lineElement);
        }
        return csvLine;
    }

    @Override
    public Iterator<String> iterator() {
        return elements.iterator();
    }
}
