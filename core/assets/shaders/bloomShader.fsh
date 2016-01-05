#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
const float PI = 3.1415926535;

void main() {
    vec4 blurSample = vec4(0.0);
    vec4 tmpPix;
    vec4 offPix;
    for(int tx =-2; tx < 3; tx++) {
        for(int ty = -2; ty < 3; ty++) {
            vec2 uv = v_texCoord0.st;
            uv.x = uv.x + tx*0.01;
            uv.y = uv.y + ty*0.01;
            tmpPix = texture2D(u_sampler2D,uv);
            offPix = -0.3+tmpPix;
            offPix = offPix *32;
            blurSample = blurSample + offPix;
        }
    }

    blurSample = blurSample / 1024;
    gl_FragColor = texture2D(u_sampler2D,v_texCoord0.st)*2+blurSample*1.2;
}
