package com.restaurant.dinehouse.print;


import com.restaurant.dinehouse.util.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

@Service
@Slf4j
public class PrinterService implements Printable {

    public List<String> getPrinters() {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(Sides.DUPLEX);
        //printRequestAttributeSet.add(Sides.ONE_SIDED);
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, printRequestAttributeSet);

        List<String> printerList = new ArrayList<>();
        for (PrintService printerService : printServices) {
            printerList.add(printerService.getName());
        }

        return printerList;
    }

    public void print() {
        String printerName = "EPSON TM-m30-S/A";
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        //printRequestAttributeSet.add(Sides.DUPLEX);
        printRequestAttributeSet.add(Sides.ONE_SIDED);
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, printRequestAttributeSet);

        PrintService runningPrinter = null;
        List<String> printerList = new ArrayList<>();
        for (PrintService printerService : printServices) {
            if (StringUtils.equalsIgnoreCase(printerService.getName(), printerName)) {
                runningPrinter = printerService;
            }
            printerList.add(printerService.getName());
        }
        log.info("Available printers {}", printerList);
        if (Objects.isNull(runningPrinter)) {
            throw new RuntimeException("Failed to find printer {}" + printerName);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(POS.POSPrinter.Justification(POS.Justifications.Center));

            outputStream.write(POS.POSPrinter.CharSize.DoubleHeight3());
            outputStream.write(SystemConstants.Store.name.getBytes());
            outputStream.write(SystemConstants.Store.address1.getBytes());
            outputStream.write(SystemConstants.Store.address2.getBytes());
            outputStream.write(SystemConstants.Store.GSTNo.getBytes());
            outputStream.write(SystemConstants.Store.contactNo.getBytes());

            outputStream.write(POS.POSPrinter.CharSize.Normal());
            outputStream.write(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
            outputStream.write(POS.POSPrinter.BarCode.SetBarcodeHeightInDots(600));
            outputStream.write(POS.POSPrinter.BarCode.SetBarWidth(POS.BarWidth.Thinnest));
            outputStream.write(POS.POSPrinter.FontSelect.FontA());
            outputStream.write(POS.POSPrinter.Justification(POS.Justifications.Center));
            outputStream.write(POS.POSPrinter.CutPage());

        } catch (IOException ex) {
            log.error("failed at writing order-info to printer doc-job {}", ex);
        }

        DocPrintJob job = runningPrinter.createPrintJob();
        Doc doc = new SimpleDoc(outputStream.toByteArray(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        try {
            job.print(doc, new HashPrintRequestAttributeSet());
        } catch (PrintException ex) {
            log.error("failed at creating printer job {}" + ex.getMessage());
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        g.setFont(new Font("Roman", 0, 8));
        g.drawString("Hello world !", 0, 10);
        return PAGE_EXISTS;
    }

    public void printString(String printerName, String text) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            bytes = text.getBytes("CP437");
            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, printRequestAttributeSet);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {
            Doc doc = new SimpleDoc(bytes, flavor, null);
            job.print(doc, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PrintService findPrintService(String printerName, PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }

    /** eample
     *  PrinterService printerService = new PrinterService();
     *         String printName = "EPSON TM-m30-S/A";
     *         for (int i = 0; i < 1; i++) {
     *             printerService.printString(printName, "\n Test Line " + i);
     *         }
     *         byte[] cutP = new byte[]{0x1d, 'V', 1};
     *         printerService.printBytes("EPSON TM-U220 Receipt", cutP);
     *         */

}
