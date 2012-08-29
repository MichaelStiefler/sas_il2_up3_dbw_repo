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
    if ((paramGameState != null) && (this.jdField_briefSound_of_type_JavaLangString != null))
      if (paramGameState.id() == 36) {
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.jdField_briefSound_of_type_JavaLangString);
        CmdEnv.top().exec("music PLAY");
        this._briefSound = this.jdField_briefSound_of_type_JavaLangString;
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
      if (this.jdField_briefSound_of_type_JavaLangString != null) {
        CmdEnv.top().exec("music POP");
        CmdEnv.top().exec("music STOP");
        this.jdField_briefSound_of_type_JavaLangString = null;
        this._briefSound = null;
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
  }

  public boolean isExistTextDescription() {
    return this.jdField_textDescription_of_type_JavaLangString != null;
  }
  public void clearTextDescription() {
    this.jdField_textDescription_of_type_JavaLangString = null;
  }
  public void setTextDescription(String paramString) {
    try {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString, RTSConf.cur.locale);
      this.jdField_textDescription_of_type_JavaLangString = localResourceBundle.getString("Description");
      prepareTextDescription(Army.amountSingle());
    } catch (Exception localException) {
      this.jdField_textDescription_of_type_JavaLangString = null;
      this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString = null;
    }
    this.wScrollDescription.resized();
  }
  protected String textDescription() {
    if (this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString == null) return null;
    if (GUINetAircraft.isSelectedValid()) {
      return this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString[GUINetAircraft.selectedRegiment().getArmy()];
    }
    return this.jdField_textArmyDescription_of_type_ArrayOfJavaLangString[0];
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
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    localDialogClient.draw(localDialogClient.x1024(144.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 0, i18n("brief.Disconnect"));
    localDialogClient.draw(localDialogClient.x1024(256.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(528.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Aircraft"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    this.bLoodout.setPosC(localDialogClient.x1024(742.0F), localDialogClient.y1024(680.0F));
  }

  public GUINetClientCBrief(GWindowRoot paramGWindowRoot) {
    super(46);
    init(paramGWindowRoot);
  }
}