Write-Host "StockMate JDK Configuration Helper" -ForegroundColor Yellow
Write-Host "==================================" -ForegroundColor Yellow
Write-Host ""
Write-Host "Your available JDKs:" -ForegroundColor Green
Write-Host "1. jbr-21 (JetBrains Runtime 21.0.9) - Current" -ForegroundColor White
Write-Host "2. ms-17 (Microsoft OpenJDK 17.0.18) - Recommended" -ForegroundColor White
Write-Host "3. temurin-24 (Eclipse Temurin 24.0.2)" -ForegroundColor White
Write-Host "4. temurin-25 (Eclipse Temurin 25)" -ForegroundColor White
Write-Host ""
Write-Host "For Android development, Java 17 is recommended." -ForegroundColor Cyan
Write-Host ""
Write-Host "To manually configure in Android Studio:" -ForegroundColor Yellow
Write-Host "1. File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle" -ForegroundColor White
Write-Host "2. Under 'Gradle JDK', select 'ms-17 Microsoft OpenJDK 17.0.18'" -ForegroundColor White
Write-Host "3. Click Apply and OK" -ForegroundColor White
Write-Host "4. Sync the project" -ForegroundColor White