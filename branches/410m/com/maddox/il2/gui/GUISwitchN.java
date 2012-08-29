// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISwitchN.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.rts.CfgInt;

public class GUISwitchN extends com.maddox.gwindow.GWindowDialogControl
{

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        if(i != 0 || !flag || !bEnable)
            return;
        f -= win.dx / 2.0F;
        f1 -= win.dy / 2.0F;
        float f2 = (float)((java.lang.Math.atan2(f1, f) * 180D) / 3.1415926535897931D);
        if(f2 < 0.0F)
            f2 += 360F;
        float f3 = angleStep / 2.0F;
        for(int j = 0; j < pos.length; j++)
        {
            if(enable != null && !enable[j])
                continue;
            int k = pos[j];
            float f4 = angle0 + (float)k * angleStep;
            float f5 = f4 - f3;
            float f6 = f4 + f3;
            if(f6 > 360F)
            {
                f5 -= 360F;
                f6 -= 360F;
            }
            if(f2 >= f5 && f2 <= f6)
            {
                setState(j, true);
                lAF().soundPlay("clickSwitch");
                return;
            }
        }

    }

    public void setState(int i, boolean flag)
    {
        if(state == i)
            return;
        state = i;
        if(flag)
            notify(2, state);
    }

    public int getState()
    {
        return state;
    }

    public void render()
    {
        setCanvasColorWHITE();
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
        win.dx = x1024(texDx);
        win.dy = y1024(texDy);
        if(cfg != null)
            refresh();
    }

    public void created()
    {
        resolutionChanged();
    }

    public GUISwitchN(com.maddox.gwindow.GWindow gwindow, float f, float f1, int ai[], boolean aflag[])
    {
        super(gwindow);
        pos = ai;
        enable = aflag;
        angle0 = f;
        angleStep = f1;
        state = 0;
        states = ai.length;
    }

    public void update()
    {
        refresh();
    }

    public void refresh()
    {
        cfg.reset();
        int i = cfg.countStates();
        int j = cfg.firstState();
        if(enable == null)
            enable = new boolean[i];
        for(int k = 0; k < i; k++)
            enable[k] = cfg.isEnabledState(k + j);

        setEnable(cfg.isEnabled());
    }

    public boolean notify(int i, int j)
    {
        if(cfg == null)
            return super.notify(i, j);
        if(i == 2)
        {
            int k = cfg.get() - cfg.firstState();
            if(k != state)
                if(bUpdate)
                {
                    cfg.set(state + cfg.firstState());
                    int l = cfg.apply();
                    cfg.reset();
                    cfg.applyExtends(l);
                    int i1 = cfg.get() - cfg.firstState();
                    if(i1 != state)
                        setState(i1, false);
                } else
                {
                    bChanged = true;
                }
        }
        return super.notify(i, j);
    }

    public GUISwitchN(com.maddox.gwindow.GWindow gwindow, float f, float f1, int ai[], com.maddox.rts.CfgInt cfgint, boolean flag)
    {
        this(gwindow, f, f1, ai, null);
        cfg = cfgint;
        bUpdate = flag;
        refresh();
        int i = cfgint.firstState();
        setState(cfgint.get() - i, false);
        setEnable(cfgint.isEnabled());
    }

    public float texDx;
    public float texDy;
    public int states;
    public int state;
    public int pos[];
    public boolean enable[];
    public float angle0;
    public float angleStep;
    public com.maddox.rts.CfgInt cfg;
    public boolean bUpdate;
    public boolean bChanged;
}
