# Mi Perfil Dev - Portafolio de Habilidades de Programación

Una aplicación web para crear y gestionar tu portafolio de habilidades de programación.

## Características

- Patrón MVC - Arquitectura limpia y escalable
- API REST - Endpoints funcionales para perfil y habilidades
- Frontend Responsivo - Interfaz moderna con HTML5, CSS3 y JavaScript
- Persistencia JSON - Almacenamiento de datos en archivos JSON
- CRUD Completo - Crear, leer, actualizar y eliminar habilidades
- Servlets Java - Manejo de peticiones HTTP
- Apache Tomcat - Desplegable en servidor web

## Tecnologías

| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| Java | 11+ | Backend |
| Servlets | 4.0 | Controladores HTTP |
| GSON | 2.10.1 | Serialización JSON |
| Maven | 3.6+ | Build & Dependency Management |
| Apache Tomcat | 9.0+ | Servidor Web |
| HTML5 / CSS3 / JS | ES6+ | Frontend |

## Requisitos Previos

- Java 11 o superior - https://www.oracle.com/java/technologies/downloads/
- Maven 3.6 o superior - https://maven.apache.org/download.cgi
- Apache Tomcat 9.0+ (solo para despliegue) - https://tomcat.apache.org/download-90.cgi

Verifica la instalación:
```bash
java -version
mvn -v
```

## Inicio Rápido

### 1. Compilar el Proyecto

```bash
cd Mi_Perfil_Dev
.\build.bat
```

Esto generará un archivo WAR en target/mi-perfil-dev.war

### 2. Ejecutar en Desarrollo

```bash
.\run.bat
```

La aplicación estará disponible en:
```
http://localhost:8080/mi-perfil-dev
```

### 3. Detener el Servidor

Presiona Ctrl + C en la terminal

## Documentación Adicional

Para información más detallada, consulta:

- **MVC_FLOW.md** - Explicación completa del patrón MVC con diagramas de flujo detallados
- **EXECUTION_GUIDE.md** - Guía exhaustiva de ejecución con 4 opciones diferentes y solución de problemas

## Estructura del Proyecto

```
Mi_Perfil_Dev/
│
├── pom.xml                          # Configuración Maven
├── build.bat                        # Script de compilación
├── run.bat                          # Script para ejecutar
│
├── src/
│   ├── main/
│   │   ├── java/com/mycompany/mi_perfil_dev/
│   │   │   ├── controller/
│   │   │   │   ├── perfilController.java      # Servlet para perfil
│   │   │   │   └── habilidadController.java   # Servlet para habilidades
│   │   │   ├── dao/
│   │   │   │   ├── perfilDAO.java             # Acceso a datos - Perfil
│   │   │   │   └── habilidadDAO.java          # Acceso a datos - Habilidades
│   │   │   ├── model/
│   │   │   │   ├── Perfil.java                # Modelo de perfil
│   │   │   │   └── Habilidad.java             # Modelo de habilidad
│   │   │   └── util/
│   │   │       └── JsonUtil.java              # Utilidades JSON
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml                    # Descriptor de despliegue
│   │       ├── index.html                     # Página de inicio
│   │       ├── error.html                     # Página de error
│   │       ├── css/
│   │       │   └── styles.css                 # Estilos
│   │       └── js/
│   │           ├── api.js                     # Cliente API
│   │           └── main.js                    # Lógica UI
│   │
│   └── test/
│       └── java/                              # Tests unitarios
│
├── target/
│   └── mi-perfil-dev.war                      # Archivo desplegable
│
└── data/
    └── perfil.json                            # Base de datos JSON
```

## Endpoints API

### Perfil

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /perfil | Obtener perfil actual |
| PUT | /perfil | Actualizar perfil |
| DELETE | /perfil | Eliminar perfil |

### Habilidades

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /habilidades | Obtener todas las habilidades |
| GET | /habilidades?id={id} | Obtener habilidad específica |
| POST | /habilidades | Crear nueva habilidad |
| PUT | /habilidades | Actualizar habilidad |
| DELETE | /habilidades?id={id} | Eliminar habilidad |

## Estructura de Datos JSON

### Perfil
```json
{
  "nombre": "Juan Pérez",
  "bio": "Desarrollador Full Stack",
  "experiencia": "5 años",
  "contacto": "juan@example.com",
  "fotoPerfil": "ruta/imagen.jpg",
  "habilidades": [...]
}
```

### Habilidad
```json
{
  "id": "uuid-xxxxx",
  "nombre": "Java",
  "nivel": "Avanzado",
  "experienciaAnios": 5,
  "descripcion": "Desarrollo backend con Spring"
}
```

## Ejemplos de Uso

### Crear una Habilidad

Request:
```bash
curl -X POST http://localhost:8080/mi-perfil-dev/habilidades \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Python",
    "nivel": "Intermedio",
    "experienciaAnios": 3,
    "descripcion": "Data Science y Automatización"
  }'
```

Response:
```json
{
  "success": true,
  "message": "Habilidad creada correctamente",
  "id": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Obtener Habilidades

```bash
curl http://localhost:8080/mi-perfil-dev/habilidades
```

Response:
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "nombre": "Java",
    "nivel": "Avanzado",
    "experienciaAnios": 5,
    "descripcion": "Desarrollo backend"
  },
  {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "nombre": "JavaScript",
    "nivel": "Avanzado",
    "experienciaAnios": 4,
    "descripcion": "Frontend y Node.js"
  }
]
```

## Despliegue en Producción

### Opción 1: Manual en Tomcat

```bash
# 1. Compilar
mvn clean package

# 2. Copiar WAR a Tomcat
cp target/mi-perfil-dev.war $TOMCAT_HOME/webapps/

# 3. Reiniciar Tomcat
$TOMCAT_HOME/bin/startup.sh (Linux/Mac)
$TOMCAT_HOME\bin\startup.bat (Windows)

# 4. Acceder
http://localhost:8080/mi-perfil-dev
```

### Opción 2: Con Docker

```dockerfile
FROM tomcat:9.0
COPY target/mi-perfil-dev.war $CATALINA_HOME/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
```

### Opción 3: Servidor Remoto

```bash
# Compilar localmente
mvn clean package

# Transferir
scp target/mi-perfil-dev.war usuario@servidor:/opt/tomcat/webapps/

# En el servidor
sudo systemctl restart tomcat
tail -f /opt/tomcat/logs/catalina.out
```

## Solución de Problemas

### Error: "mvn" no se reconoce

```bash
# Verificar si Maven está instalado
mvn -v

# Si no está, agrega Maven al PATH:
# Windows: Busca "Variables de entorno" → Editar → Agregar $MAVEN_HOME/bin al PATH
# Linux/Mac: export PATH=$PATH:$MAVEN_HOME/bin
```

### Puerto 8080 en uso

```bash
# Windows - Encuentra el proceso
netstat -ano | findstr :8080

# Linux/Mac - Busca el proceso
lsof -i :8080

# Cambia el puerto en TOMCAT_HOME/conf/server.xml
<Connector port="8081" protocol="HTTP/1.1" ... />
```

### Error 404 en la aplicación

- Verifica que accedas a http://localhost:8080/mi-perfil-dev (con el nombre de la app)
- Revisa que el WAR se haya desplegado correctamente en webapps/
- Comprueba los logs de Tomcat en TOMCAT_HOME/logs/catalina.out

## Comandos Maven Útiles

```bash
# Compilar
mvn clean compile

# Generar WAR
mvn clean package

# Ejecutar tests
mvn test

# Ejecutar con Tomcat embebido
mvn tomcat7:run

# Ver dependencias
mvn dependency:tree

# Limpiar archivos compilados
mvn clean

# Descargar todas las dependencias
mvn dependency:resolve
```

## Notas de Seguridad

- Los datos se almacenan localmente en data/perfil.json
- Para producción, considera usar una base de datos real (MySQL, PostgreSQL, etc.)
- Implementa autenticación y autorización
- Valida y sanitiza todos los inputs
- Usa HTTPS en producción

## Recursos Adicionales

- Apache Tomcat Documentation: https://tomcat.apache.org/tomcat-9.0-doc/
- Maven Documentation: https://maven.apache.org/
- Java Servlets Tutorial: https://docs.oracle.com/cd/E24329_01/web.1211/e24437/toc.htm
- GSON Library: https://github.com/google/gson
- RESTful Web Services: https://www.oreilly.com/library/view/restful-web-services/9780596529260/

## Licencia

Este proyecto está bajo licencia MIT. Ver archivo LICENSE para más detalles.

## Contribuir

Las contribuciones son bienvenidas. Para cambios importantes:

1. Fork el repositorio
2. Crea una rama (git checkout -b feature/AmazingFeature)
3. Commit tus cambios (git commit -m 'Add some AmazingFeature')
4. Push a la rama (git push origin feature/AmazingFeature)
5. Abre un Pull Request

## Soporte

Si tienes preguntas o problemas:
- Abre un issue en GitHub
- Revisa la sección de troubleshooting
- Verifica los logs de Tomcat
