// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitG4M1_11_LGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitG4M1_11_LGunner extends com.maddox.il2.objects.air.CockpitGunner
{

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
            super.doFocusLeave();
            return;
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("Turret5A", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("Turret5B", 0.0F, f1, 0.0F);
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
        if(f > 60F)
            f = 60F;
        if(f1 < -45F)
            f1 = -45F;
        if(f1 > 45F)
            f1 = 45F;
        if(f < 0.0F)
        {
            if(f1 < cvt(f, -30F, 0.0F, -6F, -23F))
                f1 = cvt(f, -30F, 0.0F, -6F, -23F);
            if(f1 > cvt(f, -30F, 0.0F, 22F, 33F))
                f1 = cvt(f, -30F, 0.0F, 22F, 33F);
        } else
        if(f < 30F)
        {
            if(f1 < cvt(f, 0.0F, 30F, -23F, -16F))
                f1 = cvt(f, 0.0F, 30F, -23F, -16F);
            if(f1 > cvt(f, 0.0F, 10F, 33F, 45F))
                f1 = cvt(f, 0.0F, 10F, 33F, 45F);
        } else
        if(f1 < cvt(f, 30F, 60F, -16F, -10F))
            f1 = cvt(f, 30F, 60F, -16F, -10F);
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        mesh.chunkSetAngles("Turret4A", 0.0F, -aircraft().FM.turret[3].tu[0], 0.0F);
        mesh.chunkSetAngles("Turret4B", 0.0F, aircraft().FM.turret[3].tu[1], 0.0F);
        if(!isRealMode())
            return;
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
        xyz[1] = -0.07F * (float)iCocking;
        mesh.chunkSetLocate("Turret5C", xyz, ypr);
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

    public CockpitG4M1_11_LGunner()
    {
        super("3DO/Cockpit/G4M1-11-LGun/hier.him", "he111_gunner");
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
