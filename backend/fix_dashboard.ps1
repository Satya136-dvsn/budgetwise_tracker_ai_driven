$filePath = "src\main\java\com\budgetwise\service\DashboardService.java"

# Read file content
$content = Get-Content $filePath -Raw

# Replace the broken method call
$oldPattern = 'List<Transaction> transactions = transactionRepository\.findByUserIdAndTransactionDateBetween\(\s+userId, startOfMonth, endOfMonth\);'
$newCode = 'List<Transaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);'

$content = $content -replace $oldPattern, $newCode

# Write back to file
$content | Set-Content $filePath -NoNewline

Write-Host "File updated successfully!"
