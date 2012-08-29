package com.maddox.il2.gui;

import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.EventLog.Action;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIDGenDeBriefing extends GUIDeBriefing
{
  private static final String fileNameDebrifing = "dgen/debrifing.txt";
  private String sound = null;

  protected boolean bMissComplete = false;
  private static final int ICON_AAA = 0;
  private static final int ICON_Bailed = 1;
  private static final int ICON_Bridge = 2;
  private static final int ICON_Car = 3;
  private static final int ICON_Crashed = 4;
  private static final int ICON_Gun = 5;
  private static final int ICON_Landed = 6;
  private static final int ICON_Plane = 7;
  private static final int ICON_Ship = 8;
  private static final int ICON_Tank = 9;
  private static final int ICON_Train = 10;
  private static final int ICON_Pilot = 11;
  private static final int ICON_Airstatic = 12;
  private static final int ICON_RadarRadio = 13;
  private Mat[] iconAction = new Mat[14];

  private HashMap assPilot = new HashMap();
  private HashMap assType = new HashMap();

  private String[] tip = new String[4];

  private float[] lineXYZ = new float[6];

  public void enter(GameState paramGameState)
  {
    super.enter(paramGameState);
    if (this.sound != null) {
      CmdEnv.top().exec("music PUSH");
      CmdEnv.top().exec("music LIST " + this.sound);
      CmdEnv.top().exec("music PLAY");
    }
  }

  public void leave(GameState paramGameState) {
    if (this.sound != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music STOP");
      this.sound = null;
    }
    super.leave(paramGameState);
  }

  public void enterPop(GameState paramGameState)
  {
    if (paramGameState.id() == 58) {
      Main.cur().currentMissionFile = Main.cur().campaign.nextMission();
      if (Main.cur().currentMissionFile == null) {
        new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
        {
          public void result(int paramInt) {
            GUIDGenDeBriefing.this.doBack();
          }
        };
        return;
      }
      Main.stateStack().change(62);
      return;
    }
    if (this.sound != null) {
      CmdEnv.top().exec("music POP");
      CmdEnv.top().exec("music PLAY");
      this.sound = null;
    }
    this.client.activateWindow();
  }

  public void _enter()
  {
    preparePilotAssociation();
    prepareTypeAssociation();

    if (NetMissionTrack.countRecorded == 0)
      this.bDifficulty.showWindow();
    else {
      this.bDifficulty.hideWindow();
    }
    Scores.compute();

    doExternalDebrifingGenerator();

    Scores.score += Main.cur().campaign.score();
    Scores.enemyAirKill += Main.cur().campaign.enemyAirDestroyed();
    Scores.friendlyKill += Main.cur().campaign.friendDestroyed();
    int[] arrayOfInt1 = Main.cur().campaign.enemyGroundDestroyed();
    if (arrayOfInt1 != null) {
      if (Scores.arrayEnemyGroundKill != null) {
        int[] arrayOfInt2 = new int[arrayOfInt1.length + Scores.enemyGroundKill];
        int i = 0;
        for (int j = 0; j < arrayOfInt1.length; j++)
          arrayOfInt2[(i++)] = arrayOfInt1[j];
        for (j = 0; j < Scores.arrayEnemyGroundKill.length; j++)
          arrayOfInt2[(i++)] = Scores.arrayEnemyGroundKill[j];
        Scores.arrayEnemyGroundKill = arrayOfInt2;
      } else {
        Scores.arrayEnemyGroundKill = arrayOfInt1;
      }
      Scores.enemyGroundKill = Scores.arrayEnemyGroundKill.length;
    }

    if (!Actor.isAlive(World.getPlayerAircraft())) {
      Main.cur().campaign.incAircraftLost();
    }
    if ((!World.isPlayerDead()) && (!World.isPlayerCaptured()) && ((World.cur().targetsGuard.isTaskComplete()) || (Time.current() / 1000.0D / 60.0D > 20.0D) || (!World.cur().diffCur.NoInstantSuccess)))
    {
      this.bMissComplete = true;
      this.bNext.showWindow();
    } else {
      this.bMissComplete = false;
      this.bNext.hideWindow();
    }

    super._enter();
  }

  private void doExternalDebrifingGenerator() {
    String str1 = "DGen.exe";
    Campaign localCampaign = Main.cur().campaign;
    try {
      String str2 = "debrief missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/ " + localCampaign.difficulty() + " " + Scores.score + " " + localCampaign.rank() + " " + "dgen/debrifing.txt";

      Runtime localRuntime = Runtime.getRuntime();

      Process localProcess = localRuntime.exec(str1 + " " + str2);

      localProcess.waitFor();
    }
    catch (Throwable localThrowable) {
      System.out.println(localThrowable.getMessage());
      localThrowable.printStackTrace();
    }
  }

  private void saveCampaign()
  {
    saveCampaign(true);
  }
  private void saveCampaign(boolean paramBoolean) {
    Campaign localCampaign = Main.cur().campaign;
    try {
      String str1 = localCampaign.branch() + localCampaign.missionsDir();
      String str2 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      SectFile localSectFile = new SectFile(str2, 1, false, World.cur().userCfg.krypto());
      if ((paramBoolean) && 
        (this.bMissComplete)) {
        if (localCampaign.isComplete())
          localCampaign.clearSavedStatics(localSectFile);
        else {
          localCampaign.saveStatics(localSectFile);
        }
      }
      localSectFile.set("list", str1, localCampaign, true);
      localSectFile.saveFile();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      return;
    }
  }

  protected void fillTextDescription() {
    BufferedReader localBufferedReader = null;
    this.textDescription = null;
    this.sound = null;
    try {
      localBufferedReader = new BufferedReader(new SFSReader("dgen/debrifing.txt", RTSConf.charEncoding));
      StringBuffer localStringBuffer = null;
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        int i = str.length();
        if (i == 0)
          continue;
        if (str.startsWith("SOUND ")) {
          this.sound = str.substring("SOUND ".length());
        } else {
          str = UnicodeTo8bit.load(str, false);
          if (localStringBuffer == null) {
            localStringBuffer = new StringBuffer(str);
          }
          else {
            localStringBuffer.append('\n');
            localStringBuffer.append(str);
          }
        }
      }
      localBufferedReader.close();
      if (localStringBuffer != null)
        this.textDescription = localStringBuffer.toString();
    } catch (Exception localException1) {
      if (localBufferedReader != null) try {
          localBufferedReader.close(); } catch (Exception localException2) {
        } System.out.println("Debrifing text load failed: " + localException1.getMessage());
      localException1.printStackTrace();
    }
  }

  private void updatePlayerScore() {
    BufferedReader localBufferedReader = null;
    try {
      Campaign localCampaign = Main.cur().campaign;
      String str = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/squadron.dat";
      localBufferedReader = new BufferedReader(new SFSReader(str, RTSConf.charEncoding));
      GUIDGenRoster localGUIDGenRoster = (GUIDGenRoster)GameState.get(65);
      GUIDGenRoster.Pilot localPilot = localGUIDGenRoster.loadPilot(localBufferedReader);
      localBufferedReader.close();
      localCampaign._rank = localPilot.rank;
      localCampaign._nawards = localPilot.nmedals;
      saveCampaign(false);
    } catch (Exception localException1) {
      if (localBufferedReader != null) try {
          localBufferedReader.close(); } catch (Exception localException2) {
        } System.out.println("Squadron file load failed: " + localException1.getMessage());
      localException1.printStackTrace();
    }
  }

  private void preparePilotAssociation()
  {
    this.assPilot.clear();
    GUIDGenRoster localGUIDGenRoster = (GUIDGenRoster)GameState.get(65);
    localGUIDGenRoster.loadPilotList();
    if (localGUIDGenRoster.pilotPlayer == null) return; try
    {
      Campaign localCampaign = Main.cur().campaign;
      String str1 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir() + "/status.dat";
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(str1, RTSConf.charEncoding));
      for (int i = 0; i < 9; i++) {
        localBufferedReader.readLine();
      }
      while (localBufferedReader.ready())
      {
        String str2 = localBufferedReader.readLine();
        if (str2 == null)
          break;
        int j = str2.length();
        if (j == 0)
          continue;
        str2 = UnicodeTo8bit.load(str2, false);
        SharedTokenizer.set(str2);
        String str3 = SharedTokenizer.next();
        String str4 = SharedTokenizer.next();
        String str5 = SharedTokenizer.next();
        String str6 = SharedTokenizer.getGap();
        if ((str6 == null) || ("None,None".equals(str6)))
          continue;
        for (int k = 0; k < localGUIDGenRoster.pilots.size(); k++) {
          GUIDGenRoster.Pilot localPilot = (GUIDGenRoster.Pilot)localGUIDGenRoster.pilots.get(k);
          String str7 = localPilot.lastName + "," + localPilot.firstName;
          if (str6.equals(str7)) {
            this.assPilot.put(str4, localPilot.sRank + " " + localPilot.lastName);
          }
        }
      }
      localBufferedReader.close();
    } catch (Exception localException) {
      System.out.println("Status file load failed: " + localException.getMessage());
      localException.printStackTrace();
      Main.stateStack().pop();
    }
  }

  private void prepareTypeAssociation() {
    this.assType.clear();
    SectFile localSectFile = Mission.cur().sectFile();
    int i = localSectFile.sectionIndex("Wing");
    if (i < 0) return;
    int j = localSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      String str1 = localSectFile.var(i, k);
      int m = localSectFile.sectionIndex(str1);
      if (m >= 0) {
        int n = localSectFile.get(str1, "Planes", 1, 1, 4);
        String str2 = localSectFile.get(str1, "Class", (String)null);
        if (str2 != null) {
          Class localClass = null;
          try {
            localClass = ObjIO.classForName(str2);
          } catch (Exception localException) {
            continue;
          }
          String str3 = Property.stringValue(localClass, "keyName", null);
          if (str3 != null) {
            String str4 = I18N.plane(str3);
            for (int i1 = 0; i1 < n; i1++)
              this.assType.put(str1 + i1, str4); 
          }
        }
      }
    }
  }

  private void drawEvents() {
    GPoint localGPoint = this.renders.getMouseXY();
    int i = -1;
    float f1 = localGPoint.x;
    float f2 = this.renders.win.dy - 1.0F - localGPoint.y;
    float f3 = IconDraw.scrSizeX() / 2;
    float f4 = f1; float f5 = f2;
    ArrayList localArrayList = EventLog.actions;
    int j = localArrayList.size();
    float f9;
    for (int k = 0; k < j; k++) {
      Mat localMat = null;
      EventLog.Action localAction2 = (EventLog.Action)localArrayList.get(k);
      switch (localAction2.event) {
      case 0:
        this.assPilot.put(localAction2.arg0, localAction2.arg1);
        break;
      case 2:
      case 3:
      case 4:
        switch (localAction2.scoreItem0) { case 0:
          localMat = this.iconAction[4]; break;
        case 8:
          localMat = this.iconAction[12]; break;
        case 1:
          localMat = this.iconAction[9]; break;
        case 2:
          localMat = this.iconAction[3]; break;
        case 3:
          localMat = this.iconAction[5]; break;
        case 4:
          localMat = this.iconAction[0]; break;
        case 5:
          localMat = this.iconAction[2]; break;
        case 6:
          localMat = this.iconAction[10]; break;
        case 7:
          localMat = this.iconAction[8]; break;
        case 9:
          localMat = this.iconAction[13]; }
        break;
      case 5:
        if ((localAction2.argi != 0) || (this.assPilot.get(localAction2.arg0) == null)) break;
        localMat = this.iconAction[1]; break;
      case 6:
        if ((localAction2.argi != 0) || (this.assPilot.get(localAction2.arg0) == null)) break;
        localMat = this.iconAction[11]; break;
      case 7:
        if ((localAction2.argi != 0) || (this.assPilot.get(localAction2.arg0) == null)) break;
        localMat = this.iconAction[6]; break;
      case 1:
      }

      if (localMat != null) {
        float f7 = (float)((localAction2.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        f9 = (float)((localAction2.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
        IconDraw.setColor(0xFF000000 | Army.color(localAction2.army0));
        IconDraw.render(localMat, f7, f9);
        if ((f7 >= f1 - f3) && (f7 <= f1 + f3) && (f9 >= f2 - f3) && (f9 <= f2 + f3)) {
          i = k;
          f4 = f7; f5 = f9;
        }
      }
    }
    if (i >= 0) {
      EventLog.Action localAction1 = (EventLog.Action)localArrayList.get(i);
      for (int m = 0; m < 4; m++) this.tip[m] = null;
      String str;
      switch (localAction1.event) {
      case 2:
        if (localAction1.scoreItem0 == 0)
          this.tip[1] = getAss(localAction1.arg0);
        else {
          this.tip[1] = getByScoreItem(localAction1.scoreItem0, localAction1.arg0);
        }
        this.tip[2] = i18n("debrief.Crashed");

        break;
      case 3:
        this.tip[1] = getAss(localAction1.arg0);
        str = getAss(localAction1.arg1);
        if (str != null) {
          this.tip[2] = i18n("debrief.ShotDownBy");
          this.tip[3] = str;
        } else {
          this.tip[2] = i18n("debrief.ShotDown");
        }

        break;
      case 4:
        this.tip[1] = getByScoreItem(localAction1.scoreItem0, localAction1.arg0);
        str = (String)this.assPilot.get(localAction1.arg1);
        if (str != null) {
          this.tip[2] = i18n("debrief.DestroyedBy");
          this.tip[3] = str;
        } else {
          this.tip[2] = i18n("debrief.Destroyed");
        }

        break;
      case 5:
        str = getAss(localAction1.arg0);
        if (str != null) {
          this.tip[1] = str;
          this.tip[2] = i18n("debrief.BailedOut");
        }

        break;
      case 6:
        str = getAss(localAction1.arg0);
        if (str != null) {
          this.tip[1] = str;
          this.tip[2] = i18n("debrief.WasKilled");
        }

        break;
      case 7:
        str = getAss(localAction1.arg0);
        if (str != null) {
          this.tip[1] = str;
          this.tip[2] = i18n("debrief.Landed");
        }

        break;
      }

      if (this.tip[1] != null) {
        this.tip[0] = EventLog.logOnTime(localAction1.time).toString();
        float f6 = this.gridFont.width(this.tip[0]);
        int n = 1;
        for (int i1 = 1; (i1 < 4) && 
          (this.tip[i1] != null); i1++)
        {
          n = i1;
          f9 = this.gridFont.width(this.tip[i1]);
          if (f6 >= f9) continue; f6 = f9;
        }
        float f8 = -this.gridFont.descender();
        f9 = this.gridFont.height() + f8;
        f6 += 2.0F * f8;
        float f10 = f9 * (n + 1) + 2.0F * f8;

        float f11 = f4 - f6 / 2.0F;
        float f12 = f5 + f3;
        if (f11 + f6 > this.renders.win.dx) f11 = this.renders.win.dx - f6;
        if (f12 + f10 > this.renders.win.dy) f12 = this.renders.win.dy - f10;
        if (f11 < 0.0F) f11 = 0.0F;
        if (f12 < 0.0F) f12 = 0.0F;

        Render.drawTile(f11, f12, f6, f10, 0.0F, this.emptyMat, -813694977, 0.0F, 0.0F, 1.0F, 1.0F);
        Render.drawEnd();
        for (int i2 = 0; i2 <= n; i2++)
          this.gridFont.output(-16777216, f11 + f8, f12 + f8 + (n - i2) * f9 + f8, 0.0F, this.tip[i2]);
      }
    }
  }

  private String getAss(String paramString) {
    String str = (String)this.assPilot.get(paramString);
    if (str == null) str = (String)this.assType.get(paramString);
    return str;
  }
  private String getByScoreItem(int paramInt, String paramString) {
    switch (paramInt) { case 8:
      return i18n("debrief.Airstatic");
    case 1:
      return i18n("debrief.Tank");
    case 2:
      return i18n("debrief.Car");
    case 3:
      return i18n("debrief.Artillery");
    case 4:
      return i18n("debrief.AAA");
    case 5:
      return i18n("debrief.Bridge");
    case 6:
      return i18n("debrief.Train");
    case 7:
      return i18n("debrief.Ship");
    case 9:
      return i18n("debrief.Radio"); }
    return paramString;
  }

  private int colorPath()
  {
    long l1 = Time.currentReal();
    long l2 = 1000L;
    double d = 2.0D * (l1 % l2) / l2;
    if (d >= 1.0D)
      d = 2.0D - d;
    int i = (int)(255.0D * d);
    return 0xFF000000 | i << 16 | i << 8 | i;
  }

  private void drawPlayerPath() {
    Render.drawBeginLines(-1);
    double d1 = 5.0D / this.cameraMap2D.worldScale;
    d1 *= d1;
    ArrayList localArrayList = EventLog.actions;
    int i = localArrayList.size();
    Object localObject = null;
    int j = colorPath();
    for (int k = 0; k < i; k++) {
      EventLog.Action localAction = (EventLog.Action)localArrayList.get(k);
      if (localAction.event != 11)
        continue;
      if (localObject == null) {
        localObject = localAction;
      } else {
        double d2 = (localObject.x - localAction.x) * (localObject.x - localAction.x) + (localObject.y - localAction.y) * (localObject.y - localAction.y);
        if (d2 < d1)
          continue;
        this.lineXYZ[0] = (float)((localObject.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        this.lineXYZ[1] = (float)((localObject.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
        this.lineXYZ[2] = 0.0F;
        this.lineXYZ[3] = (float)((localAction.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        this.lineXYZ[4] = (float)((localAction.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
        this.lineXYZ[5] = 0.0F;
        Render.drawLines(this.lineXYZ, 2, 3.0F, j, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 1);

        localObject = localAction;
      }
    }
    Render.drawEnd();
  }

  protected void doRenderMap2D()
  {
    drawPlayerPath();
    drawEvents();
  }

  protected void doBack()
  {
    saveCampaign();
    Main3D.cur3D().keyRecord.clearRecorded();
    if ((Mission.cur() != null) && (!Mission.cur().isDestroyed()))
      Mission.cur().destroy();
    Main.cur().campaign = null;
    Main.cur().currentMissionFile = null;
    Main.stateStack().pop();
  }
  protected void doDiff() {
    Main.stateStack().push(9);
  }
  protected void doLoodout() {
    Main3D.cur3D().keyRecord.clearRecorded();
    Main.stateStack().change(63);
  }
  protected void doNext() {
    if (!this.bMissComplete)
      return;
    int i = Main.cur().campaign._rank;
    Main.cur().campaign.currentMissionComplete(Scores.score, Scores.enemyAirKill, Scores.arrayEnemyGroundKill, Scores.friendlyKill);
    Main.cur().campaign._rank = i;
    String str;
    if (Main.cur().campaign.isComplete()) {
      str = Main.cur().campaign.epilogueTrack();
      doBack();
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
      updatePlayerScore();

      if (Main.cur().campaign.isComplete()) {
        str = Main.cur().campaign.epilogueTrack();
        doBack();
        if (str != null) {
          GUIBWDemoPlay.demoFile = str;
          GUIBWDemoPlay.soundFile = null;
          Main.stateStack().push(58);
        }
        return;
      }

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
            GUIDGenDeBriefing.this.doBack();
          }
        };
        return;
      }
      Main.stateStack().change(62);
    }
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;

    if (this.bNext.isVisible()) {
      localDialogClient.draw(localDialogClient.x1024(427.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(170.0F), localDialogClient.y1024(48.0F), 1, i18n("debrief.Apply"));
    }

    localDialogClient.draw(localDialogClient.x1024(0.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(170.0F), localDialogClient.y1024(48.0F), 1, i18n("debrief.MainMenu"));
    localDialogClient.draw(localDialogClient.x1024(194.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(208.0F), localDialogClient.y1024(48.0F), 1, i18n("debrief.SaveTrack"));
    localDialogClient.draw(localDialogClient.x1024(680.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(176.0F), localDialogClient.y1024(48.0F), 1, i18n("debrief.ReFly"));
  }

  public GUIDGenDeBriefing(GWindowRoot paramGWindowRoot) {
    super(64);
    init(paramGWindowRoot);
    this.iconAction[0] = Mat.New("icons/objActAAA.mat");
    this.iconAction[1] = Mat.New("icons/objActBailed.mat");
    this.iconAction[2] = Mat.New("icons/objActBridge.mat");
    this.iconAction[3] = Mat.New("icons/objActCar.mat");
    this.iconAction[4] = Mat.New("icons/objActCrashed.mat");
    this.iconAction[5] = Mat.New("icons/objActGun.mat");
    this.iconAction[6] = Mat.New("icons/objActLanded.mat");
    this.iconAction[7] = Mat.New("icons/objActPlane.mat");
    this.iconAction[8] = Mat.New("icons/objActShip.mat");
    this.iconAction[9] = Mat.New("icons/objActTank.mat");
    this.iconAction[10] = Mat.New("icons/objActTrain.mat");
    this.iconAction[11] = Mat.New("icons/objActPilot.mat");
    this.iconAction[12] = Mat.New("icons/objActAirstatic.mat");
    this.iconAction[13] = Mat.New("icons/objActRR.mat");
  }
}