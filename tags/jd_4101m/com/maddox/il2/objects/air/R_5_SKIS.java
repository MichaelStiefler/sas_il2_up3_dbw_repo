package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class R_5_SKIS extends R_5xyz
{
  private float skiAngleL = 0.0F;
  private float skiAngleR = 0.0F;
  private float spring = 0.15F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.CT.bHasBrakeControl = false;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 9:
      hierMesh().chunkVisible("SkiL2_D0", false);
      break;
    case 10:
      hierMesh().chunkVisible("SkiR2_D0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFan(float paramFloat) {
    if (Config.isUSE_RENDER())
    {
      int i = 0;

      float f1 = Aircraft.cvt(this.FM.getSpeed(), 20.0F, 50.0F, 1.0F, 0.0F);
      float f2 = Aircraft.cvt(this.FM.getSpeed(), 0.0F, 20.0F, 0.0F, 0.5F);

      if (this.FM.Gears.gWheelSinking[0] > 0.0F)
      {
        i = 1;
        this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.FM.Or.getTangage());

        if (this.skiAngleL > 20.0F)
        {
          this.skiAngleL -= this.spring;
        }

        hierMesh().chunkSetAngles("SkiL0_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - this.skiAngleL, World.Rnd().nextFloat(f2, f2));
      }
      else
      {
        if (this.skiAngleL > f1 * -10.0F + 0.01D)
        {
          this.skiAngleL -= this.spring;
          i = 1;
        }
        else if (this.skiAngleL < f1 * -10.0F - 0.01D)
        {
          this.skiAngleL += this.spring;
          i = 1;
        }

        hierMesh().chunkSetAngles("SkiL0_D0", 0.0F, -this.skiAngleL, 0.0F);
      }

      if (this.FM.Gears.gWheelSinking[1] > 0.0F)
      {
        i = 1;
        this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.FM.Or.getTangage());

        if (this.skiAngleR > 20.0F)
        {
          this.skiAngleR -= this.spring;
        }

        hierMesh().chunkSetAngles("SkiR0_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - this.skiAngleR, World.Rnd().nextFloat(f2, f2));

        if ((this.FM.Gears.gWheelSinking[0] == 0.0F) && (this.FM.Or.getRoll() < 365.0F) && (this.FM.Or.getRoll() > 355.0F))
        {
          this.skiAngleL = this.skiAngleR;
          hierMesh().chunkSetAngles("SkiL0_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2 * 2.0F, f2 * 2.0F) - this.skiAngleL, World.Rnd().nextFloat(f2, f2));
        }

      }
      else
      {
        if (this.skiAngleR > f1 * -10.0F + 0.01D)
        {
          this.skiAngleR -= this.spring;
          i = 1;
        }
        else if (this.skiAngleR < f1 * -10.0F - 0.01D)
        {
          this.skiAngleR += this.spring;
          i = 1;
        }
        hierMesh().chunkSetAngles("SkiR0_D0", 0.0F, -this.skiAngleR, 0.0F);
      }

      if ((i == 0) && (f1 == 0.0F))
      {
        super.moveFan(paramFloat);
        return;
      }

      hierMesh().chunkSetAngles("SkiC_D0", 0.0F, (this.skiAngleL + this.skiAngleR) / 2.0F, 0.0F);

      float f3 = this.skiAngleL / 20.0F;

      hierMesh().chunkSetAngles("SkiL1_D0", 0.0F, f3 * -2.0F + 0.25F * this.suspL, f3 * 8.25F + 3.0F * this.suspL);
      hierMesh().chunkSetAngles("SkiL2_D0", 0.0F, f3 * -7.0F + 1.25F * this.suspL, f3 * -6.25F + 2.75F * this.suspL);
      float f4;
      float f5;
      float f6;
      float f7;
      if (this.skiAngleL < 0.0F)
      {
        hierMesh().chunkSetAngles("SkiL3_D0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("SkiL4_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiL5_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiL6_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiL7_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiL8_D0", 0.0F, 0.0F, 0.0F);
      }
      else
      {
        hierMesh().chunkSetAngles("SkiL3_D0", 0.0F, f3 * 40.0F, f3 * 70.0F);

        f4 = f3 * -5.0F;
        f5 = f3 * 10.0F;
        f6 = f3 * 15.0F;
        f7 = f3 * 20.0F;

        hierMesh().chunkSetAngles("SkiL4_D0", 0.0F, -f4, -f7);
        hierMesh().chunkSetAngles("SkiL5_D0", 0.0F, -f5, -f7);
        hierMesh().chunkSetAngles("SkiL6_D0", 0.0F, -f6, -f7);
        hierMesh().chunkSetAngles("SkiL7_D0", 0.0F, -f5, -f7);
        hierMesh().chunkSetAngles("SkiL8_D0", 0.0F, -f5, -f7);
      }

      f3 = this.skiAngleR / 20.0F;

      hierMesh().chunkSetAngles("SkiR1_D0", 0.0F, f3 * 2.0F - 0.25F * this.suspR, f3 * 8.25F + 3.0F * this.suspR);
      hierMesh().chunkSetAngles("SkiR2_D0", 0.0F, f3 * -7.0F + 1.25F * this.suspR, f3 * -6.25F + 2.75F * this.suspR);

      if (this.skiAngleR < 0.0F)
      {
        hierMesh().chunkSetAngles("SkiR3_D0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("SkiR4_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiR5_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiR6_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiR7_D0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("SkiR8_D0", 0.0F, 0.0F, 0.0F);
      }
      else
      {
        hierMesh().chunkSetAngles("SkiR3_D0", 0.0F, f3 * 40.0F, f3 * 70.0F);

        f4 = f3 * -5.0F;
        f5 = f3 * 10.0F;
        f6 = f3 * 15.0F;
        f7 = f3 * 20.0F;

        hierMesh().chunkSetAngles("SkiR4_D0", 0.0F, -f5, -f7);
        hierMesh().chunkSetAngles("SkiR5_D0", 0.0F, -f5, -f7);
        hierMesh().chunkSetAngles("SkiR6_D0", 0.0F, -f4, -f7);
        hierMesh().chunkSetAngles("SkiR7_D0", 0.0F, -f6, -f7);
        hierMesh().chunkSetAngles("SkiR8_D0", 0.0F, -f5, -f7);
      }
    }
    super.moveFan(paramFloat);
  }

  static Class class$(String paramString) {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }

    return localClass;
  }

  static {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "R-5");
    Property.set(localClass, "meshName", "3do/plane/R-5/hier_skis.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar08());
    Property.set(localClass, "yearService", 1931.0F);
    Property.set(localClass, "yearExpired", 1944.0F);
    Property.set(localClass, "FlightModel", "FlightModels/R-5.fmd");
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 10, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", "_ExternalBomb18", "_ExternalBomb19", "_ExternalBomb20", "_ExternalBomb21", "_ExternalBomb22", "_ExternalBomb23", "_ExternalBomb24", "_ExternalBomb25", "_ExternalBomb26", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05" });

    weaponsRegister(localClass, "default", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "Gunpods", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", null, null, null });

    weaponsRegister(localClass, "Gunpods+8x10+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "BombGunAO10S 1", null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "Gunpods+16x10+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "Gunpods+4x50+2x50", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", "MGunPV1i 200", null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5GPodL 1", "PylonR5GPodR 1", "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "8x50+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "2x100+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "2x100+2x50+2x100", new String[] { "MGunPV1sipzl 500", "MGunDA762t 500", "MGunDA762t 500", null, null, null, null, null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonR5BombRackL 1", "PylonR5BombRackR 1", "PylonR5BombRackC 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}