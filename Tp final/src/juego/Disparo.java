package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {
    private double x, y;
    private double velocidad = -5; // Velocidad hacia arriba
    private int direccionX;
    private Image imagenIzquierda;
    private Image imagenDerecha;

    public Disparo(double x, double y, int direccionX) {
        this.x = x;
        this.y = y;
        this.direccionX = direccionX;
        
        // Cargar las imágenes según la dirección
        this.imagenIzquierda = Herramientas.cargarImagen("fuegoder.png"); // Cambia a la ruta de tu imagen para la izquierda
        this.imagenDerecha = Herramientas.cargarImagen("fuegoizq.png");   // Cambia a la ruta de tu imagen para la derecha
    }

    public void mover() {
        x += velocidad * direccionX;
    }

    public void dibujar(Entorno entorno) {
        // Selecciona la imagen según la dirección
        Image imagenActual = (direccionX < 0) ? imagenIzquierda : imagenDerecha;
        entorno.dibujarImagen(imagenActual, x, y, 0); // Dibuja la imagen correspondiente
    }

    public boolean colisionaCon(Tortuga tortuga) {
        double distanciaX = Math.abs(this.x - tortuga.getX());
        double distanciaY = Math.abs(this.y - tortuga.getY());
        
        // Asume un rango de proximidad para considerar la colisión (ajustar según sea necesario)
        return distanciaX < 20 && distanciaY < 20;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}