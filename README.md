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

## Características
- Registro de usuarios con verificación por correo electrónico
- Inicio y cierre seguro de sesión
- Mantenimiento de allegados
- Gestión de listas de regalos
- Edición de listas de regalos

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

## Estructura del proyecto

    Para la correcta realización del proyecto, he organizado el mismo mediante carpetas dedicadas a cada 'Clase' o 'Entidad' quedando así 4: user, relatives, gift y list las cuales representan a Usuarios, Allegados, Listas de regalos y Regalos. 

    A su vez, cada una de ellas cuentan con una estructura de carpetas organizadas de la siguiente forma: models, repositories, services, views. En cada carpeta almacenamos los archivos correspondientes a vistas, modelos, repositorios o servicios de cada entidad.

    Tambien tenemos una carpeta Views en donde almacenamos la vista principal de la aplicación (Página de inicio) así como otras vistas tales como las correspondientes a AcercaDe o Contacto.

    En la carpeta frontend/styles encontramos el archivo CSS que le brinda estilo a la aplicación así como la carpeta images que contienen imagenes empleadas en la aplicación.

## Planificación del proyecto

    Para la planificación, dividi el trabajo a realizar en días de la siguiente forma:

    Semana 1 (del 7 al 11 de junio)
        - 7 de junio - Configuración del entorno de desarrollo y repositorio
        - 8 de junio - Diseño del modelo de datos
        - 9 de junio - Diseño de la arquitectura de la aplicación
        - 10 de junio - Registro de usuarios
        - 11 de junio - Inicio y cierre de sesión

    Semana 2 (del 12 al 18 de junio)
        - 12 de junio - Mantenimiento de allegados
        - 13 de junio - Gestión de listas de regalos
        - 14 de junio - Edición de listas de regalos
        - 15 de junio - Seguridad y pruebas
        - 16 de junio - Documentación y despliegue
        - 17 de junio - Revisión y ajustes finales
        - 18 de junio - Entrega

Aunque los plazos han variado un poco, a sido el camino a seguir durante todo este tiempo de implementación del proyecto.

## Funcionalidades a cubrir

### Registro de Usuarios

    - Los usuarios deben ser capaces de registrarse en la aplicación introduciendo los datos en el formulario de registro.
    - Una vez registrados, recibirán un correo con un enlace de confirmación.
    - Haciendo click en el enlace, activarán su cuenta.

### Inicio y cierre de sesión

    - Una vez registrados, los usuarios podran iniciar sesión introduciendo sus credenciales.
    - Cuando deseen podran cerrar la sesión
    - Es importante activar la cuenta previamente para poder iniciar sesión.

### Mantenimiento de Allegados

    - Los usuarios podrán añadir allegados introduciendo para cada uno su nombre.
    - También tendran opcion de eliminarlos y/o modificarlos.

### Mis Listas

    - Se ueden crear listas introduciendo su nombre.
    - También existe la posibilidad de modificar el nombre de las listas y/o borrarlas.

### Editar Regalos

    - Una vez son creadas las listas, los usuarios pueden añadir regalos a ellas.
    - Deberán cumplir con el formulario de inclusión de regalos y estos se guardaran en la lista.
    - También tendran opción de eliminarlos y/o modificarlos.


## Estilo y Diseño

En cuanto al diseño, me he dejado llevar por la mágica Navidad dandole una apariencia navideña a la página. Aunque esta destinado a cualquier momento, la Navidad es aquella fecha en la cual se nos acumulan los regalos y tener una aplicación que nos ayude a organizarlos es genial.

Se han aplicado estilos mediante css para darle esa apariencia.

## Proceso de creación

### Diagramas de casos de uso 

Acontinuación se incluyen los diagramas de caso de uso de la aplicación:

#### Alto nivel

![Caso de uso](https://github.com/miguelnr77/DearSanta/blob/master/docs/caso%20de%20uso_altonivel.JPG)

#### Allegados

![Caso de uso](https://github.com/miguelnr77/DearSanta/blob/master/docs/casodeuso_allegados.JPG)

#### Listas

![Caso de uso](https://github.com/miguelnr77/DearSanta/blob/master/docs/casodeuso_listas.JPG)

#### Regalos

![Caso de uso](https://github.com/miguelnr77/DearSanta/blob/master/docs/casodeuso_regalos.JPG)

### Modelos C4

A continuación e incluyen los modelos C4:

#### Contexto

![C4](https://github.com/miguelnr77/DearSanta/blob/master/docs/c4context.JPG)

### Containers

![C4](https://github.com/miguelnr77/DearSanta/blob/master/docs/c4containers.JPG)

### Modelo de clases

También a continuación se incluye el modelo de clases de la aplicación:

![Modelo de clases](https://github.com/miguelnr77/DearSanta/blob/master/docs/modeloclases.png)