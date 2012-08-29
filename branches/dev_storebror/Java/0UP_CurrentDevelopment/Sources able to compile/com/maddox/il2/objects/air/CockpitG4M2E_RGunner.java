package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.GObj;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitG4M2E_RGunner extends CockpitGunner
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            mesh.chunkSetAngles("Turret5A", 0.0F, -((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[3].tu[0], 0.0F);
            mesh.chunkSetAngles("Turret5B", 0.0F, ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[3].tu[1], 0.0F);
            return true;
        }

        Interpolater()
        {
        }
    }


    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("RearAXX_D0", false);
            if(curMat == null)
            {
                curMat = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("Pilot2"));
                newMat = (com.maddox.il2.engine.Mat)curMat.Clone();
                newMat.setLayer(0);
                newMat.set((short)0, false);
            }
            aircraft().hierMesh().materialReplace("Pilot2", newMat);
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!isFocused())
        {
            return;
        } else
        {
            aircraft().hierMesh().materialReplace("Pilot2", curMat);
            aircraft().hierMesh().chunkVisible("RearAXX_D0", aircraft().isChunkAnyDamageVisible("CF_D"));
            aircraft().hierMesh().chunkVisible("CF_D0", true);
            super.doFocusLeave();
            return;
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        super.mesh.chunkSetAngles("Turret4A", 0.0F, f, 0.0F);
        super.mesh.chunkSetAngles("Turret4B", 0.0F, f1, 0.0F);
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
        if(f < -60F)
            f = -60F;
        if(f > 30F)
            f = 30F;
        if(f1 < -45F)
            f1 = -45F;
        if(f1 > 45F)
            f1 = 45F;
        if(f < -30F)
        {
            if(f1 < cvt(f, -60F, -30F, -10F, -16F))
                f1 = cvt(f, -60F, -30F, -10F, -16F);
        } else
        if(f < 0.0F)
        {
            if(f1 < cvt(f, -30F, 0.0F, -16F, -23F))
                f1 = cvt(f, -30F, 0.0F, -16F, -23F);
            if(f1 > cvt(f, -10F, 0.0F, 45F, 33F))
                f1 = cvt(f, -10F, 0.0F, 45F, 33F);
        } else
        {
            if(f1 < cvt(f, 0.0F, 30F, -23F, -6F))
                f1 = cvt(f, 0.0F, 30F, -23F, -6F);
            if(f1 > cvt(f, 0.0F, 30F, 33F, 22F))
                f1 = cvt(f, 0.0F, 30F, 33F, 22F);
        }
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        super.mesh.chunkSetAngles("Turret5A", 0.0F, -((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[4].tu[0], 0.0F);
        super.mesh.chunkSetAngles("Turret5B", 0.0F, ((com.maddox.il2.objects.sounds.SndAircraft) (aircraft())).FM.turret[4].tu[1], 0.0F);
        if(!isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !aiTurret().bIsOperable)
            super.bGunFire = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
        if(super.bGunFire)
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
        super.mesh.chunkSetLocate("Turret4C", Cockpit.xyz, Cockpit.ypr);
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

    public CockpitG4M2E_RGunner()
    {
        super("3DO/Cockpit/G4M2E-RGun/hier.him", "he111_gunner");
        curMat = null;
        iCocking = 0;
    }

    public void reflectCockpitState()
    {
    }

    com.maddox.il2.engine.Mat curMat;
    com.maddox.il2.engine.Mat newMat;
    private int iCocking;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 4);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 14);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 4);
    }
}
