package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;
import java.io.PrintStream;

public class RE_2002 extends RE_2002xyz
  implements TypeDiveBomber
{
  public float canopyF = 0.0F;
  private boolean tiltCanopyOpened = false;
  private boolean slideCanopyOpened = false;
  private boolean blisterRemoved = false;

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if ((this.tiltCanopyOpened) && (!this.blisterRemoved) && (this.FM.getSpeed() > 75.0F))
    {
      doRemoveBlister1();
    }
  }

  private final void doRemoveBlister1()
  {
    this.blisterRemoved = true;
    if (hierMesh().chunkFindCheck("Blister1_D0") != -1)
    {
      hierMesh().hideSubTrees("Blister1_D0");
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
      localWreckage.collide(true);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localWreckage.setSpeed(localVector3d);
    }
  }

  public void moveCockpitDoor(float paramFloat)
  {
    if (paramFloat > this.canopyF)
    {
      if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F)) || (this.tiltCanopyOpened))
      {
        this.tiltCanopyOpened = true;
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * paramFloat, 0.0F);
      }
      else
      {
        this.slideCanopyOpened = true;
        resetYPRmodifier();
        Aircraft.xyz[0] = -0.01F;
        Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);
        Aircraft.xyz[0] = 0.01F;
        hierMesh().chunkSetLocate("Blister4R_D0", Aircraft.xyz, Aircraft.ypr);
      }

    }
    else if (((this.FM.Gears.onGround()) && (this.FM.getSpeed() < 5.0F) && (!this.slideCanopyOpened)) || (this.tiltCanopyOpened))
    {
      hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * paramFloat, 0.0F);
      if ((this.FM.getSpeed() > 50.0F) && (paramFloat < 0.6F) && (!this.blisterRemoved))
      {
        doRemoveBlister1();
      }
      if (paramFloat == 0.0F)
        this.tiltCanopyOpened = false;
    }
    else
    {
      resetYPRmodifier();
      Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.3F);
      hierMesh().chunkSetLocate("Blister4L_D0", Aircraft.xyz, Aircraft.ypr);
      hierMesh().chunkSetLocate("Blister4R_D0", Aircraft.xyz, Aircraft.ypr);

      if (paramFloat == 0.0F) {
        this.slideCanopyOpened = false;
      }
    }
    this.canopyF = paramFloat;

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public boolean typeDiveBomberToggleAutomation() {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  protected void mydebug(String paramString)
  {
    System.out.println(paramString);
  }

  static {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "RE.2002");
    Property.set(localClass, "meshName_it", "3DO/Plane/RE-2002(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeBMPar09());
    Property.set(localClass, "meshName", "3DO/Plane/RE-2002(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/RE-2000.fmd");
    Property.set(localClass, "LOSElevation", 0.9119F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 3, 3, 3, 3, 3, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalTorp01", "_ExternalDev01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, null, null, null });

    weaponsRegister(localClass, "2x100kg", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null, null });

    weaponsRegister(localClass, "2x100kg+1x240lTank", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null, "FuelTankGun_Tank240 1" });

    weaponsRegister(localClass, "1x250kg+2x100kg", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", "BombGunIT_250_T 1", null, null, null });

    weaponsRegister(localClass, "1x250kg", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, "BombGunIT_250_T 1", null, null, null });

    weaponsRegister(localClass, "1x500kg", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, "BombGunIT_500_T 1", null, null, null });

    weaponsRegister(localClass, "1x630kg", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, "BombGunIT_630 1", null, null });

    weaponsRegister(localClass, "1xTorpedo", new String[] { "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, null, "BombGunTorp650 1", null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}