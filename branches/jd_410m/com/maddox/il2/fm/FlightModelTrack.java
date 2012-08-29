package com.maddox.il2.fm;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Spawn;
import java.io.IOException;
import java.io.PrintStream;

public class FlightModelTrack extends NetObj
  implements NetUpdate
{
  private static final boolean DEBUG = false;
  private Aircraft air;
  private NetChannel channel;
  private int flags;
  private int bits;
  private int weapons;
  private NetMsgGuaranted msgOut;
  private NetMsgInput msgIn;
  private boolean bFirstUpdate = false;
  public static final int CT_GearControl = 1;
  public static final int CT_FlapsControl = 2;
  public static final int CT_BrakeControl = 4;
  public static final int CT_AileronControl = 8;
  public static final int CT_ElevatorControl = 16;
  public static final int CT_RudderControl = 32;
  public static final int CT_TrimHorControl = 64;
  public static final int CT_TrimVerControl = 128;
  public static final int CT_TrimRudControl = 256;
  public static final int MASS_fuel = 512;
  public static final int COCKPIT_AzimuthSpeed = 1024;
  public static final int FM_saveDeep = 2048;
  public static final int FM_shakeLevel = 4096;
  public static final int FM_AMx = 8192;
  public static final int M_0 = 16384;
  public static final int M_0_shift = 14;
  public static final int M_1 = 32768;
  public static final int M_2 = 65536;
  public static final int M_3 = 131072;
  public static final int M_4 = 262144;
  public static final int M_5 = 524288;
  public static final int M_6 = 1048576;
  public static final int M_7 = 2097152;
  public static final int BITSET = -2147483648;
  public static final int WEAPONSET = 1073741824;
  public static final int CT_Sensitivity = 536870912;
  public static final int GUNS = 268435456;
  public static final int EI_tOilIn = 1;
  public static final int EI_tOilOut = 2;
  public static final int EI_tWaterOut = 4;
  public static final int EI_propPhi = 8;
  public static final int EI_compressorManifoldPressure = 16;
  public static final int EI_w = 32;
  public static final int EI_controlThrottle = 64;
  public static final int EI_controlProp = 128;
  public static final int EI_controlMix = 256;
  public static final int EI_controlMagneto = 512;
  public static final int EI_controlCompressor = 1024;
  public static final int CT_RadiatorControl = 1;
  public static final int GEARS_lgear = 2;
  public static final int GEARS_rgear = 4;
  public static final int GEARS_bIsHydroOperable = 8;
  public static final int GEARS_onGround = 16;
  public static final int CT_AirBrakeControl = 32;
  private int bitset = 0;
  private int weaponset = 0;
  private int gunsset = -1;
  private int[] m = new int[8];

  private byte ct_GearControl = 0;
  private float[] _ct_GearControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_FlapsControl = 0;
  private float[] _ct_FlapsControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_BrakeControl = 0;
  private float[] _ct_BrakeControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_AileronControl = 0;
  private float[] _ct_AileronControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_ElevatorControl = 0;
  private float[] _ct_ElevatorControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_RudderControl = 0;
  private float[] _ct_RudderControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_TrimHorControl = 0;
  private float[] _ct_TrimHorControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_TrimVerControl = 0;
  private float[] _ct_TrimVerControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_TrimRudControl = 0;
  private float[] _ct_TrimRudControl = { -1.0F, 1.0F, 32.0F };
  private byte ct_Sensitivity = 0;
  private float[] _ct_Sensitivity = { 0.0F, 1.0F, 100.0F };
  private byte mass_fuel = 0;
  private float[] _mass_fuel = { 0.0F, 65535.0F, 0.00195313F };
  private short mass_fuel1 = 0;
  private float[] _mass_fuel1 = { 0.0F, 65535.0F, 0.5F };
  private byte cockpit_AzimuthSpeed = 0;
  private float[] _cockpit_AzimuthSpeed = { -50.0F, 50.0F, 2.0F };
  private short fm_saveDeep = 0;
  private float[] _fm_saveDeep = { -3.0F, 4.0F, 200.0F };
  private byte fm_shakeLevel = 0;
  private float[] _fm_shakeLevel = { 0.0F, 1.0F, 128.0F };
  private byte fm_AMx = 0;
  private float[] _fm_AMx = { -1000000.0F, 1000000.0F, 1.0E-004F };
  private byte[] ei_tOilIn = new byte[8];
  private float[] _ei_tOilIn = { 0.0F, 400.0F, 0.25F };
  private byte[] ei_tOilOut = new byte[8];
  private float[] _ei_tOilOut = { 0.0F, 400.0F, 0.25F };
  private byte[] ei_tWaterOut = new byte[8];
  private float[] _ei_tWaterOut = { 0.0F, 400.0F, 0.25F };
  private short[] ei_propPhi = new short[8];
  private float[] _ei_propPhi = { 0.0F, 1.6F, 625.0F };
  private byte[] ei_compressorManifoldPressure = new byte[8];
  private float[] _ei_compressorManifoldPressure = { 0.0F, 2.5F, 40.0F };
  private byte[] ei_w = new byte[8];
  private float[] _ei_w = { 0.0F, 400.0F, 0.25F };
  private short[] ei_w1 = new short[8];
  private float[] _ei_w1 = { 0.0F, 640.0F, 51.181252F };
  private byte[] ei_controlThrottle = new byte[8];
  private float[] _ei_controlThrottle = { 0.0F, 1.1F, 100.0F };
  private byte[] ei_controlProp = new byte[8];
  private float[] _ei_controlProp = { -1.0F, 1.0F, 64.0F };
  private byte[] ei_controlMix = new byte[8];
  private float[] _ei_controlMix = { 0.0F, 1.0F, 64.0F };
  private byte[] ei_controlMagneto = new byte[8];
  private byte[] ei_controlCompressor = new byte[8];
  private float cockpitAzimuthSpeed;
  private float codeByteMIN = -1.0F;
  private float codeByteMAX = 1.0F;
  private float codeByteMUL = 32.0F;

  private float codeShortMIN = -1.0F;
  private float codeShortMAX = 1.0F;
  private float codeShortMUL = 32.0F;

  private boolean bFirstInput = true;

  public void setCockpitAzimuthSpeed(float paramFloat)
  {
    this.cockpitAzimuthSpeed = paramFloat;
  }
  public float getCockpitAzimuthSpeed() { return this.cockpitAzimuthSpeed; }

  public void FMupdate(FlightModel paramFlightModel)
  {
    paramFlightModel.Or.wrap();
    if (World.cur().diffCur.Wind_N_Turbulence) World.wind().getVector(paramFlightModel.Loc, paramFlightModel.Vwind); else
      paramFlightModel.Vwind.set(0.0D, 0.0D, 0.0D);
    paramFlightModel.Vair.sub(paramFlightModel.Vwld, paramFlightModel.Vwind);
    paramFlightModel.Or.transformInv(paramFlightModel.Vair, paramFlightModel.Vflow);
    paramFlightModel.AOS = FlightModel.RAD2DEG((float)Math.atan2(paramFlightModel.Vflow.y, FlightModel.hypot(paramFlightModel.Vflow.x, paramFlightModel.Vflow.z)));
  }

  private void codeByteSet(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.codeByteMIN = paramFloat1;
    this.codeByteMAX = paramFloat2;
    this.codeByteMUL = paramFloat3;
  }
  private byte codeByte(float paramFloat) {
    if (paramFloat < this.codeByteMIN) paramFloat = this.codeByteMIN;
    if (paramFloat > this.codeByteMAX) paramFloat = this.codeByteMAX;
    return (byte)((int)(this.codeByteMUL * paramFloat) & 0xFF);
  }
  private float decodeByte(byte paramByte) {
    float f = paramByte;
    return f / this.codeByteMUL;
  }
  private void checkByte(int paramInt, float paramFloat, byte paramByte, float[] paramArrayOfFloat) {
    if (this.bFirstUpdate) { this.flags |= paramInt; return; }
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    byte b = codeByte(paramFloat);
    if (b != paramByte) this.flags |= paramInt; 
  }

  private void checkMByte(int paramInt1, int paramInt2, float paramFloat, byte paramByte, float[] paramArrayOfFloat) {
    if (this.bFirstUpdate) { this.m[paramInt1] |= paramInt2; return; }
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    byte b = codeByte(paramFloat);
    if (b != paramByte) {
      this.flags |= 1 << 14 + paramInt1;
      this.m[paramInt1] |= paramInt2;
    }
  }

  private byte writeByte(int paramInt, float paramFloat, byte paramByte, float[] paramArrayOfFloat) throws IOException {
    return writeByte(paramInt, paramFloat, paramByte, false, paramArrayOfFloat);
  }
  private byte writeByte(int paramInt, float paramFloat, byte paramByte, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.flags & paramInt) == 0) return paramByte;
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    int i = codeByte(paramFloat);
    this.msgOut.writeByte(i);
    if (paramBoolean)
      System.out.println("writeByte " + i + " " + paramFloat);
    return i;
  }
  private byte writeMByte(int paramInt1, int paramInt2, float paramFloat, byte paramByte, float[] paramArrayOfFloat) throws IOException {
    return writeMByte(paramInt1, paramInt2, paramFloat, paramByte, false, paramArrayOfFloat);
  }
  private byte writeMByte(int paramInt1, int paramInt2, float paramFloat, byte paramByte, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.m[paramInt1] & paramInt2) == 0) return paramByte;
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    int i = codeByte(paramFloat);
    this.msgOut.writeByte(i);
    if (paramBoolean)
      System.out.println("writeMByte " + i + " " + paramFloat);
    return i;
  }
  private float readByte(int paramInt, float paramFloat, float[] paramArrayOfFloat) throws IOException {
    return readByte(paramInt, paramFloat, false, paramArrayOfFloat);
  }
  private float readByte(int paramInt, float paramFloat, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.flags & paramInt) == 0) return paramFloat;
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    byte b = this.msgIn.readByte();
    float f = decodeByte(b);
    if (paramBoolean)
      System.out.println("readByte " + b + " " + paramFloat + " " + f);
    return f;
  }
  private float readMByte(int paramInt1, int paramInt2, float paramFloat, float[] paramArrayOfFloat) throws IOException {
    return readMByte(paramInt1, paramInt2, paramFloat, false, paramArrayOfFloat);
  }
  private float readMByte(int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.m[paramInt1] & paramInt2) == 0) return paramFloat;
    codeByteSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    byte b = this.msgIn.readByte();
    float f = decodeByte(b);
    if (paramBoolean)
      System.out.println("readMByte " + b + " " + paramFloat + " " + f);
    return f;
  }

  private void codeShortSet(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.codeShortMIN = paramFloat1;
    this.codeShortMAX = paramFloat2;
    this.codeShortMUL = paramFloat3;
  }
  private short codeShort(float paramFloat) {
    if (paramFloat < this.codeShortMIN) paramFloat = this.codeShortMIN;
    if (paramFloat > this.codeShortMAX) paramFloat = this.codeShortMAX;
    return (short)((int)(this.codeShortMUL * paramFloat) & 0xFFFF);
  }
  private float decodeShort(short paramShort) {
    float f = paramShort;
    return f / this.codeShortMUL;
  }
  private void checkShort(int paramInt, float paramFloat, short paramShort, float[] paramArrayOfFloat) {
    if (this.bFirstUpdate) { this.flags |= paramInt; return; }
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    short s = codeShort(paramFloat);
    if (s != paramShort) this.flags |= paramInt; 
  }

  private void checkMShort(int paramInt1, int paramInt2, float paramFloat, short paramShort, float[] paramArrayOfFloat) {
    if (this.bFirstUpdate) { this.m[paramInt1] |= paramInt2; return; }
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    short s = codeShort(paramFloat);
    if (s != paramShort) {
      this.flags |= 1 << 14 + paramInt1;
      this.m[paramInt1] |= paramInt2;
    }
  }

  private short writeShort(int paramInt, float paramFloat, short paramShort, float[] paramArrayOfFloat) throws IOException {
    return writeShort(paramInt, paramFloat, paramShort, false, paramArrayOfFloat);
  }
  private short writeShort(int paramInt, float paramFloat, short paramShort, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.flags & paramInt) == 0) return paramShort;
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    int i = codeShort(paramFloat);
    this.msgOut.writeShort(i);
    if (paramBoolean)
      System.out.println("writeShort" + i + " " + paramFloat);
    return i;
  }
  private short writeMShort(int paramInt1, int paramInt2, float paramFloat, short paramShort, float[] paramArrayOfFloat) throws IOException {
    if ((this.m[paramInt1] & paramInt2) == 0) return paramShort;
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    int i = codeShort(paramFloat);
    this.msgOut.writeShort(i);
    return i;
  }
  private float readShort(int paramInt, float paramFloat, float[] paramArrayOfFloat) throws IOException {
    return readShort(paramInt, paramFloat, false, paramArrayOfFloat);
  }
  private float readShort(int paramInt, float paramFloat, boolean paramBoolean, float[] paramArrayOfFloat) throws IOException {
    if ((this.flags & paramInt) == 0) return paramFloat;
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    short s = this.msgIn.readShort();
    float f = decodeShort(s);
    if (paramBoolean)
      System.out.println("readShort" + s + " " + paramFloat + " " + f);
    return f;
  }
  private float readMShort(int paramInt1, int paramInt2, float paramFloat, float[] paramArrayOfFloat) throws IOException {
    if ((this.m[paramInt1] & paramInt2) == 0) return paramFloat;
    codeShortSet(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
    return decodeShort(this.msgIn.readShort());
  }

  public void netUpdate() {
    if (!Actor.isValid(this.air)) {
      destroy();
      return;
    }
    if (((isMirror()) || (this.channel == null)) && (!this.bFirstUpdate))
      return;
    FlightModel localFlightModel = this.air.FM;
    RealFlightModel localRealFlightModel = null;
    if ((localFlightModel instanceof RealFlightModel)) {
      localRealFlightModel = (RealFlightModel)localFlightModel;
    }
    this.flags = 0;
    this.bits = 0;
    this.weapons = 0;
    checkByte(1, localFlightModel.CT.GearControl, this.ct_GearControl, this._ct_GearControl);
    checkByte(2, localFlightModel.CT.FlapsControl, this.ct_FlapsControl, this._ct_FlapsControl);
    checkByte(4, localFlightModel.CT.BrakeControl, this.ct_BrakeControl, this._ct_BrakeControl);
    checkByte(8, localFlightModel.CT.AileronControl, this.ct_AileronControl, this._ct_AileronControl);
    checkByte(16, localFlightModel.CT.ElevatorControl, this.ct_ElevatorControl, this._ct_ElevatorControl);
    checkByte(32, localFlightModel.CT.RudderControl, this.ct_RudderControl, this._ct_RudderControl);
    checkByte(64, localFlightModel.CT.getTrimAileronControl(), this.ct_TrimHorControl, this._ct_TrimHorControl);
    checkByte(128, localFlightModel.CT.getTrimElevatorControl(), this.ct_TrimVerControl, this._ct_TrimVerControl);
    checkByte(256, localFlightModel.CT.getTrimRudderControl(), this.ct_TrimRudControl, this._ct_TrimRudControl);
    checkByte(536870912, localFlightModel.CT.Sensitivity, this.ct_Sensitivity, this._ct_Sensitivity);
    checkShort(512, localFlightModel.M.fuel, this.mass_fuel1, this._mass_fuel1);
    checkByte(1024, this.cockpitAzimuthSpeed, this.cockpit_AzimuthSpeed, this._cockpit_AzimuthSpeed);
    if (localRealFlightModel != null) {
      checkShort(2048, localRealFlightModel.saveDeep, this.fm_saveDeep, this._fm_saveDeep);
      checkByte(4096, localRealFlightModel.shakeLevel, this.fm_shakeLevel, this._fm_shakeLevel);
    }
    checkByte(8192, (float)localFlightModel.AM.x, this.fm_AMx, this._fm_AMx);
    for (int i = 0; i < localFlightModel.EI.getNum(); i++) {
      this.m[i] = 0;
      checkMByte(i, 1, localFlightModel.EI.engines[i].tOilIn, this.ei_tOilIn[i], this._ei_tOilIn);
      checkMByte(i, 2, localFlightModel.EI.engines[i].tOilOut, this.ei_tOilOut[i], this._ei_tOilOut);
      checkMByte(i, 4, localFlightModel.EI.engines[i].tWaterOut, this.ei_tWaterOut[i], this._ei_tWaterOut);
      checkMShort(i, 8, localFlightModel.EI.engines[i].getPropPhi(), this.ei_propPhi[i], this._ei_propPhi);
      checkMByte(i, 16, localFlightModel.EI.engines[i].getManifoldPressure(), this.ei_compressorManifoldPressure[i], this._ei_compressorManifoldPressure);
      checkMShort(i, 32, localFlightModel.EI.engines[i].getw(), this.ei_w1[i], this._ei_w1);
      checkMByte(i, 64, localFlightModel.EI.engines[i].getControlThrottle(), this.ei_controlThrottle[i], this._ei_controlThrottle);
      checkMByte(i, 128, localFlightModel.EI.engines[i].getControlProp(), this.ei_controlProp[i], this._ei_controlProp);
      checkMByte(i, 256, localFlightModel.EI.engines[i].getControlMix(), this.ei_controlMix[i], this._ei_controlMix);
      if ((this.ei_controlMagneto[i] != (byte)localFlightModel.EI.engines[i].getControlMagnetos()) || (this.bFirstUpdate)) {
        this.flags |= 1 << 14 + i;
        this.m[i] |= 512;
      }
      if ((this.ei_controlCompressor[i] != (byte)localFlightModel.EI.engines[i].getControlCompressor()) || (this.bFirstUpdate)) {
        this.flags |= 1 << 14 + i;
        this.m[i] |= 1024;
      }
    }

    this.bits |= (localFlightModel.CT.getRadiatorControl() > 0.5F ? 1 : 0);
    this.bits |= (localFlightModel.Gears.lgear ? 2 : 0);
    this.bits |= (localFlightModel.Gears.rgear ? 4 : 0);
    this.bits |= (localFlightModel.Gears.bIsHydroOperable ? 8 : 0);
    this.bits |= (localFlightModel.Gears.onGround ? 16 : 0);
    this.bits |= (localFlightModel.CT.AirBrakeControl > 0.5F ? 32 : 0);
    if (this.bits != this.bitset) {
      this.bitset = this.bits;
      this.flags |= -2147483648;
    }
    for (i = 0; i < 20; i++)
      this.weapons |= (localFlightModel.CT.WeaponControl[i] != 0 ? 1 << i : 0);
    if ((localFlightModel.CT.TWCT & 0x4) != 0) this.weapons |= 4;
    if ((localFlightModel.CT.TWCT & 0x8) != 0) this.weapons |= 8;
    localFlightModel.CT.TWCT = 0;
    if (this.weapons != this.weaponset) {
      this.weaponset = this.weapons;
      this.flags |= 1073741824;
    }
    if (this.bFirstUpdate) {
      this.bitset = (this.bits ^ 0xFFFFFFFF);
      this.weaponset = (this.weapons ^ 0xFFFFFFFF);
      this.flags |= 268435456;
    }
    else {
      i = 0;
      for (int j = 0; j < localFlightModel.CT.Weapons.length; j++) {
        BulletEmitter[] arrayOfBulletEmitter = localFlightModel.CT.Weapons[j];
        if (arrayOfBulletEmitter != null) {
          for (int k = 0; k < arrayOfBulletEmitter.length; k++)
            if ((arrayOfBulletEmitter[k] instanceof Gun)) {
              Gun localGun = (Gun)arrayOfBulletEmitter[k];
              i += localGun.countBullets();
            }
        }
      }
      if (i != this.gunsset) {
        this.flags |= 268435456;
      }
    }
    if (this.flags == 0) return;

    if (!this.bFirstUpdate)
      this.msgOut = new NetMsgGuaranted();
    try
    {
      netWrite();

      if (!this.bFirstUpdate)
        postTo(this.channel, this.msgOut);
    } catch (Exception localException) {
      printDebug(localException);
    }
    if (!this.bFirstUpdate)
      this.msgOut = null;
  }

  private void netWrite() throws IOException {
    FlightModel localFlightModel = this.air.FM;
    RealFlightModel localRealFlightModel = null;
    if ((localFlightModel instanceof RealFlightModel))
      localRealFlightModel = (RealFlightModel)localFlightModel;
    this.msgOut.writeInt(this.flags);

    this.ct_GearControl = writeByte(1, localFlightModel.CT.GearControl, this.ct_GearControl, this._ct_GearControl);
    this.ct_FlapsControl = writeByte(2, localFlightModel.CT.FlapsControl, this.ct_FlapsControl, this._ct_FlapsControl);
    this.ct_BrakeControl = writeByte(4, localFlightModel.CT.BrakeControl, this.ct_BrakeControl, this._ct_BrakeControl);
    this.ct_AileronControl = writeByte(8, localFlightModel.CT.AileronControl, this.ct_AileronControl, this._ct_AileronControl);
    this.ct_ElevatorControl = writeByte(16, localFlightModel.CT.ElevatorControl, this.ct_ElevatorControl, this._ct_ElevatorControl);
    this.ct_RudderControl = writeByte(32, localFlightModel.CT.RudderControl, this.ct_RudderControl, this._ct_RudderControl);
    this.ct_TrimHorControl = writeByte(64, localFlightModel.CT.getTrimAileronControl(), this.ct_TrimHorControl, this._ct_TrimHorControl);
    this.ct_TrimVerControl = writeByte(128, localFlightModel.CT.getTrimElevatorControl(), this.ct_TrimVerControl, this._ct_TrimVerControl);
    this.ct_TrimRudControl = writeByte(256, localFlightModel.CT.getTrimRudderControl(), this.ct_TrimRudControl, this._ct_TrimRudControl);
    this.mass_fuel1 = writeShort(512, localFlightModel.M.fuel, this.mass_fuel1, this._mass_fuel1);
    this.cockpit_AzimuthSpeed = writeByte(1024, this.cockpitAzimuthSpeed, this.cockpit_AzimuthSpeed, this._cockpit_AzimuthSpeed);
    if (localRealFlightModel != null) {
      this.fm_saveDeep = writeShort(2048, localRealFlightModel.saveDeep, this.fm_saveDeep, this._fm_saveDeep);
      this.fm_shakeLevel = writeByte(4096, localRealFlightModel.shakeLevel, this.fm_shakeLevel, this._fm_shakeLevel);
    }
    this.fm_AMx = writeByte(8192, (float)localFlightModel.AM.x, this.fm_AMx, this._fm_AMx);

    for (int i = 0; i < localFlightModel.EI.getNum(); i++) {
      if ((this.flags & 1 << 14 + i) != 0) {
        this.msgOut.writeShort(this.m[i]);

        this.ei_tOilIn[i] = writeMByte(i, 1, localFlightModel.EI.engines[i].tOilIn, this.ei_tOilIn[i], this._ei_tOilIn);
        this.ei_tOilOut[i] = writeMByte(i, 2, localFlightModel.EI.engines[i].tOilOut, this.ei_tOilOut[i], this._ei_tOilOut);
        this.ei_tWaterOut[i] = writeMByte(i, 4, localFlightModel.EI.engines[i].tWaterOut, this.ei_tWaterOut[i], this._ei_tWaterOut);
        this.ei_propPhi[i] = writeMShort(i, 8, localFlightModel.EI.engines[i].getPropPhi(), this.ei_propPhi[i], this._ei_propPhi);
        this.ei_compressorManifoldPressure[i] = writeMByte(i, 16, localFlightModel.EI.engines[i].getManifoldPressure(), this.ei_compressorManifoldPressure[i], this._ei_compressorManifoldPressure);
        this.ei_w1[i] = writeMShort(i, 32, localFlightModel.EI.engines[i].getw(), this.ei_w1[i], this._ei_w1);
        this.ei_controlThrottle[i] = writeMByte(i, 64, localFlightModel.EI.engines[i].getControlThrottle(), this.ei_controlThrottle[i], this._ei_controlThrottle);
        this.ei_controlProp[i] = writeMByte(i, 128, localFlightModel.EI.engines[i].getControlProp(), this.ei_controlProp[i], this._ei_controlProp);
        this.ei_controlMix[i] = writeMByte(i, 256, localFlightModel.EI.engines[i].getControlMix(), this.ei_controlMix[i], this._ei_controlMix);
        if ((this.m[i] & 0x200) != 0)
          this.msgOut.writeByte(this.ei_controlMagneto[i] = (byte)localFlightModel.EI.engines[i].getControlMagnetos());
        if ((this.m[i] & 0x400) != 0) {
          this.msgOut.writeByte(this.ei_controlCompressor[i] = (byte)localFlightModel.EI.engines[i].getControlCompressor());
        }
      }
    }
    if ((this.flags & 0x80000000) != 0) {
      this.msgOut.writeByte(this.bits);
    }

    if ((this.flags & 0x40000000) != 0)
    {
      this.msgOut.writeInt(this.weapons);
    }

    this.ct_Sensitivity = writeByte(536870912, localFlightModel.CT.Sensitivity, this.ct_Sensitivity, this._ct_Sensitivity);

    if ((this.flags & 0x10000000) != 0)
    {
      i = 0;
      for (int j = 0; j < localFlightModel.CT.Weapons.length; j++) {
        BulletEmitter[] arrayOfBulletEmitter = localFlightModel.CT.Weapons[j];
        if (arrayOfBulletEmitter != null) {
          for (int k = 0; k < arrayOfBulletEmitter.length; k++) {
            if ((arrayOfBulletEmitter[k] instanceof Gun)) {
              Gun localGun = (Gun)arrayOfBulletEmitter[k];
              i += localGun.countBullets();
              this.msgOut.writeShort(localGun.countBullets());
            }
          }

        }

      }

      this.gunsset = i;
    }
  }

  public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
    if ((isMaster()) || (this.channel == null) || (!Actor.isValid(this.air))) {
      return false;
    }
    this.msgIn = paramNetMsgInput;
    FlightModel localFlightModel = this.air.FM;
    RealFlightModel localRealFlightModel = null;
    if ((localFlightModel instanceof RealFlightModel))
      localRealFlightModel = (RealFlightModel)this.air.FM;
    this.flags = this.msgIn.readInt();

    localFlightModel.CT.GearControl = readByte(1, localFlightModel.CT.GearControl, this._ct_GearControl);
    if (this.bFirstInput) {
      localFlightModel.CT.forceGear(localFlightModel.CT.GearControl);

      Aircraft.forceGear(this.air.getClass(), this.air.hierMesh(), localFlightModel.CT.GearControl);
    }
    localFlightModel.CT.FlapsControl = readByte(2, localFlightModel.CT.FlapsControl, this._ct_FlapsControl);
    localFlightModel.CT.BrakeControl = readByte(4, localFlightModel.CT.BrakeControl, this._ct_BrakeControl);
    localFlightModel.CT.AileronControl = readByte(8, localFlightModel.CT.AileronControl, this._ct_AileronControl);
    localFlightModel.CT.ElevatorControl = readByte(16, localFlightModel.CT.ElevatorControl, this._ct_ElevatorControl);
    localFlightModel.CT.RudderControl = readByte(32, localFlightModel.CT.RudderControl, this._ct_RudderControl);
    localFlightModel.CT.setTrimAileronControl(readByte(64, localFlightModel.CT.getTrimAileronControl(), this._ct_TrimHorControl));
    localFlightModel.CT.setTrimElevatorControl(readByte(128, localFlightModel.CT.getTrimElevatorControl(), this._ct_TrimVerControl));
    localFlightModel.CT.setTrimRudderControl(readByte(256, localFlightModel.CT.getTrimRudderControl(), this._ct_TrimRudControl));
    if (NetMissionTrack.playingVersion() == 100)
      localFlightModel.M.fuel = readByte(512, localFlightModel.M.fuel, this._mass_fuel);
    else
      localFlightModel.M.fuel = readShort(512, localFlightModel.M.fuel, this._mass_fuel1);
    this.cockpitAzimuthSpeed = readByte(1024, this.cockpitAzimuthSpeed, this._cockpit_AzimuthSpeed);
    if (localRealFlightModel != null) {
      localRealFlightModel.saveDeep = readShort(2048, localRealFlightModel.saveDeep, this._fm_saveDeep);
      localRealFlightModel.shakeLevel = readByte(4096, localRealFlightModel.shakeLevel, this._fm_shakeLevel);
    } else {
      if ((this.flags & 0x800) != 0) this.msgIn.readShort();
      if ((this.flags & 0x1000) != 0) this.msgIn.readByte();
    }
    localFlightModel.AM.x = readByte(8192, (float)localFlightModel.AM.x, this._fm_AMx);

    for (int i = 0; i < localFlightModel.EI.getNum(); i++) {
      if ((this.flags & 1 << 14 + i) != 0) {
        this.m[i] = this.msgIn.readShort();

        localFlightModel.EI.engines[i].tOilIn = readMByte(i, 1, localFlightModel.EI.engines[i].tOilIn, this._ei_tOilIn);
        localFlightModel.EI.engines[i].tOilOut = readMByte(i, 2, localFlightModel.EI.engines[i].tOilOut, this._ei_tOilOut);
        localFlightModel.EI.engines[i].tWaterOut = readMByte(i, 4, localFlightModel.EI.engines[i].tWaterOut, this._ei_tWaterOut);
        localFlightModel.EI.engines[i].setPropPhi(readMShort(i, 8, localFlightModel.EI.engines[i].getPropPhi(), this._ei_propPhi));
        localFlightModel.EI.engines[i].setManifoldPressure(readMByte(i, 16, localFlightModel.EI.engines[i].getManifoldPressure(), this._ei_compressorManifoldPressure));
        if (NetMissionTrack.playingVersion() == 100)
          localFlightModel.EI.engines[i].setw(readMByte(i, 32, localFlightModel.EI.engines[i].getw(), this._ei_w));
        else
          localFlightModel.EI.engines[i].setw(readMShort(i, 32, localFlightModel.EI.engines[i].getw(), this._ei_w1));
        localFlightModel.EI.engines[i].setControlThrottle(readMByte(i, 64, localFlightModel.EI.engines[i].getControlThrottle(), this._ei_controlThrottle));
        localFlightModel.EI.engines[i].setControlProp(readMByte(i, 128, localFlightModel.EI.engines[i].getControlProp(), this._ei_controlProp));
        localFlightModel.EI.engines[i].setControlMix(readMByte(i, 256, localFlightModel.EI.engines[i].getControlMix(), this._ei_controlMix));
        if ((this.m[i] & 0x200) != 0)
          localFlightModel.EI.engines[i].setControlMagneto(this.msgIn.readByte());
        if ((this.m[i] & 0x400) != 0) {
          localFlightModel.EI.engines[i].setControlCompressor(this.msgIn.readByte());
        }
      }
    }
    if ((this.flags & 0x80000000) != 0) {
      this.bits = this.msgIn.readByte();

      localFlightModel.CT.setRadiatorControl((this.bits & 0x1) != 0 ? 1.0F : 0.0F);

      localFlightModel.Gears.lgear = ((this.bits & 0x2) != 0);
      localFlightModel.Gears.rgear = ((this.bits & 0x4) != 0);
      localFlightModel.Gears.bIsHydroOperable = ((this.bits & 0x8) != 0);
      localFlightModel.Gears.onGround = ((this.bits & 0x10) != 0);

      localFlightModel.CT.AirBrakeControl = ((this.bits & 0x20) != 0 ? 1.0F : 0.0F);
    }

    if ((this.flags & 0x40000000) != 0) {
      this.weapons = this.msgIn.readInt();

      for (i = 0; i < 20; i++) {
        localFlightModel.CT.WeaponControl[i] = ((this.weapons & 1 << i) != 0 ? 1 : false);
      }
    }

    localFlightModel.CT.Sensitivity = readByte(536870912, localFlightModel.CT.Sensitivity, this._ct_Sensitivity);

    if ((this.flags & 0x10000000) != 0)
    {
      for (i = 0; i < localFlightModel.CT.Weapons.length; i++) {
        BulletEmitter[] arrayOfBulletEmitter = localFlightModel.CT.Weapons[i];
        if (arrayOfBulletEmitter != null) {
          for (int j = 0; j < arrayOfBulletEmitter.length; j++) {
            if ((arrayOfBulletEmitter[j] instanceof Gun)) {
              if (this.msgIn.available() < 4) break;
              int k = this.msgIn.readShort();
              Gun localGun = (Gun)arrayOfBulletEmitter[j];
              localGun._loadBullets(k);
            }
          }

        }

      }

    }

    localFlightModel.CT.setPowerControl(localFlightModel.EI.engines[0].getControlThrottle());
    localFlightModel.CT.setStepControl(localFlightModel.EI.engines[0].getControlProp());
    localFlightModel.CT.setMixControl(localFlightModel.EI.engines[0].getControlMix());
    localFlightModel.CT.setMagnetoControl(localFlightModel.EI.engines[0].getControlMagnetos());
    localFlightModel.CT.setCompressorControl(localFlightModel.EI.engines[0].getControlCompressor());

    this.msgIn = null;

    if (isMirrored()) {
      NetMsgGuaranted localNetMsgGuaranted = this.msgOut;
      this.msgOut = new NetMsgGuaranted();
      try {
        netWrite();
        post(this.msgOut);
      } catch (Exception localException) {
        printDebug(localException);
      }
      this.msgOut = localNetMsgGuaranted;
    }
    this.bFirstInput = false;
    return true;
  }

  public FlightModelTrack(Aircraft paramAircraft)
  {
    super(null);
    this.air = paramAircraft;
    this.air.setFMTrack(this);
  }

  public FlightModelTrack(Aircraft paramAircraft, NetChannel paramNetChannel, int paramInt) {
    super(null, paramNetChannel, paramInt);
    this.air = paramAircraft;
    this.air.setFMTrack(this);
    this.channel = paramNetChannel;
    ((NetUser)NetEnv.host()).setArmy(this.air.getArmy());
    this.air.FM.Gravity = (this.air.FM.M.getFullMass() * Atmosphere.g());
  }

  public void msgNetDelChannel(NetChannel paramNetChannel) {
    if (this.channel == paramNetChannel)
      this.channel = null;
  }

  public void destroy() {
    super.destroy();
    this.air = null;
    this.channel = null;
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    if (!(paramNetChannel instanceof NetChannelOutStream))
      return null;
    if (!paramNetChannel.isMirrored(this.air.net))
      return null;
    if (paramNetChannel.isMirrored(this))
      return null;
    NetMsgSpawn localNetMsgSpawn = new NetMsgSpawn(this);
    localNetMsgSpawn.writeNetObj(this.air.net);
    this.channel = paramNetChannel;
    this.msgOut = localNetMsgSpawn;
    this.bFirstUpdate = true;
    netUpdate();
    this.bFirstUpdate = false;
    this.msgOut = null;
    return localNetMsgSpawn;
  }

  static
  {
    Spawn.add(FlightModelTrack.class, new SPAWN());
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        Aircraft localAircraft = (Aircraft)localNetObj.superObj();
        FlightModelTrack localFlightModelTrack = new FlightModelTrack(localAircraft, paramNetMsgInput.channel(), paramInt);
        if (paramNetMsgInput.available() > 0)
          localFlightModelTrack.netInput(paramNetMsgInput); 
      } catch (Exception localException) {
        FlightModelTrack.access$000(localException);
      }
    }
  }
}