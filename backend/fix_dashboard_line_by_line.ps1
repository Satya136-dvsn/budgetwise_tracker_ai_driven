$filePath = "src\main\java\com\budgetwise\service\DashboardService.java"

# Read all lines
$lines = Get-Content $filePath

# Create new lines array with fixes
$newLines = @()
$skipNext = $false

for ($i = 0; $i -lt $lines.Count; $i++) {
    if ($skipNext) {
        $skipNext = $false
        continue
    }
    
    # Line 39 (index 39) - keep as is
    # Lines 40-41 (index 39-40) - remove these LocalDate lines
    if ($i -eq 39 -or $i -eq 40) {
        continue  # Skip these lines
    }
    # Line 42 (index 41) - empty line, keep
    # Lines 43-44 (index 42-43) - replace with single line
    elseif ($i -eq 42) {
        $newLines += "`t`tList<Transaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);"
        $skipNext = $true  # Skip line 43
    }
    else {
        $newLines += $lines[$i]
    }
}

# Write back
$newLines | Set-Content $filePath

Write-Host "Successfully fixed getDashboardSummary method!"
