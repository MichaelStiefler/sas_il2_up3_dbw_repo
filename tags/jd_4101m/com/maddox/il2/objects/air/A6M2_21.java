package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

public class A6M2_21 extends A6M
{
  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLOut_D0", 0.0F, 110.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingROut_D0", 0.0F, -110.0F * paramFloat, 0.0F);
  }
  public void moveWingFold(float paramFloat) {
    moveWingFold(hierMesh(), paramFloat);
    this.FM.doRequestFMSFX(1, (int)cvt(paramFloat, 0.1F, 1.0F, 0.0F, 40.0F));
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.FM.CT.getArrestor() > 0.2F)
    {
      float f;
      if (this.FM.Gears.arrestorVAngle != 0.0F) {
        f = cvt(this.FM.Gears.arrestorVAngle, -26.0F, 11.0F, 1.0F, 0.0F);
        this.arrestor = (0.8F * this.arrestor + 0.2F * f);
        moveArrestorHook(this.arrestor);
      } else {
        f = -42.0F * this.FM.Gears.arrestorVSink / 37.0F;
        if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }
        if ((f > 0.0F) && (this.FM.CT.getArrestor() < 0.95F)) {
          f = 0.0F;
        }
        if (f > 0.0F)
          this.arrestor = (0.7F * this.arrestor + 0.3F * (this.arrestor + f));
        else {
          this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f));
        }
        if (this.arrestor < 0.0F)
          this.arrestor = 0.0F;
        else if (this.arrestor > 1.0F) {
          this.arrestor = 1.0F;
        }
        moveArrestorHook(this.arrestor);
      }
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      this.FM.CT.bHasArrestorControl = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = A6M2_21.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A6M");

    Property.set(localClass, "meshName", "3DO/Plane/A6M2-21(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A6M2-21(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1940.5F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A6M2-21.fmd");
    Property.set(localClass, "cockpitClass", CockpitA6M2.class);
    Property.set(localClass, "LOSElevation", 1.01885F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xdt", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", "FuelTankGun_Tank0", null, null, null, null, null, null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, null });

    weaponsRegister(localClass, "2x60", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 60", "MGunMGFFk 60", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", "BombGun50kg" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}