#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {
    vec4 color = vec4(clamp(v_color.rgb - 0.5*(v_color.rgb), 0.0, 1.0), 1.);
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * color;
}
