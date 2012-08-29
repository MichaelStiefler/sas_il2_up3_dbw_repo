// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitDo217_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Do217, Do217_K1, Aircraft

public class CockpitDo217_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.objects.air.Do217)fm.actor).bPitUnfocused = false;
            aircraft().hierMesh().chunkVisible("Interior1_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_D0", false);
            aircraft().hierMesh().chunkVisible("Head1_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask1_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask2_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask3_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D0", false);
            aircraft().hierMesh().chunkVisible("Hmask4_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot1_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot2_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
            aircraft().hierMesh().chunkVisible("Pilot4_D1", false);
            aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret2A_D0", false);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
            if(aircraft() instanceof com.maddox.il2.objects.air.Do217_K1)
            {
                mesh.chunkVisible("k2-Box", false);
                mesh.chunkVisible("k2-Cable", false);
                mesh.chunkVisible("k2-cushion", false);
                mesh.chunkVisible("k2-FuG203", false);
                mesh.chunkVisible("k2-gunsight", false);
            } else
            {
                mesh.chunkVisible("StuviArm", false);
                mesh.chunkVisible("StuviPlate", false);
                mesh.chunkVisible("Revi_D0", false);
                mesh.chunkVisible("StuviHandle", false);
                mesh.chunkVisible("StuviLock", false);
            }
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
            ((com.maddox.il2.objects.air.Do217)fm.actor).bPitUnfocused = true;
            aircraft().hierMesh().chunkVisible("Interior1_D0", true);
            if(!fm.AS.isPilotParatrooper(0))
            {
                aircraft().hierMesh().chunkVisible("Pilot1_D0", !fm.AS.isPilotDead(0));
                aircraft().hierMesh().chunkVisible("Head1_D0", !fm.AS.isPilotDead(0));
                aircraft().hierMesh().chunkVisible("Pilot1_D1", fm.AS.isPilotDead(0));
            }
            if(!fm.AS.isPilotParatrooper(1))
            {
                aircraft().hierMesh().chunkVisible("Pilot2_D0", !fm.AS.isPilotDead(1));
                aircraft().hierMesh().chunkVisible("Pilot2_D1", fm.AS.isPilotDead(1));
            }
            if(!fm.AS.isPilotParatrooper(2))
            {
                aircraft().hierMesh().chunkVisible("Pilot3_D0", !fm.AS.isPilotDead(2));
                aircraft().hierMesh().chunkVisible("Pilot3_D1", fm.AS.isPilotDead(2));
            }
            if(!fm.AS.isPilotParatrooper(3))
            {
                aircraft().hierMesh().chunkVisible("Pilot4_D0", !fm.AS.isPilotDead(3));
                aircraft().hierMesh().chunkVisible("Pilot4_D1", fm.AS.isPilotDead(3));
            }
            aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret2A_D0", true);
            aircraft().hierMesh().chunkVisible("Turret2B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
            aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
            if(aircraft() instanceof com.maddox.il2.objects.air.Do217_K1)
            {
                mesh.chunkVisible("k2-Box", true);
                mesh.chunkVisible("k2-Cable", true);
                mesh.chunkVisible("k2-cushion", true);
                mesh.chunkVisible("k2-FuG203", true);
                mesh.chunkVisible("k2-gunsight", true);
            } else
            {
                mesh.chunkVisible("StuviArm", true);
                mesh.chunkVisible("StuviPlate", true);
                mesh.chunkVisible("Revi_D0", true);
                mesh.chunkVisible("StuviHandle", true);
                mesh.chunkVisible("StuviLock", true);
            }
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
        mesh.chunkSetAngles("Z_turret1A", 0.0F, -fm.turret[0].tu[0], 0.0F);
        mesh.chunkSetAngles("Z_turret1B", 0.0F, fm.turret[0].tu[1], 0.0F);
        mesh.chunkSetAngles("Z_turret3A", 0.0F, -fm.turret[2].tu[0], 0.0F);
        mesh.chunkSetAngles("Z_turret3B", 0.0F, fm.turret[2].tu[1], 0.0F);
        mesh.chunkSetAngles("Z_turret4A", 0.0F, -fm.turret[3].tu[0], 0.0F);
        mesh.chunkSetAngles("Z_turret4B", 0.0F, fm.turret[3].tu[1], 0.0F);
        mesh.chunkSetAngles("Z_turret5A", 0.0F, -fm.turret[4].tu[0], 0.0F);
        mesh.chunkSetAngles("Z_turret5B", 0.0F, fm.turret[4].tu[1], 0.0F);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("z_Turret2A", 0.0F, orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("z_Turret2B", 0.0F, orient.getTangage(), 0.0F);
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
        if(f1 > 80F)
            f1 = 80F;
        if(f1 < -3F)
            f1 = -3F;
        if(f > 155F || f < -155F)
        {
            if(f1 < 40F)
                f1 = 40F;
        } else
        if(f > 135F)
        {
            if(f1 < ((f - 135F) * 40F) / 20F)
                f1 = ((f - 135F) * 40F) / 20F;
        } else
        if(f < -135F)
        {
            if(f1 < ((-f - 135F) * 40F) / 20F)
                f1 = ((-f - 135F) * 40F) / 20F;
        } else
        if((f > 110F || f < -110F) && f1 < 0.0F)
            f1 = 0.0F;
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
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN02")));
            doHitMasterAircraft(aircraft(), hook1, "_MGUN02");
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
        } else
        {
            iCocking = 0;
        }
        if(emitter != null)
        {
            boolean flag = emitter.countBullets() % 2 == 0;
            mesh.chunkVisible("ZTurret2B-Bullet01", flag);
            mesh.chunkVisible("ZTurret2B-Bullet02", !flag);
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

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
        {
            mesh.chunkVisible("XGlassHoles1", true);
            mesh.chunkVisible("XGlassHoles3", true);
        }
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassHoles7", true);
            mesh.chunkVisible("XGlassHoles4", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassHoles5", true);
            mesh.chunkVisible("XGlassHoles3", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
            mesh.chunkVisible("XGlassHoles1", true);
        mesh.chunkVisible("XGlassHoles6", true);
        mesh.chunkVisible("XGlassHoles4", true);
        if((fm.AS.astateCockpitState & 1) != 0)
            mesh.chunkVisible("XGlassHoles6", true);
        if((fm.AS.astateCockpitState & 0x40) != 0)
        {
            mesh.chunkVisible("XGlassHoles7", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
        if((fm.AS.astateCockpitState & 0x80) != 0)
        {
            mesh.chunkVisible("XGlassHoles5", true);
            mesh.chunkVisible("XGlassHoles2", true);
        }
    }

    public CockpitDo217_TGunner()
    {
        super("3DO/Cockpit/Do217k1/hierTGun.him", "he111_gunner");
        firstEnter = true;
        w = new Vector3f();
        bNeedSetUp = true;
        hook1 = null;
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
    public com.maddox.JGP.Vector3f w;
    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;
    private float pictLever;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_TGunner.class)))), "aiTuretNum", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_TGunner.class)))), "weaponControlNum", 11);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitDo217_TGunner.class)))), "astatePilotIndx", 2);
    }
}