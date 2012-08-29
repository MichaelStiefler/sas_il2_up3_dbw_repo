// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetupInput.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.gwindow.GWindowVSliderInt;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.JoyFF;
import com.maddox.rts.LDRres;
import com.maddox.rts.Mouse;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUISwitchBox3, GUIDialogClient, GUIControls, 
//            GUISeparate

public class GUISetupInput extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bControlPanel)
            {
                if(wJoyProfile.getSelected() > 0)
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick" + wJoyProfile.getSelected());
                else
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick");
                com.maddox.il2.game.Main.stateStack().push(20);
                com.maddox.il2.gui.GUIControls guicontrols = (com.maddox.il2.gui.GUIControls)com.maddox.il2.game.Main.stateStack().peek();
                guicontrols.scrollClient.vScroll.setPos(guicontrols.scrollClient.vScroll.posMax, true);
                return true;
            }
            if(gwindow == sMirrorControl)
            {
                if(js[12] == 0)
                {
                    if((double)comboAxeReversed[comboAxe.getSelected()] == -1D)
                        js[12] = 2;
                    else
                        js[12] = 1;
                } else
                {
                    js[12] = 0;
                }
                setJoyS(12, js[12]);
                setPosSize();
                return true;
            }
            if(gwindow == bSave)
            {
                com.maddox.rts.Mouse.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_mouse");
                if(sForceFeedback != null)
                    if(sForceFeedback.isChecked())
                    {
                        if(!com.maddox.rts.JoyFF.isStarted())
                        {
                            com.maddox.rts.JoyFF.setEnable(true);
                            com.maddox.il2.objects.effects.ForceFeedback.start();
                            if(com.maddox.il2.game.Mission.isPlaying())
                                com.maddox.il2.objects.effects.ForceFeedback.startMission();
                        }
                    } else
                    if(com.maddox.rts.JoyFF.isStarted())
                    {
                        com.maddox.il2.objects.effects.ForceFeedback.stop();
                        com.maddox.rts.JoyFF.setEnable(false);
                    }
                if(wJoyProfile.getSelected() > 0)
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick" + wJoyProfile.getSelected());
                else
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick");
                return true;
            }
            if(gwindow == bLoad)
            {
                if(wJoyProfile.getSelected() > 0)
                    com.maddox.rts.Joy.adapter().loadConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick" + wJoyProfile.getSelected());
                else
                    com.maddox.rts.Joy.adapter().loadConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick");
                fillDialogs();
                return true;
            }
            if(gwindow == comboAxe)
            {
                fillSliders(curAxe());
                return true;
            }
            if(gwindow == slider[0])
            {
                setJoyS(1, slider[0].pos());
                return true;
            }
            if(gwindow == slider[1])
            {
                setJoyS(2, slider[1].pos());
                return true;
            }
            if(gwindow == slider[2])
            {
                setJoyS(3, slider[2].pos());
                return true;
            }
            if(gwindow == slider[3])
            {
                setJoyS(4, slider[3].pos());
                return true;
            }
            if(gwindow == slider[4])
            {
                setJoyS(5, slider[4].pos());
                return true;
            }
            if(gwindow == slider[5])
            {
                setJoyS(6, slider[5].pos());
                return true;
            }
            if(gwindow == slider[6])
            {
                setJoyS(7, slider[6].pos());
                return true;
            }
            if(gwindow == slider[7])
            {
                setJoyS(8, slider[7].pos());
                return true;
            }
            if(gwindow == slider[8])
            {
                setJoyS(9, slider[8].pos());
                return true;
            }
            if(gwindow == slider[9])
            {
                setJoyS(10, slider[9].pos());
                return true;
            }
            if(gwindow == deadSlider)
            {
                setJoyS(0, deadSlider.pos());
                return true;
            }
            if(gwindow == filterSlider)
            {
                setJoyS(11, (100 * filterSlider.pos()) / 10);
                return true;
            }
            if(gwindow == edit[0])
            {
                setJoyS(1, clampValue(edit[0], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[1])
            {
                setJoyS(2, clampValue(edit[1], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[2])
            {
                setJoyS(3, clampValue(edit[2], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[3])
            {
                setJoyS(4, clampValue(edit[3], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[4])
            {
                setJoyS(5, clampValue(edit[4], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[5])
            {
                setJoyS(6, clampValue(edit[5], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[6])
            {
                setJoyS(7, clampValue(edit[6], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[7])
            {
                setJoyS(8, clampValue(edit[7], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[8])
            {
                setJoyS(9, clampValue(edit[8], 0, 0, 100));
                return true;
            }
            if(gwindow == edit[9])
            {
                setJoyS(10, clampValue(edit[9], 0, 0, 100));
                return true;
            }
            if(gwindow == wMouse)
            {
                float f = clampValue(wMouse, 1.0F, 0.1F, 10F);
                float af[] = com.maddox.rts.Mouse.adapter().getSensitivity();
                af[0] = af[1] = f;
                wMouse.setValue("" + f, false);
                return true;
            }
            if(gwindow == bDefault)
            {
                doSetDefault();
                return true;
            }
            if(gwindow == bBack)
            {
                com.maddox.rts.Mouse.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_mouse");
                if(sForceFeedback != null)
                    if(sForceFeedback.isChecked())
                    {
                        if(!com.maddox.rts.JoyFF.isStarted())
                        {
                            com.maddox.rts.JoyFF.setEnable(true);
                            com.maddox.il2.objects.effects.ForceFeedback.start();
                            if(com.maddox.il2.game.Mission.isPlaying())
                                com.maddox.il2.objects.effects.ForceFeedback.startMission();
                        }
                    } else
                    if(com.maddox.rts.JoyFF.isStarted())
                    {
                        com.maddox.il2.objects.effects.ForceFeedback.stop();
                        com.maddox.rts.JoyFF.setEnable(false);
                    }
                com.maddox.il2.engine.Config.cur.ini.set("rts", "JoyProfile", wJoyProfile.getSelected());
                if(wJoyProfile.getSelected() > 0)
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick" + wJoyProfile.getSelected());
                else
                    com.maddox.rts.Joy.adapter().saveConfig(com.maddox.il2.engine.Config.cur.ini, "rts_joystick");
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(256F), y1024(48F), x1024(160F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(490F), x1024(528F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(576F), x1024(832F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(48F), 2.0F, y1024(444F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(48F), y1024(32F), x1024(192F), y1024(32F), 2, i18n("setupIn.JoystickAxes"));
            draw(x1024(432F), y1024(32F), x1024(208F), y1024(32F), 0, i18n("setupIn.Profile"));
            draw(x1024(384F), y1024(80F), x1024(400F), y1024(32F), 0, comboAxe.getValue());
            draw(x1024(368F), y1024(364F), x1024(208F), y1024(32F), 2, i18n("setupIn.DeadBand"));
            draw(x1024(576F), y1024(364F), x1024(48F), y1024(32F), 1, "0");
            draw(x1024(832F), y1024(364F), x1024(48F), y1024(32F), 1, "50");
            draw(x1024(368F), y1024(402F), x1024(208F), y1024(32F), 2, i18n("setupIn.Filtering"));
            draw(x1024(576F), y1024(402F), x1024(48F), y1024(32F), 1, "0");
            draw(x1024(832F), y1024(402F), x1024(48F), y1024(32F), 1, "1");
            if(sForceFeedback != null)
                draw(x1024(136F), y1024(520F), x1024(208F), y1024(32F), 0, i18n("setupIn.ForceFeedback"));
            draw(x1024(416F), y1024(520F), x1024(336F), y1024(32F), 2, i18n("setupIn.MouseSensitivity"));
            draw(x1024(96F), y1024(608F), x1024(128F), y1024(48F), 0, i18n("setupIn.Back"));
            draw(x1024(304F), y1024(608F), x1024(160F), y1024(48F), 0, i18n("setupIn.Default"));
            draw(x1024(528F), y1024(608F), x1024(272F), y1024(48F), 2, i18n("setupIn.ControlPanel"));
            draw(x1024(654F), y1024(62F), x1024(128F), y1024(48F), 1, i18n("setupIn.Load"));
            draw(x1024(754F), y1024(62F), x1024(128F), y1024(48F), 1, i18n("setupIn.Save"));
            draw(x1024(368F), y1024(444F), x1024(208F), y1024(32F), 2, i18n("setupIn.Symmetrical"));
            boolean flag = true;
            boolean flag1 = true;
            boolean flag2 = true;
            if(comboAxeREA[1] == -1 || comboAxeREA[2] == -1)
                flag1 = false;
            if(comboAxeREA[0] == -1)
                flag = false;
            if(comboAxe.getValue().equals(i18n("setupIn.none")) && !flag && !flag1)
                return;
            for(int i = 0; i < 3; i++)
                if(comboAxe.getSelected() == comboAxeREA[i])
                    flag2 = false;

            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            com.maddox.gwindow.GBevel gbevel = guilookandfeel.bevelComboDown;
            if(flag2)
            {
                float f = gbevel.L.dx;
                float f1 = x1024(32F);
                float f2 = y1024(208F) / 2.0F - f;
                float f3 = y1024(16F);
                int j1 = curAxe();
                int k1 = (j1 & 0xffff) - 531 - 49;
                int l1 = (j1 >> 16) - 531 - 32;
                com.maddox.rts.Joy.adapter().getNotFilteredPos(k1, joyRawPos);
                com.maddox.rts.Joy.adapter().getPos(k1, joyPos);
                float f7 = com.maddox.rts.Joy.normal(joyRawPos[l1]);
                float f9 = com.maddox.rts.Joy.normal(joyPos[l1]);
                setCanvasColorWHITE();
                guilookandfeel.drawBevel(this, x1024(272F), y1024(240F), f1, y1024(208F), gbevel, guilookandfeel.basicelements);
                if(js[12] == 0)
                {
                    f2 -= f3 / 2.0F;
                    if((double)comboAxeReversed[comboAxe.getSelected()] == -1D)
                    {
                        f7 = -f7;
                        f9 = -f9;
                    }
                    com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(272F) + 2.0F * f, y1024(344F), x1024(32F) - 4F * f, 1.0F);
                    com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, x1024(272F) + 2.0F * f, y1024(448F) - (f7 + 1.0F) * f2 - f - f3, f1 - 4F * f, f3);
                    com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, x1024(272F) + 2.0F * f, y1024(448F) - (f9 + 1.0F) * f2 - f - f3, f1 - 4F * f, f3);
                } else
                {
                    if((double)comboAxeReversed[comboAxe.getSelected()] == -1D)
                    {
                        f7 = -f7;
                        f9 = -f9;
                    }
                    com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, x1024(272F) + f, y1024(448F) - (f7 + 1.0F) * f2 - f, x1024(32F) - 2.0F * f, (f7 + 1.0F) * f2);
                    com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, x1024(272F) + 2.0F * f, y1024(448F) - (f9 + 1.0F) * f2 - f, x1024(32F) - 4F * f, (f9 + 1.0F) * f2);
                }
            }
            if(!flag1)
                return;
            int j = (comboAxeInts[comboAxeREA[1]] & 0xffff) - 531 - 49;
            int k = (comboAxeInts[comboAxeREA[1]] >> 16) - 531 - 32;
            int l = (comboAxeInts[comboAxeREA[2]] & 0xffff) - 531 - 49;
            int i1 = (comboAxeInts[comboAxeREA[2]] >> 16) - 531 - 32;
            com.maddox.rts.Joy.adapter().getNotFilteredPos(j, joyRawPos);
            com.maddox.rts.Joy.adapter().getPos(j, joyPos);
            float f4 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[1]] * joyPos[k]);
            float f5 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[1]] * joyRawPos[k]);
            com.maddox.rts.Joy.adapter().getNotFilteredPos(l, joyRawPos);
            com.maddox.rts.Joy.adapter().getPos(l, joyPos);
            float f6 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[2]] * joyPos[i1]);
            float f8 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[2]] * joyRawPos[i1]);
            float f10 = gbevel.L.dx;
            float f11 = x1024(16F);
            setCanvasColorWHITE();
            guilookandfeel.drawBevel(this, x1024(64F), y1024(240F), x1024(208F), y1024(208F), gbevel, guilookandfeel.basicelements);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(64F) + 2.0F * f10, y1024(344F), x1024(208F) - 4F * f10, 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(168F), y1024(240F) + 2.0F * f10, 1.0F, y1024(208F) - 4F * f10);
            float f12 = (x1024(208F) - f11) / 2.0F - f10;
            float f13 = x1024(168F) + f8 * f12;
            float f14 = y1024(344F) + f5 * f12;
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, f13 - f11 / 2.0F, f14 - f11 / 2.0F, f11, f11);
            f13 = x1024(168F) + f6 * f12;
            f14 = y1024(344F) + f4 * f12;
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, f13 - f11 / 2.0F, f14 - f11 / 2.0F, f11, f11);
            if(!flag)
            {
                return;
            } else
            {
                int i2 = (comboAxeInts[comboAxeREA[0]] & 0xffff) - 531 - 49;
                int j2 = (comboAxeInts[comboAxeREA[0]] >> 16) - 531 - 32;
                com.maddox.rts.Joy.adapter().getNotFilteredPos(i2, joyRawPos);
                com.maddox.rts.Joy.adapter().getPos(i2, joyPos);
                float f15 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[0]] * joyRawPos[j2]);
                float f16 = com.maddox.rts.Joy.normal(comboAxeReversed[comboAxeREA[0]] * joyPos[j2]);
                setCanvasColorWHITE();
                guilookandfeel.drawBevel(this, x1024(64F), y1024(448F), x1024(208F), y1024(32F), gbevel, guilookandfeel.basicelements);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(168F), y1024(448F) + 2.0F * f10, 1.0F, y1024(32F) - 4F * f10);
                float f17 = x1024(168F) + f15 * f12;
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, f17 - f11 / 2.0F, y1024(448F) + 2.0F * f10, f11, y1024(32F) - 4F * f10);
                f17 = x1024(168F) + f16 * f12;
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, f17 - f11 / 2.0F, y1024(448F) + 2.0F * f10, f11, y1024(32F) - 4F * f10);
                return;
            }
        }

        public void setPosSize()
        {
            set1024PosSize(64F, 48F, 896F, 688F);
            comboAxe.set1024PosSize(32F, 80F, 224F, 32F);
            float f = lookAndFeel().getVSliderIntW();
            for(int i = 0; i < 10; i++)
            {
                slider[i].setPosSize(x1024(384 + i * 48) + (x1024(48F) - f) / 2.0F, y1024(128F), f, y1024(176F));
                edit[i].set1024PosSize(384 + i * 48, 320F, 48F, 32F);
            }

            wJoyProfile.setPosSize(x1024(500F), y1024(32F), x1024(160F), M(1.7F));
            bLoad.setPosC(x1024(720F), y1024(50F));
            bSave.setPosC(x1024(820F), y1024(50F));
            sMirrorControl.setPosC(x1024(672F), y1024(460F));
            float f1 = lookAndFeel().getHSliderIntH();
            deadSlider.setPosSize(x1024(624F), y1024(364F) + (y1024(32F) - f1) / 2.0F, x1024(208F), f1);
            filterSlider.setPosSize(x1024(624F), y1024(402F) + (y1024(32F) - f1) / 2.0F, x1024(208F), f1);
            wMouse.set1024PosSize(768F, 520F, 96F, 32F);
            bBack.setPosC(x1024(56F), y1024(632F));
            bDefault.setPosC(x1024(264F), y1024(632F));
            bControlPanel.setPosC(x1024(840F), y1024(632F));
            if(sForceFeedback != null)
                sForceFeedback.setPosC(x1024(96F), y1024(532F));
        }

        private int joyPos[];
        private int joyRawPos[];

        public DialogClient()
        {
            joyPos = new int[8];
            joyRawPos = new int[8];
        }
    }


    public void _enter()
    {
        fillDialogs();
        if(sForceFeedback != null)
            sForceFeedback.setChecked(com.maddox.rts.JoyFF.isStarted(), false);
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void fillDialogs()
    {
        for(int i = 0; i < 3; i++)
            comboAxeREA[i] = -1;

        for(int j = 0; j < 32; j++)
            comboAxeReversed[j] = 1;

        int k = 0;
        int l = comboAxe.getSelected();
        comboAxe.clear(false);
        java.lang.String as[] = com.maddox.il2.ai.UserCfg.nameHotKeyEnvs;
        for(int i1 = 0; i1 < as.length; i1++)
        {
            if(!as[i1].equals("move"))
                continue;
            com.maddox.rts.HotKeyEnv hotkeyenv = com.maddox.rts.HotKeyEnv.env(as[i1]);
            com.maddox.util.HashMapInt hashmapint = hotkeyenv.all();
            for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
            {
                comboAxeInts[k] = hashmapintentry.getKey();
                java.lang.String s = (java.lang.String)hashmapintentry.getValue();
                if(s.startsWith("-"))
                {
                    s = s.substring(1);
                    comboAxeReversed[k] = -1;
                }
                java.lang.String s1;
                try
                {
                    s1 = java.util.ResourceBundle.getBundle("i18n/controls", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader()).getString(s);
                }
                catch(java.lang.Exception exception)
                {
                    s1 = s;
                    java.lang.System.out.println("Warning: Control" + s + "is not present in i18n/controls.properties file");
                }
                int l1 = (comboAxeInts[k] & 0xffff) - 531 - 49;
                int i2 = (comboAxeInts[k] >> 16) - 531 - 32;
                if(com.maddox.rts.VK.getKeyText(comboAxeInts[k] >> 16 & 0xffff).startsWith("Joystick"))
                {
                    comboAxeInts[k] = (comboAxeInts[k] >> 16) + ((comboAxeInts[k] & 0xffff) << 16);
                    l1 = (comboAxeInts[k] & 0xffff) - 531 - 49;
                    i2 = (comboAxeInts[k] >> 16) - 531 - 32;
                }
                if(!com.maddox.rts.Joy.adapter().isExistAxe(l1, i2))
                    continue;
                comboAxe.add(s1);
                if(s.equals("rudder"))
                    comboAxeREA[0] = k;
                else
                if(s.equals("elevator"))
                    comboAxeREA[1] = k;
                else
                if(s.equals("aileron"))
                    comboAxeREA[2] = k;
                k++;
            }

        }

        if(k == 0)
        {
            sMirrorControl.setEnable(false);
            for(int j1 = 0; j1 < 9; j1++)
            {
                slider[j1].setEnable(false);
                edit[j1].setEnable(false);
            }

            deadSlider.setEnable(false);
            filterSlider.setEnable(false);
            comboAxe.add(i18n("setupIn.none"));
            comboAxe.setSelected(0, true, false);
            comboAxe.setEnable(false);
        } else
        {
            sMirrorControl.setEnable(true);
            for(int k1 = 0; k1 < 9; k1++)
            {
                slider[k1].setEnable(true);
                edit[k1].setEnable(true);
            }

            deadSlider.setEnable(true);
            filterSlider.setEnable(true);
            comboAxe.setEnable(true);
            if(l < 0 || l >= k)
                l = 0;
            comboAxe.setSelected(-1, false, false);
            comboAxe.setSelected(l, true, true);
        }
        float af[] = com.maddox.rts.Mouse.adapter().getSensitivity();
        wMouse.setValue("" + af[0], false);
    }

    private int curAxe()
    {
        int i = comboAxe.getSelected();
        int j = comboAxeInts[i];
        if(i < 0)
            return -1;
        else
            return j;
    }

    private void fillSliders(int i)
    {
        if(i < 0)
            return;
        int j = (i & 0xffff) - 531 - 49;
        int k = (i >> 16) - 531 - 32;
        com.maddox.rts.Joy.adapter().getSensitivity(j, k, js);
        if(js[12] == 0)
            sMirrorControl.setChecked(true, false);
        else
            sMirrorControl.setChecked(false, false);
        if(js[0] < 0)
            js[0] = 0;
        if(js[0] > 50)
            js[0] = 50;
        deadSlider.setPos(js[0], false);
        filterSlider.setPos((10 * js[11]) / 100, false);
        for(int l = 0; l < 10; l++)
        {
            if(js[l + 1] < 0)
                js[l + 1] = 0;
            if(js[l + 1] > 100)
                js[l + 1] = 100;
            slider[l].setPos(js[l + 1], false);
            edit[l].setValue("" + js[l + 1], false);
        }

    }

    private void doSetDefault()
    {
        js[0] = 0;
        for(int i = 1; i < 11; i++)
            js[i] = i * 10;

        js[11] = 0;
        js[12] = 0;
        for(int j = 0; j < 4; j++)
        {
            for(int k = 0; k < 8; k++)
                if(com.maddox.rts.Joy.adapter().isExistAxe(j, k))
                    com.maddox.rts.Joy.adapter().setSensitivity(j, k, js);

        }

        float af[] = com.maddox.rts.Mouse.adapter().getSensitivity();
        af[0] = af[1] = 1.0F;
        fillDialogs();
    }

    private void setJoyS(int i, int j)
    {
        int k = curAxe();
        int l = (k & 0xffff) - 531 - 49;
        int i1 = (k >> 16) - 531 - 32;
        if(k < 0)
        {
            return;
        } else
        {
            com.maddox.rts.Joy.adapter().getSensitivity(l, i1, js);
            js[i] = j;
            com.maddox.rts.Joy.adapter().setSensitivity(l, i1, js);
            fillSliders(k);
            return;
        }
    }

    private float clampValue(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol, float f, float f1, float f2)
    {
        java.lang.String s = gwindoweditcontrol.getValue();
        try
        {
            f = java.lang.Float.parseFloat(s);
        }
        catch(java.lang.Exception exception) { }
        if(f < f1)
            f = f1;
        if(f > f2)
            f = f2;
        gwindoweditcontrol.setValue("" + f, false);
        return f;
    }

    private int clampValue(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol, int i, int j, int k)
    {
        java.lang.String s = gwindoweditcontrol.getValue();
        try
        {
            i = java.lang.Integer.parseInt(s);
        }
        catch(java.lang.Exception exception) { }
        if(i < j)
            i = j;
        if(i > k)
            i = k;
        gwindoweditcontrol.setValue("" + i, false);
        return i;
    }

    public GUISetupInput(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(53);
        slider = new com.maddox.gwindow.GWindowVSliderInt[10];
        edit = new com.maddox.gwindow.GWindowEditControl[10];
        js = new int[13];
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setupIn.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        dialogClient.addControl(comboAxe = new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        wJoyProfile = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        bLoad = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSave = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        sMirrorControl = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        comboAxe.setEditable(false);
        comboAxe.resized();
        for(int i = 0; i < 10; i++)
        {
            dialogClient.addControl(slider[i] = new GWindowVSliderInt(dialogClient));
            slider[i].setRange(0, 101, 10 * (i + 1));
            edit[i] = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new com.maddox.gwindow.GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {

                public void keyboardKey(int j, boolean flag)
                {
                    super.keyboardKey(j, flag);
                    if(j == 10 && flag)
                        notify(2, 0);
                }

            }
);
            edit[i].bSelectOnFocus = false;
            edit[i].bDelayedNotify = true;
            edit[i].bNumericOnly = true;
            edit[i].align = 1;
        }

        dialogClient.addControl(deadSlider = new GWindowHSliderInt(dialogClient));
        deadSlider.setRange(0, 51, 0);
        dialogClient.addControl(filterSlider = new GWindowHSliderInt(dialogClient));
        filterSlider.setRange(0, 11, 0);
        wMouse = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new com.maddox.gwindow.GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {

            public void keyboardKey(int j, boolean flag)
            {
                super.keyboardKey(j, flag);
                if(j == 10 && flag)
                    notify(2, 0);
            }

        }
);
        wMouse.bNumericOnly = wMouse.bNumericFloat = true;
        wMouse.bDelayedNotify = true;
        wMouse.align = 1;
        wJoyProfile.add(i18n("setupIn.Joystick1"));
        wJoyProfile.add(i18n("setupIn.Joystick2"));
        wJoyProfile.add(i18n("setupIn.Joystick3"));
        wJoyProfile.add(i18n("setupIn.Joystick4"));
        wJoyProfile.setEditable(false);
        wJoyProfile.setSelected(com.maddox.il2.engine.Config.cur.ini.get("rts", "JoyProfile", 0), true, false);
        comboAxeInts = new int[32];
        comboAxeReversed = new int[32];
        bControlPanel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDefault = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        if(com.maddox.rts.JoyFF.isAttached())
            sForceFeedback = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.gwindow.GWindowComboControl wJoyProfile;
    public com.maddox.il2.gui.GUIButton bLoad;
    public com.maddox.il2.gui.GUIButton bSave;
    public com.maddox.il2.gui.GUISwitchBox3 sMirrorControl;
    public static final int MAX_DEAD_BAND = 50;
    public static final int MAX_FILTER = 10;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowComboControl comboAxe;
    public com.maddox.gwindow.GWindowVSliderInt slider[];
    public com.maddox.gwindow.GWindowEditControl edit[];
    public com.maddox.gwindow.GWindowHSliderInt deadSlider;
    public com.maddox.gwindow.GWindowHSliderInt filterSlider;
    public com.maddox.gwindow.GWindowEditControl wMouse;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIButton bDefault;
    public com.maddox.il2.gui.GUISwitchBox3 sForceFeedback;
    public com.maddox.il2.gui.GUIButton bControlPanel;
    private int comboAxeReversed[];
    private int comboAxeInts[];
    private int comboAxeREA[] = {
        -1, -1, -1
    };
    private int comboAxeIndx[] = {
        1, 0, 5
    };
    public com.maddox.rts.Joy joy;
    private int js[];











}
