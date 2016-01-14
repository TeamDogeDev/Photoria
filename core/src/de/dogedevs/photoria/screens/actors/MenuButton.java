package de.dogedevs.photoria.screens.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 11.01.2016.
 */
public class MenuButton extends Actor {

    public enum ButtonType {
        SMALL,
        NORMAL
    }

    private TextureRegion boxTexture;
    private String text;
    private BitmapFont font;
    private Color renderColor, fontColor, hoverColor;

    public MenuButton(String text, float x, float y, BitmapFont font, Color fontColor, Color hoverColor, ButtonType btnType) {
        this.text = text;
        this.font = font;
        this.fontColor = fontColor;
        this.hoverColor = hoverColor;
        this.renderColor = fontColor;
        switch (btnType) {
            case SMALL:
                boxTexture = new TextureRegion(Statics.asset.getTexture(Textures.MENU_BOX_SMALL));
                break;
            case NORMAL:
            default:
                boxTexture = new TextureRegion(Statics.asset.getTexture(Textures.MENU_BOX));
                break;
        }
        this.setPosition(x, y);
        this.setSize(boxTexture.getRegionWidth(), boxTexture.getRegionHeight());
        initListener();
    }

    private void initListener() {
        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                renderColor = hoverColor;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                renderColor = fontColor;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        System.out.println(getX() + " " + getHeight());
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(boxTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        font.setColor(renderColor);
        font.draw(batch, text, getX(), getY() + font.getLineHeight(), getWidth(), Align.center, false);
        font.setColor(fontColor);
    }


}
