/* Decompiler 486ms, total 685ms, lines 1383 */
import com.ea.sdk.SDKTextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Text {
   public static final char C_LINE = '\u0080';
   public static final char C_ELLIPSES = '\u0085';
   public static final char C_RED_ING = '¼';
   public static final char C_BLUE_ING = '½';
   public static final char C_GREEN_ING = '¾';
   public static final char C_CHECK = '\u0087';
   public static final char C_MINIDASH = '\u0088';
   public static final char C_CURSOR2 = '\u0084';
   public static final char C_CURSOR = '\u008a';
   public static final char C_POINTER = '\u0090';
   public static final char C_GREYLINE = '\u0089';
   public static final char C_HEART = '\u008d';
   public static final char C_SHIELD = '\u008b';
   public static final char HYPHEN = '-';
   public static final char NEWLINE = '|';
   public static final char HARD_SPACE = ' ';
   public static final int FONT_WIDTH = 9;
   public static final int FONT_HEIGHT = 12;
   public static final int FONT_SPACING = 7;
   public static final int FONT_SPACE = 7;
   public static final int FONT_MAX = 144;
   public static final int FONT_MINCHAR = 33;
   public static final int FONT_MAXCHAR = 177;
   public static final int TEXT_TYPE_SHIFT = 10;
   public static final int TEXT_ID_MASK = 1023;
   public static final short TEXT_INVALID = -1;
   public static final int FONT_ACCENT_START = 121;
   private static final int USED = 1;
   private static final int FREE = 0;
   private byte[] chars;
   private int length = 0;
   private static char[] wordBreaks = new char[]{'|', '\n', '-', ' '};
   private static final int MAXBUFFERS = 7;
   private static Text[] scratchBuffers = new Text[7];
   private static int bufferFlags = 0;
   private static final int MAX_STRING_ARGS = 50;
   private static Text dynamicArgs = new Text(1024);
   private static short[] argIndex = new short[50];
   public static int numTextArgs = 0;
   public static boolean selectLanguage = false;
   public static int defaultLanguage = -1;
   private static int[] textSizes = new int[17];
   private static int[] textCount = new int[17];
   private static byte[][] text = new byte[17][];
   private static short[][] textMap = new short[17][];
   private static int[] textIndex;
   private static int textLastType;
   private static int textCurChunk;
   private static int textCurOffset;
   private static InputStream textChunkStream;
   private static final int[] indices = new int[6];

   public static boolean startup() {
      scratchBuffers[0] = new Text(2048);
      scratchBuffers[1] = new Text(2048);
      scratchBuffers[2] = new Text(2048);
      scratchBuffers[3] = new Text(2048);
      scratchBuffers[4] = new Text(2048);
      scratchBuffers[5] = new Text(2048);
      scratchBuffers[6] = new Text(2048);

      try {
         int[] var0 = textCount;
         var0[0] = 57;
         var0[1] = 208;
         var0[2] = 201;
         var0[3] = 16;
         var0[4] = 8;
         var0[5] = 314;
         var0[6] = 121;
         var0[7] = 79;
         var0[8] = 65;
         var0[9] = 102;
         var0[10] = 2;
         var0[11] = 71;
         var0[12] = 70;
         var0[13] = 63;
         var0[14] = 62;
         var0[15] = 3;
         var0[16] = 3;
         beginTextLoading();
         int[] var1 = textSizes;
         int[] var2 = textIndex;

         for(int var3 = 0; var3 < 5; ++var3) {
            for(int var4 = 0; var4 < 17; ++var4) {
               var1[var4] = Math.max(var1[var4], var2[(var4 + var3 * 17) * 3 + 2]);
            }
         }

         finishTextLoading();
         allocateText(0);
         allocateText(1);
         allocateText(2);
         allocateText(4);
         allocateText(5);
         allocateText(16);
         String var6 = App.app.getAppProperty("Force-Locale");
         if (var6 != null && var6.length() != 0) {
            var6 = var6.toLowerCase();
         }

         if (null != var6 && !var6.startsWith("auto-")) {
            if (var6.equals("menu")) {
               defaultLanguage = -1;
            } else if (var6.equals("cz")) {
               defaultLanguage = 0;
            } else if (var6.equals("pl")) {
               defaultLanguage = 1;
            } else if (var6.equals("pt")) {
               defaultLanguage = 2;
            } else if (var6.equals("ru")) {
               defaultLanguage = 3;
            } else if (var6.equals("tr")) {
               defaultLanguage = 4;
            } else {
               defaultLanguage = identifyLocale(var6);
            }
         } else {
            var6 = System.getProperty("microedition.locale").toLowerCase();
            defaultLanguage = identifyLocale(var6);
            if (defaultLanguage == -1 && var6.startsWith("auto-")) {
               defaultLanguage = identifyLocale(var6.substring(4));
            }
         }

         selectLanguage = defaultLanguage == -1;
         if (selectLanguage) {
            defaultLanguage = 0;
         }

         App.checkPausedState();
         beginTextLoading();
         loadTextFromIndex(defaultLanguage, 0);
         loadTextFromIndex(defaultLanguage, 1);
         loadTextFromIndex(defaultLanguage, 2);
         loadTextFromIndex(defaultLanguage, 4);
         loadTextFromIndex(defaultLanguage, 5);
         loadTextFromIndex(defaultLanguage, 16);
         finishTextLoading();
         SDKTextUtils.setCurrentLanguage(defaultLanguage);
      } catch (IOException var5) {
         App.Error(var5, 666);
      }

      resetTextArgs();
      return true;
   }

   public Text(int var1) {
      this.chars = new byte[var1];
   }

   public int length() {
      return this.length;
   }

   public void setLength(int var1) {
      this.length = Math.max(var1, 0);
   }

   public Text delete(int var1) {
      return this.delete(var1, 1);
   }

   public Text delete(int var1, int var2) {
      System.arraycopy(this.chars, var1 + var2, this.chars, var1, this.length - (var1 + var2));
      this.length -= var2;
      return this;
   }

   public char charAt(int var1) {
      return (char)(this.chars[var1] & 255);
   }

   public void setCharAt(char var1, int var2) {
      this.chars[var2] = (byte)var1;
   }

   public Text append(char var1) {
      this.chars[this.length++] = (byte)var1;
      return this;
   }

   public Text append(byte var1) {
      this.chars[this.length++] = var1;
      return this;
   }

   public Text append(long var1) {
      this.insert(var1, this.length);
      return this;
   }

   public Text append(int var1) {
      this.insert(var1, this.length);
      return this;
   }

   public Text append(int var1, int var2) {
      this.insert(var1, var2, this.length);
      return this;
   }

   public void appendTextArg(int var1) {
      short var2 = 0;
      short var3 = argIndex[var1];
      if (var1 > 0) {
         var2 = argIndex[var1 - 1];
      }

      dynamicArgs.substring(this, var2, var3 - var2);
   }

   public Text append(Text var1) {
      return this.append((Text)var1, 0, var1.length);
   }

   public Text append(Text var1, int var2) {
      return this.append(var1, var2, var1.length - var2);
   }

   public Text append(Text var1, int var2, int var3) {
      if (var3 > 0) {
         System.arraycopy(var1.chars, var2, this.chars, this.length, var3);
         this.length += var3;
      }

      return this;
   }

   public Text append(String var1) {
      return this.append((String)var1, 0, var1.length());
   }

   public Text append(String var1, int var2) {
      return this.append(var1, var2, var1.length() - var2);
   }

   public Text append(String var1, int var2, int var3) {
      for(int var4 = var2; var4 < var2 + var3; ++var4) {
         this.chars[this.length++] = (byte)var1.charAt(var4);
      }

      return this;
   }

   public Text insert(char var1, int var2) {
      System.arraycopy(this.chars, var2, this.chars, var2 + 1, this.length - var2);
      this.chars[var2] = (byte)var1;
      ++this.length;
      return this;
   }

   public Text insert(byte var1, int var2) {
      System.arraycopy(this.chars, var2, this.chars, var2 + 1, this.length - var2);
      this.chars[var2] = var1;
      ++this.length;
      return this;
   }

   public Text insert(long var1, int var3) {
      if (var1 < 0L) {
         this.insert('-', var3);
         ++var3;
         var1 = -var1;
      }

      do {
         this.insert((char)((int)(48L + var1 % 10L)), var3);
         var1 /= 10L;
      } while(var1 != 0L);

      return this;
   }

   public Text insert(int var1, int var2) {
      if (var1 < 0) {
         this.insert('-', var2);
         ++var2;
         var1 = -var1;
      }

      do {
         this.insert((char)(48 + var1 % 10), var2);
         var1 /= 10;
      } while(var1 != 0);

      return this;
   }

   public Text insert(int var1, int var2, int var3) {
      if (var1 < 0) {
         this.insert('-', var3);
         ++var3;
         var1 = -var1;
      }

      int var4 = this.length;
      this.insert(var1 >> var2, var3);
      this.insert('.', var3 + (this.length - var4));
      this.insert(var1 & (1 << var2) - 1, var3 + (this.length - var4));
      return this;
   }

   public Text insert(Text var1, int var2) {
      return this.insert((Text)var1, 0, var1.length, var2);
   }

   public Text insert(Text var1, int var2, int var3) {
      return this.insert(var1, var2, var1.length - var2, var3);
   }

   public Text insert(Text var1, int var2, int var3, int var4) {
      System.arraycopy(this.chars, var4, this.chars, var4 + var3, this.length - var4);
      System.arraycopy(var1.chars, var2, this.chars, var4, var3);
      this.length += var3;
      return this;
   }

   public Text insert(String var1, int var2) {
      return this.insert((String)var1, 0, var1.length(), var2);
   }

   public Text insert(String var1, int var2, int var3) {
      return this.insert(var1, var2, var1.length() - var2, var3);
   }

   public Text insert(String var1, int var2, int var3, int var4) {
      System.arraycopy(this.chars, var4, this.chars, var4 + var3, this.length - var4);
      this.length += var3;

      while(true) {
         --var3;
         if (var3 < 0) {
            return this;
         }

         this.chars[var4++] = (byte)var1.charAt(var2++);
      }
   }

   public int findFirstOf(char var1) {
      return this.findFirstOf(var1, 0);
   }

   public int findFirstOf(char var1, int var2) {
      while(var2 < this.length) {
         if (this.chars[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public int findAnyFirstOf(char[] var1) {
      return this.findAnyFirstOf(var1, 0);
   }

   public int findAnyFirstOf(char[] var1, int var2) {
      while(var2 < this.length) {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            if (this.chars[var2] == var1[var3]) {
               return var2;
            }
         }

         ++var2;
      }

      return -1;
   }

   public int findFirstOf(String var1) {
      return this.findFirstOf(var1, 0);
   }

   public int findFirstOf(String var1, int var2) {
      int var3 = 0;
      int var4 = var2;
      if (var1.length() == 0) {
         return -1;
      } else {
         while(var2 < this.length) {
            if (this.chars[var2] != var1.charAt(var3)) {
               ++var4;
               var2 = var4;
               var3 = 0;
            } else {
               ++var3;
               if (var3 == var1.length()) {
                  return var4;
               }

               ++var2;
            }
         }

         return -1;
      }
   }

   public int findLastOf(char var1) {
      return this.findLastOf(var1, this.length);
   }

   public int findLastOf(char var1, int var2) {
      while(true) {
         --var2;
         if (var2 >= 0) {
            if (this.chars[var2] != var1) {
               continue;
            }

            return var2;
         }

         return -1;
      }
   }

   public int findAnyLastOf(char[] var1) {
      return this.findAnyLastOf(var1, this.length);
   }

   public int findAnyLastOf(char[] var1, int var2) {
      label22:
      while(true) {
         --var2;
         if (var2 >= 0 && var2 < this.length) {
            int var3 = 0;

            while(true) {
               if (var3 >= var1.length) {
                  continue label22;
               }

               if (this.chars[var2] == var1[var3]) {
                  return var2;
               }

               ++var3;
            }
         }

         return -1;
      }
   }

   public void substring(Text var1) {
      this.substring(var1, 0, this.length);
   }

   public void substring(Text var1, int var2) {
      this.substring(var1, var2, this.length - var2);
   }

   public void substring(Text var1, int var2, int var3) {
      for(int var4 = var2; var4 < var2 + var3; ++var4) {
         var1.chars[var1.length++] = this.chars[var4];
      }

   }

   public void dehyphenate() {
      this.dehyphenate(0, this.length);
   }

   public void dehyphenate(int var1) {
      this.dehyphenate(var1, this.length - var1);
   }

   public void dehyphenate(int var1, int var2) {
      int var3;
      while((var3 = this.findFirstOf('-', var1)) != -1 && var3 < var1 + var2) {
         this.delete(var3);
         var2 -= var3 - var1 + 2;
         ++var3;
         var1 = var3;
      }

   }

   public void trim() {
      this.trim(true, true);
   }

   public void trim(boolean var1, boolean var2) {
      if (var1) {
         while(this.length > 0 && this.chars[0] == 32) {
            this.delete(0);
         }
      }

      if (var2) {
         while(this.length > 0 && this.chars[this.length - 1] == 32) {
            this.delete(this.length - 1);
         }
      }

   }

   public int wrapText(int var1) {
      return this.wrapText(0, var1, -1, '|');
   }

   public int wrapText(int var1, char var2) {
      return this.wrapText(0, var1, -1, var2);
   }

   public int wrapText(int var1, int var2, char var3) {
      return this.wrapText(0, var1, var2, var3);
   }

   public int wrapText(int var1, int var2, int var3, char var4) {
      int var5 = this.length;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      int var9 = var1;
      char var10 = 0;
      boolean var12 = false;
      byte[] var13 = this.chars;

      while(true) {
         int var11;
         while((var11 = this.findAnyFirstOf(wordBreaks, var1)) != -1) {
            var7 += var11 - var1;
            if (!var12 && var10 == '-') {
               --var7;
            }

            int var14 = var7 + (var13[var11] == 45 ? 1 : 0);
            if (var14 <= var2 && var10 != '|' && var10 != '\n') {
               var12 = false;
               var10 = (char)var13[var11];
               var1 = var11 + 1;
               ++var7;
               if (var10 == '-' && var13[var1] == 45) {
                  var12 = true;
                  ++var1;
               }
            } else {
               var6 += var1 - var9;
               if (var12) {
                  --var1;
               }

               var1 = (var9 = this.insertLineBreak(var9, var1 - 1, var4)) + 1;
               ++var8;

               while(var1 < this.length && var8 < var3 && (var13[var1 - 1] == 124 || var13[var1 - 1] == 10)) {
                  ++var8;
                  ++var1;
                  ++var9;
               }

               var7 = 1;
               var10 = 0;
               var12 = false;
               if (var3 > 0 && var8 == var3) {
                  this.length = var9;
                  return var6;
               }
            }
         }

         var7 += this.length - var1;
         if (!var12 && var10 == '-') {
            --var7;
         }

         if (var7 > var2 || var10 == '|' || var10 == '\n') {
            var6 += var1 - var9;
            if (var12) {
               --var1;
            }

            var1 = (var9 = this.insertLineBreak(var9, var1 - 1, var4)) + 1;
            ++var8;
            if (var3 > 0 && var8 == var3) {
               this.length = var9;
               return var6;
            }
         }

         this.dehyphenate(var9, this.length - var9);
         return var5;
      }
   }

   private int insertLineBreak(int var1, int var2, char var3) {
      if (this.chars[var2] == 45) {
         ++var2;
         if (this.chars[var2] == 45) {
            this.chars[var2] = (byte)var3;
         } else {
            this.insert(var3, var2);
         }
      } else {
         this.chars[var2] = (byte)var3;
      }

      int var4 = this.length;
      this.dehyphenate(var1, var2 - var1 - 1);
      return var2 - (var4 - this.length) + 1;
   }

   public int parseInt() {
      return this.parseInt(0);
   }

   public int parseInt(int var1) {
      int var2 = 0;
      boolean var3 = false;
      boolean var4 = false;

      for(int var5 = var1; var5 < this.length; ++var5) {
         char var6 = (char)this.chars[var5];
         if (var3) {
            if (!isDigit(var6)) {
               break;
            }

            var2 *= 10;
            var2 += var6 - 48;
         } else if (!isDigit(var6) && var6 != '-') {
            if (!isSpace(var6)) {
               break;
            }
         } else {
            var4 = var6 == '-';
            var3 = true;
         }
      }

      if (var4) {
         var2 = -var2;
      }

      return var2;
   }

   public final int getStringWidth() {
      return this.getStringWidth(0, this.length, true);
   }

   public final int getStringWidth(boolean var1) {
      return this.getStringWidth(0, this.length, var1);
   }

   public final int getStringWidth(int var1, int var2) {
      return this.getStringWidth(var1, var2, true);
   }

   public final int getStringWidth(int var1, int var2, boolean var3) {
      int var4 = 0;
      int var5 = 0;
      if (var2 == -1 || var2 >= this.length) {
         var2 = this.length;
      }

      if (var1 >= 0 && var1 < var2) {
         for(int var6 = var1; var6 < var2; ++var6) {
            char var7 = (char)this.chars[var6];
            if (var7 != '\n' && var7 != '|') {
               if (var7 == ' ') {
                  var5 += 7;
               } else {
                  var5 += 7;
               }
            } else {
               if (!var3) {
                  break;
               }

               if (var5 > var4) {
                  var4 = var5;
                  var5 = 0;
               }
            }
         }
      }

      if (var5 > var4) {
         var4 = var5;
      }

      return var4;
   }

   public final int getNumLines() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.length; ++var2) {
         char var3 = (char)this.chars[var2];
         if (var3 == '\n' || var3 == '|') {
            ++var1;
         }
      }

      return var1;
   }

   public boolean compareTo(Text var1) {
      boolean var2 = var1.length == this.length;

      for(int var3 = 0; var2 && var3 < this.length; ++var3) {
         var2 = var2 && var1.chars[var3] == this.chars[var3];
      }

      return var2;
   }

   public boolean compareTo(String var1) {
      boolean var2 = var1.length() == this.length;

      for(int var3 = 0; var2 && var3 < this.length; ++var3) {
         var2 = var2 && var1.charAt(var3) == this.chars[var3];
      }

      return var2;
   }

   public void toLower() {
      for(int var1 = 0; var1 < this.length; ++var1) {
         this.chars[var1] = (byte)toLower((char)this.chars[var1]);
      }

   }

   public void toUpper() {
      for(int var1 = 0; var1 < this.length; ++var1) {
         this.chars[var1] = (byte)toUpper((char)this.chars[var1]);
      }

   }

   public Text print() {
      this.print(System.out, 0, this.length);
      return this;
   }

   public Text print(int var1) {
      this.print(System.out, var1, this.length - var1);
      return this;
   }

   public Text print(PrintStream var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3 + var2; ++var4) {
         char var5 = (char)this.chars[var4];
         if (var5 == '|') {
            var1.print('|');
            var5 = '\n';
         }

         var1.print(var5);
      }

      return this;
   }

   public String toString() {
      return "Unsupported";
   }

   public void dispose() {
      for(int var1 = 0; var1 < 7; ++var1) {
         if (scratchBuffers[var1] == this) {
            bufferFlags &= ~(1 << var1);
            this.setLength(0);
            return;
         }
      }

   }

   public static boolean isSpace(char var0) {
      return var0 == ' ' || var0 == '\n' || var0 == '|' || var0 == '\r' || var0 == '\t';
   }

   public static boolean isDigit(char var0) {
      return var0 >= '0' && var0 <= '9';
   }

   public static char toLower(char var0) {
      return var0 >= 'A' && var0 <= 'Z' ? (char)(var0 - 65 + 97) : var0;
   }

   public static char toUpper(char var0) {
      return var0 >= 'a' && var0 <= 'z' ? (char)(var0 - 97 + 65) : var0;
   }

   public static Text getSmallBuffer() {
      for(int var0 = 6; var0 >= 0; --var0) {
         if ((bufferFlags & 1 << var0) == 0) {
            bufferFlags |= 1 << var0;
            return scratchBuffers[var0];
         }
      }

      return null;
   }

   public static Text getFatalErrorBuffer() {
      scratchBuffers[0].setLength(0);
      return scratchBuffers[0];
   }

   public static Text getLargeBuffer() {
      for(int var0 = 0; var0 < 7; ++var0) {
         if ((bufferFlags & 1 << var0) == 0) {
            bufferFlags |= 1 << var0;
            return scratchBuffers[var0];
         }
      }

      return null;
   }

   public static void freeAllBuffers() {
      bufferFlags = 0;

      for(int var0 = 0; var0 < 7; ++var0) {
         scratchBuffers[var0].setLength(0);
      }

   }

   public static final void allocateText(int var0) {
      text[var0] = new byte[textSizes[var0]];
      textMap[var0] = new short[textCount[var0]];
   }

   public static final void unloadText(int var0) {
      if (text[var0] != null) {
         text[var0] = null;
         textMap[var0] = null;
         System.gc();
      }

   }

   public static final int identifyLocale(String var0) {
      int var1 = defaultLanguage;
      if (var0 == null) {
         return var1;
      } else {
         try {
            String var2 = "";
            if (var0.length() > 3 && var0.charAt(2) == '-') {
               var2 = "," + var0.substring(0, 2) + ",";
            }

            var0 = "," + var0 + ",";
            Text var3 = getSmallBuffer();
            beginTextLoading();

            for(int var4 = 0; var4 < 5; ++var4) {
               loadTextFromIndex(var4, 16);
               var3.setLength(0);
               composeText((short)16, (short)1, var3);
               var3.insert((char)',', 0);
               var3.append(',');
               if (var3.findFirstOf(var0) != -1 || var3.findFirstOf(var2) != -1) {
                  var1 = var4;
                  break;
               }
            }

            finishTextLoading();
            var3.dispose();
         } catch (IOException var5) {
            App.Error(var5, 666);
         }

         return var1;
      }
   }

   public static final void setLanguage(int var0) {
      try {
         defaultLanguage = var0;
         beginTextLoading();

         for(int var1 = 0; var1 < 17; ++var1) {
            if (text[var1] != null) {
               loadTextFromIndex(defaultLanguage, var1);
            }
         }

         finishTextLoading();
         SDKTextUtils.setCurrentLanguage(var0);
      } catch (IOException var2) {
         App.Error(var2, 666);
      }

   }

   public static final void beginTextLoading() throws IOException {
      textIndex = Resource.loadFileIndex("/strings.idx");
      textLastType = -1;
      textCurChunk = -1;
   }

   public static final void finishTextLoading() {
      textIndex = null;
      textChunkStream = null;
   }

   public static final void loadTextFromIndex(int var0, int var1) {
      try {
         if (var1 < textLastType) {
            App.Error(87);
         }

         textLastType = var1;
         int var2 = textIndex[(var1 + var0 * 17) * 3];
         int var3 = textIndex[(var1 + var0 * 17) * 3 + 1];
         int var4 = textIndex[(var1 + var0 * 17) * 3 + 2];
         if (textCurChunk != var2) {
            textChunkStream = App.getResourceAsStream("/strings" + var2 + ".bin");
            textCurOffset = 0;
            textCurChunk = var2;
         }

         InputStream var5 = textChunkStream;
         if (var3 != textCurOffset) {
            Resource.bufSkip(var5, var3 - textCurOffset, false);
            textCurOffset += var3 - textCurOffset;
         }

         Resource.readByteArray(var5, text[var1], 0, var4);
         textCurOffset += var4;
         var5 = null;
         short[] var6 = textMap[var1];
         byte[] var7 = text[var1];
         byte var8 = 0;
         int var11 = var8 + 1;
         var6[var8] = 0;

         for(int var9 = 0; var9 < var4; ++var9) {
            if (var7[var9] == 0) {
               var6[var11++] = (short)(var9 + 1);
            }
         }
      } catch (Exception var10) {
         App.Error(var10, 88);
      }

   }

   public static final void loadText(int var0) {
      try {
         allocateText(var0);
         beginTextLoading();
         loadTextFromIndex(defaultLanguage, var0);
         finishTextLoading();
      } catch (IOException var2) {
         App.Error(var2, 666);
      }

   }

   public static final void resetTextArgs() {
      numTextArgs = 0;
      dynamicArgs.setLength(0);
   }

   public static final void addTextArg(boolean var0) {
      if (var0) {
         composeText((short)5, (short)180, dynamicArgs);
      } else {
         composeText((short)5, (short)181, dynamicArgs);
      }

      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextArg(char var0) {
      if (numTextArgs + 1 >= 50) {
         App.Error(new Exception("Added too many String Args"), 50);
      }

      dynamicArgs.append(var0);
      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextArg(int var0) {
      if (numTextArgs + 1 >= 50) {
         App.Error(new Exception("Added too many String Args"), 50);
      }

      dynamicArgs.append(var0);
      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextArg(Text var0, int var1, int var2) {
      if (numTextArgs + 1 >= 50) {
         App.Error(new Exception("Added too many String Args"), 50);
      }

      var0.substring(dynamicArgs, var1, var2 - var1);
      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextArg(Text var0) {
      if (numTextArgs + 1 >= 50) {
         App.Error(new Exception("Added too many String Args"), 50);
      }

      dynamicArgs.append(var0);
      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextArg(String var0) {
      if (numTextArgs + 1 >= 50) {
         App.Error(new Exception("Added too many String Args"), 50);
      }

      dynamicArgs.append(var0);
      argIndex[numTextArgs++] = (short)dynamicArgs.length();
   }

   public static final void addTextIDArg(short var0) {
      addTextArg((short)(var0 >> 10 & 31), (short)(var0 & 1023));
   }

   public static final void addTextArg(short var0, short var1) {
      Text var2 = getLargeBuffer();
      composeText(var0, var1, var2);
      addTextArg(var2);
      var2.dispose();
   }

   public static final int STRINGID(short var0, short var1) {
      return var0 << 10 | var1;
   }

   public static final void composeText(int var0, Text var1) {
      composeText((short)(var0 >> 10 & 31), (short)(var0 & 1023), var1);
   }

   public static final void composeText(short var0, short var1, Text var2) {
      byte[] var3 = text[var0];
      if (var3 == null) {
         App.Error(85);
      }

      if (var1 < 0 || var1 > textMap[var0].length) {
         App.Error(84);
      }

      short var4 = textMap[var0][var1];

      for(int var5 = var3[var4] & 255; var5 != 0; var5 = var3[var4] & 255) {
         byte var7;
         if (var5 == 92) {
            ++var4;
            var7 = var3[var4];
            if (var7 == 37) {
               var2.append((char)var7);
            } else if (var7 == 110) {
               var2.append('\n');
            } else {
               var2.append('\\');
               var2.append((char)var7);
            }
         } else if (var5 != 37) {
            var2.append((char)var5);
         } else {
            ++var4;
            var7 = var3[var4];
            int var10000 = (var7 - 48) * 10;
            ++var4;
            int var6 = var10000 + (var3[var4] - 48);
            --var6;
            if (var6 < 0 || var6 >= numTextArgs) {
               App.Error(86);
            }

            var2.appendTextArg(var6);
         }

         ++var4;
      }

   }

   public static void composeTextField(int var0, Text var1) {
      composeText((short)(var0 >> 10), (short)(var0 & 1023), var1);
   }

   public static int[] getCharIndices(char var0) {
      int var2 = var0 - 33;
      int var3 = 0;
      indices[1] = indices[2] = indices[4] = indices[5] = 0;
      switch(var0) {
      case '\u0085':
         var2 = 94;
      case '\u0086':
      case '\u0087':
      case '\u0088':
      case '\u0089':
      case '\u008a':
      case '\u008e':
      case '\u008f':
      case '\u0090':
      case '\u0091':
      case '\u0092':
      case '\u0093':
      case '\u0094':
      case '\u0095':
      case '\u0096':
      case '\u0097':
      case '\u0098':
      case '\u009a':
      case '\u009b':
      case '\u009d':
      case '\u009e':
      case '\u009f':
      case ' ':
      case '£':
      case '¤':
      case '¥':
      case '§':
      case '¨':
      case '«':
      case '¬':
      case '\u00ad':
      case '®':
      case '¯':
      case '±':
      case '²':
      case '³':
      case '´':
      case 'µ':
      case '¶':
      case '·':
      case '¸':
      case '¹':
      case '»':
      case 'Ç':
      case '×':
      default:
         break;
      case '\u008b':
         var2 = 101;
         break;
      case '\u008c':
         var2 = 142;
         break;
      case '\u008d':
         var2 = 100;
         break;
      case '\u0099':
         var2 = 107;
         break;
      case '\u009c':
         var2 = 143;
         break;
      case '¡':
         var2 = 120;
         break;
      case '¢':
         var2 = 127;
         break;
      case '¦':
         var2 = 124;
         break;
      case '©':
         var2 = 106;
         break;
      case 'ª':
         var2 = 135;
         indices[2] = -3;
         break;
      case '°':
         var2 = 126;
         break;
      case 'º':
         var2 = 63;
         break;
      case '¼':
      case '½':
      case '¾':
         var2 = var0 - 188 + 108;
         break;
      case '¿':
         var2 = 119;
         break;
      case 'À':
      case 'Á':
      case 'Â':
      case 'Ã':
      case 'Ä':
      case 'Å':
         var3 = 121 + (var0 - 192);
         var2 = 128;
         break;
      case 'Æ':
         var3 = 120;
         var2 = 132;
         break;
      case 'È':
      case 'É':
      case 'Ê':
      case 'Ë':
         var3 = 121 + (var0 - 200);
         if (var0 == 203) {
            ++var3;
         }

         var2 = 129;
         break;
      case 'Ì':
      case 'Í':
      case 'Î':
      case 'Ï':
         var3 = 121 + (var0 - 204);
         if (var0 == 207) {
            ++var3;
         }

         var2 = 130;
         break;
      case 'Ð':
         var2 = 83;
         var3 = 0;
         break;
      case 'Ñ':
         var2 = 134;
         var3 = 124;
         break;
      case 'Ò':
      case 'Ó':
      case 'Ô':
      case 'Õ':
      case 'Ö':
         var3 = 121 + (var0 - 210);
         var2 = 131;
         break;
      case 'Ø':
         var2 = 46;
         var3 = 14;
         break;
      case 'Ù':
      case 'Ú':
      case 'Û':
      case 'Ü':
         var3 = 121 + (var0 - 217);
         if (var0 == 220) {
            ++var3;
         }

         var2 = 132;
         break;
      case 'Ý':
         var2 = 133;
         var3 = 122;
         break;
      case 'Þ':
         var2 = 84;
         var3 = 0;
         break;
      case 'ß':
         var3 = 122;
         var2 = 135;
         break;
      case 'à':
         var3 = 120;
         var2 = 135;
         break;
      case 'á':
         var3 = 120;
         var2 = 136;
         break;
      case 'â':
         var2 = 85;
         var3 = 0;
         break;
      case 'ã':
         var3 = 120;
         var2 = 129;
         break;
      case 'ä':
         var3 = 120;
         var2 = 137;
         break;
      case 'å':
         var3 = 126;
         var2 = 130;
         break;
      case 'æ':
         var2 = 86;
         var3 = 0;
         break;
      case 'ç':
         var2 = 116;
         break;
      case 'è':
         var2 = 122;
         var3 = 134;
         break;
      case 'é':
         var2 = 120;
         var3 = 134;
         break;
      case 'ê':
         var2 = 142;
         var3 = 0;
         break;
      case 'ë':
         var2 = 120;
         var3 = 138;
         break;
      case 'ì':
         var2 = 122;
         var3 = 139;
         break;
      case 'í':
         var2 = 0;
         var3 = 87;
         break;
      case 'î':
         var2 = 120;
         var3 = 139;
         break;
      case 'ï':
         var2 = 120;
         var3 = 140;
         break;
      case 'ð':
         var2 = 126;
         var3 = 132;
         break;
      case 'ñ':
         var2 = 125;
         var3 = 133;
         break;
      case 'ò':
         var2 = 122;
         var3 = 141;
         break;
      case 'ó':
         var2 = 126;
         var3 = 141;
         break;
      case 'ô':
         var2 = 120;
         var3 = 141;
         break;
      case 'õ':
         var2 = 128;
         indices[2] = -3;
         break;
      case 'ö':
         var2 = 131;
         indices[2] = -3;
         break;
      case '÷':
         var2 = 12;
         var3 = 0;
      }

      indices[0] = (char)var2;
      indices[3] = (char)var3;
      return indices;
   }

   private static void Test() {
   }
}
