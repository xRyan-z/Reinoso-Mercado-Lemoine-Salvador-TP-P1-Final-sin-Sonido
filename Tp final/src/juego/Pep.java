package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Pep {
    private double x, y; //coordenada x e y de Pep
    private double velocidadX; //velocidad en el eje x
    private double velocidadY; //velocidad en el eje y
    private boolean saltando; //verifica el salto de pep
    private static final double GRAVEDAD = 0.2; //fisica de pep para caer/saltar
    private static final double VELOCIDAD_SALTO = -7; //velocidad del salto de pep varia de la gravedad

    // Imágenes de Pep
    private Image imagenQuietoDerecha;
    private Image imagenQuietoIzquierda;
    private Image imagenIzquierda;
    private Image imagenDerecha;
    private Image imagenSaltoIzquierda;
    private Image imagenSaltoDerecha;

    // Para recordar la última dirección en la que se movió
    private boolean mirandoDerecha;
    private boolean mirandoIzquierda;

    // Posición de reinicio
    private static final double POSICION_INICIAL_X = 400; // Cambia a la posición deseada
    private static final double POSICION_INICIAL_Y = 455; // Cambia a la posición deseada
    private static final double ALTURA_VENTANA = 600; // Cambia esto a la altura real de tu venta

    public Pep(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidadX = 2;  
        this.velocidadY = 0;   
        this.saltando = false;

        // Cargar imágenes
        this.imagenQuietoDerecha = Herramientas.cargarImagen("pepnormalder.png");
        this.imagenQuietoIzquierda = Herramientas.cargarImagen("pepnormal.png");
        this.imagenIzquierda = Herramientas.cargarImagen("pepizq.png");
        this.imagenDerecha = Herramientas.cargarImagen("pepder.png");
        this.imagenSaltoIzquierda =Herramientas.cargarImagen("pepsaltandoder.png");
        this.imagenSaltoDerecha = Herramientas.cargarImagen("pepsaltandoizq.png");


        this.mirandoDerecha = true;
        this.mirandoIzquierda = false;
    }
    public Disparo disparar() {
    	//Crea y devuelve un nuevo objeto tipo disparo
        int direccionDisparo = mirandoDerecha ? -1 : 1; // 1 para derecha, -1 para izquierda
        return new Disparo(this.x, this.y, direccionDisparo);
    }
    
    //fisicas de movimiento de pep
    public void moverse(Entorno entorno, Isla[] islas) {
        this.velocidadX = 0; 
        
        //movimiento para de izq
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            this.velocidadX = -2; //velocidad en el eje X de Pep
            this.mirandoIzquierda = true; //imagen pepizq
            this.mirandoDerecha = false;//imagen pepder
        }
        //movimiento para la derecha
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            this.velocidadX = 2; //velocidad en el eje X de Pep
            this.mirandoDerecha = true; //imagen pepder
            this.mirandoIzquierda = false; //imagen pepizq
        }

        this.x += this.velocidadX; //movimiento de Pep en el eje X

        boolean enIsla = false;
        for (Isla isla : islas) {
            if (isla != null && this.estaSobreIsla(isla)) {
                enIsla = true;
                break;
            }
        }

        if (entorno.sePresiono(entorno.TECLA_ARRIBA) && enIsla) {
            this.saltando = true;
            this.velocidadY = VELOCIDAD_SALTO;
        }

        if (!enIsla || saltando) {
            this.y += this.velocidadY;
            this.velocidadY += GRAVEDAD;  // Aumenta la velocidad de caída por gravedad
        } else {
            // Si está en una isla, detén la caída
            this.velocidadY = 0;
            this.saltando = false;  // Resetea el estado de salto
        }

        if (enIsla && this.saltando) {
            this.saltando = false; 
           
        }

        // Verificar si el personaje ha caído fuera de los límites de la pantalla
        if (this.y > ALTURA_VENTANA) {
            
        	reiniciar(); //****Sacar para la prueba****//
        }
    }

    void reiniciar() {
        this.x = POSICION_INICIAL_X;
        this.y = POSICION_INICIAL_Y;
        this.velocidadY = 0; // Resetear velocidad vertical al reiniciar
        this.saltando = false; // Reiniciar el estado de salto
    }

    //indicar de imagenes mediante la accion que haga pep
    public void dibujarse(Entorno entorno) {
        Image imagenActual;

        if (saltando) {
            if (this.velocidadX > 0) {
                imagenActual = imagenSaltoDerecha;
            } else {
                imagenActual = imagenSaltoIzquierda;
            }
        } else if (this.velocidadX > 0) {
            imagenActual = imagenDerecha;
        } else if (this.velocidadX < 0) {
            imagenActual = imagenIzquierda;
        } else {
            if (mirandoDerecha) {
                imagenActual = imagenQuietoDerecha;
            } else if (mirandoIzquierda) {
                imagenActual = imagenQuietoIzquierda;
            } else {
                imagenActual = imagenQuietoDerecha; 
            }
        }

        entorno.dibujarImagen(imagenActual, this.x, this.y, 0);
    }
    
    //colision de pep sobre la isla
    private boolean estaSobreIsla(Isla isla) {
        return this.x >= isla.getX() - isla.ancho / 2 && this.x <= isla.getX() + isla.ancho / 2 &&
               this.y + 25 >= isla.getY() - isla.alto / 2 && this.y + 40 <= isla.getY() + isla.alto / 2;
    }
    //colision de pep con Gnomo
    public boolean pepColisionaConGnomo(double gnomoX, double gnomoY, double gnomoAncho, double gnomoAlto) {
        // Verifica si Pep y el Gnomo están en la misma zona (o casi) para considerarlo una "colisión"
        return gnomoY + gnomoAlto / 2 >= this.y - 25 && gnomoY - gnomoAlto / 2 <= this.y + 25 &&
               gnomoX + gnomoAncho / 2 >= this.x - 15 && gnomoX - gnomoAncho / 2 <= this.x + 15;
    }
    //colision de pep con tortugas
    public boolean pepColisionaConTortuga(double tortugaX, double tortugaY, double tortugaAncho, double tortugaAlto) {
        // Verifica si Pep y el Gnomo están en la misma zona (o casi) para considerarlo una "colisión"
        return tortugaY + tortugaAlto / 2 >= this.y - 25 && tortugaY - tortugaAlto / 2 <= this.y + 25 &&
               tortugaX + tortugaAncho / 2 >= this.x - 25 && tortugaX - tortugaAncho / 2 <= this.x + 15;
    }
    

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
