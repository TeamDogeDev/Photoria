package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.utils.Utils;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.BitmapFonts;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {


//    private static final String HUD_PATH = "./hud/hud.png";
//    private static final String HUDBAR_PATH = "./hud/hudBars.png";
//    private static final String NET_PATH = "./hud/net.png";

    private Texture textBox = AssetLoader.getTexture(Textures.HUD_TEXTBOX);
    private Texture okButtonSheet = AssetLoader.getTexture(Textures.HUD_OK_BUTTON);
    private TextureRegion[] okFrames = TextureRegion.split(okButtonSheet, okButtonSheet.getWidth()/10, okButtonSheet.getHeight())[0];
    private Animation okButtonAnimation = new Animation(0.05f, okFrames);


    private BitmapFont font;
    private Texture hudTexture = AssetLoader.getTexture(Textures.HUD_BARS);
    private static final int HUD_TILE_WIDTH = 720 >> 1;
    private static final int HUD_TILE_HEIGHT = 32;

    private Texture hudBarTexture = AssetLoader.getTexture(Textures.HUD_BARS_FILL);
    private Texture itemSlotTexture = AssetLoader.getTexture(Textures.HUD_ITEM_SLOTS);

    private TextureRegion[][] hudBars = TextureRegion.split(hudBarTexture, 1, HUD_TILE_HEIGHT);
    private TextureRegion healthBar = hudBars[0][0];
    private TextureRegion energyBar = hudBars[0][1];

    private Texture netTexture = AssetLoader.getTexture(Textures.HUD_RADAR_CHART);
    private TextureRegion[][] hudParts = TextureRegion.split(hudTexture, HUD_TILE_WIDTH, HUD_TILE_HEIGHT);

    private TextureRegion health = hudParts[0][0];
    private TextureRegion energy = hudParts[0][1];

    private Vector2 netOffset = new Vector2(Gdx.graphics.getWidth() - netTexture.getWidth(), Gdx.graphics.getHeight() - netTexture.getHeight());

    private Vector2 netCenter = new Vector2(128, 128);
    private Vector2 stat0 = new Vector2(128, 244);
    private Vector2 stat1 = new Vector2(250, 156);
    private Vector2 stat2 = new Vector2(204, 12);
    private Vector2 stat3 = new Vector2(52, 12);
    private Vector2 stat4 = new Vector2(6, 156);
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
    private int itemBarWidth = (int) (numSlots * (itemSlotTexture.getWidth() + spacing));

    private static Queue<Textbox> textboxes = new Queue<>();

    public GameOverlay(Entity playerEntity) {
        this.player = playerEntity;
        initShader();
        initComponents();
        init();
    }

    private void initShader() {
        bloomShader = AssetLoader.getShader(ShaderPrograms.BLOOM_SHADER);
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
        font = AssetLoader.getBitmapFont(BitmapFonts.TEXTBOX_FONT, true);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        renderHealth();
        renderStats();
        renderItemBar();
        renderMouseArrow();
        if (isTextBoxVisible()) {
            renderTextBox();
        }
    }

    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    private void renderStats() {
        batch.begin();
        batch.draw(netTexture, netOffset.x, netOffset.y);
        batch.end();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN.add(Color.YELLOW));
        shapeRenderer.polygon(statsToVertices(elementsComponent.green / 20, elementsComponent.red / 20, elementsComponent.yellow / 20, elementsComponent.blue / 20, elementsComponent.purple / 20));

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    private float[] statsToVertices(float st0, float st1, float st2, float st3, float st4) {
        st0 = st0*0.9f+0.1f;
        st1 = st1*0.9f+0.1f;
        st2 = st2*0.9f+0.1f;
        st3 = st3*0.9f+0.1f;
        st4 = st4*0.9f+0.1f;

//        Vector2 posst0 = stat0.cpy().add(netCenter).add(netOffset).scl(1f);
        Vector2 posst0 = netOffset.cpy().add(netCenter.cpy().add(stat0.cpy().sub(netCenter).scl(st0)));
        Vector2 posst1 = netOffset.cpy().add(netCenter.cpy().add(stat1.cpy().sub(netCenter).scl(st1)));
        Vector2 posst2 = netOffset.cpy().add(netCenter.cpy().add(stat2.cpy().sub(netCenter).scl(st2)));
        Vector2 posst3 = netOffset.cpy().add(netCenter.cpy().add(stat3.cpy().sub(netCenter).scl(st3)));
        Vector2 posst4 = netOffset.cpy().add(netCenter.cpy().add(stat4.cpy().sub(netCenter).scl(st4)));
        return new float[]{posst0.x, posst0.y, posst1.x, posst1.y, posst2.x, posst2.y, posst3.x, posst3.y, posst4.x, posst4.y,};
    }

    private void renderHealth() {
        health.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bloomBatch.begin();
        float healthScale = (float) healthComponent.health / healthComponent.maxHealth;
        float energyScale = (float) energyComponent.energy / energyComponent.maxEnergy;
        bloomBatch.draw(healthBar, offset, Gdx.graphics.getHeight() - (health.getRegionHeight() + offset), health.getRegionWidth() * healthScale, healthBar.getRegionHeight());
        bloomBatch.draw(energyBar, offset, Gdx.graphics.getHeight() - ((energy.getRegionHeight() * 2 + offset + spacing)), energy.getRegionWidth() * energyScale, energyBar.getRegionHeight());
        bloomBatch.end();

        batch.begin();
        batch.draw(health, offset, Gdx.graphics.getHeight() - (health.getRegionHeight() + offset));
        batch.draw(energy, offset, Gdx.graphics.getHeight() - ((energy.getRegionHeight() * 2 + offset + spacing)));

        batch.end();


    }

    Vector2 dir = new Vector2(0,0);
    Texture arrowSheet = AssetLoader.getTexture(Textures.HUD_ARROW);
    TextureRegion[] arrowRegions = TextureRegion.split(arrowSheet, arrowSheet.getWidth()/4, arrowSheet.getHeight()/1)[0];
    private Animation arrowAnimation = new Animation(0.1f, arrowRegions);
    private float arrowTime;

    private void renderMouseArrow() {



//        MainGame.log(Gdx.input.getX() + " : " + Gdx.input.getY());
        Utils.grabMouse(250);
        dir.x = Gdx.input.getX();
        dir.y = Gdx.graphics.getHeight()-Gdx.input.getY();

        float rotation = dir.cpy().sub(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2)).angle()-90;
//        rotation += Gdx.graphics.getDeltaTime()*100;
        arrowTime += Gdx.graphics.getDeltaTime();
        TextureRegion arrow = arrowAnimation.getKeyFrame(arrowTime, true);
        batch.begin();
        batch.draw(arrow,
                (Gdx.graphics.getWidth()-arrow.getRegionWidth())/2,
                ((Gdx.graphics.getHeight()-arrow.getRegionHeight())/2),

                arrow.getRegionWidth()/2, arrow.getRegionHeight()/2,
                arrow.getRegionWidth(), arrow.getRegionHeight(),
                1f, 1f,
                rotation);
        batch.end();
    }

    private void renderItemBar() {
        batch.begin();
        for (int i = 0; i < numSlots; i++) {

            float x = ((itemSlotTexture.getWidth() + spacing) * i) + ((Gdx.graphics.getWidth() - itemBarWidth) >> 1);
            float y = Gdx.graphics.getHeight() - itemSlotTexture.getHeight() - offset;
            batch.draw(itemSlotTexture, x, y);
            font.draw(batch, "" + (i + 1), x, y + itemSlotTexture.getHeight());

            Texture texture = AssetLoader.getTexture(Textures.values()[Textures.ORB_BLUE.ordinal()+i]);
            int borderPx = 5;
            batch.draw(texture,
                        x+4+borderPx, y+4+borderPx,
                        0, 0,
                        texture.getWidth()-4-(borderPx<<1), texture.getHeight()-4-(borderPx<<1),
                        1, 1, 0, 0, 0,
                        texture.getWidth(), texture.getHeight(),
                        false, false);

        }
        batch.end();
    }

    private float stateTime = 0f;
    private float fadeVal = 0f;

    private void renderTextBox() {
        if(!fadeOut) {
            if(fadeVal < 1) {
                fadeVal += 0.05f;
            }
        } else {
            if(fadeVal > 0) {
                fadeVal -= 0.05f;
            } else {
            }
        }
        batch.begin();
        float x = (Gdx.graphics.getWidth() - textBox.getWidth()) >> 1;
        float y = -textBox.getHeight() + ((textBox.getHeight()+32)* fadeVal);
        batch.draw(textBox, x, y);
        font.draw(batch, currentTextbox.text, x + 10, y + textBox.getHeight()-10, textBox.getWidth(), Align.left, true);

        if(!currentTextbox.isDeterminant()) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion keyFrame = okButtonAnimation.getKeyFrame(stateTime, true);
            batch.draw(keyFrame, x + textBox.getWidth() - keyFrame.getRegionWidth() - 5, y + 5);
        } else {
            stateTime = 0f;
        }
        batch.end();

        if (currentTextbox.isDeterminant()) {
            currentTextbox.visibleSince += Gdx.graphics.getDeltaTime();
            if (currentTextbox.visibleSince >= currentTextbox.duration) {
                nextTextbox();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            nextTextbox();
        }
        if(fadeVal <= 0) {
            fadeOut = false;
            currentTextbox = null;
        }
    }

    private void nextTextbox() {
        if (textboxes.size > 0) {
            currentTextbox = textboxes.removeFirst();
        } else {
            if(fadeVal > 0) {
                fadeOut = true;
            }
        }
    }


    private static Textbox currentTextbox;
    private boolean fadeOut = false;

    public static boolean isTextBoxVisible() {
        return currentTextbox != null;
    }

    public static void addTextbox(String text) {
        addTextbox(text, -1);
    }

    public static void addTextbox(String text, float duration) {
        textboxes.addLast(new Textbox(text, duration));
        if (!isTextBoxVisible()) {
            currentTextbox = textboxes.removeFirst();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        hudTexture.dispose();
        netTexture.dispose();
        hudBarTexture.dispose();
        textBox.dispose();
        okButtonSheet.dispose();
    }

    private static class Textbox {
        String text;
        float duration;
        float visibleSince = 0;

        public Textbox(String text, float duration) {
            this.text = text;
            this.duration = duration;
        }

        public boolean isDeterminant() {
            return duration > 0;
        }
    }
}
