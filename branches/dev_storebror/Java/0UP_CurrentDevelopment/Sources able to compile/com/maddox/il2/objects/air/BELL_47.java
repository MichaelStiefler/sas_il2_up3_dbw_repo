package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class BELL_47 extends Scheme1
    implements TypeScout, TypeTransport
{

    public BELL_47()
    {
        suka = new Loc();
        pictVBrake = 0.0F;
        pictAileron = 0.0F;
        pictVator = 0.0F;
        pictRudder = 0.0F;
        pictBlister = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30Cal_MGR", false);
        if(getGunByHookName("_MGUN03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30Cal_MGL", false);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(super.FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected void moveAileron(float f)
    {
    }

    protected void moveElevator(float f)
    {
    }

    protected void moveFlap(float f)
    {
    }

    protected void moveRudder(float f)
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        BELL_47.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        Aircraft.xyz[2] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 0.5F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        float f = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.5F, 0.0F, 5F);
        hierMesh().chunkSetAngles("GearL2_D0", 0.0F, floatindex(f, gearL2), 0.0F);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, floatindex(f, gearL4), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, floatindex(f, gearL5), 0.0F);
        Aircraft.xyz[2] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 0.5F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
        f = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.5F, 0.0F, 5F);
        hierMesh().chunkSetAngles("GearR2_D0", 0.0F, -floatindex(f, gearL2), 0.0F);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, -floatindex(f, gearL4), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, -floatindex(f, gearL5), 0.0F);
    }

    private float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, (int)(1.0F + shot.mass * 18.95F * 2.0F));
        if(shot.chunkName.startsWith("WingRMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.121F) < shot.mass)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, (int)(1.0F + shot.mass * 18.95F * 2.0F));
        if(shot.chunkName.startsWith("Engine"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < shot.mass)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
            if(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).z > 0.0D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineDies(shot.initiator, 0);
                if(shot.mass > 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 5);
            }
            if(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x < 0.10000000149011612D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.57F)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
        }
        if(shot.chunkName.startsWith("Pilot1"))
        {
            killPilot(shot.initiator, 0);
            super.FM.setCapableOfBMP(false, shot.initiator);
            if(((com.maddox.JGP.Tuple3d) (Aircraft.Pd)).z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
        } else
        if(shot.chunkName.startsWith("Pilot2"))
        {
            killPilot(shot.initiator, 1);
            if(((com.maddox.JGP.Tuple3d) (Aircraft.Pd)).z > 0.5D && shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
        } else
        {
            if(shot.chunkName.startsWith("Turret"))
                super.FM.turret[0].bIsOperable = false;
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateEngineStates[0] == 4 && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
                super.FM.setCapableOfBMP(false, shot.initiator);
            super.msgShot(shot);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 34: // '"'
            return super.cutFM(35, j, actor);

        case 37: // '%'
            return super.cutFM(38, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void doKillPilot(int i)
    {
        if(i == 1)
            super.FM.turret[0].bIsOperable = false;
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("HMask1_D0", false);
            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("HMask2_D0", false);
            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }

    protected void moveFan(float f)
    {
        int i = 0;
        byte byte0 = 2;
        int j = 0;
        int k = 1;
        if(oldProp[j] < 2)
        {
            i = java.lang.Math.abs((int)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw() * 0.06F));
            if(i >= 1)
                i = 1;
            if(i != oldProp[j] && hierMesh().isChunkVisible(Props[j][oldProp[j]]))
            {
                hierMesh().chunkVisible(Props[0][oldProp[j]], false);
                oldProp[j] = i;
                hierMesh().chunkVisible(Props[j][i], true);
            }
        }
        if(i == 0)
        {
            super.propPos[j] = (super.propPos[j] + 57.3F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw() * f) % 360F;
        } else
        {
            float f1 = 57.3F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw();
            f1 %= 2880F;
            f1 /= 2880F;
            if(f1 <= 0.5F)
                f1 *= 2.0F;
            else
                f1 = f1 * 2.0F - 2.0F;
            f1 *= 1200F;
            super.propPos[j] = (super.propPos[j] + f1 * f) % 360F;
        }
        hierMesh().chunkSetAngles(Props[j][0], 0.0F, -super.propPos[j], 0.0F);
        if(oldProp[k] < 2)
        {
            i = java.lang.Math.abs((int)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw() * 0.1F));
            if(i >= 1)
                i = 1;
            if(i != oldProp[k] && hierMesh().isChunkVisible(Props[k][oldProp[k]]))
            {
                hierMesh().chunkVisible(Props[k][oldProp[k]], false);
                oldProp[k] = i;
                hierMesh().chunkVisible(Props[k][i], true);
            }
        }
        if(i == 0)
        {
            super.propPos[k] = (super.propPos[k] + 57.3F * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw() * 6F) * f) % 360F;
        } else
        {
            float f2 = 57.3F * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getw() * 6F);
            f2 %= 2880F;
            f2 /= 2880F;
            if(f2 <= 0.5F)
                f2 *= 2.0F;
            else
                f2 = f2 * 2.0F - 2.0F;
            f2 *= 1200F;
            super.propPos[k] = (super.propPos[k] + f2 * f) % 360F;
        }
        hierMesh().chunkSetAngles(Props[k][0], 0.0F, -super.propPos[k], 0.0F);
    }

    private void stability()
    {
        double d = 0.0D;
        if(super.FM.getSpeedKMH() > ((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS)
            d = (super.FM.getSpeedKMH() - ((com.maddox.il2.fm.FlightModelMain) (super.FM)).VmaxFLAPS) / 10F;
        com.maddox.JGP.Point3d point3d = new Point3d(0.0D, 0.0D, 0.0D);
        point3d.x = 0.0D - ((double)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getTangage() / 10F) - (double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getElevator() * 2.5D);
        point3d.y = (0.0D - ((double)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getKren() / 10F) - (double)((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAileron() * 2.5D)) + d / 3D;
        point3d.z = 2D;
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setPropPos(point3d);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).producedAF.x += 7000D * (double)(-((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getElevator() * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput());
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).producedAF.y += 6000D * (double)(-((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAileron() * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput());
    }

    public void update(float f)
    {
        stability();
        boolean flag = false;
        super.update(f);
        float f1 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAirBrake();
        f1 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAileron();
        if(java.lang.Math.abs(pictAileron - f1) > 0.01F)
        {
            pictAileron = f1;
            flag = true;
        }
        f1 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getRudder();
        if(java.lang.Math.abs(pictRudder - f1) > 0.01F)
        {
            pictRudder = f1;
            flag = true;
        }
        f1 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getElevator();
        if(java.lang.Math.abs(pictVator - f1) > 0.01F)
        {
            pictVator = f1;
            flag = true;
        }
        if(flag)
        {
            for(int i = 0; i < 9; i++)
            {
                float f3 = -60F * pictVBrake * (fcA[i] * pictAileron + fcE[i] * pictVator + fcR[i] * pictRudder);
                hierMesh().chunkSetAngles("Flap0" + (i + 1) + "B_D0", f3, 0.0F, 0.0F);
            }

            hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 45F * pictAileron, 0.0F);
            hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 45F * pictAileron, 0.0F);
            hierMesh().chunkSetAngles("Rudder1_D0", 20F * pictRudder, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("VatorL_D0", -20F * pictVator, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 20F * pictVator, 0.0F, 0.0F);
        }
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateBailoutStep > 1)
        {
            if(pictBlister < 1.0F)
                pictBlister += 3F * f;
            hierMesh().chunkSetAngles("Blister1_D0", -110F * pictBlister, 0.0F, 0.0F);
        }
        float f2 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.getPowerOutput() * Aircraft.cvt(super.FM.getSpeedKMH(), 0.0F, 600F, 2.0F, 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAirBrake() > 0.5F)
        {
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getTangage() > 5F)
            {
                super.FM.getW().scale(Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getTangage(), 45F, 90F, 1.0F, 0.1F));
                float f4 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getTangage();
                if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getKren()) > 90F)
                    f4 = 90F + (90F - f4);
                float f5 = f4 - 90F;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator = Aircraft.cvt(f5, -20F, 20F, 0.5F, -0.5F);
                f5 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.getKren();
                if(java.lang.Math.abs(f5) > 90F)
                    if(f5 > 0.0F)
                        f5 = 180F - f5;
                    else
                        f5 = -180F - f5;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimAileron = Aircraft.cvt(f5, -20F, 20F, 0.5F, -0.5F);
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimRudder = Aircraft.cvt(f5, -15F, 15F, 0.04F, -0.04F);
            }
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimAileron = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimRudder = 0.0F;
        }
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Or.increment(f2 * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getRudder() + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getTrimRudderControl()), f2 * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getElevator() + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getTrimElevatorControl()), f2 * (((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getAileron() + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getTrimAileronControl()));
    }

    public static boolean bChangedPit = false;
    public com.maddox.il2.engine.Loc suka;
    private float pictVBrake;
    private float pictAileron;
    private float pictVator;
    private float pictRudder;
    private float pictBlister;
    private static final float fcA[] = {
        0.0F, 0.04F, 0.1F, 0.04F, 0.02F, -0.02F, -0.04F, -0.1F, -0.04F
    };
    private static final float fcE[] = {
        0.98F, 0.48F, 0.1F, -0.48F, -0.7F, -0.7F, -0.48F, 0.1F, 0.48F
    };
    private static final float fcR[] = {
        0.02F, 0.48F, 0.8F, 0.48F, 0.28F, -0.28F, -0.48F, -0.8F, -0.48F
    };
    private float deltaAzimuth;
    private float deltaTangage;
    private static final float gearL2[] = {
        0.0F, 1.0F, 2.0F, 2.9F, 3.2F, 3.35F
    };
    private static final float gearL4[] = {
        0.0F, 7.5F, 15F, 22F, 29F, 35.5F
    };
    private static final float gearL5[] = {
        0.0F, 1.5F, 4F, 7.5F, 10F, 11.5F
    };
    protected int oldProp[] = {
        0, 0, 0, 0, 0, 0
    };
    protected static final java.lang.String Props[][] = {
        {
            "Prop1_D0", "PropRot1_D0", "Prop1_D1"
        }, {
            "Prop2_D0", "PropRot2_D0", "Prop2_D1"
        }, {
            "Prop3_D0", "PropRot3_D0", "Prop3_D1"
        }, {
            "Prop4_D0", "PropRot4_D0", "Prop4_D1"
        }, {
            "Prop5_D0", "PropRot5_D0", "Prop5_D1"
        }, {
            "Prop6_D0", "PropRot6_D0", "Prop6_D1"
        }
    };

    static 
    {
        java.lang.Class class1 = BELL_47.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bell47");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Bell47/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1935F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1960F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bell47.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBELL_47.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
