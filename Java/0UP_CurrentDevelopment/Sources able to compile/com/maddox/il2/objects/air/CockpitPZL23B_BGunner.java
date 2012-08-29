package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitPZL23B_BGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        super.mesh.chunkSetAngles("Turret2A", 0.0F, orient.getYaw(), 0.0F);
        super.mesh.chunkSetAngles("Turret2B", 0.0F, orient.getTangage(), 0.0F);
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
        if(f < -30F)
            f = -30F;
        if(f > 30F)
            f = 30F;
        if(f1 > 0.0F)
            f1 = 0.0F;
        if(f1 < -60F)
            f1 = -60F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !aiTurret().bIsOperable)
            super.bGunFire = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
        if(super.bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN03");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
        }
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !aiTurret().bIsOperable)
            super.bGunFire = false;
        else
            super.bGunFire = flag;
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
    }

    public CockpitPZL23B_BGunner()
    {
        super("3DO/Cockpit/PZL23B_BGun/hier.him", "he111_gunner");
        hook1 = null;
        bNeedSetUp = true;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
    }

    private com.maddox.il2.engine.Hook hook1;
    private boolean bNeedSetUp;

    static 
    {
        com.maddox.rts.Property.set(CockpitPZL23B_BGunner.class, "aiTuretNum", 1);
        com.maddox.rts.Property.set(CockpitPZL23B_BGunner.class, "weaponControlNum", 11);
        com.maddox.rts.Property.set(CockpitPZL23B_BGunner.class, "astatePilotIndx", 2);
    }
}
