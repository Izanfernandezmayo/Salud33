package com.mycompany.salud3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.util.IOUtils;
import java.io.*;

public class ExcelEntrenos {
    public static void main(String[] args) throws Exception {
        String file = "entrenos.xlsx";
        XSSFWorkbook wb;
        File f = new File(file);

        if (f.exists()) {
            try (FileInputStream in = new FileInputStream(f)) {
                wb = new XSSFWorkbook(in);
            }
        } else {
            wb = new XSSFWorkbook();
        }

        XSSFSheet sheet = wb.getSheet("Entrenamientos");
        if (sheet == null) sheet = wb.createSheet("Entrenamientos");

        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Fecha");
        header.createCell(1).setCellValue("Duración (min)");
        header.createCell(2).setCellValue("Calorías");

        XSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("2025-11-05");
        row.createCell(1).setCellValue(45);
        row.createCell(2).setCellValue(360);

        CellStyle cs = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        cs.setDataFormat(df.getFormat("0.00"));
        row.getCell(2).setCellStyle(cs);
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(1, 15 * 256);

        try (FileInputStream is = new FileInputStream("imagen_local.jpg")) {
            byte[] bytes = IOUtils.toByteArray(is);
            int idx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            CreationHelper helper = wb.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(4);
            anchor.setRow1(1);
            Picture pict = drawing.createPicture(anchor, idx);
            pict.resize();
        } catch (IOException e) {
            System.out.println("No se encontró la imagen, se omitirá.");
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            wb.write(out);
        }
        wb.close();

        System.out.println("Archivo Excel creado/modificado correctamente.");
    }
}