$filePath = "src\main\java\com\budgetwise\service\DashboardService.java"

# Read all lines
$lines = Get-Content $filePath

# Find and replace lines 40-44 (0-indexed: 39-43)
# Line 40 (index 39): comment - keep as is
# Line 41 (index 40): empty - remove
# Line 42 (index 41): empty - remove  
# Lines 43-44 (index 42-43): replace with new code

# Create new lines array
$newLines = @()
for ($i = 0; $i -lt $lines.Count; $i++) {
    if ($i -eq 40 -or $i -eq 41) {
        # Skip these empty lines
        continue
    }
    elseif ($i -eq 42) {
        # Replace these two lines with the fix
        $newLines += "`t`tList<Transaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);"
        $i++ # Skip line 43 as well
    }
    else {
        $newLines += $lines[$i]
    }
}

# Write back
$newLines | Set-Content $filePath

Write-Host "Dashboard service fixed!"
