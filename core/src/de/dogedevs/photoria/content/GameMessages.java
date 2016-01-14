package de.dogedevs.photoria.content;

import de.dogedevs.photoria.model.map.ChunkBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elektropapst on 14.01.2016.
 */
public class GameMessages {

    private Map<Integer, List<String>> biomeEnterMessages;

    public GameMessages() {
        loadBiomeEnterMessages();
    }

    private void loadBiomeEnterMessages() {
        biomeEnterMessages = new HashMap<>();

        biomeEnterMessages.put(ChunkBuffer.NORMAL_BIOM, loadNormalBiomeMessages());
        biomeEnterMessages.put(ChunkBuffer.RED_BIOM, loadRedBiomeMessages());
        biomeEnterMessages.put(ChunkBuffer.GREEN_BIOM, loadGreenBiomeMessages());
        biomeEnterMessages.put(ChunkBuffer.BLUE_BIOM, loadBlueBiomeMessages());
        biomeEnterMessages.put(ChunkBuffer.YELLOW_BIOM, loadYellowBiomeMessages());
        biomeEnterMessages.put(ChunkBuffer.PURPLE_BIOM, loadPurpleBiomeMessages());
    }

    private List<String> loadNormalBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("This is the normal biome.");
        messages.add("Nothing to see here...");
        return messages;
    }

    private List<String> loadRedBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("This is the red biome.");
        messages.add("Much fire. Very Lava");
        return messages;
    }

    private List<String> loadGreenBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("This is the green biome.");
        messages.add("So slime. Very acid. bäh");
        return messages;
    }

    private List<String> loadBlueBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Welcome to the blue biome.");
        messages.add("It's cold...");
        return messages;
    }

    private List<String> loadYellowBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Wow, a yellow biome.");
        messages.add("Laser???");
        return messages;
    }

    private List<String> loadPurpleBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("YAB - Yet another biome.");
        messages.add("Purple = Energy weapon... vry cool.");
        return messages;
    }

    public List<String> getEnterMessageForBiome(int biome) {
        return biomeEnterMessages.get(biome);
    }

}
