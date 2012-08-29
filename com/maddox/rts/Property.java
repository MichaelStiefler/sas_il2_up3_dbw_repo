// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.WeakHashMapExt;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.rts:
//            PropertyLong, PropertyDouble, PropertyString, PropertyObject, 
//            PropertyObserver, Finger

public class Property
{

    public java.lang.String name()
    {
        return name;
    }

    public int finger()
    {
        return finger;
    }

    public java.lang.Object owner()
    {
        return owner.get();
    }

    public java.lang.Class classValue()
    {
        return java.lang.Void.class;
    }

    public int intValue()
    {
        throw new ClassCastException("Property NOT Integer class");
    }

    public float floatValue()
    {
        throw new ClassCastException("Property NOT Float class");
    }

    public long longValue()
    {
        throw new ClassCastException("Property NOT Long class");
    }

    public double doubleValue()
    {
        throw new ClassCastException("Property NOT Double class");
    }

    public java.lang.Object value()
    {
        throw new ClassCastException("Property NOT Object class");
    }

    public java.lang.String stringValue()
    {
        throw new ClassCastException("Property NOT String class");
    }

    public long fingerValue(long l)
    {
        return l;
    }

    public static com.maddox.rts.Property find(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.find(class1, com.maddox.rts.Finger.Int(s));
    }

    public static com.maddox.rts.Property find(java.lang.Class class1, int i)
    {
        Object obj = null;
        if(lastOwner != null && lastOwner.get() == class1)
        {
            com.maddox.util.HashMapInt hashmapint = lastMapInt;
            com.maddox.rts.Property property = (com.maddox.rts.Property)hashmapint.get(i);
            if(property != null)
                return property;
        }
        for(java.lang.Class class2 = class1; class2 != null; class2 = class2.getSuperclass())
        {
            com.maddox.util.HashMapInt hashmapint1 = (com.maddox.util.HashMapInt)propertyMap.get(class2);
            if(hashmapint1 != null)
            {
                com.maddox.rts.Property property1 = (com.maddox.rts.Property)hashmapint1.get(i);
                if(property1 != null)
                {
                    lastOwner = property1.owner;
                    lastMapInt = hashmapint1;
                    return property1;
                }
            }
            java.lang.Class aclass[] = class2.getInterfaces();
            if(aclass != null && aclass.length > 0)
            {
                for(int j = 0; j < aclass.length; j++)
                {
                    com.maddox.util.HashMapInt hashmapint2 = (com.maddox.util.HashMapInt)propertyMap.get(aclass[j]);
                    if(hashmapint2 != null)
                    {
                        com.maddox.rts.Property property2 = (com.maddox.rts.Property)hashmapint2.get(i);
                        if(property2 != null)
                        {
                            lastOwner = property2.owner;
                            lastMapInt = hashmapint2;
                            return property2;
                        }
                    }
                }

            }
        }

        return null;
    }

    public static com.maddox.rts.Property get(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.get(obj, com.maddox.rts.Finger.Int(s));
    }

    public static com.maddox.rts.Property get(java.lang.Object obj, int i)
    {
        if(obj instanceof java.lang.String)
            obj = ((java.lang.String)obj).intern();
        if(lastOwner != null && lastOwner.get() == obj)
        {
            com.maddox.util.HashMapInt hashmapint = lastMapInt;
            return (com.maddox.rts.Property)hashmapint.get(i);
        }
        com.maddox.util.HashMapInt hashmapint1 = (com.maddox.util.HashMapInt)propertyMap.get(obj);
        if(hashmapint1 == null)
            return null;
        com.maddox.rts.Property property = (com.maddox.rts.Property)hashmapint1.get(i);
        if(property != null)
        {
            lastOwner = property.owner;
            lastMapInt = hashmapint1;
        }
        return property;
    }

    public static boolean vars(java.util.List list, java.lang.Object obj)
    {
        if(obj instanceof java.lang.String)
            obj = ((java.lang.String)obj).intern();
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)propertyMap.get(obj);
        if(hashmapint == null)
            return false;
        if(hashmapint.size() == 0)
            return false;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
        {
            com.maddox.rts.Property property = (com.maddox.rts.Property)hashmapintentry.getValue();
            list.add(property.name());
        }

        return true;
    }

    public static boolean vars(java.util.List list, java.lang.Class class1)
    {
        for(java.lang.Class class2 = class1; class2 != null; class2 = class2.getSuperclass())
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)propertyMap.get(class2);
            if(hashmapint != null && hashmapint.size() != 0)
            {
                for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
                {
                    com.maddox.rts.Property property1 = (com.maddox.rts.Property)hashmapintentry.getValue();
                    _varsMap.put(property1.name(), null);
                }

            }
        }

        if(_varsMap.size() == 0)
            return false;
        for(java.util.Map.Entry entry = _varsMap.nextEntry(null); entry != null; entry = _varsMap.nextEntry(entry))
        {
            com.maddox.rts.Property property = (com.maddox.rts.Property)entry.getValue();
            list.add(property.name());
        }

        _varsMap.clear();
        return true;
    }

    public static boolean containsValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.containsValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static boolean containsValue(java.lang.Class class1, int i)
    {
        return com.maddox.rts.Property.find(class1, i) != null;
    }

    public static boolean containsValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.containsValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static boolean containsValue(java.lang.Object obj, int i)
    {
        return com.maddox.rts.Property.get(obj, i) != null;
    }

    public static java.lang.Class classValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.classValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.Class classValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return null;
        else
            return property.classValue();
    }

    public static java.lang.Class classValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.classValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.Class classValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return null;
        else
            return property.classValue();
    }

    public static int intValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.intValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static int intValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return 0;
        else
            return property.intValue();
    }

    public static int intValue(java.lang.Class class1, java.lang.String s, int i)
    {
        return com.maddox.rts.Property.intValue(class1, com.maddox.rts.Finger.Int(s), i);
    }

    public static int intValue(java.lang.Class class1, int i, int j)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return j;
        else
            return property.intValue();
    }

    public static int intValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.intValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static int intValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return 0;
        else
            return property.intValue();
    }

    public static int intValue(java.lang.Object obj, java.lang.String s, int i)
    {
        return com.maddox.rts.Property.intValue(obj, com.maddox.rts.Finger.Int(s), i);
    }

    public static int intValue(java.lang.Object obj, int i, int j)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return j;
        else
            return property.intValue();
    }

    public static float floatValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.floatValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static float floatValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return 0.0F;
        else
            return property.floatValue();
    }

    public static float floatValue(java.lang.Class class1, java.lang.String s, float f)
    {
        return com.maddox.rts.Property.floatValue(class1, com.maddox.rts.Finger.Int(s), f);
    }

    public static float floatValue(java.lang.Class class1, int i, float f)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return f;
        else
            return property.floatValue();
    }

    public static float floatValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.floatValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static float floatValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return 0.0F;
        else
            return property.floatValue();
    }

    public static float floatValue(java.lang.Object obj, java.lang.String s, float f)
    {
        return com.maddox.rts.Property.floatValue(obj, com.maddox.rts.Finger.Int(s), f);
    }

    public static float floatValue(java.lang.Object obj, int i, float f)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return f;
        else
            return property.floatValue();
    }

    public static long longValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.longValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static long longValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return 0L;
        else
            return property.longValue();
    }

    public static long longValue(java.lang.Class class1, java.lang.String s, long l)
    {
        return com.maddox.rts.Property.longValue(class1, com.maddox.rts.Finger.Int(s), l);
    }

    public static long longValue(java.lang.Class class1, int i, long l)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return l;
        else
            return property.longValue();
    }

    public static long longValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.longValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static long longValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return 0L;
        else
            return property.longValue();
    }

    public static long longValue(java.lang.Object obj, java.lang.String s, long l)
    {
        return com.maddox.rts.Property.longValue(obj, com.maddox.rts.Finger.Int(s), l);
    }

    public static long longValue(java.lang.Object obj, int i, long l)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return l;
        else
            return property.longValue();
    }

    public static double doubleValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.doubleValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static double doubleValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return 0.0D;
        else
            return property.doubleValue();
    }

    public static double doubleValue(java.lang.Class class1, java.lang.String s, double d)
    {
        return com.maddox.rts.Property.doubleValue(class1, com.maddox.rts.Finger.Int(s), d);
    }

    public static double doubleValue(java.lang.Class class1, int i, double d)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return d;
        else
            return property.doubleValue();
    }

    public static double doubleValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.doubleValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static double doubleValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return 0.0D;
        else
            return property.doubleValue();
    }

    public static double doubleValue(java.lang.Object obj, java.lang.String s, double d)
    {
        return com.maddox.rts.Property.doubleValue(obj, com.maddox.rts.Finger.Int(s), d);
    }

    public static double doubleValue(java.lang.Object obj, int i, double d)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return d;
        else
            return property.doubleValue();
    }

    public static java.lang.Object value(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.value(class1, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.Object value(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return null;
        else
            return property.value();
    }

    public static java.lang.Object value(java.lang.Class class1, java.lang.String s, java.lang.Object obj)
    {
        return com.maddox.rts.Property.value(class1, com.maddox.rts.Finger.Int(s), obj);
    }

    public static java.lang.Object value(java.lang.Class class1, int i, java.lang.Object obj)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return obj;
        else
            return property.value();
    }

    public static java.lang.Object value(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.value(obj, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.Object value(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return null;
        else
            return property.value();
    }

    public static java.lang.Object value(java.lang.Object obj, java.lang.String s, java.lang.Object obj1)
    {
        return com.maddox.rts.Property.value(obj, com.maddox.rts.Finger.Int(s), obj1);
    }

    public static java.lang.Object value(java.lang.Object obj, int i, java.lang.Object obj1)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return obj1;
        else
            return property.value();
    }

    public static java.lang.String stringValue(java.lang.Class class1, java.lang.String s)
    {
        return com.maddox.rts.Property.stringValue(class1, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.String stringValue(java.lang.Class class1, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return "";
        else
            return property.stringValue();
    }

    public static java.lang.String stringValue(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        return com.maddox.rts.Property.stringValue(class1, com.maddox.rts.Finger.Int(s), s1);
    }

    public static java.lang.String stringValue(java.lang.Class class1, int i, java.lang.String s)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.find(class1, i);
        if(property == null)
            return s;
        else
            return property.stringValue();
    }

    public static java.lang.String stringValue(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.Property.stringValue(obj, com.maddox.rts.Finger.Int(s));
    }

    public static java.lang.String stringValue(java.lang.Object obj, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return "";
        else
            return property.stringValue();
    }

    public static java.lang.String stringValue(java.lang.Object obj, java.lang.String s, java.lang.String s1)
    {
        return com.maddox.rts.Property.stringValue(obj, com.maddox.rts.Finger.Int(s), s1);
    }

    public static java.lang.String stringValue(java.lang.Object obj, int i, java.lang.String s)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return s;
        else
            return property.stringValue();
    }

    public void set(int i)
    {
        throw new ClassCastException("Property NOT Integer class");
    }

    public void set(float f)
    {
        throw new ClassCastException("Property NOT Float class");
    }

    public void set(long l)
    {
        throw new ClassCastException("Property NOT Long class");
    }

    public void set(double d)
    {
        throw new ClassCastException("Property NOT Double class");
    }

    public void set(java.lang.Object obj)
    {
        throw new ClassCastException("Property NOT Object class");
    }

    public void set(java.lang.String s)
    {
        throw new ClassCastException("Property NOT String class");
    }

    public static void set(java.lang.Object obj, java.lang.String s, java.lang.Class class1)
    {
        java.lang.Class class2 = null;
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property != null)
            class2 = property.classValue();
        if(class1 == class2)
            return;
        if(class1 == (java.lang.Integer.class) || class1 == (java.lang.Long.class))
        {
            new PropertyLong(obj, s);
            return;
        }
        if(class1 == (java.lang.Float.class) || class1 == (java.lang.Double.class))
        {
            new PropertyDouble(obj, s);
            return;
        }
        if(class1 == (java.lang.String.class))
        {
            new PropertyString(obj, s);
            return;
        }
        if(class1 == (java.lang.Object.class))
        {
            new PropertyObject(obj, s);
            return;
        } else
        {
            throw new ClassCastException("NOT supported property class: " + class1.getName());
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, int i)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyLong(obj, s, i);
        } else
        {
            property.set(i);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, int j)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(j);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, long l)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyLong(obj, s, l);
        } else
        {
            property.set(l);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, long l)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(l);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, float f)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyDouble(obj, s, f);
        } else
        {
            property.set(f);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, float f)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(f);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, double d)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyDouble(obj, s, d);
        } else
        {
            property.set(d);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, double d)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(d);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, java.lang.Object obj1)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyObject(obj, s, obj1);
        } else
        {
            property.set(obj1);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, java.lang.Object obj1)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(obj1);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void set(java.lang.Object obj, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, s);
        if(property == null)
        {
            new PropertyString(obj, s, s1);
        } else
        {
            property.set(s1);
            property.invokeObserver(lastMapInt, 3);
        }
    }

    public static void set(java.lang.Object obj, int i, java.lang.String s)
    {
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
        {
            throw new NullPointerException("Property (" + i + ") NOT present");
        } else
        {
            property.set(s);
            property.invokeObserver(lastMapInt, 3);
            return;
        }
    }

    public static void remove(java.lang.Object obj, java.lang.String s)
    {
        com.maddox.rts.Property.remove(obj, com.maddox.rts.Finger.Int(s));
    }

    public static void remove(java.lang.Object obj, int i)
    {
        if(obj instanceof java.lang.String)
            obj = ((java.lang.String)obj).intern();
        com.maddox.rts.Property property = com.maddox.rts.Property.get(obj, i);
        if(property == null)
            return;
        lastMapInt.remove(property.finger);
        if(lastMapInt.size() == 0 && ((obj instanceof java.lang.String) || (obj instanceof java.lang.Class)))
            propertyHoldMap.remove(obj);
        property.invokeObserver(lastMapInt, 2);
    }

    protected Property(java.lang.Object obj, java.lang.String s)
    {
        if(obj instanceof java.lang.String)
            obj = ((java.lang.String)obj).intern();
        name = s;
        if(s != null)
            finger = com.maddox.rts.Finger.Int(s);
        else
            finger = 0;
        com.maddox.util.HashMapInt hashmapint;
        if(lastOwner != null && lastOwner.get() == obj)
        {
            owner = lastOwner;
            hashmapint = lastMapInt;
            if(hashmapint.containsKey(finger))
                lastAction = 3;
            else
                lastAction = 1;
        } else
        {
            owner = new WeakReference(obj);
            hashmapint = (com.maddox.util.HashMapInt)propertyMap.get(obj);
            if(hashmapint == null)
            {
                hashmapint = new HashMapInt();
                propertyMap.put(obj, hashmapint);
                lastAction = 1;
            } else
            if(hashmapint.containsKey(finger))
                lastAction = 3;
            else
                lastAction = 1;
            lastOwner = owner;
            lastMapInt = hashmapint;
        }
        hashmapint.put(finger, this);
        if(lastAction == 1 && ((obj instanceof java.lang.String) || (obj instanceof java.lang.Class)))
            propertyHoldMap.put(obj, null);
    }

    protected void invokeObserver(com.maddox.util.HashMapInt hashmapint, int i)
    {
        com.maddox.rts.PropertyObserver propertyobserver = (com.maddox.rts.PropertyObserver)hashmapint.get(OBSERVER_ID);
        if(propertyobserver == null)
        {
            return;
        } else
        {
            propertyobserver.invokeListeners(i, this);
            return;
        }
    }

    public static void addListener(java.lang.Object obj, java.lang.Object obj1, boolean flag, boolean flag1)
    {
        com.maddox.rts.PropertyObserver propertyobserver = (com.maddox.rts.PropertyObserver)com.maddox.rts.Property.get(obj, OBSERVER_ID);
        if(propertyobserver == null)
            propertyobserver = new PropertyObserver(obj);
        propertyobserver.addListener(obj1, flag, flag1);
    }

    public static void removeListener(java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.PropertyObserver propertyobserver = (com.maddox.rts.PropertyObserver)com.maddox.rts.Property.get(obj, OBSERVER_ID);
        if(propertyobserver == null)
        {
            return;
        } else
        {
            propertyobserver.removeListener(obj1);
            return;
        }
    }

    public static com.maddox.util.WeakHashMapExt propertyMap = new WeakHashMapExt();
    public static com.maddox.util.HashMapExt propertyHoldMap = new HashMapExt();
    public static final int ADDED = 1;
    public static final int REMOVED = 2;
    public static final int CHANGED = 3;
    public static final java.lang.String OBSERVER = "OBSERVER";
    public static final int OBSERVER_ID = com.maddox.rts.Finger.Int("OBSERVER");
    private java.lang.String name;
    private int finger;
    private java.lang.ref.WeakReference owner;
    private static com.maddox.util.HashMapExt _varsMap = new HashMapExt();
    protected static java.lang.ref.WeakReference lastOwner = null;
    protected static com.maddox.util.HashMapInt lastMapInt = null;
    protected static int lastAction = 0;

}
