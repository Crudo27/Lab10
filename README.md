# Laboratorio 10 - Lenguajes de Programacion III

**Tema:** Java Swing avanzado: binding manual con PropertyChangeSupport, gráficos Graphics2D, audio WAV y aplicación MVC con almacenamiento JDBC.

## Requisitos

- JDK 17 o superior.
- Driver JDBC HSQLDB 1.8 o posterior. La dependencia se define en `pom.xml` para importarla en Eclipse como proyecto Maven.

## Clases que se ejecutan desde Eclipse

- `lab10.Principal`: selector de todas las aplicaciones.
- `lab10.actividades.FormularioProducto`: binding bidireccional mediante formulario y eventos del modelo.
- `lab10.actividades.GraficoTemperaturas`: gráfico de líneas para siete días y lectura interactiva.
- `lab10.actividades.EfectosSonido`: tres efectos WAV con `Clip`.
- `lab10.actividades.ReproductorMusica`: reproducir, pausar y reanudar audio WAV.
- `lab10.mvc.InventarioApp`: aplicación MVC con base de datos local JDBC HSQLDB y operaciones CRUD.

## Compilacion y pruebas (instalacion Debian con HSQLDB)

```sh
javac -cp /usr/share/java/hsqldb1.8.0.jar -encoding UTF-8 -d out $(find src tests -name '*.java')
java -cp out:/usr/share/java/hsqldb1.8.0.jar lab10.Pruebas
java -cp out:/usr/share/java/hsqldb1.8.0.jar lab10.Principal
```

La aplicación genera sus propios datos WAV, de manera que no necesita recursos externos. El módulo JDBC usa `datos/inventario` como ubicación del almacén persistente local. El directorio `datos/` está excluido de Git.
