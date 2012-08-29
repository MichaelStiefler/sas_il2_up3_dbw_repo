package com.maddox.il2.ai;

public final class Army
{
  private static int[] color = { -1, -16777024, -4194304, -16728064, -16727872, -4194112, -4145152, -16777088, -8388608, -16760832, -15957927, -8388480, -8355840, -15047193, -6380544, -13212020, -12550080 };

  private static String[] name = { "None", "Red", "Blue", "Green", "Gold", "Purple", "Aqua", "Maroon", "Navy", "Emerald", "Olive", "Magenta", "Teal", "Orange", "Turquoise", "Brown", "Salad" };

  public static int amountSingle()
  {
    return 3;
  }
  public static int amountNet() {
    return 17;
  }
  public static int color(int paramInt) { return color[paramInt]; } 
  public static String name(int paramInt) {
    return name[paramInt];
  }
}