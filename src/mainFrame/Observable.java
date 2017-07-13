package mainFrame;

import map.terrain.TerrainType;

import javax.swing.*;
import java.awt.*;

public interface Observable
{
    ImageIcon getImageIcon();

    boolean isAvailableTerrain(TerrainType terrain);

    int getX();

    int getY();

    Color getColor();
    Shape getOval(int xRoot, int yRoot, int tileSize, int cotang);
}