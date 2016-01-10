package de.dogedevs.photoria.content.mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dogedevs.photoria.content.ai.AiType;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Furuha on 09.01.2016.
 */
public class MobManager {

    private Map<Integer, List<MobTemplate>> templates;

    public MobManager(){
        Gson gson = new Gson();
        templates = new HashMap<>();
        FileHandle folder = Gdx.files.internal("json/mobs/");
        if(folder.isDirectory()){
            FileHandle[] files = folder.list("json");
            for(FileHandle file: files){
                String content = file.readString("UTF-8");
                MobTemplate template = gson.fromJson(content, MobTemplate.class);

                for(Integer biome: template.biome){
                    if(templates.get(biome) == null){
                        templates.put(biome, new ArrayList<MobTemplate>());
                    }
                    templates.get(biome).add(template);
                }
            }
        }
    }

    public List<MobTemplate> getTemplatesForBiome(int biome){
        List<MobTemplate> list = templates.get(biome);
        return list;
    }

    public MobTemplate getRandomTemplateForBiome(int biome){
        List<MobTemplate> list = templates.get(biome);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.get(MathUtils.random(0, list.size() - 1));
    }

    public static void main(String[] args){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        MobTemplate template = new MobTemplate();

        template.id = 1;
        template.name = "Green Slime";

        template.biome = new ArrayList<>();
        template.biome.add(0);
        template.biome.add(2);
        template.biome.add(3);
        template.biome.add(4);
        template.biome.add(5);
        template.biome.add(6);

        template.attributes = new ArrayList<>();
        template.attributes.add(MobAttribute.MULTIPLY);
        template.attributes.add(MobAttribute.GROUND);

        template.ai = AiType.FOLLOW;

        template.blue = 1;
        template.red = 1;
        template.green = 1;
        template.yellow = 1;
        template.purple = 1;

        template.weapon = 1;

        template.maxHealth = 30;

        template.type = MobType.LOW;

        template.texture = Textures.SLIME_GREEN;
        System.out.print(gson.toJson(template));
    }


}
