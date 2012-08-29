// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitA_20G_TGunner.java

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
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitA_20G_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("Turret1B_D0", aircraft().hierMesh().isChunkVisible("Turret1A_D0"));
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("Turret1A", -orient.getYaw(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        float f2 = java.lang.Math.abs(f);
        for(; f < -180F; f += 360F);
        for(; f > 180F; f -= 360F);
        for(; prevA0 < -180F; prevA0 += 360F);
        for(; prevA0 > 180F; prevA0 -= 360F);
        if(!isRealMode())
        {
            prevA0 = f;
            return;
        }
        if(bNeedSetUp)
        {
            prevTime = com.maddox.rts.Time.current() - 1L;
            bNeedSetUp = false;
        }
        if(f < -120F && prevA0 > 120F)
            f += 360F;
        else
        if(f > 120F && prevA0 < -120F)
            prevA0 += 360F;
        float f3 = f - prevA0;
        float f4 = 0.001F * (float)(com.maddox.rts.Time.current() - prevTime);
        float f5 = java.lang.Math.abs(f3 / f4);
        if(f5 > 120F)
            if(f > prevA0)
                f = prevA0 + 120F * f4;
            else
            if(f < prevA0)
                f = prevA0 - 120F * f4;
        prevTime = com.maddox.rts.Time.current();
        if(f1 > 89F)
            f1 = 89F;
        if(f1 < cvt(f2, 140F, 180F, -1F, 25F))
            f1 = cvt(f2, 140F, 180F, -1F, 25F);
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
        prevA0 = f;
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN07");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN07");
            if(hook2 == null)
                hook2 = new HookNamed(aircraft(), "_MGUN08");
            doHitMasterAircraft(aircraft(), hook2, "_MGUN08");
        }
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

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
    }

    public CockpitA_20G_TGunner()
    {
        super("3DO/Cockpit/A-20G-TGun/hier.him", "he111_gunner");
        bNeedSetUp = true;
        prevTime = -1L;
        prevA0 = 0.0F;
        hook1 = null;
        hook2 = null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bNeedSetUp;
    private long prevTime;
    private float prevA0;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitA_20G_TGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitA_20G_TGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitA_20G_TGunner.class, "astatePilotIndx", 1);
    }
}
