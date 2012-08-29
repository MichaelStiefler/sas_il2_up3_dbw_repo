package com.maddox.il2.game.campaign;

import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Console;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.SharedTokenizer;
import java.io.PrintStream;

public abstract class Campaign
{
  private static Class _cls = Campaign.class;
  public Awards _awards;
  public int _nawards;
  public String _country;
  public String _missionsDir;
  public int _difficulty;
  public int _completeMissions;
  public int _score;
  public int _scoreAward;
  public int _scoreRank;
  public int _rank;
  public int _aircraftLost;
  public int _enemyAirDestroyed;
  public int[] _enemyGroundDestroyed;
  public int _friendDestroyed;
  public String _lastMissionMapName;
  public String _epilogueTrack;
  private int amountMissions = 0;
  private int newScoreRank;
  private SectFile missionsSectFile;

  public int army()
  {
    return 0;
  }

  public String branch()
  {
    return this._country;
  }
  public String country() {
    return Regiment.getCountryFromBranch(this._country);
  }

  public String missionsDir()
  {
    return this._missionsDir;
  }

  public int difficulty()
  {
    return this._difficulty;
  }

  public int completeMissions()
  {
    return this._completeMissions;
  }

  public int score()
  {
    return this._score;
  }

  public int scoreRank()
  {
    return this._scoreRank;
  }

  public int rank()
  {
    return this._rank;
  }

  public int aircraftLost()
  {
    return this._aircraftLost;
  }

  public int enemyAirDestroyed()
  {
    return this._enemyAirDestroyed;
  }

  public int[] enemyGroundDestroyed()
  {
    return this._enemyGroundDestroyed;
  }

  public int friendDestroyed()
  {
    return this._friendDestroyed;
  }

  public String lastMissionMapName() {
    return this._lastMissionMapName; } 
  public void setLastMissionMapName(String paramString) { this._lastMissionMapName = paramString; }

  public String epilogueTrack() {
    return this._epilogueTrack;
  }
  public boolean isComplete() {
    if (this.amountMissions == 0) {
      SectFile localSectFile = missionsSectFile();
      int i = localSectFile.sectionIndex("list");
      if (i >= 0) {
        int j = localSectFile.vars(i);
        for (int k = 0; k < j; k++) {
          String str = localSectFile.var(i, k);
          if (!lineIsIntroName(str))
            this.amountMissions += 1;
        }
      }
    }
    return this.amountMissions == completeMissions();
  }

  public int awards(int paramInt)
  {
    int i = paramInt;
    if (i < this._scoreAward) i = this._scoreAward;
    return this._awards.count(i);
  }

  public int[] getAwards(int paramInt)
  {
    int i = paramInt;
    if (i < this._scoreAward) i = this._scoreAward;
    return this._awards.index(i);
  }
  public void incAircraftLost() {
    this._aircraftLost += 1;
  }
  protected int rankStep() { return 1200; }

  public int newRank(int paramInt) {
    if (paramInt < this._scoreRank + rankStep())
      return this._rank;
    int i = this._rank + (paramInt - this._scoreRank) / rankStep();
    if (i >= 6) {
      i = 6;
      this.newScoreRank = paramInt;
    } else {
      this.newScoreRank = ((i - this._rank) * rankStep() + this._scoreRank);
    }
    return i;
  }

  public void currentMissionComplete(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    this._completeMissions += 1;
    this._score = paramInt1;
    this._enemyAirDestroyed = paramInt2;
    this._enemyGroundDestroyed = paramArrayOfInt;
    this._friendDestroyed = paramInt3;
    int i = newRank(paramInt1);
    if (i > this._rank) {
      this._rank = i;
      this._scoreRank = this.newScoreRank;
    }
    if (paramInt1 > this._scoreAward)
      this._scoreAward = paramInt1;
  }

  public void clearSavedStatics(SectFile paramSectFile) {
    String str = branch() + missionsDir();
    int i = paramSectFile.sectionIndex(str + "Bridge");
    if (i >= 0)
      paramSectFile.sectionRemove(i);
    i = paramSectFile.sectionIndex(str + "House");
    if (i >= 0)
      paramSectFile.sectionRemove(i);
  }

  public void saveStatics(SectFile paramSectFile) {
    String str1 = branch() + missionsDir();
    clearSavedStatics(paramSectFile);
    if (Mission.cur() == null) return;
    if (Mission.cur().sectFile() == null) return;
    int i = paramSectFile.sectionAdd(str1 + "Bridge");
    World.cur().statics.saveStateBridges(paramSectFile, i);
    i = paramSectFile.sectionAdd(str1 + "House");
    World.cur().statics.saveStateHouses(paramSectFile, i);

    String str2 = Mission.cur().sectFile().get("MAIN", "MAP", (String)null);
    setLastMissionMapName(str2);
  }

  public void copySavedStatics(SectFile paramSectFile1, SectFile paramSectFile2) {
    String str = branch() + missionsDir();
    copySection(paramSectFile1, str + "Bridge", paramSectFile2, "AddBridge");
    copySection(paramSectFile1, str + "House", paramSectFile2, "AddHouse");
  }
  private void copySection(SectFile paramSectFile1, String paramString1, SectFile paramSectFile2, String paramString2) {
    int i = paramSectFile2.sectionIndex(paramString2);
    if (i >= 0) paramSectFile2.sectionRemove(i);
    int j = paramSectFile1.sectionIndex(paramString1);
    if (j < 0) return;
    i = paramSectFile2.sectionAdd(paramString2);
    int k = paramSectFile1.vars(j);
    for (int m = 0; m < k; m++)
      paramSectFile2.lineAdd(i, paramSectFile1.line(j, m));
  }

  private void copySavedStatics(SectFile paramSectFile) {
    String str1 = paramSectFile.get("MAIN", "MAP", (String)null);
    if (str1 == null) return;
    if (!str1.equals(lastMissionMapName())) return;

    String str2 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
    SectFile localSectFile = new SectFile(str2, 0, false, World.cur().userCfg.krypto());
    copySavedStatics(localSectFile, paramSectFile);
  }

  public SectFile missionsSectFile()
  {
    if (this.missionsSectFile != null)
      return this.missionsSectFile;
    this.missionsSectFile = new SectFile("missions/campaign/" + branch() + "/" + missionsDir() + "/campaign.ini", 0);
    return this.missionsSectFile;
  }

  public boolean isDGen()
  {
    SectFile localSectFile = missionsSectFile();
    String str = localSectFile.get("Main", "ExecGenerator", (String)null);
    if (str == null)
      return false;
    return "dgen.exe".equalsIgnoreCase(str);
  }

  public void doExternalGenerator() {
    SectFile localSectFile = missionsSectFile();
    String str1 = localSectFile.get("Main", "ExecGenerator", (String)null);
    if (str1 == null) {
      return;
    }

    this.amountMissions = 0;
    this.missionsSectFile = null;
    try {
      RTSConf.cur.console.flush();
      EventLog.close();
    } catch (Throwable localThrowable1) {
      System.out.println(localThrowable1.getMessage());
      localThrowable1.printStackTrace();
    }
    try
    {
      String str2 = "users/" + World.cur().userCfg.sId + "/";
      String str3 = "missions/campaign/" + branch() + "/" + missionsDir() + "/";
      String str4 = str2 + " " + str3 + " " + completeMissions() + " " + difficulty() + " " + score() + " " + rank();

      Runtime localRuntime = Runtime.getRuntime();

      Process localProcess = localRuntime.exec(str1 + " " + str4);

      localProcess.waitFor();
    }
    catch (Throwable localThrowable2) {
      System.out.println(localThrowable2.getMessage());
      localThrowable2.printStackTrace();
    }
  }

  public String nextIntro() {
    SectFile localSectFile = missionsSectFile();
    int i = localSectFile.sectionIndex("list");
    if (i >= 0) {
      int j = localSectFile.vars(i);
      int k = 0;
      for (int m = 0; m < j; m++) {
        String str1 = localSectFile.var(i, m);
        if (lineIsIntroName(str1)) {
          if (k == this._completeMissions) {
            SharedTokenizer.set(localSectFile.line(i, m));
            String str2 = SharedTokenizer.nextToken();
            return SharedTokenizer.nextToken();
          }
        }
        else k++;
      }
    }

    return null;
  }

  public SectFile nextMission()
  {
    if (isComplete()) return null;
    SectFile localSectFile1 = missionsSectFile();
    int i = localSectFile1.sectionIndex("list");

    String str1 = null;
    int j = localSectFile1.vars(i);
    int k = 0;
    for (int m = 0; m < j; m++) {
      str2 = localSectFile1.var(i, m);
      if (!lineIsIntroName(str2)) {
        if (k == this._completeMissions) {
          str1 = localSectFile1.line(i, m);
          break;
        }
        k++;
      }
    }

    SharedTokenizer.set(str1);
    j = SharedTokenizer.countTokens();
    m = (int)Math.round((j - 1) * Math.random());
    String str2 = SharedTokenizer.nextToken();
    while (m-- > 0)
      str2 = SharedTokenizer.nextToken();
    String str3 = "missions/campaign/" + branch() + "/" + missionsDir() + "/" + str2;
    SectFile localSectFile2 = new SectFile(str3, 0);

    if ((!isDGen()) && 
      (!prepareMission(localSectFile2)))
      return null;
    copySavedStatics(localSectFile2);

    return localSectFile2;
  }

  private boolean lineIsIntroName(String paramString) {
    if (paramString == null) return false;
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      int k = paramString.charAt(j);
      if (k == 42) return true;
      if (k > 32) return false;
    }
    return false;
  }

  protected boolean prepareMission(SectFile paramSectFile)
  {
    String str1 = paramSectFile.get("MAIN", "player", (String)null);
    String str2 = str1.substring(0, str1.length() - 1);
    int[] arrayOfInt = { 0, 0, 0, 0 };
    int i = paramSectFile.sectionIndex("Wing");
    int j = paramSectFile.vars(i);
    int i1;
    for (int k = 0; k < j; k++) {
      String str3 = paramSectFile.var(i, k);
      if (str3.startsWith(str2)) {
        n = str3.charAt(str3.length() - 1) - '0';
        i1 = paramSectFile.get(str3, "Planes", 0, 0, 4);
        arrayOfInt[n] = i1;
      }
    }

    k = -1;
    int m = 0;
    switch (this._rank) {
    case 6:
      for (n = 0; n < 4; n++) {
        if (arrayOfInt[n] != 0) {
          k = n;
          m = 0;
          break;
        }
      }
      break;
    case 5:
      n = 0;
      for (i1 = 0; i1 < 4; i1++) {
        if (arrayOfInt[i1] != 0) {
          if (n == 0) {
            n++;
          } else {
            k = i1;
            m = 0;
            break;
          }
        }
      }
      if (k >= 0)
        break;
    case 4:
      for (n = 3; n >= 0; n--) {
        if (arrayOfInt[n] != 0) {
          k = n;
          m = 0;
          break;
        }
      }
      break;
    case 3:
      for (n = 0; n < 4; n++) {
        if (arrayOfInt[n] > 1) {
          k = n;
          m = 1;
          break;
        }
      }
      if (k >= 0) break;
    case 2:
      for (n = 3; n >= 0; n--) {
        if (arrayOfInt[n] > 1) {
          k = n;
          m = 1;
          break;
        }
      }
      if (k >= 0) break;
      for (n = 3; n >= 0; n--) {
        if (arrayOfInt[n] != 0) {
          k = n;
          m = 0;
          break;
        }
      }
      break;
    case 1:
      for (n = 0; n < 4; n++) {
        if (arrayOfInt[n] > 2) {
          k = n;
          m = arrayOfInt[n] - 1;
          break;
        }
      }
      if (k >= 0) break;
    case 0:
      for (n = 3; n >= 0; n--) {
        if (arrayOfInt[n] != 0) {
          k = n;
          m = arrayOfInt[n] - 1;
          break;
        }
      }

    }

    for (int n = 0; n < 4; n++) {
      if (arrayOfInt[n] > 0) {
        String str4 = paramSectFile.get(str2 + n, "Class", (String)null);
        try {
          Class localClass = ObjIO.classForName(str4);
          if (!Property.containsValue(localClass, "cockpitClass"))
            arrayOfInt[n] = 0;
        } catch (Exception localException) {
          arrayOfInt[n] = 0;
        }
      }

    }

    if (arrayOfInt[k] <= 0) {
      k++;
      m = -1;
      while (k < 4) {
        if (arrayOfInt[k] > 0) {
          m = 0;
          break;
        }
        k++;
      }
      if (m < 0) {
        while (k > 0) {
          k--;
          if (arrayOfInt[k] > 0) {
            m = arrayOfInt[k] - 1;
          }
        }
      }

    }

    if (m >= 0) {
      paramSectFile.set("Main", "player", str2 + k);
      paramSectFile.set("Main", "playerNum", m);
      return true;
    }

    return false;
  }

  public void init(Awards paramAwards, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this._awards = paramAwards;
    this._nawards = -1;
    this._country = paramString1;
    this._missionsDir = paramString2;
    this._difficulty = paramInt1;
    this._rank = paramInt2;
  }

  static
  {
    ObjIO.field(_cls, "_awards");

    ObjIO.field(_cls, "_nawards");

    ObjIO.field(_cls, "_country");

    ObjIO.field(_cls, "_missionsDir");

    ObjIO.field(_cls, "_difficulty");

    ObjIO.field(_cls, "_completeMissions");

    ObjIO.field(_cls, "_score");

    ObjIO.field(_cls, "_scoreAward");

    ObjIO.field(_cls, "_scoreRank");

    ObjIO.field(_cls, "_rank");

    ObjIO.field(_cls, "_aircraftLost");

    ObjIO.field(_cls, "_enemyAirDestroyed");

    ObjIO.field(_cls, "_enemyGroundDestroyed");

    ObjIO.field(_cls, "_friendDestroyed");

    ObjIO.field(_cls, "_lastMissionMapName");

    ObjIO.field(_cls, "_epilogueTrack");
  }
}