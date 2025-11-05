package com.mycompany.salud3;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;

public class GraficosEntrenos {
    public static void main(String[] args) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Datos");

        String[] meses = {"Ene", "Feb", "Mar"};
        double[] minutos = {30, 45, 60};

        for (int i = 0; i < meses.length; i++) {
            XSSFRow r = sheet.createRow(i + 1);
            r.createCell(0).setCellValue(meses[i]);
            r.createCell(1).setCellValue(minutos[i]);
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 3, 1, 10, 20);
        XSSFChart chart = drawing.createChart(anchor);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFCategoryAxis xAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        XDDFValueAxis yAxis = chart.createValueAxis(AxisPosition.LEFT);
        yAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        XDDFDataSource<String> cat = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                new CellRangeAddress(1, meses.length, 0, 0));
        XDDFNumericalDataSource<Double> val = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                new CellRangeAddress(1, meses.length, 1, 1));

        XDDFChartData data = chart.createData(ChartTypes.BAR, xAxis, yAxis);
        data.addSeries(cat, val);
        chart.plot(data);

        try (var out = new java.io.FileOutputStream("grafico_excel.xlsx")) {
            wb.write(out);
        }
        wb.close();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(30, "Duración", "Ene");
    }
}