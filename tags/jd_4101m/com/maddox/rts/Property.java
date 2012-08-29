package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.WeakHashMapExt;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map.Entry;

public class Property
{
  public static WeakHashMapExt propertyMap = new WeakHashMapExt();

  public static HashMapExt propertyHoldMap = new HashMapExt();
  public static final int ADDED = 1;
  public static final int REMOVED = 2;
  public static final int CHANGED = 3;
  public static final String OBSERVER = "OBSERVER";
  public static final int OBSERVER_ID = Finger.Int("OBSERVER");
  private String name;
  private int finger;
  private WeakReference owner;
  private static HashMapExt _varsMap = new HashMapExt();

  protected static WeakReference lastOwner = null;
  protected static HashMapInt lastMapInt = null;
  protected static int lastAction = 0;

  public String name()
  {
    return this.name; } 
  public int finger() { return this.finger; } 
  public Object owner() { return this.owner.get(); }

  public Class classValue() {
    return Void.class;
  }
  public int intValue() { throw new ClassCastException("Property NOT Integer class"); } 
  public float floatValue() { throw new ClassCastException("Property NOT Float class"); } 
  public long longValue() { throw new ClassCastException("Property NOT Long class"); } 
  public double doubleValue() { throw new ClassCastException("Property NOT Double class"); } 
  public Object value() { throw new ClassCastException("Property NOT Object class"); } 
  public String stringValue() { throw new ClassCastException("Property NOT String class"); } 
  public long fingerValue(long paramLong) { return paramLong; } 
  public static Property find(Class paramClass, String paramString) {
    return find(paramClass, Finger.Int(paramString));
  }
  public static Property find(Class paramClass, int paramInt) { HashMapInt localHashMapInt = null;
    if ((lastOwner != null) && (lastOwner.get() == paramClass)) {
      localHashMapInt = lastMapInt;
      localObject1 = (Property)localHashMapInt.get(paramInt);
      if (localObject1 != null)
        return localObject1;
    }
    Object localObject1 = paramClass;
    while (localObject1 != null) {
      localHashMapInt = (HashMapInt)propertyMap.get(localObject1);
      if (localHashMapInt != null) {
        localObject2 = (Property)localHashMapInt.get(paramInt);
        if (localObject2 != null) {
          lastOwner = ((Property)localObject2).owner;
          lastMapInt = localHashMapInt;
          return localObject2;
        }
      }
      Object localObject2 = ((Class)localObject1).getInterfaces();
      if ((localObject2 != null) && (localObject2.length > 0)) {
        for (int i = 0; i < localObject2.length; i++) {
          localHashMapInt = (HashMapInt)propertyMap.get(localObject2[i]);
          if (localHashMapInt != null) {
            Property localProperty = (Property)localHashMapInt.get(paramInt);
            if (localProperty != null) {
              lastOwner = localProperty.owner;
              lastMapInt = localHashMapInt;
              return localProperty;
            }
          }
        }
      }
      localObject1 = ((Class)localObject1).getSuperclass();
    }
    return (Property)(Property)null; }

  public static Property get(Object paramObject, String paramString) {
    return get(paramObject, Finger.Int(paramString));
  }
  public static Property get(Object paramObject, int paramInt) {
    if ((paramObject instanceof String)) paramObject = ((String)paramObject).intern();
    if ((lastOwner != null) && (lastOwner.get() == paramObject)) {
      localHashMapInt = lastMapInt;
      return (Property)localHashMapInt.get(paramInt);
    }
    HashMapInt localHashMapInt = (HashMapInt)propertyMap.get(paramObject);
    if (localHashMapInt == null) return null;
    Property localProperty = (Property)localHashMapInt.get(paramInt);
    if (localProperty != null) {
      lastOwner = localProperty.owner;
      lastMapInt = localHashMapInt;
    }
    return localProperty;
  }

  public static boolean vars(List paramList, Object paramObject)
  {
    if ((paramObject instanceof String)) paramObject = ((String)paramObject).intern();
    HashMapInt localHashMapInt = (HashMapInt)propertyMap.get(paramObject);
    if (localHashMapInt == null) return false;
    if (localHashMapInt.size() == 0) return false;
    HashMapIntEntry localHashMapIntEntry = localHashMapInt.nextEntry(null);
    while (localHashMapIntEntry != null) {
      Property localProperty = (Property)localHashMapIntEntry.getValue();
      paramList.add(localProperty.name());
      localHashMapIntEntry = localHashMapInt.nextEntry(localHashMapIntEntry);
    }
    return true;
  }

  public static boolean vars(List paramList, Class paramClass)
  {
    Class localClass = paramClass;
    Object localObject2;
    while (localClass != null) {
      localObject1 = (HashMapInt)propertyMap.get(localClass);
      if ((localObject1 != null) && (((HashMapInt)localObject1).size() != 0)) {
        localObject2 = ((HashMapInt)localObject1).nextEntry(null);
        while (localObject2 != null) {
          Property localProperty = (Property)((HashMapIntEntry)localObject2).getValue();
          _varsMap.put(localProperty.name(), null);
          localObject2 = ((HashMapInt)localObject1).nextEntry((HashMapIntEntry)localObject2);
        }
      }
      localClass = localClass.getSuperclass();
    }
    if (_varsMap.size() == 0) return false;
    Object localObject1 = _varsMap.nextEntry(null);
    while (localObject1 != null) {
      localObject2 = (Property)((Map.Entry)localObject1).getValue();
      paramList.add(((Property)localObject2).name());
      localObject1 = _varsMap.nextEntry((Map.Entry)localObject1);
    }
    _varsMap.clear();
    return true;
  }

  public static boolean containsValue(Class paramClass, String paramString) {
    return containsValue(paramClass, Finger.Int(paramString));
  }
  public static boolean containsValue(Class paramClass, int paramInt) { return find(paramClass, paramInt) != null; } 
  public static boolean containsValue(Object paramObject, String paramString) {
    return containsValue(paramObject, Finger.Int(paramString));
  }
  public static boolean containsValue(Object paramObject, int paramInt) { return get(paramObject, paramInt) != null; }

  public static Class classValue(Class paramClass, String paramString) {
    return classValue(paramClass, Finger.Int(paramString));
  }
  public static Class classValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return null;
    return localProperty.classValue(); } 
  public static Class classValue(Object paramObject, String paramString) {
    return classValue(paramObject, Finger.Int(paramString));
  }
  public static Class classValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return null;
    return localProperty.classValue(); }

  public static int intValue(Class paramClass, String paramString) {
    return intValue(paramClass, Finger.Int(paramString));
  }
  public static int intValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return 0;
    return localProperty.intValue(); } 
  public static int intValue(Class paramClass, String paramString, int paramInt) {
    return intValue(paramClass, Finger.Int(paramString), paramInt);
  }
  public static int intValue(Class paramClass, int paramInt1, int paramInt2) { Property localProperty = find(paramClass, paramInt1);
    if (localProperty == null) return paramInt2;
    return localProperty.intValue(); } 
  public static int intValue(Object paramObject, String paramString) {
    return intValue(paramObject, Finger.Int(paramString));
  }
  public static int intValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return 0;
    return localProperty.intValue(); } 
  public static int intValue(Object paramObject, String paramString, int paramInt) {
    return intValue(paramObject, Finger.Int(paramString), paramInt);
  }
  public static int intValue(Object paramObject, int paramInt1, int paramInt2) { Property localProperty = get(paramObject, paramInt1);
    if (localProperty == null) return paramInt2;
    return localProperty.intValue(); }

  public static float floatValue(Class paramClass, String paramString) {
    return floatValue(paramClass, Finger.Int(paramString));
  }
  public static float floatValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return 0.0F;
    return localProperty.floatValue(); } 
  public static float floatValue(Class paramClass, String paramString, float paramFloat) {
    return floatValue(paramClass, Finger.Int(paramString), paramFloat);
  }
  public static float floatValue(Class paramClass, int paramInt, float paramFloat) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return paramFloat;
    return localProperty.floatValue(); } 
  public static float floatValue(Object paramObject, String paramString) {
    return floatValue(paramObject, Finger.Int(paramString));
  }
  public static float floatValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return 0.0F;
    return localProperty.floatValue(); } 
  public static float floatValue(Object paramObject, String paramString, float paramFloat) {
    return floatValue(paramObject, Finger.Int(paramString), paramFloat);
  }
  public static float floatValue(Object paramObject, int paramInt, float paramFloat) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return paramFloat;
    return localProperty.floatValue(); }

  public static long longValue(Class paramClass, String paramString) {
    return longValue(paramClass, Finger.Int(paramString));
  }
  public static long longValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return 0L;
    return localProperty.longValue(); } 
  public static long longValue(Class paramClass, String paramString, long paramLong) {
    return longValue(paramClass, Finger.Int(paramString), paramLong);
  }
  public static long longValue(Class paramClass, int paramInt, long paramLong) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return paramLong;
    return localProperty.longValue(); } 
  public static long longValue(Object paramObject, String paramString) {
    return longValue(paramObject, Finger.Int(paramString));
  }
  public static long longValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return 0L;
    return localProperty.longValue(); } 
  public static long longValue(Object paramObject, String paramString, long paramLong) {
    return longValue(paramObject, Finger.Int(paramString), paramLong);
  }
  public static long longValue(Object paramObject, int paramInt, long paramLong) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return paramLong;
    return localProperty.longValue(); }

  public static double doubleValue(Class paramClass, String paramString) {
    return doubleValue(paramClass, Finger.Int(paramString));
  }
  public static double doubleValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return 0.0D;
    return localProperty.doubleValue(); } 
  public static double doubleValue(Class paramClass, String paramString, double paramDouble) {
    return doubleValue(paramClass, Finger.Int(paramString), paramDouble);
  }
  public static double doubleValue(Class paramClass, int paramInt, double paramDouble) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return paramDouble;
    return localProperty.doubleValue(); } 
  public static double doubleValue(Object paramObject, String paramString) {
    return doubleValue(paramObject, Finger.Int(paramString));
  }
  public static double doubleValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return 0.0D;
    return localProperty.doubleValue(); } 
  public static double doubleValue(Object paramObject, String paramString, double paramDouble) {
    return doubleValue(paramObject, Finger.Int(paramString), paramDouble);
  }
  public static double doubleValue(Object paramObject, int paramInt, double paramDouble) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return paramDouble;
    return localProperty.doubleValue(); }

  public static Object value(Class paramClass, String paramString) {
    return value(paramClass, Finger.Int(paramString));
  }
  public static Object value(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return null;
    return localProperty.value(); } 
  public static Object value(Class paramClass, String paramString, Object paramObject) {
    return value(paramClass, Finger.Int(paramString), paramObject);
  }
  public static Object value(Class paramClass, int paramInt, Object paramObject) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return paramObject;
    return localProperty.value(); } 
  public static Object value(Object paramObject, String paramString) {
    return value(paramObject, Finger.Int(paramString));
  }
  public static Object value(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return null;
    return localProperty.value(); } 
  public static Object value(Object paramObject1, String paramString, Object paramObject2) {
    return value(paramObject1, Finger.Int(paramString), paramObject2);
  }
  public static Object value(Object paramObject1, int paramInt, Object paramObject2) { Property localProperty = get(paramObject1, paramInt);
    if (localProperty == null) return paramObject2;
    return localProperty.value(); }

  public static String stringValue(Class paramClass, String paramString) {
    return stringValue(paramClass, Finger.Int(paramString));
  }
  public static String stringValue(Class paramClass, int paramInt) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return "";
    return localProperty.stringValue(); } 
  public static String stringValue(Class paramClass, String paramString1, String paramString2) {
    return stringValue(paramClass, Finger.Int(paramString1), paramString2);
  }
  public static String stringValue(Class paramClass, int paramInt, String paramString) { Property localProperty = find(paramClass, paramInt);
    if (localProperty == null) return paramString;
    return localProperty.stringValue(); } 
  public static String stringValue(Object paramObject, String paramString) {
    return stringValue(paramObject, Finger.Int(paramString));
  }
  public static String stringValue(Object paramObject, int paramInt) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return "";
    return localProperty.stringValue(); } 
  public static String stringValue(Object paramObject, String paramString1, String paramString2) {
    return stringValue(paramObject, Finger.Int(paramString1), paramString2);
  }
  public static String stringValue(Object paramObject, int paramInt, String paramString) { Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return paramString;
    return localProperty.stringValue(); }

  public void set(int paramInt) {
    throw new ClassCastException("Property NOT Integer class"); } 
  public void set(float paramFloat) { throw new ClassCastException("Property NOT Float class"); } 
  public void set(long paramLong) { throw new ClassCastException("Property NOT Long class"); } 
  public void set(double paramDouble) { throw new ClassCastException("Property NOT Double class"); } 
  public void set(Object paramObject) { throw new ClassCastException("Property NOT Object class"); } 
  public void set(String paramString) { throw new ClassCastException("Property NOT String class"); }

  public static void set(Object paramObject, String paramString, Class paramClass) {
    Class localClass = null;
    Property localProperty = get(paramObject, paramString);
    if (localProperty != null)
      localClass = localProperty.classValue();
    if (paramClass == localClass) return;
    if ((paramClass == Integer.class) || (paramClass == Long.class)) { new PropertyLong(paramObject, paramString); return; }
    if ((paramClass == Float.class) || (paramClass == Double.class)) { new PropertyDouble(paramObject, paramString); return; }
    if (paramClass == String.class) { new PropertyString(paramObject, paramString); return; }
    if (paramClass == Object.class) { new PropertyObject(paramObject, paramString); return; }
    throw new ClassCastException("NOT supported property class: " + paramClass.getName());
  }

  public static void set(Object paramObject, String paramString, int paramInt) {
    Property localProperty = get(paramObject, paramString);
    if (localProperty == null) {
      new PropertyLong(paramObject, paramString, paramInt);
    } else {
      localProperty.set(paramInt);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject, int paramInt1, int paramInt2) {
    Property localProperty = get(paramObject, paramInt1);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt1 + ") NOT present");
    }
    localProperty.set(paramInt2);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void set(Object paramObject, String paramString, long paramLong)
  {
    Property localProperty = get(paramObject, paramString);
    if (localProperty == null) {
      new PropertyLong(paramObject, paramString, paramLong);
    } else {
      localProperty.set(paramLong);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject, int paramInt, long paramLong) {
    Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt + ") NOT present");
    }
    localProperty.set(paramLong);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void set(Object paramObject, String paramString, float paramFloat)
  {
    Property localProperty = get(paramObject, paramString);
    if (localProperty == null) {
      new PropertyDouble(paramObject, paramString, paramFloat);
    } else {
      localProperty.set(paramFloat);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject, int paramInt, float paramFloat) {
    Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt + ") NOT present");
    }
    localProperty.set(paramFloat);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void set(Object paramObject, String paramString, double paramDouble)
  {
    Property localProperty = get(paramObject, paramString);
    if (localProperty == null) {
      new PropertyDouble(paramObject, paramString, paramDouble);
    } else {
      localProperty.set(paramDouble);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject, int paramInt, double paramDouble) {
    Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt + ") NOT present");
    }
    localProperty.set(paramDouble);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void set(Object paramObject1, String paramString, Object paramObject2)
  {
    Property localProperty = get(paramObject1, paramString);
    if (localProperty == null) {
      new PropertyObject(paramObject1, paramString, paramObject2);
    } else {
      localProperty.set(paramObject2);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject1, int paramInt, Object paramObject2) {
    Property localProperty = get(paramObject1, paramInt);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt + ") NOT present");
    }
    localProperty.set(paramObject2);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void set(Object paramObject, String paramString1, String paramString2)
  {
    Property localProperty = get(paramObject, paramString1);
    if (localProperty == null) {
      new PropertyString(paramObject, paramString1, paramString2);
    } else {
      localProperty.set(paramString2);
      localProperty.invokeObserver(lastMapInt, 3);
    }
  }

  public static void set(Object paramObject, int paramInt, String paramString) {
    Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) {
      throw new NullPointerException("Property (" + paramInt + ") NOT present");
    }
    localProperty.set(paramString);
    localProperty.invokeObserver(lastMapInt, 3);
  }

  public static void remove(Object paramObject, String paramString) {
    remove(paramObject, Finger.Int(paramString));
  }
  public static void remove(Object paramObject, int paramInt) { if ((paramObject instanceof String)) paramObject = ((String)paramObject).intern();
    Property localProperty = get(paramObject, paramInt);
    if (localProperty == null) return;
    lastMapInt.remove(localProperty.finger);
    if ((lastMapInt.size() == 0) && (((paramObject instanceof String)) || ((paramObject instanceof Class)))) {
      propertyHoldMap.remove(paramObject);
    }
    localProperty.invokeObserver(lastMapInt, 2); }

  protected Property(Object paramObject, String paramString)
  {
    if ((paramObject instanceof String)) paramObject = ((String)paramObject).intern();
    this.name = paramString;
    if (paramString != null) this.finger = Finger.Int(paramString); else
      this.finger = 0;
    HashMapInt localHashMapInt;
    if ((lastOwner != null) && (lastOwner.get() == paramObject)) {
      this.owner = lastOwner;
      localHashMapInt = lastMapInt;
      if (localHashMapInt.containsKey(this.finger)) lastAction = 3; else
        lastAction = 1;
    } else {
      this.owner = new WeakReference(paramObject);
      localHashMapInt = (HashMapInt)propertyMap.get(paramObject);
      if (localHashMapInt == null) {
        localHashMapInt = new HashMapInt();
        propertyMap.put(paramObject, localHashMapInt);
        lastAction = 1;
      }
      else if (localHashMapInt.containsKey(this.finger)) { lastAction = 3; } else {
        lastAction = 1;
      }
      lastOwner = this.owner;
      lastMapInt = localHashMapInt;
    }
    localHashMapInt.put(this.finger, this);
    if ((lastAction == 1) && (((paramObject instanceof String)) || ((paramObject instanceof Class))))
      propertyHoldMap.put(paramObject, null);
  }

  protected void invokeObserver(HashMapInt paramHashMapInt, int paramInt)
  {
    PropertyObserver localPropertyObserver = (PropertyObserver)paramHashMapInt.get(OBSERVER_ID);
    if (localPropertyObserver == null) return;
    localPropertyObserver.invokeListeners(paramInt, this);
  }

  public static void addListener(Object paramObject1, Object paramObject2, boolean paramBoolean1, boolean paramBoolean2)
  {
    PropertyObserver localPropertyObserver = (PropertyObserver)get(paramObject1, OBSERVER_ID);
    if (localPropertyObserver == null)
      localPropertyObserver = new PropertyObserver(paramObject1);
    localPropertyObserver.addListener(paramObject2, paramBoolean1, paramBoolean2);
  }

  public static void removeListener(Object paramObject1, Object paramObject2)
  {
    PropertyObserver localPropertyObserver = (PropertyObserver)get(paramObject1, OBSERVER_ID);
    if (localPropertyObserver == null) return;
    localPropertyObserver.removeListener(paramObject2);
  }
}