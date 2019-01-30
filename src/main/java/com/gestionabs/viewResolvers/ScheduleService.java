package com.gestionabs.viewResolvers;

import com.gestionabs.beans.Session;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleService {

        public static HSSFWorkbook buildExcelDocument(Session[][] weekProgram){

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Emploi du temps");
            int rowNumber = 5;
            int columnsNumber = 6;

            HSSFHeader header = worksheet.getHeader();
            header.setCenter(HSSFHeader.font("Calibri", "Bold") + HSSFHeader.fontSize((short) 20) + "Emploi du temps");

            HSSFRow rows[] = new HSSFRow[rowNumber];

            int i,j;

            for(i = 0;i<rowNumber ; i++){
                rows[i] = worksheet.createRow( i);
            }

            for(i=0;i<rowNumber;i++){
                for(j=0;j<columnsNumber;j++ ){
                    rows[i].createCell(j);
                }
            }

            HSSFFont font1 = workbook.createFont();
            HSSFCellStyle cellStyle1 = workbook.createCellStyle();
            font1.setFontHeightInPoints((short)22);
            font1.setColor(HSSFColor.GREY_80_PERCENT.index);
            font1.setFontName(HSSFFont.FONT_ARIAL);
            cellStyle1.setFont(font1);
            cellStyle1.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle1.setFillForegroundColor(HSSFColor.GOLD.index);
            cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFFont font2 = workbook.createFont();
            HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            font2.setFontHeightInPoints((short)13);
            font2.setFontName(HSSFFont.FONT_ARIAL);
            cellStyle2.setFont(font2);
            cellStyle2.setWrapText(true);
            cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);

            HSSFFont font3 = workbook.createFont();
            HSSFCellStyle cellStyle3 = workbook.createCellStyle();
            font3.setFontHeightInPoints((short)15);
            font3.setFontName(HSSFFont.FONT_ARIAL);
            cellStyle3.setFont(font3);
            cellStyle3.setWrapText(true);
            cellStyle3.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle3.setFillForegroundColor(HSSFColor.GOLD.index);
            cellStyle3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);



            for(Cell cell : rows[0]){
                cell.setCellStyle(cellStyle1);
            }

            for(Row row:  rows){
                row.getCell(0).setCellStyle(cellStyle3);
            }

            for(i=1;i<5;i++){
                for(j=1;j<=5;j++){
                    rows[i].getCell(j).setCellStyle(cellStyle2);
                }
            }


            rows[0].getCell(1).setCellValue("Lundi");
            rows[0].getCell(2).setCellValue("Mardi");
            rows[0].getCell(3).setCellValue("Mercredi");
            rows[0].getCell(4).setCellValue("Jeudi");
            rows[0].getCell(5).setCellValue("Vendredi");

            rows[1].getCell(0).setCellValue("8:00-10:00");
            rows[2].getCell(0).setCellValue("10:00-12:00");
            rows[3].getCell(0).setCellValue("14:00-16:00");
            rows[4].getCell(0).setCellValue("16:00-18:00");

            for(i=0;i<5;i++){
                Session[] daySessions = weekProgram[i];
                for(j=0;j<4;j++){
                    Session session = daySessions[j];
                    if(session!=null)
                        rows[j+1].getCell(i+1).setCellValue(session.getSubject().getName() + "\n"+
                                "Prof : " + session.getTeacher().getFullName() + "\n"+
                                "Salle : " + session.getClassRoom().getName());
                }
            }


            for(i = 0;i <8;i++){
                worksheet.autoSizeColumn(i);
            }
            return workbook ;
        }
}
