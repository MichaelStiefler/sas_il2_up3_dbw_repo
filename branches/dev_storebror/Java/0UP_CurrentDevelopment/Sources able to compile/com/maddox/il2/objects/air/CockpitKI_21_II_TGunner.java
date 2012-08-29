package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitKI_21_II_TGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("Turret3A", -16F, -f, 0.0F);
        mesh.chunkSetAngles("Turret3B", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Turret3D", 0.0F, -f, 0.0F);
        mesh.chunkSetAngles("Turret3E", 0.0F, f1, 0.0F);
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
                if(f > 40F)
                    f = 40F;
                if(f1 > 35F)
                    f1 = 35F;
                if(f1 < 0.0F)
                    f1 = 0.0F;
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
                if(iCocking > 0)
                    iCocking = 0;
                else
                    iCocking = 1;
            } else
            {
                iCocking = 0;
            }
            resetYPRmodifier();
            Cockpit.xyz[1] = -0.07F * (float)iCocking;
            Cockpit.ypr[1] = 0.0F;
            mesh.chunkSetLocate("Turret3C", Cockpit.xyz, Cockpit.ypr);
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

    public CockpitKI_21_II_TGunner()
    {
        super("3DO/Cockpit/KI21SASTGun/TGunnerKI21II.him", "he111_gunner");
        bNeedSetUp = true;
        iCocking = 0;
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
        mesh.materialReplace("Gloss1D0o", mat);
    }

    public void reflectCockpitState()
    {
    }

    private boolean bNeedSetUp;
    private int iCocking;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 1);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 11);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 3);
    }
}
