Write-Host "Cleaning StockMate project..." -ForegroundColor Yellow
Remove-Item -Path "C:\Users\rotsh\Desktop\StockMate\.gradle" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "C:\Users\rotsh\Desktop\StockMate\.idea" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "C:\Users\rotsh\Desktop\StockMate\app\build" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "C:\Users\rotsh\Desktop\StockMate\build" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "Clean complete! Now open the project in Android Studio." -ForegroundColor Green