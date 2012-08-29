// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetupSound.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.VK;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.sound.RadioChannel;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUILookAndFeel, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUIPocket, GUISwitch16, GUISwitchN, GUISwitchBox2, 
//            GUIComboCfgInt, GUIButton, GUISeparate, GUIDialogClient

public class GUISetupSound extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bGeneral)
            {
                setPage(0);
                return true;
            }
            if(gwindow == bAudio)
            {
                setPage(1);
                return true;
            }
            if(gwindow == bMusic)
            {
                setPage(2);
                return true;
            }
            if(gwindow == bRadio)
            {
                setPage(3);
                return true;
            }
            if(gwindow == bBack)
            {
                endedPage();
                com.maddox.sound.AudioDevice.applySettings();
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == sPhoneOn)
            {
                doSwitchPhoneOn(sPhoneOn.isChecked());
                return true;
            }
            if(gwindow == sTest)
            {
                doSwitchTest(sTest.isChecked());
                return true;
            }
            if(gwindow == cTransMode)
            {
                doSwitchTransMode(cTransMode.getSelected() == 1);
                hideShowTransMode();
                return true;
            } else
            {
                update();
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            setCanvasFont(0);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(768F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(272F), y1024(32F), 2.0F, y1024(560F));
            if(page == 1)
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(304F), y1024(336F), x1024(496F), 2.0F);
            else
            if(page == 3)
            {
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(304F), y1024(208F), x1024(496F), 2.0F);
                com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(304F), y1024(432F), x1024(496F), 2.0F);
            }
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            draw(x1024(96F), y1024(80F), x1024(160F), y1024(48F), 0, i18n("sound.General"));
            draw(x1024(96F), y1024(144F), x1024(160F), y1024(48F), 0, i18n("sound.Audio"));
            draw(x1024(96F), y1024(208F), x1024(160F), y1024(48F), 0, i18n("sound.Music"));
            draw(x1024(96F), y1024(272F), x1024(160F), y1024(48F), 0, i18n("sound.Radio"));
            if(page == 0)
            {
                setCanvasColorWHITE();
                draw(x1024(520F), y1024(80F), x1024(64F), y1024(64F), texFull);
                draw(x1024(520F), y1024(208F), x1024(64F), y1024(64F), texFull);
                draw(x1024(520F), y1024(304F), x1024(64F), y1024(64F), texFull);
                draw(x1024(520F), y1024(400F), x1024(64F), y1024(64F), texFull);
                setCanvasColor(com.maddox.gwindow.GColor.Gray);
                draw(x1024(368F), y1024(144F), x1024(368F), y1024(32F), 1, i18n("sound.Master_volume"));
                draw(x1024(368F), y1024(272F), x1024(368F), y1024(32F), 1, i18n("sound.Objects_volume"));
                draw(x1024(368F), y1024(368F), x1024(368F), y1024(32F), 1, i18n("sound.Music_volume"));
                draw(x1024(368F), y1024(464F), x1024(368F), y1024(32F), 1, i18n("sound.Voice_volume"));
                draw(x1024(384F), y1024(528F), x1024(336F), y1024(48F), 0, i18n("sound.Play_music"));
            } else
            if(page == 1)
            {
                draw(x1024(384F), y1024(80F), x1024(336F), y1024(32F), 1, i18n("sound.Sound_engine"));
                draw(x1024(384F), y1024(160F), x1024(336F), y1024(32F), 1, i18n("sound.Playback_channels"));
                draw(x1024(384F), y1024(240F), x1024(336F), y1024(32F), 1, i18n("sound.Audio_quality"));
                draw(x1024(384F), y1024(352F), x1024(336F), y1024(32F), 1, i18n("sound.Speakers_type"));
                draw(x1024(480F), y1024(448F), x1024(240F), y1024(48F), 0, i18n("sound.Enable_hardware"));
                draw(x1024(480F), y1024(528F), x1024(240F), y1024(48F), 0, i18n("sound.Reverse_stereo"));
            } else
            if(page == 2)
            {
                draw(x1024(400F), y1024(192F), x1024(352F), y1024(48F), 0, i18n("sound.Play_take-off"));
                draw(x1024(400F), y1024(304F), x1024(352F), y1024(48F), 0, i18n("sound.Play_in-flight"));
                draw(x1024(400F), y1024(416F), x1024(352F), y1024(48F), 0, i18n("sound.Play_crash"));
            } else
            if(page == 3)
            {
                setCanvasColorWHITE();
                draw(x1024(312F), y1024(272F), x1024(64F), y1024(64F), texFull);
                if(!isTransMode_PressMuteKey())
                    draw(x1024(312F), y1024(496F), x1024(64F), y1024(64F), texFull);
                drawInd(this);
                setCanvasColor(com.maddox.gwindow.GColor.Gray);
                draw(x1024(392F), y1024(128F), x1024(408F), y1024(32F), 0, i18n("setupNet.Voice"));
                draw(x1024(392F), y1024(288F), x1024(200F), y1024(32F), 0, i18n("sound.MicLevel"));
                draw(x1024(392F), y1024(360F), x1024(200F), y1024(32F), 0, i18n("sound.PlayClicks"));
                draw(x1024(328F), y1024(448F), x1024(200F), y1024(32F), 2, i18n("sound.TransMode"));
                if(isTransMode_PressMuteKey())
                {
                    int i = com.maddox.rts.HotKeyEnv.env("$$$misc").find("radioMuteKey");
                    if(i != 0)
                        draw(x1024(328F), y1024(512F), x1024(400F), y1024(32F), 0, i18n("sound.MuteKey") + " : " + keyToStr(i));
                } else
                {
                    draw(x1024(392F), y1024(512F), x1024(200F), y1024(32F), 0, i18n("sound.Sensitivity"));
                }
                draw(x1024(376F), y1024(568F), x1024(354F), y1024(32F), 2, i18n("sound.Test"));
            }
            draw(x1024(96F), y1024(656F), x1024(288F), y1024(48F), 0, i18n("sound.Apply_Back"));
        }

        private java.lang.String keyToStr(int i)
        {
            if(i == 0)
                return "";
            if((i & 0xffff0000) == 0)
                return resName(com.maddox.rts.VK.getKeyText(i));
            else
                return resName(com.maddox.rts.VK.getKeyText(i >> 16 & 0xffff)) + " " + resName(com.maddox.rts.VK.getKeyText(i & 0xffff));
        }

        private void initResource()
        {
            try
            {
                resource = java.util.ResourceBundle.getBundle("i18n/controls", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
            }
            catch(java.lang.Exception exception) { }
        }

        private java.lang.String resName(java.lang.String s)
        {
            if(resource == null)
                return s;
            return resource.getString(s);
            java.lang.Exception exception;
            exception;
            return s;
        }

        public void setPosSize()
        {
            set1024PosSize(96F, 32F, 832F, 736F);
            bGeneral.setPosC(x1024(56F), y1024(104F));
            bAudio.setPosC(x1024(56F), y1024(168F));
            bMusic.setPosC(x1024(56F), y1024(232F));
            bRadio.setPosC(x1024(56F), y1024(296F));
            bBack.setPosC(x1024(56F), y1024(680F));
            pGeneral.setPosSize(x1024(400F), y1024(32F), x1024(304F), y1024(32F));
            pAudio.setPosSize(x1024(400F), y1024(32F), x1024(304F), y1024(32F));
            pMusic.setPosSize(x1024(400F), y1024(32F), x1024(304F), y1024(32F));
            pRadio.setPosSize(x1024(400F), y1024(32F), x1024(304F), y1024(32F));
            sMasterVolume.setPosC(x1024(552F), y1024(112F));
            sObjectsVolume.setPosC(x1024(552F), y1024(240F));
            sMusicVolume.setPosC(x1024(552F), y1024(336F));
            sVoiceVolume.setPosC(x1024(552F), y1024(432F));
            sMusicOn.setPosC(x1024(344F), y1024(552F));
            cEngine.setPosSize(x1024(416F), y1024(112F), x1024(272F), y1024(32F));
            cChannels.setPosSize(x1024(416F), y1024(192F), x1024(272F), y1024(32F));
            cRate.setPosSize(x1024(416F), y1024(272F), x1024(272F), y1024(32F));
            cType.setPosSize(x1024(416F), y1024(384F), x1024(272F), y1024(32F));
            sHardware.setPosC(x1024(456F), y1024(472F));
            sReverseStereo.setPosC(x1024(456F), y1024(552F));
            sPlayListTakeOff.setPosC(x1024(360F), y1024(216F));
            sPlayListInflight.setPosC(x1024(360F), y1024(328F));
            sPlayListCrash.setPosC(x1024(360F), y1024(440F));
            sPhoneOn.setPosC(x1024(352F), y1024(144F));
            sMicLevel.setPosC(x1024(344F), y1024(304F));
            sClicks.setPosC(x1024(352F), y1024(376F));
            cTransMode.setPosSize(x1024(536F), y1024(448F), x1024(256F), y1024(32F));
            sMicSens.setPosC(x1024(344F), y1024(528F));
            sTest.setPosC(x1024(768F), y1024(584F));
        }

        java.util.ResourceBundle resource;


        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        update();
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void update()
    {
        sMasterVolume.update();
        sObjectsVolume.update();
        sMusicVolume.update();
        sVoiceVolume.update();
        sMusicOn.update();
        cEngine.update();
        cRate.update();
        cType.update();
        sHardware.update();
        sReverseStereo.update();
        sPlayListTakeOff.update();
        sPlayListInflight.update();
        sPlayListCrash.update();
        sPhoneOn.refresh();
        sMicLevel.update();
        sClicks.refresh();
        sMicSens.update();
        sTest.refresh();
    }

    private void set(com.maddox.gwindow.GWindow gwindow, int i)
    {
        if(page == i)
            gwindow.showWindow();
        else
            gwindow.hideWindow();
    }

    private void setPage(int i)
    {
        endedPage();
        page = i;
        beginingPage();
        set(pGeneral, 0);
        set(sMasterVolume, 0);
        set(sObjectsVolume, 0);
        set(sMusicVolume, 0);
        set(sVoiceVolume, 0);
        set(sMusicOn, 0);
        set(pAudio, 1);
        set(cEngine, 1);
        set(cChannels, 1);
        set(cRate, 1);
        set(cType, 1);
        set(sHardware, 1);
        set(sReverseStereo, 1);
        set(pMusic, 2);
        set(sPlayListTakeOff, 2);
        set(sPlayListInflight, 2);
        set(sPlayListCrash, 2);
        set(pRadio, 3);
        set(sPhoneOn, 3);
        set(sMicLevel, 3);
        set(sClicks, 3);
        set(cTransMode, 3);
        set(sMicSens, 3);
        set(sTest, 3);
    }

    private void hideShowTransMode()
    {
        if(page != 3)
            return;
        if(isTransMode_PressMuteKey())
            sMicSens.hideWindow();
        else
            sMicSens.showWindow();
    }

    private void drawU(com.maddox.gwindow.GWindow gwindow, int i, int j, float f, float f1)
    {
        float f2 = gwindow.x1024(10F);
        float f3 = gwindow.y1024(16F);
        float f4 = gwindow.x1024(12F);
        if(i > 8)
            i = 8;
        if(i > 0)
        {
            for(int k = 0; k < i; k++)
            {
                com.maddox.gwindow.GColor gcolor = com.maddox.gwindow.GColor.Green;
                if(k >= 6)
                    gcolor = com.maddox.gwindow.GColor.Yellow;
                com.maddox.il2.gui.GUISeparate.draw(gwindow, gcolor, f + (float)k * f4, f1, f2, f3);
            }

        }
        if(j > 5)
            j = 5;
        if(j > 0)
        {
            float f5 = 9F * f4;
            for(int l = 0; l < j; l++)
            {
                com.maddox.gwindow.GColor gcolor1 = com.maddox.gwindow.GColor.Yellow;
                if(l >= 1)
                    gcolor1 = com.maddox.gwindow.GColor.Red;
                com.maddox.il2.gui.GUISeparate.draw(gwindow, gcolor1, f5 + f + (float)l * f4, f1, f2, f3);
            }

        }
    }

    private void drawInd(com.maddox.gwindow.GWindow gwindow)
    {
        int i = com.maddox.sound.AudioDevice.getRadioStatus();
        int j = java.lang.Math.round(((float)com.maddox.sound.AudioDevice.getRadioLevel() / 100F) * 8F);
        int k = java.lang.Math.round(((float)com.maddox.sound.AudioDevice.getRadioOverflow() / 100F) * 5F);
        boolean flag = (com.maddox.sound.AudioDevice.getRadioStatus() & 0x10) != 0;
        gwindow.setCanvasColorWHITE();
        gwindow.draw(gwindow.x1024(736F), gwindow.y1024(212F), gwindow.x1024(48F), gwindow.y1024(48F), texIndicator, flag ? 176F : 128F, 0.0F, 48F, 48F);
        gwindow.setCanvasColorWHITE();
        int l = (int)gwindow.x1024(540F);
        int i1 = (int)gwindow.y1024(228F);
        int j1 = (int)gwindow.x1024(12F) * 14;
        int k1 = (int)gwindow.y1024(16F);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindow.lAF()).basicelements;
        com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)gwindow.lAF()).bevelComboUp;
        gwindow.lAF().drawBevel(gwindow, (float)l - gbevel.L.dx, (float)i1 - gbevel.T.dy, (float)j1 + gbevel.L.dx + gbevel.R.dx, (float)k1 + gbevel.T.dy + gbevel.B.dy, gbevel, gtexture);
        drawU(gwindow, j, k, l, i1);
    }

    private void beginingPage()
    {
        switch(page)
        {
        case 3: // '\003'
            cTransMode.setSelected(com.maddox.sound.AudioDevice.getPTTMode() ? 1 : 0, true, false);
            hideShowTransMode();
            sPhoneOn.setChecked(com.maddox.sound.AudioDevice.npFlags.get(0), false);
            sPhoneOn.setEnable(com.maddox.il2.game.Main.cur().netServerParams == null);
            break;
        }
    }

    private void endedPage()
    {
        switch(page)
        {
        case 3: // '\003'
            doSwitchTest(false);
            sTest.setChecked(false, false);
            break;
        }
    }

    private void doSwitchPhoneOn(boolean flag)
    {
        com.maddox.sound.AudioDevice.npFlags.set(0, flag);
    }

    private void doSwitchTest(boolean flag)
    {
        if(flag)
        {
            if(!com.maddox.sound.RadioChannel.tstLoop)
            {
                npRun = com.maddox.sound.AudioDevice.isNetPhoneRunning();
                if(!npRun)
                    com.maddox.sound.AudioDevice.beginNetPhone();
                prevChannel = com.maddox.sound.RadioChannel.activeChannel();
                if(prevChannel == null)
                {
                    testChannel = new RadioChannel("$tst$", null, 1);
                    testChannel.setActive(true);
                }
                com.maddox.sound.RadioChannel.tstLoop = true;
                sPhoneOn.setEnable(false);
            }
        } else
        {
            if(com.maddox.sound.RadioChannel.tstLoop)
            {
                if(testChannel != null)
                {
                    testChannel.setActive(false);
                    testChannel.destroy();
                    testChannel = null;
                }
                if(prevChannel != null)
                    prevChannel.setActive(true);
                if(!npRun)
                    com.maddox.sound.AudioDevice.endNetPhone();
                com.maddox.sound.RadioChannel.tstLoop = false;
            }
            sPhoneOn.setEnable(true);
        }
    }

    private void doSwitchTransMode(boolean flag)
    {
        com.maddox.sound.AudioDevice.npFlags.set(1, flag);
        com.maddox.sound.AudioDevice.setPTTMode(flag);
    }

    private boolean isTransMode_PressMuteKey()
    {
        return cTransMode.getSelected() == 1;
    }

    public GUISetupSound(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(13);
        page = 0;
        npRun = false;
        testChannel = null;
        prevChannel = null;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        dialogClient.initResource();
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("sound.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        texFull = new GTexRegion("GUI/game/staticelements.mat", 192F, 160F, 64F, 64F);
        pGeneral = new GUIPocket(dialogClient, i18n("sound.General"));
        pAudio = new GUIPocket(dialogClient, i18n("sound.Audio"));
        pMusic = new GUIPocket(dialogClient, i18n("sound.Music"));
        pRadio = new GUIPocket(dialogClient, i18n("sound.Radio"));
        sMasterVolume = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("MasterVolume"), true));
        sObjectsVolume = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("ObjectVolume"), true));
        sMusicVolume = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("MusicVolume"), true));
        sVoiceVolume = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("VoiceVolume"), true));
        sMusicOn = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusFlags"), 0, true));
        cEngine = (com.maddox.il2.gui.GUIComboCfgInt)dialogClient.addControl(new GUIComboCfgInt(dialogClient, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("SoundEngine"), true, "sound."));
        cChannels = (com.maddox.il2.gui.GUIComboCfgInt)dialogClient.addControl(new GUIComboCfgInt(dialogClient, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("NumChannels"), true, "sound."));
        sHardware = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("SoundFlags"), 1, true));
        cType = (com.maddox.il2.gui.GUIComboCfgInt)dialogClient.addControl(new GUIComboCfgInt(dialogClient, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("Speakers"), true, "sound."));
        cRate = (com.maddox.il2.gui.GUIComboCfgInt)dialogClient.addControl(new GUIComboCfgInt(dialogClient, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("SamplingRate"), true, "sound."));
        sReverseStereo = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("SoundFlags"), 0, true));
        sPlayListTakeOff = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusState"), 0, true));
        sPlayListInflight = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusState"), 1, true));
        sPlayListCrash = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("MusState"), 2, true));
        sPhoneOn = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)null, 0, true));
        sMicLevel = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("MicLevel"), true));
        sClicks = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)com.maddox.rts.CfgTools.get("RadioFlags"), 2, true));
        cTransMode = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cTransMode.add(i18n("sound._Sensitivity"));
        cTransMode.add(i18n("sound._MuteKey"));
        cTransMode.setEditable(false);
        cTransMode.setSelected(0, true, false);
        sMicSens = (com.maddox.il2.gui.GUISwitchN)dialogClient.addControl(new GUISwitch16(dialogClient, new int[] {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
            10, 11, 12, 13, 14
        }, (com.maddox.rts.CfgInt)com.maddox.rts.CfgTools.get("ActLevel"), true));
        sTest = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient, (com.maddox.rts.CfgFlags)null, 0, true));
        texIndicator = com.maddox.gwindow.GTexture.New("GUI/game/buttons.mat");
        setPage(0);
        bGeneral = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bAudio = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bMusic = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bRadio = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    private static final int GENERAL = 0;
    private static final int AUDIO = 1;
    private static final int MUSIC = 2;
    private static final int RADIO = 3;
    private int page;
    public com.maddox.il2.gui.GUIButton bGeneral;
    public com.maddox.il2.gui.GUIButton bAudio;
    public com.maddox.il2.gui.GUIButton bMusic;
    public com.maddox.il2.gui.GUIButton bRadio;
    public com.maddox.il2.gui.GUIButton bBack;
    public com.maddox.il2.gui.GUIPocket pGeneral;
    public com.maddox.il2.gui.GUISwitchN sMasterVolume;
    public com.maddox.il2.gui.GUISwitchN sObjectsVolume;
    public com.maddox.il2.gui.GUISwitchN sMusicVolume;
    public com.maddox.il2.gui.GUISwitchN sVoiceVolume;
    public com.maddox.il2.gui.GUISwitchBox2 sMusicOn;
    public com.maddox.il2.gui.GUIPocket pAudio;
    public com.maddox.il2.gui.GUIComboCfgInt cEngine;
    public com.maddox.il2.gui.GUIComboCfgInt cChannels;
    public com.maddox.il2.gui.GUIComboCfgInt cRate;
    public com.maddox.il2.gui.GUIComboCfgInt cType;
    public com.maddox.il2.gui.GUISwitchBox2 sHardware;
    public com.maddox.il2.gui.GUISwitchBox2 sReverseStereo;
    public com.maddox.il2.gui.GUIPocket pMusic;
    public com.maddox.il2.gui.GUISwitchBox2 sPlayListTakeOff;
    public com.maddox.il2.gui.GUISwitchBox2 sPlayListInflight;
    public com.maddox.il2.gui.GUISwitchBox2 sPlayListCrash;
    public com.maddox.il2.gui.GUIPocket pRadio;
    public com.maddox.il2.gui.GUISwitchBox2 sPhoneOn;
    public com.maddox.il2.gui.GUISwitchN sMicLevel;
    public com.maddox.il2.gui.GUISwitchBox2 sClicks;
    public com.maddox.gwindow.GWindowComboControl cTransMode;
    public com.maddox.il2.gui.GUISwitchN sMicSens;
    public com.maddox.il2.gui.GUISwitchBox2 sTest;
    public com.maddox.gwindow.GTexture texIndicator;
    public com.maddox.gwindow.GTexRegion texFull;
    private boolean npRun;
    private com.maddox.sound.RadioChannel testChannel;
    private com.maddox.sound.RadioChannel prevChannel;









}
