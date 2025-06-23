package com.example.Water_Store.utils;

import com.example.Water_Store.entity.SystemLog;
import com.example.Water_Store.entity.Order;
import com.example.Water_Store.entity.OrderItem;
import org.apache.commons.csv.*;

import java.io.*;
import java.util.List;

public class CsvExportUtil {

    public static ByteArrayInputStream exportOrders(List<Order> orders) throws IOException {
        String[] headers = { "Tracking ID", "User Email", "Status", "Product", "Quantity", "Price", "Total" };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(headers));

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                double total = item.getPrice() * item.getQuantity();
                printer.printRecord(
                        order.getTrackingId(),
                        order.getUser().getEmail(),
                        order.getStatus(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        total
                );
            }
        }

        printer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }

    public static ByteArrayInputStream exportSalesReport(List<Order> orders) throws IOException {
        String[] headers = { "Date", "Tracking ID", "Amount" };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(headers));

        for (Order order : orders) {
            double total = order.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            printer.printRecord(
                    order.getCreatedAt().toLocalDate(),
                    order.getTrackingId(),
                    String.format("%.2f", total)
            );
        }

        printer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }


    public static ByteArrayInputStream exportSystemLogs(List<SystemLog> logs) throws IOException {
        String[] headers = { "Timestamp", "Type", "Endpoint", "Message", "Stack Trace" };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(headers));

        for (SystemLog log : logs) {
            printer.printRecord(
                    log.getTimestamp(),
                    log.getType(),
                    log.getEndpoint(),
                    log.getMessage(),
                    log.getStackTrace()
            );
        }

        printer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }


}
