package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.model.ChunkBuffer;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    TiledMap map;

    public MapBuilder() {
        map = new TiledMap();
        ChunkBuffer buffer = new ChunkBuffer();
        DynamicMapTileLayer mapLayer = new DynamicMapTileLayer(new SimplexMapGenerator(), 32, 32, 1, buffer); // quick and dirty
        DynamicMapTileLayer mapLayer2 = new DynamicMapTileLayer(new SimplexMapGenerator(), 32, 32, 2, buffer); // quick and dirty
        DynamicMapTileLayer debugLayer = new DynamicMapTileLayer(new ChunkDebugMapGenerator(), 32, 32, 1, null); // quick and dirty
        map.getLayers().add(mapLayer);
        map.getLayers().add(mapLayer2);
        map.getLayers().add(debugLayer);
    }

    public TiledMap getTiledMap() {
        return map;
    }

}
