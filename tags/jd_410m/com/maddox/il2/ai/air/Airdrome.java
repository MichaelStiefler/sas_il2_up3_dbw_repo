package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Time;
import java.util.ArrayList;

public class Airdrome
{
  public static float CONN_DIST = 10.0F;
  public Point_Runaway[][] runw;
  public Point_Taxi[][] taxi;
  public Point_Stay[][] stay;
  public boolean[] stayHold;
  AiardromePoint[] aPoints;
  int poiNum;
  AiardromeLine[] aLines;
  int lineNum;
  Point_Any[] airdromeWay;
  Point3d testParkPoint;
  ArrayList airdromeList;
  private static Point3d P = new Point3d();
  private static Point2f Pcur = new Point2f();
  private static Vector2f Vcur = new Vector2f();
  private static Vector2f V_to = new Vector2f();
  private static Vector2f Vdiff = new Vector2f();

  private static Vector2f V_pn = new Vector2f();
  private static Vector2f Vrun = new Vector2f();
  private static Orient tmpOr = new Orient();

  public Airdrome()
  {
    this.aPoints = new AiardromePoint[512];
    this.poiNum = 0;
    this.aLines = new AiardromeLine[512];
    this.lineNum = 0;
    this.airdromeWay = new Point_Any[512];
    this.testParkPoint = new Point3d();
    this.airdromeList = new ArrayList();

    for (int i = 0; i < 512; i++) this.aPoints[i] = new AiardromePoint();
    for (i = 0; i < 512; i++) this.aLines[i] = new AiardromeLine();
    for (i = 0; i < 512; i++) this.airdromeWay[i] = new Point_Any(0.0F, 0.0F);
  }

  private void freeStayPoint(Point_Any paramPoint_Any)
  {
    if (paramPoint_Any == null) return;

    if ((paramPoint_Any instanceof Point_Stay))
      for (int i = 0; i < this.stayHold.length; i++)
        for (int j = 0; j < this.stay[i].length - 1; j++)
          if (paramPoint_Any == this.stay[i][j]) {
            this.stayHold[i] = false;
            return;
          }
  }

  public void findTheWay(Pilot paramPilot)
  {
    int i3 = 0; int i4 = 0;

    this.poiNum = 0;
    this.lineNum = 0;

    Vrun.x = (float)paramPilot.Vwld.x;
    Vrun.y = (float)paramPilot.Vwld.y;
    Point_Null localPoint_Null = new Point_Null((float)paramPilot.Loc.x, (float)paramPilot.Loc.y);

    int m = -1; int n = -1;
    int i1 = -1; int i2 = -1;
    float f2;
    float f3 = f2 = 2000.0F;
    int j;
    float f1;
    for (int i = 0; i < this.runw.length; i++) {
      for (j = 0; j < this.runw[i].length; j++) {
        f1 = localPoint_Null.distance(this.runw[i][j]);
        if (f1 < f2) {
          f2 = f1; m = i; n = j;
        }
        if (f1 < f3) {
          V_pn.sub(this.runw[i][j], localPoint_Null);
          V_pn.normalize();
          Vrun.normalize();
          if (V_pn.dot(Vrun) > 0.9F) {
            f3 = f1; i1 = i; i2 = j;
          }
        }
      }
    }
    this.aPoints[this.poiNum].poiCounter = 0;
    if (i1 >= 0) this.aPoints[(this.poiNum++)].poi = this.runw[i1][i2];
    else if (m >= 0) this.aPoints[(this.poiNum++)].poi = this.runw[m][n];
    int i5;
    for (i = 0; i < this.stay.length; i++) {
      if (this.stay[i].length >= 2) {
        f1 = localPoint_Null.distance(this.stay[i][1]);
        if ((f1 < 2000.0F) && (this.stayHold[i] == 0)) {
          Engine.land(); this.testParkPoint.set(this.stay[i][1].x, this.stay[i][1].y, Landscape.HQ_Air(this.stay[i][1].x, this.stay[i][1].y));
          Engine.collideEnv().getSphere(this.airdromeList, this.testParkPoint, 1.5F * paramPilot.actor.collisionR() + 10.0F);
          i5 = this.airdromeList.size();
          this.airdromeList.clear();
          if (i5 == 0) {
            this.aLines[this.lineNum].to = this.poiNum;
            this.aPoints[this.poiNum].poiCounter = (777 + i); this.aPoints[(this.poiNum++)].poi = this.stay[i][1];
            this.aLines[(this.lineNum++)].from = this.poiNum;
            this.aPoints[this.poiNum].poiCounter = 255; this.aPoints[(this.poiNum++)].poi = this.stay[i][0];
          }
        }
      }
    }

    if (this.poiNum >= 3)
    {
      m = -1; n = -1;
      int k;
      for (i = 0; i < this.taxi.length; i++) {
        if ((this.taxi[i].length < 2) || 
          (localPoint_Null.distance(this.taxi[i][0]) > 2000.0F))
          continue;
        i6 = 0;
        for (k = 0; k < this.poiNum; k++) {
          if (this.aPoints[k].poi.distance(this.taxi[i][0]) < 18.0F) {
            i3 = k;
            i6 = 1;
            break;
          }
        }
        if (i6 == 0) {
          i3 = this.poiNum;
          this.aPoints[this.poiNum].poiCounter = 255; this.aPoints[(this.poiNum++)].poi = this.taxi[i][0];
        }

        for (j = 1; j < this.taxi[i].length; j++) {
          i6 = 0;
          for (k = 0; k < this.poiNum; k++) {
            if (this.aPoints[k].poi.distance(this.taxi[i][j]) < 18.0F) {
              i4 = k;
              i6 = 1;
              break;
            }
          }
          if (i6 == 0) {
            i4 = this.poiNum;
            this.aPoints[this.poiNum].poiCounter = 255; this.aPoints[(this.poiNum++)].poi = this.taxi[i][j];
          }
          this.aLines[this.lineNum].from = i3; this.aLines[(this.lineNum++)].to = i4;
          i3 = i4;
        }
      }

      for (i = 0; i < this.poiNum; i++) {
        Engine.land(); this.testParkPoint.set(this.aPoints[i].poi.x, this.aPoints[i].poi.y, Landscape.HQ_Air(this.aPoints[i].poi.x, this.aPoints[i].poi.y));
        Engine.collideEnv().getSphere(this.airdromeList, this.testParkPoint, 1.2F * paramPilot.actor.collisionR() + 3.0F);
        i5 = this.airdromeList.size();
        if ((i5 == 1) && ((this.airdromeList.get(0) instanceof Aircraft))) i5 = 0;
        this.airdromeList.clear();
        if (i5 <= 0) continue; this.aPoints[i].poiCounter = -100;
      }

      for (int i6 = 0; i6 < 255; i6++) {
        int i7 = 0;
        for (i = 0; i < this.poiNum; i++) {
          if (this.aPoints[i].poiCounter == i6)
            for (j = 0; j < this.lineNum; j++) {
              int i8 = 0;
              if (this.aLines[j].to == i) i8 = this.aLines[j].from;
              if (this.aLines[j].from == i) i8 = this.aLines[j].to;
              if (i8 != 0) {
                if (this.aPoints[i8].poiCounter >= 777)
                {
                  this.aPoints[i8].from = i;
                  this.stayHold[(this.aPoints[i8].poiCounter - 777)] = true;

                  int i9 = i8;
                  k = 0;
                  while ((i9 > 0) || (k > 128))
                  {
                    this.airdromeWay[(k++)] = this.aPoints[i9].poi;
                    i9 = this.aPoints[i9].from;
                  }

                  this.airdromeWay[(k++)] = this.aPoints[0].poi;
                  paramPilot.airdromeWay = new Point_Any[k];
                  for (int i10 = 0; i10 < k; i10++) {
                    paramPilot.airdromeWay[i10] = new Point_Any(0.0F, 0.0F);
                    paramPilot.airdromeWay[i10].set(this.airdromeWay[(k - i10 - 1)]);
                  }
                  return;
                }
                if (i6 + 1 < this.aPoints[i8].poiCounter) {
                  this.aPoints[i8].poiCounter = (i6 + 1);
                  this.aPoints[i8].from = i;
                  i7 = 1;
                }
              }
            }
        }
        if (i7 == 0)
          break;
      }
    }
    World.cur(); if (paramPilot.actor != World.getPlayerAircraft()) {
      MsgDestroy.Post(Time.current() + 30000L, paramPilot.actor);
      paramPilot.setStationedOnGround(true);
    }
    if (this.poiNum > 0) {
      paramPilot.airdromeWay = new Point_Any[this.poiNum];
      for (i = 0; i < this.poiNum; i++) paramPilot.airdromeWay[i] = this.aPoints[i].poi;
    }
  }

  private Point_Any getNext(Pilot paramPilot)
  {
    if (paramPilot.airdromeWay == null) return null;
    if (paramPilot.airdromeWay.length == 0) return null;
    if (paramPilot.curAirdromePoi >= paramPilot.airdromeWay.length) return null;
    return paramPilot.airdromeWay[(paramPilot.curAirdromePoi++)];
  }

  public void update(Pilot paramPilot, float paramFloat)
  {
    if ((!paramPilot.isCapableOfTaxiing()) || (paramPilot.EI.getThrustOutput() < 0.01F)) {
      paramPilot.TaxiMode = false;
      paramPilot.set_task(3);
      paramPilot.set_maneuver(49);
      paramPilot.AP.setStabAll(false);
      return;
    }
    if (paramPilot.AS.isPilotDead(0)) {
      paramPilot.TaxiMode = false;
      paramPilot.setSpeedMode(8);
      paramPilot.smConstPower = 0.0F;
      if (Airport.distToNearestAirport(paramPilot.Loc) > 900.0D)
        ((Aircraft)paramPilot.actor).postEndAction(6000.0D, paramPilot.actor, 3, null);
      else
        MsgDestroy.Post(Time.current() + 300000L, paramPilot.actor);
      return;
    }

    P.x = paramPilot.Loc.x;
    P.y = paramPilot.Loc.y;

    Vcur.x = (float)paramPilot.Vwld.x;
    Vcur.y = (float)paramPilot.Vwld.y;
    paramPilot.super_update(paramFloat);
    P.z = paramPilot.Loc.z;
    if (paramPilot.wayCurPos == null) {
      findTheWay(paramPilot);
      paramPilot.wayPrevPos = (paramPilot.wayCurPos = getNext(paramPilot));
    }
    if (paramPilot.wayCurPos != null) {
      Point_Any localPoint_Any1 = paramPilot.wayCurPos;
      Point_Any localPoint_Any2 = paramPilot.wayPrevPos;
      Pcur.set((float)P.x, (float)P.y);
      float f1 = Pcur.distance(localPoint_Any1);
      float f2 = Pcur.distance(localPoint_Any2);

      V_to.sub(localPoint_Any1, Pcur);
      V_to.normalize();

      float f3 = 5.0F + 0.1F * f1;
      if (f3 > 12.0F) f3 = 12.0F;
      if (f3 > 0.9F * paramPilot.VminFLAPS) f3 = 0.9F * paramPilot.VminFLAPS;
      if (((paramPilot.curAirdromePoi < paramPilot.airdromeWay.length) && (f1 < 15.0F)) || (f1 < 4.0F))
      {
        f3 = 0.0F;
        Point_Any localPoint_Any3 = getNext(paramPilot);
        if (localPoint_Any3 == null) {
          paramPilot.CT.setPowerControl(0.0F);
          paramPilot.Loc.set(P);
          if (paramPilot.finished) return;
          paramPilot.finished = true;

          int i = 1000;
          if (paramPilot.wayCurPos != null) i = 2400000;
          paramPilot.actor.collide(true);
          paramPilot.Vwld.set(0.0D, 0.0D, 0.0D);
          paramPilot.CT.setPowerControl(0.0F);
          paramPilot.EI.setCurControlAll(true);
          paramPilot.EI.setEngineStops();
          paramPilot.TaxiMode = false;
          World.cur(); if (paramPilot.actor != World.getPlayerAircraft())
          {
            if ((Mission.isDogfight()) && (Main.cur().mission.zutiMisc_DespawnAIPlanesAfterLanding))
            {
              MsgDestroy.Post(Time.current() + 4000L, paramPilot.actor);
            }
            else
              MsgDestroy.Post(Time.current() + i, paramPilot.actor);
          }
          paramPilot.setStationedOnGround(true);
          paramPilot.set_maneuver(1);
          paramPilot.setSpeedMode(8);
          return;
        }
        paramPilot.wayPrevPos = paramPilot.wayCurPos;
        paramPilot.wayCurPos = localPoint_Any3;
      }
      V_to.scale(f3);

      float f4 = 2.0F * paramFloat;
      Vdiff.set(V_to);
      Vdiff.sub(Vcur);
      float f5 = Vdiff.length();
      if (f5 > f4) {
        Vdiff.normalize();
        Vdiff.scale(f4);
      }
      Vcur.add(Vdiff);

      tmpOr.setYPR(Pilot.RAD2DEG(Vcur.direction()), paramPilot.Or.getPitch(), 0.0F);
      paramPilot.Or.interpolate(tmpOr, 0.2F);
      paramPilot.Vwld.x = Vcur.x;
      paramPilot.Vwld.y = Vcur.y;
      P.x += Vcur.x * paramFloat;
      P.y += Vcur.y * paramFloat;
    } else {
      paramPilot.TaxiMode = false;
      paramPilot.wayPrevPos = (paramPilot.wayCurPos = new Point_Null((float)paramPilot.Loc.x, (float)paramPilot.Loc.y));
    }
    paramPilot.Loc.set(P);
  }

  class AiardromeLine
  {
    int from;
    int to;

    AiardromeLine()
    {
    }
  }

  class AiardromePoint
  {
    Point_Any poi;
    int from;
    int poiCounter;

    AiardromePoint()
    {
    }
  }
}