package graficos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



import sonido.Reproductor;

@SuppressWarnings("serial")
public class Game extends JPanel {

	static boolean show = true;
	private static int fase = 0;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static Reproductor rep= new Reproductor();
	private int i = 0;

	public Game() {
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		setFocusable(true);
	}

	
	public void setFase(int fase){
		this.fase = fase;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		switch(fase){
		case 0:
			super.paint(g);			
			Font font = null;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/font.ttf")));
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			g2d.setFont(new Font(font.getName(), Font.BOLD, 30));
			try {
				Image backgroundImage = ImageIO.read(new File("resources/portada.jpg"));
				g.drawImage(backgroundImage, 0, 0, screenSize.width, screenSize.height, this);
				if (show) {
					g2d.drawString("Pulsa INTRO para continuar", (int) (screenSize.width*0.33), (int) (screenSize.height*0.85));
				} else {
					g.drawImage(backgroundImage, 0, 0, screenSize.width, screenSize.height, this);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			HojaSprites spr = new HojaSprites();
			try {
				spr.cargarHoja(new File("resources/pajaro.png"), 1, 6);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			g.drawImage(spr.sprites[i], (int) (screenSize.getWidth()*0.8), (int) (screenSize.getHeight()*0.8), spr.sprites[i].getWidth(), spr.sprites[i].getHeight(), this);
			i++;
			if (i==6) 
				i=0;
			
			break;
		case 1:
			g.setColor(Color.cyan);
			g.fillRect(0, 0, screenSize.width, screenSize.height);
			spr = new HojaSprites();
			try {
				spr.cargarHoja(new File("resources/Celda.png"), 10, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(spr.sprites[0], 0, 0, spr.sprites[0].getWidth(), spr.sprites[0].getHeight(), this);			
			break;
		}
		

	}

	public class MyKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()));
			if(KeyEvent.getKeyText(e.getKeyCode()).equals("Intro")){
				fase = 1;
			}
			if(KeyEvent.getKeyText(e.getKeyCode()).equals("Abajo")){
				fase = 1;
			}
			if(KeyEvent.getKeyText(e.getKeyCode()).equals("Arriba")){
				fase = 1;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("keyReleased=" + KeyEvent.getKeyText(e.getKeyCode()));
		}
	}

	/*
	 * @Override public void paintComponent(Graphics g) {
	 * super.paintComponent(g);
	 * 
	 * try { Image backgroundImage = ImageIO.read(new
	 * File("resources/portada.jpg")); g.drawImage(backgroundImage, 0, 0, this);
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Pokemon Ada Byron");
		Game game = new Game();
		frame.add(game);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.repaint();
		pantallaInicio(game);
		menuPrincipal(game);
		
	}

	private static void pantallaInicio(Game game) throws InterruptedException {
		rep.reproducir(new File("resources/title.wav"));			
		while (fase==0) {
			Thread.sleep(400);
			show = (show == true) ? false : true;
			game.repaint();
		}
	}
	
	private static void menuPrincipal(Game game) {
		rep.stop();
		while (fase==1){
			game.repaint();
		}
		
	}

}