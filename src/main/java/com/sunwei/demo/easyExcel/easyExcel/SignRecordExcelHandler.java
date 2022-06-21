package com.sunwei.demo.easyExcel.easyExcel;

import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import org.apache.poi.ss.usermodel.*;


public class SignRecordExcelHandler implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (BooleanUtils.isNotTrue(context.getHead())) {
            Row row = context.getRow();
            int firstCellNum = row.getFirstCellNum();
            int lastCellNum = row.getLastCellNum();
            boolean res = false;
            for (int i = firstCellNum; i < lastCellNum; i++) {
                Cell cellNum = row.getCell(i);
                if (cellNum.getStringCellValue().contains("java")) {
                    res = true;
                }
            }
            if (res){
                Cell cell = context.getCell();
                Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
                context.getFirstCellData().setWriteCellStyle(null);
            }
        }

//        if (BooleanUtils.isNotTrue(context.getHead())) {
//            if (cell.getStringCellValue().contains("java")) {
//                Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
//                XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//                row.setRowStyle(cellStyle);
//            } else if (cell.getStringCellValue().contains("mysql")) {
//                Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
//                CellStyle cellStyle = workbook.createCellStyle();
//                cellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
//                // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
//                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//                cell.setCellStyle(cellStyle);
//                // 这里要把 WriteCellData的样式清空， 不然后面还有一个拦截器 FillStyleCellWriteHandler 默认会将 WriteCellStyle 设置到 cell里面去 会导致自己设置的不一样（很关键）
//                context.getFirstCellData().setWriteCellStyle(null);
//            }
//        }

    }

}
