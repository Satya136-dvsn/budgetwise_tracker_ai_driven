$file = "src\main\java\com\budgetwise\service\DashboardService.java"
$content = Get-Content $file -Raw

# Only change getDashboardSummary to use all transactions
# Remove the date variable lines
$content = $content -replace 'LocalDate startOfMonth = LocalDate\.now\(\)\.with DayOfMonth\(1\);[\r\n\s]+LocalDate endOfMonth = LocalDate\.now\(\)\.withDayOfMonth\(LocalDate\.now\(\)\.lengthOfMonth\(\)\);', ''

# Change the first occurrence (getDashboardSummary) to use all transactions
$content = $content -replace 'List<Transaction> transactions = transactionRepository\.findByUserIdAndTransactionDateBetween\(\s*userId, startOfMonth, endOfMonth\);', 'List<Transaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);', 1

$content | Set-Content $file -NoNewline

Write-Host "Fixed dashboard summary method"
