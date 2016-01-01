package de.dogedevs.photoria.rendering.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Furuha on 01.01.2016.
 */
public class AnimationLoader {

    public Animation[] getEyeAnimations(){
        Texture walkSheet = new Texture(Gdx.files.internal("eyeball.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/3, walkSheet.getHeight()/4);
        TextureRegion[][] walkFrames = new TextureRegion[4][3 * 1];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                walkFrames[i][j] = tmp[i][j];
            }
        }
        Animation[] result = new Animation[4];
        result[0] = new Animation(0.3f, walkFrames[0]); //Up
        result[1] = new Animation(0.3f, walkFrames[2]); //Down
        result[2] = new Animation(0.3f, walkFrames[1]); //Left
        result[3] = new Animation(0.3f, walkFrames[3]); //Right
        return  result;
    }
    
    public Animation[] getWormAnimation(){
        Texture wormWalkSheet = new Texture(Gdx.files.internal("big_worm.png"));
        TextureRegion[][] wormTmp = TextureRegion.split(wormWalkSheet, wormWalkSheet.getWidth()/3, wormWalkSheet.getHeight()/4);
        TextureRegion[][] wormWalkFrames = new TextureRegion[4][3 * 1];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                wormWalkFrames[i][j] = wormTmp[i][j];
            }
        }
        Animation[] result = new Animation[4];
        result[0] = new Animation(0.3f, wormWalkFrames[0]); //Up
        result[1] = new Animation(0.3f, wormWalkFrames[2]); //Down
        result[2] = new Animation(0.3f, wormWalkFrames[1]); //Left
        result[3] = new Animation(0.3f, wormWalkFrames[3]); //Right
        return  result;
    }


    public static Animation[] getShipAnimation(){
        Texture wormWalkSheet = new Texture(Gdx.files.internal("ship_right.png"));
        TextureRegion[][] wormTmp = TextureRegion.split(wormWalkSheet, wormWalkSheet.getWidth()/4, wormWalkSheet.getHeight()/1);
        TextureRegion[][] wormWalkFrames = new TextureRegion[1][4];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                wormWalkFrames[i][j] = wormTmp[i][j];
            }
        }
        Animation[] result = new Animation[4];
        result[0] = new Animation(0.3f, wormWalkFrames[0]); //Up
        return  result;
    }
}
