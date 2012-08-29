package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.GameTrack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.JU_88MSTL;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.trains.Train;
import com.maddox.il2.objects.trains.Wagon;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.cars.CarGeneric;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public class Voice extends VoiceBase
{
  private int SpeakRearGunKill;
  private int SpeakPullUp;
  private int SpeakRearGunShake;
  private int[][] SpeakAttackByRockets = new int[2][4];
  private int[][] SpeakAttackByBombs = new int[2][4];
  private int[][] SpeakTargetDestroyed = new int[2][4];
  private int[][] SpeakDanger = new int[2][4];
  private int[][] SpeakHelpNeeded = new int[2][4];
  private int[][] SpeakClearTail = new int[2][4];
  private int[][] SpeakCoverMe = new int[2][4];
  private int[][] SpeakCoverProvided = new int[2][4];
  private int[][] SpeakHelpFromAir = new int[2][4];
  private int[][] SpeakNiceKill = new int[2][4];
  private int[][] SpeakEndOfAmmo = new int[2][4];
  private int[][] SpeakMayday = new int[2][4];
  private int[][] SpeakCheckFire = new int[2][4];
  private int[][] SpeakHint = new int[2][4];
  private int[][] SpeakToReturn = new int[2][4];
  public int[][] SpeakBailOut = new int[2][4];

  private int[][] SpeakAttackGround = new int[2][4];
  private int[][] SpeakAttackMyTarget = new int[2][4];
  private int[][] SpeakAttackBombers = new int[2][4];
  private int[][] SpeakTargetAll = new int[2][4];
  private int[][] SpeakDropTanks = new int[2][4];
  private int[][] SpeakBreak = new int[2][4];
  private int[][] SpeakRejoin = new int[2][4];
  private int[][] SpeakTightFormation = new int[2][4];
  private int[][] SpeakLoosenFormation = new int[2][4];
  private int[][] SpeakOk = new int[2][4];
  private int[][] SpeakLandingPermited = new int[2][4];

  private int[] SpeakBombing = new int[2];
  private int[] SpeakEndBombing = new int[2];
  private int[] SpeakEndGattack = new int[2];
  private int[] SpeakDeviateSmall = new int[2];
  private int[] SpeakDeviateBig = new int[2];
  private int[] SpeakHeading = new int[2];
  private int[] SpeakAltitude = new int[2];
  public int[] SpeakNearBombers = new int[2];
  private int[] Speak1minute = new int[2];
  private int[] Speak5minutes = new int[2];
  public int[] SpeakBombersUnderAttack = new int[2];
  public int[] SpeakBombersUnderAttack1 = new int[2];
  public int[] SpeakBombersUnderAttack2 = new int[2];
  private int[] SpeakEnemyDetected = new int[2];
  private static Aircraft internalAir = null;
  private static RangeRandom rnd = new RangeRandom();

  public static int[] str = new int[8];
  public static int[][] str2 = new int[4][8];
  public static final int afPILOT = 0;
  public static final int afNEARFRIEND = 1;
  public static final int afWINGMAN = 2;
  public static final int afLEADER = 3;
  public static final int afREARGUN = 4;
  public static final int afLAND = 5;
  public static final int afBOMBERS = 6;
  public static final int anNONE = 0;
  public static final int anLEADER = 1;
  public static final int anBOMBER1 = 2;
  public static final int anBOMBER2 = 3;
  public static final int anLAND = 4;
  public static final int anREARGUN = 5;
  public static final int anACTOR6 = 6;
  public static final int anACTOR7 = 7;
  public static final int anACTOR8 = 8;
  public static final int anACTOR9 = 9;
  private static int[] headings = { 1, 3, 5, 6, 15, 20, 21, 27, 28, 30, 35, 36 };

  private static int[] altitudes = { 2, 4, 7, 19, 26, 29, 34, 37, 41, 42, 46, 47, 53, 54, 58, 59, 63, 64, 68, 69, 8 };

  private static final int[] clkstr = { 16, 23, 31, 38, 43, 50, 55, 60, 65, 70, 9, 12, 16, 24, 32, 39, 44, 51, 56, 61, 66, 71, 10, 13, 17, 25, 33, 40, 45, 52, 57, 62, 67, 72, 11, 14, 18 };

  private static final int[] aNumber = { 285, 326, 331, 287, 284, 329, 327, 281, 297, 330, 282, 338, 332, 286, 283, 328 };

  private static int[] your_o_clock = { 206, 173, 182, 185, 188, 191, 194, 197, 200, 203, 176, 179, 206, 174, 183, 186, 189, 192, 195, 198, 201, 204, 177, 180, 207, 175, 184, 187, 190, 193, 196, 199, 202, 205, 178, 181, 208 };

  private static final int[] thisIsNumber = { 158, 360, 354, 346, 345, 357, 351, 333, 350, 358, 343, 337, 359, 349, 344, 336 };

  private static final int[] thisIsPara = { 334, 347, 352, 355 };

  private static final int[] thisIsWing = { 335, 348, 353, 356 };

  private static final int[] thisIsRotte = { 361, 362, 363, 364 };

  private static final int[] thisIsSwarm = { 365, 366, 367, 368 };

  private static final int[] pilotVoice = { 6, 9, 8, 7, 7, 9, 8, 6, 8, 9, 7, 6, 9, 8, 7, 6 };
  public static final int M_SYNC = 0;
  public static final int M_DELAY = 1;
  public static final int M_IMMED = 2;
  protected static int syncMode;
  private static Point3d P3d;

  public static Voice cur()
  {
    return World.cur().voicebase;
  }

  public static boolean isEnableVoices() {
    return VoiceFX.isEnabled();
  }

  public static void setEnableVoices(boolean paramBoolean)
  {
    VoiceFX.setEnabled(paramBoolean);
  }

  static boolean frequency() {
    return Main3D.cur3D().ordersTree.frequency();
  }

  public static void setSyncMode(int paramInt)
  {
    syncMode = paramInt;
  }

  public static void reset() {
    for (int i = 0; i < str.length; i++) str[i] = 0;
    for (i = 0; i < str2.length; i++)
      if (str2[i] != null)
        for (int j = 0; j < str2[i].length; j++) str2[i][j] = 0;
  }

  public static void endSession()
  {
    VoiceFX.end();
  }

  private static boolean isPunctChar(char paramChar) {
    return (paramChar == '!') || (paramChar == '?') || (paramChar == '.') || (paramChar == ',') || (paramChar == '@');
  }

  public static int actorVoice(Aircraft paramAircraft, int paramInt) {
    if ((paramAircraft == null) || (!Actor.isValid(paramAircraft)) || ((paramAircraft instanceof JU_88MSTL))) return 0;
    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) {
      if (((Maneuver)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).silence) return 0;
      if (((Maneuver)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).kamikaze) return 0;
    }
    Squadron localSquadron = paramAircraft.getSquadron();
    if (localSquadron == null) return 0;
    Wing localWing = null;
    Aircraft localAircraft1 = null;
    if ((paramInt == 3) || (paramInt == 0)) {
      if ((paramAircraft.isNet()) && (!paramAircraft.isNetMaster())) return 0;
      float f = 0.0F;
      for (int j = 0; j < localSquadron.wing.length; j++) {
        if (localSquadron.wing[j] == null) continue; f += localSquadron.wing[j].aircReady();
      }
      if (f < 2.0F) return 0;
    }
    switch (paramInt) {
    case 3:
      for (int i = 0; i < localSquadron.wing.length; i++) {
        if (localSquadron.wing[i] != null) {
          localWing = localSquadron.wing[i];
          break;
        }
      }
      if (localWing == null) return 0;
      if ((localWing.airc.length > 0) && (localWing.airc[0] != null))
        localAircraft1 = localWing.airc[0];
      else return 0;
      if (!Actor.isAlive(localAircraft1)) return 0;
      World.cur(); if (localAircraft1 == World.getPlayerAircraft()) return 0;
      if (localAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[0] > 50) return 0;
      return 1;
    case 1:
      Aircraft localAircraft2 = War.getNearestFriend(paramAircraft);
      if (!Actor.isAlive(localAircraft2)) return 0;
      if (localAircraft2 == World.getPlayerAircraft()) return 0;
      if (Mission.isDogfight()) return 0;
      return pilotVoice[(localAircraft2.getWing().indexInSquadron() * 4 + localAircraft2.aircIndex())];
    case 4:
      if (!Actor.isAlive(paramAircraft)) return 0;
      if ((paramAircraft == World.getPlayerAircraft()) && (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsAIControlled)) return 5;
      return 0;
    case 0:
      if (!Actor.isAlive(paramAircraft)) return 0;
      if (paramAircraft == World.getPlayerAircraft()) return 0;
      if (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[0] > 50) return 0;

      return pilotVoice[(paramAircraft.getWing().indexInSquadron() * 4 + paramAircraft.aircIndex())];
    case 6:
      return 2;
    case 5:
      return 4;
    case 2:
    }
    return 0;
  }

  public static void new_speak(int paramInt1, int paramInt2, String paramString, int[] paramArrayOfInt, int paramInt3) {
    speak(paramInt1, paramInt2, paramString, paramArrayOfInt, paramInt3, true, true);
  }

  public static void speak(int paramInt1, int paramInt2, String paramString, int[] paramArrayOfInt, int paramInt3) {
    speak(paramInt1, paramInt2, paramString, paramArrayOfInt, paramInt3, true, false);
  }

  public static void speak(int paramInt1, int paramInt2, String paramString, int[] paramArrayOfInt, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = syncMode;
    syncMode = 0;

    if (paramInt1 == 0) return;

    if ((paramBoolean1) && (paramInt1 != 5)) {
      ((NetUser)NetEnv.host()).speekVoice(syncMode, paramInt1, paramInt2, paramString, paramArrayOfInt, paramInt3, paramBoolean2);
    }

    if (!Config.isUSE_RENDER()) return;

    int j = World.getPlayerArmy();
    if (Main3D.cur3D().ordersTree.frequency()) {
      if (paramInt2 != j) return;
    }
    else if (paramInt2 == j) return;

    play(i, paramInt1, paramInt2, paramString, paramArrayOfInt, paramBoolean2);

    if (World.cur().isDebugFM()) {
      String str1 = "";
      for (int m = 0; (m < paramArrayOfInt.length) && (paramArrayOfInt[m] != 0); m++) str1 = str1 + " " + VoiceBase.vbStr[paramArrayOfInt[m]];
      System.out.print("AN: ");
      System.out.print(paramInt1);
      System.out.print("  Army: ");
      System.out.print(paramInt2);
      System.out.println("  : " + str1);
    }

    for (int k = 0; (k < paramArrayOfInt.length) && (paramArrayOfInt[k] != 0); k++) paramArrayOfInt[k] = 0; 
  }

  public static void play(int paramInt1, int paramInt2, int paramInt3, String paramString, int[] paramArrayOfInt, boolean paramBoolean)
  {
    if (Main3D.cur3D().gameTrackPlay() != null)
      return;
    if (Main3D.cur3D().gameTrackRecord() != null)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(4);
        localNetMsgGuaranted.writeInt(paramInt1);
        localNetMsgGuaranted.writeInt(paramInt2);
        localNetMsgGuaranted.writeInt(paramInt3);
        localNetMsgGuaranted.writeBoolean(paramBoolean);
        int i = paramArrayOfInt.length;
        localNetMsgGuaranted.writeInt(i);
        for (int j = 0; j < i; j++)
          localNetMsgGuaranted.writeInt(paramArrayOfInt[j]);
        if (paramString != null)
          localNetMsgGuaranted.write255(paramString);
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      }
      catch (Exception localException) {
      }
    HUD.message(paramArrayOfInt, paramInt2, paramInt3, paramBoolean);
    VoiceFX.play(paramInt1, paramInt2, paramInt3, paramString, paramArrayOfInt);
  }
  public static boolean netInputPlay(NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readInt();
    int j = paramNetMsgInput.readInt();
    int k = paramNetMsgInput.readInt();
    boolean bool = paramNetMsgInput.readBoolean();
    int m = paramNetMsgInput.readInt();
    int[] arrayOfInt = new int[m];
    for (int n = 0; n < m; n++)
      arrayOfInt[n] = paramNetMsgInput.readInt();
    String str1 = null;
    if (paramNetMsgInput.available() > 0) {
      str1 = paramNetMsgInput.read255();
    }
    HUD.message(arrayOfInt, j, k, bool);
    VoiceFX.play(i, j, k, str1, arrayOfInt);
    return true;
  }

  public static void speak(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4) {
    str[0] = paramInt3;
    str[1] = 0;
    speak(paramInt1, paramInt2, paramString, str, paramInt4);
  }

  public static void new_speak(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4) {
    str[0] = paramInt3;
    str[1] = 0;
    new_speak(paramInt1, paramInt2, paramString, str, paramInt4);
  }

  public static void speakRandom(int paramInt1, int paramInt2, String paramString, int[] paramArrayOfInt, int paramInt3)
  {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++);
    if (i < 1) return;
    int j = rnd.nextInt(0, i - 1);
    speak(paramInt1, paramInt2, paramString, paramArrayOfInt[j], paramInt3);
  }

  public static void speakNewRandom(int paramInt1, int paramInt2, String paramString, int[] paramArrayOfInt, int paramInt3)
  {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++);
    if (i < 1) return;
    int j = rnd.nextInt(0, i - 1);
    new_speak(paramInt1, paramInt2, paramString, paramArrayOfInt[j], paramInt3);
  }

  public static void airSpeaksArray(Aircraft paramAircraft, int paramInt1, int[] paramArrayOfInt, int paramInt2) {
    int i = actorVoice(paramAircraft, paramInt1);
    if (i == 0) return;
    int j = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speak(i, j, str1, paramArrayOfInt, paramInt2);
  }

  public static void airSpeaksNewArray(Aircraft paramAircraft, int paramInt1, int[] paramArrayOfInt, int paramInt2) {
    int i = actorVoice(paramAircraft, paramInt1);
    if (i == 0) return;
    int j = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    new_speak(i, j, str1, paramArrayOfInt, paramInt2);
  }

  public static void airSpeaks(Aircraft paramAircraft, int paramInt1, int paramInt2, int paramInt3) {
    str[0] = paramInt2;
    str[1] = 0;
    airSpeaksArray(paramAircraft, paramInt1, str, paramInt3);
  }

  public static void airSpeaks(Aircraft paramAircraft, int paramInt1, int paramInt2) {
    str[0] = paramInt1;
    str[1] = 0;
    airSpeaksArray(paramAircraft, 0, str, paramInt2);
  }

  public static void airSpeaksNew(Aircraft paramAircraft, int paramInt1, int paramInt2) {
    str[0] = paramInt1;
    str[1] = 0;
    airSpeaksNewArray(paramAircraft, 0, str, paramInt2);
  }

  public static void speakRandomArray(Aircraft paramAircraft, int[][] paramArrayOfInt, int paramInt) {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != null); i++) {
      if (paramArrayOfInt[i][0] != 0)
        if (paramArrayOfInt[i][1] == 0) { airSpeaks(paramAircraft, paramArrayOfInt[i][0], paramInt);
        }
        else
        {
          int j;
          if (paramArrayOfInt[i][2] == 0) j = 2; else
            j = 3;
          airSpeaks(paramAircraft, paramArrayOfInt[i][rnd.nextInt(0, j - 1)], paramInt);
        }
    }
    reset();
  }

  public static void speakRandom(Aircraft paramAircraft, int[] paramArrayOfInt, int paramInt)
  {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++);
    if (i < 1) return;
    int j = rnd.nextInt(0, i - 1);
    airSpeaks(paramAircraft, paramArrayOfInt[j], paramInt);
  }

  public static void speakNewRandom(Aircraft paramAircraft, int[] paramArrayOfInt, int paramInt)
  {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++);
    if (i < 1) return;
    int j = rnd.nextInt(0, i - 1);
    airSpeaksNew(paramAircraft, paramArrayOfInt[j], paramInt);
  }

  public static void speakAltitude(Aircraft paramAircraft, int paramInt)
  {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakAltitude[j]) return;
    cur().SpeakAltitude[j] = (i + 20);
    str[0] = 118;
    if (paramInt > 10000) paramInt = 10000;
    if (paramInt < 1) paramInt = 1;
    str[1] = altitudes[(paramInt / 500)];
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 3);
  }

  public static void speakHeading(Aircraft paramAircraft, Vector3f paramVector3f) {
    float f = 57.324841F * (float)Math.atan2(paramVector3f.x, paramVector3f.y);
    int i = (int)f;
    i = (i + 180) % 360;
    speakHeading(paramAircraft, i);
  }

  public static void speakHeading(Aircraft paramAircraft) {
    Vector3d localVector3d = paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vwld;
    float f = 57.324841F * (float)Math.atan2(localVector3d.jdField_x_of_type_Double, localVector3d.jdField_y_of_type_Double);
    int i = (int)f;
    i = (i + 180) % 360;
    speakHeading(paramAircraft, i);
  }

  public static void speakHeadingToHome(Aircraft paramAircraft, Point3d paramPoint3d) {
    float f = 57.324841F * (float)Math.atan2(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double);
    int i = (int)f;
    i = (i + 180) % 360;
    while (i < 0) i += 360;
    while (i >= 360) i -= 360;
    i /= 30;
    int j = actorVoice(paramAircraft, 5);
    int k = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    new_speak(j, k, str1, 235, 2);
    str[0] = 165;
    str[1] = headings[i];
    str[2] = 0;
    airSpeaksArray(paramAircraft, 5, str, 2);
    speak(j, k, str1, 252, 2);
  }

  public static void speakHeadingToTarget(Aircraft paramAircraft, Point3d paramPoint3d) {
    float f = 57.324841F * (float)Math.atan2(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double);
    int i = (int)f;
    i = (i + 180) % 360;
    while (i < 0) i += 360;
    while (i >= 360) i -= 360;
    i /= 30;
    int j = actorVoice(paramAircraft, 5);
    int k = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    str[0] = 165;
    str[1] = headings[i];
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 5, str, 2);
    speak(j, k, str1, 252, 2);
  }

  public static void speakHeading(Aircraft paramAircraft, int paramInt) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakHeading[j]) return;
    cur().SpeakHeading[j] = (i + 20);
    while (paramInt < 0) paramInt += 360;
    while (paramInt >= 360) paramInt -= 360;
    paramInt /= 30;
    str[0] = 165;
    str[1] = headings[paramInt];
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 3);
  }

  public static void speak5minutes(Aircraft paramAircraft)
  {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().Speak5minutes[j]) return;
    cur().Speak5minutes[j] = (i + 40);
    str[0] = 81;
    if (rnd.nextFloat() < 0.5F) str[1] = 49; else
      str[1] = 74;
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 1);
  }

  public static void speak1minute(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().Speak1minute[j]) return;
    cur().Speak1minute[j] = (i + 40);
    str[0] = 81;
    if (rnd.nextFloat() < 0.5F) str[1] = 22; else
      str[1] = 73;
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 1);
  }

  public static void speakBeginGattack(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    if (paramAircraft.aircNumber() < 2) return;
    int i = actorVoice(paramAircraft, 3);
    if (i == 0) return;

    int j = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    new_speak(i, j, str1, 81, 1);
    speak(i, j, str1, 169, 1);
    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) {
      Pilot localPilot = (Pilot)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if (Actor.isValid(localPilot.jdField_target_ground_of_type_ComMaddoxIl2EngineActor)) {
        localObject = localPilot.jdField_target_ground_of_type_ComMaddoxIl2EngineActor;
        if ((localObject instanceof CarGeneric)) str[0] = 162;
        else if ((localObject instanceof TankGeneric)) str[0] = 152;
        else if ((localObject instanceof AAA)) str[0] = 111;
        else if ((localObject instanceof Wagon)) str[0] = 161;
        else if ((localObject instanceof Train)) str[0] = 161;
        else if ((localObject instanceof TgtShip)) str[0] = 99;
        str[1] = 0;
        speak(i, j, str1, str[0], 1);
        speak(i, j, str1, 75, 1);
      }
      Object localObject = paramAircraft.getSquadron();
      Wing localWing1 = paramAircraft.getWing();
      Wing localWing2 = null;

      for (int k = 0; k < ((Squadron)localObject).wing.length; k++) {
        if (localObject.wing[k] != null) {
          localWing2 = localObject.wing[k];
          break;
        }
      }
      if (localWing1.airc.length > 0)
        for (int m = 0; m < localWing1.airc.length; m++)
          if (((localWing2 != localWing1) || (m != 0)) && 
            (localWing1.airc[m] != null)) speakAttackGround(localWing1.airc[m]);
    }
  }

  public static void speakLeaderEndGattack()
  {
    if (!Actor.isValid(internalAir)) return;
    str[0] = 81;
    str[1] = 153;
    str[2] = 136;
    str[3] = 0;
    airSpeaksNewArray(internalAir, 3, str, 1);
  }

  public static void speakLeaderRepeatGattack() {
    if (!Actor.isValid(internalAir)) return;
    str[0] = 81;
    str[1] = 138;
    str[2] = 136;
    str[3] = 0;
    airSpeaksNewArray(internalAir, 3, str, 1);
  }

  public static void speakEndGattack(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    if (paramAircraft.aircIndex() > 0) return;

    if ((paramAircraft instanceof TypeBomber)) return;
    if (paramAircraft.aircNumber() < 2) return;

    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakEndGattack[j]) return;
    cur().SpeakEndGattack[j] = (i + 60);

    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) {
      Pilot localPilot = (Pilot)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      internalAir = paramAircraft;
      if (Actor.isValid(localPilot.jdField_target_ground_of_type_ComMaddoxIl2EngineActor))
        new MsgAction(10.0D) {
          public void doAction() {
            Voice.speakLeaderRepeatGattack();
          }
        };
      else new MsgAction(10.0D) {
          public void doAction() {
            Voice.speakLeaderEndGattack();
          }
        }; 
    }
  }

  public static void speakAttackByRockets(Aircraft paramAircraft)
  {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakAttackByRockets[j][k]) return;
    cur().SpeakAttackByRockets[j][k] = (i + 60);

    setSyncMode(2);
    speakThisIs(paramAircraft);
    str[0] = 108;
    str[1] = 79;
    str[2] = 141;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakAttackByBombs(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakAttackByBombs[j][k]) return;
    cur().SpeakAttackByBombs[j][k] = (i + 60);

    setSyncMode(2);
    speakThisIs(paramAircraft);
    str[0] = 85;
    str[1] = 0;
    airSpeaksArray(paramAircraft, 0, str, 1);
  }

  public static void speakNearBombers(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakNearBombers[j]) return;
    cur().SpeakNearBombers[j] = (i + 300);

    int k = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    str[0] = 219;
    str[1] = 220;
    str[2] = 221;
    str[3] = 136;
    str[4] = 0;
    airSpeaksNewArray(paramAircraft, 6, str, 3);
  }

  public static void speakCheckFire(Aircraft paramAircraft1, Aircraft paramAircraft2) {
    if (!Actor.isAlive(paramAircraft1)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft1.getArmy() - 1 & 0x1;
    int k = paramAircraft1.aircIndex();
    if (i < cur().SpeakCheckFire[j][k]) return;
    cur().SpeakCheckFire[j][k] = (i + 15);

    int m = paramAircraft2.getWing().indexInSquadron() * 4 + paramAircraft2.aircIndex();
    if (m > 15) return;
    str[0] = aNumber[m];
    str[1] = 0;
    setSyncMode(2);
    airSpeaksNewArray(paramAircraft1, 0, str, 1);

    str[0] = 87;
    str[1] = 166;
    str[2] = 0;
    setSyncMode(2);
    speakRandom(paramAircraft1, str, 1);
  }

  public static void speakBombersUnderAttack(Aircraft paramAircraft, boolean paramBoolean) {
    int i = (int)(Time.current() / 60000L);
    if (!Actor.isValid(paramAircraft)) return;
    int j = (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) && (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance() < 20000.0F) ? 1 : 0;

    int k = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int m = k - 1 & 0x1;
    if (!paramBoolean) {
      if (i < cur().SpeakBombersUnderAttack[m]) return;
      cur().SpeakBombersUnderAttack[m] = (i + 25);
      if (j != 0) new_speak(2, k, str1, 225, 2); else
        new_speak(2, k, str1, 226, 2);
    } else {
      if (actorVoice(paramAircraft, 1) == 0) return;
      if (i < cur().SpeakBombersUnderAttack1[m]) {
        if (i < cur().SpeakBombersUnderAttack2[m]) {
          cur().SpeakBombersUnderAttack2[m] = (i + 5);
          str[0] = 119;
          str[1] = 215;
          str[2] = 213;
          str[3] = 216;
          str[4] = 0;
          speakNewRandom(2, k, str1, str, 2);

          str[0] = 226;
          str[1] = 0;
          speak(2, k, str1, str, 2);
        }
        return;
      }
      cur().SpeakBombersUnderAttack1[m] = (i + 25);
      if (j != 0) new_speak(2, k, str1, 226, 2); else
        new_speak(2, k, str1, 228, 2);
    }
  }

  public static void speakDanger(Aircraft paramAircraft, int paramInt) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakDanger[j][k]) return;
    cur().SpeakDanger[j][k] = (i + 27);

    setSyncMode(2);
    if (paramInt == 4) {
      int m = actorVoice(paramAircraft, 4);
      if (m == 0) return;
      int n = paramAircraft.getArmy();
      String str1 = paramAircraft.getRegiment().speech();
      str[0] = 260;
      str[1] = 255;
      str[2] = 254;
      speakNewRandom(m, n, str1, str, 1);
    } else {
      speakClearTail(paramAircraft);
    }
  }

  public static void speakClearTail(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.aircIndex();
    int k = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakClearTail[k][j]) return;
    cur().SpeakClearTail[k][j] = (i + 37);

    setSyncMode(2);
    speakThisIs(paramAircraft);
    float f = rnd.nextFloat();
    if (f < 0.33F)
      str[0] = 90;
    else if (f < 0.66F)
      str[0] = 218;
    else {
      str[0] = 146;
    }
    f = rnd.nextFloat();
    if (f < 0.5F)
      str[1] = 115;
    else {
      str[1] = 89;
    }
    str[2] = 0;
    airSpeaksArray(paramAircraft, 0, str, 1);
  }

  public static void speakBombing(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakBombing[j]) return;
    cur().SpeakBombing[j] = (i + 1);

    reset();
    str2[0][0] = 81;
    str2[1][0] = 'è';
    str2[1][1] = 'ç';
    str2[1][2] = 85;
    speakRandomArray(paramAircraft, str2, 1);
  }

  public static void speakEndBombing(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakEndBombing[j]) return;
    cur().SpeakEndBombing[j] = (i + 300);

    int k = paramAircraft.getRegiment().diedBombers;
    str[0] = 107;
    if (k > 1)
      str[1] = 222;
    else if (k == 1)
      str[1] = 223;
    else {
      str[1] = 224;
    }
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 6, str, 2);
  }

  public static void speakDeviateSmall(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakDeviateSmall[j]) return;
    cur().SpeakDeviateSmall[j] = (i + 4);

    str[0] = 170;
    str[1] = 150;
    str[2] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 2);
    Wing localWing = paramAircraft.getWing();
    if (localWing.airc.length > 0) {
      speakHeading(localWing.airc[0]);
      speakAltitude(paramAircraft, (int)localWing.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
      str[0] = 136;
      str[1] = 0;
      airSpeaksArray(paramAircraft, 3, str, 2);
    }
  }

  public static void speakDeviateBig(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    if (i < cur().SpeakDeviateBig[j]) return;
    cur().SpeakDeviateBig[j] = (i + 10);

    if (rnd.nextFloat() < 0.5F) {
      if (rnd.nextFloat() < 0.5F) str[0] = 90; else
        str[0] = 214;
    }
    else if (rnd.nextFloat() < 0.5F) str[0] = 170; else {
      str[0] = 217;
    }
    str[1] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 2);

    str[0] = 171;
    str[1] = 149;
    str[2] = 0;
    airSpeaksArray(paramAircraft, 3, str, 2);
    Wing localWing = paramAircraft.getWing();
    if (localWing.airc.length > 0) {
      speakHeading(localWing.airc[0]);
      speakAltitude(paramAircraft, (int)localWing.airc[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
    }
  }

  public static void speakEnemyDetected(Aircraft paramAircraft1, Aircraft paramAircraft2) {
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft1.getArmy() - 1 & 0x1;
    if (i < cur().SpeakEnemyDetected[j]) return;
    cur().SpeakEnemyDetected[j] = (i + 40);
    if (!(paramAircraft1.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) return;
    if (paramAircraft1.aircNumber() < 2) return;

    Aircraft localAircraft = paramAircraft2;
    if (localAircraft == null) return;
    str[0] = 81;
    if ((localAircraft instanceof TypeFighter)) str[1] = 107;
    else if ((localAircraft instanceof TypeBomber)) str[1] = 84;
    else if ((localAircraft instanceof TypeDiveBomber)) str[1] = 84; else
      str[1] = 83;
    str[2] = speakTarget_O_Clock(paramAircraft1, localAircraft);
    str[3] = 0;
    int k = actorVoice(paramAircraft1, 3);
    if (k == 0) return;
    airSpeaksNewArray(paramAircraft1, 3, str, 1);
  }

  private static int speakTarget_O_Clock(Aircraft paramAircraft, Actor paramActor)
  {
    int i = paramAircraft.target_O_Clock(paramActor);
    if ((i < 1) || (i > 36)) return 0;
    return clkstr[i];
  }

  public static void speakCheckYour6(Aircraft paramAircraft1, Aircraft paramAircraft2) {
    if (!Actor.isAlive(paramAircraft1)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft1.getArmy() - 1 & 0x1;
    int k = paramAircraft1.aircIndex();
    if (i < cur().SpeakHint[j][k]) return;
    cur().SpeakHint[j][k] = (i + 2);
    if (paramAircraft1.aircNumber() < 2) return;
    int m = paramAircraft1.getWing().indexInSquadron() * 4 + paramAircraft1.aircIndex();
    if (m > 15) return;
    str[0] = aNumber[m];
    str[1] = 88;
    str[2] = your_o_clock[paramAircraft1.target_O_Clock(paramAircraft2)];
    airSpeaksNewArray(paramAircraft1, 1, str, 1);
  }

  public static void speakToReturn(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakToReturn[j][k]) return;
    cur().SpeakToReturn[j][k] = (i + 5);
    if (paramAircraft.aircNumber() < 2) return;
    int m = paramAircraft.getWing().indexInSquadron() * 4 + paramAircraft.aircIndex();
    if (m > 15) return;
    str[0] = aNumber[m];
    str[1] = 140;
    str[2] = 136;
    str[3] = 0;
    airSpeaksNewArray(paramAircraft, 3, str, 1);
  }

  public static void speakBailOut(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakBailOut[j][k]) return;
    cur().SpeakBailOut[j][k] = (i + 1);
    if (paramAircraft.aircNumber() < 2) return;
    int m = paramAircraft.getWing().indexInSquadron() * 4 + paramAircraft.aircIndex();
    if (m > 15) return;
    int n = actorVoice(paramAircraft, 1);
    Aircraft localAircraft = War.getNearestFriend(paramAircraft);

    setSyncMode(2);
    if ((localAircraft != null) && ((rnd.nextFloat() > 0.5F) || (paramAircraft == World.getPlayerAircraft()))) {
      airSpeaksNew(localAircraft, aNumber[m], 1);
      str[0] = 82;
      str[1] = 116;
      str[2] = 120;
      str[3] = 0;
      setSyncMode(2);
      speakRandom(localAircraft, str, 1);
    } else {
      if (paramAircraft == World.getPlayerAircraft()) return;
      speakThisIs(paramAircraft);
      str[0] = 121;
      str[1] = 123;
      str[2] = 125;
      str[3] = 0;
      setSyncMode(2);
      speakRandom(paramAircraft, str, 1);
    }
  }

  public static void speakMayday(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakMayday[j][k]) return;
    cur().SpeakMayday[j][k] = (i + 1);

    setSyncMode(2);
    speakThisIs(paramAircraft);
    str[0] = 122;
    str[1] = 91;
    str[2] = 126;
    str[3] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakMissionAccomplished(Aircraft paramAircraft) {
    int i = actorVoice(paramAircraft, 3);
    if (i == 0) return;
    int j = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    new_speak(i, j, str1, 81, 1);
    if (paramAircraft.getRegiment().diedAircrafts == 0)
      speak(i, j, str1, 128, 1);
    else {
      speak(i, j, str1, 127, 1);
    }
    str[0] = 139;
    str[1] = 105;
    str[2] = 168;
    str[3] = 0;
    speakRandom(i, j, str1, str, 1);
    speakHeading(paramAircraft);
    speakAltitude(paramAircraft, (int)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);

    if (!(paramAircraft instanceof TypeFighter)) {
      Aircraft localAircraft = War.getNearestFriendlyFighter(paramAircraft, 50000.0F);
      if (localAircraft != null)
        speakEndBombing(paramAircraft);
    }
  }

  public static void speakThisIs(Aircraft paramAircraft)
  {
    if (paramAircraft == null) return;
    int i = paramAircraft.getWing().indexInSquadron();
    int j = paramAircraft.aircIndex();
    int k = paramAircraft.aircNumber();
    int m = i * 4 + j;
    if (m > 15) return;
    str[0] = thisIsNumber[m];
    str[1] = 0;
    int n = syncMode;
    airSpeaksNewArray(paramAircraft, 0, str, 1);
    setSyncMode(n);
  }

  public static void speakIAm(Aircraft paramAircraft) {
    if (paramAircraft == null) return;
    int i = paramAircraft.getWing().indexInSquadron();
    int j = paramAircraft.aircIndex();
    int k = paramAircraft.aircNumber();
    int m = i * 4 + j;
    if (m > 15) return;
    str[0] = thisIsNumber[m];
    int n = paramAircraft.getArmy() - 1 & 0x1;
    if (n == 0) {
      if (j == 0) {
        if (k == 2) str[0] = thisIsPara[i];
        else if (k > 1) str[0] = thisIsWing[i];
      }
    }
    else if (j == 0) {
      if (k == 2) str[0] = thisIsRotte[i];
      else if (k > 1) str[0] = thisIsSwarm[i];
    }

    str[1] = 0;
    int i1 = syncMode;
    airSpeaksNewArray(paramAircraft, 0, str, 1);
    setSyncMode(i1);
  }

  public static void speakNumber_same_str(int paramInt, Aircraft paramAircraft) {
    if (paramAircraft == null) return;
    int i = paramAircraft.getWing().indexInSquadron();
    int j = paramAircraft.aircIndex();
    int k = i * 4 + j;
    if (k > 15) return;
    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = syncMode;
    speak(paramInt, m, str1, aNumber[k], 2);
    setSyncMode(n);
  }

  public static void speakNumber(int paramInt, Aircraft paramAircraft) {
    if (paramAircraft == null) return;
    int i = paramAircraft.getWing().indexInSquadron();
    int j = paramAircraft.aircIndex();
    int k = i * 4 + j;
    if (k > 15) return;
    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = syncMode;
    new_speak(paramInt, m, str1, aNumber[k], 2);
    setSyncMode(n);
  }

  public static void speakCoverMe(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakCoverMe[j][k]) return;
    cur().SpeakCoverMe[j][k] = (i + 15);

    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = actorVoice(paramAircraft, 0);

    speakThisIs(paramAircraft);
    str[0] = 310;
    str[1] = 309;
    str[2] = 268;
    str[3] = 0;
    speakRandom(n, m, str1, str, 1);
  }

  public static void speakYouAreClear(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakCoverMe[j][k]) return;
    cur().SpeakCoverMe[j][k] = (i + 15);

    speakThisIs(paramAircraft);
    str[0] = 341;
    str[1] = 0;
    airSpeaksArray(paramAircraft, 0, str, 1);
  }

  public static void speakTargetAll(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakTargetAll[j][k]) return;
    cur().SpeakTargetAll[j][k] = (i + 1);

    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = actorVoice(paramAircraft, 0);

    speakThisIs(paramAircraft);
    str[0] = 324;
    str[1] = 320;
    str[2] = 277;
    str[3] = 0;
    speakRandom(n, m, str1, str, 1);
  }

  public static void speakAttackFighters(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakTargetAll[j][k]) return;
    cur().SpeakTargetAll[j][k] = (i + 30);

    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = actorVoice(paramAircraft, 0);
    speakThisIs(paramAircraft);
    str[0] = 323;
    str[1] = 306;
    str[2] = 267;
    str[3] = 0;
    speakRandom(n, m, str1, str, 1);
  }

  public static void speakAttackBombers(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakAttackBombers[j][k]) return;
    cur().SpeakAttackBombers[j][k] = (i + 30);

    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int n = actorVoice(paramAircraft, 0);
    speakThisIs(paramAircraft);
    str[0] = 307;
    str[1] = 264;
    str[2] = 0;
    speakRandom(n, m, str1, str, 1);
  }

  public static void speakAttackMyTarget(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;

    speakIAm(paramAircraft);
    str2[0][0] = 278;
    str2[0][1] = 264;
    speakRandomArray(paramAircraft, str2, 1);
  }

  public static void speakAttackGround(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;

    speakIAm(paramAircraft);
    str2[0][0] = 262;
    str2[0][1] = 264;
    speakRandomArray(paramAircraft, str2, 1);
  }

  public static void speakDropTanks(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str2[0][0] = 322;
    str2[0][1] = 275;
    speakRandomArray(paramAircraft, str2, 1);
  }

  public static void speakHelpNeeded(Aircraft paramAircraft, int paramInt)
  {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakHelpNeeded[j][k]) return;
    cur().SpeakHelpNeeded[j][k] = (i + 30);
  }

  public static void speakCoverProvided(Aircraft paramAircraft1, Aircraft paramAircraft2)
  {
    if ((!Actor.isAlive(paramAircraft1)) || (!Actor.isAlive(paramAircraft2))) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft1.aircIndex();
    int k = paramAircraft1.getArmy() - 1 & 0x1;
    if (i < cur().SpeakCoverProvided[k][j]) return;
    cur().SpeakCoverProvided[k][j] = (i + 30);
    int m = paramAircraft1.getArmy();
    String str1 = paramAircraft1.getRegiment().speech();
    int n = actorVoice(paramAircraft1, 0);

    speakThisIs(paramAircraft1);
    if (World.Rnd().nextBoolean()) speakNumber_same_str(n, paramAircraft2);
    str[0] = 310;
    str[1] = 309;
    str[2] = 268;
    str[3] = 0;
    speakRandom(n, m, str1, str, 1);
  }

  public static void speakHelpNeededFromBase(Aircraft paramAircraft, boolean paramBoolean)
  {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = paramAircraft.getArmy() - 1 & 0x1;
    int j = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int k = actorVoice(paramAircraft, 5);

    new_speak(k, j, str1, 235, 1);
    if (paramBoolean) {
      str[0] = 237;
      str[1] = 239;
      str[2] = 0;
      speakRandom(k, j, str1, str, 1);
    } else {
      str[0] = 234;
      str[1] = 233;
      str[2] = 0;
      speakRandom(k, j, str1, str, 1);
    }
  }

  public static void speakHelpFromAir(Aircraft paramAircraft, int paramInt) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakCoverProvided[j][k]) return;
    cur().SpeakCoverProvided[j][k] = (i + 45);

    speakThisIs(paramAircraft);
    switch (paramInt) {
    case 1:
      str[0] = 291;
      str[1] = 294;
      str[2] = 291;
      break;
    case 2:
      str[0] = 341;
      str[1] = 342;
      str[2] = 342;
      break;
    default:
      str[0] = 295;
      str[1] = 339;
      str[2] = 340;
    }
    str[3] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakRearGunKill() {
    int i = (int)(Time.current() / 1000L);
    if (i < cur().SpeakRearGunKill) return;
    cur().SpeakRearGunKill = (i + 20);
    int j = World.getPlayerArmy();
    String str1 = World.getPlayerAircraft().getRegiment().speech();
    setSyncMode(2);
    new_speak(5, j, str1, 258, 1);
  }

  public static void speakRearGunShake() {
    int i = (int)(Time.current() / 1000L);
    if (i < cur().SpeakRearGunShake) return;
    cur().SpeakRearGunShake = (i + 20);
    int j = World.getPlayerArmy();
    String str1 = World.getPlayerAircraft().getRegiment().speech();
    setSyncMode(2);
    if (rnd.nextFloat() < 0.5F)
      new_speak(5, j, str1, 256, 1);
    else
      new_speak(5, j, str1, 259, 1);
  }

  public static void speakNiceKill(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakNiceKill[j][k]) return;
    cur().SpeakNiceKill[j][k] = (i + 5);

    if (paramAircraft == null) return;
    float f = rnd.nextFloat();
    int m = actorVoice(paramAircraft, 1);

    setSyncMode(2);
    if (paramAircraft == World.getPlayerAircraft())
      f = 0.0F;
    else if (m == 0) {
      f = 1.0F;
    }
    if (f > 0.5F) {
      speakThisIs(paramAircraft);
      str[0] = 293;
      str[1] = 290;
      str[2] = 0;
      speakRandom(paramAircraft, str, 2);
    } else {
      int n = paramAircraft.getArmy();
      String str1 = paramAircraft.getRegiment().speech();
      speakNumber(m, paramAircraft);
      str[0] = 289;
      str[1] = 288;
      str[2] = 296;
      str[3] = 0;
      speakRandom(m, n, str1, str, 1);
    }
  }

  public static void speakRearGunTargetDestroyed() {
    Aircraft localAircraft = World.getPlayerAircraft();
    if (!Actor.isAlive(localAircraft)) return;
    int i = localAircraft.getArmy();
    String str1 = localAircraft.getRegiment().speech();
    setSyncMode(2);
    str[0] = 153;
    str[1] = 93;
    str[2] = 154;
    str[3] = 0;
    speakNewRandom(5, i, str1, str, 2);
    str[0] = 257;
    str[1] = 261;
    str[2] = 0;
    speakRandom(5, i, str1, str, 2);
  }

  public static void speakTargetDestroyed(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 1000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakTargetDestroyed[j][k]) return;
    cur().SpeakTargetDestroyed[j][k] = (i + 60);

    if ((paramAircraft == World.getPlayerAircraft()) && (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length > 0) && (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length] < 90) && (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsAIControlled))
    {
      new MsgAction(5.5D) {
        public void doAction() {
          Voice.speakRearGunTargetDestroyed();
        } } ;
    }
    if (Actor.isAlive(paramAircraft))
    {
      speakThisIs(paramAircraft);
      str[0] = 153;
      str[1] = 93;
      str[2] = 154;
      str[3] = 0;
      speakRandom(paramAircraft, str, 1);
    }
  }

  public static void speakEndOfAmmo(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    if (((paramAircraft instanceof TypeBomber)) || ((paramAircraft instanceof TypeTransport))) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakEndOfAmmo[j][k]) return;
    cur().SpeakEndOfAmmo[j][k] = (i + 5);

    setSyncMode(2);
    speakThisIs(paramAircraft);
    str[0] = 292;
    str[1] = 124;
    str[2] = 0;
    speakRandom(paramAircraft, str, 2);
  }

  public static void speakBreak(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if (i < cur().SpeakBreak[j][k]) return;
    cur().SpeakBreak[j][k] = (i + 1);

    speakIAm(paramAircraft);
    str[0] = 302;
    str[1] = 298;
    str[2] = 266;
    str[3] = 0;
    speakRandom(paramAircraft, str, 2);
  }

  public static void speakRejoin(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 318;
    str[1] = 317;
    str[2] = 274;
    str[3] = 0;
    speakRandom(paramAircraft, str, 2);
  }

  public static void speakTightFormation(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 300;
    str[1] = 301;
    str[2] = 279;
    str[3] = 0;
    speakRandom(paramAircraft, str, 2);
  }

  public static void speakLoosenFormation(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    str[0] = 299;
    str[1] = 299;
    str[2] = 265;
    str[3] = 0;
    speakNewRandom(paramAircraft, str, 2);
  }

  public static void speakOk(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 298, 1);
  }

  public static void speakUnable(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 339;
    str[1] = 340;
    str[2] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakNextWayPoint(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 314;
    str[1] = 271;
    str[2] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakPrevWayPoint(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 316;
    str[1] = 319;
    str[2] = 272;
    str[3] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakReturnToBase(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 325;
    str[1] = 305;
    str[2] = 276;
    str[3] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakHangOn(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    speakIAm(paramAircraft);
    str[0] = 308;
    str[1] = 269;
    str[2] = 0;
    speakRandom(paramAircraft, str, 1);
  }

  public static void speakEchelonRight(Aircraft paramAircraft) {
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 304, 2);
  }

  public static void speakEchelonLeft(Aircraft paramAircraft) {
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 303, 2);
  }

  public static void speakLineAbreast(Aircraft paramAircraft) {
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 312, 2);
  }

  public static void speakLineAstern(Aircraft paramAircraft) {
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 313, 1);
  }

  public static void speakVic(Aircraft paramAircraft) {
    speakIAm(paramAircraft);
    airSpeaks(paramAircraft, 321, 1);
  }

  public static void speakPullUp(Aircraft paramAircraft) {
    int i = (int)(Time.current() / 1000L);
    if (i < cur().SpeakPullUp) return;
    cur().SpeakPullUp = (i + 30);
    int j = actorVoice(paramAircraft, 1);
    int k = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    setSyncMode(2);
    speakNumber(j, paramAircraft);
    str[0] = 137;
    str[1] = 172;
    str[2] = 167;
    str[3] = 0;
    speakRandom(j, k, str1, str, 3);
  }

  public static void speakLandingPermited(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = (int)(Time.current() / 60000L);
    int j = paramAircraft.getArmy() - 1 & 0x1;
    int k = paramAircraft.aircIndex();
    if ((paramAircraft != World.getPlayerAircraft()) && 
      (i < cur().SpeakLandingPermited[j][k])) return;
    cur().SpeakLandingPermited[j][k] = (i + 60);

    int m = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speakNumber(4, paramAircraft);
    str[0] = 240;
    str[1] = 243;
    str[2] = 248;
    str[3] = 0;
    speakRandom(4, m, str1, str, 2);
  }

  public static void speakLandingDenied(Aircraft paramAircraft) {
    int i = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speakNumber(4, paramAircraft);
    str[0] = 250;
    str[1] = 236;
    str[2] = 0;
    speakRandom(4, i, str1, str, 2);
  }

  public static void speakWaveOff(Aircraft paramAircraft) {
    int i = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speakNumber(4, paramAircraft);
    str[0] = 236;
    str[1] = 238;
    str[2] = 0;
    speakRandom(4, i, str1, str, 2);
  }

  public static void speakLanding(Aircraft paramAircraft)
  {
    speakThisIs(paramAircraft);
    airSpeaks(paramAircraft, 134, 1);
  }

  public static void speakGoAround(Aircraft paramAircraft) {
    int i = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    int j = actorVoice(paramAircraft, 0);
    speakThisIs(paramAircraft);
    str[0] = 135;
    str[1] = 117;
    str[2] = 0;
    speakRandom(j, i, str1, str, 1);
  }

  public static void speakGoingIn(Aircraft paramAircraft) {
    airSpeaks(paramAircraft, 130, 1);
  }

  public static void testTargDestr(Actor paramActor1, Actor paramActor2) {
    if ((Actor.isValid(paramActor2)) && ((!paramActor2.isNet()) || (paramActor2.isNetMaster())))
    {
      try
      {
        if ((paramActor1 instanceof Aircraft)) {
          if (!(paramActor1 instanceof TypeFighter)) {
            ((Wing)paramActor1.getOwner()).regiment().diedBombers += 1;
            if ((paramActor2 instanceof TypeFighter)) {
              speakBombersUnderAttack((Aircraft)paramActor1, true);
            }
          }
          ((Wing)paramActor1.getOwner()).regiment().diedAircrafts += 1;

          if (((paramActor2 instanceof Aircraft)) && 
            (paramActor1.getArmy() != paramActor2.getArmy()) && 
            (!((Aircraft)paramActor1).buried)) speakNiceKill((Aircraft)paramActor2);

        }
        else if ((paramActor2 instanceof Aircraft)) {
          int i = paramActor1.getArmy();
          if (i == 0) {
            paramActor1.pos.getAbs(P3d);
            i = Front.army(P3d.jdField_x_of_type_Double, P3d.jdField_x_of_type_Double);
          }
          if (i != paramActor2.getArmy())
            speakTargetDestroyed((Aircraft)paramActor2);
        }
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  public static void speakTakeoffPermited(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speakNumber(4, paramAircraft);
    str[0] = 241;
    str[1] = 0;
    speakRandom(4, i, str1, str, 1);
  }

  public static void speakTakeoffDenied(Aircraft paramAircraft) {
    if (!Actor.isAlive(paramAircraft)) return;
    int i = paramAircraft.getArmy();
    String str1 = paramAircraft.getRegiment().speech();
    speakNumber(4, paramAircraft);
    str[0] = 253;
    str[1] = 0;
    speakRandom(4, i, str1, str, 1);
  }

  static
  {
    reset();

    syncMode = 0;

    P3d = new Point3d();
  }
}