/* Decompiler 131ms, total 316ms, lines 568 */
public class Span {
   public static final int COLOR_LSB = 65793;
   public static final int COLOR_2LSB = 197379;
   public static final int COLOR_3LSB = 460551;
   public static final int COLOR_MSB = 8421504;
   public static final int MASK_LSB = 16711422;
   public static final int MASK_2LSB = 16579836;
   public static final int MASK_3LSB = 16316664;
   public static final int ALPHA_LSBMASK = 16843009;
   static int countBackFace;
   static int countDrawn;
   static int spanPixels;
   static int spanCalls;
   static int zeroDT;
   static int zeroDS;
   static int zeroDZ;

   static void resetCounters() {
      countBackFace = 0;
      countDrawn = 0;
      spanPixels = 0;
      spanCalls = 0;
      zeroDT = 0;
      zeroDS = 0;
      zeroDZ = 0;
   }

   public void span(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      spanPixels += var9;
      ++spanCalls;
      if (var8 == 0) {
         ++zeroDZ;
      }

      if (var6 == 0) {
         ++zeroDS;
         spanTexDTBlend25(var1, var2, var3, var5, var7, var9);
      } else if (var7 == 0) {
         ++zeroDT;
         spanTexDSBlend25(var1, var2, var3, var5, var6, var9);
      } else {
         spanTexBlend25(var1, var2, var3, var5, var6, var7, var9);
      }

   }

   public void span(int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = TinyGL.pixels;
      byte[] var7 = TinyGL.textureBase;
      int[] var8 = TinyGL.spanPalette;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         var6[var1] = ((var6[var1] & 16711422) >> 1) + ((var8[var7[var2 >> 12]] & 16711422) >> 1);
         var2 += var4;
         var1 += var3;
      }
   }

   public static void spanTexSub(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int var9 = TinyGL.sShift;
      int var10 = TinyGL.sMask;
      int var11 = TinyGL.tShift;
      int var12 = TinyGL.tMask;

      int[] var13;
      int var14;
      int var15;
      for(var13 = TinyGL.spanPalette; var6 >= 4; var6 -= 4) {
         var14 = (var7[var0] | 16843009) - var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var15 = var14 & 16843008;
         var7[var0] = (var14 ^ var15) & var15 - (var15 >> 7);
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var14 = (var7[var0] | 16843009) - var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var15 = var14 & 16843008;
         var7[var0] = (var14 ^ var15) & var15 - (var15 >> 7);
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var14 = (var7[var0] | 16843009) - var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var15 = var14 & 16843008;
         var7[var0] = (var14 ^ var15) & var15 - (var15 >> 7);
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var14 = (var7[var0] | 16843009) - var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var15 = var14 & 16843008;
         var7[var0] = (var14 ^ var15) & var15 - (var15 >> 7);
         var1 += var4;
         var2 += var5;
         var0 += var3;
      }

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         var14 = (var7[var0] | 16843009) - var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var15 = var14 & 16843008;
         var7[var0] = (var14 ^ var15) & var15 - (var15 >> 7);
         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanTexBlend25(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int var9 = TinyGL.sShift;
      int var10 = TinyGL.sMask;
      int var11 = TinyGL.tShift;
      int var12 = TinyGL.tMask;
      int[] var13 = TinyGL.spanPalette;

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         var7[var0] = ((var7[var0] & 16711422) >> 1) + ((var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]] & 16316664) >> 2);
         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanTexDTBlend25(int var0, int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = TinyGL.pixels;
      byte[] var7 = TinyGL.textureBase;
      int var8 = TinyGL.sShift;
      int var9 = TinyGL.sMask;
      int var10 = TinyGL.tShift;
      int var11 = TinyGL.tMask;
      int[] var12 = TinyGL.spanPalette;
      var1 = var1 >> var8 & var9;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         var6[var0] = ((var6[var0] & 16711422) >> 1) + ((var12[var7[var1 + (var2 >> var10 & var11)]] & 16316664) >> 2);
         var2 += var4;
         var0 += var3;
      }
   }

   public static void spanTexDSBlend25(int var0, int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = TinyGL.pixels;
      byte[] var7 = TinyGL.textureBase;
      int var8 = TinyGL.sShift;
      int var9 = TinyGL.sMask;
      int var10 = TinyGL.tShift;
      int var11 = TinyGL.tMask;
      int[] var12 = TinyGL.spanPalette;
      var2 = var2 >> var10 & var11;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         var6[var0] = ((var6[var0] & 16711422) >> 1) + ((var12[var7[(var1 >> var8 & var9) + var2]] & 16316664) >> 2);
         var1 += var4;
         var0 += var3;
      }
   }

   public static void spanTex(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int var9 = TinyGL.sShift;
      int var10 = TinyGL.sMask;
      int var11 = TinyGL.tShift;
      int var12 = TinyGL.tMask;

      int[] var13;
      for(var13 = TinyGL.spanPalette; var6 >= 8; var6 -= 8) {
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
      }

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         var7[var0] = var13[var8[var1 >> var9 & var10 | var2 >> var11 & var12]];
         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanTexDT(int var0, int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = TinyGL.pixels;
      byte[] var7 = TinyGL.textureBase;
      int var8 = TinyGL.sShift;
      int var9 = TinyGL.sMask;
      int var10 = TinyGL.tShift;
      int var11 = TinyGL.tMask;
      int[] var12 = TinyGL.spanPalette;

      for(var1 = var1 >> var8 & var9; var5 >= 8; var5 -= 8) {
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
      }

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         var6[var0] = var12[var7[var1 | var2 >> var10 & var11]];
         var2 += var4;
         var0 += var3;
      }
   }

   public static void spanTexDS(int var0, int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = TinyGL.pixels;
      byte[] var7 = TinyGL.textureBase;
      int var8 = TinyGL.sShift;
      int var9 = TinyGL.sMask;
      int var10 = TinyGL.tShift;
      int var11 = TinyGL.tMask;
      int[] var12 = TinyGL.spanPalette;

      for(var2 = var2 >> var10 & var11; var5 >= 8; var5 -= 8) {
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
      }

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         var6[var0] = var12[var7[var1 >> var8 & var9 | var2]];
         var1 += var4;
         var0 += var3;
      }
   }

   public static void spanTransBlend50(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int var9 = TinyGL.sShift;
      int var10 = TinyGL.sMask;
      int var11 = TinyGL.tShift;
      int var12 = TinyGL.tMask;
      int[] var13 = TinyGL.spanPalette;

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         byte var14 = var8[var1 >> var9 & var10 | var2 >> var11 & var12];
         if (var14 != 0) {
            var7[var0] = ((var7[var0] & 16711422) >> 1) + var13[var14];
         }

         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanTransAdd(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int var9 = TinyGL.sShift;
      int var10 = TinyGL.sMask;
      int var11 = TinyGL.tShift;
      int var12 = TinyGL.tMask;
      int[] var13 = TinyGL.spanPalette;

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         byte var16 = var8[var1 >> var9 & var10 | var2 >> var11 & var12];
         if (var16 != 0) {
            int var14 = (var7[var0] & 16711422) + var13[var16];
            int var15 = var14 & 16843008;
            var7[var0] = var14 ^ var15 | var15 - (var15 >> 7);
         }

         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanTrans(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int[] var7 = TinyGL.pixels;
      byte[] var8 = TinyGL.textureBase;
      int[] var9 = TinyGL.spanPalette;
      int var10 = TinyGL.sShift;
      int var11 = TinyGL.sMask;
      int var12 = TinyGL.tShift;
      int var13 = TinyGL.tMask;

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         byte var14 = var8[var1 >> var10 & var11 | var2 >> var12 & var13];
         if (var14 != 0) {
            var7[var0] = var9[var14];
         }

         var1 += var4;
         var2 += var5;
         var0 += var3;
      }
   }

   public static void spanSpriteBlend50(int var0, int var1, int var2, int var3, int var4) {
      int[] var5 = TinyGL.pixels;
      byte[] var6 = TinyGL.textureBase;

      int[] var7;
      for(var7 = TinyGL.spanPalette; var4 >= 8; var4 -= 8) {
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
      }

      while(true) {
         --var4;
         if (var4 < 0) {
            return;
         }

         var5[var0] = ((var5[var0] & 16711422) >> 1) + var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
      }
   }

   public static void spanSpriteAdd(int var0, int var1, int var2, int var3, int var4) {
      int[] var5 = TinyGL.pixels;
      byte[] var6 = TinyGL.textureBase;

      int[] var7;
      int var8;
      int var9;
      for(var7 = TinyGL.spanPalette; var4 >= 8; var4 -= 8) {
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
      }

      while(true) {
         --var4;
         if (var4 < 0) {
            return;
         }

         var8 = (var5[var0] & 16711422) + var7[var6[var1 >> 12]];
         var9 = var8 & 16843008;
         var5[var0] = var8 ^ var9 | var9 - (var9 >> 7);
         var1 += var3;
         var0 += var2;
      }
   }

   public static void spanSprite(int var0, int var1, int var2, int var3, int var4) {
      int[] var5 = TinyGL.pixels;
      byte[] var6 = TinyGL.textureBase;

      int[] var7;
      for(var7 = TinyGL.spanPalette; var4 >= 8; var4 -= 8) {
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
      }

      while(true) {
         --var4;
         if (var4 < 0) {
            return;
         }

         var5[var0] = var7[var6[var1 >> 12]];
         var1 += var3;
         var0 += var2;
      }
   }
}
