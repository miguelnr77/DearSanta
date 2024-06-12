# DearSanta

DearSanta es una aplicación web para gestionar listas de regalos, ideal para ocasiones especiales como Navidad, Reyes, etc.

## Herramientas y Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación utilizado para desarrollar la aplicación.
- **Spring Boot 3**: Framework para crear aplicaciones basadas en Spring.
- **Vaadin 24**: Framework para desarrollar interfaces de usuario web en Java.
- **MySQL**: Base de datos relacional utilizada para almacenar los datos.
- **phpMyAdmin**: Herramienta para la gestión de la base de datos MySQL.
- **IntelliJ IDEA**: Entorno de desarrollo integrado (IDE) utilizado para desarrollar la aplicación.
- **Git**: Sistema de control de versiones.
- **GitHub**: Plataforma para alojar el repositorio del proyecto.

## Configuración del Entorno de Desarrollo

### Requisitos Previos

- **Java 17+**: [Descargar e instalar](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Spring Boot 3+**: [Documentación oficial](https://spring.io/projects/spring-boot)
- **Vaadin 24+**: [Documentación oficial](https://vaadin.com/docs)
- **MySQL**: [Descargar e instalar](https://dev.mysql.com/downloads/mysql/)
- **phpMyAdmin**: [Descargar e instalar](https://www.phpmyadmin.net/)
- **IntelliJ IDEA**: [Descargar e instalar](https://www.jetbrains.com/idea/download/)
- **Git**: [Descargar e instalar](https://git-scm.com/)

### Configuración del Proyecto

1. **Clonar el Repositorio:**

    ```bash
    git clone https://github.com/miguelnr77/DearSanta.git
    cd DearSanta
    ```

2. **Configurar la Base de Datos:**

    - Crear una base de datos en MySQL llamada `dearsantadb`.
    - Configurar las credenciales de la base de datos en el archivo `application.properties`.

3. **Ejecución del Proyecto:**

    - Importar el proyecto en IntelliJ IDEA.
    - Ejecutar la aplicación desde la clase principal `DearSantaApplication`.

## Cambios Recientes

### Registro de Usuarios

- Implementado el registro de usuarios con confirmación por correo electrónico.
- Validación para evitar el registro de usuarios con correos electrónicos duplicados.
- Configuración de los tokens de verificación para la activación de cuentas.

### Vistas

- **MainView**: Página principal con opciones de navegación.
- **RegisterView**: Página de registro de usuarios con formulario de registro.
- **AboutView**: Página "Acerca de".
- **ContactView**: Página de "Contacto".

### Estilo y Diseño

- Aplicación de estilos CSS personalizados para una apariencia navideña.
- Ajustes de diseño para que el formulario de registro se adapte a la estética general de la página.

## Rutas Principales

- `/` - Página principal (MainView)
- `/register` - Página de registro (RegisterView)
- `/about` - Página "Acerca de" (AboutView)
- `/contact` - Página de "Contacto" (ContactView)

## Contribuciones

Las contribuciones son bienvenidas. Para contribuir, por favor, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza los cambios necesarios y confirma tus cambios (`git commit -am 'Añadir nueva funcionalidad'`).
4. Empuja los cambios a tu rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Licencia

Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para más detalles.
