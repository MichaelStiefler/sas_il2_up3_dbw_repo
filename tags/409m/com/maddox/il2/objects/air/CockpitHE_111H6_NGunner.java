// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_111H6_NGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, HE_111, Aircraft

public class CockpitHE_111H6_NGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.objects.air.HE_111)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
            aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_FAK", false);
            aircraft().hierMesh().chunkVisible("Head1_FAK", false);
            aircraft().hierMesh().chunkVisible("Pilot1_FAL", false);
            aircraft().hierMesh().chunkVisible("Pilot2_FAK", false);
            aircraft().hierMesh().chunkVisible("Pilot2_FAL", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.objects.air.HE_111)fm.actor).bPitUnfocused = true;
        aircraft().hierMesh().chunkVisible("Cockpit_D0", aircraft().hierMesh().isChunkVisible("Nose_D0") || aircraft().hierMesh().isChunkVisible("Nose_D1") || aircraft().hierMesh().isChunkVisible("Nose_D2"));
        aircraft().hierMesh().chunkVisible("Turret1C_D0", aircraft().hierMesh().isChunkVisible("Turret1B_D0"));
        aircraft().hierMesh().chunkVisible("Pilot1_FAK", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
        aircraft().hierMesh().chunkVisible("Head1_FAK", aircraft().hierMesh().isChunkVisible("Head1_D0"));
        aircraft().hierMesh().chunkVisible("Pilot1_FAL", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
        aircraft().hierMesh().chunkVisible("Pilot2_FAK", aircraft().hierMesh().isChunkVisible("Pilot2_D0"));
        aircraft().hierMesh().chunkVisible("Pilot2_FAL", aircraft().hierMesh().isChunkVisible("Pilot2_D1"));
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("TurretA", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, f1, 0.0F);
        if(f > 15F)
            f = 15F;
        if(f1 < -21F)
            f1 = -21F;
        mesh.chunkSetAngles("CameraRodA", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("CameraRodB", 0.0F, f1, 0.0F);
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
        if(f < -25F)
            f = -25F;
        if(f > 15F)
            f = 15F;
        if(f1 > 0.0F)
            f1 = 0.0F;
        if(f1 < -40F)
            f1 = -40F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
            mesh.chunkSetAngles("Butona", 0.15F, 0.0F, 0.0F);
        else
            mesh.chunkSetAngles("Butona", 0.0F, 0.0F, 0.0F);
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

    public CockpitHE_111H6_NGunner()
    {
        super("3DO/Cockpit/He-111H-6-NGun/hier.him", "he111_gunner");
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10F * fm.CT.ElevatorControl);
        mesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40F * fm.CT.AileronControl);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("ZHolesL_D1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("ZHolesL_D2", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("ZHolesR_D1", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("ZHolesR_D2", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("ZHolesF_D1", true);
        if((fm.AS.astateCockpitState & 0x80) != 0)
            mesh.chunkVisible("zOil_D1", true);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, "astatePilotIndx", 1);
    }
}
