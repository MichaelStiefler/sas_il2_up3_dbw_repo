// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Pilot.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.KI_46_OTSUHEI;
import com.maddox.il2.objects.air.ME_163B1A;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.ParaTorpedoGun;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.ai.air:
//            Maneuver, AirGroup, Airdrome, NearestTargets

public class Pilot extends com.maddox.il2.ai.air.Maneuver
{

    public void targetAll()
    {
        airTargetType = 9;
    }

    public void targetFighters()
    {
        airTargetType = 7;
    }

    public void targetBombers()
    {
        airTargetType = 8;
    }

    public void attackGround(int i)
    {
        groundTargetType = i;
    }

    public Pilot(java.lang.String s)
    {
        super(s);
        airTargetType = 9;
        groundTargetType = 0;
        dumbOffTime = 0L;
        oldTask = 3;
        oldTaskObject = null;
        oldGTarget = null;
        continueManeuver = false;
        Energed = false;
        dist = 0.0F;
        dE = 0.0F;
    }

    private boolean killed(com.maddox.il2.engine.Actor actor)
    {
        if(actor == null)
            return true;
        if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
            actor = actor.getOwner();
        if(com.maddox.il2.engine.Actor.isValid(actor))
            return !actor.isAlive();
        else
            return true;
    }

    public boolean killed(com.maddox.il2.fm.FlightModel flightmodel)
    {
        if(flightmodel == null)
            return true;
        if(flightmodel.AS.astatePilotStates[0] == 100)
            return true;
        if(com.maddox.il2.engine.Actor.isValid(flightmodel.actor))
            return killed(flightmodel.actor);
        else
            return flightmodel.isTakenMortalDamage();
    }

    private boolean detectable(com.maddox.il2.engine.Actor actor)
    {
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return false;
        if(Skill >= 2)
        {
            return true;
        } else
        {
            VDanger.set(((com.maddox.il2.objects.air.Aircraft)actor).FM.Loc);
            VDanger.sub(Loc);
            OnMe.scale(-1D, VDanger);
            Or.transformInv(VDanger);
            return VDanger.x >= 0.0D;
        }
    }

    public boolean isDumb()
    {
        return com.maddox.rts.Time.current() < dumbOffTime;
    }

    public void setDumbTime(long l)
    {
        dumbOffTime = com.maddox.rts.Time.current() + l;
    }

    public void addDumbTime(long l)
    {
        if(isDumb())
            dumbOffTime += l;
        else
            setDumbTime(l);
    }

    public void super_update(float f)
    {
        super.update(f);
    }

    public void update(float f)
    {
        if(actor.net != null && actor.net.isMirror())
        {
            ((com.maddox.il2.objects.air.NetAircraft.Mirror)actor.net).fmUpdate(f);
            return;
        }
        moveCarrier();
        if(TaxiMode)
        {
            com.maddox.il2.ai.World.cur().airdrome.update(this, f);
            return;
        }
        if(isTick(8, 0) || get_maneuver() == 0)
        {
            setPriorities();
            setTaskAndManeuver();
        }
        super.update(f);
    }

    protected void setPriorities()
    {
        if(killed(danger))
            danger = null;
        if(killed(target))
            target = null;
        if(killed(airClient))
            airClient = null;
        if(killed(target_ground))
            target_ground = null;
        setBusy(false);
        if(AS.isPilotDead(0))
        {
            setBusy(true);
            set_maneuver(44);
            if(crew > 1)
                ((com.maddox.il2.objects.air.Aircraft)actor).hitDaSilk();
            set_task(0);
            return;
        }
        if(get_maneuver() == 46)
        {
            setBusy(true);
            dontSwitch = true;
            return;
        }
        float f = 0.0F;
        int i = EI.getNum();
        if(i != 0)
        {
            for(int j = 0; j < i; j++)
                f += EI.engines[j].getReadyness() / (float)i;

            if(f < 0.7F)
                setReadyToReturn(true);
            if(f < 0.3F)
                setReadyToDie(true);
        }
        if(M.fuel < 0.3F * M.maxFuel)
        {
            int k = AP.way.Cur();
            AP.way.last();
            float f1 = AP.getWayPointDistance();
            AP.way.setCur(k);
            if(M.maxFuel < 0.001F)
                M.maxFuel = 0.001F;
            float f2 = (1000F * Range * M.fuel) / M.maxFuel;
            if(f2 < 2.0F * f1 && !(actor instanceof com.maddox.il2.objects.air.TypeGlider))
                setReadyToReturn(true);
        }
        if(M.fuel < 0.01F && !(actor instanceof com.maddox.il2.objects.air.TypeGlider))
            setReadyToDie(true);
        if(isTakenMortalDamage() || !isCapableOfBMP())
        {
            setBusy(true);
            ((com.maddox.il2.objects.air.Aircraft)actor).hitDaSilk();
            set_task(0);
            if(Group != null)
                Group.delAircraft((com.maddox.il2.objects.air.Aircraft)actor);
        }
        if(isReadyToDie())
        {
            AP.way.setCur(1);
            bombsOut = true;
            CT.dropFuelTanks();
            set_task(0);
            if(get_maneuver() != 49 && get_maneuver() != 12 && get_maneuver() != 54)
            {
                clear_stack();
                set_maneuver(49);
            }
            setBusy(true);
            return;
        }
        if(get_maneuver() == 44 || get_maneuver() == 25 || get_maneuver() == 49 || get_maneuver() == 26 || get_maneuver() == 64 || get_maneuver() == 2 || get_maneuver() == 57 || get_maneuver() == 60 || get_maneuver() == 61)
        {
            setBusy(true);
            dontSwitch = true;
            return;
        }
        if(getDangerAggressiveness() > 0.88F && danger != null && ((danger.actor instanceof com.maddox.il2.objects.air.TypeFighter) || (danger.actor instanceof com.maddox.il2.objects.air.TypeStormovik)) && ((com.maddox.il2.ai.air.Maneuver)danger).isOk())
        {
            if(get_task() != 4)
            {
                set_task(4);
                clear_stack();
                set_maneuver(0);
                if((actor instanceof com.maddox.il2.objects.air.TypeStormovik) && Group != null)
                {
                    int l = Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor);
                    if(Group.nOfAirc >= l + 2)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)Group.airc[l + 1].FM;
                        com.maddox.il2.objects.sounds.Voice.speakCheckYour6((com.maddox.il2.objects.air.Aircraft)actor, (com.maddox.il2.objects.air.Aircraft)danger.actor);
                        com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)maneuver.actor, 1);
                        maneuver.airClient = this;
                        set_task(6);
                        clear_stack();
                        maneuver.target = danger;
                        set_maneuver(27);
                        setBusy(true);
                        return;
                    }
                }
            }
            com.maddox.il2.objects.sounds.Voice.speakClearTail((com.maddox.il2.objects.air.Aircraft)actor);
            setBusy(true);
            return;
        }
        if(isReadyToReturn() && !AP.way.isLanding())
        {
            if(Group != null && Group.grTask != 1)
            {
                com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(Group);
                airgroup.rejoinGroup = null;
                Group.delAircraft((com.maddox.il2.objects.air.Aircraft)actor);
                airgroup.addAircraft((com.maddox.il2.objects.air.Aircraft)actor);
                airgroup.w.last();
                airgroup.w.prev();
                airgroup.w.curr().setTimeout(3);
                airgroup.timeOutForTaskSwitch = 10000;
                AP.way.last();
                AP.way.prev();
                AP.way.curr().getP(p1f);
                p1f.z = -10F + (float)Loc.z;
            }
            bombsOut = true;
            CT.dropFuelTanks();
            return;
        }
        if(get_task() == 6)
        {
            if(target != null && airClient != null && target == ((com.maddox.il2.ai.air.Maneuver)airClient).danger)
            {
                if(actor instanceof com.maddox.il2.objects.air.TypeStormovik)
                {
                    if(((com.maddox.il2.ai.air.Maneuver)airClient).getDangerAggressiveness() > 0.0F)
                    {
                        setBusy(true);
                        return;
                    }
                    airClient = null;
                }
                setBusy(true);
                return;
            }
            if((((com.maddox.il2.objects.air.Aircraft)actor).aircIndex() & 1) == 0 && Group != null)
            {
                int i1 = Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor);
                if(Group.nOfAirc >= i1 + 2 && (Group.airc[i1 + 1].aircIndex() & 1) != 0)
                {
                    com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)Group.airc[i1 + 1].FM;
                    if(maneuver1.airClient == this && maneuver1.getDangerAggressiveness() > 0.5F && maneuver1.danger != null && ((com.maddox.il2.ai.air.Maneuver)maneuver1.danger).isOk())
                    {
                        com.maddox.il2.objects.sounds.Voice.speakCheckYour6((com.maddox.il2.objects.air.Aircraft)maneuver1.actor, (com.maddox.il2.objects.air.Aircraft)maneuver1.danger.actor);
                        com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 1);
                        set_task(6);
                        clear_stack();
                        target = maneuver1.danger;
                        set_maneuver(27);
                        setBusy(true);
                        return;
                    }
                }
            }
            if(target != null && ((com.maddox.il2.ai.air.Maneuver)target).getDangerAggressiveness() > 0.5F && ((com.maddox.il2.ai.air.Maneuver)target).danger == this && ((com.maddox.il2.ai.air.Maneuver)target).isOk())
            {
                setBusy(true);
                return;
            }
        }
        if(isDumb())
        {
            setBusy(true);
            return;
        }
        if(get_task() == 7 && target_ground != null && get_maneuver() != 0)
        {
            setBusy(true);
            return;
        }
        if(get_task() == 3 && AP.way.isLanding())
        {
            setBusy(true);
            return;
        } else
        {
            return;
        }
    }

    private void setTaskAndManeuver()
    {
        if(dontSwitch)
        {
            dontSwitch = false;
            return;
        }
        if(!isBusy())
        {
            if((wasBusy || get_maneuver() == 0) && Group != null)
            {
                clear_stack();
                Group.setTaskAndManeuver(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor));
            }
        } else
        if(get_maneuver() == 0)
        {
            clear_stack();
            setManeuverByTask();
        }
    }

    public void setManeuverByTask()
    {
        clear_stack();
        switch(get_task())
        {
        default:
            break;

        case 2: // '\002'
            if(Leader != null && com.maddox.il2.engine.Actor.isValid(Leader.actor) && !Leader.isReadyToReturn() && !Leader.isReadyToDie() && Leader.getSpeed() > 35F)
                set_maneuver(24);
            else
                set_task(3);
            break;

        case 3: // '\003'
            set_maneuver(21);
            if(AP.way.isLanding())
                break;
            wingman(true);
            if(Leader != null)
                break;
            boolean flag1 = false;
            int i;
            float f;
            if(AP.way.Cur() < AP.way.size() - 1)
            {
                AP.way.next();
                i = AP.way.curr().Action;
                boolean flag2 = AP.way.curr().getTarget() != null;
                f = AP.getWayPointDistance();
                AP.way.prev();
            } else
            {
                i = AP.way.curr().Action;
                f = AP.getWayPointDistance();
            }
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)(com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM;
            float f2 = AP.getWayPointDistance();
            do
            {
                if(pilot.Wingman == null)
                    break;
                pilot = (com.maddox.il2.ai.air.Pilot)pilot.Wingman;
                pilot.wingman(true);
                pilot.AP.way.setCur(AP.way.Cur());
                if(!pilot.AP.way.isLanding() && pilot.get_task() == 3)
                    pilot.set_task(2);
            } while(true);
            if(((com.maddox.il2.objects.air.Aircraft)actor).aircIndex() == 0 && Speak5minutes == 0 && i == 3 && (double)f < 30000D)
            {
                com.maddox.il2.objects.sounds.Voice.speak5minutes((com.maddox.il2.objects.air.Aircraft)actor);
                Speak5minutes = 1;
            }
            break;

        case 4: // '\004'
            if(get_maneuver() == 0)
                set_maneuver(21);
            if(danger == null)
            {
                set_task(3);
                break;
            }
            if(actor instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                bombsOut = true;
                CT.dropFuelTanks();
            }
            dist = (float)Loc.distance(danger.Loc);
            VDanger.sub(danger.Loc, Loc);
            OnMe.scale(-1D, VDanger);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            tmpOr.transformInv(VDanger);
            diffV.sub(danger.Vwld, Vwld);
            tmpOr.transformInv(diffV);
            diffVLength = diffV.length();
            tmpOr.setYPR(danger.Or.getYaw(), 0.0F, 0.0F);
            danger.Or.transformInv(OnMe);
            VDanger.normalize();
            OnMe.normalize();
            dE = (Energy - danger.Energy) * 0.1019F;
            Energed = danger.Energy > Energy;
            Faster = danger.getSpeed() > getSpeed();
            Higher = danger.Loc.z > Loc.z;
            Near = dist < 300F;
            OnBack = VDanger.x < 0.0D && dist < 2000F;
            Visible = VDanger.dot(MainLook) > 0.0D;
            Looks = OnMe.x > 0.0D;
            VDanger.normalize();
            if(OnBack && Near && (danger instanceof com.maddox.il2.objects.air.TypeFighter) && ((actor instanceof com.maddox.il2.objects.air.TypeTransport) || Wingman == null || killed(Wingman) || ((com.maddox.il2.ai.air.Pilot)Wingman).target != danger))
                if(isLeader())
                {
                    if(((actor instanceof com.maddox.il2.objects.air.TypeFighter) || (actor instanceof com.maddox.il2.objects.air.TypeStormovik) && Skill > 1 && AP.way.curr().Action == 0) && Wingman != null && !killed(Wingman) && !((com.maddox.il2.ai.air.Pilot)Wingman).requestCoverFor(this))
                        if(Wingman.Wingman != null && !killed(Wingman.Wingman))
                            ((com.maddox.il2.ai.air.Pilot)Wingman.Wingman).requestCoverFor(this);
                        else
                        if(Skill >= 2)
                        {
                            com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriendlyFighter((com.maddox.il2.objects.air.Aircraft)actor, 8000F);
                            if(aircraft != null && (aircraft.FM instanceof com.maddox.il2.ai.air.Pilot))
                                ((com.maddox.il2.ai.air.Pilot)aircraft.FM).requestCoverFor(this);
                        }
                } else
                if(Skill >= 2)
                {
                    com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.War.getNearestFriendlyFighter((com.maddox.il2.objects.air.Aircraft)actor, 8000F);
                    if((aircraft1 instanceof com.maddox.il2.objects.air.TypeFighter) && (aircraft1.FM instanceof com.maddox.il2.ai.air.Pilot))
                        ((com.maddox.il2.ai.air.Pilot)aircraft1.FM).requestCoverFor(this);
                }
            if(actor instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                fighterDefence();
                break;
            }
            if(actor instanceof com.maddox.il2.objects.air.TypeStormovik)
                stormovikDefence();
            else
                transportDefence();
            break;

        case 5: // '\005'
            if(airClient != null && !killed(airClient))
            {
                followOffset.set(100D, 0.0D, 20D);
                set_maneuver(65);
                break;
            }
            airClient = null;
            if(target != null && !killed(target))
            {
                set_task(6);
                set_maneuver(0);
            } else
            {
                set_task(3);
                set_maneuver(21);
            }
            break;

        case 6: // '\006'
            WeWereInAttack = true;
            if(actor instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                bombsOut = true;
                CT.dropFuelTanks();
            }
            if(target == null || !hasCourseWeaponBullets())
            {
                if(AP.way.curr().Action == 3)
                {
                    set_task(7);
                    set_maneuver(0);
                    break;
                }
                set_task(3);
                if(Leader != null)
                    set_maneuver(24);
                else
                    set_maneuver(21);
                break;
            }
            int j = ((com.maddox.il2.objects.air.Aircraft)actor).aircIndex();
            if(target.actor instanceof com.maddox.il2.objects.air.TypeBomber)
            {
                attackBombers();
                break;
            }
            if(target.actor instanceof com.maddox.il2.objects.air.TypeStormovik)
            {
                attackStormoviks();
                break;
            }
            if(j == 0 || j == 2)
                set_maneuver(27);
            if(j != 1 && j != 3)
                break;
            if(Leader != null && !killed(Leader))
            {
                airClient = Leader;
                set_task(5);
                set_maneuver(0);
            } else
            {
                set_maneuver(27);
            }
            break;

        case 7: // '\007'
            if(!WeWereInGAttack)
                WeWereInGAttack = true;
            if(!com.maddox.il2.engine.Actor.isAlive(target_ground))
            {
                set_task(2);
                set_maneuver(0);
                break;
            }
            boolean flag = true;
            if(CT.Weapons[0] != null && CT.Weapons[0][0] != null && CT.Weapons[0][0].bulletMassa() > 0.05F && CT.Weapons[0][0].countBullets() > 0)
                flag = false;
            if(flag && CT.getWeaponMass() < 15F || CT.getWeaponMass() < 1.0F)
            {
                com.maddox.il2.objects.sounds.Voice.speakEndOfAmmo((com.maddox.il2.objects.air.Aircraft)actor);
                set_task(2);
                set_maneuver(0);
                if(AP.way.curr().Action != 3)
                    AP.way.next();
                target_ground = null;
            }
            if((target_ground instanceof com.maddox.il2.ai.ground.Prey) && (((com.maddox.il2.ai.ground.Prey)target_ground).HitbyMask() & 1) == 0)
            {
                float f1 = 0.0F;
                for(int k = 0; k < 4; k++)
                    if(CT.Weapons[k] != null && CT.Weapons[k][0] != null && CT.Weapons[k][0].countBullets() != 0 && CT.Weapons[k][0].bulletMassa() > f1)
                        f1 = CT.Weapons[k][0].bulletMassa();

                if(f1 < 0.08F || (target_ground instanceof com.maddox.il2.ai.ground.TgtShip) && f1 < 0.55F)
                {
                    set_task(2);
                    set_maneuver(0);
                    if(AP.way.curr().Action != 3)
                        AP.way.next();
                    target_ground = null;
                }
            }
            if(CT.Weapons[3] != null && CT.Weapons[3][0] != null && CT.Weapons[3][0].countBullets() != 0)
            {
                if(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.ParaTorpedoGun)
                {
                    set_maneuver(43);
                    break;
                }
                if(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.TorpedoGun)
                {
                    if(target_ground instanceof com.maddox.il2.ai.ground.TgtShip)
                    {
                        if((this instanceof com.maddox.il2.objects.air.TypeHasToKG) && Skill >= 2)
                            set_maneuver(81);
                        else
                            set_maneuver(51);
                    } else
                    {
                        set_maneuver(43);
                    }
                    break;
                }
                if(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara)
                {
                    AP.way.curr().setTarget(null);
                    target_ground = null;
                    set_maneuver(21);
                    break;
                }
                if(CT.Weapons[3][0].bulletMassa() < 10F)
                {
                    set_maneuver(52);
                    break;
                }
                if((actor instanceof com.maddox.il2.objects.air.TypeDiveBomber) && Alt > 1200F)
                    set_maneuver(50);
                else
                    set_maneuver(43);
                break;
            }
            if(target_ground instanceof com.maddox.il2.objects.bridges.BridgeSegment)
            {
                set_task(2);
                set_maneuver(0);
                if(AP.way.curr().Action != 3)
                    AP.way.next();
                target_ground = null;
            }
            if((actor instanceof com.maddox.il2.objects.air.TypeFighter) || (actor instanceof com.maddox.il2.objects.air.TypeStormovik))
            {
                set_maneuver(43);
                break;
            }
            set_task(2);
            set_maneuver(0);
            if(AP.way.curr().Action != 3)
                AP.way.next();
            target_ground = null;
            break;

        case 0: // '\0'
            if(isReadyToDie())
                set_maneuver(49);
            break;

        case 1: // '\001'
            set_maneuver(45);
            break;
        }
    }

    public boolean requestCoverFor(com.maddox.il2.fm.FlightModel flightmodel)
    {
        if(actor instanceof com.maddox.il2.objects.air.TypeTransport)
        {
            com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 0);
            return false;
        }
        if(danger == null || ((com.maddox.il2.ai.air.Pilot)danger).target != this || danger.Loc.distance(Loc) > 600D + 200D * (double)Skill || (danger.actor instanceof com.maddox.il2.objects.air.TypeStormovik) || (danger.actor instanceof com.maddox.il2.objects.air.TypeBomber))
        {
            if(((com.maddox.il2.ai.air.Pilot)flightmodel).danger == null || killed(((com.maddox.il2.ai.air.Pilot)flightmodel).danger) || (((com.maddox.il2.ai.air.Pilot)flightmodel).danger.actor instanceof com.maddox.il2.objects.air.TypeTransport) || ((com.maddox.il2.ai.air.Pilot)flightmodel).danger.Loc.distance(flightmodel.Loc) > 3000D)
            {
                com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 2);
                return true;
            }
            set_task(6);
            set_maneuver(27);
            target = ((com.maddox.il2.ai.air.Pilot)flightmodel).danger;
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                com.maddox.il2.objects.sounds.Voice.speakCoverProvided((com.maddox.il2.objects.air.Aircraft)actor, (com.maddox.il2.objects.air.Aircraft)flightmodel.actor);
            else
                com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 1);
            return true;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 0);
            return false;
        }
    }

    public void setAsDanger(com.maddox.il2.engine.Actor actor)
    {
        if(get_maneuver() == 44)
            return;
        if(get_maneuver() == 26)
            return;
        if(isDumb() && !isReadyToReturn())
            return;
        if(actor.getArmy() == this.actor.getArmy())
        {
            set_maneuver(8);
            setDumbTime(5000L);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                com.maddox.il2.objects.sounds.Voice.speakCheckFire((com.maddox.il2.objects.air.Aircraft)this.actor, (com.maddox.il2.objects.air.Aircraft)actor);
            return;
        }
        if(!com.maddox.il2.engine.Actor.isValid(this.actor))
        {
            if(com.maddox.il2.ai.World.cur().isArcade())
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(actor, "Jeopardizing invalid actor (one being destroyed)..");
                com.maddox.il2.objects.effects.Explosions.generateComicBulb(actor, "Sucker", 5F);
                if((actor instanceof com.maddox.il2.objects.air.TypeFighter) && (((com.maddox.il2.objects.air.Aircraft)actor).FM instanceof com.maddox.il2.ai.air.Pilot))
                    ((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).set_maneuver(35);
            }
            com.maddox.il2.objects.sounds.Voice.speakNiceKill((com.maddox.il2.objects.air.Aircraft)actor);
            return;
        }
        switch(Skill)
        {
        case 0: // '\0'
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 98)
                return;
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.sub(Loc, ((com.maddox.il2.objects.air.Aircraft)actor).FM.Loc);
                ((com.maddox.il2.objects.air.Aircraft)actor).FM.Or.transformInv(vector3d);
                if(vector3d.z > 0.0D)
                    return;
            }
            break;

        case 1: // '\001'
            if(!detectable(actor))
                return;
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 97)
                return;
            break;

        case 2: // '\002'
            if(getMnTime() < 1.0F)
                return;
            break;

        case 3: // '\003'
            if(getMnTime() < 1.0F)
                return;
            break;
        }
        if(this.actor instanceof com.maddox.il2.objects.air.TypeTransport)
        {
            if(AP.way.curr().Action != 3 && (get_maneuver() == 24 || get_maneuver() == 21))
            {
                set_task(4);
                set_maneuver(0);
            }
            return;
        }
        if(get_task() == 2)
        {
            set_task(3);
            set_maneuver(0);
        }
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
        {
            if(actor instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                if(turret.length > 0 && AS.astatePilotStates[turret.length] < 90)
                    com.maddox.il2.objects.sounds.Voice.speakDanger((com.maddox.il2.objects.air.Aircraft)this.actor, 4);
                com.maddox.il2.objects.sounds.Voice.speakDanger((com.maddox.il2.objects.air.Aircraft)this.actor, 0);
            }
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            com.maddox.il2.ai.air.Pilot pilot = this;
            pilot.danger = aircraft.FM;
            if((this.actor instanceof com.maddox.il2.objects.air.TypeFighter) && get_task() != 4)
            {
                target = aircraft.FM;
                set_task(4);
                set_maneuver(0);
                clear_stack();
                return;
            }
        }
    }

    private void transportDefence()
    {
        switch(Skill)
        {
        default:
            break;

        case 0: // '\0'
            set_maneuver(21);
            break;

        case 1: // '\001'
            if(isLonely(30F, true))
                set_maneuver(38);
            break;

        case 2: // '\002'
        case 3: // '\003'
            if(!isLonely(40F, false))
                break;
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
            {
            case 0: // '\0'
                set_maneuver(39);
                break;

            case 1: // '\001'
                set_maneuver(38);
                break;

            case 2: // '\002'
                set_maneuver(15);
                break;

            case 3: // '\003'
                set_maneuver(14);
                break;
            }
            break;
        }
    }

    private void stormovikDefence()
    {
        if(dist > 400F)
        {
            set_maneuver(3);
            return;
        }
        switch(Skill)
        {
        default:
            break;

        case 0: // '\0'
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                set_maneuver(14);
            else
                set_maneuver(56);
            break;

        case 1: // '\001'
            if(Visible)
            {
                set_maneuver(14);
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 7))
            {
            case 0: // '\0'
                set_maneuver(29);
                break;

            case 1: // '\001'
                set_maneuver(35);
                break;

            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
                set_maneuver(14);
                break;

            case 6: // '\006'
            case 7: // '\007'
                set_maneuver(39);
                break;
            }
            break;

        case 2: // '\002'
        case 3: // '\003'
            if(Visible)
            {
                if(VDanger.x > 0.93999999761581421D)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 13))
                    {
                    case 0: // '\0'
                    case 1: // '\001'
                    case 2: // '\002'
                        set_maneuver(38);
                        break;

                    case 3: // '\003'
                    case 4: // '\004'
                        set_maneuver(27);
                        target = danger;
                        break;

                    case 5: // '\005'
                    case 6: // '\006'
                        set_maneuver(39);
                        break;

                    case 7: // '\007'
                    case 8: // '\b'
                    case 9: // '\t'
                        set_maneuver(35);
                        break;

                    case 10: // '\n'
                    case 11: // '\013'
                        set_maneuver(6);
                        break;

                    case 12: // '\f'
                        set_maneuver(56);
                        break;

                    case 13: // '\r'
                        set_maneuver(14);
                        break;
                    }
                    break;
                }
                if(java.lang.Math.abs(VDanger.x) < 0.27500000596046448D)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 7))
                    {
                    case 0: // '\0'
                        set_maneuver(16);
                        break;

                    case 1: // '\001'
                        set_maneuver(17);
                        break;

                    case 2: // '\002'
                    case 3: // '\003'
                    case 4: // '\004'
                        set_maneuver(28);
                        break;

                    case 5: // '\005'
                    case 6: // '\006'
                        set_maneuver(6);
                        break;

                    case 7: // '\007'
                        set_maneuver(13);
                        break;
                    }
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
                {
                case 0: // '\0'
                case 1: // '\001'
                    set_maneuver(14);
                    break;

                case 2: // '\002'
                    set_maneuver(29);
                    break;

                case 3: // '\003'
                    set_maneuver(39);
                    break;
                }
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 17))
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
                set_maneuver(29);
                break;

            case 5: // '\005'
            case 6: // '\006'
                set_maneuver(35);
                break;

            case 7: // '\007'
            case 8: // '\b'
                set_maneuver(28);
                break;

            case 9: // '\t'
            case 10: // '\n'
                set_maneuver(39);
                break;

            case 11: // '\013'
                set_maneuver(38);
                break;

            case 12: // '\f'
            case 13: // '\r'
                set_maneuver(32);
                break;

            case 14: // '\016'
                set_maneuver(7);
                break;

            case 15: // '\017'
            case 16: // '\020'
            case 17: // '\021'
                set_maneuver(15);
                break;
            }
            break;
        }
    }

    private void fighterDefence()
    {
        switch(Skill)
        {
        default:
            break;

        case 0: // '\0'
            if(VDanger.x > 0.90000000000000002D)
            {
                set_maneuver(27);
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 2))
            {
            case 0: // '\0'
            case 1: // '\001'
                set_maneuver(14);
                break;

            default:
                set_maneuver(28);
                break;
            }
            break;

        case 1: // '\001'
            if((double)dE > 500D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
                {
                case 1: // '\001'
                case 2: // '\002'
                    set_maneuver(22);
                    break;

                default:
                    set_maneuver(28);
                    break;
                }
                break;
            }
            if((double)dE > 200D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 5))
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    set_maneuver(22);
                    break;

                case 4: // '\004'
                    set_maneuver(35);
                    break;

                default:
                    set_maneuver(39);
                    break;
                }
                break;
            }
            if((double)dE > -200D)
            {
                if(VDanger.x > 0.80000000000000004D)
                {
                    set_maneuver(27);
                    break;
                }
                if(diffVLength < 50D && OnMe.x > 0.80000000000000004D)
                {
                    if((double)dist < 200D)
                    {
                        if((double)Alt > 500D)
                        {
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(19);
                                break;

                            case 2: // '\002'
                                set_maneuver(33);
                                break;

                            case 3: // '\003'
                                set_maneuver(34);
                                break;

                            case 4: // '\004'
                                set_maneuver(7);
                                break;

                            case 5: // '\005'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                            break;
                        }
                        if(Alt > 150F)
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(7);
                                break;

                            case 2: // '\002'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                        else
                            set_maneuver(29);
                        break;
                    }
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
                    {
                    case 0: // '\0'
                        set_maneuver(7);
                        break;

                    case 1: // '\001'
                        set_maneuver(19);
                        break;

                    case 2: // '\002'
                        set_maneuver(33);
                        break;

                    case 3: // '\003'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(35);
                        break;
                    }
                    break;
                }
                if(OnMe.x > 0.90000000000000002D)
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 2))
                    {
                    case 0: // '\0'
                        set_maneuver(35);
                        break;

                    case 1: // '\001'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(22);
                        break;
                    }
                else
                    set_maneuver(22);
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
            {
            case 0: // '\0'
                set_maneuver(27);
                break;

            case 1: // '\001'
                set_maneuver(35);
                break;

            case 2: // '\002'
                set_maneuver(19);
                break;

            case 3: // '\003'
                set_maneuver(33);
                break;

            default:
                set_maneuver(34);
                break;
            }
            break;

        case 2: // '\002'
            if((double)dE > 500D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                if(OnMe.x > 0.90000000000000002D && (double)dist < 200D)
                {
                    set_maneuver(35);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    set_maneuver(67);
                    break;

                case 4: // '\004'
                    set_maneuver(28);
                    break;

                default:
                    set_maneuver(16);
                    break;
                }
                break;
            }
            if((double)dE > 200D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                if(OnMe.x > 0.90000000000000002D && (double)dist > 200D)
                {
                    set_maneuver(35);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 5))
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    set_maneuver(67);
                    break;

                case 4: // '\004'
                    set_maneuver(35);
                    break;

                default:
                    set_maneuver(39);
                    break;
                }
                break;
            }
            if((double)dE > -200D)
            {
                if(VDanger.x > 0.80000000000000004D)
                {
                    set_maneuver(27);
                    break;
                }
                if(diffVLength < 50D && OnMe.x > 0.80000000000000004D)
                {
                    if((double)dist < 200D)
                    {
                        if((double)Alt > 500D)
                        {
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 10))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(19);
                                break;

                            case 2: // '\002'
                                set_maneuver(33);
                                break;

                            case 3: // '\003'
                                set_maneuver(34);
                                break;

                            case 4: // '\004'
                                set_maneuver(7);
                                break;

                            case 5: // '\005'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                            break;
                        }
                        if(Alt > 150F)
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 8))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(7);
                                break;

                            case 2: // '\002'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                        else
                            set_maneuver(29);
                        break;
                    }
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
                    {
                    case 0: // '\0'
                        set_maneuver(7);
                        break;

                    case 1: // '\001'
                        set_maneuver(19);
                        break;

                    case 2: // '\002'
                        set_maneuver(33);
                        break;

                    case 3: // '\003'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(35);
                        break;
                    }
                    break;
                }
                if(OnMe.x > 0.90000000000000002D)
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 2))
                    {
                    case 0: // '\0'
                        set_maneuver(35);
                        break;

                    case 1: // '\001'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(67);
                        break;
                    }
                else
                    set_maneuver(67);
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
            {
            case 0: // '\0'
                set_maneuver(27);
                break;

            case 1: // '\001'
                set_maneuver(35);
                break;

            case 2: // '\002'
                set_maneuver(19);
                break;

            case 3: // '\003'
                set_maneuver(33);
                break;

            default:
                set_maneuver(34);
                break;
            }
            break;

        case 3: // '\003'
            if((double)dE > 500D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                if(OnMe.x > 0.90000000000000002D && (double)dist < 200D)
                {
                    set_maneuver(35);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    set_maneuver(67);
                    break;

                case 4: // '\004'
                    set_maneuver(28);
                    break;

                default:
                    set_maneuver(16);
                    break;
                }
                break;
            }
            if((double)dE > 200D)
            {
                if(VDanger.x > 0.90000000000000002D)
                {
                    set_maneuver(27);
                    break;
                }
                if(OnMe.x > 0.90000000000000002D && (double)dist > 200D)
                {
                    set_maneuver(35);
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 5))
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    set_maneuver(67);
                    break;

                case 4: // '\004'
                    set_maneuver(35);
                    break;

                default:
                    set_maneuver(39);
                    break;
                }
                break;
            }
            if((double)dE > -200D)
            {
                if(VDanger.x > 0.80000000000000004D)
                {
                    set_maneuver(27);
                    break;
                }
                if(diffVLength < 50D && OnMe.x > 0.80000000000000004D)
                {
                    if((double)dist < 200D)
                    {
                        if((double)Alt > 500D)
                        {
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 14))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(19);
                                break;

                            case 2: // '\002'
                                set_maneuver(33);
                                break;

                            case 3: // '\003'
                                set_maneuver(34);
                                break;

                            case 4: // '\004'
                                set_maneuver(7);
                                break;

                            case 5: // '\005'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                            break;
                        }
                        if(Alt > 150F)
                            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 10))
                            {
                            case 0: // '\0'
                                set_maneuver(35);
                                break;

                            case 1: // '\001'
                                set_maneuver(7);
                                break;

                            case 2: // '\002'
                                set_maneuver(32);
                                break;

                            default:
                                set_maneuver(29);
                                break;
                            }
                        else
                            set_maneuver(29);
                        break;
                    }
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
                    {
                    case 0: // '\0'
                        set_maneuver(7);
                        break;

                    case 1: // '\001'
                        set_maneuver(19);
                        break;

                    case 2: // '\002'
                        set_maneuver(33);
                        break;

                    case 3: // '\003'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(35);
                        break;
                    }
                    break;
                }
                if(OnMe.x > 0.90000000000000002D)
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 2))
                    {
                    case 0: // '\0'
                        set_maneuver(35);
                        break;

                    case 1: // '\001'
                        set_maneuver(34);
                        break;

                    default:
                        set_maneuver(67);
                        break;
                    }
                else
                    set_maneuver(67);
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
            {
            case 0: // '\0'
                set_maneuver(27);
                break;

            case 1: // '\001'
                set_maneuver(35);
                break;

            case 2: // '\002'
                set_maneuver(19);
                break;

            case 3: // '\003'
                set_maneuver(33);
                break;

            default:
                set_maneuver(34);
                break;
            }
            break;
        }
    }

    public void attackBombers()
    {
        float f = 0.0F;
        if(CT.Weapons[1] != null && ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).countBullets() > 0)
            f = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletMassa();
        if(actor instanceof com.maddox.il2.objects.air.ME_163B1A)
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 2))
            {
            case 0: // '\0'
                setBomberAttackType(7);
                break;

            default:
                setBomberAttackType(2);
                break;
            }
        else
        if((actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI) && ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).countBullets() > 0)
            setBomberAttackType(10);
        else
            switch(Skill)
            {
            default:
                break;

            case 0: // '\0'
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 5))
                {
                case 0: // '\0'
                    setBomberAttackType(7);
                    break;

                case 1: // '\001'
                    setBomberAttackType(2);
                    break;

                default:
                    setBomberAttackType(5);
                    break;
                }
                break;

            case 1: // '\001'
                if(f > 0.12F)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
                    {
                    case 0: // '\0'
                        setBomberAttackType(2);
                        break;

                    case 1: // '\001'
                        setBomberAttackType(1);
                        break;

                    default:
                        setBomberAttackType(0);
                        break;
                    }
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                {
                case 0: // '\0'
                    setBomberAttackType(1);
                    break;

                case 1: // '\001'
                    setBomberAttackType(7);
                    break;

                default:
                    setBomberAttackType(2);
                    break;
                }
                break;

            case 2: // '\002'
                if(f > 0.12F)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                    {
                    case 0: // '\0'
                        setBomberAttackType(2);
                        break;

                    case 1: // '\001'
                        setBomberAttackType(1);
                        break;

                    default:
                        setBomberAttackType(0);
                        break;
                    }
                    break;
                }
                if(f > 0.05F)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 10))
                    {
                    case 0: // '\0'
                    case 1: // '\001'
                    case 2: // '\002'
                        setBomberAttackType(1);
                        break;

                    case 3: // '\003'
                        setBomberAttackType(7);
                        break;

                    case 4: // '\004'
                        setBomberAttackType(6);
                        break;

                    default:
                        setBomberAttackType(2);
                        break;
                    }
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                {
                case 0: // '\0'
                    setBomberAttackType(1);
                    break;

                case 1: // '\001'
                    setBomberAttackType(7);
                    break;

                case 2: // '\002'
                    setBomberAttackType(3);
                    break;

                default:
                    setBomberAttackType(2);
                    break;
                }
                break;

            case 3: // '\003'
                if(f > 0.12F)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 7))
                    {
                    case 0: // '\0'
                        setBomberAttackType(2);
                        break;

                    case 1: // '\001'
                        setBomberAttackType(1);
                        break;

                    case 2: // '\002'
                        setBomberAttackType(6);
                        break;

                    default:
                        setBomberAttackType(0);
                        break;
                    }
                    break;
                }
                if(f > 0.05F)
                {
                    switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 10))
                    {
                    case 0: // '\0'
                    case 1: // '\001'
                    case 2: // '\002'
                        setBomberAttackType(1);
                        break;

                    case 3: // '\003'
                        setBomberAttackType(7);
                        break;

                    case 4: // '\004'
                        setBomberAttackType(3);
                        break;

                    case 5: // '\005'
                        setBomberAttackType(6);
                        break;

                    default:
                        setBomberAttackType(2);
                        break;
                    }
                    break;
                }
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 4))
                {
                case 0: // '\0'
                    setBomberAttackType(1);
                    break;

                case 1: // '\001'
                    setBomberAttackType(7);
                    break;

                case 2: // '\002'
                    setBomberAttackType(3);
                    break;

                default:
                    setBomberAttackType(2);
                    break;
                }
                break;
            }
        set_maneuver(63);
    }

    public void attackStormoviks()
    {
        switch(Skill)
        {
        default:
            break;

        case 0: // '\0'
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 5))
            {
            case 0: // '\0'
                setBomberAttackType(8);
                break;

            case 1: // '\001'
                setBomberAttackType(9);
                break;

            default:
                setBomberAttackType(5);
                break;
            }
            break;

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            if(target.crew > 1)
            {
                switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 6))
                {
                case 0: // '\0'
                    setBomberAttackType(9);
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    setBomberAttackType(0);
                    break;

                default:
                    setBomberAttackType(4);
                    break;
                }
                break;
            }
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 3))
            {
            case 0: // '\0'
                setBomberAttackType(9);
                break;

            default:
                setBomberAttackType(8);
                break;
            }
            break;
        }
        set_maneuver(63);
    }

    private void assignManeuverToWingmen(int i)
    {
        for(com.maddox.il2.ai.air.Pilot pilot = this; pilot.Wingman != null;)
        {
            pilot = (com.maddox.il2.ai.air.Pilot)pilot.Wingman;
            pilot.set_maneuver(i);
        }

    }

    private void assignTaskToWingmen(int i)
    {
        for(com.maddox.il2.ai.air.Pilot pilot = this; pilot.Wingman != null;)
        {
            pilot = (com.maddox.il2.ai.air.Pilot)pilot.Wingman;
            pilot.set_task(i);
        }

    }

    public boolean isLeader()
    {
        if(actor instanceof com.maddox.il2.objects.air.TypeFighter)
            return ((com.maddox.il2.objects.air.Aircraft)actor).aircIndex() % 2 == 0;
        else
            return Leader == null;
    }

    public boolean isLonely(float f, boolean flag)
    {
        if(flag)
        {
            if(Leader == null && Wingman == null)
                return true;
            double d = 0.0D;
            if(Leader != null)
                d = Leader.Loc.distance(Loc);
            if(Wingman != null)
                d = java.lang.Math.min(Wingman.Loc.distance(Loc), d);
            return d > (double)f;
        }
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.air.NearestTargets.getEnemy(9, -1, Loc, f, 0);
        if(com.maddox.il2.engine.Actor.isValid(actor))
            return actor.pos.getAbsPoint().distance(Loc) > (double)f;
        else
            return true;
    }

    public static final int GTARGET_ALL = 0;
    public static final int GTARGET_TANKS = 1;
    public static final int GTARGET_FLAK = 2;
    public static final int GTARGET_VEHICLES = 3;
    public static final int GTARGET_TRAIN = 4;
    public static final int GTARGET_BRIDGE = 5;
    public static final int GTARGET_SHIPS = 6;
    public static final int ATARGET_FIGHTERS = 7;
    public static final int ATARGET_BOMBERS = 8;
    public static final int ATARGET_ALL = 9;
    public static final int TARGET_FIGHTERS = 7;
    public static final int TARGET_BOMBERS = 8;
    public static final int TARGET_ALL = 9;
    private int airTargetType;
    private int groundTargetType;
    private long dumbOffTime;
    private int oldTask;
    private com.maddox.il2.fm.FlightModel oldTaskObject;
    private com.maddox.il2.engine.Actor oldGTarget;
    private boolean continueManeuver;
    private static final com.maddox.JGP.Vector3d MainLook = new Vector3d(0.34202013999999997D, 0.0D, 0.93969259999999999D);
    private static com.maddox.JGP.Vector3d VDanger = new Vector3d();
    private static com.maddox.JGP.Vector3d OnMe = new Vector3d();
    private static com.maddox.JGP.Vector3d diffV = new Vector3d();
    private static double diffVLength = 0.0D;
    private static com.maddox.JGP.Vector3f tmpV = new Vector3f();
    private static com.maddox.JGP.Point3d p1 = new Point3d();
    private static com.maddox.JGP.Point3d p2 = new Point3d();
    private static com.maddox.JGP.Point3f p1f = new Point3f();
    private static com.maddox.JGP.Point3f p2f = new Point3f();
    private boolean Visible;
    private boolean Near;
    private boolean OnBack;
    private boolean Looks;
    private boolean Higher;
    private boolean Faster;
    private boolean Energed;
    private float dist;
    private float dE;
    private com.maddox.il2.engine.Actor act;
    private com.maddox.il2.engine.Actor actg;

}
