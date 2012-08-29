/*4.10.1 class*/
package com.maddox.il2.ai.ground;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.MsgDreamListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiSupportMethods_ResourcesManagement;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;

public class ChiefGround extends Chief implements MsgDreamListener
{
	private int curState;
	private int stateCountdown;
	private boolean shift_ToRightSide;
	private boolean shift_SwitchToBrakeWhenDone;
	private ArrayList unitsPacked;
	private RoadPath road;
	private int minGrabSeg;
	private int maxGrabSeg;
	private int chiefSeg;
	private double chiefAlong;
	private int minSeg;
	private int maxSeg;
	private float groupSpeed;
	private float maxSpace;
	private boolean withPreys;
	private long waitTime;
	private int posCountdown;
	private Vector3d estim_speed;
	private Point3d tmpp = new Point3d();
	private int[] curForm;

	class Move extends Interpolate
	{
		public boolean tick()
		{
			if (waitTime > 0L && Time.tick() >= waitTime)
				waitTime = 0L;
			if (unitsPacked.size() > 0)
			{
				ChiefGround.this.moveChiefPacked(Time.tickLenFs());
				return true;
			}
			if (access$306(ChiefGround.this) <= 0)
			{
				int i = SecsToTicks(World.Rnd().nextFloat(300.0F, 500.0F));
				switch (curState)
				{
					case 0:
						stateCountdown = i;
					break;
					case 1:
						curState = 0;
						stateCountdown = i;
						ChiefGround.this.reformIfNeed(false);
					break;
					case 2:
						if (shift_SwitchToBrakeWhenDone)
						{
							curState = 3;
							stateCountdown = SecsToTicks(World.Rnd().nextFloat(38.0F, 65.0F));
						}
						else
						{
							curState = 0;
							stateCountdown = i;
						}
						ChiefGround.this.reformIfNeed(false);
					break;
					case 3:
						curState = 0;
						stateCountdown = i;
						ChiefGround.this.reformIfNeed(true);
					break;
				}
			}
			if (access$806(ChiefGround.this) <= 0)
				ChiefGround.this.recomputeAveragePosition();
			return true;
		}
	}

	private static final void ERR_NO_UNITS(String string)
	{
		String string_0_ = "INTERNAL ERROR IN ChiefGround." + string + "(): No units";
		System.out.println(string_0_);
		throw new ActorException(string_0_);
	}

	private static final void ERR(String string)
	{
		String string_1_ = "INTERNAL ERROR IN ChiefGround: " + string;
		System.out.println(string_1_);
		throw new ActorException(string_1_);
	}

	private static final void ConstructorFailure()
	{
		throw new ActorException();
	}

	public boolean isPacked()
	{
		return unitsPacked == null || unitsPacked.size() > 0;
	}

	public Object getSwitchListener(Message message)
	{
		return this;
	}

	private void SetPosition(Point3d point3d, float f)
	{
		pos.getAbs(tmpp);
		pos.setAbs(point3d);
		estim_speed.sub(point3d, tmpp);
		if (f <= 1.0E-4F)
			estim_speed.set(0.0, 0.0, 0.0);
		else
			estim_speed.scale(1.0 / (double) f);
	}

	public double getSpeed(Vector3d vector3d)
	{
		double d = estim_speed.length();
		if (vector3d == null)
			return d;
		vector3d.set(estim_speed);
		if (d <= 1.0E-4)
			vector3d.set(0.0, 0.0, 1.0E-4);
		return d;
	}

	public ChiefGround(String string, int i, SectFile sectfile, String string_2_, SectFile sectfile_3_, String string_4_)
	{
		try
		{
			road = new RoadPath(sectfile_3_, string_4_);
			road.RegisterTravellerToBridges(this);
			setName(string);
			setArmy(i);
			chiefSeg = 0;
			chiefAlong = 0.0;
			minSeg = -1;
			maxSeg = -1;
			waitTime = 0L;
			curForm = null;
			minGrabSeg = maxGrabSeg = -1;
			pos = new ActorPosMove(this);
			pos.setAbs(road.get(0).start);
			pos.reset();
			posCountdown = 0;
			estim_speed = new Vector3d(0.0, 0.0, 0.0);
			int i_5_ = sectfile.sectionIndex(string_2_);
			int i_6_ = sectfile.vars(i_5_);
			if (i_6_ <= 0)
				throw new ActorException("ChiefGround: Missing units");
			unitsPacked = new ArrayList();
			for (int i_7_ = 0; i_7_ < i_6_; i_7_++)
			{
				String string_8_ = sectfile.var(i_5_, i_7_);
				Object object = Spawn.get(string_8_);
				if (object == null)
					throw new ActorException("ChiefGround: Unknown type of object (" + string_8_ + ")");
				int i_9_ = Finger.Int(string_8_);
				int i_10_ = 0;
				unitsPacked.add(new UnitInPackedForm(i_7_, i_9_, i_10_));
			}
			withPreys = false;
			unpackUnits();
			recomputeAveragePosition();
			if (!interpEnd("move"))
				interpPut(new Move(), "move", Time.current(), null);
			
			//TODO: Added by |ZUTI|
			//-------------------------------------------------------
			zutiRoadSegments = road.nsegments()-2;
			zutiCarName = string_2_.substring(string_2_.indexOf(".")+1, string_2_.length());
			zutiLastPointX = (int)road.get(road.segments.size()-1).getEndP().x;
			zutiLastPointY = (int)road.get(road.segments.size()-1).getEndP().y;
			
			zutiReportFinalDestination = ZutiSupportMethods.isMovingRRRObject(zutiCarName, null);
			//System.out.println("Name: " + zutiCarName + "Segments: " + zutiRoadSegments + ", last point=" + zutiLastPointX + ", " + zutiLastPointY);
			//-------------------------------------------------------
		}
		catch (Exception exception)
		{
			System.out.println("ChiefGround creation failure:");
			System.out.println(exception.getMessage());
			exception.printStackTrace();
			ConstructorFailure();
		}
	}

	public int getCodeOfBridgeSegment(UnitInterface unitinterface)
	{
		int i = unitinterface.GetUnitData().segmentIdx;
		return road.getCodeOfBridgeSegment(i);
	}

	public void BridgeSegmentDestroyed(int i, int i_11_, Actor actor)
	{
		boolean bool = road.MarkDestroyedSegments(i, i_11_);
		if (bool && unitsPacked.size() <= 0)
		{
			Object[] objects = getOwnerAttached();
			if (objects.length <= 0)
				ERR_NO_UNITS("BridgeSegmentDestroyed");
			for (int i_12_ = 0; i_12_ < objects.length; i_12_++)
			{
				int i_13_ = (((UnitInterface) objects[i_12_]).GetUnitData().segmentIdx);
				if (road.segIsWrongOrDamaged(i_13_))
					((UnitInterface) objects[i_12_]).absoluteDeath(actor);
			}
		}
	}

	private void recomputeAveragePosition()
	{
		if (unitsPacked.size() > 0)
			ERR("average position when PACKED");
		Object[] objects = getOwnerAttached();
		if (objects.length <= 0)
			ERR_NO_UNITS("recomputeAveragePosition");
		int i = objects.length;
		int i_14_ = 10000;
		int i_15_ = -10000;
		for (int i_16_ = 0; i_16_ < i; i_16_++)
		{
			int i_17_ = ((UnitInterface) objects[i_16_]).GetUnitData().segmentIdx;
			if (i_17_ < i_14_)
				i_14_ = i_17_;
			if (i_17_ > i_15_)
				i_15_ = i_17_;
		}
		Point3d point3d = new Point3d(((Actor) objects[0]).pos.getAbsPoint());
		double d = World.land().HQ(point3d.x, point3d.y);
		if (point3d.z < d)
			point3d.z = d;
		SetPosition(point3d, 1.05F);
		posCountdown = SecsToTicks(World.Rnd().nextFloat(0.9F, 1.2F));
		road.unlockBridges(this, minGrabSeg, maxGrabSeg);
		minGrabSeg = i_14_;
		maxGrabSeg = i_15_;
		road.lockBridges(this, minGrabSeg, maxGrabSeg);
	}

	private void computePositionForPacked()
	{
		if (unitsPacked.size() > 0)
			ERR("advanced position when PACKED");
		Point3d point3d = pos.getAbsPoint();
		double d = 999999.9;
		chiefSeg = minSeg;
		for (int i = minSeg; i <= maxSeg; i++)
		{
			double d_18_ = road.get(i).computePosAlong(point3d);
			double d_19_ = road.get(i).computePosSide(point3d);
			double d_20_ = d_18_ * d_18_ + d_19_ * d_19_;
			if (d >= d_20_)
			{
				d = d_20_;
				chiefSeg = i;
			}
		}
		chiefAlong = road.get(chiefSeg).computePosAlong_Fit(point3d);
		SetPosition(road.get(chiefSeg).computePos_Fit(chiefAlong, 0.0, 0.0F), 0.0F);
		posCountdown = SecsToTicks(World.Rnd().nextFloat(0.9F, 1.2F));
		road.unlockBridges(this, minGrabSeg, maxGrabSeg);
		minGrabSeg = maxGrabSeg = chiefSeg;
		road.lockBridges(this, minGrabSeg, maxGrabSeg);
	}

	private void recomputeChiefWaitTime(int i)
	{
		long l = road.getMaxWaitTime(i, i);
		long l_21_ = Time.tick();
		if (l > l_21_ && l > waitTime)
			waitTime = l;
	}

	private void recomputeMinMaxSegments()
	{
		if (unitsPacked.size() > 0)
			ERR("min/max seg when PACKED");
		Object[] objects = getOwnerAttached();
		if (objects.length <= 0)
			ERR_NO_UNITS("recomputeMinMaxSegments");
		int i = objects.length;
		int i_22_ = 10000;
		int i_23_ = -10000;
		for (int i_24_ = 0; i_24_ < i; i_24_++)
		{
			int i_25_ = ((UnitInterface) objects[i_24_]).GetUnitData().segmentIdx;
			if (i_25_ < i_22_)
				i_22_ = i_25_;
			if (i_25_ > i_23_)
				i_23_ = i_25_;
		}
		road.unlockBridges(this, minGrabSeg, maxGrabSeg);
		minGrabSeg = i_22_;
		maxGrabSeg = i_23_;
		road.lockBridges(this, minGrabSeg, maxGrabSeg);
		if (i_22_ != minSeg || i_23_ != maxSeg)
		{
			long l = road.getMaxWaitTime(Math.min(i_22_, minSeg), i_23_);
			long l_26_ = Time.tick();
			if (l > l_26_ && l > waitTime)
				waitTime = l;
			minSeg = i_22_;
			maxSeg = i_23_;
		}
	}

	private void recomputeUnitsProperties_Packed()
	{
		int i = unitsPacked.size();
		if (i <= 0)
			recomputeUnitsProperties();
		else
		{
			groupSpeed = 100000.0F;
			maxSpace = -1.0F;
			weaponsMask = 0;
			hitbyMask = 0;
			for (int i_27_ = 0; i_27_ < i; i_27_++)
			{
				UnitInPackedForm unitinpackedform = (UnitInPackedForm) unitsPacked.get(i_27_);
				float f = unitinpackedform.SPEED_AVERAGE;
				if (f < groupSpeed)
					groupSpeed = f;
				f = unitinpackedform.BEST_SPACE;
				if (f > maxSpace)
					maxSpace = f;
				weaponsMask |= unitinpackedform.WEAPONS_MASK;
				hitbyMask |= unitinpackedform.HITBY_MASK;
			}
			if (groupSpeed < 0.0010F || groupSpeed > 10000.0F)
				ERR("group speed is too small");
			if (maxSpace <= 0.01F)
				ERR("maxSpace is too small");
		}
	}

	public void recomputeUnitsProperties()
	{
		if (unitsPacked.size() > 0)
			recomputeUnitsProperties_Packed();
		else
		{
			Object[] objects = getOwnerAttached();
			if (objects.length <= 0)
				ERR_NO_UNITS("recomputeUnitsProperties");
			int i = objects.length;
			groupSpeed = 10000.0F;
			maxSpace = -1.0F;
			weaponsMask = 0;
			hitbyMask = 0;
			withPreys = false;
			for (int i_28_ = 0; i_28_ < i; i_28_++)
			{
				UnitInterface unitinterface = (UnitInterface) objects[i_28_];
				float f = unitinterface.SpeedAverage();
				if (f < groupSpeed)
					groupSpeed = f;
				f = unitinterface.BestSpace();
				if (f > maxSpace)
					maxSpace = f;
				if (unitinterface instanceof Predator)
					weaponsMask |= ((Predator) unitinterface).WeaponsMask();
				if (unitinterface instanceof Prey)
				{
					hitbyMask |= ((Prey) unitinterface).HitbyMask();
					if (!(unitinterface instanceof Predator))
						withPreys = true;
				}
			}
			if (groupSpeed <= 0.0010F)
				ERR("group speed is too small");
			if (maxSpace <= 0.01F)
				ERR("maxSpace is too small");
		}
	}

	private float computeMaxSpace(ArrayList arraylist, int i, int i_29_)
	{
		float f = -1.0F;
		while (i_29_-- > 0)
		{
			float f_30_ = ((UnitInterface) arraylist.get(i++)).BestSpace();
			if (f_30_ > f)
				f = f_30_;
		}
		if (f <= 0.01F)
			ERR("maxSpace is too small");
		return f;
	}

	private float computeMaxSpace(Object[] objects, int i, int i_31_)
	{
		float f = -1.0F;
		while (i_31_-- > 0)
		{
			float f_32_ = ((UnitInterface) objects[i++]).BestSpace();
			if (f_32_ > f)
				f = f_32_;
		}
		if (f <= 0.01F)
			ERR("maxSpace is too small");
		return f;
	}

	private static final int SecsToTicks(float f)
	{
		int i = (int) (0.5 + (double) (f / Time.tickLenFs()));
		return i <= 0 ? 0 : i;
	}

	public Actor GetNearestEnemy(Point3d point3d, double d, int i, float f)
	{
		if (unitsPacked.size() > 0)
			return null;
		if (f < 0.0F)
			NearestEnemies.set(i);
		else
			NearestEnemies.set(i, -9999.9F, f);
		Actor actor = NearestEnemies.getAFoundEnemy(point3d, d, getArmy());
		if (actor == null)
			return null;
		if (!(actor instanceof Prey))
		{
			System.out.println("chiefg: nearest enemies: non-Prey");
			return null;
		}
		switch (curState)
		{
			case 0:
				if (!withPreys)
				{
					curState = 1;
					stateCountdown = SecsToTicks(World.Rnd().nextFloat(50.0F, 90.0F));
					reformIfNeed(false);
				}
			break;
			case 1:
				stateCountdown = SecsToTicks(World.Rnd().nextFloat(50.0F, 90.0F));
			break;
		}
		return actor;
	}

	public void Detach(Actor actor, Actor actor_33_)
	{
		if (unitsPacked.size() > 0)
			ERR("Detaching when PACKED");
		Object[] objects = getOwnerAttached();
		if (objects.length <= 0)
			ERR_NO_UNITS("Detach");
		int i = objects.length;
		int i_34_;
		for (i_34_ = 0; i_34_ < i; i_34_++)
		{
			if (actor == (Actor) objects[i_34_])
				break;
		}
		if (i_34_ >= i)
			ERR("Detaching unknown unit");
		UnitInterface unitinterface = (UnitInterface) actor;
		UnitData unitdata = unitinterface.GetUnitData();
		if (i_34_ < i - 1)
		{
			Actor actor_35_ = (Actor) objects[i_34_ + 1];
			UnitInterface unitinterface_36_ = (UnitInterface) actor_35_;
			UnitData unitdata_37_ = unitinterface_36_.GetUnitData();
			unitdata_37_.leader = unitdata.leader;
		}
		unitdata.leader = null;
		actor.setOwner(null);
		if (i > 1)
		{
			recomputeUnitsProperties();
			recomputeMinMaxSegments();
			reformIfNeed(true);
			recomputeAveragePosition();
		}
		if (i <= 1)
		{
			road.UnregisterTravellerFromBridges(this);
			road.unlockBridges(this, minGrabSeg, maxGrabSeg);
			minGrabSeg = maxGrabSeg = -1;
			World.onActorDied(this, actor_33_);
			destroy();
		}
	}

	public void msgDream(boolean bool)
	{
		boolean bool_38_ = unitsPacked.size() > 0;
		if (bool)
		{
			if (!bool_38_)
				ERR("Wakeup out of place");
			unpackUnits();
		}
		else
		{
			if (bool_38_)
				ERR("Sleeping out of place");
			packUnits();
		}
	}

	public void packUnits()
	{
		if (unitsPacked.size() <= 0)
		{
			Object[] objects = getOwnerAttached();
			if (objects.length <= 0)
				ERR_NO_UNITS("packUnits");
			computePositionForPacked();
			int i = objects.length;
			for (int i_39_ = 0; i_39_ < i; i_39_++)
			{
				unitsPacked.add(((UnitInterface) objects[i_39_]).Pack());
				((Actor) objects[i_39_]).destroy();
			}
			recomputeUnitsProperties();
		}
	}

	public void unpackUnits()
	{
		int i = unitsPacked.size();
		if (i > 0)
		{
			if (getOwnerAttached().length > 0)
				ERR("unpack units");
			for (int i_40_ = 0; i_40_ < i; i_40_++)
			{
				int i_41_ = ((UnitInPackedForm) unitsPacked.get(i_40_)).CodeName();
				int i_42_ = ((UnitInPackedForm) unitsPacked.get(i_40_)).CodeType();
				Object object = Spawn.get(i_42_);
				int i_43_ = ((UnitInPackedForm) unitsPacked.get(i_40_)).State();
				((UnitSpawn) object).unitSpawn(i_41_, i_43_, this);
			}
			unitsPacked.clear();
			curState = 0;
			stateCountdown = 0;
			road.unlockBridges(this, minGrabSeg, maxGrabSeg);
			minGrabSeg = maxGrabSeg = -1;
			recomputeUnitsProperties();
			formUnitsAfterUnpacking();
		}
	}

	private static void AutoChooseFormation(int i, boolean bool, int i_44_, float f, double d, int[] is)
	{
		if (i_44_ > 0)
		{
			if (i == 2 || i == 3)
			{
				is[0] = bool ? 2 : 3;
				is[1] = 1;
				is[2] = i_44_;
			}
			else
			{
				is[0] = 0;
				int i_45_ = (int) (d / (double) f);
				if (i_45_ <= 0)
					i_45_ = 1;
				if (i_45_ > i_44_)
					i_45_ = i_44_;
				if (i_45_ <= 1)
				{
					is[1] = 1;
					is[2] = i_44_;
				}
				else if (i == 0)
				{
					is[1] = 1;
					is[2] = i_44_;
				}
				else
				{
					is[0] = 1;
					if (i_45_ >= 3 && i_44_ < i_45_ - 1 + i_45_ - 1)
					{
						i_45_ /= 2;
						if (i_45_ < 2)
							i_45_ = 2;
					}
					is[1] = i_45_;
					int i_46_ = 2 * i_45_ - 1;
					int i_47_ = (i_44_ + i_46_ - 1) / i_46_;
					is[2] = i_47_ * 2;
					if ((i_44_ + i_46_ - 1) % i_46_ < i_45_ - 1)
						is[2]--;
				}
			}
		}
	}

	private void formUnitsAfterUnpacking()
	{
		if (unitsPacked.size() <= 0)
		{
			Object[] objects = getOwnerAttached();
			if (objects.length <= 0)
				ERR_NO_UNITS("formUnitsAfterUnpacking");
			float f = (float) (objects.length - 1) * maxSpace;
			double d = road.get(chiefSeg).computePosAlong_Fit(pos.getAbsPoint());
			RoadPart roadpart = new RoadPart();
			road.FindFreeSpace((double) f, chiefSeg, d, roadpart);
			int[] is = new int[3];
			AutoChooseFormation(curState, shift_ToRightSide, objects.length, maxSpace, road.ComputeMinRoadWidth(roadpart.begseg, roadpart.endseg), is);
			curForm = is;
			f = (float) (is[2] - 1) * maxSpace;
			road.FindFreeSpace((double) f, chiefSeg, d, roadpart);
			int i = roadpart.begseg;
			double d_49_ = roadpart.begt;
			int i_50_ = 0;
			float f_51_ = 0.0F;
			for (int i_52_ = 0; i_52_ < is[2]; i_52_++)
			{
				int i_53_ = is[1];
				if ((i_52_ & 0x1) == 0 && is[0] == 1)
					i_53_--;
				int i_54_ = i_53_;
				if (i_50_ + i_54_ > objects.length)
					i_54_ = objects.length - i_50_;
				float f_55_ = computeMaxSpace(objects, i_50_, i_54_);
				float f_56_ = 0.0F;
				if (i_52_ > 0)
				{
					f_56_ = Math.max(f_51_, f_55_);
					double d_57_ = (double) f_56_;
					d_49_ -= d_57_;
					while (d_49_ < 0.0)
					{
						d_57_ = -d_49_;
						d_49_ = 0.0;
						if (!road.segIsWrongOrDamaged(i - 1))
						{
							i--;
							d_49_ = road.get(i).length2D - d_57_;
						}
						else
						{
							d_49_ = -1.0;
							break;
						}
					}
					if (d_49_ < 0.0)
						break;
				}
				for (int i_58_ = 0; i_58_ < i_54_; i_58_++)
				{
					UnitData unitdata = ((UnitInterface) objects[i_50_]).GetUnitData();
					unitdata.segmentIdx = i;
					unitdata.leaderDist = f_56_;
					if (i_50_ == 0)
						unitdata.leader = null;
					else if (i_52_ == 0)
					{
						unitdata.leader = (Actor) objects[0];
						unitdata.leaderDist = 0.0F;
					}
					else if (is[0] == 0 || (i_52_ & 0x1) == 0 || i_58_ > 0)
						unitdata.leader = (Actor) objects[i_50_ - is[1]];
					else
						unitdata.leader = (Actor) objects[i_50_ - is[1] + 1];
					float f_59_ = is[0] == 0 ? f_55_ : maxSpace;
					float f_60_ = (float) (is[1] - 1) * f_59_;
					if (is[0] == 1 && (i_52_ & 0x1) == 0)
						f_60_ -= f_59_;
					unitdata.sideOffset = -f_60_ / 2.0F + (float) i_58_ * f_59_;
					Actor actor = (Actor) objects[i_50_];
					Point3d point3d = road.get(i).computePos_Fit(d_49_, (double) (unitdata.sideOffset), actor.collisionR());
					point3d.z += (double) ((UnitInterface) actor).HeightAboveLandSurface();
					actor.pos.setAbs(point3d);
					Vector3f vector3f = new Vector3f();
					if (road.get(i).IsLandAligned())
						Engine.land().N(point3d.x, point3d.y, vector3f);
					else
						vector3f.set(road.get(i).normal);
					Orient orient = new Orient();
					orient.setYPR(road.get(i).yaw, 0.0F, 0.0F);
					orient.orient(vector3f);
					actor.pos.setAbs(orient);
					actor.pos.reset();
					((UnitInterface) objects[i_50_]).startMove();
					i_50_++;
				}
				if (i_50_ >= objects.length)
					break;
				f_51_ = f_55_;
			}
			if (i_50_ <= 0)
				ERR("No room to place units");
			for (/**/; i_50_ < objects.length; i_50_++)
				((Actor) objects[i_50_]).destroy();
			recomputeMinMaxSegments();
		}
	}

	private void reformForSHIFT(Object[] objects, float f, boolean bool)
	{
		if (objects != null)
		{
			ArrayList arraylist = new ArrayList(objects.length);
			for (int i = 0; i < objects.length; i++)
				arraylist.add(objects[i]);
			Collections.sort(arraylist, new Comparator()
			{
				public int compare(Object object, Object object_62_)
				{
					UnitData unitdata = ((UnitInterface) object).GetUnitData();
					UnitData unitdata_63_ = ((UnitInterface) object_62_).GetUnitData();
					RoadSegment roadsegment = road.get(unitdata.segmentIdx);
					RoadSegment roadsegment_64_ = road.get(unitdata_63_.segmentIdx);
					double d = (roadsegment_64_.length2Dallprev + (roadsegment_64_.computePosAlong(((Actor) object_62_).pos.getAbsPoint())) - roadsegment.length2Dallprev - roadsegment.computePosAlong(((Actor) object).pos.getAbsPoint()));
					return d == 0.0 ? 0 : d > 0.0 ? 1 : -1;
				}
			});
			float f_65_ = bool ? 5000.0F : -5000.0F;
			for (int i = 0; i < arraylist.size(); i++)
			{
				UnitData unitdata = ((UnitInterface) arraylist.get(i)).GetUnitData();
				if (i == 0)
				{
					unitdata.leaderDist = 0.0F;
					unitdata.leader = null;
				}
				else
				{
					unitdata.leaderDist = f;
					unitdata.leader = (Actor) arraylist.get(i - 1);
				}
				unitdata.sideOffset = f_65_;
			}
			for (int i = 0; i < objects.length; i++)
				((UnitInterface) objects[i]).forceReaskMove();
		}
	}

	private void reform(Object[] objects, int[] is)
	{
		if (objects != null)
		{
			if (is[0] == 2 || is[0] == 3)
				reformForSHIFT(objects, maxSpace, is[0] == 2);
			else
			{
				ArrayList arraylist = new ArrayList(objects.length);
				for (int i = 0; i < objects.length; i++)
					arraylist.add(objects[i]);
				Collections.sort(arraylist, new Comparator()
				{
					public int compare(Object object, Object object_67_)
					{
						UnitData unitdata = ((UnitInterface) object).GetUnitData();
						UnitData unitdata_68_ = ((UnitInterface) object_67_).GetUnitData();
						RoadSegment roadsegment = road.get(unitdata.segmentIdx);
						RoadSegment roadsegment_69_ = road.get(unitdata_68_.segmentIdx);
						double d = (roadsegment_69_.length2Dallprev + (roadsegment_69_.computePosAlong(((Actor) object_67_).pos.getAbsPoint())) - roadsegment.length2Dallprev - (roadsegment.computePosAlong(((Actor) object).pos.getAbsPoint())));
						return d == 0.0 ? 0 : d > 0.0 ? 1 : -1;
					}
				});
				for (int i = 0; i < arraylist.size(); i++)
				{
					UnitData unitdata = ((UnitInterface) arraylist.get(i)).GetUnitData();
					if (is[0] == 0)
						unitdata.leaderDist = (float) (i / is[1]);
					else
					{
						int i_70_ = i / (is[1] * 2 - 1);
						i_70_ = (i % (is[1] * 2 - 1) < is[1] - 1 ? i_70_ * 2 : i_70_ * 2 + 1);
						unitdata.leaderDist = (float) i_70_;
					}
				}
				Collections.sort(arraylist, new Comparator()
				{
					public int compare(Object object, Object object_72_)
					{
						UnitData unitdata = ((UnitInterface) object).GetUnitData();
						UnitData unitdata_73_ = ((UnitInterface) object_72_).GetUnitData();
						if (unitdata.leaderDist != unitdata_73_.leaderDist)
						{
							double d = (double) (unitdata.leaderDist - unitdata_73_.leaderDist);
							return d == 0.0 ? 0 : d > 0.0 ? 1 : -1;
						}
						RoadSegment roadsegment = road.get(unitdata.segmentIdx);
						RoadSegment roadsegment_74_ = road.get(unitdata_73_.segmentIdx);
						double d = (roadsegment.computePosSide(((Actor) object).pos.getAbsPoint()) - (roadsegment_74_.computePosSide(((Actor) object_72_).pos.getAbsPoint())));
						return d == 0.0 ? 0 : d > 0.0 ? 1 : -1;
					}
				});
				int i = 0;
				float f = 0.0F;
				for (int i_75_ = 0; i_75_ < is[2]; i_75_++)
				{
					int i_76_ = is[1];
					if ((i_75_ & 0x1) == 0 && is[0] == 1)
						i_76_--;
					int i_77_ = i_76_;
					if (i + i_77_ > arraylist.size())
						i_77_ = arraylist.size() - i;
					float f_78_ = computeMaxSpace(arraylist, i, i_77_);
					float f_79_ = 0.0F;
					if (i_75_ > 0)
						f_79_ = Math.max(f, f_78_);
					for (int i_80_ = 0; i_80_ < i_77_; i_80_++)
					{
						UnitData unitdata = ((UnitInterface) arraylist.get(i)).GetUnitData();
						unitdata.leaderDist = f_79_;
						int i_81_;
						if (i == 0)
							i_81_ = -1;
						else if (i_75_ == 0)
						{
							i_81_ = 0;
							unitdata.leaderDist = 0.0F;
						}
						else if (is[0] == 0 || (i_75_ & 0x1) == 0 || i_80_ > 0)
							i_81_ = i - is[1];
						else
							i_81_ = i - is[1] + 1;
						unitdata.leader = i_81_ < 0 ? null : (Actor) arraylist.get(i_81_);
						float f_82_ = is[0] == 0 ? f_78_ : maxSpace;
						float f_83_ = (float) (is[1] - 1) * f_82_;
						if (is[0] == 1 && (i_75_ & 0x1) == 0)
							f_83_ -= f_82_;
						unitdata.sideOffset = -f_83_ / 2.0F + (float) i_80_ * f_82_;
						i++;
					}
					if (i >= arraylist.size())
						break;
					f = f_78_;
				}
				for (i = 0; i < objects.length; i++)
					((UnitInterface) objects[i]).forceReaskMove();
			}
		}
	}

	private int[] FindBestFormation(int i)
	{
		int[] is = new int[3];
		AutoChooseFormation(curState, shift_ToRightSide, i, maxSpace, road.ComputeMinRoadWidth(maxSeg, minSeg), is);
		return is;
	}

	private void reformIfNeed(boolean bool)
	{
		Object[] objects = getOwnerAttached();
		if (objects.length <= 0)
			ERR_NO_UNITS("reformIfNeed");
		if (bool)
		{
			curForm = FindBestFormation(objects.length);
			reform(objects, curForm);
		}
		else
		{
			int[] is = FindBestFormation(objects.length);
			if (is[0] != curForm[0] || is[1] != curForm[1])
			{
				curForm = is;
				reform(objects, curForm);
			}
		}
	}

	public void CollisionOccured(UnitInterface unitinterface, Actor actor)
	{
		if (actor instanceof UnitInterface && (actor.getArmy() == getArmy() || !actor.isAlive() || actor.getArmy() == 0 || !isAlive() || getArmy() == 0))
		{
			Actor actor_84_ = actor.getOwner();
			if (actor_84_ != null && actor_84_ != this)
			{
				if (!(actor_84_ instanceof ChiefGround))
					throw new ActorException("ChiefGround: ground unit with wrong owner");
				ChiefGround chiefground_85_ = (ChiefGround) actor_84_;
				UnitInterface unitinterface_86_ = (UnitInterface) actor;
				Vector2d vector2d = road.get(unitinterface.GetUnitData().segmentIdx).dir2D;
				Vector2d vector2d_87_ = (chiefground_85_.road.get(unitinterface_86_.GetUnitData().segmentIdx).dir2D);
				boolean bool = ((vector2d.x * vector2d_87_.x + vector2d.y * vector2d_87_.y) < 0.0);
				boolean bool_88_ = false;
				if (chiefground_85_.waitTime < waitTime)
					bool_88_ = true;
				else if (chiefground_85_.waitTime == waitTime)
				{
					if (chiefground_85_.groupSpeed > groupSpeed)
						bool_88_ = true;
					else if (chiefground_85_.groupSpeed == groupSpeed && chiefground_85_.name().compareTo(name()) < 0)
						bool_88_ = true;
				}
				curState = chiefground_85_.curState = 2;
				int i = SecsToTicks(World.Rnd().nextFloat(47.0F, 72.0F));
				int i_89_ = SecsToTicks(World.Rnd().nextFloat(47.0F, 72.0F));
				if (bool)
				{
					shift_ToRightSide = true;
					shift_SwitchToBrakeWhenDone = false;
					chiefground_85_.shift_ToRightSide = true;
					chiefground_85_.shift_SwitchToBrakeWhenDone = false;
					stateCountdown = i;
					chiefground_85_.stateCountdown = i_89_;
				}
				else
				{
					shift_ToRightSide = bool_88_;
					shift_SwitchToBrakeWhenDone = bool_88_;
					chiefground_85_.shift_ToRightSide = !bool_88_;
					chiefground_85_.shift_SwitchToBrakeWhenDone = !bool_88_;
					int i_90_ = SecsToTicks(World.Rnd().nextFloat(18.0F, 25.0F));
					if (bool_88_)
					{
						chiefground_85_.stateCountdown = i;
						stateCountdown = chiefground_85_.stateCountdown - i_90_;
					}
					else
					{
						stateCountdown = i;
						chiefground_85_.stateCountdown = stateCountdown - i_90_;
					}
				}
				reformIfNeed(false);
				chiefground_85_.reformIfNeed(false);
			}
		}
	}

	public double computePosAlong_Fit(int i, Point3d point3d)
	{
		return road.get(i).computePosAlong_Fit(point3d);
	}

	public double computePosAlong(int i, Point3d point3d)
	{
		return road.get(i).computePosAlong(point3d);
	}

	public double computePosSide(int i, Point3d point3d)
	{
		return road.get(i).computePosSide(point3d);
	}

	private static final float distance2D(Point3d point3d, Point3d point3d_91_)
	{
		return (float) Math.sqrt(((point3d.x - point3d_91_.x) * (point3d.x - point3d_91_.x)) + ((point3d.y - point3d_91_.y) * (point3d.y - point3d_91_.y)));
	}

	private static boolean intersectLineCircle(float f, float f_92_, float f_93_, float f_94_, float f_95_, float f_96_, float f_97_)
	{
		float f_98_ = f_97_ * f_97_;
		float f_99_ = f_93_ - f;
		float f_100_ = f_94_ - f_92_;
		float f_101_ = f_99_ * f_99_ + f_100_ * f_100_;
		float f_102_ = ((f_95_ - f) * f_99_ + (f_96_ - f_92_) * f_100_) / f_101_;
		if (f_102_ >= 0.0F && f_102_ <= 1.0F)
		{
			float f_103_ = f + f_102_ * f_99_;
			float f_104_ = f_92_ + f_102_ * f_100_;
			float f_105_ = ((f_103_ - f_95_) * (f_103_ - f_95_) + (f_104_ - f_96_) * (f_104_ - f_96_));
			return f_98_ - f_105_ >= 0.0F;
		}
		float f_106_ = ((f_93_ - f_95_) * (f_93_ - f_95_) + (f_94_ - f_96_) * (f_94_ - f_96_));
		float f_107_ = (f - f_95_) * (f - f_95_) + (f_92_ - f_96_) * (f_92_ - f_96_);
		return f_106_ <= f_98_ || f_107_ <= f_98_;
	}

	private static boolean intersectCircle(Point3d point3d, double d, Point3d point3d_108_, double d_109_, float f)
	{
		float f_110_ = (float) (point3d_108_.x + d_109_ * (double) Geom.cosDeg(f));
		float f_111_ = (float) (point3d_108_.y + d_109_ * (double) Geom.sinDeg(f));
		return intersectLineCircle((float) point3d_108_.x, (float) point3d_108_.y, f_110_, f_111_, (float) point3d.x, (float) point3d.y, (float) d);
	}

	private UnitMove createStay_UnitMove(float f, int i)
	{
		RoadSegment roadsegment = road.get(i);
		if (roadsegment.IsLandAligned())
			return new UnitMove(f, new Vector3f(0.0F, 0.0F, -1.0F));
		return new UnitMove(f, roadsegment.normal);
	}

	private boolean cantEnterIntoSegment_checkComplete(int i)
	{
		if (i >= road.nsegments() - 1)
		{
			boolean bool = waitTime > 0L && i > maxSeg;
			if (!bool)
				World.onTaskComplete(this);
			return true;
		}
		return !road.segIsPassableBy(i, this) || waitTime > 0L && i > maxSeg;
	}

	private boolean cantEnterIntoSegment(int i)
	{
		return (i >= road.nsegments() - 1 || !road.segIsPassableBy(i, this) || waitTime > 0L && i > maxSeg);
	}

	private boolean cantEnterIntoSegmentPacked_checkComplete(int i)
	{
		if (i >= road.nsegments() - 1)
		{
			boolean bool = waitTime > 0L;
			if (!bool)
				World.onTaskComplete(this);
			return true;
		}
		return !road.segIsPassableBy(i, this) || waitTime > 0L;
	}

	private void moveChiefPacked(float f)
	{
		RoadSegment roadsegment = road.get(chiefSeg);
		double d = (double) (groupSpeed * f);
		while (chiefAlong + d >= roadsegment.length2D)
		{
			if (cantEnterIntoSegmentPacked_checkComplete(chiefSeg + 1))
			{
				chiefAlong = roadsegment.length2D;
				d = 0.0;
				break;
			}
			chiefAlong = chiefAlong + d - roadsegment.length2D;
			chiefSeg++;
			recomputeChiefWaitTime(chiefSeg);
			roadsegment = road.get(chiefSeg);
			road.unlockBridges(this, minGrabSeg, maxGrabSeg);
			minGrabSeg = maxGrabSeg = chiefSeg;
			road.lockBridges(this, minGrabSeg, maxGrabSeg);
		}
		chiefAlong += d;
		SetPosition(roadsegment.computePos_Fit(chiefAlong, 0.0, 0.0F), f);
	}

	public UnitMove AskMoveCommand(Actor actor, Point3d point3d, StaticObstacle staticobstacle)
	{		
		UnitInterface unitinterface = (UnitInterface) actor;
		UnitData unitdata = unitinterface.GetUnitData();
		boolean bool = point3d != null && point3d.z < 0.0;
		boolean bool_112_ = point3d != null && point3d.z > 0.0;
		if ((curState == 2 || curState == 3) && bool_112_)
			bool_112_ = false;
		
		RoadSegment roadsegment = road.get(unitdata.segmentIdx);

		//TODO: Added by |ZUTI|
		//------------------------------------------------------------
		zutiCarReachedFinalDestination(unitdata.segmentIdx);
		//------------------------------------------------------------
		
		Point3d point3d_113_ = new Point3d(actor.pos.getAbsPoint());
		if (bool)
		{
			int i = 0;
			double d = roadsegment.computePosAlong(point3d_113_);
			if (d >= 0.0)
				d = d > roadsegment.length2D ? d - roadsegment.length2D : 0.0;
			for (;;)
			{
				if (d > 0.0)
					cantEnterIntoSegment_checkComplete(unitdata.segmentIdx + 1);
				if (i >= 0)
				{
					if (cantEnterIntoSegment(unitdata.segmentIdx + 1))
					{
						if (i != 0)
							break;
					}
					else
					{
						RoadSegment roadsegment_114_ = road.get(unitdata.segmentIdx + 1);
						double d_115_ = roadsegment_114_.computePosAlong(point3d_113_);
						if (d_115_ >= 0.0)
							d_115_ = (d_115_ > roadsegment_114_.length2D ? d_115_ - roadsegment_114_.length2D : 0.0);
						if (Math.abs(d_115_) < Math.abs(d))
						{
							i = 1;
							d = d_115_;
							unitdata.segmentIdx++;
						}
						else if (i != 0)
							break;
					}
				}
				if (i <= 0)
				{
					if (cantEnterIntoSegment(unitdata.segmentIdx - 1))
						break;
					RoadSegment roadsegment_116_ = road.get(unitdata.segmentIdx - 1);
					double d_117_ = roadsegment_116_.computePosAlong(point3d_113_);
					if (d_117_ >= 0.0)
						d_117_ = (d_117_ > roadsegment_116_.length2D ? d_117_ - roadsegment_116_.length2D : 0.0);
					if (!(Math.abs(d_117_) < Math.abs(d)))
						break;
					i = -1;
					d = d_117_;
					unitdata.segmentIdx--;
				}
			}
			if (i != 0)
			{
				roadsegment = road.get(unitdata.segmentIdx);
				recomputeMinMaxSegments();
				reformIfNeed(false);
			}
		}
		else
		{
			boolean bool_118_ = false;
			UnitMove unitmove = null;
			for (/**/; (roadsegment.computePosAlong(actor.pos.getAbsPoint()) >= roadsegment.length2D - 1.0); roadsegment = road.get(unitdata.segmentIdx))
			{
				if (cantEnterIntoSegment_checkComplete(unitdata.segmentIdx + 1))
				{
					unitmove = createStay_UnitMove(5.0F, unitdata.segmentIdx);
					break;
				}
				bool_118_ = true;
				unitdata.segmentIdx++;
				if (unitdata.segmentIdx > maxSeg || unitdata.segmentIdx - 1 <= minSeg)
					recomputeMinMaxSegments();
			}
			if (bool_118_)
				reformIfNeed(false);
			if (unitmove != null)
				return unitmove;
		}
		Vector3d vector3d = new Vector3d(roadsegment.dir2D.x, roadsegment.dir2D.y, 0.0);
		if (!bool && staticobstacle.updateState())
		{
			double d = roadsegment.computePosAlong(point3d_113_);
			double d_119_ = road.ComputeSignedDistAlong(unitdata.segmentIdx, d, staticobstacle.segIdx, staticobstacle.along);
			double d_120_ = (double) actor.collisionR();
			if (d_120_ <= 0.0)
				d_120_ = 0.0;
			d_120_ += (double) staticobstacle.R;
			if (d_120_ <= 0.0)
				d_120_ = 2.0;
			double d_121_ = d_119_;
			if (d_119_ >= 0.0)
			{
				d_121_ -= d_120_;
				if (d_121_ <= 0.0)
					d_121_ = 0.0;
			}
			else
			{
				d_121_ += d_120_;
				if (d_121_ >= 0.0)
					d_121_ = 0.0;
			}
			if (!(Math.abs(d_119_) > 130.0))
			{
				/* empty */
			}
			if (d_121_ < -Math.max(7.0 * Math.abs(d_120_), 120.0))
				staticobstacle.clear();
			else if (d_119_ <= 0.0)
			{
				if (d_121_ >= -1.5)
					vector3d.z = 2.5;
			}
			else if (d_121_ > Math.max(3.0 * d_120_, 20.0))
				vector3d.z = Math.max(d_120_, 3.0);
			else
			{
				double d_122_ = 0.5 * d_120_;
				if (d_122_ < 0.01)
					vector3d.z = 1.0;
				else
				{
					Vector2f vector2f = new Vector2f((float) (staticobstacle.pos.x - point3d_113_.x), (float) (staticobstacle.pos.y - point3d_113_.y));
					if (vector2f.length() < 0.1F)
						vector3d.z = 4.0;
					else
					{
						vector2f.normalize();
						AnglesFork anglesfork = new AnglesFork();
						anglesfork.setSrcRad((float) Math.atan2((double) vector2f.y, (double) vector2f.x));
						anglesfork.setDstDeg(anglesfork.getSrcDeg() + (staticobstacle.side > 0.0 ? 90.0F : -90.0F));
						double d_123_ = d_120_ + 0.5;
						if (!intersectCircle(staticobstacle.pos, d_123_, point3d_113_, d_122_, anglesfork.getSrcDeg()))
						{
							float f = anglesfork.getSrcDeg();
							vector3d.set((double) Geom.cosDeg(f), (double) Geom.sinDeg(f), d_122_);
						}
						else if (intersectCircle(staticobstacle.pos, d_123_, point3d_113_, d_122_, anglesfork.getDstDeg()))
							vector3d.z = 2.0;
						else
						{
							for (int i = 0; i < 6; i++)
							{
								float f = anglesfork.getDeg(0.5F);
								if (intersectCircle(staticobstacle.pos, d_123_, point3d_113_, d_122_, f))
									anglesfork.setSrcDeg(f);
								else
									anglesfork.setDstDeg(f);
							}
							float f = anglesfork.getDstDeg();
							vector3d.set((double) Geom.cosDeg(f), (double) Geom.sinDeg(f), d_122_);
						}
					}
				}
			}
		}
		if (vector3d.z > 0.0)
		{
			bool_112_ = false;
			bool = true;
			vector3d.x *= vector3d.z;
			vector3d.y *= vector3d.z;
			point3d = new Point3d(vector3d);
			point3d.z = -1.0;
		}
		if (bool)
		{
			Point3d point3d_124_ = new Point3d(point3d_113_);
			point3d_124_.x += point3d.x;
			point3d_124_.y += point3d.y;
			double d = roadsegment.computePosAlong(point3d_124_);
			double d_125_ = roadsegment.computePosSide(point3d_124_);
			if (d >= roadsegment.length2D - 0.2)
			{
				point3d_124_ = roadsegment.computePos_Fit(roadsegment.length2D, d_125_, actor.collisionR());
				point3d_124_.x += roadsegment.dir2D.x * 0.2;
				point3d_124_.y += roadsegment.dir2D.y * 0.2;
			}
			else
				point3d_124_ = roadsegment.computePos_Fit(d, d_125_, actor.collisionR());
			double d_126_ = (double) distance2D(point3d_124_, point3d_113_);
			float f = (float) d_126_ / groupSpeed;
			Vector3f vector3f = (road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : road.get(unitdata.segmentIdx).normal);
			return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d_124_, f, vector3f, groupSpeed);
		}
		if (curState == 3)
			return createStay_UnitMove(5.0F, unitdata.segmentIdx);
		if (unitdata.leader == null)
		{
			float f = (unitinterface.CommandInterval() * World.Rnd().nextFloat(0.8F, 1.0F));
			double d = (double) (groupSpeed * f);
			double d_127_ = d + roadsegment.computePosAlong(point3d_113_);
			double d_128_ = (double) unitdata.sideOffset;
			if (bool_112_)
			{
				d_127_ += point3d.x;
				d_128_ += point3d.y;
			}
			Point3d point3d_129_;
			if (d_127_ >= roadsegment.length2D - 0.2)
			{
				point3d_129_ = roadsegment.computePos_Fit(roadsegment.length2D, d_128_, actor.collisionR());
				point3d_129_.x += roadsegment.dir2D.x * 0.2;
				point3d_129_.y += roadsegment.dir2D.y * 0.2;
			}
			else
				point3d_129_ = roadsegment.computePos_Fit(d_127_, d_128_, actor.collisionR());
			d = (double) distance2D(point3d_129_, point3d_113_);
			float f_130_ = (float) d / groupSpeed;
			Vector3f vector3f = (road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : road.get(unitdata.segmentIdx).normal);
			return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d_129_, f_130_, vector3f, groupSpeed);
		}
		Actor actor_131_ = unitdata.leader;
		UnitData unitdata_132_ = ((UnitInterface) actor_131_).GetUnitData();
		RoadSegment roadsegment_133_ = road.get(unitdata_132_.segmentIdx);
		Point3d point3d_134_ = new Point3d();
		float f = unitinterface.CommandInterval();
		f *= World.Rnd().nextFloat(0.8F, 1.0F);
		float f_135_ = actor_131_.futurePosition(f, point3d_134_);
		f_135_ += 0.4F;
		double d = roadsegment_133_.computePosAlong(point3d_134_);
		Point3d point3d_136_ = new Point3d();
		actor.pos.getAbs(point3d_136_);
		double d_137_ = roadsegment.computePosAlong(point3d_136_);
		double d_138_ = road.ComputeSignedDistAlong(unitdata.segmentIdx, d_137_, unitdata_132_.segmentIdx, d);
		double d_139_ = (double) unitdata.leaderDist;
		if (bool_112_)
			d_139_ += point3d.x;
		double d_140_ = d_138_ - d_139_;
		if (d_140_ < 0.0)
		{
			f = unitinterface.StayInterval();
			if (roadsegment.IsLandAligned())
				return new UnitMove(f, new Vector3f(0.0F, 0.0F, -1.0F));
			return new UnitMove(f, road.get(unitdata.segmentIdx).normal);
		}
		double d_141_ = roadsegment.length2D - d_137_;
		if (d_140_ <= d_141_)
			d_137_ += d_140_;
		else
		{
			d_137_ = roadsegment.length2D;
			f_135_ *= d_141_ / d_140_;
		}
		f_135_ *= 1.05F;
		double d_142_ = (double) unitdata.sideOffset;
		if (bool_112_)
			d_142_ += point3d.y;
		point3d_136_ = roadsegment.computePos_FitBegCirc(d_137_, d_142_, actor.collisionR());
		if (d_137_ >= roadsegment.length2D - 0.1)
		{
			point3d_136_.x += roadsegment.dir2D.x * 0.2;
			point3d_136_.y += roadsegment.dir2D.y * 0.2;
		}
		d_139_ = (double) distance2D(point3d_136_, actor.pos.getAbsPoint());
		Vector3f vector3f = (road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1.0F) : road.get(unitdata.segmentIdx).normal);
		return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d_136_, f_135_ < 0.3F ? 0.3F : f_135_, vector3f, -1.0F);
	}

	static int access$306(ChiefGround chiefground)
	{
		return --chiefground.stateCountdown;
	}

	static int access$806(ChiefGround chiefground)
	{
		return --chiefground.posCountdown;
	}
	
	//TODO: |ZUTI| methods and variables
	//-----------------------------------------------------
	private String zutiCarName = null;
	private int zutiRoadSegments = -1;
	private int zutiLastPointX = -1;
	private int zutiLastPointY = -1;
	private boolean zutiReportFinalDestination = true;
	
	public List zutiUnitsList = new ArrayList();
	
	private void zutiCarReachedFinalDestination(int currentSegmentIndex)
	{
		if( zutiReportFinalDestination && currentSegmentIndex == zutiRoadSegments )
		{
			Point3d cPos = pos.getAbsPoint();
			int result = zutiLastPointX-(int)cPos.x + zutiLastPointY-(int)cPos.y;
			if( Math.abs(result) < 5 )
			{
				//System.out.println(zutiCarName + " has reached its final destination!");
				zutiReportFinalDestination = false;
				
				ZutiSupportMethods_ResourcesManagement.addResourcesFromMovingRRRObjects(zutiCarName, cPos, this.getArmy(), zutiGetSurvivability(), true);
			}
		}
	}
	
	private float zutiGetSurvivability()
	{
		float liveUnits = 0;
		//System.out.println("UNITS: " + zutiUnitsList.size());
		for( int i=0; i<zutiUnitsList.size(); i++ )
		{
			Actor actor = (Actor)zutiUnitsList.get(i);
			if( actor.isAlive() )
				liveUnits++;
		}
		//System.out.println("LIVE UNITS: " + liveUnits);
		float totalUnits = zutiUnitsList.size();
		//System.out.println("SURVIVABILITY = " + liveUnits / totalUnits);
		return liveUnits / totalUnits;
	}
	//-----------------------------------------------------
}