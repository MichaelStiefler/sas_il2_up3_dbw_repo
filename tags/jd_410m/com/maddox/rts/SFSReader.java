package com.maddox.rts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class SFSReader extends InputStreamReader
{
  public SFSReader(String paramString)
    throws FileNotFoundException
  {
    super(new SFSInputStream(paramString));
  }

  public SFSReader(File paramFile) throws FileNotFoundException {
    super(new SFSInputStream(paramFile));
  }

  public SFSReader(String paramString1, String paramString2) throws FileNotFoundException, UnsupportedEncodingException
  {
    super(new SFSInputStream(paramString1), paramString2);
  }

  public SFSReader(File paramFile, String paramString) throws FileNotFoundException, UnsupportedEncodingException
  {
    super(new SFSInputStream(paramFile), paramString);
  }

  public SFSReader(String paramString, int[] paramArrayOfInt) throws FileNotFoundException {
    super(new KryptoInputFilter(new SFSInputStream(paramString), paramArrayOfInt));
  }

  public SFSReader(File paramFile, int[] paramArrayOfInt) throws FileNotFoundException {
    super(new KryptoInputFilter(new SFSInputStream(paramFile), paramArrayOfInt));
  }

  public SFSReader(String paramString1, String paramString2, int[] paramArrayOfInt) throws FileNotFoundException, UnsupportedEncodingException
  {
    super(new KryptoInputFilter(new SFSInputStream(paramString1), paramArrayOfInt), paramString2);
  }

  public SFSReader(File paramFile, String paramString, int[] paramArrayOfInt) throws FileNotFoundException, UnsupportedEncodingException
  {
    super(new KryptoInputFilter(new SFSInputStream(paramFile), paramArrayOfInt), paramString);
  }
}