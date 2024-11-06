package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego {
    private Entorno entorno; // dibuja el entorno
    private Image fondo; // imagen de fondo del juego
    private Image imagenIslaNivel1;  // Imagen para islas de nivel 1
    private Image imagenIslaNivel2;  // Imagen para islas de nivel 2
    private Image imagenIslaNivel3;  // Imagen para islas de nivel 3
    private Image imagenIslaNivel4;  // Imagen para islas de nivel 4
    private Image imagenIslaNivel5;  // Imagen para islas de nivel 5
    private Image ganaste; // imagen de cuando ganas
    private Image perdiste;// imagen de cuando perdes 
    private Isla[] islas; //arreglo de islas
    private Casa casa; 
    private Pep pep;
    private Tortuga[] tortugas; //arreglo de tortugas
    private Gnomo[] gnomo; //arreglo de gnomos
    private Random random;
    private Isla islacasa;
    private Disparo[] disparos; //arreglo de disparos
    private int maxDisparos = 10;
    private int eliminadas;
    private int salvados;
    private int perdidos;
    private boolean perder, ganar;
    private void verificarColisiones() {
        
    	
    	// Recorrer todos los disparos
        for (int i = 0; i < disparos.length; i++) {
            Disparo disparo = disparos[i];
            if (disparo != null) { // Asegura que el disparo no sea null
                // Recorrer todas las tortugas
                for (int j = 0; j < tortugas.length; j++) {
                    Tortuga tortuga = tortugas[j];
                    if (tortuga != null && disparo.colisionaCon(tortuga)) { 
                        // Si el disparo colisiona con la tortuga
                        double nuevaX = random.nextInt(entorno.ancho()); // Nueva posición aleatoria en X
                        tortuga.resetearPosicion(nuevaX); // Reinicia la tortuga en una nueva posición
                        disparos[i] = null;  // Elimina el disparo
                        eliminadas++; // Incrementar el contador de tortugas eliminadas
                        break;  // Salir del bucle interno
                    }
                }
            }
        }
    }
    
    Juego() {
    	
    	//variable principales 
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.random = new Random();
        this.disparos = new Disparo[maxDisparos];
        perder=false;
        ganar=false;
        

        // Cargar las imágenes de fondo y de las islas
        fondo= Herramientas.cargarImagen("Fondo.jpg");
        
        imagenIslaNivel1 = Herramientas.cargarImagen("IslaNivel1.png"); // Cargar imagen para nivel 1
        imagenIslaNivel2 = Herramientas.cargarImagen("IslaNivel2.png"); // Cargar imagen para nivel 2
        imagenIslaNivel3 = Herramientas.cargarImagen("IslaNivel34.png"); // Cargar imagen para nivel 3-4
        imagenIslaNivel4 = Herramientas.cargarImagen("IslaNivel34.png"); // Cargar imagen para nivel 3-4
        imagenIslaNivel5 = Herramientas.cargarImagen("IslaNivel5.png"); // Cargar imagen para nivel 5
        ganaste = Herramientas.cargarImagen("ganaste.jpg");
        perdiste = Herramientas.cargarImagen("gameover.png");
        
        
        // Inicializar las islas del nivel 1 con la imagen
        this.islas = new Isla[20];
        int anchoEntorno = 800;
        int altoEntorno = 600;

        // Nivel 1 (más bajo) - 5 islas
        double alturaNivel1 = altoEntorno - 100;
        for (int i = 0; i < 5; i++) {
            double x = (anchoEntorno / 6) * (i + 1);
            int anchoIsla = 90;
            islas[i] = new Isla(x, alturaNivel1, anchoIsla, 20, imagenIslaNivel1); // Pasa la imagen al constructor
        }

        // Nivel 2 - 4 islas
        double alturaNivel2 = altoEntorno - 200;
        for (int i = 0; i < 4; i++) {
            double x = (anchoEntorno / 5) * (i + 1);
            int anchoIsla = 120;
            islas[i + 5] = new Isla(x, alturaNivel2, anchoIsla, 20, imagenIslaNivel2);  // Usa null para islas sin imagen
        }

        // Nivel 3 - 3 islas
        double alturaNivel3 = altoEntorno - 300;
        for (int i = 0; i < 3; i++) {
            double x = (anchoEntorno / 4) * (i + 1);
            int anchoIsla = 150;
            islas[i + 9] = new Isla(x, alturaNivel3, anchoIsla, 20, imagenIslaNivel3);  // Usa null si no hay imagen específica
        }

        // Nivel 4 - 2 islas
        double alturaNivel4 = altoEntorno - 400;
        for (int i = 0; i < 2; i++) {
            double x = (anchoEntorno / 3) * (i + 1);
            int anchoIsla = 150;
            islas[i + 12] = new Isla(x, alturaNivel4, anchoIsla, 20, imagenIslaNivel4);
        }

        // Nivel 5 (superior) - 1 isla
        double alturaNivel5 = altoEntorno - 500;
        islas[14] = new Isla(anchoEntorno / 2, alturaNivel5, 100, 20, imagenIslaNivel5);  // Centrar y sin imagen

        // Crear la casa en la isla más alta
        Isla islaAlta = islas[14];
        this.casa = new Casa(islaAlta.getX(), islaAlta.getY() - 30, 40, 30);
                                     
        this.pep = new Pep(400,455);
        this.tortugas = new Tortuga[3];
        for (int i = 0; i < tortugas.length; i++) {
            double posicionX = random.nextInt(entorno.ancho()); // Posición aleatoria en el eje X
            tortugas[i] = new Tortuga(posicionX, 0); // Iniciar todas las tortugas desde Y=0
        }
      
        this.gnomo= new Gnomo[4];
        
        for (int i=0;i <4;i++) {
        	gnomo[i] = new Gnomo(395,50);
        }
        this.entorno.iniciar();
    }
    
    private void reiniciarTortugas() {
        for (int i = 0; i < tortugas.length; i++) {
            Tortuga tortuga = tortugas[i];
            
            // Solo intenta reiniciar si la tortuga no es null
            if (tortuga != null && tortuga.getY() > entorno.alto()) {
                double nuevaX = random.nextInt(entorno.ancho()); // Nueva posición aleatoria en X
                tortuga.resetearPosicion(nuevaX);
            }
        }
    }
    

    public void tick() {
    	//muestra los textos con la informacion de juego y muesta la imagen del fondo
        entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial Black", 18, Color.white);
		entorno.escribirTexto("Tortugas eliminadas: "+ eliminadas, 30, 550);
		entorno.escribirTexto("Gnomos salvados: "+ salvados, 30, 570);
		entorno.escribirTexto("Gnomos perdidos: " + perdidos, 30, 590);
		entorno.escribirTexto("Tiempo transcurrido: " + entorno.tiempo()/1000, 450, 550);
        
		casa.dibujarse(entorno); //dibuja la casa
        pep.moverse(entorno, islas); //actualizacion del movimiento de pep
        pep.dibujarse(entorno); //dibuja a pep
		
		
		// Dibujar las islas
        for (Isla isla : islas) {
            if (isla != null) {
                isla.dibujarse(entorno);
            }
        }
        // Dibujar y mover cada tortuga
        for (Tortuga tortuga : tortugas) {
            if (tortuga != null) {
                tortuga.dibujarse(entorno);
                tortuga.moverse(islas, islacasa);
                for (Gnomo gnomo : gnomo) {
                	if(tortuga.getY() >= 200) {
                		if (tortuga.TortugaColisionaConGnomo(gnomo.getX(), gnomo.getY(), gnomo.getAncho(), gnomo.getAlto())) {
                			// Acción a tomar si hay colisión con el gnomo
                			// Por ejemplo, puedes eliminar al gnomo de la lista o cambiar su posición
                			perdidos+=1;
                			gnomo.respawn(395, 50);
                			break; // Salir del bucle si se detecta una colisión con este gnomo
                		}
                    }
                }
                // Verificar colisión entre Pep y tortuga
                if (pep.pepColisionaConTortuga(tortuga.getX(), tortuga.getY(), tortuga.getAncho(), tortuga.getAlto())) {
                    perder = true; // Establecer perder a true si hay colisión
                    pep.reiniciar();
                    
                //}
                //if (pep.getY()>599) {
                	//perder = true;
                    
                }
            }
            reiniciarTortugas(); //si una tortuga es eliminada aparece de nuevo
        }
        
        
        //dibujar y mover cada gnomo
        for (int i = 0; i < 4; i++) {
			gnomo[i].dibujarse(entorno); //metodo que dibujo del gnomo
			gnomo[i].moverse(islas); //metodo de movimiento del gnomo
           if(gnomo[i].getY()>600) { //Si el gnomo cae al vacio
        	   perdidos += 1;//se suma 1 a perdida de gnomos
        	   gnomo[i].respawn(395, 50); //aparece en esta coordenada
        	   
           }
           //colision con pep
           if (pep.pepColisionaConGnomo(gnomo[i].getX(), gnomo[i].getY(), gnomo[i].getAncho(), gnomo[i].getAlto())&& pep.getY()>300) {        	   
        	   gnomo[i].respawn(395, 50); //si colisiona lo salva y vuelve a respawnear en la coordenada de la casa
        	   salvados+= 1;        	   
           }
           //metodo para saber si gano o perdio
           if(salvados >= 20) {//si Pep salva 20 gnomos gana el juego
        	   ganar = true;	//variable ganar pasa a verdadero
           }
           if(perdidos >= 25) {//si Pep pierde 25 gnomos pierde el juego
        	   perder = true; //variable perder pasa a falso
           }
        }
        if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
            for (int i = 0; i < maxDisparos; i++) {
                if (disparos[i] == null) {  // Busca una posición libre en el arreglo
                    disparos[i] = pep.disparar(); // Asigna un nuevo disparo en esa posición
                    break;
                }
            }
        }
        for (int i = 0; i < maxDisparos; i++) {
            if (disparos[i] != null) {
                disparos[i].mover();
                disparos[i].dibujar(entorno);

                // Elimina el disparo si sale de la pantalla
                if (disparos[i].getX() < 0 || disparos[i].getX() > entorno.ancho()) {
                    disparos[i] = null; // Libera la posición del disparo
                }
            }
        }
   
		
	
		 if (perder) {
		        // Dibuja la imagen de Game Over y detiene las acciones adicionales
		        entorno.dibujarImagen(perdiste, 400, 300, 0, 0.5);
		        return;
		    }
		    
		    if (ganar) { 
		    	//dibuja la imagen de Ganador y termina el juego
		        entorno.dibujarImagen(ganaste, 400, 300, 0, 2);
		        return;
		    }
        verificarColisiones(); //verifica si impacta las balas en las tortugas
    }
    
    
    
    public static void main(String[] args) {
		@SuppressWarnings("unused")
		Juego juego = new Juego();
    }
}