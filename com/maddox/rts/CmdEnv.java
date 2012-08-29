// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdEnv.java

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

// Referenced classes of package com.maddox.rts:
//            Cmd, CmdArea, RTSConf, RTS

public class CmdEnv
{

    public com.maddox.rts.CmdEnv parent()
    {
        return _parent;
    }

    public java.lang.Object invoke(java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.Cmd cmd = null;
        try
        {
            java.lang.Class class1 = java.lang.Class.forName(s);
            cmd = (com.maddox.rts.Cmd)class1.newInstance();
        }
        catch(java.lang.Exception exception)
        {
            return null;
        }
        if(noAccess(cmd))
            return null;
        if(setAtom("_$$$command", "_$$$$$", cmd))
        {
            _bUseHistory = false;
            java.lang.Object obj = null;
            try
            {
                obj = exec("_$$$$$ " + s1);
            }
            catch(java.lang.Exception exception1) { }
            _bUseHistory = true;
            delAtom("_$$$command", "_$$$$$");
            return obj;
        } else
        {
            return null;
        }
    }

    public static boolean validAtomName(java.lang.String s)
    {
        return s != null && !s.startsWith("_$$$");
    }

    public boolean flag(java.lang.String s)
    {
        java.lang.Object obj = atom(null, s);
        if(obj == null || !(obj instanceof java.lang.String))
            return false;
        else
            return "on".equals(obj);
    }

    public void setFlag(java.lang.String s, boolean flag1)
    {
        setAtom(null, s, flag1 ? "on" : "off");
    }

    public boolean delAtom(java.lang.String s)
    {
        int i = s.indexOf('^');
        if(i > 0 && i < s.length())
            return delAtom(s.substring(0, i), s.substring(i + 1));
        else
            return delAtom(null, s);
    }

    public boolean setAtom(java.lang.String s, java.lang.Object obj)
    {
        int i = s.indexOf('^');
        if(i > 0 && i < s.length())
            return setAtom(s.substring(0, i), s.substring(i + 1), obj);
        else
            return setAtom(null, s, obj);
    }

    public java.lang.Object atom(java.lang.String s)
    {
        int i = s.indexOf('^');
        if(i > 0 && i < s.length())
            return atom(s.substring(0, i), s.substring(i + 1));
        else
            return atom(null, s);
    }

    public boolean existAtom(java.lang.String s, boolean flag1)
    {
        int i = s.indexOf('^');
        if(i > 0 && i < s.length())
            return existAtom(s.substring(0, i), s.substring(i + 1), flag1);
        else
            return existAtom(null, s, flag1);
    }

    public boolean delAtom(java.lang.String s, java.lang.String s1)
    {
        java.lang.Object obj;
        if(s == null)
        {
            obj = _env;
        } else
        {
            java.lang.Object obj1 = _env.get(s);
            if(obj1 instanceof com.maddox.rts.CmdArea)
                return ((com.maddox.rts.CmdArea)obj1).delAtom(s1);
            obj = (java.util.Map)obj1;
        }
        if(obj != null && com.maddox.rts.CmdEnv.validAtomName(s1))
        {
            ((java.util.Map) (obj)).remove(s1);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setAtom(java.lang.String s, java.lang.String s1, java.lang.Object obj)
    {
        java.lang.Object obj1;
        if(s == null)
        {
            obj1 = _env;
        } else
        {
            java.lang.Object obj2 = _env.get(s);
            if(obj2 instanceof com.maddox.rts.CmdArea)
                return ((com.maddox.rts.CmdArea)obj2).setAtom(s1, obj);
            obj1 = (java.util.Map)obj2;
            if(obj1 == null && ("_$$$command".equals(s) || "_$$$alias".equals(s)))
            {
                obj1 = new TreeMap();
                _env.put(s, obj1);
            }
        }
        if(obj1 != null && com.maddox.rts.CmdEnv.validAtomName(s1))
        {
            ((java.util.Map) (obj1)).put(s1, obj);
            return true;
        } else
        {
            return false;
        }
    }

    public java.lang.Object atom(java.lang.String s, java.lang.String s1)
    {
        java.lang.Object obj = null;
        if(s == null)
        {
            obj = _env;
        } else
        {
            java.lang.Object obj1 = _env.get(s);
            if(obj1 instanceof com.maddox.rts.CmdArea)
            {
                java.lang.Object obj3 = ((com.maddox.rts.CmdArea)obj1).atom(s1);
                if(obj3 != null)
                    return obj3;
            } else
            {
                obj = (java.util.Map)obj1;
            }
        }
        if(obj != null && com.maddox.rts.CmdEnv.validAtomName(s1))
        {
            java.lang.Object obj2 = ((java.util.Map) (obj)).get(s1);
            if(obj2 != null)
                return obj2;
        }
        com.maddox.rts.CmdEnv cmdenv = parent();
        if(cmdenv != null)
            return cmdenv.atom(s, s1);
        else
            return null;
    }

    public boolean existAtom(java.lang.String s, java.lang.String s1, boolean flag1)
    {
        java.lang.Object obj = null;
        if(s == null)
        {
            obj = _env;
        } else
        {
            java.lang.Object obj1 = _env.get(s);
            if(obj1 instanceof com.maddox.rts.CmdArea)
            {
                if(((com.maddox.rts.CmdArea)obj1).existAtom(s1, flag1))
                    return true;
            } else
            {
                obj = (java.util.Map)obj1;
            }
        }
        if(obj != null && com.maddox.rts.CmdEnv.validAtomName(s1) && ((java.util.Map) (obj)).containsKey(s1))
            return true;
        if(flag1)
        {
            com.maddox.rts.CmdEnv cmdenv = parent();
            if(cmdenv != null)
                return cmdenv.existAtom(s, s1, flag1);
            else
                return false;
        } else
        {
            return false;
        }
    }

    public void setCommand(com.maddox.rts.Cmd cmd, java.lang.String s, java.lang.String s1)
    {
        java.util.Map map = cmd.properties();
        map.put("NAME", s);
        map.put("HELP", s1);
        setAtom("_$$$command", s, cmd);
    }

    public java.lang.String metaExpand(java.lang.String s)
    {
        java.lang.String s1 = s;
        if(s1.length() == 0)
            return s;
        if(s1.charAt(0) != '$' && s1.indexOf(" $") == -1)
            return s1;
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        Object obj = null;
        boolean flag1 = true;
        int i = 100;
        while(flag1) 
        {
            if(i-- == 0)
            {
                if(com.maddox.rts.Cmd.ERR_HARD)
                    java.lang.System.err.println("Command cicled in command: " + s);
                return null;
            }
            flag1 = false;
            if(s1.length() > 0 && (s1.charAt(0) == '$' || s1.indexOf(" $") != -1))
            {
                if(stringbuffer.length() > 0)
                    stringbuffer = new StringBuffer();
                for(com.maddox.util.QuoteTokenizer quotetokenizer = new QuoteTokenizer(s1); quotetokenizer.hasMoreTokens();)
                {
                    java.lang.String s2 = quotetokenizer.nextToken();
                    java.lang.Object obj1 = null;
                    if(s2.length() > 1 && s2.charAt(0) == '$')
                    {
                        char c = s2.charAt(1);
                        switch(c)
                        {
                        default:
                            s2 = s2.substring(1);
                            if(!existAtom(s2, true))
                            {
                                if(com.maddox.rts.Cmd.ERR_SOFT)
                                    java.lang.System.err.println("Atom: " + s2 + " not found - ignored");
                                s2 = null;
                            } else
                            {
                                obj1 = atom(s2);
                                if(obj1 != null)
                                {
                                    s2 = obj1.toString();
                                    flag1 = true;
                                } else
                                {
                                    s2 = null;
                                }
                            }
                            break;

                        case 36: // '$'
                        case 38: // '&'
                            break;
                        }
                    }
                    if(s2 != null)
                    {
                        if(stringbuffer.length() > 0)
                            stringbuffer.append(' ');
                        if(obj1 == null)
                            stringbuffer.append(com.maddox.util.QuoteTokenizer.toToken(s2));
                        else
                            stringbuffer.append(s2);
                    }
                }

                s1 = stringbuffer.toString();
            }
        }
        if(s1.length() > 0 && (s1.charAt(0) == '$' || s1.indexOf(" $") != -1))
        {
            if(stringbuffer.length() > 0)
                stringbuffer = new StringBuffer();
            com.maddox.util.QuoteTokenizer quotetokenizer1 = new QuoteTokenizer(s1);
            boolean flag2 = false;
            while(quotetokenizer1.hasMoreTokens()) 
            {
                java.lang.String s3 = quotetokenizer1.nextToken();
                if(s3.length() > 1 && s3.charAt(0) == '$')
                {
                    char c1 = s3.charAt(1);
                    switch(c1)
                    {
                    case 38: // '&'
                        flag2 = true;
                        s3 = null;
                        break;

                    case 36: // '$'
                        s3 = s3.substring(1);
                        break;
                    }
                }
                if(s3 != null)
                {
                    if(!flag2 && stringbuffer.length() > 0)
                        stringbuffer.append(' ');
                    stringbuffer.append(com.maddox.util.QuoteTokenizer.toToken(s3));
                    flag2 = false;
                }
            }
            s1 = stringbuffer.toString();
        }
        return s1;
    }

    public java.lang.Object exec(java.lang.String s)
    {
        curExecCmdString = s;
        boolean flag2;
        com.maddox.util.QuoteTokenizer quotetokenizer;
        java.lang.String s1;
        java.lang.String s2;
        boolean flag1 = flag("fast");
        flag2 = false;
        if(!flag1)
        {
            flag2 = flag("alias");
            if(_bUseHistory)
                s = historyEntry(s);
        }
        quotetokenizer = null;
        s1 = null;
        s2 = metaExpand(s);
        if(s2 == null)
            return null;
        int i;
        if(!flag2)
            break MISSING_BLOCK_LABEL_215;
        i = 0;
          goto _L1
_L3:
        quotetokenizer = new QuoteTokenizer(s2);
        if(quotetokenizer.hasMoreTokens())
        {
            s1 = quotetokenizer.nextToken();
            java.lang.String s4 = (java.lang.String)atom("_$$$alias", s1);
            if(s4 == null)
                break; /* Loop/switch isn't completed */
            if(quotetokenizer.hasMoreTokens())
                s2 = s4 + ' ' + quotetokenizer.getGap();
            else
                s2 = s4;
            continue; /* Loop/switch isn't completed */
        }
        return null;
        i++;
_L1:
        if(i < 10) goto _L3; else goto _L2
_L2:
        if(i != 10)
            break MISSING_BLOCK_LABEL_246;
        if(com.maddox.rts.Cmd.ERR_HARD)
            java.lang.System.err.println("Command cicled alias in command: " + s);
        return null;
        quotetokenizer = new QuoteTokenizer(s2);
        if(quotetokenizer.hasMoreTokens())
        {
            s1 = quotetokenizer.nextToken();
            break MISSING_BLOCK_LABEL_246;
        }
        return null;
        com.maddox.rts.Cmd cmd;
        cmd = (com.maddox.rts.Cmd)atom("_$$$command", s1);
        if(cmd != null)
            break MISSING_BLOCK_LABEL_302;
        if(com.maddox.rts.Cmd.ERR_HARD)
            java.lang.System.err.println("Command not found: " + s);
        setResult(null);
        return null;
        if(!noAccess(cmd))
            break MISSING_BLOCK_LABEL_349;
        if(com.maddox.rts.Cmd.ERR_HARD)
            java.lang.System.err.println("Command not found: " + s);
        setResult(null);
        return null;
        java.lang.Object obj;
        if(cmd.isRawFormat())
        {
            if(quotetokenizer.hasMoreTokens())
                obj = cmd.exec(this, quotetokenizer.getGap());
            else
                obj = cmd.exec(this, "");
        } else
        {
            java.util.Map map = (java.util.Map)cmd.properties().get("PARAMS");
            java.util.HashMap hashmap = new HashMap();
            java.util.ArrayList arraylist = null;
            if(quotetokenizer.hasMoreTokens())
            {
                java.lang.String s3 = quotetokenizer.getGap();
                java.lang.String s5 = null;
                for(com.maddox.util.QuoteTokenizer quotetokenizer1 = new QuoteTokenizer(s3); quotetokenizer1.hasMoreTokens();)
                {
                    java.lang.String s6 = quotetokenizer1.nextToken();
                    if(map != null && map.containsKey(s6))
                    {
                        if(arraylist != null)
                        {
                            if(s5 == null)
                                s5 = "_$$";
                            hashmap.put(s5, arraylist);
                        } else
                        if(s5 != null)
                            hashmap.put(s5, arraylist);
                        s5 = s6;
                        arraylist = (java.util.ArrayList)hashmap.get(s5);
                    } else
                    {
                        if(arraylist == null)
                            arraylist = new ArrayList();
                        arraylist.add(s6);
                    }
                }

                if(arraylist != null)
                {
                    if(s5 == null)
                        s5 = "_$$";
                    hashmap.put(s5, arraylist);
                } else
                if(s5 != null)
                    hashmap.put(s5, arraylist);
            }
            if(map != null)
            {
                java.util.Set set = map.keySet();
                for(java.util.Iterator iterator = set.iterator(); iterator.hasNext();)
                {
                    java.lang.Object obj1 = iterator.next();
                    if(!hashmap.containsKey(obj1))
                    {
                        java.lang.Object obj2 = map.get(obj1);
                        if(obj2 != null)
                        {
                            java.util.ArrayList arraylist1 = new ArrayList();
                            for(com.maddox.util.QuoteTokenizer quotetokenizer2 = new QuoteTokenizer((java.lang.String)obj2); quotetokenizer2.hasMoreTokens(); arraylist1.add(quotetokenizer2.nextToken()));
                            hashmap.put(obj1, arraylist1);
                        }
                    }
                }

            }
            obj = cmd.exec(this, hashmap);
        }
        setResult(obj);
        return obj;
        java.lang.Exception exception;
        exception;
        exception.printStackTrace();
        setResult(null);
        return null;
    }

    private void setResult(java.lang.Object obj)
    {
        setAtom(null, "_$$2", atom(null, "_$$1"));
        setAtom(null, "_$$1", atom(null, "_$$0"));
        setAtom(null, "_$$0", obj);
    }

    private java.lang.String historyEntry(java.lang.String s)
    {
        java.lang.String s1 = (java.lang.String)atom(null, "history");
        java.util.TreeMap treemap = (java.util.TreeMap)_env.get("_$$$history");
        if(s1 == null)
        {
            if(treemap != null)
                _env.remove("_$$$history");
            return s;
        }
        int i = java.lang.Integer.parseInt(s1);
        if(i <= 0)
        {
            if(treemap != null)
                for(; treemap.size() > i; treemap.remove(treemap.firstKey()));
            return s;
        }
        if(s == null || s.length() == 0)
            return s;
        java.lang.String s2 = null;
        if(treemap == null)
        {
            treemap = new TreeMap();
            _env.put("_$$$history", treemap);
        } else
        if(s.charAt(0) == '!')
            if(s.charAt(1) == '!')
            {
                s2 = (java.lang.String)treemap.get(treemap.lastKey());
            } else
            {
                java.lang.String s3 = s.substring(1);
                try
                {
                    java.lang.Integer integer = java.lang.Integer.valueOf(s3);
                    s2 = (java.lang.String)treemap.get(integer);
                }
                catch(java.lang.NumberFormatException numberformatexception)
                {
                    java.util.Set set = treemap.keySet();
                    for(java.util.Iterator iterator = set.iterator(); iterator.hasNext();)
                    {
                        java.lang.String s4 = (java.lang.String)treemap.get(iterator.next());
                        com.maddox.util.QuoteTokenizer quotetokenizer = new QuoteTokenizer(s4);
                        if(com.maddox.util.StrMath.simple(s3, quotetokenizer.nextToken()))
                            s2 = s4;
                    }

                }
                catch(java.lang.Exception exception) { }
            }
        _curNumCmd++;
        if(s2 != null)
            s = s2;
        treemap.put(new Integer(_curNumCmd), s);
        for(; treemap.size() > i; treemap.remove(treemap.firstKey()));
        return s;
    }

    public int curNumCmd()
    {
        return _curNumCmd;
    }

    public int levelAccess()
    {
        return _levelAccess;
    }

    public void setLevelAccess(int i)
    {
        _levelAccess = i;
    }

    public boolean noAccess(com.maddox.rts.CmdEnv cmdenv)
    {
        return _levelAccess > cmdenv._levelAccess;
    }

    public boolean noAccess(int i)
    {
        return _levelAccess > i;
    }

    public boolean noAccess(com.maddox.rts.Cmd cmd)
    {
        return _levelAccess > cmd.levelAccess();
    }

    public java.util.Map area()
    {
        return _env;
    }

    public static com.maddox.rts.CmdEnv top()
    {
        return com.maddox.rts.RTSConf.cur.cmdEnv;
    }

    public CmdEnv(com.maddox.rts.CmdEnv cmdenv)
    {
        _env = new TreeMap();
        _parent = cmdenv;
        _curNumCmd = 0;
        _bUseHistory = true;
        _levelAccess = cmdenv.levelAccess();
    }

    protected CmdEnv()
    {
        _env = new TreeMap();
        _parent = null;
        _env.put("_$$$command", new TreeMap());
        _env.put("_$$$alias", new TreeMap());
        _env.put("_$$$history", new TreeMap());
        setFlag("fast", false);
        setFlag("echo", true);
        setFlag("alias", true);
        setAtom(null, "history", "0");
        setAtom("_$$$command", "CmdLoad", new CmdLoad());
        _curNumCmd = 0;
        _bUseHistory = true;
        _levelAccess = 0;
    }

    public static final java.lang.String AREA_PREFIX = "_$$$";
    public static final java.lang.String AREA_COMMAND = "_$$$command";
    public static final java.lang.String AREA_ALIAS = "_$$$alias";
    public static final java.lang.String AREA_HISTORY = "_$$$history";
    public static final int LEVEL_DEVELOPER = 0;
    public static final int LEVEL_USER = 1;
    public static final int LEVEL_GUEST = 2;
    public static final java.lang.String FAST = "fast";
    public static final java.lang.String ALIAS = "alias";
    public static final java.lang.String HISTORY = "history";
    public static final java.lang.String ECHO = "echo";
    public static final java.lang.String ON = "on";
    public static final java.lang.String OFF = "off";
    public static final java.lang.Object RETURN_OK = "OK";
    public java.lang.String curExecCmdString;
    private java.util.TreeMap _env;
    private com.maddox.rts.CmdEnv _parent;
    private int _curNumCmd;
    private boolean _bUseHistory;
    private int _levelAccess;

    static 
    {
        com.maddox.rts.RTS.loadNative();
    }
}
