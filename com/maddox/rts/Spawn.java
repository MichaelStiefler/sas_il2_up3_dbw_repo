package com.maddox.rts;

import com.maddox.util.HashMapInt;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class Spawn
{
  public static final boolean USE_CHECK = true;
  private static String lastClassName = "Unknown";
  private static HashMapInt mapNames = new HashMapInt();
  private static HashMapInt mapSpawns = new HashMapInt();

  public static String getLastClassName()
  {
    return lastClassName;
  }

  public static boolean exist(String paramString)
  {
    return mapSpawns.get(Finger.Int(paramString)) != null;
  }

  public static Object get(String paramString)
  {
    return get(paramString, true);
  }

  public static Object get(String paramString, boolean paramBoolean)
  {
    lastClassName = paramString;
    int i = Finger.Int(paramString);
    Object localObject = mapSpawns.get(i);
    if (localObject == null) {
      try
      {
        Class localClass = Class.forName(paramString);
        localObject = mapSpawns.get(i);
      } catch (Throwable localThrowable) {
        if (paramBoolean) {
          System.out.println("Spawn.get( " + paramString + " ): " + localThrowable.getMessage());
          localThrowable.printStackTrace();
        }
      }
    }
    return localObject;
  }

  public static Object get_WithSoftClass(String paramString)
  {
    return get_WithSoftClass(paramString, true);
  }

  public static Object get_WithSoftClass(String paramString, boolean paramBoolean)
  {
    lastClassName = paramString;

    int i = Finger.Int(paramString);
    Object localObject1 = mapSpawns.get(i);
    if (localObject1 != null) {
      return localObject1;
    }

    int j = paramString.lastIndexOf('$');
    while (j >= 0) {
      String str = paramString.substring(0, j);
      Class localClass;
      try {
        localClass = Class.forName(str);
      } catch (Throwable localThrowable2) {
        if (paramBoolean) {
          System.out.println("Spawn.get_WithSoftClass( " + paramString + " ): " + localThrowable2.getMessage());

          localThrowable2.printStackTrace();
        }
        return null;
      }

      if (!SoftClass.class.isAssignableFrom(localClass))
      {
        j = -1;
        break;
      }

      Object localObject2 = null;
      try {
        localObject2 = Class.forName(paramString);
      } catch (Throwable localThrowable3) {
      }
      if (localObject2 != null)
      {
        if (paramBoolean) {
          System.out.println("'" + paramString + "' can't be SoftClass and " + "standard class simultaneously.");
        }

        return null;
      }

      localObject2 = new Class[] { String.class };
      Object[] arrayOfObject = { paramString.substring(j + 1) };
      try {
        Method localMethod = localClass.getMethod("registerSpawner", localObject2);
        localMethod.invoke(null, arrayOfObject);
      } catch (NoSuchMethodException localNoSuchMethodException) {
        if (paramBoolean) {
          System.out.println("SoftClass '" + str + "' must have method registerSpawner()");

          localNoSuchMethodException.printStackTrace();
        }
        return null;
      } catch (Exception localException) {
        if (paramBoolean) {
          System.out.println(paramString + ".registerSpawner() failed.");
          localException.printStackTrace();
        }
        return null;
      }

    }

    if (j < 0) {
      try {
        Class.forName(paramString);
      } catch (Throwable localThrowable1) {
        if (paramBoolean) {
          System.out.println("Spawn.get_WithSoftClass( " + paramString + " ): " + localThrowable1.getMessage());

          localThrowable1.printStackTrace();
        }
        return null;
      }

    }

    localObject1 = mapSpawns.get(i);
    if ((localObject1 == null) && 
      (paramBoolean)) {
      System.out.println("No spawner for '" + paramString + "'");
    }

    return localObject1;
  }

  public static Object get(int paramInt)
  {
    return mapSpawns.get(paramInt);
  }

  public static void add(Class paramClass, Object paramObject)
  {
    String str1 = paramClass.getName();
    int i = Finger.Int(str1);

    String str2 = (String)mapNames.get(i);
    if ((str2 != null) && (!str2.equals(str1)))
      throw new RTSException("Spawn: conflict class name finger '" + str2 + "' and '" + str1 + "'");
    mapNames.put(i, str1);

    mapSpawns.put(i, paramObject);
  }

  public static void add_SoftClass(String paramString, Object paramObject)
  {
    int i = Finger.Int(paramString);

    String str = (String)mapNames.get(i);
    if ((str != null) && (!str.equals(paramString))) {
      throw new RTSException("Spawn: conflict class name finger '" + str + "' and '" + paramString + "'");
    }
    mapNames.put(i, paramString);

    mapSpawns.put(i, paramObject);
  }
}