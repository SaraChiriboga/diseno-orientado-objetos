import java.awt.*;

public class Triangulo extends FiguraBase {

    // Atributos específicos
    private double base;
    private double altura;
    private double lado1;   // base
    private double lado2;   // lado izquierdo
    private double lado3;   // lado derecho

    // Constructores

    public Triangulo(double base, double altura) {
        if (base <= 0 || altura <= 0)
            throw new IllegalArgumentException("Base y altura deben ser positivas.");
        this.base   = base;
        this.altura = altura;
        this.lado1  = base;
        // Semilado horizontal → hipotenusa del triángulo rectángulo interno
        double semilado = Math.sqrt(Math.pow(base / 2, 2) + Math.pow(altura, 2));
        this.lado2 = semilado;
        this.lado3 = semilado;
    }

    public Triangulo(double lado1, double lado2, double lado3) {
        validarTriangulo(lado1, lado2, lado3);
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
        this.base  = lado1;
        // Altura deducida de la fórmula de Herón (respecto a lado1)
        double s    = (lado1 + lado2 + lado3) / 2;
        double area = Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
        this.altura = (2 * area) / lado1;
    }

    private void validarTriangulo(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0)
            throw new IllegalArgumentException("Los lados deben ser positivos.");
        if (a + b <= c || a + c <= b || b + c <= a)
            throw new IllegalArgumentException("Los lados no forman un triángulo válido.");
    }

    // Getters
    public double getBase()   { return base;   }
    public double getAltura() { return altura; }
    public double getLado1()  { return lado1;  }
    public double getLado2()  { return lado2;  }
    public double getLado3()  { return lado3;  }

    // IFigura: cálculo
    @Override
    public double calcularArea() {
        return (base * altura) / 2;
    }

    @Override
    public double calcularPerimetro() {
        return lado1 + lado2 + lado3;
    }

    // IFigura: funcionales
    @Override
    public void escalar(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("El factor debe ser positivo.");
        base   *= factor;
        altura *= factor;
        lado1  *= factor;
        lado2  *= factor;
        lado3  *= factor;
    }

    @Override
    public String obtenerInfo() {
        return String.format(
                "Figura   : Triángulo\n" +
                        "Base     : %.2f\n" +
                        "Altura   : %.2f\n" +
                        "Lados    : %.2f / %.2f / %.2f\n" +
                        "Área     : %.2f\n" +
                        "Perímetro: %.2f",
                base, altura, lado1, lado2, lado3,
                calcularArea(), calcularPerimetro()
        );
    }

    // Dibujo Swing
    @Override
    protected void paintFigura(Graphics2D g2d, int ancho, int alto) {
        // Escalar proporcionalmente
        double prop = base / altura;
        int maxAncho = (int) (ancho * 0.60);
        int maxAlto  = (int) (alto  * 0.45);

        int basePx, altoPx;
        if (prop >= 1) {
            basePx = maxAncho;
            altoPx = (int) (maxAncho / prop);
            if (altoPx > maxAlto) { altoPx = maxAlto; basePx = (int) (maxAlto * prop); }
        } else {
            altoPx = maxAlto;
            basePx = (int) (maxAlto * prop);
            if (basePx > maxAncho) { basePx = maxAncho; altoPx = (int) (maxAncho / prop); }
        }
        basePx = Math.max(basePx, 40);
        altoPx = Math.max(altoPx, 30);

        // Vértices del triángulo (centrado en el panel)
        int cx = ancho / 2;
        int yBase = (alto / 2) + altoPx / 2 - 10;

        int[] xs = { cx, cx - basePx / 2, cx + basePx / 2 };
        int[] ys = { yBase - altoPx, yBase, yBase };

        // Sombra
        int[] xsSombra = { xs[0] + 5, xs[1] + 5, xs[2] + 5 };
        int[] ysSombra = { ys[0] + 5, ys[1] + 5, ys[2] + 5 };
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillPolygon(xsSombra, ysSombra, 3);

        // Relleno con degradado
        GradientPaint degradado = new GradientPaint(
                cx, yBase - altoPx, new Color(190, 230, 255),
                cx, yBase, colorRelleno
        );
        g2d.setPaint(degradado);
        g2d.fillPolygon(xs, ys, 3);

        // Borde
        g2d.setColor(colorBorde);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawPolygon(xs, ys, 3);

        // Etiqueta base
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(colorBorde);
        String lblBase = String.format("b = %.1f", base);
        int lblW = g2d.getFontMetrics().stringWidth(lblBase);
        g2d.drawString(lblBase, cx - lblW / 2, yBase + 16);

        // Línea de altura punteada
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{6, 4}, 0));
        g2d.drawLine(cx, yBase - altoPx, cx, yBase);

        // Etiqueta altura
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawString(String.format("h=%.1f", altura), cx + 5, yBase - altoPx / 2);
    }
}