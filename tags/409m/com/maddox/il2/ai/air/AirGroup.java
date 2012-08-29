// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AirGroup.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Formation;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.D3A;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.G4M2E;
import com.maddox.il2.objects.air.I_16TYPE24DRONE;
import com.maddox.il2.objects.air.JU_87;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.TB_3_4M_34R_SPB;
import com.maddox.il2.objects.air.TypeBNZFighter;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTNBFighter;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.BombGunParafrag8;
import com.maddox.il2.objects.weapons.TorpedoGun;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.ai.air:
//            AirGroupList, Maneuver, Pilot

public class AirGroup
{

    public java.lang.String grTaskName()
    {
        return GTList[grTask];
    }

    public AirGroup()
    {
        Pos = new Vector3d();
        initVars();
    }

    public AirGroup(com.maddox.il2.ai.Squadron squadron, com.maddox.il2.ai.Way way)
    {
        Pos = new Vector3d();
        initVars();
        sq = squadron;
        w = way;
    }

    public AirGroup(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        Pos = new Vector3d();
        initVars();
        if(airgroup == null)
            return;
        sq = airgroup.sq;
        if(airgroup.w != null)
        {
            w = new Way(airgroup.w);
            w.setCur(airgroup.w.Cur());
        } else
        {
            w = new Way();
            com.maddox.il2.ai.WayPoint waypoint = new WayPoint((float)airgroup.Pos.x, (float)airgroup.Pos.y, (float)airgroup.Pos.z);
            w.add(waypoint);
        }
        Pos.set(airgroup.Pos);
        int i = com.maddox.il2.ai.air.AirGroupList.length(airgroup.enemies[0]);
        for(int j = 0; j < i; j++)
            com.maddox.il2.ai.air.AirGroupList.addAirGroup(enemies, 0, com.maddox.il2.ai.air.AirGroupList.getGroup(airgroup.enemies[0], j));

        i = com.maddox.il2.ai.air.AirGroupList.length(airgroup.friends[0]);
        for(int k = 0; k < i; k++)
            com.maddox.il2.ai.air.AirGroupList.addAirGroup(friends, 0, com.maddox.il2.ai.air.AirGroupList.getGroup(airgroup.friends[0], k));

        rejoinGroup = airgroup;
        gTargetPreference = airgroup.gTargetPreference;
        aTargetPreference = airgroup.aTargetPreference;
        enemyFighters = airgroup.enemyFighters;
        oldEnemyNum = airgroup.oldEnemyNum;
        if(com.maddox.il2.ai.air.AirGroupList.groupInList(com.maddox.il2.ai.War.Groups[0], airgroup))
            com.maddox.il2.ai.air.AirGroupList.addAirGroup(com.maddox.il2.ai.War.Groups, 0, this);
        else
            com.maddox.il2.ai.air.AirGroupList.addAirGroup(com.maddox.il2.ai.War.Groups, 1, this);
    }

    public void initVars()
    {
        nOfAirc = 0;
        airc = new com.maddox.il2.objects.air.Aircraft[16];
        sq = null;
        w = null;
        Pos = new Vector3d(0.0D, 0.0D, 0.0D);
        enemies = new com.maddox.il2.ai.air.AirGroupList[1];
        friends = new com.maddox.il2.ai.air.AirGroupList[1];
        clientGroup = null;
        targetGroup = null;
        leaderGroup = null;
        rejoinGroup = null;
        grAttached = 0;
        gTargetPreference = 0;
        aTargetPreference = 9;
        enemyFighters = false;
        gTargWasFound = false;
        gTargDestroyed = false;
        gTargMode = 0;
        gTargActor = null;
        gTargPoint = new Point3d();
        gTargRadius = 0.0F;
        aTargWasFound = false;
        aTargDestroyed = false;
        WeWereInGAttack = false;
        WeWereInAttack = false;
        formationType = -1;
        fInterpolation = false;
        oldFType = -1;
        oldFScale = 0.0F;
        oldFInterp = false;
        oldEnemyNum = 0;
        timeOutForTaskSwitch = 0;
        grTask = 1;
    }

    public void release()
    {
        for(int i = 0; i < nOfAirc; i++)
        {
            if(airc[i] != null)
                ((com.maddox.il2.ai.air.Maneuver)airc[i].FM).Group = null;
            airc[i] = null;
        }

        nOfAirc = 0;
        sq = null;
        w = null;
        Pos = null;
        if(enemies[0] != null)
            enemies[0].release();
        if(friends[0] != null)
            friends[0].release();
        enemies = null;
        friends = null;
        clientGroup = null;
        targetGroup = null;
        leaderGroup = null;
        rejoinGroup = null;
        gTargPoint = null;
    }

    public void addAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(nOfAirc >= 16)
        {
            java.lang.System.out.print("Group > 16 in squadron " + sq.name());
            return;
        }
        int i;
        if(aircraft.getSquadron() == sq)
            for(i = 0; i < nOfAirc; i++)
                if(airc[i].getSquadron() != sq || airc[i].getWing().indexInSquadron() * 4 + airc[i].aircIndex() > aircraft.getWing().indexInSquadron() * 4 + aircraft.aircIndex())
                    break;

        else
            i = nOfAirc;
        for(int j = nOfAirc - 1; j >= i; j--)
            airc[j + 1] = airc[j];

        airc[i] = aircraft;
        if(w != null)
        {
            aircraft.FM.AP.way = new Way(w);
            aircraft.FM.AP.way.setCur(w.Cur());
        }
        nOfAirc++;
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver)
            ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).Group = this;
    }

    public void delAircraft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        for(int i = 0; i < nOfAirc; i++)
        {
            if(aircraft != airc[i])
                continue;
            ((com.maddox.il2.ai.air.Maneuver)airc[i].FM).Group = null;
            for(int j = i; j < nOfAirc - 1; j++)
                airc[j] = airc[j + 1];

            nOfAirc--;
            break;
        }

        if(grTask == 1 || grTask == 2)
            setTaskAndManeuver(0);
    }

    public void changeAircraft(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        for(int i = 0; i < nOfAirc; i++)
            if(aircraft == airc[i])
            {
                ((com.maddox.il2.ai.air.Maneuver)aircraft.FM).Group = null;
                ((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).Group = this;
                ((com.maddox.il2.ai.air.Maneuver)aircraft1.FM).setBusy(false);
                airc[i] = aircraft1;
                return;
            }

    }

    public void rejoinToGroup(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        if(airgroup == null)
            return;
        for(int i = nOfAirc - 1; i >= 0; i--)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = airc[i];
            delAircraft(aircraft);
            airgroup.addAircraft(aircraft);
        }

        rejoinGroup = null;
    }

    public void attachGroup(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        if(airgroup == null)
            return;
        for(int i = 0; i < nOfAirc; i++)
            if(airc[i].FM instanceof com.maddox.il2.ai.air.Maneuver)
            {
                com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
                if(!(maneuver instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)maneuver).isRealMode())
                {
                    if(maneuver.get_maneuver() == 26)
                        return;
                    if(maneuver.get_maneuver() == 64)
                        return;
                }
            }

        w = null;
        w = new Way(airgroup.w);
        w.setCur(airgroup.w.Cur());
        for(int j = 0; j < nOfAirc; j++)
        {
            airc[j].FM.AP.way = null;
            airc[j].FM.AP.way = new Way(airgroup.w);
            airc[j].FM.AP.way.setCur(airgroup.w.Cur());
        }

        com.maddox.il2.ai.Formation.leaderOffset(airc[0].FM, formationType, airc[0].FM.Offset);
        leaderGroup = airgroup;
        leaderGroup.grAttached++;
        grTask = 1;
        setFormationAndScale(airgroup.formationType, 1.0F, true);
    }

    public void detachGroup(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        if(airgroup == null)
            return;
        leaderGroup.grAttached--;
        if(leaderGroup.grAttached < 0)
            leaderGroup.grAttached = 0;
        leaderGroup = null;
        grTask = 1;
        setTaskAndManeuver(0);
    }

    public int numInGroup(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        for(int i = 0; i < nOfAirc; i++)
            if(aircraft == airc[i])
                return i;

        return -1;
    }

    public void setEnemyFighters()
    {
        int i = com.maddox.il2.ai.air.AirGroupList.length(enemies[0]);
        enemyFighters = false;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(enemies[0], j);
            if(airgroup.nOfAirc > 0 && (airgroup.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
            {
                enemyFighters = true;
                return;
            }
        }

    }

    public void setFormationAndScale(byte byte0, float f, boolean flag)
    {
        if(oldFType == byte0 && oldFScale == f && oldFInterp == flag)
            return;
        fInterpolation = flag;
        for(int i = 1; i < nOfAirc; i++)
        {
            if(airc[i] instanceof com.maddox.il2.objects.air.TypeGlider)
                return;
            ((com.maddox.il2.ai.air.Maneuver)airc[i].FM).formationScale = f;
            com.maddox.il2.ai.Formation.gather(airc[i].FM, byte0, tmpV);
            if(!flag)
                airc[i].FM.Offset.set(tmpV);
            formationType = ((com.maddox.il2.ai.air.Maneuver)airc[i].FM).formationType;
        }

        if(grTask == 1 || grTask == 2)
            setTaskAndManeuver(0);
        oldFType = byte0;
        oldFScale = f;
        oldFInterp = flag;
    }

    public void formationUpdate()
    {
        if(fInterpolation)
        {
            boolean flag = false;
            for(int i = 1; i < nOfAirc; i++)
                if(com.maddox.il2.engine.Actor.isAlive(airc[i]))
                {
                    if(airc[i] instanceof com.maddox.il2.objects.air.TypeGlider)
                        return;
                    com.maddox.il2.ai.Formation.gather(airc[i].FM, formationType, tmpV);
                    tmpV1.sub(tmpV, airc[i].FM.Offset);
                    float f = (float)tmpV1.length();
                    if(f != 0.0F)
                    {
                        flag = true;
                        if(f < 0.1F)
                        {
                            airc[i].FM.Offset.set(tmpV);
                        } else
                        {
                            double d = 0.00040000000000000002D * tmpV1.length();
                            if(d > 1.0D)
                                d = 1.0D;
                            tmpV1.normalize();
                            tmpV1.scale(d);
                            airc[i].FM.Offset.add(tmpV1);
                        }
                    }
                }

            if(!flag)
                fInterpolation = false;
            if(grTask == 1 || grTask == 2)
                setTaskAndManeuver(0);
        }
    }

    public boolean groupsInContact(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        for(int i = 0; i < nOfAirc; i++)
        {
            for(int j = 0; j < airgroup.nOfAirc; j++)
            {
                tmpV.sub(airc[i].FM.Loc, airgroup.airc[j].FM.Loc);
                if(tmpV.lengthSquared() < 50000000D)
                    return true;
            }

        }

        return false;
    }

    public boolean inCorridor(com.maddox.JGP.Point3d point3d)
    {
        if(w == null)
            return true;
        int i = w.Cur();
        if(i == 0)
            return true;
        w.prev();
        tmpP = w.curr().getP();
        w.setCur(i);
        tmpV.sub(w.curr().getP(), tmpP);
        P1P2vector.set(tmpV);
        float f = (float)P1P2vector.length();
        if(f > 0.0001F)
            P1P2vector.scale(1.0F / f);
        else
            P1P2vector.set(1.0D, 0.0D);
        tmpV.sub(point3d, tmpP);
        myPoint.set(tmpV);
        if(P1P2vector.dot(myPoint) < -25000D)
            return false;
        norm1.set(-P1P2vector.y, P1P2vector.x);
        float f1 = (float)norm1.dot(myPoint);
        if(f1 > 25000F)
            return false;
        if(f1 < -25000F)
            return false;
        tmpV.sub(point3d, w.curr().getP());
        myPoint.set(tmpV);
        return P1P2vector.dot(myPoint) <= 25000D;
    }

    public void setGroupTask(int i)
    {
        grTask = i;
        if(grTask == 1 || grTask == 2)
        {
            setTaskAndManeuver(0);
        } else
        {
            for(int j = 0; j < nOfAirc; j++)
                if(!((com.maddox.il2.ai.air.Maneuver)airc[j].FM).isBusy())
                    setTaskAndManeuver(j);

        }
    }

    public void dropBombs()
    {
        for(int i = 0; i < nOfAirc; i++)
            if(!((com.maddox.il2.ai.air.Maneuver)airc[i].FM).isBusy())
                ((com.maddox.il2.ai.air.Maneuver)airc[i].FM).bombsOut = true;

        if(friends[0] != null)
        {
            int j = com.maddox.il2.ai.air.AirGroupList.length(friends[0]);
            for(int k = 0; k < j; k++)
            {
                com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(friends[0], k);
                if(airgroup != null && airgroup.leaderGroup == this)
                    airgroup.dropBombs();
            }

        }
    }

    public com.maddox.il2.objects.air.Aircraft firstOkAirc(int i)
    {
        for(int j = 0; j < nOfAirc; j++)
            if(i < 0 || i >= nOfAirc || j != i && (j != i + 1 || airc[j].aircIndex() != airc[i].aircIndex() + 1))
            {
                com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[j].FM;
                if((maneuver.get_task() == 7 || maneuver.get_task() == 6 || maneuver.get_task() == 4) && maneuver.isOk())
                    return airc[j];
            }

        return null;
    }

    public boolean waitGroup(int i)
    {
        com.maddox.il2.objects.air.Aircraft aircraft = firstOkAirc(i);
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
        if(aircraft != null)
        {
            maneuver.airClient = aircraft.FM;
            maneuver.set_task(1);
            maneuver.clear_stack();
            maneuver.set_maneuver(59);
            return true;
        } else
        {
            maneuver.set_task(3);
            maneuver.clear_stack();
            maneuver.set_maneuver(21);
            return false;
        }
    }

    public void setGTargMode(int i)
    {
        gTargetPreference = i;
    }

    public void setGTargMode(com.maddox.il2.engine.Actor actor)
    {
        if(actor != null && com.maddox.il2.engine.Actor.isAlive(actor))
        {
            if((actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || (actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.ai.Chief) || (actor instanceof com.maddox.il2.objects.bridges.Bridge))
            {
                gTargMode = 1;
                gTargActor = actor;
            } else
            {
                gTargMode = 2;
                gTargActor = actor;
                gTargPoint.set(actor.pos.getAbsPoint());
                gTargRadius = 200F;
                if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
                {
                    gTargRadius = 20F;
                    setGTargMode(6);
                }
            }
        } else
        {
            gTargMode = 0;
        }
    }

    public void setGTargMode(com.maddox.JGP.Point3d point3d, float f)
    {
        gTargMode = 2;
        gTargPoint.set(point3d);
        gTargRadius = f;
    }

    public com.maddox.il2.engine.Actor setGAttackObject(int i)
    {
        if(i > nOfAirc - 1)
            return null;
        if(i < 0)
            return null;
        com.maddox.il2.engine.Actor actor = null;
        if(gTargMode == 1)
            actor = com.maddox.il2.ai.War.GetRandomFromChief(airc[i], gTargActor);
        else
        if(gTargMode == 2)
            actor = com.maddox.il2.ai.War.GetNearestEnemy(airc[i], gTargetPreference, gTargPoint, gTargRadius);
        else
            actor = null;
        if(actor != null)
        {
            gTargWasFound = true;
            gTargDestroyed = false;
        }
        if(actor == null && gTargWasFound)
        {
            gTargDestroyed = true;
            gTargWasFound = false;
        }
        return actor;
    }

    public void setATargMode(int i)
    {
        aTargetPreference = i;
    }

    public com.maddox.il2.ai.air.AirGroup chooseTargetGroup()
    {
        if(enemies == null)
            return null;
        int i = com.maddox.il2.ai.air.AirGroupList.length(enemies[0]);
        com.maddox.il2.ai.air.AirGroup airgroup = null;
        float f = 1E+012F;
        boolean flag = false;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup1 = com.maddox.il2.ai.air.AirGroupList.getGroup(enemies[0], j);
            boolean flag1 = false;
            if(airgroup1 != null && airgroup1.nOfAirc > 0)
            {
                if(aTargetPreference == 9)
                    flag1 = true;
                else
                if(aTargetPreference == 7 && (airgroup1.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
                    flag1 = true;
                else
                if(aTargetPreference == 8 && !(airgroup1.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
                    flag1 = true;
                if(flag1)
                {
                    for(int k = 0; k < airgroup1.nOfAirc; k++)
                    {
                        if(!com.maddox.il2.engine.Actor.isAlive(airgroup1.airc[k]) || !airgroup1.airc[k].FM.isCapableOfBMP() || airgroup1.airc[k].FM.isTakenMortalDamage())
                            continue;
                        flag1 = true;
                        break;
                    }

                }
                if(flag1)
                {
                    tmpV.sub(Pos, airgroup1.Pos);
                    if(tmpV.lengthSquared() < (double)f)
                    {
                        airgroup = airgroup1;
                        f = (float)tmpV.lengthSquared();
                    }
                }
            }
        }

        return airgroup;
    }

    public boolean somebodyAttacks()
    {
        boolean flag = false;
        for(int i = 0; i < nOfAirc; i++)
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
            if((maneuver instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)maneuver).isRealMode() && airc[i].aircIndex() == 0)
            {
                flag = true;
                break;
            }
            if(isWingman(i) || !maneuver.isOk() || !maneuver.hasCourseWeaponBullets())
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public boolean somebodyGAttacks()
    {
        boolean flag = false;
        for(int i = 0; i < nOfAirc; i++)
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
            if((maneuver instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)maneuver).isRealMode() && airc[i].aircIndex() == 0)
            {
                flag = true;
                break;
            }
            if(!maneuver.isOk() || maneuver.get_task() == 1)
                continue;
            flag = true;
            break;
        }

        return flag;
    }

    public void switchWayPoint()
    {
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[0].FM;
        tmpV.sub(w.curr().getP(), maneuver.Loc);
        float f = (float)tmpV.lengthSquared();
        int i = w.Cur();
        w.next();
        tmpV.sub(w.curr().getP(), maneuver.Loc);
        float f1 = (float)tmpV.lengthSquared();
        w.setCur(i);
        if(f > f1)
        {
            java.lang.String s = airc[0].FM.AP.way.curr().getTargetName();
            airc[0].FM.AP.way.next();
            w.next();
            if(airc[0].FM.AP.way.curr().Action == 0 && airc[0].FM.AP.way.curr().getTarget() == null)
                airc[0].FM.AP.way.curr().setTarget(s);
            if(w.curr().getTarget() == null)
                w.curr().setTarget(s);
        }
    }

    public boolean isWingman(int i)
    {
        if(i < 0)
            return false;
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
        if((airc[i].aircIndex() & 1) != 0 && !maneuver.aggressiveWingman)
        {
            if(i > 0)
                maneuver.Leader = airc[i - 1].FM;
            else
                return false;
            if(maneuver.Leader != null && airc[i - 1].aircIndex() == airc[i].aircIndex() - 1 && enemyFighters && com.maddox.il2.engine.Actor.isAlive(airc[i - 1]) && ((com.maddox.il2.ai.air.Maneuver)maneuver.Leader).isOk())
                return true;
        }
        return false;
    }

    public com.maddox.il2.objects.air.Aircraft chooseTarget(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        com.maddox.il2.objects.air.Aircraft aircraft = null;
        if(airgroup != null && airgroup.nOfAirc > 0)
            aircraft = airgroup.airc[com.maddox.il2.ai.World.Rnd().nextInt(0, airgroup.nOfAirc - 1)];
        if(aircraft != null && (!com.maddox.il2.engine.Actor.isAlive(aircraft) || !aircraft.FM.isCapableOfBMP() || aircraft.FM.isTakenMortalDamage()))
        {
            for(int i = 0; i < airgroup.nOfAirc; i++)
                if(com.maddox.il2.engine.Actor.isAlive(airgroup.airc[i]) && airgroup.airc[i].FM.isCapableOfBMP() && !airgroup.airc[i].FM.isTakenMortalDamage())
                    aircraft = airgroup.airc[i];

        }
        return aircraft;
    }

    public com.maddox.il2.fm.FlightModel setAAttackObject(int i)
    {
        if(i > nOfAirc - 1)
            return null;
        if(i < 0)
            return null;
        com.maddox.il2.objects.air.Aircraft aircraft = null;
        com.maddox.il2.ai.air.AirGroup airgroup = targetGroup;
        if(airgroup == null || airgroup.nOfAirc == 0)
            airgroup = chooseTargetGroup();
        aircraft = chooseTarget(airgroup);
        if(aircraft != null)
        {
            aTargWasFound = true;
            aTargDestroyed = false;
        }
        if(aircraft == null && aTargWasFound)
        {
            aTargDestroyed = true;
            aTargWasFound = false;
        }
        if(aircraft != null)
            return aircraft.FM;
        else
            return null;
    }

    public void setTaskAndManeuver(int i)
    {
        if(i > nOfAirc - 1)
            return;
        if(i < 0)
            return;
        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[i].FM;
label0:
        switch(grTask)
        {
        case 1: // '\001'
            Object obj2 = null;
            Object obj = null;
            java.lang.Object obj4 = null;
            tmpV.set(0.0D, 0.0D, 0.0D);
            for(int j = 0; j < nOfAirc; j++)
            {
                com.maddox.il2.ai.air.Maneuver maneuver3 = (com.maddox.il2.ai.air.Maneuver)airc[j].FM;
                if(airc[j] instanceof com.maddox.il2.objects.air.TypeGlider)
                {
                    maneuver3.accurate_set_FOLLOW();
                    continue;
                }
                tmpV.add(maneuver3.Offset);
                if(maneuver3.isBusy() && (!(maneuver3 instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)maneuver3).isRealMode() || !maneuver3.isOk()))
                    continue;
                maneuver3.Leader = null;
                if(leaderGroup == null || leaderGroup.nOfAirc == 0)
                    maneuver3.accurate_set_task_maneuver(3, 21);
                else
                if(((com.maddox.il2.ai.air.Maneuver)leaderGroup.airc[0].FM).isBusy())
                {
                    maneuver3.accurate_set_task_maneuver(3, 21);
                } else
                {
                    maneuver3.accurate_set_FOLLOW();
                    maneuver3.followOffset.set(tmpV);
                    maneuver3.Leader = leaderGroup.airc[0].FM;
                }
                tmpV.set(0.0D, 0.0D, 0.0D);
                for(int k = j + 1; k < nOfAirc; k++)
                {
                    com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)airc[k].FM;
                    tmpV.add(maneuver1.Offset);
                    if(!maneuver1.isBusy())
                    {
                        maneuver1.accurate_set_FOLLOW();
                        if(airc[k] instanceof com.maddox.il2.objects.air.TypeGlider)
                            continue;
                        if(obj4 == null)
                        {
                            maneuver1.followOffset.set(tmpV);
                            maneuver1.Leader = maneuver3;
                        } else
                        {
                            maneuver1.followOffset.set(maneuver1.Offset);
                            maneuver1.Leader = ((com.maddox.il2.fm.FlightModel) (obj4));
                        }
                    }
                    if(maneuver1 instanceof com.maddox.il2.fm.RealFlightModel)
                    {
                        if((airc[k].aircIndex() & 1) == 0)
                            obj4 = maneuver1;
                    } else
                    {
                        obj4 = null;
                    }
                }

                break;
            }

            break;

        case 4: // '\004'
            if(maneuver.isBusy())
                break;
            if(maneuver.target_ground == null || !com.maddox.il2.engine.Actor.isAlive(maneuver.target_ground) || maneuver.Loc.distance(maneuver.target_ground.pos.getAbsPoint()) > 3000D)
                maneuver.target_ground = setGAttackObject(i);
            if(maneuver.target_ground == null)
            {
                if(waitGroup(i) || i != 0)
                    break;
                if(maneuver.AP.way.curr().Action == 3)
                    maneuver.AP.way.next();
                setGroupTask(1);
                break;
            }
            if(airc[i] instanceof com.maddox.il2.objects.air.TypeDockable)
            {
                if(airc[i] instanceof com.maddox.il2.objects.air.I_16TYPE24DRONE)
                    ((com.maddox.il2.objects.air.I_16TYPE24DRONE)airc[i]).typeDockableAttemptDetach();
                if(airc[i] instanceof com.maddox.il2.objects.air.MXY_7)
                    ((com.maddox.il2.objects.air.MXY_7)airc[i]).typeDockableAttemptDetach();
                if(airc[i] instanceof com.maddox.il2.objects.air.G4M2E)
                    ((com.maddox.il2.objects.air.G4M2E)airc[i]).typeDockableAttemptDetach();
                if(airc[i] instanceof com.maddox.il2.objects.air.TB_3_4M_34R_SPB)
                    ((com.maddox.il2.objects.air.TB_3_4M_34R_SPB)airc[i]).typeDockableAttemptDetach();
            }
            if(maneuver.AP.way.Cur() == maneuver.AP.way.size() - 1 && maneuver.AP.way.curr().Action == 3 || (airc[i] instanceof com.maddox.il2.objects.air.MXY_7))
            {
                maneuver.kamikaze = true;
                maneuver.set_task(7);
                maneuver.clear_stack();
                maneuver.set_maneuver(46);
                break;
            }
            boolean flag = true;
            if(maneuver.hasRockets())
                flag = false;
            if(maneuver.CT.Weapons[0] != null && maneuver.CT.Weapons[0][0] != null && maneuver.CT.Weapons[0][0].bulletMassa() > 0.05F && maneuver.CT.Weapons[0][0].countBullets() > 0)
                flag = false;
            if(flag && maneuver.CT.getWeaponMass() < 7F || maneuver.CT.getWeaponMass() < 1.0F)
            {
                com.maddox.il2.objects.sounds.Voice.speakEndOfAmmo(airc[i]);
                if(waitGroup(i) || i != 0)
                    break;
                if(maneuver.AP.way.curr().Action == 3)
                    maneuver.AP.way.next();
                setGroupTask(1);
                break;
            }
            if((maneuver.target_ground instanceof com.maddox.il2.ai.ground.Prey) && (((com.maddox.il2.ai.ground.Prey)maneuver.target_ground).HitbyMask() & 1) == 0)
            {
                float f = 0.0F;
                for(int l = 0; l < 4; l++)
                    if(maneuver.CT.Weapons[l] != null)
                    {
                        for(int l1 = 0; l1 < maneuver.CT.Weapons[l].length; l1++)
                            if(maneuver.CT.Weapons[l][l1] != null && maneuver.CT.Weapons[l][l1].countBullets() != 0 && maneuver.CT.Weapons[l][l1].bulletMassa() > f)
                                f = maneuver.CT.Weapons[l][l1].bulletMassa();

                    }

                if(f < 0.08F || (maneuver.target_ground instanceof com.maddox.il2.ai.ground.TgtShip) && f < 0.55F)
                {
                    maneuver.AP.way.next();
                    maneuver.set_task(1);
                    maneuver.clear_stack();
                    maneuver.set_maneuver(21);
                    maneuver.target_ground = null;
                    break;
                }
            }
            if(maneuver.CT.Weapons[3] != null && maneuver.CT.Weapons[3][0] != null && maneuver.CT.Weapons[3][0].countBullets() != 0)
            {
                if(maneuver.CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.TorpedoGun)
                {
                    if(maneuver.target_ground instanceof com.maddox.il2.ai.ground.TgtShip)
                    {
                        maneuver.set_task(7);
                        maneuver.clear_stack();
                        maneuver.set_maneuver(51);
                    } else
                    {
                        maneuver.set_task(7);
                        maneuver.clear_stack();
                        maneuver.set_maneuver(43);
                    }
                    break;
                }
                if(maneuver.CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara)
                {
                    w.curr().setTarget(null);
                    maneuver.target_ground = null;
                    grTask = 1;
                    setTaskAndManeuver(i);
                    break;
                }
                if(maneuver.CT.Weapons[3][0].bulletMassa() < 10F && !(maneuver.CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunParafrag8))
                {
                    maneuver.set_task(7);
                    maneuver.clear_stack();
                    maneuver.set_maneuver(52);
                    break;
                }
                if((airc[i] instanceof com.maddox.il2.objects.air.TypeDiveBomber) && maneuver.Alt > 1200F)
                {
                    maneuver.set_task(7);
                    maneuver.clear_stack();
                    maneuver.set_maneuver(50);
                } else
                {
                    maneuver.set_task(7);
                    maneuver.clear_stack();
                    maneuver.set_maneuver(43);
                }
                break;
            }
            if((maneuver.target_ground instanceof com.maddox.il2.objects.bridges.BridgeSegment) && !maneuver.hasRockets())
            {
                maneuver.set_task(1);
                maneuver.clear_stack();
                maneuver.set_maneuver(59);
                maneuver.target_ground = null;
                break;
            }
            if((airc[i] instanceof com.maddox.il2.objects.air.F4U) && maneuver.CT.Weapons[2] != null && (double)maneuver.CT.Weapons[2][0].bulletMassa() > 100D && maneuver.CT.Weapons[2][0].countBullets() > 0)
            {
                maneuver.set_task(7);
                maneuver.clear_stack();
                maneuver.set_maneuver(47);
                break;
            }
            if((airc[i] instanceof com.maddox.il2.objects.air.TypeFighter) || (airc[i] instanceof com.maddox.il2.objects.air.TypeStormovik))
            {
                maneuver.set_task(7);
                maneuver.clear_stack();
                maneuver.set_maneuver(43);
            } else
            {
                w.curr().setTarget(null);
                maneuver.target_ground = null;
                grTask = 1;
                setTaskAndManeuver(i);
                grTask = 4;
            }
            break;

        case 3: // '\003'
            if(maneuver.isBusy())
                break;
            if(!(maneuver instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)maneuver).isRealMode())
            {
                maneuver.bombsOut = true;
                maneuver.CT.dropFuelTanks();
            }
            if(isWingman(i))
            {
                maneuver.airClient = maneuver.Leader;
                maneuver.followOffset.set(200D, 0.0D, 20D);
                maneuver.set_task(5);
                maneuver.clear_stack();
                maneuver.set_maneuver(65);
                break;
            }
            maneuver.airClient = null;
            boolean flag1 = true;
            if(inCorridor(maneuver.Loc))
                flag1 = false;
            if(flag1)
            {
                int i1 = w.Cur();
                w.next();
                if(inCorridor(maneuver.Loc))
                    flag1 = false;
                w.setCur(i1);
                if(flag1)
                {
                    int j1 = w.Cur();
                    w.prev();
                    if(inCorridor(maneuver.Loc))
                        flag1 = false;
                    w.setCur(j1);
                }
            }
            if(flag1)
            {
                maneuver.set_task(3);
                maneuver.clear_stack();
                maneuver.set_maneuver(21);
                break;
            }
            if(maneuver.target == null || !((com.maddox.il2.ai.air.Maneuver)maneuver.target).isOk() || maneuver.Loc.distance(maneuver.target.Loc) > 4000D)
                maneuver.target = setAAttackObject(i);
            if(maneuver.target == null || !maneuver.hasCourseWeaponBullets())
            {
                if(!waitGroup(i) && i == 0)
                    setGroupTask(1);
                break;
            }
            maneuver.set_task(6);
            if(maneuver.target.actor instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                if((maneuver.actor instanceof com.maddox.il2.objects.air.TypeBNZFighter) || maneuver.VmaxH > maneuver.target.VmaxH + 30F || (maneuver.target.actor instanceof com.maddox.il2.objects.air.TypeTNBFighter) && !(maneuver.actor instanceof com.maddox.il2.objects.air.TypeTNBFighter))
                {
                    maneuver.clear_stack();
                    maneuver.set_maneuver(62);
                } else
                {
                    maneuver.clear_stack();
                    maneuver.set_maneuver(27);
                }
                break;
            }
            if(maneuver.target.actor instanceof com.maddox.il2.objects.air.TypeStormovik)
                ((com.maddox.il2.ai.air.Pilot)maneuver).attackStormoviks();
            else
                ((com.maddox.il2.ai.air.Pilot)maneuver).attackBombers();
            break;

        case 2: // '\002'
            Object obj3 = null;
            Object obj1 = null;
            java.lang.Object obj5 = null;
            tmpV.set(0.0D, 0.0D, 0.0D);
            for(int k1 = 0; k1 < nOfAirc; k1++)
            {
                com.maddox.il2.ai.air.Maneuver maneuver4 = (com.maddox.il2.ai.air.Maneuver)airc[k1].FM;
                tmpV.add(maneuver4.Offset);
                if(!maneuver4.isBusy() || k1 == nOfAirc - 1 || (maneuver4 instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)maneuver4).isRealMode())
                {
                    maneuver4.Leader = null;
                    if(clientGroup != null && clientGroup.nOfAirc > 0 && clientGroup.airc[0] != null)
                    {
                        maneuver4.airClient = clientGroup.airc[0].FM;
                        maneuver4.accurate_set_task_maneuver(5, 59);
                    } else
                    {
                        maneuver4.accurate_set_task_maneuver(3, 21);
                    }
                    tmpV.set(0.0D, 0.0D, 0.0D);
                    for(int i2 = k1 + 1; i2 < nOfAirc; i2++)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver2 = (com.maddox.il2.ai.air.Maneuver)airc[i2].FM;
                        tmpV.add(maneuver2.Offset);
                        if(!maneuver2.isBusy())
                        {
                            maneuver2.accurate_set_FOLLOW();
                            if(obj5 == null)
                            {
                                maneuver2.followOffset.set(tmpV);
                                maneuver2.Leader = maneuver4;
                            } else
                            {
                                maneuver2.followOffset.set(maneuver2.Offset);
                                maneuver2.Leader = ((com.maddox.il2.fm.FlightModel) (obj5));
                            }
                        }
                        if(maneuver2 instanceof com.maddox.il2.fm.RealFlightModel)
                        {
                            if((airc[i2].aircIndex() & 1) == 0)
                                obj5 = maneuver2;
                        } else
                        {
                            obj5 = null;
                        }
                    }

                    break label0;
                }
            }

            break;

        case 5: // '\005'
            if(maneuver.isBusy())
                return;
            break;

        case 6: // '\006'
            if(maneuver.isBusy())
                return;
            break;

        default:
            if(maneuver.isBusy())
                return;
            maneuver.set_maneuver(21);
            break;
        }
    }

    public void update()
    {
        if(nOfAirc == 0 || airc[0] == null)
            return;
        for(int i = 1; i < nOfAirc; i++)
            if(!com.maddox.il2.engine.Actor.isAlive(airc[i]))
            {
                delAircraft(airc[i]);
                i--;
            }

        com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)airc[0].FM;
        if(leaderGroup != null)
            if(leaderGroup.nOfAirc == 0)
                detachGroup(leaderGroup);
            else
            if(leaderGroup.airc[0] == null)
                detachGroup(leaderGroup);
            else
            if(leaderGroup.airc[0].FM.AP.way.isLanding())
            {
                detachGroup(leaderGroup);
            } else
            {
                maneuver.AP.way.setCur(leaderGroup.w.Cur());
                if(maneuver.get_maneuver() == 21 && !((com.maddox.il2.ai.air.Maneuver)leaderGroup.airc[0].FM).isBusy())
                    setTaskAndManeuver(0);
            }
        if(w == null)
            w = new Way(maneuver.AP.way);
        if(!maneuver.AP.way.isLanding() && maneuver.isOk())
            w.setCur(maneuver.AP.way.Cur());
        if(!maneuver.AP.way.isLanding())
        {
            for(int j = 1; j < nOfAirc; j++)
                if(!((com.maddox.il2.ai.air.Maneuver)airc[j].FM).AP.way.isLanding() && !((com.maddox.il2.ai.air.Maneuver)airc[j].FM).isBusy())
                    ((com.maddox.il2.ai.air.Maneuver)airc[j].FM).AP.way.setCur(w.Cur());

        }
        if(maneuver.AP.way.curr().isRadioSilence())
        {
            for(int k = 0; k < nOfAirc; k++)
                ((com.maddox.il2.ai.air.Maneuver)airc[k].FM).silence = true;

        } else
        {
            for(int l = 0; l < nOfAirc; l++)
                ((com.maddox.il2.ai.air.Maneuver)airc[l].FM).silence = false;

        }
        Pos.set(maneuver.Loc);
        if(formationType == -1)
            setFormationAndScale((byte)0, 1.0F, true);
        if(timeOutForTaskSwitch == 0)
            switch(w.curr().Action)
            {
            case 3: // '\003'
                boolean flag = w.curr().getTarget() != null || (airc[0] instanceof com.maddox.il2.objects.air.TypeFighter) || (airc[0] instanceof com.maddox.il2.objects.air.TypeStormovik) || (airc[0] instanceof com.maddox.il2.objects.air.D3A) || (airc[0] instanceof com.maddox.il2.objects.air.MXY_7) || (airc[0] instanceof com.maddox.il2.objects.air.JU_87);
                if(grTask == 4)
                {
                    WeWereInGAttack = true;
                    boolean flag1 = somebodyGAttacks();
                    boolean flag6 = false;
                    for(int l2 = 0; l2 < nOfAirc; l2++)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver3 = (com.maddox.il2.ai.air.Maneuver)airc[l2].FM;
                        if(maneuver3.gattackCounter >= 7)
                            flag6 = true;
                    }

                    if(!flag1 || flag6 || gTargDestroyed)
                    {
                        airc[0].FM.AP.way.next();
                        w.next();
                        setGroupTask(1);
                        for(int l3 = 1; l3 < nOfAirc; l3++)
                        {
                            com.maddox.il2.ai.air.Maneuver maneuver5 = (com.maddox.il2.ai.air.Maneuver)airc[l3].FM;
                            maneuver5.push(57);
                            maneuver5.pop();
                        }

                        setFormationAndScale((byte)0, 1.0F, true);
                        if(flag6)
                        {
                            for(int k4 = 0; k4 < nOfAirc; k4++)
                                ((com.maddox.il2.ai.air.Maneuver)airc[k4].FM).gattackCounter = 0;

                        }
                    }
                } else
                if(grTask == 3)
                {
                    switchWayPoint();
                    WeWereInGAttack = true;
                    if(com.maddox.il2.ai.air.AirGroupList.length(enemies[0]) != oldEnemyNum)
                        setGroupTask(3);
                    boolean flag2 = somebodyAttacks();
                    if(!flag2 || aTargDestroyed)
                    {
                        setGroupTask(1);
                        if(!flag2)
                            timeOutForTaskSwitch = 90;
                        for(int i2 = 1; i2 < nOfAirc; i2++)
                        {
                            com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)airc[i2].FM;
                            if(!maneuver1.isBusy())
                            {
                                maneuver1.push(57);
                                maneuver1.pop();
                            }
                        }

                    }
                }
                if(grTask != 1 || w.curr().Action != 3)
                    break;
                gTargWasFound = false;
                gTargDestroyed = false;
                gTargMode = 0;
                if(!flag)
                    break;
                setFormationAndScale((byte)5, 8F, true);
                if(maneuver.AP.getWayPointDistance() >= 5000F)
                    break;
                boolean flag3 = false;
                if(w.curr().getTarget() != null)
                {
                    setGTargMode(w.curr().getTarget());
                    if(gTargMode != 0)
                    {
                        maneuver.target_ground = setGAttackObject(0);
                        if(maneuver.target_ground != null && maneuver.target_ground.distance(airc[0]) < 12000D)
                        {
                            setGroupTask(4);
                            com.maddox.il2.objects.sounds.Voice.speakBeginGattack(airc[0]);
                        } else
                        if(maneuver.AP.getWayPointDistance() < 1500F)
                            flag3 = true;
                    } else
                    {
                        flag3 = true;
                    }
                } else
                {
                    flag3 = true;
                }
                if(!flag3)
                    break;
                com.maddox.il2.engine.Engine.land();
                tmpP3d.set(w.curr().x(), w.curr().y(), com.maddox.il2.engine.Landscape.HQ(w.curr().x(), w.curr().y()));
                setGTargMode(tmpP3d, 800F);
                maneuver.target_ground = setGAttackObject(0);
                if(maneuver.target_ground != null)
                {
                    setGroupTask(4);
                    com.maddox.il2.objects.sounds.Voice.speakBeginGattack(airc[0]);
                }
                break;

            case 0: // '\0'
            case 2: // '\002'
                if(grTask == 2)
                {
                    if(enemyFighters)
                    {
                        int i1 = com.maddox.il2.ai.air.AirGroupList.length(enemies[0]);
                        for(int j2 = 0; j2 < i1; j2++)
                        {
                            com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(enemies[0], j2);
                            if(airgroup.nOfAirc <= 0 || !(airgroup.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
                                continue;
                            targetGroup = airgroup;
                            setGroupTask(3);
                            break;
                        }

                    }
                    if(w.Cur() >= w.size() - 1)
                    {
                        setGroupTask(1);
                        setFormationAndScale((byte)0, 1.0F, true);
                    }
                    if(clientGroup == null || clientGroup.nOfAirc == 0 || clientGroup.w.Cur() >= clientGroup.w.size() - 1 || clientGroup.airc[0].FM.AP.way.isLanding())
                    {
                        maneuver.AP.way.next();
                        w.setCur(maneuver.AP.way.Cur());
                        for(int j1 = 1; j1 < nOfAirc; j1++)
                            ((com.maddox.il2.ai.air.Maneuver)airc[j1].FM).AP.way.setCur(w.Cur());

                        setGroupTask(1);
                        setFormationAndScale((byte)0, 1.0F, true);
                    }
                    switchWayPoint();
                    break;
                }
                if(grTask == 3)
                {
                    switchWayPoint();
                    WeWereInGAttack = true;
                    if(com.maddox.il2.ai.air.AirGroupList.length(enemies[0]) != oldEnemyNum)
                        setGroupTask(3);
                    boolean flag4 = somebodyAttacks();
                    if(flag4 && !aTargDestroyed)
                        break;
                    setGroupTask(1);
                    setFormationAndScale((byte)0, 1.0F, false);
                    if(!flag4)
                        timeOutForTaskSwitch = 90;
                    for(int k2 = 1; k2 < nOfAirc; k2++)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver2 = (com.maddox.il2.ai.air.Maneuver)airc[k2].FM;
                        if(!maneuver2.isBusy())
                        {
                            maneuver2.push(57);
                            maneuver2.pop();
                        }
                    }

                    break;
                }
                if(grTask == 4)
                {
                    WeWereInGAttack = true;
                    boolean flag5 = somebodyGAttacks();
                    boolean flag7 = false;
                    for(int i3 = 0; i3 < nOfAirc; i3++)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver4 = (com.maddox.il2.ai.air.Maneuver)airc[i3].FM;
                        if(maneuver4.gattackCounter >= 7)
                            flag7 = true;
                    }

                    if(flag5 && !flag7 && !gTargDestroyed)
                        break;
                    setGroupTask(1);
                    setFormationAndScale((byte)0, 1.0F, true);
                    for(int i4 = 1; i4 < nOfAirc; i4++)
                    {
                        com.maddox.il2.ai.air.Maneuver maneuver6 = (com.maddox.il2.ai.air.Maneuver)airc[i4].FM;
                        maneuver6.push(57);
                        maneuver6.pop();
                    }

                    if(!flag7)
                        break;
                    for(int l4 = 0; l4 < nOfAirc; l4++)
                        ((com.maddox.il2.ai.air.Maneuver)airc[l4].FM).gattackCounter = 0;

                    break;
                }
                if(grTask != 1)
                    break;
                if(WeWereInGAttack || gTargMode != 0)
                {
                    WeWereInGAttack = false;
                    gTargMode = 0;
                    setFormationAndScale((byte)0, 1.0F, false);
                    ((com.maddox.il2.ai.air.Maneuver)airc[0].FM).WeWereInGAttack = true;
                }
                if(WeWereInAttack)
                {
                    WeWereInAttack = false;
                    setFormationAndScale((byte)0, 1.0F, false);
                    ((com.maddox.il2.ai.air.Maneuver)airc[0].FM).WeWereInAttack = true;
                }
                if(w.Cur() > 0 && grAttached == 0 && oldFType == 0)
                {
                    w.curr().getP(tmpP);
                    tmpV.sub(tmpP, Pos);
                    if(tmpV.lengthSquared() < 4000000D)
                        setFormationAndScale((byte)0, 2.5F, true);
                    else
                        setFormationAndScale((byte)0, 1.0F, true);
                }
                int k1 = w.Cur();
                w.next();
                if(w.curr().Action == 2 || w.curr().Action == 3 && (w.curr().getTarget() != null && !(airc[0] instanceof com.maddox.il2.objects.air.Scheme4) || (airc[0] instanceof com.maddox.il2.objects.air.TypeStormovik) || (airc[0] instanceof com.maddox.il2.objects.air.JU_87)))
                {
                    w.curr().getP(tmpP);
                    tmpV.sub(tmpP, Pos);
                    float f = (float)tmpV.length();
                    if(f < 20000F)
                        setFormationAndScale((byte)5, 8F, true);
                }
                w.setCur(k1);
                if(w.curr().getTarget() != null)
                {
                    com.maddox.il2.engine.Actor actor = w.curr().getTargetActorRandom();
                    if(actor != null && (actor instanceof com.maddox.il2.objects.air.Aircraft))
                    {
                        tmpV.sub(((com.maddox.il2.objects.air.Aircraft)actor).FM.Loc, Pos);
                        if(tmpV.lengthSquared() < 144000000D)
                            if(actor.getArmy() == airc[0].getArmy())
                            {
                                if((airc[0] instanceof com.maddox.il2.objects.air.TypeFighter) && !maneuver.hasBombs() && !maneuver.hasRockets())
                                {
                                    if(w.Cur() < w.size() - 2)
                                    {
                                        clientGroup = ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).Group;
                                        setGroupTask(2);
                                        setFormationAndScale((byte)0, 2.5F, true);
                                    }
                                } else
                                {
                                    attachGroup(((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).Group);
                                }
                            } else
                            if((airc[0] instanceof com.maddox.il2.objects.air.TypeFighter) || (airc[0] instanceof com.maddox.il2.objects.air.TypeStormovik))
                            {
                                targetGroup = ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).Group;
                                setGroupTask(3);
                            }
                    }
                } else
                if(com.maddox.il2.ai.air.AirGroupList.length(enemies[0]) > 0)
                {
                    boolean flag8 = false;
                    if(airc[0] instanceof com.maddox.il2.objects.air.TypeStormovik)
                    {
                        int j3 = com.maddox.il2.ai.air.AirGroupList.length(enemies[0]);
                        for(int j4 = 0; j4 < j3; j4++)
                        {
                            com.maddox.il2.ai.air.AirGroup airgroup1 = com.maddox.il2.ai.air.AirGroupList.getGroup(enemies[0], j4);
                            if(airgroup1 == null || airgroup1.nOfAirc == 0 || (airgroup1.airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
                                continue;
                            flag8 = true;
                            targetGroup = airgroup1;
                            break;
                        }

                        if(flag8)
                        {
                            if(maneuver.hasBombs())
                                flag8 = false;
                            int l1 = w.Cur();
                            for(; flag8 && w.Cur() < w.size() - 1; w.next())
                                if(w.curr().Action == 3)
                                    flag8 = false;

                            w.setCur(l1);
                        }
                    }
                    if(!flag8 && (airc[0] instanceof com.maddox.il2.objects.air.TypeFighter))
                    {
                        for(int k3 = 0; k3 < nOfAirc; k3++)
                            if(((com.maddox.il2.ai.air.Maneuver)airc[k3].FM).canAttack())
                                flag8 = true;

                        if(flag8 && maneuver.CT.getWeaponMass() > 220F)
                            flag8 = false;
                    }
                    if(flag8)
                        setGroupTask(3);
                }
                if(rejoinGroup != null)
                    rejoinToGroup(rejoinGroup);
                break;

            case 1: // '\001'
            default:
                grTask = 1;
                break;
            }
        oldEnemyNum = com.maddox.il2.ai.air.AirGroupList.length(enemies[0]);
        if(timeOutForTaskSwitch > 0)
            timeOutForTaskSwitch--;
    }

    public int nOfAirc;
    public com.maddox.il2.objects.air.Aircraft airc[];
    public com.maddox.il2.ai.Squadron sq;
    public com.maddox.il2.ai.Way w;
    public com.maddox.JGP.Vector3d Pos;
    public com.maddox.il2.ai.air.AirGroupList enemies[];
    public com.maddox.il2.ai.air.AirGroupList friends[];
    public com.maddox.il2.ai.air.AirGroup clientGroup;
    public com.maddox.il2.ai.air.AirGroup targetGroup;
    public com.maddox.il2.ai.air.AirGroup leaderGroup;
    public com.maddox.il2.ai.air.AirGroup rejoinGroup;
    public int grAttached;
    public int gTargetPreference;
    public int aTargetPreference;
    public boolean enemyFighters;
    private boolean gTargWasFound;
    private boolean gTargDestroyed;
    private int gTargMode;
    private com.maddox.il2.engine.Actor gTargActor;
    private com.maddox.JGP.Point3d gTargPoint;
    private float gTargRadius;
    private boolean aTargWasFound;
    private boolean aTargDestroyed;
    private boolean WeWereInGAttack;
    private boolean WeWereInAttack;
    public byte formationType;
    private byte oldFType;
    private float oldFScale;
    private boolean oldFInterp;
    public boolean fInterpolation;
    private int oldEnemyNum;
    public int timeOutForTaskSwitch;
    public int grTask;
    public static final int FLY_WAYPOINT = 1;
    public static final int DEFENDING = 2;
    public static final int ATTACK_AIR = 3;
    public static final int ATTACK_GROUND = 4;
    public static final int TAKEOFF = 5;
    public static final int LANDING = 6;
    public static final int GT_MODE_NONE = 0;
    public static final int GT_MODE_CHIEF = 1;
    public static final int GT_MODE_AROUND_POINT = 2;
    private static java.lang.String GTList[] = {
        "NO_TASK.", "FLY_WAYPOINT", "DEFENDING", "ATTACK_AIR", "ATTACK_GROUND", "TAKEOFF", "LANDING"
    };
    private static com.maddox.JGP.Vector3d tmpV = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV1 = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV3d = new Vector3d();
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.JGP.Point3d tmpP3d = new Point3d();
    private static com.maddox.JGP.Vector2d P1P2vector = new Vector2d();
    private static com.maddox.JGP.Vector2d norm1 = new Vector2d();
    private static com.maddox.JGP.Vector2d norm2 = new Vector2d();
    private static com.maddox.JGP.Vector2d myPoint = new Vector2d();
    private static com.maddox.JGP.Vector3f tmpVf = new Vector3f();

}
