package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class DO_335A0 extends DO_335
{
  private boolean bKeelUp = true;

  public void update(float paramFloat)
  {
    hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water2_D0", 0.0F, -10.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    hierMesh().chunkSetAngles("Water3_D0", 0.0F, -10.0F * this.FM.EI.engines[1].getControlRadiator(), 0.0F);
    for (int i = 2; i < 8; i++) {
      hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
    if ((this.FM.AS.isMaster()) && (this.bKeelUp) && 
      (this.FM.AS.astateBailoutStep == 3)) {
      this.FM.AS.setInternalDamage(this, 5);
      this.FM.AS.setInternalDamage(this, 4);
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
    localVector3d.set(this.FM.Vwld);
    localWreckage.setSpeed(localVector3d);
    hierMesh().chunkVisible("Prop2_D0", false);
    hierMesh().chunkVisible("Prop2_D1", false);
    hierMesh().chunkVisible("PropRot2_D0", false);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Do-335");
    Property.set(localClass, "meshName", "3DO/Plane/Do-335A-0/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Do-335.fmd");

    Property.set(localClass, "cockpitClass", CockpitDO_335.class);
    Property.set(localClass, "LOSElevation", 1.00705F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN02", "_MGUN03", "_MGUN01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn01", "_BombSpawn03", "_BombSpawn01", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", "_BombSpawn11" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8sc50", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", null, null, null, null, null, "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50" });

    weaponsRegister(localClass, "8sc70", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", null, null, null, null, null, "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70", "BombGunSC70" });

    weaponsRegister(localClass, "2sc250", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", null, "BombGunSC250", "BombGunNull", "BombGunSC250", "BombGunNull", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xab250", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", null, "BombGunAB250", "BombGunNull", "BombGunAB250", "BombGunNull", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1sc500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "BombGunSC500", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1sd500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "BombGunSD500", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1ab500", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "BombGunAB500", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1sc1000", new String[] { "MGunMG15120si 200", "MGunMG15120si 200", "MGunMK103ki 70", "BombGunSC1000", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}