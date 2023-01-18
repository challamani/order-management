package com.restaurant.dinehouse.print;


import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.model.OrderItem;
import com.restaurant.dinehouse.repository.OrderItemRepository;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrinterService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

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

    public boolean print(Long orderId, boolean isDuplicate) {
        String printerName = "EPSON TM-m30-S/A";
        //DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        //printRequestAttributeSet.add(Sides.DUPLEX);
        printRequestAttributeSet.add(Sides.ONE_SIDED);

        for(PrintService printerService: PrintServiceLookup.lookupPrintServices(null, null)){
            log.info("printer name {}",printerService.getName());
        }

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(null, null);
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
            writeHeader(outputStream);
            writeLineItem(outputStream,orderId);
            outputStream.write(POS.POSPrinter.SetStyles(POS.PrintStyle.None));
            outputStream.write(StringUtils.LF.getBytes());
            if(isDuplicate){
                outputStream.write("******DUPLICATE BILL********".getBytes());
                outputStream.write(StringUtils.LF.getBytes());
            }
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
            return false;
        }
        return true;
    }

    private void writeHeader(ByteArrayOutputStream outputStream) throws IOException {

        outputStream.write(POS.POSPrinter.Justification(POS.Justifications.Center));
        outputStream.write(POS.POSPrinter.CharSize.DoubleHeight2());
        outputStream.write(SystemConstants.Store.name.getBytes());
        outputStream.write(StringUtils.LF.getBytes());
        outputStream.write(SystemConstants.Store.address1.getBytes());
        outputStream.write(StringUtils.LF.getBytes());
        outputStream.write(SystemConstants.Store.address2.getBytes());
        outputStream.write(StringUtils.LF.getBytes());
        outputStream.write(SystemConstants.Store.GSTNo.getBytes());
        outputStream.write(StringUtils.LF.getBytes());
        outputStream.write(SystemConstants.Store.contactNo.getBytes());
        outputStream.write(StringUtils.LF.getBytes());
    }

    private void writeLineItem(ByteArrayOutputStream outputStream, Long orderId) throws IOException {

        Optional<Order> dbOrder = orderRepository.findById(orderId);
        if(dbOrder.isPresent()) {
            Order order = dbOrder.get();
            outputStream.write(POS.POSPrinter.CharSize.Normal());
            outputStream.write(POS.POSPrinter.Justification(POS.Justifications.Left));
            outputStream.write(StringUtils.LF.getBytes());

            outputStream.write("Order# ".concat(Long.toString(order.getId()))
                    .concat(", Address/Table ").concat(order.getAddress())
            .getBytes());

            outputStream.write(StringUtils.LF.getBytes());
            String itemsHeader = String.format("%-29s", "Item")
                    .concat("  ")
                    .concat(String.format("%-3s", "Qty"))
                    .concat("  ")
                    .concat(String.format("%-6s", "Price"));
            outputStream.write(itemsHeader.getBytes());

            outputStream.write(StringUtils.LF.getBytes());
            outputStream.write("_______________________________________________".getBytes());

            outputStream.write(StringUtils.LF.getBytes());
            outputStream.write(POS.POSPrinter.Justification(POS.Justifications.Left));
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
            orderItems.stream().forEach(orderItem -> {

                String derivedItemName = String
                        .format("%" + (-30) + "s", orderItem.getItemName().length() > 29 ? orderItem.getItemName().substring(0,29): orderItem.getItemName())
                        .replace(' ', ' ');

                String itemLine = derivedItemName
                        .concat("  ")
                        .concat(orderItem.getQuantity().toString())
                        .concat("  ")
                        .concat(String.format("%-6s", orderItem.getPrice().toString()));
                try {
                    outputStream.write(itemLine.getBytes());
                    outputStream.write(StringUtils.LF.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            outputStream.write(StringUtils.LF.getBytes());
            outputStream.write("_______________________________________________".getBytes());
            outputStream.write(StringUtils.LF.getBytes());
            outputStream.write(String.format("%-30s", "Total")
                    .concat(" ")
                    .concat(order.getPayableAmount().toString())
                    .getBytes());
            outputStream.write(StringUtils.LF.getBytes());
        }
    }

    public void printString(String printerName, String text) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, printRequestAttributeSet);
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

}
