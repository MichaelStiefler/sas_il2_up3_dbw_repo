// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitBF_110Early_Gunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitBF_110Early_Gunner extends com.maddox.il2.objects.air.CockpitGunner
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            fm = com.maddox.il2.ai.World.getPlayerFM();
            if(fm == null)
                return true;
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
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
            aircraft().hierMesh().chunkVisible("Wire_D0", false);
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            aircraft().hierMesh().chunkVisible("Nose_D0", false);
            aircraft().hierMesh().chunkVisible("Blister1_D0", false);
            aircraft().hierMesh().chunkVisible("Blister2_D0", false);
            aircraft().hierMesh().chunkVisible("Blister3_D0", false);
            aircraft().hierMesh().chunkVisible("Blister4_D0", false);
            aircraft().hierMesh().chunkVisible("Blister5_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("Wire_D0", true);
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        aircraft().hierMesh().chunkVisible("Nose_D0", true);
        aircraft().hierMesh().chunkVisible("Blister1_D0", true);
        aircraft().hierMesh().chunkVisible("Blister2_D0", true);
        aircraft().hierMesh().chunkVisible("Blister3_D0", true);
        aircraft().hierMesh().chunkVisible("Blister4_D0", true);
        aircraft().hierMesh().chunkVisible("Blister5_D0", true);
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("TurretA", -f, 0.0F, 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -f1 + -15F);
        mesh.chunkSetAngles("TurretC", 0.0F, -com.maddox.il2.fm.FMMath.clamp(f, -cvt(f1, -19F, 12F, 5F, 35F), cvt(f1, -19F, 12F, 5F, 35F)), 0.0F);
        mesh.chunkSetAngles("TurretD", 0.0F, f1, 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
            if(!aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = -orient.getYaw();
                float f1 = orient.getTangage();
                if(f1 < -19F)
                    f1 = -19F;
                if(f1 > 30F)
                    f1 = 30F;
                float f2;
                if(f1 < 0.0F)
                    f2 = cvt(f1, -19F, 0.0F, 20F, 30F);
                else
                if(f1 < 12F)
                    f2 = cvt(f1, 0.0F, 12F, 30F, 35F);
                else
                    f2 = cvt(f1, 12F, 30F, 35F, 40F);
                if(f < 0.0F)
                {
                    if(f < -f2)
                        f = -f2;
                } else
                if(f > f2)
                    f = f2;
                orient.setYPR(-f, f1, 0.0F);
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
            com.maddox.il2.engine.Orient orient = hookGunner().getGunMove();
            float f = orient.getYaw();
            float f1 = orient.getTangage();
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = new HookNamed(aircraft(), "_MGUN05");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN05");
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

    public CockpitBF_110Early_Gunner()
    {
        super("3DO/Cockpit/Bf-110FAM-Gun/hier.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        mesh.materialReplace("Gloss2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot1"));
        mesh.materialReplace("Pilot1", mat);
    }

    public void reflectWorldToInstruments(float f)
    {
        if(fm != null)
        {
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            mesh.chunkVisible("Head1_D0", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
            mesh.chunkVisible("Head1_D1", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
        }
    }

    private boolean bNeedSetUp;
    private static final float scalePatronsR[][] = {
        {
            0.0F, 0.0F, 0.0F
        }, {
            0.02F, 0.0F, 0.018F
        }, {
            0.061F, 0.044F, 0.061F
        }, {
            0.083F, 0.069F, 0.083F
        }
    };
    private static final float scalePatronsL[][] = {
        {
            0.0F, 0.0F, 0.0F
        }, {
            0.02F, 0.0F, 0.18F
        }, {
            0.061F, 0.044F, 0.061F
        }, {
            0.083F, 0.069F, 0.083F
        }
    };
    private static final float scalePatronsR1[][] = {
        {
            5.5F, 2.0F, -2.5F
        }, {
            13.5F, 0.0F, -1.5F
        }, {
            12F, 0.0F, -1F
        }, {
            15F, 4F, 2.0F
        }
    };
    private static final float scalePatronsR2[][] = {
        {
            4F, 0.0F, -3F
        }, {
            4.5F, 0.0F, -3.5F
        }, {
            9F, 0.5F, -3.5F
        }, {
            10F, 0.0F, -4.5F
        }
    };
    private static final float scalePatronsL1[][] = {
        {
            -4.5F, 2.0F, 4F
        }, {
            -4.5F, 0.0F, 9F
        }, {
            -3F, 0.0F, 10.5F
        }, {
            -3F, 4F, 15F
        }
    };
    private static final float scalePatronsL2[][] = {
        {
            0.0F, 0.0F, 3F
        }, {
            -2F, 0.0F, 9F
        }, {
            -1F, 0.0F, 2.5F
        }, {
            -4F, 0.0F, 8F
        }
    };
    private static final float scaleHylse1[][] = {
        {
            6F, 7F, 6F
        }, {
            0.0F, 0.0F, 0.0F
        }, {
            -8F, 0.0F, -8F
        }, {
            -17F, 0.0F, -17F
        }
    };
    private static final float scaleHylse2[][] = {
        {
            -8F, 0.0F, 8F
        }, {
            -7F, 0.0F, 7F
        }, {
            -8F, 0.0F, 8F
        }, {
            -1F, 0.0F, 1.0F
        }
    };
    private com.maddox.il2.engine.Hook hook1;



}
