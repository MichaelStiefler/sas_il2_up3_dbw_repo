// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Spawn.java

package com.maddox.rts;

import com.maddox.util.HashMapInt;
import java.io.PrintStream;
import java.lang.reflect.Method;

// Referenced classes of package com.maddox.rts:
//            RTSException, Finger

public class Spawn
{

    public Spawn()
    {
    }

    public static java.lang.String getLastClassName()
    {
        return lastClassName;
    }

    public static boolean exist(java.lang.String s)
    {
        return mapSpawns.get(com.maddox.rts.Finger.Int(s)) != null;
    }

    public static java.lang.Object get(java.lang.String s)
    {
        return com.maddox.rts.Spawn.get(s, true);
    }

    public static java.lang.Object get(java.lang.String s, boolean flag)
    {
        lastClassName = s;
        int i = com.maddox.rts.Finger.Int(s);
        java.lang.Object obj = mapSpawns.get(i);
        if(obj == null)
            try
            {
                java.lang.Class class1 = java.lang.Class.forName(s);
                obj = mapSpawns.get(i);
            }
            catch(java.lang.Throwable throwable)
            {
                if(flag)
                {
                    java.lang.System.out.println("Spawn.get( " + s + " ): " + throwable.getMessage());
                    throwable.printStackTrace();
                }
            }
        return obj;
    }

    public static java.lang.Object get_WithSoftClass(java.lang.String s)
    {
        return com.maddox.rts.Spawn.get_WithSoftClass(s, true);
    }

    public static java.lang.Object get_WithSoftClass(java.lang.String s, boolean flag)
    {
        lastClassName = s;
        int i = com.maddox.rts.Finger.Int(s);
        java.lang.Object obj = mapSpawns.get(i);
        if(obj != null)
            return obj;
        int j;
        for(j = s.lastIndexOf('$'); j >= 0;)
        {
            java.lang.String s1 = s.substring(0, j);
            java.lang.Class class1;
            try
            {
                class1 = java.lang.Class.forName(s1);
            }
            catch(java.lang.Throwable throwable1)
            {
                if(flag)
                {
                    java.lang.System.out.println("Spawn.get_WithSoftClass( " + s + " ): " + throwable1.getMessage());
                    throwable1.printStackTrace();
                }
                return null;
            }
            if(!(com.maddox.rts.SoftClass.class).isAssignableFrom(class1))
            {
                j = -1;
            } else
            {
                java.lang.Class class2 = null;
                try
                {
                    class2 = java.lang.Class.forName(s);
                }
                catch(java.lang.Throwable throwable2) { }
                if(class2 != null)
                {
                    if(flag)
                        java.lang.System.out.println("'" + s + "' can't be SoftClass and " + "standard class simultaneously.");
                    return null;
                }
                java.lang.Class aclass[] = {
                    java.lang.String.class
                };
                java.lang.Object aobj[] = {
                    s.substring(j + 1)
                };
                try
                {
                    java.lang.reflect.Method method = class1.getMethod("registerSpawner", aclass);
                    method.invoke(null, aobj);
                }
                catch(java.lang.NoSuchMethodException nosuchmethodexception)
                {
                    if(flag)
                    {
                        java.lang.System.out.println("SoftClass '" + s1 + "' must have method registerSpawner()");
                        nosuchmethodexception.printStackTrace();
                    }
                    return null;
                }
                catch(java.lang.Exception exception)
                {
                    if(flag)
                    {
                        java.lang.System.out.println(s + ".registerSpawner() failed.");
                        exception.printStackTrace();
                    }
                    return null;
                }
            }
            break;
        }

        if(j < 0)
            try
            {
                java.lang.Class.forName(s);
            }
            catch(java.lang.Throwable throwable)
            {
                if(flag)
                {
                    java.lang.System.out.println("Spawn.get_WithSoftClass( " + s + " ): " + throwable.getMessage());
                    throwable.printStackTrace();
                }
                return null;
            }
        obj = mapSpawns.get(i);
        if(obj == null && flag)
            java.lang.System.out.println("No spawner for '" + s + "'");
        return obj;
    }

    public static java.lang.Object get(int i)
    {
        return mapSpawns.get(i);
    }

    public static void add(java.lang.Class class1, java.lang.Object obj)
    {
        java.lang.String s = class1.getName();
        int i = com.maddox.rts.Finger.Int(s);
        java.lang.String s1 = (java.lang.String)mapNames.get(i);
        if(s1 != null && !s1.equals(s))
        {
            throw new RTSException("Spawn: conflict class name finger '" + s1 + "' and '" + s + "'");
        } else
        {
            mapNames.put(i, s);
            mapSpawns.put(i, obj);
            return;
        }
    }

    public static void add_SoftClass(java.lang.String s, java.lang.Object obj)
    {
        int i = com.maddox.rts.Finger.Int(s);
        java.lang.String s1 = (java.lang.String)mapNames.get(i);
        if(s1 != null && !s1.equals(s))
        {
            throw new RTSException("Spawn: conflict class name finger '" + s1 + "' and '" + s + "'");
        } else
        {
            mapNames.put(i, s);
            mapSpawns.put(i, obj);
            return;
        }
    }

    public static final boolean USE_CHECK = true;
    private static java.lang.String lastClassName = "Unknown";
    private static com.maddox.util.HashMapInt mapNames = new HashMapInt();
    private static com.maddox.util.HashMapInt mapSpawns = new HashMapInt();

}
