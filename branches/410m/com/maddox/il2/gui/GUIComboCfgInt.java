// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIComboCfgInt.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.il2.game.I18N;
import com.maddox.rts.CfgInt;

public class GUIComboCfgInt extends com.maddox.gwindow.GWindowComboControl
{

    public GUIComboCfgInt(com.maddox.gwindow.GWindow gwindow, com.maddox.rts.CfgInt cfgint, boolean flag)
    {
        this(gwindow, cfgint, flag, null);
    }

    public GUIComboCfgInt(com.maddox.gwindow.GWindow gwindow, com.maddox.rts.CfgInt cfgint, boolean flag, java.lang.String s)
    {
        super(gwindow, 0.0F, 0.0F, 1.0F);
        i18nPref = s;
        setEditable(false);
        bUpdate = flag;
        bChanged = false;
        cfg = cfgint;
        refresh(true);
        int i = cfgint.firstState();
        setSelected(cfgint.get() - i, true, false);
    }

    public void created()
    {
        super.created();
        metricWin = null;
    }

    public void update()
    {
        refresh(false);
    }

    public void refresh(boolean flag)
    {
        cfg.reset();
        int i = cfg.countStates();
        int j = cfg.firstState();
        if(posEnable == null)
            posEnable = new boolean[i];
        for(int k = 0; k < i; k++)
        {
            if(flag)
                if(i18nPref == null)
                {
                    add(cfg.nameState(k + j));
                } else
                {
                    java.lang.String s = cfg.nameState(k + j);
                    java.lang.String s1 = i18nPref + s;
                    if(com.maddox.il2.game.I18N.isGuiExist(s1))
                        add(com.maddox.il2.game.I18N.gui(s1));
                    else
                        add(s);
                }
            posEnable[k] = cfg.isEnabledState(k + j);
        }

        setEnable(cfg.isEnabled());
    }

    public void resolutionChanged()
    {
        super.resolutionChanged();
        refresh(false);
    }

    public boolean notify(int i, int j)
    {
        if(i == 2)
        {
            int k = cfg.get() - cfg.firstState();
            if(k != getSelected())
                if(bUpdate)
                {
                    cfg.set(getSelected() + cfg.firstState());
                    int l = cfg.apply();
                    cfg.reset();
                    cfg.applyExtends(l);
                    int i1 = cfg.get() - cfg.firstState();
                    if(i1 != getSelected())
                        setSelected(i1, true, false);
                } else
                {
                    bChanged = true;
                }
        }
        return super.notify(i, j);
    }

    public com.maddox.rts.CfgInt cfg;
    public boolean bUpdate;
    public boolean bChanged;
    public java.lang.String i18nPref;
}
