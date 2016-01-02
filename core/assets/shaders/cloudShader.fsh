varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform float redLevel;

void main() {
//    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;

    gl_FragColor = vec4(redLevel, 0, 0, 0.5);
}