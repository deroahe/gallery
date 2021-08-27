package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.util.export.ExcelData;
import com.deroahe.gallerybe.util.export.ExcelDataService;
import com.deroahe.gallerybe.util.export.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/util")
public class UtilController {

    private ExcelDataService excelDataService;

    @Autowired
    public UtilController(ExcelDataService excelDataService) {
        this.excelDataService = excelDataService;
    }

    @GetMapping("/export-excel")
    public void exportXcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=galleryStats_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExcelData excelData = excelDataService.getExportDataElements(true);

        ExcelExporter excelExporter = new ExcelExporter(excelData);

        excelExporter.export(response);
    }
}
