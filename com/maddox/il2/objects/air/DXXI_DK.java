package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class DXXI_DK extends DXXI
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    if (this.thisWeaponsName.equals("madsen"))
    {
      hierMesh().chunkVisible("MadsenR_D0", true);
      hierMesh().chunkVisible("MadsenL_D0", true);
    }

    if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1))
    {
      hierMesh().chunkVisible("GearL3_D0", false);
      hierMesh().chunkVisible("GearR3_D0", false);
      hierMesh().chunkVisible("GearL3W_D0", true);
      hierMesh().chunkVisible("GearR3W_D0", true);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    if (World.cur().camouflage == 1)
    {
      paramHierMesh.chunkVisible("GearL3_D0", false);
      paramHierMesh.chunkVisible("GearR3_D0", false);
      paramHierMesh.chunkVisible("GearL3W_D0", true);
      paramHierMesh.chunkVisible("GearR3W_D0", true);
    }
  }

  protected void moveFan(float paramFloat) {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);

      float f1 = this.FM.CT.getAileron();
      float f2 = this.FM.CT.getElevator();
      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1, cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));
    }
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      this.bChangedPit = true;
    Wreckage localWreckage;
    Vector3d localVector3d;
    if (hierMesh().isChunkVisible("GearR22_D2"))
    {
      if (!hierMesh().isChunkVisible("gearr31_d0"))
      {
        hierMesh().chunkVisible("gearr31_d0", true);
        hierMesh().chunkVisible("gearr32_d0", true);
        localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR22_D1"));
        localWreckage.collide(true);
        localVector3d = new Vector3d();
        localVector3d.set(this.FM.Vwld);
        localWreckage.setSpeed(localVector3d);
      }
    }
    if (hierMesh().isChunkVisible("GearL22_D2"))
    {
      if (!hierMesh().isChunkVisible("gearl31_d0"))
      {
        hierMesh().chunkVisible("gearl31_d0", true);
        hierMesh().chunkVisible("gearl32_d0", true);
        localWreckage = new Wreckage(this, hierMesh().chunkFind("GearL22_D1"));
        localWreckage.collide(true);
        localVector3d = new Vector3d();
        localVector3d.set(this.FM.Vwld);
        localWreckage.setSpeed(localVector3d);
      }
    }
  }

  static
  {
    Class localClass = DXXI_DK.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_DK/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1943.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerDK.fmd");

    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryNoName);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", null, null });

    weaponsRegister(localClass, "madsen", new String[] { "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", "MGunMadsen20 60", "MGunMadsen20 60" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}