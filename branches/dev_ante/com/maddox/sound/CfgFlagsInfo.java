package com.maddox.sound;

public class CfgFlagsInfo
{
  String name;
  int code;
  boolean value;
  boolean isDefault;

  public CfgFlagsInfo(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.name = paramString;
    this.code = paramInt;
    this.value = paramBoolean1;
    this.isDefault = paramBoolean2;
  }
}