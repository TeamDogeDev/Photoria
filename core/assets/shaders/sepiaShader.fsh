#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {

    vec4 sepia_1 = vec4(.2, .05, .0, 1.0);
    vec4 sepia_2 = vec4(1., .9, .5, 1.0);

    vec4 color = texture2D(u_sampler2D, v_texCoord0);

    float sepia_mix = dot(vec3(.3, .59, .11), vec3(color));
    color = mix(color, vec4(sepia_mix), vec4(.5));
    vec4 sepia = mix(sepia_1, sepia_2, sepia_mix);

    gl_FragColor = mix(color, sepia, 1.0);
}
