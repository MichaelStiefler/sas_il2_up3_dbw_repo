package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ResourceBundle;

public class GUICampaignStat extends GUIStat
{
  public WAwardButton bViewAward;
  protected GTexture lastAward;
  protected boolean bMissComplete = false;
  protected int completeMissions;
  protected String rank = "";
  protected String diff;
  protected int awards;
  protected boolean bNewRank = false;
  protected boolean bBlick = false;
  protected long blickTime;

  public void enterPush(GameState paramGameState)
  {
    enter(paramGameState); } 
  public void enterPop(GameState paramGameState) { enter(paramGameState); } 
  public void enter(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 58)) {
      Main.cur().currentMissionFile = Main.cur().campaign.nextMission();
      if (Main.cur().currentMissionFile == null) {
        new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
        {
          public void result(int paramInt) {
            GUICampaignStat.this.doExit();
          }
        };
        return;
      }
      Main.stateStack().change(28);
      return;
    }

    if ((paramGameState != null) && (paramGameState.id() != 29) && (paramGameState.id() != 63))
    {
      _enter();
      return;
    }
    this.lastAward = null;
    Scores.compute();
    updateScrollSizes();
    Scores.score += Main.cur().campaign.score();
    Scores.enemyAirKill += Main.cur().campaign.enemyAirDestroyed();
    Scores.friendlyKill += Main.cur().campaign.friendDestroyed();
    this.completeMissions = Main.cur().campaign.completeMissions();
    int[] arrayOfInt1 = Main.cur().campaign.enemyGroundDestroyed();
    int k;
    if (arrayOfInt1 != null) {
      if (Scores.arrayEnemyGroundKill != null) {
        int[] arrayOfInt2 = new int[arrayOfInt1.length + Scores.enemyGroundKill];
        j = 0;
        for (k = 0; k < arrayOfInt1.length; k++)
          arrayOfInt2[(j++)] = arrayOfInt1[k];
        for (k = 0; k < Scores.arrayEnemyGroundKill.length; k++)
          arrayOfInt2[(j++)] = Scores.arrayEnemyGroundKill[k];
        Scores.arrayEnemyGroundKill = arrayOfInt2;
      } else {
        Scores.arrayEnemyGroundKill = arrayOfInt1;
      }
      Scores.enemyGroundKill = Scores.arrayEnemyGroundKill.length;
    }

    if (!Actor.isAlive(World.getPlayerAircraft())) {
      Main.cur().campaign.incAircraftLost();
    }
    int i = Main.cur().campaign.newRank(Scores.score);
    int j = -1;
    if ((!World.isPlayerDead()) && (!World.isPlayerCaptured()) && ((World.cur().targetsGuard.isTaskComplete()) || (Time.current() / 1000.0D / 60.0D > 20.0D) || (!World.cur().diffCur.NoInstantSuccess)))
    {
      this.bMissComplete = true;
      this.bNext.showWindow();
      this.completeMissions += 1;
      k = Main.cur().campaign.rank();
      this.bNewRank = (i != k);

      this.awards = Main.cur().campaign.awards(Scores.score);
      int m = Main.cur().campaign.awards(Main.cur().campaign.score());
      if (this.awards > m)
        j = this.awards - 1;
    } else {
      this.bMissComplete = false;
      this.bNext.hideWindow();
      i = Main.cur().campaign.rank();
      this.bNewRank = false;
      this.awards = Main.cur().campaign.awards(Main.cur().campaign.score());
    }
    this.blickTime = Time.currentReal();
    this.bBlick = false;

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

    if (this.awards == 0) this.bViewAward.hideWindow(); else
      this.bViewAward.showWindow();
    if (j >= 0) {
      int[] arrayOfInt3 = Main.cur().campaign.getAwards(Scores.score);
      if ((Main.cur().campaign.branch().equals("de")) && (World.cur().isHakenAllowed()))
        this.lastAward = GTexture.New("missions/campaign/de/awardh" + arrayOfInt3[j] + ".mat");
      else if ((Main.cur().campaign.branch().equals("fi")) && (World.cur().isHakenAllowed()))
        this.lastAward = GTexture.New("missions/campaign/fi/awardh" + arrayOfInt3[j] + ".mat");
      else
        this.lastAward = GTexture.New("missions/campaign/" + Main.cur().campaign.branch() + "/award" + arrayOfInt3[j] + ".mat");
    }
    updateScrollSizes();

    _enter();
    tryShowCapturedMessage();
  }
  public void _enter() {
    this.client.activateWindow();
  }

  private void saveCampaign() {
    Campaign localCampaign = Main.cur().campaign;
    try {
      String str1 = localCampaign.branch() + localCampaign.missionsDir();
      String str2 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      SectFile localSectFile = new SectFile(str2, 1, false, World.cur().userCfg.krypto());
      if (this.bMissComplete) {
        if (localCampaign.isComplete())
          localCampaign.clearSavedStatics(localSectFile);
        else
          localCampaign.saveStatics(localSectFile);
      }
      localSectFile.set("list", str1, localCampaign, true);
      localSectFile.saveFile();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      return;
    }
  }

  protected void doRefly() {
    this.lastAward = null;
    Main3D.cur3D().keyRecord.clearRecorded();
    Main.stateStack().change(29);
  }

  protected void doNext() {
    if (!this.bMissComplete)
      return;
    this.lastAward = null;
    Main.cur().campaign.currentMissionComplete(Scores.score, Scores.enemyAirKill, Scores.arrayEnemyGroundKill, Scores.friendlyKill);
    String str;
    if (Main.cur().campaign.isComplete()) {
      str = Main.cur().campaign.epilogueTrack();
      doExit();
      if (str != null) {
        GUIBWDemoPlay.demoFile = str;
        GUIBWDemoPlay.soundFile = null;
        Main.stateStack().push(58);
      }
    } else {
      saveCampaign();
      Main3D.cur3D().keyRecord.clearRecorded();
      if ((Mission.cur() != null) && (!Mission.cur().isDestroyed())) {
        Mission.cur().destroy();
      }
      Main.cur().campaign.doExternalGenerator();

      str = Main.cur().campaign.nextIntro();
      if (str != null) {
        GUIBWDemoPlay.demoFile = str;
        GUIBWDemoPlay.soundFile = null;
        Main.stateStack().push(58);
        return;
      }
      Main.cur().currentMissionFile = Main.cur().campaign.nextMission();
      if (Main.cur().currentMissionFile == null) {
        new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
        {
          public void result(int paramInt) {
            GUICampaignStat.this.doExit();
          }
        };
        return;
      }
      Main.stateStack().change(28);
    }
  }

  protected void doExit() {
    this.lastAward = null;
    saveCampaign();
    Main3D.cur3D().keyRecord.clearRecorded();
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed()))
      Mission.cur().destroy();
    Main.cur().campaign = null;
    Main.cur().currentMissionFile = null;
    Main.stateStack().pop();
  }

  protected void clientRender()
  {
    GUIStat.DialogClient localDialogClient = this.dialogClient;
    GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)localDialogClient.lookAndFeel();
    GUISeparate.draw(localDialogClient, GColor.Gray, localDialogClient.x1024(32.0F), localDialogClient.y1024(448.0F), localDialogClient.x1024(672.0F), 2.0F);
    GUISeparate.draw(localDialogClient, GColor.Gray, localDialogClient.x1024(416.0F), localDialogClient.y1024(464.0F), 2.0F, localDialogClient.y1024(240.0F));
    localDialogClient.setCanvasColor(GColor.Gray);
    localDialogClient.setCanvasFont(0);
    localDialogClient.draw(localDialogClient.x1024(48.0F), localDialogClient.y1024(32.0F), localDialogClient.x1024(640.0F), localDialogClient.y1024(32.0F), 1, i18n("campstat.AirKills") + " " + Scores.enemyAirKill);
    localDialogClient.draw(localDialogClient.x1024(48.0F), localDialogClient.y1024(160.0F), localDialogClient.x1024(640.0F), localDialogClient.y1024(32.0F), 1, i18n("campstat.GroundKills") + " " + Scores.enemyGroundKill);

    localDialogClient.draw(localDialogClient.x1024(80.0F), localDialogClient.y1024(288.0F), localDialogClient.x1024(256.0F), localDialogClient.y1024(32.0F), 1, i18n("campstat.RANK"));
    localGUILookAndFeel.drawBevel(localDialogClient, localDialogClient.x1024(32.0F), localDialogClient.y1024(320.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    if (this.bNewRank) {
      long l1 = Time.currentReal();
      long l2 = l1 - this.blickTime;
      if (l2 > 1000L) {
        this.bBlick = (!this.bBlick);
        this.blickTime = l1;
      }
      if (this.bBlick)
        localDialogClient.setCanvasColor(65535);
    }
    localDialogClient.draw(localDialogClient.x1024(32.0F) + localGUILookAndFeel.bevelComboDown.L.dx, localDialogClient.y1024(320.0F), localDialogClient.x1024(352.0F) - localGUILookAndFeel.bevelComboDown.L.dx - localGUILookAndFeel.bevelComboDown.R.dx, localDialogClient.y1024(32.0F), 1, this.rank);

    if (this.bBlick) {
      localDialogClient.setCanvasColor(GColor.Gray);
    }
    localDialogClient.draw(localDialogClient.x1024(80.0F), localDialogClient.y1024(368.0F), localDialogClient.x1024(256.0F), localDialogClient.y1024(32.0F), 1, i18n("campstat.SCORE"));
    localGUILookAndFeel.drawBevel(localDialogClient, localDialogClient.x1024(32.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(400.0F), localDialogClient.x1024(352.0F), localDialogClient.y1024(32.0F), 1, "" + Scores.score);

    localDialogClient.draw(localDialogClient.x1024(416.0F), localDialogClient.y1024(288.0F), localDialogClient.x1024(288.0F), localDialogClient.y1024(32.0F), 1, i18n("campstat.AWARDS"));
    if (this.awards == 0) {
      localGUILookAndFeel.drawBevel(localDialogClient, localDialogClient.x1024(480.0F), localDialogClient.y1024(320.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(112.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    }
    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(498.0F), localDialogClient.x1024(256.0F), localDialogClient.y1024(32.0F), 2, i18n("campstat.Total"));
    localDialogClient.draw(localDialogClient.x1024(304.0F), localDialogClient.y1024(496.0F), localDialogClient.x1024(96.0F), localDialogClient.y1024(32.0F), 0, "" + Main.cur().campaign.completeMissions());

    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(544.0F), localDialogClient.x1024(256.0F), localDialogClient.y1024(32.0F), 2, i18n("campstat.Lost"));
    localDialogClient.draw(localDialogClient.x1024(304.0F), localDialogClient.y1024(544.0F), localDialogClient.x1024(96.0F), localDialogClient.y1024(32.0F), 0, "" + Main.cur().campaign.aircraftLost());

    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(592.0F), localDialogClient.x1024(256.0F), localDialogClient.y1024(32.0F), 2, i18n("campstat.Friendly_kills"));
    localDialogClient.draw(localDialogClient.x1024(304.0F), localDialogClient.y1024(592.0F), localDialogClient.x1024(96.0F), localDialogClient.y1024(32.0F), 0, "" + Scores.friendlyKill);

    localDialogClient.draw(localDialogClient.x1024(32.0F), localDialogClient.y1024(640.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(32.0F), 2, i18n("campstat.Difficulty"));
    localGUILookAndFeel.drawBevel(localDialogClient, localDialogClient.x1024(208.0F), localDialogClient.y1024(640.0F), localDialogClient.x1024(192.0F), localDialogClient.y1024(32.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    localDialogClient.draw(localDialogClient.x1024(208.0F) + localGUILookAndFeel.bevelComboDown.L.dx, localDialogClient.y1024(640.0F), localDialogClient.x1024(192.0F) - localGUILookAndFeel.bevelComboDown.L.dx - localGUILookAndFeel.bevelComboDown.R.dx, localDialogClient.y1024(32.0F), 0, this.diff);

    clientRender2();
  }

  protected void clientRender2() {
    GUIStat.DialogClient localDialogClient = this.dialogClient;

    if (NetMissionTrack.countRecorded == 0)
      localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(464.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("campstat.SaveTrack"));
    if (this.bMissComplete)
      localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(528.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("campstat.Apply"));
    localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(592.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("campstat.Refly"));
    localDialogClient.draw(localDialogClient.x1024(496.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 0, i18n("campstat.MainMenu"));
  }

  protected void clientSetPosSize() {
    GUIStat.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.set1024PosSize(144.0F, 32.0F, 736.0F, 736.0F);
    this.airScroll.set1024PosSize(32.0F, 64.0F, 672.0F, 80.0F);
    this.groundScroll.set1024PosSize(32.0F, 192.0F, 672.0F, 80.0F);

    this.bViewAward.set1024PosSize(480.0F, 320.0F, 160.0F, 112.0F);

    this.bSave.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(488.0F));
    this.bNext.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(552.0F));
    this.bRefly.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(617.0F));
    this.bExit.setPosC(localDialogClient.x1024(456.0F), localDialogClient.y1024(680.0F));
  }

  public GUICampaignStat(int paramInt) {
    super(paramInt);
  }

  public GUICampaignStat(GWindowRoot paramGWindowRoot) {
    super(30);
    init(paramGWindowRoot);
    this.infoMenu.info = i18n("campstat.info");
    this.bViewAward = ((WAwardButton)this.dialogClient.addControl(new WAwardButton(this.dialogClient)));
  }

  class WAwardButton extends GWindowButton
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramInt1, paramInt2);
      if (GUICampaignStat.this.bMissComplete) GUIAwards.indexIcons = Main.cur().campaign.getAwards(Scores.score); else
        GUIAwards.indexIcons = Main.cur().campaign.getAwards(Main.cur().campaign.score());
      Main.stateStack().push(32);
      return true;
    }
    public void render() {
      super.render();
      if (GUICampaignStat.this.lastAward != null) {
        setCanvasColorWHITE();
        int i = this.root.C.alpha;
        this.root.C.alpha = 255;
        if (this.bDown) draw(this.win.dx / 5.0F + 1.0F, this.win.dy / 5.0F + 1.0F, 3.0F * this.win.dx / 5.0F, 3.0F * this.win.dy / 5.0F, GUICampaignStat.this.lastAward); else
          draw(this.win.dx / 5.0F, this.win.dy / 5.0F, 3.0F * this.win.dx / 5.0F, 3.0F * this.win.dy / 5.0F, GUICampaignStat.this.lastAward);
        this.root.C.alpha = i;
      }
    }

    public WAwardButton(GWindow arg2) {
      super();
      this.cap = new GCaption(GUICampaignStat.this.i18n("campstat.View"));
      this.align = 1;
    }
  }
}