// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMission.java

package com.maddox.il2.builder;

import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFileBox;
import com.maddox.gwindow.GWindowFileBoxExec;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSeparate;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.gwindow.GWindowStatusBar;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Path, PathAir, PlMapLoad, 
//            Builder, Pathes, PathFind, BldConfig, 
//            Zuti_WManageAircrafts

public class PlMission extends com.maddox.il2.builder.Plugin
{
    class WConditions extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mConditions.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mConditions.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("MissionConditions");
            float f = 13F;
            clientWindow = create(new GWindowTabDialogClient());
            com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient = (com.maddox.gwindow.GWindowTabDialogClient)clientWindow;
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = null;
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("weather"), gwindowtabdialogclient.create(gwindowdialogclient = new GWindowDialogClient()));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Weather"), null));
            gwindowdialogclient.addControl(wCloudType = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 1.0F, 8F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getCloudType();
                    return super.notify(l, i1);
                }

            }
);
            wCloudType.setEditable(false);
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Clear"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Good"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Hazy"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Poor"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Blind"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Rain/Snow"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Thunder"));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("CloudHeight"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f + 5.5F, 3F, 2.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[m]"), null));
            gwindowdialogclient.addControl(wCloudHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, f, 3F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getCloudHeight();
                    return super.notify(l, i1);
                }

            }
);
            wCloudHeight.setEditable(true);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 13F, 11F, 1.6F, com.maddox.il2.builder.Plugin.i18n("WindTable"), null));
            boxWindTable = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 15F, 20F, 25F);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 3F, 16F, 9F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Altitude[m]"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 10F, 16F, 9F, 1.3F, com.maddox.il2.builder.Plugin.i18n("WindSpeed[m/s]"), null));
            gwindowdialogclient.addLabel(wLabel0 = new GWindowLabel(gwindowdialogclient, 1.0F, 18F, 5F, 1.3F, "10", null));
            wLabel0.align = 2;
            gwindowdialogclient.addLabel(wLabel1 = new GWindowLabel(gwindowdialogclient, 1.0F, 20F, 5F, 1.3F, "1000", null));
            wLabel1.align = 2;
            gwindowdialogclient.addLabel(wLabel2 = new GWindowLabel(gwindowdialogclient, 1.0F, 22F, 5F, 1.3F, "2000", null));
            wLabel2.align = 2;
            gwindowdialogclient.addLabel(wLabel3 = new GWindowLabel(gwindowdialogclient, 1.0F, 24F, 5F, 1.3F, "3000", null));
            wLabel3.align = 2;
            gwindowdialogclient.addLabel(wLabel4 = new GWindowLabel(gwindowdialogclient, 1.0F, 26F, 5F, 1.3F, "4000", null));
            wLabel4.align = 2;
            gwindowdialogclient.addLabel(wLabel5 = new GWindowLabel(gwindowdialogclient, 1.0F, 28F, 5F, 1.3F, "5000", null));
            wLabel5.align = 2;
            gwindowdialogclient.addLabel(wLabel6 = new GWindowLabel(gwindowdialogclient, 1.0F, 30F, 5F, 1.3F, "6000", null));
            wLabel6.align = 2;
            gwindowdialogclient.addLabel(wLabel7 = new GWindowLabel(gwindowdialogclient, 1.0F, 32F, 5F, 1.3F, "7000", null));
            wLabel7.align = 2;
            gwindowdialogclient.addLabel(wLabel8 = new GWindowLabel(gwindowdialogclient, 1.0F, 34F, 5F, 1.3F, "8000", null));
            wLabel8.align = 2;
            gwindowdialogclient.addLabel(wLabel9 = new GWindowLabel(gwindowdialogclient, 1.0F, 36F, 5F, 1.3F, "9000", null));
            wLabel9.align = 2;
            gwindowdialogclient.addLabel(wLabel10 = new GWindowLabel(gwindowdialogclient, 1.0F, 38F, 5F, 1.3F, "10000", null));
            wLabel10.align = 2;
            gwindowdialogclient.addLabel(wLabel00 = new GWindowLabel(gwindowdialogclient, 10F, 18F, 5F, 1.3F, "", null));
            wLabel00.align = 2;
            gwindowdialogclient.addLabel(wLabel11 = new GWindowLabel(gwindowdialogclient, 10F, 20F, 5F, 1.3F, "", null));
            wLabel11.align = 2;
            gwindowdialogclient.addLabel(wLabel22 = new GWindowLabel(gwindowdialogclient, 10F, 22F, 5F, 1.3F, "", null));
            wLabel22.align = 2;
            gwindowdialogclient.addLabel(wLabel33 = new GWindowLabel(gwindowdialogclient, 10F, 24F, 5F, 1.3F, "", null));
            wLabel33.align = 2;
            gwindowdialogclient.addLabel(wLabel44 = new GWindowLabel(gwindowdialogclient, 10F, 26F, 5F, 1.3F, "", null));
            wLabel44.align = 2;
            gwindowdialogclient.addLabel(wLabel55 = new GWindowLabel(gwindowdialogclient, 10F, 28F, 5F, 1.3F, "", null));
            wLabel55.align = 2;
            gwindowdialogclient.addLabel(wLabel66 = new GWindowLabel(gwindowdialogclient, 10F, 30F, 5F, 1.3F, "", null));
            wLabel66.align = 2;
            gwindowdialogclient.addLabel(wLabel77 = new GWindowLabel(gwindowdialogclient, 10F, 32F, 5F, 1.3F, "", null));
            wLabel77.align = 2;
            gwindowdialogclient.addLabel(wLabel88 = new GWindowLabel(gwindowdialogclient, 10F, 34F, 5F, 1.3F, "", null));
            wLabel88.align = 2;
            gwindowdialogclient.addLabel(wLabel99 = new GWindowLabel(gwindowdialogclient, 10F, 36F, 5F, 1.3F, "", null));
            wLabel99.align = 2;
            gwindowdialogclient.addLabel(wLabel1010 = new GWindowLabel(gwindowdialogclient, 10F, 38F, 5F, 1.3F, "", null));
            wLabel1010.align = 2;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("WindDirection"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f + 5.5F, 5F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[deg]"), null));
            gwindowdialogclient.addControl(wWindDirection = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, f, 5F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getWindDirection();
                    return super.notify(l, i1);
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("WindVelocity"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f + 5.5F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[m/s]"), null));
            gwindowdialogclient.addControl(wWindVelocity = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, f, 7F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getWindVelocity();
                    return super.notify(l, i1);
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Gust"), null));
            gwindowdialogclient.addControl(wGust = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 9F, 8F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getGust();
                    return super.notify(l, i1);
                }

            }
);
            wGust.setEditable(false);
            wGust.add(com.maddox.il2.builder.Plugin.i18n("None"));
            wGust.add(com.maddox.il2.builder.Plugin.i18n("Low"));
            wGust.add(com.maddox.il2.builder.Plugin.i18n("Moderate"));
            wGust.add(com.maddox.il2.builder.Plugin.i18n("Strong"));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 11F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Turbulence"), null));
            gwindowdialogclient.addControl(wTurbulence = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 11F, 8F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getTurbulence();
                    return super.notify(l, i1);
                }

            }
);
            wTurbulence.setEditable(false);
            wTurbulence.add(com.maddox.il2.builder.Plugin.i18n("None"));
            wTurbulence.add(com.maddox.il2.builder.Plugin.i18n("Low"));
            wTurbulence.add(com.maddox.il2.builder.Plugin.i18n("Moderate"));
            wTurbulence.add(com.maddox.il2.builder.Plugin.i18n("Strong"));
            wTurbulence.add(com.maddox.il2.builder.Plugin.i18n("VeryStrong"));
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("Season"), gwindowtabdialogclient.create(gwindowdialogclient = new GWindowDialogClient()));
            f = 10F;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Time"), null));
            gwindowdialogclient.addControl(wTimeH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, f, 1.0F, 2.0F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int l, int i1)
                {
                    if(l != 2)
                    {
                        return false;
                    } else
                    {
                        getTime();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, f + 2.15F, 1.0F, 1.0F, 1.3F, ":", null));
            gwindowdialogclient.addControl(wTimeM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, f + 2.5F, 1.0F, 2.0F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int l, int i1)
                {
                    if(l != 2)
                    {
                        return false;
                    } else
                    {
                        getTime();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Day"), null));
            gwindowdialogclient.addControl(wDay = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 3F, 5F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getDay();
                    return super.notify(l, i1);
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Month"), null));
            gwindowdialogclient.addControl(wMonth = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 5F, 5F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getMonth();
                    return super.notify(l, i1);
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, f - 1.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Year"), null));
            gwindowdialogclient.addControl(wYear = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, f, 7F, 5F) {

                public boolean notify(int l, int i1)
                {
                    if(l == 2)
                        getYear();
                    return super.notify(l, i1);
                }

            }
);
            wDay.setEditable(false);
            wYear.setEditable(false);
            wMonth.setEditable(false);
            for(int i = 0; i < _dayKey.length; i++)
                wDay.add(_dayKey[i]);

            for(int j = 0; j < _monthKey.length; j++)
                wMonth.add(_monthKey[j]);

            for(int k = 0; k < _yearKey.length; k++)
                wYear.add(_yearKey[k]);

            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("misc"), gwindowtabdialogclient.create(gwindowdialogclient = new GWindowDialogClient()));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("timeLocked"), null));
            gwindowdialogclient.addControl(wTimeFix = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 14F, 1.0F, null) {

                public void preRender()
                {
                    super.preRender();
                    setChecked(com.maddox.il2.ai.World.cur().isTimeOfDayConstant(), false);
                }

                public boolean notify(int l, int i1)
                {
                    if(l != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.ai.World.cur().setTimeOfDayConstant(isChecked());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("weaponsLocked"), null));
            gwindowdialogclient.addControl(wWeaponFix = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 14F, 3F, null) {

                public void preRender()
                {
                    super.preRender();
                    setChecked(com.maddox.il2.ai.World.cur().isWeaponsConstant(), false);
                }

                public boolean notify(int l, int i1)
                {
                    if(l != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.ai.World.cur().setWeaponsConstant(isChecked());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            float f = com.maddox.il2.ai.World.getTimeofDay();
            int i = (int)f % 24;
            int j = (int)(60F * (f - (float)(int)f));
            wTimeH.setValue("" + i, false);
            wTimeM.setValue("" + j, false);
            wCloudType.setSelected(cloudType, true, false);
            int k = (int)cloudHeight;
            wCloudHeight.setValue("" + k, false);
            wTimeFix.setChecked(com.maddox.il2.ai.World.cur().isTimeOfDayConstant(), false);
            wWeaponFix.setChecked(com.maddox.il2.ai.World.cur().isWeaponsConstant(), false);
            wDay.setValue("" + day, false);
            wMonth.setValue("" + month, false);
            wYear.setValue("" + year, false);
            wWindDirection.setValue("" + windDirection, false);
            wWindVelocity.setValue("" + windVelocity, false);
            int l = gust;
            int i1 = turbulence;
            if(gust > 0)
                l = (gust - 6) / 2;
            wGust.setSelected(l, true, false);
            if(turbulence > 0)
                i1 = turbulence - 2;
            wTurbulence.setSelected(i1, true, false);
            calcWindTable(cloudType, cloudHeight, windVelocity);
        }

        public void calcWindTable(int i, float f, float f1)
        {
            float f2 = f1;
            if(f2 == 0.0F)
                f2 = 0.25F + (float)(wCloudType.getSelected() * wCloudType.getSelected()) * 0.12F;
            float f3 = f + 300F;
            float f4 = f3 / 2.0F;
            float f5 = (f2 * f4) / 3000F + f2;
            float f6 = (f2 * (f3 - f4)) / 9000F + f5;
            int ai[] = new int[11];
            for(int j = 0; j <= 10; j++)
            {
                int k = j * 1000;
                if((float)k > f3)
                    f2 = f6 + (((float)k - f3) * f2) / 18000F;
                if((float)k > f4)
                    f2 = f5 + (((float)k - f4) * f2) / 9000F;
                else
                if((float)k > 10F)
                    f2 += (f2 * (float)k) / 3000F;
                if((float)k > 10F);
                ai[j] = (int)f2;
            }

            wLabel00.cap.set("" + ai[0]);
            wLabel11.cap.set("" + ai[1]);
            wLabel22.cap.set("" + ai[2]);
            wLabel33.cap.set("" + ai[3]);
            wLabel44.cap.set("" + ai[4]);
            wLabel55.cap.set("" + ai[5]);
            wLabel66.cap.set("" + ai[6]);
            wLabel77.cap.set("" + ai[7]);
            wLabel88.cap.set("" + ai[8]);
            wLabel99.cap.set("" + ai[9]);
            wLabel1010.cap.set("" + ai[10]);
        }

        public void getTime()
        {
            java.lang.String s = wTimeH.getValue();
            double d = 0.0D;
            try
            {
                d = java.lang.Double.parseDouble(s);
            }
            catch(java.lang.Exception exception) { }
            if(d < 0.0D)
                d = 0.0D;
            if(d > 23D)
                d = 23D;
            s = wTimeM.getValue();
            double d1 = 0.0D;
            try
            {
                d1 = java.lang.Double.parseDouble(s);
            }
            catch(java.lang.Exception exception1) { }
            if(d1 < 0.0D)
                d1 = 0.0D;
            if(d1 >= 60D)
                d1 = 59D;
            float f = (float)(d + d1 / 60D);
            if((int)(f * 60F) != (int)(com.maddox.il2.ai.World.getTimeofDay() * 60F))
            {
                com.maddox.il2.ai.World.setTimeofDay(f);
                if(com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
                    com.maddox.il2.ai.World.land().cubeFullUpdate();
            }
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getCloudType()
        {
            cloudType = wCloudType.getSelected();
            com.maddox.il2.game.Mission.setCloudsType(cloudType);
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getCloudHeight()
        {
            try
            {
                cloudHeight = java.lang.Float.parseFloat(wCloudHeight.getValue());
            }
            catch(java.lang.Exception exception) { }
            if(cloudHeight < 300F)
                cloudHeight = 300F;
            if(cloudHeight > 5000F)
                cloudHeight = 5000F;
            com.maddox.il2.game.Mission.setCloudsHeight(cloudHeight);
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getYear()
        {
            year = java.lang.Integer.parseInt(wYear.getValue());
            com.maddox.il2.game.Mission.setYear(year);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void getDay()
        {
            day = java.lang.Integer.parseInt(wDay.getValue());
            com.maddox.il2.game.Mission.setDay(day);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void getMonth()
        {
            month = java.lang.Integer.parseInt(wMonth.getValue());
            com.maddox.il2.game.Mission.setMonth(month);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void getWindDirection()
        {
            try
            {
                windDirection = java.lang.Float.parseFloat(wWindDirection.getValue());
            }
            catch(java.lang.Exception exception) { }
            if(windDirection < 0.0F)
                windDirection = 0.0F;
            if(windDirection >= 360F)
                windDirection = 0.0F;
            com.maddox.il2.game.Mission.setWindDirection(windDirection);
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getWindVelocity()
        {
            try
            {
                windVelocity = java.lang.Float.parseFloat(wWindVelocity.getValue());
            }
            catch(java.lang.Exception exception) { }
            if(windVelocity > 15F)
                windVelocity = 15F;
            if(windVelocity < 0.0F)
                windVelocity = 0.0F;
            com.maddox.il2.game.Mission.setWindVelocity(windVelocity);
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getGust()
        {
            gust = wGust.getSelected();
            if(gust > 0)
                gust = gust * 2 + 6;
            float f = (float)gust * 1.0F;
            com.maddox.il2.game.Mission.setGust(f);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void getTurbulence()
        {
            turbulence = wTurbulence.getSelected();
            if(turbulence > 0)
                turbulence+= = 2;
            float f = (float)turbulence * 1.0F;
            com.maddox.il2.game.Mission.setTurbulence(f);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowEditControl wTimeH;
        com.maddox.gwindow.GWindowEditControl wTimeM;
        com.maddox.gwindow.GWindowComboControl wCloudType;
        com.maddox.gwindow.GWindowEditControl wCloudHeight;
        com.maddox.gwindow.GWindowCheckBox wTimeFix;
        com.maddox.gwindow.GWindowCheckBox wWeaponFix;
        com.maddox.gwindow.GWindowComboControl wYear;
        com.maddox.gwindow.GWindowComboControl wDay;
        com.maddox.gwindow.GWindowComboControl wMonth;
        com.maddox.gwindow.GWindowEditControl wWindDirection;
        com.maddox.gwindow.GWindowEditControl wWindVelocity;
        com.maddox.gwindow.GWindowComboControl wGust;
        com.maddox.gwindow.GWindowComboControl wTurbulence;
        com.maddox.gwindow.GWindowBoxSeparate boxWindTable;
        com.maddox.gwindow.GWindowLabel wLabel0;
        com.maddox.gwindow.GWindowLabel wLabel1;
        com.maddox.gwindow.GWindowLabel wLabel2;
        com.maddox.gwindow.GWindowLabel wLabel3;
        com.maddox.gwindow.GWindowLabel wLabel4;
        com.maddox.gwindow.GWindowLabel wLabel5;
        com.maddox.gwindow.GWindowLabel wLabel6;
        com.maddox.gwindow.GWindowLabel wLabel7;
        com.maddox.gwindow.GWindowLabel wLabel8;
        com.maddox.gwindow.GWindowLabel wLabel9;
        com.maddox.gwindow.GWindowLabel wLabel10;
        com.maddox.gwindow.GWindowLabel wLabel00;
        com.maddox.gwindow.GWindowLabel wLabel11;
        com.maddox.gwindow.GWindowLabel wLabel22;
        com.maddox.gwindow.GWindowLabel wLabel33;
        com.maddox.gwindow.GWindowLabel wLabel44;
        com.maddox.gwindow.GWindowLabel wLabel55;
        com.maddox.gwindow.GWindowLabel wLabel66;
        com.maddox.gwindow.GWindowLabel wLabel77;
        com.maddox.gwindow.GWindowLabel wLabel88;
        com.maddox.gwindow.GWindowLabel wLabel99;
        com.maddox.gwindow.GWindowLabel wLabel1010;

        public WConditions()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 23F, 45F, true);
            bSizable = true;
        }
    }

    class WFoW extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mFoW.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mFoW.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("mds.tabRadar");
            clientWindow = create(new GWindowDialogClient());
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
            new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 1.0F, 52F, 23F);
            new GWindowHSeparate(gwindowdialogclient, 4F, 3.5F, 48F);
            new GWindowHSeparate(gwindowdialogclient, 4F, 11.5F, 48F);
            new GWindowHSeparate(gwindowdialogclient, 2.0F, 21.5F, 50F);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 4F, 2.0F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.advanced1"), null));
            gwindowdialogclient.addControl(wZutiRadar_IsRadarInAdvancedMode = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 2.0F, 2.0F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(wZutiRadar_ShipsAsRadar.isChecked())
                    {
                        wZutiRadar_ShipRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ShipRadar_MinHeight.setEnable(isChecked());
                        wZutiRadar_ShipRadar_MaxHeight.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MinHeight.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MaxHeight.setEnable(isChecked());
                    }
                    if(wZutiRadar_ScoutsAsRadar.isChecked())
                    {
                        wZutiRadar_ScoutRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ScoutRadar_DeltaHeight.setEnable(isChecked());
                        wZutiRadar_ScoutGroundObjects_Alpha.setEnable(isChecked());
                        wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());
                    }
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 22F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.refresh"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 23.5F, 22F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.SEC"), null));
            gwindowdialogclient.addControl(wZutiRadar_RefreshInterval = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 20F, 22F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x1869f));
                        zutiRadar_RefreshInterval = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 4F, 4F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.shipsAsRadars"), null));
            gwindowdialogclient.addControl(wZutiRadar_ShipsAsRadar = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 2.0F, 4F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(wZutiRadar_IsRadarInAdvancedMode.isChecked())
                    {
                        wZutiRadar_ShipRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ShipRadar_MinHeight.setEnable(isChecked());
                        wZutiRadar_ShipRadar_MaxHeight.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MinHeight.setEnable(isChecked());
                        wZutiRadar_ShipSmallRadar_MaxHeight.setEnable(isChecked());
                    }
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ShipsAsRadar = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 8F, 16F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.BigShips"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 10F, 16F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.SmallShips"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 23.5F, 8F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.KM"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 35.5F, 8F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.M"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 47.5F, 8F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.M"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 23.5F, 10F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.KM"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 35.5F, 10F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.M"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 47.5F, 10F, 20F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.M"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 13.75F, 6F, 16F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.bigShipMax"), null, 1));
            gwindowdialogclient.addControl(wZutiRadar_ShipRadar_MaxRange = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 20F, 8F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 1, 0x1869f));
                        zutiRadar_ShipRadar_MaxRange = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 25.75F, 6F, 16F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.bigShipMin"), null, 1));
            gwindowdialogclient.addControl(wZutiRadar_ShipRadar_MinHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 8F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x1869f));
                        zutiRadar_ShipRadar_MinHeight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 37.75F, 6F, 16F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.bigShipMaxH"), null, 1));
            gwindowdialogclient.addControl(wZutiRadar_ShipRadar_MaxHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 44F, 8F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 1000, 0x1869f));
                        zutiRadar_ShipRadar_MaxHeight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addControl(wZutiRadar_ShipSmallRadar_MaxRange = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 20F, 10F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 1, 0x1869f));
                        zutiRadar_ShipSmallRadar_MaxRange = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addControl(wZutiRadar_ShipSmallRadar_MinHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 10F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x1869f));
                        zutiRadar_ShipSmallRadar_MinHeight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addControl(wZutiRadar_ShipSmallRadar_MaxHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 44F, 10F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 1000, 0x1869f));
                        zutiRadar_ShipSmallRadar_MaxHeight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 4F, 12F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutsAsRadars"), null));
            gwindowdialogclient.addControl(wZutiRadar_ScoutsAsRadar = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 2.0F, 12F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(wZutiRadar_IsRadarInAdvancedMode.isChecked())
                    {
                        wZutiRadar_ScoutRadar_MaxRange.setEnable(isChecked());
                        wZutiRadar_ScoutRadar_DeltaHeight.setEnable(isChecked());
                        wZutiRadar_ScoutGroundObjects_Alpha.setEnable(isChecked());
                        wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());
                    }
                    bZutiRadar_ScoutRadarType_Red.setEnable(isChecked());
                    wZutiRadar_ScoutRadarType_Red.setEnable(isChecked());
                    bZutiRadar_ScoutRadarType_Blue.setEnable(isChecked());
                    wZutiRadar_ScoutRadarType_Blue.setEnable(isChecked());
                    wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ScoutsAsRadar = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 14F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutAcScanMax"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 29.5F, 14F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.KM"), null));
            gwindowdialogclient.addControl(wZutiRadar_ScoutRadar_MaxRange = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 26F, 14F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 1, 0x1869f));
                        zutiRadar_ScoutRadar_MaxRange = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 16F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutAcDelta"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 29.5F, 16F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.M"), null));
            gwindowdialogclient.addControl(wZutiRadar_ScoutRadar_DeltaHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 26F, 16F, 3.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 100, 0x1869f));
                        zutiRadar_ScoutRadar_DeltaHeight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 18F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutGroundAlpha"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 29.5F, 18F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.DEG"), null));
            gwindowdialogclient.addControl(wZutiRadar_ScoutGroundObjects_Alpha = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 26F, 18F, 3.5F) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEnable(false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ScoutGroundObjects_Alpha = getSelected() + 1;
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            wZutiRadar_ScoutGroundObjects_Alpha.setEditable(false);
            wZutiRadar_ScoutGroundObjects_Alpha.add("30");
            wZutiRadar_ScoutGroundObjects_Alpha.add("35");
            wZutiRadar_ScoutGroundObjects_Alpha.add("40");
            wZutiRadar_ScoutGroundObjects_Alpha.add("45");
            wZutiRadar_ScoutGroundObjects_Alpha.add("50");
            wZutiRadar_ScoutGroundObjects_Alpha.add("55");
            wZutiRadar_ScoutGroundObjects_Alpha.add("60");
            wZutiRadar_ScoutGroundObjects_Alpha.add("65");
            wZutiRadar_ScoutGroundObjects_Alpha.add("70");
            wZutiRadar_ScoutGroundObjects_Alpha.add("75");
            wZutiRadar_ScoutGroundObjects_Alpha.add("80");
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 5F, 20F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutCmplRecon"), null));
            gwindowdialogclient.addControl(wZutiRadar_ScoutCompleteRecon = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 2.0F, 20F, null) {

                public void afterCreated()
                {
                    super.afterCreated();
                    setEnable(false);
                }

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ScoutCompleteRecon = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addControl(bZutiRadar_ScoutRadarType_Red = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 32.5F, 14F, 20F, 2.0F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutRed"), null) {

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    if(zuti_manageAircrafts == null)
                        zuti_manageAircrafts = new Zuti_WManageAircrafts();
                    if(zuti_manageAircrafts.isVisible())
                    {
                        zuti_manageAircrafts.hideWindow();
                        zuti_manageAircrafts.clearAirNames();
                    } else
                    {
                        zuti_manageAircrafts.setTitle(com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutRedTitle"));
                        zuti_manageAircrafts.setParentEditControl(wZutiRadar_ScoutRadarType_Red);
                        zuti_manageAircrafts.showWindow();
                    }
                    return true;
                }

            }
);
            gwindowdialogclient.addControl(wZutiRadar_ScoutRadarType_Red = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 17F, 22.5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = false;
                    bDelayedNotify = true;
                    bCanEdit = false;
                    hideWindow();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ScoutRadarType_Red = getValue();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addControl(bZutiRadar_ScoutRadarType_Blue = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 32.5F, 17F, 20F, 2.0F, com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutBlue"), null) {

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                        return false;
                    if(zuti_manageAircrafts == null)
                        zuti_manageAircrafts = new Zuti_WManageAircrafts();
                    if(zuti_manageAircrafts.isVisible())
                    {
                        zuti_manageAircrafts.hideWindow();
                        zuti_manageAircrafts.clearAirNames();
                    } else
                    {
                        zuti_manageAircrafts.setTitle(com.maddox.il2.builder.Plugin.i18n("mds.radar.scoutBlueTitle"));
                        zuti_manageAircrafts.setParentEditControl(wZutiRadar_ScoutRadarType_Blue);
                        zuti_manageAircrafts.showWindow();
                    }
                    return true;
                }

            }
);
            gwindowdialogclient.addControl(wZutiRadar_ScoutRadarType_Blue = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 21F, 31F, 31F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = false;
                    bDelayedNotify = true;
                    bCanEdit = false;
                    hideWindow();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_ScoutRadarType_Blue = getValue();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            try
            {
                wZutiRadar_IsRadarInAdvancedMode.setChecked(com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE, false);
                wZutiRadar_RefreshInterval.setValue((new Integer(zutiRadar_RefreshInterval)).toString(), false);
                wZutiRadar_ShipsAsRadar.setChecked(zutiRadar_ShipsAsRadar, false);
                wZutiRadar_ShipRadar_MaxRange.setValue((new Integer(zutiRadar_ShipRadar_MaxRange)).toString(), false);
                wZutiRadar_ShipRadar_MinHeight.setValue((new Integer(zutiRadar_ShipRadar_MinHeight)).toString(), false);
                wZutiRadar_ShipRadar_MaxHeight.setValue((new Integer(zutiRadar_ShipRadar_MaxHeight)).toString(), false);
                wZutiRadar_ShipSmallRadar_MaxRange.setValue((new Integer(zutiRadar_ShipSmallRadar_MaxRange)).toString(), false);
                wZutiRadar_ShipSmallRadar_MinHeight.setValue((new Integer(zutiRadar_ShipSmallRadar_MinHeight)).toString(), false);
                wZutiRadar_ShipSmallRadar_MaxHeight.setValue((new Integer(zutiRadar_ShipSmallRadar_MaxHeight)).toString(), false);
                wZutiRadar_ScoutsAsRadar.setChecked(zutiRadar_ScoutsAsRadar, false);
                wZutiRadar_ScoutRadar_MaxRange.setValue((new Integer(zutiRadar_ScoutRadar_MaxRange)).toString(), false);
                wZutiRadar_ScoutRadar_DeltaHeight.setValue((new Integer(zutiRadar_ScoutRadar_DeltaHeight)).toString(), false);
                wZutiRadar_ScoutRadarType_Red.setValue(zutiRadar_ScoutRadarType_Red, false);
                wZutiRadar_ScoutRadarType_Blue.setValue(zutiRadar_ScoutRadarType_Blue, false);
                wZutiRadar_ScoutGroundObjects_Alpha.setSelected(zutiRadar_ScoutGroundObjects_Alpha - 1, true, false);
                wZutiRadar_ScoutCompleteRecon.setChecked(zutiRadar_ScoutCompleteRecon, false);
            }
            catch(java.lang.Exception exception) { }
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowCheckBox wZutiRadar_IsRadarInAdvancedMode;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_ShipsAsRadar;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_ScoutsAsRadar;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_ScoutCompleteRecon;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipRadar_MaxRange;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipRadar_MinHeight;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipRadar_MaxHeight;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipSmallRadar_MaxRange;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipSmallRadar_MinHeight;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ShipSmallRadar_MaxHeight;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ScoutRadar_MaxRange;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ScoutRadar_DeltaHeight;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_RefreshInterval;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ScoutRadarType_Red;
        com.maddox.gwindow.GWindowEditControl wZutiRadar_ScoutRadarType_Blue;
        com.maddox.gwindow.GWindowComboControl wZutiRadar_ScoutGroundObjects_Alpha;
        com.maddox.gwindow.GWindowButton bZutiRadar_ScoutRadarType_Red;
        com.maddox.gwindow.GWindowButton bZutiRadar_ScoutRadarType_Blue;
        final float separateWidth = 52F;
        final float fowStartOf2ndCol = 21F;
        final float fowTextBoxWidth = 3.5F;
        final float fowStartOf3rdCol = 26F;
        final float fowStartOf4thCol = 46.5F;
        final float fowTitleTextW = 16F;
        final float fowTextW = 30F;
        final float fowStartCol1 = 20F;
        final float fowStartCol2 = 32F;
        final float fowStartCol3 = 44F;


        public WFoW()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 55F, 27F, true);
            bSizable = false;
        }
    }

    class WCraters extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mCraters.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mCraters.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("mds.tabCraters");
            clientWindow = create(new GWindowDialogClient());
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 28F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat11"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 35F, 1.0F, 5F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat12"), null));
            gwindowdialogclient.addControl(wZutiMisc_BombsCat1_CratersVisibilityMultiplier = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 1.0F, 3F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = bNumericFloat = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Float.parseFloat(getValue()), 1.0F, 99999F));
                        zutiMisc_BombsCat1_CratersVisibilityMultiplier = java.lang.Float.parseFloat(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 28F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat21"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 35F, 3F, 5F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat22"), null));
            gwindowdialogclient.addControl(wZutiMisc_BombsCat2_CratersVisibilityMultiplier = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 3F, 3F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = bNumericFloat = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Float.parseFloat(getValue()), 1.0F, 99999F));
                        zutiMisc_BombsCat2_CratersVisibilityMultiplier = java.lang.Float.parseFloat(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 28F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat31"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 35F, 5F, 5F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.craters.cat32"), null));
            gwindowdialogclient.addControl(wZutiMisc_BombsCat3_CratersVisibilityMultiplier = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 32F, 5F, 3F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = bNumericFloat = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Float.parseFloat(getValue()), 1.0F, 99999F));
                        zutiMisc_BombsCat3_CratersVisibilityMultiplier = java.lang.Float.parseFloat(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            try
            {
                wZutiMisc_BombsCat1_CratersVisibilityMultiplier.setValue((new Float(zutiMisc_BombsCat1_CratersVisibilityMultiplier)).toString(), false);
                wZutiMisc_BombsCat2_CratersVisibilityMultiplier.setValue((new Float(zutiMisc_BombsCat2_CratersVisibilityMultiplier)).toString(), false);
                wZutiMisc_BombsCat3_CratersVisibilityMultiplier.setValue((new Float(zutiMisc_BombsCat3_CratersVisibilityMultiplier)).toString(), false);
            }
            catch(java.lang.Exception exception) { }
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowLabel lSeparate_Craters;
        com.maddox.gwindow.GWindowEditControl wZutiMisc_BombsCat1_CratersVisibilityMultiplier;
        com.maddox.gwindow.GWindowEditControl wZutiMisc_BombsCat2_CratersVisibilityMultiplier;
        com.maddox.gwindow.GWindowEditControl wZutiMisc_BombsCat3_CratersVisibilityMultiplier;
        com.maddox.gwindow.GWindowBoxSeparate bSeparate_Craters;


        public WCraters()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 40F, 9F, true);
            bSizable = true;
        }
    }

    class WRespawnTime extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mRespawnTime.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mRespawnTime.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("mds.tabRespawn");
            clientWindow = create(new GWindowDialogClient());
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.bigship"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 24F, 1.0F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.seconds"), null));
            gwindowdialogclient.addControl(wRespawnTime_Bigship = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 18F, 1.0F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x124f80));
                        respawnTime_Bigship = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.ship"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 24F, 3F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.seconds"), null));
            gwindowdialogclient.addControl(wRespawnTime_Ship = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 18F, 3F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x124f80));
                        respawnTime_Ship = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.aeroanchored"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 24F, 5F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.seconds"), null));
            gwindowdialogclient.addControl(wRespawnTime_Aeroanchored = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 18F, 5F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x124f80));
                        respawnTime_Aeroanchored = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.artillery"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 24F, 7F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.seconds"), null));
            gwindowdialogclient.addControl(wRespawnTime_Artillery = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 18F, 7F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x124f80));
                        respawnTime_Artillery = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.searchlight"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 24F, 9F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.respawn.seconds"), null));
            gwindowdialogclient.addControl(wRespawnTime_Searchlight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 18F, 9F, 5F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        setValue(checkValidRange(java.lang.Integer.parseInt(getValue()), 0, 0x124f80));
                        respawnTime_Searchlight = java.lang.Integer.parseInt(getValue());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            try
            {
                wRespawnTime_Bigship.setValue((new Integer(respawnTime_Bigship)).toString(), false);
                wRespawnTime_Ship.setValue((new Integer(respawnTime_Ship)).toString(), false);
                wRespawnTime_Aeroanchored.setValue((new Integer(respawnTime_Aeroanchored)).toString(), false);
                wRespawnTime_Artillery.setValue((new Integer(respawnTime_Artillery)).toString(), false);
                wRespawnTime_Searchlight.setValue((new Integer(respawnTime_Searchlight)).toString(), false);
            }
            catch(java.lang.Exception exception) { }
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowBoxSeparate bSeparate_Respawn;
        com.maddox.gwindow.GWindowEditControl wRespawnTime_Bigship;
        com.maddox.gwindow.GWindowEditControl wRespawnTime_Ship;
        com.maddox.gwindow.GWindowEditControl wRespawnTime_Aeroanchored;
        com.maddox.gwindow.GWindowEditControl wRespawnTime_Artillery;
        com.maddox.gwindow.GWindowEditControl wRespawnTime_Searchlight;
        final float separateWidth = 27F;
        final float respawnTextFieldStart = 20F;


        public WRespawnTime()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 28F, 13F, true);
            bSizable = true;
        }
    }

    class WMisc extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mMisc.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mMisc.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("mds.tabMisc");
            clientWindow = create(new GWindowDialogClient());
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.towerComms"), null));
            gwindowdialogclient.addControl(wZutiRadar_EnableTowerCommunications = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 1.0F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_EnableTowerCommunications = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.disableAI"), null));
            gwindowdialogclient.addControl(wZutiMisc_DisableAIRadioChatter = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 3F, null) {

                public void pcarrierSpawnPointsComboStarter()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiMisc_DisableAIRadioChatter = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.despawn"), null));
            gwindowdialogclient.addControl(wZutiMisc_DespawnAIPlanesAfterLanding = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 5F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiMisc_DespawnAIPlanesAfterLanding = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.hideAirfields"), null));
            gwindowdialogclient.addControl(wZutiRadar_HideUnpopulatedAirstripsFromMinimap = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 7F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_HideUnpopulatedAirstripsFromMinimap = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.hideHBNumbers"), null));
            gwindowdialogclient.addControl(wZutiMisc_HidePlayersCountOnHomeBase = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 9F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiMisc_HidePlayersCountOnHomeBase = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 11F, 30F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.misc.disableVectoring"), null));
            gwindowdialogclient.addControl(wZutiRadar_DisableVectoring = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 32F, 11F, null) {

                public void preRender()
                {
                    super.preRender();
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        zutiRadar_DisableVectoring = isChecked();
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            try
            {
                wZutiRadar_HideUnpopulatedAirstripsFromMinimap.setChecked(zutiRadar_HideUnpopulatedAirstripsFromMinimap, false);
                wZutiRadar_DisableVectoring.setChecked(zutiRadar_DisableVectoring, false);
                wZutiRadar_EnableTowerCommunications.setChecked(zutiRadar_EnableTowerCommunications, false);
                wZutiMisc_DisableAIRadioChatter.setChecked(zutiMisc_DisableAIRadioChatter, false);
                wZutiMisc_DespawnAIPlanesAfterLanding.setChecked(zutiMisc_DespawnAIPlanesAfterLanding, false);
                wZutiMisc_HidePlayersCountOnHomeBase.setChecked(zutiMisc_HidePlayersCountOnHomeBase, false);
            }
            catch(java.lang.Exception exception) { }
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowLabel lSeparate_Misc;
        com.maddox.gwindow.GWindowBoxSeparate bSeparate_Misc;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_EnableTowerCommunications;
        com.maddox.gwindow.GWindowCheckBox wZutiMisc_DisableAIRadioChatter;
        com.maddox.gwindow.GWindowCheckBox wZutiMisc_DespawnAIPlanesAfterLanding;
        com.maddox.gwindow.GWindowCheckBox wZutiMisc_HidePlayersCountOnHomeBase;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_HideUnpopulatedAirstripsFromMinimap;
        com.maddox.gwindow.GWindowCheckBox wZutiRadar_DisableVectoring;
        final float separateWidth = 35F;
        final float miscBoxStart = 32F;


        public WMisc()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 36F, 15F, true);
            bSizable = true;
        }
    }

    class DlgFileConfirmSave extends com.maddox.gwindow.GWindowFileBoxExec
    {

        public boolean isCloseBox()
        {
            return bClose;
        }

        public void exec(com.maddox.gwindow.GWindowFileBox gwindowfilebox, java.lang.String s)
        {
            box = gwindowfilebox;
            bClose = true;
            if(s == null || box.files.size() == 0)
            {
                box.endExec();
                return;
            }
            int i = s.lastIndexOf("/");
            if(i >= 0)
                s = s.substring(i + 1);
            for(int j = 0; j < box.files.size(); j++)
            {
                java.lang.String s1 = ((java.io.File)box.files.get(j)).getName();
                if(s.compareToIgnoreCase(s1) == 0)
                {
                    com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.game.I18N.gui("warning.Warning"), com.maddox.il2.game.I18N.gui("warning.ReplaceFile"), 1, 0.0F) {

                        public void result(int k)
                        {
                            if(k != 3)
                                bClose = false;
                            box.endExec();
                        }

                    }
;
                    return;
                }
            }

            box.endExec();
        }

        com.maddox.gwindow.GWindowFileBox box;
        boolean bClose;

        DlgFileConfirmSave()
        {
            bClose = true;
        }
    }

    static class GWindowMenuItemArmy extends com.maddox.gwindow.GWindowMenuItem
    {

        int army;

        public GWindowMenuItemArmy(com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1, int i)
        {
            super(gwindowmenu, s, s1);
            army = i;
        }
    }


    public static void setChanged()
    {
        if(cur != null)
            cur.bChanged = true;
    }

    public static boolean isChanged()
    {
        if(cur != null)
            return cur.bChanged;
        else
            return false;
    }

    public static java.lang.String missionFileName()
    {
        if(cur == null)
            return null;
        else
            return cur.missionFileName;
    }

    public static void doMissionReload()
    {
        if(cur == null)
            return;
        if(!cur.bReload)
        {
            return;
        } else
        {
            cur.bReload = false;
            cur.doLoadMission("missions/" + cur.missionFileName);
            return;
        }
    }

    public boolean load(java.lang.String s)
    {
        builder.deleteAll();
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
        int i = sectfile.sectionIndex("MAIN");
        if(i < 0)
        {
            builder.tipErr("MissionLoad: '" + s + "' - section MAIN not found");
            return false;
        }
        int j = sectfile.varIndex(i, "MAP");
        if(j < 0)
        {
            builder.tipErr("MissionLoad: '" + s + "' - in section MAIN line MAP not found");
            return false;
        }
        java.lang.String s1 = sectfile.value(i, j);
        com.maddox.il2.builder.PlMapLoad.Land land = com.maddox.il2.builder.PlMapLoad.getLandForFileName(s1);
        if(land == com.maddox.il2.builder.PlMapLoad.getLandLoaded())
        {
            com.maddox.il2.ai.World.cur().statics.restoreAllBridges();
            com.maddox.il2.ai.World.cur().statics.restoreAllHouses();
        } else
        if(!pluginMapLoad.mapLoad(land))
        {
            builder.tipErr("MissionLoad: '" + s + "' - tirrain '" + s1 + "' not loaded");
            return false;
        }
        int k = sectfile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setTimeOfDayConstant(k == 1);
        com.maddox.il2.ai.World.setTimeofDay(sectfile.get("MAIN", "TIME", 12F, 0.0F, 23.99F));
        int l = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setWeaponsConstant(l == 1);
        java.lang.String s2 = sectfile.get("MAIN", "player", (java.lang.String)null);
        com.maddox.il2.builder.Path.playerNum = sectfile.get("MAIN", "playerNum", 0, 0, 3);
        missionArmy = sectfile.get("MAIN", "army", 1, 1, 2);
        year = sectfile.get("SEASON", "Year", 1940, 1930, 1960);
        month = sectfile.get("SEASON", "Month", com.maddox.il2.ai.World.land().config.month, 1, 12);
        day = sectfile.get("SEASON", "Day", 15, 1, 31);
        windDirection = sectfile.get("WEATHER", "WindDirection", 0.0F, 0.0F, 359.99F);
        windVelocity = sectfile.get("WEATHER", "WindSpeed", 0.0F, 0.0F, 15F);
        gust = sectfile.get("WEATHER", "Gust", 0, 0, 12);
        turbulence = sectfile.get("WEATHER", "Turbulence", 0, 0, 6);
        cloudType = sectfile.get("MAIN", "CloudType", 0, 0, 6);
        cloudHeight = sectfile.get("MAIN", "CloudHeight", 1000F, 300F, 5000F);
        com.maddox.il2.game.Mission.createClouds(cloudType, cloudHeight);
        if(com.maddox.il2.game.Main3D.cur3D().clouds != null)
            com.maddox.il2.game.Main3D.cur3D().clouds.setShow(false);
        com.maddox.il2.game.Main3D.cur3D().spritesFog.setShow(false);
        wConditions.update();
        zutiInitMDSVariables();
        zutiLoadMDSVariables(sectfile);
        wFoW.update();
        wCraters.update();
        wRespawnTime.update();
        wMisc.update();
        com.maddox.il2.builder.Plugin.doLoad(sectfile);
        if(s2 != null)
        {
            java.lang.Object aobj[] = builder.pathes.getOwnerAttached();
            int i1 = 0;
            do
            {
                if(i1 >= aobj.length)
                    break;
                com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)aobj[i1];
                if(s2.equals(path.name()))
                {
                    if(!((com.maddox.il2.builder.PathAir)path).bOnlyAI)
                    {
                        com.maddox.il2.builder.Path.player = path;
                        missionArmy = path.getArmy();
                    }
                    break;
                }
                i1++;
            } while(true);
        }
        builder.repaint();
        bChanged = false;
        return true;
    }

    public boolean save(java.lang.String s)
    {
        if(com.maddox.il2.builder.PlMapLoad.getLandLoaded() == null)
        {
            builder.tipErr("MissionSave: tirrain not selected");
            return false;
        }
        com.maddox.rts.SectFile sectfile = new SectFile();
        sectfile.setFileName(s);
        int i = sectfile.sectionAdd("MAIN");
        sectfile.lineAdd(i, "MAP", com.maddox.il2.builder.PlMapLoad.mapFileName());
        sectfile.lineAdd(i, "TIME", "" + com.maddox.il2.ai.World.getTimeofDay());
        if(com.maddox.il2.ai.World.cur().isTimeOfDayConstant())
            sectfile.lineAdd(i, "TIMECONSTANT", "1");
        if(com.maddox.il2.ai.World.cur().isWeaponsConstant())
            sectfile.lineAdd(i, "WEAPONSCONSTANT", "1");
        sectfile.lineAdd(i, "CloudType", "" + cloudType);
        sectfile.lineAdd(i, "CloudHeight", "" + cloudHeight);
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Path.player))
        {
            sectfile.lineAdd(i, "player", com.maddox.il2.builder.Path.player.name());
            if(com.maddox.il2.builder.Path.playerNum >= ((com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Path.player).planes)
                com.maddox.il2.builder.Path.playerNum = 0;
        } else
        {
            com.maddox.il2.builder.Path.playerNum = 0;
        }
        sectfile.lineAdd(i, "army", "" + missionArmy);
        sectfile.lineAdd(i, "playerNum", "" + com.maddox.il2.builder.Path.playerNum);
        int j = sectfile.sectionAdd("SEASON");
        sectfile.lineAdd(j, "Year", "" + year);
        sectfile.lineAdd(j, "Month", "" + month);
        sectfile.lineAdd(j, "Day", "" + day);
        int k = sectfile.sectionAdd("WEATHER");
        sectfile.lineAdd(k, "WindDirection", "" + windDirection);
        sectfile.lineAdd(k, "WindSpeed", "" + windVelocity);
        sectfile.lineAdd(k, "Gust", "" + gust);
        sectfile.lineAdd(k, "Turbulence", "" + turbulence);
        zutiSaveMDSVariables(sectfile);
        if(!com.maddox.il2.builder.Plugin.doSave(sectfile))
        {
            return false;
        } else
        {
            sectfile.saveFile(s);
            bChanged = false;
            return true;
        }
    }

    public void mapLoaded()
    {
        zutiInitMDSVariables();
        if(!builder.isLoadedLandscape())
            return;
        java.lang.String s = "maps/" + com.maddox.il2.builder.PlMapLoad.mapFileName();
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        int i = sectfile.sectionIndex("static");
        if(i >= 0 && sectfile.vars(i) > 0)
        {
            java.lang.String s1 = sectfile.var(i, 0);
            com.maddox.il2.objects.Statics.load(com.maddox.rts.HomePath.concatNames(s, s1), com.maddox.il2.builder.PlMapLoad.bridgeActors);
        }
        int j = sectfile.sectionIndex("text");
        if(j >= 0 && sectfile.vars(j) > 0)
        {
            java.lang.String s2 = sectfile.var(j, 0);
            if(com.maddox.il2.game.Main3D.cur3D().land2DText == null)
                com.maddox.il2.game.Main3D.cur3D().land2DText = new Land2DText();
            else
                com.maddox.il2.game.Main3D.cur3D().land2DText.clear();
            com.maddox.il2.game.Main3D.cur3D().land2DText.load(com.maddox.rts.HomePath.concatNames(s, s2));
        }
        com.maddox.il2.objects.Statics.trim();
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape _tmp = landscape;
        if(com.maddox.il2.engine.Landscape.isExistMeshs())
        {
            for(int k = 0; k < com.maddox.il2.builder.PathFind.tShip.sy; k++)
            {
                for(int l = 0; l < com.maddox.il2.builder.PathFind.tShip.sx; l++)
                {
                    com.maddox.il2.engine.Landscape _tmp1 = landscape;
                    if(com.maddox.il2.engine.Landscape.isExistMesh(l, com.maddox.il2.builder.PathFind.tShip.sy - k - 1))
                    {
                        com.maddox.il2.builder.PathFind.tShip.I(l, k, com.maddox.il2.builder.PathFind.tShip.intI(l, k) | 8);
                        com.maddox.il2.builder.PathFind.tNoShip.I(l, k, com.maddox.il2.builder.PathFind.tNoShip.intI(l, k) | 8);
                    }
                }

            }

        }
        com.maddox.il2.game.Mission.createClouds(cloudType, cloudHeight);
        if(com.maddox.il2.game.Main3D.cur3D().clouds != null)
            com.maddox.il2.game.Main3D.cur3D().clouds.setShow(false);
        com.maddox.il2.game.Main3D.cur3D().spritesFog.setShow(false);
        wConditions.update();
        wFoW.update();
        wCraters.update();
        wRespawnTime.update();
        wMisc.update();
    }

    public void configure()
    {
        builder.bMultiSelect = false;
        if(com.maddox.il2.builder.PlMission.getPlugin("MapLoad") == null)
        {
            throw new RuntimeException("PlMission: plugin 'MapLoad' not found");
        } else
        {
            pluginMapLoad = (com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.PlMission.getPlugin("MapLoad");
            return;
        }
    }

    void _viewTypeAll(boolean flag)
    {
        com.maddox.il2.builder.Plugin.doViewTypeAll(flag);
        viewBridge(flag);
        viewRunaway(flag);
        viewName.bChecked = builder.conf.bShowName = flag;
        viewTime.bChecked = builder.conf.bShowTime = flag;
        for(int i = 0; i < builder.conf.bShowArmy.length; i++)
            viewArmy[i].bChecked = builder.conf.bShowArmy[i] = flag;

        if(!flag)
            builder.setSelected(null);
    }

    void viewBridge(boolean flag)
    {
        builder.conf.bViewBridge = flag;
        viewBridge.bChecked = builder.conf.bViewBridge;
    }

    void viewBridge()
    {
        viewBridge(!builder.conf.bViewBridge);
    }

    void viewRunaway(boolean flag)
    {
        builder.conf.bViewRunaway = flag;
        viewRunaway.bChecked = builder.conf.bViewRunaway;
    }

    void viewRunaway()
    {
        viewRunaway(!builder.conf.bViewRunaway);
    }

    public static void checkShowCurrentArmy()
    {
        java.lang.Object obj = builder.selectedPath();
        if(obj == null)
            obj = builder.selectedActor();
        if(obj == null)
            return;
        int i = ((com.maddox.il2.engine.Actor) (obj)).getArmy();
        if(!builder.conf.bShowArmy[i])
            builder.setSelected(null);
    }

    private java.lang.String checkMisExtension(java.lang.String s)
    {
        if(!s.toLowerCase().endsWith(".mis"))
            return s + ".mis";
        else
            return s;
    }

    public void createGUI()
    {
        zutiInitMDSVariables();
        builder.mDisplayFilter.subMenu.addItem("-", null);
        viewBridge = builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("showBridge"), com.maddox.il2.builder.PlMission.i18n("TIPshowBridge")) {

            public void execute()
            {
                viewBridge();
            }

        }
);
        viewBridge.bChecked = builder.conf.bViewBridge;
        viewRunaway = builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("showRunway"), com.maddox.il2.builder.PlMission.i18n("TIPshowRunway")) {

            public void execute()
            {
                viewRunaway();
            }

        }
);
        viewRunaway.bChecked = builder.conf.bViewRunaway;
        builder.mDisplayFilter.subMenu.addItem("-", null);
        viewName = builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("showName"), com.maddox.il2.builder.PlMission.i18n("TIPshowName")) {

            public void execute()
            {
                bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowName = !com.maddox.il2.builder.Plugin.builder.conf.bShowName;
            }

        }
);
        viewName.bChecked = builder.conf.bShowName;
        viewTime = builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("showTime"), com.maddox.il2.builder.PlMission.i18n("TIPshowTime")) {

            public void execute()
            {
                bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowTime = !com.maddox.il2.builder.Plugin.builder.conf.bShowTime;
            }

        }
);
        viewTime.bChecked = builder.conf.bShowTime;
        viewArmy = new com.maddox.il2.builder.GWindowMenuItemArmy[com.maddox.il2.builder.Builder.armyAmount()];
        for(int i = 0; i < com.maddox.il2.builder.Builder.armyAmount(); i++)
        {
            viewArmy[i] = builder.mDisplayFilter.subMenu.addItem(new com.maddox.il2.builder.GWindowMenuItemArmy(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("showArmy") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i)), com.maddox.il2.builder.PlMission.i18n("TIPshowArmy") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i)), i) {

                public void execute()
                {
                    bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[army] = !com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[army];
                    com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                }

            }
);
            viewArmy[i].bChecked = builder.conf.bShowArmy[i];
        }

        builder.mDisplayFilter.subMenu.addItem("-", null);
        builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("&ShowAll"), com.maddox.il2.builder.PlMission.i18n("TIPShowAll")) {

            public void execute()
            {
                _viewTypeAll(true);
            }

        }
);
        builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMission.i18n("&HideAll"), com.maddox.il2.builder.PlMission.i18n("TIPHideAll")) {

            public void execute()
            {
                _viewTypeAll(false);
            }

        }
);
        builder.mFile.subMenu.addItem(1, new com.maddox.gwindow.GWindowMenuItem(builder.mFile.subMenu, com.maddox.il2.builder.PlMission.i18n("Load"), com.maddox.il2.builder.PlMission.i18n("TIPLoad")) {

            public void execute()
            {
                doDlgLoadMission();
            }

        }
);
        builder.mFile.subMenu.addItem(2, new com.maddox.gwindow.GWindowMenuItem(builder.mFile.subMenu, com.maddox.il2.builder.PlMission.i18n("Save"), com.maddox.il2.builder.PlMission.i18n("TIPSaveAs")) {

            public void execute()
            {
                if(missionFileName != null)
                {
                    save("missions/" + missionFileName);
                } else
                {
                    com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                            "*.mis"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                            {
                                s = checkMisExtension(s);
                                missionFileName = s;
                                ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                lastOpenFile = s;
                                save("missions/" + s);
                            }
                        }

                    }
;
                    gwindowfilesaveas.exec = new DlgFileConfirmSave();
                    if(lastOpenFile != null)
                        gwindowfilesaveas.setSelectFile(lastOpenFile);
                }
            }


        }
);
        builder.mFile.subMenu.addItem(3, new com.maddox.gwindow.GWindowMenuItem(builder.mFile.subMenu, com.maddox.il2.builder.PlMission.i18n("SaveAs"), com.maddox.il2.builder.PlMission.i18n("TIPSaveAs")) {

            public void execute()
            {
                com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                    new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                        "*.mis"
                    })
                }) {

                    public void result(java.lang.String s)
                    {
                        if(s != null)
                        {
                            s = checkMisExtension(s);
                            missionFileName = s;
                            ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                            lastOpenFile = s;
                            save("missions/" + s);
                        }
                    }

                }
;
                gwindowfilesaveas.exec = new DlgFileConfirmSave();
                if(lastOpenFile != null)
                    gwindowfilesaveas.setSelectFile(lastOpenFile);
            }


        }
);
        builder.mFile.subMenu.addItem(4, new com.maddox.gwindow.GWindowMenuItem(builder.mFile.subMenu, com.maddox.il2.builder.PlMission.i18n("Play"), com.maddox.il2.builder.PlMission.i18n("TIPPlay")) {

            public void execute()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
                    return;
                if(com.maddox.il2.builder.PlMission.isChanged() || missionFileName == null)
                {
                    if(missionFileName != null)
                    {
                        if(save("missions/" + missionFileName))
                            playMission();
                    } else
                    {
                        com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                            new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                "*.mis"
                            })
                        }) {

                            public void result(java.lang.String s)
                            {
                                if(s != null)
                                {
                                    s = checkMisExtension(s);
                                    missionFileName = s;
                                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                    lastOpenFile = s;
                                    if(save("missions/" + s))
                                        playMission();
                                }
                            }

                        }
;
                        gwindowfilesaveas.exec = new DlgFileConfirmSave();
                        if(lastOpenFile != null)
                            gwindowfilesaveas.setSelectFile(lastOpenFile);
                    }
                } else
                {
                    playMission();
                }
            }


        }
);
        builder.mFile.subMenu.bNotify = true;
        builder.mFile.subMenu.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                if(j != 13)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(2).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(3).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(4).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    return false;
                }
            }

        }
);
        mConditions = builder.mConfigure.subMenu.addItem(0, new com.maddox.gwindow.GWindowMenuItem(builder.mConfigure.subMenu, com.maddox.il2.builder.PlMission.i18n("&Conditions"), com.maddox.il2.builder.PlMission.i18n("TIPConditions")) {

            public void execute()
            {
                if(wConditions.isVisible())
                    wConditions.hideWindow();
                else
                    wConditions.showWindow();
            }

        }
);
        mFoW = builder.mConfigure.subMenu.addItem(1, new com.maddox.gwindow.GWindowMenuItem(builder.mConfigure.subMenu, com.maddox.il2.builder.PlMission.i18n("&FogOfWar"), com.maddox.il2.builder.PlMission.i18n("TIPFoW")) {

            public void execute()
            {
                if(wFoW.isVisible())
                    wFoW.hideWindow();
                else
                    wFoW.showWindow();
            }

        }
);
        builder.mConfigure.subMenu.addItem(2, "-", null);
        mCraters = builder.mConfigure.subMenu.addItem(3, new com.maddox.gwindow.GWindowMenuItem(builder.mConfigure.subMenu, com.maddox.il2.builder.PlMission.i18n("Cra&ters"), com.maddox.il2.builder.PlMission.i18n("TIPCraters")) {

            public void execute()
            {
                if(wCraters.isVisible())
                    wCraters.hideWindow();
                else
                    wCraters.showWindow();
            }

        }
);
        mRespawnTime = builder.mConfigure.subMenu.addItem(4, new com.maddox.gwindow.GWindowMenuItem(builder.mConfigure.subMenu, com.maddox.il2.builder.PlMission.i18n("&Respawn"), com.maddox.il2.builder.PlMission.i18n("TIPRespawnTime")) {

            public void execute()
            {
                if(wRespawnTime.isVisible())
                    wRespawnTime.hideWindow();
                else
                    wRespawnTime.showWindow();
            }

        }
);
        mMisc = builder.mConfigure.subMenu.addItem(5, new com.maddox.gwindow.GWindowMenuItem(builder.mConfigure.subMenu, com.maddox.il2.builder.PlMission.i18n("&Misc"), com.maddox.il2.builder.PlMission.i18n("TIPMisc")) {

            public void execute()
            {
                if(wMisc.isVisible())
                    wMisc.hideWindow();
                else
                    wMisc.showWindow();
            }

        }
);
        builder.mEdit.subMenu.addItem(0, "-", null);
        wConditions = new WConditions();
        wConditions.update();
        wFoW = new WFoW();
        wFoW.update();
        wCraters = new WCraters();
        wCraters.update();
        wRespawnTime = new WRespawnTime();
        wRespawnTime.update();
        wMisc = new WMisc();
        wMisc.update();
    }

    private void doLoadMission(java.lang.String s)
    {
        _loadFileName = s;
        _loadMessageBox = new GWindowMessageBox(builder.clientWindow.root, 20F, true, com.maddox.il2.builder.PlMission.i18n("StandBy"), com.maddox.il2.builder.PlMission.i18n("LoadingMission"), 4, 0.0F);
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                load(_loadFileName);
                _loadMessageBox.close(false);
            }

        }
;
    }

    private void playMission()
    {
        com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile("missions/" + missionFileName, 0);
        bReload = true;
        com.maddox.il2.game.Main.stateStack().push(4);
    }

    private void doDlgLoadMission()
    {
        if(!com.maddox.il2.builder.PlMission.isChanged())
        {
            _doDlgLoadMission();
            return;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(builder.clientWindow.root, 20F, true, com.maddox.il2.builder.PlMission.i18n("LoadMission"), com.maddox.il2.builder.PlMission.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            _doDlgLoadMission();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    _doDlgLoadMission();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        _doDlgLoadMission();
                    }
                }


            }
;
            return;
        }
    }

    private void _doDlgLoadMission()
    {
        com.maddox.gwindow.GWindowFileOpen gwindowfileopen = new com.maddox.gwindow.GWindowFileOpen(builder.clientWindow.root, true, com.maddox.il2.builder.PlMission.i18n("LoadMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
            new GFileFilterName(com.maddox.il2.builder.PlMission.i18n("MissionFiles"), new java.lang.String[] {
                "*.mis"
            })
        }) {

            public void result(java.lang.String s)
            {
                if(s != null)
                {
                    missionFileName = s;
                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                    lastOpenFile = s;
                    doLoadMission("missions/" + s);
                }
            }

        }
;
        if(lastOpenFile != null)
            gwindowfileopen.setSelectFile(lastOpenFile);
    }

    public boolean exitBuilder()
    {
        if(!com.maddox.il2.builder.PlMission.isChanged())
        {
            return true;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(builder.clientWindow.root, 20F, true, com.maddox.il2.builder.PlMission.i18n("ConfirmExit"), com.maddox.il2.builder.PlMission.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    bChanged = false;
                                    com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        bChanged = false;
                        com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                    }
                }


            }
;
            return false;
        }
    }

    public void loadNewMap()
    {
        if(!bChanged)
        {
            missionFileName = null;
            ((com.maddox.gwindow.GWindowRootMenu)builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
            ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
            return;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(builder.clientWindow.root, 20F, true, com.maddox.il2.builder.PlMission.i18n("SaveMission"), com.maddox.il2.builder.PlMission.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    bChanged = false;
                                    ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        bChanged = false;
                        ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                    }
                    missionFileName = null;
                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                }


            }
;
            return;
        }
    }

    public void freeResources()
    {
        if(wConditions.isVisible())
            wConditions.hideWindow();
        if(!bReload)
        {
            missionFileName = null;
            ((com.maddox.gwindow.GWindowRootMenu)builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
        }
    }

    public PlMission()
    {
        missionArmy = 1;
        cloudType = 0;
        cloudHeight = 1000F;
        windDirection = 0.0F;
        windVelocity = 0.0F;
        gust = 0;
        turbulence = 0;
        day = 15;
        month = com.maddox.il2.ai.World.land().config.month;
        year = 1940;
        bChanged = false;
        bReload = false;
        cur = this;
    }

    private java.lang.String checkValidRange(int i, int j, int k)
    {
        if(i < j)
            i = j;
        else
        if(i > k)
            i = k;
        return java.lang.Integer.toString(i);
    }

    private java.lang.String checkValidRange(float f, float f1, float f2)
    {
        if(f < f1)
            f = f1;
        else
        if(f > f2)
            f = f2;
        return java.lang.Float.toString(f);
    }

    private java.lang.String checkValidRange(int i, int j, com.maddox.gwindow.GWindowLabel gwindowlabel)
    {
        int k = java.lang.Integer.parseInt(gwindowlabel.cap.caption);
        if(i < j)
            i = j;
        else
        if(i > k)
            i = k;
        return java.lang.Integer.toString(i);
    }

    private void zutiLoadMDSVariables(com.maddox.rts.SectFile sectfile)
    {
        try
        {
            com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE = false;
            if(sectfile.get("MDS", "MDS_Radar_SetRadarToAdvanceMode", 0, 0, 1) == 1)
                com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE = true;
            zutiRadar_ShipsAsRadar = false;
            if(sectfile.get("MDS", "MDS_Radar_ShipsAsRadar", 0, 0, 1) == 1)
                zutiRadar_ShipsAsRadar = true;
            zutiRadar_ShipRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ShipRadar_MaxRange", 100, 1, 0x1869f);
            zutiRadar_ShipRadar_MinHeight = sectfile.get("MDS", "MDS_Radar_ShipRadar_MinHeight", 100, 0, 0x1869f);
            zutiRadar_ShipRadar_MaxHeight = sectfile.get("MDS", "MDS_Radar_ShipRadar_MaxHeight", 5000, 1000, 0x1869f);
            zutiRadar_ShipSmallRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxRange", 25, 1, 0x1869f);
            zutiRadar_ShipSmallRadar_MinHeight = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MinHeight", 0, 0, 0x1869f);
            zutiRadar_ShipSmallRadar_MaxHeight = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxHeight", 2000, 1000, 0x1869f);
            zutiRadar_ScoutsAsRadar = false;
            if(sectfile.get("MDS", "MDS_Radar_ScoutsAsRadar", 0, 0, 1) == 1)
                zutiRadar_ScoutsAsRadar = true;
            zutiRadar_ScoutRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ScoutRadar_MaxRange", 2, 1, 0x1869f);
            zutiRadar_ScoutRadar_DeltaHeight = sectfile.get("MDS", "MDS_Radar_ScoutRadar_DeltaHeight", 1500, 100, 0x1869f);
            zutiRadar_ScoutCompleteRecon = false;
            if(sectfile.get("MDS", "MDS_Radar_ScoutCompleteRecon", 0, 0, 1) == 1)
                zutiRadar_ScoutCompleteRecon = true;
            zutiLoadScouts_Red(sectfile);
            zutiLoadScouts_Blue(sectfile);
            zutiRadar_ScoutGroundObjects_Alpha = sectfile.get("MDS", "MDS_Radar_ScoutGroundObjects_Alpha", 5, 1, 11);
            zutiRadar_RefreshInterval = sectfile.get("MDS", "MDS_Radar_RefreshInterval", 0, 0, 0x1869f);
            zutiRadar_DisableVectoring = false;
            if(sectfile.get("MDS", "MDS_Radar_DisableVectoring", 0, 0, 1) == 1)
                zutiRadar_DisableVectoring = true;
            zutiRadar_EnableTowerCommunications = true;
            if(sectfile.get("MDS", "MDS_Radar_EnableTowerCommunications", 1, 0, 1) == 0)
                zutiRadar_EnableTowerCommunications = false;
            zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
            if(sectfile.get("MDS", "MDS_Radar_HideUnpopulatedAirstripsFromMinimap", 0, 0, 1) == 1)
                zutiRadar_HideUnpopulatedAirstripsFromMinimap = true;
            zutiMisc_DisableAIRadioChatter = false;
            if(sectfile.get("MDS", "MDS_Misc_DisableAIRadioChatter", 0, 0, 1) == 1)
                zutiMisc_DisableAIRadioChatter = true;
            zutiMisc_DespawnAIPlanesAfterLanding = true;
            if(sectfile.get("MDS", "MDS_Misc_DespawnAIPlanesAfterLanding", 1, 0, 1) == 0)
                zutiMisc_DespawnAIPlanesAfterLanding = false;
            zutiMisc_HidePlayersCountOnHomeBase = false;
            if(sectfile.get("MDS", "MDS_Misc_HidePlayersCountOnHomeBase", 0, 0, 1) == 1)
                zutiMisc_HidePlayersCountOnHomeBase = true;
            zutiMisc_BombsCat1_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat1_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            zutiMisc_BombsCat2_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat2_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            zutiMisc_BombsCat3_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat3_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            respawnTime_Bigship = sectfile.get("RespawnTime", "Bigship", 1800, 0, 0x124f80);
            respawnTime_Ship = sectfile.get("RespawnTime", "Ship", 1800, 0, 0x124f80);
            respawnTime_Aeroanchored = sectfile.get("RespawnTime", "Aeroanchored", 1800, 0, 0x124f80);
            respawnTime_Artillery = sectfile.get("RespawnTime", "Artillery", 1800, 0, 0x124f80);
            respawnTime_Searchlight = sectfile.get("RespawnTime", "Searchlight", 1800, 0, 0x124f80);
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void zutiSaveMDSVariables(com.maddox.rts.SectFile sectfile)
    {
        try
        {
            int i = sectfile.sectionIndex("MDS");
            if(i < 0)
                i = sectfile.sectionAdd("MDS");
            sectfile.lineAdd(i, "MDS_Radar_SetRadarToAdvanceMode", BoolToInt(com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE));
            sectfile.lineAdd(i, "MDS_Radar_RefreshInterval", (new Integer(zutiRadar_RefreshInterval)).toString());
            sectfile.lineAdd(i, "MDS_Radar_DisableVectoring", BoolToInt(zutiRadar_DisableVectoring));
            sectfile.lineAdd(i, "MDS_Radar_EnableTowerCommunications", BoolToInt(zutiRadar_EnableTowerCommunications));
            sectfile.lineAdd(i, "MDS_Radar_ShipsAsRadar", BoolToInt(zutiRadar_ShipsAsRadar));
            sectfile.lineAdd(i, "MDS_Radar_ShipRadar_MaxRange", (new Integer(zutiRadar_ShipRadar_MaxRange)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ShipRadar_MinHeight", (new Integer(zutiRadar_ShipRadar_MinHeight)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ShipRadar_MaxHeight", (new Integer(zutiRadar_ShipRadar_MaxHeight)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MaxRange", (new Integer(zutiRadar_ShipSmallRadar_MaxRange)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MinHeight", (new Integer(zutiRadar_ShipSmallRadar_MinHeight)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MaxHeight", (new Integer(zutiRadar_ShipSmallRadar_MaxHeight)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ScoutsAsRadar", BoolToInt(zutiRadar_ScoutsAsRadar));
            sectfile.lineAdd(i, "MDS_Radar_ScoutRadar_MaxRange", (new Integer(zutiRadar_ScoutRadar_MaxRange)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ScoutRadar_DeltaHeight", (new Integer(zutiRadar_ScoutRadar_DeltaHeight)).toString());
            sectfile.lineAdd(i, "MDS_Radar_HideUnpopulatedAirstripsFromMinimap", BoolToInt(zutiRadar_HideUnpopulatedAirstripsFromMinimap));
            sectfile.lineAdd(i, "MDS_Radar_ScoutGroundObjects_Alpha", (new Integer(zutiRadar_ScoutGroundObjects_Alpha)).toString());
            sectfile.lineAdd(i, "MDS_Radar_ScoutCompleteRecon", BoolToInt(zutiRadar_ScoutCompleteRecon));
            zutiSaveScouts_Red(sectfile);
            zutiSaveScouts_Blue(sectfile);
            sectfile.lineAdd(i, "MDS_Misc_DisableAIRadioChatter", BoolToInt(zutiMisc_DisableAIRadioChatter));
            sectfile.lineAdd(i, "MDS_Misc_DespawnAIPlanesAfterLanding", BoolToInt(zutiMisc_DespawnAIPlanesAfterLanding));
            sectfile.lineAdd(i, "MDS_Misc_HidePlayersCountOnHomeBase", BoolToInt(zutiMisc_HidePlayersCountOnHomeBase));
            sectfile.lineAdd(i, "MDS_Misc_BombsCat1_CratersVisibilityMultiplier", (new Float(zutiMisc_BombsCat1_CratersVisibilityMultiplier)).toString());
            sectfile.lineAdd(i, "MDS_Misc_BombsCat2_CratersVisibilityMultiplier", (new Float(zutiMisc_BombsCat2_CratersVisibilityMultiplier)).toString());
            sectfile.lineAdd(i, "MDS_Misc_BombsCat3_CratersVisibilityMultiplier", (new Float(zutiMisc_BombsCat3_CratersVisibilityMultiplier)).toString());
            i = sectfile.sectionAdd("RespawnTime");
            sectfile.lineAdd(i, "Bigship", (new Integer(respawnTime_Bigship)).toString());
            sectfile.lineAdd(i, "Ship", (new Integer(respawnTime_Ship)).toString());
            sectfile.lineAdd(i, "Aeroanchored", (new Integer(respawnTime_Aeroanchored)).toString());
            sectfile.lineAdd(i, "Artillery", (new Integer(respawnTime_Artillery)).toString());
            sectfile.lineAdd(i, "Searchlight", (new Integer(respawnTime_Searchlight)).toString());
        }
        catch(java.lang.Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void zutiInitMDSVariables()
    {
        if(com.maddox.il2.gui.GUI.pad != null)
            com.maddox.il2.gui.GUI.pad.zutiColorAirfields = true;
        zutiRadar_ShipsAsRadar = false;
        zutiRadar_ShipRadar_MaxRange = 100;
        zutiRadar_ShipRadar_MinHeight = 100;
        zutiRadar_ShipRadar_MaxHeight = 5000;
        zutiRadar_ShipSmallRadar_MaxRange = 25;
        zutiRadar_ShipSmallRadar_MinHeight = 0;
        zutiRadar_ShipSmallRadar_MaxHeight = 2000;
        zutiRadar_ScoutsAsRadar = false;
        zutiRadar_ScoutRadar_MaxRange = 2;
        zutiRadar_ScoutRadar_DeltaHeight = 1500;
        zutiRadar_ScoutRadarType_Red = "";
        zutiRadar_ScoutRadarType_Blue = "";
        zutiRadar_RefreshInterval = 0;
        zutiRadar_DisableVectoring = false;
        zutiRadar_EnableTowerCommunications = true;
        zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
        com.maddox.il2.game.Mission.ZUTI_RADAR_IN_ADV_MODE = false;
        zutiRadar_ScoutGroundObjects_Alpha = 5;
        zutiRadar_ScoutCompleteRecon = false;
        zutiMisc_DisableAIRadioChatter = false;
        zutiMisc_DespawnAIPlanesAfterLanding = true;
        zutiMisc_HidePlayersCountOnHomeBase = false;
        zutiMisc_BombsCat1_CratersVisibilityMultiplier = 1.0F;
        zutiMisc_BombsCat2_CratersVisibilityMultiplier = 1.0F;
        zutiMisc_BombsCat3_CratersVisibilityMultiplier = 1.0F;
        respawnTime_Bigship = 1800;
        respawnTime_Ship = 1800;
        respawnTime_Aeroanchored = 1800;
        respawnTime_Artillery = 1800;
        respawnTime_Searchlight = 1800;
    }

    private java.lang.String BoolToInt(boolean flag)
    {
        if(flag)
            return "1";
        else
            return "0";
    }

    private void zutiSaveScouts_Red(com.maddox.rts.SectFile sectfile)
    {
        if(zutiRadar_ScoutRadarType_Red != null && zutiRadar_ScoutRadarType_Red.trim().length() > 0)
        {
            int i = sectfile.sectionAdd("MDS_Scouts_Red");
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(zutiRadar_ScoutRadarType_Red); stringtokenizer.hasMoreTokens(); sectfile.lineAdd(i, stringtokenizer.nextToken()));
        }
    }

    private void zutiSaveScouts_Blue(com.maddox.rts.SectFile sectfile)
    {
        if(zutiRadar_ScoutRadarType_Blue != null && zutiRadar_ScoutRadarType_Blue.trim().length() > 0)
        {
            int i = sectfile.sectionAdd("MDS_Scouts_Blue");
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(zutiRadar_ScoutRadarType_Blue); stringtokenizer.hasMoreTokens(); sectfile.lineAdd(i, stringtokenizer.nextToken()));
        }
    }

    private void zutiLoadScouts_Red(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("MDS_Scouts_Red");
        if(i > -1)
        {
            zutiRadar_ScoutRadarType_Red = "";
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectfile.line(i, k);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                java.lang.String s1 = null;
                if(stringtokenizer.hasMoreTokens())
                    s1 = stringtokenizer.nextToken();
                if(s1 == null)
                    continue;
                s1 = s1.intern();
                java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s1, "airClass", null);
                if(class1 != null && com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    zutiRadar_ScoutRadarType_Red += s1 + " ";
            }

        }
    }

    private void zutiLoadScouts_Blue(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("MDS_Scouts_Blue");
        if(i > -1)
        {
            zutiRadar_ScoutRadarType_Blue = "";
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectfile.line(i, k);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                java.lang.String s1 = null;
                if(stringtokenizer.hasMoreTokens())
                    s1 = stringtokenizer.nextToken();
                if(s1 == null)
                    continue;
                s1 = s1.intern();
                java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s1, "airClass", null);
                if(class1 != null && com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    zutiRadar_ScoutRadarType_Blue += s1 + " ";
            }

        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected static com.maddox.il2.builder.PlMission cur;
    protected int missionArmy;
    private int cloudType;
    private float cloudHeight;
    private float windDirection;
    private float windVelocity;
    private int gust;
    private int turbulence;
    private int day;
    private int month;
    private int year;
    private java.lang.String _yearKey[] = {
        "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", 
        "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", 
        "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", 
        "1960"
    };
    private java.lang.String _dayKey[] = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", 
        "31"
    };
    private java.lang.String _monthKey[] = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
        "11", "12"
    };
    private static final int RADAR_SHIPRADAR_MAXRANGE_MIN = 1;
    private static final int RADAR_SHIPRADAR_MAXRANGE_MAX = 0x1869f;
    private static final int RADAR_SHIPRADAR_MINHEIGHT_MIN = 0;
    private static final int RADAR_SHIPRADAR_MINHEIGHT_MAX = 0x1869f;
    private static final int RADAR_SHIPRADAR_MAXHEIGHT_MIN = 1000;
    private static final int RADAR_SHIPRADAR_MAXHEIGHT_MAX = 0x1869f;
    private static final int RADAR_SHIPSMALLRADAR_MAXRANGE_MIN = 1;
    private static final int RADAR_SHIPSMALLRADAR_MAXRANGE_MAX = 0x1869f;
    private static final int RADAR_SHIPSMALLRADAR_MINHEIGHT_MIN = 0;
    private static final int RADAR_SHIPSMALLRADAR_MINHEIGHT_MAX = 0x1869f;
    private static final int RADAR_SHIPSMALLRADAR_MAXHEIGHT_MIN = 1000;
    private static final int RADAR_SHIPSMALLRADAR_MAXHEIGHT_MAX = 0x1869f;
    private static final int RADAR_SCOUTRADAR_MAXRANGE_MIN = 1;
    private static final int RADAR_SCOUTRADAR_MAXRANGE_MAX = 0x1869f;
    private static final int RADAR_SCOUTRADAR_DELTAHEIGHT_MIN = 100;
    private static final int RADAR_SCOUTRADAR_DELTAHEIGHT_MAX = 0x1869f;
    private static final int RADAR_SCOUTGROUNDOBJECT_ALPHA_MIN = 1;
    private static final int RADAR_SCOUTGROUNDOBJECT_ALPHA_MAX = 11;
    private static final int RADAR_REFRESHINTERVAL_MIN = 0;
    private static final int RADAR_REFRESHINTERVAL_MAX = 0x1869f;
    private static final float MISC_BOMBSCAT1_CRATERSVISIBILITYMULTIPLIER_MIN = 1F;
    private static final float MISC_BOMBSCAT1_CRATERSVISIBILITYMULTIPLIER_MAX = 99999F;
    private static final float MISC_BOMBSCAT2_CRATERSVISIBILITYMULTIPLIER_MIN = 1F;
    private static final float MISC_BOMBSCAT2_CRATERSVISIBILITYMULTIPLIER_MAX = 99999F;
    private static final float MISC_BOMBSCAT3_CRATERSVISIBILITYMULTIPLIER_MIN = 1F;
    private static final float MISC_BOMBSCAT3_CRATERSVISIBILITYMULTIPLIER_MAX = 99999F;
    private static final int RT_BIGSHIP_MIN = 0;
    private static final int RT_BIGSHIP_MAX = 0x124f80;
    private static final int RT_SHIP_MIN = 0;
    private static final int RT_SHIP_MAX = 0x124f80;
    private static final int RT_AEROANCHORED_MIN = 0;
    private static final int RT_AEROANCHORED_MAX = 0x124f80;
    private static final int RT_ARTILLERY_MIN = 0;
    private static final int RT_ARTILLERY_MAX = 0x124f80;
    private static final int RT_SEARCHLIGHT_MIN = 0;
    private static final int RT_SEARCHLIGHT_MAX = 0x124f80;
    private boolean bChanged;
    private java.lang.String missionFileName;
    private boolean bReload;
    com.maddox.il2.builder.PlMapLoad pluginMapLoad;
    com.maddox.il2.builder.WConditions wConditions;
    com.maddox.gwindow.GWindowMenuItem mConditions;
    com.maddox.il2.builder.WFoW wFoW;
    com.maddox.gwindow.GWindowMenuItem mFoW;
    com.maddox.il2.builder.WCraters wCraters;
    com.maddox.gwindow.GWindowMenuItem mCraters;
    com.maddox.il2.builder.WRespawnTime wRespawnTime;
    com.maddox.gwindow.GWindowMenuItem mRespawnTime;
    com.maddox.il2.builder.WMisc wMisc;
    com.maddox.gwindow.GWindowMenuItem mMisc;
    com.maddox.gwindow.GWindowMenuItem viewBridge;
    com.maddox.gwindow.GWindowMenuItem viewRunaway;
    com.maddox.gwindow.GWindowMenuItem viewName;
    com.maddox.gwindow.GWindowMenuItem viewTime;
    com.maddox.gwindow.GWindowMenuItem viewArmy[];
    private java.lang.String lastOpenFile;
    private com.maddox.gwindow.GWindowMessageBox _loadMessageBox;
    private java.lang.String _loadFileName;
    private int zutiRadar_RefreshInterval;
    private boolean zutiRadar_DisableVectoring;
    private boolean zutiRadar_EnableTowerCommunications;
    private boolean zutiRadar_HideUnpopulatedAirstripsFromMinimap;
    private boolean zutiRadar_ShipsAsRadar;
    private int zutiRadar_ShipRadar_MaxRange;
    private int zutiRadar_ShipRadar_MinHeight;
    private int zutiRadar_ShipRadar_MaxHeight;
    private int zutiRadar_ShipSmallRadar_MaxRange;
    private int zutiRadar_ShipSmallRadar_MinHeight;
    private int zutiRadar_ShipSmallRadar_MaxHeight;
    private boolean zutiRadar_ScoutsAsRadar;
    private int zutiRadar_ScoutRadar_MaxRange;
    private int zutiRadar_ScoutRadar_DeltaHeight;
    private java.lang.String zutiRadar_ScoutRadarType_Red;
    private java.lang.String zutiRadar_ScoutRadarType_Blue;
    private int zutiRadar_ScoutGroundObjects_Alpha;
    private boolean zutiRadar_ScoutCompleteRecon;
    private boolean zutiMisc_DisableAIRadioChatter;
    private boolean zutiMisc_DespawnAIPlanesAfterLanding;
    private boolean zutiMisc_HidePlayersCountOnHomeBase;
    private float zutiMisc_BombsCat1_CratersVisibilityMultiplier;
    private float zutiMisc_BombsCat2_CratersVisibilityMultiplier;
    private float zutiMisc_BombsCat3_CratersVisibilityMultiplier;
    private int respawnTime_Bigship;
    private int respawnTime_Ship;
    private int respawnTime_Aeroanchored;
    private int respawnTime_Artillery;
    private int respawnTime_Searchlight;
    private com.maddox.il2.builder.Zuti_WManageAircrafts zuti_manageAircrafts;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMission.class, "name", "Mission");
    }
































































































}
