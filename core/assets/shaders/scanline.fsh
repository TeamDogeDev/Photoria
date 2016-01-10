#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform vec2 u_resolution;
uniform float scale = 2.0;

void main() {
    if(mod(floor(v_texCoord0.y * u_resolution.y / scale), 2.0) == 0.0) {
        gl_FragColor = vec4(0f, 0f, 0f, 1f);
    } else {
        gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
    }
}
