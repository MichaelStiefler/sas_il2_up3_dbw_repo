package com.maddox.il2.ai;

import com.maddox.il2.engine.Config;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.ObjIO;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;

public class UserCfg
{
  public static final String defName;
  public static final String defCallsign;
  public static final String defSurname;
  public static final String[] nameHotKeyEnvs;
  public String sId;
  private int[] krypto;
  public String name;
  public String callsign;
  public String surname;
  public int singleDifficulty;
  public int netDifficulty;
  public String placeBirth;
  public int yearBirth;
  public String netRegiment;
  public String netAirName;
  public String netPilot;
  public int netSquadron = 0;
  public int netTacticalNumber = 1;
  public boolean netNumberOn = true;

  public float coverMashineGun = 500.0F;
  public float coverCannon = 500.0F;
  public float coverRocket = 500.0F;
  public float rocketDelay = 10.0F;
  public float bombDelay = 0.0F;
  public float fuel = 100.0F;

  private HashMapExt skinMap = new HashMapExt();
  private HashMapExt weaponMap = new HashMapExt();
  private HashMapExt noseartMap = new HashMapExt();

  public String iniFileName() {
    return "users/" + this.sId + "/settings.ini";
  }

  public String getSkin(String paramString) {
    return (String)this.skinMap.get(paramString);
  }
  public String getWeapon(String paramString) {
    return (String)this.weaponMap.get(paramString);
  }
  public String getNoseart(String paramString) {
    return (String)this.noseartMap.get(paramString);
  }
  public void setSkin(String paramString1, String paramString2) {
    this.skinMap.put(paramString1, paramString2);
  }
  public void setWeapon(String paramString1, String paramString2) {
    this.weaponMap.put(paramString1, paramString2);
  }
  public void setNoseart(String paramString1, String paramString2) {
    this.noseartMap.put(paramString1, paramString2);
  }

  private void loadMap(IniFile paramIniFile, HashMapExt paramHashMapExt, String paramString) {
    paramHashMapExt.clear();
    String[] arrayOfString = paramIniFile.getVariables(paramString);
    if ((arrayOfString == null) || (arrayOfString.length == 0)) return;
    for (int i = 0; i < arrayOfString.length; i++) {
      String str = paramIniFile.get(paramString, arrayOfString[i], (String)null);
      if (str != null)
        paramHashMapExt.put(arrayOfString[i], str); 
    }
  }

  private void saveMap(IniFile paramIniFile, HashMapExt paramHashMapExt, String paramString) {
    paramIniFile.deleteSubject(paramString);
    if (paramHashMapExt.size() == 0) return;
    Map.Entry localEntry = paramHashMapExt.nextEntry(null);
    while (localEntry != null) {
      if (localEntry.getValue() != null)
        paramIniFile.set(paramString, (String)localEntry.getKey(), (String)localEntry.getValue());
      localEntry = paramHashMapExt.nextEntry(localEntry);
    }
  }

  public void loadConf() {
    IniFile localIniFile = new IniFile(iniFileName(), 0);
    for (int i = 0; i < nameHotKeyEnvs.length; i++) {
      HotKeyEnv.setCurrentEnv(nameHotKeyEnvs[i]);
      HotKeyEnv.currentEnv().all().clear();
      HotKeyEnv.fromIni(nameHotKeyEnvs[i], localIniFile, "HotKey " + nameHotKeyEnvs[i]);
    }
    this.singleDifficulty = localIniFile.get("difficulty", "single", 0);
    this.netDifficulty = localIniFile.get("difficulty", "net", 0);
    this.netRegiment = localIniFile.get("net", "regiment", (String)null);
    this.netAirName = localIniFile.get("net", "airclass", (String)null);
    this.netPilot = localIniFile.get("net", "pilot", (String)null);
    this.netSquadron = localIniFile.get("net", "squadron", 0, 0, 3);
    this.netTacticalNumber = localIniFile.get("net", "tacticalnumber", 1, 1, 99);
    this.netNumberOn = (localIniFile.get("net", "numberOn", 1, 0, 1) == 1);
    this.coverMashineGun = localIniFile.get("cover", "mashinegun", 500.0F, 100.0F, 1000.0F);
    this.coverCannon = localIniFile.get("cover", "cannon", 500.0F, 100.0F, 1000.0F);
    this.coverRocket = localIniFile.get("cover", "rocket", 500.0F, 100.0F, 1000.0F);
    this.rocketDelay = localIniFile.get("cover", "rocketdelay", 10.0F, 1.0F, 60.0F);
    this.bombDelay = localIniFile.get("cover", "bombdelay", 0.0F, 0.0F, 10.0F);
    this.fuel = localIniFile.get("cover", "fuel", 100.0F, 0.0F, 100.0F);
    loadMap(localIniFile, this.skinMap, "skin");
    loadMap(localIniFile, this.weaponMap, "weapon");
    loadMap(localIniFile, this.noseartMap, "noseart");
    this.placeBirth = localIniFile.get("dgen", "placeBirth", (String)null);
    this.yearBirth = localIniFile.get("dgen", "yearBirth", 1910, 1850, 2050);
    redirectKeysPause();
    if (Config.cur.newCloudsRender) {
      this.singleDifficulty |= 16777216;
      this.netDifficulty |= 16777216;
    } else {
      this.singleDifficulty &= -16777217;
      this.netDifficulty &= -16777217;
    }
  }

  public void saveConf() {
    IniFile localIniFile = new IniFile(iniFileName(), 1);
    for (int i = 0; i < nameHotKeyEnvs.length; i++) {
      localIniFile.deleteSubject("HotKey " + nameHotKeyEnvs[i]);
      HotKeyEnv.toIni(nameHotKeyEnvs[i], localIniFile, "HotKey " + nameHotKeyEnvs[i]);
    }
    localIniFile.deleteSubject("difficulty");
    localIniFile.set("difficulty", "single", this.singleDifficulty);
    localIniFile.set("difficulty", "net", this.netDifficulty);
    localIniFile.deleteSubject("net");
    if (this.netRegiment != null) localIniFile.set("net", "regiment", this.netRegiment);
    if (this.netAirName != null) localIniFile.set("net", "airclass", this.netAirName);
    if (this.netPilot != null) localIniFile.set("net", "pilot", this.netPilot);
    localIniFile.set("net", "squadron", this.netSquadron);
    localIniFile.set("net", "tacticalnumber", this.netTacticalNumber);
    localIniFile.set("net", "numberOn", this.netNumberOn ? "1" : "0");
    localIniFile.deleteSubject("cover");
    localIniFile.set("cover", "mashinegun", this.coverMashineGun);
    localIniFile.set("cover", "cannon", this.coverCannon);
    localIniFile.set("cover", "rocket", this.coverRocket);
    localIniFile.set("cover", "rocketdelay", this.rocketDelay);
    localIniFile.set("cover", "bombdelay", this.bombDelay);
    localIniFile.set("cover", "fuel", this.fuel);
    saveMap(localIniFile, this.skinMap, "skin");
    saveMap(localIniFile, this.weaponMap, "weapon");
    saveMap(localIniFile, this.noseartMap, "noseart");
    if (this.placeBirth != null) localIniFile.set("dgen", "placeBirth", this.placeBirth);
    localIniFile.set("dgen", "yearBirth", this.yearBirth);
    localIniFile.saveFile();
    redirectKeysPause();
  }

  private void redirectKeysPause() {
    ArrayList localArrayList = new ArrayList();
    HotKeyEnv localHotKeyEnv = HotKeyEnv.env("hotkeys");
    HashMapInt localHashMapInt = localHotKeyEnv.all();
    HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
    Object localObject1;
    while (localHashMapIntEntry != null) {
      i = localHashMapIntEntry.getKey();
      localObject1 = (String)localHashMapIntEntry.getValue();
      if ("pause".equals(localObject1))
        localArrayList.add(new Integer(i));
      localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
    }
    for (int i = 0; i < localArrayList.size(); i++) {
      localObject1 = (Integer)localArrayList.get(i);
      localHashMapInt.remove(((Integer)localObject1).intValue());
    }
    localArrayList.clear();

    localHotKeyEnv = HotKeyEnv.env("timeCompression");
    localHashMapInt = localHotKeyEnv.all();
    localHashMapIntEntry = localHashMapInt.nextEntry(null);
    Object localObject2;
    while (localHashMapIntEntry != null) {
      j = localHashMapIntEntry.getKey();
      localObject2 = (String)localHashMapIntEntry.getValue();
      if ("timeSpeedPause".equals(localObject2))
        localArrayList.add(new Integer(j));
      localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
    }

    for (int j = 0; j < localArrayList.size(); j++) {
      localObject2 = (Integer)localArrayList.get(j);
      int k = ((Integer)localObject2).intValue();
      HotKeyEnv.env("hotkeys").all().put(k, "pause");
    }
  }

  public int[] krypto() {
    if (this.krypto == null) {
      long l = Finger.Long("users/" + this.sId);
      this.krypto = new int[17];
      for (int i = 0; i < 17; i++) {
        int j = (int)(l >> 8 * (i % 8) & 0xFF);
        int k = i / 8;
        while (k > 0) {
          j <<= 2; j = (j & 0x3 | j) & 0xFF;
          k--;
        }
        if (j == 0) j = 255;
        this.krypto[i] = j;
      }
    }
    return this.krypto;
  }

  public boolean existUserDir() {
    return existUserDir(this.sId);
  }
  private boolean existUserDir(String paramString) {
    File localFile = new File(HomePath.toFileSystemName("users/" + paramString, 0));
    return localFile.isDirectory();
  }
  public boolean existUserConf() {
    File localFile = new File(HomePath.toFileSystemName("users/" + this.sId + "/settings.ini", 0));
    return localFile.exists();
  }
  private void removeDGens() {
    String str1 = "users/" + this.sId + "/campaigns.ini";
    SectFile localSectFile = new SectFile(str1, 0, false, krypto());
    int i = localSectFile.sectionIndex("list");
    if (i < 0) return;
    int j = localSectFile.vars(i);
    for (int k = 0; k < j; k++)
      try {
        Campaign localCampaign = (Campaign)ObjIO.fromString(localSectFile.value(i, k));
        if (localCampaign.isDGen()) {
          String str2 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir();
          File localFile1 = new File(HomePath.toFileSystemName(str2, 0));
          File[] arrayOfFile = localFile1.listFiles();
          if (arrayOfFile != null) {
            for (int m = 0; m < arrayOfFile.length; m++) {
              File localFile2 = arrayOfFile[m];
              String str3 = localFile2.getName();
              if ((".".equals(str3)) || ("..".equals(str3)))
                continue;
              localFile2.delete();
            }
          }
          localFile1.delete();
        }
        localCampaign.clearSavedStatics(localSectFile); } catch (Exception localException) {
      }
  }

  public void removeUserDir() {
    removeDGens();
    File localFile = new File(HomePath.toFileSystemName("users/" + this.sId, 0));
    removeTree(localFile);
  }
  public static void removeTree(File paramFile) {
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null) {
        for (int i = 0; i < arrayOfFile.length; i++) {
          if (arrayOfFile[i].isDirectory()) removeTree(arrayOfFile[i]); else
            arrayOfFile[i].delete();
        }
      }
    }
    paramFile.delete();
  }
  public void createUserDir() {
    File localFile = new File(HomePath.toFileSystemName("users/" + this.sId, 0));
    if (localFile.exists())
      removeTree(localFile);
    localFile.mkdirs();
  }
  public void createUserConf() {
    String str1 = "users/" + this.sId + "/settings.ini";
    File localFile = new File(HomePath.toFileSystemName(str1, 0));
    if (localFile.exists())
      removeTree(localFile);
    String str2 = "users/default.ini";
    IniFile localIniFile = new IniFile(str2, 0);
    localIniFile.saveFile(str1);
  }

  public void makeId() {
    String str1 = "";
    this.sId = null;
    if (this.surname.length() > 0) {
      this.sId = this.surname.toLowerCase();
      if (!Character.isDigit(this.sId.charAt(0))) {
        for (i = 0; i < this.sId.length(); i++) {
          char c = this.sId.charAt(i);
          if (!Character.isLetterOrDigit(c)) {
            this.sId = null;
            break;
          }
        }
      }
    }
    if (this.sId == null)
      this.sId = "";
    else {
      for (i = 0; i < this.sId.length(); i++)
        if (this.sId.charAt(i) >= '') {
          this.sId = "";
          break;
        }
    }
    int i = 0;
    while (true)
    {
      String str2;
      if ((i == 0) && (this.sId.length() > 0)) str2 = this.sId; else
        str2 = this.sId + i;
      if (!existUserDir(str2)) {
        this.sId = str2;
        return;
      }
      i++;
    }
  }
  public UserCfg() {
  }

  public UserCfg(String paramString1, String paramString2, String paramString3) {
    if ((paramString1 == null) || (paramString1.length() == 0)) paramString1 = " ";
    if ((paramString2 == null) || (paramString2.length() == 0)) paramString2 = " ";
    if ((paramString3 == null) || (paramString3.length() == 0)) paramString3 = " ";
    this.name = paramString1;
    this.callsign = paramString2;
    this.surname = paramString3;
    this.sId = null;
  }

  public static UserCfg loadCurrent() {
    File localFile = new File(HomePath.toFileSystemName("users/all.ini", 0));
    if (!localFile.exists())
      return createDefault();
    SectFile localSectFile = new SectFile("users/all.ini", 0);
    int i = localSectFile.sectionIndex("list");
    int j = localSectFile.sectionIndex("current");
    if ((i < 0) || (j < 0))
      return createDefault();
    int k = localSectFile.vars(i);
    if (k == 0)
      return createDefault();
    String str1 = localSectFile.var(j, 0);
    int m = 0;
    try { m = Integer.parseInt(str1); } catch (Exception localException) {
    }
    if (m >= k)
      m = k - 1;
    String str2 = localSectFile.var(i, m);
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(i, m));
    UserCfg localUserCfg = new UserCfg(UnicodeTo8bit.load(localNumberTokenizer.next(defName)), UnicodeTo8bit.load(localNumberTokenizer.next(defCallsign)), UnicodeTo8bit.load(localNumberTokenizer.next(defSurname)));

    localUserCfg.sId = str2;
    if (!localUserCfg.existUserDir())
      localUserCfg.createUserDir();
    if (!localUserCfg.existUserConf())
      localUserCfg.createUserConf();
    localUserCfg.loadConf();
    return localUserCfg;
  }

  public static UserCfg createDefault() {
    SectFile localSectFile = new SectFile();
    localSectFile.clear();
    UserCfg localUserCfg = new UserCfg(defName, defCallsign, defSurname);
    localUserCfg.makeId();
    int i = localSectFile.sectionAdd("list");
    localSectFile.lineAdd(i, localUserCfg.sId, UnicodeTo8bit.save(localUserCfg.name, true) + " " + UnicodeTo8bit.save(localUserCfg.callsign, true) + " " + UnicodeTo8bit.save(localUserCfg.surname, true));

    int j = localSectFile.sectionAdd("current");
    localSectFile.lineAdd(j, "0");
    localSectFile.saveFile("users/all.ini");
    localUserCfg.createUserDir();
    localUserCfg.createUserConf();
    localUserCfg.loadConf();
    return localUserCfg;
  }

  static
  {
    if (Config.LOCALE.equals("RU")) {
      defName = "Иван";
      defCallsign = "Ваня";
      defSurname = "Иванов";
    } else {
      defName = "John";
      defCallsign = "Mad";
      defSurname = "Doe";
    }

    nameHotKeyEnvs = new String[] { "pilot", "gunner", "aircraftView", "SnapView", "PanView", "orders", "misc", "$$$misc", "timeCompression", "move" };
  }
}