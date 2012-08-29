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
    if (this.briefSound != null) {
      CmdEnv.top().exec("music PUSH");
      CmdEnv.top().exec("music LIST " + this.briefSound);
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
    if (this.briefSound != null) {
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
    if (this.briefSound != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }
    Main.stateStack().pop();
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    localDialogClient.draw(localDialogClient.x1024(0.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(170.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Back"));

    localDialogClient.draw(localDialogClient.x1024(194.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Difficulty"));
    localDialogClient.draw(localDialogClient.x1024(680.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Arming"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    this.bLoodout.setPosC(localDialogClient.x1024(768.0F), localDialogClient.y1024(689.0F));
  }

  public GUISingleBriefing(GWindowRoot paramGWindowRoot)
  {
    super(4);
    init(paramGWindowRoot);
  }
}