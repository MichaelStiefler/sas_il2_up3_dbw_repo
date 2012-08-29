// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Land2D.java

package com.maddox.il2.engine;

import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Destroy;

// Referenced classes of package com.maddox.il2.engine:
//            CameraOrtho2D, Render, RenderContext, Mat

public class Land2D
    implements com.maddox.rts.Destroy, com.maddox.opengl.MsgGLContextListener
{

    public java.lang.String tgaName()
    {
        return tgaName;
    }

    public double worldSizeX()
    {
        return worldSizeX;
    }

    public double worldSizeY()
    {
        return worldSizeY;
    }

    public double mapSizeX()
    {
        return mapSizeX;
    }

    public double mapSizeY()
    {
        return mapSizeY;
    }

    public double worldOfsX()
    {
        return worldOfsX;
    }

    public double worldOfsY()
    {
        return worldOfsY;
    }

    protected int pixelsX()
    {
        return nx * (64 - 2 * BORDER);
    }

    public boolean isShow()
    {
        return bShow;
    }

    public void show(boolean flag)
    {
        bShow = flag;
    }

    public void render()
    {
        render(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(float f, float f1, float f2, float f3)
    {
        if(!bShow || glId == null || !(com.maddox.il2.engine.Render.currentCamera() instanceof com.maddox.il2.engine.CameraOrtho2D))
            return;
        if(tileMat != null)
            renderTile(f, f1, f2, f3);
        renderWorld(f, f1, f2, f3);
    }

    private void renderTile(float f, float f1, float f2, float f3)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.0F)
            f = 1.0F;
        if(f1 < 0.0F)
            f1 = 0.0F;
        if(f1 > 1.0F)
            f1 = 1.0F;
        if(f2 < 0.0F)
            f2 = 0.0F;
        if(f2 > 1.0F)
            f2 = 1.0F;
        if(f3 < 0.0F)
            f3 = 0.0F;
        if(f3 > 1.0F)
            f3 = 1.0F;
        int i = (int)((double)(255F * f) + 0.5D) | (int)((double)(255F * f1) + 0.5D) << 8 | (int)((double)(255F * f2) + 0.5D) << 16 | (int)((double)(255F * f3) + 0.5D) << 24;
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        com.maddox.il2.engine.Render.drawTile(cameraortho2d.left, cameraortho2d.bottom, cameraortho2d.right - cameraortho2d.left, cameraortho2d.top - cameraortho2d.bottom, 0.0F, tileMat, i, 0.0F, 1.0F, 1.0F, -1F);
    }

    private void renderWorld(float f, float f1, float f2, float f3)
    {
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        com.maddox.il2.engine.Render.clearStates();
        com.maddox.opengl.gl.Color4f(f, f1, f2, f3);
        com.maddox.opengl.gl.DepthMask(false);
        com.maddox.opengl.gl.Enable(3553);
        com.maddox.opengl.gl.Disable(3008);
        com.maddox.opengl.gl.Disable(3042);
        com.maddox.opengl.gl.Disable(2929);
        com.maddox.opengl.gl.TexEnvi(8960, 8704, 8448);
        float f4 = (float)(worldDx * cameraortho2d.worldScale);
        float f5 = (float)(worldDy * cameraortho2d.worldScale);
        for(int i = ny - 1; i >= 0; i--)
        {
            float f6 = (float)(((double)i * worldDy - worldFillY - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
            if(f6 > cameraortho2d.top || f6 + f5 < cameraortho2d.bottom)
                continue;
            for(int j = 0; j < nx; j++)
            {
                float f7 = (float)(((double)j * worldDx - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
                if(f7 <= cameraortho2d.right && f7 + f4 >= cameraortho2d.left)
                {
                    com.maddox.opengl.gl.BindTexture(3553, glId[(ny - i - 1) * nx + j]);
                    com.maddox.opengl.gl.Begin(7);
                    com.maddox.opengl.gl.TexCoord2f(ZERO, ONE);
                    com.maddox.opengl.gl.Vertex3f(f7, f6, 0.0F);
                    com.maddox.opengl.gl.TexCoord2f(ONE, ONE);
                    com.maddox.opengl.gl.Vertex3f(f7 + f4, f6, 0.0F);
                    com.maddox.opengl.gl.TexCoord2f(ONE, ZERO);
                    com.maddox.opengl.gl.Vertex3f(f7 + f4, f6 + f5, 0.0F);
                    com.maddox.opengl.gl.TexCoord2f(ZERO, ZERO);
                    com.maddox.opengl.gl.Vertex3f(f7, f6 + f5, 0.0F);
                    com.maddox.opengl.gl.End();
                }
            }

        }

        com.maddox.il2.engine.Render.prepareStates();
    }

    public boolean load(java.lang.String s, double d, double d1)
    {
        tgaName = s;
        worldSizeX = d;
        worldSizeY = d1;
        mapSizeX = d;
        mapSizeY = d1;
        worldOfsX = 0.0D;
        worldOfsY = 0.0D;
        return reload();
    }

    public boolean reload()
    {
        freeGlIds();
        boolean flag = load();
        if(flag)
        {
            int i = 64 - 2 * BORDER;
            worldDx = (worldSizeX / (double)(nx * i - fillX)) * (double)i;
            worldDy = (worldSizeY / (double)(ny * i - fillY)) * (double)i;
            worldFillY = (worldSizeY / (double)(ny * i - fillY)) * (double)fillY;
            com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        } else
        {
            worldDx = worldDy = 1.0D;
            worldFillY = 0.0D;
            com.maddox.opengl.GLContext.getCurrent().msgRemoveListener(this, null);
        }
        return flag;
    }

    private boolean load()
    {
        int i = com.maddox.il2.engine.RenderContext.cfgTxrQual.get();
        i -= com.maddox.il2.engine.RenderContext.cfgTxrQual.countStates() - com.maddox.il2.engine.RenderContext.cfgTxrQual.firstState() - 1;
        int j;
        for(j = 1; i-- > 0; j *= 2);
        BORDER = 2 * j;
        if(BORDER * 2 >= 64)
            BORDER = 16;
        ZERO = (float)BORDER / 64F;
        ONE = 1.0F - ZERO;
        int ai[] = new int[2];
        glId = com.maddox.il2.engine.Mat.loadTextureAsArrayFromTgaB(tgaName, com.maddox.il2.engine.Mat.MINLINEAR | com.maddox.il2.engine.Mat.MAGLINEAR | com.maddox.il2.engine.Mat.WRAPUV, 64, 64, BORDER, ai);
        if(glId != null)
        {
            int k = 64 - 2 * BORDER;
            nx = ((ai[0] + k) - 1) / k;
            ny = ((ai[1] + k) - 1) / k;
            fillX = ai[0] % k;
            fillY = ai[1] % k;
            if(fillX != 0)
                fillX = k - fillX;
            if(fillY != 0)
                fillY = k - fillY;
            return true;
        } else
        {
            return false;
        }
    }

    private void freeGlIds()
    {
        if(glId == null)
        {
            return;
        } else
        {
            com.maddox.opengl.gl.DeleteTextures(nx * ny, glId);
            glId = null;
            return;
        }
    }

    public void msgGLContext(int i)
    {
        if(i == 2)
            glId = null;
        else
        if(i == 8)
        {
            glId = null;
            load();
        }
    }

    public void setBigMap(java.lang.String s, double d, double d1, double d2, 
            double d3)
    {
        mapSizeX = d;
        mapSizeY = d1;
        worldOfsX = d2;
        worldOfsY = d3;
        tileMat = null;
        if(s != null)
            try
            {
                tileMat = com.maddox.il2.engine.Mat.New(s);
            }
            catch(java.lang.Exception exception) { }
    }

    public Land2D()
    {
        BORDER = 2;
        ZERO = (float)BORDER / 64F;
        ONE = 1.0F - ZERO;
        tileMat = null;
        glId = null;
        bShow = true;
    }

    public Land2D(java.lang.String s, double d, double d1)
    {
        BORDER = 2;
        ZERO = (float)BORDER / 64F;
        ONE = 1.0F - ZERO;
        tileMat = null;
        glId = null;
        bShow = true;
        load(s, d, d1);
    }

    public void destroy()
    {
        if(com.maddox.opengl.GLContext.isValid(com.maddox.opengl.GLContext.getCurrent()))
            freeGlIds();
        else
            glId = null;
        if(com.maddox.opengl.GLContext.getCurrent() != null)
            com.maddox.opengl.GLContext.getCurrent().msgRemoveListener(this, null);
    }

    public boolean isDestroyed()
    {
        return glId == null;
    }

    public static final int TILE = 64;
    public static final int BORDER0 = 2;
    private int BORDER;
    private float ZERO;
    private float ONE;
    private java.lang.String tgaName;
    private com.maddox.il2.engine.Mat tileMat;
    private double worldSizeX;
    private double worldSizeY;
    private double mapSizeX;
    private double mapSizeY;
    private double worldOfsX;
    private double worldOfsY;
    private double worldDx;
    private double worldDy;
    private double worldFillY;
    private int nx;
    private int ny;
    private int fillX;
    private int fillY;
    private int glId[];
    private boolean bShow;
}
