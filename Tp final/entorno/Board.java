package entorno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Graphics2D g2d;
	private InterfaceJuego juego;
	private boolean[] teclas;
	private boolean[] teclas_momento;    
	private boolean[] teclas_levantadas;  
	private boolean iniciado;
	private int tickNumber;
	private int paintNumber;
	private int mouseX;
	private int mouseY;
	private boolean[] botones;
	private boolean[] botones_momento;
	private boolean[] botones_levantados;
	private boolean mouseIn;
	private long startTime;
	


	public Board(InterfaceJuego j)
	{
		iniciado = false;
		juego = j;
		teclas = new boolean[256];
		teclas_momento = new boolean[256];
		teclas_levantadas = new boolean[256];
		botones = new boolean[4];
		botones_momento = new boolean[4];
		botones_levantados = new boolean[4];
		tickNumber=0;
		paintNumber=0;

		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		addMouseMotionListener(new MMoveAdapter());

		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);        

	}

	public void iniciar()
	{
		iniciado = true;
		startTime = System.currentTimeMillis();
		timer = new Timer(10, this);
		timer.start(); 
		
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g2d = (Graphics2D)g;

		try
		{
			if (iniciado)
				juego.tick();

			liberarTeclasMomento();
			liberarMouse() ;
			

		}
		catch(Exception e)
		{
			e.printStackTrace();

		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		g2d = null;
	}

	private void liberarTeclasMomento() 
	{
		for (int i = 0; i < teclas_momento.length; i++) {
			teclas_momento[i] = false;
			teclas_levantadas[i]=false;
		}

	}

	private void liberarMouse() 
	{
		for (int i = 0; i < botones_momento.length; i++) {
			botones_momento[i] = false;
			botones_levantados[i]=false;
		}
	}


	// Llamado cuando salta el timer
	public void actionPerformed(ActionEvent e)
	{
		tickNumber++;
		repaint();  
	}

	private class TAdapter extends KeyAdapter
	{
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			if( 0 <= key && key < teclas.length )
			{
				teclas[key] = false;
				teclas_momento[key] = false;
				teclas_levantadas[key]=true;
			}
		}

		public void keyPressed(KeyEvent e)
		{


			int key = e.getKeyCode();
			//System.out.println(key);

			if( 0 <= key && key < teclas.length )
			{
				teclas_momento[key] = false;
				teclas_levantadas[key]=false;

				if (!teclas[key]) {
					teclas_momento[key] = true;
				}

				teclas[key] = true;
			}
		}
	}

	private class MAdapter extends MouseAdapter   {


		public void mousePressed(MouseEvent e) {

			int tipoBoton=e.getButton();	
			//System.out.println("se apreto boton" + tipoBoton );


			if( 0 <= tipoBoton && tipoBoton < botones.length )
			{
				botones_momento[tipoBoton] = false;
				botones_levantados[tipoBoton]=false;

				if (!botones[tipoBoton]) {
					botones_momento[tipoBoton] = true;
				}

				botones[tipoBoton] = true;
			}

		}

		public void mouseReleased(MouseEvent e) {
			int tipoBoton = e.getButton();
			// System.out.println("se libero boton" + tipoBoton );
			if( 0 <= tipoBoton && tipoBoton < botones.length )
			{
				botones[tipoBoton] = false;
				botones_momento[tipoBoton] = false;
				botones_levantados[tipoBoton]=true;
			}

		}

		public void mouseEntered(MouseEvent e)	{
			mouseIn=true;
		}
		
		public void mouseExited(MouseEvent e)	{
			mouseIn=false;
		}
	}

	private class MMoveAdapter implements MouseMotionListener   {

		public void mouseMoved(MouseEvent e) {
			
			mouseX=e.getX();
			mouseY=e.getY();
		}


		public void mouseDragged(MouseEvent e) {
			
			mouseX=e.getX();
			mouseY=e.getY();

		}
	}



	public Graphics2D getG2D()
	{
		return g2d;
	}


	public boolean estaPresionada(char key) 
	{
		if (97 <= key && key <= 122) // la paso a mayusculas
			key -= 32;

		if( key < 0 || key >= teclas.length )
			throw new RuntimeException( "Error! Se consult� si la tecla " + (int) key + " est� presionada, pero esa tecla no existe." );

		return teclas[key];
	}
	public boolean estaPresionado(int bot) 
	{

		if( bot < 0 || bot >= botones.length )
			throw new RuntimeException( "Error! Se consulta si el boton " + bot + " esta presionado, pero  no existe." );

		return botones[bot];
	}

	public boolean sePresiono(char key) 
	{
		if (97 <= key && key <= 122) // la paso a mayusculas
			key -= 32;

		if( key < 0 || key >= teclas_momento.length )
			throw new RuntimeException( "Error! Se consult� si la tecla " + (int) key + " est� presionada, pero esa tecla no existe." );

		return teclas_momento[key];
	}


	public boolean sePresionoBoton(int bot) 
	{

		if( bot < 0 || bot >= botones_momento.length )
			throw new RuntimeException( "Error! Se consulto si el boton " + bot + " esta presionada, pero esa tecla no existe." );

		return botones_momento[bot];
	}


	public boolean seLevanto(char key) 
	{
		if (97 <= key && key <= 122) // la paso a mayusculas
			key -= 32;

		if( key < 0 || key >= teclas_momento.length )
			throw new RuntimeException( "Error! Se consult� si la tecla " + (int) key + " est� presionada, pero esa tecla no existe." );

		return teclas_levantadas[key];
	}


	public boolean seLevantoboton(int bot) 
	{
		if( bot < 0 || bot >= botones_momento.length )
			throw new RuntimeException( "Error! Se consulto si el boton " + bot + " esta presionada, pero esa tecla no existe." );

		return botones_levantados[bot];
	}

	public int mouseX() {
		return mouseX;
	}

	public int mouseY() {
		return mouseY;
	}

	public int numeroDeTick() {
		return tickNumber;
	}

	public boolean mousePresent() {
		return this.mouseIn;
	}
	
	public long tiempo() {
		return System.currentTimeMillis()-this.startTime;
		
	}
}