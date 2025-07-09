# AQUAENGINE - Docker Compose Setup

Este archivo contiene las instrucciones para ejecutar el proyecto AQUAENGINE usando Docker Compose.

## 🚀 Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Docker Compose (incluido en Docker Desktop)
- Al menos 8GB de RAM disponible
- Al menos 10GB de espacio libre en disco

## 📋 Servicios Incluidos

### Infraestructura
- **MySQL 8.0** - Base de datos principal (puerto 3306)
- **Kafka** - Message broker (puerto 9092)
- **Zookeeper** - Coordinador para Kafka
- **RabbitMQ** - Message broker adicional (puertos 5672, 15672)

### Microservicios
- **Registry Service** - Eureka Server (puerto 8761)
- **Gateway Service** - API Gateway (puerto 8080)
- **Auth Service** - Autenticación y autorización (puerto 8088)
- **Order Service** - Gestión de órdenes (puerto 8082)
- **Inventory Service** - Gestión de inventario (puerto 8083)
- **Billing Service** - Facturación (puerto 8084)
- **Payment Service** - Pagos (puerto 8085)
- **Profile Service** - Perfiles de usuario (puerto 8087)

- **Notification Service** - Notificaciones (puerto 8086)

## 🛠️ Comandos de Uso

### 1. Iniciar todos los servicios
```bash
docker-compose up -d
```

### 2. Ver logs de todos los servicios
```bash
docker-compose logs -f
```

### 3. Ver logs de un servicio específico
```bash
docker-compose logs -f [nombre-servicio]
# Ejemplo: docker-compose logs -f auth-service
```

### 4. Detener todos los servicios
```bash
docker-compose down
```

### 5. Detener y eliminar volúmenes
```bash
docker-compose down -v
```

### 6. Reconstruir un servicio específico
```bash
docker-compose build [nombre-servicio]
docker-compose up -d [nombre-servicio]
```

### 7. Ver estado de los servicios
```bash
docker-compose ps
```

## 🔧 Configuración Inicial

### 1. Crear las bases de datos
Después de que MySQL esté corriendo, ejecuta el script de inicialización:

```bash
# Conectar al contenedor MySQL
docker exec -it aquaengine-mysql mysql -u root -prootpassword

# Dentro de MySQL, ejecutar:
source /path/to/init-databases.sql
```

O ejecutar directamente:
```bash
docker exec -i aquaengine-mysql mysql -u root -prootpassword < init-databases.sql
```

### 2. Verificar que todos los servicios estén corriendo
```bash
docker-compose ps
```

## 🌐 URLs de Acceso

### Servicios de Infraestructura
- **Eureka Dashboard**: http://localhost:8761
- **RabbitMQ Management**: http://localhost:15672 (admin/admin123)
- **MySQL**: localhost:3306

### API Gateway
- **Gateway**: http://localhost:8080

### Microservicios (a través del Gateway)
- **Auth Service**: http://localhost:8080/api/v1/auth/
- **Order Service**: http://localhost:8080/api/v1/orders/
- **Inventory Service**: http://localhost:8080/api/v1/inventories/
- **Billing Service**: http://localhost:8080/api/v1/billing/
- **Payment Service**: http://localhost:8080/api/v1/payments/
- **Profile Service**: http://localhost:8080/api/v1/profiles/
- **Notification Service**: http://localhost:8080/api/v1/notifications/


### Health Checks
- **Registry**: http://localhost:8761/actuator/health
- **Gateway**: http://localhost:8080/actuator/health
- **Auth**: http://localhost:8088/actuator/health
- **Order**: http://localhost:8082/actuator/health
- **Inventory**: http://localhost:8083/actuator/health
- **Billing**: http://localhost:8084/actuator/health
- **Payment**: http://localhost:8085/actuator/health
- **Profile**: http://localhost:8087/actuator/health

- **Notification**: http://localhost:8086/actuator/health

## 🔍 Troubleshooting

### 1. Servicios no inician
```bash
# Ver logs detallados
docker-compose logs [nombre-servicio]

# Verificar recursos del sistema
docker stats
```

### 2. Problemas de conectividad entre servicios
```bash
# Verificar red Docker
docker network ls
docker network inspect aquaengine_aquaengine-network
```

### 3. Problemas de base de datos
```bash
# Conectar a MySQL
docker exec -it aquaengine-mysql mysql -u aquaengine -paquaengine123

# Verificar bases de datos
SHOW DATABASES;
```

### 4. Limpiar completamente
```bash
# Detener y eliminar todo
docker-compose down -v --remove-orphans
docker system prune -a
```

## 📊 Monitoreo

### 1. Estado de los contenedores
```bash
docker-compose ps
```

### 2. Uso de recursos
```bash
docker stats
```

### 3. Logs en tiempo real
```bash
docker-compose logs -f --tail=100
```

## 🔐 Credenciales

- **MySQL Root**: root/rootpassword
- **MySQL User**: aquaengine/aquaengine123
- **RabbitMQ**: admin/admin123

## 📝 Notas Importantes

1. **Primera ejecución**: La primera vez que ejecutes `docker-compose up`, puede tomar varios minutos para descargar las imágenes y construir los servicios.

2. **Puertos**: Asegúrate de que los puertos especificados no estén en uso por otros servicios.

3. **Memoria**: Los servicios de Kafka y MySQL pueden consumir bastante memoria. Asegúrate de tener suficiente RAM disponible.

4. **Persistencia**: Los datos de MySQL se almacenan en un volumen Docker. Si ejecutas `docker-compose down -v`, perderás todos los datos.

5. **Logs**: Los logs de cada servicio se pueden ver individualmente o todos juntos usando los comandos de Docker Compose.

## 🚀 Desarrollo

Para desarrollo local, puedes:

1. Modificar el código en los directorios de los servicios
2. Reconstruir el servicio específico: `docker-compose build [servicio]`
3. Reiniciar el servicio: `docker-compose up -d [servicio]`

## 📞 Soporte

Si encuentras problemas:

1. Verifica los logs del servicio específico
2. Asegúrate de que todos los requisitos previos estén cumplidos
3. Verifica que los puertos no estén en uso
4. Revisa la documentación de cada servicio individual 