package de.dogedevs.photoria.rendering.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.model.map.ChunkBuffer;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapBuilder {

    TiledMap map;
    ChunkBuffer buffer;

    public MapBuilder() {
        map = new TiledMap();
        buffer = new ChunkBuffer();
        LiquidChunkTileLayer liquidLayer = new LiquidChunkTileLayer(new SimplexMapGenerator(), 32, 32, 3, buffer);
        ChunkTileLayer mapLayer = new ChunkTileLayer(new SimplexMapGenerator(), 32, 32, 1, buffer);
        ChunkTileLayer mapLayer2 = new ChunkTileLayer(new SimplexMapGenerator(), 32, 32, 2, buffer);
        DebugChunkLayer debugLayer = new DebugChunkLayer(32, 32, buffer);

        debugLayer.setVisible(Config.showDebugLayer);
        debugLayer.setName("debug");
        liquidLayer.setName("liquid");
        mapLayer.setName("ground");
        mapLayer2.setName("ground2");

        map.getLayers().add(liquidLayer);
        map.getLayers().add(mapLayer);
        map.getLayers().add(mapLayer2);
        map.getLayers().add(debugLayer);
    }

    public TiledMap getTiledMap() {
        return map;
    }

    public ChunkBuffer getBuffer() {
        return buffer;
    }
}
