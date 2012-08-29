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
    this.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any = new Point_Any[512];
    this.testParkPoint = new Point3d();
    this.airdromeList = new ArrayList();

    for (int i = 0; i < 512; i++) this.aPoints[i] = new AiardromePoint();
    for (int j = 0; j < 512; j++) this.aLines[j] = new AiardromeLine();
    for (int k = 0; k < 512; k++) this.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[k] = new Point_Any(0.0F, 0.0F);
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

    Vrun.jdField_x_of_type_Float = (float)paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
    Vrun.jdField_y_of_type_Float = (float)paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double;
    Point_Null localPoint_Null = new Point_Null((float)paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, (float)paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);

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
          Engine.land(); this.testParkPoint.set(this.stay[i][1].jdField_x_of_type_Float, this.stay[i][1].jdField_y_of_type_Float, Landscape.HQ_Air(this.stay[i][1].jdField_x_of_type_Float, this.stay[i][1].jdField_y_of_type_Float));
          Engine.collideEnv().getSphere(this.airdromeList, this.testParkPoint, 1.5F * paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor.collisionR() + 10.0F);
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
        Engine.land(); this.testParkPoint.set(this.aPoints[i].poi.jdField_x_of_type_Float, this.aPoints[i].poi.jdField_y_of_type_Float, Landscape.HQ_Air(this.aPoints[i].poi.jdField_x_of_type_Float, this.aPoints[i].poi.jdField_y_of_type_Float));
        Engine.collideEnv().getSphere(this.airdromeList, this.testParkPoint, 1.2F * paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor.collisionR() + 3.0F);
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
                    this.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[(k++)] = this.aPoints[i9].poi;
                    i9 = this.aPoints[i9].from;
                  }

                  this.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[(k++)] = this.aPoints[0].poi;
                  paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any = new Point_Any[k];
                  for (int i10 = 0; i10 < k; i10++) {
                    paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[i10] = new Point_Any(0.0F, 0.0F);
                    paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[i10].set(this.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[(k - i10 - 1)]);
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
    World.cur(); if (paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
      MsgDestroy.Post(Time.current() + 30000L, paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      paramPilot.setStationedOnGround(true);
    }
    if (this.poiNum > 0) {
      paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any = new Point_Any[this.poiNum];
      for (i = 0; i < this.poiNum; i++) paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[i] = this.aPoints[i].poi;
    }
  }

  private Point_Any getNext(Pilot paramPilot)
  {
    if (paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any == null) return null;
    if (paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any.length == 0) return null;
    if (paramPilot.jdField_curAirdromePoi_of_type_Int >= paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any.length) return null;
    return paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any[(paramPilot.jdField_curAirdromePoi_of_type_Int++)];
  }

  public void update(Pilot paramPilot, float paramFloat)
  {
    if ((!paramPilot.isCapableOfTaxiing()) || (paramPilot.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getThrustOutput() < 0.01F)) {
      paramPilot.jdField_TaxiMode_of_type_Boolean = false;
      paramPilot.set_task(3);
      paramPilot.set_maneuver(49);
      paramPilot.AP.setStabAll(false);
      return;
    }
    if (paramPilot.AS.isPilotDead(0)) {
      paramPilot.jdField_TaxiMode_of_type_Boolean = false;
      paramPilot.setSpeedMode(8);
      paramPilot.smConstPower = 0.0F;
      if (Airport.distToNearestAirport(paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 900.0D)
        ((Aircraft)paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(6000.0D, paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor, 3, null);
      else
        MsgDestroy.Post(Time.current() + 300000L, paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      return;
    }

    P.jdField_x_of_type_Double = paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double;
    P.jdField_y_of_type_Double = paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double;

    Vcur.jdField_x_of_type_Float = (float)paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
    Vcur.jdField_y_of_type_Float = (float)paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double;
    paramPilot.super_update(paramFloat);
    P.jdField_z_of_type_Double = paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;
    if (paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any == null) {
      findTheWay(paramPilot);
      paramPilot.jdField_wayPrevPos_of_type_ComMaddoxIl2AiAirPoint_Any = (paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any = getNext(paramPilot));
    }
    if (paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any != null) {
      Point_Any localPoint_Any1 = paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any;
      Point_Any localPoint_Any2 = paramPilot.jdField_wayPrevPos_of_type_ComMaddoxIl2AiAirPoint_Any;
      Pcur.set((float)P.jdField_x_of_type_Double, (float)P.jdField_y_of_type_Double);
      float f1 = Pcur.distance(localPoint_Any1);
      float f2 = Pcur.distance(localPoint_Any2);

      V_to.sub(localPoint_Any1, Pcur);
      V_to.normalize();

      float f3 = 5.0F + 0.1F * f1;
      if (f3 > 12.0F) f3 = 12.0F;
      if (f3 > 0.9F * paramPilot.jdField_VminFLAPS_of_type_Float) f3 = 0.9F * paramPilot.jdField_VminFLAPS_of_type_Float;
      if (((paramPilot.jdField_curAirdromePoi_of_type_Int < paramPilot.jdField_airdromeWay_of_type_ArrayOfComMaddoxIl2AiAirPoint_Any.length) && (f1 < 15.0F)) || (f1 < 4.0F))
      {
        f3 = 0.0F;
        Point_Any localPoint_Any3 = getNext(paramPilot);
        if (localPoint_Any3 == null) {
          paramPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
          paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(P);
          if (paramPilot.jdField_finished_of_type_Boolean) return;
          paramPilot.jdField_finished_of_type_Boolean = true;

          int i = 1000;
          if (paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any != null) i = 2400000;
          paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor.collide(true);
          paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          paramPilot.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
          paramPilot.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
          paramPilot.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
          paramPilot.jdField_TaxiMode_of_type_Boolean = false;
          World.cur(); if (paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
            MsgDestroy.Post(Time.current() + i, paramPilot.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          }
          paramPilot.setStationedOnGround(true);
          paramPilot.set_maneuver(1);
          paramPilot.setSpeedMode(8);
          return;
        }
        paramPilot.jdField_wayPrevPos_of_type_ComMaddoxIl2AiAirPoint_Any = paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any;
        paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any = localPoint_Any3;
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

      tmpOr.setYPR(Pilot.RAD2DEG(Vcur.direction()), paramPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getPitch(), 0.0F);
      paramPilot.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.interpolate(tmpOr, 0.2F);
      paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = Vcur.jdField_x_of_type_Float;
      paramPilot.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = Vcur.jdField_y_of_type_Float;
      P.jdField_x_of_type_Double += Vcur.jdField_x_of_type_Float * paramFloat;
      P.jdField_y_of_type_Double += Vcur.jdField_y_of_type_Float * paramFloat;
    } else {
      paramPilot.jdField_TaxiMode_of_type_Boolean = false;
      paramPilot.jdField_wayPrevPos_of_type_ComMaddoxIl2AiAirPoint_Any = (paramPilot.jdField_wayCurPos_of_type_ComMaddoxIl2AiAirPoint_Any = new Point_Null((float)paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, (float)paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double));
    }
    paramPilot.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(P);
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