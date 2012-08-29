package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class CW_21 extends CW21xyz
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.1F, 0.8F, 0.0F, -82.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(paramFloat, 0.3F, 0.99F, 0.0F, -82.0F), 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("LLight_D0", 0.0F, f, 0.0F);
    float tmp109_108 = (Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F); Aircraft.xyz[1] = tmp109_108; Aircraft.xyz[0] = tmp109_108;
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.21F, 0.63F, 0.0F, 0.09F);
    Aircraft.ypr[0] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 75.0F);
    paramHierMesh.chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.FM.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
  }

  static
  {
    Class localClass = CW_21.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "CW-21");
    Property.set(localClass, "meshName", "3DO/Plane/CW-21(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00du());
    Property.set(localClass, "meshName_du", "3DO/Plane/CW-21(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_du", new PaintSchemeFMPar00du());
    Property.set(localClass, "yearService", 1940.6F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/CW-21.fmd");
    Property.set(localClass, "cockpitClass", CockpitCW_21.class);
    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 300", "MGunBrowning303sipzl 300", "MGunBrowning303sipzl 300", "MGunBrowning303sipzl 300" });
    weaponsRegister(localClass, "2x303_2x05", new String[] { "MGunBrowning303sipzl 300", "MGunBrowning303sipzl 300", "MGunBrowning50si 225", "MGunBrowning50si 225" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}