package com.maddox.il2.fm;

public class FMDamages
{
  public static final long _AILERON_L = 1L;
  public static final long _AILERON_R = 2L;
  public static final long _FUSELAGE = 4L;
  public static final long _ENGINE_1 = 8L;
  public static final long _ENGINE_2 = 16L;
  public static final long _ENGINE_3 = 32L;
  public static final long _ENGINE_4 = 64L;
  public static final long _GEAR_C = 128L;
  public static final long _FLAP_R = 256L;
  public static final long _GEAR_L = 512L;
  public static final long _GEAR_R = 1024L;
  public static final long _VER_STAB_1 = 2048L;
  public static final long _VER_STAB_2 = 4096L;
  public static final long _NOSE = 8192L;
  public static final long _OIL = 16384L;
  public static final long _RUDDER_1 = 32768L;
  public static final long _RUDDER_2 = 65536L;
  public static final long _HOR_STAB_L = 131072L;
  public static final long _HOR_STAB_R = 262144L;
  public static final long _TAIL_1 = 524288L;
  public static final long _TAIL_2 = 1048576L;
  public static final long _TANK_1 = 2097152L;
  public static final long _TANK_2 = 4194304L;
  public static final long _TANK_3 = 8388608L;
  public static final long _TANK_4 = 16777216L;
  public static final long _TURRET_1 = 33554432L;
  public static final long _TURRET_2 = 67108864L;
  public static final long _TURRET_3 = 134217728L;
  public static final long _TURRET_4 = 268435456L;
  public static final long _TURRET_5 = 536870912L;
  public static final long _TURRET_6 = 1073741824L;
  public static final long _ELEVATOR_L = 2147483648L;
  public static final long _ELEVATOR_R = 4294967296L;
  public static final long _WING_ROOT_L = 8589934592L;
  public static final long _WING_MIDDLE_L = 17179869184L;
  public static final long _WING_END_L = 34359738368L;
  public static final long _WING_ROOT_R = 68719476736L;
  public static final long _WING_MIDDLE_R = 137438953472L;
  public static final long _WING_END_R = 274877906944L;
  public static final long _NOMOREPARTS = 17592186044416L;
  public static final long _RETURN_MASK = 547641784443L;
  public static final long _DEATH_MASK = 238372257912L;

  public static final boolean readyToReturn(long paramLong)
  {
    return (paramLong & 0x81FE787B) != 547641784443L;
  }

  public static final boolean readyToDeath(long paramLong)
  {
    return (paramLong & 0x80180078) != 238372257912L;
  }
}