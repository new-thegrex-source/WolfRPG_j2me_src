/* Decompiler 198ms, total 368ms, lines 334 */
import javax.microedition.lcdui.Image;

public class Graphics {
   private Image backBuffer;
   public javax.microedition.lcdui.Graphics g;
   private int curColor = 0;
   public static final int TRANS_NONE = 0;
   public static final int TRANS_ROT90 = 5;
   public static final int TRANS_ROT180 = 3;
   public static final int TRANS_ROT270 = 6;
   public static final int TRANS_MIRROR = 2;
   public static final int TRANS_MIRROR_ROT90 = 7;
   public static final int TRANS_MIRROR_ROT180 = 1;
   public static final int TRANS_MIRROR_ROT270 = 4;
   public static final short ANCHORS_NONE = 0;
   public static final short ANCHORS_HCENTER = 1;
   public static final short ANCHORS_VCENTER = 2;
   public static final short ANCHORS_LEFT = 4;
   public static final short ANCHORS_RIGHT = 8;
   public static final short ANCHORS_TOP = 16;
   public static final short ANCHORS_BOTTOM = 32;
   public static final short ANCHORS_TOP_CENTER = 17;
   public static final short ANCHORS_CENTER = 3;
   private static final short ANCHORS_HORIZONTAL = 13;
   private static final short ANCHORS_VERTICAL = 50;

   public void setGraphics(javax.microedition.lcdui.Graphics var1) {
      this.g = var1;
   }

   public final void setColor(int var1) {
      this.curColor = -16777216 | var1;
      this.g.setColor(var1);
   }

   public final void fillCircle(int var1, int var2, int var3) {
      this.g.fillArc(var1, var2, var3, var3, 0, 360);
   }

   public final void fillRect(int var1, int var2, int var3, int var4) {
      this.g.fillRect(var1, var2, var3, var4);
   }

   public final void fillRect(int var1, int var2, int var3, int var4, int var5) {
      this.g.setColor(var5);
      this.g.fillRect(var1, var2, var3, var4);
      this.g.setColor(this.curColor);
   }

   public final void drawRect(int var1, int var2, int var3, int var4) {
      this.g.drawRect(var1, var2, var3, var4);
   }

   public final void drawRect(int var1, int var2, int var3, int var4, int var5) {
      this.g.setColor(var5);
      this.g.drawRect(var1, var2, var3, var4);
      this.g.setColor(this.curColor);
   }

   public final void eraseRgn(int var1, int var2, int var3, int var4) {
      this.g.setColor(0);
      this.g.fillRect(var1, var2, var3, var4);
      this.g.setColor(this.curColor);
   }

   public final void eraseRgn(int[] var1) {
      this.eraseRgn(var1[0], var1[1], var1[2], var1[3]);
   }

   public final void drawLine(int var1, int var2, int var3, int var4) {
      this.g.drawLine(var1, var2, var3, var4);
   }

   public final void drawLine(int var1, int var2, int var3, int var4, int var5) {
      this.g.setColor(var5);
      this.g.drawLine(var1, var2, var3, var4);
      this.g.setColor(this.curColor);
   }

   public final void drawImage(Image var1, int var2, int var3) {
      this.g.drawImage(var1, var2, var3, 0);
   }

   public final void drawImage(Image var1, int var2, int var3, int var4) {
      this.g.drawImage(var1, var2, var3, var4);
   }

   public final void drawImage(Image var1, int var2, int var3, int var4, int var5) {
      this.drawRegion(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, var3, var4, var5);
   }

   public final void drawRegion(Image var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.drawRegion(var1, var2, var3, var4, var5, var6, var7, 0, 0);
   }

   public final int getClipX() {
      return this.g.getClipX();
   }

   public final int getClipY() {
      return this.g.getClipY();
   }

   public final int getClipWidth() {
      return this.g.getClipWidth();
   }

   public final int getClipHeight() {
      return this.g.getClipHeight();
   }

   public final int getTranslateX() {
      return this.g.getTranslateX();
   }

   public final int getTranslateY() {
      return this.g.getTranslateY();
   }

   public final void drawRegion(Image var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      try {
         this.g.drawRegion(var1, var2, var3, var4, var5, var9, var6, var7, var8);
      } catch (Exception var11) {
         App.Error(var11, 110);
      }

   }

   public final void fillRegion(Image var1, int var2, int var3, int var4, int var5) {
      this.fillRegion(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, var3, var4, var5, 0);
   }

   public final void fillRegion(Image var1, int var2, int var3, int var4, int var5, int var6) {
      this.fillRegion(var1, 0, 0, var1.getWidth(), var1.getHeight(), var2, var3, var4, var5, var6);
   }

   public final void fillRegion(Image var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this.fillRegion(var1, var2, var3, var4, var5, var6, var7, var8, var9, 0);
   }

   public final void fillRegion(Image var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      int var11 = var7;

      int var15;
      for(int var12 = var7 + var9; var11 < var12; var11 += var15) {
         int var13 = var6;
         int var14 = var6 + var8;

         int var16;
         for(var15 = Math.min(var5, var12 - var11); var13 < var14; var13 += var16) {
            var16 = Math.min(var4, var14 - var13);
            this.drawRegion(var1, var2, var3, var16, var15, var13, var11, 0, var10);
         }
      }

   }

   public final void drawBevel(int var1, int var2, int var3, int var4, int var5, int var6) {
      --var5;
      --var6;
      this.setColor(var1);
      this.drawLine(var3, var4, var3, var4 + var6);
      this.drawLine(var3, var4, var3 + var5, var4);
      this.setColor(var2);
      this.drawLine(var3 + var5, var4, var3 + var5, var4 + var6);
      this.drawLine(var3, var4 + var6, var3 + var5, var4 + var6);
   }

   public final void drawString(Text var1, int var2, int var3, int var4) {
      this.drawString(Canvas.imgFont, var1, var2, var3, 12, var4, 0, var1.length());
   }

   public final void drawString(Text var1, int var2, int var3, int var4, int var5, int var6) {
      this.drawString(Canvas.imgFont, var1, var2, var3, 12, var4, var5, var6);
   }

   public final void drawString(Text var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.drawString(Canvas.imgFont, var1, var2, var3, var4, var5, var6, var7);
   }

   public final void drawString(Image var1, Text var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var8 == -1) {
         var8 = var2.length();
      }

      if (var8 != 0) {
         int var9 = var2.getStringWidth(var7, var7 + var8, false);
         int var10 = var3;
         if ((var6 & 8) != 0) {
            var3 -= var9;
            var10 = var3;
         } else if ((var6 & 1) != 0) {
            var3 -= var9 / 2 + (var9 & 1);
         }

         int var11;
         if ((var6 & 32) != 0) {
            var4 -= var5;
         } else if ((var6 & 2) != 0) {
            var11 = var2.getNumLines();
            var4 -= var5 * var11 / 2;
         }

         var11 = var7 + Math.min(var8, var2.length() - var7);

         for(int var12 = var7; var12 < var11; ++var12) {
            char var13 = var2.charAt(var12);
            if (var13 != '\n' && var13 != '|') {
               if (var13 != ' ' && var13 != 160) {
                  if (var13 == '\\' && var12 < var11) {
                     ++var12;
                     var13 = var2.charAt(var12);
                     int var14 = var13 - 65;
                     if (var14 >= 0 && var14 < 15) {
                        this.drawBuffIcon(var14, var3, var4, 0);
                     } else {
                        this.drawChar(var1, var13, var3, var4);
                     }
                  } else {
                     this.drawChar(var1, var13, var3, var4);
                  }

                  var3 += 7;
               } else {
                  var3 += 7;
               }
            } else {
               var4 += var5;
               if ((var6 & 1) != 0) {
                  var3 = var10 - var2.getStringWidth(var12 + 1, var11, false) / 2;
               } else {
                  var3 = var10;
               }
            }
         }

      }
   }

   public final void setClip(int var1, int var2, int var3, int var4) {
      this.g.setClip(var1, var2, var3, var4);
   }

   public final void drawChar(Image var1, char var2, int var3, int var4) {
      int[] var5 = Text.getCharIndices(var2);
      int var6 = var5[0];
      int var7 = var5[3];
      if (var6 < 0 || var6 >= 144 || var7 < 0 || var7 >= 144) {
         var6 = 30;
         var7 = 0;
      }

      this.drawRegion(var1, (var6 & 15) * 9, ((var6 & 240) >> 4) * 12, 9, 12, var2 != 237 ? var3 + var5[1] : var3 + var5[1] + 2, var4 + var5[2]);
      if (var7 != 0) {
         this.drawRegion(var1, (var7 & 15) * 9, ((var7 & 240) >> 4) * 12, 9, 12, var3 + var5[4], var4 + var5[5]);
      }

   }

   public final void drawBoxedString(Text var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = var1.getStringWidth();
      int var8 = var1.getNumLines() * 10 + var4 * 2;
      int var9 = var2 - var7 / 2 - var4;
      int var11 = var3 - var4;
      int var10 = var9 + var7 + var4 * 2;
      int var12 = var11 + var8;
      this.setColor(var5);
      this.fillRect(var9, var11, var7 + var4 * 2 + 1, var8 + 1);
      this.setColor(var6);
      --var9;
      ++var10;
      --var11;
      ++var12;
      this.drawLine(var9, var11, var10, var11);
      this.drawLine(var10, var11, var10, var12);
      this.drawLine(var9, var12, var10, var12);
      this.drawLine(var9, var11, var9, var12);
      this.drawString(var1, var2 - 1, var3 + 1, 1);
   }

   public final void drawBuffIcon(int var1, int var2, int var3, int var4) {
      this.drawRegion(Canvas.imgBuffIcons, 0, var1 * 12, 12, 12, var2, var3, var4, 0);
   }

   public final void drawCursor(int var1, int var2, int var3) {
      this.drawCursor(var1, var2, var3, true);
   }

   public final void drawCursor(int var1, int var2, int var3, boolean var4) {
      Text var5 = Text.getSmallBuffer();
      var5.setLength(0);
      if (var4) {
         var5.append('\u008a');
      } else {
         var5.append('\u0084');
      }

      this.drawString(var5, var1, var2, var3);
      var5.dispose();
   }

   public final void drawRGB(int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.g.drawRGB(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final void clipRect(int var1, int var2, int var3, int var4) {
      this.g.clipRect(var1, var2, var3, var4);
   }

   public final void clearClipRect() {
      this.g.setClip(0, 0, Canvas.displayRect[2], Canvas.displayRect[3]);
   }

   public final void setScreenSpace(int[] var1) {
      this.g.translate(-this.g.getTranslateX(), -this.g.getTranslateY());
      this.g.setClip(var1[0], var1[1], var1[2], var1[3]);
      this.g.translate(var1[0], var1[1]);
   }

   public final void setScreenSpace(int var1, int var2, int var3, int var4) {
      this.g.translate(-this.g.getTranslateX(), -this.g.getTranslateY());
      this.g.setClip(var1, var2, var3, var4);
      this.g.translate(var1, var2);
   }

   public final void resetScreenSpace() {
      this.setScreenSpace(Canvas.displayRect);
   }

   public final void copyTo(javax.microedition.lcdui.Graphics var1) {
      var1.drawImage(this.backBuffer, 0, 0, 0);
   }
}
