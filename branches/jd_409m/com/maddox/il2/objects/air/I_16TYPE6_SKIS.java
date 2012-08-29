package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.rts.CLASS;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class I_16TYPE6_SKIS extends I_16FixedSkis
  implements TypeTNBFighter
{
  private float flaperonAngle = 0.0F;
  private float aileronsAngle = 0.0F;
  private boolean hasTubeSight = false;
  private CockpitI_16TYPE6 pit = null;
  private boolean sideDoorOpened = false;
  private boolean removeSpinnerHub = false;

  public void moveCockpitDoor(float paramFloat)
  {
    hierMesh().chunkSetAngles("Blister2_D0", 0.0F, 160.0F * paramFloat, 0.0F);
  }

  public void hitDaSilk()
  {
    super.hitDaSilk();
    if ((!this.sideDoorOpened) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0)))
    {
      this.sideDoorOpened = true;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl = true;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceCockpitDoor(0.0F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      if (!this.removeSpinnerHub)
      {
        boolean bool = hierMesh().isChunkVisible("PropRot1_D0");
        hierMesh().chunkVisible("PropHubRot1_D0", bool);
        hierMesh().chunkVisible("PropHub1_D0", !bool);
      }
    }
    super.moveFan(paramFloat);
  }

  public void moveWheelSink()
  {
  }

  public void moveGear(float paramFloat)
  {
  }

  protected void moveAileron(float paramFloat)
  {
    this.aileronsAngle = paramFloat;
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat + this.flaperonAngle, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    this.flaperonAngle = (paramFloat * 17.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * this.aileronsAngle - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * this.aileronsAngle + this.flaperonAngle, 0.0F);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D1", true);
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilotarm2_d0", false);
    hierMesh().chunkVisible("pilotarm1_d0", false);
  }

  public boolean hasTubeSight()
  {
    return this.hasTubeSight;
  }

  public void missionStarting()
  {
    super.missionStarting();
    customization();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  private void customization()
  {
    int i = 0;
    int j = 0;
    int k = hierMesh().chunkFindCheck("CF_D0");
    int m = hierMesh().materialFindInChunk("Gloss1D0o", k);
    Mat localMat = hierMesh().material(m);
    String str1 = localMat.Name();
    if (str1.startsWith("PaintSchemes/Cache"))
    {
      try
      {
        str1 = str1.substring(19);
        str1 = str1.substring(0, str1.indexOf("/"));
        String str2 = Main.cur().netFileServerSkin.primaryPath();
        File localFile = new File(HomePath.toFileSystemName(str2 + "/I-16type6_Skis/Customization.ini", 0));
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
        String str3 = null;

        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          if (str3.equals("[TubeSight]"))
          {
            n = 1;
            i1 = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
          }
          else if (str3.equals("[OldStyleSkis]"))
          {
            n = 0;
            i1 = 1;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
          }
          else if (str3.equals("[RadioWires]"))
          {
            n = 0;
            i1 = 0;
            i2 = 1;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
          }
          else if (str3.equals("[FullWheelCovers]"))
          {
            n = 0;
            i1 = 0;
            i2 = 0;
            i3 = 1;
            i4 = 0;
            i5 = 0;
            i6 = 0;
          }
          else if (str3.equals("[RemoveSpinner]"))
          {
            n = 0;
            i1 = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 1;
          }
          else if (str3.equals("[KeepSpinner]"))
          {
            n = 0;
            i1 = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 1;
            i6 = 0;
          }
          else if (str3.equals("[CanopyRails]"))
          {
            n = 0;
            i1 = 0;
            i2 = 0;
            i3 = 0;
            i4 = 1;
            i5 = 0;
            i6 = 0;
          } else {
            if (!str3.equals(str1))
              continue;
            if (n != 0)
            {
              this.hasTubeSight = true;
            }
            if (i1 != 0)
            {
              hierMesh().chunkVisible("SkiL1_D0", false);
              hierMesh().chunkVisible("SkiR1_D0", false);
              hierMesh().chunkVisible("OldSkiL1_D0", true);
              hierMesh().chunkVisible("OldSkiR1_D0", true);
            }
            if (i2 != 0)
            {
              hierMesh().chunkVisible("RadioWire1_d0", true);
              hierMesh().chunkVisible("RadioWire2_d0", true);
            }
            if (i3 != 0)
            {
              hierMesh().chunkVisible("GearCoverR_D0", true);
              hierMesh().chunkVisible("GearCoverL_D0", true);
            }
            if (i4 != 0)
            {
              hierMesh().chunkVisible("Rails_d0", true);
              hierMesh().chunkVisible("Blister2Rail_D0", true);
              hierMesh().chunkVisible("Blister2_D0", false);
              hierMesh().chunkVisible("T6Rail_D0", false);
            }
            if (i5 != 0)
              j = 1;
            if (i6 != 0)
              i = 1;
          }
        }
        localBufferedReader.close();
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }
    }
    else
    {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
      {
        this.hasTubeSight = true;
      }
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
      {
        hierMesh().chunkVisible("GearCoverR_D0", true);
        hierMesh().chunkVisible("GearCoverL_D0", true);
      }
    }

    if (this.pit != null)
    {
      this.pit.setTubeSight(this.hasTubeSight);
    }

    hierMesh().chunkVisible("Sight_D0", !this.hasTubeSight);
    hierMesh().chunkVisible("TubeSight_D0", this.hasTubeSight);

    if ((i != 0) || ((j == 0) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null))))
    {
      this.removeSpinnerHub = true;
      hierMesh().chunkVisible("PropHubRot1_D0", false);
      hierMesh().chunkVisible("PropHub1_D0", false);
    }
  }

  public void registerPit(CockpitI_16TYPE6 paramCockpitI_16TYPE6)
  {
    this.pit = paramCockpitI_16TYPE6;
    if (paramCockpitI_16TYPE6 != null)
    {
      paramCockpitI_16TYPE6.setTubeSight(this.hasTubeSight);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 11:
      hierMesh().chunkVisible("RadioWire1_d0", false);
      hierMesh().chunkVisible("RadioWire2_d0", false);
      break;
    case 36:
      hierMesh().chunkVisible("RadioWire2_d0", false);
      break;
    case 38:
      hierMesh().chunkVisible("RadioWire2_d0", false);
      break;
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Gears.hitCentreGear();
      hierMesh().chunkVisible("RadioWire1_d0", false);
      hierMesh().chunkVisible("RadioWire2_d0", false);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type6(multi)/hier_skis.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar07());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type6/hier_skis.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar07());
    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-16type6Skis.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitI_16TYPE6.class });

    Property.set(localClass, "LOSElevation", 0.82595F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 2, 2, 2, 2, 2, 2, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASk 900", "MGunShKASk 900", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x50kg", new String[] { "MGunShKASk 900", "MGunShKASk 900", null, null, null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x100kg", new String[] { "MGunShKASk 900", "MGunShKASk 900", null, null, null, null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xRS-82", new String[] { "MGunShKASk 900", "MGunShKASk 900", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "6xRS-82", new String[] { "MGunShKASk 900", "MGunShKASk 900", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}