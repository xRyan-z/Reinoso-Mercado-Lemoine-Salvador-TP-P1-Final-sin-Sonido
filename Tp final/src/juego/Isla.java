package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;

public class Isla {
    public double x, y; //posicion de coord x y coord y de la isla
    public int ancho, alto; //valores ancho y alto de la isla
    private Image imagen;  // Imagen opcional para la isla

    public Isla(double x, double y, int ancho, int alto, Image imagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = imagen;  // Asigna la imagen al atributo
    }
    
    //dibuja la isla
    public void dibujarse(Entorno entorno) {
        if (imagen != null) {
            // Dibuja la imagen si está disponible
            entorno.dibujarImagen(imagen, this.x, this.y, 0);
        } else {
            // Dibuja un rectángulo si no hay imagen
            entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.yellow);
        }
    }
    
    //getters utilizados para la clase juego para obtener
    //las variables de la clase 
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }

    public boolean colisionaConTortuga(double tortugaX, double tortugaY, double tortugaAncho, double tortugaAlto) {
        // Verifica si la tortuga está sobre la isla (aprox)
        return tortugaY + tortugaAlto / 2 >= this.y - this.alto / 2 && tortugaY - tortugaAlto / 2 <= this.y + this.alto / 2 &&
               tortugaX + tortugaAncho / 2 >= this.x - this.ancho / 2 && tortugaX - tortugaAncho / 2 <= this.x + this.ancho / 2;
    }

    public boolean colisionaConGnomo(double gnomoX, double gnomoY, double gnomoAncho, double gnomoAlto) {
        // Verifica si el Gnomo está sobre la isla (aprox)
        return gnomoY + gnomoAlto / 2 >= this.y - this.alto / 2 && gnomoY - gnomoAlto / 2 <= this.y + this.alto / 2 &&
               gnomoX + gnomoAncho / 2 >= this.x - this.ancho / 2 && gnomoX - gnomoAncho / 2 <= this.x + this.ancho / 2;
    }
}
