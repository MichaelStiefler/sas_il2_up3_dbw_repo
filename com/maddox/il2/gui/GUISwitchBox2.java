// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitchBox2.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.il2.engine.Config;
import com.maddox.rts.CfgFlags;

public class GUISwitchBox2 extends com.maddox.gwindow.GWindowCheckBox
{

    private static void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(texDown != null)
                return;
            com.maddox.gwindow.GTexture gtexture = com.maddox.gwindow.GTexture.New("GUI/game/buttons2.mat");
            texDown = new GTexRegion(gtexture, 96F, 0.0F, 64F, 80F);
            texUp = new GTexRegion(gtexture, 96F, 160F, 64F, 80F);
            texDDown = new GTexRegion(gtexture, 168F, 0.0F, 64F, 80F);
            texDUp = new GTexRegion(gtexture, 168F, 160F, 64F, 80F);
        }
    }

    public void render()
    {
        setCanvasColorWHITE();
        if(isEnable())
        {
            if(isChecked())
                draw(0.0F, 0.0F, win.dx, win.dy, texUp);
            else
                draw(0.0F, 0.0F, win.dx, win.dy, texDown);
        } else
        if(isChecked())
            draw(0.0F, 0.0F, win.dx, win.dy, texDUp);
        else
            draw(0.0F, 0.0F, win.dx, win.dy, texDDown);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        gsize.dx = win.dx;
        gsize.dy = win.dy;
        return gsize;
    }

    public void setPosC(float f, float f1)
    {
        super.setPos(f - win.dx / 2.0F, f1 - win.dy / 2.0F);
    }

    public void resolutionChanged()
    {
        win.dx = x1024(64F);
        win.dy = y1024(80F);
    }

    public void created()
    {
        metricWin = null;
        resolutionChanged();
    }

    public void update()
    {
        refresh();
    }

    public void refresh()
    {
        if(cfg == null)
        {
            return;
        } else
        {
            cfg.reset();
            setChecked(cfg.get(iFlag), false);
            setEnable(cfg.isEnabledFlag(iFlag));
            return;
        }
    }

    public boolean _notify(int i, int j)
    {
        if(i == 2)
        {
            bChecked = !bChecked;
            if(cfg != null)
                if(bUpdate)
                {
                    cfg.set(iFlag, isChecked());
                    int k = cfg.apply(iFlag);
                    cfg.reset();
                    cfg.applyExtends(k);
                    boolean flag = cfg.get(iFlag);
                    if(flag != isChecked())
                        setChecked(flag, false);
                } else
                {
                    bChanged = true;
                }
        }
        return super.notify(i, j);
    }

    public GUISwitchBox2(com.maddox.gwindow.GWindow gwindow)
    {
        super(gwindow, 0.0F, 0.0F, (java.lang.String)null);
        com.maddox.il2.gui.GUISwitchBox2.init();
    }

    public GUISwitchBox2(com.maddox.gwindow.GWindow gwindow, com.maddox.rts.CfgFlags cfgflags, int i, boolean flag)
    {
        this(gwindow);
        com.maddox.il2.gui.GUISwitchBox2.init();
        cfg = cfgflags;
        iFlag = i;
        bUpdate = flag;
        bChanged = false;
        if(cfgflags != null)
        {
            setChecked(cfgflags.get(i), false);
            setEnable(cfgflags.isEnabledFlag(i));
        }
    }

    public com.maddox.rts.CfgFlags cfg;
    public int iFlag;
    public boolean bUpdate;
    public boolean bChanged;
    public static com.maddox.gwindow.GTexRegion texUp;
    public static com.maddox.gwindow.GTexRegion texDown;
    public static com.maddox.gwindow.GTexRegion texDUp;
    public static com.maddox.gwindow.GTexRegion texDDown;
}
