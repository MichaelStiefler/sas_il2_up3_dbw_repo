package com.maddox.il2.net;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.game.Main;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.util.HashMapExt;

public class NetUserRegiment extends Regiment
{
  protected NetFileRequest netFileRequest;
  protected String ownerFileNameBmp = "";
  protected String localFileNameBmp;
  protected String fileNameTga = "";

  public String name() {
    if (this.fileNameTga.length() > 0) {
      return "UserRegiment" + this.fileNameTga;
    }
    return "UserRegiment";
  }
  public String fileName() {
    return null; } 
  public boolean isUserDefined() { return true; }

  private void setBranch(String paramString) {
    this.branch = paramString.toLowerCase().intern();
    if (branchMap.containsKey(this.branch))
      this.country = ((String)branchMap.get(this.branch));
    else
      this.country = this.branch;
  }

  public String fileNameTga()
  {
    if (this.fileNameTga.length() == 0)
      return "";
    return "../Cache/" + this.fileNameTga;
  }
  public String ownerFileNameBmp() { return this.ownerFileNameBmp; } 
  public boolean isEmpty() {
    return "".equals(this.fileNameTga);
  }
  public void setId(char[] paramArrayOfChar) {
    this.id = paramArrayOfChar;
    this.sid = new String(this.id);
  }

  public void setGruppeNumber(int paramInt) {
    this.gruppeNumber = paramInt;
  }

  public boolean equals(String paramString1, String paramString2, char[] paramArrayOfChar, int paramInt)
  {
    if (!paramString1.equals(this.branch)) return false;
    if (!paramString2.equals(this.ownerFileNameBmp)) return false;
    if ((paramArrayOfChar[0] != this.id[0]) || (paramArrayOfChar[1] != this.id[1])) return false;
    return paramInt == this.gruppeNumber;
  }

  public void setLocalFileNameBmp(String paramString)
  {
    this.localFileNameBmp = paramString;

    if ((paramString == null) || ("".equals(paramString))) {
      this.fileNameTga = "";
      return;
    }
    paramString = paramString.toLowerCase();
    int i = paramString.lastIndexOf('/');
    int j = paramString.lastIndexOf('\\');
    if (i >= 0) {
      if ((j > 0) && (j < i))
        i = j;
    }
    else i = j;

    if (i >= 0)
      this.fileNameTga = paramString.substring(i + 1);
    else
      this.fileNameTga = paramString;
    i = this.fileNameTga.lastIndexOf(".bmp");
    if (i >= 0)
      this.fileNameTga = this.fileNameTga.substring(0, i);
    this.fileNameTga += ".tga";
    String str = null;
    NetFileServerReg localNetFileServerReg = Main.cur().netFileServerReg;
    if (this.ownerFileNameBmp.equalsIgnoreCase(this.localFileNameBmp))
      str = localNetFileServerReg.primaryPath() + "/" + this.localFileNameBmp;
    else
      str = localNetFileServerReg.alternativePath() + "/" + this.localFileNameBmp;
    if (!BmpUtils.bmp8PalToTGA4(str, "PaintSchemes/Cache/" + this.fileNameTga))
      this.fileNameTga = "";
  }

  public void set(String paramString1, String paramString2, char[] paramArrayOfChar, int paramInt)
  {
    if (paramString2 == null)
      paramString2 = "";
    if (equals(paramString1, paramString2, paramArrayOfChar, paramInt))
      return;
    setBranch(paramString1);
    this.ownerFileNameBmp = paramString2;
    this.localFileNameBmp = null;
    setId(paramArrayOfChar);
    setGruppeNumber(paramInt);
  }

  public void destroy() {
    if (this.netFileRequest != null) {
      this.netFileRequest.doCancel();
      this.netFileRequest = null;
    }
    super.destroy();
  }

  public NetUserRegiment()
  {
    this.flags |= 16384;
    set("ru", "", new char[] { '0', '0' }, 1);
  }
}