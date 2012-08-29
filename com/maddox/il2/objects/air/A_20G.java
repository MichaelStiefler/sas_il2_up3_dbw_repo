package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.BombGunTorpMk13;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class A_20G extends A_20
  implements TypeStormovik
{
  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
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
      if (j == 2) {
        j = 1;
      }
      hitFlesh(j, paramShot, i);
      return;
    }
    super.hitBone(paramString, paramShot, paramPoint3d);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f2 > 89.0F) { f2 = 89.0F; bool = false; }
      float f3 = Math.abs(f1);
      if (f2 >= Aircraft.cvt(f3, 140.0F, 180.0F, -1.0F, 25.0F)) break;
      f2 = Aircraft.cvt(f3, 140.0F, 180.0F, -1.0F, 25.0F); break;
    case 1:
      if (f1 < -30.0F) { f1 = -30.0F; bool = false; }
      if (f1 > 30.0F) { f1 = 30.0F; bool = false; }
      if (f2 < -45.0F) { f2 = -45.0F; bool = false; }
      if (f2 <= 15.0F) break; f2 = 15.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
      break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[1].bIsOperable = false;
    }
  }

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Blister1_D0", 0.0F, Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (paramBoolean) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[0] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 0, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[1] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[2] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, 1);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[3] > 3) && 
        (World.Rnd().nextFloat() < 0.0023F)) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 3, 1);

    }

    for (int i = 1; i < 4; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && 
      ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunTorpMk13))) {
      hierMesh().chunkVisible("Bay1_D0", false);
      hierMesh().chunkVisible("Bay2_D0", false);
    }
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A-20");
    Property.set(localClass, "meshName", "3DO/Plane/A-20G(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_gb", "3DO/Plane/A-20G(AU)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/A-20G(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar03());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1965.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A-20G.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitA_20G.class, CockpitA_20G_TGunner.class, CockpitA_20G_BGunner.class });

    Property.set(localClass, "LOSElevation", 0.92575F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 10, 10, 11, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "40xParaF", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGunParafrag8 20", "BombGunParafrag8 20", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGunFAB50", "BombGunFAB50", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x1008x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x1008x1002x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x3004x300", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x3004x3002x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGunFAB50", "BombGunFAB50", "BombGun300lbs", "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGun500lbs", "BombGun500lbs", null, null, null });

    Aircraft.weaponsRegister(localClass, "2x5008x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGun500lbs", "BombGun500lbs", "BombGunFAB50 4", "BombGunFAB50 4", null });

    Aircraft.weaponsRegister(localClass, "2x5004x300", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, "BombGun500lbs", "BombGun500lbs", "BombGun300lbs 2", "BombGun300lbs 2", null });

    Aircraft.weaponsRegister(localClass, "2x5002x500", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", null, null, null });

    Aircraft.weaponsRegister(localClass, "1x1000", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, null, null, null, null, "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10008x100", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, null, null, "BombGunFAB50 4", "BombGunFAB50 4", "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10004x300", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, null, null, "BombGun300lbs 2", "BombGun300lbs 2", "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x10002x500", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", "BombGun500lbs", "BombGun500lbs", null, null, null, null, "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1xmk13", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50t 400", "MGunBrowning50t 400", "MGunBrowning303t 400", null, null, null, null, null, null, "BombGunTorpMk13" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}