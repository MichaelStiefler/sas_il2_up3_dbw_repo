// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   glu.java

package com.maddox.opengl;


// Referenced classes of package com.maddox.opengl:
//            GLUNurbs, GLUQuadric, GLUTesselator, gl

public final class glu
{

    public glu()
    {
    }

    public static final native void Ortho2D(double d, double d1, double d2, double d3);

    public static final native void Perspective(double d, double d1, double d2, double d3);

    public static final native void LookAt(double d, double d1, double d2, double d3, 
            double d4, double d5, double d6, double d7, double d8);

    public static final native int Project(double d, double d1, double d2, double ad[], double ad1[], 
            int ai[], double ad2[], double ad3[], double ad4[]);

    public static final native int UnProject(double d, double d1, double d2, double ad[], double ad1[], 
            int ai[], double ad2[], double ad3[], double ad4[]);

    public static final native int ScaleImage(int i, int j, int k, int l, byte abyte0[], int i1, int j1, int k1, 
            byte abyte1[]);

    public static final native void PickMatrix(double d, double d1, double d2, double d3, 
            int ai[]);

    public static final native int Build1DMipmaps(int i, int j, int k, int l, int i1, byte abyte0[]);

    public static final native int Build1DMipmaps(int i, int j, int k, int l, int i1, short aword0[]);

    public static final native int Build1DMipmaps(int i, int j, int k, int l, int i1, int ai[]);

    public static final native int Build1DMipmaps(int i, int j, int k, int l, int i1, float af[]);

    public static final native int Build2DMipmaps(int i, int j, int k, int l, int i1, int j1, byte abyte0[]);

    public static final native int Build2DMipmaps(int i, int j, int k, int l, int i1, int j1, short aword0[]);

    public static final native int Build2DMipmaps(int i, int j, int k, int l, int i1, int j1, int ai[]);

    public static final native int Build2DMipmaps(int i, int j, int k, int l, int i1, int j1, float af[]);

    public static final native java.lang.String ErrorString(int i);

    public static final native java.lang.String GetString(int i);

    public static final com.maddox.opengl.GLUNurbs NewNurbsRenderer()
    {
        return new GLUNurbs();
    }

    public static final void DeleteNurbsRenderer(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.Delete();
    }

    public static final void NurbsProperty(com.maddox.opengl.GLUNurbs glunurbs, int i, float f)
    {
        glunurbs.Property(i, f);
    }

    public static final void GetNurbsProperty(com.maddox.opengl.GLUNurbs glunurbs, int i, float af[])
    {
        glunurbs.GetProperty(i, af);
    }

    public static final void LoadSamplingMatrices(com.maddox.opengl.GLUNurbs glunurbs, float af[], float af1[], int ai[])
    {
        glunurbs.LoadSamplingMatrices(af, af1, ai);
    }

    public static final void BeginSurface(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.BeginSurface();
    }

    public static final void EndSurface(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.EndSurface();
    }

    public static final void NurbsSurface(com.maddox.opengl.GLUNurbs glunurbs, int i, float af[], int j, float af1[], int k, int l, float af2[], 
            int i1, int j1, int k1)
    {
        glunurbs.Surface(i, af, j, af1, k, l, af2, i1, j1, k1);
    }

    public static final void BeginCurve(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.BeginCurve();
    }

    public static final void EndCurve(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.EndCurve();
    }

    public static final void NurbsCurve(com.maddox.opengl.GLUNurbs glunurbs, int i, float af[], int j, float af1[], int k, int l)
    {
        glunurbs.Curve(i, af, j, af1, k, l);
    }

    public static final void BeginTrim(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.BeginTrim();
    }

    public static final void EndTrim(com.maddox.opengl.GLUNurbs glunurbs)
    {
        glunurbs.EndTrim();
    }

    public static final void PwlCurve(com.maddox.opengl.GLUNurbs glunurbs, int i, float af[], int j, int k)
    {
        glunurbs.PwlCurve(i, af, j, k);
    }

    public static final com.maddox.opengl.GLUQuadric NewQuadric()
    {
        return new GLUQuadric();
    }

    public static final void DeleteQuadric(com.maddox.opengl.GLUQuadric gluquadric)
    {
        gluquadric.Delete();
    }

    public static final void QuadricDrawStyle(com.maddox.opengl.GLUQuadric gluquadric, int i)
    {
        gluquadric.DrawStyle(i);
    }

    public static final void QuadricNormals(com.maddox.opengl.GLUQuadric gluquadric, int i)
    {
        gluquadric.Normals(i);
    }

    public static final void QuadricOrientation(com.maddox.opengl.GLUQuadric gluquadric, int i)
    {
        gluquadric.Orientation(i);
    }

    public static final void QuadricTexture(com.maddox.opengl.GLUQuadric gluquadric, boolean flag)
    {
        gluquadric.Texture(flag);
    }

    public static final void Sphere(com.maddox.opengl.GLUQuadric gluquadric, double d, int i, int j)
    {
        gluquadric.Sphere(d, i, j);
    }

    public static final void Cylinder(com.maddox.opengl.GLUQuadric gluquadric, double d, double d1, double d2, int i, 
            int j)
    {
        gluquadric.Cylinder(d, d1, d2, i, j);
    }

    public static final void Disk(com.maddox.opengl.GLUQuadric gluquadric, double d, double d1, int i, int j)
    {
        gluquadric.Disk(d, d1, i, j);
    }

    public static final void PartialDisk(com.maddox.opengl.GLUQuadric gluquadric, double d, double d1, int i, int j, double d2, double d3)
    {
        gluquadric.PartialDisk(d, d1, i, j, d2, d3);
    }

    public static final com.maddox.opengl.GLUTesselator NewTess()
    {
        return new GLUTesselator();
    }

    public static final void DeleteTess(com.maddox.opengl.GLUTesselator glutesselator)
    {
        glutesselator.Delete();
    }

    public static final void TessVertex(com.maddox.opengl.GLUTesselator glutesselator, double ad[], float af[])
    {
        glutesselator.Vertex(ad, af);
    }

    public static final void TessVertex(com.maddox.opengl.GLUTesselator glutesselator, double ad[], int ai[])
    {
        glutesselator.Vertex(ad, ai);
    }

    public static final void TessVertex(com.maddox.opengl.GLUTesselator glutesselator, double ad[], double ad1[])
    {
        glutesselator.Vertex(ad, ad1);
    }

    public static final void BeginPolygon(com.maddox.opengl.GLUTesselator glutesselator)
    {
        glutesselator.BeginPolygon();
    }

    public static final void EndPolygon(com.maddox.opengl.GLUTesselator glutesselator)
    {
        glutesselator.EndPolygon();
    }

    public static final void NextContour(com.maddox.opengl.GLUTesselator glutesselator, int i)
    {
        glutesselator.NextContour(i);
    }

    static 
    {
        com.maddox.opengl.gl.loadNative();
    }
}
