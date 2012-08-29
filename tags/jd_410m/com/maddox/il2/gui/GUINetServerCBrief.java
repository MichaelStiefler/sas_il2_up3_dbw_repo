package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.SectFile;

public class GUINetServerCBrief extends GUIBriefing
{
  private String _briefSound = null;

  public void enter(GameState paramGameState) {
    super.enter(paramGameState);
    if ((paramGameState == null) || (paramGameState.id() != 44)) {
      ((NetUser)NetEnv.host()).setPilot(null);
      ((NetUser)NetEnv.host()).setSkin(null);
      ((NetUser)NetEnv.host()).setNoseart(null);
    }
    if ((paramGameState != null) && (this.briefSound != null))
    {
      String str;
      if ((paramGameState.id() == 69) || (paramGameState.id() == 38))
      {
        if (((NetUser)NetEnv.host()).getArmy() != 0) {
          str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((NetUser)NetEnv.host()).getArmy());
          if (str != null)
            this.briefSound = str;
        }
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.briefSound);
        CmdEnv.top().exec("music PLAY");
        this._briefSound = this.briefSound;
      }
      else if (paramGameState.id() == 44) {
        str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((NetUser)NetEnv.host()).getArmy());
        if (str == null) str = Main.cur().currentMissionFile.get("MAIN", "briefSound");
        if ((str != null) && (!str.equals(this._briefSound))) {
          this._briefSound = str;
          CmdEnv.top().exec("music LIST " + this._briefSound);
        }
      }
    }
  }

  public void leave(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 47))
    {
      if (this.briefSound != null) {
        CmdEnv.top().exec("music POP");
        CmdEnv.top().exec("music STOP");
        this.briefSound = null;
        this._briefSound = null;
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
    super.fillTextDescription();
    prepareTextDescription(Army.amountSingle());
  }
  protected String textDescription() {
    if (this.textArmyDescription == null) return null;
    if (GUINetAircraft.isSelectedValid()) {
      return this.textArmyDescription[GUINetAircraft.selectedRegiment().getArmy()];
    }
    return this.textArmyDescription[0];
  }

  protected void doNext() {
    if (!GUINetAircraft.isSelectedValid()) {
      Main.stateStack().change(44);
      return;
    }
    GUINetAircraft.doFly();
  }

  protected void doDiff() {
    Main.stateStack().push(41);
  }
  protected void doLoodout() {
    Main.stateStack().change(44);
  }
  protected void doBack() {
    GUINetServer.exitServer(true);
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(15.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(140.0F), localDialogClient.y1024(48.0F), 1, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? i18n("main.Quit") : i18n("brief.MainMenu"));

    localDialogClient.draw(localDialogClient.x1024(194.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(680.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Aircraft"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    this.bLoodout.setPosC(localDialogClient.x1024(768.0F), localDialogClient.y1024(689.0F));
  }

  public GUINetServerCBrief(GWindowRoot paramGWindowRoot) {
    super(45);
    init(paramGWindowRoot);
  }
}