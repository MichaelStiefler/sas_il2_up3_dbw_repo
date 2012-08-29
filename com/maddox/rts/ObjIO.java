// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ObjIO.java

package com.maddox.rts;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

// Referenced classes of package com.maddox.rts:
//            RTSException, Property, LDRres, Finger

public class ObjIO
{
    static class Res
    {

        public boolean isEmpty()
        {
            if(name != null)
                return false;
            if(enum != null)
                return false;
            return tip == null;
        }

        public void clear()
        {
            name = null;
            enum = null;
            tip = null;
        }

        java.lang.String name;
        java.lang.Object enum[];
        java.lang.String tip;

        public Res(com.maddox.rts.Res res1)
        {
            name = res1.name;
            enum = res1.enum;
            tip = res1.tip;
        }

        public Res()
        {
        }
    }

    static class FieldCompare
        implements java.util.Comparator
    {

        public int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            java.lang.reflect.Field field1 = (java.lang.reflect.Field)obj;
            java.lang.reflect.Field field2 = (java.lang.reflect.Field)obj1;
            return field1.getName().compareTo(field2.getName());
        }

        FieldCompare()
        {
        }
    }

    static class Node
    {

        public void clear()
        {
            flags = 0;
            enum = null;
            min = null;
            max = null;
            step = null;
            get = null;
            set = null;
            edget = null;
            edset = null;
            validate = null;
        }

        public boolean isEmpty()
        {
            if(flags != 0)
                return false;
            if(enum != null)
                return false;
            if(min != null)
                return false;
            if(max != null)
                return false;
            if(get != null)
                return false;
            if(set != null)
                return false;
            if(edget != null)
                return false;
            if(edset != null)
                return false;
            return validate == null;
        }

        public void set(com.maddox.rts.Node node, java.lang.Object obj, com.maddox.rts.NodeObjects nodeobjects)
        {
            flags |= node.flags & 1;
            if((flags & 0x100000) == 0 && (node.flags & 0x100000) != 0)
            {
                flags |= 0x100000;
                enum = node.enum;
            }
            if((flags & 0x200000) == 0 && (node.flags & 0x200000) != 0)
            {
                flags |= 0x200000;
                min = node.min;
                max = node.max;
                step = node.step;
            }
            if((flags & 0x400000) == 0 && (node.flags & 0x400000) != 0)
            {
                flags |= node.flags & 0x1400000;
                get = node.get;
                nodeobjects.get = obj;
            }
            if((flags & 0x800000) == 0 && (node.flags & 0x800000) != 0)
            {
                flags |= node.flags & 0x2800000;
                set = node.set;
                nodeobjects.set = obj;
            }
            if((flags & 0x4000000) == 0 && (node.flags & 0x4000000) != 0)
            {
                flags |= node.flags & 0x14000000;
                edget = node.edget;
                nodeobjects.edget = obj;
            }
            if((flags & 0x8000000) == 0 && (node.flags & 0x8000000) != 0)
            {
                flags |= node.flags & 0x28000000;
                edset = node.edset;
                nodeobjects.edset = obj;
            }
            if((flags & 0x40000000) == 0 && (node.flags & 0x40000000) != 0)
            {
                flags |= 0x40000000;
                validate = node.validate;
                nodeobjects.validate = obj;
            }
        }

        public int flags;
        public java.lang.Object enum[];
        public java.lang.Object min;
        public java.lang.Object max;
        public java.lang.Object step;
        public java.lang.reflect.Method get;
        public java.lang.reflect.Method set;
        public java.lang.reflect.Method edget;
        public java.lang.reflect.Method edset;
        public java.lang.reflect.Method validate;

        public Node()
        {
        }

        public Node(com.maddox.rts.Node node)
        {
            flags = node.flags;
            enum = node.enum;
            min = node.min;
            max = node.max;
            step = node.step;
            get = node.get;
            set = node.set;
            edget = node.edget;
            edset = node.edset;
            validate = node.validate;
        }
    }

    static class NodeObjects
    {

        public void clear()
        {
            get = null;
            set = null;
            edget = null;
            edset = null;
            validate = null;
        }

        public boolean isEmpty()
        {
            if(get != null)
                return false;
            if(set != null)
                return false;
            if(edget != null)
                return false;
            if(edset != null)
                return false;
            return validate == null;
        }

        public java.lang.Object get;
        public java.lang.Object set;
        public java.lang.Object edget;
        public java.lang.Object edset;
        public java.lang.Object validate;

        public NodeObjects()
        {
        }

        public NodeObjects(com.maddox.rts.NodeObjects nodeobjects)
        {
            get = nodeobjects.get;
            set = nodeobjects.set;
            edget = nodeobjects.edget;
            edset = nodeobjects.edset;
            validate = nodeobjects.validate;
        }
    }


    public static void setFriendPackages(java.lang.String as[])
    {
        friendPackage = as;
    }

    public static java.lang.String classGetName(java.lang.Class class1)
    {
        java.lang.String s = class1.getName();
        for(int i = 0; i < friendPackage.length; i++)
        {
            java.lang.String s1 = friendPackage[i];
            if(s.startsWith(s1))
                return s.substring(s1.length());
        }

        return s;
    }

    public static java.lang.Class classForName(java.lang.String s)
        throws java.lang.ClassNotFoundException
    {
        int i = 0;
_L1:
        if(i >= friendPackage.length)
            break MISSING_BLOCK_LABEL_43;
        return java.lang.Class.forName(friendPackage[i] + s);
        java.lang.Exception exception;
        exception;
        i++;
          goto _L1
        return java.lang.Class.forName(s);
        java.lang.Throwable throwable;
        throwable;
        throw new ClassNotFoundException(throwable.getMessage());
    }

    private static void checkFieldParams(java.lang.Class class1, java.lang.String s)
    {
        if(class1 == null)
            throw new RTSException("Parameter 'class' == null");
        if(s == null)
            throw new RTSException("Parameter 'fieldName' == null");
        else
            return;
    }

    private static void checkFieldParams(java.lang.Class class1, java.lang.String as[])
    {
        if(class1 == null)
            throw new RTSException("Parameter 'class' == null");
        if(as == null)
            throw new RTSException("Parameter 'fieldNames' == null");
        else
            return;
    }

    private static com.maddox.rts.Node getNode(java.lang.Class class1, java.lang.String s)
    {
        java.lang.String s1 = s + "$IO";
        com.maddox.rts.Node node = (com.maddox.rts.Node)com.maddox.rts.Property.value(class1, s1, null);
        if(node == null)
        {
            node = new Node();
            com.maddox.rts.Property.set(class1, s1, node);
        }
        return node;
    }

    private static void initNode(java.lang.Class class1, java.lang.String s)
    {
        java.lang.String s1 = s + "$IO";
        if(!com.maddox.rts.Property.containsValue(class1, s1))
            com.maddox.rts.Property.set(class1, s1, (java.lang.Object)null);
    }

    private static com.maddox.rts.Node getNode(java.lang.Class class1)
    {
        com.maddox.rts.Node node = (com.maddox.rts.Node)com.maddox.rts.Property.value(class1, "$ObjIO", null);
        if(node == null)
        {
            node = new Node();
            com.maddox.rts.Property.set(class1, "$ObjIO", node);
        }
        return node;
    }

    public static java.lang.Class toWrapperClass(java.lang.Class class1)
    {
        if(class1.isPrimitive())
            if(class1 == java.lang.Boolean.TYPE)
                class1 = java.lang.Boolean.class;
            else
            if(class1 == java.lang.Byte.TYPE)
                class1 = java.lang.Byte.class;
            else
            if(class1 == java.lang.Short.TYPE)
                class1 = java.lang.Short.class;
            else
            if(class1 == java.lang.Character.TYPE)
                class1 = java.lang.Character.class;
            else
            if(class1 == java.lang.Integer.TYPE)
                class1 = java.lang.Integer.class;
            else
            if(class1 == java.lang.Long.TYPE)
                class1 = java.lang.Long.class;
            else
            if(class1 == java.lang.Float.TYPE)
                class1 = java.lang.Float.class;
            else
            if(class1 == java.lang.Double.TYPE)
                class1 = java.lang.Double.class;
        return class1;
    }

    private static java.lang.reflect.Field[] getFields(java.lang.Class class1)
    {
        java.lang.reflect.Field afield[] = (java.lang.reflect.Field[])(java.lang.reflect.Field[])com.maddox.rts.Property.value(class1, "$ClassIOFields$", null);
        if(afield != null)
            return afield;
        if(!com.maddox.rts.Property.vars(_fldPropLst, class1))
            return filedsEmpty;
        int i = _fldPropLst.size();
label0:
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = (java.lang.String)_fldPropLst.get(j);
            if(s == null || !s.endsWith("$IO"))
                continue;
            int l = s.indexOf('.');
            if(l < 0)
                l = s.length() - "$IO".length();
            int i1 = s.indexOf('[');
            if(i1 > 0 && i1 < l)
                l = i1;
            java.lang.String s1 = s.substring(0, l);
            Object obj = null;
            java.lang.Class class2 = class1;
            do
            {
                if(class2 == null)
                    continue label0;
                try
                {
                    java.lang.reflect.Field field1 = class2.getDeclaredField(s1);
                    if(!_fldLst.contains(field1))
                        _fldLst.add(field1);
                    continue label0;
                }
                catch(java.lang.Exception exception)
                {
                    class2 = class2.getSuperclass();
                }
            } while(true);
        }

        _fldPropLst.clear();
        i = _fldLst.size();
        if(i > 0)
        {
            afield = new java.lang.reflect.Field[i];
            for(int k = 0; k < i; k++)
                afield[k] = (java.lang.reflect.Field)_fldLst.get(k);

            java.util.Arrays.sort(afield, _fldsCompare);
            _fldLst.clear();
        } else
        {
            afield = filedsEmpty;
        }
        com.maddox.rts.Property.set(class1, "$ClassIOFields$", afield);
        return afield;
    }

    private static java.lang.Class getFieldType(java.lang.Class class1, java.lang.String s, boolean flag)
    {
        getFieldType_arrays = 0;
        java.lang.Class class2 = class1;
        int i = s.length();
        int j = 0;
        int k = 0;
        while(j < i) 
        {
            char c = s.charAt(j);
            if(c == '.')
            {
                if(k < j)
                    class2 = com.maddox.rts.ObjIO.getFieldTypeOne(class2, s.substring(k, j), false);
                k = ++j;
            } else
            if(c == '[')
            {
                if(k < j)
                    class2 = com.maddox.rts.ObjIO.getFieldTypeOne(class2, s.substring(k, j), true);
                k = j += 2;
            } else
            {
                j++;
            }
        }
        if(k < j)
            class2 = com.maddox.rts.ObjIO.getFieldTypeOne(class2, s.substring(k, j), false);
        if(flag)
            return com.maddox.rts.ObjIO.toWrapperClass(class2);
        else
            return class2;
    }

    private static java.lang.Class getFieldTypeOne(java.lang.Class class1, java.lang.String s, boolean flag)
    {
        try
        {
            java.lang.reflect.Field field1 = class1.getField(s);
            class1 = field1.getType();
            if(flag && class1.isArray())
                class1 = class1.getComponentType();
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.ObjIO.printDebug(exception);
            throw new RTSException("Filed '" + s + "' not found in class " + class1.getName());
        }
        if(flag)
            getFieldType_arrays++;
        return class1;
    }

    public static void fieldsSuperclass(java.lang.Class class1)
    {
        if(class1 == null)
            throw new RTSException("Parameter 'class' == null");
        java.lang.Class class2 = class1.getSuperclass();
        if(class2 == null)
            return;
        if(!com.maddox.rts.Property.vars(_fldPropLst, class2))
            return;
        int i = _fldPropLst.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = (java.lang.String)_fldPropLst.get(j);
            if(s != null && s.endsWith("$IO") && !com.maddox.rts.Property.containsValue(class1, s))
                com.maddox.rts.Property.set(class1, s, com.maddox.rts.Property.value(class2, s, null));
        }

        _fldPropLst.clear();
    }

    public static void fieldsAllSuperclasses(java.lang.Class class1)
    {
        if(class1 == null)
        {
            throw new RTSException("Parameter 'class' == null");
        } else
        {
            com.maddox.rts.ObjIO.fieldsAllSuperclasses(class1, class1.getSuperclass());
            return;
        }
    }

    private static void fieldsAllSuperclasses(java.lang.Class class1, java.lang.Class class2)
    {
        if(class2 == null)
            return;
        com.maddox.rts.ObjIO.fieldsAllSuperclasses(class1, class2.getSuperclass());
        if(!com.maddox.rts.Property.vars(_fldPropLst, class2))
            return;
        int i = _fldPropLst.size();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = (java.lang.String)_fldPropLst.get(j);
            if(s != null && s.endsWith("$IO") && !com.maddox.rts.Property.containsValue(class1, s))
                com.maddox.rts.Property.set(class1, s, com.maddox.rts.Property.value(class2, s, null));
        }

        _fldPropLst.clear();
    }

    public static void field(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.ObjIO.initNode(class1, s);
    }

    public static void fields(java.lang.Class class1, java.lang.String as[])
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, as);
        for(int i = 0; i < as.length; i++)
            if(as[i] != null)
                com.maddox.rts.ObjIO.initNode(class1, as[i]);

    }

    public static void edReadOnly(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        node.flags |= 1;
    }

    public static void enum(java.lang.Class class1, java.lang.String s, java.lang.Object aobj[])
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(aobj != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, true);
            java.lang.Class class3 = ((java.lang.Object) (aobj)).getClass().getComponentType();
            if((node.flags & 0x2000000) != 0)
                class3 = java.lang.String.class;
            if(class3 != class2)
                throw new RTSException("Class elements of enum array (" + class3.getName() + ") not equal type of class field (" + class2.getName() + ")");
        }
        node.enum = aobj;
        node.flags |= 0x100000;
    }

    private static java.lang.Object checkEnum(java.lang.Object obj, java.lang.Object aobj[])
    {
        for(int i = 0; i < aobj.length; i++)
            if(obj.equals(aobj[i]))
                return obj;

        return aobj[0];
    }

    public static void enum(java.lang.Class class1, java.lang.String s, byte abyte0[])
    {
        if(abyte0 == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Byte abyte[] = new java.lang.Byte[abyte0.length];
        for(int i = 0; i < abyte0.length; i++)
            abyte[i] = new Byte(abyte0[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (abyte)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, short aword0[])
    {
        if(aword0 == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Short ashort[] = new java.lang.Short[aword0.length];
        for(int i = 0; i < aword0.length; i++)
            ashort[i] = new Short(aword0[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (ashort)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, char ac[])
    {
        if(ac == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Character acharacter[] = new java.lang.Character[ac.length];
        for(int i = 0; i < ac.length; i++)
            acharacter[i] = new Character(ac[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (acharacter)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, int ai[])
    {
        if(ai == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Integer ainteger[] = new java.lang.Integer[ai.length];
        for(int i = 0; i < ai.length; i++)
            ainteger[i] = new Integer(ai[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (ainteger)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, long al[])
    {
        if(al == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Long along[] = new java.lang.Long[al.length];
        for(int i = 0; i < al.length; i++)
            along[i] = new Long(al[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (along)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, float af[])
    {
        if(af == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Float afloat[] = new java.lang.Float[af.length];
        for(int i = 0; i < af.length; i++)
            afloat[i] = new Float(af[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (afloat)));
    }

    public static void enum(java.lang.Class class1, java.lang.String s, double ad[])
    {
        if(ad == null)
        {
            com.maddox.rts.ObjIO.enum(class1, s, (java.lang.Object[])null);
            return;
        }
        java.lang.Double adouble[] = new java.lang.Double[ad.length];
        for(int i = 0; i < ad.length; i++)
            adouble[i] = new Double(ad[i]);

        com.maddox.rts.ObjIO.enum(class1, s, ((java.lang.Object []) (adouble)));
    }

    public static void range(java.lang.Class class1, java.lang.String s, java.lang.Object obj, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(obj != null || obj1 != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, true);
            if(obj != null && class2 != obj.getClass())
                throw new RTSException("Class of min patameter (" + obj.getClass().getName() + ") not equal type of class field (" + class2.getName() + ")");
            if(obj1 != null && class2 != obj1.getClass())
                throw new RTSException("Class of max patameter (" + obj1.getClass().getName() + ") not equal type of class field (" + class2.getName() + ")");
            if(obj2 != null && class2 != obj2.getClass())
                throw new RTSException("Class of step patameter (" + obj2.getClass().getName() + ") not equal type of class field (" + class2.getName() + ")");
        }
        node.min = obj;
        node.max = obj1;
        node.step = obj2;
        node.flags |= 0x200000;
    }

    private static java.lang.Object checkRange(java.lang.Object obj, java.lang.Object obj1, java.lang.Object obj2)
    {
        if(!(obj instanceof java.lang.Comparable))
            return obj;
        java.lang.Comparable comparable;
        comparable = (java.lang.Comparable)obj;
        if(obj1 != null && comparable.compareTo(obj1) < 0)
            comparable = (java.lang.Comparable)obj1;
        if(obj2 != null && comparable.compareTo(obj2) > 0)
            comparable = (java.lang.Comparable)obj2;
        return comparable;
        java.lang.Exception exception;
        exception;
        com.maddox.rts.ObjIO.printDebug(exception);
        return obj;
    }

    public static void range(java.lang.Class class1, java.lang.String s, byte byte0, byte byte1, byte byte2)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Byte(byte0), new Byte(byte1), new Byte(byte2));
    }

    public static void range(java.lang.Class class1, java.lang.String s, short word0, short word1, short word2)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Short(word0), new Short(word1), new Short(word2));
    }

    public static void range(java.lang.Class class1, java.lang.String s, char c, char c1)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Character(c), new Character(c1), null);
    }

    public static void range(java.lang.Class class1, java.lang.String s, int i, int j, int k)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Integer(i), new Integer(j), new Integer(k));
    }

    public static void range(java.lang.Class class1, java.lang.String s, long l, long l1, long l2)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Long(l), new Long(l1), new Long(l2));
    }

    public static void range(java.lang.Class class1, java.lang.String s, float f, float f1, float f2)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Float(f), new Float(f1), new Float(f2));
    }

    public static void range(java.lang.Class class1, java.lang.String s, double d, double d1, double d2)
    {
        com.maddox.rts.ObjIO.range(class1, s, new Double(d), new Double(d1), new Double(d2));
    }

    public static void get(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(s1 != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, null));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != class2)
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal class field '" + class2.getName() + "'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.get = method;
        } else
        {
            node.get = null;
        }
        node.flags |= 0x400000;
    }

    public static void getEd(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(s1 != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, null));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != class2)
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal class field '" + class2.getName() + "'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.edget = method;
        } else
        {
            node.edget = null;
        }
        node.flags |= 0x4000000;
    }

    private static java.lang.Class[] fieldClassArray(int i, java.lang.Class class1)
    {
        int j = i;
        if(class1 != null)
            j++;
        if(j == 0)
            return null;
        if(_fieldsClass != null && _fieldsClass.length != j)
            _fieldsClass = null;
        if(_fieldsClass == null)
            _fieldsClass = new java.lang.Class[j];
        for(int k = 0; k < i; k++)
            _fieldsClass[k] = java.lang.Integer.TYPE;

        if(class1 != null)
            _fieldsClass[i] = class1;
        return _fieldsClass;
    }

    public static void set(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(s1 != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, class2));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.set = method;
        } else
        {
            node.set = null;
        }
        node.flags |= 0x800000;
    }

    public static void setEd(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(s1 != null)
        {
            java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, class2));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.edset = method;
        } else
        {
            node.edset = null;
        }
        node.flags |= 0x8000000;
    }

    public static void access(java.lang.Class class1, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        com.maddox.rts.ObjIO.get(class1, s, s1);
        com.maddox.rts.ObjIO.set(class1, s, s2);
    }

    public static void accessEd(java.lang.Class class1, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        com.maddox.rts.ObjIO.getEd(class1, s, s1);
        com.maddox.rts.ObjIO.setEd(class1, s, s2);
    }

    public static void access(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.get(class1, s, "ioGet" + s);
        com.maddox.rts.ObjIO.set(class1, s, "ioSet" + s);
    }

    public static void accessEd(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.getEd(class1, s, "edGet" + s);
        com.maddox.rts.ObjIO.setEd(class1, s, "edSet" + s);
    }

    public static void accessStr(java.lang.Class class1, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
        if(s1 != null)
        {
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, null));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != (java.lang.String.class))
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal 'String'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.get = method;
        } else
        {
            node.get = null;
        }
        node.flags |= 0x1400000;
        if(s2 != null)
        {
            java.lang.reflect.Method method1 = null;
            try
            {
                method1 = class1.getMethod(s2, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, java.lang.String.class));
                java.lang.Class class4 = method1.getReturnType();
                if(class4 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s2 + "' returned type '" + class4.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.rts.ObjIO.printDebug(exception1);
                throw new RTSException("Method '" + s2 + "' not found in class " + class1.getName());
            }
            node.set = method1;
        } else
        {
            node.set = null;
        }
        node.flags |= 0x2800000;
    }

    public static void accessEdStr(java.lang.Class class1, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        java.lang.Class class2 = com.maddox.rts.ObjIO.getFieldType(class1, s, false);
        if(s1 != null)
        {
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, null));
                java.lang.Class class3 = method.getReturnType();
                if(class3 != (java.lang.String.class))
                    throw new RTSException("Method '" + s1 + "' returned type '" + class3.getName() + "' not equal 'String'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.edget = method;
        } else
        {
            node.edget = null;
        }
        node.flags |= 0x14000000;
        if(s2 != null)
        {
            java.lang.reflect.Method method1 = null;
            try
            {
                method1 = class1.getMethod(s2, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, java.lang.String.class));
                java.lang.Class class4 = method1.getReturnType();
                if(class4 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s2 + "' returned type '" + class4.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.rts.ObjIO.printDebug(exception1);
                throw new RTSException("Method '" + s2 + "' not found in class " + class1.getName());
            }
            node.edset = method1;
        } else
        {
            node.edset = null;
        }
        node.flags |= 0x28000000;
    }

    public static void accessStr(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.accessStr(class1, s, "ioGet" + s, "ioSet" + s);
    }

    public static void accessEdStr(java.lang.Class class1, java.lang.String s)
    {
        com.maddox.rts.ObjIO.accessEdStr(class1, s, "edGet" + s, "edSet" + s);
    }

    public static void validate(java.lang.Class class1, java.lang.String s, java.lang.String s1)
    {
        com.maddox.rts.ObjIO.checkFieldParams(class1, s);
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1, s);
        if(s1 != null)
        {
            com.maddox.rts.ObjIO.getFieldType(class1, s, false);
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s1, com.maddox.rts.ObjIO.fieldClassArray(getFieldType_arrays, null));
                java.lang.Class class2 = method.getReturnType();
                if(class2 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s1 + "' returned type '" + class2.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s1 + "' not found in class " + class1.getName());
            }
            node.validate = method;
        } else
        {
            node.validate = null;
        }
        node.flags |= 0x40000000;
    }

    public static void validate(java.lang.Class class1, java.lang.String s)
    {
        if(class1 == null)
            throw new RTSException("Parameter 'class' == null");
        com.maddox.rts.Node node = com.maddox.rts.ObjIO.getNode(class1);
        if(s != null)
        {
            java.lang.reflect.Method method = null;
            try
            {
                method = class1.getMethod(s, null);
                java.lang.Class class2 = method.getReturnType();
                if(class2 != java.lang.Void.TYPE)
                    throw new RTSException("Method '" + s + "' returned type '" + class2.getName() + "' not equal 'void'");
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.ObjIO.printDebug(exception);
                throw new RTSException("Method '" + s + "' not found in class " + class1.getName());
            }
            node.validate = method;
        } else
        {
            node.validate = null;
        }
        node.flags |= 0x40000000;
    }

    private static void stackClear()
    {
        stackPtr = -1;
    }

    private static int stackSize()
    {
        return stackPtr + 1;
    }

    private static void stackPop()
    {
        stackClasses.set(stackPtr, null);
        stackObjects.set(stackPtr, null);
        stackFields.set(stackPtr, null);
        stackBundles.set(stackPtr, null);
        stackPtr--;
        if(stackPtr >= 0)
            com.maddox.rts.ObjIO.setCurObject();
    }

    private static void stackPop(int i)
    {
        for(; stackPtr > i; stackPtr--)
        {
            stackClasses.set(stackPtr, null);
            stackObjects.set(stackPtr, null);
            stackFields.set(stackPtr, null);
            stackBundles.set(stackPtr, null);
        }

        if(stackPtr >= 0)
            com.maddox.rts.ObjIO.setCurObject();
    }

    private static void stackPush(java.lang.Object obj, java.lang.Class class1)
    {
        stackPtr++;
        if(stackPtr + 1 > stackObjects.size())
        {
            stackClasses.add(null);
            stackObjects.add(null);
            stackFields.add(null);
            stackBundles.add(null);
        }
        com.maddox.rts.ObjIO.setCurObject(obj, class1);
    }

    private static void setCurObject(java.lang.Object obj, java.lang.Class class1)
    {
        stackClasses.set(stackPtr, class1);
        stackObjects.set(stackPtr, obj);
        com.maddox.rts.ObjIO.setCurObject();
        if(bUseBundle)
            if(class1.isPrimitive() || class1 == (java.lang.String.class) || class1 == (java.lang.StringBuffer.class) || class1.isArray())
            {
                stackBundles.set(stackPtr, null);
            } else
            {
                java.util.ResourceBundle resourcebundle = null;
                try
                {
                    resourcebundle = java.util.ResourceBundle.getBundle(class1.getName() + "IO", java.util.Locale.getDefault(), com.maddox.rts.LDRres.loader());
                }
                catch(java.lang.Exception exception) { }
                stackBundles.set(stackPtr, resourcebundle);
            }
    }

    private static void setCurObject()
    {
        curObjectNode.clear();
        curObjectObjects.clear();
        for(int i = 0; i <= stackPtr; i++)
        {
            java.lang.Class class1 = (java.lang.Class)stackClasses.get(i);
            int j = 0;
            for(int k = i; k < stackPtr; k++)
            {
                java.lang.Object obj = stackFields.get(k);
                if(obj instanceof java.lang.reflect.Field)
                {
                    java.lang.reflect.Field field1 = (java.lang.reflect.Field)obj;
                    if(k != i)
                        j = com.maddox.rts.Finger.incInt(j, ".");
                    j = com.maddox.rts.Finger.incInt(j, field1.getName());
                } else
                {
                    j = com.maddox.rts.Finger.incInt(j, "[]");
                }
            }

            j = com.maddox.rts.Finger.incInt(j, "$ObjIO");
            com.maddox.rts.Node node = (com.maddox.rts.Node)com.maddox.rts.Property.value(class1, j, null);
            if(node != null)
                curObjectNode.set(node, stackObjects.get(i), curObjectObjects);
        }

    }

    private static boolean setCurField(boolean flag, java.lang.reflect.Field field1, boolean flag1)
    {
        int i = field1.getModifiers();
        if((i & 0x10) != 0)
            return false;
        if(flag1)
        {
            if((i & 8) == 0)
                return false;
        } else
        if((i & 8) != 0)
            return false;
        com.maddox.rts.ObjIO.setCurFieldNode(field1);
        return true;
    }

    private static boolean setCurField(boolean flag, java.lang.Integer integer, boolean flag1)
    {
        com.maddox.rts.ObjIO.setCurFieldNode(integer);
        return true;
    }

    private static boolean setCurField(boolean flag, java.lang.reflect.Field field1)
        throws java.lang.IllegalAccessException, java.lang.InstantiationException
    {
        int i = field1.getModifiers();
        if((i & 0x10) != 0)
            return false;
        com.maddox.rts.ObjIO.setCurFieldNode(field1);
        if(flag && (curFieldNode.flags & 1) != 0)
            return false;
        if((i & 8) == 0 && stackObjects.get(stackPtr) == null)
        {
            java.lang.Class class1 = (java.lang.Class)stackClasses.get(stackPtr);
            java.lang.Object obj = class1.newInstance();
            com.maddox.rts.ObjIO.setCurObject(obj, class1);
        }
        return true;
    }

    private static boolean setCurField(boolean flag, java.lang.Integer integer)
        throws java.lang.IllegalAccessException, java.lang.InstantiationException
    {
        com.maddox.rts.ObjIO.setCurFieldNode(integer);
        return !flag || (curFieldNode.flags & 1) == 0;
    }

    private static void setCurFieldNode(java.lang.Object obj)
    {
        stackFields.set(stackPtr, obj);
        curFieldNode.clear();
        curFieldObjects.clear();
        for(int i = 0; i <= stackPtr; i++)
        {
            java.lang.Class class1 = (java.lang.Class)stackClasses.get(i);
            int j = 0;
            java.lang.Object obj1 = stackFields.get(i);
            if(obj1 instanceof java.lang.reflect.Field)
            {
                java.lang.reflect.Field field1 = (java.lang.reflect.Field)obj1;
                j = com.maddox.rts.Finger.Int(field1.getName());
            } else
            {
                j = com.maddox.rts.Finger.Int("[]");
            }
            for(int k = i + 1; k <= stackPtr; k++)
            {
                java.lang.Object obj2 = stackFields.get(k);
                if(obj2 instanceof java.lang.reflect.Field)
                {
                    java.lang.reflect.Field field2 = (java.lang.reflect.Field)obj2;
                    j = com.maddox.rts.Finger.incInt(j, ".");
                    j = com.maddox.rts.Finger.incInt(j, field2.getName());
                } else
                {
                    j = com.maddox.rts.Finger.incInt(j, "[]");
                }
            }

            j = com.maddox.rts.Finger.incInt(j, "$IO");
            com.maddox.rts.Node node = (com.maddox.rts.Node)com.maddox.rts.Property.value(class1, j, null);
            if(node != null)
                curFieldNode.set(node, stackObjects.get(i), curFieldObjects);
        }

    }

    private static int fillObjectArray(java.lang.Object aobj[])
    {
        int i = 0;
        for(int j = 0; j <= stackPtr; j++)
        {
            java.lang.Object obj = stackFields.get(j);
            if(!(obj instanceof java.lang.Integer))
                continue;
            if(aobj != null)
                aobj[i] = obj;
            i++;
        }

        return i;
    }

    private static java.lang.Object[] fieldObjectArray(java.lang.Object obj, boolean flag)
    {
        int i = com.maddox.rts.ObjIO.fillObjectArray(null);
        if(flag)
            i++;
        if(i == 0)
            return null;
        if(_fieldsObject != null && _fieldsObject.length != i)
            _fieldsObject = null;
        if(_fieldsObject == null)
            _fieldsObject = new java.lang.Object[i];
        com.maddox.rts.ObjIO.fillObjectArray(_fieldsObject);
        if(flag)
            _fieldsObject[i - 1] = obj;
        return _fieldsObject;
    }

    private static java.lang.Object fieldGet(boolean flag)
    {
        java.lang.Object obj;
        java.lang.Object obj1;
        obj = stackObjects.get(stackPtr);
        obj1 = stackFields.get(stackPtr);
        if(flag && (curFieldNode.flags & 0x4000000) != 0)
        {
            if(curFieldNode.edget != null)
                return curFieldNode.edget.invoke(curFieldObjects.edget, com.maddox.rts.ObjIO.fieldObjectArray(null, false));
            break MISSING_BLOCK_LABEL_148;
        }
        if((curFieldNode.flags & 0x400000) != 0)
        {
            if(curFieldNode.get != null)
                return curFieldNode.get.invoke(curFieldObjects.get, com.maddox.rts.ObjIO.fieldObjectArray(null, false));
            break MISSING_BLOCK_LABEL_148;
        }
        java.lang.Object obj2;
        if(!(obj1 instanceof java.lang.reflect.Field))
            break MISSING_BLOCK_LABEL_126;
        obj2 = (java.lang.reflect.Field)obj1;
        return ((java.lang.reflect.Field) (obj2)).get(obj);
        obj2 = (java.lang.Integer)obj1;
        return java.lang.reflect.Array.get(obj, ((java.lang.Integer) (obj2)).intValue());
        java.lang.Exception exception;
        exception;
        com.maddox.rts.ObjIO.printDebug(exception);
        return null;
    }

    private static void fieldSet(boolean flag, java.lang.Object obj)
    {
        java.lang.Object obj1 = stackObjects.get(stackPtr);
        java.lang.Object obj2 = stackFields.get(stackPtr);
        try
        {
            if(obj != null)
            {
                if(curFieldNode.min != null || curFieldNode.max != null)
                    obj = com.maddox.rts.ObjIO.checkRange(obj, curFieldNode.min, curFieldNode.max);
                if(curFieldNode.enum != null)
                    obj = com.maddox.rts.ObjIO.checkEnum(obj, curFieldNode.enum);
            }
            if(flag && (curFieldNode.flags & 0x8000000) != 0)
            {
                if(curFieldNode.edset != null)
                    curFieldNode.edset.invoke(curFieldObjects.edset, com.maddox.rts.ObjIO.fieldObjectArray(obj, true));
            } else
            if((curFieldNode.flags & 0x800000) != 0)
            {
                if(curFieldNode.set != null)
                    curFieldNode.set.invoke(curFieldObjects.set, com.maddox.rts.ObjIO.fieldObjectArray(obj, true));
            } else
            if(obj2 instanceof java.lang.reflect.Field)
            {
                java.lang.reflect.Field field1 = (java.lang.reflect.Field)obj2;
                field1.set(obj1, obj);
            } else
            {
                java.lang.Integer integer = (java.lang.Integer)obj2;
                java.lang.reflect.Array.set(obj1, integer.intValue(), obj);
            }
            if(curFieldNode.validate != null)
                curFieldNode.validate.invoke(curFieldObjects.validate, com.maddox.rts.ObjIO.fieldObjectArray(null, false));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.ObjIO.printDebug(exception);
        }
    }

    private static void fieldValidate()
    {
        try
        {
            if(curFieldNode.validate != null)
                curFieldNode.validate.invoke(curFieldObjects.validate, com.maddox.rts.ObjIO.fieldObjectArray(null, false));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.ObjIO.printDebug(exception);
        }
    }

    private static void objectValidate()
    {
        if(curObjectNode.validate == null)
            return;
        if(curObjectObjects.validate == null && !java.lang.reflect.Modifier.isStatic(curObjectNode.validate.getModifiers()))
            return;
        try
        {
            curObjectNode.validate.invoke(curObjectObjects.validate, null);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.ObjIO.printDebug(exception);
        }
    }

    public static boolean toStrings(java.util.Map map, java.lang.Object obj)
    {
        java.lang.Class class1;
        boolean flag;
        if(obj == null)
            return false;
        class1 = (obj instanceof java.lang.Class) ? (java.lang.Class)obj : obj.getClass();
        flag = obj == class1;
        java.lang.reflect.Field afield[] = com.maddox.rts.ObjIO.getFields(class1);
        com.maddox.rts.ObjIO.stackClear();
        com.maddox.rts.ObjIO.stackPush(obj, class1);
        for(int i = 0; i < afield.length; i++)
        {
            if(!com.maddox.rts.ObjIO.setCurField(false, afield[i], flag))
                continue;
            java.lang.Object obj1 = com.maddox.rts.ObjIO.fieldGet(false);
            if(obj1 == null)
                continue;
            _strBuf.setLength(0);
            boolean flag1 = false;
            if((curFieldNode.flags & 0x1000000) != 0)
            {
                com.maddox.rts.ObjIO.putString(_strBuf, obj1.toString());
                flag1 = true;
            } else
            if(afield[i].getType().isPrimitive())
                flag1 = com.maddox.rts.ObjIO._toString(_strBuf, obj1, afield[i].getType(), false);
            else
                flag1 = com.maddox.rts.ObjIO._toString(_strBuf, obj1, obj1.getClass(), afield[i].getType() != obj1.getClass());
            if(flag1)
                map.put(afield[i].getName(), _strBuf.toString());
        }

        com.maddox.rts.ObjIO.stackPop();
        return true;
        java.lang.Exception exception;
        exception;
        com.maddox.rts.ObjIO.printDebug(exception);
        com.maddox.rts.ObjIO.stackPop(-1);
        return false;
    }

    public static java.lang.String toString(java.lang.Object obj, boolean flag)
    {
        if(obj == null)
        {
            return null;
        } else
        {
            _strBuf.setLength(0);
            com.maddox.rts.ObjIO.stackClear();
            boolean flag1 = com.maddox.rts.ObjIO._toString(_strBuf, obj, (obj instanceof java.lang.Class) ? (java.lang.Class)obj : obj.getClass(), flag);
            com.maddox.rts.ObjIO.stackPop(-1);
            return flag1 ? _strBuf.toString() : null;
        }
    }

    public static boolean toString(java.lang.StringBuffer stringbuffer, java.lang.Object obj, boolean flag)
    {
        if(obj == null)
        {
            return false;
        } else
        {
            com.maddox.rts.ObjIO.stackClear();
            boolean flag1 = com.maddox.rts.ObjIO._toString(stringbuffer, obj, (obj instanceof java.lang.Class) ? (java.lang.Class)obj : obj.getClass(), flag);
            com.maddox.rts.ObjIO.stackPop(-1);
            return flag1;
        }
    }

    private static boolean _toString(java.lang.StringBuffer stringbuffer, java.lang.Object obj, java.lang.Class class1, boolean flag)
    {
        boolean flag1;
        int i;
        int j;
        if(obj == null)
            return false;
        flag1 = obj == class1;
        i = stringbuffer.length();
        j = stackPtr;
        if(!class1.isArray())
            break MISSING_BLOCK_LABEL_255;
        java.lang.Class class2;
        int k;
        com.maddox.rts.ObjIO.stackPush(obj, class1);
        class2 = class1.getComponentType();
        k = java.lang.reflect.Array.getLength(obj);
        if(k != 0)
            break MISSING_BLOCK_LABEL_63;
        com.maddox.rts.ObjIO.stackPop();
        return false;
        stringbuffer.append('[');
        if(flag)
        {
            if(class2.isPrimitive())
            {
                stringbuffer.append(com.maddox.rts.ObjIO.classGetName(class1));
            } else
            {
                stringbuffer.append("[L");
                stringbuffer.append(com.maddox.rts.ObjIO.classGetName(class2));
            }
            stringbuffer.append(' ');
        }
        stringbuffer.append(k);
        for(int i1 = 0; i1 < k; i1++)
        {
            com.maddox.rts.ObjIO.setCurField(false, new Integer(i1), flag1);
            stringbuffer.append(',');
            java.lang.Object obj1 = com.maddox.rts.ObjIO.fieldGet(false);
            if(obj1 == null)
                continue;
            if(class2.isPrimitive())
                com.maddox.rts.ObjIO._toString(stringbuffer, obj1, class2, false);
            else
                com.maddox.rts.ObjIO._toString(stringbuffer, obj1, obj1.getClass(), class2 != obj1.getClass());
        }

        com.maddox.rts.ObjIO.stackPop();
        stringbuffer.append(']');
        return true;
        java.lang.Exception exception;
        exception;
        com.maddox.rts.ObjIO.printDebug(exception);
        stringbuffer.setLength(i);
        com.maddox.rts.ObjIO.stackPop(j);
        return false;
        if(class1.isPrimitive() || class1 == (java.lang.String.class) || class1 == (java.lang.StringBuffer.class))
        {
            java.lang.String s = obj.toString();
            com.maddox.rts.ObjIO.putString(stringbuffer, s);
            return true;
        }
        com.maddox.rts.ObjIO.stackPush(obj, class1);
        java.lang.reflect.Field afield[] = com.maddox.rts.ObjIO.getFields(class1);
        stringbuffer.append('(');
        if(flag)
            stringbuffer.append(com.maddox.rts.ObjIO.classGetName(class1));
        int l = 0;
        for(int j1 = 0; j1 < afield.length; j1++)
        {
            if(!com.maddox.rts.ObjIO.setCurField(false, afield[j1], flag1))
                continue;
            java.lang.Object obj2 = com.maddox.rts.ObjIO.fieldGet(false);
            if(obj2 == null)
                continue;
            int k1 = stringbuffer.length();
            if(l == 0)
            {
                if(flag)
                    stringbuffer.append(',');
            } else
            {
                stringbuffer.append(',');
            }
            stringbuffer.append(afield[j1].getName());
            stringbuffer.append('=');
            boolean flag2 = false;
            if((curFieldNode.flags & 0x1000000) != 0)
            {
                com.maddox.rts.ObjIO.putString(stringbuffer, obj2.toString());
                flag2 = true;
            } else
            if(afield[j1].getType().isPrimitive())
                flag2 = com.maddox.rts.ObjIO._toString(stringbuffer, obj2, afield[j1].getType(), false);
            else
                flag2 = com.maddox.rts.ObjIO._toString(stringbuffer, obj2, obj2.getClass(), afield[j1].getType() != obj2.getClass());
            if(flag2)
                l++;
            else
                stringbuffer.setLength(k1);
        }

        com.maddox.rts.ObjIO.stackPop();
        if(l != 0 || flag)
            break MISSING_BLOCK_LABEL_589;
        stringbuffer.setLength(i);
        return false;
        stringbuffer.append(')');
        return true;
        afield;
        com.maddox.rts.ObjIO.printDebug(afield);
        stringbuffer.setLength(i);
        com.maddox.rts.ObjIO.stackPop(j);
        return false;
    }

    private static void putString(java.lang.StringBuffer stringbuffer, java.lang.String s)
    {
        if(s == null || s.length() == 0)
        {
            stringbuffer.append('"');
            stringbuffer.append('"');
        } else
        {
            boolean flag = false;
            int i = 0;
            do
            {
                if(i >= s.length())
                    break;
                char c = s.charAt(i);
                if(c <= ' ' || c == '=' || c == '"' || c == ',' || c == '[' || c == ']' || c == '(' || c == ')')
                {
                    flag = true;
                    break;
                }
                i++;
            } while(true);
            if(flag)
            {
                stringbuffer.append('"');
                for(int j = 0; j < s.length(); j++)
                {
                    char c1 = s.charAt(j);
                    if(c1 == '"')
                        stringbuffer.append('\\');
                    stringbuffer.append(c1);
                }

                stringbuffer.append('"');
            } else
            {
                stringbuffer.append(s);
            }
        }
    }

    public static java.lang.Object fromStrings(java.lang.Object obj, java.lang.Class class1, java.util.Map map)
    {
        if(map.size() == 0)
            return com.maddox.rts.ObjIO.fromString(obj, class1, "()");
        _strBuf.setLength(0);
        _strBuf.append('(');
        java.util.Iterator iterator = map.entrySet().iterator();
        int i = 0;
        java.util.Map.Entry entry;
        for(; iterator.hasNext(); _strBuf.append((java.lang.String)entry.getValue()))
        {
            if(i > 0)
                _strBuf.append(',');
            i++;
            entry = (java.util.Map.Entry)iterator.next();
            _strBuf.append((java.lang.String)entry.getKey());
            _strBuf.append('=');
        }

        _strBuf.append(')');
        com.maddox.rts.ObjIO.stackClear();
        return com.maddox.rts.ObjIO._fromString(false, obj, class1, _strBuf);
    }

    public static java.lang.Object fromString(java.lang.Object obj, java.lang.Class class1, java.lang.String s)
    {
        _strBuf.setLength(0);
        _strBuf.append(s);
        com.maddox.rts.ObjIO.stackClear();
        return com.maddox.rts.ObjIO._fromString(false, obj, class1, _strBuf);
    }

    public static java.lang.Object fromString(java.lang.Object obj, java.lang.String s)
    {
        return com.maddox.rts.ObjIO.fromString(obj, null, s);
    }

    public static java.lang.Object fromString(java.lang.String s)
    {
        return com.maddox.rts.ObjIO.fromString(null, null, s);
    }

    public static java.lang.Object fromString(java.lang.Object obj, java.lang.Class class1, java.lang.StringBuffer stringbuffer)
    {
        com.maddox.rts.ObjIO.stackClear();
        return com.maddox.rts.ObjIO._fromString(false, obj, class1, stringbuffer);
    }

    public static java.lang.Object fromString(java.lang.Object obj, java.lang.StringBuffer stringbuffer)
    {
        return com.maddox.rts.ObjIO.fromString(obj, null, stringbuffer);
    }

    public static java.lang.Object fromString(java.lang.StringBuffer stringbuffer)
    {
        return com.maddox.rts.ObjIO.fromString(null, null, stringbuffer);
    }

    private static java.lang.Object _fromString(boolean flag, java.lang.Object obj, java.lang.Class class1, java.lang.StringBuffer stringbuffer)
    {
        char c;
        java.lang.String s;
        java.lang.Class class2;
        int j;
        c = com.maddox.rts.ObjIO.getChar(stringbuffer);
        s = null;
        if(c != '[')
            break MISSING_BLOCK_LABEL_599;
        class2 = null;
        if(class1 != null)
        {
            if(!class1.isArray())
                return null;
            class2 = class1.getComponentType();
        }
        j = stackPtr;
        s = com.maddox.rts.ObjIO.getWord(stringbuffer);
        if(s == null || s.length() == 0)
            return null;
        if(s.charAt(0) != '[')
            break MISSING_BLOCK_LABEL_298;
        if(s.length() < 2)
            return null;
        char c1 = s.charAt(1);
        c1;
        JVM INSTR tableswitch 66 90: default 290
    //                   66 234
    //                   67 242
    //                   68 282
    //                   69 290
    //                   70 274
    //                   71 290
    //                   72 290
    //                   73 258
    //                   74 266
    //                   75 290
    //                   76 212
    //                   77 290
    //                   78 290
    //                   79 290
    //                   80 290
    //                   81 290
    //                   82 290
    //                   83 250
    //                   84 290
    //                   85 290
    //                   86 290
    //                   87 290
    //                   88 290
    //                   89 290
    //                   90 226;
           goto _L1 _L2 _L3 _L4 _L1 _L5 _L1 _L1 _L6 _L7 _L1 _L8 _L1 _L1 _L1 _L1 _L1 _L1 _L9 _L1 _L1 _L1 _L1 _L1 _L1 _L10
_L8:
        class2 = com.maddox.rts.ObjIO.classForName(s.substring(2));
        break; /* Loop/switch isn't completed */
_L10:
        class2 = java.lang.Boolean.TYPE;
        break; /* Loop/switch isn't completed */
_L2:
        class2 = java.lang.Byte.TYPE;
        break; /* Loop/switch isn't completed */
_L3:
        class2 = java.lang.Character.TYPE;
        break; /* Loop/switch isn't completed */
_L9:
        class2 = java.lang.Short.TYPE;
        break; /* Loop/switch isn't completed */
_L6:
        class2 = java.lang.Integer.TYPE;
        break; /* Loop/switch isn't completed */
_L7:
        class2 = java.lang.Long.TYPE;
        break; /* Loop/switch isn't completed */
_L5:
        class2 = java.lang.Float.TYPE;
        break; /* Loop/switch isn't completed */
_L4:
        class2 = java.lang.Double.TYPE;
        break; /* Loop/switch isn't completed */
_L1:
        return null;
        s = com.maddox.rts.ObjIO.getWord(stringbuffer);
        if(class2 == null)
            return null;
        int l;
        int k = java.lang.Integer.parseInt(s);
        if(obj != null)
        {
            java.lang.Class class3 = obj.getClass();
            if(!class3.isArray() || class3.getComponentType() != class2)
            {
                obj = null;
            } else
            {
                int i1 = java.lang.reflect.Array.getLength(obj);
                if(i1 != k)
                    obj = null;
            }
        }
        if(obj == null)
            obj = java.lang.reflect.Array.newInstance(class2, k);
        com.maddox.rts.ObjIO.stackPush(obj, obj.getClass());
        l = 0;
_L13:
        c = com.maddox.rts.ObjIO.getChar(stringbuffer);
        if(c != ']') goto _L12; else goto _L11
_L11:
        com.maddox.rts.ObjIO.stackPop();
        return obj;
_L12:
        if(c == ',')
        {
            com.maddox.rts.ObjIO.setCurField(flag, new Integer(l));
            java.lang.Object obj1 = null;
            java.lang.Object obj3 = null;
            if((curFieldNode.flags & 0x2000000) != 0)
                obj3 = com.maddox.rts.ObjIO.getWord(stringbuffer);
            else
            if(class2.isPrimitive() || class2 == (java.lang.String.class) || class2 == (java.lang.StringBuffer.class))
            {
                obj3 = com.maddox.rts.ObjIO._fromString(flag, null, class2, stringbuffer);
            } else
            {
                obj1 = com.maddox.rts.ObjIO.fieldGet(flag);
                obj3 = com.maddox.rts.ObjIO._fromString(flag, obj1, obj1 != null ? obj1.getClass() : class2, stringbuffer);
            }
            if(obj3 != obj1)
                com.maddox.rts.ObjIO.fieldSet(flag, obj3);
            else
                com.maddox.rts.ObjIO.fieldValidate();
            break MISSING_BLOCK_LABEL_579;
        }
        com.maddox.rts.ObjIO.stackPop();
        return null;
        try
        {
            l++;
        }
        catch(java.lang.Exception exception3)
        {
            com.maddox.rts.ObjIO.printDebug(exception3);
            com.maddox.rts.ObjIO.stackPop(j);
            return null;
        }
          goto _L13
        if(c == '(')
        {
            s = com.maddox.rts.ObjIO.getWord(stringbuffer);
            c = com.maddox.rts.ObjIO.peekChar(stringbuffer);
            if(c == ',' || c == ')')
            {
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s);
                    if(obj != null && obj.getClass() != class1)
                        obj = null;
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.rts.ObjIO.printDebug(exception);
                    return null;
                }
                if(c == ')')
                {
                    com.maddox.rts.ObjIO.getChar(stringbuffer);
                    if(obj == null)
                    {
                        try
                        {
                            obj = class1.newInstance();
                        }
                        catch(java.lang.Exception exception1) { }
                        if(obj != null)
                        {
                            com.maddox.rts.ObjIO.stackPush(obj, class1);
                            com.maddox.rts.ObjIO.objectValidate();
                            com.maddox.rts.ObjIO.stackPop();
                        }
                    }
                    return obj;
                }
            } else
            {
                if(s != null)
                    stringbuffer.insert(0, s);
                stringbuffer.insert(0, ',');
            }
            if(obj == null)
            {
                if(class1 == null)
                    return null;
            } else
            if(class1 == null)
                class1 = obj.getClass();
            int i = stackPtr;
            com.maddox.rts.ObjIO.stackPush(obj, class1);
            do
            {
                c = com.maddox.rts.ObjIO.getChar(stringbuffer);
                if(c == ')')
                {
                    com.maddox.rts.ObjIO.objectValidate();
                    com.maddox.rts.ObjIO.stackPop();
                    return obj;
                }
                if(c != ',')
                {
                    com.maddox.rts.ObjIO.stackPop();
                    return null;
                }
                java.lang.String s1 = com.maddox.rts.ObjIO.getWord(stringbuffer);
                c = com.maddox.rts.ObjIO.getChar(stringbuffer);
                if(c != '=')
                {
                    com.maddox.rts.ObjIO.stackPop();
                    return null;
                }
                try
                {
                    java.lang.reflect.Field field1 = class1.getField(s1);
                    if(com.maddox.rts.ObjIO.setCurField(flag, field1))
                    {
                        if(obj == null)
                            obj = stackObjects.get(stackPtr);
                        java.lang.Class class4 = field1.getType();
                        java.lang.Object obj2 = null;
                        java.lang.Object obj4 = null;
                        if((curFieldNode.flags & 0x2000000) != 0)
                            obj4 = com.maddox.rts.ObjIO.getWord(stringbuffer);
                        else
                        if(class4.isPrimitive() || class4 == (java.lang.String.class) || class4 == (java.lang.StringBuffer.class))
                        {
                            obj4 = com.maddox.rts.ObjIO._fromString(flag, null, class4, stringbuffer);
                        } else
                        {
                            obj2 = com.maddox.rts.ObjIO.fieldGet(flag);
                            obj4 = com.maddox.rts.ObjIO._fromString(flag, obj2, obj2 != null ? obj2.getClass() : class4, stringbuffer);
                        }
                        if(obj4 != obj2)
                            com.maddox.rts.ObjIO.fieldSet(flag, obj4);
                        else
                            com.maddox.rts.ObjIO.fieldValidate();
                    }
                }
                catch(java.lang.Exception exception4)
                {
                    com.maddox.rts.ObjIO.printDebug(exception4);
                    com.maddox.rts.ObjIO.stackPop(i);
                    return null;
                }
            } while(true);
        } else
        {
            if(class1 == null)
                return obj;
            if(c != '\uFFFF')
            {
                stringbuffer.insert(0, c);
                s = com.maddox.rts.ObjIO.getWord(stringbuffer);
            }
            if(s == null || s.length() == 0)
            {
                if(class1 == java.lang.Boolean.TYPE)
                    obj = new Boolean(false);
                else
                if(class1 == java.lang.Byte.TYPE)
                    obj = new Byte((byte)0);
                else
                if(class1 == java.lang.Short.TYPE)
                    obj = new Short((short)0);
                else
                if(class1 == java.lang.Character.TYPE)
                    obj = new Character('\0');
                else
                if(class1 == java.lang.Integer.TYPE)
                    obj = new Integer(0);
                else
                if(class1 == java.lang.Long.TYPE)
                    obj = new Long(0L);
                else
                if(class1 == java.lang.Float.TYPE)
                    obj = new Float(0.0F);
                else
                if(class1 == java.lang.Double.TYPE)
                    obj = new Double(0.0D);
            } else
            {
                try
                {
                    if(class1 == java.lang.Boolean.TYPE)
                        obj = new Boolean(s);
                    else
                    if(class1 == java.lang.Byte.TYPE)
                        obj = new Byte(s);
                    else
                    if(class1 == java.lang.Short.TYPE)
                        obj = new Short(s);
                    else
                    if(class1 == java.lang.Character.TYPE)
                        obj = new Character(s.charAt(0));
                    else
                    if(class1 == java.lang.Integer.TYPE)
                        obj = new Integer(s);
                    else
                    if(class1 == java.lang.Long.TYPE)
                        obj = new Long(s);
                    else
                    if(class1 == java.lang.Float.TYPE)
                        obj = new Float(s);
                    else
                    if(class1 == java.lang.Double.TYPE)
                        obj = new Double(s);
                    else
                    if(class1 == (java.lang.String.class))
                        obj = s;
                    else
                    if(class1 == (java.lang.StringBuffer.class))
                        obj = new StringBuffer(s);
                }
                catch(java.lang.Exception exception2)
                {
                    com.maddox.rts.ObjIO.printDebug(exception2);
                    return null;
                }
            }
            return obj;
        }
    }

    private static char peekChar(java.lang.StringBuffer stringbuffer)
    {
        if(stringbuffer != null)
            for(; stringbuffer.length() > 0; stringbuffer.deleteCharAt(0))
            {
                char c = stringbuffer.charAt(0);
                if(c > ' ')
                    return c;
            }

        return '\uFFFF';
    }

    private static char getChar(java.lang.StringBuffer stringbuffer)
    {
        if(stringbuffer != null)
            while(stringbuffer.length() > 0) 
            {
                char c = stringbuffer.charAt(0);
                stringbuffer.deleteCharAt(0);
                if(c > ' ')
                    return c;
            }
        return '\uFFFF';
    }

    private static java.lang.String getWord(java.lang.StringBuffer stringbuffer)
    {
        if(stringbuffer != null)
        {
            char c = '\0';
            do
            {
                if(stringbuffer.length() <= 0)
                    break;
                c = stringbuffer.charAt(0);
                if(c > ' ')
                    break;
                stringbuffer.deleteCharAt(0);
            } while(true);
            if(c == '"')
            {
                stringbuffer.deleteCharAt(0);
                int i = 0;
                int k = stringbuffer.length();
                int i1 = 0;
                for(; i < k; i++)
                {
                    char c1 = stringbuffer.charAt(i);
                    if(c1 == '"')
                        if(i1 == 92)
                        {
                            stringbuffer.deleteCharAt(i - 1);
                            i1 = 34;
                            i--;
                            k--;
                        } else
                        {
                            java.lang.String s = "";
                            if(i > 0)
                                s = stringbuffer.substring(0, i);
                            stringbuffer.delete(0, i + 1);
                            return s;
                        }
                    i1 = c1;
                }

                stringbuffer.setLength(0);
                return null;
            }
            int j = 0;
            int l = stringbuffer.length();
            do
            {
                if(j >= l)
                    break;
                char c2 = stringbuffer.charAt(j);
                if(c2 <= ' ' || c2 == '=' || c2 == ',' || c2 == ')' || c2 == ']')
                    break;
                j++;
            } while(true);
            int j1 = j - 1;
            java.lang.String s1 = "";
            if(j1 >= 0)
                s1 = stringbuffer.substring(0, j1 + 1);
            if(j > 0)
                stringbuffer.delete(0, j);
            return s1;
        } else
        {
            return null;
        }
    }

    private static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    private static com.maddox.rts.Res makeRes()
    {
        curRes.clear();
        for(int i = 0; i <= stackPtr; i++)
        {
            java.util.ResourceBundle resourcebundle = (java.util.ResourceBundle)stackBundles.get(i);
            if(resourcebundle == null)
                continue;
            _resBuf.setLength(0);
            for(int j = i; j <= stackPtr; j++)
            {
                java.lang.Object obj = stackFields.get(j);
                if(obj instanceof java.lang.reflect.Field)
                {
                    java.lang.reflect.Field field1 = (java.lang.reflect.Field)obj;
                    if(j != i)
                        _resBuf.append('.');
                    _resBuf.append(field1.getName());
                } else
                {
                    _resBuf.append("[]");
                }
            }

            java.lang.String s = null;
            try
            {
                s = resourcebundle.getString(_resBuf.toString());
            }
            catch(java.lang.Exception exception) { }
            if(s != null)
                com.maddox.rts.ObjIO.parseRes(s);
        }

        if(curRes.isEmpty())
            return null;
        else
            return new Res(curRes);
    }

    private static void parseRes(java.lang.String s)
    {
        boolean flag = false;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = s.length();
        byte byte0 = -1;
        byte byte1 = -1;
        do
        {
            if(byte0 != -1)
                switch(byte0)
                {
                default:
                    break;

                case 0: // '\0'
                    if(curRes.name == null)
                        curRes.name = s.substring(j, k);
                    break;

                case 2: // '\002'
                    if(curRes.tip == null)
                        curRes.tip = s.substring(j, k);
                    break;

                case 1: // '\001'
                    if(curRes.enum != null)
                        break;
                    _resLst.clear();
                    _resBuf.setLength(0);
                    _resBuf.append(s.substring(j, k));
                    do
                    {
                        java.lang.String s1 = com.maddox.rts.ObjIO.getResWord(_resBuf);
                        if(s1 == null || s1.length() == 0)
                            break;
                        _resLst.add(s1);
                    } while(true);
                    if(_resLst.size() > 0)
                        curRes.enum = _resLst.toArray();
                    break;
                }
            int i = k;
            j = l;
            byte0 = byte1;
            byte1 = -1;
            int j1 = i1;
            int l1 = s.indexOf("$NAME", j);
            if(l1 >= 0 && l1 < j1)
            {
                byte1 = 0;
                j1 = l1;
                k = l1;
                l = l1 + "$NAME".length();
            }
            l1 = s.indexOf("$ENUM", j);
            if(l1 >= 0 && l1 < j1)
            {
                byte1 = 1;
                j1 = l1;
                k = l1;
                l = l1 + "$ENUM".length();
            }
            l1 = s.indexOf("$TIP", j);
            if(l1 >= 0 && l1 < j1)
            {
                byte1 = 2;
                int k1 = l1;
                k = l1;
                l = l1 + "$TIP".length();
            }
            if(byte1 < 0)
                k = l = i1;
        } while(byte0 != -1 || byte1 != -1);
    }

    private static java.lang.String getResWord(java.lang.StringBuffer stringbuffer)
    {
        if(stringbuffer != null)
        {
            char c = '\0';
            do
            {
                if(stringbuffer.length() <= 0)
                    break;
                c = stringbuffer.charAt(0);
                if(c > ' ')
                    break;
                stringbuffer.deleteCharAt(0);
            } while(true);
            if(c == '"')
            {
                stringbuffer.deleteCharAt(0);
                int i = 0;
                int k = stringbuffer.length();
                int i1 = 0;
                for(; i < k; i++)
                {
                    char c1 = stringbuffer.charAt(i);
                    if(c1 == '"')
                        if(i1 == 92)
                        {
                            stringbuffer.deleteCharAt(i - 1);
                            i1 = 34;
                            i--;
                            k--;
                        } else
                        {
                            java.lang.String s = "";
                            if(i > 0)
                                s = stringbuffer.substring(0, i);
                            stringbuffer.delete(0, i + 1);
                            return s;
                        }
                    i1 = c1;
                }

                stringbuffer.setLength(0);
                return null;
            }
            int j = 0;
            int l = stringbuffer.length();
            do
            {
                if(j >= l)
                    break;
                char c2 = stringbuffer.charAt(j);
                if(c2 <= ' ')
                    break;
                j++;
            } while(true);
            int j1 = j - 1;
            java.lang.String s1 = "";
            if(j1 >= 0)
                s1 = stringbuffer.substring(0, j1 + 1);
            if(j > 0)
                stringbuffer.delete(0, j);
            return s1;
        } else
        {
            return null;
        }
    }

    public boolean isValue()
    {
        return child == null;
    }

    public int level()
    {
        return level;
    }

    public com.maddox.rts.ObjIO parent()
    {
        return parent;
    }

    public int childCount()
    {
        return child != null ? child.size() : 0;
    }

    public com.maddox.rts.ObjIO childAt(int i)
    {
        if(child != null && i < child.size())
            return (com.maddox.rts.ObjIO)child.get(i);
        else
            return null;
    }

    public java.lang.String tip()
    {
        if(res != null && res.tip != null)
            return res.tip;
        else
            return "";
    }

    public java.lang.String name()
    {
        return name;
    }

    public java.lang.Object[] getEnum()
    {
        if((field instanceof java.lang.reflect.Field) && ((java.lang.reflect.Field)field).getType() == java.lang.Boolean.TYPE)
            return booleanEnum;
        if(fieldNode == null)
            return null;
        else
            return fieldNode.enum;
    }

    public java.lang.Object[] getAliasEnum()
    {
        if((field instanceof java.lang.reflect.Field) && ((java.lang.reflect.Field)field).getType() == java.lang.Boolean.TYPE)
            if(res != null && res.enum != null && res.enum.length == 2)
                return res.enum;
            else
                return booleanEnum;
        if(fieldNode == null || fieldNode.enum == null)
            return null;
        if(res != null && res.enum != null && res.enum.length == fieldNode.enum.length)
            return res.enum;
        else
            return fieldNode.enum;
    }

    public boolean isReadOnly()
    {
        if(!isValue())
            return true;
        if(fieldNode == null)
            return false;
        else
            return (fieldNode.flags & 1) != 0;
    }

    public java.lang.Class getType()
    {
        if(field instanceof java.lang.reflect.Field)
            return ((java.lang.reflect.Field)field).getType();
        else
            return clazz.getComponentType();
    }

    public boolean isCheckRange()
    {
        if(fieldNode == null)
            return false;
        else
            return (fieldNode.flags & 0x200000) != 0;
    }

    public java.lang.Number getCheckRangeMin()
    {
        if(fieldNode == null)
            return null;
        else
            return (java.lang.Number)fieldNode.min;
    }

    public java.lang.Number getCheckRangeMax()
    {
        if(fieldNode == null)
            return null;
        else
            return (java.lang.Number)fieldNode.max;
    }

    public java.lang.Number getCheckRangeStep()
    {
        if(fieldNode == null)
            return null;
        else
            return (java.lang.Number)fieldNode.step;
    }

    public java.lang.String get()
    {
        if(!bStrValid)
            validate();
        java.lang.String s = strValue;
        java.lang.Object aobj[] = getAliasEnum();
        if(aobj != null)
        {
            java.lang.Object aobj1[] = getEnum();
            if(aobj1 != aobj)
            {
                int i = 0;
                do
                {
                    if(i >= aobj1.length)
                        break;
                    if(s.equals(aobj1[i].toString()))
                    {
                        s = aobj[i].toString();
                        break;
                    }
                    i++;
                } while(true);
            }
        }
        return s;
    }

    public void set(java.lang.String s)
    {
        if(!isValue())
            return;
        if(s == null)
            s = "";
        java.lang.Object aobj[] = getAliasEnum();
        if(aobj != null)
        {
            java.lang.Object aobj1[] = getEnum();
            if(aobj1 != aobj)
            {
                int i = 0;
                do
                {
                    if(i >= aobj1.length)
                        break;
                    if(s.equals(aobj[i].toString()))
                    {
                        s = aobj1[i].toString();
                        break;
                    }
                    i++;
                } while(true);
            }
        }
        fillStack();
        java.lang.Class class1;
        if(field instanceof java.lang.reflect.Field)
            class1 = ((java.lang.reflect.Field)field).getType();
        else
            class1 = clazz.getComponentType();
        java.lang.Object obj = null;
        java.lang.Object obj1 = null;
        if((curFieldNode.flags & 0x22000000) != 0)
            obj1 = s;
        else
        if(class1.isPrimitive() || class1 == (java.lang.String.class) || class1 == (java.lang.StringBuffer.class))
        {
            _strBuf.setLength(0);
            _strBuf.append(s);
            obj1 = com.maddox.rts.ObjIO._fromString(true, null, class1, _strBuf);
        } else
        {
            obj = com.maddox.rts.ObjIO.fieldGet(true);
            _strBuf.setLength(0);
            _strBuf.append(s);
            obj1 = com.maddox.rts.ObjIO._fromString(true, obj, obj != null ? obj.getClass() : class1, _strBuf);
        }
        if(obj1 != obj)
            com.maddox.rts.ObjIO.fieldSet(true, obj1);
        else
            com.maddox.rts.ObjIO.fieldValidate();
        com.maddox.rts.ObjIO.stackPop(-1);
        invalidate();
    }

    private void validate()
    {
        if(bStrValid)
            return;
        if(child != null)
        {
            int i = child.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.ObjIO objio = (com.maddox.rts.ObjIO)child.get(j);
                if(objio != null)
                    objio.validate();
            }

            _strBuf.setLength(0);
            if(clazz.isArray())
                _strBuf.append('[');
            else
                _strBuf.append('(');
            for(int k = 0; k < i; k++)
            {
                if(k > 0)
                    _strBuf.append(',');
                com.maddox.rts.ObjIO objio1 = (com.maddox.rts.ObjIO)child.get(k);
                if(objio1 == null)
                    continue;
                java.lang.String s = objio1.strValue;
                java.lang.Object aobj[] = objio1.getAliasEnum();
                if(aobj != null)
                {
                    java.lang.Object aobj1[] = objio1.getEnum();
                    if(aobj1 != aobj)
                    {
                        int l = 0;
                        do
                        {
                            if(l >= aobj1.length)
                                break;
                            if(s.equals(aobj1[l].toString()))
                            {
                                s = aobj[l].toString();
                                break;
                            }
                            l++;
                        } while(true);
                    }
                }
                _strBuf.append(s);
            }

            if(clazz.isArray())
                _strBuf.append(']');
            else
                _strBuf.append(')');
            strValue = _strBuf.toString();
        } else
        {
            fillStack();
            java.lang.Object obj = com.maddox.rts.ObjIO.fieldGet(true);
            if(obj != null)
                strValue = obj.toString();
            else
                strValue = "";
            com.maddox.rts.ObjIO.stackPop(-1);
        }
        bStrValid = true;
    }

    private void invalidate()
    {
        bStrValid = false;
        if(parent != null)
            parent.invalidate();
    }

    private void checkReadOnly(boolean flag)
    {
        if(flag)
        {
            if(fieldNode == null)
                fieldNode = readonlyNode;
            else
                fieldNode.flags |= 1;
        } else
        if(fieldNode != null && (fieldNode.flags & 1) != 0)
            flag = true;
        if(child != null)
        {
            int i = child.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.ObjIO objio = (com.maddox.rts.ObjIO)child.get(j);
                if(objio != null)
                    objio.checkReadOnly(flag);
            }

        }
    }

    private void fillStack()
    {
        com.maddox.rts.ObjIO objio = this;
        stackPtr = objio.level;
        for(; objio != null && objio.level >= 0; objio = objio.parent)
        {
            int i = objio.level;
            stackClasses.set(i, clazz);
            stackObjects.set(i, object);
            stackFields.set(i, field);
        }

        if(fieldNode != null)
            curFieldNode = fieldNode;
        else
            curFieldNode = emptyNode;
        if(fieldObjects != null)
            curFieldObjects = fieldObjects;
        else
            curFieldObjects = emptyObjects;
        if(objectNode != null)
            curObjectNode = objectNode;
        else
            curObjectNode = emptyNode;
        if(objectObjects != null)
            curObjectObjects = objectObjects;
        else
            curObjectObjects = emptyObjects;
    }

    private void fillCurrent()
    {
        if(!curFieldNode.isEmpty())
        {
            fieldNode = new Node(curFieldNode);
            if(!curFieldObjects.isEmpty())
                fieldObjects = new NodeObjects(curFieldObjects);
        }
        if(!curObjectNode.isEmpty())
        {
            objectNode = new Node(curObjectNode);
            if(!curObjectObjects.isEmpty())
                objectObjects = new NodeObjects(curObjectObjects);
        }
        clazz = (java.lang.Class)stackClasses.get(stackPtr);
        object = stackObjects.get(stackPtr);
        field = stackFields.get(stackPtr);
    }

    private void fillNull(java.lang.StringBuffer stringbuffer, com.maddox.rts.ObjIO objio, int i)
    {
        name = stringbuffer.toString();
        parent = objio;
        level = i;
        fieldNode = readonlyNode;
        bStrValid = true;
        strValue = "null";
    }

    private boolean make(java.lang.StringBuffer stringbuffer, com.maddox.rts.ObjIO objio, int i, java.lang.Object obj, java.lang.Class class1)
    {
        boolean flag;
        int j;
        int k;
        name = stringbuffer.toString();
        parent = objio;
        level = i;
        if(obj == null)
            return false;
        flag = obj == class1;
        j = stackPtr;
        k = stringbuffer.length();
        if(!class1.isArray())
            break MISSING_BLOCK_LABEL_293;
        java.lang.Class class2;
        int l;
        com.maddox.rts.ObjIO.stackPush(obj, class1);
        class2 = class1.getComponentType();
        l = java.lang.reflect.Array.getLength(obj);
        if(l != 0)
            break MISSING_BLOCK_LABEL_89;
        com.maddox.rts.ObjIO.stackPop();
        return false;
        child = new ArrayList();
        for(int j1 = 0; j1 < l; j1++)
        {
            com.maddox.rts.ObjIO.setCurField(true, new Integer(j1), flag);
            stringbuffer.append("[" + j1 + "]");
            java.lang.Object obj2 = com.maddox.rts.ObjIO.fieldGet(true);
            com.maddox.rts.ObjIO objio1 = new ObjIO();
            if(obj2 != null)
            {
                if(class2.isPrimitive())
                    objio1.make(stringbuffer, this, level + 1, obj2, class2);
                else
                    objio1.make(stringbuffer, this, level + 1, obj2, obj2.getClass());
            } else
            {
                objio1.fillNull(stringbuffer, this, level + 1);
            }
            child.add(objio1);
            stringbuffer.setLength(k);
        }

        com.maddox.rts.ObjIO.stackPop();
        return true;
        java.lang.Exception exception;
        exception;
        com.maddox.rts.ObjIO.printDebug(exception);
        stringbuffer.setLength(k);
        com.maddox.rts.ObjIO.stackPop(j);
        return false;
        if(class1.isPrimitive() || class1 == (java.lang.String.class) || class1 == (java.lang.StringBuffer.class))
        {
            strValue = obj.toString();
            bStrValid = true;
            return true;
        }
        com.maddox.rts.ObjIO.stackPush(obj, class1);
        java.lang.reflect.Field afield[] = com.maddox.rts.ObjIO.getFields(class1);
        child = new ArrayList();
        for(int i1 = 0; i1 < afield.length; i1++)
        {
            if(!com.maddox.rts.ObjIO.setCurField(true, afield[i1], flag))
                continue;
            java.lang.Object obj1 = com.maddox.rts.ObjIO.fieldGet(true);
            if(obj1 == null)
                continue;
            if(stringbuffer.length() > 0)
                stringbuffer.append('.');
            boolean flag1 = false;
            com.maddox.rts.ObjIO objio2 = new ObjIO();
            if(objio2.res != null && objio2.res.name != null)
                stringbuffer.append(objio2.res.name);
            else
                stringbuffer.append(afield[i1].getName());
            if((curFieldNode.flags & 0x11000000) != 0)
            {
                objio2.strValue = obj1.toString();
                objio2.bStrValid = true;
                objio2.name = stringbuffer.toString();
                objio2.parent = this;
                objio2.level = level + 1;
                flag1 = true;
            } else
            if(afield[i1].getType().isPrimitive())
                flag1 = objio2.make(stringbuffer, this, level + 1, obj1, afield[i1].getType());
            else
                flag1 = objio2.make(stringbuffer, this, level + 1, obj1, obj1.getClass());
            if(flag1)
                child.add(objio2);
            stringbuffer.setLength(k);
        }

        com.maddox.rts.ObjIO.stackPop();
        if(child.size() != 0)
            break MISSING_BLOCK_LABEL_680;
        child = null;
        return false;
        return true;
        afield;
        com.maddox.rts.ObjIO.printDebug(afield);
        stringbuffer.setLength(k);
        com.maddox.rts.ObjIO.stackPop(j);
        return false;
    }

    public void update()
    {
        invalidateTree();
        validate();
    }

    private void invalidateTree()
    {
        bStrValid = false;
        if(child != null)
        {
            int i = child.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.ObjIO objio = (com.maddox.rts.ObjIO)child.get(j);
                if(objio != null)
                    objio.invalidateTree();
            }

        }
    }

    public ObjIO(java.lang.Object obj, java.lang.String s)
    {
        bStrValid = false;
        _strBuf.setLength(0);
        if(s != null)
            _strBuf.append(s);
        bUseBundle = true;
        com.maddox.rts.ObjIO.stackClear();
        clazz = (obj instanceof java.lang.Class) ? (java.lang.Class)obj : obj.getClass();
        make(_strBuf, null, -1, obj, clazz);
        com.maddox.rts.ObjIO.stackPop(-1);
        bUseBundle = false;
        checkReadOnly(false);
    }

    private ObjIO()
    {
        bStrValid = false;
        fillCurrent();
        res = com.maddox.rts.ObjIO.makeRes();
    }

    public static java.lang.String friendPackage[] = {
        "com.maddox.il2.engine.", "com.maddox.il2.objects.", "com.maddox.il2.", "com.maddox.JGP.", "com.maddox.rts.", "com.maddox."
    };
    public static final int EDREADONLY = 1;
    public static final int ALL_FLAGS = 1;
    private static final int _ENUM = 0x100000;
    private static final int _MINMAX = 0x200000;
    private static final int _GET = 0x400000;
    private static final int _SET = 0x800000;
    private static final int _GETSTR = 0x1000000;
    private static final int _SETSTR = 0x2000000;
    private static final int _EDGET = 0x4000000;
    private static final int _EDSET = 0x8000000;
    private static final int _EDGETSTR = 0x10000000;
    private static final int _EDSETSTR = 0x20000000;
    private static final int _VALIDATE = 0x40000000;
    public static final java.lang.String KEY_FIELDS = "$ClassIOFields$";
    public static final java.lang.String KEY_OBJECT = "$ObjIO";
    public static final java.lang.String KEY_FIELD = "$IO";
    private static java.util.ArrayList _fldPropLst = new ArrayList();
    private static java.util.ArrayList _fldLst = new ArrayList();
    private static java.lang.reflect.Field filedsEmpty[] = new java.lang.reflect.Field[0];
    private static com.maddox.rts.FieldCompare _fldsCompare = new FieldCompare();
    private static int getFieldType_arrays = 0;
    private static java.lang.Class _fieldsClass[] = new java.lang.Class[1];
    private static int stackPtr = -1;
    private static boolean bUseBundle = false;
    private static java.util.ArrayList stackClasses = new ArrayList(64);
    private static java.util.ArrayList stackObjects = new ArrayList(64);
    private static java.util.ArrayList stackFields = new ArrayList(64);
    private static java.util.ArrayList stackBundles = new ArrayList(64);
    private static com.maddox.rts.Node curFieldNode = new Node();
    private static com.maddox.rts.NodeObjects curFieldObjects = new NodeObjects();
    private static com.maddox.rts.Node curObjectNode = new Node();
    private static com.maddox.rts.NodeObjects curObjectObjects = new NodeObjects();
    private static java.lang.Object _fieldsObject[] = new java.lang.Object[1];
    private static java.lang.StringBuffer _strBuf = new StringBuffer();
    private static final java.lang.String RES_NAME = "$NAME";
    private static final java.lang.String RES_ENUM = "$ENUM";
    private static final java.lang.String RES_TIP = "$TIP";
    private static final int _RES_NAME = 0;
    private static final int _RES_ENUM = 1;
    private static final int _RES_TIP = 2;
    private static com.maddox.rts.Res curRes = new Res();
    private static java.util.ArrayList _resLst = new ArrayList();
    private static java.lang.StringBuffer _resBuf = new StringBuffer();
    private int level;
    private com.maddox.rts.ObjIO parent;
    private java.util.List child;
    private volatile com.maddox.rts.Node fieldNode;
    private com.maddox.rts.NodeObjects fieldObjects;
    private com.maddox.rts.Node objectNode;
    private com.maddox.rts.NodeObjects objectObjects;
    private java.lang.Class clazz;
    private java.lang.Object object;
    private java.lang.Object field;
    private com.maddox.rts.Res res;
    private java.lang.String name;
    private boolean bStrValid;
    private java.lang.String strValue;
    private static java.lang.Object booleanEnum[] = {
        new Boolean(false), new Boolean(true)
    };
    private static com.maddox.rts.Node emptyNode = new Node();
    private static com.maddox.rts.NodeObjects emptyObjects = new NodeObjects();
    private static com.maddox.rts.Node readonlyNode;

    static 
    {
        readonlyNode = new Node();
        readonlyNode.flags |= 1;
    }
}
