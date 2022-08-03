package com.durgesh.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.durgesh.util.QRCodeGenerator;
import com.durgesh.view.PDFGenerator;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

@RestController
public class QRCodeController {

   @Autowired
   QRCodeGenerator qrCodeGenerator;

   @Autowired
   PDFGenerator pdfGenerator;

   
   //http://localhost:9090/export?text=ShabnamKumari_Durgeshkumar
   @GetMapping(value = "/export",
         produces = MediaType.APPLICATION_PDF_VALUE)
   public ResponseEntity<InputStreamResource>  employeeReport
   (@RequestParam(defaultValue = "Hello") String text) 
         throws IOException, DocumentException, WriterException {

      byte[] pngData = qrCodeGenerator
            .getQRCode(text, 0, 0);
      InputStreamResource inputStreamResource = pdfGenerator
            .InputStreamResource(pngData);
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "inline; "
            + "filename=my-file.pdf");

      return ResponseEntity.ok().headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(inputStreamResource);
   }
}
