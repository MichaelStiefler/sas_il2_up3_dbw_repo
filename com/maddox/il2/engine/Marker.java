// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Marker.java

package com.maddox.il2.engine;

import com.maddox.opengl.gl;

// Referenced classes of package com.maddox.il2.engine:
//            Camera3D

public class Marker
{

    private Marker()
    {
    }

    public static void cube(com.maddox.il2.engine.Camera3D camera3d, float f, float f1, float f2, float f3)
    {
        boolean flag = false;
        f3 = (float)((double)f3 * 0.5D);
        for(int i = 0; i < 8; i++)
        {
            if((i & 1) != 0)
                xx[i] = f + f3;
            else
                xx[i] = f - f3;
            if((i & 2) != 0)
                yy[i] = f1 + f3;
            else
                yy[i] = f1 - f3;
            if((i & 4) != 0)
                zz[i] = f2 + f3;
            else
                zz[i] = f2 - f3;
        }

        com.maddox.opengl.gl.GetBooleanv(3553, v);
        if(v[0])
        {
            com.maddox.opengl.gl.Disable(3553);
            flag = true;
        }
        com.maddox.opengl.gl.Begin(7);
        for(int j = 0; j < 24; j++)
        {
            int k = codes[j];
            com.maddox.il2.engine.Marker.Vertex3f(camera3d, xx[k], yy[k], zz[k]);
        }

        com.maddox.opengl.gl.End();
        if(flag)
            com.maddox.opengl.gl.Enable(3553);
    }

    public static void cubeXYZ(com.maddox.il2.engine.Camera3D camera3d, float f, float f1, float f2, float f3)
    {
        boolean flag = false;
        f3 = (float)((double)f3 * 0.5D);
        for(int i = 0; i < 8; i++)
        {
            if((i & 1) != 0)
            {
                xx[i] = f + f3;
                bb[i] = 1.0F;
            } else
            {
                xx[i] = f - f3;
                bb[i] = 0.0F;
            }
            if((i & 2) != 0)
            {
                yy[i] = f1 + f3;
                gg[i] = 1.0F;
            } else
            {
                yy[i] = f1 - f3;
                gg[i] = 0.0F;
            }
            if((i & 4) != 0)
            {
                zz[i] = f2 + f3;
                rr[i] = 1.0F;
            } else
            {
                zz[i] = f2 - f3;
                rr[i] = 0.0F;
            }
        }

        com.maddox.opengl.gl.GetBooleanv(3553, v);
        if(v[0])
        {
            com.maddox.opengl.gl.Disable(3553);
            flag = true;
        }
        com.maddox.opengl.gl.Begin(7);
        for(int j = 0; j < 24; j++)
        {
            int k = codes[j];
            com.maddox.opengl.gl.Color3f(rr[k], gg[k], bb[k]);
            com.maddox.il2.engine.Marker.Vertex3f(camera3d, xx[k], yy[k], zz[k]);
        }

        com.maddox.opengl.gl.End();
        if(flag)
            com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.Color3f(1.0F, 1.0F, 1.0F);
    }

    public static void triangleZ(com.maddox.il2.engine.Camera3D camera3d, float f, float f1, float f2, float f3)
    {
        boolean flag = false;
        f3 = (float)((double)f3 * 0.5D);
        float f4 = f3 * 0.5F;
        com.maddox.opengl.gl.GetBooleanv(3553, v);
        if(v[0])
        {
            com.maddox.opengl.gl.Disable(3553);
            flag = true;
        }
        com.maddox.opengl.gl.Begin(4);
        com.maddox.il2.engine.Marker.Vertex3f(camera3d, f, f1 + f3, f2);
        com.maddox.il2.engine.Marker.Vertex3f(camera3d, f + f3, f1 - f4, f2);
        com.maddox.il2.engine.Marker.Vertex3f(camera3d, f - f3, f1 - f4, f2);
        com.maddox.opengl.gl.End();
        if(flag)
            com.maddox.opengl.gl.Enable(3553);
    }

    private static void Vertex3f(com.maddox.il2.engine.Camera3D camera3d, float f, float f1, float f2)
    {
        com.maddox.opengl.gl.Vertex3f(f - camera3d.XOffset, f1 - camera3d.YOffset, f2);
    }

    private static int codes[] = {
        0, 1, 3, 2, 4, 5, 7, 6, 0, 2, 
        6, 4, 1, 3, 7, 5, 0, 1, 5, 4, 
        2, 3, 7, 6
    };
    private static boolean v[] = new boolean[2];
    private static float xx[] = new float[8];
    private static float yy[] = new float[8];
    private static float zz[] = new float[8];
    private static float bb[] = new float[8];
    private static float gg[] = new float[8];
    private static float rr[] = new float[8];

}
