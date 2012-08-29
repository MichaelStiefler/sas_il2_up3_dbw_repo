package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Message;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PropertyResourceBundle;

public class Regiment extends Actor
{
  public static final String prefixPath = "PaintSchemes/";
  public int diedBombers = 0;
  public int diedAircrafts = 0;
  protected String country;
  protected String branch;
  protected String shortFileName;
  protected char[] id = new char[2];
  protected String sid;
  protected String shortInfo;
  protected String info;
  protected int gruppeNumber = 1;
  protected String speech;
  private static ArrayList all = new ArrayList();
  protected static HashMapExt branchMap = new HashMapExt();

  private static HashMap firstMap = new HashMap();

  public String country()
  {
    return this.country; } 
  public String branch() { return this.branch; } 
  public String fileName() { return "PaintSchemes/" + this.shortFileName; } 
  public String fileNameTga() { return "../" + this.shortFileName + ".tga"; } 
  public String id() { return this.sid; } 
  public char[] aid() { return this.id; } 
  public int gruppeNumber() { return this.gruppeNumber; } 
  public String shortInfo() { return this.shortInfo; } 
  public String info() { return this.info == null ? this.shortInfo : this.info; } 
  public String speech() { return this.speech;
  }

  public static String getCountryFromBranch(String paramString)
  {
    if (branchMap.containsKey(paramString)) {
      return (String)branchMap.get(paramString);
    }
    return paramString;
  }
  public boolean isUserDefined() {
    return false;
  }
  public static void resetGame() {
    int i = all.size();
    for (int j = 0; j < i; j++) {
      Regiment localRegiment = (Regiment)all.get(j);
      localRegiment.diedBombers = 0;
      localRegiment.diedAircrafts = 0;
    }
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Regiment(String paramString1, String paramString2, int paramInt) {
    this.flags |= 16384;
    setArmy(paramInt);
    setName(paramString1);
    this.shortFileName = paramString2;
    try {
      PropertyResourceBundle localPropertyResourceBundle = new PropertyResourceBundle(new SFSInputStream(fileName()));

      this.country = localPropertyResourceBundle.getString("country");
      this.country = this.country.toLowerCase().intern();
      this.branch = this.country;
      if (branchMap.containsKey(this.country)) {
        this.country = ((String)branchMap.get(this.branch));
      }
      String str1 = localPropertyResourceBundle.getString("id");
      this.id[0] = str1.charAt(0);
      this.id[1] = str1.charAt(1);
      if (((this.id[0] < '0') || (this.id[0] > '9')) && ((this.id[0] < 'A') || (this.id[0] > 'Z')) && (this.id[0] != '_'))
      {
        throw new RuntimeException("Bad regiment id[0]");
      }if (((this.id[1] < '0') || (this.id[1] > '9')) && ((this.id[1] < 'A') || (this.id[1] > 'Z')) && (this.id[1] != '_'))
      {
        throw new RuntimeException("Bad regiment id[1]");
      }this.sid = new String(this.id);
      this.speech = this.country;
      try {
        this.speech = localPropertyResourceBundle.getString("speech");
        this.speech = this.speech.toLowerCase().intern();
      }
      catch (Exception localException2)
      {
      }

      this.shortInfo = I18N.regimentShort(paramString1);
      this.info = I18N.regimentInfo(paramString1);
      try {
        String str2 = localPropertyResourceBundle.getString("gruppeNumber");
        if (str2 != null) {
          try { this.gruppeNumber = Integer.parseInt(str2); } catch (Exception localException4) {
          }
          if (this.gruppeNumber < 1) this.gruppeNumber = 1;
          if (this.gruppeNumber > 5) this.gruppeNumber = 5; 
        }
      } catch (Exception localException3) {
      }
    } catch (Exception localException1) {
      System.out.println("Regiment load failed: " + localException1.getMessage());
      localException1.printStackTrace();
      destroy();
      return;
    }
    all.add(this);
  }

  public Regiment(String paramString, int paramInt) {
    this(paramString, makeShortFileName(paramString, paramInt), paramInt);
  }
  private static String makeShortFileName(String paramString, int paramInt) {
    if (paramInt < Army.amountSingle()) {
      return Army.name(paramInt) + "/" + paramString;
    }
    return paramString;
  }

  protected Regiment()
  {
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  public static List getAll() {
    return all;
  }

  public static Regiment findFirst(String paramString, int paramInt) {
    String str1 = null;
    Object localObject = null;
    if (paramString != null) {
      str1 = paramString + "_" + paramInt;
      localObject = (Regiment)firstMap.get(str1);
      if (localObject != null)
        return localObject;
      for (int i = 0; i < all.size(); i++) {
        Regiment localRegiment = (Regiment)all.get(i);
        if ((paramString.equals(localRegiment.country())) && (paramInt == localRegiment.getArmy())) {
          localObject = localRegiment;
          break;
        }
      }
    }
    if (localObject == null) {
      String str2 = "NoNe";
      switch (paramInt) { case 1:
        str2 = "r01"; break;
      case 2:
        str2 = "g01";
      }
      localObject = (Regiment)Actor.getByName(str2);
    }
    if ((localObject != null) && (paramString != null))
      firstMap.put(str1, localObject);
    return (Regiment)localObject;
  }

  public static void loadAll()
  {
    SectFile localSectFile = new SectFile("PaintSchemes/regiments.ini", 0);
    int i = localSectFile.sectionIndex("branch");
    if (i >= 0) {
      j = localSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        String str1 = localSectFile.var(i, k);
        String str2 = localSectFile.value(i, k);
        if ((str1 != null) && (str2 != null)) {
          branchMap.put(str1.toLowerCase().intern(), str2.toLowerCase().intern());
        }
      }
    }
    for (int j = 1; j < Army.amountSingle(); j++)
      loadSection(localSectFile, Army.name(j), j);
    loadSection(localSectFile, "NoNe", 0);
  }
  private static void loadSection(SectFile paramSectFile, String paramString, int paramInt) {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0) return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++)
      new Regiment(paramSectFile.var(i, k), paramInt);
  }
}