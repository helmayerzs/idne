package hu.idne.backend.mappers.system;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelDtoMapper<T,D> {

    String[] getHeader();

    D toDto(T entity);

    void fillRow(D dto, Sheet sheet, Integer rowNumber);

    void initCellStyles(Workbook workbook);

    default void fillHeader(Sheet sheet, Workbook workbook){
        int i = 0;
        CellStyle headerStyle = createHeaderStyle(workbook);
        Row row = sheet.createRow(0);
        for(String s : getHeader()){
            Cell cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(s);
            cell.setCellStyle(headerStyle);
        }
    }

    default CellStyle createHeaderStyle(Workbook workbook){
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }
}
