package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.order.Order;
import com.maddox.il2.game.order.OrderAnyone_Help_Me;
import com.maddox.il2.game.order.OrderVector_To_Home_Base;
import com.maddox.il2.game.order.OrderVector_To_Target;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserStat;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.rts.IniFile;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class HUD
{
  public boolean bDrawAllMessages = true;
  public boolean bDrawVoiceMessages = true;

  private boolean bNoSubTitles = false;
  private int subTitlesLines = 11;
  private boolean bNoHudLog = false;
  private Main3D main3d;
  private int viewDX;
  private int viewDY;
  long timeLoadLimit = 0L;
  int cnt;
  private String[][] renderSpeedSubstrings = { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null } };

  private int iDrawSpeed = 1;

  private int lastDrawSpeed = -1;
  private Order[] order;
  private String[] orderStr;
  public ResourceBundle resOrder;
  public ResourceBundle resMsg;
  private int msgX0;
  private int msgY0;
  private int msgDX;
  private int msgDY;
  private int msgSIZE;
  private int msgSpaceLen;
  private ArrayList msgLines = new ArrayList();
  private int[][] msgColor = { { -822083329, -822083329, -822083329, -822059105, -822067233, -822083329, -822083329, -822083329, -822083329 }, { -805371904, -805371904, -805371904, -811639040, -807452928, -805371904, -805371904, -805371904, -805371904 } };
  private int trainingX0;
  private int trainingY0;
  private int trainingDX;
  private int trainingDY;
  private int trainingSIZE;
  private int trainingSpaceLen;
  private ArrayList trainingLines = new ArrayList();

  private static int idLog = 1;
  public ResourceBundle resLog;
  private String logRightBottom;
  private long logRightBottomTime;
  private static final long logCenterTimeLife = 5000L;
  private String logCenter;
  private long logCenterTime;
  private String logIntro;
  private String logIntroESC;
  private TTFont fntCenter;
  private boolean bCoopTimeStart = false;
  private long coopTimeStart;
  private static final int lenLogBuf = 3;
  private static final long logTimeLife = 10000L;
  private static final long logTimeFire = 5000L;
  private String[] logBuf = new String[3];
  private String[] logBufStr = new String[3];
  private int[] logBufId = new int[3];
  private long[] logTime = new long[3];
  private int logPtr = 0;
  private int logLen = 0;

  private boolean bDrawNetStat = false;
  private int pageNetStat = 0;
  private int pageSizeNetStat = 0;

  ArrayList statUsers = new ArrayList();

  private ArrayList pointers = new ArrayList();
  private int nPointers = 0;
  private Mat spritePointer;
  public boolean bDrawDashBoard = true;

  private Point3d _p = new Point3d();
  private Orient _o = new Orient();
  private Orient _o1 = new Orient();
  private Orient _oNull = new Orient(0.0F, 0.0F, 0.0F);
  private Mat spriteLeft;
  private Mat spriteRight;
  private Mat spriteG;
  private Mesh meshNeedle1;
  private Mesh meshNeedle2;
  private Mesh meshNeedle3;
  private Mesh meshNeedle4;
  private Mesh meshNeedle5;
  private Mesh meshNeedle6;
  private Mesh meshNeedleMask;
  private TTFont fntLcd;

  public static int drawSpeed()
  {
    return Main3D.cur3D().hud.iDrawSpeed;
  }
  public static void setDrawSpeed(int paramInt) {
    Main3D.cur3D().hud.iDrawSpeed = paramInt;
  }

  private final void renderSpeed() {
    if (!Actor.isValid(World.getPlayerAircraft())) {
      return;
    }
    if (this.iDrawSpeed == 0) {
      return;
    }
    if (!this.bDrawAllMessages) {
      return;
    }
    if ((Main.cur().netServerParams != null) && 
      (!Main.cur().netServerParams.isShowSpeedBar())) {
      return;
    }

    TTFont localTTFont = TTFont.font[1];
    int i = localTTFont.height();
    int j = -1073741569;
    int k = (int)(World.getPlayerFM().Or.getYaw() + 0.5F);
    k = k > 90 ? 450 - k : 90 - k;

    int i1 = 0;
    float f = World.getPlayerFM().getLoadDiff();

    if ((f <= World.getPlayerFM().getLimitLoad() * 0.25F) && (f > World.getPlayerFM().getLimitLoad() * 0.1F)) {
      i1 = 1;
      this.cnt = 0;
      this.timeLoadLimit = 0L;
    }
    else if ((f <= World.getPlayerFM().getLimitLoad() * 0.1F) && (Time.current() < this.timeLoadLimit)) {
      i1 = 0;
    }
    else if ((f <= World.getPlayerFM().getLimitLoad() * 0.1F) && (Time.current() >= this.timeLoadLimit)) {
      i1 = 1;

      this.cnt += 1;
      if (this.cnt == 22) {
        this.timeLoadLimit = (125L + Time.current());
        this.cnt = 0;
      }
    } else {
      this.cnt = 0;
      this.timeLoadLimit = 0L;
    }
    String str;
    int m;
    int n;
    switch (this.iDrawSpeed) {
    case 1:
    default:
      str = ".si";
      m = (int)(World.getPlayerFM().getAltitude() * 0.1F) * 10;
      n = (int)(3.6F * Pitot.Indicator((float)World.getPlayerFM().Loc.z, World.getPlayerFM().getSpeed()) * 0.1F) * 10;
      break;
    case 2:
      str = ".gb";
      m = (int)(3.28084F * World.getPlayerFM().getAltitude() * 0.02F) * 50;
      n = (int)(1.943845F * Pitot.Indicator((float)World.getPlayerFM().Loc.z, World.getPlayerFM().getSpeed()) * 0.1F) * 10;
      break;
    case 3:
      str = ".us";
      m = (int)(3.28084F * World.getPlayerFM().getAltitude() * 0.02F) * 50;
      n = (int)(2.236936F * Pitot.Indicator((float)World.getPlayerFM().Loc.z, World.getPlayerFM().getSpeed()) * 0.1F) * 10;
    }

    if (this.iDrawSpeed != this.lastDrawSpeed) {
      try {
        this.renderSpeedSubstrings[0][0] = this.resLog.getString("HDG");
        this.renderSpeedSubstrings[1][0] = this.resLog.getString("ALT");
        this.renderSpeedSubstrings[2][0] = this.resLog.getString("SPD");

        this.renderSpeedSubstrings[3][0] = this.resLog.getString("G");

        this.renderSpeedSubstrings[0][1] = this.resLog.getString("HDG" + str);
        this.renderSpeedSubstrings[1][1] = this.resLog.getString("ALT" + str);
        this.renderSpeedSubstrings[2][1] = this.resLog.getString("SPD" + str);
        this.renderSpeedSubstrings[3][1] = this.resLog.getString("Ga");
      } catch (Exception localException) {
        this.renderSpeedSubstrings[0][0] = "HDG";
        this.renderSpeedSubstrings[1][0] = "ALT";
        this.renderSpeedSubstrings[2][0] = "SPD";

        this.renderSpeedSubstrings[3][0] = "G";

        this.renderSpeedSubstrings[0][1] = "";
        this.renderSpeedSubstrings[1][1] = "";
        this.renderSpeedSubstrings[2][1] = "";
        this.renderSpeedSubstrings[3][1] = "";
      }
    }

    localTTFont.output(j, 5.0F, 5.0F, 0.0F, this.renderSpeedSubstrings[0][0] + " " + k + " " + this.renderSpeedSubstrings[0][1]);
    if (World.cur().diffCur.NoSpeedBar) {
      return;
    }
    localTTFont.output(j, 5.0F, 5 + i, 0.0F, this.renderSpeedSubstrings[1][0] + " " + m + " " + this.renderSpeedSubstrings[1][1]);
    localTTFont.output(j, 5.0F, 5 + i + i, 0.0F, this.renderSpeedSubstrings[2][0] + " " + n + " " + this.renderSpeedSubstrings[2][1]);

    if (i1 != 0)
      localTTFont.output(j, 5.0F, 5 + i + i + i, 0.0F, this.renderSpeedSubstrings[3][0]);
  }

  public void clearSpeed() {
    this.iDrawSpeed = 1; } 
  private void initSpeed() { this.iDrawSpeed = 1;
  }

  public static void order(Order[] paramArrayOfOrder)
  {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._order(paramArrayOfOrder);
  }

  public void _order(Order[] paramArrayOfOrder) {
    if (paramArrayOfOrder == null) {
      this.order = null;
      return;
    }
    this.order = new Order[paramArrayOfOrder.length];
    this.orderStr = new String[paramArrayOfOrder.length];
    int i = World.getPlayerArmy();
    for (int j = 0; j < this.order.length; j++) {
      this.order[j] = paramArrayOfOrder[j];
      if ((paramArrayOfOrder[j] != null) && (this.order[j].name(i) != null)) {
        String str1 = this.order[j].name(i);
        String str2 = null;
        String str3 = World.getPlayerLastCountry();
        if (str3 != null)
          try {
            str2 = this.resOrder.getString(str1 + "_" + str3);
          } catch (Exception localException1) {
          }
        if (str2 == null) {
          try {
            str2 = this.resOrder.getString(str1);
          } catch (Exception localException2) {
            str2 = str1;
          }
        }
        this.orderStr[j] = str2;
      }
    }
  }

  private void renderOrder()
  {
    if (this.order == null) return;
    if (!this.bDrawAllMessages) return;
    TTFont localTTFont = TTFont.font[1];
    int i = (int)(this.viewDX * 0.05D);
    int j = localTTFont.height();
    int k = this.viewDY - 2 * localTTFont.height();
    String str = null;
    int m = k;
    int n = 0;
    int i1 = 0;

    int i2 = 0;
    boolean bool = false;

    for (int i3 = 0; i3 < this.order.length; i3++) {
      if (this.orderStr[i3] != null)
      {
        if ((this.order[i3] instanceof OrderAnyone_Help_Me)) {
          i2 = 1;
        }
        if (Main3D.cur3D().ordersTree.frequency() == null) {
          bool = true;
        }
        if (str != null)
          drawOrder(str, i, m, i1 == 0 ? -1 : i1, n, bool);
        i1 = i3;
        str = this.orderStr[i3];
        m = k;
        n = this.order[i3].attrib();

        if ((((this.order[i3] instanceof OrderVector_To_Home_Base)) || ((this.order[i3] instanceof OrderVector_To_Target))) && (Main.cur().mission.zutiRadar_DisableVectoring))
        {
          bool = true;
        }
        else bool = false;
      }

      k -= j;
    }

    if (Main3D.cur3D().ordersTree.frequency() == null) {
      bool = true;
    }
    if (str != null) {
      drawOrder(str, i, m, 0, n, bool);
    }
    if (i2 != 0)
    {
      String[] arrayOfString = Main3D.cur3D().ordersTree.getShipIDs();
      for (int i4 = 0; i4 < arrayOfString.length; i4++)
      {
        if ((i4 == 0) && (arrayOfString[i4] != null))
        {
          k -= j;
          k -= j;
          drawShipIDs(this.resOrder.getString("ShipIDs"), i, k);
          k -= j;
        }
        if (arrayOfString[i4] == null)
          continue;
        drawShipIDs(arrayOfString[i4], i, k);
        k -= j;
      }
    }
  }

  private void drawShipIDs(String paramString, int paramInt1, int paramInt2)
  {
    int i = -16776961;
    TTFont localTTFont = TTFont.font[1];
    localTTFont.output(i, paramInt1, paramInt2, 0.0F, paramString);
  }

  private void drawOrder(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
    int i = -16776961;
    if ((paramInt4 & 0x1) != 0) i = -16777089;
    else if ((paramInt4 & 0x2) != 0) i = -16744449;
    TTFont localTTFont = TTFont.font[1];

    if (paramBoolean) {
      i = 2139062143;
    }
    if (paramInt3 >= 0) localTTFont.output(i, paramInt1, paramInt2, 0.0F, "" + paramInt3 + ". " + paramString); else
      localTTFont.output(i, paramInt1, paramInt2, 0.0F, paramString);
  }

  public void clearOrder() {
    this.order = null;
  }

  private void initOrder() {
    clearOrder();
    this.resOrder = ResourceBundle.getBundle("i18n/hud_order", RTSConf.cur.locale, LDRres.loader());
  }

  public static void message(int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._message(paramArrayOfInt, paramInt1, paramInt2, paramBoolean);
  }

  public void _message(int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (!this.bDrawVoiceMessages) return;
    if (this.bNoSubTitles) return;
    if (paramInt1 < 1) return;
    if (paramInt1 > 9) return;
    if (paramInt2 < 1) return;
    if (paramInt2 > 2) return;
    if (paramArrayOfInt == null) {
      return;
    }
    TTFont localTTFont = TTFont.font[1];
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] != 0); i++) {
      Object localObject = com.maddox.il2.objects.sounds.Voice.vbStr[paramArrayOfInt[i]];
      try {
        String str1 = this.resMsg.getString((String)localObject);
        if (str1 != null)
          localObject = str1;
      } catch (Exception localException) {
      }
      StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject);
      while (localStringTokenizer.hasMoreTokens()) {
        String str2 = localStringTokenizer.nextToken();

        int j = (int)localTTFont.width(str2);
        if (this.msgLines.size() == 0) {
          this.msgLines.add(new MsgLine(str2, j, paramInt1, paramInt2, Time.current()));
        } else {
          MsgLine localMsgLine2 = (MsgLine)this.msgLines.get(this.msgLines.size() - 1);
          if ((localMsgLine2.iActor == paramInt1) && (localMsgLine2.army == paramInt2) && (!paramBoolean)) {
            int k = localMsgLine2.len + this.msgSpaceLen + j;
            if (k < this.msgDX) {
              localMsgLine2.msg = (localMsgLine2.msg + " " + str2);
              localMsgLine2.len = k;
            } else {
              this.msgLines.add(new MsgLine(str2, j, paramInt1, paramInt2, 0L));
            }
          } else {
            this.msgLines.add(new MsgLine(str2, j, paramInt1, paramInt2, 0L));
          }
        }
        paramBoolean = false;
      }
    }
    while (this.msgLines.size() > this.msgSIZE) {
      this.msgLines.remove(0);
      MsgLine localMsgLine1 = (MsgLine)this.msgLines.get(0);
      localMsgLine1.time0 = Time.current();
    }
  }

  private int msgColor(int paramInt1, int paramInt2)
  {
    return this.msgColor[(paramInt2 - 1)][(paramInt1 - 1)];
  }

  private void renderMsg() {
    if (!this.bDrawVoiceMessages) return;
    if (!this.bDrawAllMessages) return;
    int i = this.msgLines.size();
    if (i == 0) return;
    MsgLine localMsgLine = (MsgLine)this.msgLines.get(0);
    long l = localMsgLine.time0 + localMsgLine.msg.length() * 250;
    if (l < Time.current()) {
      this.msgLines.remove(0);
      i--;
      if (i == 0) return;
      localMsgLine = (MsgLine)this.msgLines.get(0);
      localMsgLine.time0 = Time.current();
    }

    TTFont localTTFont = TTFont.font[1];
    int j = this.msgX0;
    int k = this.msgY0 + this.msgDY;
    for (int n = 0; n < i; n++) {
      localMsgLine = (MsgLine)this.msgLines.get(n);
      localTTFont.output(msgColor(localMsgLine.iActor, localMsgLine.army), j, k, 0.0F, localMsgLine.msg);
      int m;
      k -= localTTFont.height();
    }
  }

  public void clearMsg() {
    this.msgLines.clear();
  }

  public void resetMsgSizes() {
    clearMsg();
    TTFont localTTFont = TTFont.font[1];
    this.msgX0 = (int)(this.viewDX * 0.3D);
    this.msgDX = (int)(this.viewDX * 0.6D);

    this.msgDY = (localTTFont.height() * this.subTitlesLines);
    if (this.msgDY > (int)(this.viewDY * 0.9D))
      this.msgDY = (int)(this.viewDY * 0.9D);
    int i = this.msgDY / localTTFont.height();
    if (i == 0) i = 1;
    this.msgDY = (localTTFont.height() * i);
    this.msgSIZE = i;
    this.msgY0 = ((int)(this.viewDY * 0.95D) - this.msgDY);

    this.msgSpaceLen = Math.round(localTTFont.width(" "));
  }

  private void initMsg() {
    resetMsgSizes();
    this.resMsg = ResourceBundle.getBundle("i18n/hud_msg", RTSConf.cur.locale, LDRres.loader());
  }

  public static void training(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._training(paramString);
  }

  public void _training(String paramString) {
    this.trainingLines.clear();
    if (paramString == null) {
      return;
    }
    TTFont localTTFont = TTFont.font[2];
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    while (localStringTokenizer.hasMoreTokens()) {
      String str1 = localStringTokenizer.nextToken();
      int i = (int)localTTFont.width(str1);
      if (this.trainingLines.size() == 0) {
        this.trainingLines.add(str1);
      } else {
        String str2 = (String)this.trainingLines.get(this.trainingLines.size() - 1);
        int j = (int)localTTFont.width(str2);
        int k = j + this.trainingSpaceLen + i;
        if (k < this.trainingDX) {
          this.trainingLines.set(this.trainingLines.size() - 1, str2 + " " + str1);
        } else {
          if (this.trainingLines.size() >= this.trainingSIZE)
            break;
          this.trainingLines.add(str1);
        }
      }
    }
  }

  private void renderTraining()
  {
    int i = this.trainingLines.size();
    if (i == 0) return;
    TTFont localTTFont = TTFont.font[2];
    int j = this.trainingX0;
    int k = this.trainingY0 + this.trainingDY;
    for (int n = 0; n < i; n++) {
      String str = (String)this.trainingLines.get(n);
      localTTFont.output(-16776961, j, k, 0.0F, str);
      int m;
      k -= localTTFont.height();
    }
  }

  public void clearTraining() {
    this.trainingLines.clear();
  }
  public void resetTrainingSizes() {
    clearTraining();
    TTFont localTTFont = TTFont.font[2];
    this.trainingX0 = (int)(this.viewDX * 0.3D);
    this.trainingDX = (int)(this.viewDX * 0.5D);
    this.trainingY0 = (int)(this.viewDY * 0.5D);
    this.trainingDY = (int)(this.viewDY * 0.45D);
    int i = this.trainingDY / localTTFont.height();
    if (i == 0) i = 1;
    this.trainingDY = (localTTFont.height() * i);
    this.trainingSIZE = i;
    this.trainingSpaceLen = Math.round(localTTFont.width(" "));
  }
  private void initTraining() {
    resetTrainingSizes();
  }

  public static void intro(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._intro(paramString);
  }

  public void _intro(String paramString) {
    if (paramString == null) {
      this.logIntro = null;
      return;
    }
    this.logIntro = paramString;
  }

  public static void introESC(String paramString) {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._introESC(paramString);
  }

  public void _introESC(String paramString) {
    if (paramString == null) {
      this.logIntroESC = null;
      return;
    }
    this.logIntroESC = paramString;
  }

  public static int makeIdLog()
  {
    return idLog++;
  }

  public static void log(String paramString, Object[] paramArrayOfObject)
  {
    if (!Config.isUSE_RENDER()) return;
    log(0, paramString, paramArrayOfObject);
  }

  public static void log(int paramInt, String paramString, Object[] paramArrayOfObject)
  {
    if (!Config.isUSE_RENDER()) return;
    if (Main3D.cur3D().gameTrackPlay() != null)
      return;
    if ((Main3D.cur3D().gameTrackRecord() != null) && 
      (paramArrayOfObject != null) && (paramArrayOfObject.length == 1) && ((paramArrayOfObject[0] instanceof Integer)))
    {
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(0);
        localNetMsgGuaranted.writeInt(paramInt);
        localNetMsgGuaranted.write255(paramString);
        localNetMsgGuaranted.writeInt(((Integer)paramArrayOfObject[0]).intValue());
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      } catch (Exception localException) {
      }
    }
    Main3D.cur3D().hud._log(paramInt, paramString, paramArrayOfObject);
  }

  public void _log(int paramInt, String paramString, Object[] paramArrayOfObject) {
    if (this.bNoHudLog) return;
    int i = __log(paramInt, paramString);
    String str = null;
    try {
      str = this.resLog.getString(paramString);
    } catch (Exception localException) {
      str = paramString;
    }
    this.logBufStr[i] = MessageFormat.format(str, paramArrayOfObject);
  }

  public static void log(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    log(0, paramString);
  }

  public static void log(int paramInt, String paramString)
  {
    log(paramInt, paramString, true);
  }

  public static void log(int paramInt, String paramString, boolean paramBoolean) {
    if (!Config.isUSE_RENDER()) return;
    if (paramBoolean) {
      if (Main3D.cur3D().gameTrackPlay() != null)
        return;
      if (Main3D.cur3D().gameTrackRecord() != null)
        try {
          NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
          localNetMsgGuaranted.writeByte(1);
          localNetMsgGuaranted.writeInt(paramInt);
          localNetMsgGuaranted.write255(paramString);
          Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
        } catch (Exception localException) {
        }
    }
    Main3D.cur3D().hud._log(paramInt, paramString);
  }

  public void _log(int paramInt, String paramString) {
    if (this.bNoHudLog) return;
    int i = __log(paramInt, paramString);
    try {
      this.logBufStr[i] = this.resLog.getString(paramString);
    } catch (Exception localException) {
      this.logBufStr[i] = paramString;
    }
  }

  public static void logRightBottom(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    if (Main3D.cur3D().gameTrackPlay() != null)
      return;
    if (Main3D.cur3D().gameTrackRecord() != null)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(2);
        localNetMsgGuaranted.write255(paramString == null ? "" : paramString);
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      } catch (Exception localException) {
      }
    Main3D.cur3D().hud._logRightBottom(paramString);
  }

  public void _logRightBottom(String paramString) {
    if (this.bNoHudLog) return;
    if (paramString == null) {
      this.logRightBottom = null;
      return;
    }
    try {
      this.logRightBottom = this.resLog.getString(paramString);
    } catch (Exception localException) {
      this.logRightBottom = paramString;
    }
    this.logRightBottomTime = Time.current();
  }

  public static void logCenter(String paramString)
  {
    if (!Config.isUSE_RENDER()) return;
    if (Main3D.cur3D().gameTrackPlay() != null)
      return;
    if (Main3D.cur3D().gameTrackRecord() != null)
      try {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(3);
        localNetMsgGuaranted.write255(paramString == null ? "" : paramString);
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      } catch (Exception localException) {
      }
    Main3D.cur3D().hud._logCenter(paramString);
  }

  public void _logCenter(String paramString) {
    if (paramString == null) {
      this.logCenter = null;
      return;
    }
    try {
      this.logCenter = this.resLog.getString(paramString);
    } catch (Exception localException) {
      this.logCenter = paramString;
    }
    this.logCenterTime = Time.current();
  }

  public static void logCoopTimeStart(long paramLong) {
    if (!Config.isUSE_RENDER()) return;
    Main3D.cur3D().hud._logCoopTimeStart(paramLong);
  }
  public void _logCoopTimeStart(long paramLong) {
    this.bCoopTimeStart = true;
    this.coopTimeStart = paramLong;
  }

  public boolean netInputLog(int paramInt, NetMsgInput paramNetMsgInput)
    throws IOException
  {
    int i;
    String str2;
    String str1;
    switch (paramInt) {
    case 0:
      i = paramNetMsgInput.readInt();
      str2 = paramNetMsgInput.read255();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(paramNetMsgInput.readInt());
      _log(i, str2, arrayOfObject);

      break;
    case 1:
      i = paramNetMsgInput.readInt();
      str2 = paramNetMsgInput.read255();
      _log(i, str2);

      break;
    case 2:
      str1 = paramNetMsgInput.read255();
      if ("".equals(str1)) str1 = null;
      _logRightBottom(str1);

      break;
    case 3:
      str1 = paramNetMsgInput.read255();
      if ("".equals(str1)) str1 = null;
      _logCenter(str1);

      break;
    }

    return true;
  }

  public void clearLog()
  {
    this.logRightBottom = null;
    this.logPtr = 0;
    this.logLen = 0;
    this.logCenter = null;
    this.logIntro = null;
    this.logIntroESC = null;
    this.bCoopTimeStart = false;
  }

  private void initLog() {
    clearLog();
    this.resLog = ResourceBundle.getBundle("i18n/hud_log", RTSConf.cur.locale, LDRres.loader());
    this.fntCenter = TTFont.font[2];
  }

  private void renderLog() {
    long l = Time.current();
    float f1;
    int m;
    if (this.bCoopTimeStart) {
      int i = (int)(this.coopTimeStart - Time.currentReal());
      if (i < 0) {
        this.bCoopTimeStart = false;
      } else if (this.bDrawAllMessages) {
        TTFont localTTFont2 = this.fntCenter;
        String str = "" + (i + 500) / 1000;
        float f2 = localTTFont2.width(str);
        localTTFont2.output(-16711681, (this.viewDX - f2) / 2.0F, this.viewDY * 0.75F, 0.0F, str);
      }
    } else if (this.logIntro != null) {
      localTTFont1 = this.fntCenter;
      f1 = localTTFont1.width(this.logIntro);
      k = -16777216;
      localTTFont1.output(k, (this.viewDX - f1) / 2.0F, this.viewDY * 0.75F, 0.0F, this.logIntro);
    } else if (this.logCenter != null) {
      if (l > this.logCenterTime + 5000L) {
        this.logCenter = null;
      } else if (this.bDrawAllMessages) {
        localTTFont1 = this.fntCenter;
        f1 = localTTFont1.width(this.logCenter);
        k = -16776961;
        m = 255 - (int)((l - this.logCenterTime) / 5000.0D * 255.0D);
        k |= m << 8;
        localTTFont1.output(k, (this.viewDX - f1) / 2.0F, this.viewDY * 0.75F, 0.0F, this.logCenter);
      }
    }
    if (this.logIntroESC != null) {
      localTTFont1 = TTFont.font[0];
      f1 = localTTFont1.width(this.logIntroESC);
      k = -1;
      localTTFont1.output(k, (this.viewDX - f1) / 2.0F, this.viewDY * 0.05F, 0.0F, this.logIntroESC);
    }
    if (!Main3D.cur3D().aircraftHotKeys.isAfterburner())
      this.logRightBottom = null;
    int i3;
    if ((this.logRightBottom != null) && (this.bDrawAllMessages)) {
      localTTFont1 = TTFont.font[1];
      j = (int)(this.viewDX * 0.95D);
      k = (int)localTTFont1.width(this.logRightBottom);
      m = (int)(this.viewDY * 0.45D - 3 * localTTFont1.height());
      i2 = -16776961;
      i3 = (int)(510.0F * (float)((Time.current() - this.logRightBottomTime) % 5000L) / 5000.0F);
      i3 -= 255;
      if (i3 < 0) i3 = -i3;
      localTTFont1.output(i2 | i3 << 8, j - k, m, 0.0F, this.logRightBottom);
    }

    if (this.logLen == 0) return;
    while ((this.logLen > 0) && 
      (l >= this.logTime[this.logPtr] + 10000L)) {
      this.logPtr = ((this.logPtr + 1) % 3);
      this.logLen -= 1;
    }

    if (this.logLen == 0) return;

    TTFont localTTFont1 = TTFont.font[1];
    int j = (int)(this.viewDX * 0.95D);
    int k = localTTFont1.height();
    int n = (int)(this.viewDY * 0.45D) - (3 - this.logLen) * k;

    for (int i2 = 0; i2 < this.logLen; i2++) {
      i3 = (this.logPtr + i2) % 3;
      int i4 = -65536;
      if (l < this.logTime[i3] + 5000L) {
        int i5 = (int)((this.logTime[i3] + 5000L - l) / 5000.0D * 255.0D);
        i4 |= i5 | i5 << 8;
      }

      float f3 = localTTFont1.width(this.logBufStr[i3]);
      if (this.bDrawAllMessages)
        localTTFont1.output(i4, j - f3, n, 0.0F, this.logBufStr[i3]);
      int i1;
      n -= k;
    }
  }

  private int __log(int paramInt, String paramString) {
    if ((this.logLen > 0) && (paramInt != 0)) {
      i = (this.logPtr + this.logLen - 1) % 3;
      if (this.logBufId[i] == paramInt) {
        this.logTime[i] = Time.current();
        this.logBuf[i] = paramString;
        return i;
      }
    }
    if (this.logLen >= 3) {
      this.logPtr = ((this.logPtr + 1) % 3);
      this.logLen = 2;
    }
    int i = (this.logPtr + this.logLen) % 3;
    this.logBuf[i] = paramString;
    this.logBufId[i] = paramInt;
    this.logTime[i] = Time.current();
    this.logLen += 1;
    return i;
  }

  private void syncStatUser(int paramInt, NetUser paramNetUser)
  {
    if (paramInt == this.statUsers.size())
      this.statUsers.add(new StatUser());
    StatUser localStatUser = (StatUser)this.statUsers.get(paramInt);
    localStatUser.user = paramNetUser;
    if ((localStatUser.iNum != paramInt + 1) || (localStatUser.sNum == null)) {
      localStatUser.iNum = (paramInt + 1);
      localStatUser.sNum = (localStatUser.iNum + ".");
    }
    if ((localStatUser.iPing != paramNetUser.ping) || (localStatUser.sPing == null)) {
      localStatUser.iPing = paramNetUser.ping;
      localStatUser.sPing = ("(" + localStatUser.iPing + ")");
    }
    int i = (int)paramNetUser.stat().score;
    if ((localStatUser.iScore != i) || (localStatUser.sScore == null)) {
      localStatUser.iScore = i;
      localStatUser.sScore = ("" + localStatUser.iScore);
    }
    if ((localStatUser.iArmy != paramNetUser.getArmy()) || (localStatUser.sArmy == null)) {
      localStatUser.iArmy = paramNetUser.getArmy();
      localStatUser.sArmy = ("(" + localStatUser.iArmy + ")" + I18N.army(Army.name(localStatUser.iArmy)));
    }
    if ((!Actor.isAlive(localStatUser.aAircraft)) || (localStatUser.aAircraft.netUser() != paramNetUser) || (localStatUser.sAircraft == null))
    {
      Aircraft localAircraft = paramNetUser.findAircraft();
      localStatUser.aAircraft = localAircraft;
      if (localAircraft == null) {
        localStatUser.sAircraft = "";
        localStatUser.sAircraftType = "";
      } else {
        localStatUser.sAircraft = localAircraft.typedName();
        localStatUser.sAircraftType = I18N.plane(Property.stringValue(localAircraft.getClass(), "keyName"));
      }
    }
  }

  private void syncNetStat() {
    syncStatUser(0, (NetUser)NetEnv.host());
    for (int i = 0; i < NetEnv.hosts().size(); i++)
      syncStatUser(i + 1, (NetUser)NetEnv.hosts().get(i));
    while (this.statUsers.size() > NetEnv.hosts().size() + 1)
      this.statUsers.remove(this.statUsers.size() - 1); 
  }

  private int x1024(float paramFloat) {
    return (int)(this.viewDX / 1024.0F * paramFloat); } 
  private int y1024(float paramFloat) { return (int)(this.viewDY / 768.0F * paramFloat); }

  public void startNetStat() {
    if (this.bDrawNetStat) return;
    if (!Mission.isPlaying()) return;
    if (Mission.isSingle()) return;
    syncNetStat();
    TTFont localTTFont = TTFont.font[1];
    int i = localTTFont.height() - localTTFont.descender();
    int j = y1024(740.0F);
    int k = 2 * i;
    this.pageSizeNetStat = ((j - k) / i);
    this.bDrawNetStat = true;
  }
  public void stopNetStat() {
    if (!this.bDrawNetStat) return;
    this.statUsers.clear();
    this.bDrawNetStat = false;
    this.pageNetStat = 0;
  }
  public boolean isDrawNetStat() { return this.bDrawNetStat; }

  public void pageNetStat() {
    if (!this.bDrawNetStat) return;
    this.pageNetStat += 1;
    if (this.pageSizeNetStat * this.pageNetStat > this.statUsers.size())
      this.pageNetStat = 0;
  }

  public void renderNetStat() {
    if (!this.bDrawNetStat) return;
    if (!Mission.isPlaying()) return;
    if (Mission.isSingle()) return;

    TTFont localTTFont1 = TTFont.font[1];
    TTFont localTTFont2 = TTFont.font[3];

    if (Main.cur().netServerParams.netStat_DisableStatistics)
    {
      return;
    }

    int i = localTTFont1.height() - localTTFont1.descender();
    int j = y1024(740.0F);
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    for (int i4 = this.pageSizeNetStat * this.pageNetStat; (i4 < this.pageSizeNetStat * (this.pageNetStat + 1)) && (i4 < this.statUsers.size()); i4++) {
      StatUser localStatUser1 = (StatUser)this.statUsers.get(i4);
      i6 = 0;
      if (Main.cur().netServerParams.netStat_ShowPilotNumber)
      {
        i6 = (int)localTTFont1.width(localStatUser1.sNum);
        if (k < i6) k = i6;
      }
      if (Main.cur().netServerParams.netStat_ShowPilotPing)
      {
        i6 = (int)localTTFont2.width(localStatUser1.sPing);
        if (m < i6) m = i6;
      }
      if (Main.cur().netServerParams.netStat_ShowPilotName)
      {
        i6 = (int)localTTFont1.width(localStatUser1.user.uniqueName());
        if (n < i6) n = i6;
      }
      if (Main.cur().netServerParams.netStat_ShowPilotScore)
      {
        i6 = (int)localTTFont1.width(localStatUser1.sScore);
        if (i1 < i6) i1 = i6;
      }
      if (Main.cur().netServerParams.netStat_ShowPilotArmy)
      {
        i6 = (int)localTTFont1.width(localStatUser1.sArmy);
        if (i2 < i6) i2 = i6;
      }
      if (!Main.cur().netServerParams.netStat_ShowPilotACDesignation)
        continue;
      i6 = (int)localTTFont1.width(localStatUser1.sAircraft);
      if (i3 >= i6) continue; i3 = i6;
    }

    i4 = x1024(40.0F) + k;
    int i5 = i4 + m + x1024(16.0F);
    int i6 = i5 + n + x1024(16.0F);
    int i7 = i6 + i1 + x1024(16.0F);
    if (Mission.isCoop()) i7 = i6;
    int i8 = i7 + i2 + x1024(16.0F);
    int i9 = i8 + i3 + x1024(16.0F);
    int i10 = j;
    for (int i11 = this.pageSizeNetStat * this.pageNetStat; (i11 < this.pageSizeNetStat * (this.pageNetStat + 1)) && (i11 < this.statUsers.size()); i11++) {
      StatUser localStatUser2 = (StatUser)this.statUsers.get(i11);
      i10 -= i;
      int i12 = Army.color(localStatUser2.iArmy);

      if (!Main.cur().netServerParams.netStat_ShowPilotArmy) {
        i12 = -1;
      }
      if (Main.cur().netServerParams.netStat_ShowPilotNumber) {
        localTTFont1.output(i12, i4 - localTTFont1.width(localStatUser2.sNum), i10, 0.0F, localStatUser2.sNum);
      }
      if (Main.cur().netServerParams.netStat_ShowPilotPing) {
        localTTFont2.output(-1, i5 - localTTFont2.width(localStatUser2.sPing) - x1024(4.0F), i10, 0.0F, localStatUser2.sPing);
      }
      if (Main.cur().netServerParams.netStat_ShowPilotName) {
        localTTFont1.output(i12, i5, i10, 0.0F, localStatUser2.user.uniqueName());
      }
      if ((!Mission.isCoop()) && (Main.cur().netServerParams.netStat_ShowPilotScore)) {
        localTTFont1.output(i12, i6, i10, 0.0F, localStatUser2.sScore);
      }
      if (Main.cur().netServerParams.netStat_ShowPilotArmy) {
        localTTFont1.output(i12, i7, i10, 0.0F, localStatUser2.sArmy);
      }
      if (Main.cur().netServerParams.netStat_ShowPilotACDesignation) {
        localTTFont1.output(i12, i8, i10, 0.0F, localStatUser2.sAircraft);
      }
      if (Main.cur().netServerParams.netStat_ShowPilotACType)
        localTTFont1.output(i12, i9, i10, 0.0F, localStatUser2.sAircraftType);
    }
  }

  public static void addPointer(float paramFloat1, float paramFloat2, int paramInt, float paramFloat3, float paramFloat4)
  {
    Main3D.cur3D().hud._addPointer(paramFloat1, paramFloat2, paramInt, paramFloat3, paramFloat4);
  }

  private void _addPointer(float paramFloat1, float paramFloat2, int paramInt, float paramFloat3, float paramFloat4) {
    if (this.nPointers == this.pointers.size()) {
      this.pointers.add(new Ptr(paramFloat1, paramFloat2, paramInt, paramFloat3, paramFloat4));
    } else {
      Ptr localPtr = (Ptr)this.pointers.get(this.nPointers);
      localPtr.set(paramFloat1, paramFloat2, paramInt, paramFloat3, paramFloat4);
    }
    this.nPointers += 1;
  }

  private void renderPointers()
  {
    if (this.nPointers == 0) return;
    float f = this.viewDX / 1024.0F;
    int i = IconDraw.scrSizeX();
    int j = IconDraw.scrSizeY();
    for (int k = 0; k < this.nPointers; k++) {
      Ptr localPtr = (Ptr)this.pointers.get(k);
      int m = (int)(64.0F * f * localPtr.alpha);
      IconDraw.setScrSize(m, m);
      IconDraw.setColor(localPtr.color & 0xFFFFFF | (int)(localPtr.alpha * 255.0F) << 24);
      IconDraw.render(this.spritePointer, localPtr.x, localPtr.y, 90.0F - localPtr.angle);
    }
    IconDraw.setScrSize(i, j);
    this.nPointers = 0;
  }
  public void clearPointers() { this.nPointers = 0; } 
  private void initPointers() {
    this.spritePointer = Mat.New("gui/game/hud/pointer.mat");
  }

  private void preRenderDashBoard()
  {
    if (!Actor.isValid(World.getPlayerAircraft())) return;
    if (!Actor.isValid(this.main3d.cockpitCur)) return;
    if (this.main3d.isViewOutside()) {
      if (this.main3d.viewActor() != World.getPlayerAircraft())
        return;
      if (!this.main3d.cockpitCur.isNullShow())
        return;
    } else if (this.main3d.isViewInsideShow()) {
      return;
    }
    if (!this.bDrawDashBoard)
      return;
    this.spriteLeft.preRender();
    this.spriteRight.preRender();

    this.spriteG.preRender();

    this.meshNeedle1.preRender();
    this.meshNeedle2.preRender();
    this.meshNeedle3.preRender();
    this.meshNeedle4.preRender();
    this.meshNeedle5.preRender();
    this.meshNeedle6.preRender();
    this.meshNeedleMask.preRender();
  }
  private void renderDashBoard() {
    if (!Actor.isValid(World.getPlayerAircraft())) return;
    if (!Actor.isValid(this.main3d.cockpitCur)) return;
    if (this.main3d.isViewOutside()) {
      if (this.main3d.viewActor() != World.getPlayerAircraft())
        return;
      if (!this.main3d.cockpitCur.isNullShow())
        return;
    } else if (this.main3d.isViewInsideShow()) {
      return;
    }
    if (!this.bDrawDashBoard)
      return;
    float f1 = this.viewDX;
    float f2 = this.viewDY;
    float f3 = f1 / 1024.0F;
    float f4 = f2 / 768.0F;
    Render.drawTile(0.0F, 0.0F, 256.0F * f3, 256.0F * f4, 0.0F, this.spriteLeft, -1, 0.0F, 1.0F, 1.0F, -1.0F);

    Render.drawTile(768.0F * f3, 0.0F, 256.0F * f3, 256.0F * f4, 0.0F, this.spriteRight, -1, 0.0F, 1.0F, 1.0F, -1.0F);

    Render.drawTile(200.0F * f3, 168.0F * f4, 64.0F * f3, 64.0F * f4, 0.0F, this.spriteG, -1, 0.0F, 1.0F, 1.0F, -1.0F);

    Point3d localPoint3d = World.getPlayerAircraft().pos.getAbsPoint();
    Orient localOrient1 = World.getPlayerAircraft().pos.getAbsOrient();

    float f5 = (float)(localPoint3d.z - World.land().HQ(localPoint3d.x, localPoint3d.y));
    this._p.x = (172.0F * f3); this._p.y = (84.0F * f4);
    this._o.set(cvt(f5, 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.meshNeedle2.setPos(this._p, this._o);
    this.meshNeedle2.render();
    this._o.set(cvt(f5, 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.meshNeedle1.setPos(this._p, this._o);
    this.meshNeedle1.render();
    String str1 = "" + (int)(f5 + 0.5D);
    float f7 = this.fntLcd.width(str1);
    this.fntLcd.output(-1, 208.0F * f3 - f7, 70.0F * f4, 0.0F, str1);

    if (f5 > 90.0F) this.meshNeedle5.setScale(90.0F * f3); else {
      this.meshNeedle5.setScaleXYZ(90.0F * f3, 90.0F * f3, cvt(f5, 0.0F, 90.0F, 13.0F, 90.0F) * f3);
    }

    f5 = (float)World.getPlayerAircraft().getSpeed(null);
    f5 *= 3.6F;
    this._o.set(cvt(f5, 0.0F, 900.0F, 0.0F, 270.0F) + 180.0F, 0.0F, 0.0F);
    this._p.x = (83.0F * f3); this._p.y = (167.0F * f4);
    this.meshNeedle2.setPos(this._p, this._o);
    this.meshNeedle2.render();
    str1 = "" + (int)(f5 + 0.5D);
    f7 = this.fntLcd.width(str1);
    this.fntLcd.output(-1, 104.0F * f3 - f7, 135.0F * f4, 0.0F, str1);

    f5 = localOrient1.azimut() + 90.0F;
    while (f5 < 0.0F) f5 += 360.0F;
    f5 %= 360.0F;
    this._o.set(f5, 0.0F, 0.0F);
    this._p.x = (939.0F * f3); this._p.y = (167.0F * f4);
    this.meshNeedle3.setPos(this._p, this._o);
    this.meshNeedle3.render();
    str1 = "" + (int)(f5 + 0.5D);
    f7 = this.fntLcd.width(str1);
    this.fntLcd.output(-1, 960.0F * f3 - f7, 216.0F * f4, 0.0F, str1);

    Orient localOrient2 = this.main3d.camera3D.pos.getAbsOrient();
    this._p.x = (511.0F * f3); this._p.y = (96.0F * f4);

    if (localOrient2.tangage() < 0.0F) {
      this._o1.set(localOrient2);
      this._o1.increment(0.0F, 0.0F, 90.0F);
      this._o1.increment(0.0F, 90.0F, 0.0F);
      this._o.sub(this._oNull, this._o1);
      this.meshNeedle5.setPos(this._p, this._o);
      this.meshNeedle5.render();
    }
    this._o1.set(localOrient2);
    this._o1.increment(0.0F, 0.0F, 90.0F);
    this._o1.increment(0.0F, 90.0F, 0.0F);
    this._o.sub(localOrient1, this._o1);
    this.meshNeedle4.setPos(this._p, this._o);
    this.meshNeedle4.render();

    if (localOrient2.tangage() >= 0.0F) {
      this._o1.set(localOrient2);
      this._o1.increment(0.0F, 0.0F, 90.0F);
      this._o1.increment(0.0F, 90.0F, 0.0F);
      this._o.sub(this._oNull, this._o1);
      this.meshNeedle5.setPos(this._p, this._o);
      this.meshNeedle5.render();
    }

    this._p.x = (851.0F * f3); this._p.y = (84.0F * f4);
    this._o1.set(localOrient1);
    this._o1.set(0.0F, -this._o1.tangage(), this._o1.kren());
    this._o1.increment(0.0F, 0.0F, 90.0F);
    this._o1.increment(0.0F, 90.0F, 0.0F);
    this._o.sub(this._oNull, this._o1);
    this.meshNeedle6.setPos(this._p, this._o);
    this.meshNeedle6.render();
    this._o.set(0.0F, 0.0F, 0.0F);
    this.meshNeedleMask.setPos(this._p, this._o);
    this.meshNeedleMask.render();

    int i = (int)(World.getPlayerFM().getOverload() * 10.0F);
    float f6 = i / 10.0F;
    String str2 = "" + f6;
    float f8 = this.fntLcd.width(str2);
    if (World.getPlayerFM().getLoadDiff() < World.getPlayerFM().getLimitLoad() * 0.25F)
      this.fntLcd.output(-16776961, 249.0F * f3 - f8, 182.0F * f4, 0.0F, str2);
    else if (i < 0)
      this.fntLcd.output(-16777216, 249.0F * f3 - f8, 182.0F * f4, 0.0F, str2);
    else
      this.fntLcd.output(-1, 249.0F * f3 - f8, 182.0F * f4, 0.0F, str2);
  }

  private float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }

  private void initDashBoard()
  {
    this.spriteLeft = Mat.New("gui/game/hud/hudleft.mat");

    this.spriteRight = Mat.New("gui/game/hud/hudright.mat");
    this.meshNeedle1 = new Mesh("gui/game/hud/needle1/mono.sim");
    this.meshNeedle2 = new Mesh("gui/game/hud/needle2/mono.sim");
    this.meshNeedle3 = new Mesh("gui/game/hud/needle3/mono.sim");
    this.meshNeedle4 = new Mesh("gui/game/hud/needle4/mono.sim");
    this.meshNeedle5 = new Mesh("gui/game/hud/needle5/mono.sim");
    this.meshNeedle6 = new Mesh("gui/game/hud/needle6/mono.sim");
    this.meshNeedleMask = new Mesh("gui/game/hud/needlemask/mono.sim");

    this.spriteG = Mat.New("gui/game/hud/hudg.mat");

    this.fntLcd = TTFont.get("lcdnova");
    setScales();
  }

  private void setScales() {
    float f1 = this.viewDX;
    float f2 = f1 / 1024.0F;
    this.meshNeedle1.setScale(140.0F * f2);
    this.meshNeedle2.setScale(140.0F * f2);
    this.meshNeedle3.setScale(75.0F * f2);
    this.meshNeedle4.setScale(100.0F * f2);
    this.meshNeedle5.setScale(90.0F * f2);
    this.meshNeedle6.setScale(150.0F * f2);
    this.meshNeedleMask.setScale(150.0F * f2);
  }

  public void render() {
    renderSpeed();
    renderOrder();
    renderMsg();
    renderTraining();
    renderLog();
    renderDashBoard();
    renderPointers();
    renderNetStat();
  }

  public void preRender() {
    preRenderDashBoard();
  }

  public void resetGame() {
    setScales();
    clearSpeed();
    clearOrder();
    clearMsg();
    clearTraining();
    clearLog();
    clearPointers();
    stopNetStat();
  }

  public void contextResize(int paramInt1, int paramInt2) {
    this.viewDX = this.main3d.renderHUD.getViewPortWidth();
    this.viewDY = this.main3d.renderHUD.getViewPortHeight();
    setScales();
    resetMsgSizes();
    resetTrainingSizes();
  }

  public HUD() {
    this.main3d = Main3D.cur3D();
    this.viewDX = this.main3d.renderHUD.getViewPortWidth();
    this.viewDY = this.main3d.renderHUD.getViewPortHeight();
    initSpeed();
    initOrder();
    initMsg();
    initTraining();
    initLog();
    initDashBoard();
    initPointers();
    this.bNoSubTitles = Config.cur.ini.get("game", "NoSubTitles", this.bNoSubTitles);
    this.subTitlesLines = Config.cur.ini.get("game", "SubTitlesLines", this.subTitlesLines);
    if (this.subTitlesLines < 1)
      this.subTitlesLines = 1;
    this.bNoHudLog = Config.cur.ini.get("game", "NoHudLog", this.bNoHudLog);
  }

  static class Ptr
  {
    float x;
    float y;
    int color;
    float alpha;
    float angle;

    public void set(float paramFloat1, float paramFloat2, int paramInt, float paramFloat3, float paramFloat4)
    {
      this.x = paramFloat1;
      this.y = paramFloat2;
      this.color = paramInt;
      this.alpha = paramFloat3;
      this.angle = (float)(paramFloat4 * 180.0F / 3.141592653589793D);
    }
    public Ptr(float paramFloat1, float paramFloat2, int paramInt, float paramFloat3, float paramFloat4) {
      set(paramFloat1, paramFloat2, paramInt, paramFloat3, paramFloat4);
    }
  }

  static class StatUser
  {
    NetUser user;
    int iNum;
    String sNum;
    int iPing;
    String sPing;
    int iScore;
    String sScore;
    int iArmy;
    String sArmy;
    Aircraft aAircraft;
    String sAircraft;
    String sAircraftType;
  }

  static class MsgLine
  {
    String msg;
    int iActor;
    int army;
    long time0;
    int len;

    MsgLine(String paramString, int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
      this.msg = paramString;
      this.len = paramInt1;
      this.iActor = paramInt2;
      this.army = paramInt3;
      this.time0 = paramLong;
    }
  }
}