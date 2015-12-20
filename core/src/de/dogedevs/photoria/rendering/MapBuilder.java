package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.SimpleMapGenerator;
import de.dogedevs.photoria.rendering.tiles.Tile;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    private AbstractMapGenerator mapGenerator;

    public MapBuilder() {
        mapGenerator = new SimpleMapGenerator();
    }

    public TiledMap getTiledMap() {
        int size = 10;

        int[][] generatedMap = mapGenerator.generate(10, 10, size);

        TiledMap tm = new TiledMap();
        TiledMapTileLayer mapLayer = new TiledMapTileLayer(size<<1, size<<1, 32, 32); // quick and dirty

        for (int row = 0; row < size << 1; row++) {
            for (int col = 0; col < size << 1; col++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                switch(generatedMap[row][col]) {
                    case 0 : cell.setTile(Tile.GREEN); break;
                    case 1 : cell.setTile(Tile.RED); break;
                    default: cell.setTile(Tile.BLUE);
                }
                mapLayer.setCell(row, col, cell);
            }
        }

        tm.getLayers().add(mapLayer);
        return tm;
    }

}
