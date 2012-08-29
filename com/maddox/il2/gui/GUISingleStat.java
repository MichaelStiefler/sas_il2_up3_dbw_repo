package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.KeyRecord;

public class GUISingleStat extends GUIStat
{
  protected String diff;

  public void _enter()
  {
    DifficultySettings localDifficultySettings = World.cur().diffCur;
    if (localDifficultySettings.isRealistic()) this.diff = i18n("singleStat.realistic");
    else if (localDifficultySettings.isNormal()) this.diff = i18n("singleStat.normal");
    else if (localDifficultySettings.isEasy()) this.diff = i18n("singleStat.easy"); else
      this.diff = i18n("singleStat.custom");
    this.iArmy = (World.getPlayerArmy() - 1);
    super._enter();
    tryShowCapturedMessage();
  }

  protected void doRefly() {
    Main3D.cur3D().keyRecord.clearRecorded();
    Main.stateStack().change(5);
  }

  protected void doNext() {
    Main3D.cur3D().keyRecord.clearRecorded();
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed()))
      Mission.cur().destroy();
    Main.stateStack().pop();
  }

  protected void doExit() {
  }

  protected void clientRender() {
    GUIStat.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIStat$DialogClient;
    GUISeparate.draw(localDialogClient, GColor.Gray, localDialogClient.x1024(32.0F), localDialogClient.y1024(512.0F), localDialogClient.x1024(672.0F), 2.0F);
    GUISeparate.draw(localDialogClient, GColor.Gray, localDialogClient.x1024(416.0F), localDialogClient.y1024(529.0F), 2.0F, localDialogClient.y1024(176.0F));
    localDialogClient.setCanvasFont(0);
    localDialogClient.draw(localDialogClient.x1024(48.0F), localDialogClient.y1024(32.0F), localDialogClient.x1024(640.0F), localDialogClient.y1024(32.0F), 1, i18n("singleStat.airKills") + " " + Scores.enemyAirKill);
    localDialogClient.draw(localDialogClient.x1024(48.0F), localDialogClient.y1024(176.0F), localDialogClient.x1024(640.0F), localDialogClient.y1024(32.0F), 1, i18n("singleStat.groundKills") + " " + Scores.enemyGroundKill);
    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(352.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), 2, i18n("singleStat.score"));
    localDialogClient.draw(localDialogClient.x1024(400.0F), localDialogClient.y1024(352.0F), localDialogClient.x1024(300.0F), localDialogClient.y1024(32.0F), 0, "" + Scores.score);
    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), 2, i18n("singleStat.friendKills"));
    localDialogClient.draw(localDialogClient.x1024(400.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(300.0F), localDialogClient.y1024(32.0F), 0, "" + Scores.friendlyKill);
    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(448.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), 2, i18n("singleStat.diff"));
    localDialogClient.draw(localDialogClient.x1024(400.0F), localDialogClient.y1024(448.0F), localDialogClient.x1024(300.0F), localDialogClient.y1024(32.0F), 0, this.diff);
    if (NetMissionTrack.countRecorded == 0)
      localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(528.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("singleStat.SaveTrack"));
    localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(592.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("singleStat.Done"));
    localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("singleStat.Refly"));

    localDialogClient.draw(localDialogClient.x1024(48.0F), localDialogClient.y1024(560.0F), localDialogClient.x1024(64.0F), localDialogClient.y1024(80.0F), this.texStat);
  }

  protected void clientSetPosSize() {
    GUIStat.DialogClient localDialogClient = this.jdField_dialogClient_of_type_ComMaddoxIl2GuiGUIStat$DialogClient;
    localDialogClient.set1024PosSize(144.0F, 32.0F, 736.0F, 736.0F);
    this.bSave.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(554.0F));
    this.bNext.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(617.0F));
    this.bRefly.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(680.0F));
    this.airScroll.set1024PosSize(32.0F, 80.0F, 672.0F, 80.0F);
    this.groundScroll.set1024PosSize(32.0F, 224.0F, 672.0F, 80.0F);
  }

  public GUISingleStat(GWindowRoot paramGWindowRoot)
  {
    super(6);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("singleStat.info");
    this.bExit.hideWindow();
  }
}