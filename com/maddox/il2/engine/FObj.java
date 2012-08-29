package com.maddox.il2.engine;

public class FObj extends GObj
  implements FObjInstance
{
  public FObj(int paramInt)
  {
    super(paramInt);
  }

  public String Name()
  {
    return Name(this.jdField_cppObj_of_type_Int);
  }

  public void Rename(String paramString)
  {
    Rename(this.jdField_cppObj_of_type_Int, paramString);
  }

  public long Hash()
  {
    return Hash(this.jdField_cppObj_of_type_Int);
  }

  public static native String Name(int paramInt);

  public static native void Rename(int paramInt, String paramString);

  public static native long Hash(int paramInt);

  public static Object Get(String paramString)
  {
    int i = GetFObj(paramString);
    if (i != 0) {
      Object localObject = GObj.getJavaObject(i);
      GObj.Unlink(i);
      return localObject;
    }
    return null;
  }

  public static Object Get(String paramString1, String paramString2)
  {
    int i = GetFObj(paramString1, paramString2);
    if (i != 0) {
      Object localObject = GObj.getJavaObject(i);
      GObj.Unlink(i);
      return localObject;
    }
    return null;
  }

  public static Object Get(long paramLong)
  {
    int i = GetFObj(paramLong);
    if (i != 0) {
      Object localObject = GObj.getJavaObject(i);
      GObj.Unlink(i);
      return localObject;
    }
    return null;
  }

  public static native int GetFObj(String paramString);

  public static native int GetFObj(String paramString1, String paramString2);

  public static native int GetFObj(long paramLong);

  public static native boolean Exist(String paramString);

  public static native boolean Exist(long paramLong);

  public static native int NextFObj(int paramInt);

  public boolean ReLoad()
  {
    return ReLoad(this.jdField_cppObj_of_type_Int) != 0;
  }

  public static native int ReLoad(int paramInt);

  static {
    GObj.loadNative();
  }
}