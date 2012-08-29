package com.maddox.il2.game.cmd;

import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserLeft;
import com.maddox.il2.net.NetUserStat;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdUser extends Cmd
{
  private static final boolean DEBUG = false;
  public static final String NN = "#";
  public static final String ARMY = "ARMY";
  public static final String STAT = "STAT";
  public static final String EVENTLOG = "EVENTLOG";
  private boolean bEventLog = false;

  protected void INFO_HARD(String paramString) {
    if (this.bEventLog)
      EventLog.type(false, paramString);
    else
      super.INFO_HARD(paramString);
  }

  private boolean isDServer(NetUser paramNetUser)
  {
    if (Main.cur().netServerParams == null)
      return false;
    if (!Main.cur().netServerParams.isDedicated())
      return false;
    return Main.cur().netServerParams.host() == paramNetUser;
  }

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (Main.cur().netServerParams == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    fillUsers(paramMap, localArrayList, false);

    if ((localArrayList.size() == 0) && (!Cmd.exist(paramMap, "STAT"))) return null;

    this.bEventLog = Cmd.exist(paramMap, "EVENTLOG");
    int i;
    Object localObject1;
    Object localObject2;
    if (Cmd.exist(paramMap, "STAT")) {
      fillUsers(paramMap, localArrayList, true);
      for (i = 0; i < localArrayList.size(); i++) {
        localObject1 = localArrayList.get(i);

        boolean bool = false;
        Object localObject3;
        String str;
        if ((localObject1 instanceof NetUser)) {
          localObject3 = (NetUser)localObject1;
          if (isDServer((NetUser)localObject3)) continue;
          localObject2 = ((NetUser)localObject3).curstat();
          str = ((NetUser)localObject3).uniqueName();
        } else {
          localObject3 = (NetUserLeft)localObject1;
          localObject2 = ((NetUserLeft)localObject3).stat;
          str = ((NetUserLeft)localObject3).uniqueName;
          bool = true;
        }
        INFO_HARD("-------------------------------------------------------");
        INFO_HARD("Name: \t" + str);
        INFO_HARD("Score: \t" + (bool ? 0 : (int)((NetUserStat)localObject2).score));
        INFO_HARD("State: \t" + playerState((NetUserStat)localObject2, bool));
        INFO_HARD("Enemy Aircraft Kill: \t" + localObject2.enemyKill[0]);
        INFO_HARD("Enemy Static Aircraft Kill: \t" + localObject2.enemyKill[8]);
        INFO_HARD("Enemy Tank Kill: \t" + localObject2.enemyKill[1]);
        INFO_HARD("Enemy Car Kill: \t" + localObject2.enemyKill[2]);
        INFO_HARD("Enemy Artillery Kill: \t" + localObject2.enemyKill[3]);
        INFO_HARD("Enemy AAA Kill: \t" + localObject2.enemyKill[4]);

        INFO_HARD("Enemy Wagon Kill: \t" + localObject2.enemyKill[6]);
        INFO_HARD("Enemy Ship Kill: \t" + localObject2.enemyKill[7]);

        INFO_HARD("Friend Aircraft Kill: \t" + localObject2.friendKill[0]);
        INFO_HARD("Friend Static Aircraft Kill: \t" + localObject2.friendKill[8]);
        INFO_HARD("Friend Tank Kill: \t" + localObject2.friendKill[1]);
        INFO_HARD("Friend Car Kill: \t" + localObject2.friendKill[2]);
        INFO_HARD("Friend Artillery Kill: \t" + localObject2.friendKill[3]);
        INFO_HARD("Friend AAA Kill: \t" + localObject2.friendKill[4]);

        INFO_HARD("Friend Wagon Kill: \t" + localObject2.friendKill[6]);
        INFO_HARD("Friend Ship Kill: \t" + localObject2.friendKill[7]);

        INFO_HARD("Fire Bullets: \t\t" + ((NetUserStat)localObject2).bulletsFire);
        INFO_HARD("Hit Bullets: \t\t" + ((NetUserStat)localObject2).bulletsHit);
        INFO_HARD("Hit Air Bullets: \t" + ((NetUserStat)localObject2).bulletsHitAir);
        INFO_HARD("Fire Roskets: \t\t" + ((NetUserStat)localObject2).rocketsFire);
        INFO_HARD("Hit Roskets: \t\t" + ((NetUserStat)localObject2).rocketsHit);
        INFO_HARD("Fire Bombs: \t\t" + ((NetUserStat)localObject2).bombFire);
        INFO_HARD("Hit Bombs: \t\t" + ((NetUserStat)localObject2).bombHit);
      }
      INFO_HARD("-------------------------------------------------------");
    }
    else
    {
      INFO_HARD(" N       Name           Ping    Score   Army        Aircraft");
      for (i = 0; i < localArrayList.size(); i++) {
        localObject1 = new StringBuffer();
        localObject2 = (NetUser)localArrayList.get(i);
        if (isDServer((NetUser)localObject2))
          continue;
        int j = NetEnv.hosts().indexOf(localObject2);
        ((StringBuffer)localObject1).append(" ");
        if (j >= 0) ((StringBuffer)localObject1).append(j + 1); else
          ((StringBuffer)localObject1).append("0");
        while (((StringBuffer)localObject1).length() < 8) ((StringBuffer)localObject1).append(" ");

        ((StringBuffer)localObject1).append(((NetUser)localObject2).uniqueName());
        while (((StringBuffer)localObject1).length() < 24) ((StringBuffer)localObject1).append(" ");

        ((StringBuffer)localObject1).append(" ");
        ((StringBuffer)localObject1).append(((NetUser)localObject2).ping);
        while (((StringBuffer)localObject1).length() < 32) ((StringBuffer)localObject1).append(" ");

        ((StringBuffer)localObject1).append(" ");
        ((StringBuffer)localObject1).append((int)((NetUser)localObject2).curstat().score);
        while (((StringBuffer)localObject1).length() < 40) ((StringBuffer)localObject1).append(" ");

        ((StringBuffer)localObject1).append("(");
        ((StringBuffer)localObject1).append(((NetUser)localObject2).getArmy());
        ((StringBuffer)localObject1).append(")");
        ((StringBuffer)localObject1).append(Army.name(((NetUser)localObject2).getArmy()));
        while (((StringBuffer)localObject1).length() < 52) ((StringBuffer)localObject1).append(" ");

        Aircraft localAircraft = ((NetUser)localObject2).findAircraft();
        if (Actor.isValid(localAircraft)) {
          ((StringBuffer)localObject1).append(localAircraft.typedName());

          ((StringBuffer)localObject1).append(" ");
          while (((StringBuffer)localObject1).length() < 64) ((StringBuffer)localObject1).append(" ");
          ((StringBuffer)localObject1).append(Property.stringValue(localAircraft.getClass(), "keyName"));
        }

        INFO_HARD(((StringBuffer)localObject1).toString());
      }
    }

    return CmdEnv.RETURN_OK;
  }

  private String playerState(NetUserStat paramNetUserStat, boolean paramBoolean) {
    if (paramBoolean) return "Left the Game";
    if ((paramNetUserStat.curPlayerState & 0x1) != 0) return "KIA";
    if ((paramNetUserStat.curPlayerState & 0x10) != 0) return "Captured";
    if ((paramNetUserStat.curPlayerState & 0x8) != 0) return "Emergency Landed";
    if ((paramNetUserStat.curPlayerState & 0x4) != 0) return "Landed at Airfield";
    if ((paramNetUserStat.curPlayerState & 0x2) != 0) return "Hit the Silk";
    if ((paramNetUserStat.curPlayerState & 0x20) != 0) return "Selects Aircraft";
    return "In Flight";
  }

  private void fillUsers(Map paramMap, List paramList, boolean paramBoolean) {
    int i = 0;
    if ((!paramMap.containsKey("_$$")) && (!paramMap.containsKey("#")) && (!paramMap.containsKey("ARMY")))
      i = 1;
    if ((paramMap.containsKey("_$$")) && (Cmd.nargs(paramMap, "_$$") == 1) && ("*".equals(Cmd.arg(paramMap, "_$$", 0))))
      i = 1;
    if (i != 0) {
      if (paramBoolean) {
        paramList.addAll(NetUserLeft.all);
      } else {
        paramList.add(NetEnv.host());
        for (int j = 0; j < NetEnv.hosts().size(); j++)
          paramList.add(NetEnv.hosts().get(j));
      }
      return;
    }

    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int k;
    int m;
    String str1;
    int i3;
    Object localObject;
    if (paramMap.containsKey("_$$")) {
      k = Cmd.nargs(paramMap, "_$$");
      for (m = 0; m < k; m++) {
        str1 = Cmd.arg(paramMap, "_$$", m);
        if (paramBoolean) {
          for (int i1 = 0; i1 < NetUserLeft.all.size(); i1++) {
            NetUserLeft localNetUserLeft = (NetUserLeft)NetUserLeft.all.get(i1);
            if ((!str1.equals(localNetUserLeft.uniqueName)) || 
              (localHashMap.containsKey(localNetUserLeft))) continue;
            localHashMap.put(localNetUserLeft, null);
            localArrayList.add(localNetUserLeft);
          }
        }
        else
        {
          NetUser localNetUser1 = (NetUser)NetEnv.host();
          if (str1.equals(localNetUser1.uniqueName())) {
            if (!localHashMap.containsKey(localNetUser1)) {
              localHashMap.put(localNetUser1, null);
              localArrayList.add(localNetUser1);
            }
          }
          else for (i3 = 0; i3 < NetEnv.hosts().size(); i3++) {
              localNetUser1 = (NetUser)NetEnv.hosts().get(i3);
              localObject = localNetUser1.uniqueName();
              if (str1.equals(localObject)) {
                if (localHashMap.containsKey(localNetUser1)) break;
                localHashMap.put(localNetUser1, null);
                localArrayList.add(localNetUser1); break;
              }
            }

        }

      }

    }

    if (paramMap.containsKey("#")) {
      k = Cmd.nargs(paramMap, "#");
      for (m = 0; m < k; m++) {
        str1 = Cmd.arg(paramMap, "#", m);
        if ((str1.charAt(0) >= '0') && (str1.charAt(0) <= '9')) {
          int i2 = Cmd.arg(paramMap, "#", m, 1000, 0, 1000);
          if (paramBoolean) {
            i3 = i2 - 1 - NetEnv.hosts().size();
            if ((i3 >= 0) && (i3 < NetUserLeft.all.size())) {
              localObject = (NetUserLeft)NetUserLeft.all.get(i3);
              if (!localHashMap.containsKey(localObject)) {
                localHashMap.put(localObject, null);
                localArrayList.add(localObject);
              }
            }
          }
          else
          {
            NetUser localNetUser2;
            if ((i2 > 0) && (i2 <= NetEnv.hosts().size())) {
              localNetUser2 = (NetUser)NetEnv.hosts().get(i2 - 1);
              if (!localHashMap.containsKey(localNetUser2)) {
                localHashMap.put(localNetUser2, null);
                localArrayList.add(localNetUser2);
              }
            } else if (i2 == 0) {
              localNetUser2 = (NetUser)NetEnv.host();
              if (!localHashMap.containsKey(localNetUser2)) {
                localHashMap.put(localNetUser2, null);
                localArrayList.add(localNetUser2);
              }
            }
          }
        }
      }
    }

    if (paramMap.containsKey("ARMY")) {
      k = Cmd.nargs(paramMap, "ARMY");
      for (m = 0; m < k; m++) {
        int n = -1;
        String str2 = Cmd.arg(paramMap, "ARMY", m);
        if ((str2.charAt(0) >= '0') && (str2.charAt(0) <= '9')) {
          n = Cmd.arg(paramMap, "ARMY", m, 1000, 0, 1000);
          if (n >= Army.amountNet())
            continue;
        } else {
          for (n = 0; n < Army.amountNet(); n++) {
            if (Army.name(n).equals(str2))
              break;
          }
          if (n == Army.amountNet())
            continue;
        }
        if (paramBoolean) {
          for (int i4 = 0; i4 < NetUserLeft.all.size(); i4++) {
            localObject = (NetUserLeft)NetUserLeft.all.get(i4);
            if ((n != ((NetUserLeft)localObject).army) || 
              (localHashMap.containsKey(localObject))) continue;
            localHashMap.put(localObject, null);
            localArrayList.add(localObject);
          }
        }
        else
        {
          NetUser localNetUser3 = (NetUser)NetEnv.host();
          if (n == localNetUser3.getArmy()) {
            if (!localHashMap.containsKey(localNetUser3)) {
              localHashMap.put(localNetUser3, null);
              localArrayList.add(localNetUser3);
            }
          }
          else for (int i5 = 0; i5 < NetEnv.hosts().size(); i5++) {
              localNetUser3 = (NetUser)NetEnv.hosts().get(i5);
              if (n == localNetUser3.getArmy()) {
                if (localHashMap.containsKey(localNetUser3)) break;
                localHashMap.put(localNetUser3, null);
                localArrayList.add(localNetUser3); break;
              }
            }

        }

      }

    }

    paramList.addAll(localArrayList);
  }

  public CmdUser()
  {
    this.jdField_param_of_type_JavaUtilTreeMap.put("#", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("ARMY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("STAT", null);

    this.jdField_param_of_type_JavaUtilTreeMap.put("EVENTLOG", null);

    this._properties.put("NAME", "user");
    this._levelAccess = 1;
  }
}