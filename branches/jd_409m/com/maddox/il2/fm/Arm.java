package com.maddox.il2.fm;

import com.maddox.rts.SectFile;

public class Arm
{
  float AILERON;
  float FLAP;
  float HOR_STAB;
  float VER_STAB;
  float ELEVATOR;
  float RUDDER;
  float WING_ROOT;
  float WING_MIDDLE;
  float WING_END;
  float WING_V;
  float GCENTER;
  float GCENTER_Z;
  float GC_AOA_SHIFT;
  float GC_FLAPS_SHIFT;
  float GC_GEAR_SHIFT;

  public void set(Arm paramArm)
  {
    this.AILERON = paramArm.AILERON;
    this.FLAP = paramArm.FLAP;
    this.HOR_STAB = paramArm.HOR_STAB;
    this.VER_STAB = paramArm.VER_STAB;
    this.ELEVATOR = paramArm.ELEVATOR;
    this.RUDDER = paramArm.RUDDER;
    this.WING_ROOT = paramArm.WING_ROOT;
    this.WING_MIDDLE = paramArm.WING_MIDDLE;
    this.WING_END = paramArm.WING_END;
    this.WING_V = paramArm.WING_V;
    this.GCENTER = paramArm.GCENTER;
    this.GCENTER_Z = paramArm.GCENTER_Z;
    this.GC_AOA_SHIFT = paramArm.GC_AOA_SHIFT;
    this.GC_FLAPS_SHIFT = paramArm.GC_FLAPS_SHIFT;
    this.GC_GEAR_SHIFT = paramArm.GC_GEAR_SHIFT;
  }

  private float getFloat(SectFile paramSectFile, String paramString) {
    return paramSectFile.get("Arm", paramString, 0.0F);
  }

  public void load(SectFile paramSectFile) {
    this.AILERON = getFloat(paramSectFile, "Aileron");
    this.FLAP = getFloat(paramSectFile, "Flap");
    this.HOR_STAB = getFloat(paramSectFile, "Stabilizer");
    this.VER_STAB = getFloat(paramSectFile, "Keel");
    this.ELEVATOR = getFloat(paramSectFile, "Elevator");
    this.RUDDER = getFloat(paramSectFile, "Rudder");
    this.WING_ROOT = getFloat(paramSectFile, "Wing_In");
    this.WING_MIDDLE = getFloat(paramSectFile, "Wing_Mid");
    this.WING_END = getFloat(paramSectFile, "Wing_Out");
    this.WING_V = getFloat(paramSectFile, "Wing_V");
    this.GCENTER = getFloat(paramSectFile, "GCenter");
    this.GCENTER_Z = getFloat(paramSectFile, "GCenterZ");
    this.GC_AOA_SHIFT = getFloat(paramSectFile, "GC_AOA_Shift");
    this.GC_FLAPS_SHIFT = getFloat(paramSectFile, "GC_Flaps_Shift");
    this.GC_GEAR_SHIFT = getFloat(paramSectFile, "GC_Gear_Shift");
  }
}