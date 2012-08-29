package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_17F extends B_17
  implements TypeBomber
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -23.0F) { f1 = -23.0F; bool = false; }
      if (f1 > 23.0F) { f1 = 23.0F; bool = false; }
      if (f2 < -15.0F) { f2 = -15.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false; break;
    case 1:
      if (f1 < -20.0F) { f1 = -20.0F; bool = false; }
      if (f1 > 20.0F) { f1 = 20.0F; bool = false; }
      if (f2 < -20.0F) { f2 = -20.0F; bool = false; }
      if (f2 <= 20.0F) break; f2 = 20.0F; bool = false; break;
    case 2:
      if (f1 < -20.0F) { f1 = -20.0F; bool = false; }
      if (f1 > 20.0F) { f1 = 20.0F; bool = false; }
      if (f2 < -20.0F) { f2 = -20.0F; bool = false; }
      if (f2 <= 20.0F) break; f2 = 20.0F; bool = false; break;
    case 3:
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 66.0F) break; f2 = 66.0F; bool = false; break;
    case 4:
      if (f2 < -75.0F) { f2 = -75.0F; bool = false; }
      if (f2 <= 6.0F) break; f2 = 6.0F; bool = false; break;
    case 5:
      if (f1 < -70.0F) { f1 = -70.0F; bool = false; }
      if (f1 > 54.0F) { f1 = 54.0F; bool = false; }
      if (f2 < -17.0F) { f2 = -17.0F; bool = false; }
      if (f2 <= 43.0F) break; f2 = 43.0F; bool = false; break;
    case 6:
      if (f1 < -63.0F) { f1 = -63.0F; bool = false; }
      if (f1 > 15.0F) { f1 = 15.0F; bool = false; }
      if (f2 < -17.0F) { f2 = -17.0F; bool = false; }
      if (f2 <= 43.0F) break; f2 = 43.0F; bool = false; break;
    case 7:
      if (f1 < -25.0F) { f1 = -25.0F; bool = false; }
      if (f1 > 25.0F) { f1 = 25.0F; bool = false; }
      if (f2 < -25.0F) { f2 = -25.0F; bool = false; }
      if (f2 <= 25.0F) break; f2 = 25.0F; bool = false;
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
    Class localClass = B_17F.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-17");
    Property.set(localClass, "meshName", "3DO/Plane/B-17F(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-17F(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar03());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 2800.8999F);

    Property.set(localClass, "FlightModel", "FlightModels/B-17F.fmd");

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 13, 14, 14, 15, 16, 17, 17, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_BombSpawn01", "_BombSpawn02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 600", "MGunBrowning50t 600", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null });

    weaponsRegister(localClass, "16x500", new String[] { "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 600", "MGunBrowning50t 600", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 8", "BombGun500lbs 8" });

    weaponsRegister(localClass, "8x1000", new String[] { "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "MGunBrowning50t 600", "MGunBrowning50t 600", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun1000lbs 4", "BombGun1000lbs 4" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}