# Getting Started

This project uses kafka messagebroker with zookeeper SO FIRST RUN THE DOCKER COMPOSE.
Also, it runs in eureka server, thanks to the integration with spring, it's not needed to run any other server to be functional.

# Admin service
Admin Dashboard
# Registry Service
This service shows the ports of every instance registered in the eureka server
# Gateway service
Service ports
    - Orders: 8082
    - Inventory: 8083
    