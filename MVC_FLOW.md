# Flujo MVC - Mi Perfil Dev

Explicación completa del patrón Model-View-Controller implementado en la aplicación.

## 1. Vista (View)

La vista es el frontend que el usuario ve y con el que interactúa.

### Componentes:

- **index.html** - Formulario y diseño de interfaz
- **styles.css** - Estilos y maquetación responsiva
- **main.js** - Lógica de la interfaz de usuario
- **api.js** - Cliente HTTP para comunicarse con el backend

### Flujo de la Vista:

1. Usuario carga la página en el navegador
2. Se descarga HTML, CSS y JavaScript
3. JavaScript hace una petición GET a /habilidades para cargar datos
4. Se muestran los datos en la interfaz
5. Usuario interactúa con formularios y botones
6. Se envían peticiones HTTP (POST, PUT, DELETE) al servidor

### Archivos en el Proyecto:

```
src/main/webapp/
├── index.html           # Estructura HTML y formulario
├── error.html           # Página de error
├── css/
│   └── styles.css       # Estilos CSS
└── js/
    ├── api.js           # Funciones para llamar a la API
    └── main.js          # Lógica de la aplicación
```

---

## 2. Controlador (Controller)

El controlador procesa las peticiones HTTP y coordina entre vista y modelo.

### Clases:

- **perfilController.java** - Servlet que maneja /perfil
- **habilidadController.java** - Servlet que maneja /habilidades

### Responsabilidades:

1. Recibe peticiones HTTP (GET, POST, PUT, DELETE)
2. Parsea parámetros y cuerpo de la petición
3. Llama a los métodos del DAO
4. Prepara respuestas en formato JSON
5. Envía respuestas al cliente
6. Maneja errores y excepciones

### Endpoints:

#### perfilController (/perfil)

```java
GET    /perfil          # Obtener perfil actual
PUT    /perfil          # Actualizar perfil
DELETE /perfil          # Eliminar perfil
```

#### habilidadController (/habilidades)

```java
GET    /habilidades     # Obtener todas las habilidades
GET    /habilidades?id=UUID  # Obtener habilidad específica
POST   /habilidades     # Crear nueva habilidad
PUT    /habilidades     # Actualizar habilidad
DELETE /habilidades?id=UUID  # Eliminar habilidad
```

### Ejemplo de Flujo:

```
Usuario hace clic en "Agregar" 
    ↓
main.js captura evento del formulario
    ↓
Valida datos
    ↓
Envía POST a /habilidades con JSON:
{
  "nombre": "Java",
  "nivel": "Avanzado",
  "experienciaAnios": 5,
  "descripcion": "Desarrollo backend"
}
    ↓
habilidadController recibe petición
    ↓
Extrae datos del body de la petición
    ↓
Crea objeto Habilidad
    ↓
Llama a habilidadDAO.agregarHabilidad(habilidad)
    ↓
Recibe respuesta del DAO (con ID generado)
    ↓
Formatea respuesta JSON:
{
  "success": true,
  "message": "Habilidad creada correctamente",
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
    ↓
Envía respuesta HTTP 201 al cliente
    ↓
main.js recibe respuesta
    ↓
Actualiza la pantalla con la nueva habilidad
    ↓
Usuario ve el cambio en tiempo real
```

### Archivos en el Proyecto:

```
src/main/java/com/mycompany/mi_perfil_dev/controller/
├── perfilController.java       # Servlet para perfil
└── habilidadController.java    # Servlet para habilidades
```

---

## 3. Modelo (Model)

El modelo representa la estructura de datos de la aplicación.

### Clases de Datos:

- **Perfil.java** - Representa un perfil con nombre, bio, experiencia, contacto, foto y lista de habilidades
- **Habilidad.java** - Representa una habilidad con id, nombre, nivel, años de experiencia y descripción

### Estructura de Perfil:

```java
public class Perfil {
    private String nombre;           // Nombre de la persona
    private String bio;              // Biografía o descripción
    private String experiencia;      // Años de experiencia
    private String contacto;         // Email o teléfono
    private String fotoPerfil;       // URL de la foto
    private List<Habilidad> habilidades;  // Lista de habilidades
    
    // Getters y setters
}
```

### Estructura de Habilidad:

```java
public class Habilidad {
    private String id;               // UUID único
    private String nombre;           // Nombre de la habilidad
    private String nivel;            // Nivel (Principiante, Intermedio, Avanzado)
    private int experienciaAnios;    // Años de experiencia
    private String descripcion;      // Descripción detallada
    
    // Getters y setters
}
```

### Características:

- Clases POJO (Plain Old Java Object)
- Getters y setters para acceso a propiedades
- Serialización automática a/desde JSON mediante GSON
- Uso de UUID para identificadores únicos de habilidades
- Compatible con anotaciones JSON (si es necesario)

### Datos en JSON:

```json
{
  "nombre": "Juan Pérez",
  "bio": "Desarrollador Full Stack",
  "experiencia": "5 años",
  "contacto": "juan@example.com",
  "fotoPerfil": "https://ejemplo.com/foto.jpg",
  "habilidades": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "nombre": "Java",
      "nivel": "Avanzado",
      "experienciaAnios": 5,
      "descripcion": "Desarrollo backend con Spring"
    },
    {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "nombre": "JavaScript",
      "nivel": "Avanzado",
      "experienciaAnios": 4,
      "descripcion": "Frontend y Node.js"
    }
  ]
}
```

### Archivos en el Proyecto:

```
src/main/java/com/mycompany/mi_perfil_dev/model/
├── Perfil.java       # Clase modelo para perfil
└── Habilidad.java    # Clase modelo para habilidad
```

---

## 4. Capa de Acceso a Datos (Data Access Object)

Los DAOs manejan la persistencia de datos en archivos JSON.

### Clases:

- **perfilDAO.java** - Operaciones CRUD para Perfil
- **habilidadDAO.java** - Operaciones CRUD para Habilidades dentro de un Perfil

### Responsabilidades:

1. Leer archivos JSON desde disco
2. Deserializar JSON a objetos Java
3. Modificar datos en memoria
4. Serializar objetos a JSON
5. Escribir archivos JSON al disco
6. Crear directorios si no existen

### Métodos de perfilDAO:

```java
public void guardarPerfil(Perfil perfil)         // Guardar perfil
public Perfil obtenerPerfil()                     // Obtener perfil
public void actualizarPerfil(Perfil perfil)      // Actualizar perfil
public void eliminarPerfil()                      // Eliminar perfil
public boolean existePerfil()                     // Verificar si existe
```

### Métodos de habilidadDAO:

```java
public void agregarHabilidad(Habilidad habilidad)           // Agregar habilidad
public List<Habilidad> obtenerTodasLasHabilidades()         // Obtener todas
public Habilidad obtenerHabilidadPorId(String id)           // Obtener por ID
public void actualizarHabilidad(Habilidad habilidad)         // Actualizar
public void eliminarHabilidad(String id)                     // Eliminar por ID
public boolean existeHabilidad(String id)                    // Verificar si existe
public Habilidad buscarHabilidad(String nombre)              // Buscar por nombre
```

### Flujo de Persistencia:

```
1. Aplicación llama a habilidadDAO.agregarHabilidad(habilidad)
        ↓
2. DAO obtiene perfil actual: Perfil p = obtenerPerfil()
        ↓
3. DAO lee data/perfil.json
        ↓
4. DAO parsea JSON a objeto Perfil usando GSON
        ↓
5. DAO agrega habilidad a la lista: p.getHabilidades().add(habilidad)
        ↓
6. DAO serializa Perfil a JSON usando GSON
        ↓
7. DAO escribe JSON a data/perfil.json
        ↓
8. DAO retorna o lanza excepción
```

### Estructura de Archivo de Datos:

```
data/
└── perfil.json    # Contiene todo el perfil y sus habilidades
```

### Archivos en el Proyecto:

```
src/main/java/com/mycompany/mi_perfil_dev/dao/
├── perfilDAO.java      # DAO para perfil
└── habilidadDAO.java   # DAO para habilidades
```

---

## Diagrama de Flujo Completo

### Petición GET (Obtener Habilidades):

```
┌─────────────────┐
│   Navegador     │
│   (Cliente)     │
└────────┬────────┘
         │
         │ 1. Usuario carga página
         │ main.js ejecuta onload()
         │
         v
┌──────────────────────┐
│   api.js             │
│ (Cliente HTTP)       │
└────────┬─────────────┘
         │
         │ 2. Llamada: fetch('/habilidades')
         │ GET /habilidades
         │
         v
┌────────────────────────────────┐
│  habilidadController.java      │
│  (Servlet - /habilidades)      │
└────────┬───────────────────────┘
         │
         │ 3. doGet() recibe petición
         │
         v
┌────────────────────────────────┐
│  habilidadDAO.java             │
│  (Acceso a datos)              │
└────────┬───────────────────────┘
         │
         │ 4. obtenerTodasLasHabilidades()
         │
         v
┌────────────────────────────────┐
│  data/perfil.json              │
│  (Archivo de datos)            │
└────────┬───────────────────────┘
         │
         │ 5. Lee archivo JSON
         │
         v
┌────────────────────────────────┐
│  GSON Library                  │
│  (Serialización JSON)          │
└────────┬───────────────────────┘
         │
         │ 6. Deserializa JSON a List<Habilidad>
         │
         v
┌────────────────────────────────┐
│  habilidadDAO.java             │
└────────┬───────────────────────┘
         │
         │ 7. Retorna List<Habilidad>
         │
         v
┌────────────────────────────────┐
│  habilidadController.java      │
└────────┬───────────────────────┘
         │
         │ 8. Serializa a JSON
         │ Configura headers
         │ Escribe respuesta HTTP
         │
         v
┌──────────────────────┐
│   api.js             │
│ (Response)           │
└────────┬─────────────┘
         │
         │ 9. Respuesta JSON recibida
         │
         v
┌──────────────────────┐
│   main.js            │
│ (Parsea JSON)        │
└────────┬─────────────┘
         │
         │ 10. Actualiza DOM
         │ Renderiza habilidades
         │
         v
┌─────────────────┐
│   Navegador     │
│  (Pantalla)     │
└─────────────────┘
      Usuario ve
    las habilidades
```

### Petición POST (Crear Habilidad):

```
┌─────────────────┐
│   Navegador     │
│   Usuario       │
└────────┬────────┘
         │
         │ 1. Completa formulario
         │ Hace clic en "Agregar"
         │
         v
┌──────────────────────┐
│   main.js            │
│ (Valida formulario)  │
└────────┬─────────────┘
         │
         │ 2. Crea objeto JSON:
         │ {
         │   "nombre": "Python",
         │   "nivel": "Intermedio",
         │   ...
         │ }
         │
         v
┌──────────────────────┐
│   api.js             │
│ (Cliente HTTP)       │
└────────┬─────────────┘
         │
         │ 3. Llamada: fetch('/habilidades', {
         │   method: 'POST',
         │   body: JSON.stringify(habilidad)
         │ })
         │
         v
┌────────────────────────────────┐
│  habilidadController.java      │
│  (Servlet - /habilidades)      │
└────────┬───────────────────────┘
         │
         │ 4. doPost() recibe petición
         │ Lee body de la petición
         │
         v
┌──────────────────────────────┐
│  GSON Library                │
│  (Deserialización JSON)      │
└────────┬─────────────────────┘
         │
         │ 5. Parsea JSON a Habilidad
         │
         v
┌────────────────────────────────┐
│  habilidadController.java      │
└────────┬───────────────────────┘
         │
         │ 6. Genera UUID para ID
         │ habilidad.setId(UUID.random())
         │
         v
┌────────────────────────────────┐
│  habilidadDAO.java             │
└────────┬───────────────────────┘
         │
         │ 7. agregarHabilidad(habilidad)
         │
         v
┌────────────────────────────────┐
│  data/perfil.json              │
│  (Lee archivo)                 │
└────────┬───────────────────────┘
         │
         v
┌────────────────────────────────┐
│  GSON Library                  │
│  (Deserializa)                 │
└────────┬───────────────────────┘
         │
         │ 8. JSON → Perfil object
         │
         v
┌────────────────────────────────┐
│  habilidadDAO.java             │
└────────┬───────────────────────┘
         │
         │ 9. Agrega habilidad:
         │ perfil.getHabilidades()
         │       .add(habilidad)
         │
         v
┌────────────────────────────────┐
│  GSON Library                  │
│  (Serializa)                   │
└────────┬───────────────────────┘
         │
         │ 10. Perfil object → JSON
         │
         v
┌────────────────────────────────┐
│  data/perfil.json              │
│  (Escribe archivo)             │
└────────┬───────────────────────┘
         │
         v
┌────────────────────────────────┐
│  habilidadDAO.java             │
└────────┬───────────────────────┘
         │
         │ 11. Retorna éxito
         │
         v
┌────────────────────────────────┐
│  habilidadController.java      │
└────────┬───────────────────────┘
         │
         │ 12. Crea respuesta JSON:
         │ {
         │   "success": true,
         │   "message": "Creada",
         │   "id": "550e8400..."
         │ }
         │
         v
┌──────────────────────┐
│   api.js             │
│ (Response)           │
└────────┬─────────────┘
         │
         │ 13. Respuesta recibida
         │
         v
┌──────────────────────┐
│   main.js            │
│ (Actualiza)          │
└────────┬─────────────┘
         │
         │ 14. Limpia formulario
         │ Llama GET /habilidades
         │ Renderiza nueva lista
         │
         v
┌─────────────────┐
│   Navegador     │
│  (Pantalla)     │
└─────────────────┘
  Usuario ve nueva
    habilidad
    agregada
```

---

## Resumen del Flujo MVC

| Componente | Responsabilidad | Tecnología |
|-----------|-----------------|-----------|
| **Vista** | Interfaz usuario, eventos | HTML5, CSS3, JavaScript, DOM |
| **Controlador** | Procesar peticiones, coordinar | Java Servlets, HTTP |
| **Modelo** | Estructura datos | Java POJOs |
| **DAO** | Persistencia datos | GSON, Archivos JSON |
| **Servidor** | Ejecutar servlets | Apache Tomcat |

---

## Ventajas de esta Arquitectura

1. **Separación de responsabilidades** - Cada capa tiene un propósito definido
2. **Mantenibilidad** - Fácil de entender y modificar
3. **Escalabilidad** - Se puede agregar más funcionalidad sin afectar otras capas
4. **Testabilidad** - Se puede testear cada componente independientemente
5. **Reutilización** - DAOs y modelos pueden usarse en diferentes controladores
6. **Flexibilidad** - Se puede cambiar el formato de persistencia (JSON a BD) solo en el DAO
