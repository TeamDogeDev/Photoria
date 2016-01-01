package de.dogedevs.photoria.model.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.rendering.map.ChunkTileLayer;
import de.dogedevs.photoria.rendering.map.DebugChunkLayer;
import de.dogedevs.photoria.rendering.map.LiquidChunkTileLayer;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapCompositor {

    TiledMap map;
    ChunkBuffer buffer;

    public MapCompositor() {
        map = new TiledMap();
        buffer = new ChunkBuffer();
        LiquidChunkTileLayer liquidLayer = new LiquidChunkTileLayer(32, 32, 3, buffer);
        ChunkTileLayer mapLayer = new ChunkTileLayer(32, 32, 1, buffer);
        ChunkTileLayer mapLayer2 = new ChunkTileLayer(32, 32, 2, buffer);
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
