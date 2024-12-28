# MyTasks - API Backend

Este repositorio contiene el backend de **MyTasks**, un sistema de gestión de tareas desarrollado con **Spring Boot**. La API sigue una arquitectura RESTful, permitiendo la creación, actualización, visualización y eliminación de tareas. El frontend de este sistema está desarrollado con **React.js** y se encuentra en otro repositorio: [MyTasks Frontend](https://github.com/patinodeveloper/MyTasks-FrontEnd.git).

## Módulos del Sistema

La API gestiona los siguientes módulos:

- **Tareas**: Permite la gestión de tareas, incluyendo creación, actualización, eliminación y listado.
- **Proyectos**: Permite la gestión de los proyectos, permitiendo agregar mas de una tarea a cada proyecto.
- **Notas** (en desarrollo): Permitirá agregar notas adicionales a cada tarea.

## Características Técnicas

- **Spring Boot**: Utilizado para desarrollar la API con arquitectura REST.
- **JPA y Hibernate**: Para la interacción con la base de datos relacional (MySQL).
- **MySQL**: Base de datos utilizada para almacenar la información de las tareas.
- **Dependencias**: Uso de dependencias esenciales de Spring, como Spring Web, Lombok, Spring Data JPA, y MySQL Driver.
- **Buenas prácticas**: Código estructurado siguiendo principios de diseño limpio y mantenibilidad.

## Diagrama de Componentes

A continuación se muestra un diagrama de componentes que ilustra cómo interactúan las partes principales del sistema:

![Diagrama de Componentes](https://res.cloudinary.com/dtncgfvxw/image/upload/v1735357055/Diagrama_de_Componentes_-_MyTasks_pjxv0x.png)

### Estructura de las Rutas

- **Base de la ruta**: `/mytasks/api/v1/`
  - Todas las rutas comienzan con este prefijo común.
  - Los módulos específicos como `tasks` se agregan para formar la ruta completa.

## Instalación

Sigue estos pasos para instalar y ejecutar la API en tu máquina local:

1. Clona el repositorio:
   ```bash
   https://github.com/patinodeveloper/MyTasks-API.git

2. Configura tu base de datos MySQL y asegura que las credenciales estén correctas en el archivo ```application.properties```.

3. Ejecuta la aplicación usando Maven o el IDE de tu preferencia (yo utilicé IntelliJ):
   ```bash
   mvn spring-boot:run

4. Abre Postman para acceder a la API y realizar peticiones HTTP.
