package hu.idne.backend.mappers.system;

import hu.idne.backend.models.system.dtos.ExcelDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;

public abstract class ExcelDtoMapperBase {

    protected Workbook wb;

    protected CellStyle dateStyle;

    protected CellStyle hourStyle;

    protected CellStyle dateHourStyle;

    public ExcelDtoMapperBase() {
    }

    public void initCellStyles(Workbook workbook) {
        this.wb = workbook;
        createDateStyle();
        createHourStyle();
        createDateHourStyle();
    }

    protected Cell createNullableDateCell(int cellNumber, Row row, CellStyle cellStyle, Date date) {
        Cell cell;
        if (date != null) {
            cell = createDateCell(cellNumber, row, cellStyle, date);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createDateCell(int cellNumber, Row row, CellStyle cellStyle, Date date) {
        Cell cell;
        cell = row.createCell(cellNumber, CellType.NUMERIC);
        cell.setCellValue(date);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    protected Cell createNullableBooleanCell(int cellNumber, Row row, Boolean bool) {
        Cell cell;
        if (bool != null) {
            cell = row.createCell(cellNumber, CellType.BOOLEAN);
            cell.setCellValue(bool);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createNullableNumberCell(int cellNumber, Row row, Integer number) {
        Cell cell;
        if (number != null) {
            cell = row.createCell(cellNumber, CellType.NUMERIC);
            cell.setCellValue(number);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createNullableNumberCell(int cellNumber, Row row, Float number) {
        Cell cell;
        if (number != null) {
            cell = row.createCell(cellNumber, CellType.NUMERIC);
            cell.setCellValue(number);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createNullableNumberCell(int cellNumber, Row row, Long number) {
        Cell cell;
        if (number != null) {
            cell = row.createCell(cellNumber, CellType.NUMERIC);
            cell.setCellValue(number);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createNullableNumberCell(int cellNumber, Row row, Double number) {
        Cell cell;
        if (number != null) {
            cell = row.createCell(cellNumber, CellType.NUMERIC);
            cell.setCellValue(number);
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    protected Cell createNullableNumberTimeCell(int cellNumber, Row row, Integer number) {
        Cell cell;
        if (number != null) {
            cell = row.createCell(cellNumber, CellType.STRING);
            cell.setCellValue(getXXX(number));
        } else {
            cell = row.createCell(cellNumber, CellType.BLANK);
        }
        return cell;
    }

    private String getXXX(int ellapsedSecs) {
        int hours = ellapsedSecs / 3600;
        int mins = (ellapsedSecs % 3600) / 60;
        int secs = ((ellapsedSecs % 3600) % 60);
        return String.format("%02d%n:%02d%n:%02d%n", hours, mins, secs);
    }


    protected int createAbstractDtoCells(int cellNumber, Row row, ExcelDTO excelDto) {
        row.createCell(cellNumber++, CellType.STRING).setCellValue(excelDto.getCreatedBy());
        createNullableDateCell(cellNumber++, row, dateHourStyle, excelDto.getCreatedDate());
        row.createCell(cellNumber++, CellType.STRING).setCellValue(excelDto.getUpdatedBy());
        createNullableDateCell(cellNumber++, row, dateHourStyle, excelDto.getUpdatedDate());
        return cellNumber;
    }

    private void createDateStyle() {
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        dateStyle = cellStyle;
    }

    private void createHourStyle() {
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("HH:mm"));
        hourStyle = cellStyle;
    }

    private void createDateHourStyle() {
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm"));
        dateHourStyle = cellStyle;
    }

}
