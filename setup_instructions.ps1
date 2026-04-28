Write-Host "========================================" -ForegroundColor Cyan
Write-Host "StockMate Project Setup Helper" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Checking Android SDK..." -ForegroundColor Yellow
$sdkPath = "C:\Users\rotsh\AppData\Local\Android\Sdk"
if (Test-Path $sdkPath) {
    Write-Host "? Android SDK found at: $sdkPath" -ForegroundColor Green
} else {
    Write-Host "? Android SDK not found at default location" -ForegroundColor Red
    Write-Host "Please update local.properties with your SDK path" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Checking Java JDK..." -ForegroundColor Yellow
$javaHome = $env:JAVA_HOME
if ($javaHome) {
    Write-Host "? JAVA_HOME: $javaHome" -ForegroundColor Green
} else {
    Write-Host "? JAVA_HOME not set" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Instructions for Android Studio:" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Open Android Studio" -ForegroundColor White
Write-Host "2. Close any open projects" -ForegroundColor White
Write-Host "3. Click 'Open' and select: C:\Users\rotsh\Desktop\StockMate" -ForegroundColor White
Write-Host "4. When prompted about Gradle, select 'Use default gradle wrapper'" -ForegroundColor White
Write-Host "5. Wait for the project to sync" -ForegroundColor White
Write-Host ""
Write-Host "If sync fails, try:" -ForegroundColor Yellow
Write-Host "   - File -> Invalidate Caches and Restart" -ForegroundColor White
Write-Host "   - Then click 'Sync Project with Gradle Files'" -ForegroundColor White
Write-Host ""
Write-Host "To run the app:" -ForegroundColor Green
Write-Host "   - Connect an Android device or start an emulator" -ForegroundColor White
Write-Host "   - Click the green 'Run' button (?)" -ForegroundColor White