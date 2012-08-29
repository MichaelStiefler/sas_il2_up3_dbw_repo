// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IconDraw.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.engine:
//            CameraOrtho2D, Mat, Actor, ActorPos, 
//            Render, Config

public class IconDraw
{

    public static boolean preRender(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return false;
        if(actor.icon == null)
            return false;
        if(actor.icon.preRender() == 0)
        {
            return false;
        } else
        {
            actor.pos.getRender(_point);
            com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
            return cameraortho2d.isSphereVisible(_point, (float)((double)r / cameraortho2d.worldScale));
        }
    }

    public static void render(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.icon == null)
        {
            return;
        } else
        {
            actor.pos.getRender(_point);
            com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
            float f = (float)((_point.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale - (double)(dx / 2));
            float f1 = (float)((_point.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale - (double)(dy / 2));
            com.maddox.il2.engine.Render.drawTile(f, f1, dx, dy, 0.0F, actor.icon, color, 0.0F, 1.0F, 1.0F, -1F);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Actor actor, double d, double d1)
    {
        com.maddox.il2.engine.IconDraw.render(actor, (float)d, (float)d1);
    }

    public static void render(com.maddox.il2.engine.Actor actor, float f, float f1)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.icon == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.Render.drawTile(f - (float)(dx / 2), f1 - (float)(dy / 2), dx, dy, 0.0F, actor.icon, color, 0.0F, 1.0F, 1.0F, -1F);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Actor actor, float f, float f1, float f2, float f3)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.icon == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.Render.drawTile(f - (float)dx * f2, f1 - (float)dy * f3, dx, dy, 0.0F, actor.icon, color, 0.0F, 1.0F, 1.0F, -1F);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Actor actor, float f, float f1, float f2)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.icon == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.IconDraw.render(actor.icon, f, f1, f2);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Mat mat, double d, double d1)
    {
        com.maddox.il2.engine.IconDraw.render(mat, (float)d, (float)d1);
    }

    public static void render(com.maddox.il2.engine.Mat mat, float f, float f1)
    {
        if(mat == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.Render.drawTile(f - (float)(dx / 2), f1 - (float)(dy / 2), dx, dy, 0.0F, mat, color, 0.0F, 1.0F, 1.0F, -1F);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Mat mat, float f, float f1, float f2, float f3)
    {
        if(mat == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.Render.drawTile(f - (float)dx * f2, f1 - (float)dy * f3, dx, dy, 0.0F, mat, color, 0.0F, 1.0F, 1.0F, -1F);
            return;
        }
    }

    public static void render(com.maddox.il2.engine.Mat mat, float f, float f1, float f2)
    {
        if(mat == null)
        {
            return;
        } else
        {
            float f3 = dx / 2;
            float f4 = dy / 2;
            float f5 = (float)java.lang.Math.sin(((double)f2 * 3.1415926535897931D) / 180D);
            float f6 = (float)java.lang.Math.cos(((double)f2 * 3.1415926535897931D) / 180D);
            com.maddox.il2.engine.IconDraw._setXyzuv(0, f, f1, -f3, -f4, f5, f6, 0.0F, 1.0F);
            com.maddox.il2.engine.IconDraw._setXyzuv(1, f, f1, f3, -f4, f5, f6, 1.0F, 1.0F);
            com.maddox.il2.engine.IconDraw._setXyzuv(2, f, f1, f3, f4, f5, f6, 1.0F, 0.0F);
            com.maddox.il2.engine.IconDraw._setXyzuv(3, f, f1, -f3, f4, f5, f6, 0.0F, 0.0F);
            com.maddox.il2.engine.Render.drawBeginTriangleLists(-1);
            com.maddox.il2.engine.Render.drawSetMaterial(mat);
            com.maddox.il2.engine.Render.drawTriangleList(_xyzuv, 4, color, 0, _xyzuvIndx, 2);
            com.maddox.il2.engine.Render.drawEnd();
            return;
        }
    }

    private static void _setXyzuv(int i, float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7)
    {
        _xyzuv[i * 5 + 0] = f + f2 * f5 + f3 * f4;
        _xyzuv[i * 5 + 1] = (f1 + f3 * f5) - f2 * f4;
        _xyzuv[i * 5 + 2] = 0.0F;
        _xyzuv[i * 5 + 3] = f6;
        _xyzuv[i * 5 + 4] = f7;
    }

    public static void setColor(int i)
    {
        colorf.x = (float)(i & 0xff) / 255F;
        colorf.y = (float)(i >> 8 & 0xff) / 255F;
        colorf.z = (float)(i >> 16 & 0xff) / 255F;
        colorf.w = (float)(i >> 24 & 0xff) / 255F;
        color = i;
    }

    public static void setColor(int i, int j, int k)
    {
        colorf.x = (float)i / 255F;
        colorf.y = (float)j / 255F;
        colorf.z = (float)k / 255F;
        com.maddox.il2.engine.IconDraw.colorClamp();
    }

    public static void setColor(int i, int j, int k, int l)
    {
        colorf.x = (float)i / 255F;
        colorf.y = (float)j / 255F;
        colorf.z = (float)k / 255F;
        colorf.w = (float)l / 255F;
        com.maddox.il2.engine.IconDraw.colorClamp();
    }

    public static void setColor(float f, float f1, float f2)
    {
        colorf.x = f;
        colorf.y = f1;
        colorf.z = f2;
        com.maddox.il2.engine.IconDraw.colorClamp();
    }

    public static void setColor(float f, float f1, float f2, float f3)
    {
        colorf.x = f;
        colorf.y = f1;
        colorf.z = f2;
        colorf.w = f3;
        com.maddox.il2.engine.IconDraw.colorClamp();
    }

    public static void setColor(com.maddox.JGP.Color4f color4f)
    {
        colorf.set(color4f);
        com.maddox.il2.engine.IconDraw.colorClamp();
    }

    public static void getColor(com.maddox.JGP.Color4f color4f)
    {
        color4f.set(colorf);
    }

    protected static void colorClamp()
    {
        if(colorf.x < 0.0F)
            colorf.x = 0.0F;
        if(colorf.x > 1.0F)
            colorf.x = 1.0F;
        if(colorf.y < 0.0F)
            colorf.y = 0.0F;
        if(colorf.y > 1.0F)
            colorf.y = 1.0F;
        if(colorf.z < 0.0F)
            colorf.z = 0.0F;
        if(colorf.z > 1.0F)
            colorf.z = 1.0F;
        if(colorf.w < 0.0F)
            colorf.w = 0.0F;
        if(colorf.w > 1.0F)
            colorf.w = 1.0F;
        color = (int)((double)(255F * colorf.x) + 0.5D) | (int)((double)(255F * colorf.y) + 0.5D) << 8 | (int)((double)(255F * colorf.z) + 0.5D) << 16 | (int)((double)(255F * colorf.w) + 0.5D) << 24;
    }

    public static void setScrSize(int i, int j)
    {
        dx = i;
        dy = j;
        if(dx > dy)
            r = dx / 2;
        else
            r = dy / 2;
    }

    public static int scrSizeX()
    {
        return dx;
    }

    public static int scrSizeY()
    {
        return dy;
    }

    public static com.maddox.il2.engine.Mat create(com.maddox.il2.engine.Actor actor)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                return null;
            if(actor.icon != null)
                return actor.icon;
            java.lang.Class class1 = actor.getClass();
            com.maddox.il2.engine.Mat mat = (com.maddox.il2.engine.Mat)com.maddox.rts.Property.value(class1, "iconMat", null);
            boolean flag = mat == null;
            if(mat == null)
            {
                java.lang.String s = com.maddox.rts.Property.stringValue(class1, "iconName", null);
                if(s != null)
                    try
                    {
                        mat = com.maddox.il2.engine.Mat.New(s);
                    }
                    catch(java.lang.Exception exception) { }
            }
            if(mat != null)
            {
                if(flag)
                    com.maddox.rts.Property.set(class1, "iconMat", mat);
                boolean flag1 = actor.pos != null && actor.draw == null;
                actor.icon = mat;
                if(flag1 && actor.isDrawing())
                {
                    actor.drawing(false);
                    actor.drawing(true);
                }
            }
            return mat;
        } else
        {
            return null;
        }
    }

    public static com.maddox.il2.engine.Mat get(java.lang.String s)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.il2.engine.Mat mat = null;
            try
            {
                mat = com.maddox.il2.engine.Mat.New(s);
            }
            catch(java.lang.Exception exception) { }
            return mat;
        } else
        {
            return null;
        }
    }

    private IconDraw()
    {
    }

    private static com.maddox.JGP.Point3d _point = new Point3d();
    private static float _xyzuv[] = new float[20];
    private static int _xyzuvIndx[] = {
        0, 1, 2, 2, 3, 0
    };
    protected static int color = -1;
    protected static com.maddox.JGP.Color4f colorf = new Color4f(1.0F, 1.0F, 1.0F, 1.0F);
    protected static int dx;
    protected static int dy = 32;
    protected static float r;

    static 
    {
        dx = 32;
        r = dx / 2;
    }
}
