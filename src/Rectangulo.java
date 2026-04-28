import java.awt.*;

public class Rectangulo extends FiguraBase {

    // Atributos específicos
    private double base;
    private double altura;

    // Constructor
    public Rectangulo(double base, double altura) {
        if (base <= 0 || altura <= 0)
            throw new IllegalArgumentException("La base y la altura deben ser positivas.");
        this.base   = base;
        this.altura = altura;
    }

    // Getters y Setters
    public double getBase()    { return base;   }
    public double getAltura()  { return altura; }
    public void   setBase(double base)     { this.base   = base;   }
    public void   setAltura(double altura) { this.altura = altura; }

    // IFigura: cálculo
    @Override
    public double calcularArea() {
        return base * altura;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * (base + altura);
    }

    // IFigura: funcionales
    @Override
    public void escalar(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("El factor debe ser positivo.");
        base   *= factor;
        altura *= factor;
    }

    @Override
    public String obtenerInfo() {
        return String.format(
                "Figura   : Rectángulo\n" +
                        "Base     : %.2f\n" +
                        "Altura   : %.2f\n" +
                        "Área     : %.2f\n" +
                        "Perímetro: %.2f",
                base, altura, calcularArea(), calcularPerimetro()
        );
    }

    // Dibujo Swing
    @Override
    protected void paintFigura(Graphics2D g2d, int ancho, int alto) {
        // Escalar proporcionalmente al panel (máximo 70 % de cada dimensión)
        double proporcion = base / altura;
        int maxAncho  = (int) (ancho * 0.60);
        int maxAlto   = (int) (alto  * 0.45);

        int rectAncho, rectAlto;
        if (proporcion >= 1) {
            rectAncho = maxAncho;
            rectAlto  = (int) (maxAncho / proporcion);
            if (rectAlto > maxAlto) { rectAlto = maxAlto; rectAncho = (int)(maxAlto * proporcion); }
        } else {
            rectAlto  = maxAlto;
            rectAncho = (int) (maxAlto * proporcion);
            if (rectAncho > maxAncho) { rectAncho = maxAncho; rectAlto = (int)(maxAncho / proporcion); }
        }
        rectAncho = Math.max(rectAncho, 30);
        rectAlto  = Math.max(rectAlto, 20);

        int rx = (ancho - rectAncho) / 2;
        int ry = (alto  - rectAlto)  / 2 - 20;

        // Sombra
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRect(rx + 6, ry + 6, rectAncho, rectAlto);

        // Relleno con degradado lineal
        GradientPaint degradado = new GradientPaint(
                rx, ry, new Color(190, 230, 255),
                rx + rectAncho, ry + rectAlto, colorRelleno
        );
        g2d.setPaint(degradado);
        g2d.fillRect(rx, ry, rectAncho, rectAlto);

        // Borde
        g2d.setColor(colorBorde);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRect(rx, ry, rectAncho, rectAlto);

        // Etiquetas
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(colorBorde);
        // Base (abajo)
        String lblBase = String.format("b = %.1f", base);
        int lblBaseW   = g2d.getFontMetrics().stringWidth(lblBase);
        g2d.drawString(lblBase, rx + (rectAncho - lblBaseW) / 2, ry + rectAlto + 16);
        // Altura (lateral)
        Graphics2D g2dCopy = (Graphics2D) g2d.create();
        g2dCopy.rotate(-Math.PI / 2, rx - 8, ry + rectAlto / 2);
        g2dCopy.drawString(String.format("h = %.1f", altura), rx - 8, ry + rectAlto / 2 + 4);
        g2dCopy.dispose();
    }
}