// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WindowPreferences.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.engine.CfgGObj;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.ScreenMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WindowPreferences
{
    static class CheckCfgFlag extends com.maddox.gwindow.GWindowCheckBox
    {

        public void resolutionChanged()
        {
            super.resolutionChanged();
            cfg.reset();
            setChecked(cfg.get(iFlag), false);
            setEnable(cfg.isEnabledFlag(iFlag));
        }

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                bChecked = !bChecked;
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
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        public com.maddox.rts.CfgFlags cfg;
        public int iFlag;
        public boolean bUpdate;
        public boolean bChanged;

        public CheckCfgFlag(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, float f, float f1, com.maddox.rts.CfgFlags cfgflags, int i, boolean flag)
        {
            super(gwindowdialogclient, f, f1, null);
            cfg = cfgflags;
            iFlag = i;
            bUpdate = flag;
            bChanged = false;
            setChecked(cfgflags.get(i), false);
            setEnable(cfgflags.isEnabledFlag(i));
            gwindowdialogclient.addControl(this);
        }
    }

    static class LabelFlag extends com.maddox.gwindow.GWindowLabel
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
                checkCfg.notify(2, 0);
            return super.notify(i, j);
        }

        com.maddox.il2.gui.CheckCfgFlag checkCfg;

        public LabelFlag(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, java.lang.String s, java.lang.String s1, com.maddox.il2.gui.CheckCfgFlag checkcfgflag)
        {
            super(gwindow, f, f1 - 0.1F, f2, 1.2F, s, s1);
            checkCfg = checkcfgflag;
            setEnable(checkcfgflag.isEnable());
        }
    }

    static class ComboCfgInt extends com.maddox.gwindow.GWindowComboControl
    {

        public void refresh(boolean flag)
        {
            int i = cfg.countStates();
            int j = cfg.firstState();
            if(posEnable == null)
                posEnable = new boolean[i];
            for(int k = 0; k < i; k++)
            {
                if(flag)
                    add(cfg.nameState(k + j));
                posEnable[k] = cfg.isEnabledState(k + j);
            }

            cfg.reset();
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
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        public com.maddox.rts.CfgInt cfg;
        public boolean bUpdate;
        public boolean bChanged;

        public ComboCfgInt(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, com.maddox.rts.CfgInt cfgint, boolean flag)
        {
            super(gwindow, f, f1, f2);
            setEditable(false);
            bUpdate = flag;
            bChanged = false;
            cfg = cfgint;
            refresh(true);
            int i = cfgint.firstState();
            setSelected(cfgint.get() - i, true, false);
            setEnable(cfgint.isEnabled());
        }
    }

    static class SliderCfgInt extends com.maddox.gwindow.GWindowHSliderInt
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = cfg.get() - cfg.firstState();
                if(k != pos())
                    if(bUpdate)
                    {
                        cfg.set(pos());
                        int l = cfg.apply();
                        cfg.reset();
                        cfg.applyExtends(l);
                        int i1 = cfg.get();
                        if(i1 != pos())
                            setPos(i1, false);
                    } else
                    {
                        bChanged = true;
                    }
                return true;
            } else
            {
                return super.notify(i, j);
            }
        }

        public com.maddox.rts.CfgInt cfg;
        public boolean bUpdate;
        public boolean bChanged;

        public SliderCfgInt(com.maddox.gwindow.GWindow gwindow, int i, int j, int k, float f, float f1, float f2, 
                com.maddox.rts.CfgInt cfgint, boolean flag)
        {
            super(gwindow, i, j, k, f, f1, f2);
            bUpdate = flag;
            bChanged = false;
            cfg = cfgint;
            int l = cfgint.countStates();
            int i1 = cfgint.firstState();
            posEnable = new boolean[l];
            for(int j1 = 0; j1 < l; j1++)
                posEnable[j1] = cfgint.isEnabledState(j1 + i1);

            cfgint.reset();
            setPos(cfgint.get(), false);
            setEnable(cfgint.isEnabled());
        }
    }


    public WindowPreferences()
    {
    }

    public static int _findVideoMode(com.maddox.rts.ScreenMode screenmode)
    {
        for(int i = 0; i < screenModes.size(); i++)
        {
            com.maddox.rts.ScreenMode screenmode1 = (com.maddox.rts.ScreenMode)screenModes.get(i);
            if(screenmode1.width() == screenmode.width() && screenmode1.height() == screenmode.height() && screenmode1.colourBits() == screenmode.colourBits())
                return i;
        }

        return 0;
    }

    public static com.maddox.gwindow.GWindowFramed create(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        framed = (com.maddox.gwindow.GWindowFramed)gwindowroot.create(1.666667F, 8.333333F, 33.33333F, 25F, true, new GWindowFramed());
        framed.title = "Preferences";
        com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient = (com.maddox.gwindow.GWindowTabDialogClient)framed.create(new GWindowTabDialogClient());
        framed.clientWindow = gwindowtabdialogclient;
        com.maddox.rts.ScreenMode ascreenmode[] = com.maddox.rts.ScreenMode.all();
        for(int i = 0; i < ascreenmode.length; i++)
        {
            com.maddox.rts.ScreenMode screenmode = ascreenmode[i];
            if(screenmode.colourBits() >= 15 && (float)screenmode.height() >= 240F)
                screenModes.add(screenmode);
        }

        if(screenModes.size() > 0)
        {
            com.maddox.gwindow.GWindowScrollingDialogClient gwindowscrollingdialogclient = (com.maddox.gwindow.GWindowScrollingDialogClient)gwindowtabdialogclient.create(new GWindowScrollingDialogClient());
            gwindowtabdialogclient.addTab("Video", gwindowscrollingdialogclient);
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)gwindowscrollingdialogclient.create(new com.maddox.gwindow.GWindowDialogClient() {

                public void created()
                {
                    super.created();
                    setMetricSize(24F, 14F);
                }

                public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
                {
                    gsize.dx = lookAndFeel().metric(24F);
                    gsize.dy = lookAndFeel().metric(14F);
                    return gsize;
                }

                public void render()
                {
                }

            }
);
            gwindowscrollingdialogclient.fixed = gwindowdialogclient;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 2.0F, 8F, gwindowdialogclient.lookAndFeel().getComboHmetric(), "Resolution", null));
            gwindowdialogclient.addControl(comboResolution = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 10F, 2.0F, 12F) {

                public boolean notify(int i2, int j2)
                {
                    if(i2 == 2)
                    {
                        new com.maddox.rts.MsgAction(64, 0.20000000000000001D, "") {

                            public void doAction(java.lang.Object obj2)
                            {
                                int k2 = getSelected();
                                com.maddox.rts.ScreenMode screenmode2 = (com.maddox.rts.ScreenMode)com.maddox.il2.gui.WindowPreferences.screenModes.get(k2);
                                com.maddox.rts.CmdEnv.top().exec("window " + screenmode2.width() + " " + screenmode2.height() + " " + screenmode2.colourBits() + " " + screenmode2.colourBits() + " FULL");
                                setSelected(com.maddox.il2.gui.WindowPreferences._findVideoMode(com.maddox.rts.ScreenMode.current()), true, false);
                            }

                        }
;
                        return true;
                    } else
                    {
                        return super.notify(i2, j2);
                    }
                }

            }
);
            comboResolution.setEditable(false);
            for(int j = 0; j < screenModes.size(); j++)
            {
                com.maddox.rts.ScreenMode screenmode1 = (com.maddox.rts.ScreenMode)screenModes.get(j);
                comboResolution.add(screenmode1.width() + "x" + screenmode1.height() + "x" + screenmode1.colourBits());
            }

            comboResolution.setSelected(com.maddox.il2.gui.WindowPreferences._findVideoMode(com.maddox.rts.ScreenMode.current()), true, false);
            comboResolution.resized();
        }
        com.maddox.gwindow.GWindowScrollingDialogClient gwindowscrollingdialogclient1 = (com.maddox.gwindow.GWindowScrollingDialogClient)gwindowtabdialogclient.create(new GWindowScrollingDialogClient());
        gwindowtabdialogclient.addTab("Render", gwindowscrollingdialogclient1);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient1 = (com.maddox.gwindow.GWindowDialogClient)gwindowscrollingdialogclient1.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void created()
            {
                super.created();
                setMetricSize(24F, 53F);
            }

            public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
            {
                gsize.dx = lookAndFeel().metric(24F);
                gsize.dy = lookAndFeel().metric(14F);
                return gsize;
            }

            public void render()
            {
            }

        }
);
        gwindowscrollingdialogclient1.fixed = gwindowdialogclient1;
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 2.0F, 2.0F, 8F, 10F, 8F, "TexQual", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 4F, 2.0F, 8F, 10F, 8F, "TexMipFilter", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 6F, 2.0F, 8F, 10F, 8F, "LandDetails", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 8F, 2.0F, 8F, 10F, 8F, "LandShading", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 10F, 2.0F, 8F, 10F, 8F, "Forest", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 12F, 2.0F, 8F, 10F, 8F, "Sky", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 15F, 2.0F, 8F, 10F, 8F, "DynamicalLights", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 17F, 2.0F, 8F, 10F, 8F, "DiffuseLight", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 19F, 2.0F, 8F, 10F, 8F, "SpecularLight", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 21F, 2.0F, 8F, 10F, 8F, "Specular", true);
        com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient1, 23F, 2.0F, 8F, 10F, 8F, "Shadows", true);
        com.maddox.il2.gui.WindowPreferences.createCfgFlag(gwindowdialogclient1, 25F, 10F, 2.0F, 8F, "ShadowsFlags", 0, true);
        com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("TexFlags");
        int k = cfgflags.firstFlag();
        int i1 = cfgflags.countFlags();
        for(int k1 = 0; k1 < i1; k1++)
            com.maddox.il2.gui.WindowPreferences.createCfgFlag(gwindowdialogclient1, 28F + (float)k1 * 1.5F, 14F, 2.0F, 12F, "TexFlags", k1 + k, true);

        com.maddox.gwindow.GWindowScrollingDialogClient gwindowscrollingdialogclient2 = (com.maddox.gwindow.GWindowScrollingDialogClient)gwindowtabdialogclient.create(new GWindowScrollingDialogClient());
        gwindowtabdialogclient.addTab("Sound", gwindowscrollingdialogclient2);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient2 = (com.maddox.gwindow.GWindowDialogClient)gwindowscrollingdialogclient2.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void created()
            {
                super.created();
                setMetricSize(24F, 28F);
            }

            public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
            {
                gsize.dx = lookAndFeel().metric(24F);
                gsize.dy = lookAndFeel().metric(14F);
                return gsize;
            }

            public void render()
            {
            }

        }
);
        gwindowscrollingdialogclient2.fixed = gwindowdialogclient2;
        float f = 2.0F;
        java.util.Collection collection = com.maddox.rts.CfgTools.all();
        java.util.Iterator iterator = collection.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.lang.Object obj = iterator.next();
            if((obj instanceof com.maddox.rts.CfgInt) && !(obj instanceof com.maddox.il2.engine.CfgGObj))
            {
                com.maddox.rts.CfgInt cfgint = (com.maddox.rts.CfgInt)obj;
                if(cfgint.countStates() > 8)
                    com.maddox.il2.gui.WindowPreferences.createSlider(gwindowdialogclient2, f, 2.0F, 8F, 10F, 8F, cfgint.name(), true);
                else
                    com.maddox.il2.gui.WindowPreferences.createCfgInt(gwindowdialogclient2, f, 2.0F, 8F, 10F, 8F, cfgint.name(), true);
                f += 2.0F;
            }
        } while(true);
        f += 3F;
        collection = com.maddox.rts.CfgTools.all();
        iterator = collection.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.lang.Object obj1 = iterator.next();
            if((obj1 instanceof com.maddox.rts.CfgFlags) && !(obj1 instanceof com.maddox.il2.engine.CfgGObj))
            {
                com.maddox.rts.CfgFlags cfgflags1 = (com.maddox.rts.CfgFlags)obj1;
                int l = cfgflags1.firstFlag();
                int j1 = cfgflags1.countFlags();
                int l1 = 0;
                while(l1 < j1) 
                {
                    com.maddox.il2.gui.WindowPreferences.createCfgFlag(gwindowdialogclient2, f, 14F, 2.0F, 12F, cfgflags1.name(), l1 + l, true);
                    f += 1.5F;
                    l1++;
                }
            }
        } while(true);
        framed.resized();
        framed.close(false);
        return framed;
    }

    private static com.maddox.il2.gui.SliderCfgInt createSlider(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, float f, float f1, float f2, float f3, float f4, java.lang.String s, boolean flag)
    {
        java.lang.String s1 = s;
        java.lang.String s2 = s + " toolTip ???";
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f1, f, f2, gwindowdialogclient.lookAndFeel().getHSliderIntHmetric(), s1, s2));
        com.maddox.rts.CfgInt cfgint = (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get(s);
        com.maddox.il2.gui.SliderCfgInt slidercfgint = new SliderCfgInt(gwindowdialogclient, cfgint.firstState(), cfgint.countStates(), 0, f3, f, f4, cfgint, flag);
        slidercfgint.setToolTip(s2);
        gwindowdialogclient.addControl(slidercfgint);
        return slidercfgint;
    }

    private static com.maddox.il2.gui.ComboCfgInt createCfgInt(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, float f, float f1, float f2, float f3, float f4, java.lang.String s, boolean flag)
    {
        java.lang.String s1 = s;
        java.lang.String s2 = s + " toolTip ???";
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f1, f, f2, gwindowdialogclient.lookAndFeel().getComboHmetric(), s1, s2));
        com.maddox.rts.CfgInt cfgint = (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get(s);
        com.maddox.il2.gui.ComboCfgInt combocfgint = new ComboCfgInt(gwindowdialogclient, f3, f, f4, cfgint, flag);
        combocfgint.setToolTip(s2);
        gwindowdialogclient.addControl(combocfgint);
        return combocfgint;
    }

    private static com.maddox.il2.gui.CheckCfgFlag createCfgFlag(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, float f, float f1, float f2, float f3, java.lang.String s, int i, boolean flag)
    {
        com.maddox.rts.CfgFlags cfgflags = (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get(s);
        java.lang.String s1 = cfgflags.nameFlag(i);
        java.lang.String s2 = s1 + " toolTip ???";
        com.maddox.il2.gui.CheckCfgFlag checkcfgflag = new CheckCfgFlag(gwindowdialogclient, f1, f, cfgflags, i, flag);
        checkcfgflag.setToolTip(s2);
        gwindowdialogclient.addControl(new LabelFlag(gwindowdialogclient, f2, f, f3, s1, s2, checkcfgflag));
        return checkcfgflag;
    }

    public static com.maddox.gwindow.GWindowFramed framed;
    public static com.maddox.gwindow.GWindowComboControl comboResolution;
    public static java.util.ArrayList screenModes = new ArrayList();

}
