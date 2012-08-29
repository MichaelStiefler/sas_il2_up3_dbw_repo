// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlaneCloud.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.opengl.gl;

public class PlaneCloud
{

    public boolean isShow()
    {
        return bShow;
    }

    public void setShow(boolean flag)
    {
        bShow = flag;
    }

    public void setHeight(float f, float f1)
    {
        z0 = f;
        z1 = f1;
    }

    public float getHeightMin()
    {
        return z0;
    }

    public float getHeightMax()
    {
        return z1;
    }

    public void preRender()
    {
        if(!bShow)
        {
            return;
        } else
        {
            mat0.preRender();
            mat1.preRender();
            return;
        }
    }

    private void CalcLightColor(com.maddox.il2.engine.Mat mat)
    {
        LightColor = mat.LightColor(Color, mat.SunLightf(SurfNorm));
    }

    public void renderFar()
    {
        com.maddox.il2.engine.Render.drawSetMaterial(mat1, (float)p.x, (float)p.y, (float)p.z, 60000F);
        CalcLightColor(mat1);
        if(z0 > 0.0F)
            draw(((int)(p.x / 4000D) - 1 - 9) * 4000, ((int)(p.y / 4000D) - 1 - 9) * 4000, 1.59375E-005F, 12000F, z0);
        if(z1 > 0.0F)
            draw(((int)(p.x / 4000D) - 1 - 9) * 4000, ((int)(p.y / 4000D) - 1 - 9) * 4000, 3.864583E-005F, 12000F, z1);
    }

    public void render()
    {
        if(!bShow)
            return;
        com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Render.currentCamera();
        camera3d.activateWorldMode(0);
        com.maddox.opengl.gl.GetFloatv(2982, Mtx);
        camera3d.deactivateWorldMode();
        camera3d.pos.getRender(p, o);
        com.maddox.il2.engine.Render.drawBeginTriangleLists(0);
        com.maddox.il2.engine.Render.drawSetMaterial(mat0, (float)p.x, (float)p.y, (float)p.z, 60000F);
        CalcLightColor(mat0);
        if(z0 > 0.0F)
            draw0(((int)(p.x / 4000D) - 1) * 4000, ((int)(p.y / 4000D) - 1) * 4000, 1.59375E-005F, 1000F, 12, z0);
        if(z1 > 0.0F)
            draw0(((int)(p.x / 4000D) - 1) * 4000, ((int)(p.y / 4000D) - 1) * 4000, 3.864583E-005F, 1000F, 12, z1);
        renderFar();
        com.maddox.il2.engine.Render.drawEnd();
    }

    private void draw(float f, float f1, float f2, float f3, float f4)
    {
        int i = 0;
        int j = 0;
        float f5 = f1;
        float f6 = 1.225E+009F;
        float f7 = 3.6E+009F;
        float f8 = 1.0F / (f7 - f6);
        float f9 = f4 > z0 ? 8000F : 0.0F;
        LightColor &= 0xffffff;
        float f10 = (f * f2) % 1.0F;
        float f11 = (f1 * f2) % 1.0F;
        float f12 = f3 * f2;
        for(int k = 0; k <= 9; k++)
        {
            float f13 = f;
            for(int i1 = 0; i1 <= 9; i1++)
            {
                float f14 = (f13 - f - f3 * 4F) * (f13 - f - f3 * 4F) + (f5 - f1 - f3 * 4F) * (f5 - f1 - f3 * 4F);
                float f15 = 1.0F;
                if(f14 > f6)
                    f15 = (f7 - f14) * f8;
                xyzuv[i++] = f13;
                xyzuv[i++] = f5;
                xyzuv[i++] = f4 * f15 + f9 * (1.0F - f15);
                xyzuv[i++] = f10 + (float)i1 * f12;
                xyzuv[i++] = f11 + (float)k * f12;
                int k1 = (int)(f15 * 255F);
                if(k1 < 0)
                    k1 = 0;
                colorDS[j++] = LightColor | k1 << 24;
                colorDS[j++] = 0;
                f13 += f3;
            }

            f5 += f3;
        }

        i = 0;
        for(int l = 0; l < 9; l++)
        {
            for(int j1 = 0; j1 < 9; j1++)
                if(l != 3 || j1 != 3)
                {
                    indexes[i++] = l * 10 + j1;
                    indexes[i++] = l * 10 + j1 + 1;
                    indexes[i++] = (l + 1) * 10 + j1 + 1;
                    indexes[i++] = (l + 1) * 10 + j1 + 1;
                    indexes[i++] = (l + 1) * 10 + j1;
                    indexes[i++] = l * 10 + j1;
                }

        }

        com.maddox.il2.engine.Render.drawTriangleList(xyzuv, colorDS, 100, indexes, i / 3);
    }

    private void draw0(float f, float f1, float f2, float f3, int i, float f4)
    {
        com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Render.currentCamera();
        float f5 = Mtx[2];
        float f6 = Mtx[6];
        float f7 = Mtx[10];
        float f8 = Mtx[14];
        float f9 = camera3d.ZNear;
        int j = 0;
        int k = 0;
        float f10 = (f * f2) % 1.0F;
        float f11 = (f1 * f2) % 1.0F;
        float f12 = f3 * f2;
        float f13 = f1;
        float f14 = -(f9 + f4 * f7 + f8);
        for(int l = 0; l <= i; l++)
        {
            float f15 = f;
            float f16 = f14 - f13 * f6;
            for(int k1 = 0; k1 <= i; k1++)
            {
                xyzuv[j + 0] = f15;
                xyzuv[j + 1] = f13;
                xyzuv[j + 2] = f4;
                xyzuv[j + 3] = f10 + (float)k1 * f12;
                xyzuv[j + 4] = f11 + (float)l * f12;
                Clip[k++] = f15 * f5 > f16 ? 0 : 1;
                j += 5;
                f15 += f3;
            }

            f13 += f3;
        }

        j = 0;
        int i1 = 0;
        for(int j1 = 0; j1 < i; j1++)
        {
            for(int l1 = 0; l1 < i; l1++)
            {
                int i2 = j1 * (i + 1) + l1;
                int j2 = i2 + i + 1;
                if((Clip[i2] | Clip[i2 + 1] | Clip[j2] | Clip[j2 + 1]) != 0)
                {
                    indexes[j + 0] = i2;
                    indexes[j + 1] = i2 + 1;
                    indexes[j + 2] = j2 + 1;
                    indexes[j + 3] = j2 + 1;
                    indexes[j + 4] = j2;
                    indexes[j + 5] = i2;
                    j += 6;
                    i1 += 2;
                }
            }

        }

        com.maddox.il2.engine.Render.drawTriangleList(xyzuv, (i + 1) * (i + 1), LightColor, 0, indexes, i1);
    }

    public PlaneCloud(float f, float f1)
    {
        bShow = true;
        p = new Point3d();
        o = new Orient();
        xyzuv = new float[845];
        colorDS = new int[338];
        Clip = new int[169];
        indexes = new int[864];
        Mtx = new float[16];
        Color = -1;
        SurfNorm = new Vector3f(0.0F, 0.0F, 1.0F);
        z0 = f;
        z1 = f1;
        mat0 = com.maddox.il2.engine.Mat.New("3do/Effects/PlaneCloud/Cloud0.mat");
        mat1 = com.maddox.il2.engine.Mat.New("3do/Effects/PlaneCloud/Cloud1.mat");
    }

    public static final float HEIGHT_MIN = 500F;
    public static final float HEIGHT_MAX = 4100F;
    private boolean bShow;
    private static final float Scale0 = 0.3825F;
    private static final float Scale1 = 0.9274999F;
    private com.maddox.il2.engine.Mat mat0;
    private com.maddox.il2.engine.Mat mat1;
    private float z0;
    private float z1;
    private static final int MAXCOUNT = 12;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient o;
    private float xyzuv[];
    private int colorDS[];
    private int Clip[];
    private int indexes[];
    private float Mtx[];
    int Color;
    int LightColor;
    com.maddox.JGP.Vector3f SurfNorm;
}
