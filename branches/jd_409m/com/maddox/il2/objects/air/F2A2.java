package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class F2A2 extends F2A
{
  public boolean bChangedExts = false;
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    this.bChangedExts = true;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    this.bChangedExts = true;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) bChangedPit = true;
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if (this.bChangedExts)
      doFixBellyDoor();
  }

  public void doFixBellyDoor() {
    hierMesh().chunkVisible("CF1_D0", hierMesh().isChunkVisible("CF_D0"));
    hierMesh().chunkVisible("CF1_D1", hierMesh().isChunkVisible("CF_D1"));
    hierMesh().chunkVisible("CF1_D2", hierMesh().isChunkVisible("CF_D2"));
    hierMesh().chunkVisible("CF1_D3", hierMesh().isChunkVisible("CF_D3"));
    hierMesh().chunkVisible("Engine11_D0", hierMesh().isChunkVisible("Engine1_D0"));
    hierMesh().chunkVisible("Engine11_D1", hierMesh().isChunkVisible("Engine1_D1"));
    hierMesh().chunkVisible("Engine11_D2", hierMesh().isChunkVisible("Engine1_D2"));
    this.bChangedExts = false;
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    Aircraft.xyz[0] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.725F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  static
  {
    Class localClass = F2A2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "F2A");
    Property.set(localClass, "meshName", "3DO/Plane/F2A-2(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/F2A-2(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFCSPar01());
    Property.set(localClass, "originCountry", PaintScheme.countryUSA);

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/F2A-2.fmd");
    Property.set(localClass, "cockpitClass", CockpitF2A2.class);
    Property.set(localClass, "LOSElevation", 1.032F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning50k 250", "MGunBrowning50k 250" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}