// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitHalifaxBMkIII_FGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitHalifaxBMkIII_FGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", -10F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretC", -8F, -cvt(orient.getYaw(), -17F, 17F, -17F, 17F), 0.0F);
        mesh.chunkSetAngles("TurretD", 0.5F, cvt(orient.getTangage(), -10F, 15F, -10F, 15F), 0.0F);
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
                if(f < -23F)
                    f = -23F;
                if(f > 23F)
                    f = 23F;
                if(f1 > 15F)
                    f1 = 15F;
                if(f1 < -25F)
                    f1 = -25F;
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

    public CockpitHalifaxBMkIII_FGunner()
    {
        super("3DO/Cockpit/B-25J-FGun/FGunnerHalifaxBMkIII.him", "bf109");
        cockpitNightMats = (new java.lang.String[] {
            "textrbm9", "texture25"
        });
        setNightMats(false);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassDamage2", true);
        if((fm.AS.astateCockpitState & 2) != 0)
            mesh.chunkVisible("XGlassDamage3", true);
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XHullDamage1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("XHullDamage2", true);
    }

    public void toggleLight()
    {
        cockpitLightControl = !cockpitLightControl;
        if(cockpitLightControl)
            setNightMats(true);
        else
            setNightMats(false);
    }

    private void retoggleLight()
    {
        if(cockpitLightControl)
        {
            setNightMats(false);
            setNightMats(true);
        } else
        {
            setNightMats(true);
            setNightMats(false);
        }
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        super.doFocusLeave();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static com.maddox.JGP.Point3d P1 = new Point3d();
    private static com.maddox.JGP.Vector3d V = new Vector3d();

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHalifaxBMkIII_FGunner.class)))), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHalifaxBMkIII_FGunner.class)))), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitHalifaxBMkIII_FGunner.class)))), "astatePilotIndx", 2);
    }
}
