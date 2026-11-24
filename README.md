# Laboratorio 10 — Lenguajes de Programación III

Aplicación Java de carrito de compras que combina Observer, Strategy y Command con una separación MVC (Carrito y Producto: modelo; Vista: interfaz de consola; Controlador: lógica de interacción).

## Ejecución

Requiere JDK 17 o superior. El proyecto se puede importar en Eclipse como proyecto Java usando `src` como carpeta de fuentes. Ejecutar `lab10.Main`.

```sh
javac -encoding UTF-8 -d out src/lab10/*.java tests/lab10/Pruebas.java
java -cp out lab10.Main
java -cp out lab10.Pruebas
```

## Funcionalidades

- Agregar, eliminar y vaciar productos mediante comandos; deshacer la última operación.
- Cambiar descuentos sin alterar el modelo: 0 %, 10 %, 30 % desde la segunda unidad idéntica o 50 % al producto más barato si hay al menos tres.
- Suscribir y desuscribir al cliente y administrador de notificaciones.
- Calcular totales con `BigDecimal` y precisión decimal.

`evidencias/` almacena salidas de verificación reproducibles y recursos del informe. Las pruebas se ejecutan sin librerías externas.
