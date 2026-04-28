public interface IFigura {
    // metodos de cálculo (Los básicos)
    double calcularArea();
    double calcularPerimetro();

    // metodos funcionales (adicionales a los basicos)
    void dibujar(); //dibujar la figura (opcional)
    void escalar(double factor); // para cambiar el tamaño de la figura proporcionalmente
    String obtenerInfo(); // retorna un resumen de la figura
}