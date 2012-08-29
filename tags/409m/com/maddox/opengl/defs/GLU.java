// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GLU.java

package com.maddox.opengl.defs;


public interface GLU
{

    public static final int VERSION_1_1 = 1;
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static final int VERSION = 0x189c0;
    public static final int EXTENSIONS = 0x189c1;
    public static final int INVALID_ENUM = 0x18a24;
    public static final int INVALID_VALUE = 0x18a25;
    public static final int OUT_OF_MEMORY = 0x18a26;
    public static final int INCOMPATIBLE_GL_VERSION = 0x18a27;
    public static final int OUTLINE_POLYGON = 0x18790;
    public static final int OUTLINE_PATCH = 0x18791;
    public static final int ERROR = 0x18707;
    public static final int NURBS_ERROR1 = 0x1879b;
    public static final int NURBS_ERROR2 = 0x1879c;
    public static final int NURBS_ERROR3 = 0x1879d;
    public static final int NURBS_ERROR4 = 0x1879e;
    public static final int NURBS_ERROR5 = 0x1879f;
    public static final int NURBS_ERROR6 = 0x187a0;
    public static final int NURBS_ERROR7 = 0x187a1;
    public static final int NURBS_ERROR8 = 0x187a2;
    public static final int NURBS_ERROR9 = 0x187a3;
    public static final int NURBS_ERROR10 = 0x187a4;
    public static final int NURBS_ERROR11 = 0x187a5;
    public static final int NURBS_ERROR12 = 0x187a6;
    public static final int NURBS_ERROR13 = 0x187a7;
    public static final int NURBS_ERROR14 = 0x187a8;
    public static final int NURBS_ERROR15 = 0x187a9;
    public static final int NURBS_ERROR16 = 0x187aa;
    public static final int NURBS_ERROR17 = 0x187ab;
    public static final int NURBS_ERROR18 = 0x187ac;
    public static final int NURBS_ERROR19 = 0x187ad;
    public static final int NURBS_ERROR20 = 0x187ae;
    public static final int NURBS_ERROR21 = 0x187af;
    public static final int NURBS_ERROR22 = 0x187b0;
    public static final int NURBS_ERROR23 = 0x187b1;
    public static final int NURBS_ERROR24 = 0x187b2;
    public static final int NURBS_ERROR25 = 0x187b3;
    public static final int NURBS_ERROR26 = 0x187b4;
    public static final int NURBS_ERROR27 = 0x187b5;
    public static final int NURBS_ERROR28 = 0x187b6;
    public static final int NURBS_ERROR29 = 0x187b7;
    public static final int NURBS_ERROR30 = 0x187b8;
    public static final int NURBS_ERROR31 = 0x187b9;
    public static final int NURBS_ERROR32 = 0x187ba;
    public static final int NURBS_ERROR33 = 0x187bb;
    public static final int NURBS_ERROR34 = 0x187bc;
    public static final int NURBS_ERROR35 = 0x187bd;
    public static final int NURBS_ERROR36 = 0x187be;
    public static final int NURBS_ERROR37 = 0x187bf;
    public static final int AUTO_LOAD_MATRIX = 0x18768;
    public static final int CULLING = 0x18769;
    public static final int SAMPLING_TOLERANCE = 0x1876b;
    public static final int DISPLAY_MODE = 0x1876c;
    public static final int PARAMETRIC_TOLERANCE = 0x1876a;
    public static final int SAMPLING_METHOD = 0x1876d;
    public static final int U_STEP = 0x1876e;
    public static final int V_STEP = 0x1876f;
    public static final int PATH_LENGTH = 0x18777;
    public static final int PARAMETRIC_ERROR = 0x18778;
    public static final int DOMAIN_DISTANCE = 0x18779;
    public static final int MAP1_TRIM_2 = 0x18772;
    public static final int MAP1_TRIM_3 = 0x18773;
    public static final int POINT = 0x186aa;
    public static final int LINE = 0x186ab;
    public static final int FILL = 0x186ac;
    public static final int SILHOUETTE = 0x186ad;
    public static final int SMOOTH = 0x186a0;
    public static final int FLAT = 0x186a1;
    public static final int NONE = 0x186a2;
    public static final int OUTSIDE = 0x186b4;
    public static final int INSIDE = 0x186b5;
    public static final int TESS_BEGIN = 0x18704;
    public static final int BEGIN = 0x18704;
    public static final int TESS_VERTEX = 0x18705;
    public static final int VERTEX = 0x18705;
    public static final int TESS_END = 0x18706;
    public static final int END = 0x18706;
    public static final int TESS_ERROR = 0x18707;
    public static final int TESS_EDGE_FLAG = 0x18708;
    public static final int EDGE_FLAG = 0x18708;
    public static final int CW = 0x18718;
    public static final int CCW = 0x18719;
    public static final int INTERIOR = 0x1871a;
    public static final int EXTERIOR = 0x1871b;
    public static final int UNKNOWN = 0x1871c;
    public static final int TESS_ERROR1 = 0x18737;
    public static final int TESS_ERROR2 = 0x18738;
    public static final int TESS_ERROR3 = 0x18739;
    public static final int TESS_ERROR4 = 0x1873a;
    public static final int TESS_ERROR5 = 0x1873b;
    public static final int TESS_ERROR6 = 0x1873c;
    public static final int TESS_ERROR7 = 0x1873d;
    public static final int TESS_ERROR8 = 0x1873e;
    public static final int TESS_MISSING_BEGIN_POLYGON = 0x18737;
    public static final int TESS_MISSING_BEGIN_CONTOUR = 0x18738;
    public static final int TESS_MISSING_END_POLYGON = 0x18739;
    public static final int TESS_MISSING_END_CONTOUR = 0x1873a;
    public static final int TESS_COORD_TOO_LARGE = 0x1873b;
    public static final int TESS_NEED_COMBINE_CALLBACK = 0x1873c;
}
