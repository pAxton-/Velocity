package org.pAxDev.Logic;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Created by Lance Paxton on 2/23/16.
 */
public class Grid {
    private GridSquare[][] gridSquares;
    private int size;

    public Grid(float _width, float _height, float _incPx, float _incPy, int size, LoadTexture lt) {
        gridSquares = new GridSquare[size][size];
        float width = _width;
        float height = _height;
        float incPx = _incPx;
        float incPy = _incPy;
        this.size = size;
        int ylop = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                ylop++;
                gridSquares[x][y] = new GridSquare(
                                                          new Vector3f(incPx, incPy, 1),
                                                          new Vector2f(width, height),
                                                          new Vector4f(1, 1, 1, 0.05f),
                                                          null,
                                                          lt

                );
                incPx = incPx + width;
                if (ylop > size - 1) {
                    incPx = _incPx;
                    incPy = incPy + height;
                    ylop = 0;
                }
                if ( y == (size-1) || y == 0) {
                    gridSquares[x][y].setGridType(GridType.BORDER);
                    gridSquares[x][y].color = new Vector4f(1,0,1,.5f);
                    gridSquares[x][y].setPosition(new Vector3f(gridSquares[x][y].getPositionX(), gridSquares[x][y].getPositionY(), 3));
                }
                if ( x == (size-1) || x == 0) {
                    gridSquares[x][y].setGridType(GridType.BORDER);
                    gridSquares[x][y].color = new Vector4f(1,0,1,.5f);
                    gridSquares[x][y].setPosition(new Vector3f(gridSquares[x][y].getPositionX(), gridSquares[x][y].getPositionY(), 3));
                }
            }
        }
    }
    public int getSize() {
        return size;
    }
    public GridSquare[][] getGridSquares() {
        return gridSquares;
    }
}
