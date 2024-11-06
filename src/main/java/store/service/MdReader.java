package store.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import store.domain.MdData;
import store.domain.MdErrors;

public class MdReader {
    BufferedReader reader;

    public MdReader(String path) {
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T extends MdData<?>> T addItems(T data) {
        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                data.addItem(line);
            }

            reader.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }

        return data;
    }

}
