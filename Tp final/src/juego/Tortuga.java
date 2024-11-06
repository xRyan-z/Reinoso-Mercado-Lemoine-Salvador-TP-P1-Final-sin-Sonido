package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
public class Tortuga {
    private double x, y;
    private double velocidadX = 1;
    private boolean cayendo = true;
	private boolean sobreIsla;
	private Image imagenIzquierda;
    private Image imagenDerecha;
    private double ancho; // Ancho de la tortuga
    private double alto;  // Altura de la tortuga

    public Tortuga(double x, double y) {
        this.x = x;
        this.y = y;
        this.imagenIzquierda = Herramientas.cargarImagen("Tortugaizq.png");
        this.imagenDerecha = Herramientas.cargarImagen("Tortugader.png");
    }

    public void moverse(Isla[] islas, Isla islaCasa) {
        if (cayendo) {
            // Simula la caída de la tortuga
            y += 3; // Velocidad de la caída de la tortuga

            // Verifica si la tortuga ha colisionado con alguna isla, excluyendo la isla de la casa
            for (Isla isla : islas) {
                if (isla != null && isla != islaCasa && isla.colisionaConTortuga(x, y, 30, 30)) {
                	if (isla.getY() <= 250)
                		continue;
                    cayendo = false; // La tortuga ya no cae 
                    y = isla.getY() - isla.getAlto() / 2 - 15; // Ajusta la posición Y para que quede sobre la isla
                    break;
                }
            }
        } else {
            // Movimiento horizontal en la isla
            x += velocidadX;

            // Verificar colisiones con los extremos de la isla, excluyendo la isla de la casa
            for (Isla isla : islas) {
                if (isla != null && isla != islaCasa && isla.colisionaConTortuga(x, y, 30, 30)) {
                    double bordeIzquierdo = isla.getX() - isla.getAncho() / 2;
                    double bordeDerecho = isla.getX() + isla.getAncho() / 2;

                    // Cambia de dirección si la tortuga llega un poco más allá de los bordes
                    if (x <= bordeIzquierdo + 15) {
                        x = bordeIzquierdo + 15;
                        velocidadX = Math.abs(velocidadX); // Mueve hacia la derecha
                    } else if (x >= bordeDerecho - 15) {
                        x = bordeDerecho - 15;
                        velocidadX = -Math.abs(velocidadX); // Mueve hacia la izquierda
                    }
                }
            }
        }
    }

    public boolean TortugaColisionaConGnomo(double gnomoX, double gnomoY, double gnomoAncho, double gnomoAlto) {
        // Verifica si Tortuga y el Gnomo están en la misma zona (o casi) para considerarlo una "colisión"
        return gnomoY + gnomoAlto / 2 >= this.y - 25 && gnomoY - gnomoAlto / 2 <= this.y + 25 &&
               gnomoX + gnomoAncho / 2 >= this.x - 15 && gnomoX - gnomoAncho / 2 <= this.x + 15;
    }

   

    public void dibujarse(Entorno entorno) {
        Image imagenActual = (velocidadX > 0) ? imagenDerecha : imagenIzquierda;
        entorno.dibujarImagen(imagenActual, x, y, 0);
      
        }

    public void resetearPosicion(double nuevaX) {
        this.x = nuevaX;
        this.y = 0;
        this.setSobreIsla(false);  // Aseguramos que vuelva a caer
        this.cayendo= true;
	}

	public boolean isSobreIsla() {
		return sobreIsla;
	}

	public void setSobreIsla(boolean sobreIsla) {
		this.sobreIsla = sobreIsla;
	}
	 public double getAncho() {
	        return ancho; // Devuelve el ancho de la tortuga
	    }

	    public double getAlto() {
	        return alto; // Devuelve la altura de la tortuga
	    }
	
	public double getY() {
		return y;
	}
    public double getX() {
        return x;
    }
}
