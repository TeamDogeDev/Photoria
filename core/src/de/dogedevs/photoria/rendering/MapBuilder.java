package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    TiledMap map;

    public MapBuilder() {
        map = new TiledMap();
        SimplexMapGenerator simplexMapGenerator = new SimplexMapGenerator();
        DynamicMapTileLayer mapLayer = new DynamicMapTileLayer(simplexMapGenerator, 32, 32); // quick and dirty
        DynamicMapTileLayer mapCornerLayer = new DynamicMapTileLayer(simplexMapGenerator, 32, 32); // quick and dirty
        DynamicMapTileLayer debugLayer = new DynamicMapTileLayer(new ChunkDebugMapGenerator(), 32, 32); // quick and dirty

        map.getLayers().add(mapLayer);
        map.getLayers().add(mapCornerLayer);
        map.getLayers().add(debugLayer);
    }

    public TiledMap getTiledMap() {
        return map;
    }

}
