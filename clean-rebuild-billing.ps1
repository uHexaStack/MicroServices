# Script para limpiar y reconstruir completamente el BillingService
Write-Host "Deteniendo BillingService..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml stop billing-service

Write-Host "Eliminando contenedor BillingService..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml rm -f billing-service

Write-Host "Eliminando imagen BillingService..." -ForegroundColor Yellow
docker rmi aquaengine-billing-service

Write-Host "Limpiando target del BillingService..." -ForegroundColor Yellow
Remove-Item -Recurse -Force BillingService/target -ErrorAction SilentlyContinue

Write-Host "Reconstruyendo imagen BillingService..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml build --no-cache billing-service

Write-Host "Iniciando BillingService..." -ForegroundColor Green
docker-compose -f docker-compose-no-admin.yml up -d billing-service

Write-Host "Mostrando logs del BillingService..." -ForegroundColor Cyan
docker-compose -f docker-compose-no-admin.yml logs -f billing-service 