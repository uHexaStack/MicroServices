# Integración Kafka: AuthService ↔ ProfileService

## 🎯 Resumen
Se ha implementado una comunicación asíncrona entre AuthService y ProfileService usando Apache Kafka, reemplazando la comunicación síncrona directa.

## 🔄 Flujo de Comunicación

### Antes (Comunicación Síncrona):
```
AuthService → ProfileContextFacade → ProfileService
```

### Ahora (Comunicación Asíncrona con Kafka):
```
AuthService → Kafka Topic → ProfileService
```

## 📁 Archivos Modificados/Creados

### AuthService:
1. **`UserRegisteredEventHandler.java`** - Modificado para enviar eventos a Kafka
2. **`KafkaConfig.java`** - Configuración del productor Kafka
3. **`UserCommandServiceImpl.java`** - Ya existía, dispara el evento

### ProfileService:
1. **`UserRegisteredEventConsumer.java`** - Consumidor Kafka para eventos de usuario
2. **`KafkaConfig.java`** - Configuración del consumidor Kafka
3. **`UserRegisteredEvent.java`** - DTO para deserializar eventos

## 🔧 Configuración Kafka

### Tópico:
- **Nombre**: `user-registered`
- **Particiones**: 1 (por defecto)
- **Replicación**: 1 (desarrollo)

### Grupos de Consumidor:
- **ProfileService**: `profile-service-group`

### Configuración del Productor (AuthService):
- **Acks**: `all` (máxima durabilidad)
- **Retries**: 3
- **Serializer**: JSON

### Configuración del Consumidor (ProfileService):
- **Auto Offset Reset**: `earliest`
- **Auto Commit**: `false` (control manual)
- **Deserializer**: JSON

## 🚀 Beneficios de la Implementación

### 1. **Desacoplamiento**
- Los servicios ya no dependen directamente entre sí
- Cada servicio puede evolucionar independientemente

### 2. **Escalabilidad**
- Múltiples instancias de ProfileService pueden procesar eventos
- Mejor distribución de carga

### 3. **Resiliencia**
- Si ProfileService está caído, los eventos se almacenan en Kafka
- Recuperación automática cuando el servicio vuelve a estar disponible

### 4. **Trazabilidad**
- Todos los eventos quedan registrados en Kafka
- Facilita debugging y auditoría

### 5. **Performance**
- AuthService no espera la respuesta de ProfileService
- Respuesta más rápida al usuario

## 📊 Monitoreo y Logging

### AuthService Logs:
```java
logger.info("Publishing user registered event to Kafka: {}", event.userId());
logger.info("User registered event sent successfully to Kafka for user: {}", event.userId());
logger.error("Failed to send user registered event to Kafka for user: {}", event.userId(), ex);
```

### ProfileService Logs:
```java
logger.info("Received user registered event from topic: {}, partition: {}, offset: {}", topic, partition, offset);
logger.info("Processing user registration for user ID: {}", event.userId());
logger.info("Profile created successfully for user ID: {}", event.userId());
logger.error("Failed to create profile for user ID: {}", event.userId(), e);
```

## 🧪 Testing

### Comandos de Prueba:
```bash
# 1. Verificar servicios
docker-compose ps auth-service profile-service kafka

# 2. Registrar usuario
curl -X POST http://localhost:8088/api/v1/auth/signup -H "Content-Type: application/json" -d '{...}'

# 3. Verificar logs
docker-compose logs auth-service | grep "user-registered"
docker-compose logs profile-service | grep "user-registered"

# 4. Verificar perfil creado
curl -X GET http://localhost:8087/api/v1/profiles/user/1
```

## 🔍 Troubleshooting

### Problemas Comunes:
1. **Kafka no disponible**: Verificar que Kafka esté corriendo
2. **Serialización**: Verificar que los DTOs coincidan
3. **Configuración**: Verificar bootstrap-servers en application.yml
4. **Base de datos**: Verificar que MySQL esté disponible

### Comandos de Diagnóstico:
```bash
# Ver tópicos
docker exec -it aquaengine-kafka kafka-topics --list --bootstrap-server localhost:9092

# Ver consumidores
docker exec -it aquaengine-kafka kafka-consumer-groups --bootstrap-server localhost:9092 --list

# Ver mensajes
docker exec -it aquaengine-kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic user-registered --from-beginning
```

## 🔮 Próximos Pasos

### Posibles Mejoras:
1. **Dead Letter Queue**: Para eventos que fallan
2. **Retry Policy**: Configuración de reintentos
3. **Schema Registry**: Para validación de esquemas
4. **Métricas**: Prometheus + Grafana para monitoreo
5. **Event Sourcing**: Para auditoría completa

### Otros Servicios:
- OrderService ↔ InventoryService
- PaymentService ↔ BillingService
- NotificationService ↔ Todos los servicios

## 📚 Referencias

- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Event-Driven Architecture Patterns](https://martinfowler.com/articles/201701-event-driven.html) 