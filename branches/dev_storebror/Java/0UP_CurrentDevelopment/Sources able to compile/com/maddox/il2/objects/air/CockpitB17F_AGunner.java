package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB17F_AGunner extends CockpitGunner
{

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            reflectPlaneToModel();
            bNeedSetUp = false;
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D1o"));
        mesh.materialReplace("Gloss2D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretC", 0.0F, cvt(orient.getYaw(), -38F, 38F, -15F, 15F), 0.0F);
        mesh.chunkSetAngles("TurretD", 0.0F, cvt(orient.getTangage(), -43F, 43F, -10F, 10F), 0.0F);
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
                if(f1 > 43F)
                    f1 = 43F;
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

    public CockpitB17F_AGunner()
    {
        super("3DO/Cockpit/B-25J-AGun/AGunnerB17F.him", "bf109");
        bNeedSetUp = true;
    }

    private boolean bNeedSetUp;

    static 
    {
        com.maddox.rts.Property.set(CockpitB17F_AGunner.class, "aiTuretNum", 7);
        com.maddox.rts.Property.set(CockpitB17F_AGunner.class, "weaponControlNum", 17);
        com.maddox.rts.Property.set(CockpitB17F_AGunner.class, "astatePilotIndx", 4);
    }
}
