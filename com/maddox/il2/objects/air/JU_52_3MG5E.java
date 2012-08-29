package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class JU_52_3MG5E extends JU_52
  implements TypeTransport, TypeSailPlane
{
  private Point3d tmpp = new Point3d();

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 3; i++) for (int j = 0; j < 2; j++)
        if (this.FM.Gears.clpGearEff[i][j] != null) {
          this.tmpp.set(this.FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
          this.tmpp.z = 0.01D;
          this.FM.Gears.clpGearEff[i][j].pos.setAbs(this.tmpp);
          this.FM.Gears.clpGearEff[i][j].pos.reset();
        }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -50.0F) { paramArrayOfFloat[0] = -50.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 50.0F) { paramArrayOfFloat[0] = 50.0F; bool = false; }
    float f = Math.abs(paramArrayOfFloat[0]);
    if (f < 20.0F) {
      if (paramArrayOfFloat[1] < -1.0F) { paramArrayOfFloat[1] = -1.0F; bool = false; }
    }
    else if (paramArrayOfFloat[1] < -5.0F) { paramArrayOfFloat[1] = -5.0F; bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F) { paramArrayOfFloat[1] = 45.0F; bool = false; }
    return bool;
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
    }
    if ((this.FM.AS.astateEngineStates[0] > 2) && (this.FM.AS.astateEngineStates[1] > 2)) {
      this.FM.setCapableOfBMP(false, paramShot.initiator);
    }

    super.msgShot(paramShot);
  }

  static
  {
    Class localClass = JU_52_3MG5E.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "FlightModel", "FlightModels/Ju-52_3mg5e.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-52_3mg5e/hier.him");

    Property.set(localClass, "iconFar_shortClassName", "Ju-52");

    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1938.5F);
    Property.set(localClass, "yearExpired", 1945.5F);

    weaponTriggersRegister(localClass, new int[] { 10, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_BombSpawn01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 250", null });

    weaponsRegister(localClass, "18xPara", new String[] { "MGunMG15t 250", "BombGunPara 18" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}