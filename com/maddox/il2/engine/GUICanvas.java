// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUICanvas.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.rts.StringClipboard;

// Referenced classes of package com.maddox.il2.engine:
//            GUITexture, GUIMesh, CameraOrtho2D, GUIFont, 
//            Loc, Mat, Render, Mesh, 
//            Renders, TTFont

public class GUICanvas extends com.maddox.gwindow.GCanvas
{

    public boolean preRender(com.maddox.gwindow.GTexture gtexture, float f, float f1)
    {
        com.maddox.il2.engine.GUITexture guitexture = (com.maddox.il2.engine.GUITexture)gtexture;
        if(guitexture.mat == null)
            return false;
        if(f <= 0.0F || f1 <= 0.0F)
            return false;
        if(alpha == 0)
        {
            return false;
        } else
        {
            guitexture.mat.preRender();
            return true;
        }
    }

    public boolean draw(com.maddox.gwindow.GTexture gtexture, float f, float f1, float f2, float f3, float f4, float f5)
    {
        if(gtexture == null)
            return false;
        com.maddox.il2.engine.GUITexture guitexture = (com.maddox.il2.engine.GUITexture)gtexture;
        if(guitexture.mat == null)
            return false;
        if(f <= 0.0F || f1 <= 0.0F)
            return false;
        if(f4 == 0.0F || f5 == 0.0F)
            return false;
        if(alpha == 0)
            return false;
        float f6 = f4 / f;
        float f7 = f5 / f1;
        float f8 = cur.x;
        float f9 = cur.y;
        float f10 = f8 - clip.x;
        if(f10 < 0.0F)
        {
            f += f10;
            if(f <= 0.0F)
                return false;
            f8 = clip.x;
            float f11 = f10 * f6;
            f4 += f11;
            f2 -= f11;
            f10 = 0.0F;
        }
        f10 = (f + f10) - clip.dx;
        if(f10 > 0.0F)
        {
            f -= f10;
            if(f <= 0.0F)
                return false;
            f4 -= f10 * f6;
        }
        f10 = f9 - clip.y;
        if(f10 < 0.0F)
        {
            f1 += f10;
            if(f1 <= 0.0F)
                return false;
            f9 = clip.y;
            float f12 = f10 * f7;
            f5 += f12;
            f3 -= f12;
            f10 = 0.0F;
        }
        f10 = (f1 + f10) - clip.dy;
        if(f10 > 0.0F)
        {
            f1 -= f10;
            if(f1 <= 0.0F)
                return false;
            float f13 = f10 * f7;
            f5 -= f13;
        }
        f9 = size.dy - f9;
        f8 = java.lang.Math.round(f8);
        f9 = java.lang.Math.round(f9);
        f = java.lang.Math.round(f);
        f1 = java.lang.Math.round(f1);
        com.maddox.il2.engine.Render.DrawTile(f8, f9, f, -f1, 0.0F, guitexture.mat.cppObject(), color.color | (alpha & 0xff) << 24, f2 * guitexture.scalex, f3 * guitexture.scaley, f4 * guitexture.scalex, f5 * guitexture.scaley);
        return true;
    }

    public boolean preRender(com.maddox.gwindow.GMesh gmesh, float f, float f1)
    {
        com.maddox.il2.engine.GUIMesh guimesh = (com.maddox.il2.engine.GUIMesh)gmesh;
        if(guimesh.mesh == null)
            return false;
        if(f <= 0.0F || f1 <= 0.0F)
            return false;
        if(alpha == 0)
        {
            return false;
        } else
        {
            setMeshPos(guimesh, cur.x, cur.y, f, f1);
            guimesh.mesh.preRender();
            return true;
        }
    }

    private void setMeshPos(com.maddox.il2.engine.GUIMesh guimesh, float f, float f1, float f2, float f3)
    {
        float f4 = f2 / guimesh.size.dx;
        float f5 = f3 / guimesh.size.dy;
        float f6 = 1.0F;
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)render.getCamera();
        if(guimesh.boundBox[2] < cameraortho2d.ZNear || guimesh.boundBox[5] > cameraortho2d.ZFar)
        {
            float f7 = cameraortho2d.ZNear - guimesh.boundBox[2];
            if(f7 < guimesh.boundBox[5] - cameraortho2d.ZFar)
                f7 = guimesh.boundBox[5] - cameraortho2d.ZFar;
            f6 = cameraortho2d.ZFar / (f7 + cameraortho2d.ZFar);
        }
        guimesh.mesh.setScaleXYZ(f4, f5, f6);
        com.maddox.JGP.Point3d point3d = _meshLoc.getPoint();
        point3d.x = f - guimesh.boundBox[0] * f4;
        point3d.y = size.dy - (f1 + guimesh.boundBox[4] * f5);
        guimesh.mesh.setPos(_meshLoc);
    }

    public boolean draw(com.maddox.gwindow.GMesh gmesh, float f, float f1)
    {
        if(gmesh == null)
            return false;
        com.maddox.il2.engine.GUIMesh guimesh = (com.maddox.il2.engine.GUIMesh)gmesh;
        if(guimesh.mesh == null)
            return false;
        if(f <= 0.0F || f1 <= 0.0F)
            return false;
        if(alpha == 0)
            return false;
        float f2 = cur.x;
        float f3 = cur.y;
        float f4 = f;
        float f5 = f1;
        float f6 = f2 - clip.x;
        if(f6 < 0.0F)
        {
            f4 += f6;
            if(f4 <= 0.0F)
                return false;
            f2 = clip.x;
            f6 = 0.0F;
        }
        f6 = (f4 + f6) - clip.dx;
        if(f6 > 0.0F)
        {
            f4 -= f6;
            if(f4 <= 0.0F)
                return false;
        }
        f6 = f3 - clip.y;
        if(f6 < 0.0F)
        {
            f5 += f6;
            if(f5 <= 0.0F)
                return false;
            f3 = clip.y;
            f6 = 0.0F;
        }
        f6 = (f5 + f6) - clip.dy;
        if(f6 > 0.0F)
        {
            f5 -= f6;
            if(f5 <= 0.0F)
                return false;
        }
        fill_z(f2, f3, f4, f5, true);
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)render.getCamera();
        render.getViewPort(_viewPort);
        cameraortho2d.activate(1.0F, render.renders().width(), render.renders().height(), _viewPort[0], _viewPort[1], _viewPort[2], _viewPort[3], _viewPort[0] + java.lang.Math.round(f2), (_viewPort[1] + _viewPort[3]) - java.lang.Math.round(f3) - java.lang.Math.round(f5), java.lang.Math.round(f4), java.lang.Math.round(f5));
        setMeshPos(guimesh, cur.x, cur.y, f, f1);
        guimesh.mesh.render();
        cameraortho2d.activate(1.0F, render.renders().width(), render.renders().height(), _viewPort[0], _viewPort[1], _viewPort[2], _viewPort[3]);
        return true;
    }

    public void fill_z(float f, float f1, float f2, float f3, boolean flag)
    {
        if(!bClearZ)
        {
            return;
        } else
        {
            com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)render.getCamera();
            float f4 = -(flag ? cameraortho2d.ZFar - 0.01F : cameraortho2d.ZNear + 0.01F);
            com.maddox.il2.engine.Render.DrawTile(f, size.dy - f1, f2, -f3, f4, clear_set_z.mat.cppObject(), -1, 0.0F, 0.0F, 1.0F, 1.0F);
            return;
        }
    }

    public boolean draw(java.lang.String s)
    {
        if(s == null || s.length() == 0)
            return false;
        else
            return draw(s, 0, s.length());
    }

    public boolean draw(java.lang.String s, int i, int j)
    {
        if(s == null || s.length() == 0)
            return false;
        if(j <= 0)
            return false;
        if(alpha == 0)
            return false;
        com.maddox.il2.engine.GUIFont guifont = (com.maddox.il2.engine.GUIFont)font;
        if(guifont == null || guifont.fnt == null)
            return false;
        float f = guifont.height;
        float f1 = cur.y;
        float f3 = f1 - clip.y;
        if(f3 < 0.0F)
        {
            f += f3;
            if(f <= 0.0F)
                return false;
            float f2 = clip.y;
            f3 = 0.0F;
        }
        f3 = (f + f3) - clip.dy;
        if(f3 > 0.0F)
        {
            f -= f3;
            if(f <= 0.0F)
                return false;
        }
        guifont.fnt.outputClip(color.color | (alpha & 0xff) << 24, java.lang.Math.round(cur.x), java.lang.Math.round(size.dy - cur.y - guifont.height - guifont.descender), 0.0F, s, i, j, java.lang.Math.round(clip.x), java.lang.Math.round(size.dy - clip.y - clip.dy), java.lang.Math.round(clip.dx), java.lang.Math.round(clip.dy));
        return true;
    }

    public boolean draw(char ac[], int i, int j)
    {
        if(ac == null)
            return false;
        if(j <= 0)
            return false;
        if(alpha == 0)
            return false;
        com.maddox.il2.engine.GUIFont guifont = (com.maddox.il2.engine.GUIFont)font;
        if(guifont == null || guifont.fnt == null)
            return false;
        float f = guifont.height;
        float f1 = cur.y;
        float f3 = f1 - clip.y;
        if(f3 < 0.0F)
        {
            f += f3;
            if(f <= 0.0F)
                return false;
            float f2 = clip.y;
            f3 = 0.0F;
        }
        f3 = (f + f3) - clip.dy;
        if(f3 > 0.0F)
        {
            f -= f3;
            if(f <= 0.0F)
                return false;
        }
        guifont.fnt.outputClip(color.color | (alpha & 0xff) << 24, java.lang.Math.round(cur.x), java.lang.Math.round(size.dy - cur.y - guifont.height - guifont.descender), 0.0F, ac, i, j, java.lang.Math.round(clip.x), java.lang.Math.round(size.dy - clip.y - clip.dy), java.lang.Math.round(clip.dx), java.lang.Math.round(clip.dy));
        return true;
    }

    public void copyToClipboard(java.lang.String s)
    {
        com.maddox.rts.StringClipboard.copy(s);
    }

    public java.lang.String pasteFromClipboard()
    {
        return com.maddox.rts.StringClipboard.paste();
    }

    public GUICanvas(com.maddox.il2.engine.Render render1)
    {
        super(new GSize(render1.getViewPortWidth(), render1.getViewPortHeight()));
        bClearZ = true;
        render = render1;
        clear_set_z = (com.maddox.il2.engine.GUITexture)com.maddox.gwindow.GTexture.New("gui/clear_set_z.mat");
    }

    public com.maddox.il2.engine.Render render;
    public com.maddox.il2.engine.GUITexture clear_set_z;
    public boolean bClearZ;
    private static com.maddox.il2.engine.Loc _meshLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    private static int _viewPort[] = new int[4];

}
