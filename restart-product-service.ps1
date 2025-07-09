# Script para reiniciar Product Service (anteriormente Inventory Service)
Write-Host "Deteniendo Product Service..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml stop product-service

Write-Host "Eliminando contenedor Product Service..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml rm -f product-service

Write-Host "Reconstruyendo imagen Product Service..." -ForegroundColor Yellow
docker-compose -f docker-compose-no-admin.yml build product-service

Write-Host "Iniciando Product Service..." -ForegroundColor Green
docker-compose -f docker-compose-no-admin.yml up -d product-service

Write-Host "Mostrando logs del Product Service..." -ForegroundColor Cyan
docker-compose -f docker-compose-no-admin.yml logs -f product-service