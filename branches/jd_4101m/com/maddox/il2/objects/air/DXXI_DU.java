package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class DXXI_DU extends DXXI
{
  protected void moveFan(float paramFloat)
  {
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
    Class localClass = DXXI_DU.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_DU/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1940.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerDU.fmd");

    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryNetherlands);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}