package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.rts.Property;

public class P_11C extends Scheme1
  implements TypeFighter, TypeTNBFighter
{
  public static boolean bChangedPit = false;

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("CF")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 1);
    }

    if (paramShot.chunkName.startsWith("Pilot")) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 0, 101);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
      for (int i = 0; i < arrayOfObject.length; i++)
        if ((arrayOfObject[i] instanceof Bomb)) {
          hierMesh().chunkVisible("RackL_D0", true);
          hierMesh().chunkVisible("RackR_D0", true);
        }
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true;
  }

  static
  {
    Class localClass = P_11C.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P.11");
    Property.set(localClass, "meshName", "3DO/Plane/P-11c(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "meshName_pl", "3DO/Plane/P-11c/hier.him");
    Property.set(localClass, "PaintScheme_pl", new PaintSchemeFCSPar01());
    Property.set(localClass, "meshName_ro", "3DO/Plane/P-11c(Romanian)/hier.him");
    Property.set(localClass, "PaintScheme_ro", new PaintSchemeFMPar00());
    Property.set(localClass, "originCountry", PaintScheme.countryPoland);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1939.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-11c.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_11C.class);
    Property.set(localClass, "LOSElevation", 0.7956F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", null, null });

    Aircraft.weaponsRegister(localClass, "2puw125", new String[] { "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", "BombGunPuW125", "BombGunPuW125" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}