package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.sound.AudioDevice;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUINetClientDBrief extends GUIBriefing
{
  public void enter(GameState paramGameState)
  {
    super.enter(paramGameState);
    if ((paramGameState != null) && ((paramGameState.id() == 43) || (paramGameState.id() == 36)))
    {
      if (this.briefSound != null) {
        String str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((NetUser)NetEnv.host()).getArmy());
        if (str != null)
          this.briefSound = str;
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.briefSound);
        CmdEnv.top().exec("music PLAY");
      }
    }
  }

  public void leave(GameState paramGameState) {
    if ((paramGameState != null) && ((paramGameState.id() == 36) || (paramGameState.id() == 43)))
    {
      if (this.briefSound != null) {
        CmdEnv.top().exec("music POP");
        CmdEnv.top().exec("music STOP");
        this.briefSound = null;
      }
    }
    super.leave(paramGameState);
  }
  public void leavePop(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 2) && 
      (this.briefSound != null)) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }

    super.leavePop(paramGameState);
  }
  protected void fillTextDescription() {
  }

  public boolean isExistTextDescription() {
    return this.textDescription != null;
  }
  public void clearTextDescription() {
    this.textDescription = null;
  }
  public void setTextDescription(String paramString) {
    try {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString, RTSConf.cur.locale);
      this.textDescription = localResourceBundle.getString("Description");
      prepareTextDescription(Army.amountNet());
    } catch (Exception localException) {
      this.textDescription = null;
      this.textArmyDescription = null;
    }
    this.wScrollDescription.resized();
  }
  protected String textDescription() {
    if (this.textArmyDescription == null) return null;
    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    if ((i < 0) || (World.cur().bornPlaces == null))
      return this.textArmyDescription[0];
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(i);
    return this.textArmyDescription[localBornPlace.army];
  }

  private boolean isValidBornPlace()
  {
    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    if ((i < 0) || (i >= World.cur().bornPlaces.size())) {
      GUINetClientGuard localGUINetClientGuard1 = (GUINetClientGuard)Main.cur().netChannelListener;
      localGUINetClientGuard1.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("brief.BornPlace"), i18n("brief.BornPlaceSelect"), 3, 0.0F);

      return false;
    }
    int j = localNetUser.getAirdromeStay();
    if (j < 0) {
      GUINetClientGuard localGUINetClientGuard2 = (GUINetClientGuard)Main.cur().netChannelListener;
      localGUINetClientGuard2.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);

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

    AirportCarrier localAirportCarrier = getCarrier((NetUser)NetEnv.host());
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(((NetUser)NetEnv.host()).getBornPlace());
    if ((localAirportCarrier != null) && (!localBornPlace.zutiAirspawnOnly) && (World.cur().diffCur.Takeoff_N_Landing))
    {
      if ((!NetServerParams.isSynched()) && (!localAirportCarrier.ship().zutiIsStatic()))
      {
        localObject = (GUINetClientGuard)Main.cur().netChannelListener;
        ((GUINetClientGuard)localObject).curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("brief.StayPlace"), i18n("brief.StayPlaceWait"), 3, 0.0F);

        return;
      }

      Object localObject = World.cur().userCfg;
      Class localClass = (Class)Property.value(((UserCfg)localObject).netAirName, "airClass", null);
      localAirportCarrier.setGuiCallback(this);
      localAirportCarrier.ship().requestLocationOnCarrierDeck((NetUser)NetEnv.host(), localClass.getName());

      GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
      localGUINetClientGuard.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.ReFlyWait"), i18n("miss.ReFlyWait"), 4, 30.0F);
    }
    else
    {
      fly();
    }
  }

  public void flyFromCarrier(boolean paramBoolean) {
    GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
    localGUINetClientGuard.curMessageBox.close(false);
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(((NetUser)NetEnv.host()).getBornPlace());
    if ((paramBoolean) || (localBornPlace.zutiAirspawnIfCarrierFull))
    {
      fly();
    }
    else
    {
      localGUINetClientGuard.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("brief.CarrierDeckFull"), i18n("brief.CarrierDeckFullWait"), 3, 0.0F);
    }
  }

  protected void fly()
  {
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

    Main.stateStack().change(43);
  }
  protected void doLoodout() {
    if (!isValidBornPlace())
      return;
    GUIAirArming.stateId = 2;
    Main.stateStack().push(55);
  }
  protected void doDiff() {
    Main.stateStack().push(41);
  }
  protected void doBack() {
    GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
    if (localGUINetClientGuard == null)
      return;
    localGUINetClientGuard.dlgDestroy(new GUINetClientGuard.DestroyExec() {
      public void destroy(GUINetClientGuard paramGUINetClientGuard) { paramGUINetClientGuard.destroy(true); } } );
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(5.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Disconnect"));
    localDialogClient.draw(localDialogClient.x1024(194.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(680.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Arming"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    this.bLoodout.setPosC(localDialogClient.x1024(768.0F), localDialogClient.y1024(689.0F));
  }

  public GUINetClientDBrief(GWindowRoot paramGWindowRoot)
  {
    super(40);
    init(paramGWindowRoot);
  }
}