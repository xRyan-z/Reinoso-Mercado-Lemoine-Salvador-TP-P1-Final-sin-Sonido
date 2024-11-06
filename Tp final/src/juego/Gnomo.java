package juego;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;

public class Gnomo {
    private double x, y; //posicion de coord x y coord y del Gnomo
    private double velocidadX = 1.2; //velocidad a la que se mueven en x (izq o der)
    private double velocidadY = 4;   //velocidad en que caen de una isla
    private boolean cayendo = true; //booleana si se esta cayendo o no
    private Random random; //variable random, utilizada para el movimiento
    private Isla islaActual; // isla donde el Gnomo se encuentra
    private int direccion; // 1 para derecha, -1 para izquierda
    private double ancho; //variable del ancho del Gnomo
    private double alto; //variable del alto del Gnomo
    
    // Imágenes del gnomo
    private Image imagenDerecha; 
    private Image imagenIzquierda;

    public Gnomo(double x, double y) {
        this.x = x;
        this.y = y;
        this.random = new Random();
        this.direccion = random.nextBoolean() ? 1 : -1;
        this.alto = 20;
        this.ancho = 10;
        
        // Cargar las imágenes del gnomo
        this.imagenDerecha = Herramientas.cargarImagen("gnomoder.png");
        this.imagenIzquierda = Herramientas.cargarImagen("gnomoizq.png");
    }

    public void moverse(Isla[] islas) {
        if (cayendo) {
            y += velocidadY;

            // Verifica si el gnomo ha colisionado con alguna isla
            for (Isla isla : islas) {
                if (isla != null && isla.colisionaConGnomo(x, y, 20, 20)) {
                    cayendo = false;
                    velocidadY = 0;
                    y = isla.getY() - isla.getAlto() / 2 - 10;
                    islaActual = isla;
                    cambiarDireccionAleatoria();
                    break;
                }
            }
        } else {
            x += velocidadX * direccion;

            if (islaActual != null) {
                double limiteIzquierdo = islaActual.getX() - islaActual.getAncho() / 1.63;
                double limiteDerecho = islaActual.getX() + islaActual.getAncho() / 1.63;

                if (x < limiteIzquierdo || x > limiteDerecho) {
                    iniciarCaida();
                }
            }
        }
    }

    private void iniciarCaida() {
        cayendo = true;
        velocidadY = 4;
        islaActual = null;
    }

    private void cambiarDireccionAleatoria() {
        direccion = random.nextBoolean() ? 1 : -1;
    }
    //metodo utilizado para indicar donde debe aparecer el gnomo 
    //si colisiona con una tortuga,vacio o con pep
    public void respawn(int x, int y) {
        
    	this.x = x;
        this.y = y;
    }
    
    //getters utilizados para la clase juego para obtener
    //las variables de la clase 
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getAlto() {
        return this.alto;
    }

    public double getAncho() {
        return this.ancho;
    }
    //dibuja al Gnomo
    public void dibujarse(Entorno entorno) {
        // Selecciona la imagen correcta según la dirección del gnomo
        Image imagenActual = (direccion == 1) ? imagenDerecha : imagenIzquierda;
        
        // Dibuja la imagen del gnomo en lugar de un rectángulo
        entorno.dibujarImagen(imagenActual, this.x, this.y, 0);
    }
}
