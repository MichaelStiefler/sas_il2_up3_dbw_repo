package com.maddox.il2.gui;

import com.maddox.gwindow.GWindowRoot;
import com.maddox.rts.InOutStreams;
import java.io.InputStream;
import java.io.OutputStream;

public class GUIRecordNetSaveKeys extends GUIRecordSave
{
  public static String trackFileName;
  private byte[] buf;

  public String getFileExtension()
  {
    return ".ntrk";
  }

  protected void doSave()
  {
  }

  private void streamCopy(InOutStreams paramInOutStreams1, InOutStreams paramInOutStreams2, String paramString)
    throws Exception
  {
    InputStream localInputStream = paramInOutStreams1.openStream(paramString);
    OutputStream localOutputStream = paramInOutStreams2.createStream(paramString);
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = localInputStream.available();
      if (i == 0) break;
      if (i > arrayOfByte.length)
        i = arrayOfByte.length;
      localInputStream.read(arrayOfByte, 0, i);
      localOutputStream.write(arrayOfByte, 0, i);
    }
    localInputStream.close();
    localOutputStream.close();
  }

  public GUIRecordNetSaveKeys(GWindowRoot paramGWindowRoot)
  {
    super(paramGWindowRoot, 60);
  }
}