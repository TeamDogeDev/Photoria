attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform vec2 waveData;
varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
    v_color = a_color;
    v_texCoord0 = a_texCoord0;

    vec4 newPos = vec4(a_position.x + waveData.y * sin(waveData.x+a_position.x+a_position.y), a_position.y + waveData.y * cos(waveData.x+a_position.x+a_position.y), a_position.z, 1.);
    //vec4 newPos = vec4(a_position-vec3(0,0,0), 1.00);
    gl_Position = u_projTrans * newPos;
//gl_Position = u_projTrans * vec4(a_position, 1.);
}
