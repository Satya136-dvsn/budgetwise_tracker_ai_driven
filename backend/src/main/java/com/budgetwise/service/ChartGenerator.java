package com.budgetwise.service;

import com.budgetwise.entity.Budget;
import com.budgetwise.entity.Transaction;
import com.budgetwise.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartGenerator {

    private final CategoryRepository categoryRepository;

    public JFreeChart createMonthlyTrendsChart(List<Transaction> transactions) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Map<String, Double>> monthlyData = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getTransactionDate().format(DateTimeFormatter.ofPattern("MMM yyyy")),
                        Collectors.groupingBy(
                                t -> t.getType().toString(),
                                Collectors.summingDouble(t -> t.getAmount().doubleValue()))));

        // Sort by date (needs better sorting logic if spanning years, but map keys are
        // strings)
        // For simplicity in this snippet, we rely on the input order or simple string
        // sort
        // In production, use a TreeMap with LocalDate keys then format for display

        monthlyData.forEach((month, types) -> {
            double income = types.getOrDefault("INCOME", 0.0);
            double expense = types.getOrDefault("EXPENSE", 0.0);
            dataset.addValue(income, "Income", month);
            dataset.addValue(expense, "Expenses", month);
        });

        JFreeChart chart = ChartFactory.createLineChart(
                null, null, "Amount", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        styleChart(chart);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, ReportStyle.AWT_SUCCESS); // Income
        renderer.setSeriesPaint(1, ReportStyle.AWT_DANGER); // Expense
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));
        renderer.setSeriesStroke(1, new BasicStroke(2.5f));
        plot.setRenderer(renderer);

        return chart;
    }

    public JFreeChart createCategoryPieChart(List<Transaction> transactions) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Map<String, Double> expensesByCategory = transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> getCategoryName(t.getCategoryId()),
                        Collectors.summingDouble(t -> t.getAmount().doubleValue())));

        expensesByCategory.forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                null, dataset, true, true, false);

        styleChart(chart);

        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setSectionPaint("Uncategorized", Color.GRAY);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelBackgroundPaint(Color.WHITE);

        return chart;
    }

    public JFreeChart createBudgetBarChart(List<Budget> budgets) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Budget b : budgets) {
            String category = getCategoryName(b.getCategoryId());
            dataset.addValue(b.getAmount(), "Budget", category);
            dataset.addValue(b.getSpent(), "Spent", category);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                null, "Category", "Amount", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        styleChart(chart);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, ReportStyle.AWT_ACCENT); // Budget
        renderer.setSeriesPaint(1, ReportStyle.AWT_DANGER); // Spent
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter()); // Flat look
        renderer.setShadowVisible(false);

        return chart;
    }

    private void styleChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 14));

        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(ReportStyle.AWT_GRID);
            plot.setDomainGridlinesVisible(false);
            plot.setOutlineVisible(false);
        }

        chart.getLegend().setFrame(BlockBorder.NONE);
    }

    private String getCategoryName(Long categoryId) {
        if (categoryId == null)
            return "Uncategorized";
        return categoryRepository.findById(categoryId)
                .map(c -> c.getName())
                .orElse("Uncategorized");
    }
}
