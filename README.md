# MicroSurveyLab - Backend

Este repositorio contiene el backend del proyecto MicroSurveyLab.
Aquí se implementa la API REST que permite crear encuestas, registrar votos y consultar resultados.

El enfoque es simple: exponer servicios claros, separar responsabilidades y mantener el código fácil de leer y explicar.

## Enlaces públicos

Backend publicado en Railway:
[https://microsurveylab-backend-production.up.railway.app/](https://microsurveylab-backend-production.up.railway.app/)

Endpoint principal para ver datos en formato JSON:
[https://microsurveylab-backend-production.up.railway.app/api/encuestas](https://microsurveylab-backend-production.up.railway.app/api/encuestas)

Repositorio en GitHub:
[https://github.com/Robert2909/microsurveylab-backend](https://github.com/Robert2909/microsurveylab-backend)

## Base de datos

La base de datos está publicada como servicio remoto.
No se incluyen credenciales, únicamente la cadena de conexión.

Cadena de conexión:

```bash
jdbc:postgresql://roundhouse.proxy.railway.app:12345/railway
```

En ejecución local, el proyecto utiliza H2 en memoria según la configuración definida en `application.properties`.

## Tecnologías utilizadas

Uso un stack sencillo y directo:

* Java
* Spring Boot
* Spring Data JPA
* Maven
* PostgreSQL para el entorno en la nube
* H2 para desarrollo local

No se agregaron herramientas que no aportaran directamente a la funcionalidad del sistema.

## Ejecución en local

Para ejecutar el backend en local se requiere:

* Java 17
* Maven

Pasos:

1. Clonar el repositorio

```git
git clone [https://github.com/Robert2909/microsurveylab-backend](https://github.com/Robert2909/microsurveylab-backend)
cd microsurveylab-backend
```

2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

3. Acceder a la API

```bash
[http://localhost:8080/api/encuestas](http://localhost:8080/api/encuestas)
```

## Consola H2 (modo local)

Cuando se ejecuta en local con la configuración por defecto, se puede acceder a la consola H2.

```bash
URL:
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)
```

Parámetros usuales:

* JDBC URL: jdbc:h2:mem:testdb
* Usuario: sa
* Contraseña: vacía

## Endpoints expuestos

Encuestas:

* GET /api/encuestas
* GET /api/encuestas/{id}
* POST /api/encuestas
* PUT /api/encuestas/{id}
* DELETE /api/encuestas/{id}
* POST /api/encuestas/{id}/activar
* POST /api/encuestas/{id}/desactivar

Votos y resultados:

* POST /api/encuestas/{encuestaId}/votos
* GET /api/encuestas/{encuestaId}/resultados

## Ejemplos de uso con CURL

En los ejemplos siguientes, BASE_URL puede ser:

* Producción: [https://microsurveylab-backend-production.up.railway.app](https://microsurveylab-backend-production.up.railway.app)
* Local: [http://localhost:8080](http://localhost:8080)

Así se ve la sintaxis general con BASE_URL para acceder a las pruebas.

```bash
"BASE_URL/api/encuestas"
```

En los ejemplos dejaré el enlace para las pruebas remotas como parte de los ejemplos.

Listar encuestas:

```bash
curl -X GET "https://microsurveylab-backend-production.up.railway.app/api/encuestas"
```

Crear encuesta:

```bash
curl -X POST "https://microsurveylab-backend-production.up.railway.app/api/encuestas" \
  -H "Content-Type: application/json" \
  -d '{
    "pregunta": "¿Cuál es tu lenguaje favorito?",
    "descripcion": "Encuesta de ejemplo",
    "opciones": ["Java", "JavaScript", "Python"]
  }'
```

Registrar voto:

```bash
curl -X POST "https://microsurveylab-backend-production.up.railway.app/api/encuestas/1/votos" \
  -H "Content-Type: application/json" \
  -d '{ "opcionId": 2 }'
```

Consultar resultados:

```bash
curl -X GET "https://microsurveylab-backend-production.up.railway.app/api/encuestas/1/resultados"
```

Eliminar encuesta:

```bash
curl -X DELETE "https://microsurveylab-backend-production.up.railway.app/api/encuestas/1"
```

## Notas finales

El backend está organizado por capas: controller, service, repository, model y dto.

Todo el código contiene comentarios de documentación.

Me parece que usé demasiados bloques de código.
