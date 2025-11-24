package com.budgetwise.service;

import com.budgetwise.entity.Budget;
import com.budgetwise.entity.SavingsGoal;
import com.budgetwise.entity.Transaction;
import com.budgetwise.repository.CategoryRepository;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfReportGenerator {

    private final ChartGenerator chartGenerator;
    private final CategoryRepository categoryRepository;

    public byte[] generateDashboardPdf(Long userId, List<Transaction> transactions, List<Budget> budgets,
            List<SavingsGoal> goals) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(40, 40, 40, 40);

            addCoverPage(document, "Dashboard Report");
            document.add(new AreaBreak());

            addSectionHeader(document, "Executive Summary");
            addSummaryTable(document, transactions);

            addSectionHeader(document, "Financial Trends");
            addChart(document, chartGenerator.createMonthlyTrendsChart(transactions));

            addSectionHeader(document, "Expense Breakdown");
            addChart(document, chartGenerator.createCategoryPieChart(transactions));

            addSectionHeader(document, "Budget Performance");
            addChart(document, chartGenerator.createBudgetBarChart(budgets));
            addBudgetsTable(document, budgets);

            addFooter(document, pdf);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("ERROR generating Dashboard PDF: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to generate Dashboard PDF", e);
        }
    }

    public byte[] generateAnalyticsPdf(Long userId, List<Transaction> transactions, String timeRange) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(40, 40, 40, 40);

            addCoverPage(document, "Analytics Report (" + timeRange + ")");
            document.add(new AreaBreak());

            addSectionHeader(document, "Trend Analysis");
            addChart(document, chartGenerator.createMonthlyTrendsChart(transactions));

            addSectionHeader(document, "Category Breakdown");
            addChart(document, chartGenerator.createCategoryPieChart(transactions));

            addSectionHeader(document, "Detailed Transactions");
            addTransactionsTable(document, transactions);

            addFooter(document, pdf);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("ERROR generating Analytics PDF: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to generate Analytics PDF", e);
        }
    }

    private void addCoverPage(Document document, String title) {
        document.add(new Paragraph("\n\n\n\n"));
        Paragraph titlePara = new Paragraph(title)
                .setFontSize(ReportStyle.FONT_SIZE_TITLE)
                .setBold()
                .setFontColor(ReportStyle.PRIMARY_COLOR)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(titlePara);

        Paragraph subtitle = new Paragraph("BudgetWise Tracker Professional Report")
                .setFontSize(ReportStyle.FONT_SIZE_SUBHEADER)
                .setFontColor(ReportStyle.TEXT_COLOR)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(10);
        document.add(subtitle);

        Paragraph date = new Paragraph(
                "Generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                .setFontSize(ReportStyle.FONT_SIZE_BODY)
                .setFontColor(ReportStyle.TEXT_COLOR)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20);
        document.add(date);
    }

    private void addSectionHeader(Document document, String title) {
        Paragraph p = new Paragraph(title)
                .setFontSize(ReportStyle.FONT_SIZE_HEADER)
                .setBold()
                .setFontColor(ReportStyle.PRIMARY_COLOR)
                .setMarginTop(20)
                .setMarginBottom(10)
                .setBorderBottom(new SolidBorder(ReportStyle.PRIMARY_COLOR, 1f));
        document.add(p);
    }

    private void addSummaryTable(Document document, List<Transaction> transactions) {
        double income = transactions.stream().filter(t -> t.getType().toString().equals("INCOME"))
                .mapToDouble(t -> t.getAmount().doubleValue()).sum();
        double expense = transactions.stream().filter(t -> t.getType().toString().equals("EXPENSE"))
                .mapToDouble(t -> t.getAmount().doubleValue()).sum();

        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1 })).useAllAvailableWidth();
        table.addCell(createCell("Total Income", true));
        table.addCell(createCell(String.format("₹%.2f", income), false));
        table.addCell(createCell("Total Expenses", true));
        table.addCell(createCell(String.format("₹%.2f", expense), false));
        table.addCell(createCell("Net Savings", true));
        table.addCell(createCell(String.format("₹%.2f", income - expense), false));

        document.add(table);
    }

    private void addTransactionsTable(Document document, List<Transaction> transactions) {
        Table table = new Table(UnitValue.createPercentArray(new float[] { 2, 2, 2, 3 })).useAllAvailableWidth();
        addHeaderCell(table, "Date");
        addHeaderCell(table, "Type");
        addHeaderCell(table, "Amount");
        addHeaderCell(table, "Category");

        boolean isOdd = true;
        for (Transaction t : transactions) {
            addBodyCell(table, t.getTransactionDate().toString(), isOdd);
            addBodyCell(table, t.getType().toString(), isOdd);
            addBodyCell(table, String.format("₹%.2f", t.getAmount()), isOdd);
            addBodyCell(table, getCategoryName(t.getCategoryId()), isOdd);
            isOdd = !isOdd;
        }
        document.add(table);
    }

    private void addBudgetsTable(Document document, List<Budget> budgets) {
        Table table = new Table(UnitValue.createPercentArray(new float[] { 3, 2, 2, 2 })).useAllAvailableWidth();
        addHeaderCell(table, "Category");
        addHeaderCell(table, "Limit");
        addHeaderCell(table, "Spent");
        addHeaderCell(table, "Remaining");

        boolean isOdd = true;
        for (Budget b : budgets) {
            addBodyCell(table, getCategoryName(b.getCategoryId()), isOdd);
            addBodyCell(table, String.format("₹%.2f", b.getAmount()), isOdd);
            addBodyCell(table, String.format("₹%.2f", b.getSpent()), isOdd);
            addBodyCell(table, String.format("₹%.2f", b.getAmount().subtract(b.getSpent())), isOdd);
            isOdd = !isOdd;
        }
        document.add(table);
    }

    private void addChart(Document document, JFreeChart chart) {
        try {
            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", baos);
            Image pdfImage = new Image(ImageDataFactory.create(baos.toByteArray()));
            pdfImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            pdfImage.setMarginBottom(20);
            document.add(pdfImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHeaderCell(Table table, String text) {
        Cell cell = new Cell().add(new Paragraph(text).setBold().setFontColor(ReportStyle.WHITE));
        cell.setBackgroundColor(ReportStyle.PRIMARY_COLOR);
        cell.setPadding(5);
        cell.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(cell);
    }

    private void addBodyCell(Table table, String text, boolean isOdd) {
        Cell cell = new Cell().add(new Paragraph(text).setFontSize(ReportStyle.FONT_SIZE_BODY));
        cell.setBackgroundColor(isOdd ? ReportStyle.LIGHT_BG_COLOR : ReportStyle.WHITE);
        cell.setPadding(5);
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
    }

    private Cell createCell(String text, boolean isHeader) {
        Cell cell = new Cell().add(new Paragraph(text).setBold());
        if (isHeader) {
            cell.setBackgroundColor(ReportStyle.PRIMARY_COLOR);
            cell.setFontColor(ReportStyle.WHITE);
        } else {
            cell.setBackgroundColor(ReportStyle.LIGHT_BG_COLOR);
        }
        cell.setPadding(8);
        return cell;
    }

    private void addFooter(Document document, PdfDocument pdf) {
        int n = pdf.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            document.showTextAligned(new Paragraph(String.format("Page %d of %d", i, n)),
                    559, 20, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }
    }

    private String getCategoryName(Long categoryId) {
        if (categoryId == null)
            return "Uncategorized";
        return categoryRepository.findById(categoryId).map(c -> c.getName()).orElse("Uncategorized");
    }
}
