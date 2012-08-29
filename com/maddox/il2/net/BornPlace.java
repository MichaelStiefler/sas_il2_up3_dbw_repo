// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BornPlace.java

package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.net:
//            NetServerParams

public class BornPlace
{
    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }


    public com.maddox.il2.engine.Loc getAircraftPlace(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        com.maddox.il2.engine.Loc loc = null;
        if(i < 0 || i > com.maddox.il2.ai.World.cur().airdrome.stay.length)
        {
            double d = com.maddox.il2.ai.World.land().HQ(place.x, place.y);
            loc = new Loc(place.x, place.y, d, 0.0F, 0.0F, 0.0F);
        } else
        if(i >= com.maddox.il2.ai.World.cur().airdrome.stayHold.length)
        {
            com.maddox.il2.ai.air.Point_Stay point_stay = com.maddox.il2.ai.World.cur().airdrome.stay[i][0];
            loc = new Loc(point_stay.x, point_stay.y, 0.0D, 0.0F, 0.0F, 0.0F);
            if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing || zutiAirspawnOnly)
            {
                com.maddox.JGP.Point3d point3d = new Point3d(200D, 200D, 0.0D);
                loc.add(point3d);
                return loc;
            }
            com.maddox.il2.ai.AirportCarrier airportcarrier = (com.maddox.il2.ai.AirportCarrier)com.maddox.il2.ai.Airport.nearest(loc.getPoint(), -1, 4);
            if(airportcarrier != null)
            {
                corn.set(loc.getPoint());
                corn1.set(loc.getPoint());
                corn.z = com.maddox.il2.engine.Engine.cur.land.HQ(loc.getPoint().x, loc.getPoint().y);
                corn1.z = corn.z + 40D;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(corn, corn1, false, clipFilter, pship);
                if(airportcarrier.ship() != actor)
                    airportcarrier = null;
            }
            if(airportcarrier != null)
            {
                if(com.maddox.il2.game.Mission.isDogfight())
                    if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                    {
                        return airportcarrier.setClientTakeOff(loc.getPoint(), aircraft);
                    } else
                    {
                        airportcarrier.setTakeoff(loc.getPoint(), new com.maddox.il2.objects.air.Aircraft[] {
                            aircraft
                        });
                        return aircraft.pos.getAbs();
                    }
                airportcarrier.getTakeoff(aircraft, loc);
            } else
            {
                loc = new Loc(point_stay.x, point_stay.y, 1000D, 0.0F, 0.0F, 0.0F);
            }
        } else
        {
            com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
            com.maddox.il2.ai.air.Point_Stay point_stay1 = apoint_stay[i][apoint_stay[i].length - 1];
            com.maddox.il2.ai.World.land();
            double d1 = com.maddox.il2.engine.Landscape.HQ(point_stay1.x, point_stay1.y);
            loc = new Loc(point_stay1.x, point_stay1.y, d1, 0.0F, 0.0F, 0.0F);
            int j = apoint_stay[i].length - 2;
            if(j >= 0)
            {
                com.maddox.JGP.Point3d point3d1 = new Point3d(point_stay1.x, point_stay1.y, 0.0D);
                com.maddox.JGP.Point3d point3d2 = new Point3d(apoint_stay[i][j].x, apoint_stay[i][j].y, 0.0D);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                vector3d.sub(point3d2, point3d1);
                vector3d.normalize();
                com.maddox.il2.engine.Orient orient = new Orient();
                orient.setAT0(vector3d);
                if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing || zutiAirspawnOnly)
                {
                    loc.set(orient);
                    return loc;
                }
                loc.getPoint().z += aircraft.FM.Gears.H;
                com.maddox.il2.engine.Engine.land().N(loc.getPoint().x, loc.getPoint().y, v1);
                orient.orient(v1);
                orient.increment(0.0F, aircraft.FM.Gears.Pitch, 0.0F);
                loc.set(orient);
            }
        }
        return loc;
    }

    public BornPlace(double d, double d1, int i, float f)
    {
        place = new Point2d();
        bParachute = true;
        zutiOriginalX = 0.0D;
        zutiOriginalY = 0.0D;
        zutiStaticPositionOnly = false;
        zutiBpStayPoints = null;
        zutiAlreadyAssigned = false;
        zutiSpawnHeight = 1000;
        zutiSpawnSpeed = 200;
        zutiSpawnOrient = 0;
        zutiBpIndex = -1;
        zutiRadarHeight_MIN = 0;
        zutiRadarHeight_MAX = 5000;
        zutiRadarRange = 50;
        zutiMaxBasePilots = 0;
        zutiAirspawnIfCarrierFull = false;
        zutiAirspawnOnly = false;
        zutiNumberOfSpawnPlaces = 0;
        zutiAircrafts = null;
        zutiHomeBaseCountries = null;
        zutiEnablePlaneLimits = false;
        zutiDecreasingNumberOfPlanes = false;
        zutiIncludeStaticPlanes = false;
        zutiDisableSpawning = false;
        zutiEnableFriction = false;
        zutiFriction = 3.7999999999999998D;
        place.set(d, d1);
        army = i;
        r = f;
        zutiOriginalX = place.x;
        zutiOriginalY = place.y;
    }

    public boolean zutiCanUserJoin()
    {
        return zutiMaxBasePilots == 0 || zutiMaxBasePilots > 0 && tmpForBrief < zutiMaxBasePilots;
    }

    public void zutiCountBornPlaceStayPoints()
    {
        zutiNumberOfSpawnPlaces = 0;
        if(com.maddox.il2.ai.World.cur().airdrome.stay == null)
            return;
        com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
        double d = place.x;
        double d1 = place.y;
        double d2 = r * r;
        int i = apoint_stay.length;
        for(int j = 0; j < i;)
            try
            {
                if(apoint_stay[j] == null)
                    continue;
                com.maddox.il2.ai.air.Point_Stay point_stay = apoint_stay[j][apoint_stay[j].length - 1];
                double d3 = ((double)point_stay.x - d) * ((double)point_stay.x - d) + ((double)point_stay.y - d1) * ((double)point_stay.y - d1);
                if(d3 <= d2)
                    zutiNumberOfSpawnPlaces++;
                continue;
            }
            catch(java.lang.Exception exception)
            {
                exception.printStackTrace();
                java.lang.System.out.println("Error when reading HomeBase born place nr. " + j + " (" + exception.toString() + ")");
                j++;
            }

    }

    public void zutiSetBornPlaceStayPointsNumber(int i)
    {
        zutiNumberOfSpawnPlaces = i;
    }

    public void zutiFillAirNames()
    {
        if(zutiAircrafts == null || zutiAircrafts.size() < 1)
        {
            zutiCreateCompleteACList();
            return;
        }
        if(airNames == null)
            airNames = new ArrayList();
        airNames.clear();
        int i = zutiAircrafts.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(j);
            airNames.add(zutiaircraft.getAcName());
        }

    }

    public java.util.ArrayList zutiGetAcLoadouts(java.lang.String s)
    {
        if(zutiAircrafts == null || zutiAircrafts.size() < 1)
            return new ArrayList();
        int i = zutiAircrafts.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(j);
            if(zutiaircraft.getAcName().equals(s))
                return zutiaircraft.getSelectedWeaponI18NNames();
        }

        return new ArrayList();
    }

    public void zutiReleaseAircraft(com.maddox.il2.fm.FlightModel flightmodel, java.lang.String s, boolean flag, boolean flag1, boolean flag2)
    {
        if(zutiAircrafts == null)
            return;
        boolean flag3 = false;
        if(flightmodel != null)
            flag3 = com.maddox.il2.net.BornPlace.isLandedOnHomeBase(flightmodel, this);
        int i = zutiAircrafts.size();
        int j = 0;
        do
        {
            if(j >= i)
                break;
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(j);
            if(zutiaircraft.getAcName().equals(s) || zutiaircraft.getClassShortAcName().equals(s))
            {
                if(zutiDecreasingNumberOfPlanes && (!flag || !flag2 || !flag3))
                    zutiaircraft.decreasePlaneCount();
                if(!flag1)
                    zutiaircraft.decreaseInUse();
                break;
            }
            j++;
        } while(true);
        if(!flag3 && flag && flag2 && !flag1 && zutiDecreasingNumberOfPlanes && com.maddox.il2.net.BornPlace.isLandedOnHomeBase(flightmodel, null))
        {
            com.maddox.il2.net.BornPlace bornplace = com.maddox.il2.net.BornPlace.getCurrentBornPlace(flightmodel.Loc.x, flightmodel.Loc.y);
            if(bornplace != null)
            {
                int k = 0;
                do
                {
                    if(k >= bornplace.zutiAircrafts.size())
                        break;
                    com.maddox.il2.game.ZutiAircraft zutiaircraft1 = (com.maddox.il2.game.ZutiAircraft)bornplace.zutiAircrafts.get(k);
                    if(zutiaircraft1.getAcName().equals(s) || zutiaircraft1.getClassShortAcName().equals(s))
                    {
                        zutiaircraft1.increasePlaneCount();
                        break;
                    }
                    k++;
                } while(true);
            }
        }
    }

    public boolean zutiIsAcAvailable(java.lang.String s)
    {
        if(zutiAircrafts == null)
            return false;
        int i;
        int j;
        i = zutiAircrafts.size();
        j = 0;
_L1:
        boolean flag;
        if(j >= i)
            break MISSING_BLOCK_LABEL_111;
        com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(j);
        if(!zutiaircraft.getAcName().equals(s))
            break MISSING_BLOCK_LABEL_73;
        flag = zutiaircraft.isAvailable(zutiDecreasingNumberOfPlanes);
        if(flag)
            zutiaircraft.increaseInUse();
        return flag;
        j++;
          goto _L1
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("BornPlace error, ID_03: " + exception.toString());
        return false;
    }

    public java.util.ArrayList zutiGetNotAvailablePlanesList()
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(zutiAircrafts == null)
            return arraylist;
        for(int i = 0; i < zutiAircrafts.size(); i++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(i);
            if(!zutiaircraft.isAvailable(zutiDecreasingNumberOfPlanes))
                arraylist.add(zutiaircraft.getAcName());
        }

        return arraylist;
    }

    public java.util.ArrayList zutiGetAvailablePlanesList()
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(zutiAircrafts == null)
            return arraylist;
        for(int i = 0; i < zutiAircrafts.size(); i++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(i);
            if(zutiaircraft.isAvailable(zutiDecreasingNumberOfPlanes))
                arraylist.add(zutiaircraft.getAcName());
        }

        return arraylist;
    }

    public void zutiActivatePlanes(java.util.ArrayList arraylist)
    {
        if(zutiAircrafts == null)
            return;
        airNames.clear();
        for(int i = 0; i < zutiAircrafts.size(); i++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)zutiAircrafts.get(i);
            java.lang.String s = zutiaircraft.getAcName();
            boolean flag = true;
            for(int j = 0; j < arraylist.size(); j++)
            {
                java.lang.String s1 = (java.lang.String)arraylist.get(j);
                if(s.equals(s1))
                    flag = false;
            }

            if(flag)
            {
                airNames.add(s);
                zutiaircraft.setMaxAllowed(1);
            } else
            {
                zutiaircraft.setMaxAllowed(0);
            }
        }

    }

    private void zutiAddAllAircraft()
    {
        if(airNames == null)
            airNames = new ArrayList();
        airNames.clear();
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
        for(int i = 0; i < arraylist.size(); i++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(i);
            if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!airNames.contains(s))
                airNames.add(s);
        }

    }

    private void zutiCreateCompleteACList()
    {
        zutiAircrafts = new ArrayList();
        zutiAddAllAircraft();
        for(int i = 0; i < airNames.size(); i++)
        {
            java.lang.String s = (java.lang.String)airNames.get(i);
            com.maddox.il2.game.ZutiAircraft zutiaircraft = new ZutiAircraft();
            zutiaircraft.setAcName(s);
            if(zutiDecreasingNumberOfPlanes)
                zutiaircraft.setMaxAllowed(-1);
            else
                zutiaircraft.setMaxAllowed(0);
            if(zutiEnablePlaneLimits)
            {
                java.util.ArrayList arraylist = new ArrayList();
                arraylist.add("Default");
                zutiaircraft.setSelectedWeapons(arraylist);
            }
            zutiAircrafts.add(zutiaircraft);
        }

    }

    public void zutiLoadDefaultCountries()
    {
        if(zutiHomeBaseCountries == null)
            zutiHomeBaseCountries = new ArrayList();
        zutiHomeBaseCountries.clear();
        if(army == 1)
        {
            zutiHomeBaseCountries.clear();
            zutiHomeBaseCountries.add("None");
            zutiHomeBaseCountries.add("USSR");
            zutiHomeBaseCountries.add("RAF");
            zutiHomeBaseCountries.add("USAAF");
        } else
        if(army == 2)
        {
            zutiHomeBaseCountries.clear();
            zutiHomeBaseCountries.add("None");
            zutiHomeBaseCountries.add("Germany");
            zutiHomeBaseCountries.add("Italy");
            zutiHomeBaseCountries.add("IJA");
        } else
        {
            zutiHomeBaseCountries.clear();
            zutiHomeBaseCountries.add("None");
        }
    }

    public void zutiLoadAllCountries()
    {
        if(zutiHomeBaseCountries == null)
            zutiHomeBaseCountries = new ArrayList();
        zutiHomeBaseCountries.clear();
        java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(j);
            java.lang.String s = resourcebundle.getString(regiment.branch());
            if(!zutiHomeBaseCountries.contains(s))
                zutiHomeBaseCountries.add(s);
        }

    }

    public static com.maddox.il2.net.BornPlace getCurrentBornPlace(double d, double d1)
    {
        java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
        if(arraylist == null || arraylist.size() == 0)
            return null;
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)arraylist.get(i);
            double d2 = d - bornplace.place.x;
            double d3 = d1 - bornplace.place.y;
            double d4 = java.lang.Math.sqrt(d2 * d2 + d3 * d3);
            if((double)bornplace.r > d4)
                return bornplace;
        }

        return null;
    }

    public double getBornPlaceFriction(double d, double d1)
    {
        double d2 = d - place.x;
        double d3 = d1 - place.y;
        double d4 = java.lang.Math.sqrt(d2 * d2 + d3 * d3);
        if((double)r > d4)
        {
            if(zutiEnableFriction)
                return zutiFriction;
            else
                return 3.7999999523162842D;
        } else
        {
            return -1D;
        }
    }

    public static boolean isLandedOnHomeBase(com.maddox.il2.fm.FlightModel flightmodel, com.maddox.il2.net.BornPlace bornplace)
    {
        if(bornplace == null)
        {
            java.util.ArrayList arraylist = com.maddox.il2.ai.World.cur().bornPlaces;
            if(arraylist == null || arraylist.size() == 0)
                return false;
            for(int i = 0; i < arraylist.size(); i++)
            {
                com.maddox.il2.net.BornPlace bornplace1 = (com.maddox.il2.net.BornPlace)arraylist.get(i);
                if(flightmodel.actor.getArmy() == bornplace1.army)
                {
                    double d2 = flightmodel.Loc.x - bornplace1.place.x;
                    double d4 = flightmodel.Loc.y - bornplace1.place.y;
                    double d5 = java.lang.Math.sqrt(d2 * d2 + d4 * d4);
                    if((double)bornplace1.r > d5)
                        return true;
                }
            }

        } else
        {
            double d = flightmodel.Loc.x - bornplace.place.x;
            double d1 = flightmodel.Loc.y - bornplace.place.y;
            double d3 = java.lang.Math.sqrt(d * d + d1 * d1);
            if((double)bornplace.r > d3)
                return true;
        }
        return false;
    }

    public com.maddox.JGP.Point2d place;
    public float r;
    public int army;
    public boolean bParachute;
    public java.util.ArrayList airNames;
    private static com.maddox.JGP.Vector3d v1 = new Vector3d();
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static com.maddox.JGP.Point3d pship = new Point3d();
    static com.maddox.il2.net.ClipFilter clipFilter = new ClipFilter();
    public int tmpForBrief;
    public double zutiOriginalX;
    public double zutiOriginalY;
    public boolean zutiStaticPositionOnly;
    public java.util.ArrayList zutiBpStayPoints;
    public boolean zutiAlreadyAssigned;
    public int zutiSpawnHeight;
    public int zutiSpawnSpeed;
    public int zutiSpawnOrient;
    public int zutiBpIndex;
    public int zutiRadarHeight_MIN;
    public int zutiRadarHeight_MAX;
    public int zutiRadarRange;
    public int zutiMaxBasePilots;
    public boolean zutiAirspawnIfCarrierFull;
    public boolean zutiAirspawnOnly;
    private int zutiNumberOfSpawnPlaces;
    public java.util.ArrayList zutiAircrafts;
    public java.util.ArrayList zutiHomeBaseCountries;
    public boolean zutiEnablePlaneLimits;
    public boolean zutiDecreasingNumberOfPlanes;
    public boolean zutiIncludeStaticPlanes;
    public boolean zutiDisableSpawning;
    public boolean zutiEnableFriction;
    public double zutiFriction;

}
