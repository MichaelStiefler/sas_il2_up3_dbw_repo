package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;

public class GUIQuickStats extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bPrev;
  public GUIButton bRes;
  static int qmbBulletsFired;
  static int qmbBulletsHitAir;
  static int qmbBulletsHit;
  static int qmbBulletsFiredHitGround;
  static int qmbMissionBombsFired;
  static int qmbMissionBombsHit;
  static int qmbMissionRocketsFired;
  static int qmbMissionRocketsHit;
  static int qmbSessionBombsFired;
  static int qmbSessionBombsHit;
  static int qmbSessionRocketsFired;
  static int qmbSessionRocketsHit;
  static int qmbTotalBombsFired;
  static int qmbTotalBombsHit;
  static int qmbTotalRocketsFired;
  static int qmbTotalRocketsHit;
  static int qmbSessionBulletsFired;
  static int qmbSessionBulletsHitAir;
  static int qmbSessionBulletsHit;
  static int qmbSessionBulletsFiredHitGround;
  static int qmbTotalBulletsFired;
  static int qmbTotalBulletsHitAir;
  static int qmbTotalBulletsHit;
  static int qmbTotalBulletsFiredHitGround;
  static float qmbPercentageAir;
  static float qmbSessionPctAir;
  static float qmbTotalPctAir;
  static float qmbMissionPctBomb;
  static float qmbSessionPctBomb;
  static float qmbTotalPctBomb;
  static float qmbMissionPctRocket;
  static float qmbSessionPctRocket;
  static float qmbTotalPctRocket;
  static float qmbPercentageGround;
  static float qmbSessionPctGround;
  static float qmbTotalPctGround;
  static int qmbDead;
  static int qmbSessionDead;
  static int qmbTotalDead;
  static int qmbPara;
  static int qmbSessionPara;
  static int qmbTotalPara;
  static int qmbScore;
  static int qmbSessionScore;
  static int qmbTotalScore;
  static int qmbMissionAirKill;
  static int qmbSessionAirKill;
  static int qmbTotalAirKill;
  static int qmbMissionGroundKill;
  static int qmbSessionGroundKill;
  static int qmbTotalGroundKill;
  static int qmbMissionTankKill;
  static int qmbSessionTankKill;
  static int qmbTotalTankKill;
  static int qmbMissionCarKill;
  static int qmbSessionCarKill;
  static int qmbTotalCarKill;
  static int qmbMissionArtilleryKill;
  static int qmbSessionArtilleryKill;
  static int qmbTotalArtilleryKill;
  static int qmbMissionAAAKill;
  static int qmbSessionAAAKill;
  static int qmbTotalAAAKill;
  static int qmbMissionTrainKill;
  static int qmbSessionTrainKill;
  static int qmbTotalTrainKill;
  static int qmbMissionShipKill;
  static int qmbSessionShipKill;
  static int qmbTotalShipKill;
  static int qmbMissionBridgeKill;
  static int qmbSessionBridgeKill;
  static int qmbTotalBridgeKill;
  static int qmbMissionAirStaticKill;
  static int qmbSessionAirStaticKill;
  static int qmbTotalAirStaticKill;
  public Table wScrollStats;
  private static final int nOfRows = 24;

  public void _enter()
  {
    loadQMBStat();
    fillTableElements();
    this.wScrollStats.resized();
    this.client.activateWindow();
  }

  private void fillTableElements() {
    this.wScrollStats.tableElements[0][0] = ("  " + i18n("q.BFire"));
    this.wScrollStats.tableElements[1][0] = ("  " + i18n("q.AHit"));
    this.wScrollStats.tableElements[2][0] = ("  " + i18n("q.Air%"));
    this.wScrollStats.tableElements[3][0] = ("  " + i18n("q.GHit"));
    this.wScrollStats.tableElements[4][0] = ("  " + i18n("q.Ground%"));
    this.wScrollStats.tableElements[5][0] = ("  " + i18n("q.BombFire"));
    this.wScrollStats.tableElements[6][0] = ("  " + i18n("q.BombHit"));
    this.wScrollStats.tableElements[7][0] = ("  " + i18n("q.BombPct"));
    this.wScrollStats.tableElements[8][0] = ("  " + i18n("q.RockFire"));
    this.wScrollStats.tableElements[9][0] = ("  " + i18n("q.RockHit"));
    this.wScrollStats.tableElements[10][0] = ("  " + i18n("q.RockPct"));
    this.wScrollStats.tableElements[11][0] = ("  " + i18n("q.AirKill"));
    this.wScrollStats.tableElements[12][0] = ("  " + i18n("q.GroundKill"));
    this.wScrollStats.tableElements[13][0] = ("  " + i18n("q.TankKill"));
    this.wScrollStats.tableElements[14][0] = ("  " + i18n("q.CarKill"));
    this.wScrollStats.tableElements[15][0] = ("  " + i18n("q.ArtKill"));
    this.wScrollStats.tableElements[16][0] = ("  " + i18n("q.AAAKill"));
    this.wScrollStats.tableElements[17][0] = ("  " + i18n("q.TrainKill"));
    this.wScrollStats.tableElements[18][0] = ("  " + i18n("q.AirStaticKill"));
    this.wScrollStats.tableElements[19][0] = ("  " + i18n("q.ShipKill"));
    this.wScrollStats.tableElements[20][0] = ("  " + i18n("q.BridgeKill"));
    this.wScrollStats.tableElements[21][0] = ("  " + i18n("q.Bailout"));
    this.wScrollStats.tableElements[22][0] = ("  " + i18n("q.Dead"));
    this.wScrollStats.tableElements[23][0] = ("  " + i18n("q.Score"));

    this.wScrollStats.tableElements[0][1] = ("" + qmbBulletsFired);
    this.wScrollStats.tableElements[1][1] = ("" + qmbBulletsHitAir);
    this.wScrollStats.tableElements[2][1] = ("" + qmbPercentageAir);
    this.wScrollStats.tableElements[3][1] = ("" + qmbBulletsFiredHitGround);
    this.wScrollStats.tableElements[4][1] = ("" + qmbPercentageGround);
    this.wScrollStats.tableElements[5][1] = ("" + qmbMissionBombsFired);
    this.wScrollStats.tableElements[6][1] = ("" + qmbMissionBombsHit);
    this.wScrollStats.tableElements[7][1] = ("" + qmbMissionPctBomb);
    this.wScrollStats.tableElements[8][1] = ("" + qmbMissionRocketsFired);
    this.wScrollStats.tableElements[9][1] = ("" + qmbMissionRocketsHit);
    this.wScrollStats.tableElements[10][1] = ("" + qmbMissionPctRocket);
    this.wScrollStats.tableElements[11][1] = ("" + qmbMissionAirKill);
    this.wScrollStats.tableElements[12][1] = ("" + qmbMissionGroundKill);
    this.wScrollStats.tableElements[13][1] = ("" + qmbMissionTankKill);
    this.wScrollStats.tableElements[14][1] = ("" + qmbMissionCarKill);
    this.wScrollStats.tableElements[15][1] = ("" + qmbMissionArtilleryKill);
    this.wScrollStats.tableElements[16][1] = ("" + qmbMissionAAAKill);
    this.wScrollStats.tableElements[17][1] = ("" + qmbMissionTrainKill);
    this.wScrollStats.tableElements[18][1] = ("" + qmbMissionAirStaticKill);
    this.wScrollStats.tableElements[19][1] = ("" + qmbMissionShipKill);
    this.wScrollStats.tableElements[20][1] = ("" + qmbMissionBridgeKill);
    this.wScrollStats.tableElements[21][1] = ("" + qmbPara);
    this.wScrollStats.tableElements[22][1] = ("" + qmbDead);
    this.wScrollStats.tableElements[23][1] = ("" + qmbScore);

    this.wScrollStats.tableElements[0][2] = ("" + qmbSessionBulletsFired);
    this.wScrollStats.tableElements[1][2] = ("" + qmbSessionBulletsHitAir);
    this.wScrollStats.tableElements[2][2] = ("" + qmbSessionPctAir);
    this.wScrollStats.tableElements[3][2] = ("" + qmbSessionBulletsFiredHitGround);
    this.wScrollStats.tableElements[4][2] = ("" + qmbSessionPctGround);
    this.wScrollStats.tableElements[5][2] = ("" + qmbSessionBombsFired);
    this.wScrollStats.tableElements[6][2] = ("" + qmbSessionBombsHit);
    this.wScrollStats.tableElements[7][2] = ("" + qmbSessionPctBomb);
    this.wScrollStats.tableElements[8][2] = ("" + qmbSessionRocketsFired);
    this.wScrollStats.tableElements[9][2] = ("" + qmbSessionRocketsHit);
    this.wScrollStats.tableElements[10][2] = ("" + qmbSessionPctRocket);
    this.wScrollStats.tableElements[11][2] = ("" + qmbSessionAirKill);
    this.wScrollStats.tableElements[12][2] = ("" + qmbSessionGroundKill);
    this.wScrollStats.tableElements[13][2] = ("" + qmbSessionTankKill);
    this.wScrollStats.tableElements[14][2] = ("" + qmbSessionCarKill);
    this.wScrollStats.tableElements[15][2] = ("" + qmbSessionArtilleryKill);
    this.wScrollStats.tableElements[16][2] = ("" + qmbSessionAAAKill);
    this.wScrollStats.tableElements[17][2] = ("" + qmbSessionTrainKill);
    this.wScrollStats.tableElements[18][2] = ("" + qmbSessionAirStaticKill);
    this.wScrollStats.tableElements[19][2] = ("" + qmbSessionShipKill);
    this.wScrollStats.tableElements[20][2] = ("" + qmbSessionBridgeKill);
    this.wScrollStats.tableElements[21][2] = ("" + qmbSessionPara);
    this.wScrollStats.tableElements[22][2] = ("" + qmbSessionDead);
    this.wScrollStats.tableElements[23][2] = ("" + qmbSessionScore);

    this.wScrollStats.tableElements[0][3] = ("" + qmbTotalBulletsFired);
    this.wScrollStats.tableElements[1][3] = ("" + qmbTotalBulletsHitAir);
    this.wScrollStats.tableElements[2][3] = ("" + qmbTotalPctAir);
    this.wScrollStats.tableElements[3][3] = ("" + qmbTotalBulletsFiredHitGround);
    this.wScrollStats.tableElements[4][3] = ("" + qmbTotalPctGround);
    this.wScrollStats.tableElements[5][3] = ("" + qmbTotalBombsFired);
    this.wScrollStats.tableElements[6][3] = ("" + qmbTotalBombsHit);
    this.wScrollStats.tableElements[7][3] = ("" + qmbTotalPctBomb);
    this.wScrollStats.tableElements[8][3] = ("" + qmbTotalRocketsFired);
    this.wScrollStats.tableElements[9][3] = ("" + qmbTotalRocketsHit);
    this.wScrollStats.tableElements[10][3] = ("" + qmbTotalPctRocket);
    this.wScrollStats.tableElements[11][3] = ("" + qmbTotalAirKill);
    this.wScrollStats.tableElements[12][3] = ("" + qmbTotalGroundKill);
    this.wScrollStats.tableElements[13][3] = ("" + qmbTotalTankKill);
    this.wScrollStats.tableElements[14][3] = ("" + qmbTotalCarKill);
    this.wScrollStats.tableElements[15][3] = ("" + qmbTotalArtilleryKill);
    this.wScrollStats.tableElements[16][3] = ("" + qmbTotalAAAKill);
    this.wScrollStats.tableElements[17][3] = ("" + qmbTotalTrainKill);
    this.wScrollStats.tableElements[18][3] = ("" + qmbTotalAirStaticKill);
    this.wScrollStats.tableElements[19][3] = ("" + qmbTotalShipKill);
    this.wScrollStats.tableElements[20][3] = ("" + qmbTotalBridgeKill);
    this.wScrollStats.tableElements[21][3] = ("" + qmbTotalPara);
    this.wScrollStats.tableElements[22][3] = ("" + qmbTotalDead);
    this.wScrollStats.tableElements[23][3] = ("" + qmbTotalScore);
  }

  public void _leave() {
    saveStat();
    this.client.hideWindow();
  }

  public static void saveStat() {
    String str1 = "users/" + World.cur().userCfg.sId + "/QMB.ini";
    SectFile localSectFile = new SectFile(str1, 1, false, World.cur().userCfg.krypto());

    String str2 = "MAIN";
    int i = localSectFile.sectionIndex(str2);
    localSectFile.sectionClear(i);

    localSectFile.lineAdd(i, "qmbTotalScore", "" + qmbTotalScore);

    localSectFile.lineAdd(i, "qmbTotalAirKill", "" + qmbTotalAirKill);
    localSectFile.lineAdd(i, "qmbTotalGroundKill", "" + qmbTotalGroundKill);

    localSectFile.lineAdd(i, "qmbTotalBulletsFired", "" + qmbTotalBulletsFired);
    localSectFile.lineAdd(i, "qmbTotalBulletsHitAir", "" + qmbTotalBulletsHitAir);
    localSectFile.lineAdd(i, "qmbTotalBulletsFiredHitGround", "" + qmbTotalBulletsFiredHitGround);

    localSectFile.lineAdd(i, "qmbTotalPctAir", "" + qmbTotalPctAir);
    localSectFile.lineAdd(i, "qmbTotalPctGround", "" + qmbTotalPctGround);

    localSectFile.lineAdd(i, "qmbTotalTankKill", "" + qmbTotalTankKill);
    localSectFile.lineAdd(i, "qmbTotalCarKill", "" + qmbTotalCarKill);
    localSectFile.lineAdd(i, "qmbTotalArtilleryKill", "" + qmbTotalArtilleryKill);
    localSectFile.lineAdd(i, "qmbTotalAAAKill", "" + qmbTotalAAAKill);
    localSectFile.lineAdd(i, "qmbTotalTrainKill", "" + qmbTotalTrainKill);
    localSectFile.lineAdd(i, "qmbTotalShipKill", "" + qmbTotalShipKill);
    localSectFile.lineAdd(i, "qmbTotalAirStaticKill", "" + qmbTotalAirStaticKill);
    localSectFile.lineAdd(i, "qmbTotalBridgeKill", "" + qmbTotalBridgeKill);

    localSectFile.lineAdd(i, "qmbTotalPara", "" + qmbTotalPara);
    localSectFile.lineAdd(i, "qmbTotalDead", "" + qmbTotalDead);

    localSectFile.lineAdd(i, "qmbTotalBombsFired", "" + qmbTotalBombsFired);
    localSectFile.lineAdd(i, "qmbTotalBombsHit", "" + qmbTotalBombsHit);

    localSectFile.lineAdd(i, "qmbTotalRocketsFired", "" + qmbTotalRocketsFired);
    localSectFile.lineAdd(i, "qmbTotalRocketsHit", "" + qmbTotalRocketsHit);

    localSectFile.lineAdd(i, "qmbTotalPctBomb", "" + qmbTotalPctBomb);
    localSectFile.lineAdd(i, "qmbTotalPctRocket", "" + qmbTotalPctRocket);

    localSectFile.saveFile();
  }

  public static void loadQMBStat() {
    String str = "users/" + World.cur().userCfg.sId + "/QMB.ini";
    if (exestFile(str)) {
      SectFile localSectFile = new SectFile(str, 1, false, World.cur().userCfg.krypto());
      qmbTotalScore = localSectFile.get("MAIN", "qmbTotalScore", 0);

      qmbTotalAirKill = localSectFile.get("MAIN", "qmbTotalAirKill", 0);
      qmbTotalGroundKill = localSectFile.get("MAIN", "qmbTotalGroundKill", 0);

      qmbTotalBulletsFired = localSectFile.get("MAIN", "qmbTotalBulletsFired", 0);
      qmbTotalBulletsHitAir = localSectFile.get("MAIN", "qmbTotalBulletsHitAir", 0);
      qmbTotalBulletsFiredHitGround = localSectFile.get("MAIN", "qmbTotalBulletsFiredHitGround", 0);

      qmbTotalPctAir = localSectFile.get("MAIN", "qmbTotalPctAir", 0.0F);
      qmbTotalPctGround = localSectFile.get("MAIN", "qmbTotalPctGround", 0.0F);

      qmbTotalTankKill = localSectFile.get("MAIN", "qmbTotalTankKill", 0);
      qmbTotalCarKill = localSectFile.get("MAIN", "qmbTotalCarKill", 0);
      qmbTotalArtilleryKill = localSectFile.get("MAIN", "qmbTotalArtilleryKill", 0);
      qmbTotalAAAKill = localSectFile.get("MAIN", "qmbTotalAAAKill", 0);

      qmbTotalTrainKill = localSectFile.get("MAIN", "qmbTotalTrainKill", 0);
      qmbTotalShipKill = localSectFile.get("MAIN", "qmbTotalShipKill", 0);
      qmbTotalAirStaticKill = localSectFile.get("MAIN", "qmbTotalAirStaticKill", 0);
      qmbTotalBridgeKill = localSectFile.get("MAIN", "qmbTotalBridgeKill", 0);

      qmbTotalPara = localSectFile.get("MAIN", "qmbTotalPara", 0);
      qmbTotalDead = localSectFile.get("MAIN", "qmbTotalDead", 0);

      qmbTotalBombsFired = localSectFile.get("MAIN", "qmbTotalBombsFired", 0);
      qmbTotalBombsHit = localSectFile.get("MAIN", "qmbTotalBombsHit", 0);

      qmbTotalRocketsFired = localSectFile.get("MAIN", "qmbTotalRocketsFired", 0);
      qmbTotalRocketsHit = localSectFile.get("MAIN", "qmbTotalRocketsHit", 0);

      qmbTotalPctBomb = localSectFile.get("MAIN", "qmbTotalPctBomb", 0.0F);

      qmbTotalPctRocket = localSectFile.get("MAIN", "qmbTotalPctRocket", 0.0F);
    }
  }

  private static boolean exestFile(String paramString) {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
    } catch (Exception localException) {
      return false;
    }
    return true;
  }

  public static void qmbMissionComplete(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt, boolean paramBoolean1, boolean paramBoolean2, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    loadQMBStat();
    qmbBulletsFired = paramInt1;
    qmbBulletsHitAir = paramInt2;
    qmbBulletsHit = paramInt3;
    qmbBulletsFiredHitGround = qmbBulletsHit - qmbBulletsHitAir;

    qmbMissionBombsFired = paramInt7;
    qmbMissionBombsHit = paramInt10;
    qmbMissionRocketsFired = paramInt8;
    qmbMissionRocketsHit = paramInt9;

    qmbSessionBombsFired += paramInt7;
    qmbSessionBombsHit += paramInt10;
    qmbSessionRocketsFired += paramInt8;
    qmbSessionRocketsHit += paramInt9;

    qmbTotalBombsFired += paramInt7;
    qmbTotalBombsHit += paramInt10;
    qmbTotalRocketsFired += paramInt8;
    qmbTotalRocketsHit += paramInt9;

    qmbSessionBulletsFired += paramInt1;
    qmbSessionBulletsHitAir += paramInt2;
    qmbSessionBulletsHit += paramInt3;
    qmbSessionBulletsFiredHitGround = qmbSessionBulletsHit - qmbSessionBulletsHitAir;

    qmbTotalBulletsFired += paramInt1;
    qmbTotalBulletsHitAir += paramInt2;
    qmbTotalBulletsHit += paramInt3;
    qmbTotalBulletsFiredHitGround += qmbBulletsFiredHitGround;

    if (qmbBulletsFired > 0) {
      qmbPercentageAir = qmbBulletsHitAir * 100.0F / qmbBulletsFired;
      qmbPercentageGround = qmbBulletsFiredHitGround * 100.0F / qmbBulletsFired;
      qmbPercentageAir = (int)(qmbPercentageAir * 100.0F) / 100.0F;
      qmbPercentageGround = (int)(qmbPercentageGround * 100.0F) / 100.0F;
    } else {
      qmbPercentageAir = GUIQuickStats.qmbPercentageGround = 0.0F;
    }

    if (qmbSessionBulletsFired > 0) {
      qmbSessionPctAir = qmbSessionBulletsHitAir * 100.0F / qmbSessionBulletsFired;
      qmbSessionPctGround = qmbSessionBulletsFiredHitGround * 100.0F / qmbSessionBulletsFired;
      qmbSessionPctAir = (int)(qmbSessionPctAir * 100.0F) / 100.0F;
      qmbSessionPctGround = (int)(qmbSessionPctGround * 100.0F) / 100.0F;
    } else {
      qmbSessionPctAir = GUIQuickStats.qmbSessionPctGround = 0.0F;
    }

    if (qmbTotalBulletsFired > 0) {
      qmbTotalPctAir = qmbTotalBulletsHitAir * 100.0F / qmbTotalBulletsFired;
      qmbTotalPctGround = qmbTotalBulletsFiredHitGround * 100.0F / qmbTotalBulletsFired;
      qmbTotalPctAir = (int)(qmbTotalPctAir * 100.0F) / 100.0F;
      qmbTotalPctGround = (int)(qmbTotalPctGround * 100.0F) / 100.0F;
    } else {
      qmbTotalPctAir = GUIQuickStats.qmbTotalPctGround = 0.0F;
    }

    if (qmbMissionBombsFired > 0) {
      qmbMissionPctBomb = qmbMissionBombsHit * 100.0F / qmbMissionBombsFired;
      qmbMissionPctBomb = (int)(qmbMissionPctBomb * 100.0F) / 100.0F;
    } else {
      qmbMissionPctBomb = 0.0F;
    }

    if (qmbSessionBombsFired > 0.0F) {
      qmbSessionPctBomb = qmbSessionBombsHit * 100.0F / qmbSessionBombsFired;
      qmbSessionPctBomb = (int)(qmbSessionPctBomb * 100.0F) / 100.0F;
    } else {
      qmbSessionPctBomb = 0.0F;
    }

    if (qmbTotalBombsFired > 0) {
      qmbTotalPctBomb = qmbTotalBombsHit * 100.0F / qmbTotalBombsFired;
      qmbTotalPctBomb = (int)(qmbTotalPctBomb * 100.0F) / 100.0F;
    } else {
      qmbTotalPctBomb = 0.0F;
    }

    if (qmbMissionRocketsFired > 0) {
      qmbMissionPctRocket = qmbMissionRocketsHit * 100.0F / qmbMissionRocketsFired;
      qmbMissionPctRocket = (int)(qmbMissionPctRocket * 100.0F) / 100.0F;
    } else {
      qmbMissionPctRocket = 0.0F;
    }

    if (qmbSessionRocketsFired > 0) {
      qmbSessionPctRocket = qmbSessionRocketsHit * 100.0F / qmbSessionRocketsFired;
      qmbSessionPctRocket = (int)(qmbSessionPctRocket * 100.0F) / 100.0F;
    } else {
      qmbSessionPctRocket = 0.0F;
    }

    if (qmbTotalRocketsFired > 0) {
      qmbTotalPctRocket = qmbTotalRocketsHit * 100.0F / qmbTotalRocketsFired;
      qmbTotalPctRocket = (int)(qmbTotalPctRocket * 100.0F) / 100.0F;
    } else {
      qmbTotalPctRocket = 0.0F;
    }

    qmbMissionAirKill = paramInt5;
    qmbSessionAirKill += qmbMissionAirKill;
    qmbTotalAirKill += qmbMissionAirKill;

    qmbMissionGroundKill = paramInt6;
    qmbSessionGroundKill += qmbMissionGroundKill;
    qmbTotalGroundKill += qmbMissionGroundKill;

    if (paramArrayOfInt != null) {
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        switch (paramArrayOfInt[i]) {
        case 1:
          qmbMissionTankKill += 1;
          qmbSessionTankKill += 1;
          qmbTotalTankKill += 1;
          break;
        case 2:
          qmbMissionCarKill += 1;
          qmbSessionCarKill += 1;
          qmbTotalCarKill += 1;
          break;
        case 3:
          qmbMissionArtilleryKill += 1;
          qmbSessionArtilleryKill += 1;
          qmbTotalArtilleryKill += 1;
          break;
        case 4:
          qmbMissionAAAKill += 1;
          qmbSessionAAAKill += 1;
          qmbTotalAAAKill += 1;
          break;
        case 6:
          qmbMissionTrainKill += 1;
          qmbSessionTrainKill += 1;
          qmbTotalTrainKill += 1;
          break;
        case 7:
          qmbMissionShipKill += 1;
          qmbSessionShipKill += 1;
          qmbTotalShipKill += 1;
          break;
        case 5:
          qmbMissionBridgeKill += 1;
          qmbSessionBridgeKill += 1;
          qmbTotalBridgeKill += 1;
          break;
        case 8:
          qmbMissionAirStaticKill += 1;
          qmbSessionAirStaticKill += 1;
          qmbTotalAirStaticKill += 1;
        }
      }

    }

    if (paramBoolean2) {
      qmbPara += 1;
      qmbSessionPara += 1;
      qmbTotalPara += 1;
    }

    if (paramBoolean1) {
      qmbDead += 1;
      qmbSessionDead += 1;
      qmbTotalDead += 1;
    }

    qmbScore = paramInt4;
    qmbSessionScore += qmbScore;
    qmbTotalScore += qmbScore;
    saveStat();
  }

  public static void resetMissionStat()
  {
    qmbBulletsFired = 0;
    qmbBulletsHitAir = 0;
    qmbPercentageAir = 0.0F;
    qmbBulletsFiredHitGround = 0;
    qmbPercentageGround = 0.0F;
    qmbMissionBombsFired = 0;
    qmbMissionBombsHit = 0;
    qmbMissionPctBomb = 0.0F;
    qmbMissionRocketsFired = 0;
    qmbMissionRocketsHit = 0;
    qmbMissionPctRocket = 0.0F;
    qmbMissionAirKill = 0;
    qmbMissionGroundKill = 0;
    qmbMissionTankKill = 0;
    qmbMissionCarKill = 0;
    qmbMissionArtilleryKill = 0;
    qmbMissionAAAKill = 0;
    qmbMissionTrainKill = 0;
    qmbMissionAirStaticKill = 0;
    qmbMissionShipKill = 0;
    qmbMissionBridgeKill = 0;
    qmbDead = 0;
    qmbPara = 0;
    qmbScore = 0;
  }

  public static void resetSessionStat() {
    qmbSessionBulletsHit = 0;
    qmbSessionBulletsFired = 0;
    qmbSessionBulletsHitAir = 0;
    qmbSessionPctAir = 0.0F;
    qmbSessionBulletsFiredHitGround = 0;
    qmbSessionPctGround = 0.0F;
    qmbSessionBombsFired = 0;
    qmbSessionBombsHit = 0;
    qmbSessionPctBomb = 0.0F;
    qmbSessionRocketsFired = 0;
    qmbSessionRocketsHit = 0;
    qmbSessionPctRocket = 0.0F;
    qmbSessionAirKill = 0;
    qmbSessionGroundKill = 0;
    qmbSessionTankKill = 0;
    qmbSessionCarKill = 0;
    qmbSessionArtilleryKill = 0;
    qmbSessionAAAKill = 0;
    qmbSessionTrainKill = 0;
    qmbSessionAirStaticKill = 0;
    qmbSessionShipKill = 0;
    qmbSessionBridgeKill = 0;
    qmbSessionPara = 0;
    qmbSessionDead = 0;
    qmbSessionScore = 0;
  }

  public static void resetQMBStat() {
    qmbBulletsHit = 0;
    qmbTotalBulletsFired = 0;
    qmbTotalBulletsHitAir = 0;
    qmbTotalPctAir = 0.0F;
    qmbTotalBulletsFiredHitGround = 0;
    qmbTotalPctGround = 0.0F;
    qmbTotalBombsFired = 0;
    qmbTotalBombsHit = 0;
    qmbTotalPctBomb = 0.0F;
    qmbTotalRocketsFired = 0;
    qmbTotalRocketsHit = 0;
    qmbTotalPctRocket = 0.0F;
    qmbTotalAirKill = 0;
    qmbTotalGroundKill = 0;
    qmbTotalTankKill = 0;
    qmbTotalCarKill = 0;
    qmbTotalArtilleryKill = 0;
    qmbTotalAAAKill = 0;
    qmbTotalTrainKill = 0;
    qmbTotalAirStaticKill = 0;
    qmbTotalShipKill = 0;
    qmbTotalBridgeKill = 0;
    qmbTotalPara = 0;
    qmbTotalDead = 0;
    qmbTotalScore = 0;
    resetMissionStat();
    resetSessionStat();
    saveStat();
  }

  public GUIQuickStats(GWindowRoot paramGWindowRoot)
  {
    super(71);
    resetSessionStat();
    resetMissionStat();

    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("qmb.stat");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));
    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bRes = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.wScrollStats = new Table(this.dialogClient);
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public static class Table extends GWindowTable {
    public String[][] tableElements = new String[24][4];

    public int countRows() { return 24; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      String str = this.tableElements[paramInt1][paramInt2];
      int i = 0;
      if (paramInt2 > 0) {
        i = 1;
      }
      if (paramInt1 % 2 == 0)
        setCanvasColor(new GColor(93, 154, 173));
      else {
        setCanvasColor(new GColor(113, 174, 193));
      }
      draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      setCanvasColorBLACK();
      draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
    }

    public void afterCreated() {
      super.afterCreated();
      addColumn("", null);
      addColumn(I18N.gui("q.LastMission"), null);
      addColumn(I18N.gui("q.CurrentSession"), null);
      addColumn(I18N.gui("q.Career"), null);
      getColumn(0).setRelativeDx(34.0F);
      for (int i = 1; i < 4; i++) {
        getColumn(i).setRelativeDx(22.0F);
      }
      alignColumns();
      this.vSB.scroll = rowHeight(0);
      resized();
    }

    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }

    public Table(GWindow paramGWindow) {
      super();
      this.bNotify = true;
      this.wClient.bNotify = true;
    }
  }

  public class DialogClient extends GUIDialogClient
  {
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2)
        return super.notify(paramGWindow, paramInt1, paramInt2);
      if (paramGWindow == GUIQuickStats.this.bPrev) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUIQuickStats.this.bRes) {
        GUIQuickStats.resetQMBStat();
        GUIQuickStats.this.fillTableElements();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render()
    {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(630.0F), x1024(924.0F), 2.5F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(0.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuickStats.this.i18n("q.BAC"));
      draw(x1024(285.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuickStats.this.i18n("q.RES"));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIQuickStats.this.bPrev.setPosC(x1024(85.0F), y1024(689.0F));
      GUIQuickStats.this.bRes.setPosC(x1024(370.0F), y1024(689.0F));

      GUIQuickStats.this.wScrollStats.set1024PosSize(80.0F, 32.0F, 880.0F, 540.0F);
    }

    public DialogClient()
    {
    }
  }
}