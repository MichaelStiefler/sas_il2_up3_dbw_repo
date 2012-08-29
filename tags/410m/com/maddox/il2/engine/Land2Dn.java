// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Land2Dn.java

package com.maddox.il2.engine;

import com.maddox.rts.CfgInt;
import com.maddox.rts.HomePath;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.engine:
//            Land2D, CameraOrtho2D, Render, RenderContext

public class Land2Dn extends com.maddox.il2.engine.Land2D
{

    public java.lang.String tgaName()
    {
        if(land2D != null)
            return land2D[0].tgaName();
        else
            return null;
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

    private int selectRender()
    {
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        double d = cameraortho2d.worldScale;
        int i = 0;
        double d1 = scale[0] - d;
        if(d1 < 0.0D)
            d1 = -d1;
        for(int j = 1; j < land2D.length; j++)
        {
            double d2 = scale[j] - d;
            if(d2 < 0.0D)
                d2 = -d2;
            if(d2 < d1)
            {
                d1 = d2;
                i = j;
            }
        }

        return i;
    }

    public void render()
    {
        if(!isShow() || land2D == null || !(com.maddox.il2.engine.Render.currentCamera() instanceof com.maddox.il2.engine.CameraOrtho2D))
        {
            return;
        } else
        {
            land2D[selectRender()].render();
            return;
        }
    }

    public void render(float f, float f1, float f2, float f3)
    {
        if(!isShow() || land2D == null || !(com.maddox.il2.engine.Render.currentCamera() instanceof com.maddox.il2.engine.CameraOrtho2D))
        {
            return;
        } else
        {
            land2D[selectRender()].render(f, f1, f2, f3);
            return;
        }
    }

    public boolean load(java.lang.String as[], double d, double d1)
    {
        destroy();
        land2D = new com.maddox.il2.engine.Land2D[as.length];
        scale = new double[land2D.length];
        for(int i = 0; i < land2D.length; i++)
        {
            com.maddox.il2.engine.Land2D land2d = new Land2D();
            land2D[i] = land2d;
            if(!land2d.load(as[i], d, d1))
            {
                destroy();
                return false;
            }
        }

        worldSizeX = d;
        worldSizeY = d1;
        computeScale();
        return true;
    }

    public boolean reload()
    {
        if(land2D == null)
            return false;
        for(int i = 0; i < land2D.length; i++)
            if(!land2D[i].reload())
            {
                destroy();
                return false;
            }

        computeScale();
        return true;
    }

    private void computeScale()
    {
        int i = com.maddox.il2.engine.RenderContext.cfgTxrQual.get();
        i -= com.maddox.il2.engine.RenderContext.cfgTxrQual.countStates() - com.maddox.il2.engine.RenderContext.cfgTxrQual.firstState() - 1;
        double d;
        for(d = 1.0D; i-- > 0; d *= 2D);
        for(int j = 0; j < land2D.length; j++)
            scale[j] = (double)land2D[j].pixelsX() / worldSizeX / d;

    }

    public boolean load(java.lang.String s, double d, double d1)
    {
        return load(new java.lang.String[] {
            s
        }, d, d1);
    }

    public void msgGLContext(int i)
    {
    }

    public Land2Dn()
    {
        worldOfsX = 0.0D;
        worldOfsY = 0.0D;
    }

    public Land2Dn(java.lang.String as[], double d, double d1)
    {
        worldOfsX = 0.0D;
        worldOfsY = 0.0D;
        load(as, d, d1);
    }

    public Land2Dn(java.lang.String s, double d, double d1)
    {
        worldOfsX = 0.0D;
        worldOfsY = 0.0D;
        com.maddox.rts.SectFile sectfile = new SectFile("maps/" + s);
        java.lang.String as[] = null;
        int i = sectfile.sectionIndex("MAP2D");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            if(j > 0)
            {
                as = new java.lang.String[j];
                for(int l = 0; l < j; l++)
                    as[l] = com.maddox.rts.HomePath.concatNames("maps/" + s, sectfile.var(i, l));

            }
        }
        if(as != null)
        {
            load(as, d, d1);
            int k = sectfile.sectionIndex("MAP2D_BIG");
            java.lang.String s1 = null;
            if(k >= 0)
            {
                s1 = sectfile.get("MAP2D_BIG", "tile", (java.lang.String)null);
                mapSizeX = sectfile.get("MAP2D_BIG", "sizeX", (float)d);
                mapSizeY = sectfile.get("MAP2D_BIG", "sizeY", (float)d1);
                worldOfsX = sectfile.get("MAP2D_BIG", "ofsX", 0.0F);
                worldOfsY = sectfile.get("MAP2D_BIG", "ofsY", 0.0F);
            }
            if(s1 != null)
            {
                s1 = com.maddox.rts.HomePath.concatNames("maps/" + s, s1);
            } else
            {
                mapSizeX = d;
                mapSizeY = d1;
            }
            for(int i1 = 0; i1 < land2D.length; i1++)
                land2D[i1].setBigMap(s1, mapSizeX, mapSizeY, worldOfsX, worldOfsY);

        }
    }

    public void destroy()
    {
        if(land2D != null)
        {
            for(int i = 0; i < land2D.length; i++)
            {
                com.maddox.il2.engine.Land2D land2d = land2D[i];
                if(land2d != null)
                    land2d.destroy();
            }

            land2D = null;
        }
    }

    public boolean isDestroyed()
    {
        return land2D == null;
    }

    private com.maddox.il2.engine.Land2D land2D[];
    private double scale[];
    private double worldSizeX;
    private double worldSizeY;
    private double mapSizeX;
    private double mapSizeY;
    private double worldOfsX;
    private double worldOfsY;
}
