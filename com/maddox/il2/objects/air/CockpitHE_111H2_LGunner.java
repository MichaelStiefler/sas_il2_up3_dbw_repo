// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_111H2_LGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner

public class CockpitHE_111H2_LGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretLA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretLB", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!isRealMode())
            return;
        if(!aiTurret().bIsOperable)
        {
            orient.setYPR(0.0F, 0.0F, 0.0F);
            return;
        }
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        if(f < -55F)
            f = -55F;
        if(f > 23F)
            f = 23F;
        if(f1 > 30F)
            f1 = 30F;
        if(f1 < -40F)
            f1 = -40F;
        if(f1 < -55F - 0.5F * f)
            f1 = -55F - 0.5F * f;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN04");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN04");
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
            iNewVisDrums = (int)((float)emitter.countBullets() / 250F);
            if(iNewVisDrums < iOldVisDrums)
            {
                iOldVisDrums = iNewVisDrums;
                mesh.chunkVisible("DrumL1", iNewVisDrums > 1);
                mesh.chunkVisible("DrumL2", iNewVisDrums > 0);
                sfxClick(13);
            }
        } else
        {
            iCocking = 0;
        }
        resetYPRmodifier();
        xyz[0] = -0.07F * (float)iCocking;
        mesh.chunkSetLocate("LeverL", xyz, ypr);
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        else
            bGunFire = flag;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
    }

    public CockpitHE_111H2_LGunner()
    {
        super("3DO/Cockpit/He-111H-2-LGun/hier.him", "he111_gunner");
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 2;
        iNewVisDrums = 2;
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            mesh.chunkVisible("Flare", true);
            setNightMats(true);
        } else
        {
            mesh.chunkVisible("Flare", false);
            setNightMats(false);
        }
    }

    public void reflectCockpitState()
    {
        if(fm.AS.astateCockpitState != 0)
            mesh.chunkVisible("Holes_D1", true);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, "aiTuretNum", 3);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, "weaponControlNum", 13);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, "astatePilotIndx", 4);
    }
}
