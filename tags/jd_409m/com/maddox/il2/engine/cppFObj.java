package com.maddox.il2.engine;

public class cppFObj extends cppGObj
{
  public static final float GObjHASHloadFactor = 0.75F;
  public static final int GObjHASHinitialCapacity = 101;
  private long hash;
  private String name;
  private cppFObj nextFObj;

  private static boolean InsertFObj(cppFObj paramcppFObj)
  {
    return false;
  }

  private static void RemoveFObj(cppFObj paramcppFObj)
  {
  }

  protected void virtual_FObj()
  {
  }

  protected boolean Load()
  {
    return false;
  }

  public String Name()
  {
    return this.name;
  }

  public boolean Rename(String paramString)
  {
    return false;
  }

  public long Hash()
  {
    return this.hash;
  }

  public cppGObj Clone()
  {
    return null;
  }

  public static cppFObj Get(String paramString)
  {
    return null;
  }

  public static cppFObj Get(String paramString1, String paramString2)
  {
    return null;
  }

  public boolean MakeFullName(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    return false;
  }

  public static cppFObj Get(long paramLong)
  {
    return null;
  }

  public static boolean Exist(String paramString)
  {
    return false;
  }

  public static boolean Exist(long paramLong)
  {
    return false;
  }

  public static cppFObj NextFObj(cppFObj paramcppFObj)
  {
    return null;
  }

  public void DEFFCLASS()
  {
  }

  public void IMPFCLASS()
  {
  }

  public void IMPFCLASSGENERIC()
  {
  }
}