import javax.swing.*;
import java.awt.*;

public abstract class FiguraBase implements IFigura {

    //Color compartido por todas las figuras
    protected Color colorRelleno  = new Color(99, 179, 237);   // azul claro
    protected Color colorBorde    = new Color(44, 82, 130);    // azul oscuro

    //  dibujar(): abre ventana Swing con la figura
    @Override
    public void dibujar() {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Figura: " + getClass().getSimpleName());
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.setSize(500, 500);
            ventana.setLocationRelativeTo(null);
            ventana.setResizable(false);

            // Panel de dibujo con fondo degradado
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    // Antialiasing para bordes suaves
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    // Fondo degradado suave
                    GradientPaint fondo = new GradientPaint(
                            0, 0, new Color(240, 248, 255),
                            getWidth(), getHeight(), new Color(200, 230, 250)
                    );
                    g2d.setPaint(fondo);
                    g2d.fillRect(0, 0, getWidth(), getHeight());

                    // Delegar el dibujo específico a la subclase
                    paintFigura(g2d, getWidth(), getHeight());

                    // Cuadro de información
                    dibujarInfo(g2d);
                }
            };

            ventana.add(panel);
            ventana.setVisible(true);
        });
    }

    private void dibujarInfo(Graphics2D g2d) {
        String[] lineas = obtenerInfo().split("\n");
        int padding = 10;
        int lineaAltura = 18;
        int cajaAltura  = lineas.length * lineaAltura + padding * 2;
        int cajaAncho   = 260;
        int x = 10;
        int y = 490 - cajaAltura - 10;

        // Fondo semitransparente
        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRoundRect(x, y, cajaAncho, cajaAltura, 12, 12);
        g2d.setColor(colorBorde);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, cajaAncho, cajaAltura, 12, 12);

        // Texto
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2d.setColor(new Color(30, 60, 90));
        for (int i = 0; i < lineas.length; i++) {
            g2d.drawString(lineas[i], x + padding, y + padding + 13 + i * lineaAltura);
        }
    }

    protected abstract void paintFigura(Graphics2D g2d, int ancho, int alto);
}