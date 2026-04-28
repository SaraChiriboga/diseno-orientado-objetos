import java.awt.*;

public class Circulo extends FiguraBase {

    private double radio;

    // Constructor
    public Circulo(double radio) {
        if (radio <= 0) throw new IllegalArgumentException("El radio debe ser positivo.");
        this.radio = radio;
    }

    // Getters y Setters
    public double getRadio() { return radio; }
    public void   setRadio(double radio) { this.radio = radio; }

    // IFigura: cálculo
    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }

    // IFigura: funcionales
    public void escalar(double factor) {
        if (factor <= 0) throw new IllegalArgumentException("El factor debe ser positivo.");
        radio *= factor;
    }

    @Override
    public String obtenerInfo() {
        return String.format(
                "Figura   : Círculo\n" +
                        "Radio    : %.2f\n" +
                        "Área     : %.2f\n" +
                        "Perímetro: %.2f",
                radio, calcularArea(), calcularPerimetro()
        );
    }

    // Dibujo Swing
    @Override
    protected void paintFigura(Graphics2D g2d, int ancho, int alto) {
        // Escalar el radio a píxeles (máximo 35 % del lado menor)
        int maxPx = (int) (Math.min(ancho, alto) * 0.35);
        int radioPx = Math.min(maxPx, (int) (radio * 4));
        radioPx = Math.max(radioPx, 20);

        int cx = ancho / 2;
        int cy = alto / 2 - 20;

        // Sombra
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillOval(cx - radioPx + 6, cy - radioPx + 6, radioPx * 2, radioPx * 2);

        // Relleno con degradado radial
        RadialGradientPaint degradado = new RadialGradientPaint(
                cx - radioPx / 3f, cy - radioPx / 3f, radioPx * 1.2f,
                new float[]{0f, 1f},
                new Color[]{new Color(190, 230, 255), colorRelleno}
        );
        g2d.setPaint(degradado);
        g2d.fillOval(cx - radioPx, cy - radioPx, radioPx * 2, radioPx * 2);

        // Borde
        g2d.setColor(colorBorde);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawOval(cx - radioPx, cy - radioPx, radioPx * 2, radioPx * 2);

        // Etiqueta del radio
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(colorBorde);
        g2d.drawLine(cx, cy, cx + radioPx, cy);
        g2d.drawString(String.format("r = %.1f", radio), cx + 5, cy - 5);
    }
}