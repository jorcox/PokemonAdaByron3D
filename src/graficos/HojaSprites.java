package graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites {

	int width;
	int height;
	int rows;
	int columns;
	BufferedImage[] sprites;

	public void cargarHoja(File file, int rows, int columns) throws IOException {
	      this.rows = rows;
	      this.columns = columns;
	      sprites = new BufferedImage[rows * columns];
	      

	      BufferedImage spriteSheet = ImageIO.read(file);
	      
	      this.width = spriteSheet.getWidth();
	      this.height = spriteSheet.getHeight();

	      
	      for(int i = 0; i < rows; i++) {
	         for(int j = 0; j < columns; j++) {
	            sprites[(i * columns) + j] = spriteSheet.getSubimage((j*(width/columns)),(i*(height/rows)),
	            		(width/columns)-1, (height/rows)-1);
	         }
	      }
	   }
	

}
