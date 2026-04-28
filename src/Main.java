import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        System.out.println("      SISTEMA INTERACTIVO DE FIGURAS  ");

        while (!salir) {
            imprimirMenu();
            int opcion = (int) leerDouble("\nSeleccione una opción: ");

            switch (opcion) {
                case 1 -> configurarCirculo();
                case 2 -> configurarRectangulo();
                case 3 -> configurarTriangulo();
                case 0 -> {
                    System.out.println("\n ¡Hasta pronto!");
                    salir = true;
                }
                default -> System.out.println("   Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    private static void imprimirMenu() {
        System.out.println("\n----------- MENÚ PRINCIPAL -----------");
        System.out.println(" [1] Crear Círculo");
        System.out.println(" [2] Crear Rectángulo");
        System.out.println(" [3] Crear Triángulo");
        System.out.println(" [0] Salir");
        System.out.println("--------------------------------------");
    }

    // Lógica para el Círculo
    private static void configurarCirculo() {
        System.out.println("\n--- Nuevo Círculo ---");
        double radio = leerDouble("Ingrese el radio: ");
        Circulo c = new Circulo(radio);

        mostrarYDibujar(c);
    }

    // Lógica para el Rectángulo
    private static void configurarRectangulo() {
        System.out.println("\n--- Nuevo Rectángulo ---");
        double base = leerDouble("Ingrese la base: ");
        double altura = leerDouble("Ingrese la altura: ");
        Rectangulo r = new Rectangulo(base, altura);

        mostrarYDibujar(r);
    }

    // Lógica para el Triángulo──
    private static void configurarTriangulo() {
        System.out.println("\n--- Nuevo Triángulo ---");
        System.out.println("¿Cómo desea definirlo?");
        System.out.println(" [1] Base y Altura");
        System.out.println(" [2] Tres Lados");
        int tipo = (int) leerDouble("Opción: ");

        Triangulo t;
        if (tipo == 2) {
            double l1 = leerDouble("  Lado 1 (Base): ");
            double l2 = leerDouble("  Lado 2: ");
            double l3 = leerDouble("  Lado 3: ");
            try {
                t = new Triangulo(l1, l2, l3);
                mostrarYDibujar(t);
            } catch (IllegalArgumentException e) {
                System.out.println("   Error: " + e.getMessage());
            }
        } else {
            double b = leerDouble("  Base: ");
            double h = leerDouble("  Altura: ");
            t = new Triangulo(b, h);
            mostrarYDibujar(t);
        }
    }


    private static void mostrarYDibujar(IFigura figura) {
        System.out.println("\n" + "-".repeat(30));
        System.out.println(figura.obtenerInfo());
        System.out.println("-".repeat(30));
        System.out.println("Abriendo ventana de visualización...");
        figura.dibujar();
    }


    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("  Entrada inválida. Use números.");
                scanner.nextLine();
            }
        }
    }
}