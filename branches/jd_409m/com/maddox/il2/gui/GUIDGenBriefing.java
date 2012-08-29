package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.CmdEnv;

public class GUIDGenBriefing extends GUIBriefing
{
  public void enter(GameState paramGameState)
  {
    this.curMissionNum = -1;
    super.enter(paramGameState);
    if ((paramGameState != null) && ((paramGameState.id() == 61) || (paramGameState.id() == 27) || (paramGameState.id() == 64)))
    {
      if (this.jdField_briefSound_of_type_JavaLangString != null) {
        CmdEnv.top().exec("music PUSH");
        CmdEnv.top().exec("music LIST " + this.jdField_briefSound_of_type_JavaLangString);
        CmdEnv.top().exec("music PLAY");
      }
    }
  }

  protected void doLoodout() {
    Main.stateStack().push(54);
  }

  protected void doDiff() {
    Main.stateStack().push(65);
  }
  protected void doNext() {
    if (this.jdField_briefSound_of_type_JavaLangString != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music STOP");
    }
    World.cur().diffUser.set(Main.cur().campaign.difficulty());
    Main.stateStack().change(63);
  }
  protected void doBack() {
    if (this.jdField_briefSound_of_type_JavaLangString != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
    }
    Main.cur().campaign = null;
    Main.cur().currentMissionFile = null;
    Main.stateStack().pop();
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(144.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 0, i18n("brief.MainMenu"));
    localDialogClient.draw(localDialogClient.x1024(256.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Roster"));
    localDialogClient.draw(localDialogClient.x1024(528.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Arming"));
    super.clientRender();
  }
  protected void clientSetPosSize() {
  }
  public GUIDGenBriefing(GWindowRoot paramGWindowRoot) {
    super(62);
    init(paramGWindowRoot);
  }
}