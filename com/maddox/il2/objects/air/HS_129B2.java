// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HS_129B2.java

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

// Referenced classes of package com.maddox.il2.objects.air:
//            HS_129, PaintSchemeFMPar02, NetAircraft, Aircraft

public class HS_129B2 extends com.maddox.il2.objects.air.HS_129
{

    public HS_129B2()
    {
    }

    public void missionStarting()
    {
        super.missionStarting();
        customization();
    }

    private void customization()
    {
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
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s1 + "/Hs-129B-2/Customization.ini", 0));
                java.io.BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                Object obj = null;
                boolean flag = false;
                boolean flag1 = false;
                boolean flag2 = false;
                do
                {
                    java.lang.String s2;
                    if((s2 = bufferedreader.readLine()) == null)
                        break;
                    if(s2.equals("[SmoothNose]"))
                    {
                        flag = true;
                        flag1 = false;
                        flag2 = false;
                    } else
                    if(s2.equals("[NoMirror]"))
                    {
                        flag = false;
                        flag1 = true;
                        flag2 = false;
                    } else
                    if(s2.equals("[NoDFLoop]"))
                    {
                        flag = false;
                        flag1 = false;
                        flag2 = true;
                    } else
                    if(s2.equals(s))
                    {
                        if(flag)
                            hierMesh().chunkVisible("NoseParts_D0", false);
                        if(flag1)
                            hierMesh().chunkVisible("Mirror", false);
                        if(flag2)
                            hierMesh().chunkVisible("DF_loop", false);
                    }
                } while(true);
                bufferedreader.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception);
            }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
            {
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.MGunMK103s_Hs129)
                {
                    hierMesh().chunkVisible("cannonPod", true);
                    hierMesh().chunkVisible("Mk103_Barrel", true);
                    continue;
                }
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.MGunMK101s_Hs129)
                {
                    hierMesh().chunkVisible("cannonPod", true);
                    hierMesh().chunkVisible("Mk101_Barrel", true);
                    continue;
                }
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.MGunBK374Hs129)
                {
                    hierMesh().chunkVisible("BK37_dummy", true);
                    FM.Sq.liftKeel *= 1.5F;
                    continue;
                }
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonHS129MG17S)
                    hierMesh().chunkVisible("MG17_dummy", true);
            }

        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && (com.maddox.il2.ai.World.cur().camouflage == 2 || com.maddox.il2.ai.World.cur().camouflage == 5))
            hierMesh().chunkVisible("NoseParts_D0", false);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HS_129B2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hs-129");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Hs-129B-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3do/plane/Hs-129B-2(ro)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Hs-129B-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHS_129B2.class);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 9, 1, 1, 
            1, 1, 3, 3, 3, 3, 3, 3, 3, 9, 
            9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_HEAVYCANNON01", "_ExternalDev01", "_MGUN03", "_MGUN04", 
            "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev02", 
            "_ExternalDev03"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "R4-1xSC250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC250", null, null, 
            null, null, "BombGunSC250 1", null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xSC250_2xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC250", null, null, 
            null, null, "BombGunSC250 1", null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "R4-2xCS50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "R4-4xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, 
            null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "4xAB23", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, 
            null, null, null, "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "6xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, 
            null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "4xSC50_2xAB23", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHs129BombRackC4x50", null, null, 
            null, null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "R3-4xMG17", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", 
            "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "4xMG17_2xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", 
            "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "4xMG17_2xAB23", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, null, "PylonHS129MG17S", "MGunMG17k 1000", "MGunMG17k 1000", 
            "MGunMG17k 1000", "MGunMG17k 1000", null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "R2-1xMk101", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xMk101_2xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, 
            null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xMk101_2xAB23", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", "MGunMK101s_Hs129 30", null, null, "PylonHS129MK101", null, null, 
            null, null, null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xMk103", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xMk103_2xSC50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, 
            null, null, null, null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "1xMk103_2xAB23", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 1000", "MGunMG15120ki 250", "MGunMG15120ki 250", null, "MGunMK103s_Hs129 80", null, "PylonHS129MK103", null, null, 
            null, null, null, null, null, null, null, "BombGunAB23 1", "BombGunAB23 1", "PylonHs129BombRackL", 
            "PylonHs129BombRackR"
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "Wa-1xBK37", new java.lang.String[] {
            null, null, "MGunMG15120ki 250", "MGunMG15120ki 250", null, null, "MGunBK374Hs129 12", "PylonHS129BK37", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.HS_129B2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
