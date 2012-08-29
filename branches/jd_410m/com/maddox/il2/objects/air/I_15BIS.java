package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class I_15BIS extends I_15xyz
{
  public void missionStarting()
  {
    super.missionStarting();
    customization();
  }

  private void customization()
  {
    int i = hierMesh().chunkFindCheck("cf_D0");
    int j = hierMesh().materialFindInChunk("Gloss1D0o", i);
    Mat localMat = hierMesh().material(j);
    String str1 = localMat.Name();
    if (str1.startsWith("PaintSchemes/Cache"))
    {
      try
      {
        str1 = str1.substring(19);
        str1 = str1.substring(0, str1.indexOf("/"));
        String str2 = Main.cur().netFileServerSkin.primaryPath();
        File localFile = new File(HomePath.toFileSystemName(str2 + "/I-15bis/Customization.ini", 0));
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
        String str3 = null;
        int k = 0;
        int m = 0;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          if (str3.equals("[WheelSpats]"))
          {
            k = 1;
            m = 0; continue;
          }
          if (str3.equals("[WheelSpatsGreen]"))
          {
            k = 1;
            m = 1; continue;
          }
          if ((!str3.equals(str1)) || 
            (k == 0))
            continue;
          hierMesh().chunkVisible("GearL4_D0", true);
          hierMesh().chunkVisible("GearR4_D0", true);
          if ((m == 0) || (!Config.isUSE_RENDER()))
            continue;
          hierMesh().materialReplace("Spat", "SpatGreen");
        }

        localBufferedReader.close();
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }

    }
    else if (World.Rnd().nextFloat(0.0F, 1.0F) > 0.9F)
    {
      hierMesh().chunkVisible("GearL4_D0", true);
      hierMesh().chunkVisible("GearR4_D0", true);
    }
  }

  static
  {
    Class localClass = I_15BIS.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-15bis");
    Property.set(localClass, "meshName", "3DO/Plane/I-15bis/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar08());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-15bis.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_15Bis.class);
    Property.set(localClass, "LOSElevation", 0.84305F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xAO10", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xAO10_2xFAB50", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, "BombGunAO10S 1", "BombGunAO10S 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB50", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xRS82", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}