package com.pass.excel.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2019/3/14
 * @Description:
 */
public class PoiToExcel {
    public static void main(String[] args) throws Exception{
        File xlsFile = new File("C:\\Users\\DELL\\Desktop\\one.xlsx");
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);
        Sheet sheet1 = workbook.getSheet("Sheet1");
        int lastRowNum = sheet1.getLastRowNum();
        List<String> list = new ArrayList<String>();
        Row row;
        String first = sheet1.getRow(0).getCell(0).getStringCellValue();
        for (int i = 0 ; i <= lastRowNum ; i ++) {
            row = sheet1.getRow(i);
            if (null != row) {
                String last = row.getCell(0).getStringCellValue();
                if (!first.equals(last)) {
                    first = last;
                    list.add("");
                    list.add("");
                    list.add("");
                    list.add(last);
                } else if (first.equals(last)) {
                    list.add(last);
                }
            }
        }

//        list.stream().forEach(a -> System.out.println(a));

        // 创建工作薄
        HSSFWorkbook wirite_workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = wirite_workbook.createSheet("Sheet1");

        for (int i = 0; i < list.size(); i ++)
        {
            if (null == list.get(i) || "".equals(list.get(i))) {
                sheet.createRow(i);
            } else {
                HSSFRow rows = sheet.createRow(i);

                // 向工作表中添加数据
                rows.createCell(0).setCellValue(list.get(i));
            }
        }

        File file = new File("C:\\Users\\DELL\\Desktop\\two.xlsx");
        FileOutputStream xlsStream = new FileOutputStream(file);
        wirite_workbook.write(xlsStream);
    }
}
