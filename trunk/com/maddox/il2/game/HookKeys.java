package com.maddox.il2.game;

import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdTrackIRAngles;
import java.util.ArrayList;

public class HookKeys
{
  public boolean bPanView = true;
  private int az = 0;
  private int tan = 0;
  private int actAz = 0;
  private int actTan = 0;
  private ArrayList snapLst = new ArrayList();
  public static HookKeys current;

  public void resetGame()
  {
    this.az = 0;
    this.tan = 0;
    this.actAz = 0;
    this.actTan = 0;
    int i = this.snapLst.size();
    for (int j = 0; j < i; j++)
      ((HotKeyCmdSnap)(HotKeyCmdSnap)this.snapLst.get(j)).b = false;
  }

  public void setMode(boolean paramBoolean) {
    this.bPanView = paramBoolean;
    this.az = 0;
    this.tan = 0;
    this.actAz = 0;
    this.actTan = 0;
    int i = this.snapLst.size();
    for (int j = 0; j < i; j++)
      ((HotKeyCmdSnap)(HotKeyCmdSnap)this.snapLst.get(j)).b = false; 
  }
  public boolean isPanView() {
    return this.bPanView;
  }
  private boolean snapSet(boolean paramBoolean, int paramInt1, int paramInt2) {
    if (this.bPanView) return false;
    if (paramBoolean) {
      if (paramInt1 != 10) {
        this.actAz += 1;
        this.az += paramInt1;
      }
      if (paramInt2 != 10) {
        this.actTan += 1;
        this.tan += paramInt2;
      }
    }
    else {
      if (paramInt1 != 10) {
        this.actAz -= 1;
        this.az -= paramInt1;
      }
      if (paramInt2 != 10) {
        this.actTan -= 1;
        this.tan -= paramInt2;
      }
    }
    float f1 = this.actAz > 0 ? this.az / this.actAz : 0.0F;
    float f2 = this.actTan > 0 ? this.tan / this.actTan : 0.0F;
    HookPilot.current.snapSet(f1, f2);
    HookGunner.doSnapSet(f1, f2);
    HookView.current.snapSet(f1, f2);
    return true;
  }

  private void panSet(int paramInt1, int paramInt2)
  {
    if (!this.bPanView) return;
    HookPilot.current.panSet(paramInt1, paramInt2);
    HookGunner.doPanSet(paramInt1, paramInt2);
    HookView.current.panSet(paramInt1, paramInt2);
  }

  private void initSnapKeys() {
    String str = "SnapView";
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "SnapPanSwitch", "00") {
      public void begin() {
        HookKeys.this.setMode(!HookKeys.this.isPanView());
      }
      public void created() { setRecordId(60);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_0_0", "01") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 0, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 0, 0);  } 
      public void created() {
        setRecordId(61);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_0_1", "02") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 0, 1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 0, 1);  } 
      public void created() {
        setRecordId(62);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_0_m1", "03") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 0, -1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 0, -1);  } 
      public void created() {
        setRecordId(63);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m1_0", "04") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -1, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -1, 0);  } 
      public void created() {
        setRecordId(64);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_1_0", "05") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 1, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 1, 0);  } 
      public void created() {
        setRecordId(65);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m1_1", "06") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -1, 1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -1, 1);  } 
      public void created() {
        setRecordId(66);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_1_1", "07") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 1, 1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 1, 1);  } 
      public void created() {
        setRecordId(67);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m1_m1", "08") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -1, -1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -1, -1);  } 
      public void created() {
        setRecordId(68);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_1_m1", "09") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 1, -1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 1, -1);  } 
      public void created() {
        setRecordId(69);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m3_0", "10") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -3, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -3, 0);  } 
      public void created() {
        setRecordId(70);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_3_0", "11") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 3, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 3, 0);  } 
      public void created() {
        setRecordId(71);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m3_1", "12") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -3, 1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -3, 1);  } 
      public void created() {
        setRecordId(72);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_3_1", "13") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 3, 1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 3, 1);  } 
      public void created() {
        setRecordId(73);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m3_m1", "14") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -3, -1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -3, -1);  } 
      public void created() {
        setRecordId(74);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_3_m1", "15") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 3, -1); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 3, -1);  } 
      public void created() {
        setRecordId(75);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_0_2", "16") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 10, 2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 10, 2);  } 
      public void created() {
        setRecordId(76);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m2_2", "17") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -2, 2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -2, 2);  } 
      public void created() {
        setRecordId(77);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_2_2", "18") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 2, 2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 2, 2);  } 
      public void created() {
        setRecordId(78);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_0_m2", "19") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 10, -2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 10, -2);  } 
      public void created() {
        setRecordId(79);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m2_m2", "20") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -2, -2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -2, -2);  } 
      public void created() {
        setRecordId(80);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_2_m2", "21") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 2, -2); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 2, -2);  } 
      public void created() {
        setRecordId(81);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_m2_0", "22") {
      public void begin() { this.b = HookKeys.this.snapSet(true, -2, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, -2, 0);  } 
      public void created() {
        setRecordId(82);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdSnap(true, "Snap_2_0", "23") {
      public void begin() { this.b = HookKeys.this.snapSet(true, 2, 0); } 
      public void end() { if (this.b) HookKeys.this.snapSet(false, 2, 0);  } 
      public void created() {
        setRecordId(83);
      } } );
  }

  private void initPanKeys() {
    String str = "PanView";
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdMouseMove(true, "Mouse") {
      public void move(int paramInt1, int paramInt2, int paramInt3) {
        if (HookPilot.current != null)
          HookPilot.current.mouseMove(paramInt1, paramInt2, paramInt3); 
      }

      public void created() {
        this.sortingName = null;
        setRecordId(50);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdTrackIRAngles(true, "TrackIR") {
      public void angles(float paramFloat1, float paramFloat2, float paramFloat3) {
        if (HookPilot.current != null)
          HookPilot.current.viewSet(-paramFloat1, -paramFloat2);
        if (HookGunner.current() != null)
          HookGunner.current().viewSet(-paramFloat1, -paramFloat2);
        if (HookView.current != null)
          HookView.current.viewSet(-paramFloat1, -paramFloat2); 
      }

      public void created() {
        this.sortingName = null;
        setRecordId(53);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanReset", "01") {
      public void begin() { HookKeys.this.panSet(0, 0); } 
      public void created() { setRecordId(90);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanUp", "02") {
      public void begin() { HookKeys.this.panSet(0, 1); } 
      public void created() { setRecordId(97);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanDown", "03") {
      public void begin() { HookKeys.this.panSet(0, -1); } 
      public void created() { setRecordId(98);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanLeft2", "04") {
      public void begin() { HookKeys.this.panSet(-1, 0); } 
      public void created() { setRecordId(92);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanRight2", "05") {
      public void begin() { HookKeys.this.panSet(1, 0); } 
      public void created() { setRecordId(95);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanLeft", "06") {
      public void begin() { HookKeys.this.panSet(-1, 1); } 
      public void created() { setRecordId(91);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanRight", "07") {
      public void begin() { HookKeys.this.panSet(1, 1); } 
      public void created() { setRecordId(94);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanLeft3", "08") {
      public void begin() { HookKeys.this.panSet(-1, -1); } 
      public void created() { setRecordId(93);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "PanRight3", "09") {
      public void begin() { HookKeys.this.panSet(1, -1); } 
      public void created() { setRecordId(96); } } );
  }

  private HookKeys() {
    initPanKeys();
    initSnapKeys();
  }

  public static HookKeys New()
  {
    if (current == null)
      current = new HookKeys();
    return current;
  }

  class HotKeyCmdSnap extends HotKeyCmd
  {
    boolean b;

    public HotKeyCmdSnap(boolean paramString1, String paramString2, String arg4)
    {
      super(paramString2, str);
      HookKeys.this.snapLst.add(this);
    }
  }
}