package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB_24J100_FGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("Body", 180F, 0.0F, 0.0F);
        mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretC", 0.0F, cvt(orient.getYaw(), -38F, 38F, -15F, 15F), 0.0F);
        mesh.chunkSetAngles("TurretE", -orient.getYaw(), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurretF", 0.0F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretG", -cvt(orient.getYaw(), -33F, 33F, -33F, 33F), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurretH", 0.0F, cvt(orient.getTangage(), -10F, 32F, -10F, 32F), 0.0F);
        resetYPRmodifier();
        Cockpit.xyz[0] = cvt(java.lang.Math.max(java.lang.Math.abs(orient.getYaw()), java.lang.Math.abs(orient.getTangage())), 0.0F, 20F, 0.0F, 0.3F);
        mesh.chunkSetLocate("TurretI", Cockpit.xyz, Cockpit.ypr);
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
                if(f < -38F)
                    f = -38F;
                if(f > 38F)
                    f = 38F;
                if(f1 > 38F)
                    f1 = 38F;
                if(f1 < -41F)
                    f1 = -41F;
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
                    hook1 = new HookNamed(aircraft(), "_MGUN01");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN01");
                if(hook2 == null)
                    hook2 = new HookNamed(aircraft(), "_MGUN02");
                doHitMasterAircraft(aircraft(), hook2, "_MGUN02");
            }
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public CockpitB_24J100_FGunner()
    {
        super("3DO/Cockpit/ConsolidatedA6CTurret/B24J100FGunner.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(CockpitB_24J100_FGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(CockpitB_24J100_FGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(CockpitB_24J100_FGunner.class, "astatePilotIndx", 2);
    }
}
