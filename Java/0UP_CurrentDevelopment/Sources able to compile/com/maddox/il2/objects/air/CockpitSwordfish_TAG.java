package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitSwordfish_TAG extends CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            com.maddox.il2.engine.hotkey.HookPilot hookpilot = com.maddox.il2.engine.hotkey.HookPilot.current;
            hookpilot.doAim(false);
            ((Swordfish)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Gunsight_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        if(isFocused())
        {
            ((Swordfish)fm.actor).bPitUnfocused = true;
            aircraft().hierMesh().chunkVisible("Gunsight_D0", true);
            super.doFocusLeave();
        }
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        cockpitAirBrakePos = 0.9F * cockpitAirBrakePos + 0.1F * ((Swordfish)fm.actor).airBrakePos;
        elevTurretA = 70F * (1.0F - cockpitAirBrakePos);
        mesh.chunkSetAngles("TurrBase", 0.0F, elevTurretA, 0.0F);
        mesh.chunkSetAngles("TurrBase1", elevTurretA, 0.0F, 0.0F);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        yawTurretA = orient.getYaw();
        mesh.chunkSetAngles("Turret1A", 0.0F, -yawTurretA, 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!isRealMode())
            return;
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        if(f < -70F)
            f = -70F;
        if(f > 70F)
            f = 70F;
        if(f1 > 70F)
            f1 = 70F;
        if(f1 < -45F)
            f1 = -45F;
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
                hook1 = new HookNamed(aircraft(), "_turret1");
            doHitMasterAircraft(aircraft(), hook1, "_turret1");
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
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        mesh.materialReplace("Matt1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D2o"));
        mesh.materialReplace("Matt1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
    }

    public CockpitSwordfish_TAG()
    {
        super("3DO/Cockpit/SwordfishTAG/hier.him", "he111_gunner");
        bEntered = false;
        yawTurretA = 0.0F;
        cockpitAirBrakePos = 0.0F;
        w = new Vector3f();
        pictAiler = 0.0F;
        pictElev = 0.0F;
        tmpP = new Point3d();
        tmpV = new Vector3d();
        bNeedSetUp = true;
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 3;
        iNewVisDrums = 3;
        pictLever = 0.0F;
    }

    private boolean bTurrVisible;
    private float saveFov;
    private float aAim;
    private float tAim;
    private float kAim;
    private boolean bEntered;
    private float elevTurretA;
    private float yawTurretA;
    private float cockpitAirBrakePos;
    public com.maddox.JGP.Vector3f w;
    private float pictAiler;
    private float pictElev;
    private com.maddox.JGP.Point3d tmpP;
    private com.maddox.JGP.Vector3d tmpV;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;
    private float pictLever;

    static 
    {
        com.maddox.rts.Property.set(CockpitSwordfish_TAG.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(CockpitSwordfish_TAG.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(CockpitSwordfish_TAG.class, "astatePilotIndx", 2);
    }
}
