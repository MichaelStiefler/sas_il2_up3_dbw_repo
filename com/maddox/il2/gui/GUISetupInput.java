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
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.Joy;
import com.maddox.rts.JoyFF;
import com.maddox.rts.Mouse;

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
                com.maddox.il2.game.Main.stateStack().push(20);
                com.maddox.il2.gui.GUIControls guicontrols = (com.maddox.il2.gui.GUIControls)com.maddox.il2.game.Main.stateStack().peek();
                guicontrols.scrollClient.vScroll.setPos(guicontrols.scrollClient.vScroll.posMax, true);
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(480F), x1024(528F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(576F), x1024(832F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(336F), y1024(48F), 2.0F, y1024(432F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(48F), y1024(32F), x1024(192F), y1024(32F), 2, i18n("setupIn.JoystickAxes"));
            draw(x1024(432F), y1024(32F), x1024(208F), y1024(32F), 0, i18n("setupIn.Profile"));
            draw(x1024(384F), y1024(80F), x1024(400F), y1024(32F), 0, comboAxe.getValue());
            draw(x1024(368F), y1024(368F), x1024(208F), y1024(32F), 2, i18n("setupIn.DeadBand"));
            draw(x1024(576F), y1024(368F), x1024(48F), y1024(32F), 1, "0");
            draw(x1024(832F), y1024(368F), x1024(48F), y1024(32F), 1, "50");
            draw(x1024(368F), y1024(416F), x1024(208F), y1024(32F), 2, i18n("setupIn.Filtering"));
            draw(x1024(576F), y1024(416F), x1024(48F), y1024(32F), 1, "0");
            draw(x1024(832F), y1024(416F), x1024(48F), y1024(32F), 1, "1");
            if(sForceFeedback != null)
                draw(x1024(136F), y1024(512F), x1024(208F), y1024(32F), 0, i18n("setupIn.ForceFeedback"));
            draw(x1024(416F), y1024(512F), x1024(336F), y1024(32F), 2, i18n("setupIn.MouseSensitivity"));
            draw(x1024(96F), y1024(608F), x1024(128F), y1024(48F), 0, i18n("setupIn.Back"));
            draw(x1024(304F), y1024(608F), x1024(160F), y1024(48F), 0, i18n("setupIn.Default"));
            draw(x1024(528F), y1024(608F), x1024(272F), y1024(48F), 2, i18n("setupIn.ControlPanel"));
            if(!com.maddox.rts.Joy.adapter().isExistAxe(0, 0))
                return;
            com.maddox.rts.Joy.adapter().getNotFilteredPos(0, joyRawPos);
            com.maddox.rts.Joy.adapter().getPos(0, joyPos);
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            com.maddox.gwindow.GBevel gbevel = guilookandfeel.bevelComboDown;
            float f = gbevel.L.dx;
            float f1 = x1024(16F);
            setCanvasColorWHITE();
            guilookandfeel.drawBevel(this, x1024(64F), y1024(240F), x1024(208F), y1024(208F), gbevel, guilookandfeel.basicelements);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(64F) + 2.0F * f, y1024(344F), x1024(208F) - 4F * f, 1.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(168F), y1024(240F) + 2.0F * f, 1.0F, y1024(208F) - 4F * f);
            float f2 = com.maddox.rts.Joy.normal(joyPos[0]);
            float f4 = com.maddox.rts.Joy.normal(joyPos[1]);
            float f5 = com.maddox.rts.Joy.normal(joyRawPos[0]);
            float f7 = com.maddox.rts.Joy.normal(joyRawPos[1]);
            float f8 = (x1024(208F) - f1) / 2.0F - f;
            float f9 = x1024(168F) + f5 * f8;
            float f11 = y1024(344F) + f7 * f8;
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, f9 - f1 / 2.0F, f11 - f1 / 2.0F, f1, f1);
            f9 = x1024(168F) + f2 * f8;
            f11 = y1024(344F) + f4 * f8;
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, f9 - f1 / 2.0F, f11 - f1 / 2.0F, f1, f1);
            byte byte0 = 5;
            if(com.maddox.rts.Joy.adapter().isExistAxe(0, byte0))
            {
                setCanvasColorWHITE();
                guilookandfeel.drawBevel(this, x1024(64F), y1024(448F), x1024(208F), y1024(32F), gbevel, guilookandfeel.basicelements);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Black, x1024(168F), y1024(448F) + 2.0F * f, 1.0F, y1024(32F) - 4F * f);
                float f6 = com.maddox.rts.Joy.normal(joyRawPos[byte0]);
                float f3 = com.maddox.rts.Joy.normal(joyPos[byte0]);
                float f10 = x1024(168F) + f6 * f8;
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Red, f10 - f1 / 2.0F, y1024(448F) + 2.0F * f, f1, y1024(32F) - 4F * f);
                f10 = x1024(168F) + f3 * f8;
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Green, f10 - f1 / 2.0F, y1024(448F) + 2.0F * f, f1, y1024(32F) - 4F * f);
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

            float f1 = lookAndFeel().getHSliderIntH();
            deadSlider.setPosSize(x1024(624F), y1024(368F) + (y1024(32F) - f1) / 2.0F, x1024(208F), f1);
            filterSlider.setPosSize(x1024(624F), y1024(416F) + (y1024(32F) - f1) / 2.0F, x1024(208F), f1);
            wMouse.set1024PosSize(768F, 512F, 96F, 32F);
            bBack.setPosC(x1024(56F), y1024(632F));
            bDefault.setPosC(x1024(264F), y1024(632F));
            bControlPanel.setPosC(x1024(840F), y1024(632F));
            if(sForceFeedback != null)
                sForceFeedback.setPosC(x1024(96F), y1024(528F));
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
        int i = comboAxe.getSelected();
        comboAxe.clear(false);
        int j = 0;
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 1))
        {
            comboAxe.add(i18n("setupIn.Pitch"));
            j++;
        }
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 0))
        {
            comboAxe.add(i18n("setupIn.Roll"));
            j++;
        }
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 5))
        {
            comboAxe.add(i18n("setupIn.Yaw"));
            j++;
        }
        if(j == 0)
        {
            for(int k = 0; k < 9; k++)
            {
                slider[k].setEnable(false);
                edit[k].setEnable(false);
            }

            deadSlider.setEnable(false);
            filterSlider.setEnable(false);
            comboAxe.add(i18n("setupIn.none"));
            comboAxe.setSelected(0, true, false);
            comboAxe.setEnable(false);
        } else
        {
            for(int l = 0; l < 9; l++)
            {
                slider[l].setEnable(true);
                edit[l].setEnable(true);
            }

            deadSlider.setEnable(true);
            filterSlider.setEnable(true);
            comboAxe.setEnable(true);
            if(i < 0 || i >= j)
                i = 0;
            comboAxe.setSelected(-1, false, false);
            comboAxe.setSelected(i, true, true);
        }
        float af[] = com.maddox.rts.Mouse.adapter().getSensitivity();
        wMouse.setValue("" + af[0], false);
    }

    private int curAxe()
    {
        int i = comboAxe.getSelected();
        if(i < 0)
            return -1;
        switch(i)
        {
        case 0: // '\0'
            return 1;

        case 1: // '\001'
            return 0;

        case 2: // '\002'
            return 5;
        }
        return -1;
    }

    private void fillSliders(int i)
    {
        if(i < 0)
            return;
        com.maddox.rts.Joy.adapter().getSensitivity(0, i, js);
        if(js[0] < 0)
            js[0] = 0;
        if(js[0] > 50)
            js[0] = 50;
        deadSlider.setPos(js[0], false);
        filterSlider.setPos((10 * js[11]) / 100, false);
        for(int j = 0; j < 10; j++)
        {
            if(js[j + 1] < 0)
                js[j + 1] = 0;
            if(js[j + 1] > 100)
                js[j + 1] = 100;
            slider[j].setPos(js[j + 1], false);
            edit[j].setValue("" + js[j + 1], false);
        }

    }

    private void doSetDefault()
    {
        js[0] = 0;
        for(int i = 1; i < 11; i++)
            js[i] = i * 10;

        js[11] = 0;
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 0))
            com.maddox.rts.Joy.adapter().setSensitivity(0, 0, js);
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 1))
            com.maddox.rts.Joy.adapter().setSensitivity(0, 1, js);
        if(com.maddox.rts.Joy.adapter().isExistAxe(0, 5))
            com.maddox.rts.Joy.adapter().setSensitivity(0, 5, js);
        float af[] = com.maddox.rts.Mouse.adapter().getSensitivity();
        af[0] = af[1] = 1.0F;
        fillDialogs();
    }

    private void setJoyS(int i, int j)
    {
        int k = curAxe();
        if(k < 0)
        {
            return;
        } else
        {
            com.maddox.rts.Joy.adapter().getSensitivity(0, k, js);
            js[i] = j;
            com.maddox.rts.Joy.adapter().setSensitivity(0, k, js);
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
        js = new int[12];
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setupIn.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        dialogClient.addControl(comboAxe = new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
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
        bControlPanel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bDefault = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        if(com.maddox.rts.JoyFF.isAttached())
            sForceFeedback = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        dialogClient.activateWindow();
        client.hideWindow();
    }

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
    private int comboAxeIndx[] = {
        1, 0, 5
    };
    private int js[];






}
