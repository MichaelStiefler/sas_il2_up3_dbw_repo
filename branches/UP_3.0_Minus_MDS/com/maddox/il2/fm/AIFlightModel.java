// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AIFlightModel.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            FlightModel, FlightModelMain, Controls, Polares, 
//            EnginesInterface, Mass, Atmosphere, FMMath, 
//            AircraftState, Squares, Gear, Wind, 
//            Supersonic, Arm

public class AIFlightModel extends com.maddox.il2.fm.FlightModel
{

    public AIFlightModel(java.lang.String s)
    {
        super(s);
        callSuperUpdate = true;
        dataDrawn = false;
        Cw = new Vector3d();
        Fw = new Vector3d();
        Wtrue = new Vector3d();
        TmpP = new Point3d();
        Vn = new Vector3d();
        TmpV = new Vector3d();
        TmpVd = new Vector3d();
        L = new Loc();
        w = com.maddox.rts.Time.current();
    }

    private void flutter()
    {
        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "CF_D0", "CF_D0");
    }

    public com.maddox.JGP.Vector3d getW()
    {
        return Wtrue;
    }

    public void update(float f)
    {
        ((com.maddox.il2.fm.FlightModelMain)this).CT.update(f, (((com.maddox.il2.fm.FlightModel)this).getSpeed() + 50F) * 0.5F, ((com.maddox.il2.fm.FlightModelMain)this).EI, false);
        ((com.maddox.il2.fm.FlightModelMain)this).Wing.setFlaps(((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap());
        ((com.maddox.il2.fm.FlightModelMain)this).EI.update(f);
        super.update(f);
        ((com.maddox.il2.fm.FlightModelMain)this).Gravity = ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass() * com.maddox.il2.fm.Atmosphere.g();
        ((com.maddox.il2.fm.FlightModelMain)this).M.computeFullJ(((com.maddox.il2.fm.FlightModelMain)this).J, ((com.maddox.il2.fm.FlightModelMain)this).J0);
        if(((com.maddox.il2.fm.FMMath)this).isTick(44, 0))
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AS.update(f * 44F);
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).rareAction(f * 44F, true);
            ((com.maddox.il2.fm.FlightModelMain)this).M.computeParasiteMass(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons);
            ((com.maddox.il2.fm.FlightModelMain)this).Sq.computeParasiteDrag(((com.maddox.il2.fm.FlightModelMain)this).CT, ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons);
            if(((com.maddox.il2.ai.air.Maneuver) ((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM)).get_task() == 7 || ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM).get_maneuver() == 43)
                ((com.maddox.il2.fm.FlightModel)this).putScareShpere();
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 25)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor;
                if(aircraft.aircIndex() == 0 && !(aircraft instanceof com.maddox.il2.objects.air.TypeFighter))
                {
                    int i = ((com.maddox.il2.engine.Interpolate)this).actor.getArmy() - 1 & 1;
                    int j = (int)(com.maddox.rts.Time.current() / 60000L);
                    if(j > com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack[i] || j > com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack1[i] || j > com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack2[i])
                    {
                        com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.War.getNearestEnemy(aircraft, 6000F);
                        if(aircraft1 != null && ((aircraft1 instanceof com.maddox.il2.objects.air.TypeFighter) || (aircraft1 instanceof com.maddox.il2.objects.air.TypeStormovik)))
                            com.maddox.il2.objects.sounds.Voice.speakBombersUnderAttack(aircraft, false);
                    }
                    if(j > com.maddox.il2.objects.sounds.Voice.cur().SpeakNearBombers[i])
                    {
                        com.maddox.il2.objects.air.Aircraft aircraft2 = com.maddox.il2.ai.War.getNearestFriendlyFighter(aircraft, 4000F);
                        if(aircraft2 != null)
                            com.maddox.il2.objects.sounds.Voice.speakNearBombers(aircraft);
                    }
                }
            }
        }
        if(com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && com.maddox.rts.Time.current() > w + 50L)
            com.maddox.il2.ai.World.wind().getVectorAI(((com.maddox.il2.fm.FlightModelMain)this).Loc, ((com.maddox.il2.fm.FlightModelMain)this).Vwind);
        else
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwind)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vair)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwind)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vair)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        Density = com.maddox.il2.fm.Atmosphere.density((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
        ((com.maddox.il2.fm.FlightModelMain)this).AOA = com.maddox.il2.fm.FMMath.RAD2DEG(-(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x));
        ((com.maddox.il2.fm.FlightModelMain)this).AOS = com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).y, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x));
        ((com.maddox.il2.fm.FlightModelMain)this).V2 = (float)((com.maddox.il2.fm.FlightModelMain)this).Vflow.lengthSquared();
        ((com.maddox.il2.fm.FlightModelMain)this).V = (float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain)this).V2);
        ((com.maddox.il2.fm.FlightModelMain)this).Mach = ((com.maddox.il2.fm.FlightModelMain)this).V / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
        if(((com.maddox.il2.fm.FlightModelMain)this).Ss.allParamsSet)
            Density *= ((com.maddox.il2.fm.FlightModelMain)this).Ss.getDragFactorForMach(((com.maddox.il2.fm.FlightModelMain)this).Mach);
        else
            Density *= ((com.maddox.il2.fm.FlightModelMain)this).Ss.getDragFactorForMach(((com.maddox.il2.fm.FlightModelMain)this).Mach);
        if(((com.maddox.il2.fm.FlightModelMain)this).Mach > 0.8F)
            ((com.maddox.il2.fm.FlightModelMain)this).Mach = 0.8F;
        Kq = 1.0F / (float)java.lang.Math.sqrt(1.0F - ((com.maddox.il2.fm.FlightModelMain)this).Mach * ((com.maddox.il2.fm.FlightModelMain)this).Mach);
        ((com.maddox.il2.fm.FlightModelMain)this).q_ = Density * ((com.maddox.il2.fm.FlightModelMain)this).V2 * 0.5F;
        if(!callSuperUpdate)
            return;
        double d = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - ((com.maddox.il2.fm.FlightModelMain)this).Gears.screenHQ;
        if(d < 0.0D)
            d = 0.0D;
        Cw.x = -((com.maddox.il2.fm.FlightModelMain)this).q_ * (((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cx(((com.maddox.il2.fm.FlightModelMain)this).AOA) + 2.0F * ((com.maddox.il2.fm.FlightModelMain)this).GearCX * ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() + 2.0F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragAirbrakeCx * ((com.maddox.il2.fm.FlightModelMain)this).CT.getAirBrake());
        Cw.z = ((com.maddox.il2.fm.FlightModelMain)this).q_ * ((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cy(((com.maddox.il2.fm.FlightModelMain)this).AOA) * Kq;
        if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType != 0 && ((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 1)
            Cw.z *= com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain)this).fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
        if(d < 0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length)
        {
            double d1 = 1.0D - d / (0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length);
            double d2 = 1.0D + 0.29999999999999999D * d1;
            double d4 = 1.0D + 0.29999999999999999D * d1;
            Cw.z *= d2;
            Cw.x *= d4;
        }
        Cw.y = -((com.maddox.il2.fm.FlightModelMain)this).q_ * ((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cz(((com.maddox.il2.fm.FlightModelMain)this).AOS);
        for(int k = 0; k < ((com.maddox.il2.fm.FlightModelMain)this).EI.getNum(); k++)
            Cw.x += -((com.maddox.il2.fm.FlightModelMain)this).q_ * (0.8F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragEngineCx[k]);

        ((com.maddox.JGP.Tuple3d) (Fw)).scale(((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut, ((com.maddox.JGP.Tuple3d) (Cw)));
        Fw.x -= ((com.maddox.il2.fm.FlightModelMain)this).q_ * (((com.maddox.il2.fm.FlightModelMain)this).Sq.dragParasiteCx + ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragProducedCx);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).set(((com.maddox.JGP.Tuple3d) (Fw)));
        ((com.maddox.il2.fm.FlightModelMain)this).AF.y -= ((com.maddox.il2.fm.FlightModelMain)this).AOS * ((com.maddox.il2.fm.FlightModelMain)this).q_ * 0.1F;
        if(com.maddox.il2.fm.FMMath.isNAN(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF))))
        {
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).set(0.0D, 0.0D, 0.0D);
            flutter();
        } else
        if(((com.maddox.il2.fm.FlightModelMain)this).AF.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 50F))
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AF.normalize();
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).scale(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 50F);
            flutter();
        }
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (Wtrue)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.il2.fm.FlightModelMain)this).AM.x = (double)((((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLIn * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_ROOT + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLMid * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END) - ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRIn * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_ROOT - ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRMid * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE - ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END) * ((com.maddox.JGP.Tuple3d) (Cw)).z;
        if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType != 0)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 2)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AM.x = (double)(-((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRIn * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_ROOT - ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRMid * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE - ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END) * ((com.maddox.JGP.Tuple3d) (Cw)).z;
                if(com.maddox.rts.Time.current() >= ((com.maddox.il2.fm.FlightModelMain)this).fmsfxTimeDisable)
                    ((com.maddox.il2.fm.FlightModelMain)this).doRequestFMSFX(0, 0);
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 3)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AM.x = (double)(((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLIn * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_ROOT + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLMid * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut * ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END) * ((com.maddox.JGP.Tuple3d) (Cw)).z;
                if(com.maddox.rts.Time.current() >= ((com.maddox.il2.fm.FlightModelMain)this).fmsfxTimeDisable)
                    ((com.maddox.il2.fm.FlightModelMain)this).doRequestFMSFX(0, 0);
            }
        }
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA) > 33F)
            ((com.maddox.il2.fm.FlightModelMain)this).AM.y = 1.0F * (((com.maddox.il2.fm.FlightModelMain)this).Sq.liftStab * ((com.maddox.il2.fm.FlightModelMain)this).Arms.HOR_STAB) * (((com.maddox.il2.fm.FlightModelMain)this).q_ * ((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cy(((com.maddox.il2.fm.FlightModelMain)this).AOA) * Kq);
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOS) > 33F)
            ((com.maddox.il2.fm.FlightModelMain)this).AM.z = 1.0F * ((0.2F + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftKeel) * ((com.maddox.il2.fm.FlightModelMain)this).Arms.VER_STAB) * (((com.maddox.il2.fm.FlightModelMain)this).q_ * ((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cy(((com.maddox.il2.fm.FlightModelMain)this).AOS) * Kq);
        float f1 = ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut;
        float f2 = ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut;
        float f3 = (float)((com.maddox.il2.fm.FlightModelMain)this).Vflow.lengthSquared() - 120F;
        if(f3 < 0.0F)
            f3 = 0.0F;
        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x < 0.0D)
            f3 = 0.0F;
        if(f3 > 15000F)
            f3 = 15000F;
        if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM).get_maneuver() != 20)
        {
            float f4 = f1 - f2;
            if(!((com.maddox.il2.fm.FlightModelMain)this).getOp(19) && d > 20D && f3 > 10F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AM.y += (double)(5F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x;
                ((com.maddox.il2.fm.FlightModelMain)this).AM.z += 80F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * ((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign();
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 20F || ((com.maddox.il2.fm.FlightModelMain)this).AOA < -20F)
                {
                    float f6 = 1.0F;
                    if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z < 0.0D)
                        f6 = -1F;
                    ((com.maddox.il2.fm.FlightModelMain)this).AM.z += (double)(30F * f6 * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * (3D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x);
                    ((com.maddox.il2.fm.FlightModelMain)this).AM.x -= (double)(50F * f6 * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * (((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z + 3D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x);
                }
            } else
            {
                if(!((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
                {
                    float f7 = ((com.maddox.il2.fm.FlightModelMain)this).AOA * 3F;
                    if(f7 > 25F)
                        f7 = 25F;
                    if(f7 < -25F)
                        f7 = -25F;
                    if(!((com.maddox.il2.fm.FlightModelMain)this).getOp(34))
                        ((com.maddox.il2.fm.FlightModelMain)this).AM.x -= f7 * f2 * f3;
                    else
                    if(!((com.maddox.il2.fm.FlightModelMain)this).getOp(37))
                        ((com.maddox.il2.fm.FlightModelMain)this).AM.x += f7 * f1 * f3;
                    else
                    if(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM).get_maneuver() == 44 && (((com.maddox.il2.fm.FlightModelMain)this).AOA > 15F || ((com.maddox.il2.fm.FlightModelMain)this).AOA < -12F))
                    {
                        if(f4 > 0.0F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z > 0.0D)
                            ((com.maddox.il2.fm.FlightModelMain)this).W.z = -9.9999997473787516E-005D;
                        if(f4 < 0.0F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z < 0.0D)
                            ((com.maddox.il2.fm.FlightModelMain)this).W.z = 9.9999997473787516E-005D;
                        if(f3 > 1000F)
                            f3 = 1000F;
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z < 0.0D)
                        {
                            ((com.maddox.il2.fm.FlightModelMain)this).AM.z -= 3F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                            if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 0.0F)
                                ((com.maddox.il2.fm.FlightModelMain)this).AM.x += 40F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                            else
                                ((com.maddox.il2.fm.FlightModelMain)this).AM.x -= 40F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain)this).AM.z += 3F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                            if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 0.0F)
                                ((com.maddox.il2.fm.FlightModelMain)this).AM.x -= 40F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                            else
                                ((com.maddox.il2.fm.FlightModelMain)this).AM.x += 40F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * f3;
                        }
                    }
                }
                if(((com.maddox.il2.fm.FlightModelMain)this).Sq.liftKeel > 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain)this).AM.z += ((com.maddox.il2.fm.FlightModelMain)this).AOS * ((com.maddox.il2.fm.FlightModelMain)this).q_ * 0.5F;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).AM.x += ((com.maddox.il2.fm.FlightModelMain)this).AOS * ((com.maddox.il2.fm.FlightModelMain)this).q_ * 1.0F;
                double d5 = 1.0D;
                if(d < 1.5D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length)
                {
                    d5 += (d - 1.5D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length) / (double)((com.maddox.il2.fm.FlightModelMain)this).Length;
                    if(d5 < 0.0D)
                        d5 = 0.0D;
                }
                if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x < 20D && java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOS) < 33F)
                    ((com.maddox.il2.fm.FlightModelMain)this).AM.y += d5 * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).z;
                float f8 = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x;
                if(f8 > 150F)
                    f8 = 150F;
                float f9 = ((com.maddox.il2.fm.FlightModelMain)this).SensYaw;
                if(f9 > 0.2F)
                    f9 = 0.2F;
                float f10 = ((com.maddox.il2.fm.FlightModelMain)this).SensRoll;
                if(f10 > 4F)
                    f10 = 4F;
                double d7 = 20D - (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA);
                if(d7 < (double)((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff)
                    d7 = ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff;
                double d9 = (double)((com.maddox.il2.fm.FlightModelMain)this).AOA - (double)((com.maddox.il2.fm.FlightModelMain)this).CT.getElevator() * d7;
                double d10 = 0.017000000000000001D * (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA);
                if(d10 < 1.0D)
                    d10 = 1.0D;
                if(d9 > 90D)
                    d9 = -(180D - d9);
                if(d9 < -90D)
                    d9 = 180D - d9;
                d9 *= d10;
                double d11 = 12D - (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOS);
                if(d11 < 0.0D)
                    d11 = 0.0D;
                double d12 = (double)((com.maddox.il2.fm.FlightModelMain)this).AOS - (double)((com.maddox.il2.fm.FlightModelMain)this).CT.getRudder() * d11;
                double d13 = 0.01D * (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOS);
                if(d13 < 1.0D)
                    d13 = 1.0D;
                if(d12 > 90D)
                    d12 = -(180D - d12);
                if(d12 < -90D)
                    d12 = 180D - d12;
                d12 *= d13;
                float f13 = ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing;
                if(f13 < 1.0F)
                    f13 = 1.0F;
                f13 = 1.0F / f13;
                Wtrue.x = ((com.maddox.il2.fm.FlightModelMain)this).CT.getAileron() * f8 * f10 * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareAilerons * f13 * 1.0F;
                Wtrue.y = d9 * (double)f8 * (double)((com.maddox.il2.fm.FlightModelMain)this).SensPitch * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.squareElevators * (double)f13 * 0.012000000104308128D;
                Wtrue.z = d12 * (double)f8 * (double)f9 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.squareRudders * (double)f13 * 0.15000000596046448D;
            }
        } else
        {
            float f5 = 1.0F;
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z < 0.0D)
                f5 = -1F;
            ((com.maddox.il2.fm.FlightModelMain)this).AM.z += (double)(30F * f5 * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * (3D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x);
            ((com.maddox.il2.fm.FlightModelMain)this).AM.x -= (double)(50F * f5 * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * (((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z + 3D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x);
        }
        if(((com.maddox.il2.fm.FlightModelMain)this).Sq.squareElevators < 0.1F && d > 20D && f3 > 10F)
            ((com.maddox.il2.fm.FlightModelMain)this).AM.y += ((com.maddox.il2.fm.FlightModelMain)this).Gravity * 0.4F;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAM)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAM)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAF)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).EI.producedF)));
        if(((com.maddox.il2.fm.FlightModelMain)this).W.lengthSquared() > 36D)
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).scale(6D / ((com.maddox.il2.fm.FlightModelMain)this).W.length());
        double d3 = 0.10000000000000001D + (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing;
        if(d3 < 1.0D)
            d3 = 1.0D;
        d3 = 1.0D / d3;
        ((com.maddox.il2.fm.FlightModelMain)this).W.x *= 1.0D - 0.12D * (0.20000000000000001D + (double)f1 + (double)f2) * d3;
        ((com.maddox.il2.fm.FlightModelMain)this).W.y *= 1.0D - 0.5D * (0.20000000000000001D + (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.liftStab) * d3;
        ((com.maddox.il2.fm.FlightModelMain)this).W.z *= 1.0D - 0.5D * (0.20000000000000001D + (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.liftKeel) * d3;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.il2.fm.FlightModelMain)this).Gears.roughness = 0.5D;
        ((com.maddox.il2.fm.FlightModelMain)this).Gears.ground(((com.maddox.il2.fm.FlightModel) (this)), true);
        ((com.maddox.il2.fm.FlightModelMain)this).GM.x *= 0.10000000000000001D;
        ((com.maddox.il2.fm.FlightModelMain)this).GM.y *= 0.40000000000000002D;
        ((com.maddox.il2.fm.FlightModelMain)this).GM.z *= 0.80000000000000004D;
        int l = 2;
        if(((com.maddox.il2.fm.FlightModelMain)this).GF.lengthSquared() == 0.0D && ((com.maddox.il2.fm.FlightModelMain)this).GM.lengthSquared() == 0.0D || super.brakeShoe)
            l = 1;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).ACmeter)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).ACmeter)).scale(1.0F / ((com.maddox.il2.fm.FlightModelMain)this).Gravity);
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(0.0D, 0.0D, -((com.maddox.il2.fm.FlightModelMain)this).Gravity);
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)));
        double d6 = 1.0D / (double)((com.maddox.il2.fm.FlightModelMain)this).M.mass;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).LocalAccel)).scale(d6, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
        if(com.maddox.il2.fm.FMMath.isNAN(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM))))
        {
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).set(0.0D, 0.0D, 0.0D);
            flutter();
        } else
        if(((com.maddox.il2.fm.FlightModelMain)this).AM.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 100F))
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AM.normalize();
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).scale(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 100F);
            flutter();
        }
        super.dryFriction -= 0.01D;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.gearsChanged)
            super.dryFriction = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfPoiOnGr > 0)
            super.dryFriction += 0.02F;
        if(super.dryFriction < 1.0F)
            super.dryFriction = 1.0F;
        if(super.dryFriction > 32F)
            super.dryFriction = 32F;
        float f11 = 4F * (0.25F - ((com.maddox.il2.fm.FlightModelMain)this).EI.getPowerOutput());
        if(f11 < 0.0F)
            f11 = 0.0F;
        f11 *= f11;
        f11 *= super.dryFriction;
        float f12 = f11 * ((com.maddox.il2.fm.FlightModelMain)this).M.mass * ((com.maddox.il2.fm.FlightModelMain)this).M.mass;
        if(!super.brakeShoe && (((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfPoiOnGr == 0 && ((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfGearsOnGr < 3 || f11 == 0.0F || ((com.maddox.il2.fm.FlightModelMain)this).SummM.lengthSquared() > (double)(2.0F * f12) || ((com.maddox.il2.fm.FlightModelMain)this).SummF.lengthSquared() > (double)(80F * f12) || ((com.maddox.il2.fm.FlightModelMain)this).W.lengthSquared() > (double)(0.00014F * f11) || ((com.maddox.il2.fm.FlightModelMain)this).Vwld.lengthSquared() > (double)(0.09F * f11)))
        {
            double d8 = 1.0D / (double)l;
            for(int i1 = 0; i1 < l; i1++)
            {
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)));
                ((com.maddox.il2.fm.FlightModelMain)this).AW.x = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).x) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x;
                ((com.maddox.il2.fm.FlightModelMain)this).AW.y = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).y) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y;
                ((com.maddox.il2.fm.FlightModelMain)this).AW.z = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).z) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z;
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d8 * (double)f, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AW)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)), ((com.maddox.JGP.Tuple3d) (Vn)));
                ((com.maddox.JGP.Tuple3d) (Wtrue)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d8 * (double)f, ((com.maddox.JGP.Tuple3d) (Wtrue)));
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment((float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).z)), (float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).y)), (float)com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).x));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vn)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d6, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Accel)).set(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d8 * (double)f);
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d8 * (double)f, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                ((com.maddox.JGP.Tuple3d) (TmpP)).set(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).add(((com.maddox.JGP.Tuple3d) (TmpP)));
                boolean flag;
                if(com.maddox.il2.fm.FMMath.isNAN(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc))))
                    flag = false;
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).set(0.0D, 0.0D, 0.0D);
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).set(0.0D, 0.0D, 0.0D);
                if(i1 < l - 1)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).Gears.ground(((com.maddox.il2.fm.FlightModel) (this)), true);
                    ((com.maddox.il2.fm.FlightModelMain)this).GM.x *= 0.10000000000000001D;
                    ((com.maddox.il2.fm.FlightModelMain)this).GM.y *= 0.40000000000000002D;
                    ((com.maddox.il2.fm.FlightModelMain)this).GM.z *= 0.80000000000000004D;
                    ((com.maddox.JGP.Tuple3d) (TmpV)).set(0.0D, 0.0D, -((com.maddox.il2.fm.FlightModelMain)this).Gravity);
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpV)));
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                }
            }

            for(int j1 = 0; j1 < 3; j1++)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[j1] = (((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[j1] + (float)java.lang.Math.toDegrees(java.lang.Math.atan((((com.maddox.il2.fm.FlightModelMain)this).Gears.gVelocity[j1] * (double)f) / 0.375D))) % 360F;
                ((com.maddox.il2.fm.FlightModelMain)this).Gears.gVelocity[j1] *= 0.94999998807907104D;
            }

            super.HM.chunkSetAngles("GearL1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[0], 0.0F);
            super.HM.chunkSetAngles("GearR1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[1], 0.0F);
            super.HM.chunkSetAngles("GearC1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[2], 0.0F);
        }
    }

    private long w;
    public float Density;
    public float Kq;
    protected boolean callSuperUpdate;
    protected boolean dataDrawn;
    com.maddox.JGP.Vector3d Cw;
    com.maddox.JGP.Vector3d Fw;
    protected com.maddox.JGP.Vector3d Wtrue;
    private com.maddox.JGP.Point3d TmpP;
    private com.maddox.JGP.Vector3d Vn;
    private com.maddox.JGP.Vector3d TmpV;
    private com.maddox.JGP.Vector3d TmpVd;
    private com.maddox.il2.engine.Loc L;
}
