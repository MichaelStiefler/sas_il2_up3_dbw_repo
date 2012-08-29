// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AIFlightModel.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            FlightModel, Controls, Polares, EnginesInterface, 
//            Mass, Atmosphere, AircraftState, Squares, 
//            Gear, Wind, Arm

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
    }

    private void flutter()
    {
        ((com.maddox.il2.objects.air.Aircraft)actor).msgCollision(actor, "CF_D0", "CF_D0");
    }

    public com.maddox.JGP.Vector3d getW()
    {
        return Wtrue;
    }

    public void update(float f)
    {
        CT.update(f, (getSpeed() + 50F) * 0.5F, EI, false);
        Wing.setFlaps(CT.getFlap());
        EI.update(f);
        super.update(f);
        Gravity = M.getFullMass() * com.maddox.il2.fm.Atmosphere.g();
        M.computeFullJ(J, J0);
        if(isTick(44, 0))
        {
            AS.update(f * 44F);
            ((com.maddox.il2.objects.air.Aircraft)actor).rareAction(f * 44F, true);
            M.computeParasiteMass(CT.Weapons);
            Sq.computeParasiteDrag(CT, CT.Weapons);
            if(((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).get_task() == 7 || ((com.maddox.il2.ai.air.Maneuver)(com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).get_maneuver() == 43)
                putScareShpere();
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 25)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
                if(aircraft.aircIndex() == 0 && !(aircraft instanceof com.maddox.il2.objects.air.TypeFighter))
                {
                    int i = actor.getArmy() - 1 & 1;
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
        if(com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence && !Gears.onGround())
            com.maddox.il2.ai.World.wind().getVectorAI(Loc, Vwind);
        else
            Vwind.set(0.0D, 0.0D, 0.0D);
        Vair.sub(Vwld, Vwind);
        Or.transformInv(Vair, Vflow);
        Density = com.maddox.il2.fm.Atmosphere.density((float)Loc.z);
        AOA = com.maddox.il2.fm.AIFlightModel.RAD2DEG(-(float)java.lang.Math.atan2(Vflow.z, Vflow.x));
        AOS = com.maddox.il2.fm.AIFlightModel.RAD2DEG((float)java.lang.Math.atan2(Vflow.y, Vflow.x));
        V2 = (float)Vflow.lengthSquared();
        V = (float)java.lang.Math.sqrt(V2);
        Mach = V / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)Loc.z);
        if(Mach > 0.8F)
            Mach = 0.8F;
        Kq = 1.0F / (float)java.lang.Math.sqrt(1.0F - Mach * Mach);
        q_ = Density * V2 * 0.5F;
        if(!callSuperUpdate)
            return;
        double d = Loc.z - Gears.screenHQ;
        if(d < 0.0D)
            d = 0.0D;
        Cw.x = -q_ * (Wing.new_Cx(AOA) + 2.0F * GearCX * CT.getGear() + 2.0F * Sq.dragAirbrakeCx * CT.getAirBrake());
        Cw.z = q_ * Wing.new_Cy(AOA) * Kq;
        if(fmsfxCurrentType != 0 && fmsfxCurrentType == 1)
            Cw.z *= com.maddox.il2.objects.air.Aircraft.cvt(fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
        if(d < 0.40000000000000002D * (double)Length)
        {
            double d1 = 1.0D - d / (0.40000000000000002D * (double)Length);
            double d2 = 1.0D + 0.29999999999999999D * d1;
            double d4 = 1.0D + 0.29999999999999999D * d1;
            Cw.z *= d2;
            Cw.x *= d4;
        }
        Cw.y = -q_ * Wing.new_Cz(AOS);
        for(int k = 0; k < EI.getNum(); k++)
            Cw.x += -q_ * (0.8F * Sq.dragEngineCx[k]);

        Fw.scale(Sq.liftWingLIn + Sq.liftWingLMid + Sq.liftWingLOut + Sq.liftWingRIn + Sq.liftWingRMid + Sq.liftWingROut, Cw);
        Fw.x -= q_ * (Sq.dragParasiteCx + Sq.dragProducedCx);
        AF.set(Fw);
        AF.y -= AOS * q_ * 0.1F;
        if(com.maddox.il2.fm.AIFlightModel.isNAN(AF))
        {
            AF.set(0.0D, 0.0D, 0.0D);
            flutter();
        } else
        if(AF.length() > (double)(Gravity * 50F))
        {
            AF.normalize();
            AF.scale(Gravity * 50F);
            flutter();
        }
        AM.set(0.0D, 0.0D, 0.0D);
        Wtrue.set(0.0D, 0.0D, 0.0D);
        AM.x = (double)((Sq.liftWingLIn * Arms.WING_ROOT + Sq.liftWingLMid * Arms.WING_MIDDLE + Sq.liftWingLOut * Arms.WING_END) - Sq.liftWingRIn * Arms.WING_ROOT - Sq.liftWingRMid * Arms.WING_MIDDLE - Sq.liftWingROut * Arms.WING_END) * Cw.z;
        if(fmsfxCurrentType != 0)
        {
            if(fmsfxCurrentType == 2)
            {
                AM.x = (double)(-Sq.liftWingRIn * Arms.WING_ROOT - Sq.liftWingRMid * Arms.WING_MIDDLE - Sq.liftWingROut * Arms.WING_END) * Cw.z;
                if(com.maddox.rts.Time.current() >= fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
            if(fmsfxCurrentType == 3)
            {
                AM.x = (double)(Sq.liftWingLIn * Arms.WING_ROOT + Sq.liftWingLMid * Arms.WING_MIDDLE + Sq.liftWingLOut * Arms.WING_END) * Cw.z;
                if(com.maddox.rts.Time.current() >= fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
        }
        if(java.lang.Math.abs(AOA) > 33F)
            AM.y = 1.0F * (Sq.liftStab * Arms.HOR_STAB) * (q_ * Tail.new_Cy(AOA) * Kq);
        if(java.lang.Math.abs(AOS) > 33F)
            AM.z = 1.0F * ((0.2F + Sq.liftKeel) * Arms.VER_STAB) * (q_ * Tail.new_Cy(AOS) * Kq);
        float f1 = Sq.liftWingLIn + Sq.liftWingLMid + Sq.liftWingLOut;
        float f2 = Sq.liftWingRIn + Sq.liftWingRMid + Sq.liftWingROut;
        float f3 = (float)Vflow.lengthSquared() - 120F;
        if(f3 < 0.0F)
            f3 = 0.0F;
        if(Vflow.x < 0.0D)
            f3 = 0.0F;
        if(f3 > 15000F)
            f3 = 15000F;
        if(((com.maddox.il2.ai.air.Maneuver)(com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).get_maneuver() != 20)
        {
            float f4 = f1 - f2;
            if(!getOp(19) && d > 20D && f3 > 10F)
            {
                AM.y += (double)(5F * Sq.squareWing) * Vflow.x;
                AM.z += 80F * Sq.squareWing * EI.getPropDirSign();
                if(AOA > 20F || AOA < -20F)
                {
                    float f6 = 1.0F;
                    if(W.z < 0.0D)
                        f6 = -1F;
                    AM.z += (double)(30F * f6 * Sq.squareWing) * (3D * Vflow.z + Vflow.x);
                    AM.x -= (double)(50F * f6 * Sq.squareWing) * (Vflow.z + 3D * Vflow.x);
                }
            } else
            {
                if(!Gears.onGround())
                {
                    float f7 = AOA * 3F;
                    if(f7 > 25F)
                        f7 = 25F;
                    if(f7 < -25F)
                        f7 = -25F;
                    if(!getOp(34))
                        AM.x -= f7 * f2 * f3;
                    else
                    if(!getOp(37))
                        AM.x += f7 * f1 * f3;
                    else
                    if(((com.maddox.il2.ai.air.Maneuver)(com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).get_maneuver() == 44 && (AOA > 15F || AOA < -12F))
                    {
                        if(f4 > 0.0F && W.z > 0.0D)
                            W.z = -9.9999997473787516E-005D;
                        if(f4 < 0.0F && W.z < 0.0D)
                            W.z = 9.9999997473787516E-005D;
                        if(f3 > 1000F)
                            f3 = 1000F;
                        if(W.z < 0.0D)
                        {
                            AM.z -= 3F * Sq.squareWing * f3;
                            if(AOA > 0.0F)
                                AM.x += 40F * Sq.squareWing * f3;
                            else
                                AM.x -= 40F * Sq.squareWing * f3;
                        } else
                        {
                            AM.z += 3F * Sq.squareWing * f3;
                            if(AOA > 0.0F)
                                AM.x -= 40F * Sq.squareWing * f3;
                            else
                                AM.x += 40F * Sq.squareWing * f3;
                        }
                    }
                }
                if(Sq.liftKeel > 0.1F)
                    AM.z += AOS * q_ * 0.5F;
                else
                    AM.x += AOS * q_ * 1.0F;
                double d5 = 1.0D;
                if(d < 1.5D * (double)Length)
                {
                    d5 += (d - 1.5D * (double)Length) / (double)Length;
                    if(d5 < 0.0D)
                        d5 = 0.0D;
                }
                if(Vflow.x < 20D && java.lang.Math.abs(AOS) < 33F)
                    AM.y += d5 * AF.z;
                float f8 = (float)Vflow.x;
                if(f8 > 150F)
                    f8 = 150F;
                float f9 = SensYaw;
                if(f9 > 0.2F)
                    f9 = 0.2F;
                float f10 = SensRoll;
                if(f10 > 4F)
                    f10 = 4F;
                double d7 = 20D - (double)java.lang.Math.abs(AOA);
                if(d7 < (double)minElevCoeff)
                    d7 = minElevCoeff;
                double d9 = (double)AOA - (double)CT.getElevator() * d7;
                double d10 = 0.017000000000000001D * (double)java.lang.Math.abs(AOA);
                if(d10 < 1.0D)
                    d10 = 1.0D;
                if(d9 > 90D)
                    d9 = -(180D - d9);
                if(d9 < -90D)
                    d9 = 180D - d9;
                d9 *= d10;
                double d11 = 12D - (double)java.lang.Math.abs(AOS);
                if(d11 < 0.0D)
                    d11 = 0.0D;
                double d12 = (double)AOS - (double)CT.getRudder() * d11;
                double d13 = 0.01D * (double)java.lang.Math.abs(AOS);
                if(d13 < 1.0D)
                    d13 = 1.0D;
                if(d12 > 90D)
                    d12 = -(180D - d12);
                if(d12 < -90D)
                    d12 = 180D - d12;
                d12 *= d13;
                float f13 = Sq.squareWing;
                if(f13 < 1.0F)
                    f13 = 1.0F;
                f13 = 1.0F / f13;
                Wtrue.x = CT.getAileron() * f8 * f10 * Sq.squareAilerons * f13 * 1.0F;
                Wtrue.y = d9 * (double)f8 * (double)SensPitch * (double)Sq.squareElevators * (double)f13 * 0.012000000104308128D;
                Wtrue.z = d12 * (double)f8 * (double)f9 * (double)Sq.squareRudders * (double)f13 * 0.15000000596046448D;
            }
        } else
        {
            float f5 = 1.0F;
            if(W.z < 0.0D)
                f5 = -1F;
            AM.z += (double)(30F * f5 * Sq.squareWing) * (3D * Vflow.z + Vflow.x);
            AM.x -= (double)(50F * f5 * Sq.squareWing) * (Vflow.z + 3D * Vflow.x);
        }
        if(Sq.squareElevators < 0.1F && d > 20D && f3 > 10F)
            AM.y += Gravity * 0.4F;
        AM.add(producedAM);
        AF.add(producedAF);
        producedAM.set(0.0D, 0.0D, 0.0D);
        producedAF.set(0.0D, 0.0D, 0.0D);
        AF.add(EI.producedF);
        if(W.lengthSquared() > 36D)
            W.scale(6D / W.length());
        double d3 = 0.10000000000000001D + (double)Sq.squareWing;
        if(d3 < 1.0D)
            d3 = 1.0D;
        d3 = 1.0D / d3;
        W.x *= 1.0D - 0.12D * (0.20000000000000001D + (double)f1 + (double)f2) * d3;
        W.y *= 1.0D - 0.5D * (0.20000000000000001D + (double)Sq.liftStab) * d3;
        W.z *= 1.0D - 0.5D * (0.20000000000000001D + (double)Sq.liftKeel) * d3;
        GF.set(0.0D, 0.0D, 0.0D);
        GM.set(0.0D, 0.0D, 0.0D);
        Gears.roughness = 0.5D;
        Gears.ground(this, true);
        GM.x *= 0.10000000000000001D;
        GM.y *= 0.40000000000000002D;
        GM.z *= 0.80000000000000004D;
        int l = 2;
        if(GF.lengthSquared() == 0.0D && GM.lengthSquared() == 0.0D || brakeShoe)
            l = 1;
        SummF.add(AF, GF);
        ACmeter.set(SummF);
        ACmeter.scale(1.0F / Gravity);
        TmpV.set(0.0D, 0.0D, -Gravity);
        Or.transformInv(TmpV);
        GF.add(TmpV);
        SummF.add(AF, GF);
        SummM.add(AM, GM);
        double d6 = 1.0D / (double)M.mass;
        LocalAccel.scale(d6, SummF);
        if(com.maddox.il2.fm.AIFlightModel.isNAN(AM))
        {
            AM.set(0.0D, 0.0D, 0.0D);
            flutter();
        } else
        if(AM.length() > (double)(Gravity * 100F))
        {
            AM.normalize();
            AM.scale(Gravity * 100F);
            flutter();
        }
        dryFriction -= 0.01D;
        if(Gears.gearsChanged)
            dryFriction = 1.0F;
        if(Gears.nOfPoiOnGr > 0)
            dryFriction += 0.02F;
        if(dryFriction < 1.0F)
            dryFriction = 1.0F;
        if(dryFriction > 32F)
            dryFriction = 32F;
        float f11 = 4F * (0.25F - EI.getPowerOutput());
        if(f11 < 0.0F)
            f11 = 0.0F;
        f11 *= f11;
        f11 *= dryFriction;
        float f12 = f11 * M.mass * M.mass;
        if(!brakeShoe && (Gears.nOfPoiOnGr == 0 && Gears.nOfGearsOnGr < 3 || f11 == 0.0F || SummM.lengthSquared() > (double)(2.0F * f12) || SummF.lengthSquared() > (double)(80F * f12) || W.lengthSquared() > (double)(0.00014F * f11) || Vwld.lengthSquared() > (double)(0.09F * f11)))
        {
            double d8 = 1.0D / (double)l;
            for(int i1 = 0; i1 < l; i1++)
            {
                SummF.add(AF, GF);
                SummM.add(AM, GM);
                AW.x = ((J.y - J.z) * W.y * W.z + SummM.x) / J.x;
                AW.y = ((J.z - J.x) * W.z * W.x + SummM.y) / J.y;
                AW.z = ((J.x - J.y) * W.x * W.y + SummM.z) / J.z;
                TmpV.scale(d8 * (double)f, AW);
                W.add(TmpV);
                Or.transform(W, Vn);
                Wtrue.add(W);
                TmpV.scale(d8 * (double)f, Wtrue);
                Or.increment((float)(-com.maddox.il2.fm.AIFlightModel.RAD2DEG(TmpV.z)), (float)(-com.maddox.il2.fm.AIFlightModel.RAD2DEG(TmpV.y)), (float)com.maddox.il2.fm.AIFlightModel.RAD2DEG(TmpV.x));
                Or.transformInv(Vn, W);
                TmpV.scale(d6, SummF);
                Or.transform(TmpV);
                Accel.set(TmpV);
                TmpV.scale(d8 * (double)f);
                Vwld.add(TmpV);
                TmpV.scale(d8 * (double)f, Vwld);
                TmpP.set(TmpV);
                Loc.add(TmpP);
                boolean flag;
                if(com.maddox.il2.fm.AIFlightModel.isNAN(Loc))
                    flag = false;
                GF.set(0.0D, 0.0D, 0.0D);
                GM.set(0.0D, 0.0D, 0.0D);
                if(i1 < l - 1)
                {
                    Gears.ground(this, true);
                    GM.x *= 0.10000000000000001D;
                    GM.y *= 0.40000000000000002D;
                    GM.z *= 0.80000000000000004D;
                    TmpV.set(0.0D, 0.0D, -Gravity);
                    Or.transformInv(TmpV);
                    GF.add(TmpV);
                }
            }

            for(int j1 = 0; j1 < 3; j1++)
            {
                Gears.gWheelAngles[j1] = (Gears.gWheelAngles[j1] + (float)java.lang.Math.toDegrees(java.lang.Math.atan((Gears.gVelocity[j1] * (double)f) / 0.375D))) % 360F;
                Gears.gVelocity[j1] *= 0.94999998807907104D;
            }

            HM.chunkSetAngles("GearL1_D0", 0.0F, -Gears.gWheelAngles[0], 0.0F);
            HM.chunkSetAngles("GearR1_D0", 0.0F, -Gears.gWheelAngles[1], 0.0F);
            HM.chunkSetAngles("GearC1_D0", 0.0F, -Gears.gWheelAngles[2], 0.0F);
        }
    }

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
