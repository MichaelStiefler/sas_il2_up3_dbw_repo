package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class LA_7R extends LA_X
{
  private static final float powR = 0.4120879F;
  private static final float powA = 0.77F;
  private boolean bHasEngine = true;
  private Eff3DActor flame = null; private Eff3DActor dust = null; private Eff3DActor trail = null; private Eff3DActor sprite = null;
  private boolean bPowR;
  private static Vector3d v = new Vector3d();

  public void destroy()
  {
    if (Actor.isValid(this.flame)) this.flame.destroy();
    if (Actor.isValid(this.dust)) this.dust.destroy();
    if (Actor.isValid(this.trail)) this.trail.destroy();
    if (Actor.isValid(this.sprite)) this.sprite.destroy();
    super.destroy();
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (Config.isUSE_RENDER()) {
      this.flame = Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1.0F);
      this.dust = Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100D.eff", -1.0F);
      this.trail = Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100T.eff", -1.0F);
      this.sprite = Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100S.eff", -1.0F);
      Eff3DActor.setIntesity(this.flame, 0.0F);
      Eff3DActor.setIntesity(this.dust, 0.0F);
      Eff3DActor.setIntesity(this.trail, 0.0F);
      Eff3DActor.setIntesity(this.sprite, 0.0F);
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (!Config.isUSE_RENDER()) return;
    super.moveFan(paramFloat);
    if (isNetMirror()) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6) {
        Eff3DActor.setIntesity(this.flame, 1.0F);
        Eff3DActor.setIntesity(this.dust, 1.0F);
        Eff3DActor.setIntesity(this.trail, 1.0F);
        Eff3DActor.setIntesity(this.sprite, 1.0F);
      } else {
        Eff3DActor.setIntesity(this.flame, 0.0F);
        Eff3DActor.setIntesity(this.dust, 0.0F);
        Eff3DActor.setIntesity(this.trail, 0.0F);
        Eff3DActor.setIntesity(this.sprite, 0.0F);
      }
    }
    else if ((this.bHasEngine) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() > 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6)) {
      Eff3DActor.setIntesity(this.flame, 1.0F);
      Eff3DActor.setIntesity(this.dust, 1.0F);
      Eff3DActor.setIntesity(this.trail, 1.0F);
      Eff3DActor.setIntesity(this.sprite, 1.0F);
    } else {
      Eff3DActor.setIntesity(this.flame, 0.0F);
      Eff3DActor.setIntesity(this.dust, 0.0F);
      Eff3DActor.setIntesity(this.trail, 0.0F);
      Eff3DActor.setIntesity(this.sprite, 0.0F);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 19:
      this.bHasEngine = false;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.setEngineDies(this, 1);
      return cut(Aircraft.partNames()[paramInt1]);
    case 3:
    case 4:
      return false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (isNetMirror()) return;

    this.bPowR = (this == World.getPlayerAircraft());
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() - Engine.land().HQ(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.x, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.y) > 5.0D) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 0.0F)) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() > (this.bPowR ? 0.4120879F : 0.77F)) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 0) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setStage(this, 6);

        if (this.bPowR) {
          HUD.log("EngineI" + (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6 ? '1' : '0'));
        }
      }
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() < (this.bPowR ? 0.4120879F : 0.77F)) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() > 0)) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setEngineStops(this);

        if (this.bPowR)
          HUD.log("EngineI" + (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6 ? '1' : '0'));
      }
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-7R(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/La-7R.fmd");
    Property.set(localClass, "cockpitClass", CockpitLA_7R.class);
    Property.set(localClass, "LOSElevation", 0.92F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunB20s 200", "MGunB20s 200", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunB20s 200", "MGunB20s 200", "BombGunFAB50 1", "BombGunFAB50 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunB20s 200", "MGunB20s 200", "BombGunFAB100 1", "BombGunFAB100 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunB20s 200", "MGunB20s 200", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}