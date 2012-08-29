// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitHE_111Z_RBGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Cockpit

public class CockpitHE_111Z_RBGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretBA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretBB", 0.0F, orient.getTangage(), 0.0F);
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
                if(f < -35F)
                    f = -35F;
                if(f > 40F)
                    f = 40F;
                if(f1 > 46F)
                    f1 = 46F;
                if(f1 < -30F)
                    f1 = -30F;
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
                    hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN03")));
                doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
                if(iCocking > 0)
                    iCocking = 0;
                else
                    iCocking = 1;
                iNewVisDrums = (int)((float)emitter.countBullets() / 250F);
                if(iNewVisDrums < iOldVisDrums)
                {
                    iOldVisDrums = iNewVisDrums;
                    mesh.chunkVisible("DrumB1", iNewVisDrums > 1);
                    mesh.chunkVisible("DrumB2", iNewVisDrums > 0);
                    sfxClick(13);
                }
            } else
            {
                iCocking = 0;
            }
            resetYPRmodifier();
            com.maddox.il2.objects.air.Cockpit.xyz[0] = -0.07F * (float)iCocking;
            mesh.chunkSetLocate("LeverB", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
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

    public CockpitHE_111Z_RBGunner()
    {
        super("3DO/Cockpit/He-111H-2-BGun/RBGunnerHE111Z.him", "he111_gunner");
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 2;
        iNewVisDrums = 2;
        com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (mesh)), "LIGHT1");
        com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light1 = new LightPointActor(new LightPoint(), loc.getPoint());
        light1.light.setColor(203F, 198F, 161F);
        light1.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LIGHT1", ((java.lang.Object) (light1)));
        hooknamed = new HookNamed(((com.maddox.il2.engine.Mesh) (mesh)), "LIGHT2");
        loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        hooknamed.computePos(((com.maddox.il2.engine.Actor) (this)), new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
        light2 = new LightPointActor(new LightPoint(), loc.getPoint());
        light2.light.setColor(203F, 198F, 161F);
        light2.light.setEmit(0.0F, 0.0F);
        pos.base().draw.lightMap().put("LIGHT2", ((java.lang.Object) (light2)));
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
        {
            light1.light.setEmit(0.004F, 6.05F);
            light2.light.setEmit(1.1F, 0.2F);
            mesh.chunkVisible("Flare", true);
            setNightMats(true);
        } else
        {
            light1.light.setEmit(0.0F, 0.0F);
            light2.light.setEmit(0.0F, 0.0F);
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

    private com.maddox.il2.engine.LightPointActor light1;
    private com.maddox.il2.engine.LightPointActor light2;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHE_111Z_RBGunner.class)))), "aiTuretNum", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHE_111Z_RBGunner.class)))), "weaponControlNum", 12);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHE_111Z_RBGunner.class)))), "astatePilotIndx", 3);
    }
}
