package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class BF_109E4 extends BF_109
{
  private float kangle = 0.0F;

  public void update(float paramFloat)
  {
    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F));

      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F));
    }

    hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());

    if (this.kangle > 1.0F)
      this.kangle = 1.0F;
    super.update(paramFloat);
    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D0", false);
      else {
        hierMesh().chunkVisible("CF_D0", true);
      }
    }
    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D1", false);
      hierMesh().chunkVisible("CF_D2", false);
      hierMesh().chunkVisible("CF_D3", false);
    }

    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("Blister1_D0", false);
      else
        hierMesh().chunkVisible("Blister1_D0", true);
      Point3d localPoint3d = World.getPlayerAircraft().pos.getAbsPoint();
      if (localPoint3d.z - World.land().HQ(localPoint3d.x, localPoint3d.y) < 0.009999999776482582D)
        hierMesh().chunkVisible("CF_D0", true);
      if (this.FM.AS.bIsAboutToBailout)
        hierMesh().chunkVisible("Blister1_D0", false);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat <= f1) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat >= 1.0F - f1) {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F);
    }
    if (paramFloat < 0.01F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat) {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat <= f1) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat >= 1.0F - f1) {
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F);
    }
    if (paramFloat > 0.99F) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat) {
    if (paramFloat > 77.5F) {
      paramFloat = 77.5F;
      this.FM.Gears.steerAngle = paramFloat;
    }
    if (paramFloat < -77.5F) {
      paramFloat = -77.5F;
      this.FM.Gears.steerAngle = paramFloat;
    }
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = BF_109E4.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109E-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_sk", "3DO/Plane/Bf-109E-4(sk)/hier.him");

    Property.set(localClass, "PaintScheme_sk", new PaintSchemeFMPar01());
    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1944.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-109E-4.fmd");
    Property.set(localClass, "cockpitClass", CockpitBF_109Ex.class);

    Property.set(localClass, "LOSElevation", 0.74985F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 1000", "MGunMG17si 1000", "MGunMGFFk 60", "MGunMGFFk 60" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}