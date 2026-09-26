import java.util.ArrayList;
import java.util.Scanner;

/*
 * =============================================================================
 * EVALUACIÓN PARCIAL 1 — TIENDA DE VIDEOJUEGOS
 * Alumno: Solución de Referencia
 * Asignatura: Desarrollo Orientado a Objetos (2° Semestre 2026)
 * =============================================================================
 * 
 * REQUERIMIENTO 1 (R1) — Justificación del Paradigma:
 * 
 * Este sistema fue desarrollado bajo el Paradigma Orientado a Objetos (POO).
 * En POO, modelamos el dominio mediante clases (Producto, ProductoFisico, ProductoDigital)
 * que encapsulan tanto sus datos (atributos privados) como su comportamiento (métodos).
 * 
 * Dos diferencias concretas con el paradigma funcional en Python visto previamente:
 * 
 * 1. Tipado Estático y Seguridad en Tiempo de Compilación:
 *    En Python el tipado es dinámico (una variable puede cambiar de tipo en cualquier momento
 *    y los errores de tipo saltan en ejecución). En Java, cada variable, parámetro y método
 *    debe declarar explícitamente su tipo (int, String, Producto), permitiendo al compilador
 *    (javac) verificar la coherencia de los tipos antes de que el programa se ejecute.
 * 
 * 2. Organización del Código y Encapsulamiento:
 *    En Python funcional el código se estructura en funciones independientes y sueltas
 *    que operan sobre diccionarios o tuplas mutables. En Java POO, no existen funciones libres:
 *    todo método debe pertenecer a una clase, protegiendo el estado interno mediante
 *    modificadores de acceso (private) y exponiendo únicamente interfaces seguras (getters/setters).
 * =============================================================================
 */

public class Main {

    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        precargarDatosPrueba();

        boolean ejecutando = true;

        while (ejecutando) {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    registrarProducto();
                    break;
                case "2":
                    listarInventario();
                    break;
                case "3":
                    buscarProducto();
                    break;
                case "4":
                    venderProducto();
                    break;
                case "5":
                    resumenInventario();
                    break;
                case "6":
                    mostrarMensaje("¡Gracias por usar el sistema de la Tienda de Videojuegos! Hasta pronto.", "OK");
                    ejecutando = false;
                    break;
                default:
                    mostrarMensaje("Opción no válida. Por favor, ingrese un número del 1 al 6.", "ERROR");
                    break;
            }
            System.out.println();
        }
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static void mostrarMensaje(String mensaje, String tipo) {
        String prefijo = "";
        switch (tipo.toUpperCase()) {
            case "OK":
                prefijo = "✓ [OK] ";
                break;
            case "ERROR":
                prefijo = "✗ [ERROR] ";
                break;
            case "INFO":
                prefijo = "ℹ [INFO] ";
                break;
            case "ALERTA":
                prefijo = "⚠ [ALERTA] ";
                break;
            default:
                prefijo = "[" + tipo + "] ";
                break;
        }
        System.out.println(prefijo + mensaje);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("=========================================");
        System.out.println("       TIENDA DE VIDEOJUEGOS             ");
        System.out.println("=========================================");
        System.out.println("1. Registrar producto (Físico / Digital)");
        System.out.println("2. Listar inventario");
        System.out.println("3. Buscar producto por nombre");
        System.out.println("4. Vender producto");
        System.out.println("5. Resumen del inventario");
        System.out.println("6. Salir");
        System.out.println("-----------------------------------------");
    }

    private static void registrarProducto() {
        System.out.println("\n--- REGISTRAR NUEVO PRODUCTO ---");
        System.out.println("1. Juego Físico");
        System.out.println("2. Juego Digital");
        System.out.print("Seleccione el tipo de producto: ");
        String tipoStr = scanner.nextLine().trim();

        if (!tipoStr.equals("1") && !tipoStr.equals("2")) {
            mostrarMensaje("Tipo de producto inválido. Operación cancelada.", "ERROR");
            return;
        }

        try {
            System.out.print("Nombre del videojuego: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                mostrarMensaje("El nombre no puede estar vacío.", "ERROR");
                return;
            }

            System.out.print("Precio base ($): ");
            int precioBase = Integer.parseInt(scanner.nextLine().trim());
            if (precioBase <= 0) {
                mostrarMensaje("El precio base debe ser un valor positivo.", "ERROR");
                return;
            }

            System.out.print("Cantidad en stock: ");
            int stock = Integer.parseInt(scanner.nextLine().trim());
            if (stock < 0) {
                mostrarMensaje("El stock no puede ser negativo.", "ERROR");
                return;
            }

            if (tipoStr.equals("1")) {
                System.out.print("Costo de envío ($): ");
                int costoEnvio = Integer.parseInt(scanner.nextLine().trim());
                if (costoEnvio < 0) {
                    mostrarMensaje("El costo de envío no puede ser negativo.", "ERROR");
                    return;
                }

                ProductoFisico nuevoFisico = new ProductoFisico(nombre, precioBase, stock, costoEnvio);
                inventario.add(nuevoFisico);
                mostrarMensaje("Producto físico registrado exitosamente: " + nombre, "OK");

            } else {
                System.out.print("Porcentaje de descuento (0-100): ");
                int descuento = Integer.parseInt(scanner.nextLine().trim());
                if (descuento < 0 || descuento > 100) {
                    mostrarMensaje("El descuento debe estar entre 0 y 100.", "ERROR");
                    return;
                }

                System.out.print("Plataforma (ej: Steam, PS5, Switch, Xbox): ");
                String plataforma = scanner.nextLine().trim();
                if (plataforma.isEmpty()) {
                    plataforma = "Digital";
                }

                ProductoDigital nuevoDigital = new ProductoDigital(nombre, precioBase, stock, descuento, plataforma);
                inventario.add(nuevoDigital);
                mostrarMensaje("Producto digital registrado exitosamente: " + nombre, "OK");
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("Entrada no válida: debe ingresar un valor numérico entero.", "ERROR");
        }
    }

    private static void listarInventario() {
        System.out.println("\n--- INVENTARIO ACTUAL ---");
        if (inventario.isEmpty()) {
            mostrarMensaje("No hay productos registrados en el inventario actualmente.", "INFO");
            return;
        }

        for (int i = 0; i < inventario.size(); i++) {
            Producto p = inventario.get(i);
            System.out.println("[" + (i + 1) + "] " + p.mostrarInfo());
        }
        System.out.println("Total de productos en catálogo: " + inventario.size());
    }

    private static void buscarProducto() {
        System.out.println("\n--- BÚSQUEDA DE PRODUCTOS ---");
        if (inventario.isEmpty()) {
            mostrarMensaje("El inventario está vacío. No hay productos para buscar.", "INFO");
            return;
        }

        System.out.print("Ingrese el término o nombre a buscar: ");
        String termino = scanner.nextLine().trim().toLowerCase();

        if (termino.isEmpty()) {
            mostrarMensaje("Debe ingresar un término para la búsqueda.", "ALERTA");
            return;
        }

        int coincidencias = 0;
        System.out.println("\nResultados encontrados:");
        for (Producto p : inventario) {
            if (p.getNombre().toLowerCase().contains(termino)) {
                System.out.println("  • " + p.mostrarInfo());
                coincidencias++;
            }
        }

        if (coincidencias == 0) {
            mostrarMensaje("No se encontraron productos que coincidan con '" + termino + "'.", "INFO");
        } else {
            mostrarMensaje("Se encontraron " + coincidencias + " producto(s) coincidentes.", "OK");
        }
    }

    private static void venderProducto() {
        System.out.println("\n--- VENDER PRODUCTO ---");
        if (inventario.isEmpty()) {
            mostrarMensaje("No hay productos en inventario para vender.", "INFO");
            return;
        }

        for (int i = 0; i < inventario.size(); i++) {
            Producto p = inventario.get(i);
            System.out.println("[" + (i + 1) + "] " + p.getNombre() + 
                               " | Stock actual: " + p.getStock() + 
                               " | Precio c/u: $" + p.calcularPrecioFinal());
        }

        try {
            System.out.print("\nIngrese el número de producto a vender (1 a " + inventario.size() + "): ");
            int numProducto = Integer.parseInt(scanner.nextLine().trim());

            if (numProducto < 1 || numProducto > inventario.size()) {
                mostrarMensaje("Número de producto fuera de rango. Operación abortada.", "ERROR");
                return;
            }

            Producto productoSeleccionado = inventario.get(numProducto - 1);

            System.out.print("Ingrese la cantidad de unidades a vender: ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());

            if (cantidad <= 0) {
                mostrarMensaje("La cantidad a vender debe ser un número entero mayor a 0.", "ERROR");
                return;
            }

            if (cantidad > productoSeleccionado.getStock()) {
                mostrarMensaje("Stock insuficiente. Disponibles: " + productoSeleccionado.getStock() + 
                               " unidades, solicitadas: " + cantidad + " unidades.", "ERROR");
                return;
            }

            productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
            int totalVenta = productoSeleccionado.calcularPrecioFinal() * cantidad;

            System.out.println("\n-----------------------------------------");
            mostrarMensaje("¡Venta realizada con éxito!", "OK");
            System.out.println("Producto vendido: " + productoSeleccionado.getNombre());
            System.out.println("Unidades vendidas: " + cantidad);
            System.out.println("Precio unitario final: $" + productoSeleccionado.calcularPrecioFinal());
            System.out.println("TOTAL A PAGAR: $" + totalVenta);
            System.out.println("Stock remanente: " + productoSeleccionado.getStock() + " unidades");
            System.out.println("-----------------------------------------");

        } catch (NumberFormatException e) {
            mostrarMensaje("Entrada no válida: debe ingresar valores numéricos enteros.", "ERROR");
        }
    }

    private static void resumenInventario() {
        System.out.println("\n--- RESUMEN GENERAL DEL INVENTARIO ---");
        if (inventario.isEmpty()) {
            mostrarMensaje("El inventario está actualmente vacío.", "INFO");
            return;
        }

        int totalFisicos = 0;
        int totalDigitales = 0;
        long valorTotalInventario = 0;

        for (Producto p : inventario) {
            if (p instanceof ProductoFisico) {
                totalFisicos++;
            } else if (p instanceof ProductoDigital) {
                totalDigitales++;
            }
            valorTotalInventario += (long) p.calcularPrecioFinal() * p.getStock();
        }

        System.out.println("Total de productos distintos: " + inventario.size());
        System.out.println("  • Juegos en formato físico : " + totalFisicos);
        System.out.println("  • Juegos en formato digital: " + totalDigitales);
        System.out.println("-----------------------------------------");
        System.out.println("VALORIZACIÓN TOTAL DEL INVENTARIO: $" + valorTotalInventario);
        System.out.println("-----------------------------------------");
    }

    private static void precargarDatosPrueba() {
        inventario.add(new ProductoFisico("The Legend of Zelda: Tears of the Kingdom", 49990, 10, 3990));
        inventario.add(new ProductoDigital("Hollow Knight: Silksong", 15000, 50, 20, "Steam"));
    }
}