package com.maddox.il2.ai;

public class DifficultySettings
{
  public boolean Wind_N_Turbulence;
  public boolean Flutter_Effect;
  public boolean Stalls_N_Spins;
  public boolean Blackouts_N_Redouts;
  public boolean Engine_Overheat;
  public boolean Torque_N_Gyro_Effects;
  public boolean Realistic_Landings;
  public boolean Takeoff_N_Landing;
  public boolean Cockpit_Always_On;
  public boolean No_Outside_Views;
  public boolean Head_Shake;
  public boolean No_Icons;
  public boolean Realistic_Gunnery;
  public boolean Limited_Ammo;
  public boolean Limited_Fuel;
  public boolean Vulnerability;
  public boolean No_Padlock;
  public boolean Clouds;
  public boolean No_Map_Icons;
  public boolean SeparateEStart;
  public boolean NoInstantSuccess;
  public boolean NoMinimapPath;
  public boolean NoSpeedBar;
  public boolean ComplexEManagement;
  public boolean NewCloudsRender;
  public static final int REALISTIC_MASK = Integer.parseInt("1111111111111111111111111", 2);
  public static final int EASY_MASK = Integer.parseInt("1000000101000000000000000", 2);
  public static final int NORMAL_MASK = Integer.parseInt("1100101101111110011111111", 2);

  public DifficultySettings() {
    set(-1);

    this.Cockpit_Always_On = false;
    this.No_Icons = false;
    this.No_Outside_Views = false;
    this.No_Padlock = false;
    this.No_Map_Icons = false;
  }

  public boolean isRealistic() {
    return ((get() | 0x1000000) ^ REALISTIC_MASK) == 0;
  }

  public boolean isNormal() {
    return ((get() | 0x1000000) ^ NORMAL_MASK) == 0;
  }

  public boolean isEasy() {
    return ((get() | 0x1000000) ^ EASY_MASK) == 0;
  }

  public boolean isCustom() {
    return (!isRealistic()) && (!isNormal()) && (!isEasy());
  }

  public void setRealistic() {
    boolean bool = this.NewCloudsRender;
    set(REALISTIC_MASK);
    this.NewCloudsRender = bool;
  }

  public void setNormal() {
    boolean bool = this.NewCloudsRender;
    set(NORMAL_MASK);
    this.NewCloudsRender = bool;
  }

  public void setEasy() {
    boolean bool = this.NewCloudsRender;
    set(EASY_MASK);
    this.NewCloudsRender = bool;
  }

  public void set(DifficultySettings paramDifficultySettings) {
    set(paramDifficultySettings.get());
  }

  public void set(int paramInt) {
    this.Wind_N_Turbulence = ((paramInt & 0x1) != 0);
    this.Flutter_Effect = ((paramInt & 0x2) != 0);
    this.Stalls_N_Spins = ((paramInt & 0x4) != 0);
    this.Blackouts_N_Redouts = ((paramInt & 0x8) != 0);

    this.Engine_Overheat = ((paramInt & 0x10) != 0);
    this.Torque_N_Gyro_Effects = ((paramInt & 0x20) != 0);
    this.Realistic_Landings = ((paramInt & 0x40) != 0);
    this.Takeoff_N_Landing = ((paramInt & 0x80) != 0);

    this.Cockpit_Always_On = ((paramInt & 0x100) != 0);
    this.No_Outside_Views = ((paramInt & 0x200) != 0);
    this.Head_Shake = ((paramInt & 0x400) != 0);
    this.No_Icons = ((paramInt & 0x800) != 0);

    this.Realistic_Gunnery = ((paramInt & 0x1000) != 0);
    this.Limited_Ammo = ((paramInt & 0x2000) != 0);
    this.Limited_Fuel = ((paramInt & 0x4000) != 0);
    this.Vulnerability = ((paramInt & 0x8000) != 0);

    this.No_Padlock = ((paramInt & 0x10000) != 0);

    this.Clouds = ((paramInt & 0x20000) != 0);
    this.No_Map_Icons = ((paramInt & 0x40000) != 0);

    this.SeparateEStart = ((paramInt & 0x80000) != 0);
    this.NoInstantSuccess = ((paramInt & 0x100000) != 0);
    this.NoMinimapPath = ((paramInt & 0x200000) != 0);
    this.NoSpeedBar = ((paramInt & 0x400000) != 0);
    this.ComplexEManagement = ((paramInt & 0x800000) != 0);

    this.NewCloudsRender = ((paramInt & 0x1000000) != 0);
  }

  public int get() {
    int i = 0;
    if (this.Wind_N_Turbulence) i |= 1;
    if (this.Flutter_Effect) i |= 2;
    if (this.Stalls_N_Spins) i |= 4;
    if (this.Blackouts_N_Redouts) i |= 8;

    if (this.Engine_Overheat) i |= 16;
    if (this.Torque_N_Gyro_Effects) i |= 32;
    if (this.Realistic_Landings) i |= 64;
    if (this.Takeoff_N_Landing) i |= 128;

    if (this.Cockpit_Always_On) i |= 256;
    if (this.No_Outside_Views) i |= 512;
    if (this.Head_Shake) i |= 1024;
    if (this.No_Icons) i |= 2048;

    if (this.Realistic_Gunnery) i |= 4096;
    if (this.Limited_Ammo) i |= 8192;
    if (this.Limited_Fuel) i |= 16384;
    if (this.Vulnerability) i |= 32768;

    if (this.No_Padlock) i |= 65536;

    if (this.Clouds) i |= 131072;
    if (this.No_Map_Icons) i |= 262144;

    if (this.SeparateEStart) i |= 524288;
    if (this.NoInstantSuccess) i |= 1048576;
    if (this.NoMinimapPath) i |= 2097152;
    if (this.NoSpeedBar) i |= 4194304;
    if (this.ComplexEManagement) i |= 8388608;

    if (this.NewCloudsRender) i |= 16777216;

    return i;
  }
}