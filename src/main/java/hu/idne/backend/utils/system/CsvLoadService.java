package hu.idne.backend.utils.system;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
@Service
public class CsvLoadService<E> {

    public List<E> load(Class clazz, String fileName) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), StandardCharsets.UTF_8))) {

            CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<E> result = csvToBean.parse();
            reader.close();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
