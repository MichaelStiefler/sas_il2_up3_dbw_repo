// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitG4M2E_RGunner.java

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

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Cockpit, Aircraft

public class CockpitG4M2E_RGunner extends com.maddox.il2.objects.air.CockpitGunner
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
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("RearAXX_D0", false);
            if(curMat == null)
            {
                curMat = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().material(((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().materialFind("Pilot2"));
                newMat = (com.maddox.il2.engine.Mat)((com.maddox.il2.engine.GObj) (curMat)).Clone();
                newMat.setLayer(0);
                newMat.set((short)0, false);
            }
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().materialReplace("Pilot2", newMat);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(!((com.maddox.il2.objects.air.Cockpit)this).isFocused())
        {
            return;
        } else
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().materialReplace("Pilot2", curMat);
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("RearAXX_D0", ((com.maddox.il2.objects.air.Cockpit)this).aircraft().isChunkAnyDamageVisible("CF_D"));
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("CF_D0", true);
            super.doFocusLeave();
            return;
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret4A", 0.0F, f, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret4B", 0.0F, f1, 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
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
            if(f1 < ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -60F, -30F, -10F, -16F))
                f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -60F, -30F, -10F, -16F);
        } else
        if(f < 0.0F)
        {
            if(f1 < ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -30F, 0.0F, -16F, -23F))
                f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -30F, 0.0F, -16F, -23F);
            if(f1 > ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -10F, 0.0F, 45F, 33F))
                f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, -10F, 0.0F, 45F, 33F);
        } else
        {
            if(f1 < ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, 0.0F, 30F, -23F, -6F))
                f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, 0.0F, 30F, -23F, -6F);
            if(f1 > ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, 0.0F, 30F, 33F, 22F))
                f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(f, 0.0F, 30F, 33F, 22F);
        }
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret5A", 0.0F, -((com.maddox.il2.objects.sounds.SndAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).FM.turret[4].tu[0], 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret5B", 0.0F, ((com.maddox.il2.objects.sounds.SndAircraft) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).FM.turret[4].tu[1], 0.0F);
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
            super.bGunFire = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
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
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.07F * (float)iCocking;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Turret4C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
    }

    public void doGunFire(boolean flag)
    {
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
            super.bGunFire = false;
        else
            super.bGunFire = flag;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
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
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 4);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "weaponControlNum", 14);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 4);
    }
}