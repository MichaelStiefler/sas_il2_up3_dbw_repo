package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class DO_335V13 extends DO_335
{
  private boolean bKeelUp = true;

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, -10.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, -10.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator(), 0.0F);
    for (int i = 2; i < 8; i++) {
      hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (this.bKeelUp) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateBailoutStep == 3)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(this, 5);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setInternalDamage(this, 4);
      this.bKeelUp = false;
    }
  }

  public final void doKeelShutoff()
  {
    nextDMGLevels(4, 2, "Keel1_D" + chunkDamageVisible("Keel1"), this);
    this.oldProp[1] = 99;
    Wreckage localWreckage;
    if (hierMesh().isChunkVisible("Prop2_D1"))
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Prop2_D1"));
    else {
      localWreckage = new Wreckage(this, hierMesh().chunkFind("Prop2_D0"));
    }
    Eff3DActor.New(localWreckage, null, null, 1.0F, Wreckage.SMOKE, 3.0F);
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vwld);
    localWreckage.setSpeed(localVector3d);
    hierMesh().chunkVisible("Prop2_D0", false);
    hierMesh().chunkVisible("Prop2_D1", false);
    hierMesh().chunkVisible("PropRot2_D0", false);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if (paramString.startsWith("xxcannon04")) {
      debuggunnery("Armament System: Left Wing Cannon: Disabled..");
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 1);
      getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
      return;
    }
    if (paramString.startsWith("xxcannon05")) {
      debuggunnery("Armament System: Right Wing Cannon: Disabled..");
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setJamBullets(1, 2);
      getEnergyPastArmor(World.Rnd().nextFloat(6.98F, 24.35F), paramShot);
      return;
    }
    super.hitBone(paramString, paramShot, paramPoint3d);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Do-335");
    Property.set(localClass, "meshName", "3DO/Plane/Do-335V-13/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());

    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Do-335V-13.fmd");

    Property.set(localClass, "cockpitClass", CockpitDO_335V13.class);
    Property.set(localClass, "LOSElevation", 1.00705F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN02", "_MGUN03", "_MGUN01", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn01", "_BombSpawn03", "_BombSpawn01", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8sc50", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    Aircraft.weaponsRegister(localClass, "8sc70", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, null, null, null, null, "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70" });

    Aircraft.weaponsRegister(localClass, "2sc250", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, "BombGunSC250", "BombGunNull", "BombGunSC250", "BombGunNull", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xab250", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", null, "BombGunAB250", "BombGunNull", "BombGunAB250", "BombGunNull", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1sc500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1sd500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1ab500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunAB500", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1sc1000", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "MGunMK103k 70", "MGunMK103k 70", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}