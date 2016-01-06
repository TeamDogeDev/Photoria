#version 120

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float biom;
uniform sampler2D u_sampler2D;
const float PI = 3.1415926535;

float toonify(in float intensity) {
    if(intensity > 0.8) {
        return 1.0;
    } else if(intensity > 0.5) {
        return 0.8;
    } else if(intensity > 0.25) {
        return 0.3;
    } else {
        return 0.1;
    }
}

void cartoon() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0);
    float factor = toonify(max(color.r, max(color.g, color.b)));
    gl_FragColor = vec4(factor*color.rgb, color.a);
}

void pixelize(in float pixelSize) {
    float dx = pixelSize*(1./1280); // BAD
    float dy = pixelSize*(1./768); // BAD

    vec4 intensity = texture2D(u_sampler2D, v_texCoord0);
    intensity = vec4(vec3(((intensity.g * 3 + intensity.r * 2 + intensity.b)/6)+.4), 1);

    vec2 coord = vec2(dx*floor(v_texCoord0.x/dx), dy*floor(v_texCoord0.y/dy));
    gl_FragColor = texture2D(u_sampler2D, coord) * intensity;

}

void fishEye(in float ap) {
    float apHalf = ap * 0.5 * (PI / 180.0);
    float maxFac = sin(apHalf);

    vec2 uv;
    vec2 xy = 2.0 * v_texCoord0.xy -1.0;

    float d = length(xy);

    if(d < (2.0-maxFac)) {
        d = length(xy * maxFac);
        float z = sqrt(1.0 - d * d);
        float r = atan(d, z) / PI;
        float phi = atan(xy.y, xy.x);

        uv.x = r * cos(phi) + 0.5;
        uv.y = r * sin(phi) + 0.5;
    } else {
        uv = v_texCoord0;
    }

    vec4 c = texture2D(u_sampler2D, uv);

    gl_FragColor = c;
}

void halfMid() {
    vec2 offset = vec2(-0.5, -0.5);
    vec4 c = texture2D(u_sampler2D, (v_texCoord0*2) + offset);
    gl_FragColor = c;
}

void bloom2() {
    vec4 blurSample = vec4(0.);
    vec4 tmpPix;
    vec4 offPix;
    vec2 uv = v_texCoord0.st;

    for(int i = -4; i < 5; i++) {
        tmpPix = texture2D(u_sampler2D, uv + vec2(i * 0.005, 0));
        offPix = -.3 + tmpPix;
        offPix *= 15;
        if((offPix.r + offPix.g + offPix.b) > 0) {
            blurSample += offPix;
        }
    }

    for(int i = -4; i < 5; i++) {
        tmpPix = texture2D(u_sampler2D, uv + vec2(0, i * 0.005));
        offPix = -.3 + tmpPix;
        offPix *= 15;
        if((offPix.r + offPix.g + offPix.b) > 0) {
            blurSample += offPix;
        }
    }

    blurSample /= 64;
    gl_FragColor = blurSample * 1.2;
}

void bloom() {
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

void gray() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0);
    color = vec4(vec3((color.g * 3 + color.r * 2 + color.b)/6), 1);
    gl_FragColor = color;
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void randomTest() {
    float dx = 1.*(1./32); // BAD
    float dy = 1.*(1./32); // BAD

    vec2 coord = vec2(dx*floor(v_texCoord0.x/dx), dy*floor(v_texCoord0.y/dy));
    vec4 intensity = texture2D(u_sampler2D, v_texCoord0);
    intensity = vec4(vec3(((intensity.g * 3 + intensity.r * 2 + intensity.b)/6)+.4), 1);

    if(biom == 1) {
        vec4 tmpCol = vec4(1, 0, 0, 1);
        gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * tmpCol;
    } else {
        vec4 tmpCol = vec4(0, 0, 1, 1);
        gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * tmpCol;
    }

}

void main() {
    // CartoonShader
//     cartoon();

    // PixelizeShader
//     float pixelSize = 256;
//     pixelize(pixelSize);

    // FishEye
//     float ap = 178.0;
//     fishEye(ap);

    // HalfMid
//    halfMid();
//    float power = 0.5;
//    bloom();
    // Passthrough
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0);

//    randomTest();
//    gray();
}
