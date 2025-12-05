# Instrucciones de Ejecución - Mi Perfil Dev

Guía completa para compilar, ejecutar y desplegar la aplicación.

---

## Opción 1: Script de Compilación y Ejecución (Recomendado)

La forma más simple de compilar y ejecutar la aplicación. Esta es la forma recomendada para desarrollo local.

### Requisitos Previos:

- Java 11 o superior instalado
- Maven 3.6 o superior instalado (o será detectado automáticamente)
- PowerShell en Windows

### Paso 1: Abrir Terminal

Abre PowerShell en la carpeta del proyecto:

```powershell
cd C:\ruta\al\proyecto\Mi_Perfil_Dev
```

Verifica que estés en la carpeta correcta:

```powershell
dir *.bat
```

Deberías ver:
```
build.bat
run.bat
```

### Paso 2: Compilar el Proyecto

Ejecuta el script de compilación:

```powershell
.\build.bat
```

Este script:
- Busca Maven en el sistema
- Ejecuta limpieza (mvn clean)
- Compila código fuente (mvn compile)
- Ejecuta tests (mvn test)
- Empaqueta en WAR (mvn package)

Espera a que termine. Deberías ver mensajes como:

```
[INFO] Building jar: target/mi-perfil-dev.jar
[INFO] Building war: target/mi-perfil-dev.war
[INFO] BUILD SUCCESS
```

Si ves "BUILD SUCCESS", la compilación fue exitosa.

Si ves "BUILD FAILURE", hay un error. Verifica:
- Sintaxis Java en el código
- Configuración de pom.xml
- Dependencias Maven

### Paso 3: Ejecutar la Aplicación

Ejecuta el script de ejecución:

```powershell
.\run.bat
```

Este script:
- Busca Maven en el sistema
- Ejecuta Tomcat embebido (mvn tomcat7:run)
- Inicia servidor en puerto 8080
- Despliega la aplicación

Espera a que termine de iniciarse. Verás logs similares a:

```
[INFO] Started Tomcat Server
[INFO] The Server is running at http://localhost:8080/mi-perfil-dev
[INFO] Press any key to stop the server
```

O busca un mensaje como:

```
INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 5234 ms
```

### Paso 4: Acceder a la Aplicación

Abre tu navegador web y accede a:

```
http://localhost:8080/mi-perfil-dev
```

Deberías ver:
- Formulario para "Agregar Habilidad"
- Lista de habilidades (vacía inicialmente)
- Campos: Nombre, Nivel, Años de Experiencia, Descripción

### Paso 5: Prueba la Aplicación

1. Completa el formulario:
   - Nombre: Python
   - Nivel: Intermedio
   - Años de Experiencia: 3
   - Descripción: Data Science

2. Haz clic en "Agregar Habilidad"

3. Deberías ver:
   - La habilidad aparece en la lista
   - Se desplegó a data/perfil.json automáticamente

4. Intenta:
   - Editar una habilidad
   - Eliminar una habilidad
   - Recargar la página (los datos persisten)

### Paso 6: Detener la Aplicación

En la ventana de PowerShell donde corre el servidor, presiona:

```powershell
Ctrl + C
```

El servidor se detendrá:

```
Press any key to stop the server
```

Se verá:
```
[INFO] Tomcat Server was stopped
```

---

## Opción 2: Comandos Maven Directos

Si prefieres usar Maven directamente sin scripts. Úsalo si build.bat no funciona.

### Requisito:

Maven debe estar en tu PATH del sistema.

Verifica:
```bash
mvn -v
```

Si no está instalado, descárgalo desde: https://maven.apache.org/download.cgi

### Compilar Proyecto:

```bash
mvn clean compile
```

Limpia y compila el código. Genera archivos en target/classes/

### Generar WAR:

```bash
mvn clean package
```

Limpia, compila, testa y empaqueta. Genera target/mi-perfil-dev.war

### Ejecutar con Tomcat Embebido:

```bash
mvn tomcat7:run
```

Inicia Tomcat en puerto 8080 y despliega la app.

Accede a: http://localhost:8080/mi-perfil-dev

Detener: Ctrl + C

### Ver Dependencias:

```bash
mvn dependency:tree
```

Muestra todas las dependencias del proyecto.

### Ejecutar Tests:

```bash
mvn test
```

Ejecuta tests unitarios si existen.

### Limpiar Archivos Compilados:

```bash
mvn clean
```

Elimina la carpeta target/.

---

## Opción 3: Despliegue Manual en Tomcat Independiente

Para desplegar en una instalación de Tomcat separada (no embebido).

Úsalo para ambientes de producción o cuando quieras más control.

### Requisitos:

- Apache Tomcat 9.0+ descargado y configurado
- Variable TOMCAT_HOME configurada o ruta conocida

### Paso 1: Compilar el Proyecto

```bash
mvn clean package
```

Genera target/mi-perfil-dev.war

### Paso 2: Copiar WAR a Tomcat

En **Windows**:
```bash
copy target\mi-perfil-dev.war C:\apache-tomcat-9.0\webapps\
```

En **Linux/Mac**:
```bash
cp target/mi-perfil-dev.war /opt/tomcat/webapps/
```

O usando variable de entorno:
```bash
cp target/mi-perfil-dev.war $TOMCAT_HOME/webapps/
```

### Paso 3: Reiniciar Tomcat

En **Windows**:
```bash
C:\apache-tomcat-9.0\bin\startup.bat
```

En **Linux/Mac**:
```bash
/opt/tomcat/bin/startup.sh
```

Espera a ver mensajes de inicio.

### Paso 4: Verificar Despliegue

El archivo WAR debe extraerse automáticamente en:
```
$TOMCAT_HOME/webapps/mi-perfil-dev/
```

Verifica que existe:
```
$TOMCAT_HOME/webapps/mi-perfil-dev/index.html
```

### Paso 5: Acceder a la Aplicación

```
http://localhost:8080/mi-perfil-dev
```

### Paso 6: Ver Logs

En **Windows**:
```
C:\apache-tomcat-9.0\logs\catalina.out
```

En **Linux/Mac**:
```bash
tail -f /opt/tomcat/logs/catalina.out
```

### Paso 7: Detener Tomcat

En **Windows**:
```bash
C:\apache-tomcat-9.0\bin\shutdown.bat
```

En **Linux/Mac**:
```bash
/opt/tomcat/bin/shutdown.sh
```

---

## Opción 4: Docker (Avanzado)

Para containerizar la aplicación. Requiere Docker instalado.

### Paso 1: Compilar WAR

```bash
mvn clean package
```

### Paso 2: Crear Dockerfile

Crea archivo `Dockerfile` en raíz del proyecto:

```dockerfile
FROM tomcat:9.0-jdk11

# Copiar WAR a webapps
COPY target/mi-perfil-dev.war $CATALINA_HOME/webapps/

# Exponer puerto
EXPOSE 8080

# Iniciar Tomcat
CMD ["catalina.sh", "run"]
```

### Paso 3: Construir Imagen Docker

```bash
docker build -t mi-perfil-dev:1.0 .
```

### Paso 4: Ejecutar Contenedor

```bash
docker run -p 8080:8080 mi-perfil-dev:1.0
```

### Paso 5: Acceder

```
http://localhost:8080/mi-perfil-dev
```

### Paso 6: Detener Contenedor

```bash
docker ps                    # Ver contenedores activos
docker stop <container-id>   # Detener por ID
```

---

## Solución de Problemas de Ejecución

### Error: "El archivo build.bat no se encontrado"

**Problema:** No estás en la carpeta correcta.

**Solución:**

Verifica dónde estás:
```powershell
pwd
```

Verifica archivos en la carpeta:
```powershell
dir *.bat
```

Deberías ver:
```
build.bat
run.bat
```

Si no ves los archivos, navega a la carpeta correcta:
```powershell
cd C:\ruta\completa\Mi_Perfil_Dev
```

---

### Error: "mvn no se reconoce como comando interno"

**Problema:** Maven no está en el PATH del sistema.

**Solución Opción 1 - Agregar Maven al PATH:**

1. Descarga Maven desde: https://maven.apache.org/download.cgi
2. Extrae en carpeta, ej: C:\Program Files\apache-maven-3.9.0
3. Abre "Variables de entorno" en Windows:
   - Busca "Variables" en el menú Inicio
   - Haz clic en "Editar las variables de entorno del sistema"
   - Haz clic en "Variables de entorno"
4. En "Variables de usuario", haz clic en "Nueva"
5. Nombre: MAVEN_HOME
   Valor: C:\Program Files\apache-maven-3.9.0
6. En "Variables de sistema", edita "Path"
7. Agrega: %MAVEN_HOME%\bin
8. Haz clic OK en todo
9. Reinicia PowerShell
10. Verifica: mvn -v

**Solución Opción 2 - Usar ruta completa:**

```bash
C:\Program Files\apache-maven-3.9.0\bin\mvn clean package
```

**Solución Opción 3 - Usar script build.bat:**

El script build.bat busca Maven automáticamente:
```powershell
.\build.bat
```

---

### Error: "Puerto 8080 en uso"

**Problema:** Otra aplicación está usando el puerto 8080.

**Solución Opción 1 - Encontrar proceso en puerto:**

En **Windows**:
```powershell
netstat -ano | findstr :8080
```

Verás algo como:
```
TCP    0.0.0.0:8080    0.0.0.0:0    LISTENING    1234
```

Donde 1234 es el PID. Termina el proceso:
```powershell
taskkill /PID 1234 /F
```

En **Linux/Mac**:
```bash
lsof -i :8080
```

Termina el proceso:
```bash
kill -9 <PID>
```

**Solución Opción 2 - Cambiar el puerto:**

Edita `pom.xml`, busca:
```xml
<maven.tomcat.port>8080</maven.tomcat.port>
```

Cambia a:
```xml
<maven.tomcat.port>8081</maven.tomcat.port>
```

Luego accede a: http://localhost:8081/mi-perfil-dev

**Solución Opción 3 - Esperar:**

A veces el puerto queda ocupado temporalmente. Espera unos segundos e intenta de nuevo.

---

### Error: "Connection refused" en navegador

**Problema:** El servidor Tomcat no está corriendo.

**Solución:**

1. Verifica que ejecutaste .\run.bat o mvn tomcat7:run
2. En la terminal, busca mensaje: "Server startup in X ms"
3. Si no ves este mensaje, hay un error de inicio
4. Mira los logs para ver el error
5. Intenta de nuevo:

```powershell
.\run.bat
```

---

### Error: "404 Not Found" en navegador

**Problema:** URL incorrecta o aplicación no desplegada.

**Solución:**

Verifica que accedas a URL CORRECTA:

```
CORRECTA:  http://localhost:8080/mi-perfil-dev
INCORRECTA: http://localhost:8080
INCORRECTA: http://localhost:8080/
INCORRECTA: http://localhost:8080/mi-perfil-dev/
```

Nota: NO agregar barra final (/)

Si aún así da 404:

1. Verifica que WAR está en webapps/:
   - Para Tomcat independiente: $TOMCAT_HOME/webapps/mi-perfil-dev/
   - Tomcat embebido: target/tomcat/webapps/mi-perfil-dev/

2. Verifica logs:
   ```
   target/tomcat/logs/catalina.out
   ```

3. Reconstruye y redeploya:
   ```bash
   mvn clean package
   ```

---

### Error: "No se cargan las habilidades" (blank page)

**Problema:** API falla al obtener datos.

**Solución:**

1. Abre consola de navegador:
   - En Chrome/Firefox: F12
   - Ve a pestaña "Console"

2. Ve a pestaña "Network":
   - Recarga la página (F5)
   - Busca petición GET /habilidades
   - Si está en rojo, hay error

3. Haz clic en la petición:
   - Ve a "Response"
   - Mira el mensaje de error

4. Verifica archivo de datos:
   ```
   data/perfil.json
   ```
   
   Debe existir. Si no existe, crea uno manualmente:
   ```json
   {
     "nombre": "Tu Nombre",
     "bio": "Tu biografía",
     "experiencia": "0 años",
     "contacto": "tu@email.com",
     "fotoPerfil": "",
     "habilidades": []
   }
   ```

5. Verifica permisos:
   - Carpeta data/ debe existir
   - Debe tener permisos de lectura/escritura

6. Verifica logs de Tomcat:
   ```
   target/tomcat/logs/catalina.out
   ```

---

### Error: "No se guardan los datos" (agregar habilidad falla)

**Problema:** DAO no puede escribir archivo.

**Solución:**

1. Verifica que carpeta data/ existe:
   ```powershell
   Test-Path data\
   ```

   Si no existe, créala:
   ```powershell
   mkdir data
   ```

2. Verifica permisos de carpeta:
   - Carpeta data/ debe tener permisos de escritura
   - En Windows: Clic derecho > Propiedades > Seguridad

3. Verifica archivo data/perfil.json:
   - Debe ser JSON válido
   - Usa validador: https://jsonlint.com/

4. Busca error en logs:
   ```
   target/tomcat/logs/catalina.out
   ```
   
   Busca: IOException, FileNotFoundException, etc.

5. Intenta ejecutar como Administrador:
   - Abre PowerShell como Admin
   - Ejecuta build.bat y run.bat de nuevo

---

### Error: "Compilación falla con errores de sintaxis"

**Problema:** Error en código Java.

**Solución:**

1. Lee el error completo:
   - Muestra archivo: src/main/java/.../NombreClase.java
   - Muestra línea: [ERROR] line XX
   - Muestra tipo: syntax error, cannot find symbol, etc.

2. Abre el archivo indicado en VS Code

3. Ve a la línea indicada

4. Verifica:
   - Sintaxis correcta (; al final, {} balanceadas)
   - Importes faltantes
   - Variables declaradas
   - Tipos de datos correctos

5. Corrige el error

6. Intenta compilar de nuevo:
   ```bash
   mvn clean compile
   ```

---

### Error: "Cannot find package"

**Problema:** Dependencia no descargada o no en pom.xml.

**Solución:**

1. Verifica que pom.xml tiene la dependencia:
   ```xml
   <dependency>
     <groupId>com.google.code.gson</groupId>
     <artifactId>gson</artifactId>
     <version>2.10.1</version>
   </dependency>
   ```

2. Descarga dependencias manualmente:
   ```bash
   mvn dependency:resolve
   ```

3. Limpia caché de Maven:
   ```bash
   rmdir /s %USERPROFILE%\.m2\repository
   ```
   
   Luego:
   ```bash
   mvn clean install
   ```

4. Intenta compilar de nuevo:
   ```bash
   mvn clean compile
   ```

---

## Verificación de Éxito

Sabrás que todo funciona cuando:

1. ✓ Ejecutas `.\build.bat` sin errores
2. ✓ Ejecutas `.\run.bat` y ves "Server startup"
3. ✓ Accedes a http://localhost:8080/mi-perfil-dev
4. ✓ Ves formulario de habilidades
5. ✓ Puedes agregar una habilidad
6. ✓ La habilidad aparece en la lista
7. ✓ Recargas página y los datos persisten
8. ✓ Puedes editar y eliminar habilidades

---

## Recursos Útiles

- Maven Documentation: https://maven.apache.org/guides/
- Apache Tomcat Docs: https://tomcat.apache.org/tomcat-9.0-doc/
- Java Servlets: https://docs.oracle.com/cd/E24329_01/web.1211/e24437/toc.htm
- GSON Guide: https://github.com/google/gson/blob/master/README.md
- Troubleshooting Java: https://docs.oracle.com/en/java/javase/17/docs/
