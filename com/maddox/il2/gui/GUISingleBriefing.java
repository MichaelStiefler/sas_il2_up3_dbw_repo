package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

public class GUISingleBriefing extends GUIBriefing
{
  public void enterPush(GameState paramGameState)
  {
    World.cur().diffUser.set(World.cur().userCfg.singleDifficulty);
    super.enterPush(paramGameState);
    if (this.jdField_briefSound_of_type_JavaLangString != null) {
      CmdEnv.top().exec("music PUSH");
      CmdEnv.top().exec("music LIST " + this.jdField_briefSound_of_type_JavaLangString);
      CmdEnv.top().exec("music PLAY");
    }
  }

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 17) {
      World.cur().userCfg.singleDifficulty = World.cur().diffUser.get();
      World.cur().userCfg.saveConf();
    }
    this.client.activateWindow();
  }

  protected void doNext() {
    if (this.jdField_briefSound_of_type_JavaLangString != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music STOP");
    }
    Main.stateStack().change(5);
  }
  protected void doLoodout() {
    Main.stateStack().push(54);
  }

  protected void doDiff() {
    Main.stateStack().push(17);
  }
  protected void doBack() {
    if (this.jdField_briefSound_of_type_JavaLangString != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }
    Main.stateStack().pop();
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    localDialogClient.draw(localDialogClient.x1024(144.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 0, i18n("brief.Back"));
    localDialogClient.draw(localDialogClient.x1024(256.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(528.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Arming"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIBriefingGeneric$DialogClient;
    this.bLoodout.setPosC(localDialogClient.x1024(742.0F), localDialogClient.y1024(680.0F));
  }

  public GUISingleBriefing(GWindowRoot paramGWindowRoot)
  {
    super(4);
    init(paramGWindowRoot);
  }
}