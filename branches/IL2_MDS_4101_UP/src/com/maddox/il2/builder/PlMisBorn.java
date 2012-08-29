/*4.10.1 compatible class - removed some of the DT deck spawning procedures*/
package com.maddox.il2.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.il2.game.ZutiAircraft;
import java.util.StringTokenizer;

public class PlMisBorn extends Plugin
{
	//private static final int rMIN = 500;
	//private static final int rMAX = 10000;
	//private static final int rDEF = 1000;
	//private static final int rSTEP = 50;
	protected ArrayList allActors = new ArrayList();
	
	//TODO: modified by |ZUTI|: added new Item
	Item[] item = { new Item(Plugin.i18n("BornPlace")), new Item(Plugin.i18n("mds.BornPlace_STD")) };
	
	private Point2d p2d = new Point2d();
	private Point2d p2dt = new Point2d();
	private Point3d p3d = new Point3d();
	//private static final int NCIRCLESEGMENTS = 48;
	private static float[] _circleXYZ = new float[144];
	private PlMission pluginMission;
	private int startComboBox1;
	private GWindowMenuItem viewType;
	private String[] _actorInfo = new String[1];
	GWindowTabDialogClient.Tab tabTarget;
	
	//Tab Properties
	GWindowComboControl wArmy;
	GWindowHSliderInt wR;
	GWindowCheckBox wParachute;
	//Tab Aircraft
	GWindowTabDialogClient.Tab tabAircraft;
	Table lstAvailable;
	Table lstInReserve;
	GWindowButton bAddAll;
	GWindowButton bAdd;
	GWindowButton bRemAll;
	GWindowButton bRem;
	GWindowLabel lSeparate;
	GWindowBoxSeparate bSeparate;
	GWindowLabel lCountry;
	GWindowComboControl cCountry;
	static ArrayList lstCountry = new ArrayList();
	GWindowButton bCountryAdd;
	GWindowButton bCountryRem;
	GWindowLabel lYear;
	GWindowComboControl cYear;
	static ArrayList lstYear = new ArrayList();
	GWindowButton bYearAdd;
	GWindowButton bYearRem;
	GWindowLabel lType;
	GWindowComboControl cType;
	static ArrayList lstType = new ArrayList();
	GWindowButton bTypeAdd;
	GWindowButton bTypeRem;
	
	static Class class$com$maddox$il2$objects$air$TypeStormovik;
	static Class class$com$maddox$il2$objects$air$TypeFighter;
	static Class class$com$maddox$il2$objects$air$TypeBomber;
	static Class class$com$maddox$il2$objects$air$TypeScout;
	static Class class$com$maddox$il2$objects$air$TypeDiveBomber;
	static Class class$com$maddox$il2$objects$air$TypeSailPlane;
	static Class class$com$maddox$il2$objects$air$Scheme1;
	static Class class$com$maddox$il2$builder$PlMisBorn;
	
	class Table extends GWindowTable
	{
		public ArrayList lst = new ArrayList();
		
		//Added by |ZUTI|
		//----------------------------------------------
		public boolean zutiShowAcWeaponsWindow = false;
		//----------------------------------------------
		
		public int countRows()
		{
			return lst != null ? lst.size() : 0;
		}

		public Object getValueAt(int i, int i_0_)
		{
			if (lst == null) return null;
			if (i < 0 || i >= lst.size()) return null;
			
			//TODO: Modified by |ZUTI
			//-----------------------------------
			ZutiAircraft zac = (ZutiAircraft) lst.get(i);
			return zac;
			//-----------------------------------
		}

		public void resolutionChanged()
		{
			vSB.scroll = rowHeight(0);
			super.resolutionChanged();
		}
		
		public Table(GWindow gwindow, String string, float f, float f_1_, float f_2_, float f_3_)
		{
			super(gwindow, f, f_1_, f_2_, f_3_);
			bColumnsSizable = false;
			addColumn(string, null);
			vSB.scroll = rowHeight(0);
			resized();
		}
	}

	static class Item
	{
		public String name;

		public Item(String object)
		{
			name = object;
		}
	}

	//TODO: Added by |ZUTI| code
	//-------------------------------
	static class TableItem
	{
		public ZutiAircraft zac;

		public TableItem(ZutiAircraft object)
		{
			zac = object;
		}
	}
	//-------------------------------

	public void renderMap2DAfter()
	{
		if (!Plugin.builder.isFreeView() && viewType.bChecked)
		{
			Actor actor = Plugin.builder.selectedActor();
			int i = allActors.size();
			for (int i_4_ = 0; i_4_ < i; i_4_++)
			{
				ActorBorn actorborn = (ActorBorn) allActors.get(i_4_);
				if (Plugin.builder.project2d(actorborn.pos.getAbsPoint(), p2d))
				{
					int i_5_ = Army.color(actorborn.getArmy());
					if (actorborn == actor) i_5_ = Builder.colorSelected();
					IconDraw.setColor(i_5_);
					IconDraw.render(actorborn, p2d.x, p2d.y);
					actorborn.pos.getAbs(p3d);
					p3d.x += (double) actorborn.r;
					if (Plugin.builder.project2d(p3d, p2dt))
					{
						double d = p2dt.x - p2d.x;
						if (d > (double) (Plugin.builder.conf.iconSize / 3))
							drawCircle(p2d.x, p2d.y, d, i_5_);
					}
				}
			}
		}
	}

	private void drawCircle(double d, double d_6_, double d_7_, int i)
	{
		int i_8_ = 48;
		double d_9_ = 6.283185307179586 / (double) i_8_;
		double d_10_ = 0.0;
		for (int i_11_ = 0; i_11_ < i_8_; i_11_++)
		{
			_circleXYZ[i_11_ * 3 + 0] = (float) (d + d_7_ * Math.cos(d_10_));
			_circleXYZ[i_11_ * 3 + 1] = (float) (d_6_ + d_7_ * Math.sin(d_10_));
			_circleXYZ[i_11_ * 3 + 2] = 0.0F;
			d_10_ += d_9_;
		}
		Render.drawBeginLines(-1);
		Render.drawLines(_circleXYZ, i_8_, 1.0F, i, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 4);
		Render.drawEnd();
	}

	public boolean save(SectFile sectfile)
	{
		int i = allActors.size();
		if (i != 0)
		{
			int bpSectionId = sectfile.sectionAdd("BornPlace");
			for (int bpCount = 0; bpCount < i; bpCount++)
			{
				ActorBorn actorborn = (ActorBorn) allActors.get(bpCount);
				
				//TODO: Added by |ZUTI|: re-check actorborns for carrier spawns
				//-------------------------------------------------------------
				//System.out.println("SPAWN POINTS = " + actorborn.zutiCarrierSpawnPoints + ", selected=" + actorborn.zutiCarrierSelectedSpawnPointsIndex);
				if( actorborn.zutiCarrierSpawnPoints > 0 && actorborn.zutiCarrierSelectedSpawnPointsIndex == 0 )
					actorborn.zutiCarrierSelectedSpawnPointsIndex = actorborn.zutiCarrierSpawnPoints;
				//-------------------------------------------------------------
				
				//TODO: Modified by |ZUTI| - added various things to home base mission line
				sectfile.lineAdd(bpSectionId, (	"" + 
											actorborn.getArmy() + " " + 
											actorborn.r + " " + 
											(int) actorborn.pos.getAbsPoint().x + " " + 
											(int) actorborn.pos.getAbsPoint().y + " " + 
											(actorborn.bParachute ? "1" : "0") + " " + 
											actorborn.zutiSpawnHeight  + " " + 
											actorborn.zutiSpawnSpeed  + " " + 
											actorborn.zutiSpawnOrient + " " + 
											actorborn.zutiMaxBasePilots + " "  + 
											ZutiSupportMethods.boolToInt(actorborn.zutiCanThisHomeBaseBeCaptured) + " " + 
											actorborn.zutiRadarHeight_MIN + " " + 
											actorborn.zutiRadarHeight_MAX + " " + 
											actorborn.zutiRadarRange + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiAirspawnOnly) + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiEnablePlaneLimits) + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiDecreasingNumberOfPlanes) + " " +
											ZutiSupportMethods.boolToInt(actorborn.zutiDisableSpawning) + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiIsFrictionEnabled()) + " " + 
											actorborn.zutiFriction + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiIncludeStaticPlanes) + " " +
											ZutiSupportMethods.boolToInt(actorborn.zutiStaticPositionOnly) + " " + 
											actorborn.zutiCapturingRequiredParatroopers + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiDisableRendering) + " " + 
											actorborn.zutiCarrierSelectedSpawnPointsIndex + " " +
											ZutiSupportMethods.boolToInt(actorborn.zutiIsStandAloneBornPlace) + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiEnableQueue) + " " +
											actorborn.zutiDeckClearTimeout + " " +
											ZutiSupportMethods.boolToInt(actorborn.zutiAirspawnIfQueueFull) + " " + 
											ZutiSupportMethods.boolToInt(actorborn.zutiPilotInVulnerableWhileOnTheDeck) + " " +
											ZutiSupportMethods.boolToInt(actorborn.zutiCaptureOnlyIfNoChiefPresent)	)
										);
				
				//TODO: Added by |ZUTI|
				//--------------------------------------------------------------------------
				ArrayList results = actorborn.zutiAircraft;
				if( results != null )
				{
					int	sectionId = sectfile.sectionAdd("BornPlace" + bpCount);
						
					for( int x=0; x<results.size(); x++ )
					{
						ZutiAircraft zac = (ZutiAircraft)results.get(x);
						sectfile.lineAdd(sectionId, zac.getMissionLine(actorborn.zutiEnablePlaneLimits));
					}
				}

				//Save born place specific RRR settings
				if( actorborn.zutiOverrideDefaultRRRSettings )
					ZutiSupportMethods_Builder.saveBornPlaceRRR(actorborn, sectfile);
				//Save born place new planes for captured event
				ZutiSupportMethods_Builder.saveBornPlaceCapturedPlanes(actorborn, sectfile);
				//Save born place selected countries
				ZutiSupportMethods_Builder.saveBornPlaceCountries(actorborn, sectfile);
				//Save born place spawn places
				ZutiSupportMethods_Builder.saveSpawnPointholdersForSTDBornPlace(actorborn, sectfile);
				//--------------------------------------------------------------------------
			}
			//TODO: Added by |ZUTI|
			//-----------------------------------------------------
			ZutiSupportMethods_Builder.clearSTDSpawnPlaceholdersSavedStatus();
			//-----------------------------------------------------
		}
		return true;
	}	
	
	public void load(SectFile sectfile)
	{
		//TODO: Added by |ZUTI|
		boolean zutiMdsSectionIdExists = sectfile.sectionIndex("MDS") > -1;
		
		int i = sectfile.sectionIndex("BornPlace");
		if (i >= 0)
		{
			int i_18_ = sectfile.vars(i);
			Point3d point3d = new Point3d();
			for (int i_19_ = 0; i_19_ < i_18_; i_19_++)
			{
				NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, i_19_));
				int i_20_ = numbertokenizer.next(0, 0, Army.amountNet() - 1);
				int i_21_ = numbertokenizer.next(1000, 500, 10000);
				point3d.x = (double) numbertokenizer.next(0);
				point3d.y = (double) numbertokenizer.next(0);
				boolean bool = numbertokenizer.next(1) == 1;
				
				//Modified by |ZUTI|: here we are loading only original born places
				ActorBorn actorborn = insert(point3d, false, false, false, false);
				//TODO: Added by |ZUTI|
				//-------------------------------------------------------------------------
				try
				{
					actorborn.zutiSpawnHeight = numbertokenizer.next(1000, 0, 10000);
					actorborn.zutiSpawnSpeed = numbertokenizer.next(200, 0, 500);
					actorborn.zutiSpawnOrient = numbertokenizer.next(0, 0, 360);
					actorborn.zutiMaxBasePilots = numbertokenizer.next(0, 0, 99999);
					actorborn.zutiCanThisHomeBaseBeCaptured = false;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiCanThisHomeBaseBeCaptured = true;
					actorborn.zutiRadarHeight_MIN = numbertokenizer.next(0, 0, 99999);
					actorborn.zutiRadarHeight_MAX = numbertokenizer.next(5000, 0, 99999);
					actorborn.zutiRadarRange = numbertokenizer.next(50, 1, 99999);
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiAirspawnOnly = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiEnablePlaneLimits = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiDecreasingNumberOfPlanes = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiDisableSpawning = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiEnableFriction = true;
					actorborn.zutiFriction = numbertokenizer.next(3.8D, 0.0D, 10.0D);
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiIncludeStaticPlanes = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiStaticPositionOnly = true;
					actorborn.zutiCapturingRequiredParatroopers = numbertokenizer.next(100, 0, 99999);
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiDisableRendering = true;
					actorborn.zutiCarrierSelectedSpawnPointsIndex = numbertokenizer.next(0, 0, 99999);
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiIsStandAloneBornPlace = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiEnableQueue = true;
					actorborn.zutiDeckClearTimeout = numbertokenizer.next(30, 0, 99999);
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiAirspawnIfQueueFull = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiPilotInVulnerableWhileOnTheDeck = true;
					if(numbertokenizer.next(0, 0, 1) == 1 )
						actorborn.zutiCaptureOnlyIfNoChiefPresent = true;
					
					//Check again, perhaps existing home bases are of STD type and are on carriers...
					if( actorborn.zutiIsStandAloneBornPlace && ZutiSupportMethods_Builder.isStdBaseOnCarrier(allActors, point3d, actorborn.r) )
					{
						System.out.println("You are probably trying to load stand alone home base from mission to a carrier! Not possible.");
						System.out.println("  Ignoring home base at x=" + (int)point3d.x + ", y=" + (int)point3d.y);
						allActors.remove(actorborn);
						actorborn = null;
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				//-------------------------------------------------------------------------
				
				if (actorborn != null)
				{
					actorborn.zutiAircraft.clear();
					actorborn.setArmy(i_20_);
					actorborn.r = i_21_;
					actorborn.bParachute = bool;
					int i_22_ = sectfile.sectionIndex("BornPlace" + i_19_);
					if (i_22_ >= 0)
					{
						int i_23_ = sectfile.vars(i_22_);
						for (int i_24_ = 0; i_24_ < i_23_; i_24_++)
						{
							//Changed by |ZUTI|: Rewritten with usage of StringTokenizer
							//Original
							//String string = sectfile.var(i_22_, i_24_);
							//|ZUTI|
							//--------------------------------------------------------------
							String readLine = sectfile.line(i_22_, i_24_);
							StringTokenizer stringtokenizer = new StringTokenizer(readLine);
							
							ZutiAircraft zac = new ZutiAircraft();
							ZutiSupportMethods.fillZutiAircraft(zac, stringtokenizer, zutiMdsSectionIdExists);
							String string = zac.getAcName();
							if (string != null)
							{
								string = string.intern();
								Class var_class = ((Class) Property.value(string, "airClass", null));
								if (var_class != null && (Property.containsValue(var_class, "cockpitClass")))
								{
									//we add the name to this array in either case
									actorborn.zutiAircraft.add(zac);
								}
							}
						}
					}
					//if (actorborn.zutiAircraft.size() == 0)
					//	addAllAircraft(actorborn.zutiAircraft);
					//--------------------------------------------------------------
						
					//TODO: Added by |ZUTI|
					//--------------------------------------------------------------
					ZutiSupportMethods_Builder.loadBornPlaceRRR(actorborn, sectfile);
					ZutiSupportMethods_Builder.loadBornPlaceCapturedPlanes(actorborn, sectfile);
					ZutiSupportMethods_Builder.loadBornPlaceCountries_oldMDS(actorborn, sectfile);
					ZutiSupportMethods_Builder.loadBornPlaceCountries_newMDS(actorborn, sectfile);
					//--------------------------------------------------------------
				}
			}
		}
	}	
	
	public void deleteAll()
	{
		int i = allActors.size();
		for (int i_25_ = 0; i_25_ < i; i_25_++)
		{
			ActorBorn actorborn = (ActorBorn) allActors.get(i_25_);
			actorborn.destroy();
		}
		allActors.clear();
	}

	public void delete(Actor actor)
	{
		allActors.remove(actor);
		actor.destroy();
	}

	public void insert(Loc loc, boolean bool)
	{
		int i = Plugin.builder.wSelect.comboBox1.getSelected();
		int i_28_ = Plugin.builder.wSelect.comboBox2.getSelected();
		if (i == startComboBox1 && (i_28_ >= 0 && i_28_ < item.length))
		{
			//TODO: Modified by |ZUTI|: Modified insert call
			insert(loc.getPoint(), bool, i_28_ == 0 ? false:true, true, true);
		}
	}
	
	//TODO: Added by |ZUTI|: last two parameters
	private ActorBorn insert(Point3d point3d, boolean bool, boolean zutiIsStandAlone, boolean zutiSelectInsertedActorBorn, boolean zutiPerformPositionChecks)
	{
		if (allActors.size() >= 255)
		{
			//System.out.println("NULL 1");
			return null;
		}
		//TODO: Added by |ZUTI|
		//-----------------------------------------------------------------------
		if( zutiPerformPositionChecks )
		{
			if (!ZutiSupportMethods_Builder.canPlaceBornPlaceAtLocation(allActors, point3d, 3000F, zutiIsStandAlone))
			{
				//System.out.println("NULL 2");
				return null;
			}
		}
		
		//Remove "duplicates" that are placed on each other
		if( ZutiSupportMethods_Builder.isBornPlaceExistingAtCoordinates(allActors, point3d.x, point3d.y) )
		{
			//System.out.println("NULL 3");
			return null;
		}
		
		//Add some separation for easier selecting of marker and actor in FMB
		if( zutiPerformPositionChecks )
			point3d.y = point3d.y + ZutiSupportMethods_Builder.HOME_BASE_Y_SEPARATION;
		//-----------------------------------------------------------------------
		ActorBorn actorborn;
		try
		{
			ActorBorn actorborn_27_ = new ActorBorn(point3d);
			//TODO: Disabled by |ZUTI|: don't add all AC to available list.
			//addAllAircraft(actorborn_27_.zutiAircraft);
			Plugin.builder.align(actorborn_27_);
			Property.set(actorborn_27_, "builderSpawn", "");
			Property.set(actorborn_27_, "builderPlugin", this);
			allActors.add(actorborn_27_);
			if (bool) Plugin.builder.setSelected(actorborn_27_);
			PlMission.setChanged();
			actorborn = actorborn_27_;
			
			//TODO: Added by |ZUTI|
			//-------------------------------------------------------------------
			actorborn.zutiCarrierSpawnPoints = ZutiSupportMethods_Builder.getCarrierSpawnPointsBasedOnyCarrierType(point3d);
			actorborn.zutiCarrierSelectedSpawnPointsIndex = actorborn.zutiCarrierSpawnPoints;
			if( zutiIsStandAlone )
				actorborn.zutiIsStandAloneBornPlace = true;
			else
				actorborn.zutiIsStandAloneBornPlace = false;
			
			if( zutiSelectInsertedActorBorn )
				Plugin.builder.setSelected(actorborn);
			else
				changeType();
			//-------------------------------------------------------------------
		}
		catch (Exception exception)
		{
			return null;
		}
		return actorborn;
	}

	public void changeType()
	{
		Plugin.builder.setSelected(null);
	}

	private void updateView()
	{
		int i = allActors.size();
		for (int i_29_ = 0; i_29_ < i; i_29_++)
		{
			ActorBorn actorborn = (ActorBorn) allActors.get(i_29_);
			actorborn.drawing(viewType.bChecked);
		}
	}

	public void configure()
	{
		if (Plugin.getPlugin("Mission") == null) 
			throw new RuntimeException("PlMisBorn: plugin 'Mission' not found");
		pluginMission = (PlMission)Plugin.getPlugin("Mission");
	}

	private void fillComboBox2(int i)
	{
		if (i == startComboBox1)
		{
			if (Plugin.builder.wSelect.curFilledType != i)
			{
				Plugin.builder.wSelect.curFilledType = i;
				Plugin.builder.wSelect.comboBox2.clear(false);
				for (int i_30_ = 0; i_30_ < item.length; i_30_++)
				{
					Plugin.builder.wSelect.comboBox2.add(item[i_30_].name);
				}
				Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
			}
			Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
			
			Plugin.builder.wSelect.setMesh(null, true);
		}
	}

	public void viewTypeAll(boolean bool)
	{
		viewType.bChecked = bool;
		updateView();
	}

	public String[] actorInfo(Actor actor)
	{
		_actorInfo[0] = Plugin.i18n("BornPlace");
		return _actorInfo;
	}

	public void syncSelector()
	{
		ActorBorn actorborn = (ActorBorn)Plugin.builder.selectedActor();
		fillComboBox2(startComboBox1);
		
		//TODO: Modified by |ZUTI|
		//---------------------------------------------------------------
		if( !actorborn.zutiIsStandAloneBornPlace )
			Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
		else
			Plugin.builder.wSelect.comboBox2.setSelected(1, true, false);
		//---------------------------------------------------------------
		
		Plugin.builder.wSelect.tabsClient.addTab(1, tabTarget);
		Plugin.builder.wSelect.tabsClient.addTab(2, tabAircraft);
		Plugin.builder.wSelect.tabsClient.addTab(3, ZutiSupportMethods_Builder.getPlMisBornSpawnTab());
		Plugin.builder.wSelect.tabsClient.addTab(4, ZutiSupportMethods_Builder.getPlMisBornCapturingTab());
		Plugin.builder.wSelect.tabsClient.addTab(5, ZutiSupportMethods_Builder.getPlMisBornRRRTab());
		
		fillTabAircraft();
		
		//TODO: Added by |ZUTI|
		//--------------------------------------------------------------
		ZutiSupportMethods_Builder.resetPlMisBornVariableValues(this);
		//--------------------------------------------------------------
		
		wR.setPos((actorborn.r - 500) / 50, false);
		wArmy.setSelected(actorborn.getArmy(), true, false);
	}

	public void updateSelector()
	{
		fillTabAircraft();
	}

	public void createGUI()
	{
		//TODO: Added by |ZUTI|: lets enlarge Object window a bit...
		//--------------------------------------------------------------
		//Plugin.builder.wSelect.metricWin = new GRegion(2, 2, 40, 36);
		//--------------------------------------------------------------
		
		startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
		Plugin.builder.wSelect.comboBox1.add(Plugin.i18n("BornPlace"));
		Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener()
		{
			public boolean notify(GWindow gwindow, int i, int i_32_)
			{
				int i_33_ = Plugin.builder.wSelect.comboBox1.getSelected();
				if (i_33_ >= 0 && i == 2)
					PlMisBorn.this.fillComboBox2(i_33_);
				return false;
			}
		});
		int i;
		for (i = Plugin.builder.mDisplayFilter.subMenu.size() - 1; i >= 0; i--)
		{
			if (pluginMission.viewBridge == Plugin.builder.mDisplayFilter.subMenu.getItem(i))
				break;
		}
		if (--i >= 0)
		{
			viewType = Plugin.builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showBornPlaces"), null)
			{
				public void execute()
				{
					bChecked = !bChecked;
					PlMisBorn.this.updateView();
				}
			});
			viewType.bChecked = true;
		}
		GWindowDialogClient gwindowdialogclient = (GWindowDialogClient) Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
		tabTarget = (Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("BornPlaceActor"), gwindowdialogclient));
		
		//Properties Tab
		//--------------------------------------------------------------
		gwindowdialogclient.addLabel(lProperties = new GWindowLabel(gwindowdialogclient, 3.0F, 0.5F, 6.0F, 1.6F, Plugin.i18n("mds.properties"), null));
		bSeparate_Properties = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 1.0F, 37.0F, 22.0F);
		bSeparate_Properties.exclude = lProperties;
		
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 2.0F, 6.0F, 1.3F, Plugin.i18n("mds.properties.radius"), null));
		gwindowdialogclient.addLabel(lRadius = new GWindowLabel(gwindowdialogclient, 17.0F, 2.0F, 6.0F, 1.3F, " [500m]", null));
		gwindowdialogclient.addControl(wR = new GWindowHSliderInt(gwindowdialogclient, 0, 250, 20, 7.0F, 2.0F, 10.0F)
		{
			public void afterCreated()
			{
				super.afterCreated();
				bSlidingNotify = true;
			}

			public boolean notify(int i_42_, int i_43_)
			{
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				if( actorborn != null )
					lRadius.cap = new com.maddox.gwindow.GCaption(" [" + actorborn.r + "m]" );
				
				ZutiSupportMethods_Builder.updateCountriesWindow(actorborn);
				
				if (i_42_ != 2)
					return false;
								
				//ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.r = pos() * 50 + 500;
				lRadius.cap = new com.maddox.gwindow.GCaption(" [" + actorborn.r + "m]" );
				
				PlMission.setChanged();
				return false;
			}
		});
		
		
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 4.0F, 6.0F, 1.3F, Plugin.i18n("mds.properties.army"), null));
		gwindowdialogclient.addControl(wArmy = new GWindowComboControl(gwindowdialogclient, 8.0F, 4.0F, 10.0F)
		{
			public void afterCreated()
			{
				super.afterCreated();
				setEditable(false);
				for (int i_47_ = 0; i_47_ < Army.amountNet(); i_47_++)
					add(I18N.army(Army.name(i_47_)));
			}

			public boolean notify(int i_48_, int i_49_)
			{
				if (i_48_ != 2)
					return false;
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.setArmy(getSelected());
				PlMission.setChanged();
				return false;
			}
		});
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 6.0F, 36.0F, 1.3F, Plugin.i18n("Parachute"), null));
		gwindowdialogclient.addControl(wParachute = new GWindowCheckBox(gwindowdialogclient, 36.0F, 6.0F, null)
		{
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.bParachute, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.bParachute = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		
		//TODO: Added by |ZUTI|
		//----------------------------------------------------------------------------------------
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 8.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.friction"), null));
		gwindowdialogclient.addControl(wFriction = new GWindowEditControl(gwindowdialogclient, 31.0F, 8.0F, 4.0F, 1.3F, "Home Base field friction: default is 3.8")
		{
			public void afterCreated()
			{
				super.afterCreated();
				bNumericOnly = true;
				bNumericFloat = true;
				bDelayedNotify = true;
			}
			
			public void preRender()
			{
				if( getValue().trim().length() > 0 )
					return;
			
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setValue(new Double(actorborn.zutiFriction).toString(), false);
				
				if( wEnableFriction != null )
				{
					if( actorborn.zutiFriction == 3.8D )
						wEnableFriction.setChecked(false, false);
					else
						wEnableFriction.setChecked(true, false);
				}
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2 || getValue().length() < 1)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiFriction = Double.parseDouble(getValue());
				PlMission.setChanged();
				return false;
			}
		});
		gwindowdialogclient.addControl(wEnableFriction = new GWindowCheckBox(gwindowdialogclient, 36.0F, 8.0F, null)
		{
			public void preRender()
			{
				super.preRender();
				
				if( wFriction != null )
				{
					wFriction.setEnable(this.isChecked());
				}
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				if( !this.isChecked() )
				{
					ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
					actorborn.zutiFriction = 3.8D;
					if( wFriction != null )
					{
						wFriction.setValue("3.8");
						wFriction.setEnable(false);
					}
				}
				else
				{
					wFriction.setValue("3.0");
					wFriction.setEnable(true);
				}
				
				PlMission.setChanged();
				return false;
			}
		});
		
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 10.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.spawn"), null));
		gwindowdialogclient.addControl(new GWindowCheckBox(gwindowdialogclient, 36.0F, 10.0F, null)
		{
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiDisableSpawning, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiDisableSpawning = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 12.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.staticPosition"), null));
		gwindowdialogclient.addControl(new GWindowCheckBox(gwindowdialogclient, 36.0F, 12.0F, null)
		{
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiStaticPositionOnly, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiStaticPositionOnly = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 14.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.disableRendering"), null));
		gwindowdialogclient.addControl(new GWindowCheckBox(gwindowdialogclient, 36.0F, 14.0F, null)
		{
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiDisableRendering, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiDisableRendering = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 16.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.showSpawnIndicators"), null));
		gwindowdialogclient.addControl(new GWindowCheckBox(gwindowdialogclient, 36.0F, 16.0F, null)
		{			
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				
				if( actorborn.zutiIsStandAloneBornPlace )
				{
					this.setEnable(false);
				}
				else
				{
					this.setEnable(true);
				}
				
				setChecked(actorborn.zutiToggleSpawnPlaceIndicatorsStatus, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiToggleSpawnPlaceIndicatorsStatus = isChecked();
				
				World world = World.cur();
				if( world == null || world.airdrome == null || world.airdrome.stay == null )
				{
					new GWindowMessageBox(Plugin.builder.clientWindow.root, 20.0F, true, Plugin.i18n("mds.section.error"), (Plugin.i18n("mds.section.noSpawnPlaces")), 4, 0.0F);
					return false;
				}
				
				if( actorborn.zutiToggleSpawnPlaceIndicatorsStatus )
				{
					ZutiSupportMethods_Builder.loadSpawnPlaceMarkers_BornPlace(world.airdrome.stay, actorborn.pos.getAbsPoint().x, actorborn.pos.getAbsPoint().y, actorborn.r);
				}
				else
				{
					ZutiSupportMethods_Builder.removeSpawnPlaceMarkers_BornPlace(actorborn.pos.getAbsPoint().x, actorborn.pos.getAbsPoint().y, actorborn.r);
				}
				
				return false;
			}
		});
		
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 18.0F, 36.0F, 1.3F, Plugin.i18n("mds.properties.showHomeBaseInfrastructure"), null));
		gwindowdialogclient.addControl(new GWindowCheckBox(gwindowdialogclient, 36.0F, 18.0F, null)
		{			
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				
				if( actorborn.zutiIsStandAloneBornPlace )
				{
					this.setEnable(false);
				}
				else
				{
					this.setEnable(true);
				}
				
				setChecked(actorborn.zutiToggleAirdromeInfrastructureStatus, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiToggleAirdromeInfrastructureStatus = isChecked();
				ZutiSupportMethods_Builder.showAirportPoints(actorborn.airdromeInfrastructurePoints, actorborn.zutiToggleAirdromeInfrastructureStatus, actorborn.pos.getAbsPoint().x, actorborn.pos.getAbsPoint().y, actorborn.r);	
				return false;
			}
		});
		
		gwindowdialogclient.addControl(new GWindowButton(gwindowdialogclient, 2.0F, 20.0F, 35.0F, 2.0F, Plugin.i18n("mds.properties.countries"), null)
		{
			public boolean notify(int i_83_, int i_84_)
			{
				if (i_83_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				ZutiSupportMethods_Builder.countriesWindowShow(actorborn, 0);
				
				return true;
			}
		});
		
		
		gwindowdialogclient.addLabel(lLimitations = new GWindowLabel(gwindowdialogclient, 3.0F, 24.0F, 9.0F, 1.6F, Plugin.i18n("mds.limitations"), null));
		bSeparate_Limitations = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 24.5F, 37.0F, 7.0F);
		bSeparate_Limitations.exclude = lLimitations;
		
		//wEnablePlaneLimits
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 25.5F, 36.0F, 1.3F, Plugin.i18n("mds.limitations.enable"), null));
		gwindowdialogclient.addControl(wEnablePlaneLimits = new GWindowCheckBox(gwindowdialogclient, 36.0F, 25.5F, null)
		{			
			public void preRender()
			{
				super.preRender();
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiEnablePlaneLimits, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				wDecreasingNumberOfPlanes.setEnable(isChecked());
				
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiEnablePlaneLimits = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		
		//wDecreasingNumberOfPlanes
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 27.5F, 36.0F, 1.3F, Plugin.i18n("mds.limitations.decAc"), null));
		gwindowdialogclient.addControl(wDecreasingNumberOfPlanes = new GWindowCheckBox(gwindowdialogclient, 36.0F, 27.5F, null)
		{			
			public void preRender()
			{
				super.preRender();
				setEnable(wEnablePlaneLimits.isChecked());
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiDecreasingNumberOfPlanes, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{
				wIncludeStaticPlanes.setEnable(isChecked());
				
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiDecreasingNumberOfPlanes = isChecked();
				PlMission.setChanged();
				return false;
			}
		});
		
		//wIncludeStaticPlanes
		gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 3.0F, 29.5F, 36.0F, 1.3F, Plugin.i18n("mds.limitations.countStatic"), null));
		gwindowdialogclient.addControl(wIncludeStaticPlanes = new GWindowCheckBox(gwindowdialogclient, 36.0F, 29.5F, null)
		{			
			public void preRender()
			{
				super.preRender();
				setEnable(wDecreasingNumberOfPlanes.isChecked());
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				setChecked(actorborn.zutiIncludeStaticPlanes, false);
			}

			public boolean notify(int i_52_, int i_53_)
			{			
				if (i_52_ != 2)
					return false;
				
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				actorborn.zutiIncludeStaticPlanes = isChecked();
				PlMission.setChanged();
				return false;
			}
		});	
		//--------------------------------------------------------------
		
		//Aircrafts Tab
		//--------------------------------------------------------------
		GWindowDialogClient gwindowdialogclient_54_ = (GWindowDialogClient) Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient()
		{
			public void resized()
			{
				super.resized();
				PlMisBorn.this.setAircraftSizes(this);
			}
		});
		tabAircraft = (Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("bplace_aircraft"), gwindowdialogclient_54_));
		lstAvailable = new Table(gwindowdialogclient_54_, Plugin.i18n("bplace_planes"), 1.0F, 1.0F, 6.0F, 10.0F);
		//TODO: Added by |ZUTI|
		lstAvailable.zutiShowAcWeaponsWindow = true;		
		lstInReserve = new Table(gwindowdialogclient_54_, Plugin.i18n("bplace_list"), 14.0F, 1.0F, 6.0F, 10.0F);
		gwindowdialogclient_54_.addControl(bAddAll = new GWindowButton(gwindowdialogclient_54_, 8.0F, 1.0F, 5.0F, 2.0F, (Plugin.i18n("bplace_addall")), null)
		{
			public boolean notify(int i_61_, int i_62_)
			{
				if (i_61_ != 2)
					return false;
				lstAvailable.lst.clear();
				PlMisBorn.this.addAllAircraft(lstAvailable.lst);
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bAdd = new GWindowButton(gwindowdialogclient_54_, 8.0F, 3.0F, 5.0F, 2.0F, (Plugin.i18n("bplace_add")), null)
		{
			public boolean notify(int i_68_, int i_69_)
			{
				if (i_68_ != 2)
					return false;
				int i_70_ = lstInReserve.selectRow;
				if (i_70_ < 0 || i_70_ >= lstInReserve.lst.size())
					return true;
				
				lstAvailable.lst.add(lstInReserve.lst.get(i_70_));
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bRemAll = new GWindowButton(gwindowdialogclient_54_, 8.0F, 6.0F, 5.0F, 2.0F, (Plugin.i18n("bplace_delall")), null)
		{
			public boolean notify(int i_76_, int i_77_)
			{
				if (i_76_ != 2)
					return false;
				lstAvailable.lst.clear();
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bRem = new GWindowButton(gwindowdialogclient_54_, 8.0F, 8.0F, 5.0F, 2.0F, (Plugin.i18n("bplace_del")), null)
		{
			public boolean notify(int i_83_, int i_84_)
			{
				if (i_83_ != 2)
					return false;
				int i_85_ = lstAvailable.selectRow;
				if (i_85_ < 0 || i_85_ >= lstAvailable.lst.size())
					return true;
				lstAvailable.lst.remove(i_85_);
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		
		//TODO: Added by |ZUTI|
		//--------------------------------------------------------------
		gwindowdialogclient_54_.addControl(bModifyPlane = new GWindowButton(gwindowdialogclient_54_, 8.0F, 12.0F, 5.0F, 2.0F, Plugin.i18n("mds.aircraft.modify"), null)
		{			
			public boolean notify(int i_83_, int i_84_)
			{
				ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
				bModifyPlane.setEnable(actorborn.zutiEnablePlaneLimits);
				
				if (i_83_ != 2)
					return false;
				
				if (lstAvailable.selectRow < 0 || lstAvailable.selectRow >= lstAvailable.lst.size())
					return true;
				else
				{
					ZutiAircraft zac = (ZutiAircraft)lstAvailable.lst.get(lstAvailable.selectRow);
					Zuti_WAircraftProperties zwap = new Zuti_WAircraftProperties();
					zwap.setAircraft( zac );
					zwap.setTitle( zac.getAcName() );
					zwap.showWindow();
				}
				return true;
			}
		});
		//--------------------------------------------------------------
		
		gwindowdialogclient_54_.addLabel(lSeparate = new GWindowLabel(gwindowdialogclient_54_, 3.0F, 12.0F, 12.0F, 1.6F, " " + Plugin.i18n("bplace_cats") + " ", null));
		bSeparate = new GWindowBoxSeparate(gwindowdialogclient_54_, 1.0F, 12.5F, 27.0F, 8.0F);
		bSeparate.exclude = lSeparate;
		gwindowdialogclient_54_.addLabel(lCountry = new GWindowLabel(gwindowdialogclient_54_, 2.0F, 14.0F, 7.0F, 1.6F, Plugin.i18n("bplace_country"), null));
		gwindowdialogclient_54_.addControl(cCountry = new GWindowComboControl(gwindowdialogclient_54_, 9.0F, 14.0F, 7.0F)
		{
			public void afterCreated()
			{
				super.afterCreated();
				setEditable(false);
				ResourceBundle resourcebundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
				TreeMap treemap = new TreeMap();
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_89_ = 0; i_89_ < arraylist.size(); i_89_++)
				{
					Class var_class = (Class) arraylist.get(i_89_);
					if (Property.containsValue(var_class, "cockpitClass"))
					{
						String string = Property.stringValue(var_class, "originCountry", null);
						if (string != null)
						{
							String string_90_;
							try
							{
								string_90_ = resourcebundle.getString(string);
							}
							catch (Exception exception)
							{
								continue;
							}
							treemap.put(string_90_, string);
						}
					}
				}
				Iterator iterator = treemap.keySet().iterator();
				while (iterator.hasNext())
				{
					String string = (String) iterator.next();
					String string_91_ = (String) treemap.get(string);
					PlMisBorn.lstCountry.add(string_91_);
					add(string);
				}
				if (PlMisBorn.lstCountry.size() > 0) setSelected(0, true, false);
			}
		});
		gwindowdialogclient_54_.addControl(bCountryAdd = new GWindowButton(gwindowdialogclient_54_, 17.0F, 14.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_add")), null)
		{
			public boolean notify(int i_97_, int i_98_)
			{
				if (i_97_ != 2)
					return false;
				String string = ((String) PlMisBorn.lstCountry.get(cCountry.getSelected()));
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_99_ = 0; i_99_ < arraylist.size(); i_99_++)
				{
					Class var_class = (Class) arraylist.get(i_99_);
					if (Property.containsValue(var_class, "cockpitClass") && string.equals(Property.stringValue(var_class, "originCountry", null)))
					{
						//TODO: Modified by |ZUTI|: just replaced original block
						//-----------------------------------------------------------------
						String zutiAcName = Property.stringValue(var_class, "keyName");
						
						ZutiAircraft zac = new ZutiAircraft();
						zac.setAcName( zutiAcName );
						
						if (!lstAvailable.lst.contains(zac))
							lstAvailable.lst.add(zac);
						//-----------------------------------------------------------------
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bCountryRem = new GWindowButton(gwindowdialogclient_54_, 22.0F, 14.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_del")), null)
		{
			public boolean notify(int i_106_, int i_107_)
			{
				if (i_106_ != 2)
					return false;
				String string = ((String) PlMisBorn.lstCountry.get(cCountry.getSelected()));
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_108_ = 0; i_108_ < arraylist.size(); i_108_++)
				{
					Class var_class = (Class) arraylist.get(i_108_);
					if (Property.containsValue(var_class, "cockpitClass") && string.equals(Property.stringValue(var_class, "originCountry", null)))
					{
						String string_109_ = Property.stringValue(var_class, "keyName");
						int i_110_ = lstAvailable.lst.indexOf(string_109_);
						if (i_110_ >= 0) lstAvailable.lst.remove(i_110_);
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addLabel(lYear = new GWindowLabel(gwindowdialogclient_54_, 2.0F, 16.0F, 7.0F, 1.6F, Plugin.i18n("bplace_year"), null));
		gwindowdialogclient_54_.addControl(cYear = new GWindowComboControl(gwindowdialogclient_54_, 9.0F, 16.0F, 7.0F)
		{
			public void afterCreated()
			{
				super.afterCreated();
				setEditable(false);
				TreeMap treemap = new TreeMap();
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_114_ = 0; i_114_ < arraylist.size(); i_114_++)
				{
					Class var_class = (Class) arraylist.get(i_114_);
					if (Property.containsValue(var_class, "cockpitClass"))
					{
						float f = Property.floatValue(var_class, "yearService", 0.0F);
						if (f != 0.0F) treemap.put("" + (int) f, null);
					}
				}
				Iterator iterator = treemap.keySet().iterator();
				while (iterator.hasNext())
				{
					String string = (String) iterator.next();
					PlMisBorn.lstYear.add(string);
					add(string);
				}
				if (PlMisBorn.lstYear.size() > 0)
					setSelected(0, true, false);
			}
		});
		gwindowdialogclient_54_.addControl(bYearAdd = new GWindowButton(gwindowdialogclient_54_, 17.0F, 16.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_add")), null)
		{
			public boolean notify(int i_120_, int i_121_)
			{
				if (i_120_ != 2)
					return false;
				String string = (String) PlMisBorn.lstYear.get(cYear.getSelected());
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_122_ = 0; i_122_ < arraylist.size(); i_122_++)
				{
					Class var_class = (Class) arraylist.get(i_122_);
					if (Property.containsValue(var_class, "cockpitClass") && string.equals("" + (int) (Property.floatValue(var_class, "yearService", 0.0F))))
					{
						//TODO: Modified by |ZUTI|: just replaced original block
						//-----------------------------------------------------------------
						String zutiAcName = Property.stringValue(var_class, "keyName");
						
						ZutiAircraft zac = new ZutiAircraft();
						zac.setAcName( zutiAcName );
						
						if (!lstAvailable.lst.contains(zac))
							lstAvailable.lst.add(zac);
						//-----------------------------------------------------------------
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bYearRem = new GWindowButton(gwindowdialogclient_54_, 22.0F, 16.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_del")), null)
		{
			public boolean notify(int i_129_, int i_130_)
			{
				if (i_129_ != 2)
					return false;
				String string = (String) PlMisBorn.lstYear.get(cYear.getSelected());
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_131_ = 0; i_131_ < arraylist.size(); i_131_++)
				{
					Class var_class = (Class) arraylist.get(i_131_);
					if (Property.containsValue(var_class, "cockpitClass") && string.equals("" + (int) (Property.floatValue(var_class, "yearService", 0.0F))))
					{
						String string_132_ = Property.stringValue(var_class, "keyName");
						int i_133_ = lstAvailable.lst.indexOf(string_132_);
						if (i_133_ >= 0) lstAvailable.lst.remove(i_133_);
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addLabel(lType = new GWindowLabel(gwindowdialogclient_54_, 2.0F, 18.0F, 7.0F, 1.6F, Plugin.i18n("bplace_category"), null));
		gwindowdialogclient_54_.addControl(cType = new GWindowComboControl(gwindowdialogclient_54_, 9.0F, 18.0F, 7.0F)
		{
			public void afterCreated()
			{
				super.afterCreated();
				setEditable(false);
				TreeMap treemap = new TreeMap();
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_137_ = 0; i_137_ < arraylist.size(); i_137_++)
				{
					Class var_class = (Class) arraylist.get(i_137_);
					if (Property.containsValue(var_class, "cockpitClass"))
					{
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeStormovik"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_sturm"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeStormovik")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeFighter"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_fiter"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeFighter")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeBomber"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_bomber"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeBomber")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeScout) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeScout = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeScout"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeScout)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_recon"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeScout) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeScout = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeScout")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeScout)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeDiveBomber"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_diver"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeDiveBomber")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeSailPlane"))) : (PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_sailer"), (((PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.TypeSailPlane")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane)));
						if ((((PlMisBorn.class$com$maddox$il2$objects$air$Scheme1) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.Scheme1"))) : (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1)).isAssignableFrom(var_class))
							treemap.put(Plugin.i18n("bplace_single"), (((PlMisBorn.class$com$maddox$il2$objects$air$Scheme1) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = (PlMisBorn.class$ZutiPlMisBorn("com.maddox.il2.objects.air.Scheme1")))
									: (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1)));
						else
							treemap.put(Plugin.i18n("bplace_multi"), null);
					}
				}
				Iterator iterator = treemap.keySet().iterator();
				while (iterator.hasNext())
				{
					String string = (String) iterator.next();
					Class var_class = (Class) treemap.get(string);
					PlMisBorn.lstType.add(var_class);
					add(string);
				}
				if (PlMisBorn.lstType.size() > 0) setSelected(0, true, false);
			}
		});
		gwindowdialogclient_54_.addControl(bTypeAdd = new GWindowButton(gwindowdialogclient_54_, 17.0F, 18.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_add")), null)
		{
			public boolean notify(int i_143_, int i_144_)
			{
				if (i_143_ != 2)
					return false;
				Class var_class = (Class) PlMisBorn.lstType.get(cType.getSelected());
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_145_ = 0; i_145_ < arraylist.size(); i_145_++)
				{
					Class var_class_146_ = (Class) arraylist.get(i_145_);
					if (Property.containsValue(var_class_146_, "cockpitClass"))
					{
						if (var_class == null)
						{
							if ((((PlMisBorn.class$com$maddox$il2$objects$air$Scheme1) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = (class$ZutiPlMisBorn("com.maddox.il2.objects.air.Scheme1"))) : (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1)).isAssignableFrom(var_class_146_))
								continue;
						}
						else if (!var_class.isAssignableFrom(var_class_146_))
							continue;

						//TODO: Modified by |ZUTI|: just replaced original block
						//-----------------------------------------------------------------
						String zutiAcName = Property.stringValue(var_class, "keyName");
						
						ZutiAircraft zac = new ZutiAircraft();
						zac.setAcName( zutiAcName );
						
						if (!lstAvailable.lst.contains(zac))
							lstAvailable.lst.add(zac);
						//-----------------------------------------------------------------
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
		gwindowdialogclient_54_.addControl(bTypeRem = new GWindowButton(gwindowdialogclient_54_, 22.0F, 18.0F, 5.0F, 1.6F, (Plugin.i18n("bplace_del")), null)
		{
			public boolean notify(int i_152_, int i_153_)
			{
				if (i_152_ != 2)
					return false;
				Class var_class = (Class) PlMisBorn.lstType.get(cType.getSelected());
				ArrayList arraylist = Main.cur().airClasses;
				for (int i_154_ = 0; i_154_ < arraylist.size(); i_154_++)
				{
					Class var_class_155_ = (Class) arraylist.get(i_154_);
					if (Property.containsValue(var_class_155_, "cockpitClass"))
					{
						if (var_class == null)
						{
							if ((((PlMisBorn.class$com$maddox$il2$objects$air$Scheme1) == null) ? (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = (class$ZutiPlMisBorn("com.maddox.il2.objects.air.Scheme1"))) : (PlMisBorn.class$com$maddox$il2$objects$air$Scheme1)).isAssignableFrom(var_class_155_))
								continue;
						}
						else if (!var_class.isAssignableFrom(var_class_155_))
							continue;
						String string = Property.stringValue(var_class_155_, "keyName");
						int i_156_ = lstAvailable.lst.indexOf(string);
						if (i_156_ >= 0)
							lstAvailable.lst.remove(i_156_);
					}
				}
				PlMisBorn.this.fillTabAircraft();
				return true;
			}
		});
	}

	//TODO: Rewritten by |ZUTI|: complete method
	private void fillTabAircraft()
	{
		ActorBorn actorborn = (ActorBorn) Plugin.builder.selectedActor();
		if( actorborn == null )
			return;
		
		int selectedRow = lstInReserve.selectRow;
		lstAvailable.lst = actorborn.zutiAircraft;
		lstInReserve.lst.clear();
		
		//Fill inReserve list with all AC that exist
		addAllAircraft( lstInReserve.lst );
		
		//If airNames is not null, remove AC that are in it from inReserve list
		if( lstAvailable.lst != null && lstAvailable.lst.size() > 0 )
		{
			for( int x=0; x<actorborn.zutiAircraft.size(); x++ )
			{
				lstInReserve.lst.remove( (ZutiAircraft)actorborn.zutiAircraft.get(x) );
			}
		}
		
		lstInReserve.setSelect(selectedRow, 0);
		lstAvailable.resized();
		lstInReserve.resized();
		
		//TODO: Added by |ZUTI|: Sorting...
		//----------------------------------------
		if( lstAvailable.lst != null )
			Collections.sort(lstAvailable.lst, new ZutiSupportMethods_Builder.ZutiAircraft_CompareByName());
		if( lstInReserve.lst != null )
			Collections.sort(lstInReserve.lst, new ZutiSupportMethods_Builder.ZutiAircraft_CompareByName());
		//----------------------------------------
	}

	private void addAllAircraft(ArrayList arraylist)
	{
		ArrayList arraylist_160_ = Main.cur().airClasses;
		for (int i = 0; i < arraylist_160_.size(); i++)
		{
			Class var_class = (Class) arraylist_160_.get(i);
			if (Property.containsValue(var_class, "cockpitClass"))
			{
				//TODO: |ZUTI| replaced whole code block
				//----------------------------------------
				String string = Property.stringValue(var_class, "keyName");
				int index = string.indexOf("*");
				if( index < 0 || index > 1 )
				{
					ZutiAircraft zac = new ZutiAircraft();
					zac.setAcName(string);
					
					if( !arraylist.contains(zac) )
						arraylist.add(zac);
				}
				//----------------------------------------
			}
		}
		
		//TODO: Added by |ZUTI|
		//------------------------------------------------------------
		if( ZUTI_ALL_AC_LIST_SIZE < 0 )
			ZUTI_ALL_AC_LIST_SIZE = arraylist.size();
		
		//System.out.println("AC list size: " + arraylist.size());
		//System.out.println("=================================");
		//------------------------------------------------------------
	}

	private void setAircraftSizes(GWindow gwindow)
	{
		float f = gwindow.win.dx;
		float f_162_ = gwindow.win.dy;
		GFont gfont = gwindow.root.textFonts[0];
		float f_163_ = gwindow.lAF().metric();
		GSize gsize = new GSize();
		gfont.size(Plugin.i18n("bplace_addall"), gsize);
		float f_164_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_add"), gsize);
		float f_165_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_delall"), gsize);
		float f_166_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_del"), gsize);
		float f_167_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_planes"), gsize);
		float f_168_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_list"), gsize);
		float f_169_ = gsize.dx;
		
		//TODO: Added by |ZUTI|
		//------------------------------------------------------------
		gfont.size(Plugin.i18n("mds.aircraft.modify"), gsize);
		//------------------------------------------------------------

		float zSize1 = gsize.dx;
		float f_170_ = f_164_;
		if (f_170_ < f_165_)
			f_170_ = f_165_;
		if (f_170_ < f_166_)
			f_170_ = f_166_;
		if (f_170_ < f_167_)
			f_170_ = f_167_;
			
		//TODO: Added by |ZUTI|
		//------------------------------------------------------------
		if (f_170_ < zSize1)
			f_170_ = zSize1;
		//------------------------------------------------------------
		
		float f_171_ = f_163_ + f_170_;
		f_170_ += (f_163_ + 4.0F * f_163_ + f_168_ + 4.0F * f_163_ + f_169_ + 4.0F * f_163_);
		if (f < f_170_)
			f = f_170_;
		float f_172_ = 10.0F * f_163_ + 10.0F * f_163_ + 2.0F * f_163_;
		if (f_162_ < f_172_)
			f_162_ = f_172_;
		float f_173_ = (f - f_171_) / 2.0F;
		bAddAll.setPosSize(f_173_, f_163_, f_171_, 2.0F * f_163_);
		bAdd.setPosSize(f_173_, f_163_ + 2.0F * f_163_, f_171_, 2.0F * f_163_);
		bRemAll.setPosSize(f_173_, 2.0F * f_163_ + 4.0F * f_163_, f_171_, 2.0F * f_163_);
		bRem.setPosSize(f_173_, 2.0F * f_163_ + 6.0F * f_163_, f_171_, 2.0F * f_163_);
		bModifyPlane.setPosSize(f_173_, 2.0F * f_163_ + 10.0F * f_163_, f_171_, 2.0F * f_163_);
		float f_174_ = (f - f_171_ - 4.0F * f_163_) / 2.0F;
		float f_175_ = f_162_ - 6.0F * f_163_ - 2.0F * f_163_ - 3.0F * f_163_;
		lstAvailable.setPosSize(f_163_, f_163_, f_174_, f_175_);
		lstInReserve.setPosSize(f - f_163_ - f_174_, f_163_, f_174_, f_175_);
		gfont.size(" " + Plugin.i18n("bplace_cats") + " ", gsize);
		f_174_ = gsize.dx;
		float f_176_ = f_163_ + f_175_;
		lSeparate.setPosSize(2.0F * f_163_, f_176_, f_174_, 2.0F * f_163_);
		bSeparate.setPosSize(f_163_, f_176_ + f_163_, f - 2.0F * f_163_, f_162_ - f_176_ - 2.0F * f_163_);
		gfont.size(Plugin.i18n("bplace_country"), gsize);
		float f_177_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_year"), gsize);
		if (f_177_ < gsize.dx)
			f_177_ = gsize.dx;
		gfont.size(Plugin.i18n("bplace_category"), gsize);
		if (f_177_ < gsize.dx)
			f_177_ = gsize.dx;
		f_171_ = 2.0F * f_163_ + f_165_ + f_167_;
		f_174_ = f - f_177_ - f_171_ - 6.0F * f_163_;
		float f_178_ = gwindow.lAF().getComboH();
		lCountry.setPosSize(2.0F * f_163_, f_176_ + 2.0F * f_163_, f_177_, 2.0F * f_163_);
		cCountry.setPosSize(2.0F * f_163_ + f_177_ + f_163_, (f_176_ + 2.0F * f_163_ + (2.0F * f_163_ - f_178_) / 2.0F), f_174_, f_178_);
		bCountryAdd.setPosSize(f - 4.0F * f_163_ - f_167_ - f_165_, f_176_ + 2.0F * f_163_, f_163_ + f_165_, 2.0F * f_163_);
		bCountryRem.setPosSize(f - 3.0F * f_163_ - f_167_, f_176_ + 2.0F * f_163_, f_163_ + f_167_, 2.0F * f_163_);
		lYear.setPosSize(2.0F * f_163_, f_176_ + 4.0F * f_163_, f_177_, 2.0F * f_163_);
		cYear.setPosSize(2.0F * f_163_ + f_177_ + f_163_, (f_176_ + 4.0F * f_163_ + (2.0F * f_163_ - f_178_) / 2.0F), f_174_, f_178_);
		bYearAdd.setPosSize(f - 4.0F * f_163_ - f_167_ - f_165_, f_176_ + 4.0F * f_163_, f_163_ + f_165_, 2.0F * f_163_);
		bYearRem.setPosSize(f - 3.0F * f_163_ - f_167_, f_176_ + 4.0F * f_163_, f_163_ + f_167_, 2.0F * f_163_);
		lType.setPosSize(2.0F * f_163_, f_176_ + 6.0F * f_163_, f_177_, 2.0F * f_163_);
		cType.setPosSize(2.0F * f_163_ + f_177_ + f_163_, (f_176_ + 6.0F * f_163_ + (2.0F * f_163_ - f_178_) / 2.0F), f_174_, f_178_);
		bTypeAdd.setPosSize(f - 4.0F * f_163_ - f_167_ - f_165_, f_176_ + 6.0F * f_163_, f_163_ + f_165_, 2.0F * f_163_);
		bTypeRem.setPosSize(f - 3.0F * f_163_ - f_167_, f_176_ + 6.0F * f_163_, f_163_ + f_167_, 2.0F * f_163_);
	}

	static Class class$ZutiPlMisBorn(String string)
	{
		Class var_class;
		try
		{
			var_class = Class.forName(string);
		}
		catch (ClassNotFoundException classnotfoundexception)
		{
			throw new NoClassDefFoundError(classnotfoundexception.getMessage());
		}
		return var_class;
	}

	static
	{
		Property.set((class$com$maddox$il2$builder$PlMisBorn == null ? (class$com$maddox$il2$builder$PlMisBorn = class$ZutiPlMisBorn("com.maddox.il2.builder.PlMisBorn")) : class$com$maddox$il2$builder$PlMisBorn), "name", "MisBorn");
	}

	//TODO: |ZUTI| methods and variables
	//-----------------------------------------------------------------------
	//For identifying for which side are we modifying captured ac list... 0=default, 1=red, 2=blue
	protected static int ZUTI_ALL_AC_LIST_SIZE = -1;
	
	//TODO: Added by |ZUTI| - added to original tabs
	protected GWindowBoxSeparate bSeparate_Properties;
	protected GWindowLabel lRadius;
	protected GWindowButton bModifyPlane;
	protected GWindowEditControl wFriction;
	protected GWindowCheckBox wEnablePlaneLimits;
	protected GWindowCheckBox wDecreasingNumberOfPlanes;
	protected GWindowCheckBox wIncludeStaticPlanes;
	protected GWindowLabel lProperties;
	protected GWindowLabel lLimitations;
	protected GWindowBoxSeparate bSeparate_Limitations;
	protected GWindowCheckBox wEnableFriction; 
	//-----------------------------------------------------------------------
}