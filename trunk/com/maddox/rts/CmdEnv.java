package com.maddox.rts;

import com.maddox.rts.cmd.CmdLoad;
import com.maddox.util.QuoteTokenizer;
import com.maddox.util.StrMath;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CmdEnv
{
  public static final String AREA_PREFIX = "_$$$";
  public static final String AREA_COMMAND = "_$$$command";
  public static final String AREA_ALIAS = "_$$$alias";
  public static final String AREA_HISTORY = "_$$$history";
  public static final int LEVEL_DEVELOPER = 0;
  public static final int LEVEL_USER = 1;
  public static final int LEVEL_GUEST = 2;
  public static final String FAST = "fast";
  public static final String ALIAS = "alias";
  public static final String HISTORY = "history";
  public static final String ECHO = "echo";
  public static final String ON = "on";
  public static final String OFF = "off";
  public static final Object RETURN_OK = "OK";
  public String curExecCmdString;
  private TreeMap _env;
  private CmdEnv _parent;
  private int _curNumCmd;
  private boolean _bUseHistory;
  private int _levelAccess;

  public CmdEnv parent()
  {
    return this._parent;
  }

  public Object invoke(String paramString1, String paramString2)
  {
    Cmd localCmd = null;
    try {
      Class localClass = Class.forName(paramString1);
      localCmd = (Cmd)localClass.newInstance();
    } catch (Exception localException1) {
      return null;
    }
    if (noAccess(localCmd))
      return null;
    if (setAtom("_$$$command", "_$$$$$", localCmd)) {
      this._bUseHistory = false;
      Object localObject = null;
      try {
        localObject = exec("_$$$$$ " + paramString2);
      } catch (Exception localException2) {
      }
      this._bUseHistory = true;
      delAtom("_$$$command", "_$$$$$");
      return localObject;
    }
    return null;
  }

  public static boolean validAtomName(String paramString)
  {
    return (paramString != null) && (!paramString.startsWith("_$$$"));
  }

  public boolean flag(String paramString)
  {
    Object localObject = atom(null, paramString);
    if ((localObject == null) || (!(localObject instanceof String)))
      return false;
    return "on".equals(localObject);
  }

  public void setFlag(String paramString, boolean paramBoolean)
  {
    setAtom(null, paramString, paramBoolean ? "on" : "off");
  }

  public boolean delAtom(String paramString)
  {
    int i = paramString.indexOf('^');
    if ((i > 0) && (i < paramString.length())) {
      return delAtom(paramString.substring(0, i), paramString.substring(i + 1));
    }
    return delAtom(null, paramString);
  }

  public boolean setAtom(String paramString, Object paramObject)
  {
    int i = paramString.indexOf('^');
    if ((i > 0) && (i < paramString.length())) {
      return setAtom(paramString.substring(0, i), paramString.substring(i + 1), paramObject);
    }
    return setAtom(null, paramString, paramObject);
  }

  public Object atom(String paramString)
  {
    int i = paramString.indexOf('^');
    if ((i > 0) && (i < paramString.length())) {
      return atom(paramString.substring(0, i), paramString.substring(i + 1));
    }
    return atom(null, paramString);
  }

  public boolean existAtom(String paramString, boolean paramBoolean)
  {
    int i = paramString.indexOf('^');
    if ((i > 0) && (i < paramString.length())) {
      return existAtom(paramString.substring(0, i), paramString.substring(i + 1), paramBoolean);
    }
    return existAtom(null, paramString, paramBoolean);
  }

  public boolean delAtom(String paramString1, String paramString2)
  {
    Object localObject1;
    if (paramString1 == null) {
      localObject1 = this._env;
    } else {
      Object localObject2 = this._env.get(paramString1);
      if ((localObject2 instanceof CmdArea))
        return ((CmdArea)localObject2).delAtom(paramString2);
      localObject1 = (Map)localObject2;
    }
    if ((localObject1 != null) && (validAtomName(paramString2))) {
      ((Map)localObject1).remove(paramString2);
      return true;
    }

    return false;
  }

  public boolean setAtom(String paramString1, String paramString2, Object paramObject)
  {
    Object localObject1;
    if (paramString1 == null) {
      localObject1 = this._env;
    } else {
      Object localObject2 = this._env.get(paramString1);
      if ((localObject2 instanceof CmdArea)) {
        return ((CmdArea)localObject2).setAtom(paramString2, paramObject);
      }
      localObject1 = (Map)localObject2;
      if ((localObject1 == null) && (
        ("_$$$command".equals(paramString1)) || ("_$$$alias".equals(paramString1)))) {
        localObject1 = new TreeMap();
        this._env.put(paramString1, localObject1);
      }
    }

    if ((localObject1 != null) && (validAtomName(paramString2))) {
      ((Map)localObject1).put(paramString2, paramObject);
      return true;
    }

    return false;
  }

  public Object atom(String paramString1, String paramString2)
  {
    Object localObject1 = null;
    if (paramString1 == null) { localObject1 = this._env;
    } else {
      localObject2 = this._env.get(paramString1);
      if ((localObject2 instanceof CmdArea)) {
        Object localObject3 = ((CmdArea)localObject2).atom(paramString2);
        if (localObject3 != null)
          return localObject3;
      } else {
        localObject1 = (Map)localObject2;
      }
    }
    if ((localObject1 != null) && (validAtomName(paramString2))) {
      localObject2 = ((Map)localObject1).get(paramString2);
      if (localObject2 != null)
        return localObject2;
    }
    Object localObject2 = parent();
    if (localObject2 != null) {
      return ((CmdEnv)localObject2).atom(paramString1, paramString2);
    }
    return null;
  }

  public boolean existAtom(String paramString1, String paramString2, boolean paramBoolean)
  {
    Object localObject1 = null;
    Object localObject2;
    if (paramString1 == null) { localObject1 = this._env;
    } else {
      localObject2 = this._env.get(paramString1);
      if ((localObject2 instanceof CmdArea)) {
        if (((CmdArea)localObject2).existAtom(paramString2, paramBoolean))
          return true;
      }
      else localObject1 = (Map)localObject2;
    }

    if ((localObject1 != null) && (validAtomName(paramString2)) && 
      (((Map)localObject1).containsKey(paramString2))) {
      return true;
    }
    if (paramBoolean) {
      localObject2 = parent();
      if (localObject2 != null) {
        return ((CmdEnv)localObject2).existAtom(paramString1, paramString2, paramBoolean);
      }
      return false;
    }

    return false;
  }

  public void setCommand(Cmd paramCmd, String paramString1, String paramString2)
  {
    Map localMap = paramCmd.properties();
    localMap.put("NAME", paramString1);
    localMap.put("HELP", paramString2);
    setAtom("_$$$command", paramString1, paramCmd);
  }

  public String metaExpand(String paramString)
  {
    String str1 = paramString;
    if (str1.length() == 0) return paramString;
    if ((str1.charAt(0) != '$') && (str1.indexOf(" $") == -1))
      return str1;
    StringBuffer localStringBuffer = new StringBuffer();
    QuoteTokenizer localQuoteTokenizer = null;

    int i = 1;
    int j = 100;
    Object localObject;
    int m;
    while (i != 0) {
      if (j-- == 0) {
        if (Cmd.ERR_HARD)
          System.err.println("Command cicled in command: " + paramString);
        return null;
      }
      i = 0;
      if ((str1.length() <= 0) || (
        (str1.charAt(0) != '$') && (str1.indexOf(" $") == -1))) continue;
      if (localStringBuffer.length() > 0)
        localStringBuffer = new StringBuffer();
      localQuoteTokenizer = new QuoteTokenizer(str1);
      while (localQuoteTokenizer.hasMoreTokens()) {
        String str2 = localQuoteTokenizer.nextToken();
        localObject = null;
        if ((str2.length() > 1) && (str2.charAt(0) == '$')) {
          m = str2.charAt(1);
          switch (m) {
          case 36:
          case 38:
            break;
          default:
            str2 = str2.substring(1);
            if (!existAtom(str2, true)) {
              if (Cmd.ERR_SOFT)
                System.err.println("Atom: " + str2 + " not found - ignored");
              str2 = null;
            } else {
              localObject = atom(str2);
              if (localObject != null) {
                str2 = localObject.toString();
                i = 1;
              } else {
                str2 = null;
              }
            }
          }
        }

        if (str2 != null) {
          if (localStringBuffer.length() > 0) localStringBuffer.append(' ');
          if (localObject == null)
            localStringBuffer.append(QuoteTokenizer.toToken(str2));
          else
            localStringBuffer.append(str2);
        }
      }
      str1 = localStringBuffer.toString();
    }

    if ((str1.length() > 0) && (
      (str1.charAt(0) == '$') || (str1.indexOf(" $") != -1))) {
      if (localStringBuffer.length() > 0)
        localStringBuffer = new StringBuffer();
      localQuoteTokenizer = new QuoteTokenizer(str1);
      int k = 0;
      while (localQuoteTokenizer.hasMoreTokens()) {
        localObject = localQuoteTokenizer.nextToken();
        if ((((String)localObject).length() > 1) && (((String)localObject).charAt(0) == '$')) {
          m = ((String)localObject).charAt(1);
          switch (m) {
          case 38:
            k = 1;
            localObject = null;
            break;
          case 36:
            localObject = ((String)localObject).substring(1);
            break;
          }

        }

        if (localObject != null) {
          if ((k == 0) && (localStringBuffer.length() > 0)) localStringBuffer.append(' ');
          localStringBuffer.append(QuoteTokenizer.toToken((String)localObject));
          k = 0;
        }
      }
      str1 = localStringBuffer.toString();
    }

    return (String)str1;
  }

  public Object exec(String paramString)
  {
    this.curExecCmdString = paramString;
    try {
      boolean bool1 = flag("fast");
      boolean bool2 = false;

      if (!bool1) {
        bool2 = flag("alias");
        if (this._bUseHistory) {
          paramString = historyEntry(paramString);
        }
      }
      QuoteTokenizer localQuoteTokenizer = null;
      String str = null;

      Object localObject2 = metaExpand(paramString);
      if (localObject2 == null)
        return null;
      Object localObject3;
      if (bool2)
      {
        for (int i = 0; i < 10; i++) {
          localQuoteTokenizer = new QuoteTokenizer((String)localObject2);
          if (localQuoteTokenizer.hasMoreTokens()) {
            str = localQuoteTokenizer.nextToken();
            localObject3 = (String)atom("_$$$alias", str);
            if (localObject3 == null)
              break;
            if (localQuoteTokenizer.hasMoreTokens())
              localObject2 = (String)localObject3 + ' ' + localQuoteTokenizer.getGap();
            else
              localObject2 = localObject3;
          } else {
            return null;
          }
        }

        if (i == 10) {
          if (Cmd.ERR_HARD)
            System.err.println("Command cicled alias in command: " + paramString);
          return null;
        }
      } else {
        localQuoteTokenizer = new QuoteTokenizer((String)localObject2);
        if (localQuoteTokenizer.hasMoreTokens())
          str = localQuoteTokenizer.nextToken();
        else {
          return null;
        }
      }

      Cmd localCmd = (Cmd)atom("_$$$command", str);
      if (localCmd == null) {
        if (Cmd.ERR_HARD)
          System.err.println("Command not found: " + paramString);
        setResult(null);
        return null;
      }
      if (noAccess(localCmd)) {
        if (Cmd.ERR_HARD) {
          System.err.println("Command not found: " + paramString);
        }
        setResult(null);
        return null;
      }
      Object localObject1;
      if (localCmd.isRawFormat()) {
        if (localQuoteTokenizer.hasMoreTokens())
          localObject1 = localCmd.exec(this, localQuoteTokenizer.getGap());
        else
          localObject1 = localCmd.exec(this, "");
      } else {
        Map localMap = (Map)localCmd.properties().get("PARAMS");
        localObject3 = new HashMap();
        Object localObject4 = null;
        Object localObject5;
        Object localObject6;
        if (localQuoteTokenizer.hasMoreTokens()) {
          localObject2 = localQuoteTokenizer.getGap();
          localObject5 = null;
          localQuoteTokenizer = new QuoteTokenizer((String)localObject2);
          while (localQuoteTokenizer.hasMoreTokens()) {
            localObject6 = localQuoteTokenizer.nextToken();
            if ((localMap != null) && (localMap.containsKey(localObject6))) {
              if (localObject4 != null) {
                if (localObject5 == null) localObject5 = "_$$";
                ((HashMap)localObject3).put(localObject5, localObject4);
              } else if (localObject5 != null) {
                ((HashMap)localObject3).put(localObject5, localObject4);
              }
              localObject5 = localObject6;
              localObject4 = (ArrayList)((HashMap)localObject3).get(localObject5);
            } else {
              if (localObject4 == null)
                localObject4 = new ArrayList();
              ((ArrayList)localObject4).add(localObject6);
            }
          }
          if (localObject4 != null) {
            if (localObject5 == null) localObject5 = "_$$";
            ((HashMap)localObject3).put(localObject5, localObject4);
          } else if (localObject5 != null) {
            ((HashMap)localObject3).put(localObject5, localObject4);
          }
        }

        if (localMap != null) {
          localObject5 = localMap.keySet();
          localObject6 = ((Set)localObject5).iterator();
          while (((Iterator)localObject6).hasNext()) {
            Object localObject7 = ((Iterator)localObject6).next();
            if (!((HashMap)localObject3).containsKey(localObject7)) {
              Object localObject8 = localMap.get(localObject7);
              if (localObject8 != null) {
                localObject4 = new ArrayList();
                localQuoteTokenizer = new QuoteTokenizer((String)localObject8);
                while (localQuoteTokenizer.hasMoreTokens())
                  ((ArrayList)localObject4).add(localQuoteTokenizer.nextToken());
                ((HashMap)localObject3).put(localObject7, localObject4);
              }
            }
          }
        }

        localObject1 = localCmd.exec(this, (Map)localObject3);
      }

      setResult(localObject1);
      return localObject1;
    } catch (Exception localException) {
      localException.printStackTrace();

      setResult(null);
    }return null;
  }

  private void setResult(Object paramObject) {
    setAtom(null, "_$$2", atom(null, "_$$1"));
    setAtom(null, "_$$1", atom(null, "_$$0"));
    setAtom(null, "_$$0", paramObject);
  }

  private String historyEntry(String paramString) {
    String str1 = (String)atom(null, "history");
    TreeMap localTreeMap = (TreeMap)this._env.get("_$$$history");
    if (str1 == null) {
      if (localTreeMap != null)
        this._env.remove("_$$$history");
      return paramString;
    }

    int i = Integer.parseInt(str1);
    if (i <= 0) {
      if (localTreeMap != null) {
        while (localTreeMap.size() > i)
          localTreeMap.remove(localTreeMap.firstKey());
      }
      return paramString;
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramString;
    }
    Object localObject = null;
    if (localTreeMap == null) {
      localTreeMap = new TreeMap();
      this._env.put("_$$$history", localTreeMap);
    } else if (paramString.charAt(0) == '!') {
      if (paramString.charAt(1) == '!') {
        localObject = (String)localTreeMap.get(localTreeMap.lastKey());
      } else {
        String str2 = paramString.substring(1);
        try {
          Integer localInteger = Integer.valueOf(str2);
          localObject = (String)localTreeMap.get(localInteger);
        }
        catch (NumberFormatException localNumberFormatException) {
          Set localSet = localTreeMap.keySet();
          Iterator localIterator = localSet.iterator();
          while (localIterator.hasNext()) {
            String str3 = (String)localTreeMap.get(localIterator.next());
            QuoteTokenizer localQuoteTokenizer = new QuoteTokenizer(str3);
            if (StrMath.simple(str2, localQuoteTokenizer.nextToken()))
              localObject = str3;
          }
        } catch (Exception localException) {
        }
      }
    }
    this._curNumCmd += 1;
    if (localObject != null)
      paramString = (String)localObject;
    localTreeMap.put(new Integer(this._curNumCmd), paramString);
    while (localTreeMap.size() > i) {
      localTreeMap.remove(localTreeMap.firstKey());
    }
    return (String)paramString;
  }

  public int curNumCmd()
  {
    return this._curNumCmd;
  }

  public int levelAccess()
  {
    return this._levelAccess;
  }

  public void setLevelAccess(int paramInt)
  {
    this._levelAccess = paramInt;
  }
  public boolean noAccess(CmdEnv paramCmdEnv) {
    return this._levelAccess > paramCmdEnv._levelAccess;
  }

  public boolean noAccess(int paramInt)
  {
    return this._levelAccess > paramInt;
  }

  public boolean noAccess(Cmd paramCmd)
  {
    return this._levelAccess > paramCmd.levelAccess();
  }

  public Map area()
  {
    return this._env;
  }

  public static CmdEnv top()
  {
    return RTSConf.cur.cmdEnv;
  }

  public CmdEnv(CmdEnv paramCmdEnv)
  {
    this._env = new TreeMap();
    this._parent = paramCmdEnv;
    this._curNumCmd = 0;
    this._bUseHistory = true;
    this._levelAccess = paramCmdEnv.levelAccess();
  }

  protected CmdEnv()
  {
    this._env = new TreeMap();
    this._parent = null;
    this._env.put("_$$$command", new TreeMap());
    this._env.put("_$$$alias", new TreeMap());
    this._env.put("_$$$history", new TreeMap());

    setFlag("fast", false);
    setFlag("echo", true);
    setFlag("alias", true);
    setAtom(null, "history", "0");
    setAtom("_$$$command", "CmdLoad", new CmdLoad());
    this._curNumCmd = 0;
    this._bUseHistory = true;
    this._levelAccess = 0;
  }

  static
  {
    RTS.loadNative();
  }
}