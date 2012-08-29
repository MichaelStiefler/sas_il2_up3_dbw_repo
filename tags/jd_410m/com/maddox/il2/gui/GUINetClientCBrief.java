package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.util.ResourceBundle;

public class GUINetClientCBrief extends GUIBriefing
{
  private String _briefSound = null;

  public void enter(GameState paramGameState) {
    super.enter(paramGameState);
    if ((paramGameState != null) && (this.briefSound != null))
      if (paramGameState.id() == 36) {
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.briefSound);
        CmdEnv.top().exec("music PLAY");
        this._briefSound = this.briefSound;
      }
      else if (paramGameState.id() == 44) {
        String str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + ((NetUser)NetEnv.host()).getArmy());
        if (str == null) str = Main.cur().currentMissionFile.get("MAIN", "briefSound");
        if ((str != null) && (!str.equals(this._briefSound))) {
          this._briefSound = str;
          CmdEnv.top().exec("music LIST " + this._briefSound);
        }
      }
  }

  public void leave(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 48))
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
      prepareTextDescription(Army.amountSingle());
    } catch (Exception localException) {
      this.textDescription = null;
      this.textArmyDescription = null;
    }
    this.wScrollDescription.resized();
  }
  protected String textDescription() {
    if (this.textArmyDescription == null) return null;
    if (GUINetAircraft.isSelectedValid()) {
      return this.textArmyDescription[GUINetAircraft.selectedRegiment().getArmy()];
    }
    return this.textArmyDescription[0];
  }

  private boolean isValidAircraft() {
    return false;
  }

  protected void doNext() {
    if (!GUINetAircraft.isSelectedValid()) {
      Main.stateStack().change(44);
      return;
    }
    GUINetAircraft.doFly();
  }
  protected void doLoodout() {
    Main.stateStack().change(44);
  }
  protected void doDiff() {
    Main.stateStack().push(41);
  }
  protected void doBack() {
    GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
    localGUINetClientGuard.dlgDestroy(new GUINetClientGuard.DestroyExec() {
      public void destroy(GUINetClientGuard paramGUINetClientGuard) { paramGUINetClientGuard.destroy(true); } } );
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    localDialogClient.draw(localDialogClient.x1024(5.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Disconnect"));
    localDialogClient.draw(localDialogClient.x1024(194.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(680.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Aircraft"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    this.bLoodout.setPosC(localDialogClient.x1024(768.0F), localDialogClient.y1024(689.0F));
  }

  public GUINetClientCBrief(GWindowRoot paramGWindowRoot) {
    super(46);
    init(paramGWindowRoot);
  }
}