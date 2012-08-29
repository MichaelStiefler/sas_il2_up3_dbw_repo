// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ProfileUser.java

package com.maddox.il2.net.client;

import com.maddox.il2.net.NetUserStat;
import java.io.PrintStream;

public class ProfileUser
{
    public static class Info
    {

        public int profileId;
        public java.lang.String alias;

        public Info()
        {
        }
    }

    public static class Field
    {

        public java.lang.String name;
        public int type;
        public java.lang.String value;

        public Field()
        {
        }
    }


    public ProfileUser()
    {
    }

    public int findField(java.lang.String s)
    {
        if(fields == null)
            return -1;
        for(int i = 0; i < fields.length; i++)
            if(s.equals(fields[i].name))
                return i;

        java.lang.System.out.println("ProfileUser: field '" + s + "' NOT found");
        return -1;
    }

    public java.lang.String getField(java.lang.String s)
    {
        int i = findField(s);
        if(i < 0)
            return null;
        else
            return fields[i].value;
    }

    public void setField(java.lang.String s, java.lang.String s1)
    {
        int i = findField(s);
        if(i < 0)
        {
            return;
        } else
        {
            fields[i].value = s1;
            return;
        }
    }

    public void set(com.maddox.il2.net.client.ProfileUser profileuser)
    {
        profileId = profileuser.profileId;
        flags = profileuser.flags;
        alias = profileuser.alias;
        email = profileuser.email;
        home = profileuser.home;
        lastLogin = profileuser.lastLogin;
        if(profileuser.fields != null)
        {
            fields = new com.maddox.il2.net.client.Field[profileuser.fields.length];
            for(int i = 0; i < profileuser.fields.length; i++)
            {
                fields[i] = new Field();
                fields[i].name = profileuser.fields[i].name;
                fields[i].type = profileuser.fields[i].type;
                fields[i].value = profileuser.fields[i].value;
            }

        }
    }

    public int get(java.lang.String s, int i)
    {
        java.lang.String s1;
        s1 = getField(s);
        if(s1 == null)
            return i;
        return java.lang.Integer.parseInt(s1);
        java.lang.Exception exception;
        exception;
        return i;
    }

    public float get(java.lang.String s, float f)
    {
        java.lang.String s1;
        s1 = getField(s);
        if(s1 == null)
            return f;
        return java.lang.Float.parseFloat(s1);
        java.lang.Exception exception;
        exception;
        return f;
    }

    public void set(java.lang.String s, int i)
    {
        setField(s, "" + i);
    }

    public void set(java.lang.String s, float f)
    {
        setField(s, "" + f);
    }

    public com.maddox.il2.net.NetUserStat getStat()
    {
        com.maddox.il2.net.NetUserStat netuserstat = new NetUserStat();
        netuserstat.nMissions = get("missions", 0);
        netuserstat.nSorties = get("sorties", 0);
        netuserstat.nTakeoffs = get("takeoffs", 0);
        netuserstat.nLandings = get("landings", 0);
        netuserstat.nDitches = get("ditches", 0);
        netuserstat.nBails = get("bails", 0);
        netuserstat.nDeaths = get("deaths", 0);
        netuserstat.tTotal = get("ttotal", 0.0F);
        netuserstat.tSingle = get("tsingle", 0.0F);
        netuserstat.tMulti = get("tmulti", 0.0F);
        netuserstat.tGunner = get("tgunner", 0.0F);
        netuserstat.tNight = get("tnight", 0.0F);
        netuserstat.tIns = get("tins", 0.0F);
        netuserstat.tCCountry = get("tccountry", 0.0F);
        netuserstat.rating = get("rating", 0.0F);
        netuserstat.score = get("score", 0.0F);
        netuserstat.enemyKill[0] = get("kills", 0);
        netuserstat.enemyKill[1] = get("tanks", 0);
        netuserstat.enemyKill[2] = get("cars", 0);
        netuserstat.enemyKill[3] = get("guns", 0);
        netuserstat.enemyKill[4] = get("aaas", 0);
        netuserstat.enemyKill[5] = get("bridges", 0);
        netuserstat.enemyKill[6] = get("trains", 0);
        netuserstat.enemyKill[7] = get("ships", 0);
        netuserstat.enemyKill[8] = get("skills", 0);
        netuserstat.friendKill[0] = get("fkills", 0);
        netuserstat.friendKill[1] = get("ftanks", 0);
        netuserstat.friendKill[2] = get("fcars", 0);
        netuserstat.friendKill[3] = get("fguns", 0);
        netuserstat.friendKill[4] = get("faaas", 0);
        netuserstat.friendKill[6] = get("ftrains", 0);
        netuserstat.friendKill[7] = get("fships", 0);
        netuserstat.friendKill[8] = get("fskills", 0);
        netuserstat.bulletsFire = get("fired", 0);
        netuserstat.bulletsHit = get("hit", 0);
        netuserstat.bulletsHitAir = get("aerialhits", 0);
        netuserstat.rocketsFire = get("rockets", 0);
        netuserstat.rocketsHit = get("rockethits", 0);
        netuserstat.bombFire = get("bombs", 0);
        netuserstat.bombHit = get("bombhits", 0);
        return netuserstat;
    }

    public void setStat(com.maddox.il2.net.NetUserStat netuserstat)
    {
        set("missions", netuserstat.nMissions);
        set("sorties", netuserstat.nSorties);
        set("takeoffs", netuserstat.nTakeoffs);
        set("landings", netuserstat.nLandings);
        set("ditches", netuserstat.nDitches);
        set("bails", netuserstat.nBails);
        set("deaths", netuserstat.nDeaths);
        set("ttotal", netuserstat.tTotal);
        set("tsingle", netuserstat.tSingle);
        set("tmulti", netuserstat.tMulti);
        set("tgunner", netuserstat.tGunner);
        set("tnight", netuserstat.tNight);
        set("tins", netuserstat.tIns);
        set("tccountry", netuserstat.tCCountry);
        set("rating", netuserstat.rating);
        set("score", (float)netuserstat.score);
        set("kills", netuserstat.enemyKill[0]);
        set("tanks", netuserstat.enemyKill[1]);
        set("cars", netuserstat.enemyKill[2]);
        set("guns", netuserstat.enemyKill[3]);
        set("aaas", netuserstat.enemyKill[4]);
        set("bridges", netuserstat.enemyKill[5]);
        set("trains", netuserstat.enemyKill[6]);
        set("ships", netuserstat.enemyKill[7]);
        set("skills", netuserstat.enemyKill[8]);
        set("fkills", netuserstat.friendKill[0]);
        set("ftanks", netuserstat.friendKill[1]);
        set("fcars", netuserstat.friendKill[2]);
        set("fguns", netuserstat.friendKill[3]);
        set("faaas", netuserstat.friendKill[4]);
        set("ftrains", netuserstat.friendKill[6]);
        set("fships", netuserstat.friendKill[7]);
        set("fskills", netuserstat.friendKill[8]);
        set("fired", netuserstat.bulletsFire);
        set("hit", netuserstat.bulletsHit);
        set("aerialhits", netuserstat.bulletsHitAir);
        set("rockets", netuserstat.rocketsFire);
        set("rockethits", netuserstat.rocketsHit);
        set("bombs", netuserstat.bombFire);
        set("bombhits", netuserstat.bombHit);
        set("buildings", 0);
        set("others", 0);
    }

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
    public java.lang.String alias;
    public java.lang.String email;
    public java.lang.String home;
    public int lastLogin;
    public com.maddox.il2.net.client.Field fields[];
    public static final int Field_INTEGER = 0;
    public static final int Field_FLOAT = 1;
    public static final int Field_STRING = 2;
    public static final int Field_TIME = 3;
    public static final java.lang.String F_Missions = "missions";
    public static final java.lang.String F_Sorties = "sorties";
    public static final java.lang.String F_Takeoffs = "takeoffs";
    public static final java.lang.String F_Landings = "landings";
    public static final java.lang.String F_Ditches = "ditches";
    public static final java.lang.String F_Bails = "bails";
    public static final java.lang.String F_Deaths = "deaths";
    public static final java.lang.String F_TTotal = "ttotal";
    public static final java.lang.String F_TSingle = "tsingle";
    public static final java.lang.String F_TMulti = "tmulti";
    public static final java.lang.String F_TGunner = "tgunner";
    public static final java.lang.String F_TNight = "tnight";
    public static final java.lang.String F_TIns = "tins";
    public static final java.lang.String F_TCCountry = "tccountry";
    public static final java.lang.String F_Fired = "fired";
    public static final java.lang.String F_Hit = "hit";
    public static final java.lang.String F_AerialHits = "aerialhits";
    public static final java.lang.String F_Rockets = "rockets";
    public static final java.lang.String F_RocketHits = "rockethits";
    public static final java.lang.String F_Bombs = "bombs";
    public static final java.lang.String F_BombHits = "bombhits";
    public static final java.lang.String F_Kills = "kills";
    public static final java.lang.String F_FKills = "fkills";
    public static final java.lang.String F_SKills = "skills";
    public static final java.lang.String F_FSKills = "fskills";
    public static final java.lang.String F_Ships = "ships";
    public static final java.lang.String F_FShips = "fships";
    public static final java.lang.String F_Tanks = "tanks";
    public static final java.lang.String F_FTanks = "ftanks";
    public static final java.lang.String F_Cars = "cars";
    public static final java.lang.String F_FCars = "fcars";
    public static final java.lang.String F_Guns = "guns";
    public static final java.lang.String F_FGuns = "fguns";
    public static final java.lang.String F_AAAs = "aaas";
    public static final java.lang.String F_FAAAs = "faaas";
    public static final java.lang.String F_Trains = "trains";
    public static final java.lang.String F_FTrains = "ftrains";
    public static final java.lang.String F_Bridges = "bridges";
    public static final java.lang.String F_Buildings = "buildings";
    public static final java.lang.String F_Others = "others";
    public static final java.lang.String F_Score = "score";
    public static final java.lang.String F_Rating = "rating";
}
