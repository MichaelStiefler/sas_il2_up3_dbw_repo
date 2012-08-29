/*4.10.1 class*/
package com.maddox.il2.ai.air;
import java.util.ArrayList;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiSupportMethods_AI;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.ZutiSupportMethods_NetSend;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;

public class Airdrome
{
	public static float CONN_DIST = 10.0F;
	public Point_Runaway[][] runw;
	public Point_Taxi[][] taxi;
	public Point_Stay[][] stay;
	public boolean[] stayHold;
	AiardromePoint[] aPoints = new AiardromePoint[512];
	int poiNum = 0;
	AiardromeLine[] aLines = new AiardromeLine[512];
	int lineNum = 0;
	Point_Any[] airdromeWay = new Point_Any[512];
	Point3d testParkPoint = new Point3d();
	ArrayList airdromeList = new ArrayList();
	private static Point3d P = new Point3d();
	private static Point2f Pcur = new Point2f();
	private static Vector2f Vcur = new Vector2f();
	private static Vector2f V_to = new Vector2f();
	private static Vector2f Vdiff = new Vector2f();
	private static Vector2f V_pn = new Vector2f();
	private static Vector2f Vrun = new Vector2f();
	private static Orient tmpOr = new Orient();
	
	class AiardromeLine
	{
		int from;
		int to;
	}
	
	class AiardromePoint
	{
		Point_Any poi;
		int from;
		int poiCounter;
	}
	
	public Airdrome()
	{
		for (int i = 0; i < 512; i++)
			aPoints[i] = new AiardromePoint();
		for (int i = 0; i < 512; i++)
			aLines[i] = new AiardromeLine();
		for (int i = 0; i < 512; i++)
			airdromeWay[i] = new Point_Any(0.0F, 0.0F);
	}
	
	public void freeStayPoint(Point_Any point_any)
	{
		if (point_any != null)
		{
			if (point_any instanceof Point_Stay)
			{
				for (int i = 0; i < stayHold.length; i++)
				{
					for (int i_0_ = 0; i_0_ < stay[i].length - 1; i_0_++)
					{
						if (point_any == stay[i][i_0_])
						{
							stayHold[i] = false;
							return;
						}
					}
				}
			}
		}
	}
	
	public void findTheWay(Pilot pilot)
	{
		int i = 0;
		int i_1_ = 0;
		poiNum = 0;
		lineNum = 0;
		Vrun.x = (float)pilot.Vwld.x;
		Vrun.y = (float)pilot.Vwld.y;
		Point_Null point_null = new Point_Null((float)pilot.Loc.x, (float)pilot.Loc.y);
		int i_2_ = -1;
		int i_3_ = -1;
		int i_4_ = -1;
		int i_5_ = -1;
		float f_6_;
		float f = f_6_ = 2000.0F;
		for (int i_7_ = 0; i_7_ < runw.length; i_7_++)
		{
			for (int i_8_ = 0; i_8_ < runw[i_7_].length; i_8_++)
			{
				float f_9_ = point_null.distance(runw[i_7_][i_8_]);
				if (f_9_ < f_6_)
				{
					f_6_ = f_9_;
					i_2_ = i_7_;
					i_3_ = i_8_;
				}
				if (f_9_ < f)
				{
					V_pn.sub(runw[i_7_][i_8_], point_null);
					V_pn.normalize();
					Vrun.normalize();
					if (V_pn.dot(Vrun) > 0.9F)
					{
						f = f_9_;
						i_4_ = i_7_;
						i_5_ = i_8_;
					}
				}
			}
		}
		aPoints[poiNum].poiCounter = 0;
		if (i_4_ >= 0)
			aPoints[poiNum++].poi = runw[i_4_][i_5_];
		else if (i_2_ >= 0)
			aPoints[poiNum++].poi = runw[i_2_][i_3_];
		for (int i_10_ = 0; i_10_ < stay.length; i_10_++)
		{
			if (stay[i_10_].length >= 2)
			{
				float f_11_ = point_null.distance(stay[i_10_][1]);
				if (f_11_ < 2000.0F && !stayHold[i_10_])
				{
					Point3d point3d = testParkPoint;
					double d = (double)stay[i_10_][1].x;
					double d_12_ = (double)stay[i_10_][1].y;
					Engine.land();
					point3d.set(d, d_12_, (double)Landscape.HQ_Air(stay[i_10_][1].x, stay[i_10_][1].y));
					Engine.collideEnv().getSphere(airdromeList, testParkPoint, (double)(1.5F * pilot.actor.collisionR() + 10.0F));
					int i_13_ = airdromeList.size();
					airdromeList.clear();
					if (i_13_ == 0)
					{
						aLines[lineNum].to = poiNum;
						aPoints[poiNum].poiCounter = 777 + i_10_;
						aPoints[poiNum++].poi = stay[i_10_][1];
						aLines[lineNum++].from = poiNum;
						aPoints[poiNum].poiCounter = 255;
						aPoints[poiNum++].poi = stay[i_10_][0];
					}
				}
			}
		}
		if (poiNum >= 3)
		{
			i_2_ = -1;
			i_3_ = -1;
			for (int i_14_ = 0; i_14_ < taxi.length; i_14_++)
			{
				if (taxi[i_14_].length >= 2 && !(point_null.distance(taxi[i_14_][0]) > 2000.0F))
				{
					boolean bool = false;
					for (int i_15_ = 0; i_15_ < poiNum; i_15_++)
					{
						if (aPoints[i_15_].poi.distance(taxi[i_14_][0]) < 18.0F)
						{
							i = i_15_;
							bool = true;
							break;
						}
					}
					if (!bool)
					{
						i = poiNum;
						aPoints[poiNum].poiCounter = 255;
						aPoints[poiNum++].poi = taxi[i_14_][0];
					}
					for (int i_16_ = 1; i_16_ < taxi[i_14_].length; i_16_++)
					{
						bool = false;
						for (int i_17_ = 0; i_17_ < poiNum; i_17_++)
						{
							if (aPoints[i_17_].poi.distance(taxi[i_14_][i_16_]) < 18.0F)
							{
								i_1_ = i_17_;
								bool = true;
								break;
							}
						}
						if (!bool)
						{
							i_1_ = poiNum;
							aPoints[poiNum].poiCounter = 255;
							aPoints[poiNum++].poi = taxi[i_14_][i_16_];
						}
						aLines[lineNum].from = i;
						aLines[lineNum++].to = i_1_;
						i = i_1_;
					}
				}
			}
			for (int i_18_ = 0; i_18_ < poiNum; i_18_++)
			{
				Point3d point3d = testParkPoint;
				double d = (double)aPoints[i_18_].poi.x;
				double d_19_ = (double)aPoints[i_18_].poi.y;
				Engine.land();
				point3d.set(d, d_19_, (double)Landscape.HQ_Air(aPoints[i_18_].poi.x, aPoints[i_18_].poi.y));
				Engine.collideEnv().getSphere(airdromeList, testParkPoint, (double)((1.2F * pilot.actor.collisionR()) + 3.0F));
				int i_20_ = airdromeList.size();
				if (i_20_ == 1 && airdromeList.get(0) instanceof Aircraft)
					i_20_ = 0;
				airdromeList.clear();
				if (i_20_ > 0)
					aPoints[i_18_].poiCounter = -100;
			}
			for (int i_21_ = 0; i_21_ < 255; i_21_++)
			{
				boolean bool = false;
				for (int i_22_ = 0; i_22_ < poiNum; i_22_++)
				{
					if (aPoints[i_22_].poiCounter == i_21_)
					{
						for (int i_23_ = 0; i_23_ < lineNum; i_23_++)
						{
							int i_24_ = 0;
							if (aLines[i_23_].to == i_22_)
								i_24_ = aLines[i_23_].from;
							if (aLines[i_23_].from == i_22_)
								i_24_ = aLines[i_23_].to;
							if (i_24_ != 0)
							{
								if (aPoints[i_24_].poiCounter >= 777)
								{
									aPoints[i_24_].from = i_22_;
									stayHold[aPoints[i_24_].poiCounter - 777] = true;
									int i_25_ = i_24_;
									int i_26_;
									for (i_26_ = 0; i_25_ > 0 || i_26_ > 128; i_25_ = aPoints[i_25_].from)
										airdromeWay[i_26_++] = aPoints[i_25_].poi;
									airdromeWay[i_26_++] = aPoints[0].poi;
									pilot.airdromeWay = new Point_Any[i_26_];
									for (int i_27_ = 0; i_27_ < i_26_; i_27_++)
									{
										pilot.airdromeWay[i_27_] = new Point_Any(0.0F, 0.0F);
										pilot.airdromeWay[i_27_].set(airdromeWay[i_26_ - i_27_ - 1]);
									}
									return;
								}
								if (i_21_ + 1 < aPoints[i_24_].poiCounter)
								{
									aPoints[i_24_].poiCounter = i_21_ + 1;
									aPoints[i_24_].from = i_22_;
									bool = true;
								}
							}
						}
					}
				}
				if (!bool)
					break;
			}
		}
		com.maddox.il2.engine.Actor actor = pilot.actor;
		World.cur();
		if (actor != World.getPlayerAircraft())
		{
			MsgDestroy.Post(Time.current() + 30000L, pilot.actor);
			pilot.setStationedOnGround(true);
		}
		if (poiNum > 0)
		{
			pilot.airdromeWay = new Point_Any[poiNum];
			for (int i_28_ = 0; i_28_ < poiNum; i_28_++)
				pilot.airdromeWay[i_28_] = aPoints[i_28_].poi;
		}
	}
	
	private Point_Any getNext(Pilot pilot)
	{
		if (pilot.airdromeWay == null)
			return null;
		if (pilot.airdromeWay.length == 0)
			return null;
		if (pilot.curAirdromePoi >= pilot.airdromeWay.length)
			return null;
		return pilot.airdromeWay[pilot.curAirdromePoi++];
	}
	
	public void update(Pilot pilot, float f)
	{
		if (!pilot.isCapableOfTaxiing() || pilot.EI.getThrustOutput() < 0.01F)
		{
			pilot.TaxiMode = false;
			pilot.set_task(3);
			pilot.set_maneuver(49);
			pilot.AP.setStabAll(false);
		}
		else if (pilot.AS.isPilotDead(0))
		{
			pilot.TaxiMode = false;
			pilot.setSpeedMode(8);
			pilot.smConstPower = 0.0F;
			if (Airport.distToNearestAirport(pilot.Loc) > 900.0)
				((Aircraft)pilot.actor).postEndAction(6000.0, pilot.actor, 3, null);
			else
				MsgDestroy.Post(Time.current() + 300000L, pilot.actor);
		}
		else
		{
			P.x = pilot.Loc.x;
			P.y = pilot.Loc.y;
			Vcur.x = (float)pilot.Vwld.x;
			Vcur.y = (float)pilot.Vwld.y;
			pilot.super_update(f);
			P.z = pilot.Loc.z;
			if (pilot.wayCurPos == null)
			{
				findTheWay(pilot);
				pilot.wayPrevPos = pilot.wayCurPos = getNext(pilot);
			}
			if (pilot.wayCurPos != null)
			{
				Point_Any point_any = pilot.wayCurPos;
				Point_Any point_any_29_ = pilot.wayPrevPos;
				Pcur.set((float)P.x, (float)P.y);
				float f_30_ = Pcur.distance(point_any);
				Pcur.distance(point_any_29_);
				V_to.sub(point_any, Pcur);
				V_to.normalize();
				float f_32_ = 5.0F + 0.1F * f_30_;
				if (f_32_ > 12.0F)
					f_32_ = 12.0F;
				if (f_32_ > 0.9F * pilot.VminFLAPS)
					f_32_ = 0.9F * pilot.VminFLAPS;
				if ((pilot.curAirdromePoi < pilot.airdromeWay.length && f_30_ < 15.0F) || f_30_ < 4.0F)
				{
					f_32_ = 0.0F;
					Point_Any point_any_33_ = getNext(pilot);
					if (point_any_33_ == null)
					{
						pilot.CT.setPowerControl(0.0F);
						pilot.Loc.set(P);
						if (!pilot.finished)
						{
							pilot.finished = true;
							int i = 1000;
							if (pilot.wayCurPos != null)
								i = 2400000;
							pilot.actor.collide(true);
							pilot.Vwld.set(0.0, 0.0, 0.0);
							pilot.CT.setPowerControl(0.0F);
							pilot.EI.setCurControlAll(true);
							pilot.EI.setEngineStops();
							pilot.TaxiMode = false;
							com.maddox.il2.engine.Actor actor = pilot.actor;
							World.cur();
							//TODO: Changed by |ZUTI|: if aircraft is players AC (he is gunner), eject him and proceed.
							//---------------------------------------------------------------
							if( Mission.isDogfight() )
							{
								if( actor == World.getPlayerAircraft() )
									ZutiSupportMethods_NetSend.ejectPlayer((NetUser)NetEnv.host());
							}
								
							//TODO: Added by |ZUTI|: despawn when stopped
							//-------------------------------------------------
							//if( zutiAirdromeBornPlace == null )
							//	zutiAirdromeBornPlace = ZutiSupportMethods_Net.getNearestBornPlace_AnyArmy(P.x, P.y);
							
							//String currentAcName = Property.stringValue((actor).getClass(), "keyName");
							//System.out.println("Airdrome - Aircraft >" + currentAcName + "< landed!");
							//ZutiSupportMethods_Net.addAircraftToBornPlace(zutiAirdromeBornPlace, currentAcName, true, false);
							if( Mission.MDS_VARIABLES().zutiMisc_DespawnAIPlanesAfterLanding )//|| (zutiAirdromeBornPlace != null && zutiAirdromeBornPlace.zutiIsStandAloneBornPlace) )
							{
								MsgDestroy.Post((Time.current() + ZutiSupportMethods_AI.DESPAWN_AC_DELAY), actor);
							}
							else
								MsgDestroy.Post(Time.current() + (long) i, pilot.actor);
							//---------------------------------------------------------------
							
							pilot.setStationedOnGround(true);
							pilot.set_maneuver(1);
							pilot.setSpeedMode(8);
							return;
						}
						return;
					}
					pilot.wayPrevPos = pilot.wayCurPos;
					pilot.wayCurPos = point_any_33_;
				}
				V_to.scale(f_32_);
				float f_34_ = 2.0F * f;
				Vdiff.set(V_to);
				Vdiff.sub(Vcur);
				float f_35_ = Vdiff.length();
				if (f_35_ > f_34_)
				{
					Vdiff.normalize();
					Vdiff.scale(f_34_);
				}
				Vcur.add(Vdiff);
				Orient orient = tmpOr;
				if (pilot != null)
				{
					/* empty */
				}
				orient.setYPR(Pilot.RAD2DEG(Vcur.direction()), pilot.Or.getPitch(), 0.0F);
				pilot.Or.interpolate(tmpOr, 0.2F);
				pilot.Vwld.x = (double)Vcur.x;
				pilot.Vwld.y = (double)Vcur.y;
				P.x += (double)(Vcur.x * f);
				P.y += (double)(Vcur.y * f);
			}
			else
			{
				pilot.TaxiMode = false;
				pilot.wayPrevPos = pilot.wayCurPos = new Point_Null((float)pilot.Loc.x, (float)pilot.Loc.y);
			}
			pilot.Loc.set(P);
		}
	}
	
	//TODO: |ZUTI| Methods and variables
	//---------------------------------------------------------
	//private BornPlace zutiAirdromeBornPlace = null;
	//---------------------------------------------------------
}