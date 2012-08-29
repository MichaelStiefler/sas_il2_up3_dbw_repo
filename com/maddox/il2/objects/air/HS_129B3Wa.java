// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HS_129B3Wa.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunBK75;
import com.maddox.il2.objects.weapons.PylonHS129BK75;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HS_129, PaintSchemeFMPar02, Aircraft, NetAircraft

public class HS_129B3Wa extends com.maddox.il2.objects.air.HS_129
{

    public HS_129B3Wa()
    {
        phase = 0;
        disp = 0.0F;
        oldbullets = -1;
        g1 = null;
        BK75dropped = false;
        BK75stabilizingMultiplier = 2.0F;
    }

    public void auxPressed(int i)
    {
        if(i == 1)
            FM.CT.dropExternalStores(true);
        super.auxPressed(i);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(!BK75dropped && !FM.isPlayers() && (isNetMaster() || !isNet()) && !FM.AS.isPilotDead(0) && !isNetPlayer() && FM.AS.astateBailoutStep == 0 && (FM.AS.astateEngineStates[0] > 2 || FM.AS.astateEngineStates[1] > 2 || FM.EI.engines[0].getReadyness() < 0.5F || FM.EI.engines[1].getReadyness() < 0.5F || ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 49 || FM.isReadyToReturn()))
            FM.CT.dropExternalStores(true);
    }

    public boolean dropExternalStores(boolean flag)
    {
        if(!BK75dropped)
        {
            if(flag && (FM.getSpeedKMH() > 342F || FM.getSpeedKMH() < 95F))
                return false;
            sfxHit(1.0F, new Point3d(0.0D, 0.0D, -1D));
            BK75dropped = true;
            ((com.maddox.il2.objects.weapons.Gun)g1).destroy();
            FM.Sq.liftKeel /= BK75stabilizingMultiplier;
            if(com.maddox.il2.ai.World.getPlayerAircraft() == this)
                com.maddox.il2.ai.World.cur().scoreCounter.playerDroppedExternalStores(100);
            for(int i = 0; i < FM.CT.Weapons.length; i++)
            {
                com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
                if(abulletemitter == null)
                    continue;
                for(int j = 0; j < abulletemitter.length; j++)
                {
                    com.maddox.il2.ai.BulletEmitter bulletemitter = abulletemitter[j];
                    if((bulletemitter instanceof com.maddox.il2.objects.weapons.MGunBK75) || (bulletemitter instanceof com.maddox.il2.objects.weapons.PylonHS129BK75))
                    {
                        FM.CT.Weapons[i][j] = com.maddox.il2.objects.weapons.GunEmpty.get();
                        com.maddox.il2.objects.weapons.GunEmpty gunempty = com.maddox.il2.objects.weapons.GunEmpty.get();
                    }
                }

            }

            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            vector3d.z = vector3d.z - 8D;
            hierMesh().chunkVisible("Hole_d0", true);
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("BK75Wreck_D0"));
            wreckage.setSpeed(vector3d);
            wreckage.collide(true);
            hierMesh().chunkSetAngles("BK75Pod_D0", 0.0F, 180F, 0.0F);
            hierMesh().chunkSetAngles("Barrel_D0", 0.0F, 145F, 0.0F);
            hierMesh().chunkVisible("BK75Pod_D0", false);
            hierMesh().chunkVisible("Barrel_D0", false);
            hierMesh().chunkVisible("sled_d0", false);
            hierMesh().chunkVisible("shell_d0", false);
            return true;
        } else
        {
            return false;
        }
    }

    public void onAircraftLoaded()
    {
        if(FM.CT.Weapons[1] != null)
            g1 = FM.CT.Weapons[1][0];
        FM.Sq.liftKeel *= BK75stabilizingMultiplier;
    }

    public void update(float f)
    {
        if(g1 != null)
            switch(phase)
            {
            default:
                break;

            case 0: // '\0'
                if(g1.isShots() && oldbullets != g1.countBullets())
                {
                    oldbullets = g1.countBullets();
                    phase++;
                    hierMesh().chunkVisible("Shell_D0", true);
                    disp = 0.0F;
                }
                break;

            case 1: // '\001'
                disp += 6F * f;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp * 1.5F;
                hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(disp >= 0.75F)
                    phase++;
                break;

            case 2: // '\002'
                com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Shell_D0"));
                com.maddox.il2.engine.Eff3DActor.New(wreckage, null, null, 1.0F, com.maddox.il2.objects.Wreckage.SMOKE, 3F);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.set(FM.Vwld);
                vector3d.z = vector3d.z - 8D;
                wreckage.setSpeed(vector3d);
                hierMesh().chunkVisible("Shell_D0", false);
                phase++;
                break;

            case 3: // '\003'
                disp -= 0.8F * f;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                com.maddox.il2.objects.air.Aircraft.xyz[0] = disp * 1.5F;
                hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                if(disp <= 0.0F)
                {
                    disp = 0.0F;
                    com.maddox.il2.objects.air.Aircraft.xyz[0] = disp;
                    hierMesh().chunkSetLocate("Barrel_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    hierMesh().chunkSetLocate("Sled_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                    phase++;
                }
                break;

            case 4: // '\004'
                phase = 0;
                break;
            }
        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private int phase;
    private float disp;
    private int oldbullets;
    private com.maddox.il2.ai.BulletEmitter g1;
    private boolean BK75dropped;
    private float BK75stabilizingMultiplier;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HS_129B3Wa.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hs-129");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Hs-129B-3Wa/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Hs-129B-3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHS_129B3.class);
        com.maddox.il2.objects.air.HS_129B3Wa.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_HEAVYCANNON01", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.HS_129B3Wa.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131ki 500", "MGunMG131ki 500", "MGunBK75 13", "PylonHS129BK75"
        });
        com.maddox.il2.objects.air.HS_129B3Wa.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
