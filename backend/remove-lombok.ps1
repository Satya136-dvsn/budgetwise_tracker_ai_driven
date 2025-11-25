$files = @(
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\AdminController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\AIController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\BudgetController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\DashboardController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\ExportController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\GdprController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\InvestmentController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\SavingsGoalController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\controller\TransactionController.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\AdminStatsDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\AnomalyDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\BudgetAdviceDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\BudgetDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\CategorizationRequestDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\CategorizationSuggestionDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\CategoryBreakdownDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\ContributionRequest.java",
   "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\DashboardSummaryDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\ExportRequest.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\InvestmentDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\MonthlyTrendDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\PortfolioSummaryDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\PostDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\PredictionDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\SavingsGoalDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\dto\TransactionDto.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\AuditLog.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\Bill.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\Budget.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\Comment.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\Investment.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\SavingsGoal.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\ScheduledReport.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\entity\Transaction.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\AdminService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\AnomalyDetectionService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\BudgetAdvisorService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\BudgetService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\CategorizationService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\ChartGeneratorService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\DashboardService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\DropboxService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\ExcelReportGenerator.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\ExportService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\InvestmentService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\PdfReportGenerator.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\PredictionService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\SavingsGoalService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\TransactionService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\UserService.java",
    "C:\budgetwise tracker\backend\src\main\java\com\budgetwise\service\WebSocketService.java"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        $content = Get-Content $file -Raw
        # Remove all Lombok imports
        $content = $content -replace "import\s+lombok\.[^;]+;[\r\n]+", ""
        # Remove @RequiredArgsConstructor annotation
        $content = $content -replace "@RequiredArgsConstructor[\r\n]+", ""
        # Remove @Data annotation
        $content = $content -replace "@Data[\r\n]+", ""
        # Remove @Builder annotation
        $content = $content -replace "@Builder[\r\n]+", ""
        # Remove @AllArgsConstructor annotation
        $content = $content -replace "@AllArgsConstructor[\r\n]+", ""
        # Remove @NoArgsConstructor annotation
        $content = $content -replace "@NoArgsConstructor[\r\n]+", ""
        # Remove @Getter annotation
        $content = $content -replace "@Getter[\r\n]+", ""
        # Remove @Setter annotation
        $content = $content -replace "@Setter[\r\n]+", ""
        # Save back
        Set-Content -Path $file -Value $content -NoNewline
        Write-Host "Processed: $file"
    }
}
Write-Host "Lombok removal complete!"
