#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14159265359

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float saw(float x) {
	return 2. * (mod(x, 1.0) - 0.5);
}

float tanSaw(float x) {
	return tan(x * PI);
}

float sqSaw(float x) {
	x = saw(x) * 0.999; // MONDO HACK!
	return x / sqrt(1. - x*x);
}

vec2 rotate2D(vec2 v, float a) {
	return vec2(v.x * cos(a) - v.y * sin(a), v.y * cos(a) + v.x * sin(a));
}

void main( void ) {

	vec2 position = 2. * (( gl_FragCoord.xy / resolution.xy ) - 0.5);
	position.x *= resolution.x / resolution.y;

	vec2 realMouse = 2. * (mouse - 0.5);

	realMouse.x *= resolution.x / resolution.y;

	//vec3 lightPos = vec3(vec2(0., 0.) - position, 1.);
	vec3 lightPos = vec3(realMouse - position, 1.);

	float diffuse = 0.0;
	float l = length(position);
	vec2 pos = (position * 4.);
	vec3 normal = vec3(sqSaw(pos.x), sqSaw(pos.y), 16.);

	diffuse = 0.5 + 0.5 * dot(normalize(lightPos), normalize(normal));

	vec3 halfway = normalize((vec3(0., 0., 1.) + lightPos));

	float spec = pow(dot(halfway, normalize(normal)), (0.75 + 0.25 * sin(time)) * 128.);


	gl_FragColor = vec4( diffuse * hsv2rgb(vec3(time * 0.01, 0.25, 0.5)) + spec * 0.5, 1.0 );

}