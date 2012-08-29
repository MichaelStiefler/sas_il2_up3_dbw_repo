package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class YAK_3R extends YAK
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

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1500.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    f = Math.max(-paramFloat * 1500.0F, -60.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
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

        if (this.bPowR) {
          HUD.log("EngineI" + (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getStage() == 6 ? '1' : '0'));
        }
      }
    }

    hierMesh().chunkSetAngles("OilRad_D0", 0.0F, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator() * 25.0F, 0.0F);
    hierMesh().chunkSetAngles("Water_luk", 0.0F, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator() * 12.0F, 0.0F);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-3R(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-3R.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_3R.class);
    Property.set(localClass, "LOSElevation", 0.6576F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVYaki 60" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null });
  }
}