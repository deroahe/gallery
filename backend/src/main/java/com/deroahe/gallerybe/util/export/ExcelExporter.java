package com.deroahe.gallerybe.util.export;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Slf4j
public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private ExcelData excelData;
    private final static int MAX_NUMBER_OF_COLUMNS = 6;

    static byte[] blackColor = {(byte) 0, (byte) 0, (byte) 0};
    public static XSSFColor BLACK_BACKGROUND = new XSSFColor(blackColor, null);
    static byte[] greenColor = {(byte) 9, (byte) 250, (byte) 10};
    public static XSSFColor GREEN_WRITING = new XSSFColor(greenColor, null);

    static byte[] lightgreenColor={(byte)199 ,(byte) 255, (byte) 199};
    public static XSSFColor GREEN_BACKGROUND = new XSSFColor(lightgreenColor, null);



    public ExcelExporter(ExcelData excelData) {
        this.excelData = excelData;
        workbook = new XSSFWorkbook();
    }

    private int writeHeaderLineHashtags(int rowNo) {


        Row row = sheet.createRow(1);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(row, 0, "", style);
        createCell(row, 1, "Hashtag Name", style);
        createCell(row, 2, "No. of Occurrences", style);

        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }

        rowNo++;
        return rowNo;
    }

    private int writeHeaderLineImages(int rowNo) {


        Row row = sheet.createRow(rowNo);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(row, 0, "", style);
        createCell(row, 1, "Image Url", style);
        createCell(row, 2, "Image Votes", style);

        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }

        rowNo++;
        return rowNo;
    }

    private int writeHeaderLineUsers(int rowNo) {


        Row row = sheet.createRow(rowNo);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        createCell(row, 0, "", style);
        createCell(row, 1, "Username", style);
        createCell(row, 2, "User Email", style);

        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }

        rowNo++;
        return rowNo;
    }

    /*private void writeRoundLine(int rowNr, int roundNr, String name){
        Row row = sheet.createRow(rowNr);
        int col = 0;
        XSSFCellStyle style = roundLineStyle(workbook);
        createCell(row,col,"Round: " + String.valueOf(roundNr),style);
        col = col +1;
        while(col<MAX_NUMBER_OF_COLUMNS){
            createCell(row,col,"",style);
            col++;
        }
    }*/

    public static XSSFCellStyle headerLineStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        font.setColor(GREEN_WRITING);
        style.setFont(font);
        style.setFillForegroundColor(BLACK_BACKGROUND);
        style.setFillBackgroundColor(BLACK_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);

        return  style;
    }

    private void writeImageAppHeader(String name){
        sheet = workbook.createSheet("ImageAppSummary");
        Row row = sheet.createRow(0);
        int col = 0;
        XSSFCellStyle style = headerLineStyle(workbook);
        createCell(row,col, name,style);
        col = col +1;
        /*createCell(row,col,name,style);
        col ++;*/
        while(col<MAX_NUMBER_OF_COLUMNS){
            createCell(row,col,"",style);
            col++;
        }

    }


    private int writeHashtagDataLines(int rowNr){
        log.info("The writing of hashtag data has started");
        int rowCount = rowNr;

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);


        List<Map.Entry<String, Integer>> hashtagMapList = new LinkedList<Map.Entry<String, Integer>>(excelData.getMap().entrySet());
        for(Map.Entry<String,Integer> entry: hashtagMapList){
            Row row = sheet.createRow(rowCount);
            int column = 0;
            createCell(row, column++, "", style);
            createCell(row, column++, entry.getKey(), style);
            createCell(row, column++, String.valueOf(entry.getValue()), style);
            rowCount++;
        }
        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }
        log.info("The writing of hashtag data has ended");
        return rowCount;
    }

    private int writeImageDataLines(int rowNr){
        log.info("The writing of image data has started");
        int rowCount = rowNr;

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);


        for(Image image : excelData.getMostUpvotedImages()){
            Row row = sheet.createRow(rowCount);
            int column = 0;
            createCell(row, column++, "", style);
            createCell(row, column++, image.getImageUrl(), style);
            createCell(row, column++, String.valueOf(image.getImageScore()), style);
            rowCount++;
        }


        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }
        log.info("The writing of image data has ended");
        return rowCount;
    }

    private int writeUserDataLines(int rowNr){
        log.info("The writing of user data has started");
        int rowCount = rowNr;

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setFillForegroundColor(GREEN_BACKGROUND);
        style.setFillBackgroundColor(GREEN_BACKGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);


        for(User user : excelData.getNewUsers()){
            Row row = sheet.createRow(rowCount);
            int column = 0;
            createCell(row, column++, "", style);
            createCell(row, column++, user.getUserUsername(), style);
            createCell(row, column++, String.valueOf(user.getUserEmail()), style);
            rowCount++;
        }


        for (int j = 0; j < MAX_NUMBER_OF_COLUMNS; j++) {
            sheet.autoSizeColumn(j);
        }
        log.info("The writing of user data has ended");
        return rowCount;
    }


    //public void export(HttpServletResponse response) throws IOException {
    public void export(HttpServletResponse response) throws IOException {
        writeImageAppHeader("Image Application Summary Report");
        int rowNr = writeHeaderLineHashtags(1);
        rowNr = writeHashtagDataLines(rowNr+1);
        rowNr = writeHeaderLineImages(rowNr+3);
        rowNr = writeImageDataLines(rowNr+1);
        rowNr = writeHeaderLineUsers(rowNr+3);
        rowNr = writeUserDataLines(rowNr+1);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();



    }

}
