package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class SEAFIRE3F extends SPITFIRE5
{
  private float arrestor = 0.0F;
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((isNet()) && (isNetMirror())) return;
    if (paramString1.startsWith("Hook")) {
      return;
    }
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -85.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap05_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap06_D0", 0.0F, f, 0.0F);
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }
  public void moveArrestorHook(float paramFloat) {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Hook2_D0", 0.0F, -12.0F * paramFloat, 0.0F);
    resetYPRmodifier();
    xyz[2] = (0.1385F * paramFloat);
    hierMesh().chunkSetLocate("Hook3_D0", xyz, ypr);
    this.arrestor = paramFloat;
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.55F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    float f = (float)Math.sin(cvt(paramFloat, 0.01F, 0.99F, 0.0F, 3.141593F));
    hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9.0F * f);
    hierMesh().chunkSetAngles("Head1_D0", 12.0F * f, 0.0F, 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this.FM.CT.getArrestor() > 0.2F)
    {
      float f;
      if (this.FM.Gears.arrestorVAngle != 0.0F) {
        f = cvt(this.FM.Gears.arrestorVAngle, -50.0F, 7.0F, 1.0F, 0.0F);
        this.arrestor = (0.8F * this.arrestor + 0.2F * f);
        moveArrestorHook(this.arrestor);
      }
      else {
        f = -33.0F * this.FM.Gears.arrestorVSink / 57.0F;
        if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }
        if ((f > 0.0F) && (this.FM.CT.getArrestor() < 0.95F)) {
          f = 0.0F;
        }
        if (f > 0.2F) {
          f = 0.2F;
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

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, -112.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingLOut_D0", 0.0F, -112.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, -112.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingROut_D0", 0.0F, -112.0F * paramFloat, 0.0F);
  }
  public void moveWingFold(float paramFloat) {
    moveWingFold(hierMesh(), paramFloat);
    if (paramFloat < 0.001F) {
      setGunPodsOn(true);
      hideWingWeapons(false);
    } else {
      setGunPodsOn(false);
      this.FM.CT.WeaponControl[0] = false;
      hideWingWeapons(true);
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
    Class localClass = SEAFIRE3F.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Seafire");
    Property.set(localClass, "meshName", "3DO/Plane/SeafireMkIII(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Seafire-F-MkIII.fmd");
    Property.set(localClass, "cockpitClass", CockpitSea3.class);
    Property.set(localClass, "LOSElevation", 1.06985F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 9, 3, 9, 9, 3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1x30dt", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_TankSpit30" });

    weaponsRegister(localClass, "1x45dt", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_TankSpit45" });

    weaponsRegister(localClass, "1x90dt", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_TankSpit90" });

    weaponsRegister(localClass, "1x500", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", "PylonSpitC", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4x60hvar", new String[] { "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunBrowning303kWF 350", "MGunHispanoMkIkWF 120", "MGunHispanoMkIkWF 120", null, null, null, null, null, null, "PylonSpitROCK", "PylonSpitROCK", "PylonSpitROCK", "PylonSpitROCK", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}