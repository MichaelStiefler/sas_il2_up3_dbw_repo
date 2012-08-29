package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_17D extends B_17
  implements TypeBomber
{
  protected void moveBayDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Bay02_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[2].bIsOperable = false;
      break;
    case 3:
      break;
    case 4:
      break;
    case 5:
      break;
    case 6:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[3].bIsOperable = false;
      break;
    case 7:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].bIsOperable = false;
      break;
    case 8:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[5].bIsOperable = false;
      break;
    case 9:
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -11.0F) { f1 = -11.0F; bool = false; }
      if (f1 > 11.0F) { f1 = 11.0F; bool = false; }
      if (f2 < -14.0F) { f2 = -14.0F; bool = false; }
      if (f2 <= 14.0F) break; f2 = 14.0F; bool = false; break;
    case 1:
      if (f1 < -26.0F) { f1 = -26.0F; bool = false; }
      if (f1 > 0.0F) { f1 = 0.0F; bool = false; }
      if (f2 < -14.0F) { f2 = -14.0F; bool = false; }
      if (f2 <= 14.0F) break; f2 = 14.0F; bool = false; break;
    case 2:
      if (f1 < -11.0F) { f1 = -11.0F; bool = false; }
      if (f1 > 11.0F) { f1 = 11.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 0.0F) break; f2 = 0.0F; bool = false; break;
    case 3:
      if (f1 < -12.0F) { f1 = -12.0F; bool = false; }
      if (f1 > 12.0F) { f1 = 12.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 2.0F) break; f2 = 2.0F; bool = false; break;
    case 4:
      if (f1 < -41.0F) { f1 = -41.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 5:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 53.0F) { f1 = 53.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
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
    Class localClass = B_17D.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-17");
    Property.set(localClass, "meshName", "3DO/Plane/B-17D(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-17D(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 2800.8999F);

    Property.set(localClass, "FlightModel", "FlightModels/B-17D.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 13, 14, 15, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", null, null });

    Aircraft.weaponsRegister(localClass, "20x100", new String[] { "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGunFAB50 10", "BombGunFAB50 10" });

    Aircraft.weaponsRegister(localClass, "14x300", new String[] { "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun300lbs 7", "BombGun300lbs 7" });

    Aircraft.weaponsRegister(localClass, "4x1000", new String[] { "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 600", "MGunBrowning50t 600", "BombGun1000lbs 2", "BombGun1000lbs 2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}