
# 1. Crea un repositorio en GitHub, que sea público y que tenga al profesor como colaborador directo del repositorio. Crea un README.md donde plantees los siguientes puntos:
## Nombre del proyecto:
### TAREAPI

## Descripción detallada de los documentos que intervendrán en el proyecto, así como sus campos.


* ### Usuario --> El usuario que se logeara en la base de datos y se pueda asignar,borrar o actualizar una tarea si es dueño de la propia tarea o administrador
- _id : String?
- username: String
- password: String
- email: String
- direccion: Direccion
- roles: String = "USER"

* ### Direccion --> La direccion del usuario
- calle:String
- num:String
- municipio:String
- provincia:String
- cp:String

* ### Tarea --> La tarea que se vaya a asignar al usuario
- _id : String?
- titulo: String
- texto: String
- estado: Boolean
- fecha_inicio: Date
- usuario: String?


# 2. En el README anteriormente construido deberás incluir lo siguiente (aparte de lo ya descrito)
## a. Indicar los endpoints que se van a desarrollar para cada documento.
## b. Describir cada uno de los endpoints. Realiza una explicación sencilla de cada endpoint.

### /users
- POST → /login → Autenticar un usuario y generar un token JWT.
- POST → /register → Registra un nuevo usuario si no esta en la base de datos
- GET → /getInfo → Obtiene toda la informacion de un usuario, sin pasarlo por un dto

## /tareas
- POST → /crearTarea → Crea una tarea
- GET → /obtenerTareas → Lista tus tareas asignadas
- GET → /obtenerTareasSinAsignar → Lista las tareas que no se han asignado
- GET → /obtenerTodasTareas → Lista todas las tareas de la base de datos, solo puede ser ejecutado por un administrador
- PUT → /asignarTarea/{idTarea} → Asigna la tarea al usuario que ha entrado en el endpoint
- PUT → /asignarTareaAUsuario/{username}/{tareaId} → Asigna una tarea a un usuario, solo puede ser ejecutado por un administrador
- PUT → /modTarea/{tareaId} → Modifica la tarea, el texto o el titulo
- PUT → /completarTarea/{tareaId} → Completa una tarea asignada
- PUT → /desmarcarTarea/{tareaId} → Desmarca una tarea asignada
- DELETE → /eliminarTarea/{idTarea} → Elimina una tarea, solo puede hacerlo un admin o el propietario de la tarea

## c. Describe la lógica de negocio que va a contener tu aplicación.

- ## Usuarios
* La contraseña se hashea antes de entrar a la base de datos
* No se puede crear un usuario con un username existente
* La contraseña no puede ser una cadena vacía, el nombre tampoco
* El usuario puede ser eliminado por sí mismo o por un administrador

- ## Tareas
* Una tarea solo puede ser asignada a un usuario
* Una tarea no puede tener campos vacios
* Si una tarea ya ha sido asignada no se puede reasignar
* Una tarea solo puede ser borrada por el propio usuario o admin
* Una tarea solo puede ser acabada por el usuario que se la asigno

- ## Direccion
* La direccion debe de ser correcta (provincia y municipio) 



## d. Describe las excepciones que vas a generar y los códigos de estado que vas a poner en todos los casos.
- 400 → Bad request → La información de la request no son válidos
- 401 → Unauthorized → El acceso no está autorizado
- 403 → Forbidden → El acceso necesita admin
- 404 → Not found → No encuentra algo en la base de datos
- 409 → Conflict → Se intenta insertar algo que ya está en la base de datos

## e. Describe las restricciones de seguridad que vas a aplicar dentro de tu API REST
- Todos los endpoints a excepción de login y register requieren de JWT, cada usuario requerirá de un token para acceder a los endpoints que requieren autorización
- Roles, aparte del jwt si intentas acceder a un endpoint que requiere el admin debes de tener el role
- Bcrypt, la contraseña entra a la base de datos encriptada
- En cada endpoint que se ha creado se valida la información

# PRUEBAS GESTION DE USUARIOS
* Todas estas pruebas están consultando a render y no a localhost, asi que render funciona
* Las pruebas que hice en la interfaz grafica no estan borradas, estan comentadas en el readme


- Iniciar sesion con un usuario existente
![img.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img.png)
<!--
![img_1.png](src/main/resources/capturasDeFuncionamiento/img_1.png)
![img.png](src/main/resources/capturasDeFuncionamiento/img.png)
![img_2.png](src/main/resources/capturasDeFuncionamiento/img_2.png)
-->
- Iniciar sesion con credenciales incorrectas
![img_1.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_1.png)
<!--
![img_4.png](src/main/resources/capturasDeFuncionamiento/img_4.png)
![img_3.png](src/main/resources/capturasDeFuncionamiento/img_3.png)
-->
- Registrarse correctamente
![img_2.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_2.png)
![img_3.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_3.png)
<!--
![img_6.png](src/main/resources/capturasDeFuncionamiento/img_6.png)
![img_5.png](src/main/resources/capturasDeFuncionamiento/img_5.png)
![img_7.png](src/main/resources/capturasDeFuncionamiento/img_7.png)
![img_8.png](src/main/resources/capturasDeFuncionamiento/img_8.png)
-->
- Registro incorrecto

![img_4.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_4.png)
![img_5.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_5.png)
![img_6.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_6.png)
![img_7.png](src/main/resources/capturasDeFuncionamiento/deInsonmnia/img_7.png)

<!--
![img_10.png](src/main/resources/capturasDeFuncionamiento/img_10.png)
![img_11.png](src/main/resources/capturasDeFuncionamiento/img_11.png)
![img_9.png](src/main/resources/capturasDeFuncionamiento/img_9.png)
-->

<!--
- videos demostrando el funcionamiento de la interfaz grafica, login y registro
- https://drive.google.com/file/d/1u-fG6bRFW7P0V1mL9h0HFCUSNWxk0N31/view?usp=sharing
- https://drive.google.com/file/d/1Znc8UbSSNjrjdEDooLWrA_4seVOkr9OA/view?usp=sharing
-->

# PRUEBAS GESTION DE TAREAS

* ### TOKEN DE ADMINISTRADOR: 
* eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJhbiIsImV4cCI6MTc0MDU4OTMwMSwiaWF0IjoxNzQwNTg1NzAxLCJyb2xlcyI6IlJPTEVfQURNSU4ifQ.eHrGxsBl4iHD57D5alsug5E324_wwIBF8k2X4pDcUNWYooBm7FQMY1lvcJcbIm5ihhyCurZ9cM78qtWKu1NORRe_PcFTsETqbYJm4ksSudoUYAWxk3KRRS9AKyhKYxZO63AyMlg0MbudAdiKsd8EOaHG3_oZI9oV_feOto7xJrQlTzNDG-vhP24fGO5BaRfCtzJkskO_0pqe1ctLDs2jSXm60kzXanKJciDPjGJHqcPlCvN8IAzq7iGFb8iQ4X64tEanvKwkJVFcUyjl9zZlDlFad4Jqg6aBudmWlXVpRHpDss_BT4mrHMJKPlepEq-HZApMpakl0b7FYy_FQwrizw
### TOKEN DE USUARIO:
* eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJhbmNlc2MiLCJleHAiOjE3NDA1ODkzMjgsImlhdCI6MTc0MDU4NTcyOCwicm9sZXMiOiJST0xFX1VTRVIifQ.STC0RGynn35XfDVAB_OL0AIiodQw-VbBWQfpTYDYvm1n3dLg0GjUYaMB2v6Hs7sYUu8gS9uztUj7tb13eS47f-w1NstS1aQxUuw86H8IOlbirLPh0NB3SnJnpAxy-SgHeqwINcgqUlO2unYIaVupMqXKGaeKrbZDzy3kAPLYegKQBuYHcuyHbf6kWsDifkKK6o_0dC2mMdoOUTMDuM_ANt58tQTP88HyYSj71i8-oYLVuK80wsmWVchm5ZcYL6ycAp4T12qzRfCvYMqcZOBqqgsicEmp6FjZZOL5V-dndJZkT-gbc57Od-o5iSPL5jDuj4N6ps1h0VgQFnuSjD8XLQ

- ## Insertar una tarea
- Necesitas un jwt para que funcione, y los campos de la tarea no pueden estar vacios

* Caso correcto 
![img.png](src/main/resources/capturasDeFuncionamientoTareas/img.png)

* No hay autorizacion
![img_1.png](src/main/resources/capturasDeFuncionamientoTareas/img_1.png)

* El json esta mal
![img_2.png](src/main/resources/capturasDeFuncionamientoTareas/img_2.png)
![img_3.png](src/main/resources/capturasDeFuncionamientoTareas/img_3.png)

- ## Obtener tus tareas asignadas
- Necesitas un jwt, si no tienes tareas asignadas te devuelve una lista vacia, para asignarte a una tarea es otro endpoint

* Caso correcto
![img_4.png](src/main/resources/capturasDeFuncionamientoTareas/img_4.png)
![img_5.png](src/main/resources/capturasDeFuncionamientoTareas/img_5.png)

* No hay autorizacion
![img_6.png](src/main/resources/capturasDeFuncionamientoTareas/img_6.png)

- ## Obtener las tareas que no se han asignado
- Necesitas un jwt, si no hay tareas devuelve una lista vacia

* Caso correcto
![img_9.png](src/main/resources/capturasDeFuncionamientoTareas/img_9.png)

* No hay autorizacion 
![img_7.png](src/main/resources/capturasDeFuncionamientoTareas/img_7.png)

- ## Obtener todas las tareas
- Necesitas un jwt y ser administrador

* Caso correcto
![img_11.png](src/main/resources/capturasDeFuncionamientoTareas/img_11.png)

* No hay autorizacion
![img_10.png](src/main/resources/capturasDeFuncionamientoTareas/img_10.png)

* No eres admin
![img_12.png](src/main/resources/capturasDeFuncionamientoTareas/img_12.png)

- ## Asignarse una tarea
- Necesitas un jwt, la tarea no puede se puede asignar si ya esta asignada y que la tarea exista

* Caso correcto
![img_13.png](src/main/resources/capturasDeFuncionamientoTareas/img_13.png)

* No hay autorizacion
![img_15.png](src/main/resources/capturasDeFuncionamientoTareas/img_15.png)

* La tarea ya estaba asignada
![img_14.png](src/main/resources/capturasDeFuncionamientoTareas/img_14.png)

* La tarea no existe
![img_20.png](src/main/resources/capturasDeFuncionamientoTareas/img_20.png)

- ## Asignar tarea a un usuario
- Necesitas un jwt, ser admin, que la tarea no tenga a nadie asignado y que la tarea y el usuario existan

* Caso correcto
![img_18.png](src/main/resources/capturasDeFuncionamientoTareas/img_18.png)

* No hay autorizacion
![img_16.png](src/main/resources/capturasDeFuncionamientoTareas/img_16.png)

* No eres admin
![img_17.png](src/main/resources/capturasDeFuncionamientoTareas/img_17.png)

* La tarea ya estaba asignada
![img_19.png](src/main/resources/capturasDeFuncionamientoTareas/img_19.png)

* La tarea no existe
![img_21.png](src/main/resources/capturasDeFuncionamientoTareas/img_21.png)

* El usuario no existe
![img_22.png](src/main/resources/capturasDeFuncionamientoTareas/img_22.png)

- ## Modificar el titulo o texto de una tarea
- Necesitas un jwt, ser admin o el que tiene la tarea asignada, el json de la tarea tiene que ser correcto(titulo y texto no pueden estar vacios), la tarea tiene que estar asignada y la tarea tiene que existir

* Caso correcto
![img.png](src/main/resources/capturasDeFuncionamientoTareas/img_39.png)

* No hay autorizacion
![img_1.png](src/main/resources/capturasDeFuncionamientoTareas/img_40.png)

* No eres el que tiene la tarea asignada
![img_2.png](src/main/resources/capturasDeFuncionamientoTareas/img_41.png)

* Json mal
![img_3.png](src/main/resources/capturasDeFuncionamientoTareas/img_42.png)

* La tarea no existe
![img_4.png](src/main/resources/capturasDeFuncionamientoTareas/img_43.png)

- ## Completar una tarea
- Necesitas un jwt, la tarea tiene que estar asignada al que realiza la peticion, la tarea tiene que estar por acabar y que la tarea exista

* Caso correcto
![img_23.png](src/main/resources/capturasDeFuncionamientoTareas/img_23.png)

* No hay autorizacion
![img_24.png](src/main/resources/capturasDeFuncionamientoTareas/img_24.png)

* No tienes la tarea asignada
![img_25.png](src/main/resources/capturasDeFuncionamientoTareas/img_25.png)

* No existe la tarea
![img_26.png](src/main/resources/capturasDeFuncionamientoTareas/img_26.png)

* La tarea esta asignada a otro usuario
![img_27.png](src/main/resources/capturasDeFuncionamientoTareas/img_27.png)

* La tarea ya esta acabada
![img_28.png](src/main/resources/capturasDeFuncionamientoTareas/img_28.png)

- ## Desmarcar una tarea
- Necesitas un jwt, la tarea tiene que estar asignada al que realiza la peticion, la tarea tiene que estar acabada y que la tarea exista

* Caso correcto
![img_29.png](src/main/resources/capturasDeFuncionamientoTareas/img_29.png)

* No hay autorizacion
![img_30.png](src/main/resources/capturasDeFuncionamientoTareas/img_30.png)

* No tienes la tarea asignada
![img_31.png](src/main/resources/capturasDeFuncionamientoTareas/img_31.png)

* La tarea no existe
![img_34.png](src/main/resources/capturasDeFuncionamientoTareas/img_34.png)

* La tarea esta asignada a otro usuario
![img_32.png](src/main/resources/capturasDeFuncionamientoTareas/img_32.png)

* La tarea no estaba acabada 
![img_33.png](src/main/resources/capturasDeFuncionamientoTareas/img_33.png)

- ## Eliminar una tarea
- Necesitas un jwt y ser admin o el que tiene la tarea asignada

* Caso correcto
![img_35.png](src/main/resources/capturasDeFuncionamientoTareas/img_35.png)

* No hay autorizacion
![img_37.png](src/main/resources/capturasDeFuncionamientoTareas/img_37.png)

* No existe la tarea
![img_36.png](src/main/resources/capturasDeFuncionamientoTareas/img_36.png)

* No eres el que tiene la tarea asignada
![img_38.png](src/main/resources/capturasDeFuncionamientoTareas/img_38.png)

# FUNCIONAMIENTO DE LA INTERFAZ GRAFICA
* https://drive.google.com/file/d/1TTjsL4PGNh62h9qsk5H8ae1lAs3CoDaZ/view?usp=sharing