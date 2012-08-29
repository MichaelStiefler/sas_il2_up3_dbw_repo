// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitDo217_PGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Do217

public class CockpitDo217_PGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.objects.air.Do217)fm.actor).bPitUnfocused = false;
            saveFov = com.maddox.il2.game.Main3D.FOVX;
            com.maddox.rts.CmdEnv.top().exec("fov 25.0");
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
            super.doFocusLeave();
        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
        com.maddox.rts.CmdEnv.top().exec("fov " + saveFov);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("z_Turret6A", 0.0F, orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("z_Turret6B", 0.0F, orient.getTangage(), 0.0F);
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
        if(f1 > 0.0F)
            f1 = 0.0F;
        if(f1 < 0.0F)
            f1 = 0.0F;
        if(f > 0.0F)
            f = 0.0F;
        if(f < 0.0F)
            f = 0.0F;
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
        {
            if(hook1 == null)
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN06")));
            doHitMasterAircraft(aircraft(), hook1, "_MGUN06");
            if(hook2 == null)
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN07")));
            doHitMasterAircraft(aircraft(), hook1, "_MGUN07");
            if(hook3 == null)
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN08")));
            doHitMasterAircraft(aircraft(), hook1, "_MGUN08");
            if(hook4 == null)
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN09")));
            doHitMasterAircraft(aircraft(), hook1, "_MGUN09");
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
        } else
        {
            iCocking = 0;
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

    protected void reflectPlaneMats()
    {
    }

    public CockpitDo217_PGunner()
    {
        super("3DO/Cockpit/Do217k1/hierpGun.him", "he111_gunner");
        firstEnter = true;
        w = new Vector3f();
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
        hook3 = null;
        hook4 = null;
        iCocking = 0;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean firstEnter;
    private float saveFov;
    public com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;
    private com.maddox.il2.engine.Hook hook3;
    private com.maddox.il2.engine.Hook hook4;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;
    private float pictLever;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_PGunner.class)))), "aiTuretNum", 5);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_PGunner.class)))), "weaponControlNum", 15);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_PGunner.class)))), "astatePilotIndx", 6);
    }
}
