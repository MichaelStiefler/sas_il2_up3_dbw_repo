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
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

public class ObjIO
{
  public static String[] friendPackage = { "com.maddox.il2.engine.", "com.maddox.il2.objects.", "com.maddox.il2.", "com.maddox.JGP.", "com.maddox.rts.", "com.maddox." };
  public static final int EDREADONLY = 1;
  public static final int ALL_FLAGS = 1;
  private static final int _ENUM = 1048576;
  private static final int _MINMAX = 2097152;
  private static final int _GET = 4194304;
  private static final int _SET = 8388608;
  private static final int _GETSTR = 16777216;
  private static final int _SETSTR = 33554432;
  private static final int _EDGET = 67108864;
  private static final int _EDSET = 134217728;
  private static final int _EDGETSTR = 268435456;
  private static final int _EDSETSTR = 536870912;
  private static final int _VALIDATE = 1073741824;
  public static final String KEY_FIELDS = "$ClassIOFields$";
  public static final String KEY_OBJECT = "$ObjIO";
  public static final String KEY_FIELD = "$IO";
  private static ArrayList _fldPropLst = new ArrayList();
  private static ArrayList _fldLst = new ArrayList();
  private static Field[] filedsEmpty = new Field[0];

  private static FieldCompare _fldsCompare = new FieldCompare();

  private static int getFieldType_arrays = 0;

  private static Class[] _fieldsClass = new Class[1];

  private static int stackPtr = -1;
  private static boolean bUseBundle = false;
  private static ArrayList stackClasses = new ArrayList(64);
  private static ArrayList stackObjects = new ArrayList(64);
  private static ArrayList stackFields = new ArrayList(64);
  private static ArrayList stackBundles = new ArrayList(64);

  private static Node curFieldNode = new Node();
  private static NodeObjects curFieldObjects = new NodeObjects();
  private static Node curObjectNode = new Node();
  private static NodeObjects curObjectObjects = new NodeObjects();

  private static Object[] _fieldsObject = new Object[1];

  private static StringBuffer _strBuf = new StringBuffer();
  private static final String RES_NAME = "$NAME";
  private static final String RES_ENUM = "$ENUM";
  private static final String RES_TIP = "$TIP";
  private static final int _RES_NAME = 0;
  private static final int _RES_ENUM = 1;
  private static final int _RES_TIP = 2;
  private static Res curRes = new Res();
  private static ArrayList _resLst = new ArrayList();
  private static StringBuffer _resBuf = new StringBuffer();
  private int level;
  private ObjIO parent;
  private List child;
  private volatile Node fieldNode;
  private NodeObjects fieldObjects;
  private Node objectNode;
  private NodeObjects objectObjects;
  private Class clazz;
  private Object object;
  private Object field;
  private Res res;
  private String name;
  private boolean bStrValid = false;
  private String strValue;
  private static Object[] booleanEnum = { new Boolean(false), new Boolean(true) };

  private static Node emptyNode = new Node();
  private static NodeObjects emptyObjects = new NodeObjects();

  private static Node readonlyNode = new Node();

  public static void setFriendPackages(String[] paramArrayOfString)
  {
    friendPackage = paramArrayOfString;
  }

  public static String classGetName(Class paramClass)
  {
    String str1 = paramClass.getName();
    for (int i = 0; i < friendPackage.length; i++) {
      String str2 = friendPackage[i];
      if (str1.startsWith(str2)) {
        return str1.substring(str2.length());
      }
    }
    return str1;
  }

  public static Class classForName(String paramString)
    throws ClassNotFoundException
  {
    try
    {
      for (int i = 0; i < friendPackage.length; ) try {
          return Class.forName(friendPackage[i] + paramString);
        }
        catch (Exception localException)
        {
          i++;
        }


      return Class.forName(paramString); } catch (Throwable localThrowable) {
    }
    throw new ClassNotFoundException(localThrowable.getMessage());
  }

  private static void checkFieldParams(Class paramClass, String paramString)
  {
    if (paramClass == null)
      throw new RTSException("Parameter 'class' == null");
    if (paramString == null)
      throw new RTSException("Parameter 'fieldName' == null");
  }

  private static void checkFieldParams(Class paramClass, String[] paramArrayOfString) {
    if (paramClass == null)
      throw new RTSException("Parameter 'class' == null");
    if (paramArrayOfString == null)
      throw new RTSException("Parameter 'fieldNames' == null");
  }

  private static Node getNode(Class paramClass, String paramString) {
    String str = paramString + "$IO";
    Node localNode = (Node)Property.value(paramClass, str, null);
    if (localNode == null) {
      localNode = new Node();
      Property.set(paramClass, str, localNode);
    }
    return localNode;
  }

  private static void initNode(Class paramClass, String paramString) {
    String str = paramString + "$IO";
    if (!Property.containsValue(paramClass, str))
      Property.set(paramClass, str, (Object)null);
  }

  private static Node getNode(Class paramClass) {
    Node localNode = (Node)Property.value(paramClass, "$ObjIO", null);
    if (localNode == null) {
      localNode = new Node();
      Property.set(paramClass, "$ObjIO", localNode);
    }
    return localNode;
  }

  public static Class toWrapperClass(Class paramClass) {
    if (paramClass.isPrimitive()) {
      if (paramClass == Boolean.TYPE) paramClass = Boolean.class;
      else if (paramClass == Byte.TYPE) paramClass = Byte.class;
      else if (paramClass == Short.TYPE) paramClass = Short.class;
      else if (paramClass == Character.TYPE) paramClass = Character.class;
      else if (paramClass == Integer.TYPE) paramClass = Integer.class;
      else if (paramClass == Long.TYPE) paramClass = Long.class;
      else if (paramClass == Float.TYPE) paramClass = Float.class;
      else if (paramClass == Double.TYPE) paramClass = Double.class;
    }
    return paramClass;
  }

  private static Field[] getFields(Class paramClass) {
    Field[] arrayOfField = (Field[])Property.value(paramClass, "$ClassIOFields$", null);
    if (arrayOfField != null)
      return arrayOfField;
    if (!Property.vars(_fldPropLst, paramClass))
      return filedsEmpty;
    int i = _fldPropLst.size();
    for (int j = 0; j < i; j++) {
      String str1 = (String)_fldPropLst.get(j);
      if ((str1 != null) && (str1.endsWith("$IO"))) {
        int m = str1.indexOf('.');
        if (m < 0)
          m = str1.length() - "$IO".length();
        int n = str1.indexOf('[');
        if ((n > 0) && (n < m))
          m = n;
        String str2 = str1.substring(0, m);
        Field localField = null;
        Class localClass = paramClass;
        while (localClass != null) {
          try {
            localField = localClass.getDeclaredField(str2);
            if (!_fldLst.contains(localField))
              _fldLst.add(localField);
          }
          catch (Exception localException) {
            localClass = localClass.getSuperclass();
          }
        }
      }
    }
    _fldPropLst.clear();
    i = _fldLst.size();
    if (i > 0) {
      arrayOfField = new Field[i];
      for (int k = 0; k < i; k++)
        arrayOfField[k] = ((Field)_fldLst.get(k));
      Arrays.sort(arrayOfField, _fldsCompare);
      _fldLst.clear();
    } else {
      arrayOfField = filedsEmpty;
    }

    Property.set(paramClass, "$ClassIOFields$", arrayOfField);
    return arrayOfField;
  }

  private static Class getFieldType(Class paramClass, String paramString, boolean paramBoolean)
  {
    getFieldType_arrays = 0;
    Class localClass = paramClass;
    int i = paramString.length();
    int j = 0;
    int k = 0;
    while (j < i) {
      int m = paramString.charAt(j);
      if (m == 46) {
        if (k < j)
          localClass = getFieldTypeOne(localClass, paramString.substring(k, j), false);
        j++;
        k = j;
      } else if (m == 91) {
        if (k < j)
          localClass = getFieldTypeOne(localClass, paramString.substring(k, j), true);
        j += 2;
        k = j;
      } else {
        j++;
      }
    }
    if (k < j)
      localClass = getFieldTypeOne(localClass, paramString.substring(k, j), false);
    if (paramBoolean) return toWrapperClass(localClass);
    return localClass;
  }
  private static Class getFieldTypeOne(Class paramClass, String paramString, boolean paramBoolean) {
    try {
      Field localField = paramClass.getField(paramString);
      paramClass = localField.getType();
      if ((paramBoolean) && (paramClass.isArray()))
        paramClass = paramClass.getComponentType();
    } catch (Exception localException) {
      printDebug(localException);
      throw new RTSException("Filed '" + paramString + "' not found in class " + paramClass.getName());
    }
    if (paramBoolean)
      getFieldType_arrays += 1;
    return paramClass;
  }

  public static void fieldsSuperclass(Class paramClass)
  {
    if (paramClass == null)
      throw new RTSException("Parameter 'class' == null");
    Class localClass = paramClass.getSuperclass();
    if (localClass == null) return;
    if (!Property.vars(_fldPropLst, localClass))
      return;
    int i = _fldPropLst.size();
    for (int j = 0; j < i; j++) {
      String str = (String)_fldPropLst.get(j);
      if ((str == null) || (!str.endsWith("$IO")) || 
        (Property.containsValue(paramClass, str))) continue;
      Property.set(paramClass, str, Property.value(localClass, str, null));
    }

    _fldPropLst.clear();
  }

  public static void fieldsAllSuperclasses(Class paramClass)
  {
    if (paramClass == null)
      throw new RTSException("Parameter 'class' == null");
    fieldsAllSuperclasses(paramClass, paramClass.getSuperclass());
  }

  private static void fieldsAllSuperclasses(Class paramClass1, Class paramClass2) {
    if (paramClass2 == null) return;
    fieldsAllSuperclasses(paramClass1, paramClass2.getSuperclass());
    if (!Property.vars(_fldPropLst, paramClass2))
      return;
    int i = _fldPropLst.size();
    for (int j = 0; j < i; j++) {
      String str = (String)_fldPropLst.get(j);
      if ((str == null) || (!str.endsWith("$IO")) || 
        (Property.containsValue(paramClass1, str))) continue;
      Property.set(paramClass1, str, Property.value(paramClass2, str, null));
    }

    _fldPropLst.clear();
  }

  public static void field(Class paramClass, String paramString)
  {
    checkFieldParams(paramClass, paramString);
    initNode(paramClass, paramString);
  }

  public static void fields(Class paramClass, String[] paramArrayOfString)
  {
    checkFieldParams(paramClass, paramArrayOfString);
    for (int i = 0; i < paramArrayOfString.length; i++)
      if (paramArrayOfString[i] != null)
        initNode(paramClass, paramArrayOfString[i]);
  }

  public static void edReadOnly(Class paramClass, String paramString)
  {
    checkFieldParams(paramClass, paramString);
    Node localNode = getNode(paramClass, paramString);
    localNode.flags |= 1;
  }

  public static void jdMethod_enum(Class paramClass, String paramString, Object[] paramArrayOfObject)
  {
    checkFieldParams(paramClass, paramString);
    Node localNode = getNode(paramClass, paramString);
    if (paramArrayOfObject != null) {
      Class localClass1 = getFieldType(paramClass, paramString, true);
      Class localClass2 = paramArrayOfObject.getClass().getComponentType();
      if ((localNode.flags & 0x2000000) != 0)
        localClass2 = String.class;
      if (localClass2 != localClass1)
        throw new RTSException("Class elements of enum array (" + localClass2.getName() + ") not equal type of class field (" + localClass1.getName() + ")");
    }
    localNode.jdField_enum = paramArrayOfObject;
    localNode.flags |= 1048576;
  }

  private static Object checkEnum(Object paramObject, Object[] paramArrayOfObject) {
    for (int i = 0; i < paramArrayOfObject.length; i++)
      if (paramObject.equals(paramArrayOfObject[i]))
        return paramObject;
    return paramArrayOfObject[0];
  }

  public static void jdMethod_enum(Class paramClass, String paramString, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Byte[] arrayOfByte = new Byte[paramArrayOfByte.length];
    for (int i = 0; i < paramArrayOfByte.length; i++) arrayOfByte[i] = new Byte(paramArrayOfByte[i]);
    jdMethod_enum(paramClass, paramString, arrayOfByte);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, short[] paramArrayOfShort)
  {
    if (paramArrayOfShort == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Short[] arrayOfShort = new Short[paramArrayOfShort.length];
    for (int i = 0; i < paramArrayOfShort.length; i++) arrayOfShort[i] = new Short(paramArrayOfShort[i]);
    jdMethod_enum(paramClass, paramString, arrayOfShort);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Character[] arrayOfCharacter = new Character[paramArrayOfChar.length];
    for (int i = 0; i < paramArrayOfChar.length; i++) arrayOfCharacter[i] = new Character(paramArrayOfChar[i]);
    jdMethod_enum(paramClass, paramString, arrayOfCharacter);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Integer[] arrayOfInteger = new Integer[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++) arrayOfInteger[i] = new Integer(paramArrayOfInt[i]);
    jdMethod_enum(paramClass, paramString, arrayOfInteger);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, long[] paramArrayOfLong)
  {
    if (paramArrayOfLong == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Long[] arrayOfLong = new Long[paramArrayOfLong.length];
    for (int i = 0; i < paramArrayOfLong.length; i++) arrayOfLong[i] = new Long(paramArrayOfLong[i]);
    jdMethod_enum(paramClass, paramString, arrayOfLong);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Float[] arrayOfFloat = new Float[paramArrayOfFloat.length];
    for (int i = 0; i < paramArrayOfFloat.length; i++) arrayOfFloat[i] = new Float(paramArrayOfFloat[i]);
    jdMethod_enum(paramClass, paramString, arrayOfFloat);
  }

  public static void jdMethod_enum(Class paramClass, String paramString, double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble == null) { jdMethod_enum(paramClass, paramString, (Object[])null); return; }
    Double[] arrayOfDouble = new Double[paramArrayOfDouble.length];
    for (int i = 0; i < paramArrayOfDouble.length; i++) arrayOfDouble[i] = new Double(paramArrayOfDouble[i]);
    jdMethod_enum(paramClass, paramString, arrayOfDouble);
  }

  public static void range(Class paramClass, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    checkFieldParams(paramClass, paramString);
    Node localNode = getNode(paramClass, paramString);
    if ((paramObject1 != null) || (paramObject2 != null)) {
      Class localClass = getFieldType(paramClass, paramString, true);
      if ((paramObject1 != null) && 
        (localClass != paramObject1.getClass())) {
        throw new RTSException("Class of min patameter (" + paramObject1.getClass().getName() + ") not equal type of class field (" + localClass.getName() + ")");
      }
      if ((paramObject2 != null) && 
        (localClass != paramObject2.getClass())) {
        throw new RTSException("Class of max patameter (" + paramObject2.getClass().getName() + ") not equal type of class field (" + localClass.getName() + ")");
      }
      if ((paramObject3 != null) && 
        (localClass != paramObject3.getClass())) {
        throw new RTSException("Class of step patameter (" + paramObject3.getClass().getName() + ") not equal type of class field (" + localClass.getName() + ")");
      }
    }
    localNode.min = paramObject1;
    localNode.max = paramObject2;
    localNode.step = paramObject3;
    localNode.flags |= 2097152;
  }

  private static Object checkRange(Object paramObject1, Object paramObject2, Object paramObject3) {
    if (!(paramObject1 instanceof Comparable)) return paramObject1; try
    {
      Comparable localComparable = (Comparable)paramObject1;
      if ((paramObject2 != null) && 
        (localComparable.compareTo(paramObject2) < 0))
        localComparable = (Comparable)paramObject2;
      if ((paramObject3 != null) && 
        (localComparable.compareTo(paramObject3) > 0))
        localComparable = (Comparable)paramObject3;
      return localComparable;
    } catch (Exception localException) {
      printDebug(localException);
    }return paramObject1;
  }

  public static void range(Class paramClass, String paramString, byte paramByte1, byte paramByte2, byte paramByte3)
  {
    range(paramClass, paramString, new Byte(paramByte1), new Byte(paramByte2), new Byte(paramByte3));
  }

  public static void range(Class paramClass, String paramString, short paramShort1, short paramShort2, short paramShort3)
  {
    range(paramClass, paramString, new Short(paramShort1), new Short(paramShort2), new Short(paramShort3));
  }

  public static void range(Class paramClass, String paramString, char paramChar1, char paramChar2)
  {
    range(paramClass, paramString, new Character(paramChar1), new Character(paramChar2), null);
  }

  public static void range(Class paramClass, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    range(paramClass, paramString, new Integer(paramInt1), new Integer(paramInt2), new Integer(paramInt3));
  }

  public static void range(Class paramClass, String paramString, long paramLong1, long paramLong2, long paramLong3)
  {
    range(paramClass, paramString, new Long(paramLong1), new Long(paramLong2), new Long(paramLong3));
  }

  public static void range(Class paramClass, String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    range(paramClass, paramString, new Float(paramFloat1), new Float(paramFloat2), new Float(paramFloat3));
  }

  public static void range(Class paramClass, String paramString, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    range(paramClass, paramString, new Double(paramDouble1), new Double(paramDouble2), new Double(paramDouble3));
  }

  public static void get(Class paramClass, String paramString1, String paramString2)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    if (paramString2 != null) {
      Class localClass1 = getFieldType(paramClass, paramString1, false);
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, null));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != localClass1)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal class field '" + localClass1.getName() + "'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.get = localMethod;
    } else {
      localNode.get = null;
    }
    localNode.flags |= 4194304;
  }

  public static void getEd(Class paramClass, String paramString1, String paramString2)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    if (paramString2 != null) {
      Class localClass1 = getFieldType(paramClass, paramString1, false);
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, null));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != localClass1)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal class field '" + localClass1.getName() + "'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.edget = localMethod;
    } else {
      localNode.edget = null;
    }
    localNode.flags |= 67108864;
  }

  private static Class[] fieldClassArray(int paramInt, Class paramClass) {
    int i = paramInt;
    if (paramClass != null)
      i++;
    if (i == 0)
      return null;
    if ((_fieldsClass != null) && (_fieldsClass.length != i))
      _fieldsClass = null;
    if (_fieldsClass == null)
      _fieldsClass = new Class[i];
    for (int j = 0; j < paramInt; j++)
      _fieldsClass[j] = Integer.TYPE;
    if (paramClass != null)
      _fieldsClass[paramInt] = paramClass;
    return _fieldsClass;
  }

  public static void set(Class paramClass, String paramString1, String paramString2)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    if (paramString2 != null) {
      Class localClass1 = getFieldType(paramClass, paramString1, false);
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, localClass1));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != Void.TYPE)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal 'void'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.set = localMethod;
    } else {
      localNode.set = null;
    }
    localNode.flags |= 8388608;
  }

  public static void setEd(Class paramClass, String paramString1, String paramString2)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    if (paramString2 != null) {
      Class localClass1 = getFieldType(paramClass, paramString1, false);
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, localClass1));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != Void.TYPE)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal 'void'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.edset = localMethod;
    } else {
      localNode.edset = null;
    }
    localNode.flags |= 134217728;
  }

  public static void access(Class paramClass, String paramString1, String paramString2, String paramString3)
  {
    get(paramClass, paramString1, paramString2);
    set(paramClass, paramString1, paramString3);
  }

  public static void accessEd(Class paramClass, String paramString1, String paramString2, String paramString3)
  {
    getEd(paramClass, paramString1, paramString2);
    setEd(paramClass, paramString1, paramString3);
  }

  public static void access(Class paramClass, String paramString)
  {
    get(paramClass, paramString, "ioGet" + paramString);
    set(paramClass, paramString, "ioSet" + paramString);
  }

  public static void accessEd(Class paramClass, String paramString)
  {
    getEd(paramClass, paramString, "edGet" + paramString);
    setEd(paramClass, paramString, "edSet" + paramString);
  }

  public static void accessStr(Class paramClass, String paramString1, String paramString2, String paramString3)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    Class localClass1 = getFieldType(paramClass, paramString1, false);
    Method localMethod;
    if (paramString2 != null) {
      localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, null));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != String.class)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal 'String'");
      } catch (Exception localException1) {
        printDebug(localException1);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.get = localMethod;
    } else {
      localNode.get = null;
    }
    localNode.flags |= 20971520;

    if (paramString3 != null) {
      localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString3, fieldClassArray(getFieldType_arrays, String.class));
        Class localClass3 = localMethod.getReturnType();
        if (localClass3 != Void.TYPE)
          throw new RTSException("Method '" + paramString3 + "' returned type '" + localClass3.getName() + "' not equal 'void'");
      } catch (Exception localException2) {
        printDebug(localException2);
        throw new RTSException("Method '" + paramString3 + "' not found in class " + paramClass.getName());
      }
      localNode.set = localMethod;
    } else {
      localNode.set = null;
    }
    localNode.flags |= 41943040;
  }

  public static void accessEdStr(Class paramClass, String paramString1, String paramString2, String paramString3)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    Class localClass1 = getFieldType(paramClass, paramString1, false);
    Method localMethod;
    if (paramString2 != null) {
      localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, null));
        Class localClass2 = localMethod.getReturnType();
        if (localClass2 != String.class)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass2.getName() + "' not equal 'String'");
      } catch (Exception localException1) {
        printDebug(localException1);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.edget = localMethod;
    } else {
      localNode.edget = null;
    }
    localNode.flags |= 335544320;

    if (paramString3 != null) {
      localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString3, fieldClassArray(getFieldType_arrays, String.class));
        Class localClass3 = localMethod.getReturnType();
        if (localClass3 != Void.TYPE)
          throw new RTSException("Method '" + paramString3 + "' returned type '" + localClass3.getName() + "' not equal 'void'");
      } catch (Exception localException2) {
        printDebug(localException2);
        throw new RTSException("Method '" + paramString3 + "' not found in class " + paramClass.getName());
      }
      localNode.edset = localMethod;
    } else {
      localNode.edset = null;
    }
    localNode.flags |= 671088640;
  }

  public static void accessStr(Class paramClass, String paramString)
  {
    accessStr(paramClass, paramString, "ioGet" + paramString, "ioSet" + paramString);
  }

  public static void accessEdStr(Class paramClass, String paramString)
  {
    accessEdStr(paramClass, paramString, "edGet" + paramString, "edSet" + paramString);
  }

  public static void validate(Class paramClass, String paramString1, String paramString2)
  {
    checkFieldParams(paramClass, paramString1);
    Node localNode = getNode(paramClass, paramString1);
    if (paramString2 != null) {
      getFieldType(paramClass, paramString1, false);
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString2, fieldClassArray(getFieldType_arrays, null));
        Class localClass = localMethod.getReturnType();
        if (localClass != Void.TYPE)
          throw new RTSException("Method '" + paramString2 + "' returned type '" + localClass.getName() + "' not equal 'void'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString2 + "' not found in class " + paramClass.getName());
      }
      localNode.validate = localMethod;
    } else {
      localNode.validate = null;
    }
    localNode.flags |= 1073741824;
  }

  public static void validate(Class paramClass, String paramString)
  {
    if (paramClass == null)
      throw new RTSException("Parameter 'class' == null");
    Node localNode = getNode(paramClass);
    if (paramString != null) {
      Method localMethod = null;
      try {
        localMethod = paramClass.getMethod(paramString, null);
        Class localClass = localMethod.getReturnType();
        if (localClass != Void.TYPE)
          throw new RTSException("Method '" + paramString + "' returned type '" + localClass.getName() + "' not equal 'void'");
      } catch (Exception localException) {
        printDebug(localException);
        throw new RTSException("Method '" + paramString + "' not found in class " + paramClass.getName());
      }
      localNode.validate = localMethod;
    } else {
      localNode.validate = null;
    }
    localNode.flags |= 1073741824;
  }

  private static void stackClear()
  {
    stackPtr = -1;
  }
  private static int stackSize() {
    return stackPtr + 1;
  }
  private static void stackPop() {
    stackClasses.set(stackPtr, null);
    stackObjects.set(stackPtr, null);
    stackFields.set(stackPtr, null);
    stackBundles.set(stackPtr, null);
    stackPtr -= 1;
    if (stackPtr >= 0)
      setCurObject(); 
  }

  private static void stackPop(int paramInt) {
    while (stackPtr > paramInt) {
      stackClasses.set(stackPtr, null);
      stackObjects.set(stackPtr, null);
      stackFields.set(stackPtr, null);
      stackBundles.set(stackPtr, null);
      stackPtr -= 1;
    }
    if (stackPtr >= 0)
      setCurObject(); 
  }

  private static void stackPush(Object paramObject, Class paramClass) {
    stackPtr += 1;
    if (stackPtr + 1 > stackObjects.size()) {
      stackClasses.add(null);
      stackObjects.add(null);
      stackFields.add(null);
      stackBundles.add(null);
    }
    setCurObject(paramObject, paramClass);
  }
  private static void setCurObject(Object paramObject, Class paramClass) {
    stackClasses.set(stackPtr, paramClass);
    stackObjects.set(stackPtr, paramObject);
    setCurObject();
    if (bUseBundle)
      if ((paramClass.isPrimitive()) || (paramClass == String.class) || (paramClass == StringBuffer.class) || (paramClass.isArray()))
      {
        stackBundles.set(stackPtr, null);
      } else {
        ResourceBundle localResourceBundle = null;
        try {
          localResourceBundle = ResourceBundle.getBundle(paramClass.getName() + "IO", Locale.getDefault(), LDRres.loader()); } catch (Exception localException) {
        }
        stackBundles.set(stackPtr, localResourceBundle);
      }
  }

  private static void setCurObject() {
    curObjectNode.clear();
    curObjectObjects.clear();
    for (int i = 0; i <= stackPtr; i++) {
      Class localClass = (Class)stackClasses.get(i);
      int j = 0;
      for (int k = i; k < stackPtr; k++) {
        localObject = stackFields.get(k);
        if ((localObject instanceof Field)) {
          Field localField = (Field)localObject;
          if (k != i) j = Finger.incInt(j, ".");
          j = Finger.incInt(j, localField.getName());
        } else {
          j = Finger.incInt(j, "[]");
        }
      }
      j = Finger.incInt(j, "$ObjIO");
      Object localObject = (Node)Property.value(localClass, j, null);
      if (localObject != null)
        curObjectNode.set((Node)localObject, stackObjects.get(i), curObjectObjects); 
    }
  }

  private static boolean setCurField(boolean paramBoolean1, Field paramField, boolean paramBoolean2) {
    int i = paramField.getModifiers();
    if ((i & 0x10) != 0) return false;
    if (paramBoolean2) {
      if ((i & 0x8) == 0) return false;
    }
    else if ((i & 0x8) != 0) return false;

    setCurFieldNode(paramField);
    return true;
  }

  private static boolean setCurField(boolean paramBoolean1, Integer paramInteger, boolean paramBoolean2) {
    setCurFieldNode(paramInteger);
    return true;
  }

  private static boolean setCurField(boolean paramBoolean, Field paramField)
    throws IllegalAccessException, InstantiationException
  {
    int i = paramField.getModifiers();
    if ((i & 0x10) != 0) return false;
    setCurFieldNode(paramField);
    if ((paramBoolean) && ((curFieldNode.flags & 0x1) != 0)) return false;
    if (((i & 0x8) == 0) && (stackObjects.get(stackPtr) == null))
    {
      Class localClass = (Class)stackClasses.get(stackPtr);
      Object localObject = localClass.newInstance();
      setCurObject(localObject, localClass);
    }
    return true;
  }

  private static boolean setCurField(boolean paramBoolean, Integer paramInteger)
    throws IllegalAccessException, InstantiationException
  {
    setCurFieldNode(paramInteger);
    return (!paramBoolean) || ((curFieldNode.flags & 0x1) == 0);
  }

  private static void setCurFieldNode(Object paramObject)
  {
    stackFields.set(stackPtr, paramObject);
    curFieldNode.clear();
    curFieldObjects.clear();
    for (int i = 0; i <= stackPtr; i++) {
      Class localClass = (Class)stackClasses.get(i);
      int j = 0;
      Object localObject1 = stackFields.get(i);
      if ((localObject1 instanceof Field)) {
        Field localField = (Field)localObject1;
        j = Finger.Int(localField.getName());
      } else {
        j = Finger.Int("[]");
      }
      for (int k = i + 1; k <= stackPtr; k++) {
        localObject1 = stackFields.get(k);
        if ((localObject1 instanceof Field)) {
          localObject2 = (Field)localObject1;
          j = Finger.incInt(j, ".");
          j = Finger.incInt(j, ((Field)localObject2).getName());
        } else {
          j = Finger.incInt(j, "[]");
        }
      }
      j = Finger.incInt(j, "$IO");
      Object localObject2 = (Node)Property.value(localClass, j, null);
      if (localObject2 != null)
        curFieldNode.set((Node)localObject2, stackObjects.get(i), curFieldObjects);
    }
  }

  private static int fillObjectArray(Object[] paramArrayOfObject) {
    int i = 0;
    for (int j = 0; j <= stackPtr; j++) {
      Object localObject = stackFields.get(j);
      if ((localObject instanceof Integer)) {
        if (paramArrayOfObject != null)
          paramArrayOfObject[i] = localObject;
        i++;
      }
    }
    return i;
  }

  private static Object[] fieldObjectArray(Object paramObject, boolean paramBoolean) {
    int i = fillObjectArray(null);
    if (paramBoolean) i++;
    if (i == 0)
      return null;
    if ((_fieldsObject != null) && (_fieldsObject.length != i))
      _fieldsObject = null;
    if (_fieldsObject == null)
      _fieldsObject = new Object[i];
    fillObjectArray(_fieldsObject);
    if (paramBoolean)
      _fieldsObject[(i - 1)] = paramObject;
    return _fieldsObject;
  }

  private static Object fieldGet(boolean paramBoolean)
  {
    Object localObject1 = stackObjects.get(stackPtr);
    Object localObject2 = stackFields.get(stackPtr);
    try {
      if ((paramBoolean) && ((curFieldNode.flags & 0x4000000) != 0)) {
        if (curFieldNode.edget != null)
          return curFieldNode.edget.invoke(curFieldObjects.edget, fieldObjectArray(null, false));
      } else if ((curFieldNode.flags & 0x400000) != 0) {
        if (curFieldNode.get != null)
          return curFieldNode.get.invoke(curFieldObjects.get, fieldObjectArray(null, false));
      } else {
        if ((localObject2 instanceof Field)) {
          localObject3 = (Field)localObject2;
          return ((Field)localObject3).get(localObject1);
        }
        Object localObject3 = (Integer)localObject2;
        return Array.get(localObject1, ((Integer)localObject3).intValue());
      }
    }
    catch (Exception localException) {
      printDebug(localException);
    }
    return null;
  }

  private static void fieldSet(boolean paramBoolean, Object paramObject) {
    Object localObject1 = stackObjects.get(stackPtr);
    Object localObject2 = stackFields.get(stackPtr);
    try {
      if (paramObject != null) {
        if ((curFieldNode.min != null) || (curFieldNode.max != null))
          paramObject = checkRange(paramObject, curFieldNode.min, curFieldNode.max);
        if (curFieldNode.jdField_enum != null)
          paramObject = checkEnum(paramObject, curFieldNode.jdField_enum);
      }
      if ((paramBoolean) && ((curFieldNode.flags & 0x8000000) != 0)) {
        if (curFieldNode.edset != null)
          curFieldNode.edset.invoke(curFieldObjects.edset, fieldObjectArray(paramObject, true));
      }
      else if ((curFieldNode.flags & 0x800000) != 0) {
        if (curFieldNode.set != null)
          curFieldNode.set.invoke(curFieldObjects.set, fieldObjectArray(paramObject, true));
      }
      else
      {
        Object localObject3;
        if ((localObject2 instanceof Field)) {
          localObject3 = (Field)localObject2;
          ((Field)localObject3).set(localObject1, paramObject);
        } else {
          localObject3 = (Integer)localObject2;
          Array.set(localObject1, ((Integer)localObject3).intValue(), paramObject);
        }
      }

      if (curFieldNode.validate != null)
        curFieldNode.validate.invoke(curFieldObjects.validate, fieldObjectArray(null, false));
    }
    catch (Exception localException) {
      printDebug(localException);
    }
  }

  private static void fieldValidate() {
    try {
      if (curFieldNode.validate != null)
        curFieldNode.validate.invoke(curFieldObjects.validate, fieldObjectArray(null, false));
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  private static void objectValidate() {
    if (curObjectNode.validate == null) return;
    if ((curObjectObjects.validate == null) && (!Modifier.isStatic(curObjectNode.validate.getModifiers())))
      return;
    try {
      curObjectNode.validate.invoke(curObjectObjects.validate, null);
    } catch (Exception localException) {
      printDebug(localException);
    }
  }

  public static boolean toStrings(Map paramMap, Object paramObject)
  {
    if (paramObject == null) return false;
    Class localClass = (paramObject instanceof Class) ? (Class)paramObject : paramObject.getClass();
    boolean bool1 = paramObject == localClass;
    try {
      Field[] arrayOfField = getFields(localClass);
      stackClear();
      stackPush(paramObject, localClass);
      for (int i = 0; i < arrayOfField.length; i++) {
        if (setCurField(false, arrayOfField[i], bool1)) {
          Object localObject = fieldGet(false);
          if (localObject != null) {
            _strBuf.setLength(0);
            boolean bool2 = false;
            if ((curFieldNode.flags & 0x1000000) != 0) {
              putString(_strBuf, localObject.toString());
              bool2 = true;
            }
            else if (arrayOfField[i].getType().isPrimitive()) { bool2 = _toString(_strBuf, localObject, arrayOfField[i].getType(), false); } else {
              bool2 = _toString(_strBuf, localObject, localObject.getClass(), arrayOfField[i].getType() != localObject.getClass());
            }
            if (bool2)
              paramMap.put(arrayOfField[i].getName(), _strBuf.toString());
          }
        }
      }
      stackPop();
      return true;
    } catch (Exception localException) {
      printDebug(localException);
      stackPop(-1);
    }return false;
  }

  public static String toString(Object paramObject, boolean paramBoolean)
  {
    if (paramObject == null) return null;
    _strBuf.setLength(0);
    stackClear();
    boolean bool = _toString(_strBuf, paramObject, (paramObject instanceof Class) ? (Class)paramObject : paramObject.getClass(), paramBoolean);
    stackPop(-1);
    return bool ? _strBuf.toString() : null;
  }

  public static boolean toString(StringBuffer paramStringBuffer, Object paramObject, boolean paramBoolean)
  {
    if (paramObject == null) return false;
    stackClear();
    boolean bool = _toString(paramStringBuffer, paramObject, (paramObject instanceof Class) ? (Class)paramObject : paramObject.getClass(), paramBoolean);
    stackPop(-1);
    return bool;
  }

  private static boolean _toString(StringBuffer paramStringBuffer, Object paramObject, Class paramClass, boolean paramBoolean) {
    if (paramObject == null) return false;
    boolean bool1 = paramObject == paramClass;
    int i = paramStringBuffer.length();
    int j = stackPtr;
    int k;
    int m;
    Object localObject2;
    if (paramClass.isArray())
      try {
        stackPush(paramObject, paramClass);
        Class localClass = paramClass.getComponentType();
        k = Array.getLength(paramObject);
        if (k == 0) {
          stackPop();
          return false;
        }
        paramStringBuffer.append('[');
        if (paramBoolean) {
          if (localClass.isPrimitive()) {
            paramStringBuffer.append(classGetName(paramClass));
          } else {
            paramStringBuffer.append("[L");
            paramStringBuffer.append(classGetName(localClass));
          }
          paramStringBuffer.append(' ');
        }
        paramStringBuffer.append(k);
        for (m = 0; m < k; m++) {
          setCurField(false, new Integer(m), bool1);
          paramStringBuffer.append(',');
          localObject2 = fieldGet(false);
          if (localObject2 != null) {
            if (localClass.isPrimitive())
              _toString(paramStringBuffer, localObject2, localClass, false);
            else
              _toString(paramStringBuffer, localObject2, localObject2.getClass(), localClass != localObject2.getClass());
          }
        }
        stackPop();
        paramStringBuffer.append(']');
        return true;
      } catch (Exception localException1) {
        printDebug(localException1);
        paramStringBuffer.setLength(i);
        stackPop(j);

        return false;
      }
    Object localObject1;
    if ((paramClass.isPrimitive()) || (paramClass == String.class) || (paramClass == StringBuffer.class)) {
      localObject1 = paramObject.toString();
      putString(paramStringBuffer, (String)localObject1);
      return true;
    }
    try {
      stackPush(paramObject, paramClass);
      localObject1 = getFields(paramClass);
      paramStringBuffer.append('(');
      if (paramBoolean)
        paramStringBuffer.append(classGetName(paramClass));
      k = 0;
      for (m = 0; m < localObject1.length; m++) {
        if (setCurField(false, localObject1[m], bool1)) {
          localObject2 = fieldGet(false);
          if (localObject2 != null) {
            int n = paramStringBuffer.length();
            if (k == 0) {
              if (paramBoolean)
                paramStringBuffer.append(',');
            }
            else paramStringBuffer.append(',');

            paramStringBuffer.append(localObject1[m].getName());
            paramStringBuffer.append('=');
            boolean bool2 = false;
            if ((curFieldNode.flags & 0x1000000) != 0) {
              putString(paramStringBuffer, localObject2.toString());
              bool2 = true;
            }
            else if (localObject1[m].getType().isPrimitive()) { bool2 = _toString(paramStringBuffer, localObject2, localObject1[m].getType(), false); } else {
              bool2 = _toString(paramStringBuffer, localObject2, localObject2.getClass(), localObject1[m].getType() != localObject2.getClass());
            }
            if (bool2) k++; else
              paramStringBuffer.setLength(n);
          }
        }
      }
      stackPop();
      if ((k == 0) && (!paramBoolean)) {
        paramStringBuffer.setLength(i);
        return false;
      }
      paramStringBuffer.append(')');
      return true;
    } catch (Exception localException2) {
      printDebug(localException2);
      paramStringBuffer.setLength(i);
      stackPop(j);
    }
    return false;
  }
  private static void putString(StringBuffer paramStringBuffer, String paramString) {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramStringBuffer.append('"'); paramStringBuffer.append('"');
    } else {
      int i = 0;
      int k;
      for (int j = 0; j < paramString.length(); j++) {
        k = paramString.charAt(j);
        if ((k > 32) && (k != 61) && (k != 34) && (k != 44) && (k != 91) && (k != 93) && (k != 40) && (k != 41))
        {
          continue;
        }

        i = 1;
        break;
      }

      if (i != 0) {
        paramStringBuffer.append('"');
        for (k = 0; k < paramString.length(); k++) {
          char c = paramString.charAt(k);
          if (c == '"')
            paramStringBuffer.append('\\');
          paramStringBuffer.append(c);
        }
        paramStringBuffer.append('"');
      } else {
        paramStringBuffer.append(paramString);
      }
    }
  }

  public static Object fromStrings(Object paramObject, Class paramClass, Map paramMap)
  {
    if (paramMap.size() == 0)
      return fromString(paramObject, paramClass, "()");
    _strBuf.setLength(0);
    _strBuf.append('(');
    Iterator localIterator = paramMap.entrySet().iterator();
    int i = 0;
    while (localIterator.hasNext()) {
      if (i > 0) _strBuf.append(',');
      i++;
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      _strBuf.append((String)localEntry.getKey());
      _strBuf.append('=');
      _strBuf.append((String)localEntry.getValue());
    }
    _strBuf.append(')');
    stackClear();
    return _fromString(false, paramObject, paramClass, _strBuf);
  }

  public static Object fromString(Object paramObject, Class paramClass, String paramString)
  {
    _strBuf.setLength(0);
    _strBuf.append(paramString);
    stackClear();
    return _fromString(false, paramObject, paramClass, _strBuf);
  }

  public static Object fromString(Object paramObject, String paramString)
  {
    return fromString(paramObject, null, paramString);
  }

  public static Object fromString(String paramString)
  {
    return fromString(null, null, paramString);
  }

  public static Object fromString(Object paramObject, Class paramClass, StringBuffer paramStringBuffer)
  {
    stackClear();
    return _fromString(false, paramObject, paramClass, paramStringBuffer);
  }

  public static Object fromString(Object paramObject, StringBuffer paramStringBuffer)
  {
    return fromString(paramObject, null, paramStringBuffer);
  }

  public static Object fromString(StringBuffer paramStringBuffer)
  {
    return fromString(null, null, paramStringBuffer);
  }

  private static Object _fromString(boolean paramBoolean, Object paramObject, Class paramClass, StringBuffer paramStringBuffer) {
    int i = getChar(paramStringBuffer);
    String str1 = null;
    Object localObject1;
    Object localObject2;
    if (i == 91) {
      Class localClass1 = null;
      if (paramClass != null) {
        if (!paramClass.isArray())
          return null;
        localClass1 = paramClass.getComponentType();
      }

      int k = stackPtr;
      try
      {
        str1 = getWord(paramStringBuffer);
        if ((str1 == null) || (str1.length() == 0))
          return null;
        if (str1.charAt(0) == '[') {
          if (str1.length() < 2)
            return null;
          m = str1.charAt(1);
          switch (m) { case 76:
            localClass1 = classForName(str1.substring(2)); break;
          case 90:
            localClass1 = Boolean.TYPE; break;
          case 66:
            localClass1 = Byte.TYPE; break;
          case 67:
            localClass1 = Character.TYPE; break;
          case 83:
            localClass1 = Short.TYPE; break;
          case 73:
            localClass1 = Integer.TYPE; break;
          case 74:
            localClass1 = Long.TYPE; break;
          case 70:
            localClass1 = Float.TYPE; break;
          case 68:
            localClass1 = Double.TYPE; break;
          case 69:
          case 71:
          case 72:
          case 75:
          case 77:
          case 78:
          case 79:
          case 80:
          case 81:
          case 82:
          case 84:
          case 85:
          case 86:
          case 87:
          case 88:
          case 89:
          default:
            return null;
          }
          str1 = getWord(paramStringBuffer);
        }
        if (localClass1 == null)
          return null;
        int m = Integer.parseInt(str1);
        if (paramObject != null) {
          Class localClass2 = paramObject.getClass();
          if ((!localClass2.isArray()) || (localClass2.getComponentType() != localClass1)) {
            paramObject = null;
          } else {
            int i1 = Array.getLength(paramObject);
            if (i1 != m)
              paramObject = null;
          }
        }
        if (paramObject == null) {
          paramObject = Array.newInstance(localClass1, m);
        }
        stackPush(paramObject, paramObject.getClass());

        int n = 0;
        while (true) {
          i = getChar(paramStringBuffer);
          if (i == 93) {
            stackPop();
            return paramObject;
          }if (i == 44) {
            setCurField(paramBoolean, new Integer(n));
            localObject1 = null;
            localObject2 = null;
            if ((curFieldNode.flags & 0x2000000) != 0) {
              localObject2 = getWord(paramStringBuffer);
            } else if ((localClass1.isPrimitive()) || (localClass1 == String.class) || (localClass1 == StringBuffer.class))
            {
              localObject2 = _fromString(paramBoolean, null, localClass1, paramStringBuffer);
            } else {
              localObject1 = fieldGet(paramBoolean);
              localObject2 = _fromString(paramBoolean, localObject1, localObject1 == null ? localClass1 : localObject1.getClass(), paramStringBuffer);
            }
            if (localObject2 != localObject1)
              fieldSet(paramBoolean, localObject2);
            else
              fieldValidate();
          } else {
            stackPop();
            return null;
          }
          n++;
        }
      } catch (Exception localException4) {
        printDebug(localException4);
        stackPop(k);
        return null;
      }
    }
    if (i == 40)
    {
      str1 = getWord(paramStringBuffer);
      i = peekChar(paramStringBuffer);
      if ((i == 44) || (i == 41))
      {
        try {
          paramClass = classForName(str1);
          if ((paramObject != null) && (paramObject.getClass() != paramClass))
            paramObject = null;
        } catch (Exception localException1) {
          printDebug(localException1);
          return null;
        }
        if (i == 41) {
          getChar(paramStringBuffer);
          if (paramObject == null) {
            try { paramObject = paramClass.newInstance(); } catch (Exception localException2) {
            }if (paramObject != null) {
              stackPush(paramObject, paramClass);
              objectValidate();
              stackPop();
            }
          }
          return paramObject;
        }
      }
      else {
        if (str1 != null) paramStringBuffer.insert(0, str1);
        paramStringBuffer.insert(0, ',');
      }

      if (paramObject == null) {
        if (paramClass == null)
          return null;
      } else if (paramClass == null) {
        paramClass = paramObject.getClass();
      }

      int j = stackPtr;
      stackPush(paramObject, paramClass);
      while (true)
      {
        i = getChar(paramStringBuffer);
        if (i == 41) {
          objectValidate();
          stackPop();
          return paramObject;
        }
        if (i != 44) {
          stackPop();
          return null;
        }
        String str2 = getWord(paramStringBuffer);
        i = getChar(paramStringBuffer);
        if (i != 61) {
          stackPop();
          return null;
        }
        try {
          Field localField = paramClass.getField(str2);
          if (setCurField(paramBoolean, localField)) {
            if (paramObject == null) paramObject = stackObjects.get(stackPtr);
            Class localClass3 = localField.getType();
            localObject1 = null;
            localObject2 = null;
            if ((curFieldNode.flags & 0x2000000) != 0) {
              localObject2 = getWord(paramStringBuffer);
            } else if ((localClass3.isPrimitive()) || (localClass3 == String.class) || (localClass3 == StringBuffer.class))
            {
              localObject2 = _fromString(paramBoolean, null, localClass3, paramStringBuffer);
            } else {
              localObject1 = fieldGet(paramBoolean);
              localObject2 = _fromString(paramBoolean, localObject1, localObject1 == null ? localClass3 : localObject1.getClass(), paramStringBuffer);
            }
            if (localObject2 != localObject1)
              fieldSet(paramBoolean, localObject2);
            else
              fieldValidate();
          }
        } catch (Exception localException5) {
          printDebug(localException5);
          stackPop(j);
          return null;
        }
      }
    }

    if (paramClass == null)
      return paramObject;
    if (i != 65535) {
      paramStringBuffer.insert(0, i);
      str1 = getWord(paramStringBuffer);
    }
    if ((str1 == null) || (str1.length() == 0))
    {
      if (paramClass == Boolean.TYPE) paramObject = new Boolean(false);
      else if (paramClass == Byte.TYPE) paramObject = new Byte(0);
      else if (paramClass == Short.TYPE) paramObject = new Short(0);
      else if (paramClass == Character.TYPE) paramObject = new Character('\000');
      else if (paramClass == Integer.TYPE) paramObject = new Integer(0);
      else if (paramClass == Long.TYPE) paramObject = new Long(0L);
      else if (paramClass == Float.TYPE) paramObject = new Float(0.0F);
      else if (paramClass == Double.TYPE) paramObject = new Double(0.0D); 
    }
    else {
      try {
        if (paramClass == Boolean.TYPE) paramObject = new Boolean(str1);
        else if (paramClass == Byte.TYPE) paramObject = new Byte(str1);
        else if (paramClass == Short.TYPE) paramObject = new Short(str1);
        else if (paramClass == Character.TYPE) paramObject = new Character(str1.charAt(0));
        else if (paramClass == Integer.TYPE) paramObject = new Integer(str1);
        else if (paramClass == Long.TYPE) paramObject = new Long(str1);
        else if (paramClass == Float.TYPE) paramObject = new Float(str1);
        else if (paramClass == Double.TYPE) paramObject = new Double(str1);
        else if (paramClass == String.class) paramObject = str1;
        else if (paramClass == StringBuffer.class) paramObject = new StringBuffer(str1); 
      }
      catch (Exception localException3) {
        printDebug(localException3);
        return null;
      }
    }
    return paramObject;
  }

  private static char peekChar(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer != null)
    {
      while (paramStringBuffer.length() > 0) {
        int i = paramStringBuffer.charAt(0);
        if (i > 32)
          return i;
        paramStringBuffer.deleteCharAt(0);
      }
    }

    return 65535;
  }
  private static char getChar(StringBuffer paramStringBuffer) {
    if (paramStringBuffer != null)
    {
      while (paramStringBuffer.length() > 0) {
        int i = paramStringBuffer.charAt(0);
        paramStringBuffer.deleteCharAt(0);
        if (i > 32) {
          return i;
        }
      }
    }
    return 65535;
  }
  private static String getWord(StringBuffer paramStringBuffer) {
    if (paramStringBuffer != null) {
      int i = 0;

      while (paramStringBuffer.length() > 0) {
        i = paramStringBuffer.charAt(0);
        if (i > 32)
          break;
        paramStringBuffer.deleteCharAt(0);
      }
      if (i == 34) {
        paramStringBuffer.deleteCharAt(0);
        j = 0;
        k = paramStringBuffer.length();
        m = 0;
        while (j < k) {
          i = paramStringBuffer.charAt(j);
          if (i == 34) {
            if (m == 92) {
              paramStringBuffer.deleteCharAt(j - 1);
              m = 34;
              j--; k--;
            } else {
              str = "";
              if (j > 0)
                str = paramStringBuffer.substring(0, j);
              paramStringBuffer.delete(0, j + 1);
              return str;
            }
          }
          m = i;
          j++;
        }

        paramStringBuffer.setLength(0);
        return null;
      }

      int j = 0;
      int k = paramStringBuffer.length();
      while (j < k) {
        i = paramStringBuffer.charAt(j);
        if ((i <= 32) || (i == 61) || (i == 44) || (i == 41) || (i == 93))
          break;
        j++;
      }
      int m = j - 1;

      String str = "";
      if (m >= 0)
        str = paramStringBuffer.substring(0, m + 1);
      if (j > 0)
        paramStringBuffer.delete(0, j);
      return str;
    }

    return null;
  }

  private static void printDebug(Exception paramException)
  {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }

  private static Res makeRes()
  {
    curRes.clear();
    for (int i = 0; i <= stackPtr; i++) {
      ResourceBundle localResourceBundle = (ResourceBundle)stackBundles.get(i);
      if (localResourceBundle != null) {
        _resBuf.setLength(0);
        for (int j = i; j <= stackPtr; j++) {
          localObject = stackFields.get(j);
          if ((localObject instanceof Field)) {
            Field localField = (Field)localObject;
            if (j != i) _resBuf.append('.');
            _resBuf.append(localField.getName());
          } else {
            _resBuf.append("[]");
          }
        }
        Object localObject = null;
        try {
          localObject = localResourceBundle.getString(_resBuf.toString());
        } catch (Exception localException) {
        }
        if (localObject != null)
          parseRes((String)localObject);
      }
    }
    if (curRes.isEmpty())
      return null;
    return (Res)new Res(curRes);
  }

  private static void parseRes(String paramString) {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str1 = paramString.length();
    int n = -1;
    int i1 = -1;
    do {
      if (n != -1) {
        switch (n) { case 0:
          if (curRes.name != null) break; curRes.name = paramString.substring(j, k); break;
        case 2:
          if (curRes.tip != null) break; curRes.tip = paramString.substring(j, k); break;
        case 1:
          if (curRes.jdField_enum != null) break;
          _resLst.clear();
          _resBuf.setLength(0);
          _resBuf.append(paramString.substring(j, k));
          while (true) {
            str2 = getResWord(_resBuf);
            if ((str2 == null) || (str2.length() == 0))
              break;
            _resLst.add(str2);
          }
          if (_resLst.size() <= 0) break;
          curRes.jdField_enum = _resLst.toArray(); break;
        }

      }

      i = k; j = m; n = i1; i1 = -1;

      String str2 = str1;
      String str3 = paramString.indexOf("$NAME", j);
      if ((str3 >= 0) && (str3 < str2)) {
        i1 = 0; str2 = str3; k = str3; m = str3 + "$NAME".length();
      }
      String str4 = paramString.indexOf("$ENUM", j);
      if ((str4 >= 0) && (str4 < str2)) {
        i1 = 1; str2 = str4; k = str4; m = str4 + "$ENUM".length();
      }
      String str5 = paramString.indexOf("$TIP", j);
      if ((str5 >= 0) && (str5 < str2)) {
        i1 = 2; str2 = str5; k = str5; m = str5 + "$TIP".length();
      }
      if (i1 < 0)
        k = m = str1; 
    }
    while ((n != -1) || (i1 != -1));
  }

  private static String getResWord(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer != null) {
      int i = 0;

      while (paramStringBuffer.length() > 0) {
        i = paramStringBuffer.charAt(0);
        if (i > 32)
          break;
        paramStringBuffer.deleteCharAt(0);
      }
      if (i == 34) {
        paramStringBuffer.deleteCharAt(0);
        j = 0;
        k = paramStringBuffer.length();
        m = 0;
        while (j < k) {
          i = paramStringBuffer.charAt(j);
          if (i == 34) {
            if (m == 92) {
              paramStringBuffer.deleteCharAt(j - 1);
              m = 34;
              j--; k--;
            } else {
              str = "";
              if (j > 0)
                str = paramStringBuffer.substring(0, j);
              paramStringBuffer.delete(0, j + 1);
              return str;
            }
          }
          m = i;
          j++;
        }

        paramStringBuffer.setLength(0);
        return null;
      }

      int j = 0;
      int k = paramStringBuffer.length();
      while (j < k) {
        i = paramStringBuffer.charAt(j);
        if (i <= 32)
          break;
        j++;
      }
      int m = j - 1;
      String str = "";
      if (m >= 0)
        str = paramStringBuffer.substring(0, m + 1);
      if (j > 0)
        paramStringBuffer.delete(0, j);
      return str;
    }

    return null;
  }

  public boolean isValue()
  {
    return this.child == null;
  }

  public int level()
  {
    return this.level;
  }
  public ObjIO parent() { return this.parent;
  }

  public int childCount()
  {
    return this.child == null ? 0 : this.child.size();
  }

  public ObjIO childAt(int paramInt)
  {
    if ((this.child != null) && (paramInt < this.child.size()))
      return (ObjIO)this.child.get(paramInt);
    return null;
  }

  public String tip()
  {
    if ((this.res != null) && 
      (this.res.tip != null)) {
      return this.res.tip;
    }
    return "";
  }
  public String name() {
    return this.name;
  }

  public Object[] getEnum() {
    if (((this.field instanceof Field)) && (((Field)this.field).getType() == Boolean.TYPE))
      return booleanEnum;
    if (this.fieldNode == null)
      return null;
    return this.fieldNode.jdField_enum;
  }

  public Object[] getAliasEnum()
  {
    if (((this.field instanceof Field)) && (((Field)this.field).getType() == Boolean.TYPE)) {
      if ((this.res != null) && (this.res.jdField_enum != null) && (this.res.jdField_enum.length == 2))
        return this.res.jdField_enum;
      return booleanEnum;
    }
    if ((this.fieldNode == null) || (this.fieldNode.jdField_enum == null)) {
      return null;
    }
    if ((this.res != null) && (this.res.jdField_enum != null) && (this.res.jdField_enum.length == this.fieldNode.jdField_enum.length))
      return this.res.jdField_enum;
    return this.fieldNode.jdField_enum;
  }

  public boolean isReadOnly()
  {
    if (!isValue())
      return true;
    if (this.fieldNode == null)
      return false;
    return (this.fieldNode.flags & 0x1) != 0;
  }

  public Class getType()
  {
    if ((this.field instanceof Field)) return ((Field)this.field).getType();
    return this.clazz.getComponentType();
  }

  public boolean isCheckRange()
  {
    if (this.fieldNode == null)
      return false;
    return (this.fieldNode.flags & 0x200000) != 0;
  }

  public Number getCheckRangeMin()
  {
    if (this.fieldNode == null)
      return null;
    return (Number)this.fieldNode.min;
  }

  public Number getCheckRangeMax()
  {
    if (this.fieldNode == null)
      return null;
    return (Number)this.fieldNode.max;
  }

  public Number getCheckRangeStep()
  {
    if (this.fieldNode == null)
      return null;
    return (Number)this.fieldNode.step;
  }

  public String get()
  {
    if (!this.bStrValid)
      validate();
    String str = this.strValue;
    Object[] arrayOfObject1 = getAliasEnum();
    if (arrayOfObject1 != null) {
      Object[] arrayOfObject2 = getEnum();
      if (arrayOfObject2 != arrayOfObject1) {
        for (int i = 0; i < arrayOfObject2.length; i++) {
          if (str.equals(arrayOfObject2[i].toString())) {
            str = arrayOfObject1[i].toString();
            break;
          }
        }
      }
    }
    return str;
  }

  public void set(String paramString)
  {
    if (!isValue()) return;
    if (paramString == null) paramString = "";
    Object[] arrayOfObject = getAliasEnum();
    Object localObject1;
    if (arrayOfObject != null) {
      localObject1 = getEnum();
      if (localObject1 != arrayOfObject) {
        for (int i = 0; i < localObject1.length; i++) {
          if (paramString.equals(arrayOfObject[i].toString())) {
            paramString = localObject1[i].toString();
            break;
          }
        }
      }
    }
    fillStack();

    if ((this.field instanceof Field)) localObject1 = ((Field)this.field).getType(); else
      localObject1 = this.clazz.getComponentType();
    Object localObject2 = null;
    Object localObject3 = null;
    if ((curFieldNode.flags & 0x22000000) != 0) {
      localObject3 = paramString;
    } else if ((((Class)localObject1).isPrimitive()) || (localObject1 == String.class) || (localObject1 == StringBuffer.class))
    {
      _strBuf.setLength(0);
      _strBuf.append(paramString);
      localObject3 = _fromString(true, null, (Class)localObject1, _strBuf);
    } else {
      localObject2 = fieldGet(true);
      _strBuf.setLength(0);
      _strBuf.append(paramString);
      localObject3 = _fromString(true, localObject2, localObject2 == null ? localObject1 : localObject2.getClass(), _strBuf);
    }
    if (localObject3 != localObject2)
      fieldSet(true, localObject3);
    else
      fieldValidate();
    stackPop(-1);
    invalidate();
  }

  private void validate() {
    if (this.bStrValid) return;
    if (this.child != null) {
      int i = this.child.size();
      for (int j = 0; j < i; j++) {
        ObjIO localObjIO1 = (ObjIO)this.child.get(j);
        if (localObjIO1 != null)
          localObjIO1.validate();
      }
      _strBuf.setLength(0);
      if (this.clazz.isArray()) _strBuf.append('['); else
        _strBuf.append('(');
      for (int k = 0; k < i; k++) {
        if (k > 0) _strBuf.append(',');
        ObjIO localObjIO2 = (ObjIO)this.child.get(k);
        if (localObjIO2 != null) {
          String str = localObjIO2.strValue;
          Object[] arrayOfObject1 = localObjIO2.getAliasEnum();
          if (arrayOfObject1 != null) {
            Object[] arrayOfObject2 = localObjIO2.getEnum();
            if (arrayOfObject2 != arrayOfObject1) {
              for (int m = 0; m < arrayOfObject2.length; m++) {
                if (str.equals(arrayOfObject2[m].toString())) {
                  str = arrayOfObject1[m].toString();
                  break;
                }
              }
            }
          }
          _strBuf.append(str);
        }
      }
      if (this.clazz.isArray()) _strBuf.append(']'); else
        _strBuf.append(')');
      this.strValue = _strBuf.toString();
    } else {
      fillStack();
      Object localObject = fieldGet(true);
      if (localObject != null)
        this.strValue = localObject.toString();
      else
        this.strValue = "";
      stackPop(-1);
    }
    this.bStrValid = true;
  }

  private void invalidate() {
    this.bStrValid = false;
    if (this.parent != null)
      this.parent.invalidate();
  }

  private void checkReadOnly(boolean paramBoolean) {
    if (paramBoolean) {
      if (this.fieldNode == null) this.fieldNode = readonlyNode; else
        this.fieldNode.flags |= 1;
    } else if ((this.fieldNode != null) && ((this.fieldNode.flags & 0x1) != 0)) {
      paramBoolean = true;
    }

    if (this.child != null) {
      int i = this.child.size();
      for (int j = 0; j < i; j++) {
        ObjIO localObjIO = (ObjIO)this.child.get(j);
        if (localObjIO != null)
          localObjIO.checkReadOnly(paramBoolean);
      }
    }
  }

  private void fillStack() {
    ObjIO localObjIO = this;
    stackPtr = localObjIO.level;
    while ((localObjIO != null) && (localObjIO.level >= 0)) {
      int i = localObjIO.level;
      stackClasses.set(i, this.clazz);
      stackObjects.set(i, this.object);
      stackFields.set(i, this.field);
      localObjIO = localObjIO.parent;
    }
    if (this.fieldNode != null) curFieldNode = this.fieldNode; else
      curFieldNode = emptyNode;
    if (this.fieldObjects != null) curFieldObjects = this.fieldObjects; else
      curFieldObjects = emptyObjects;
    if (this.objectNode != null) curObjectNode = this.objectNode; else
      curObjectNode = emptyNode;
    if (this.objectObjects != null) curObjectObjects = this.objectObjects; else
      curObjectObjects = emptyObjects;
  }

  private void fillCurrent()
  {
    if (!curFieldNode.isEmpty()) {
      this.fieldNode = new Node(curFieldNode);
      if (!curFieldObjects.isEmpty())
        this.fieldObjects = new NodeObjects(curFieldObjects);
    }
    if (!curObjectNode.isEmpty()) {
      this.objectNode = new Node(curObjectNode);
      if (!curObjectObjects.isEmpty())
        this.objectObjects = new NodeObjects(curObjectObjects);
    }
    this.clazz = ((Class)stackClasses.get(stackPtr));
    this.object = stackObjects.get(stackPtr);
    this.field = stackFields.get(stackPtr);
  }

  private void fillNull(StringBuffer paramStringBuffer, ObjIO paramObjIO, int paramInt) {
    this.name = paramStringBuffer.toString();
    this.parent = paramObjIO;
    this.level = paramInt;
    this.fieldNode = readonlyNode;
    this.bStrValid = true;
    this.strValue = "null";
  }

  private boolean make(StringBuffer paramStringBuffer, ObjIO paramObjIO, int paramInt, Object paramObject, Class paramClass)
  {
    this.name = paramStringBuffer.toString();
    this.parent = paramObjIO;
    this.level = paramInt;

    if (paramObject == null) return false;
    boolean bool1 = paramObject == paramClass;
    int i = stackPtr;
    int j = paramStringBuffer.length();
    int k;
    ObjIO localObjIO;
    if (paramClass.isArray()) {
      try {
        stackPush(paramObject, paramClass);
        Class localClass = paramClass.getComponentType();
        k = Array.getLength(paramObject);
        if (k == 0) {
          stackPop();
          return false;
        }
        this.child = new ArrayList();
        for (int m = 0; m < k; m++) {
          setCurField(true, new Integer(m), bool1);
          paramStringBuffer.append("[" + m + "]");
          Object localObject2 = fieldGet(true);
          localObjIO = new ObjIO();
          if (localObject2 != null) {
            if (localClass.isPrimitive())
              localObjIO.make(paramStringBuffer, this, this.level + 1, localObject2, localClass);
            else
              localObjIO.make(paramStringBuffer, this, this.level + 1, localObject2, localObject2.getClass());
          }
          else localObjIO.fillNull(paramStringBuffer, this, this.level + 1);

          this.child.add(localObjIO);
          paramStringBuffer.setLength(j);
        }
        stackPop();
        return true;
      } catch (Exception localException1) {
        printDebug(localException1);
        paramStringBuffer.setLength(j);
        stackPop(i);

        return false;
      }
    }
    if ((paramClass.isPrimitive()) || (paramClass == String.class) || (paramClass == StringBuffer.class)) {
      this.strValue = paramObject.toString();
      this.bStrValid = true;
      return true;
    }
    try
    {
      stackPush(paramObject, paramClass);
      Field[] arrayOfField = getFields(paramClass);
      this.child = new ArrayList();
      for (k = 0; k < arrayOfField.length; k++) {
        if (setCurField(true, arrayOfField[k], bool1)) {
          Object localObject1 = fieldGet(true);
          if (localObject1 != null) {
            if (paramStringBuffer.length() > 0) {
              paramStringBuffer.append('.');
            }
            boolean bool2 = false;
            localObjIO = new ObjIO();
            if ((localObjIO.res != null) && (localObjIO.res.name != null))
              paramStringBuffer.append(localObjIO.res.name);
            else
              paramStringBuffer.append(arrayOfField[k].getName());
            if ((curFieldNode.flags & 0x11000000) != 0) {
              localObjIO.strValue = localObject1.toString();
              localObjIO.bStrValid = true;
              localObjIO.name = paramStringBuffer.toString();
              localObjIO.parent = this;
              this.level += 1;
              bool2 = true;
            }
            else if (arrayOfField[k].getType().isPrimitive()) { bool2 = localObjIO.make(paramStringBuffer, this, this.level + 1, localObject1, arrayOfField[k].getType()); } else {
              bool2 = localObjIO.make(paramStringBuffer, this, this.level + 1, localObject1, localObject1.getClass());
            }
            if (bool2)
              this.child.add(localObjIO);
            paramStringBuffer.setLength(j);
          }
        }
      }
      stackPop();
      if (this.child.size() == 0) {
        this.child = null;
        return false;
      }
      return true;
    } catch (Exception localException2) {
      printDebug(localException2);
      paramStringBuffer.setLength(j);
      stackPop(i);
    }
    return false;
  }

  public void update()
  {
    invalidateTree();
    validate();
  }

  private void invalidateTree() {
    this.bStrValid = false;
    if (this.child != null) {
      int i = this.child.size();
      for (int j = 0; j < i; j++) {
        ObjIO localObjIO = (ObjIO)this.child.get(j);
        if (localObjIO != null)
          localObjIO.invalidateTree();
      }
    }
  }

  public ObjIO(Object paramObject, String paramString)
  {
    _strBuf.setLength(0);
    if (paramString != null)
      _strBuf.append(paramString);
    bUseBundle = true;
    stackClear();
    this.clazz = ((paramObject instanceof Class) ? (Class)paramObject : paramObject.getClass());
    make(_strBuf, null, -1, paramObject, this.clazz);
    stackPop(-1);
    bUseBundle = false;
    checkReadOnly(false);
  }

  private ObjIO() {
    fillCurrent();
    this.res = makeRes();
  }

  static
  {
    readonlyNode.flags |= 1;
  }

  static class Res
  {
    String name;
    Object[] jdField_enum;
    String tip;

    public boolean isEmpty()
    {
      if (this.name != null) return false;
      if (this.jdField_enum != null) return false;
      return this.tip == null;
    }

    public void clear() {
      this.name = null;
      this.jdField_enum = null;
      this.tip = null;
    }
    public Res(Res paramRes) {
      this.name = paramRes.name;
      this.jdField_enum = paramRes.jdField_enum;
      this.tip = paramRes.tip;
    }

    public Res()
    {
    }
  }

  static class FieldCompare
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      Field localField1 = (Field)paramObject1;
      Field localField2 = (Field)paramObject2;
      return localField1.getName().compareTo(localField2.getName());
    }
  }

  static class Node
  {
    public int flags;
    public Object[] jdField_enum;
    public Object min;
    public Object max;
    public Object step;
    public Method get;
    public Method set;
    public Method edget;
    public Method edset;
    public Method validate;

    public void clear()
    {
      this.flags = 0;
      this.jdField_enum = null;
      this.min = null;
      this.max = null;
      this.step = null;
      this.get = null;
      this.set = null;
      this.edget = null;
      this.edset = null;
      this.validate = null;
    }
    public boolean isEmpty() {
      if (this.flags != 0) return false;
      if (this.jdField_enum != null) return false;
      if (this.min != null) return false;
      if (this.max != null) return false;
      if (this.get != null) return false;
      if (this.set != null) return false;
      if (this.edget != null) return false;
      if (this.edset != null) return false;
      return this.validate == null;
    }

    public void set(Node paramNode, Object paramObject, ObjIO.NodeObjects paramNodeObjects) {
      this.flags |= paramNode.flags & 0x1;
      if (((this.flags & 0x100000) == 0) && ((paramNode.flags & 0x100000) != 0)) {
        this.flags |= 1048576;
        this.jdField_enum = paramNode.jdField_enum;
      }
      if (((this.flags & 0x200000) == 0) && ((paramNode.flags & 0x200000) != 0)) {
        this.flags |= 2097152;
        this.min = paramNode.min;
        this.max = paramNode.max;
        this.step = paramNode.step;
      }
      if (((this.flags & 0x400000) == 0) && ((paramNode.flags & 0x400000) != 0)) {
        this.flags |= paramNode.flags & 0x1400000;
        this.get = paramNode.get;
        paramNodeObjects.get = paramObject;
      }
      if (((this.flags & 0x800000) == 0) && ((paramNode.flags & 0x800000) != 0)) {
        this.flags |= paramNode.flags & 0x2800000;
        this.set = paramNode.set;
        paramNodeObjects.set = paramObject;
      }
      if (((this.flags & 0x4000000) == 0) && ((paramNode.flags & 0x4000000) != 0)) {
        this.flags |= paramNode.flags & 0x14000000;
        this.edget = paramNode.edget;
        paramNodeObjects.edget = paramObject;
      }
      if (((this.flags & 0x8000000) == 0) && ((paramNode.flags & 0x8000000) != 0)) {
        this.flags |= paramNode.flags & 0x28000000;
        this.edset = paramNode.edset;
        paramNodeObjects.edset = paramObject;
      }
      if (((this.flags & 0x40000000) == 0) && ((paramNode.flags & 0x40000000) != 0)) {
        this.flags |= 1073741824;
        this.validate = paramNode.validate;
        paramNodeObjects.validate = paramObject;
      }
    }
    public Node() {
    }
    public Node(Node paramNode) {
      this.flags = paramNode.flags;
      this.jdField_enum = paramNode.jdField_enum;
      this.min = paramNode.min;
      this.max = paramNode.max;
      this.step = paramNode.step;
      this.get = paramNode.get;
      this.set = paramNode.set;
      this.edget = paramNode.edget;
      this.edset = paramNode.edset;
      this.validate = paramNode.validate;
    }
  }

  static class NodeObjects
  {
    public Object get;
    public Object set;
    public Object edget;
    public Object edset;
    public Object validate;

    public void clear()
    {
      this.get = null;
      this.set = null;
      this.edget = null;
      this.edset = null;
      this.validate = null;
    }
    public boolean isEmpty() {
      if (this.get != null) return false;
      if (this.set != null) return false;
      if (this.edget != null) return false;
      if (this.edset != null) return false;
      return this.validate == null;
    }
    public NodeObjects() {
    }
    public NodeObjects(NodeObjects paramNodeObjects) {
      this.get = paramNodeObjects.get;
      this.set = paramNodeObjects.set;
      this.edget = paramNodeObjects.edget;
      this.edset = paramNodeObjects.edset;
      this.validate = paramNodeObjects.validate;
    }
  }
}