package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB17F_FGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", -13F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretB", -15.5F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretC", 0.0F, -cvt(orient.getYaw(), -17F, 17F, -17F, 17F), 0.0F);
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
                if(f < -35F)
                    f = -35F;
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
            fm.CT.WeaponControl[10] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = new HookNamed(aircraft(), "_MGUN02");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN02");
            }
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
            fm.CT.WeaponControl[10] = bGunFire;
        }
    }

    public CockpitB17F_FGunner()
    {
        super("3DO/Cockpit/B-25J-FGun/FGunnerB17F.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
        cockpitNightMats = (new java.lang.String[] {
            "textrbm9", "texture25"
        });
        setNightMats(false);
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
        super.doFocusLeave();
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

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(com.maddox.il2.fm.Pitot.Indicator((float)fm.Loc.z, fm.getSpeedKMH()), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(fm.getSpeedKMH(), 0.0F, 836.859F, 0.0F, 13F), speedometerScale), 0.0F);
        mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 10800F), 0.0F);
        mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)fm.Loc.z, 0.0F, 9144F, 0.0F, 1080F), 0.0F);
        mesh.chunkSetAngles("zHour", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay(), 0.0F, 24F, 0.0F, 720F), 0.0F);
        mesh.chunkSetAngles("zMinute", 0.0F, cvt(com.maddox.il2.ai.World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zSecond", 0.0F, cvt(((com.maddox.il2.ai.World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F, 360F), 0.0F);
        mesh.chunkSetAngles("zCompass1", 0.0F, fm.Or.getAzimut(), 0.0F);
        float f1 = 0.0F;
        com.maddox.il2.ai.WayPoint waypoint = fm.AP.way.curr();
        if(waypoint != null)
        {
            waypoint.getP(P1);
            V.sub(P1, fm.Loc);
            float f2 = (float)(57.295779513082323D * java.lang.Math.atan2(V.x, V.y));
            mesh.chunkSetAngles("zCompass2", 0.0F, 90F + f2, 0.0F);
        }
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

    private static final float speedometerScale[] = {
        0.0F, 2.5F, 54F, 104F, 154.5F, 205.5F, 224F, 242F, 259.5F, 277.5F, 
        296.25F, 314F, 334F, 344.5F
    };
    private static com.maddox.JGP.Point3d P1 = new Point3d();
    private static com.maddox.JGP.Vector3d V = new Vector3d();
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;

    static 
    {
        com.maddox.rts.Property.set(CockpitB17F_FGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(CockpitB17F_FGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(CockpitB17F_FGunner.class, "astatePilotIndx", 2);
    }
}
