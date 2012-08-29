package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class PBYX extends Scheme2
  implements TypeBomber, TypeScout, TypeSailPlane, TypeTransport
{
  private static final float[] gear3 = { 0.0F, 0.75F, 1.5F, 2.25F, 3.49F, 5.0F, 7.75F, 12.0F, 17.75F, 25.01F, 45.0F };

  private static final float[] gear4 = { 0.0F, 7.25F, 14.25F, 21.5F, 28.75F, 36.25F, 44.0F, 54.599998F, 64.0F, 72.75F, 85.0F };

  private static final float[] gear5 = { 0.0F, 3.5F, 7.0F, 10.6F, 13.9F, 17.0F, 19.25F, 20.75F, 24.25F, 30.950001F, 50.0F };

  private static Point3d tmpp = new Point3d();

  private static float floatindex(float paramFloat, float[] paramArrayOfFloat)
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

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    for (int i = 0; i < 3; i++) for (int j = 0; j < 2; j++)
        if (this.FM.Gears.clpGearEff[i][j] != null) {
          tmpp.set(this.FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
          tmpp.z = 0.01D;
          this.FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
          this.FM.Gears.clpGearEff[i][j].pos.reset();
        }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -floatindex(10.0F * paramFloat, gear3), 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, floatindex(10.0F * paramFloat, gear4), 0.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, floatindex(10.0F * paramFloat, gear5), 0.0F);

    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -floatindex(10.0F * paramFloat, gear3), 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, floatindex(10.0F * paramFloat, gear4), 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -floatindex(10.0F * paramFloat, gear5), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -90.0F) { f1 = -90.0F; bool = false; }
      if (f1 > 90.0F) { f1 = 90.0F; bool = false; }
      if (f2 < -10.0F) { f2 = -10.0F; bool = false; }
      if (f2 <= 45.0F) break; f2 = 45.0F; bool = false; break;
    case 1:
      if (f1 < -90.0F) { f1 = -90.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 2:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 90.0F) { f1 = 90.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xcf")) {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xtail")) {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel")) {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    } else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    } else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwinglin")) {
      if (chunkDamageVisible("WingLIn") < 3) {
        hitChunk("WingLIn", paramShot);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.hitTank(paramShot.initiator, 0, 1);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F))
        this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    }
    else if (paramString.startsWith("xwingrin")) {
      if (chunkDamageVisible("WingRIn") < 3) {
        hitChunk("WingRIn", paramShot);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F)) {
        this.FM.AS.hitTank(paramShot.initiator, 2, 1);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.1F))
        this.FM.AS.hitTank(paramShot.initiator, 3, 1);
    }
    else if (paramString.startsWith("xwinglmid")) {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid")) {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout")) {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout")) {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    } else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    } else if (paramString.startsWith("xengine1")) {
      if (chunkDamageVisible("Engine1") < 2) {
        hitChunk("Engine1", paramShot);
      }
      if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.5F)) {
        this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
        if (this.FM.AS.astateEngineStates[0] < 1) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 0, 1);
        }
        if (World.Rnd().nextFloat() < paramShot.power / 960000.0F) {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
        }
        getEnergyPastArmor(25.0F, paramShot);
      }
    } else if (paramString.startsWith("xengine2")) {
      if (chunkDamageVisible("Engine2") < 2) {
        hitChunk("Engine2", paramShot);
      }
      if ((getEnergyPastArmor(1.45F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[1].getCylindersRatio() * 0.5F)) {
        this.FM.EI.engines[1].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
        if (this.FM.AS.astateEngineStates[1] < 1) {
          this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 1, 1);
        }
        if (World.Rnd().nextFloat() < paramShot.power / 960000.0F) {
          this.FM.AS.hitEngine(paramShot.initiator, 1, 3);
        }
        getEnergyPastArmor(25.0F, paramShot);
      }
    } else if (paramString.startsWith("xgearl")) {
      hitChunk("GearL2", paramShot);
    } else if (paramString.startsWith("xgearr")) {
      hitChunk("GearR2", paramShot);
    } else if (paramString.startsWith("xturret")) {
      if (paramString.startsWith("xturret1")) {
        this.FM.AS.setJamBullets(10, 0);
      }
      if (paramString.startsWith("xturret2")) {
        this.FM.AS.setJamBullets(11, 0);
      }
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      int i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      killPilot(this, 5);
      killPilot(this, 6);
      break;
    case 33:
      hierMesh().chunkVisible("Wire01_D0", false);
      hitProp(0, paramInt2, paramActor);
      hitProp(1, paramInt2, paramActor);
      super.cutFM(36, paramInt2, paramActor);
    case 34:
    case 35:
      hierMesh().chunkVisible("Wire02_D0", false);
      break;
    case 36:
      hierMesh().chunkVisible("Wire02_D0", false);
      hitProp(0, paramInt2, paramActor);
      hitProp(1, paramInt2, paramActor);
      super.cutFM(33, paramInt2, paramActor);
    case 37:
    case 38:
      hierMesh().chunkVisible("Wire01_D0", false);
    case 20:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    for (int i = 1; i < 7; i++)
      if (i != 5)
        if (this.FM.getAltitude() < 3000.0F)
          hierMesh().chunkVisible("HMask" + i + "_D0", false);
        else
          hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  static
  {
    Class localClass = PBYX.class;
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);
  }
}