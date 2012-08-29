package com.maddox.util;

public class UnicodeTo8bit
{
  private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String save(String paramString, boolean paramBoolean)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i * 2);

    for (int j = 0; j < i; j++) {
      char c = paramString.charAt(j);
      switch (c) { case ' ':
        if ((j == 0) || (paramBoolean)) {
          localStringBuffer.append('\\');
          localStringBuffer.append("u0020");
        } else {
          localStringBuffer.append(' ');
        }
        break;
      case '\\':
        localStringBuffer.append('\\'); localStringBuffer.append('\\');
        break;
      case '\t':
        localStringBuffer.append('\\'); localStringBuffer.append('t');
        break;
      case '\n':
        localStringBuffer.append('\\'); localStringBuffer.append('n');
        break;
      case '\r':
        localStringBuffer.append('\\'); localStringBuffer.append('r');
        break;
      case '\f':
        localStringBuffer.append('\\'); localStringBuffer.append('f');
        break;
      default:
        if ((c < ' ') || (c > '~')) {
          localStringBuffer.append('\\');
          localStringBuffer.append('u');
          localStringBuffer.append(toHex(c >> '\f' & 0xF));
          localStringBuffer.append(toHex(c >> '\b' & 0xF));
          localStringBuffer.append(toHex(c >> '\004' & 0xF));
          localStringBuffer.append(toHex(c & 0xF));
        } else {
          localStringBuffer.append(c);
        }
      }
    }

    return localStringBuffer.toString();
  }

  public static String load(String paramString) {
    return load(paramString, true);
  }

  public static String load(String paramString, boolean paramBoolean) {
    int j = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(j);

    for (int k = 0; k < j; ) {
      int i = paramString.charAt(k++);
      if (i == 92) {
        i = paramString.charAt(k++);
        if (i == 117)
        {
          int m = 0;
          int n = 0;
          int i1 = k;
          for (int i2 = 0; i2 < 4; i2++) {
            if (n != 0) break;
            i = paramString.charAt(k++);
            switch (i) { case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
              m = (m << 4) + i - 48;
              break;
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
              m = (m << 4) + 10 + i - 97;
              break;
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
              m = (m << 4) + 10 + i - 65;
              break;
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            default:
              if (paramBoolean) {
                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
              }
              localStringBuffer.append('\\');
              localStringBuffer.append('u');
              k = i1;
              n = 1;
            }

          }

          if (n == 0)
            localStringBuffer.append((char)m);
        }
        else if (i == 116) { localStringBuffer.append('\t');
        } else if (i == 114) { localStringBuffer.append('\r');
        } else if (i == 110) { localStringBuffer.append('\n');
        } else if (i == 102) { localStringBuffer.append('\f');
        } else {
          localStringBuffer.append('\\');
          localStringBuffer.append(i);
        }
      } else {
        localStringBuffer.append(i);
      }
    }
    return localStringBuffer.toString();
  }
  private static char toHex(int paramInt) {
    return hexDigit[(paramInt & 0xF)];
  }
}