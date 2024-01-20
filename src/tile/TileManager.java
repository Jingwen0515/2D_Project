package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
  
  GamePanel gp;
  Tile[] tiles;
  int mapTileNum [][];

  public TileManager(GamePanel gp) {
    this.gp = gp;
    tiles = new Tile[10]; //create 10 tiles
    mapTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];

    getTileImage();
    loadMap("/res/maps/map.txt");
  }

  public void getTileImage(){
    try {
      tiles[0] = new Tile();
      tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass01.png"));

      tiles[1] = new Tile();
      tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));

      tiles[2] = new Tile();
      tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water01.png"));

      tiles[3] = new Tile();
      tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/earth.png"));

      tiles[4] = new Tile();
      tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/tree.png"));

      tiles[5] = new Tile();
      tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/sand.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadMap(String filePath){
    try {
      InputStream is = getClass().getResourceAsStream(filePath);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      int col = 0;
      int row = 0;

      while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
        String line = br.readLine();

        while(col < gp.maxScreenCol){
          String numbers[] = line.split(" ");
          
          int num = Integer.parseInt(numbers[col]);

          mapTileNum[col][row] = num;
          col++;
        }
        if (col == gp.maxScreenCol){
          col = 0;
          row++;
        }
      }

      br.close();

    } catch (Exception e) {
      
    }
  }

  public void draw(Graphics2D g2){
    int col=0;
    int row=0;
    int x=0;
    int y=0;

    while(col < gp.maxScreenCol && row < gp.maxScreenRow){

      int tileNum = mapTileNum[col][row];

      g2.drawImage(tiles[tileNum].image, x, y, gp.titleSize, gp.titleSize, null);

      col++;
      x += gp.titleSize;

      if(col == gp.maxScreenCol){
        col = 0;
        row++;
        x = 0;
        y += gp.titleSize;
      }
    }

  }
}
