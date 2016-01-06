package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.HashMap;

/**
 * Created by Furuha on 01.01.2016.
 */
public class AnimationLoader {

    private static HashMap<String, Animation[]> animations = new HashMap<>();

    public static Animation[] getMovementAnimations(Textures texture, boolean mirrored, int rows, int cols){
        Animation[] result = animations.get(texture.name);
        if(result != null){
            return  result;
        }

        Texture walkSheet = AssetLoader.getTexture(texture);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/cols, walkSheet.getHeight()/rows);
        TextureRegion[][] walkFrames;
        if(mirrored){
            walkFrames = new TextureRegion[rows][cols*2];
        } else {
            walkFrames = new TextureRegion[rows][cols];
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                walkFrames[i][j] = tmp[i][j];
                if(mirrored)walkFrames[i][(cols*2-1)-j] = tmp[i][j];
            }
        }
        result = new Animation[4];
        result[0] = new Animation(0.15f, walkFrames[0]); //Up
        result[1] = new Animation(0.15f, walkFrames[2]); //Down
        result[2] = new Animation(0.15f, walkFrames[1]); //Left
        result[3] = new Animation(0.15f, walkFrames[3]); //Right
        animations.put(texture.name, result);
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

    public static Animation[] getPlayerAnimations(){
        Texture walkSheet = AssetLoader.getTexture(Textures.PLAYER);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/13, walkSheet.getHeight()/21);
        TextureRegion[][] walkFrames = new TextureRegion[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                walkFrames[i][j] = tmp[i+8][j+1];
            }
        }
        Animation[] result = new Animation[5];
        result[0] = new Animation(0.1f, walkFrames[0]); //Up
        result[1] = new Animation(0.1f, walkFrames[2]); //Down
        result[2] = new Animation(0.1f, walkFrames[1]); //Left
        result[3] = new Animation(0.1f, walkFrames[3]); //Right
        result[4] = new Animation(2f, tmp[10][1]); //Idle
        return  result;
    }
}
