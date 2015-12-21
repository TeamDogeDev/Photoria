package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.generators.PerlinMapGenerator;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    TiledMap map;

    public MapBuilder() {
        map = new TiledMap();
        DynamicMapTileLayer mapLayer = new DynamicMapTileLayer(new PerlinMapGenerator(), 32, 32); // quick and dirty
        DynamicMapTileLayer debugLayer = new DynamicMapTileLayer(new ChunkDebugMapGenerator(), 32, 32); // quick and dirty
        map.getLayers().add(mapLayer);
        map.getLayers().add(debugLayer);
    }

    public TiledMap getTiledMap() {
        return map;
    }

}
