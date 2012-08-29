// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitME_323_RGunner.java

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
//            CockpitGunner, Cockpit

public class CockpitME_323_RGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretRA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretRB", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
            if(!aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = orient.getYaw();
                float f1 = orient.getTangage();
                if(f < -40F)
                    f = -40F;
                if(f > 55F)
                    f = 55F;
                if(f1 > 30F)
                    f1 = 30F;
                if(f1 < -40F)
                    f1 = -40F;
                if(f1 < -55F + 0.5F * f)
                    f1 = -55F + 0.5F * f;
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = new HookNamed(aircraft(), "_MGUN05");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN05");
                if(iCocking > 0)
                    iCocking = 0;
                else
                    iCocking = 1;
                iNewVisDrums = (int)((float)emitter.countBullets() / 250F);
                if(iNewVisDrums < iOldVisDrums)
                {
                    iOldVisDrums = iNewVisDrums;
                    mesh.chunkVisible("DrumR1", iNewVisDrums > 1);
                    mesh.chunkVisible("DrumR2", iNewVisDrums > 0);
                    sfxClick(13);
                }
            } else
            {
                iCocking = 0;
            }
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.07F * (float)iCocking;
            mesh.chunkSetLocate("LeverR", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        }
    }

    public void doGunFire(boolean flag)
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            else
                bGunFire = flag;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    public CockpitME_323_RGunner()
    {
        super("3DO/Cockpit/He-111H-2-RGun/RGunnerME323.him", "he111_gunner");
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
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitME_323_RGunner.class, "aiTuretNum", 3);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitME_323_RGunner.class, "weaponControlNum", 13);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitME_323_RGunner.class, "astatePilotIndx", 5);
    }
}
