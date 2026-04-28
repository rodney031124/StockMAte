Write-Host "Cleaning StockMate project..." -ForegroundColor Yellow

# Remove build directories
if (Test-Path ".gradle") { Remove-Item -Path ".gradle" -Recurse -Force }
if (Test-Path "app\build") { Remove-Item -Path "app\build" -Recurse -Force }
if (Test-Path "build") { Remove-Item -Path "build" -Recurse -Force }
if (Test-Path ".idea\caches") { Remove-Item -Path ".idea\caches" -Recurse -Force }
if (Test-Path ".idea\libraries") { Remove-Item -Path ".idea\libraries" -Recurse -Force }

Write-Host "Clean complete! Now sync the project in Android Studio." -ForegroundColor Green