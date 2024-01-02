![Encabezado del Proyecto](./header.png)
# SpringBatchFlow

SpringBatchFlow es una aplicación Spring Batch diseñada para procesar transacciones bancarias. Esta aplicación recibe solicitudes POST con datos de transacciones bancarias, procesa estos datos y los almacena en una base de datos PostgreSQL.

## Tecnologías Utilizadas

- Spring Boot 3
- Java 17
- PostgreSQL

## Cómo Funciona

La aplicación consta de cuatro tasklets que configuran un job de procesamiento de pagos:

1. `ValidateAccountTasklet`: Valida la cuenta. Si la cuenta está inactiva o no tiene saldo suficiente, el `ExitStatus` es "INVALID". Si la cuenta está activa y hay saldo, el `ExitStatus` es "VALID".

2. `ProcessPaymentTasklet`: Se ejecuta si `ExitStatus` es "VALID", procesando el pago y guardando los datos en la base de datos.

3. `CancelTransactionTasklet`: Se ejecuta si `ExitStatus` es "INVALID", cancelando el pago y guardando los datos en la base de datos.

4. `SendNotificationTasklet`: Se ejecuta después de `ProcessPaymentTasklet` o `CancelTransactionTasklet` para enviar una notificación con el resultado de la transacción.

## Configuración y Uso

### Requisitos

- Java 17
- PostgreSQL
- Docker

### Instalación y Ejecución

1. **Clonar el Repositorio**:

   ```sh
   git clone https://github.com/agcadu/SpringBatchFlow.git
   ```

2. **Navegar al directorio del proyecto**:

   ```sh
   cd SpringBatchFlow
   ```
   
3. **Ejecutar la aplicación**:

   ```sh
   docker compose up
   ```
   
    Esto ejecutará la aplicación y la base de datos PostgreSQL en contenedores Docker.

### API Endpoints

- POST `http://localhost:8080/api/v1/payment/transfer` - Para iniciar una transacción bancaria.

### Acceso a la Base de Datos

Utiliza JDBC para conectarse a PostgreSQL:

`jdbc:postgresql://localhost:5432/batchDB`

## Pruebas

Se incluye una colección Postman para facilitar las pruebas de la API:

- `SpringBatchFlow.postman_collection.json`


