package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public class ME_262HGII extends ME_262
{
  protected void moveRudder(float paramFloat)
  {
    updateControlsVisuals();
    if (this.FM.CT.getGear() > 0.75F)
      hierMesh().chunkSetAngles("GearC21_D0", 0.0F, 40.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    updateControlsVisuals();
  }

  private final void updateControlsVisuals()
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -21.25F * this.FM.CT.getElevator() - 21.25F * this.FM.CT.getRudder(), 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -21.25F * this.FM.CT.getElevator() + 21.25F * this.FM.CT.getRudder(), 0.0F);
  }

  public void doEjectCatapult()
  {
    new MsgAction(false, this) {
      public void doAction(Object paramObject) { Aircraft localAircraft = (Aircraft)paramObject;
        if (!Actor.isValid(localAircraft)) return;
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 10.0D);
        HookNamed localHookNamed = new HookNamed(localAircraft, "_ExternalSeat01");
        localAircraft.pos.getAbs(localLoc2);

        localHookNamed.computePos(localAircraft, localLoc2, localLoc1);

        localLoc1.transform(localVector3d);
        localVector3d.x += localAircraft.FM.Vwld.x;
        localVector3d.y += localAircraft.FM.Vwld.y;
        localVector3d.z += localAircraft.FM.Vwld.z;
        new EjectionSeat(2, localLoc1, localVector3d, localAircraft);
      }
    };
    hierMesh().chunkVisible("Seat_D0", false);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me 262");
    Property.set(localClass, "meshName", "3DO/Plane/Me-262HG-II/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1946.0F);
    Property.set(localClass, "yearExpired", 1958.2F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-262HG-II.fmd");
    Property.set(localClass, "cockpitClass", CockpitME_262HGII.class);
    Property.set(localClass, "LOSElevation", 0.74615F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24" });

    weaponsRegister(localClass, "default", new String[] { "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "24r4m", new String[] { "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}