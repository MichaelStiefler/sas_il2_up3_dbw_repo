package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.net.USGS;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.sound.AudioDevice;
import java.util.ArrayList;

public class GUINetServerDBrief extends GUIBriefing
{
  public void enter(GameState paramGameState)
  {
    super.enter(paramGameState);
    if ((paramGameState != null) && ((paramGameState.id() == 42) || (paramGameState.id() == 38)))
    {
      if (this.jdField_briefSound_of_type_JavaLangString != null) {
        String str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((NetUser)NetEnv.host()).getArmy());
        if (str != null)
          this.jdField_briefSound_of_type_JavaLangString = str;
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.jdField_briefSound_of_type_JavaLangString);
        CmdEnv.top().exec("music PLAY");
      }
    }
  }

  public void leave(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 42))
    {
      if (this.jdField_briefSound_of_type_JavaLangString != null) {
        CmdEnv.top().exec("music POP");
        CmdEnv.top().exec("music STOP");
        this.jdField_briefSound_of_type_JavaLangString = null;
      }
    }
    super.leave(paramGameState);
  }
  public void leavePop(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 2) && 
      (this.jdField_briefSound_of_type_JavaLangString != null)) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }

    super.leavePop(paramGameState);
  }

  protected void fillTextDescription() {
    super.fillTextDescription();
    prepareTextDescription(Army.amountNet());
  }
  protected String textDescription() {
    if (this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString == null) return null;
    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    if (i < 0)
      return this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString[0];
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(i);
    return this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString[localBornPlace.army];
  }

  private boolean isValidArming() {
    UserCfg localUserCfg = World.cur().userCfg;
    if (localUserCfg.netRegiment == null) return false;
    if ((((NetUser)NetEnv.host()).netUserRegiment.isEmpty()) && (Actor.getByName(localUserCfg.netRegiment) == null))
      return false;
    if (localUserCfg.netAirName == null) return false;
    if (Property.value(localUserCfg.netAirName, "airClass", null) == null) return false;
    if (localUserCfg.getWeapon(localUserCfg.netAirName) == null) return false; try
    {
      Class localClass1 = (Class)Property.value(localUserCfg.netAirName, "airClass", null);

      NetUser localNetUser = (NetUser)NetEnv.host();
      int i = localNetUser.getBornPlace();
      BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(i);
      if (localBornPlace.airNames != null) {
        ArrayList localArrayList = localBornPlace.airNames;
        int j = 0;
        for (int k = 0; k < localArrayList.size(); k++) {
          String str = (String)localArrayList.get(k);
          Class localClass2 = (Class)Property.value(str, "airClass", null);
          if ((localClass2 == null) || 
            (localClass1 != localClass2)) continue;
          j = 1;
          break;
        }

        if (j == 0)
          return false;
      }
      return Aircraft.weaponsExist(localClass1, localUserCfg.getWeapon(localUserCfg.netAirName)); } catch (Exception localException) {
    }
    return false;
  }

  private boolean isValidBornPlace() {
    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    if ((i < 0) || (i >= World.cur().bornPlaces.size())) {
      new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("brief.BornPlace"), i18n("brief.BornPlaceSelect"), 3, 0.0F);

      return false;
    }
    int j = localNetUser.getAirdromeStay();
    if (j < 0) {
      new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);

      return false;
    }
    return true;
  }

  protected void doNext() {
    if (!isValidBornPlace())
      return;
    if (!isValidArming()) {
      GUIAirArming.stateId = 2;
      Main.stateStack().push(55);
      return;
    }

    Main.cur().resetUser();

    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    int j = localNetUser.getAirdromeStay();
    UserCfg localUserCfg = World.cur().userCfg;
    localNetUser.checkReplicateSkin(localUserCfg.netAirName);
    localNetUser.checkReplicateNoseart(localUserCfg.netAirName);
    localNetUser.checkReplicatePilot();
    String str;
    if (localUserCfg.netTacticalNumber < 10) str = "0" + localUserCfg.netTacticalNumber; else
      str = "" + localUserCfg.netTacticalNumber;
    CmdEnv.top().exec("spawn " + ((Class)Property.value(localUserCfg.netAirName, "airClass", null)).getName() + " PLAYER NAME " + (((NetUser)NetEnv.host()).netUserRegiment.isEmpty() ? localUserCfg.netRegiment : "") + localUserCfg.netSquadron + "0" + str + " WEAPONS " + localUserCfg.getWeapon(localUserCfg.netAirName) + " BORNPLACE " + i + " STAYPLACE " + j + " FUEL " + localUserCfg.fuel + " OVR");

    Aircraft localAircraft = World.getPlayerAircraft();
    if (!Actor.isValid(localAircraft)) return;

    GUI.unActivate();
    HotKeyCmd.exec("aircraftView", "CockpitView");
    ForceFeedback.startMission();
    AudioDevice.soundsOn();

    Main.stateStack().change(42);
  }
  protected void doDiff() {
    Main.stateStack().push(41);
  }
  protected void doLoodout() {
    if (!isValidBornPlace())
      return;
    GUIAirArming.stateId = 2;
    Main.stateStack().push(55);
  }
  protected void doBack() {
    GUINetServer.exitServer(true);
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    localDialogClient.draw(localDialogClient.x1024(164.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(140.0F), localDialogClient.y1024(48.0F), 0, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? i18n("main.Quit") : i18n("brief.MainMenu"));

    localDialogClient.draw(localDialogClient.x1024(256.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(528.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Arming"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    this.bLoodout.setPosC(localDialogClient.x1024(742.0F), localDialogClient.y1024(680.0F));
  }

  public GUINetServerDBrief(GWindowRoot paramGWindowRoot) {
    super(39);
    init(paramGWindowRoot);
  }
}