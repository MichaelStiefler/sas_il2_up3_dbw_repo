package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class H8K1 extends H8K
  implements TypeBomber
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 35.0F) { f1 = 35.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 25.0F) break; f2 = 25.0F; bool = false; break;
    case 1:
      if (f1 < -22.0F) { f1 = -22.0F; bool = false; }
      if (f1 > 22.0F) { f1 = 22.0F; bool = false; }
      if (f2 < -57.0F) { f2 = -57.0F; bool = false; }
      if (f2 <= 33.0F) break; f2 = 33.0F; bool = false; break;
    case 2:
      if (f1 < -22.0F) { f1 = -22.0F; bool = false; }
      if (f1 > 22.0F) { f1 = 22.0F; bool = false; }
      if (f2 < -57.0F) { f2 = -57.0F; bool = false; }
      if (f2 <= 33.0F) break; f2 = 33.0F; bool = false; break;
    case 3:
      if (f2 < 0.0F) { f2 = 0.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 4:
      if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
      if (f1 > 25.0F) { f1 = 25.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 25.0F) break; f2 = 25.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[3].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[4].setHealth(paramFloat);
      break;
    case 6:
      break;
    case 7:
      break;
    case 8:
      break;
    case 9:
    }
  }

  public void doMurderPilot(int paramInt) {
    if (paramInt > 5) {
      return;
    }
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("HMask" + (paramInt + 1) + "_D0", false);
    hierMesh().chunkVisible("Pilot" + (paramInt + 1) + "_D1", true);
    hierMesh().chunkVisible("Head" + (paramInt + 1) + "_D0", false);
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
  }

  public void typeBomberAdjAltitudePlus() {
  }

  public void typeBomberAdjAltitudeMinus() {
  }

  public void typeBomberAdjSpeedReset() {
  }

  public void typeBomberAdjSpeedPlus() {
  }

  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = H8K1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "H8K");
    Property.set(localClass, "meshName", "3DO/Plane/H8K1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar04());
    Property.set(localClass, "meshName_ja", "3DO/Plane/H8K1(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 2048.0F);

    Property.set(localClass, "FlightModel", "FlightModels/H8K1.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "16x60", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, null, null, null, null, null, null, null, null, "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ", "BombGun60kgJ" });

    weaponsRegister(localClass, "8x250", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", null, null, "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", "BombGun250kgJ", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x4512", new String[] { "MGunMG81t 450", "MGunMG81t 450", "MGunMG81t 450", "MGunMG15120MGt 450", "MGunMG15120MGt 600", "BombGun4512", "BombGun4512", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}