package com.pass.util.db_table2excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yuanzhonglin
 * @date 2019/3/14
 * @Description:    生成数据表结构信息.xlsx
 * 1.sql：
        SELECT
        TABLE_NAME 表名,
        COLUMN_NAME 属性名,
        COLUMN_TYPE 数据类型,
        DATA_TYPE 字段类型,
        CHARACTER_MAXIMUM_LENGTH 长度,
        IS_NULLABLE 是否为空,
        COLUMN_DEFAULT 默认值,
        COLUMN_COMMENT 备注
        FROM
        INFORMATION_SCHEMA.COLUMNS
        where
        table_schema ='数据库名'
        order by TABLE_NAME;
 * 2.将查询出来的信息copy到database.xlsx中
 * 3.执行下面程序
 */
public class PoiToExcelCopy {
    private static XSSFWorkbook wbCreat = new XSSFWorkbook();

    public static void main(String[] args) throws Exception {
        String fromPath = "C:\\Users\\HP\\Desktop\\交接（袁中林）\\database.xlsx";// excel存放路径
        String toPath = "C:\\Users\\HP\\Desktop\\交接（袁中林）\\数据表结构.xlsx";// 保存新EXCEL路径

        // 源
        InputStream in = new FileInputStream(fromPath);
        XSSFWorkbook wb = new XSSFWorkbook(in);
        XSSFSheet sheet = wb.getSheet("Sheet1");

        // 新
//        XSSFWorkbook wbCreat = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wbCreat.createCellStyle();
        XSSFSheet sheetCreat = wbCreat.createSheet(sheet.getSheetName());

        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        int num = 0;
        String first = "";

        for (int i = firstRow; i <= lastRow; i++) {
            if (0 == i) {   // 第一行
                first = sheet.getRow(i).getCell(0).getStringCellValue();
                num = createStyle(sheetCreat, num, first, cellStyle);
                num = createRow(sheetCreat, num, sheet, i);
            } else {        // 其余行
                String last = sheet.getRow(i).getCell(0).getStringCellValue();
                if (!first.equals(last)) {  // 不同表
                    first = last;
                    num = createThreeNullRow(sheetCreat, num, sheet, i, last, cellStyle);
                } else {                    // 相同表
                    num = createRow(sheetCreat, num, sheet, i);
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream(toPath);
        wbCreat.write(fileOut);
        fileOut.close();
    }

    // 创建新行
    public static int createRow(XSSFSheet sheetCreat, int newRow, XSSFSheet sheet, int oldRow){
        // 创建新建excel Sheet的行
        Row rowCreat = sheetCreat.createRow(newRow);
        // 取得源有excel Sheet的行
        Row row = sheet.getRow(oldRow);
        // 单元格式样
        int firstCell = row.getFirstCellNum();
        int lastCell = row.getLastCellNum();
        for (int j = firstCell; j < lastCell; j++) {
            rowCreat.createCell(j);
            Cell cell = row.getCell(j);
            if (null == cell) {
                continue;
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String cellValue = cell.getStringCellValue();
            if (null == cellValue || "".equals(cellValue)) {
                continue;
            }
            rowCreat.getCell(j).setCellValue(cellValue);
        }
        return ++newRow;
    }

    // 创建表与表之间的分割
    public static int createThreeNullRow(XSSFSheet sheetCreat, int newRow, XSSFSheet sheet,
                                         int oldRow, String name, XSSFCellStyle cellStyle){
        sheetCreat.createRow(newRow);
        newRow = createStyle(sheetCreat, ++newRow, name, cellStyle);
        createRow(sheetCreat, newRow, sheet, oldRow);
        return ++newRow;
    }

    // 创建格式
    public static int createStyle(XSSFSheet sheetCreat, int newRow, String name, XSSFCellStyle cellStyle){
        // 表名
        XSSFRow table_row = sheetCreat.createRow(newRow);
        table_row.createCell(1);
        table_row.getCell(1).setCellValue(name);

        // 标题
        XSSFRow titile_row = sheetCreat.createRow(++newRow);
        titile_row.createCell(0);
        titile_row.createCell(1);
        titile_row.createCell(2);
        titile_row.createCell(3);
        titile_row.createCell(4);
        titile_row.createCell(5);
        titile_row.createCell(6);
        titile_row.createCell(7);

        List<CellStyle> cellStyles = tableStyle();
        titile_row.getCell(0).setCellStyle(cellStyles.get(0));
        titile_row.getCell(1).setCellStyle(cellStyles.get(0));
        titile_row.getCell(2).setCellStyle(cellStyles.get(0));
        titile_row.getCell(3).setCellStyle(cellStyles.get(0));
        titile_row.getCell(4).setCellStyle(cellStyles.get(0));
        titile_row.getCell(5).setCellStyle(cellStyles.get(0));
        titile_row.getCell(6).setCellStyle(cellStyles.get(0));
        titile_row.getCell(7).setCellStyle(cellStyles.get(0));

        titile_row.getCell(0).setCellValue("表名");
        titile_row.getCell(1).setCellValue("属性名");
        titile_row.getCell(2).setCellValue("数据类型");
        titile_row.getCell(3).setCellValue("字段类型");
        titile_row.getCell(4).setCellValue("长度");
        titile_row.getCell(5).setCellValue("是否为空");
        titile_row.getCell(6).setCellValue("默认值");
        titile_row.getCell(7).setCellValue("备注");

        return ++newRow;
    }

    private static java.util.List<CellStyle> tableStyle() {
        List<CellStyle> cellStyleList = new ArrayList<CellStyle>();
        // 样式准备
        // 标题样式
        CellStyle style = wbCreat.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN); // 下边框
        style.setBorderLeft(BorderStyle.THIN);// 左边框
        style.setBorderTop(BorderStyle.THIN);// 上边框
        style.setBorderRight(BorderStyle.THIN);// 右边框
        style.setAlignment(HorizontalAlignment.CENTER);
        cellStyleList.add(style);
        CellStyle style1 = wbCreat.createCellStyle();
        style1.setBorderBottom(BorderStyle.THIN); // 下边框
        style1.setBorderLeft(BorderStyle.THIN);// 左边框
        style1.setBorderTop(BorderStyle.THIN);// 上边框
        style1.setBorderRight(BorderStyle.THIN);// 右边框
        style1.setAlignment(HorizontalAlignment.CENTER);
        cellStyleList.add(style1);
        CellStyle cellStyle = wbCreat.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        // cellStyle.setVerticalAlignment(VerticalAlignment.TOP);//垂直对齐方式
        cellStyleList.add(cellStyle);
        return cellStyleList;
    }
}
