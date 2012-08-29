package com.maddox.il2.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.ZutiSupportMethods_Air;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;

public class ZutiSupportMethods_Net
{	
	/**
	 * Reset class variables
	 */
	public static void resetClassVariables()
	{
	}
	
	/**
	 * Method returns true if born place is not yet full or is disabled.
	 * @param bp
	 * @return
	 */
	public static boolean canUserJoinBornPlace(BornPlace bp)
	{
		if( bp == null )
			return false;
		
		if( bp.zutiMaxBasePilots == 0 || (bp.zutiMaxBasePilots > 0 && bp.tmpForBrief < bp.zutiMaxBasePilots) )
		{
			return true;
		}

		return false;
	}
	
	/**
	 * Method fills aircraft list for specified born place.
	 */
	public static void fillBornPlaceAirNames(BornPlace bp)
	{
		if( bp == null )
			return;
		
		if( bp.zutiAircraft == null || bp.zutiAircraft.size() < 1 )
		{
			//No AC lines for this home base = all planes should be loaded!
			ZutiSupportMethods_Net.createCompleteAircraftListForBornPlace(bp);
			return;
		}
		else
		{
			//airNames = original aircraft list that is used in arming screen
			if( bp.airNames == null )
				bp.airNames = new ArrayList();
			bp.airNames.clear();
			
			int size = bp.zutiAircraft.size();
			for( int i=0; i<size; i++ )
			{
				ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
				bp.airNames.add(zac.getAcName());
			}
		}
	}
	
	/**
	 * Returned list contains loadouts IDs for specified aircraft that are supported
	 * by specified born place. Loadouts names are localized strings.
	 * supports for specified aircraft.
	 * @param bp
	 * @param acName
	 * @return
	 */
	public static List getLoadoutsForAircraftAtBornPlace(BornPlace bp, String acName)
	{
		if( bp == null || acName == null )
			return new ArrayList();
		
		if( bp.zutiAircraft == null || bp.zutiAircraft.size() < 1 )
			return new ArrayList();
		
		int size = bp.zutiAircraft.size();
		for( int i=0; i<size; i++ )
		{
			ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			if( zac.getAcName().equals(acName) )
				return zac.getSelectedWeaponI18NNames();
		}
		
		return new ArrayList();
	}
	
	/**
	 * Method checks if specified aircraft is still available at given born place.
	 * @param bp
	 * @param acName
	 * @return
	 */
	public static ZutiAircraft getAircraftAtBornPlace(BornPlace bp, String acName)
	{
		try
		{
			if( bp == null || bp.zutiAircraft == null )
				return null;
			
			if( !bp.zutiEnablePlaneLimits )
				return new ZutiAircraft();
			
			System.out.println("ZSM_Net - checking availability for aircraft >" + acName + "<.");
			int size = bp.zutiAircraft.size();
			for( int i=0; i<size; i++ )
			{
				ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
				if( zac.getAcName().equals(acName) )
				{
					//If aircraft is available, return it and decrease it's number at specified home base.
					if( zac.isAvailable( bp ) )
					{
						zac.decreaseNumberOfAircraft(bp);
						System.out.println("  Available!");
						return zac;
					}
				}
			}
		}
		catch(Exception ex){ex.printStackTrace();}
		
		return null;
	}
	
	/**
	 * Method returns list containing available aircraft at specified born place.
	 * @param bp
	 * @return
	 */
	public static ArrayList getBornPlaceAvailableAircraftList(BornPlace bp)
	{
		ArrayList acNames = new ArrayList();
		
		if( bp == null || bp.zutiAircraft == null )
			return acNames;
		
		for( int i=0; i<bp.zutiAircraft.size(); i++ )
		{
			ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			if( zac.isAvailable(bp) )
				acNames.add( zac.getAcName() );
		}
		
		return acNames;
	}
	
	/**
	 * This method will return a list of ZutiAircraft objects with lines shorter than 255 characters. Each line will
	 * contains these aircraft properties: acName, currentNumberOfAc, maxFuel, loadouts. These entries are separated by
	 * space. When AC entry is done, an ; is inserted to separate AC between themselves.
	 * @param bp
	 * @return
	 */
	public static List getAircraftList(BornPlace bp)
	{
		List aircraft = new ArrayList();
		
		if( bp == null )
			return aircraft;
		
		ArrayList zacs = bp.zutiAircraft;
		StringBuffer sb = new StringBuffer();
		ZutiAircraft zac = null;
		String statusLine = null;
		if( zacs != null )
		{
			for( int i=0; i<zacs.size(); i++ )
			{
				zac = (ZutiAircraft)zacs.get(i);
				statusLine = zac.getAcStatusLine();
				if( (sb.toString().length() + statusLine.length()) < 210 )
				{
					sb.append( zac.getAcStatusLine() );
					sb.append(";");
				}
				else
				{
					aircraft.add( sb.toString().trim() );
					
					sb = new StringBuffer();
					sb.append( zac.getAcStatusLine() );
					sb.append(";");
				}
			}
			
			aircraft.add( sb.toString().trim() );
		}
		
		return aircraft;
	}
	

	/**
	 * Method searches for home base at specified coordinates and removes planes listed in acNames
	 * list from its aircraft list.
	 * @param acNames Aircraft list.
	 * @param x Home base X coordinate.
	 * @param y Home base Y coordinate.
	 */
	public static void setAircraftListForHomeBase(ArrayList aircraftList, double x, double y)
	{
		//This should be done only for clients
		if( World.cur() == null || Main.cur().netServerParams == null || Main.cur().netServerParams.isMaster() )
			return;
		
		List bornPlaces = World.cur().bornPlaces;
		
		if( bornPlaces == null )
			return;

		BornPlace bp = null;
		for( int i=0; i<bornPlaces.size(); i++ )
		{			
			bp = (BornPlace)bornPlaces.get(i);
			if( bp.place.x == x && bp.place.y == y )
				ZutiSupportMethods_Net.setBornPlaceAircraftList(bp, aircraftList);
		}
	}
	
	/**
	 * Method refills the airNames with full AC list and omit those planes that come with aircraftToIgnore parameter.
	 * @param bp
	 * @param acName
	 */
	public static void addAircraftToBornPlace(BornPlace bp, String acName)
	{
		if( bp == null )
		{
			System.out.println("BornPlace = null!");
			return;
		}
		
		if( bp.zutiAircraft == null )
			bp.zutiAircraft = new ArrayList();
		
		ZutiAircraft zac = null;
		String zacName = null;
		boolean updated = false;
		
		for( int i=0; i<bp.zutiAircraft.size(); i++ )
		{
			zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			zacName = zac.getAcName();
			if( zacName.equals(acName) )
			{
				//we found plane, adjust counters				
				zac.increaseNumberOfAircraft(bp);
				updated = true;
				
				System.out.println("ZSM_Net - Updated counter for aircraft >" + zacName + "<. New counter = " + zac.getNumberOfAIrcraft());
				break;
			}
		}
		
		if( !updated )
		{
			//Aircraft does not exist at specified born place. Create it.
			zac = ZutiSupportMethods_Air.createDefaultZutiAircraft(acName);
			//change default settings
			//First, change number of it from default to 1
			zac.setNumberOfAircraft(1);
			//Second, add loadouts, first nine
			zac.setDefaultLoadouts();
			//Third, add it to bp list
			bp.zutiAircraft.add(zac);
		}
		
		syncBornPlaceAirNamesList(bp);
	}
	
	/**
	 * Method is used to deduct one aircraft from specified home base.
	 * Only affective if home base has zutiDecreasingNumberOfPlanes set to true.
	 * @param bp
	 * @param acName
	 */
	public static void removeAircraftAtBornPlace(BornPlace bp, String acName)
	{
		if( bp == null || bp.zutiAircraft == null || !bp.zutiDecreasingNumberOfPlanes )
			return;
	
		ZutiAircraft zac = null;
		String zacName = null;
		
		for( int i=0; i<bp.zutiAircraft.size(); i++ )
		{
			zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			zacName = zac.getAcName();
			if( zacName.equals(acName) )
			{
				zac.decreaseNumberOfAircraft(bp);
				
				System.out.println("ZSM_Net - Updated counter for aircraft >" + zacName + "<. New counter = " + zac.getNumberOfAIrcraft());
				break;
			}
		}
		
		syncBornPlaceAirNamesList(bp);
	}
	
	/**
	 * This method sets airNames list for specified home base with those aircraft
	 * that are available.
	 * @param bp
	 */
	public static void syncBornPlaceAirNamesList(BornPlace bp)
	{
		if( bp == null )
			return;
		
		bp.airNames.clear();
		System.out.println("BornPlace >" + (int)bp.place.x + ", " + (int)bp.place.y + "< AC list");
		for( int i=0; i<bp.zutiAircraft.size(); i++ )
		{
			ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			if( zac.isAvailable(bp) )
			{
				bp.airNames.add( zac.getAcName() );
				
				System.out.println("  " + zac.getAcName());
			}
		}
		System.out.println("==============================");
	}
	
	/**
	 * Method de-activates list of AC for given born place.
	 * @param bp
	 * @param zuti aircraft list
	 */
	public static void setBornPlaceAircraftList(BornPlace bp, List zutiAircraft)
	{
		if( bp == null || bp.zutiAircraft == null || zutiAircraft == null )
			return;
		
		ZutiAircraft zac = null;
		
		System.out.println("Setting AC list for home base >" + (int)bp.place.x + "," + (int)bp.place.y + "<:");
		for( int i=0; i<zutiAircraft.size(); i++ )
		{
			zac = (ZutiAircraft)zutiAircraft.get(i);
			if( bp.zutiAircraft.contains(zac) )
				bp.zutiAircraft.remove(zac);
			
			bp.zutiAircraft.add(zac);
			System.out.println("  Updated status for aircraft >" + zac.getAcName() + "<");
		}
		System.out.println("  Total AC: " + bp.zutiAircraft.size());
		System.out.println("==========================");
	}
	
	/**
	 * Fills born place airNames variable with all aircraft found in IL2 sources!
	 * @param bp
	 */
	public static void addAllAircraftToBornPlace(BornPlace bp)
	{
		if( bp == null )
			return;
		
		if( bp.airNames == null )
			bp.airNames = new ArrayList();
		
		bp.airNames.clear();
		
		ArrayList fullList = Main.cur().airClasses;
		Class aircraftClass = null;
		String acName = null;
		
		for (int i = 0; i < fullList.size(); i++)
		{
			aircraftClass = (Class) fullList.get(i);
			if (Property.containsValue(aircraftClass, "cockpitClass"))
			{
				acName = Property.stringValue(aircraftClass, "keyName");
				if (!bp.airNames.contains(acName))
					bp.airNames.add(acName);
			}
		}
	}
	
	/**
	 * Method creates ZutiAircraftObjects based on complete aircraft list in specified born place.
	 * Those objects are then inserted into zutiAircraft list.
	 * @param bp
	 */
	public static void createCompleteAircraftListForBornPlace(BornPlace bp)
	{
		if( bp == null )
			return;
		
		bp.zutiAircraft = new ArrayList();
		addAllAircraftToBornPlace(bp);
		String acName = null;
		ZutiAircraft zac = null;
		ArrayList loadouts = null;
		
		for( int i=0; i<bp.airNames.size(); i++ )
		{
			acName = (String)bp.airNames.get(i);
			zac = new ZutiAircraft();
			zac.setAcName(acName);
			
			if( bp.zutiEnablePlaneLimits )
			{
				loadouts = new ArrayList();
				loadouts.add("Default");
				zac.setSelectedWeapons(loadouts);
			}
			bp.zutiAircraft.add(zac);
		}
	}
	
	/**
	 * Method loads countries for events of base capturing.
	 * @param bp
	 */
	public static void loadBornPlaceCapturedCountries(BornPlace bp)
	{
		if( bp == null )
			return;
		
		if( bp.zutiHomeBaseCountries != null )
		{
			bp.zutiHomeBaseCountries.clear();
			
			if( bp.army == 1 && bp.zutiHomeBaseCapturedRedCountries != null )
			{
				for( int i=0; i<bp.zutiHomeBaseCapturedRedCountries.size(); i++ )
				{
					bp.zutiHomeBaseCountries.add(bp.zutiHomeBaseCapturedRedCountries.get(i));
				}
			}
			else if( bp.army == 2 && bp.zutiHomeBaseCapturedBlueCountries != null )
			{
				for( int i=0; i<bp.zutiHomeBaseCapturedBlueCountries.size(); i++ )
				{
					bp.zutiHomeBaseCountries.add(bp.zutiHomeBaseCapturedBlueCountries.get(i));
				}
			}
			else
			{
				bp.zutiHomeBaseCountries.clear();
				bp.zutiHomeBaseCountries.add("None");
			}
		}
		else
		{
			bp.zutiHomeBaseCountries = new ArrayList();
			
			if( bp.army == 1 )
			{
				bp.zutiHomeBaseCountries.clear();
				bp.zutiHomeBaseCountries.add("None");
				bp.zutiHomeBaseCountries.add("USSR");
				bp.zutiHomeBaseCountries.add("RAF");
				bp.zutiHomeBaseCountries.add("USAAF");
			}
			else if( bp.army == 2 )
			{
				bp.zutiHomeBaseCountries.clear();
				bp.zutiHomeBaseCountries.add("None");
				bp.zutiHomeBaseCountries.add("Germany");
				bp.zutiHomeBaseCountries.add("Italy");
				bp.zutiHomeBaseCountries.add("IJA");
			}
			else
			{
				bp.zutiHomeBaseCountries.clear();
				bp.zutiHomeBaseCountries.add("None");
			}
		}
	}
	
	/**
	 * Method loads some default countries for given born place base on born place army.
	 * This eliminates that born places have countries in their countries list that do not
	 * belong to that born place army (red base having Germany as a valid country...)
	 * @param bp
	 */
	public static void loadBornPlaceDefaultCountries(BornPlace bp)
	{
		if( bp == null )
			return;
		
		if( bp.zutiHomeBaseCountries == null )
			bp.zutiHomeBaseCountries = new ArrayList();
		
		bp.zutiHomeBaseCountries.clear();
		
		if( bp.army == 1 )
		{
			bp.zutiHomeBaseCountries.clear();
			bp.zutiHomeBaseCountries.add("None");
			bp.zutiHomeBaseCountries.add("USSR");
			bp.zutiHomeBaseCountries.add("RAF");
			bp.zutiHomeBaseCountries.add("USAAF");
		}
		else if( bp.army == 2 )
		{
			bp.zutiHomeBaseCountries.clear();
			bp.zutiHomeBaseCountries.add("None");
			bp.zutiHomeBaseCountries.add("Germany");
			bp.zutiHomeBaseCountries.add("Italy");
			bp.zutiHomeBaseCountries.add("IJA");
		}
		else
		{
			bp.zutiHomeBaseCountries.clear();
			bp.zutiHomeBaseCountries.add("None");
		}
	}
	
	/**
	 * Method loads ALL IL2 DEFINED countries to specified born place.
	 * @param bp
	 */
	public static void loadAllCountriesForBornPlace(BornPlace bp)
	{
		if( bp == null )
			return;
		
		if( bp.zutiHomeBaseCountries == null )
			bp.zutiHomeBaseCountries = new ArrayList();
		
		bp.zutiHomeBaseCountries.clear();
		
		ResourceBundle resCountry = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
		Regiment regiment = null;
		String branch = null;		
		List list = Regiment.getAll();
        int k1 = list.size();
        for(int i = 0; i < k1; i++)
        {
            regiment = (Regiment)list.get(i);
            try
            {
				branch = resCountry.getString(regiment.branch());
				if( !bp.zutiHomeBaseCountries.contains(branch) )
					bp.zutiHomeBaseCountries.add(branch);
            }
            catch(Exception ex){}
		}
	}
		
	/**
	 * Method searches for free spawn point for given airport.
	 * @param index: position in World.cur().airdrome.stay array to search for free point from
	 * @return
	 */
	public static Point_Stay getFreeSpawnPointForStandAloneBornPlace(int index)
	{
		Point_Stay[] point_stays = World.cur().airdrome.stay[index];
		Point_Stay result = null;
		if( point_stays != null )
		{
			for( int i=0; i< point_stays.length; i++ )
			{
				result = point_stays[i];
			}
			return result;
		}
		return null;
	}
	
	/**
	 * Method is used to calculate some additional stuff for CRT=1 check.
	 */
	public static void checkTimeOfDay()
	{
		if(NetServerParams.timeofday > 0)
			return;
		File time = new File("Files\\gui\\game");
		if(time.isDirectory())
		{
			String[] list = time.list();
			for( int i=0; i<list.length; i++ )
			{
				try
				{
					if( list[i].endsWith("tons"))
					{	
						NetServerParams.timeofday += com.maddox.rts.Finger.LongFN(0L, time.getPath() + File.separator + list[i]);
						NetServerParams.timeofday += new File(time.getPath() + File.separator + list[i]).length();
						NetServerParams.timeofday += list[i].hashCode();
					}
				}
				catch(Exception ex){}
			}
		}
		time = new File("MODS\\STD\\gui\\game");
		if(time.isDirectory())
		{
			String [] list = time.list();
			for( int i=0; i<list.length; i++ )
			{
				try
				{
					if( list[i].endsWith("tons"))
					{
						NetServerParams.timeofday += com.maddox.rts.Finger.LongFN(0L, time.getPath() + File.separator + list[i]);
						NetServerParams.timeofday += new File(time.getPath() + File.separator + list[i]).length();
						NetServerParams.timeofday += list[i].hashCode();
					}
				}
				catch(Exception ex){}
			}
		}
	}
	
	/**
	 * Call this when new mission is loaded to reset server time!
	 * @param netserverparams
	 */
	public static void resetServerTime(NetServerParams netserverparams)
	{
		netserverparams.serverDeltaTime = 0L;
		netserverparams.serverDeltaTime_lastUpdate = 0L;
		netserverparams.zutiInitialTimeSyncDone = false;
	}
	
	/**
	 * Method executes sequence of commands that enable orders to be used/send during dogfight game.
	 * @param netserverparams
	 */
	public static void startDogfightGame(NetServerParams netserverparams)
	{
		netserverparams.prepareOrdersTree();
		Time.setPause(false);
		AudioDevice.soundsOn();
	}
	
	/**
	 * Ignore given class for CRT check or not? All classes that are named with less than 3 chars are ignored.
	 * @param className
	 * @return
	 */
	public static boolean ignoreClass(String className)
	{
		if( className.indexOf(".Zuti") > -1 )
			return true;
		
		String str = className.substring( className.lastIndexOf(".")+1, className.length() );
		if( str.length() < 3 )
			return true;
		
		return false;
	}
	
	/**
	 * Method returns max fuel selection for specified born place and aircraft. Result indicates
	 * fuel combo box selection id and is in range between 0 and 9. 0 = 10%, 9 = 100%.
	 * @param bp
	 * @param acName
	 * @return
	 */
	public static int getBornPlaceAircraftFuelSelectionLimit(BornPlace bp, String acName)
	{
		int defaultValue = 9;
		if( bp == null || acName == null )
			return defaultValue;
		
		if( bp.zutiAircraft == null || bp.zutiAircraft.size() < 1 )
			return defaultValue;
		
		int size = bp.zutiAircraft.size();
		for( int i=0; i<size; i++ )
		{
			ZutiAircraft zac = (ZutiAircraft)bp.zutiAircraft.get(i);
			if( zac.getAcName().equals(acName) )
				return zac.getMaxFuelSelection();
		}
		
		return defaultValue;
	}
	
	/**
	 * Method returns max fuel in kg for specified aircraft at specified born place.
	 * @param bp
	 * @param aircraft
	 * @return
	 */
	public static float getAircraftFuelLimitBasedOnBornPlaceAircraftFuelSelectionLimitation(BornPlace bp, Aircraft aircraft)
	{
		if( bp == null || aircraft == null )
			return 0.0F;
		
		String acName = Property.stringValue((aircraft).getClass(), "keyName");
		int maxSelection = ZutiSupportMethods_Net.getBornPlaceAircraftFuelSelectionLimit(bp, acName);
		maxSelection = maxSelection+1;
		float maxFuel = aircraft.FM.M.maxFuel;
		
		return maxFuel*maxSelection/10;
		
	}

	/**
	 * Gets born place that is nearest to given coordinates, regardless of it's army.
	 * @param x1
	 * @param y1
	 * @return
	 */
	public static BornPlace getNearestBornPlace_AnyArmy(double x1, double y1)
	{
		ArrayList list = World.cur().bornPlaces;
		double minDistance = 1000000.0D;
		BornPlace result = null;
		
		try
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				BornPlace bp = (BornPlace)list.get(i);
				double tmpDistance = Math.sqrt(Math.pow(x1-bp.place.x, 2) + Math.pow(y1-bp.place.y, 2) );					
				if( tmpDistance < minDistance )
				{
					minDistance = tmpDistance;
					result = bp;
				}
			}
		}
		catch(Exception ex){}
		
		//If pilot stopped more outside that home base radius... well, nothing for him here...
		if( result == null || minDistance > result.r )
			return null;
		
		return result;
	}
	
	/**
	 * Gets born place that is nearest to given coordinates that is NOT of army that you specified.
	 * @param x1
	 * @param y1
	 * @param army
	 * @param onlyCaptureEnabledBases
	 * @return
	 */
	public static BornPlace getNearestBornPlace_ExcludeArmy(double x1, double y1, int army, boolean onlyCaptureEnabledBases)
	{
		ArrayList list = World.cur().bornPlaces;
		double minDistance = 1000000.0D;
		BornPlace result = null;
		
		try
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				BornPlace bp = (BornPlace)list.get(i);
				
				if( bp.army == army || (onlyCaptureEnabledBases && !bp.zutiCanThisHomeBaseBeCaptured) )
					continue;
				
				double tmpDistance = Math.sqrt(Math.pow(x1-bp.place.x, 2) + Math.pow(y1-bp.place.y, 2) );					
				if( tmpDistance < minDistance )
				{
					minDistance = tmpDistance;
					result = bp;
				}
			}
		}
		catch(Exception ex){}
		
		//If pilot stopped more outside that home base radius... well, nothing for him here...
		if( result == null || minDistance > result.r )
			return null;
		
		return result;
	}
	
	/**
	 * Returns nearest friendly born place for specified coordinates
	 * @param x1
	 * @param y1
	 * @param army
	 * @return
	 */
	public static BornPlace getNearestBornPlace(double x1, double y1, int army)
	{
		ArrayList list = World.cur().bornPlaces;
		double minDistance = 1000000.0D;
		BornPlace result = null;
		
		try
		{
			int size = list.size();
			for (int i = 0; i < size; i++)
			{
				BornPlace bp = (BornPlace)list.get(i);
				if( bp.army == army )
				{
					double tmpDistance = Math.sqrt(Math.pow(x1-bp.place.x, 2) + Math.pow(y1-bp.place.y, 2) );
					
					if( tmpDistance < minDistance )
					{
						minDistance = tmpDistance;
						result = bp;
					}
				}
			}
		}
		catch(Exception ex){}
		
		return result;
	}

	/**
	 * Get born place that has specified coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public static BornPlace getBornPlace(double x, double y)
	{
		World world = World.cur();
		
		if( world == null || world.bornPlaces == null )
			return null;
		
		ArrayList bornplaces = world.bornPlaces;
		BornPlace bp = null;
		
		for( int i=0; i<bornplaces.size(); i++ )
		{
			bp = (BornPlace)bornplaces.get(i);
			if( bp.place.x == x && bp.place.y == y )
				return bp;
		}
		
		return null;
	}
	
	/**
	 * Get born place based on it's id
	 * @param bpId
	 * @return
	 */
	public static BornPlace getBornPlace(int bpId)
	{
		World world = World.cur();
		
		if( world == null || world.bornPlaces == null )
			return null;
		
		ArrayList bornplaces = world.bornPlaces;
		BornPlace bp = null;
		
		for( int i=0; i<bornplaces.size(); i++ )
		{
			bp = (BornPlace)bornplaces.get(i);
			if( bp.zutiBpIndex == bpId )
				return bp;
		}
		
		return null;
	}
	
	/**
	 * Check if given coordinates are inside any born place.
	 * @param x
	 * @param y
	 * @return Valid BornPlace object, else null.
	 */
	public static BornPlace isOnBornPlace(double x, double y)
	{
		World world = World.cur();
		
		if( world == null || world.bornPlaces == null )
			return null;
		
		ArrayList bornplaces = world.bornPlaces;
		BornPlace bp = null;
		
		for( int i=0; i<bornplaces.size(); i++ )
		{
			bp = (BornPlace)bornplaces.get(i);
			double radius = bp.r * bp.r;
			double tmpDistance = Math.pow(x-bp.place.x, 2) + Math.pow(y-bp.place.y, 2);
			
			if( tmpDistance < radius )
			{
				return bp;
			}
		}
		
		return null;
	}
	
	/**
	 * Check if given coordinates are inside specified born place.
	 * @param x
	 * @param y
	 * @param bornPlace
	 * @return Valid BornPlace object, else null.
	 */
	public static boolean isOnBornPlace(double x, double y, BornPlace bp)
	{
		double radius = bp.r * bp.r;
		double tmpDistance = Math.pow(x-bp.place.x, 2) + Math.pow(y-bp.place.y, 2);
		
		if( tmpDistance < radius )
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method is one of the keys for enabling AI aircraft to recognize
	 * and be aware of live players in dogfight game mode.
	 * @param aircraft
	 */
	public static void manageDogfightGroups(Aircraft aircraft)
	{
		if( aircraft != null && Mission.isDogfight() && Mission.isServer())
		{
			int army = aircraft.getArmy();
			
			AirGroup airgroup = null;
			int i_1_ = AirGroupList.length(War.Groups[army - 1 & 0x1]);
			for (int i_2_ = 0; i_2_ < i_1_; i_2_++)
			{
				airgroup = AirGroupList.getGroup(War.Groups[army - 1 & 0x1], i_2_);
				if (airgroup != null && airgroup.zutiIsDogfightGroup && airgroup.nOfAirc < 16)
				{
					airgroup.addAircraft(aircraft);
					return;
				}
			}
			if (airgroup == null || !airgroup.zutiIsDogfightGroup || airgroup.nOfAirc >= 16)
			{
				AirGroup zutiDogfightGroup = new AirGroup();
				zutiDogfightGroup.zutiIsDogfightGroup = true;
				zutiDogfightGroup.addAircraft(aircraft);
				AirGroupList.addAirGroup(War.Groups, army - 1 & 0x1, zutiDogfightGroup);
			}
		}
	}
	
	/**
	 * Call this method to determine if netuser is in own aircraft or not.
	 * @param netuser
	 * @param aircraft
	 * @return
	 */
	public static boolean isInOwnAircraft(NetUser netuser, Aircraft aircraft)
	{
		if( netuser == null || aircraft == null )
			return false;
		
		String compiledAcName = netuser.uniqueName() + "_0";
		if( aircraft.name().equals(compiledAcName) )
			return true;
		
		return false;
	}
	
	/**
	 * Method searches for specified born place in global born places list.
	 * @param bornPlace
	 * @return
	 */
	public static int getBornPlaceIndex(BornPlace bornPlace)
	{
		List bornPlaces = World.cur().bornPlaces;
		if( bornPlaces != null )
		{
			BornPlace bp = null;
			for( int i=0; i<bornPlaces.size(); i++ )
			{
				bp = (BornPlace)bornPlaces.get(i);
				if( bp.equals(bornPlace) )
					return i;
			}
		}
		
		return -1;
	}
}