#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform vec2 u_resolution;
uniform float radial_blur;
uniform float radial_bright;

void main() {
    vec2 radial_size = vec2(1.0/u_resolution.x, 1.0/u_resolution.y);
    vec2 radial_origin = vec2(0.5, 0.5);

    vec2 texCoord = v_texCoord0;

    vec4 sum_color = vec4(0.0);
    texCoord += radial_size * 0.5 - radial_origin;

   for(int i = 0; i < 12; i++) {
        float scale = 1.0 - radial_blur * (float(i) / 11.0);
        sum_color += texture2D(u_sampler2D, texCoord * scale + radial_origin);
   }

   gl_FragColor = sum_color / 12.0 * radial_bright;
}
