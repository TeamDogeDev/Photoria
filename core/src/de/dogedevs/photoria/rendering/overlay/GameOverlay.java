package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.HealthComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {

    private static final String HUD_PATH = "./hud.png";
    private static final String HUDBAR_PATH = "./hudBars.png";
    private static final String NET_PATH = "./net.png";

    private Texture textBox = new Texture(Gdx.files.internal("./textbox.png"));

    private BitmapFont font;
    private Texture hudTexture = new Texture(Gdx.files.internal(HUD_PATH));
    private static final int HUD_TILE_WIDTH = 720>>1;
    private static final int HUD_TILE_HEIGHT = 32;

    private Texture hudBarTexture = new Texture(Gdx.files.internal(HUDBAR_PATH));
    private Texture itemSlotTexture = new Texture(Gdx.files.internal("./itemSlot.png"));

    private TextureRegion[][] hudBars = TextureRegion.split(hudBarTexture, 1, HUD_TILE_HEIGHT);
    private TextureRegion healthBar = hudBars[0][0];
    private TextureRegion energyBar = hudBars[0][1];

    private Texture netTexture = new Texture(Gdx.files.internal(NET_PATH));
    private TextureRegion[][] hudParts = TextureRegion.split(hudTexture, HUD_TILE_WIDTH, HUD_TILE_HEIGHT);

    private TextureRegion health = hudParts[0][0];
    private TextureRegion energy = hudParts[0][1];

    private Vector2 netOffset = new Vector2(Gdx.graphics.getWidth()-netTexture.getWidth(), Gdx.graphics.getHeight()-netTexture.getHeight());

    private Vector2 netCenter = new Vector2(128,128);
    private Vector2 stat0 = new Vector2(128,244);
    private Vector2 stat1 = new Vector2(250,156);
    private Vector2 stat2 = new Vector2(204,12);
    private Vector2 stat3 = new Vector2(52,12);
    private Vector2 stat4 = new Vector2(6,156);
    private Entity player;

    private ShaderProgram bloomShader;

    private PlayerComponent playerComponent;
    private HealthComponent healthComponent;
    private EnergyComponent energyComponent;
    private ElementsComponent elementsComponent;

    private Batch bloomBatch = new SpriteBatch();


    private float offset = 10;
    private float spacing = 5;
    private int numSlots = 6;
    private int itemBarWidth = (int) (numSlots * (itemSlotTexture.getWidth()+spacing));

    public GameOverlay(Entity playerEntity) {
        this.player = playerEntity;
        initShader();
        initComponents();
        init();
    }

    private void initShader() {
        ShaderProgram.pedantic = false;
        bloomShader = new ShaderProgram(Gdx.files.internal("./shaders/vertexStub.vsh"), Gdx.files.internal("./shaders/bloomShader.fsh"));
        MainGame.log(bloomShader.isCompiled() ? "WaterShader compiled" : bloomShader.getLog());

//        bloomBatch.setShader(bloomShader);
    }

    private void initComponents() {
        playerComponent = ComponentMappers.player.get(player);
        healthComponent = ComponentMappers.health.get(player);
        energyComponent = ComponentMappers.energy.get(player);
        elementsComponent = ComponentMappers.elements.get(player);
    }

    @Override
    public void init() {
        font = new BitmapFont();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        renderHealth();
        renderStats();
    }

    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    private void renderStats() {
        batch.begin();
        batch.draw(netTexture, netOffset.x, netOffset.y);
//        batch.draw(textBox, (Gdx.graphics.getWidth() - textBox.getWidth())>>1, 32);
        batch.end();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN.add(Color.YELLOW));
        shapeRenderer.polygon(statsToVertices(elementsComponent.green/20, elementsComponent.red/20, elementsComponent.yellow/20, elementsComponent.blue/20, elementsComponent.purple/20));

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    private float[] statsToVertices(float st0, float st1, float st2, float st3, float st4) {
//        Vector2 posst0 = stat0.cpy().add(netCenter).add(netOffset).scl(1f);
        Vector2 posst0 = netOffset.cpy().add(netCenter.cpy().add(stat0.cpy().sub(netCenter).scl(st0)));
        Vector2 posst1 = netOffset.cpy().add(netCenter.cpy().add(stat1.cpy().sub(netCenter).scl(st1)));
        Vector2 posst2 = netOffset.cpy().add(netCenter.cpy().add(stat2.cpy().sub(netCenter).scl(st2)));
        Vector2 posst3 = netOffset.cpy().add(netCenter.cpy().add(stat3.cpy().sub(netCenter).scl(st3)));
        Vector2 posst4 = netOffset.cpy().add(netCenter.cpy().add(stat4.cpy().sub(netCenter).scl(st4)));
        return new float[]{posst0.x, posst0.y, posst1.x, posst1.y, posst2.x, posst2.y, posst3.x, posst3.y, posst4.x, posst4.y, };
    }

    private void renderHealth() {
        health.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bloomBatch.begin();
        float healthScale = (float) healthComponent.health / healthComponent.maxHealth;
        float energyScale = (float) energyComponent.energy / energyComponent.maxEnergy;
        bloomBatch.draw(healthBar, offset, Gdx.graphics.getHeight()-(health.getRegionHeight()+offset), health.getRegionWidth() * healthScale, healthBar.getRegionHeight());
        bloomBatch.draw(energyBar, offset, Gdx.graphics.getHeight()-((energy.getRegionHeight()*2+offset+spacing)), energy.getRegionWidth() * energyScale, energyBar.getRegionHeight());
        bloomBatch.end();

        batch.begin();
        batch.draw(health, offset, Gdx.graphics.getHeight()-(health.getRegionHeight()+offset));
        batch.draw(energy, offset, Gdx.graphics.getHeight()-((energy.getRegionHeight()*2+offset+spacing)));

        for(int i = 0; i < numSlots; i++) {
            batch.draw(itemSlotTexture, ((itemSlotTexture.getWidth()+ spacing)*i) + ((Gdx.graphics.getWidth()-itemBarWidth)>>1), Gdx.graphics.getHeight()-itemSlotTexture.getHeight()-offset);
        }
        batch.end();


    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        hudTexture.dispose();
        netTexture.dispose();
        hudBarTexture.dispose();
        textBox.dispose();
    }
}
