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
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.InventoryComponent;
import de.dogedevs.photoria.model.entity.components.ItemComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.utils.assets.enums.BitmapFonts;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {

    private static final int HUD_TILE_WIDTH = 720 >> 1;
    private static final int HUD_TILE_HEIGHT = 32;

    private Texture textBox = Statics.asset.getTexture(Textures.HUD_TEXTBOX);
    private Texture okButtonSheet = Statics.asset.getTexture(Textures.HUD_OK_BUTTON);
    private Texture hudBarTexture = Statics.asset.getTexture(Textures.HUD_BARS_FILL);
    private Texture itemSlotTexture = Statics.asset.getTexture(Textures.HUD_ITEM_SLOTS);
    private Texture netTexture = Statics.asset.getTexture(Textures.HUD_RADAR_CHART);
    private Texture hudTexture = Statics.asset.getTexture(Textures.HUD_BARS);
    private Texture descTexture = Statics.asset.getTexture(Textures.HUD_ITEM_DESCRIPTION);

    private TextureRegion[][] hudBars = TextureRegion.split(hudBarTexture, 1, HUD_TILE_HEIGHT);
    private TextureRegion[][] hudParts = TextureRegion.split(hudTexture, HUD_TILE_WIDTH, HUD_TILE_HEIGHT);
    private TextureRegion[] okFrames = TextureRegion.split(okButtonSheet, okButtonSheet.getWidth() / 10, okButtonSheet.getHeight())[0];

    private TextureRegion healthBar = hudBars[0][0];
    private TextureRegion energyBar = hudBars[0][1];
    private TextureRegion health = hudParts[0][0];
    private TextureRegion energy = hudParts[0][1];

    private Animation okButtonAnimation = new Animation(0.05f, okFrames);

    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    private BitmapFont font;
    private BitmapFont overlayFont;

    private float offset = 10;
    private float spacing = 5;
    private int numSlots = 6;
    private int itemBarWidth = (int) (numSlots * (itemSlotTexture.getWidth() + spacing));
    float healthEnergyOffset = netTexture.getWidth() + offset + spacing;


    private Vector2 netOffset = new Vector2(offset, Gdx.graphics.getHeight()-netTexture.getHeight()-offset);
    private Vector2 netCenter = new Vector2(96, 96);
    private Vector2 blueStat = new Vector2(96, netTexture.getHeight()-6);
    private Vector2 greenStat = new Vector2(186, netTexture.getHeight()-81);
    private Vector2 yellowStat = new Vector2(142, netTexture.getHeight()-187);
    private Vector2 redStat = new Vector2(50, netTexture.getHeight()-187);
    private Vector2 purpleStat = new Vector2(7, netTexture.getHeight()-81);

    private ShaderProgram bloomShader;
    private Batch bloomBatch = new SpriteBatch();

    private Entity player;

    private PlayerComponent playerComponent;
    private HealthComponent healthComponent;
    private EnergyComponent energyComponent;
    private ElementsComponent elementsComponent;
    private InventoryComponent inventoryComponent;

    private float stateTime = 0f;
    private boolean fadeOut = false;
    private float fadeVal = 0f;
    private static Textbox currentTextbox;
    private static Queue<Textbox> textboxes = new Queue<>();

    public GameOverlay(Entity playerEntity) {
        this.player = playerEntity;
        init();
    }

    private void initShader() {
        bloomShader = Statics.asset.getShader(ShaderPrograms.BLOOM_SHADER);
//        bloomBatch.setShader(bloomShader);
    }

    private void initComponents() {
        playerComponent = ComponentMappers.player.get(player);
        healthComponent = ComponentMappers.health.get(player);
        energyComponent = ComponentMappers.energy.get(player);
        elementsComponent = ComponentMappers.elements.get(player);
        inventoryComponent = ComponentMappers.inventory.get(player);
    }

    @Override
    public void init() {
        initShader();
        initComponents();
        font = Statics.asset.getBitmapFont(BitmapFonts.TEXTBOX_FONT, true);
        overlayFont = Statics.asset.getBitmapFont(BitmapFonts.OVERLAY_FONT, true);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        renderStats();
        renderPlayerIcon();
        renderHealth();
        renderItemBar();
        if (isTextBoxVisible()) {
            renderTextBox();
        }
    }

    private void renderPlayerIcon() {
        batch.begin();
        float biggest = elementsComponent.blue;
        Textures bTextures = Textures.HUD_PLAYER_BLUE;
        if(elementsComponent.yellow >= biggest){
            biggest = elementsComponent.yellow;
            bTextures = Textures.HUD_PLAYER_YELLOW;
        }
        if(elementsComponent.red >= biggest){
            biggest = elementsComponent.red;
            bTextures = Textures.HUD_PLAYER_RED;
        }
        if(elementsComponent.purple >= biggest){
            biggest = elementsComponent.purple;
            bTextures = Textures.HUD_PLAYER_PURPLE;
        }
        if(elementsComponent.green >= biggest){
            bTextures = Textures.HUD_PLAYER_GREEN;
        }
        Texture playerIcon = Statics.asset.getTexture(bTextures);

        batch.draw(playerIcon, 165, Gdx.graphics.getHeight() - playerIcon.getHeight() - offset - 8);

        batch.end();
    }

    private void renderStats() {
        batch.begin();
        batch.draw(netTexture, netOffset.x, netOffset.y);
        batch.end();

//        elementsComponent.red = 0.2f;
//        elementsComponent.green = .1f;
//        elementsComponent.blue = 0.6f;
//        elementsComponent.yellow = 0.2f;
//        elementsComponent.purple = .0f;
        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        InventoryComponent ic = ComponentMappers.inventory.get(player);
        float blue = elementsComponent.blue;
        float green = elementsComponent.green;
        float yellow = elementsComponent.yellow;
        float red = elementsComponent.red;
        float purple = elementsComponent.purple;

        if(ic != null && ic.slotAttack != null) {
            ItemComponent inventory = ComponentMappers.item.get(ic.slotAttack);
            blue += inventory.dmgElementBlue;
            green += inventory.dmgElementGreen;
            yellow += inventory.dmgElementYellow;
            red += inventory.dmgElementRed;
            purple += inventory.dmgElementPurple;
        }


        shapeRenderer.polygon(statsToVertices(blue, green, yellow, red, purple));

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    private float[] statsToVertices(float blue, float green, float yellow, float red, float purple) {
        blue = blue * 0.9f + 0.1f;
        green = green * 0.9f + 0.1f;
        yellow = yellow * 0.9f + 0.1f;
        red = red * 0.9f + 0.1f;
        purple = purple * 0.9f + 0.1f;

        Vector2 posst0 = netOffset.cpy().add(netCenter.cpy().add(blueStat.cpy().sub(netCenter).scl(blue)));
        Vector2 posst1 = netOffset.cpy().add(netCenter.cpy().add(greenStat.cpy().sub(netCenter).scl(green)));
        Vector2 posst2 = netOffset.cpy().add(netCenter.cpy().add(yellowStat.cpy().sub(netCenter).scl(yellow)));
        Vector2 posst3 = netOffset.cpy().add(netCenter.cpy().add(redStat.cpy().sub(netCenter).scl(red)));
        Vector2 posst4 = netOffset.cpy().add(netCenter.cpy().add(purpleStat.cpy().sub(netCenter).scl(purple)));
        return new float[]{posst0.x, posst0.y, posst1.x, posst1.y, posst2.x, posst2.y, posst3.x, posst3.y, posst4.x, posst4.y,};
    }

    private void renderHealth() {
        health.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bloomBatch.begin();
        float healthScale = (float) healthComponent.health / healthComponent.maxHealth;
        float energyScale = (float) energyComponent.energy / energyComponent.maxEnergy;

        bloomBatch.draw(healthBar, healthEnergyOffset, Gdx.graphics.getHeight() - (health.getRegionHeight() + offset), health.getRegionWidth() * healthScale, healthBar.getRegionHeight());
        bloomBatch.draw(energyBar, healthEnergyOffset, Gdx.graphics.getHeight() - ((energy.getRegionHeight() * 2 + offset + spacing)), energy.getRegionWidth() * energyScale, energyBar.getRegionHeight());
        bloomBatch.end();

        batch.begin();
        batch.draw(health, healthEnergyOffset, Gdx.graphics.getHeight() - (health.getRegionHeight() + offset));
        batch.draw(energy, healthEnergyOffset, Gdx.graphics.getHeight() - ((energy.getRegionHeight() * 2 + offset + spacing)));

        HealthComponent hc = ComponentMappers.health.get(player);
        EnergyComponent ec = ComponentMappers.energy.get(player);
        if(hc != null) {
            overlayFont.draw(batch, (int)hc.health + "",
            healthEnergyOffset, Gdx.graphics.getHeight() - (health.getRegionHeight() + offset)+overlayFont.getLineHeight(),health.getRegionWidth()-20, Align.right, false);
        }
        if(ec != null) {
            overlayFont.draw(batch, (int)ec.energy + "",
            healthEnergyOffset, Gdx.graphics.getHeight() - ((energy.getRegionHeight() * 2 + offset + spacing))+overlayFont.getLineHeight(),energy.getRegionWidth()-20, Align.right, false);
        }


        batch.end();


    }

    private void renderItemBar() {
        batch.begin();
//        for (int i = 0; i < ItemManager.Slot.values().length; i++) {

        for(ItemComponent.ItemType slot : ItemComponent.ItemType.values()) {
            float x = healthEnergyOffset +health.getRegionWidth()+ spacing + (slot.position*(itemSlotTexture.getWidth()+spacing)); //((itemSlotTexture.getWidth() + spacing) * i) + ((Gdx.graphics.getWidth() - itemBarWidth) >> 1);
            float y = Gdx.graphics.getHeight() - itemSlotTexture.getHeight() - offset;
            batch.draw(itemSlotTexture, x, y);

            if(inventoryComponent != null) {
                Entity itemEntity = null;
                if(slot == ItemComponent.ItemType.ATTACK) {
                    itemEntity = inventoryComponent.slotAttack;
                } else if(slot == ItemComponent.ItemType.DEFENSE) {
                    itemEntity = inventoryComponent.slotDefense;
                } else if(slot == ItemComponent.ItemType.REGENERATION) {
                    itemEntity = inventoryComponent.slotRegeneration;
                } else if(slot == ItemComponent.ItemType.STATS_UP) {
                    itemEntity = inventoryComponent.slotStatsUp;
                } else if(slot == ItemComponent.ItemType.OTHER) {
                    itemEntity = inventoryComponent.slotOther;
                } else if(slot == ItemComponent.ItemType.USE) {
                    if(inventoryComponent.slotUse.size() > 0) {
                        itemEntity = inventoryComponent.slotUse.get(0);
                    }
                }
                if(itemEntity != null) {
                    SpriteComponent attackSprite = ComponentMappers.sprite.get(itemEntity);
                    if (attackSprite != null) {
                        if (attackSprite.region != null) {
                            batch.draw(attackSprite.region, x + (attackSprite.region.getRegionWidth() / 2), y + (attackSprite.region.getRegionHeight() / 2));

                            ItemComponent itemComponent = ComponentMappers.item.get(itemEntity);
                            if(itemComponent != null) {
                                int mouseX = Gdx.input.getX();
                                int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                                if (mouseX > x && mouseY > y) {
                                    if (mouseX < x + itemSlotTexture.getWidth() &&
                                            mouseY < y + itemSlotTexture.getHeight()) {
                                        float localX = healthEnergyOffset + health.getRegionWidth()+ spacing;
                                        float localY = y-descTexture.getHeight()-spacing;
                                        batch.draw(descTexture, localX, localY);
                                        font.draw(batch,itemComponent.name + "\n" + itemComponent.description,
                                                  localX+(2*spacing), localY+descTexture.getHeight()-(2*spacing),
                                        descTexture.getWidth(), Align.left, true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            font.draw(batch, "" + (i + 1), x, y + itemSlotTexture.getHeight());

//            Texture texture = Statics.asset.getTexture(Textures.values()[Textures.ORB_BLUE.ordinal() + i]);
//            int borderPx = 5;
//            batch.draw(texture,
//                    x + 4 + borderPx, y + 4 + borderPx,
//                    0, 0,
//                    texture.getWidth() - 4 - (borderPx << 1), texture.getHeight() - 4 - (borderPx << 1),
//                    3, 3, 0, 0, 0,
//                    texture.getWidth(), texture.getHeight(),
//                    false, false);

        }
        batch.end();
    }


    private void renderTextBox() {
        if (!fadeOut) {
            if (fadeVal < 1) {
                fadeVal += 0.05f;
            }
        } else {
            if (fadeVal > 0) {
                fadeVal -= 0.05f;
            } else {
            }
        }
        batch.begin();
        float x = (Gdx.graphics.getWidth() - textBox.getWidth()) >> 1;
        float y = -textBox.getHeight() + ((textBox.getHeight() + 32) * fadeVal);
        batch.draw(textBox, x, y);
        font.draw(batch, currentTextbox.text, x + 10, y + textBox.getHeight() - 10, textBox.getWidth(), Align.left, true);

        if (!currentTextbox.isDeterminant()) {
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
        if (fadeVal <= 0) {
            fadeOut = false;
            currentTextbox = null;
        }
    }

    private void nextTextbox() {
        if (textboxes.size > 0) {
            currentTextbox = textboxes.removeFirst();
        } else {
            if (fadeVal > 0) {
                fadeOut = true;
            }
        }
    }


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
        overlayFont.dispose();
        hudTexture.dispose();
        netTexture.dispose();
        hudBarTexture.dispose();
        descTexture.dispose();
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
