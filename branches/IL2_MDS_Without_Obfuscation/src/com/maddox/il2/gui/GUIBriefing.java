/* 4.10.1 class */
package com.maddox.il2.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.ZutiSupportMethods_FM;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiMDSVariables;
import com.maddox.il2.game.ZutiPadObject;
import com.maddox.il2.game.ZutiRadarRefresh;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiSupportMethods_Multicrew;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.ZutiSupportMethods_Net;
import com.maddox.il2.net.ZutiSupportMethods_NetSend;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.TestRunway;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon_AAIAS;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon_LongRunway;
import com.maddox.il2.objects.vehicles.radios.Beacon.RadioBeacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.RadioBeaconLowVis;
import com.maddox.il2.objects.vehicles.radios.Beacon.YGBeacon;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;

public class GUIBriefing extends GUIBriefingGeneric
{
	protected String				playerName;
	protected ArrayList				playerPath				= new ArrayList();
	protected ArrayList				targets					= new ArrayList();
	protected Mat					iconBornPlace;
	protected Mat					iconPlayer;
	private static String[]			tip						= new String[3];
	private float[]					lineNXYZ				= new float[6];
	private int						lineNCounter;
	protected boolean				bSelectBorn				= false;

	protected java.util.ArrayList	beacons;

	// TODO: Variables by |ZUTI|
	// ------------------------------------------------------
	public static boolean			ZUTI_IS_BRIEFING_ACTIVE	= false;
	public static BornPlace			ZUTI_LAST_SELECTED_BORN_PLACE = null;
	// ------------------------------------------------------
	private double lastScale;
	public static class BeaconPoint
	{

		public float						x;
		public float						y;
		public com.maddox.il2.engine.Mat	icon;
		public int							army;
		public java.lang.String				id;

		public BeaconPoint()
		{
		}
	}

	public static class TargetPoint
	{
		public float	x;
		public float	y;
		public float	z						= 0.0F;
		public int		r;
		public int		type;
		public int		typeOArmy;
		public int		importance;
		public Mat		icon;
		public Mat		iconOArmy;
		public String	nameTarget;
		public String	nameTargetOrig;
		public Actor	actor;
		public boolean	isBaseActorWing			= false;
		public Wing		wing					= null;

		private boolean	visibleForPlayerArmy	= false;
		private String	zutiDesignation			= null;

		public TargetPoint()
		{
		}

		public boolean isGroundUnit()
		{
			return ZutiPadObject.isGroundUnit(actor);
		}

		public boolean getIsAlive()
		{
			if (actor == null)
				return false;

			if (actor instanceof RocketryRocket)
				return !((RocketryRocket) actor).isDamaged();

			return !actor.getDiedFlag();
		}

		/**
		 * Set targetpoint designation that correcponds to target designation.
		 * 
		 * @return
		 */
		public void zutiSetDesignation(String value)
		{
			zutiDesignation = value;
		}

		/**
		 * Get targetpoint designation that correcponds to target designation.
		 * 
		 * @return
		 */
		public String zutiGetDesignation()
		{
			return zutiDesignation;
		}

		public boolean equals(Object o)
		{
			if (!(o instanceof TargetPoint))
				return false;

			TargetPoint inKeyObject = (TargetPoint) o;

			if (actor != null)
			{
				if (this.actor.equals(inKeyObject.actor))
					return true;
			}
			else
			{
				if (this.nameTargetOrig.equals(inKeyObject.nameTargetOrig))
					return true;
			}

			return false;
		}

		public int hashCode()
		{
			if (actor != null)
				return actor.hashCode();
			else
				return nameTargetOrig.hashCode();
		}

		/**
		 * This method refreshes object position if moving icons are supported,
		 * if player side has radars and if it is visible for at least one
		 * radar!
		 */
		public void refreshPosition()
		{
			// If player has no radars, do not update target position!
			if (!Mission.MDS_VARIABLES().zutiIcons_MovingIcons || !Mission.MDS_VARIABLES().zutiRadar_PlayerSideHasRadars || !isVisibleForPlayerArmy())
				return;

			// If this difficulty flag is NOT set, draw moving icons all the time
			if (actor != null && actor.pos != null)
			{
				Point3d position = actor.pos.getAbsPoint();
				// Moving targets icon location
				x = (float) position.x;
				y = (float) position.y;
				z = (float) position.z;
			}
		}

		public boolean isVisibleForPlayerArmy()
		{
			return visibleForPlayerArmy;
		}

		public void setVisibleForPlayerArmy(boolean value)
		{
			visibleForPlayerArmy = value;
		}
	}

	private static class PathPoint
	{
		public int		type;
		public float	x;
		public float	y;

		private PathPoint()
		{
		}
	}

	public void _enter()
	{
		playerPath.clear();
		playerName = null;
		super._enter();

		// TODO: Kill RRR timers
		ZutiSupportMethods_FM.resetRRRTimers();
		// Reset radars!
		ZutiRadarRefresh.findRadars(ZutiSupportMethods.getPlayerArmy());
		// Reset start timers
		ZutiRadarRefresh.resetStartTimes();
		// Set this variable to true
		GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE = true;
		// Enable user to change cockpit again
		ZutiSupportMethods_Multicrew.CAN_REQUEST_COCKPIT_CHANGE = true;
		// Get paratroopers captured home bases
		ZutiSupportMethods_NetSend.requestFrontMarkers();
		// Get paratroopers captured home bases
		ZutiSupportMethods_NetSend.requestParaCapturedHbList();
		// Get unavailable planes
		ZutiSupportMethods_NetSend.requestAircraftList();
		// Get unavailable planes
		ZutiSupportMethods_NetSend.requestAircraftListWithReleasedOrdinance();
		// Get deactivated TInspect targets
		ZutiSupportMethods_NetSend.requestDeactivatedTargetsList();
		// Set this flag to false as soon as user enters this screen
		com.maddox.il2.objects.air.NetAircraft.ZUTI_REFLY_OWERRIDE = false;
		// Fill ground targets. Use different approach because Engine.targets actors
		// are without icons if actor is ground unit...
		ZutiSupportMethods_GUI.fillGroundChiefsArray(GUI.pad);
		// Sync craters data
		if (!ZutiSupportMethods_GUI.ZUTI_CRATERS_SYNCED)
		{
			ZutiSupportMethods_GUI.ZUTI_CRATERS_SYNCED = true;
			ZutiSupportMethods_NetSend.requestCraters();
		}
		// if( Main.cur().mission != null )
		// System.out.println("GUIBriefing: radar statistics. Red radars: " + Main.cur().mission.zutiRadar_CountRed + ", blue radars: " + Main.cur().mission.zutiRadar_CountBlue);

		// Check if BornPlaces were overrun by any chance
		Front.setMarkersChanged();
		ZutiSupportMethods.checkIfAnyBornPlaceWasOverrun();

		fillBeacons();
	}

	private void fillBeacons()
	{
		com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
		int i = -1;
		if (com.maddox.il2.game.Mission.isDogfight())
			i = ((com.maddox.il2.net.NetUser) com.maddox.rts.NetEnv.host()).getArmy();
		else
			i = sectfile.get("MAIN", "army", 0);
		beacons.clear();
		int j = sectfile.sectionIndex("NStationary");
		if (j < 0)
			return;
		int k = sectfile.vars(j);
		for (int l = 0; l < k; l++)
		{
			com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(j, l));
			BeaconPoint beaconpoint = loadbeacon(i, numbertokenizer.next(""), numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0D), numbertokenizer.next(0.0D));
			if (beaconpoint != null)
				beacons.add(beaconpoint);
		}

	}

	private BeaconPoint loadbeacon(int i, java.lang.String s, java.lang.String s1, int j, double d, double d1)
	{
		if (i != j)
			return null;
		java.lang.Class class1 = null;
		try
		{
			class1 = com.maddox.rts.ObjIO.classForName(s1);
		}
		catch (java.lang.Exception exception)
		{
			java.lang.System.out.println("Mission: class '" + s1 + "' not found");
			return null;
		}
		if ((com.maddox.il2.objects.vehicles.radios.TypeHasBeacon.class).isAssignableFrom(class1))
		{
			BeaconPoint beaconpoint = new BeaconPoint();
			beaconpoint.x = (float) d;
			beaconpoint.y = (float) d1;
			beaconpoint.army = j;
			if ((RadioBeacon.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beacon.mat");
			else if ((RadioBeaconLowVis.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beacon.mat");
			else if ((YGBeacon.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/beaconYG.mat");
			else if ((LorenzBLBeacon.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
			else if ((LorenzBLBeacon_LongRunway.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
			else if ((LorenzBLBeacon_AAIAS.class).isAssignableFrom(class1))
				beaconpoint.icon = com.maddox.il2.engine.IconDraw.get("icons/ILS.mat");
			else
				return null;
			java.lang.String s2 = com.maddox.il2.objects.vehicles.radios.Beacon.getBeaconID(beacons.size());
			beaconpoint.id = s2;
			return beaconpoint;
		}
		else
		{
			return null;
		}
	}

	public void drawBeacons(com.maddox.il2.engine.GUIRenders guirenders, com.maddox.il2.engine.TTFont ttfont, com.maddox.il2.engine.Mat mat, com.maddox.il2.engine.CameraOrtho2D cameraortho2d,
			java.util.ArrayList arraylist)
	{
		int i = arraylist.size();
		if (i == 0)
			return;
		for (int j = 0; j < i; j++)
		{
			BeaconPoint beaconpoint = (BeaconPoint) arraylist.get(j);
			int k = com.maddox.il2.ai.Army.color(beaconpoint.army);
			com.maddox.il2.engine.IconDraw.setColor(k);
			if (beaconpoint.icon == null)
				continue;
			float f = (float) (((double) beaconpoint.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
			float f1 = (float) (((double) beaconpoint.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
			com.maddox.il2.engine.IconDraw.render(beaconpoint.icon, f, f1);
			if (cameraortho2d.worldScale > 0.019999999552965164D)
			{
				float f2 = 20F;
				float f3 = 15F;
				gridFont.output(k, f + f2, f1 - f3, 0.0F, beaconpoint.id);
			}
		}

	}

	private void drawBeacons()
	{
		if (com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
			drawBeacons(renders, gridFont, emptyMat, cameraMap2D, beacons);
	}

	// TODO: Added by |ZUTI|: override parent _leave() method
	// --------------------------------------------------
	public void _leave()
	{
		super._leave();

		GUIBriefing.ZUTI_IS_BRIEFING_ACTIVE = false;
	}

	// --------------------------------------------------

	private void drawBornPlaces()
	{
		if (iconBornPlace != null)
		{
			ArrayList arraylist = World.cur().bornPlaces;
			// System.out.println("Number of born places: " + new Integer(arraylist.size()).toString());

			if (arraylist != null && arraylist.size() != 0)
			{
				int i = arraylist.size();
				BornPlace bornplace = null;
				for (int i_0_ = 0; i_0_ < i; i_0_++)
				{
					bornplace = (BornPlace) arraylist.get(i_0_);
					bornplace.tmpForBrief = 0;
				}
				NetUser netuser = (NetUser) NetEnv.host();
				int i_1_ = netuser.getBornPlace();
				if (i_1_ >= 0 && i_1_ < i)
				{
					bornplace = (BornPlace) arraylist.get(i_1_);
					bornplace.tmpForBrief = 1;
				}
				List list = NetEnv.hosts();
				// TODO: Added by |ZUTI|: extracted size to separate variable
				int hostsSize = list.size();
				for (int i_2_ = 0; i_2_ < hostsSize; i_2_++)
				{
					netuser = (NetUser) list.get(i_2_);
					int i_4_ = netuser.getBornPlace();
					if (i_4_ >= 0 && i_4_ < i)
					{
						bornplace = (BornPlace) arraylist.get(i_4_);
						bornplace.tmpForBrief++;
					}
				}
				for (int i_5_ = 0; i_5_ < i; i_5_++)
				{
					bornplace = (BornPlace) arraylist.get(i_5_);

					if (bornplace.zutiDisableRendering)
						continue;

					IconDraw.setColor(Army.color(bornplace.army));

					float f = (float) ((bornplace.place.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
					float f_6_ = (float) ((bornplace.place.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);

					// ZUTI: moving born places stay at the same location on the brief screen
					if (bornplace.zutiStaticPositionOnly)
					{
						f = (float) ((bornplace.zutiOriginalX - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
						f_6_ = (float) ((bornplace.zutiOriginalY - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
					}

					IconDraw.render(iconBornPlace, f, f_6_);
					if (i_5_ == i_1_ && iconPlayer != null)
						Render.drawTile(f, f_6_, (float) IconDraw.scrSizeX(), (float) IconDraw.scrSizeY(), 0.0F, iconPlayer, Army.color(bornplace.army), 0.0F, 1.0F, 1.0F, -1.0F);

					// TODO: Modified by |ZUTI|
					// if (bornplace.tmpForBrief > 0)
					if (bornplace.tmpForBrief > 0 && !Mission.MDS_VARIABLES().zutiMisc_HidePlayersCountOnHomeBase)
						gridFont.output(Army.color(bornplace.army), (float) ((int) f + IconDraw.scrSizeX() / 2 + 2), (float) ((int) f_6_ - IconDraw.scrSizeY() / 2 - 2), 0.0F, ""
								+ bornplace.tmpForBrief);
				}
			}
		}
	}

	public static void fillTargets(SectFile sectfile, ArrayList arraylist)
	{
		arraylist.clear();
		int i = sectfile.sectionIndex("Target");
		if (i >= 0)
		{
			int i_7_ = sectfile.vars(i);
			for (int i_8_ = 0; i_8_ < i_7_; i_8_++)
			{
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_8_));
				int i_9_ = numbertokenizer.next(0, 0, 7);
				int i_10_ = numbertokenizer.next(0, 0, 2);
				if (i_10_ != 2)
				{
					TargetPoint targetpoint = new TargetPoint();
					targetpoint.type = i_9_;
					targetpoint.importance = i_10_;
					numbertokenizer.next(0);
					numbertokenizer.next(0, 0, 720);
					numbertokenizer.next(0);
					targetpoint.x = (float) numbertokenizer.next(0);
					targetpoint.y = (float) numbertokenizer.next(0);
					int i_13_ = numbertokenizer.next(0);
					if (targetpoint.type == 3 || targetpoint.type == 6 || targetpoint.type == 1)
					{
						if (i_13_ < 50)
							i_13_ = 50;
						if (i_13_ > 3000)
							i_13_ = 3000;
					}
					targetpoint.r = i_13_;
					numbertokenizer.next(0);
					targetpoint.nameTarget = numbertokenizer.next((String) null);
					if (targetpoint.nameTarget != null && targetpoint.nameTarget.startsWith("Bridge"))
						targetpoint.nameTarget = null;
					int i_15_ = numbertokenizer.next(0);
					int i_16_ = numbertokenizer.next(0);
					if (i_15_ != 0 && i_16_ != 0)
					{
						targetpoint.x = (float) i_15_;
						targetpoint.y = (float) i_16_;
					}

					switch (targetpoint.type)
					{
						case 0:
							targetpoint.icon = IconDraw.get("icons/tdestroyair.mat");
							if (targetpoint.nameTarget != null && sectfile.exist("Chiefs", targetpoint.nameTarget))
								targetpoint.icon = IconDraw.get("icons/tdestroychief.mat");
						break;
						case 1:
							targetpoint.icon = IconDraw.get("icons/tdestroyground.mat");
						break;
						case 2:
							targetpoint.icon = IconDraw.get("icons/tdestroybridge.mat");
							targetpoint.nameTarget = null;
						break;
						case 3:
							targetpoint.icon = IconDraw.get("icons/tinspect.mat");
						break;
						case 4:
							targetpoint.icon = IconDraw.get("icons/tescort.mat");
						break;
						case 5:
							targetpoint.icon = IconDraw.get("icons/tdefence.mat");
						break;
						case 6:
							targetpoint.icon = IconDraw.get("icons/tdefenceground.mat");
						break;
						case 7:
							targetpoint.icon = IconDraw.get("icons/tdefencebridge.mat");
							targetpoint.nameTarget = null;
						break;
					}
					if (targetpoint.nameTarget != null)
					{
						if (sectfile.exist("Chiefs", targetpoint.nameTarget))
						{
							try
							{
								StringTokenizer stringtokenizer = (new StringTokenizer(sectfile.get("Chiefs", targetpoint.nameTarget, (String) null)));
								String string = stringtokenizer.nextToken();
								int i_17_ = string.indexOf(".");
								targetpoint.nameTarget = (I18N.technic(string.substring(0, i_17_)) + " " + I18N.technic(string.substring(i_17_ + 1)));
							}
							catch (Exception exception)
							{
								targetpoint.nameTarget = null;
							}
						}
						else if (sectfile.sectionIndex(targetpoint.nameTarget) >= 0)
						{
							try
							{
								String string = sectfile.get(targetpoint.nameTarget, "Class", (String) null);
								Class var_class = ObjIO.classForName(string);
								targetpoint.nameTarget = (Property.stringValue((Object) var_class, "iconFar_shortClassName", null));
							}
							catch (Exception exception)
							{
								targetpoint.nameTarget = null;
							}
						}
						else
							targetpoint.nameTarget = null;
					}
					arraylist.add(targetpoint);
				}
			}
		}
	}

	public static void drawTargets(GUIRenders guirenders, TTFont ttfont, Mat mat, CameraOrtho2D cameraortho2d, ArrayList arraylist)
	{
		int i = arraylist.size();
		if (i != 0)
		{
			GPoint gpoint = guirenders.getMouseXY();
			int i_18_ = -1;
			float f = gpoint.x;
			float f_19_ = guirenders.win.dy - 1.0F - gpoint.y;
			float f_20_ = (float) (IconDraw.scrSizeX() / 2);
			float f_21_ = f;
			float f_22_ = f_19_;
			IconDraw.setColor(-16711681);
			for (int i_23_ = 0; i_23_ < i; i_23_++)
			{
				TargetPoint targetpoint = (TargetPoint) arraylist.get(i_23_);
				if (targetpoint.icon != null)
				{
					float f_24_ = (float) (((double) targetpoint.x - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
					float f_25_ = (float) (((double) targetpoint.y - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
					IconDraw.render(targetpoint.icon, f_24_, f_25_);
					if (f_24_ >= f - f_20_ && f_24_ <= f + f_20_ && f_25_ >= f_19_ - f_20_ && f_25_ <= f_19_ + f_20_)
					{
						i_18_ = i_23_;
						f_21_ = f_24_;
						f_22_ = f_25_;
					}
				}
			}
			if (i_18_ != -1)
			{
				TargetPoint targetpoint = (TargetPoint) arraylist.get(i_18_);
				for (int i_26_ = 0; i_26_ < 3; i_26_++)
					tip[i_26_] = null;
				if (targetpoint.importance == 0)
					tip[0] = I18N.gui("brief.Primary");
				else
					tip[0] = I18N.gui("brief.Secondary");
				switch (targetpoint.type)
				{
					case 0:
						tip[1] = I18N.gui("brief.Destroy");
					break;
					case 1:
						tip[1] = I18N.gui("brief.DestroyGround");
					break;
					case 2:
						tip[1] = I18N.gui("brief.DestroyBridge");
					break;
					case 3:
						tip[1] = I18N.gui("brief.Inspect");
					break;
					case 4:
						tip[1] = I18N.gui("brief.Escort");
					break;
					case 5:
						tip[1] = I18N.gui("brief.Defence");
					break;
					case 6:
						tip[1] = I18N.gui("brief.DefenceGround");
					break;
					case 7:
						tip[1] = I18N.gui("brief.DefenceBridge");
					break;
				}
				if (targetpoint.nameTarget != null)
					tip[2] = targetpoint.nameTarget;
				float f_27_ = ttfont.width(tip[0]);
				int i_28_ = 1;
				for (int i_29_ = 1; i_29_ < 3; i_29_++)
				{
					if (tip[i_29_] == null)
						break;
					i_28_ = i_29_;
					float f_30_ = ttfont.width(tip[i_29_]);
					if (f_27_ < f_30_)
						f_27_ = f_30_;
				}
				float f_31_ = (float) -ttfont.descender();
				float f_32_ = (float) ttfont.height() + f_31_;
				f_27_ += 2.0F * f_31_;
				float f_33_ = f_32_ * (float) (i_28_ + 1) + 2.0F * f_31_;
				float f_34_ = f_21_ - f_27_ / 2.0F;
				float f_35_ = f_22_ + f_20_;
				if (f_34_ + f_27_ > guirenders.win.dx)
					f_34_ = guirenders.win.dx - f_27_;
				if (f_35_ + f_33_ > guirenders.win.dy)
					f_35_ = guirenders.win.dy - f_33_;
				if (f_34_ < 0.0F)
					f_34_ = 0.0F;
				if (f_35_ < 0.0F)
					f_35_ = 0.0F;
				Render.drawTile(f_34_, f_35_, f_27_, f_33_, 0.0F, mat, -813694977, 0.0F, 0.0F, 1.0F, 1.0F);
				Render.drawEnd();
				for (int i_36_ = 0; i_36_ <= i_28_; i_36_++)
					ttfont.output(-16777216, f_34_ + f_31_, (f_35_ + f_31_ + (float) (i_28_ - i_36_) * f_32_ + f_31_), 0.0F, tip[i_36_]);
			}
		}
	}

	/*
	 * private void drawTargets() { drawTargets(renders, gridFont, emptyMat,
	 * cameraMap2D, targets); }
	 */

	private Mat getIconAir(int i)
	{
		String string;
		switch (i)
		{
			case 0:
				string = "normfly";
			break;
			case 1:
				string = "takeoff";
			break;
			case 2:
				string = "landing";
			break;
			case 3:
				string = "gattack";
			break;
			default:
				return null;
		}
		return IconDraw.get("icons/" + string + ".mat");
	}

	private void drawPlayerPath()
	{
		checkPlayerPath();
		int i = playerPath.size();
		if (i == 0)
			return;
		if (lineNXYZ.length / 3 <= i)
			lineNXYZ = new float[(i + 1) * 3];
		lineNCounter = 0;
		for (int j = 0; j < i; j++)
		{
			PathPoint pathpoint = (PathPoint) playerPath.get(j);
			lineNXYZ[lineNCounter * 3 + 0] = (float) (((double) pathpoint.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
			lineNXYZ[lineNCounter * 3 + 1] = (float) (((double) pathpoint.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
			lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
			lineNCounter++;
		}

		com.maddox.il2.engine.Render.drawBeginLines(-1);
		com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, 2.0F, 0xff000000, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE
				| com.maddox.il2.engine.Mat.BLEND, 3);
		com.maddox.il2.engine.Render.drawEnd();
		com.maddox.il2.engine.IconDraw.setColor(0xff00ffff);
		float f = 0.0F;
		for (int k = 0; k < i; k++)
		{
			PathPoint pathpoint1 = (PathPoint) playerPath.get(k);
			float f1 = (float) (((double) pathpoint1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
			float f2 = (float) (((double) pathpoint1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
			com.maddox.il2.engine.IconDraw.render(getIconAir(pathpoint1.type), f1, f2);
			if (k == i - 1)
				gridFont.output(0xff000000, (int) f1 + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int) f2 - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + (k + 1));
			float f3 = 1.0F - (float) curScale / 7F;
			if (k >= i - 1)
				continue;
			PathPoint pathpoint2 = (PathPoint) playerPath.get(k + 1);
			com.maddox.JGP.Point3f point3f = new Point3f(pathpoint1.x, pathpoint1.y, 0.0F);
			com.maddox.JGP.Point3f point3f1 = new Point3f(pathpoint2.x, pathpoint2.y, 0.0F);
			point3f.sub(point3f1);
			float f4 = 57.32484F * (float) java.lang.Math.atan2(point3f.x, point3f.y);
			for (f4 = (f4 + 180F) % 360F; f4 < 0.0F; f4 += 360F);
			for (; f4 >= 360F; f4 -= 360F);
			f4 = java.lang.Math.round(f4);
			float f5 = 0.0F;
			float f6 = 0.0F;
			if (f4 >= 0.0F && f4 < 90F)
			{
				f5 = 15F;
				f6 = -40F;
				if (f >= 270F && f <= 360F)
				{
					f5 = -70F;
					f6 = 60F;
				}
			}
			else if (f4 >= 90F && f4 < 180F)
			{
				f5 = 15F;
				f6 = 60F;
				if (f >= 180F && f < 270F)
				{
					f5 = -70F;
					f6 = -15F;
				}
			}
			else if (f4 >= 180F && f4 < 270F)
			{
				f5 = -70F;
				f6 = 60F;
				if (f >= 90F && f < 180F)
				{
					f5 = 15F;
					f6 = 60F;
				}
			}
			else if (f4 >= 270F && f4 <= 360F)
			{
				f5 = -70F;
				f6 = -40F;
				if (f >= 0.0F && f < 90F)
				{
					f5 = 15F;
					f6 = -40F;
				}
			}
			f5 *= f3;
			f6 *= f3;
			if (curScale >= 3)
			{
				if (f5 < 0.0F)
					f5 /= 2.0F;
				if (f6 > 0.0F)
					f6 /= 2.0F;
			}
			f = f4;
			gridFont.output(0xff000000, f1 + f5, f2 + f6, 0.0F, "" + (k + 1));
			if (curScale >= 2)
				continue;
			double d = java.lang.Math.sqrt(point3f.y * point3f.y + point3f.x * point3f.x) / 1000D;
			if (d < 0.5D)
				continue;
			java.lang.String s = " km";
			if (com.maddox.il2.game.HUD.drawSpeed() == 2 || com.maddox.il2.game.HUD.drawSpeed() == 3)
			{
				d *= 0.53995698690414429D;
				s = " nm";
			}
			java.lang.String s1 = "" + d;
			s1 = s1.substring(0, s1.indexOf(".") + 2);
			gridFont.output(0xff000000, f1 + f5, (f2 + f6) - 22F, 0.0F, (int) f4 + "\260");
			gridFont.output(0xff000000, f1 + f5, (f2 + f6) - 44F, 0.0F, s1 + s);
		}

	}

	private void checkPlayerPath()
	{
		SectFile sectfile = Main.cur().currentMissionFile;
		String string;
		if (Mission.isCoop())
		{
			string = GUINetAircraft.selectedWingName();
			if (string == null)
				string = sectfile.get("MAIN", "player", (String) null);
		}
		else
			string = sectfile.get("MAIN", "player", (String) null);
		if (string == null)
		{
			if (playerName != null)
			{
				playerPath.clear();
				playerName = null;
			}
		}
		else if (!string.equals(playerName))
		{
			playerName = string;
			playerPath.clear();
			if (playerName != null)
			{
				int i = sectfile.sectionIndex(playerName + "_WAY");
				if (i >= 0)
				{
					int i_40_ = sectfile.vars(i);
					for (int i_41_ = 0; i_41_ < i_40_; i_41_++)
					{
						PathPoint pathpoint = new PathPoint();
						String string_42_ = sectfile.var(i, i_41_);
						if ("NORMFLY".equals(string_42_))
							pathpoint.type = 0;
						else if ("TAKEOFF".equals(string_42_))
							pathpoint.type = 1;
						else if ("LANDING".equals(string_42_))
							pathpoint.type = 2;
						else if ("GATTACK".equals(string_42_))
							pathpoint.type = 3;
						else
							pathpoint.type = 0;
						String string_43_ = sectfile.value(i, i_41_);
						if (string_43_ == null || string_43_.length() <= 0)
							pathpoint.x = pathpoint.y = 0.0F;
						else
						{
							NumberTokenizer numbertokenizer = new NumberTokenizer(string_43_);
							pathpoint.x = numbertokenizer.next(-1.0E30F, -1.0E30F, 1.0E30F);
							pathpoint.y = numbertokenizer.next(-1.0E30F, -1.0E30F, 1.0E30F);
							numbertokenizer.next(0.0, 0.0, 10000.0);
							numbertokenizer.next(0.0, 0.0, 1000.0);
						}
						playerPath.add(pathpoint);
					}
				}
			}
		}
	}

	protected void doRenderMap2D()
	{
		// TODO: Added by |ZUTI|
		ZutiRadarRefresh.update(lastScale < cameraMap2D.worldScale);
        lastScale = cameraMap2D.worldScale;

		int i = (int) Math.round(ZutiMDSVariables.ZUTI_ICON_SIZE * (double) client.root.win.dx / 1024.0);
		IconDraw.setScrSize(i, i);

		// TODO: Edited by |ZUTI|: Join rendering of briefing the same way for all game modes
		try
		{
			if (!Mission.MDS_VARIABLES().zutiIcons_MovingIcons || Mission.MDS_VARIABLES().zutiIcons_MovingIcons)
				ZutiSupportMethods_GUI.drawTargets(renders, gridFont, emptyMat, cameraMap2D);
			// Draw born places on top of target icons so it is more clear
			drawBornPlaces();

			// Draw player path
			drawPlayerPath();

			drawBeacons();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	protected int findBornPlace(float x, float y)
	{
		if (id() != 40 && id() != 39)
			return -1;

		ArrayList arraylist = World.cur().bornPlaces;
		if (arraylist == null || arraylist.size() == 0)
			return -1;

		int i = arraylist.size();
		double d = (double) (IconDraw.scrSizeX() / 2) / cameraMap2D.worldScale;
		d = 2.0 * d * d;

		// System.out.println("Mouse location: " + new Float(f).toString() + ", " + new Float(f_45_).toString());
		for (int index = 0; index < i; index++)
		{
			BornPlace bornplace = (BornPlace) arraylist.get(index);

			if (bornplace.zutiDisableSpawning)
				continue;

			// System.out.println("BornPlace name - " + bornplace.bpName + ", loc: " + new Double(bornplace.place.x).toString() + ", " + new Double(bornplace.place.y).toString());

			if ((((bornplace.place.x - (double) x) * (bornplace.place.x - (double) x) + (bornplace.place.y - (double) y) * (bornplace.place.y - (double) y)) < d) && bornplace.army != 0)
			{
				// System.out.println("Returning BP in array indexed - " + new Integer(i_46_).toString());
				return index;
			}
		}
		return -1;
	}

	protected boolean isBornPlace(float f, float f_47_)
	{
		return findBornPlace(f, f_47_) >= 0;
	}

	protected void setBornPlace(float f, float f_48_)
	{
		int i = findBornPlace(f, f_48_);
		if (i >= 0)
		{
			// Selected HomeBase is valid, change country markings to default. If user changes those in Arming screen, everything is ok! If not... defaults for him!
			ZUTI_LAST_SELECTED_BORN_PLACE = ((BornPlace) World.cur().bornPlaces.get(i));

			// Can user even join this born place?If not, just return -1...
			if (!ZutiSupportMethods_Net.canUserJoinBornPlace(ZUTI_LAST_SELECTED_BORN_PLACE))
			{
				new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("mds.info.bornPlace"), i18n("mds.info.bornPlaceFull"), 3, 0.0F);
				return;
			}

			if( ZutiSupportMethods_Net.getBornPlaceAvailableAircraftList(ZUTI_LAST_SELECTED_BORN_PLACE).size() <= 0 )
			{
				new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("mds.info.bornPlace"), i18n("mds.info.bornPlaceNoAvailableAc"), 3, 0.0F);
				return;
			}
			/*
			 * UserCfg usercfg = World.cur().userCfg;
			 * if( usercfg != null &&
			 * !ZutiSupportMethods.isRegimentValidForSelectedHB
			 * (usercfg.netRegiment, bp) )
			 * {
			 * String branch = ZutiSupportMethods.getHomeBaseFirstCountry(bp);
			 * //System.out.println("GUIBriefing - returned branch 1: " +
			 * branch);
			 * branch = ZutiSupportMethods.getUserCfgRegiment( branch );
			 * //System.out.println("GUIBriefing - returned branch 2: " +
			 * branch);
			 * //String countryReturned =
			 * com.maddox.il2.ai.Regiment.getCountryFromBranch
			 * ((branch.intern()));
			 * //System.out.println("GUIBriefing - returned country: " +
			 * countryReturned);
			 * usercfg.netRegiment = branch;
			 * usercfg.netSquadron = 0;
			 * }
			 */
			NetUser netuser = (NetUser) NetEnv.host();
			int userArmy = netuser.getArmy();
			netuser.setBornPlace(i);

			if (userArmy != netuser.getArmy() && briefSound != null)
			{
				String string = Main.cur().currentMissionFile.get("MAIN", "briefSound" + netuser.getArmy());
				if (string != null)
				{
					briefSound = string;
					CmdEnv.top().exec("music LIST " + briefSound);
				}
			}

			// TODO: Added by |ZUTI|: when player changes his army, reset radars!
			// --------------------------------------------------
			ZutiRadarRefresh.findRadars(ZutiSupportMethods.getPlayerArmy());

			// Since radars were refetched and player army might change,
			// we have to re-check also scout validity...
			ZutiSupportMethods_GUI.fillAirInterval(GUI.pad, true);
			// ----------------------------------------------------

			fillBeacons();
		}
	}

	protected void doMouseButton(int i, boolean bool, float f, float f_50_)
	{
		int i_51_ = i;
		if (renders != null)
		{
			/* empty */
		}
		if (i_51_ == 0)
		{
			bLPressed = bool;
			if (bSelectBorn)
			{
				if (bLPressed)
				{
					float f_52_ = (float) (cameraMap2D.worldXOffset + (double) f / cameraMap2D.worldScale);
					float f_53_ = (float) (cameraMap2D.worldYOffset + ((double) (renders.win.dy - f_50_ - 1.0F) / cameraMap2D.worldScale));
					setBornPlace(f_52_, f_53_);
				}
				return;
			}
		}
		super.doMouseButton(i, bool, f, f_50_);
	}

	protected void doMouseMove(float f, float f_54_)
	{
		if (bLPressed && !bSelectBorn)
			super.doMouseMove(f, f_54_);
		else
		{
			float f_55_ = (float) (cameraMap2D.worldXOffset + (double) f / cameraMap2D.worldScale);
			float f_56_ = (float) (cameraMap2D.worldYOffset + ((double) (renders.win.dy - f_54_ - 1.0F) / cameraMap2D.worldScale));
			bSelectBorn = isBornPlace(f_55_, f_56_);
			renders.mouseCursor = bSelectBorn ? 2 : 3;
		}
	}

	protected void fillMap() throws Exception
	{
		super.fillMap();
		SectFile sectfile = Main.cur().currentMissionFile;

		// TODO: Comment by |ZUTI|: Join rendering of briefing the same way for all game modes
		try
		{
			iconBornPlace = IconDraw.get("icons/born.mat");
			iconPlayer = IconDraw.get("icons/player.mat");

			// TODO: Comment by |ZUTI|: targets were not yet drawn
			ZutiSupportMethods_GUI.setTargetsLoaded(false);
			// TODO: Comment by |ZUTI|: Fill targets from mission file but only if mission is NOT single player
			if (!(Mission.cur() == null))
			{
				ZutiSupportMethods_GUI.fillTargetPoints(sectfile);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	protected void clientRender()
	{
		GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
		GUIBriefingGeneric.DialogClient dialogclient_81_ = dialogclient;
		float f = dialogclient.x1024(427.0F);
		float f_82_ = dialogclient.y1024(633.0F);
		float f_83_ = dialogclient.x1024(170.0F);
		float f_84_ = dialogclient.y1024(48.0F);
		
		dialogclient_81_.draw(f, f_82_, f_83_, f_84_, 1, i18n("brief.Fly"));
	}

	protected void clientRender_old()
	{
		GUIBriefingGeneric.DialogClient dialogclient = dialogClient;
		GUIBriefingGeneric.DialogClient dialogclient_57_ = dialogclient;
		float f = dialogclient.x1024(768.0F);
		float f_58_ = dialogclient.y1024(656.0F);
		float f_59_ = dialogclient.x1024(160.0F);
		float f_60_ = dialogclient.y1024(48.0F);
		dialogclient_57_.draw(f, f_58_, f_59_, f_60_, 2, i18n("brief.Fly"));
	}

	protected String infoMenuInfo()
	{
		return i18n("brief.info");
	}

	public GUIBriefing(int i)
	{
		super(i);

		playerPath = new ArrayList();
		targets = new ArrayList();
		beacons = new ArrayList();
		lineNXYZ = new float[6];
		bSelectBorn = false;
		lastScale = 0.0D;
	}

	protected AirportCarrier getCarrier(NetUser netuser)
	{
		BornPlace bornplace = (BornPlace) World.cur().bornPlaces.get(netuser.getBornPlace());
		if (!World.land().isWater(bornplace.place.x, bornplace.place.y))
		{
			return null;
		}
		else
		{
			Loc loc = new Loc(bornplace.place.x, bornplace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
			AirportCarrier airportcarrier = (AirportCarrier) Airport.nearest(loc.getPoint(), -1, 4);

			if (airportcarrier != null && airportcarrier.ship() instanceof TestRunway)
				return null;

			return airportcarrier;
		}
	}

	protected boolean isCarrierDeckFree(com.maddox.il2.net.NetUser netuser)
	{
		try
		{
			AirportCarrier airportcarrier = null;
			BornPlace bornplace = (BornPlace) World.cur().bornPlaces.get(netuser.getBornPlace());
			if (bornplace.zutiAirspawnOnly || !World.cur().diffCur.Takeoff_N_Landing || !World.land().isWater(bornplace.place.x, bornplace.place.y))
				return true;
			Loc loc = new Loc(bornplace.place.x, bornplace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
			airportcarrier = (AirportCarrier) Airport.nearest(loc.getPoint(), -1, 4);
			if (airportcarrier == null || !airportcarrier.ship().isAlive() || airportcarrier.ship().zutiIsStatic())
				return false;
			UserCfg usercfg = World.cur().userCfg;
			Class acClass = (Class) Property.value(usercfg.netAirName, "airClass", null);
			CellAirField cellairfield = airportcarrier.ship().getCellTO();
			CellAirPlane cellairplane = Aircraft.getCellAirPlane(acClass);
			if (!cellairfield.findPlaceForAirPlane(cellairplane))
				return false;

			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return false;
	}
}