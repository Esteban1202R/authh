# E-commerce - Microservicios Auth y Catalogo

Proyecto de Profundizacion de Software, grupo 802.

## Que hay en este repositorio

```
auth/            microservicio de autenticacion   puerto 8080
catalogo/        microservicio de catalogo        puerto 8081
base_de_datos/   respaldo de PostgreSQL con estructura y datos
postman/         coleccion con las peticiones listas para importar
documentacion/   explicacion del codigo y la guia de la actividad
```

## Como levantarlo en un PC nuevo

Necesitas JDK 21 y PostgreSQL 18 en `localhost:5432`, con el usuario `postgres`
y la clave `1234`. Si tu clave es otra, cambiala en los dos `application.properties`.

**1. Restaurar la base**

```
createdb -U postgres ecommerce_auth
psql -U postgres -d ecommerce_auth -f base_de_datos/ecommerce_auth.sql
```

Quedan las 4 tablas (usuarios, producto, carrito, item_carrito) con sus 3 llaves foraneas.

**2. Levantar los servicios, en este orden**

Auth primero, porque Catalogo le pregunta por el usuario y el saldo.

```
cd auth
mvnw spring-boot:run          esperar a "Tomcat started on port 8080"
```

En otra terminal:

```
cd catalogo
mvnw spring-boot:run          esperar a "Tomcat started on port 8081"
```

**3. Importar la coleccion en Postman**

Import -> File -> `postman/Catalogo_Ecommerce.postman_collection.json`

Usa variables, asi que no hay que editar cada peticion:

| Variable | Valor |
|---|---|
| authUrl | http://localhost:8080/api/ecommerce/usuario |
| catalogoUrl | http://localhost:8081/api/ecommerce |
| idUsuario | 7 |

## Orden para probar

1. Productos -> Crear producto
2. Saldo -> Recargar saldo
3. Carrito -> Crear carrito
4. Carrito -> Agregar producto
5. Carrito -> Ver carrito (se ve el total calculado)
6. Carrito -> COMPRAR
7. Saldo -> Consultar saldo y Productos -> Listar, para ver el descuento

## Codigos de respuesta

| Codigo | Cuando |
|---|---|
| 200 | la peticion se atendio, incluidas las validaciones que el sistema rechaza correctamente (el motivo va en el cuerpo) |
| 404 | el recurso pedido no existe |
| 500 | error no controlado |

Se maneja con dos excepciones propias en cada microservicio:
`ValidacionNegocioException` (200) y `NoEncontradoException` (404).

## Notas

- Las contrasenas se guardan con BCrypt y nunca se devuelven al cliente.
- El saldo no se puede asignar desde el CRUD: un usuario nuevo nace en 0 y solo
  cambia con las APIs de recargar y descontar.
- Agregar productos al carrito no descuenta inventario ni cobra. El stock y el
  saldo solo se mueven al confirmar la compra.
