package entorno;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

import javax.swing.JFrame;

public class Entorno extends JFrame
{
	private static final double version = 2.01;
	private static final long serialVersionUID = 1L;
	private Board board;
    private InterfaceJuego juego;
    
    
    public final char TECLA_ARRIBA = 38;
    public final char TECLA_ABAJO = 40;
    public final char TECLA_DERECHA = 39;
    public final char TECLA_IZQUIERDA = 37;
    public final char TECLA_ENTER = 10;
    public final char TECLA_CTRL = 17;
    public final char TECLA_ALT = 18;
    public final char TECLA_SHIFT = 16;
    public final char TECLA_ESPACIO = 32;
    public final char TECLA_INSERT = 155;
    public final char TECLA_DELETE = 127;
    public final char TECLA_INICIO = 36;
    public final char TECLA_FIN = 35;
    public final char TECLA_ESCAPE = 27;
    
    public final int BOTON_IZQUIERDO = MouseEvent.BUTTON1;
    public final int BOTON_CENTRAL = MouseEvent.BUTTON2;
    public final int BOTON_DERECHO = MouseEvent.BUTTON3;
    
    
    public final int NEGRITA = Font.BOLD;
    public final int ITALICA = Font.ITALIC;
    public final int NORMAL = Font.PLAIN;
    
    public String[] fontDisponibles;
    
    
    
    /**
     * Construye un entorno y da comienzo al juego.
     * @param juego El juego que define las reglas del combate
     * @param titulo Un t�tulo para la ventana del juego
     * @param ancho El ancho de la ventana
     * @param alto El alto de la ventana
     */
	public Entorno(InterfaceJuego juego, String titulo, int ancho, int alto)
    {
		this.juego = juego;		
		board = new Board(this.juego);
		board.setSize(ancho, alto);
		fontDisponibles=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		
		
//		this.setLayout(new BorderLayout());		
        add(board, BorderLayout.CENTER);
        this.pack();        
        Insets ins = this.getInsets();
        setSize(ancho + ins.left + ins.right, alto + ins.bottom + ins.top);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle(titulo);
        setVisible(true);
        
        System.out.println("Entorno de desarrollo 2D para Java de Programacion I - UNGS (version " + version + ")  2024");
    }
	
	
	
	
	
	/**
	 * Inicia el entorno. A partir de este llamado, se ejecutar� la funci�n
	 * tick() del Juego cada cierta cantidad de milisegundos.
	 */
	public void iniciar()
	{
		board.iniciar();
		
	}
	
	/**
	 * @return El ancho de la pantalla
	 */
	public int ancho()
	{
		return board.getWidth();
	}
	
	/**
	 * @return El alto de la pantalla
	 */
	public int alto()
	{
		return board.getHeight();
	}

	/**
	 * Dibuja una imagen en la pantalla en las coordenadas especificadas 
	 * y la rota en el �ngulo especificado.
	 * @param imagen La imagen a dibujar
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param angulo El Angulo de rotacion para la imagen (medido en radianes!)
	 */
	public void dibujarImagen(Image imagen, double x, double y, double angulo)
	{
		dibujarImagen(imagen, x, y, angulo, 1.0);
	}
	
	/**
	 * Dibuja una imagen en la pantalla en las coordenadas especificadas 
	 * y la rota en el angulo especificado.
	 * @param imagen La imagen a dibujar
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param angulo El �ngulo de rotaci�n para la imagen (�medido en radianes!)
	 * @param escala El factor de escala a utilizar para agrandar o achicar la imagen
	 */
	public void dibujarImagen(Image imagen, double x, double y, double angulo, double escala)
	{
		dibujarImagenConCentro(imagen, x, y, imagen.getWidth(null)/2, imagen.getHeight(null)/2, angulo, escala);
	}
	
	/**
	 * Dibuja una imagen en la pantalla en las coordenadas especificadas 
	 * y la rota en el �ngulo especificado. Se puede especificar en qu� 
	 * coordenadas (relativas a la imagen) se desea que est� el centro de 
	 * rotacion de la imagen.
	 * @param imagen La imagen a dibujar
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param angulo El �ngulo de rotacion para la imagen (medido en radianes!)
	 * @param escala El factor de escala a utilizar para agrandar o achicar la imagen
	 */
	public void dibujarImagenConCentro(Image imagen, double x, double y, double centro_x, double centro_y, double angulo, double escala)
	{
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;
		
        AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
        transform.concatenate( AffineTransform.getRotateInstance(angulo) );
        transform.concatenate( AffineTransform.getTranslateInstance(-escala*centro_x, -escala*centro_y) );
        if (escala != 1.0)
        	transform.concatenate( AffineTransform.getScaleInstance(escala, escala) );

        g2d.drawImage(imagen, transform, null);
	}
	/**
	 * Dibuja un circulo en las coordenadas especificadas
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param diametro El diemetro del curculo
	 * @param color El color del circulo
	 */
	public void dibujarCirculo(double x, double y, double diametro, Color color)
	{
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;

		Ellipse2D.Double circle = new Ellipse2D.Double(x-diametro/2, y-diametro/2, diametro, diametro);
		g2d.setPaint(color);
		g2d.fill(circle);
	}

	/**
	 * Dibuja un rectangulo con el ancho y el alto especificados, rotado seg�n el �ngulo dado 
	 * cuyo centro se encuentra en las coordenadas especificadas.
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param ancho El ancho del rectangulo
	 * @param alto El alto del rectangulo
	 * @param angulo El �ngulo de rotacion (�medido en radianes!)
	 * @param color El color del rectagulo
	 */
	public void dibujarRectangulo(double x, double y, double ancho, double alto, double angulo, Color color)
	{
		// Construyo el rectangulo
		Shape rect = new Rectangle((int) x, (int) y, (int) ancho, (int) alto);
				
		// Lo roto
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);    
        at.concatenate( AffineTransform.getRotateInstance(angulo) );
        at.concatenate( AffineTransform.getTranslateInstance(-x-ancho/2, -y-alto/2) );		
		Shape rect_rotado = at.createTransformedShape(rect);

		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;
		
		g2d.setPaint(color);
		g2d.fill(rect_rotado);
	}
	
	/**
	 * Dibuja un triangulo en las coordenadas especificadas y rotado seg�n el �ngulo dado.
	 * @param x La coordenada x
	 * @param y La coordenada y
	 * @param altura La altura del triangulo
	 * @param base La base del triangulo
	 * @param angulo El �ngulo de rotacion (medido en radianes!)
	 * @param color El color del tringulo
	 */
	public void dibujarTriangulo(double x, double y, int altura, int base, double angulo, Color color)
	{
		// Dibujo el triangulo con un pol�gono
		Polygon t = new Polygon();
		t.addPoint((int)x - altura/2, (int)y - base/2);
		t.addPoint((int)x + altura/2, (int)y);
		t.addPoint((int)x - altura/2, (int)y + base/2);

		
		// Lo roto
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);    
        at.rotate(angulo);
        at.concatenate( AffineTransform.getTranslateInstance(-x, -y) );		
		Shape t_rotado = at.createTransformedShape(t);

		// Y lo dibujo
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;

		g2d.setPaint(color);
		g2d.fill(t_rotado);
	}
	
	/**
	 * Escribe un texto en las coordenadas especificadas de la pantalla.
	 * @param texto El texto a escribir
	 * @param x La coordenada x
	 * @param y La coordenada y
	 */
	public void escribirTexto(String texto, double x, double y)
	{
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;
		
        g2d.drawString(texto, (int) x, (int) y);
    }
	
	/**
	 * Cambia la fuente para las proximas escrituras de texto.
	 * @param font El nombre de la fuente
	 * @param tamano El tamaño para las letras del texto
	 * @param color El color del texto
	 */
	public void cambiarFont(String font, int tamano, Color color)
	{
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;
		
		g2d.setColor(color);
		g2d.setFont(new Font(font, Font.PLAIN, tamano));
    }
	
	/**
	 * Cambia la fuente para las proximas escrituras de texto.
	 * @param font El nombre de la fuente
	 * @param tamano El tamaño para las letras del texto
	 * @param color El color del texto
	 * @param tipo El tipo de texto: normal, negrita,etc,
	 */
	public void cambiarFont(String font, int tamano, Color color, int tipo)
	{
		Graphics2D g2d = board.getG2D();
		
		if( g2d == null )
			return;
		
		g2d.setColor(color);
		g2d.setFont(new Font(font, tipo, tamano));
    }
	
	
	
	
	
	/**
	 * Indica si la tecla especificada esta siendo presionada en este momento. 
	 * @param key La tecla a consultar
	 * @return Verdadero si la tecla esta siendo presionada y Falso en caso contrario.
	 */
	public boolean estaPresionada(char key)
	{
		return board.estaPresionada(key);
	}
	
	/**
	 * Indica si la tecla especificada se acaba de presionar en este instante de 
	 * tiempo (i.e., en este tick()). 
	 * @param key La tecla a consultar
	 * @return Verdadero si la tecla se presiono en este tick().
	 */
	public boolean sePresiono(char key)
	{
		return board.sePresiono(key);
	}
	
	/**
	 * Indica si la tecla especificada se acaba de levantar en este instante de 
	 * tiempo (i.e., en este tick()). 
	 * @param key La tecla a consultar
	 * @return Verdadero si la tecla se levanto en este tick().
	 */
	public boolean seLevanto(char key)
	{
		return board.seLevanto(key);
	}
	
	
	
	/**
	 * * Indica si el boton especificado se acaba de presionar en este instante de 
	 * tiempo (i.e., en este tick()). 
	 * @param bot El boton a consultar s
	 * @return Verdadero si el boton esta siendo presionado y Falso en caso contrario.
	 */
		public boolean sePresionoBoton(int bot)
	{
		return board.sePresionoBoton(bot);
	}
	
	/**
	 * Indica si el boton especificado esta siendo presionado en este momento. 
	 * @param boton El boton a consultar
	 * @return Verdadero si el botona esta siendo presionado y Falso en caso contrario.
	 */
	public boolean estaPresionado(int bot)
	{
		return board.estaPresionado(bot);
	}
	
	/**
	 * Indica si el boton especificado se acaba de levantar en este instante de 
	 * tiempo (i.e., en este tick()). 
	 * @param boton El boton a consultar
	 * @return Verdadero si la tecla se levanto en este tick().
	 */
	public boolean seLevantoBoton(int bot)
	{
		return board.seLevantoboton(bot);
	}
	
	
	
	/**
	 * @return Coordenada x del mouse
	 */
	public int mouseX() {
		return board.mouseX();
	}
	
	
	/**
	 * @return Coordenada y del mouse
	 */
	public int mouseY() {
		return board.mouseY();
	}
	
	
	/**
	 * @return True si el puntero del mouse esta dentro de la ventana del entorno
	 */
	public boolean mousePresente() {
		return board.mousePresent();
	}


	/**
	 * Permite cambiar el color de fondo. 
	 * @param c color a aplicar
	 */
		public void colorFondo(Color c) {
		
		board.setBackground(c);
		}
		
		
		/**
		 * @return Cantidad de ticks transcurridos desde el inicio del juego
		 */
		public int numeroDeTick() {
			return board.numeroDeTick();
			
		}
	
		/**
		 * @return Cantidad de milisegundos transcurridos desde el inicio del juego
		 */
		public int tiempo() {
			return (int)board.tiempo();
			
		}
	
}
