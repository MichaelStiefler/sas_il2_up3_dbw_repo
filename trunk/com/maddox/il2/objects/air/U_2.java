package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public abstract class U_2 extends Scheme1
  implements TypeScout, TypeBomber, TypeTransport
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -25.0F) { paramArrayOfFloat[0] = -25.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 25.0F) { paramArrayOfFloat[0] = 25.0F; bool = false; }
    float f1 = Math.abs(paramArrayOfFloat[0]);
    if (f1 < 10.0F) {
      if (paramArrayOfFloat[1] < -5.0F) { paramArrayOfFloat[1] = -5.0F; bool = false; }
    }
    else if (paramArrayOfFloat[1] < -15.0F) { paramArrayOfFloat[1] = -15.0F; bool = false;
    }
    if (paramArrayOfFloat[1] > 35.0F) { paramArrayOfFloat[1] = 35.0F; bool = false; }
    if (!bool) return false;

    float f2 = paramArrayOfFloat[1];
    if ((f1 < 2.0F) && (f2 < 17.0F)) return false;
    if (f2 > -5.0F) return true;
    if (f2 > -12.0F) {
      f2 += 12.0F;
      return f1 > 12.0F + f2 * 2.571429F;
    }

    f2 = -f2;
    return f1 > f2;
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if (paramShot.chunkName.startsWith("Pilot1"))
      killPilot(paramShot.initiator, 0);
    if (paramShot.chunkName.startsWith("Pilot2"))
      killPilot(paramShot.initiator, 1);
    if ((paramShot.chunkName.startsWith("Engine")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    }
    super.msgShot(paramShot);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -29.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder1RodR_D0", -29.0F * paramFloat, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Rudder1RodL_D0", 0.0F, 29.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorLRodV_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorLRodN_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorLRodR_D0", 0.0F, -35.0F * paramFloat, 0.0F);

    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorRRodV_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorRRodN_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorRRodR_D0", 0.0F, -35.0F * paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneLn_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneLrod_D0", 0.0F, -35.0F * paramFloat, 0.0F);

    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneRn_D0", 0.0F, 35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneRrod_D0", 0.0F, -35.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
  }

  static
  {
    Class localClass = U_2.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}