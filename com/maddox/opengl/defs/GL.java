package com.maddox.opengl.defs;

public abstract interface GL
{
  public static final int VERSION_1_1 = 1;
  public static final int CURRENT_BIT = 1;
  public static final int POINT_BIT = 2;
  public static final int LINE_BIT = 4;
  public static final int POLYGON_BIT = 8;
  public static final int POLYGON_STIPPLE_BIT = 16;
  public static final int PIXEL_MODE_BIT = 32;
  public static final int LIGHTING_BIT = 64;
  public static final int FOG_BIT = 128;
  public static final int DEPTH_BUFFER_BIT = 256;
  public static final int ACCUM_BUFFER_BIT = 512;
  public static final int STENCIL_BUFFER_BIT = 1024;
  public static final int VIEWPORT_BIT = 2048;
  public static final int TRANSFORM_BIT = 4096;
  public static final int ENABLE_BIT = 8192;
  public static final int COLOR_BUFFER_BIT = 16384;
  public static final int HINT_BIT = 32768;
  public static final int EVAL_BIT = 65536;
  public static final int LIST_BIT = 131072;
  public static final int TEXTURE_BIT = 262144;
  public static final int SCISSOR_BIT = 524288;
  public static final int ALL_ATTRIB_BITS = 1048575;
  public static final int CLIENT_PIXEL_STORE_BIT = 1;
  public static final int CLIENT_VERTEX_ARRAY_BIT = 2;
  public static final int CLIENT_ALL_ATTRIB_BITS = -1;
  public static final int FALSE = 0;
  public static final int TRUE = 1;
  public static final int POINTS = 0;
  public static final int LINES = 1;
  public static final int LINE_LOOP = 2;
  public static final int LINE_STRIP = 3;
  public static final int TRIANGLES = 4;
  public static final int TRIANGLE_STRIP = 5;
  public static final int TRIANGLE_FAN = 6;
  public static final int QUADS = 7;
  public static final int QUAD_STRIP = 8;
  public static final int POLYGON = 9;
  public static final int ACCUM = 256;
  public static final int LOAD = 257;
  public static final int RETURN = 258;
  public static final int MULT = 259;
  public static final int ADD = 260;
  public static final int NEVER = 512;
  public static final int LESS = 513;
  public static final int EQUAL = 514;
  public static final int LEQUAL = 515;
  public static final int GREATER = 516;
  public static final int NOTEQUAL = 517;
  public static final int GEQUAL = 518;
  public static final int ALWAYS = 519;
  public static final int ZERO = 0;
  public static final int ONE = 1;
  public static final int SRC_COLOR = 768;
  public static final int ONE_MINUS_SRC_COLOR = 769;
  public static final int SRC_ALPHA = 770;
  public static final int ONE_MINUS_SRC_ALPHA = 771;
  public static final int DST_ALPHA = 772;
  public static final int ONE_MINUS_DST_ALPHA = 773;
  public static final int DST_COLOR = 774;
  public static final int ONE_MINUS_DST_COLOR = 775;
  public static final int SRC_ALPHA_SATURATE = 776;
  public static final int NONE = 0;
  public static final int FRONT_LEFT = 1024;
  public static final int FRONT_RIGHT = 1025;
  public static final int BACK_LEFT = 1026;
  public static final int BACK_RIGHT = 1027;
  public static final int FRONT = 1028;
  public static final int BACK = 1029;
  public static final int LEFT = 1030;
  public static final int RIGHT = 1031;
  public static final int FRONT_AND_BACK = 1032;
  public static final int AUX0 = 1033;
  public static final int AUX1 = 1034;
  public static final int AUX2 = 1035;
  public static final int AUX3 = 1036;
  public static final int NO_ERROR = 0;
  public static final int INVALID_ENUM = 1280;
  public static final int INVALID_VALUE = 1281;
  public static final int INVALID_OPERATION = 1282;
  public static final int STACK_OVERFLOW = 1283;
  public static final int STACK_UNDERFLOW = 1284;
  public static final int OUT_OF_MEMORY = 1285;
  public static final int _2D = 1536;
  public static final int _3D = 1537;
  public static final int _3D_COLOR = 1538;
  public static final int _3D_COLOR_TEXTURE = 1539;
  public static final int _4D_COLOR_TEXTURE = 1540;
  public static final int PASS_THROUGH_TOKEN = 1792;
  public static final int POINT_TOKEN = 1793;
  public static final int LINE_TOKEN = 1794;
  public static final int POLYGON_TOKEN = 1795;
  public static final int BITMAP_TOKEN = 1796;
  public static final int DRAW_PIXEL_TOKEN = 1797;
  public static final int COPY_PIXEL_TOKEN = 1798;
  public static final int LINE_RESET_TOKEN = 1799;
  public static final int EXP = 2048;
  public static final int EXP2 = 2049;
  public static final int CW = 2304;
  public static final int CCW = 2305;
  public static final int COEFF = 2560;
  public static final int ORDER = 2561;
  public static final int DOMAIN = 2562;
  public static final int PIXEL_MAP_I_TO_I = 3184;
  public static final int PIXEL_MAP_S_TO_S = 3185;
  public static final int PIXEL_MAP_I_TO_R = 3186;
  public static final int PIXEL_MAP_I_TO_G = 3187;
  public static final int PIXEL_MAP_I_TO_B = 3188;
  public static final int PIXEL_MAP_I_TO_A = 3189;
  public static final int PIXEL_MAP_R_TO_R = 3190;
  public static final int PIXEL_MAP_G_TO_G = 3191;
  public static final int PIXEL_MAP_B_TO_B = 3192;
  public static final int PIXEL_MAP_A_TO_A = 3193;
  public static final int VERTEX_ARRAY_POINTER = 32910;
  public static final int NORMAL_ARRAY_POINTER = 32911;
  public static final int COLOR_ARRAY_POINTER = 32912;
  public static final int INDEX_ARRAY_POINTER = 32913;
  public static final int TEXTURE_COORD_ARRAY_POINTER = 32914;
  public static final int EDGE_FLAG_ARRAY_POINTER = 32915;
  public static final int CURRENT_COLOR = 2816;
  public static final int CURRENT_INDEX = 2817;
  public static final int CURRENT_NORMAL = 2818;
  public static final int CURRENT_TEXTURE_COORDS = 2819;
  public static final int CURRENT_RASTER_COLOR = 2820;
  public static final int CURRENT_RASTER_INDEX = 2821;
  public static final int CURRENT_RASTER_TEXTURE_COORDS = 2822;
  public static final int CURRENT_RASTER_POSITION = 2823;
  public static final int CURRENT_RASTER_POSITION_VALID = 2824;
  public static final int CURRENT_RASTER_DISTANCE = 2825;
  public static final int POINT_SMOOTH = 2832;
  public static final int POINT_SIZE = 2833;
  public static final int POINT_SIZE_RANGE = 2834;
  public static final int POINT_SIZE_GRANULARITY = 2835;
  public static final int LINE_SMOOTH = 2848;
  public static final int LINE_WIDTH = 2849;
  public static final int LINE_WIDTH_RANGE = 2850;
  public static final int LINE_WIDTH_GRANULARITY = 2851;
  public static final int LINE_STIPPLE = 2852;
  public static final int LINE_STIPPLE_PATTERN = 2853;
  public static final int LINE_STIPPLE_REPEAT = 2854;
  public static final int LIST_MODE = 2864;
  public static final int MAX_LIST_NESTING = 2865;
  public static final int LIST_BASE = 2866;
  public static final int LIST_INDEX = 2867;
  public static final int POLYGON_MODE = 2880;
  public static final int POLYGON_SMOOTH = 2881;
  public static final int POLYGON_STIPPLE = 2882;
  public static final int EDGE_FLAG = 2883;
  public static final int CULL_FACE = 2884;
  public static final int CULL_FACE_MODE = 2885;
  public static final int FRONT_FACE = 2886;
  public static final int LIGHTING = 2896;
  public static final int LIGHT_MODEL_LOCAL_VIEWER = 2897;
  public static final int LIGHT_MODEL_TWO_SIDE = 2898;
  public static final int LIGHT_MODEL_AMBIENT = 2899;
  public static final int SHADE_MODEL = 2900;
  public static final int COLOR_MATERIAL_FACE = 2901;
  public static final int COLOR_MATERIAL_PARAMETER = 2902;
  public static final int COLOR_MATERIAL = 2903;
  public static final int FOG = 2912;
  public static final int FOG_INDEX = 2913;
  public static final int FOG_DENSITY = 2914;
  public static final int FOG_START = 2915;
  public static final int FOG_END = 2916;
  public static final int FOG_MODE = 2917;
  public static final int FOG_COLOR = 2918;
  public static final int DEPTH_RANGE = 2928;
  public static final int DEPTH_TEST = 2929;
  public static final int DEPTH_WRITEMASK = 2930;
  public static final int DEPTH_CLEAR_VALUE = 2931;
  public static final int DEPTH_FUNC = 2932;
  public static final int ACCUM_CLEAR_VALUE = 2944;
  public static final int STENCIL_TEST = 2960;
  public static final int STENCIL_CLEAR_VALUE = 2961;
  public static final int STENCIL_FUNC = 2962;
  public static final int STENCIL_VALUE_MASK = 2963;
  public static final int STENCIL_FAIL = 2964;
  public static final int STENCIL_PASS_DEPTH_FAIL = 2965;
  public static final int STENCIL_PASS_DEPTH_PASS = 2966;
  public static final int STENCIL_REF = 2967;
  public static final int STENCIL_WRITEMASK = 2968;
  public static final int MATRIX_MODE = 2976;
  public static final int NORMALIZE = 2977;
  public static final int VIEWPORT = 2978;
  public static final int MODELVIEW_STACK_DEPTH = 2979;
  public static final int PROJECTION_STACK_DEPTH = 2980;
  public static final int TEXTURE_STACK_DEPTH = 2981;
  public static final int MODELVIEW_MATRIX = 2982;
  public static final int PROJECTION_MATRIX = 2983;
  public static final int TEXTURE_MATRIX = 2984;
  public static final int ATTRIB_STACK_DEPTH = 2992;
  public static final int CLIENT_ATTRIB_STACK_DEPTH = 2993;
  public static final int ALPHA_TEST = 3008;
  public static final int ALPHA_TEST_FUNC = 3009;
  public static final int ALPHA_TEST_REF = 3010;
  public static final int DITHER = 3024;
  public static final int BLEND_DST = 3040;
  public static final int BLEND_SRC = 3041;
  public static final int BLEND = 3042;
  public static final int LOGIC_OP_MODE = 3056;
  public static final int INDEX_LOGIC_OP = 3057;
  public static final int LOGIC_OP = 3057;
  public static final int COLOR_LOGIC_OP = 3058;
  public static final int AUX_BUFFERS = 3072;
  public static final int DRAW_BUFFER = 3073;
  public static final int READ_BUFFER = 3074;
  public static final int SCISSOR_BOX = 3088;
  public static final int SCISSOR_TEST = 3089;
  public static final int INDEX_CLEAR_VALUE = 3104;
  public static final int INDEX_WRITEMASK = 3105;
  public static final int COLOR_CLEAR_VALUE = 3106;
  public static final int COLOR_WRITEMASK = 3107;
  public static final int INDEX_MODE = 3120;
  public static final int RGBA_MODE = 3121;
  public static final int DOUBLEBUFFER = 3122;
  public static final int STEREO = 3123;
  public static final int RENDER_MODE = 3136;
  public static final int PERSPECTIVE_CORRECTION_HINT = 3152;
  public static final int POINT_SMOOTH_HINT = 3153;
  public static final int LINE_SMOOTH_HINT = 3154;
  public static final int POLYGON_SMOOTH_HINT = 3155;
  public static final int FOG_HINT = 3156;
  public static final int TEXTURE_GEN_S = 3168;
  public static final int TEXTURE_GEN_T = 3169;
  public static final int TEXTURE_GEN_R = 3170;
  public static final int TEXTURE_GEN_Q = 3171;
  public static final int PIXEL_MAP_I_TO_I_SIZE = 3248;
  public static final int PIXEL_MAP_S_TO_S_SIZE = 3249;
  public static final int PIXEL_MAP_I_TO_R_SIZE = 3250;
  public static final int PIXEL_MAP_I_TO_G_SIZE = 3251;
  public static final int PIXEL_MAP_I_TO_B_SIZE = 3252;
  public static final int PIXEL_MAP_I_TO_A_SIZE = 3253;
  public static final int PIXEL_MAP_R_TO_R_SIZE = 3254;
  public static final int PIXEL_MAP_G_TO_G_SIZE = 3255;
  public static final int PIXEL_MAP_B_TO_B_SIZE = 3256;
  public static final int PIXEL_MAP_A_TO_A_SIZE = 3257;
  public static final int UNPACK_SWAP_BYTES = 3312;
  public static final int UNPACK_LSB_FIRST = 3313;
  public static final int UNPACK_ROW_LENGTH = 3314;
  public static final int UNPACK_SKIP_ROWS = 3315;
  public static final int UNPACK_SKIP_PIXELS = 3316;
  public static final int UNPACK_ALIGNMENT = 3317;
  public static final int PACK_SWAP_BYTES = 3328;
  public static final int PACK_LSB_FIRST = 3329;
  public static final int PACK_ROW_LENGTH = 3330;
  public static final int PACK_SKIP_ROWS = 3331;
  public static final int PACK_SKIP_PIXELS = 3332;
  public static final int PACK_ALIGNMENT = 3333;
  public static final int MAP_COLOR = 3344;
  public static final int MAP_STENCIL = 3345;
  public static final int INDEX_SHIFT = 3346;
  public static final int INDEX_OFFSET = 3347;
  public static final int RED_SCALE = 3348;
  public static final int RED_BIAS = 3349;
  public static final int ZOOM_X = 3350;
  public static final int ZOOM_Y = 3351;
  public static final int GREEN_SCALE = 3352;
  public static final int GREEN_BIAS = 3353;
  public static final int BLUE_SCALE = 3354;
  public static final int BLUE_BIAS = 3355;
  public static final int ALPHA_SCALE = 3356;
  public static final int ALPHA_BIAS = 3357;
  public static final int DEPTH_SCALE = 3358;
  public static final int DEPTH_BIAS = 3359;
  public static final int MAX_EVAL_ORDER = 3376;
  public static final int MAX_LIGHTS = 3377;
  public static final int MAX_CLIP_PLANES = 3378;
  public static final int MAX_TEXTURE_SIZE = 3379;
  public static final int MAX_PIXEL_MAP_TABLE = 3380;
  public static final int MAX_ATTRIB_STACK_DEPTH = 3381;
  public static final int MAX_MODELVIEW_STACK_DEPTH = 3382;
  public static final int MAX_NAME_STACK_DEPTH = 3383;
  public static final int MAX_PROJECTION_STACK_DEPTH = 3384;
  public static final int MAX_TEXTURE_STACK_DEPTH = 3385;
  public static final int MAX_VIEWPORT_DIMS = 3386;
  public static final int MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
  public static final int SUBPIXEL_BITS = 3408;
  public static final int INDEX_BITS = 3409;
  public static final int RED_BITS = 3410;
  public static final int GREEN_BITS = 3411;
  public static final int BLUE_BITS = 3412;
  public static final int ALPHA_BITS = 3413;
  public static final int DEPTH_BITS = 3414;
  public static final int STENCIL_BITS = 3415;
  public static final int ACCUM_RED_BITS = 3416;
  public static final int ACCUM_GREEN_BITS = 3417;
  public static final int ACCUM_BLUE_BITS = 3418;
  public static final int ACCUM_ALPHA_BITS = 3419;
  public static final int NAME_STACK_DEPTH = 3440;
  public static final int AUTO_NORMAL = 3456;
  public static final int MAP1_COLOR_4 = 3472;
  public static final int MAP1_INDEX = 3473;
  public static final int MAP1_NORMAL = 3474;
  public static final int MAP1_TEXTURE_COORD_1 = 3475;
  public static final int MAP1_TEXTURE_COORD_2 = 3476;
  public static final int MAP1_TEXTURE_COORD_3 = 3477;
  public static final int MAP1_TEXTURE_COORD_4 = 3478;
  public static final int MAP1_VERTEX_3 = 3479;
  public static final int MAP1_VERTEX_4 = 3480;
  public static final int MAP2_COLOR_4 = 3504;
  public static final int MAP2_INDEX = 3505;
  public static final int MAP2_NORMAL = 3506;
  public static final int MAP2_TEXTURE_COORD_1 = 3507;
  public static final int MAP2_TEXTURE_COORD_2 = 3508;
  public static final int MAP2_TEXTURE_COORD_3 = 3509;
  public static final int MAP2_TEXTURE_COORD_4 = 3510;
  public static final int MAP2_VERTEX_3 = 3511;
  public static final int MAP2_VERTEX_4 = 3512;
  public static final int MAP1_GRID_DOMAIN = 3536;
  public static final int MAP1_GRID_SEGMENTS = 3537;
  public static final int MAP2_GRID_DOMAIN = 3538;
  public static final int MAP2_GRID_SEGMENTS = 3539;
  public static final int TEXTURE_1D = 3552;
  public static final int TEXTURE_2D = 3553;
  public static final int FEEDBACK_BUFFER_POINTER = 3568;
  public static final int FEEDBACK_BUFFER_SIZE = 3569;
  public static final int FEEDBACK_BUFFER_TYPE = 3570;
  public static final int SELECTION_BUFFER_POINTER = 3571;
  public static final int SELECTION_BUFFER_SIZE = 3572;
  public static final int POLYGON_OFFSET_UNITS = 10752;
  public static final int POLYGON_OFFSET_POINT = 10753;
  public static final int POLYGON_OFFSET_LINE = 10754;
  public static final int POLYGON_OFFSET_FILL = 32823;
  public static final int POLYGON_OFFSET_FACTOR = 32824;
  public static final int TEXTURE_BINDING_1D = 32872;
  public static final int TEXTURE_BINDING_2D = 32873;
  public static final int VERTEX_ARRAY = 32884;
  public static final int NORMAL_ARRAY = 32885;
  public static final int COLOR_ARRAY = 32886;
  public static final int INDEX_ARRAY = 32887;
  public static final int TEXTURE_COORD_ARRAY = 32888;
  public static final int EDGE_FLAG_ARRAY = 32889;
  public static final int VERTEX_ARRAY_SIZE = 32890;
  public static final int VERTEX_ARRAY_TYPE = 32891;
  public static final int VERTEX_ARRAY_STRIDE = 32892;
  public static final int NORMAL_ARRAY_TYPE = 32894;
  public static final int NORMAL_ARRAY_STRIDE = 32895;
  public static final int COLOR_ARRAY_SIZE = 32897;
  public static final int COLOR_ARRAY_TYPE = 32898;
  public static final int COLOR_ARRAY_STRIDE = 32899;
  public static final int INDEX_ARRAY_TYPE = 32901;
  public static final int INDEX_ARRAY_STRIDE = 32902;
  public static final int TEXTURE_COORD_ARRAY_SIZE = 32904;
  public static final int TEXTURE_COORD_ARRAY_TYPE = 32905;
  public static final int TEXTURE_COORD_ARRAY_STRIDE = 32906;
  public static final int EDGE_FLAG_ARRAY_STRIDE = 32908;
  public static final int TEXTURE_WIDTH = 4096;
  public static final int TEXTURE_HEIGHT = 4097;
  public static final int TEXTURE_INTERNAL_FORMAT = 4099;
  public static final int TEXTURE_COMPONENTS = 4099;
  public static final int TEXTURE_BORDER_COLOR = 4100;
  public static final int TEXTURE_BORDER = 4101;
  public static final int TEXTURE_RED_SIZE = 32860;
  public static final int TEXTURE_GREEN_SIZE = 32861;
  public static final int TEXTURE_BLUE_SIZE = 32862;
  public static final int TEXTURE_ALPHA_SIZE = 32863;
  public static final int TEXTURE_LUMINANCE_SIZE = 32864;
  public static final int TEXTURE_INTENSITY_SIZE = 32865;
  public static final int TEXTURE_PRIORITY = 32870;
  public static final int TEXTURE_RESIDENT = 32871;
  public static final int DONT_CARE = 4352;
  public static final int FASTEST = 4353;
  public static final int NICEST = 4354;
  public static final int AMBIENT = 4608;
  public static final int DIFFUSE = 4609;
  public static final int SPECULAR = 4610;
  public static final int POSITION = 4611;
  public static final int SPOT_DIRECTION = 4612;
  public static final int SPOT_EXPONENT = 4613;
  public static final int SPOT_CUTOFF = 4614;
  public static final int CONSTANT_ATTENUATION = 4615;
  public static final int LINEAR_ATTENUATION = 4616;
  public static final int QUADRATIC_ATTENUATION = 4617;
  public static final int COMPILE = 4864;
  public static final int COMPILE_AND_EXECUTE = 4865;
  public static final int BYTE = 5120;
  public static final int UNSIGNED_BYTE = 5121;
  public static final int SHORT = 5122;
  public static final int UNSIGNED_SHORT = 5123;
  public static final int INT = 5124;
  public static final int UNSIGNED_INT = 5125;
  public static final int FLOAT = 5126;
  public static final int _2_BYTES = 5127;
  public static final int _3_BYTES = 5128;
  public static final int _4_BYTES = 5129;
  public static final int DOUBLE = 5130;
  public static final int DOUBLE_EXT = 5130;
  public static final int CLEAR = 5376;
  public static final int AND = 5377;
  public static final int AND_REVERSE = 5378;
  public static final int COPY = 5379;
  public static final int AND_INVERTED = 5380;
  public static final int NOOP = 5381;
  public static final int XOR = 5382;
  public static final int OR = 5383;
  public static final int NOR = 5384;
  public static final int EQUIV = 5385;
  public static final int INVERT = 5386;
  public static final int OR_REVERSE = 5387;
  public static final int COPY_INVERTED = 5388;
  public static final int OR_INVERTED = 5389;
  public static final int NAND = 5390;
  public static final int SET = 5391;
  public static final int EMISSION = 5632;
  public static final int SHININESS = 5633;
  public static final int AMBIENT_AND_DIFFUSE = 5634;
  public static final int COLOR_INDEXES = 5635;
  public static final int MODELVIEW = 5888;
  public static final int PROJECTION = 5889;
  public static final int TEXTURE = 5890;
  public static final int COLOR = 6144;
  public static final int DEPTH = 6145;
  public static final int STENCIL = 6146;
  public static final int COLOR_INDEX = 6400;
  public static final int STENCIL_INDEX = 6401;
  public static final int DEPTH_COMPONENT = 6402;
  public static final int RED = 6403;
  public static final int GREEN = 6404;
  public static final int BLUE = 6405;
  public static final int ALPHA = 6406;
  public static final int RGB = 6407;
  public static final int RGBA = 6408;
  public static final int LUMINANCE = 6409;
  public static final int LUMINANCE_ALPHA = 6410;
  public static final int BITMAP = 6656;
  public static final int POINT = 6912;
  public static final int LINE = 6913;
  public static final int FILL = 6914;
  public static final int RENDER = 7168;
  public static final int FEEDBACK = 7169;
  public static final int SELECT = 7170;
  public static final int FLAT = 7424;
  public static final int SMOOTH = 7425;
  public static final int KEEP = 7680;
  public static final int REPLACE = 7681;
  public static final int INCR = 7682;
  public static final int DECR = 7683;
  public static final int VENDOR = 7936;
  public static final int RENDERER = 7937;
  public static final int VERSION = 7938;
  public static final int EXTENSIONS = 7939;
  public static final int S = 8192;
  public static final int T = 8193;
  public static final int R = 8194;
  public static final int Q = 8195;
  public static final int MODULATE = 8448;
  public static final int DECAL = 8449;
  public static final int TEXTURE_ENV_MODE = 8704;
  public static final int TEXTURE_ENV_COLOR = 8705;
  public static final int TEXTURE_ENV = 8960;
  public static final int EYE_LINEAR = 9216;
  public static final int OBJECT_LINEAR = 9217;
  public static final int SPHERE_MAP = 9218;
  public static final int TEXTURE_GEN_MODE = 9472;
  public static final int OBJECT_PLANE = 9473;
  public static final int EYE_PLANE = 9474;
  public static final int NEAREST = 9728;
  public static final int LINEAR = 9729;
  public static final int NEAREST_MIPMAP_NEAREST = 9984;
  public static final int LINEAR_MIPMAP_NEAREST = 9985;
  public static final int NEAREST_MIPMAP_LINEAR = 9986;
  public static final int LINEAR_MIPMAP_LINEAR = 9987;
  public static final int TEXTURE_MAG_FILTER = 10240;
  public static final int TEXTURE_MIN_FILTER = 10241;
  public static final int TEXTURE_WRAP_S = 10242;
  public static final int TEXTURE_WRAP_T = 10243;
  public static final int PROXY_TEXTURE_1D = 32867;
  public static final int PROXY_TEXTURE_2D = 32868;
  public static final int CLAMP = 10496;
  public static final int REPEAT = 10497;
  public static final int R3_G3_B2 = 10768;
  public static final int ALPHA4 = 32827;
  public static final int ALPHA8 = 32828;
  public static final int ALPHA12 = 32829;
  public static final int ALPHA16 = 32830;
  public static final int LUMINANCE4 = 32831;
  public static final int LUMINANCE8 = 32832;
  public static final int LUMINANCE12 = 32833;
  public static final int LUMINANCE16 = 32834;
  public static final int LUMINANCE4_ALPHA4 = 32835;
  public static final int LUMINANCE6_ALPHA2 = 32836;
  public static final int LUMINANCE8_ALPHA8 = 32837;
  public static final int LUMINANCE12_ALPHA4 = 32838;
  public static final int LUMINANCE12_ALPHA12 = 32839;
  public static final int LUMINANCE16_ALPHA16 = 32840;
  public static final int INTENSITY = 32841;
  public static final int INTENSITY4 = 32842;
  public static final int INTENSITY8 = 32843;
  public static final int INTENSITY12 = 32844;
  public static final int INTENSITY16 = 32845;
  public static final int RGB4 = 32847;
  public static final int RGB5 = 32848;
  public static final int RGB8 = 32849;
  public static final int RGB10 = 32850;
  public static final int RGB12 = 32851;
  public static final int RGB16 = 32852;
  public static final int RGBA2 = 32853;
  public static final int RGBA4 = 32854;
  public static final int RGB5_A1 = 32855;
  public static final int RGBA8 = 32856;
  public static final int RGB10_A2 = 32857;
  public static final int RGBA12 = 32858;
  public static final int RGBA16 = 32859;
  public static final int V2F = 10784;
  public static final int V3F = 10785;
  public static final int C4UB_V2F = 10786;
  public static final int C4UB_V3F = 10787;
  public static final int C3F_V3F = 10788;
  public static final int N3F_V3F = 10789;
  public static final int C4F_N3F_V3F = 10790;
  public static final int T2F_V3F = 10791;
  public static final int T4F_V4F = 10792;
  public static final int T2F_C4UB_V3F = 10793;
  public static final int T2F_C3F_V3F = 10794;
  public static final int T2F_N3F_V3F = 10795;
  public static final int T2F_C4F_N3F_V3F = 10796;
  public static final int T4F_C4F_N3F_V4F = 10797;
  public static final int CLIP_PLANE0 = 12288;
  public static final int CLIP_PLANE1 = 12289;
  public static final int CLIP_PLANE2 = 12290;
  public static final int CLIP_PLANE3 = 12291;
  public static final int CLIP_PLANE4 = 12292;
  public static final int CLIP_PLANE5 = 12293;
  public static final int LIGHT0 = 16384;
  public static final int LIGHT1 = 16385;
  public static final int LIGHT2 = 16386;
  public static final int LIGHT3 = 16387;
  public static final int LIGHT4 = 16388;
  public static final int LIGHT5 = 16389;
  public static final int LIGHT6 = 16390;
  public static final int LIGHT7 = 16391;
}