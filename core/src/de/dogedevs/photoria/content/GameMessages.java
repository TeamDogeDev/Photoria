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
        messages.add("This is the normal biome. No enemies here, so you can relax.");
        messages.add("Walk around and look for other biomes. Press 'e' to terraform south facing cliffs.");
        return messages;
    }

    private List<String> loadRedBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("It's very hot here...");
        return messages;
    }

    private List<String> loadGreenBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("This is the slime biome. You can collect malachite ore here.");
        return messages;
    }

    private List<String> loadBlueBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("It's cold in this biome...");
        return messages;
    }

    private List<String> loadYellowBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Be careful in this biome. Slimes shoot with lasers here.");
        return messages;
    }

    private List<String> loadPurpleBiomeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Watch out! Enemies will attack you with their energy gun.");
        return messages;
    }

    public List<String> getEnterMessageForBiome(int biome) {
        return biomeEnterMessages.get(biome);
    }

}
