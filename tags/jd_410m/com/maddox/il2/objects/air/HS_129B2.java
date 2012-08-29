package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.objects.weapons.MGunBK374Hs129;
import com.maddox.il2.objects.weapons.MGunMK101s_Hs129;
import com.maddox.il2.objects.weapons.MGunMK103s_Hs129;
import com.maddox.il2.objects.weapons.PylonHS129MG17S;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class HS_129B2 extends HS_129
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
        File localFile = new File(HomePath.toFileSystemName(str2 + "/Hs-129B-2/Customization.ini", 0));
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
        String str3 = null;
        int k = 0;
        int m = 0;
        int n = 0;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          if (str3.equals("[SmoothNose]"))
          {
            k = 1;
            m = 0;
            n = 0; continue;
          }
          if (str3.equals("[NoMirror]"))
          {
            k = 0;
            m = 1;
            n = 0; continue;
          }
          if (str3.equals("[NoDFLoop]"))
          {
            k = 0;
            m = 0;
            n = 1; continue;
          }
          if (!str3.equals(str1))
            continue;
          if (k != 0)
          {
            hierMesh().chunkVisible("NoseParts_D0", false);
          }
          if (m != 0)
          {
            hierMesh().chunkVisible("Mirror", false);
          }
          if (n == 0)
            continue;
          hierMesh().chunkVisible("DF_loop", false);
        }

        localBufferedReader.close();
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    Object[] arrayOfObject = this.pos.getBaseAttached();
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++)
      {
        if ((arrayOfObject[i] instanceof MGunMK103s_Hs129))
        {
          hierMesh().chunkVisible("cannonPod", true);
          hierMesh().chunkVisible("Mk103_Barrel", true);
        }
        else if ((arrayOfObject[i] instanceof MGunMK101s_Hs129))
        {
          hierMesh().chunkVisible("cannonPod", true);
          hierMesh().chunkVisible("Mk101_Barrel", true);
        }
        else if ((arrayOfObject[i] instanceof MGunBK374Hs129))
        {
          hierMesh().chunkVisible("BK37_dummy", true);
          this.FM.Sq.liftKeel *= 1.5F;
        } else {
          if (!(arrayOfObject[i] instanceof PylonHS129MG17S))
            continue;
          hierMesh().chunkVisible("MG17_dummy", true);
        }
      }
    }

    if ((Config.isUSE_RENDER()) && ((World.cur().camouflage == 2) || (World.cur().camouflage == 5)))
    {
      hierMesh().chunkVisible("NoseParts_D0", false);
    }
  }

  static
  {
    Class localClass = HS_129B2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hs-129");
    Property.set(localClass, "meshName", "3do/plane/Hs-129B-2/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "meshName_ro", "3do/plane/Hs-129B-2(ro)/hier.him");
    Property.set(localClass, "PaintScheme_ro", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Hs-129B-2.fmd");
    Property.set(localClass, "cockpitClass", CockpitHS_129B2.class);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 9, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_HEAVYCANNON01", "_ExternalDev01", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev02", "_ExternalDev03" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "R4-1xSC250", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC250", null, null, null, null, "BombGunSC250 1", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xSC250_2xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC250", null, null, null, null, "BombGunSC250 1", null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "R4-2xCS50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, null, null, null, null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "R4-4xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, null, null });

    weaponsRegister(localClass, "4xAB23", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", null, null, null, null });

    weaponsRegister(localClass, "6xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "4xSC50_2xAB23", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "R3-4xMG17", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xMG17_2xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "4xMG17_2xAB23", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "R2-1xMk101", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xMk101_2xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "1xMk101_2xAB23", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, null, null, null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "1xMk103", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1xMk103_2xSC50", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "1xMk103_2xAB23", new String[] { "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, null, null, null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", "PylonHs129BombRackR" });

    weaponsRegister(localClass, "Wa-1xBK37", new String[] { null, null, "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, "MGunBK374Hs129 12", "PylonHS129BK37", null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}