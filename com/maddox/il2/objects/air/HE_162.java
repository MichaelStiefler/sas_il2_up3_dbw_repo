// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_162.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, Aircraft, NetAircraft, 
//            PaintScheme, EjectionSeat

public abstract class HE_162 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public HE_162()
    {
        oldctl = -1F;
        curctl = -1F;
    }

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)obj;
                if(!com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    return;
                } else
                {
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_ExternalSeat01");
                    aircraft.pos.getAbs(loc1);
                    hooknamed.computePos(aircraft, loc1, loc);
                    loc.transform(vector3d);
                    vector3d.x += aircraft.FM.Vwld.x;
                    vector3d.y += aircraft.FM.Vwld.y;
                    vector3d.z += aircraft.FM.Vwld.z;
                    new EjectionSeat(1, loc, vector3d, aircraft);
                    return;
                }
            }

        }
;
        hierMesh().chunkVisible("Seat_D0", false);
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            if(!FM.AS.bIsAboutToBailout)
            {
                if(hierMesh().isChunkVisible("Blister1_D0"))
                    hierMesh().chunkVisible("Gore1_D0", true);
                hierMesh().chunkVisible("Gore2_D0", true);
            }
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.14F)
                FM.AS.hitTank(this, 0, 1);
        }
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -110F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 115F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 110F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 115F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -110F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC25_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC27_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC28_D0", 0.0F, 0.0F, 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.0F, -0.0833F, 0.0F);
        hiermesh.chunkSetLocate("GearC64_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.HE_162.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
        if(FM.CT.getGear() > 0.99F)
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 40F * FM.CT.getRudder();
        hierMesh().chunkSetLocate("GearC25_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearC27_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15F), 0.0F);
        hierMesh().chunkSetAngles("GearC28_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30F), 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    protected void moveFan(float f)
    {
    }

    public void update(float f)
    {
        if(FM.AS.isMaster())
            if(curctl == -1F)
            {
                curctl = oldctl = FM.EI.engines[0].getControlThrottle();
            } else
            {
                curctl = FM.EI.engines[0].getControlThrottle();
                if((curctl - oldctl) / f > 3F && FM.EI.engines[0].getRPM() < 2400F && FM.EI.engines[0].getStage() == 6 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.hitEngine(this, 0, 100);
                if((curctl - oldctl) / f < -3F && FM.EI.engines[0].getRPM() < 2400F && FM.EI.engines[0].getStage() == 6)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && (FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                        FM.EI.engines[0].setEngineStops(this);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F && (FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                        FM.EI.engines[0].setKillCompressor(this);
                }
                oldctl = curctl;
            }
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && FM.AS.isMaster())
            if(FM.EI.engines[0].getPowerOutput() > 0.8F && FM.EI.engines[0].getStage() == 6)
            {
                if(FM.EI.engines[0].getPowerOutput() > 0.95F)
                    FM.AS.setSootState(this, 0, 3);
                else
                    FM.AS.setSootState(this, 0, 2);
            } else
            {
                FM.AS.setSootState(this, 0, 0);
            }
        super.update(f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("Pilot"))
            killPilot(shot.initiator, 0);
        if(shot.chunkName.startsWith("Engine"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, 6));
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass)
                FM.AS.hitEngine(shot.initiator, 0, 5);
        }
        if(shot.chunkName.startsWith("CF"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 0, 1);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
            }
        }
        super.msgShot(shot);
    }

    private float oldctl;
    private float curctl;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
