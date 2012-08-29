package com.maddox.il2.net.client;

import com.maddox.il2.net.NetUserStat;
import java.io.PrintStream;

public class ProfileUser
{
  public static final int FLAG_ADMINISTRATOR = 1;
  public static final int FLAG_SERVER = 2;
  public static final int FLAG_MODERATOR = 4;
  public static final int FLAG_GLOBAL_MODERATOR = 8;
  public static final int FLAG_DEMO = 16;
  public static final int FLAG_BETA = 32;
  public static final int FLAG_RELEASE = 64;
  public static final int FLAG_REGISTRATION = 112;
  public int profileId;
  public int clanId;
  public int flags;
  public String alias;
  public String email;
  public String home;
  public int lastLogin;
  public Field[] fields;
  public static final int Field_INTEGER = 0;
  public static final int Field_FLOAT = 1;
  public static final int Field_STRING = 2;
  public static final int Field_TIME = 3;
  public static final String F_Missions = "missions";
  public static final String F_Sorties = "sorties";
  public static final String F_Takeoffs = "takeoffs";
  public static final String F_Landings = "landings";
  public static final String F_Ditches = "ditches";
  public static final String F_Bails = "bails";
  public static final String F_Deaths = "deaths";
  public static final String F_TTotal = "ttotal";
  public static final String F_TSingle = "tsingle";
  public static final String F_TMulti = "tmulti";
  public static final String F_TGunner = "tgunner";
  public static final String F_TNight = "tnight";
  public static final String F_TIns = "tins";
  public static final String F_TCCountry = "tccountry";
  public static final String F_Fired = "fired";
  public static final String F_Hit = "hit";
  public static final String F_AerialHits = "aerialhits";
  public static final String F_Rockets = "rockets";
  public static final String F_RocketHits = "rockethits";
  public static final String F_Bombs = "bombs";
  public static final String F_BombHits = "bombhits";
  public static final String F_Kills = "kills";
  public static final String F_FKills = "fkills";
  public static final String F_SKills = "skills";
  public static final String F_FSKills = "fskills";
  public static final String F_Ships = "ships";
  public static final String F_FShips = "fships";
  public static final String F_Tanks = "tanks";
  public static final String F_FTanks = "ftanks";
  public static final String F_Cars = "cars";
  public static final String F_FCars = "fcars";
  public static final String F_Guns = "guns";
  public static final String F_FGuns = "fguns";
  public static final String F_AAAs = "aaas";
  public static final String F_FAAAs = "faaas";
  public static final String F_Trains = "trains";
  public static final String F_FTrains = "ftrains";
  public static final String F_Bridges = "bridges";
  public static final String F_Buildings = "buildings";
  public static final String F_Others = "others";
  public static final String F_Score = "score";
  public static final String F_Rating = "rating";

  public int findField(String paramString)
  {
    if (this.fields == null)
      return -1;
    for (int i = 0; i < this.fields.length; i++)
      if (paramString.equals(this.fields[i].name))
        return i;
    System.out.println("ProfileUser: field '" + paramString + "' NOT found");
    return -1;
  }
  public String getField(String paramString) {
    int i = findField(paramString);
    if (i < 0)
      return null;
    return this.fields[i].value;
  }
  public void setField(String paramString1, String paramString2) {
    int i = findField(paramString1);
    if (i < 0)
      return;
    this.fields[i].value = paramString2;
  }

  public void set(ProfileUser paramProfileUser)
  {
    this.profileId = paramProfileUser.profileId;
    this.flags = paramProfileUser.flags;
    this.alias = paramProfileUser.alias;
    this.email = paramProfileUser.email;
    this.home = paramProfileUser.home;
    this.lastLogin = paramProfileUser.lastLogin;
    if (paramProfileUser.fields != null) {
      this.fields = new Field[paramProfileUser.fields.length];
      for (int i = 0; i < paramProfileUser.fields.length; i++) {
        this.fields[i] = new Field();
        this.fields[i].name = paramProfileUser.fields[i].name;
        this.fields[i].type = paramProfileUser.fields[i].type;
        this.fields[i].value = paramProfileUser.fields[i].value;
      }
    }
  }

  public int get(String paramString, int paramInt)
  {
    String str = getField(paramString);
    if (str == null)
      return paramInt;
    try {
      return Integer.parseInt(str); } catch (Exception localException) {
    }
    return paramInt;
  }

  public float get(String paramString, float paramFloat) {
    String str = getField(paramString);
    if (str == null)
      return paramFloat;
    try {
      return Float.parseFloat(str); } catch (Exception localException) {
    }
    return paramFloat;
  }

  public void set(String paramString, int paramInt) {
    setField(paramString, "" + paramInt);
  }
  public void set(String paramString, float paramFloat) {
    setField(paramString, "" + paramFloat);
  }

  public NetUserStat getStat() {
    NetUserStat localNetUserStat = new NetUserStat();
    localNetUserStat.nMissions = get("missions", 0);
    localNetUserStat.nSorties = get("sorties", 0);
    localNetUserStat.nTakeoffs = get("takeoffs", 0);
    localNetUserStat.nLandings = get("landings", 0);
    localNetUserStat.nDitches = get("ditches", 0);
    localNetUserStat.nBails = get("bails", 0);
    localNetUserStat.nDeaths = get("deaths", 0);
    localNetUserStat.tTotal = get("ttotal", 0.0F);
    localNetUserStat.tSingle = get("tsingle", 0.0F);
    localNetUserStat.tMulti = get("tmulti", 0.0F);
    localNetUserStat.tGunner = get("tgunner", 0.0F);
    localNetUserStat.tNight = get("tnight", 0.0F);
    localNetUserStat.tIns = get("tins", 0.0F);
    localNetUserStat.tCCountry = get("tccountry", 0.0F);
    localNetUserStat.rating = get("rating", 0.0F);
    localNetUserStat.score = get("score", 0.0F);
    localNetUserStat.enemyKill[0] = get("kills", 0);
    localNetUserStat.enemyKill[1] = get("tanks", 0);
    localNetUserStat.enemyKill[2] = get("cars", 0);
    localNetUserStat.enemyKill[3] = get("guns", 0);
    localNetUserStat.enemyKill[4] = get("aaas", 0);
    localNetUserStat.enemyKill[5] = get("bridges", 0);
    localNetUserStat.enemyKill[6] = get("trains", 0);
    localNetUserStat.enemyKill[7] = get("ships", 0);
    localNetUserStat.enemyKill[8] = get("skills", 0);
    localNetUserStat.friendKill[0] = get("fkills", 0);
    localNetUserStat.friendKill[1] = get("ftanks", 0);
    localNetUserStat.friendKill[2] = get("fcars", 0);
    localNetUserStat.friendKill[3] = get("fguns", 0);
    localNetUserStat.friendKill[4] = get("faaas", 0);

    localNetUserStat.friendKill[6] = get("ftrains", 0);
    localNetUserStat.friendKill[7] = get("fships", 0);
    localNetUserStat.friendKill[8] = get("fskills", 0);
    localNetUserStat.bulletsFire = get("fired", 0);
    localNetUserStat.bulletsHit = get("hit", 0);
    localNetUserStat.bulletsHitAir = get("aerialhits", 0);
    localNetUserStat.rocketsFire = get("rockets", 0);
    localNetUserStat.rocketsHit = get("rockethits", 0);
    localNetUserStat.bombFire = get("bombs", 0);
    localNetUserStat.bombHit = get("bombhits", 0);
    return localNetUserStat;
  }

  public void setStat(NetUserStat paramNetUserStat) {
    set("missions", paramNetUserStat.nMissions);
    set("sorties", paramNetUserStat.nSorties);
    set("takeoffs", paramNetUserStat.nTakeoffs);
    set("landings", paramNetUserStat.nLandings);
    set("ditches", paramNetUserStat.nDitches);
    set("bails", paramNetUserStat.nBails);
    set("deaths", paramNetUserStat.nDeaths);
    set("ttotal", paramNetUserStat.tTotal);
    set("tsingle", paramNetUserStat.tSingle);
    set("tmulti", paramNetUserStat.tMulti);
    set("tgunner", paramNetUserStat.tGunner);
    set("tnight", paramNetUserStat.tNight);
    set("tins", paramNetUserStat.tIns);
    set("tccountry", paramNetUserStat.tCCountry);
    set("rating", paramNetUserStat.rating);
    set("score", (float)paramNetUserStat.score);
    set("kills", paramNetUserStat.enemyKill[0]);
    set("tanks", paramNetUserStat.enemyKill[1]);
    set("cars", paramNetUserStat.enemyKill[2]);
    set("guns", paramNetUserStat.enemyKill[3]);
    set("aaas", paramNetUserStat.enemyKill[4]);
    set("bridges", paramNetUserStat.enemyKill[5]);
    set("trains", paramNetUserStat.enemyKill[6]);
    set("ships", paramNetUserStat.enemyKill[7]);
    set("skills", paramNetUserStat.enemyKill[8]);
    set("fkills", paramNetUserStat.friendKill[0]);
    set("ftanks", paramNetUserStat.friendKill[1]);
    set("fcars", paramNetUserStat.friendKill[2]);
    set("fguns", paramNetUserStat.friendKill[3]);
    set("faaas", paramNetUserStat.friendKill[4]);

    set("ftrains", paramNetUserStat.friendKill[6]);
    set("fships", paramNetUserStat.friendKill[7]);
    set("fskills", paramNetUserStat.friendKill[8]);
    set("fired", paramNetUserStat.bulletsFire);
    set("hit", paramNetUserStat.bulletsHit);
    set("aerialhits", paramNetUserStat.bulletsHitAir);
    set("rockets", paramNetUserStat.rocketsFire);
    set("rockethits", paramNetUserStat.rocketsHit);
    set("bombs", paramNetUserStat.bombFire);
    set("bombhits", paramNetUserStat.bombHit);

    set("buildings", 0);
    set("others", 0);
  }

  public static class Info
  {
    public int profileId;
    public String alias;
  }

  public static class Field
  {
    public String name;
    public int type;
    public String value;
  }
}