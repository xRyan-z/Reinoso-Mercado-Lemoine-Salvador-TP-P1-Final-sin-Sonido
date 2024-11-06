package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Image;

public class Casa {
    private double x; //coordenada x de la casa
    private double y; //coordenada y de la casa
    private Image imagenCasa; //imagen que se utiliza para la casa

    // Constructor de la Casa con posición y dimensiones
    public Casa(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.imagenCasa = Herramientas.cargarImagen("casa.png"); // Ruta de la imagen de la casa
    }

    // Método para dibujar la casa usando la imagen
    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(this.imagenCasa, this.x, this.y, 0);
    }

    // Getters para obtener la posición de la casa (si es necesario)
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
