package com.maddox.il2.game.campaign;

import java.io.PrintStream;

public class CampaignDGen extends Campaign
{
  private String dgenFileName;
  private String prefix;

  public String dgenFileName()
  {
    return this.dgenFileName;
  }
  public String prefix() { return this.prefix; }

  public void doExternalCampaignGenerator(String paramString) {
    String str1 = "DGen.exe";
    try {
      String str2 = branch() + " " + " " + rank();

      Runtime localRuntime = Runtime.getRuntime();

      Process localProcess = localRuntime.exec(str1 + " " + str2);

      localProcess.waitFor();
    }
    catch (Throwable localThrowable) {
      System.out.println(localThrowable.getMessage());
      localThrowable.printStackTrace();
    }
  }

  public CampaignDGen(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3) {
    this.dgenFileName = paramString1;
    this._country = paramString2;
    this._difficulty = paramInt1;
    this._rank = paramInt2;
    this.prefix = paramString3;
  }
}