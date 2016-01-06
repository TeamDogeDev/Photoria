package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 06.01.2016.
 */
public enum ShaderPrograms {

    CLOUD_SHADER("shaders/vertexStub.vsh", "shaders/cloudShader.fsh"),
    WATER_SHADER("shaders/liquidShader.vsh", "shaders/liquidShader.fsh"),
    PASSTHROUGH_SHADER("shaders/vertexStub.vsh", "shaders/passthrough.fsh"),
    BLOOM_SHADER("shaders/vertexStub.vsh", "shaders/bloomShader.fsh"),
    STARFIELD_SHADER("shaders/vertexStub.vsh", "shaders/starfield.fsh");

    public String fragmentShader;
    public String vertexShader;

    ShaderPrograms(String vertexShader, String fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }


}
