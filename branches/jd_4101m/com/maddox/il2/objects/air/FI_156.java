package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class FI_156 extends Scheme1
  implements TypeScout, TypeTransport
{
  private static final float[] gearL2 = { 0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F };

  private static final float[] gearL4 = { 0.0F, 7.5F, 15.0F, 22.0F, 29.0F, 35.5F };

  private static final float[] gearL5 = { 0.0F, 1.5F, 4.0F, 7.5F, 10.0F, 11.5F };

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 3; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }
  public void moveWheelSink() { resetYPRmodifier();
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 0.5F);
    hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
    float f = cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, floatindex(f, gearL2), 0.0F);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, floatindex(f, gearL4), 0.0F);
    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, floatindex(f, gearL5), 0.0F);
    xyz[2] = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
    hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
    f = cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 5.0F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -floatindex(f, gearL2), 0.0F);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, -floatindex(f, gearL4), 0.0F);
    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, -floatindex(f, gearL5), 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -60.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  private float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0) return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F) return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLMid")) && 
      (World.Rnd().nextFloat(0.0F, 0.121F) < paramShot.mass)) {
      this.FM.AS.hitTank(paramShot.initiator, 0, (int)(1.0F + paramShot.mass * 18.950001F * 2.0F));
    }
    if ((paramShot.chunkName.startsWith("WingRMid")) && 
      (World.Rnd().nextFloat(0.0F, 0.121F) < paramShot.mass)) {
      this.FM.AS.hitTank(paramShot.initiator, 1, (int)(1.0F + paramShot.mass * 18.950001F * 2.0F));
    }
    if (paramShot.chunkName.startsWith("Engine")) {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < paramShot.mass)
        this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
      if ((v1.z > 0.0D) && (World.Rnd().nextFloat() < 0.12F))
      {
        this.FM.AS.setEngineDies(paramShot.initiator, 0);
        if (paramShot.mass > 0.1F) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 5);
        }
      }
      if ((v1.x < 0.1000000014901161D) && (World.Rnd().nextFloat() < 0.57F)) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
      }
    }
    if (paramShot.chunkName.startsWith("Pilot1")) {
      killPilot(paramShot.initiator, 0);
      this.FM.setCapableOfBMP(false, paramShot.initiator);
      if ((Pd.z > 0.5D) && 
        (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");

      return;
    }
    if (paramShot.chunkName.startsWith("Pilot2")) {
      killPilot(paramShot.initiator, 1);
      if ((Pd.z > 0.5D) && 
        (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");

      return;
    }
    if (paramShot.chunkName.startsWith("Turret")) {
      this.FM.turret[0].bIsOperable = false;
    }

    if ((this.FM.AS.astateEngineStates[0] == 4) && (World.Rnd().nextInt(0, 99) < 33)) {
      this.FM.setCapableOfBMP(false, paramShot.initiator);
    }

    super.msgShot(paramShot);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 34:
      return super.cutFM(35, paramInt2, paramActor);
    case 37:
      return super.cutFM(38, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    if (paramInt == 1)
      this.FM.turret[0].setHealth(paramFloat);
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("HMask2_D0", false);
      if (this.FM.AS.bIsAboutToBailout) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];

    if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
    if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
    if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
    if (f2 > 20.0F) { f2 = 20.0F; bool = false; }
    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  static
  {
    Class localClass = FI_156.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Fi-156");
    Property.set(localClass, "meshName", "3do/plane/Fi-156/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1956.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fi-156B-2.fmd");

    weaponTriggersRegister(localClass, new int[] { 10 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 750" });

    weaponsRegister(localClass, "none", new String[] { null });
  }
}