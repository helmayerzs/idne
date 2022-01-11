package hu.idne.backend.utils.system;

import hu.idne.backend.mappers.system.ExcelDtoMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;

public class HSSFExcelWriter<T,D> {

    private String sheetName = "data";

    private Integer rowNumber = 1;

    private Stream<T> inStream;

    private OutputStream excelOutStream;

    private ExcelDtoMapper<T,D> mapper;

    public HSSFExcelWriter(Stream<T> inStream, OutputStream excelOutStream, ExcelDtoMapper<T,D> mapper) {
        this.inStream = inStream;
        this.excelOutStream = excelOutStream;
        this.mapper = mapper;
    }

    public void write() throws IOException {
        try (Workbook workbook = new HSSFWorkbook()) {
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Sheet sheet = workbook.createSheet(sheetName);
            mapper.initCellStyles(workbook);
            mapper.fillHeader(sheet, workbook);
            inStream.forEach(e -> mapper.fillRow(mapper.toDto(e),sheet,rowNumber++));

            workbook.write(excelOutStream);
        }
    }
}
