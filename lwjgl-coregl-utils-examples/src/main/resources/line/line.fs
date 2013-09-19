//#version 150 core

// line cap styles
//#define LINE_CAP_BUTT
//#define LINE_CAP_SQUARE
//#define LINE_CAP_ROUND

// line join styles
//#define LINE_JOIN_NONE
//#define LINE_JOIN_MITER

// the line color
uniform vec4 lineColor;

// x = (2*r + w)
// y = (2*r + w) / 2.f
// z = (2*r + w) / 2.f - 2*r
// W = (2*r)
uniform vec4 lineParameters;

in vec2 uv;

out vec4 color;

void main() {
  // get params into individual variables
  float halfWidth = lineParameters.y;
  float halfWidthMinus2R = lineParameters.z;
  float r2 = lineParameters.w;

#ifdef LINE_CAP_ROUND

  float distance = sqrt(uv.x*uv.x + uv.y*uv.y);
  float intensity = 1.0 - smoothstep (halfWidthMinus2R, halfWidth, distance);
  color = vec4(lineColor.r, lineColor.g, lineColor.b, intensity);

#else

  float distanceY = sqrt(uv.y*uv.y);
  float intensityY = 1.0 - smoothstep (halfWidthMinus2R, halfWidth, distanceY);

  float distanceX = sqrt(uv.x*uv.x);

  #ifdef LINE_CAP_BUTT
  float intensityX = 1.0 - smoothstep (0, r2, distanceX);
  #elif defined(LINE_CAP_SQUARE)
  float intensityX = 1.0 - smoothstep(halfWidthMinus2R, halfWidth, distanceX);
  #endif

  color = vec4(lineColor.r, lineColor.g, lineColor.b, intensityX*intensityY);

#endif
}
