package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MIG_3U extends MIG_3
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { hierMesh().chunkSetAngles("FlapWater_D0", 0.0F, 0.0F, 40.5F * this.kangle);
    hierMesh().chunkSetAngles("FlapOil_D0", 0.0F, 0.0F, 12.5F * this.kangle);

    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 70.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 93.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -93.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if (((paramShot.chunkName.startsWith("CF")) || (paramShot.chunkName.startsWith("Tail"))) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 0, 2);
    if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 2, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 3, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.2F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
    }
    super.msgShot(paramShot);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 33:
      this.FM.AS.hitTank(this, 2, 7); return super.cutFM(34, paramInt2, paramActor);
    case 36:
      this.FM.AS.hitTank(this, 3, 7); return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = MIG_3U.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MiG");
    Property.set(localClass, "meshName", "3DO/Plane/MIG-3U(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1943.0F);

    Property.set(localClass, "FlightModel", "FlightModels/MiG-3U.fmd");
    Property.set(localClass, "cockpitClass", CockpitMIG_3U.class);
    Property.set(localClass, "LOSElevation", 0.906F);

    weaponTriggersRegister(localClass, new int[] { 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02" });

    weaponsRegister(localClass, "default", new String[] { "MGunShVAKs 150", "MGunShVAKs 150" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}