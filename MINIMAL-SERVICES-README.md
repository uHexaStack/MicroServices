# AQUAENGINE - Servicios Mínimos

Este documento explica cómo ejecutar solo los servicios esenciales de AQUAENGINE: **AuthService**, **ProfileService**, **Kafka** y **Zookeeper**.

## 🎯 Servicios Incluidos

- **Zookeeper** (puerto 2181) - Coordinador de Kafka
- **Kafka** (puerto 9092) - Message broker
- **Registry Service** (puerto 8761) - Service discovery (Eureka)
- **Gateway Service** (puerto 8080) - API Gateway y routing
- **Auth Service** (puerto 8088) - Autenticación y autorización
- **Profile Service** (puerto 8087) - Gestión de perfiles de usuario

## 🚀 Inicio Rápido

### Opción 1: Script Automático (Recomendado)

#### En Windows (PowerShell):
```powershell
.\start-minimal-services.ps1
```

#### En Linux/Mac:
```bash
chmod +x start-minimal-services.sh
./start-minimal-services.sh
```

### Opción 2: Comando Manual

```bash
# Construir e iniciar todos los servicios
docker-compose -f docker-compose-minimal.yml up -d --build

# Ver el estado de los servicios
docker-compose -f docker-compose-minimal.yml ps

# Ver logs en tiempo real
docker-compose -f docker-compose-minimal.yml logs -f
```

## 📊 URLs de Acceso

Una vez iniciados los servicios, puedes acceder a:

- **Eureka Registry**: http://localhost:8761
- **Gateway Service**: http://localhost:8080
- **Auth Service**: http://localhost:8088
- **Profile Service**: http://localhost:8087
- **Kafka**: localhost:9092

## 🔧 Comandos Útiles

### Ver logs de un servicio específico:
```bash
# Gateway Service
docker-compose -f docker-compose-minimal.yml logs -f gateway-service

# Auth Service
docker-compose -f docker-compose-minimal.yml logs -f auth-service

# Profile Service
docker-compose -f docker-compose-minimal.yml logs -f profile-service

# Kafka
docker-compose -f docker-compose-minimal.yml logs -f kafka

# Zookeeper
docker-compose -f docker-compose-minimal.yml logs -f zookeeper
```

### Reiniciar un servicio específico:
```bash
docker-compose -f docker-compose-minimal.yml restart gateway-service
docker-compose -f docker-compose-minimal.yml restart auth-service
docker-compose -f docker-compose-minimal.yml restart profile-service
```

### Detener todos los servicios:
```bash
docker-compose -f docker-compose-minimal.yml down
```

### Detener y eliminar volúmenes:
```bash
docker-compose -f docker-compose-minimal.yml down -v
```

## 🔍 Verificación de Servicios

### 1. Verificar Eureka Registry
Abre http://localhost:8761 en tu navegador. Deberías ver:
- Auth Service registrado
- Profile Service registrado

### 2. Verificar Kafka
```bash
# Entrar al contenedor de Kafka
docker exec -it aquaengine-kafka bash

# Listar topics
kafka-topics --bootstrap-server localhost:29092 --list

# Salir del contenedor
exit
```

### 3. Verificar Health Checks
```bash
# Gateway Service
curl http://localhost:8080/actuator/health

# Auth Service
curl http://localhost:8088/actuator/health

# Profile Service
curl http://localhost:8087/actuator/health
```

## ⚠️ Requisitos Previos

1. **Docker Desktop** instalado y ejecutándose
2. **Docker Compose** disponible
3. **Conexión a internet** para descargar las imágenes de Kafka y Zookeeper
4. **Acceso a la base de datos MySQL** (configurada en las variables de entorno)

## 🐛 Solución de Problemas

### Error: Puerto ya en uso
```bash
# Ver qué está usando el puerto
netstat -ano | findstr :8088  # Windows
lsof -i :8088                 # Linux/Mac

# Detener el proceso o cambiar el puerto en docker-compose-minimal.yml
```

### Error: Docker no está ejecutándose
- Asegúrate de que Docker Desktop esté iniciado
- En Windows, verifica que WSL2 esté habilitado

### Error: Imagen no encontrada
```bash
# Limpiar imágenes y reconstruir
docker-compose -f docker-compose-minimal.yml down
docker system prune -f
docker-compose -f docker-compose-minimal.yml build --no-cache
```

### Error: Base de datos no accesible
- Verifica que las credenciales de la base de datos sean correctas
- Asegúrate de que la base de datos esté accesible desde tu red

## 📝 Notas Importantes

- Los servicios usan la base de datos MySQL remota configurada en las variables de entorno
- Kafka y Zookeeper se ejecutan localmente en contenedores Docker
- El Registry Service (Eureka) es necesario para el service discovery entre AuthService y ProfileService
- Los health checks aseguran que los servicios se inicien en el orden correcto

## 🔄 Agregar Más Servicios

Si necesitas agregar más servicios del ecosistema AQUAENGINE, puedes:

1. Copiar la configuración del servicio desde `docker-compose.yml` original
2. Agregarlo al `docker-compose-minimal.yml`
3. Asegurarte de que las dependencias estén correctamente configuradas 