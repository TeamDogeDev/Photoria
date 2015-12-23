package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.model.map.ChunkBuffer;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    TiledMap map;

    public MapBuilder() {
        map = new TiledMap();
        ChunkBuffer buffer = new ChunkBuffer();
        ChunkTileLayer mapLayer = new ChunkTileLayer(new SimplexMapGenerator(), 32, 32, 1, buffer); // quick and dirty
        ChunkTileLayer mapLayer2 = new ChunkTileLayer(new SimplexMapGenerator(), 32, 32, 2, buffer); // quick and dirty
        DebugChunkLayer debugLayer = new DebugChunkLayer(32, 32, buffer); // quick and dirty
        debugLayer.setVisible(false);
        map.getLayers().add(mapLayer);
        map.getLayers().add(mapLayer2);
        map.getLayers().add(debugLayer);
    }

    public TiledMap getTiledMap() {
        return map;
    }

}
