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
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitA_20B3_BGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("Turret1A", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
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
        if(f1 > 15F)
            f1 = 15F;
        if(f1 < -45F)
            f1 = -45F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        mesh.chunkSetAngles("Turret1A_D0", 0.0F, aircraft().FM.turret[0].tu[0], 0.0F);
        mesh.chunkSetAngles("Turret1B_D0", 0.0F, aircraft().FM.turret[0].tu[1], 0.0F);
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN09");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN09");
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
        if(fm.AS.astateCockpitState != 0)
        {
            mesh.chunkVisible("Z_Holes1_D1", true);
            mesh.chunkVisible("Z_Holes2_D1", true);
            mesh.chunkVisible("Z_Holes3_D1", true);
            mesh.chunkVisible("Z_Holes4_D1", true);
            mesh.chunkVisible("Z_Holes5_D1", true);
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        reflectPlaneToModel();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Glass2"));
        mesh.materialReplace("Glass2", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot1"));
        mesh.materialReplace("Pilot1", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("Pilot2_D0", hiermesh.isChunkVisible("Pilot2_D0"));
        mesh.chunkVisible("HMask2_D0", hiermesh.isChunkVisible("HMask2_D0"));
        mesh.chunkVisible("Pilot2_D1", hiermesh.isChunkVisible("Pilot2_D1"));
        mesh.chunkVisible("Turret1A_D0", hiermesh.isChunkVisible("Turret1A_D0"));
        mesh.chunkVisible("Turret1B_D0", hiermesh.isChunkVisible("Turret1B_D0"));
    }

    public CockpitA_20B3_BGunner()
    {
        super("3DO/Cockpit/A-20BostonMkIII-BGun/hier.him", "he111_gunner");
        bNeedSetUp = true;
        hook1 = null;
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 1);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 11);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 2);
    }
}
