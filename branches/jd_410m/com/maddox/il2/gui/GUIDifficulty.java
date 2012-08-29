package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

public class GUIDifficulty extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUISwitchBox3 sWind_N_Turbulence;
  public GUISwitchBox3 sFlutter_Effect;
  public GUISwitchBox3 sStalls_N_Spins;
  public GUISwitchBox3 sBlackouts_N_Redouts;
  public GUISwitchBox3 sEngine_Overheat;
  public GUISwitchBox3 sTorque_N_Gyro_Effects;
  public GUISwitchBox3 sRealistic_Landings;
  public GUISwitchBox3 sTakeoff_N_Landing;
  public GUISwitchBox3 sCockpit_Always_On;
  public GUISwitchBox3 sNo_Outside_Views;
  public GUISwitchBox3 sHead_Shake;
  public GUISwitchBox3 sNo_Icons;
  public GUISwitchBox3 sNo_Map_Icons;
  public GUISwitchBox3 sRealistic_Gunnery;
  public GUISwitchBox3 sLimited_Ammo;
  public GUISwitchBox3 sLimited_Fuel;
  public GUISwitchBox3 sVulnerability;
  public GUISwitchBox3 sNo_Padlock;
  public GUISwitchBox3 sClouds;
  public GUISwitchBox3 sSeparateEStart;
  public GUISwitchBox3 sNoInstantSuccess;
  public GUISwitchBox3 sNoMinimapPath;
  public GUISwitchBox3 sNoSpeedBar;
  public GUISwitchBox3 sComplexEManagement;
  public GUISwitchBox3 sReliability;
  public GUISwitchBox3 sG_Limits;
  public GUISwitchBox3 sRealisticPilotVulnerability;
  public GUISwitchBox3 sRealisticNavigationInstruments;
  public GUISwitchBox3 sNo_Player_Icon;
  public GUISwitchBox3 sNo_Fog_Of_War_Icons;
  public GUIButton bExit;
  public GUIButton bEasy;
  public GUIButton bNormal;
  public GUIButton bHard;
  public GUIButton bNext;
  public boolean bEnable = true;
  public boolean bFirst = true;
  public boolean bSecond = false;
  public boolean bThird = false;

  public void enterPush(GameState paramGameState) {
    if (paramGameState.id() == 27)
      this.bEnable = false;
    else
      this.bEnable = true;
    _enter();
  }

  protected DifficultySettings settings() {
    return World.cur().diffUser;
  }

  public void _enter() {
    reset();

    this.sReliability.setEnable(this.bEnable);
    this.sG_Limits.setEnable(this.bEnable);
    this.sRealisticPilotVulnerability.setEnable(this.bEnable);
    this.sRealisticNavigationInstruments.setEnable(this.bEnable);

    this.sNo_Player_Icon.setEnable(this.bEnable);

    this.sWind_N_Turbulence.setEnable(this.bEnable);
    this.sFlutter_Effect.setEnable(this.bEnable);
    this.sStalls_N_Spins.setEnable(this.bEnable);
    this.sBlackouts_N_Redouts.setEnable(this.bEnable);
    this.sEngine_Overheat.setEnable(this.bEnable);
    this.sTorque_N_Gyro_Effects.setEnable(this.bEnable);
    this.sRealistic_Landings.setEnable(this.bEnable);
    this.sTakeoff_N_Landing.setEnable(this.bEnable);
    this.sCockpit_Always_On.setEnable(this.bEnable);
    this.sNo_Outside_Views.setEnable(this.bEnable);
    this.sHead_Shake.setEnable(this.bEnable);
    this.sNo_Icons.setEnable(this.bEnable);
    this.sNo_Map_Icons.setEnable(this.bEnable);
    this.sRealistic_Gunnery.setEnable(this.bEnable);
    this.sLimited_Ammo.setEnable(this.bEnable);
    this.sLimited_Fuel.setEnable(this.bEnable);
    this.sVulnerability.setEnable(this.bEnable);
    this.sNo_Padlock.setEnable(this.bEnable);
    this.sClouds.setEnable(this.bEnable);
    this.sSeparateEStart.setEnable(this.bEnable);
    this.sNoInstantSuccess.setEnable(this.bEnable);
    this.sNoMinimapPath.setEnable(this.bEnable);
    this.sNoSpeedBar.setEnable(this.bEnable);
    this.sComplexEManagement.setEnable(this.bEnable);

    setShow(this.bEnable, this.bEasy);
    setShow(this.bEnable, this.bNormal);
    setShow(this.bEnable, this.bHard);

    showHide();

    this.client.activateWindow();
  }

  private void setShow(boolean paramBoolean, GWindow paramGWindow) {
    if (paramBoolean) paramGWindow.showWindow(); else
      paramGWindow.hideWindow(); 
  }

  private void showHide() {
    setShow(this.bFirst, this.sSeparateEStart);
    setShow(this.bFirst, this.sComplexEManagement);
    setShow(this.bFirst, this.sEngine_Overheat);
    setShow(this.bFirst, this.sTorque_N_Gyro_Effects);
    setShow(this.bFirst, this.sFlutter_Effect);
    setShow(this.bFirst, this.sWind_N_Turbulence);
    setShow(this.bFirst, this.sStalls_N_Spins);
    setShow(this.bFirst, this.sVulnerability);
    setShow(this.bFirst, this.sBlackouts_N_Redouts);
    setShow(this.bFirst, this.sRealistic_Gunnery);
    setShow(this.bFirst, this.sLimited_Ammo);
    setShow(this.bFirst, this.sLimited_Fuel);

    setShow((this.bFirst) || (this.bSecond), this.bNext);

    setShow(this.bFirst, this.sReliability);
    setShow(this.bFirst, this.sG_Limits);

    setShow(this.bSecond, this.sRealisticPilotVulnerability);
    setShow(this.bSecond, this.sRealisticNavigationInstruments);

    setShow(this.bSecond, this.sCockpit_Always_On);
    setShow(this.bSecond, this.sNo_Outside_Views);
    setShow(this.bSecond, this.sHead_Shake);
    setShow(this.bSecond, this.sNo_Icons);
    setShow(this.bSecond, this.sNo_Padlock);
    setShow(this.bSecond, this.sClouds);
    setShow(this.bSecond, this.sNoInstantSuccess);
    setShow(this.bSecond, this.sTakeoff_N_Landing);
    setShow(this.bSecond, this.sRealistic_Landings);
    setShow(this.bSecond, this.sNoSpeedBar);

    setShow(this.bThird, this.sNo_Map_Icons);
    setShow(this.bThird, this.sNo_Player_Icon);
    setShow(this.bThird, this.sNo_Fog_Of_War_Icons);
    setShow(this.bThird, this.sNoMinimapPath);

    this.dialogClient.doResolutionChanged();
    this.dialogClient.setPosSize();
  }

  private void reset() {
    DifficultySettings localDifficultySettings = settings();

    this.sReliability.setChecked(localDifficultySettings.Reliability, false);
    this.sG_Limits.setChecked(localDifficultySettings.G_Limits, false);
    this.sRealisticPilotVulnerability.setChecked(localDifficultySettings.RealisticPilotVulnerability, false);
    this.sRealisticNavigationInstruments.setChecked(localDifficultySettings.RealisticNavigationInstruments, false);

    this.sNo_Player_Icon.setChecked(localDifficultySettings.No_Player_Icon, false);
    this.sNo_Fog_Of_War_Icons.setChecked(localDifficultySettings.No_Fog_Of_War_Icons, false);

    this.sWind_N_Turbulence.setChecked(localDifficultySettings.Wind_N_Turbulence, false);
    this.sFlutter_Effect.setChecked(localDifficultySettings.Flutter_Effect, false);
    this.sStalls_N_Spins.setChecked(localDifficultySettings.Stalls_N_Spins, false);
    this.sBlackouts_N_Redouts.setChecked(localDifficultySettings.Blackouts_N_Redouts, false);
    this.sEngine_Overheat.setChecked(localDifficultySettings.Engine_Overheat, false);
    this.sTorque_N_Gyro_Effects.setChecked(localDifficultySettings.Torque_N_Gyro_Effects, false);
    this.sRealistic_Landings.setChecked(localDifficultySettings.Realistic_Landings, false);
    this.sTakeoff_N_Landing.setChecked(localDifficultySettings.Takeoff_N_Landing, false);
    this.sCockpit_Always_On.setChecked(localDifficultySettings.Cockpit_Always_On, false);
    this.sNo_Outside_Views.setChecked(localDifficultySettings.No_Outside_Views, false);
    this.sHead_Shake.setChecked(localDifficultySettings.Head_Shake, false);
    this.sNo_Icons.setChecked(localDifficultySettings.No_Icons, false);
    this.sNo_Map_Icons.setChecked(localDifficultySettings.No_Map_Icons, false);
    this.sRealistic_Gunnery.setChecked(localDifficultySettings.Realistic_Gunnery, false);
    this.sLimited_Ammo.setChecked(localDifficultySettings.Limited_Ammo, false);
    this.sLimited_Fuel.setChecked(localDifficultySettings.Limited_Fuel, false);
    this.sVulnerability.setChecked(localDifficultySettings.Vulnerability, false);
    this.sNo_Padlock.setChecked(localDifficultySettings.No_Padlock, false);
    this.sClouds.setChecked(localDifficultySettings.Clouds, false);
    this.sSeparateEStart.setChecked(localDifficultySettings.SeparateEStart, false);
    this.sNoInstantSuccess.setChecked(localDifficultySettings.NoInstantSuccess, false);
    this.sNoMinimapPath.setChecked(localDifficultySettings.NoMinimapPath, false);
    this.sNoSpeedBar.setChecked(localDifficultySettings.NoSpeedBar, false);
    this.sComplexEManagement.setChecked(localDifficultySettings.ComplexEManagement, false);
    this.sNo_Fog_Of_War_Icons.setEnable(this.sNo_Map_Icons.isChecked() & this.bEnable);
  }

  public void _leave() {
    if (this.bEnable) {
      DifficultySettings localDifficultySettings = settings();

      localDifficultySettings.Reliability = this.sReliability.isChecked();
      localDifficultySettings.RealisticNavigationInstruments = this.sRealisticNavigationInstruments.isChecked();
      localDifficultySettings.G_Limits = this.sG_Limits.isChecked();
      localDifficultySettings.RealisticPilotVulnerability = this.sRealisticPilotVulnerability.isChecked();

      localDifficultySettings.No_Player_Icon = this.sNo_Player_Icon.isChecked();
      localDifficultySettings.No_Fog_Of_War_Icons = this.sNo_Fog_Of_War_Icons.isChecked();

      localDifficultySettings.Wind_N_Turbulence = this.sWind_N_Turbulence.isChecked();
      localDifficultySettings.Flutter_Effect = this.sFlutter_Effect.isChecked();
      localDifficultySettings.Stalls_N_Spins = this.sStalls_N_Spins.isChecked();
      localDifficultySettings.Blackouts_N_Redouts = this.sBlackouts_N_Redouts.isChecked();
      localDifficultySettings.Engine_Overheat = this.sEngine_Overheat.isChecked();
      localDifficultySettings.Torque_N_Gyro_Effects = this.sTorque_N_Gyro_Effects.isChecked();
      localDifficultySettings.Realistic_Landings = this.sRealistic_Landings.isChecked();
      localDifficultySettings.Takeoff_N_Landing = this.sTakeoff_N_Landing.isChecked();
      localDifficultySettings.Cockpit_Always_On = this.sCockpit_Always_On.isChecked();
      localDifficultySettings.No_Outside_Views = this.sNo_Outside_Views.isChecked();
      localDifficultySettings.Head_Shake = this.sHead_Shake.isChecked();
      localDifficultySettings.No_Icons = this.sNo_Icons.isChecked();
      localDifficultySettings.No_Map_Icons = this.sNo_Map_Icons.isChecked();
      localDifficultySettings.Realistic_Gunnery = this.sRealistic_Gunnery.isChecked();
      localDifficultySettings.Limited_Ammo = this.sLimited_Ammo.isChecked();
      localDifficultySettings.Limited_Fuel = this.sLimited_Fuel.isChecked();
      localDifficultySettings.Vulnerability = this.sVulnerability.isChecked();
      localDifficultySettings.No_Padlock = this.sNo_Padlock.isChecked();
      localDifficultySettings.Clouds = this.sClouds.isChecked();
      localDifficultySettings.SeparateEStart = this.sSeparateEStart.isChecked();
      localDifficultySettings.NoInstantSuccess = this.sNoInstantSuccess.isChecked();
      localDifficultySettings.NoMinimapPath = this.sNoMinimapPath.isChecked();
      localDifficultySettings.NoSpeedBar = this.sNoSpeedBar.isChecked();
      localDifficultySettings.ComplexEManagement = this.sComplexEManagement.isChecked();
    }
    this.client.hideWindow();
  }

  protected void clientInit(GWindowRoot paramGWindowRoot)
  {
  }

  public GUIDifficulty(GWindowRoot paramGWindowRoot)
  {
    this(paramGWindowRoot, 17);
  }
  protected GUIDifficulty(GWindowRoot paramGWindowRoot, int paramInt) {
    super(paramInt);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("diff.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bEasy = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNormal = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bHard = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNext = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.sWind_N_Turbulence = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sFlutter_Effect = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sStalls_N_Spins = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sBlackouts_N_Redouts = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sEngine_Overheat = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sTorque_N_Gyro_Effects = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sRealistic_Landings = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sTakeoff_N_Landing = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sCockpit_Always_On = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNo_Outside_Views = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sHead_Shake = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNo_Icons = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNo_Map_Icons = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sRealistic_Gunnery = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sLimited_Ammo = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sLimited_Fuel = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sVulnerability = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNo_Padlock = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sClouds = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sSeparateEStart = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNoInstantSuccess = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNoMinimapPath = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNoSpeedBar = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sComplexEManagement = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    this.sReliability = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sRealisticNavigationInstruments = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sG_Limits = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sRealisticPilotVulnerability = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    this.sNo_Player_Icon = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));
    this.sNo_Fog_Of_War_Icons = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    clientInit(paramGWindowRoot);

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIDifficulty.this.bExit) {
        if (GUIDifficulty.this.bFirst) {
          Main.stateStack().pop();
        }
        else if (GUIDifficulty.this.bSecond)
        {
          GUIDifficulty.this.bFirst = true;
          GUIDifficulty.this.bSecond = false;
          GUIDifficulty.this.bThird = false;
          GUIDifficulty.this.showHide();
        }
        else
        {
          GUIDifficulty.this.bSecond = true;
          GUIDifficulty.this.bFirst = false;
          GUIDifficulty.this.bThird = false;
          GUIDifficulty.this.showHide();
        }
        return true;
      }
      if (paramGWindow == GUIDifficulty.this.bNext) {
        if (GUIDifficulty.this.bFirst)
        {
          GUIDifficulty.this.bFirst = false;
          GUIDifficulty.this.bSecond = true;
          GUIDifficulty.this.bThird = false;
          GUIDifficulty.this.showHide();
        }
        else if (GUIDifficulty.this.bSecond)
        {
          GUIDifficulty.this.bFirst = false;
          GUIDifficulty.this.bSecond = false;
          GUIDifficulty.this.bThird = true;
          GUIDifficulty.this.showHide();
        }
        return true;
      }if (paramGWindow == GUIDifficulty.this.bEasy) {
        GUIDifficulty.this.settings().setEasy();
        GUIDifficulty.this.reset();
        return true;
      }if (paramGWindow == GUIDifficulty.this.bNormal) {
        GUIDifficulty.this.settings().setNormal();
        GUIDifficulty.this.reset();
        return true;
      }if (paramGWindow == GUIDifficulty.this.bHard) {
        GUIDifficulty.this.settings().setRealistic();
        GUIDifficulty.this.reset();
        return true;
      }if (paramGWindow == GUIDifficulty.this.sNo_Map_Icons)
      {
        GUIDifficulty.this.sNo_Fog_Of_War_Icons.setEnable(GUIDifficulty.this.sNo_Map_Icons.isChecked());
        if (!GUIDifficulty.this.sNo_Map_Icons.isChecked())
          GUIDifficulty.this.sNo_Fog_Of_War_Icons.setChecked(true, false);
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(464.0F), x1024(768.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(544.0F), x1024(768.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(577.0F), x1024(224.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Back"));
      if (GUIDifficulty.this.bEnable) {
        draw(x1024(224.0F), y1024(464.0F), x1024(128.0F), y1024(48.0F), 2, GUIDifficulty.this.i18n("diff.Easy"));
        draw(x1024(416.0F), y1024(464.0F), x1024(128.0F), y1024(48.0F), 2, GUIDifficulty.this.i18n("diff.Normal"));
        draw(x1024(608.0F), y1024(464.0F), x1024(128.0F), y1024(48.0F), 2, GUIDifficulty.this.i18n("diff.Hard"));
      }
      if ((GUIDifficulty.this.bFirst) || (GUIDifficulty.this.bSecond))
        draw(x1024(512.0F), y1024(577.0F), x1024(224.0F), y1024(48.0F), 2, GUIDifficulty.this.i18n("diff.Next"));
      if (GUIDifficulty.this.bFirst) {
        draw(x1024(128.0F), y1024(32.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.SeparateEStart"));
        draw(x1024(128.0F), y1024(96.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.ComplexEManagement"));
        draw(x1024(128.0F), y1024(160.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Engine"));
        draw(x1024(128.0F), y1024(224.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Torque"));
        draw(x1024(128.0F), y1024(288.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Flutter"));
        draw(x1024(128.0F), y1024(352.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Wind"));

        draw(x1024(128.0F), y1024(416.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Reliability"));

        draw(x1024(528.0F), y1024(32.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Stalls"));
        draw(x1024(528.0F), y1024(96.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Vulnerability"));
        draw(x1024(528.0F), y1024(160.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Blackouts"));
        draw(x1024(528.0F), y1024(224.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.RealisticGun"));
        draw(x1024(528.0F), y1024(288.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.LimitedAmmo"));
        draw(x1024(528.0F), y1024(352.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.LimitedFuel"));

        draw(x1024(528.0F), y1024(416.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.G_Limits"));
      }
      else if (GUIDifficulty.this.bSecond) {
        draw(x1024(128.0F), y1024(32.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Cockpit"));
        draw(x1024(128.0F), y1024(96.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoOutside"));
        draw(x1024(128.0F), y1024(160.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Head"));
        draw(x1024(128.0F), y1024(224.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoIcons"));
        draw(x1024(128.0F), y1024(288.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoPadlock"));
        draw(x1024(128.0F), y1024(352.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Clouds"));

        draw(x1024(528.0F), y1024(32.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoInstantSuccess"));
        draw(x1024(528.0F), y1024(96.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.Takeoff"));
        draw(x1024(528.0F), y1024(160.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.RealisticLand"));
        draw(x1024(528.0F), y1024(224.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoSpeedBar"));
        draw(x1024(528.0F), y1024(288.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.RealisticNavInstr"));
        draw(x1024(528.0F), y1024(352.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.RealisticPilotVulnerability"));
      }
      else if (GUIDifficulty.this.bThird)
      {
        draw(x1024(128.0F), y1024(32.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoMapIcons"));
        draw(x1024(128.0F), y1024(96.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoPlayerIcon"));
        draw(x1024(128.0F), y1024(160.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoFogOfWarIcons"));
        draw(x1024(128.0F), y1024(224.0F), x1024(272.0F), y1024(48.0F), 0, GUIDifficulty.this.i18n("diff.NoMinimapPath"));
      }
    }

    public void setPosSize() {
      set1024PosSize(92.0F, 72.0F, 832.0F, 656.0F);
      GUIDifficulty.this.sSeparateEStart.setPosC(x1024(88.0F), y1024(56.0F));
      GUIDifficulty.this.sComplexEManagement.setPosC(x1024(88.0F), y1024(120.0F));
      GUIDifficulty.this.sEngine_Overheat.setPosC(x1024(88.0F), y1024(184.0F));
      GUIDifficulty.this.sTorque_N_Gyro_Effects.setPosC(x1024(88.0F), y1024(248.0F));
      GUIDifficulty.this.sFlutter_Effect.setPosC(x1024(88.0F), y1024(312.0F));
      GUIDifficulty.this.sWind_N_Turbulence.setPosC(x1024(88.0F), y1024(376.0F));

      GUIDifficulty.this.sReliability.setPosC(x1024(88.0F), y1024(440.0F));

      GUIDifficulty.this.sStalls_N_Spins.setPosC(x1024(488.0F), y1024(56.0F));
      GUIDifficulty.this.sVulnerability.setPosC(x1024(488.0F), y1024(120.0F));
      GUIDifficulty.this.sBlackouts_N_Redouts.setPosC(x1024(488.0F), y1024(184.0F));
      GUIDifficulty.this.sRealistic_Gunnery.setPosC(x1024(488.0F), y1024(248.0F));
      GUIDifficulty.this.sLimited_Ammo.setPosC(x1024(488.0F), y1024(312.0F));
      GUIDifficulty.this.sLimited_Fuel.setPosC(x1024(488.0F), y1024(376.0F));

      GUIDifficulty.this.sG_Limits.setPosC(x1024(488.0F), y1024(440.0F));

      GUIDifficulty.this.sCockpit_Always_On.setPosC(x1024(88.0F), y1024(56.0F));
      GUIDifficulty.this.sNo_Outside_Views.setPosC(x1024(88.0F), y1024(120.0F));
      GUIDifficulty.this.sHead_Shake.setPosC(x1024(88.0F), y1024(184.0F));
      GUIDifficulty.this.sNo_Icons.setPosC(x1024(88.0F), y1024(248.0F));
      GUIDifficulty.this.sNo_Padlock.setPosC(x1024(88.0F), y1024(312.0F));
      GUIDifficulty.this.sClouds.setPosC(x1024(88.0F), y1024(376.0F));

      GUIDifficulty.this.sNoInstantSuccess.setPosC(x1024(488.0F), y1024(56.0F));
      GUIDifficulty.this.sTakeoff_N_Landing.setPosC(x1024(488.0F), y1024(120.0F));
      GUIDifficulty.this.sRealistic_Landings.setPosC(x1024(488.0F), y1024(184.0F));
      GUIDifficulty.this.sNoSpeedBar.setPosC(x1024(488.0F), y1024(248.0F));
      GUIDifficulty.this.sRealisticNavigationInstruments.setPosC(x1024(488.0F), y1024(312.0F));
      GUIDifficulty.this.sRealisticPilotVulnerability.setPosC(x1024(488.0F), y1024(376.0F));

      GUIDifficulty.this.sNo_Map_Icons.setPosC(x1024(88.0F), y1024(56.0F));
      GUIDifficulty.this.sNo_Player_Icon.setPosC(x1024(88.0F), y1024(120.0F));
      GUIDifficulty.this.sNo_Fog_Of_War_Icons.setPosC(x1024(88.0F), y1024(184.0F));
      GUIDifficulty.this.sNoMinimapPath.setPosC(x1024(88.0F), y1024(248.0F));

      GUIDifficulty.this.bExit.setPosC(x1024(56.0F), y1024(602.0F));
      GUIDifficulty.this.bEasy.setPosC(x1024(392.0F), y1024(488.0F));
      GUIDifficulty.this.bNormal.setPosC(x1024(584.0F), y1024(488.0F));
      GUIDifficulty.this.bHard.setPosC(x1024(776.0F), y1024(488.0F));
      GUIDifficulty.this.bNext.setPosC(x1024(776.0F), y1024(602.0F));
    }
  }
}