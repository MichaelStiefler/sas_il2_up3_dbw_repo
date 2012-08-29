package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class I_15BIS extends I_15xyz
{

    public I_15BIS()
    {
    }

    public void missionStarting()
    {
        super.missionStarting();
        customization();
    }

    private void customization()
    {
        if(!com.maddox.il2.game.Mission.isSingle())
            return;
        int i = hierMesh().chunkFindCheck("cf_D0");
        int j = hierMesh().materialFindInChunk("Gloss1D0o", i);
        com.maddox.il2.engine.Mat mat = hierMesh().material(j);
        java.lang.String s = mat.Name();
        if(s.startsWith("PaintSchemes/Cache"))
            try
            {
                s = s.substring(19);
                s = s.substring(0, s.indexOf("/"));
                java.lang.String s1 = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath();
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s1 + "/I-15bis/Customization.ini", 0));
                java.io.BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                Object obj = null;
                boolean flag = false;
                boolean flag1 = false;
                do
                {
                    java.lang.String s2;
                    if((s2 = bufferedreader.readLine()) == null)
                        break;
                    if(s2.equals("[WheelSpats]"))
                    {
                        flag = true;
                        flag1 = false;
                    } else
                    if(s2.equals("[WheelSpatsGreen]"))
                    {
                        flag = true;
                        flag1 = true;
                    } else
                    if(s2.equals(s) && flag)
                    {
                        hierMesh().chunkVisible("GearL4_D0", true);
                        hierMesh().chunkVisible("GearR4_D0", true);
                        if(flag1 && com.maddox.il2.engine.Config.isUSE_RENDER())
                            hierMesh().materialReplace("Spat", "SpatGreen");
                    }
                } while(true);
                bufferedreader.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception);
            }
        else
        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) > 0.9F)
        {
            hierMesh().chunkVisible("GearL4_D0", true);
            hierMesh().chunkVisible("GearR4_D0", true);
        }
    }

    static 
    {
        java.lang.Class class1 = I_15BIS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-15bis");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-15bis/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFCSPar08());
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1942F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-15bis.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitI_15Bis.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.84305F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        I_15BIS.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        I_15BIS.weaponsRegister(class1, "4xAO10", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", "BombGunAO10S 1", null, null, null, null, 
            null, null, null, null
        });
        I_15BIS.weaponsRegister(class1, "2xAO10_2xFAB50", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, "BombGunAO10S 1", "BombGunAO10S 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, 
            null, null, null, null
        });
        I_15BIS.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        I_15BIS.weaponsRegister(class1, "4xRS82", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            null, null, null, null, null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", null, null
        });
        I_15BIS.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
