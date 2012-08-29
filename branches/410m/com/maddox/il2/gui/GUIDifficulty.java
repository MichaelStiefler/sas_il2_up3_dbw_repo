// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIDifficulty.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUISwitchBox3, GUIDialogClient, GUISeparate

public class GUIDifficulty extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                if(bFirst)
                    com.maddox.il2.game.Main.stateStack().pop();
                else
                if(bSecond)
                {
                    bFirst = true;
                    bSecond = false;
                    bThird = false;
                    showHide();
                } else
                {
                    bSecond = true;
                    bFirst = false;
                    bThird = false;
                    showHide();
                }
                return true;
            }
            if(gwindow == bNext)
            {
                if(bFirst)
                {
                    bFirst = false;
                    bSecond = true;
                    bThird = false;
                    showHide();
                } else
                if(bSecond)
                {
                    bFirst = false;
                    bSecond = false;
                    bThird = true;
                    showHide();
                }
                return true;
            }
            if(gwindow == bEasy)
            {
                settings().setEasy();
                reset();
                return true;
            }
            if(gwindow == bNormal)
            {
                settings().setNormal();
                reset();
                return true;
            }
            if(gwindow == bHard)
            {
                settings().setRealistic();
                reset();
                return true;
            }
            if(gwindow == sNo_Map_Icons)
            {
                sNo_Fog_Of_War_Icons.setEnable(sNo_Map_Icons.isChecked());
                if(!sNo_Map_Icons.isChecked())
                    sNo_Fog_Of_War_Icons.setChecked(true, false);
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(464F), x1024(768F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(544F), x1024(768F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(96F), y1024(577F), x1024(224F), y1024(48F), 0, i18n("diff.Back"));
            if(bEnable)
            {
                draw(x1024(224F), y1024(464F), x1024(128F), y1024(48F), 2, i18n("diff.Easy"));
                draw(x1024(416F), y1024(464F), x1024(128F), y1024(48F), 2, i18n("diff.Normal"));
                draw(x1024(608F), y1024(464F), x1024(128F), y1024(48F), 2, i18n("diff.Hard"));
            }
            if(bFirst || bSecond)
                draw(x1024(512F), y1024(577F), x1024(224F), y1024(48F), 2, i18n("diff.Next"));
            if(bFirst)
            {
                draw(x1024(128F), y1024(32F), x1024(272F), y1024(48F), 0, i18n("diff.SeparateEStart"));
                draw(x1024(128F), y1024(96F), x1024(272F), y1024(48F), 0, i18n("diff.ComplexEManagement"));
                draw(x1024(128F), y1024(160F), x1024(272F), y1024(48F), 0, i18n("diff.Engine"));
                draw(x1024(128F), y1024(224F), x1024(272F), y1024(48F), 0, i18n("diff.Torque"));
                draw(x1024(128F), y1024(288F), x1024(272F), y1024(48F), 0, i18n("diff.Flutter"));
                draw(x1024(128F), y1024(352F), x1024(272F), y1024(48F), 0, i18n("diff.Wind"));
                draw(x1024(128F), y1024(416F), x1024(272F), y1024(48F), 0, i18n("diff.Reliability"));
                draw(x1024(528F), y1024(32F), x1024(272F), y1024(48F), 0, i18n("diff.Stalls"));
                draw(x1024(528F), y1024(96F), x1024(272F), y1024(48F), 0, i18n("diff.Vulnerability"));
                draw(x1024(528F), y1024(160F), x1024(272F), y1024(48F), 0, i18n("diff.Blackouts"));
                draw(x1024(528F), y1024(224F), x1024(272F), y1024(48F), 0, i18n("diff.RealisticGun"));
                draw(x1024(528F), y1024(288F), x1024(272F), y1024(48F), 0, i18n("diff.LimitedAmmo"));
                draw(x1024(528F), y1024(352F), x1024(272F), y1024(48F), 0, i18n("diff.LimitedFuel"));
                draw(x1024(528F), y1024(416F), x1024(272F), y1024(48F), 0, i18n("diff.G_Limits"));
            } else
            if(bSecond)
            {
                draw(x1024(128F), y1024(32F), x1024(272F), y1024(48F), 0, i18n("diff.Cockpit"));
                draw(x1024(128F), y1024(96F), x1024(272F), y1024(48F), 0, i18n("diff.NoOutside"));
                draw(x1024(128F), y1024(160F), x1024(272F), y1024(48F), 0, i18n("diff.Head"));
                draw(x1024(128F), y1024(224F), x1024(272F), y1024(48F), 0, i18n("diff.NoIcons"));
                draw(x1024(128F), y1024(288F), x1024(272F), y1024(48F), 0, i18n("diff.NoPadlock"));
                draw(x1024(128F), y1024(352F), x1024(272F), y1024(48F), 0, i18n("diff.Clouds"));
                draw(x1024(528F), y1024(32F), x1024(272F), y1024(48F), 0, i18n("diff.NoInstantSuccess"));
                draw(x1024(528F), y1024(96F), x1024(272F), y1024(48F), 0, i18n("diff.Takeoff"));
                draw(x1024(528F), y1024(160F), x1024(272F), y1024(48F), 0, i18n("diff.RealisticLand"));
                draw(x1024(528F), y1024(224F), x1024(272F), y1024(48F), 0, i18n("diff.NoSpeedBar"));
                draw(x1024(528F), y1024(288F), x1024(272F), y1024(48F), 0, i18n("diff.RealisticNavInstr"));
                draw(x1024(528F), y1024(352F), x1024(272F), y1024(48F), 0, i18n("diff.RealisticPilotVulnerability"));
            } else
            if(bThird)
            {
                draw(x1024(128F), y1024(32F), x1024(272F), y1024(48F), 0, i18n("diff.NoMapIcons"));
                draw(x1024(128F), y1024(96F), x1024(272F), y1024(48F), 0, i18n("diff.NoPlayerIcon"));
                draw(x1024(128F), y1024(160F), x1024(272F), y1024(48F), 0, i18n("diff.NoFogOfWarIcons"));
                draw(x1024(128F), y1024(224F), x1024(272F), y1024(48F), 0, i18n("diff.NoMinimapPath"));
            }
        }

        public void setPosSize()
        {
            set1024PosSize(92F, 72F, 832F, 656F);
            sSeparateEStart.setPosC(x1024(88F), y1024(56F));
            sComplexEManagement.setPosC(x1024(88F), y1024(120F));
            sEngine_Overheat.setPosC(x1024(88F), y1024(184F));
            sTorque_N_Gyro_Effects.setPosC(x1024(88F), y1024(248F));
            sFlutter_Effect.setPosC(x1024(88F), y1024(312F));
            sWind_N_Turbulence.setPosC(x1024(88F), y1024(376F));
            sReliability.setPosC(x1024(88F), y1024(440F));
            sStalls_N_Spins.setPosC(x1024(488F), y1024(56F));
            sVulnerability.setPosC(x1024(488F), y1024(120F));
            sBlackouts_N_Redouts.setPosC(x1024(488F), y1024(184F));
            sRealistic_Gunnery.setPosC(x1024(488F), y1024(248F));
            sLimited_Ammo.setPosC(x1024(488F), y1024(312F));
            sLimited_Fuel.setPosC(x1024(488F), y1024(376F));
            sG_Limits.setPosC(x1024(488F), y1024(440F));
            sCockpit_Always_On.setPosC(x1024(88F), y1024(56F));
            sNo_Outside_Views.setPosC(x1024(88F), y1024(120F));
            sHead_Shake.setPosC(x1024(88F), y1024(184F));
            sNo_Icons.setPosC(x1024(88F), y1024(248F));
            sNo_Padlock.setPosC(x1024(88F), y1024(312F));
            sClouds.setPosC(x1024(88F), y1024(376F));
            sNoInstantSuccess.setPosC(x1024(488F), y1024(56F));
            sTakeoff_N_Landing.setPosC(x1024(488F), y1024(120F));
            sRealistic_Landings.setPosC(x1024(488F), y1024(184F));
            sNoSpeedBar.setPosC(x1024(488F), y1024(248F));
            sRealisticNavigationInstruments.setPosC(x1024(488F), y1024(312F));
            sRealisticPilotVulnerability.setPosC(x1024(488F), y1024(376F));
            sNo_Map_Icons.setPosC(x1024(88F), y1024(56F));
            sNo_Player_Icon.setPosC(x1024(88F), y1024(120F));
            sNo_Fog_Of_War_Icons.setPosC(x1024(88F), y1024(184F));
            sNoMinimapPath.setPosC(x1024(88F), y1024(248F));
            bExit.setPosC(x1024(56F), y1024(602F));
            bEasy.setPosC(x1024(392F), y1024(488F));
            bNormal.setPosC(x1024(584F), y1024(488F));
            bHard.setPosC(x1024(776F), y1024(488F));
            bNext.setPosC(x1024(776F), y1024(602F));
        }

        public DialogClient()
        {
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 27)
            bEnable = false;
        else
            bEnable = true;
        _enter();
    }

    protected com.maddox.il2.ai.DifficultySettings settings()
    {
        return com.maddox.il2.ai.World.cur().diffUser;
    }

    public void _enter()
    {
        reset();
        sReliability.setEnable(bEnable);
        sG_Limits.setEnable(bEnable);
        sRealisticPilotVulnerability.setEnable(bEnable);
        sRealisticNavigationInstruments.setEnable(bEnable);
        sNo_Player_Icon.setEnable(bEnable);
        sWind_N_Turbulence.setEnable(bEnable);
        sFlutter_Effect.setEnable(bEnable);
        sStalls_N_Spins.setEnable(bEnable);
        sBlackouts_N_Redouts.setEnable(bEnable);
        sEngine_Overheat.setEnable(bEnable);
        sTorque_N_Gyro_Effects.setEnable(bEnable);
        sRealistic_Landings.setEnable(bEnable);
        sTakeoff_N_Landing.setEnable(bEnable);
        sCockpit_Always_On.setEnable(bEnable);
        sNo_Outside_Views.setEnable(bEnable);
        sHead_Shake.setEnable(bEnable);
        sNo_Icons.setEnable(bEnable);
        sNo_Map_Icons.setEnable(bEnable);
        sRealistic_Gunnery.setEnable(bEnable);
        sLimited_Ammo.setEnable(bEnable);
        sLimited_Fuel.setEnable(bEnable);
        sVulnerability.setEnable(bEnable);
        sNo_Padlock.setEnable(bEnable);
        sClouds.setEnable(bEnable);
        sSeparateEStart.setEnable(bEnable);
        sNoInstantSuccess.setEnable(bEnable);
        sNoMinimapPath.setEnable(bEnable);
        sNoSpeedBar.setEnable(bEnable);
        sComplexEManagement.setEnable(bEnable);
        setShow(bEnable, bEasy);
        setShow(bEnable, bNormal);
        setShow(bEnable, bHard);
        showHide();
        client.activateWindow();
    }

    private void setShow(boolean flag, com.maddox.gwindow.GWindow gwindow)
    {
        if(flag)
            gwindow.showWindow();
        else
            gwindow.hideWindow();
    }

    private void showHide()
    {
        setShow(bFirst, sSeparateEStart);
        setShow(bFirst, sComplexEManagement);
        setShow(bFirst, sEngine_Overheat);
        setShow(bFirst, sTorque_N_Gyro_Effects);
        setShow(bFirst, sFlutter_Effect);
        setShow(bFirst, sWind_N_Turbulence);
        setShow(bFirst, sStalls_N_Spins);
        setShow(bFirst, sVulnerability);
        setShow(bFirst, sBlackouts_N_Redouts);
        setShow(bFirst, sRealistic_Gunnery);
        setShow(bFirst, sLimited_Ammo);
        setShow(bFirst, sLimited_Fuel);
        setShow(bFirst || bSecond, bNext);
        setShow(bFirst, sReliability);
        setShow(bFirst, sG_Limits);
        setShow(bSecond, sRealisticPilotVulnerability);
        setShow(bSecond, sRealisticNavigationInstruments);
        setShow(bSecond, sCockpit_Always_On);
        setShow(bSecond, sNo_Outside_Views);
        setShow(bSecond, sHead_Shake);
        setShow(bSecond, sNo_Icons);
        setShow(bSecond, sNo_Padlock);
        setShow(bSecond, sClouds);
        setShow(bSecond, sNoInstantSuccess);
        setShow(bSecond, sTakeoff_N_Landing);
        setShow(bSecond, sRealistic_Landings);
        setShow(bSecond, sNoSpeedBar);
        setShow(bThird, sNo_Map_Icons);
        setShow(bThird, sNo_Player_Icon);
        setShow(bThird, sNo_Fog_Of_War_Icons);
        setShow(bThird, sNoMinimapPath);
        dialogClient.doResolutionChanged();
        dialogClient.setPosSize();
    }

    private void reset()
    {
        com.maddox.il2.ai.DifficultySettings difficultysettings = settings();
        sReliability.setChecked(difficultysettings.Reliability, false);
        sG_Limits.setChecked(difficultysettings.G_Limits, false);
        sRealisticPilotVulnerability.setChecked(difficultysettings.RealisticPilotVulnerability, false);
        sRealisticNavigationInstruments.setChecked(difficultysettings.RealisticNavigationInstruments, false);
        sNo_Player_Icon.setChecked(difficultysettings.No_Player_Icon, false);
        sNo_Fog_Of_War_Icons.setChecked(difficultysettings.No_Fog_Of_War_Icons, false);
        sWind_N_Turbulence.setChecked(difficultysettings.Wind_N_Turbulence, false);
        sFlutter_Effect.setChecked(difficultysettings.Flutter_Effect, false);
        sStalls_N_Spins.setChecked(difficultysettings.Stalls_N_Spins, false);
        sBlackouts_N_Redouts.setChecked(difficultysettings.Blackouts_N_Redouts, false);
        sEngine_Overheat.setChecked(difficultysettings.Engine_Overheat, false);
        sTorque_N_Gyro_Effects.setChecked(difficultysettings.Torque_N_Gyro_Effects, false);
        sRealistic_Landings.setChecked(difficultysettings.Realistic_Landings, false);
        sTakeoff_N_Landing.setChecked(difficultysettings.Takeoff_N_Landing, false);
        sCockpit_Always_On.setChecked(difficultysettings.Cockpit_Always_On, false);
        sNo_Outside_Views.setChecked(difficultysettings.No_Outside_Views, false);
        sHead_Shake.setChecked(difficultysettings.Head_Shake, false);
        sNo_Icons.setChecked(difficultysettings.No_Icons, false);
        sNo_Map_Icons.setChecked(difficultysettings.No_Map_Icons, false);
        sRealistic_Gunnery.setChecked(difficultysettings.Realistic_Gunnery, false);
        sLimited_Ammo.setChecked(difficultysettings.Limited_Ammo, false);
        sLimited_Fuel.setChecked(difficultysettings.Limited_Fuel, false);
        sVulnerability.setChecked(difficultysettings.Vulnerability, false);
        sNo_Padlock.setChecked(difficultysettings.No_Padlock, false);
        sClouds.setChecked(difficultysettings.Clouds, false);
        sSeparateEStart.setChecked(difficultysettings.SeparateEStart, false);
        sNoInstantSuccess.setChecked(difficultysettings.NoInstantSuccess, false);
        sNoMinimapPath.setChecked(difficultysettings.NoMinimapPath, false);
        sNoSpeedBar.setChecked(difficultysettings.NoSpeedBar, false);
        sComplexEManagement.setChecked(difficultysettings.ComplexEManagement, false);
        sNo_Fog_Of_War_Icons.setEnable(sNo_Map_Icons.isChecked() & bEnable);
    }

    public void _leave()
    {
        if(bEnable)
        {
            com.maddox.il2.ai.DifficultySettings difficultysettings = settings();
            difficultysettings.Reliability = sReliability.isChecked();
            difficultysettings.RealisticNavigationInstruments = sRealisticNavigationInstruments.isChecked();
            difficultysettings.G_Limits = sG_Limits.isChecked();
            difficultysettings.RealisticPilotVulnerability = sRealisticPilotVulnerability.isChecked();
            difficultysettings.No_Player_Icon = sNo_Player_Icon.isChecked();
            difficultysettings.No_Fog_Of_War_Icons = sNo_Fog_Of_War_Icons.isChecked();
            difficultysettings.Wind_N_Turbulence = sWind_N_Turbulence.isChecked();
            difficultysettings.Flutter_Effect = sFlutter_Effect.isChecked();
            difficultysettings.Stalls_N_Spins = sStalls_N_Spins.isChecked();
            difficultysettings.Blackouts_N_Redouts = sBlackouts_N_Redouts.isChecked();
            difficultysettings.Engine_Overheat = sEngine_Overheat.isChecked();
            difficultysettings.Torque_N_Gyro_Effects = sTorque_N_Gyro_Effects.isChecked();
            difficultysettings.Realistic_Landings = sRealistic_Landings.isChecked();
            difficultysettings.Takeoff_N_Landing = sTakeoff_N_Landing.isChecked();
            difficultysettings.Cockpit_Always_On = sCockpit_Always_On.isChecked();
            difficultysettings.No_Outside_Views = sNo_Outside_Views.isChecked();
            difficultysettings.Head_Shake = sHead_Shake.isChecked();
            difficultysettings.No_Icons = sNo_Icons.isChecked();
            difficultysettings.No_Map_Icons = sNo_Map_Icons.isChecked();
            difficultysettings.Realistic_Gunnery = sRealistic_Gunnery.isChecked();
            difficultysettings.Limited_Ammo = sLimited_Ammo.isChecked();
            difficultysettings.Limited_Fuel = sLimited_Fuel.isChecked();
            difficultysettings.Vulnerability = sVulnerability.isChecked();
            difficultysettings.No_Padlock = sNo_Padlock.isChecked();
            difficultysettings.Clouds = sClouds.isChecked();
            difficultysettings.SeparateEStart = sSeparateEStart.isChecked();
            difficultysettings.NoInstantSuccess = sNoInstantSuccess.isChecked();
            difficultysettings.NoMinimapPath = sNoMinimapPath.isChecked();
            difficultysettings.NoSpeedBar = sNoSpeedBar.isChecked();
            difficultysettings.ComplexEManagement = sComplexEManagement.isChecked();
        }
        client.hideWindow();
    }

    protected void clientInit(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
    }

    public GUIDifficulty(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        this(gwindowroot, 17);
    }

    protected GUIDifficulty(com.maddox.gwindow.GWindowRoot gwindowroot, int i)
    {
        super(i);
        bEnable = true;
        bFirst = true;
        bSecond = false;
        bThird = false;
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("diff.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bEasy = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNormal = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bHard = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNext = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        sWind_N_Turbulence = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sFlutter_Effect = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sStalls_N_Spins = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sBlackouts_N_Redouts = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sEngine_Overheat = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sTorque_N_Gyro_Effects = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sRealistic_Landings = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sTakeoff_N_Landing = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sCockpit_Always_On = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Outside_Views = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sHead_Shake = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Icons = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Map_Icons = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sRealistic_Gunnery = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sLimited_Ammo = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sLimited_Fuel = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sVulnerability = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Padlock = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sClouds = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sSeparateEStart = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNoInstantSuccess = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNoMinimapPath = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNoSpeedBar = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sComplexEManagement = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sReliability = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sRealisticNavigationInstruments = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sG_Limits = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sRealisticPilotVulnerability = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Player_Icon = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        sNo_Fog_Of_War_Icons = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        clientInit(gwindowroot);
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUISwitchBox3 sWind_N_Turbulence;
    public com.maddox.il2.gui.GUISwitchBox3 sFlutter_Effect;
    public com.maddox.il2.gui.GUISwitchBox3 sStalls_N_Spins;
    public com.maddox.il2.gui.GUISwitchBox3 sBlackouts_N_Redouts;
    public com.maddox.il2.gui.GUISwitchBox3 sEngine_Overheat;
    public com.maddox.il2.gui.GUISwitchBox3 sTorque_N_Gyro_Effects;
    public com.maddox.il2.gui.GUISwitchBox3 sRealistic_Landings;
    public com.maddox.il2.gui.GUISwitchBox3 sTakeoff_N_Landing;
    public com.maddox.il2.gui.GUISwitchBox3 sCockpit_Always_On;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Outside_Views;
    public com.maddox.il2.gui.GUISwitchBox3 sHead_Shake;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Icons;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Map_Icons;
    public com.maddox.il2.gui.GUISwitchBox3 sRealistic_Gunnery;
    public com.maddox.il2.gui.GUISwitchBox3 sLimited_Ammo;
    public com.maddox.il2.gui.GUISwitchBox3 sLimited_Fuel;
    public com.maddox.il2.gui.GUISwitchBox3 sVulnerability;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Padlock;
    public com.maddox.il2.gui.GUISwitchBox3 sClouds;
    public com.maddox.il2.gui.GUISwitchBox3 sSeparateEStart;
    public com.maddox.il2.gui.GUISwitchBox3 sNoInstantSuccess;
    public com.maddox.il2.gui.GUISwitchBox3 sNoMinimapPath;
    public com.maddox.il2.gui.GUISwitchBox3 sNoSpeedBar;
    public com.maddox.il2.gui.GUISwitchBox3 sComplexEManagement;
    public com.maddox.il2.gui.GUISwitchBox3 sReliability;
    public com.maddox.il2.gui.GUISwitchBox3 sG_Limits;
    public com.maddox.il2.gui.GUISwitchBox3 sRealisticPilotVulnerability;
    public com.maddox.il2.gui.GUISwitchBox3 sRealisticNavigationInstruments;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Player_Icon;
    public com.maddox.il2.gui.GUISwitchBox3 sNo_Fog_Of_War_Icons;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bEasy;
    public com.maddox.il2.gui.GUIButton bNormal;
    public com.maddox.il2.gui.GUIButton bHard;
    public com.maddox.il2.gui.GUIButton bNext;
    public boolean bEnable;
    public boolean bFirst;
    public boolean bSecond;
    public boolean bThird;


}
