package de.dogedevs.photoria.content;

import de.dogedevs.photoria.model.entity.ai.EscapeOnDamageAi;
import de.dogedevs.photoria.model.entity.ai.FollowAi;
import de.dogedevs.photoria.model.entity.components.AiComponent;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class MobStats {
    // Mob's initial health
    public static final float EYE_HEALTH = 1000;
    public static final float SLIME_HEALTH = 100;

    // Mob's AI
    public static final AiComponent.AiInterface SLIME_AI = new FollowAi();
    public static final AiComponent.AiInterface EYE_AI = new EscapeOnDamageAi();

}
