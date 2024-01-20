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
    mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];

    getTileImage();
    loadMap("/res/maps/world01.txt");
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

      while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
        String line = br.readLine();

        while(col < gp.maxWorldCol){
          String numbers[] = line.split(" ");
          
          int num = Integer.parseInt(numbers[col]);

          mapTileNum[col][row] = num;
          col++;
        }
        if (col == gp.maxWorldCol){
          col = 0;
          row++;
        }
      }

      br.close();

    } catch (Exception e) {
      
    }
  }

  public void draw(Graphics2D g2){
    int worldCol=0;
    int worldRow=0;


    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

      int tileNum = mapTileNum[worldCol][worldRow];

      int worldX = worldCol * gp.titleSize;
      int worldY = worldRow * gp.titleSize;
      int screenX = worldX - gp.player.worldX + gp.screenWidth/2 - gp.titleSize/2;
      int screenY = worldY - gp.player.worldY + gp.screenHeight/2 - gp.titleSize/2;

      if(worldX + gp.titleSize> gp.player.worldX - gp.player.screenX && 
         worldX - gp.titleSize< gp.player.worldX + gp.player.screenX && 
         worldY + gp.titleSize> gp.player.worldY - gp.player.screenY && 
         worldY - gp.titleSize< gp.player.worldY + gp.player.screenY){

        g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.titleSize, gp.titleSize, null);

      }

      worldCol++;

      if(worldCol == gp.maxWorldCol){
        worldCol = 0;
        worldRow++;

      }
    }

  }
}
