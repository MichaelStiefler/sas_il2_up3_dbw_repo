package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.RTSConf;
import java.util.ResourceBundle;

public class GUICampaignStatView extends GUICampaignStat
{
  public void _enter()
  {
    Scores.enemyAirKill = Main.cur().campaign.enemyAirDestroyed();
    Scores.friendlyKill = Main.cur().campaign.friendDestroyed();
    this.completeMissions = Main.cur().campaign.completeMissions();
    Scores.arrayEnemyGroundKill = Main.cur().campaign.enemyGroundDestroyed();
    Scores.enemyGroundKill = 0;
    if (Scores.arrayEnemyGroundKill != null)
      Scores.enemyGroundKill = Scores.arrayEnemyGroundKill.length;
    Scores.score = Main.cur().campaign.score();
    this.awards = Main.cur().campaign.awards(Scores.score);

    if (this.awards == 0) this.bViewAward.hideWindow(); else {
      this.bViewAward.showWindow();
    }
    int i = Main.cur().campaign.rank();
    this.rank = "";
    try {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("missions/campaign/" + Main.cur().campaign.branch() + "/" + "rank", RTSConf.cur.locale);
      this.rank = localResourceBundle.getString("" + i);
    } catch (Exception localException) {
    }
    DifficultySettings localDifficultySettings = new DifficultySettings();
    localDifficultySettings.set(Main.cur().campaign.difficulty());
    if (localDifficultySettings.isRealistic()) this.diff = i18n("campstat.realistic");
    else if (localDifficultySettings.isNormal()) this.diff = i18n("campstat.normal");
    else if (localDifficultySettings.isEasy()) this.diff = i18n("campstat.easy"); else
      this.diff = i18n("campstat.custom");
    this.iArmy = (Main.cur().campaign.army() - 1);
    updateScrollSizes();
    this.client.activateWindow();
  }
  public void leavePop(GameState paramGameState) {
    Main.cur().campaign = null;
    _leave();
  }
  protected void doRecordSave() {
  }
  protected void doRefly() {
  }
  protected void doNext() {  }

  protected void doExit() { Main.stateStack().pop(); }

  protected void clientRender2()
  {
    GUIStat.DialogClient localDialogClient = this.dialogClient;

    localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("campstat.Back"));
  }

  public GUICampaignStatView(GWindowRoot paramGWindowRoot) {
    super(31);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("campstat.info");
    this.bViewAward = ((GUICampaignStat.WAwardButton)this.dialogClient.addControl(new GUICampaignStat.WAwardButton(this, this.dialogClient)));
    this.bSave.hideWindow();
    this.bNext.hideWindow();
    this.bRefly.hideWindow();
  }
}