/* Decompiler 379ms, total 564ms, lines 956 */
public final class TinyGL {
   public static final int SHIFT_STRETCH = 12;
   public static final int UNIT_SCALE = 65536;
   static final int MATRIX_ONE_SHIFT = 14;
   static final int MATRIX_ONE = 16384;
   static final int SCREEN_SHIFT = 3;
   static final int SCREEN_ONE = 8;
   static final int INTERPOLATE_SHIFT = 16;
   static final int INTERPOLATE_TO_PIXELS_SHIFT = 19;
   static final int SCREEN_PRESTEP = 7;
   static final int INTERPOLATE_PRESTEP = 524287;
   static final boolean CLAMP_TO_VIEWPORT = false;
   static final int PIXEL_GUARD_SIZE = 1;
   static final int MAX_PRIMITIVE_VERTS = 20;
   static final int OE_SHIFT = 4;
   static final int CULL_NONE = 0;
   static final int CULL_CW = 1;
   static final int CULL_CCW = 2;
   static int faceCull = 2;
   public static Span span;
   public static int renderMode;
   public static int NEAR_CLIP = 256;
   public static int CULL_EXTRA;
   public static final int NUM_FOG_LEVELS = 16;
   static byte[] textureBase;
   static int[] imageBounds;
   static int[] spanPalette;
   static int[][] paletteBase;
   static int[][] scratchPalette;
   static int sWidth;
   static int sShift;
   static int sMask;
   static int tHeight;
   static int tShift;
   static int tMask;
   static boolean swapXY;
   static int screenWidth;
   static int screenHeight;
   static int[] pixels;
   static int[] columnScale;
   static final int COLUMN_SCALE_INIT = Integer.MAX_VALUE;
   static final int COLUMN_SCALE_OCCLUDED = 2147483646;
   static int[] view;
   static int[] view2D;
   static int[] projection;
   static int[] mvp;
   static int[] mvp2D;
   static TGLVert[] cv;
   static TGLVert[] clippingVert1;
   static TGLVert[] clippingVert2;
   static TGLVert[] mv;
   static TGLEdge[] edges;
   public static int fogMin;
   public static int fogRange;
   public static int fogColor;
   public static int viewportX;
   public static int viewportY;
   public static int viewportWidth;
   public static int viewportHeight;
   static int viewportClampX1;
   static int viewportClampY1;
   static int viewportClampX2;
   static int viewportClampY2;
   public static int viewportX2;
   public static int viewportY2;
   static int viewportXScale;
   static int viewportXBias;
   static int viewportYScale;
   static int viewportYBias;
   static int viewportZScale;
   static int viewportZBias;
   static int viewX;
   static int viewY;
   static int viewZ;
   static int c_backFacedPolys;
   static int c_frontFacedPolys;
   static int c_totalQuad;
   static int c_clippedQuad;
   static int c_unclippedQuad;
   static int c_rejectedQuad;

   static boolean startup(int var0, int var1) {
      mvp = new int[16];
      mvp2D = new int[16];
      view = new int[16];
      view2D = new int[16];
      projection = new int[16];
      cv = new TGLVert[20];

      int var2;
      for(var2 = 0; var2 < cv.length; ++var2) {
         cv[var2] = new TGLVert();
      }

      for(var2 = 0; var2 < mv.length; ++var2) {
         mv[var2] = new TGLVert();
      }

      scratchPalette[0] = new int[256];

      for(var2 = 1; var2 < 16; ++var2) {
         scratchPalette[var2] = scratchPalette[0];
      }

      clippingVert1 = new TGLVert[6];
      clippingVert2 = new TGLVert[6];

      for(var2 = 0; var2 < clippingVert1.length; ++var2) {
         clippingVert1[var2] = new TGLVert();
         clippingVert2[var2] = new TGLVert();
      }

      screenWidth = var0;
      screenHeight = var1;
      columnScale = new int[screenWidth];
      pixels = new int[screenWidth * screenHeight];
      resetViewPort();
      setView(0, 0, 0, 0, 0, 0, 250, 250);
      return true;
   }

   public static final int[] getFogPalette(int var0) {
      var0 = (8388607 / (var0 >> 16) - fogMin << 4) / fogRange;
      var0 = (var0 & ~var0 >> 31) - 15;
      var0 = (var0 & var0 >> 31) + 15;
      return paletteBase[var0];
   }

   static void clearColorBuffer(int var0) {
      int[] var1 = pixels;
      var0 |= -16777216;
      int var2 = viewportX + viewportY * screenWidth;

      int var3;
      for(var3 = 0; var3 < viewportWidth; ++var3) {
         var1[var2 + var3] = var0;
      }

      var3 = var2;

      for(int var4 = 1; var4 < viewportHeight; ++var4) {
         var3 += screenWidth;
         System.arraycopy(var1, var2, var1, var3, viewportWidth);
      }

   }

   static void buildViewMatrix(int var0, int var1, int var2, int var3, int var4, int var5, int[] var6) {
      int var7 = Render.sinTable[var3 + 512 & 1023] >> 2;
      int var8 = Render.sinTable[var3 + 256 & 1023] >> 2;
      int var9 = Render.sinTable[var4 + 512 & 1023] >> 2;
      int var10 = Render.sinTable[var4 + 256 & 1023] >> 2;
      int var11 = Render.sinTable[var5 & 1023] >> 2;
      int var12 = Render.sinTable[var5 + 256 & 1023] >> 2;
      int var13 = var11 * var9 >> 14;
      int var14 = var12 * var9 >> 14;
      var6[0] = var13 * var8 + var12 * -var7 >> 14;
      var6[4] = var13 * var7 + var12 * var8 >> 14;
      var6[8] = var11 * var10 >> 14;
      var6[1] = -(var14 * var8 + -var11 * -var7) >> 14;
      var6[5] = -(var14 * var7 + -var11 * var8) >> 14;
      var6[9] = -(var12 * var10) >> 14;
      var6[2] = -(var10 * var8) >> 14;
      var6[6] = -(var10 * var7) >> 14;
      var6[10] = -(-var9);

      for(int var15 = 0; var15 < 3; ++var15) {
         var6[12 + var15] = -(var0 * var6[0 + var15] + var1 * var6[4 + var15] + var2 * var6[8 + var15]) >> 14;
      }

      var6[3] = 0;
      var6[7] = 0;
      var6[11] = 0;
      var6[15] = 16384;
   }

   static void buildProjectionMatrix(int var0, int var1, int[] var2) {
      int var4 = var1 >> 1;
      int var3 = (var0 << 14) / var1;
      int var5 = Render.sinTable[var4 & 1023] >> 2;
      int var6 = Render.sinTable[var4 + 256 & 1023] >> 2;
      var2[0] = (var6 << 14) / (var3 * var5 >> 14);
      var2[4] = 0;
      var2[8] = 0;
      var2[12] = 0;
      var2[1] = 0;
      var2[5] = (var6 << 14) / var5;
      var2[9] = 0;
      var2[13] = 0;
      var2[2] = 0;
      var2[6] = 0;
      var2[10] = -16384;
      var2[14] = -2 * NEAR_CLIP;
      var2[3] = 0;
      var2[7] = 0;
      var2[11] = -16384;
      var2[15] = 0;
   }

   static void multMatrix(int[] var0, int[] var1, int[] var2) {
      for(int var3 = 0; var3 < 4; ++var3) {
         for(int var4 = 0; var4 < 4; ++var4) {
            var2[var3 * 4 + var4] = var0[var3 * 4 + 0] * var1[0 + var4] + var0[var3 * 4 + 1] * var1[4 + var4] + var0[var3 * 4 + 2] * var1[8 + var4] + var0[var3 * 4 + 3] * var1[12 + var4] >> 14;
         }
      }

   }

   private static void _setViewport(int var0, int var1, int var2, int var3) {
      viewportX = var0;
      viewportY = var1;
      viewportWidth = var2;
      viewportHeight = var3;
      viewportX2 = var0 + var2;
      viewportY2 = var1 + var3;
      viewportClampX1 = viewportX << 3;
      viewportClampY1 = viewportY << 3;
      viewportClampX2 = (viewportX2 << 3) + 8 - 1;
      viewportClampY2 = (viewportY2 << 3) + 8 - 1;
      viewportXScale = var2 << 2;
      viewportYScale = var3 << 2;
      viewportXBias = (var0 + var2 / 2 << 3) - 4;
      viewportYBias = (var1 + var3 / 2 << 3) - 4;
      viewportZScale = 32768;
      viewportZBias = 32768;
   }

   static void setViewport(int var0, int var1, int var2, int var3) {
      if (viewportX != var0 || viewportY != var1 || viewportWidth != var2 || viewportHeight != var3) {
         _setViewport(0, 0, screenWidth, screenHeight);
         clearColorBuffer(0);
         _setViewport(var0 + 1, var1 + 1, var2 - 2, var3 - 2);
         Canvas.repaintFlags &= -65;
      }
   }

   public static void resetViewPort() {
      setViewport(0, 0, screenWidth, screenHeight);
   }

   static void setView(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      viewX = var0;
      viewY = var1;
      viewZ = var2;
      var3 &= 1023;
      var4 &= 1023;
      buildViewMatrix(var0, var1, var2, var3, var4, var5, view);
      buildViewMatrix(var0, var1, 0, var3, 0, 0, view2D);
      buildProjectionMatrix(var6, var7, projection);
      multMatrix(view, projection, mvp);
      if (var4 > 512) {
         var4 -= 1024;
      }

      buildProjectionMatrix(var6 + Math.abs(var4), var7, projection);
      multMatrix(view2D, projection, mvp2D);
   }

   static void viewMtxMove(TGLVert var0, int var1, int var2, int var3) {
      if (var1 != 0) {
         var1 = -var1;
         var0.x += view[2] * var1 >> 14;
         var0.y += view[6] * var1 >> 14;
         var0.z += view[10] * var1 >> 14;
      }

      if (var2 != 0) {
         var0.x += view[0] * var2 >> 14;
         var0.y += view[4] * var2 >> 14;
         var0.z += view[8] * var2 >> 14;
      }

      if (var3 != 0) {
         var3 = -var3;
         var0.x += view[1] * var3 >> 14;
         var0.y += view[5] * var3 >> 14;
         var0.z += view[9] * var3 >> 14;
      }

   }

   static void drawModelVerts(TGLVert[] var0, int var1) {
      if ((Render.renderMode & 1) != 0) {
         if (faceCull != 0) {
            TGLVert var2 = var0[0];
            TGLVert var3 = var0[1];
            TGLVert var4 = var0[2];
            int var5 = var3.x - var2.x;
            int var6 = var3.y - var2.y;
            int var7 = var3.z - var2.z;
            int var8 = var4.x - var2.x;
            int var9 = var4.y - var2.y;
            int var10 = var4.z - var2.z;
            int var11 = var6 * var10 - var7 * var9 >> 14;
            int var12 = var7 * var8 - var5 * var10 >> 14;
            int var13 = var5 * var9 - var6 * var8 >> 14;
            int var14 = (viewX - var2.x) * var11 + (viewY - var2.y) * var12 + (viewZ - var2.z) * var13;
            if (faceCull == 2 && var14 < 0 || faceCull == 1 && var14 > 0) {
               ++c_backFacedPolys;
               return;
            }
         }

         ++c_frontFacedPolys;
         TGLVert[] var15 = transform3DVerts(var0, var1);
         if ((Render.renderMode & 2) != 0) {
            if (var1 == 4) {
               ClipQuad(var15[0], var15[1], var15[2], var15[3]);
            } else {
               ClipPolygon(0, var1);
            }

         }
      }
   }

   static TGLVert[] transform3DVerts(TGLVert[] var0, int var1) {
      int[] var2 = mvp;

      for(int var6 = 0; var6 < var1; ++var6) {
         TGLVert var7 = var0[var6];
         TGLVert var8 = cv[var6];
         int var3 = var7.x;
         int var4 = var7.y;
         int var5 = var7.z;
         var8.x = (var3 * var2[0] + var4 * var2[4] + var5 * var2[8] >> 14) + var2[12];
         var8.y = (var3 * var2[1] + var4 * var2[5] + var5 * var2[9] >> 14) + var2[13];
         var8.z = (var3 * var2[2] + var4 * var2[6] + var5 * var2[10] >> 14) + var2[14];
         var8.w = (var3 * var2[3] + var4 * var2[7] + var5 * var2[11] >> 14) + var2[15];
         var8.s = var7.s;
         var8.t = var7.t;
      }

      return cv;
   }

   static TGLVert[] transform2DVerts(TGLVert[] var0, int var1) {
      int[] var2 = mvp2D;

      for(int var5 = 0; var5 < var1; ++var5) {
         TGLVert var6 = var0[var5];
         TGLVert var7 = cv[var5];
         int var3 = var6.x;
         int var4 = var6.y;
         var7.x = (var3 * var2[0] + var4 * var2[4] >> 14) + var2[12];
         var7.z = (var3 * var2[2] + var4 * var2[6] >> 14) + var2[14];
         var7.w = (var3 * var2[3] + var4 * var2[7] >> 14) + var2[15];
         var7.s = var6.s;
         var7.t = var6.t;
      }

      return cv;
   }

   static void ClipQuad(TGLVert var0, TGLVert var1, TGLVert var2, TGLVert var3) {
      ++c_totalQuad;

      for(int var8 = 0; var8 < 5; ++var8) {
         int var4;
         int var5;
         int var6;
         int var7;
         switch(var8) {
         case 0:
         default:
            var4 = var0.w + var0.x;
            var5 = var1.w + var1.x;
            var6 = var2.w + var2.x;
            var7 = var3.w + var3.x;
            break;
         case 1:
            var4 = var0.w - var0.x;
            var5 = var1.w - var1.x;
            var6 = var2.w - var2.x;
            var7 = var3.w - var3.x;
            break;
         case 2:
            var4 = var0.w + var0.y;
            var5 = var1.w + var1.y;
            var6 = var2.w + var2.y;
            var7 = var3.w + var3.y;
            break;
         case 3:
            var4 = var0.w - var0.y;
            var5 = var1.w - var1.y;
            var6 = var2.w - var2.y;
            var7 = var3.w - var3.y;
            break;
         case 4:
            var4 = var0.w + var0.z;
            var5 = var1.w + var1.z;
            var6 = var2.w + var2.z;
            var7 = var3.w + var3.z;
            break;
         case 5:
            var4 = var0.w - var0.z;
            var5 = var1.w - var1.z;
            var6 = var2.w - var2.z;
            var7 = var3.w - var3.z;
         }

         int var9 = (var4 < 0 ? 1 : 0) | (var5 < 0 ? 2 : 0) | (var6 < 0 ? 4 : 0) | (var7 < 0 ? 8 : 0);
         if (var9 != 0) {
            if (var9 == 15) {
               ++c_rejectedQuad;
               return;
            }

            ++c_clippedQuad;
            ClipPolygon(var8, 4);
            return;
         }
      }

      ++c_unclippedQuad;
      RasterizeConvexPolygon(4);
   }

   static void ClipPolygon(int var0, int var1) {
      while(var0 < 5) {
         int var2;
         label111:
         switch(var0) {
         case 0:
         default:
            var2 = 0;

            while(true) {
               if (var2 >= var1) {
                  break label111;
               }

               cv[var2].clipDist = cv[var2].w + cv[var2].x;
               ++var2;
            }
         case 1:
            var2 = 0;

            while(true) {
               if (var2 >= var1) {
                  break label111;
               }

               cv[var2].clipDist = cv[var2].w - cv[var2].x;
               ++var2;
            }
         case 2:
            var2 = 0;

            while(true) {
               if (var2 >= var1) {
                  break label111;
               }

               cv[var2].clipDist = cv[var2].w + cv[var2].y;
               ++var2;
            }
         case 3:
            var2 = 0;

            while(true) {
               if (var2 >= var1) {
                  break label111;
               }

               cv[var2].clipDist = cv[var2].w - cv[var2].y;
               ++var2;
            }
         case 4:
            var2 = 0;

            while(true) {
               if (var2 >= var1) {
                  break label111;
               }

               cv[var2].clipDist = cv[var2].w + cv[var2].z;
               ++var2;
            }
         case 5:
            for(var2 = 0; var2 < var1; ++var2) {
               cv[var2].clipDist = cv[var2].w - cv[var2].z;
            }
         }

         for(var2 = 0; var2 < var1; ++var2) {
            int var3 = var2 + 1;
            if (var3 == var1) {
               var3 = 0;
            }

            if (cv[var2].clipDist < 0 != cv[var3].clipDist < 0) {
               int var4 = var1 - 1 - var2;
               TGLVert var5;
               if (var4 > 0) {
                  var5 = cv[var1];
                  System.arraycopy(cv, var2 + 1, cv, var2 + 2, var4);
                  cv[var2 + 1] = var5;
                  ++var3;
               }

               var5 = cv[var2 + 1];
               var5.clipDist = 0;
               TGLVert var6;
               TGLVert var7;
               if (cv[var2].clipDist < 0) {
                  var6 = cv[var3];
                  var7 = cv[var2];
               } else {
                  var6 = cv[var2];
                  var7 = cv[var3];
               }

               int var8 = (var6.clipDist << 16) / (var6.clipDist - var7.clipDist);
               var5.x = var6.x + ((var7.x - var6.x) * var8 >> 16);
               var5.y = var6.y + ((var7.y - var6.y) * var8 >> 16);
               var5.z = var6.z + ((var7.z - var6.z) * var8 >> 16);
               var5.w = var6.w + ((var7.w - var6.w) * var8 >> 16);
               var5.s = var6.s + ((var7.s - var6.s) * var8 >> 16);
               var5.t = var6.t + ((var7.t - var6.t) * var8 >> 16);
               ++var1;
               ++var2;
            }
         }

         for(var2 = 0; var2 < var1; ++var2) {
            TGLVert var9 = cv[var2];
            if (var9.clipDist < 0) {
               System.arraycopy(cv, var2 + 1, cv, var2, var1 - 1 - var2);
               --var2;
               --var1;
               cv[var1] = var9;
            }
         }

         if (var1 < 3) {
            return;
         }

         ++var0;
      }

      RasterizeConvexPolygon(var1);
   }

   static boolean clipLine(TGLVert[] var0) {
      TGLVert var4 = var0[0];
      TGLVert var5 = var0[1];

      for(int var6 = 0; var6 < 3; ++var6) {
         int var1;
         int var2;
         switch(var6) {
         case 0:
         default:
            var1 = var4.w + var4.x;
            var2 = var5.w + var5.x;
            break;
         case 1:
            var1 = var4.w - var4.x;
            var2 = var5.w - var5.x;
            break;
         case 2:
            var1 = var4.w + var4.z;
            var2 = var5.w + var5.z;
         }

         int var7 = (var1 < 0 ? 1 : 0) | (var2 < 0 ? 2 : 0);
         if (var7 != 0) {
            if (var7 == 3) {
               return false;
            }

            int var3;
            switch(var7) {
            case 1:
               var3 = (var2 << 14) / (var2 - var1);
               var4.x = var5.x + ((var4.x - var5.x) * var3 >> 14);
               var4.y = var5.y + ((var4.y - var5.y) * var3 >> 14);
               var4.z = var5.z + ((var4.z - var5.z) * var3 >> 14);
               var4.w = var5.w + ((var4.w - var5.w) * var3 >> 14);
               var4.s = var5.s + ((var4.s - var5.s) * var3 >> 14);
               var4.t = var5.t + ((var4.t - var5.t) * var3 >> 14);
               break;
            case 2:
               var3 = (var1 << 14) / (var1 - var2);
               var5.x = var4.x + ((var5.x - var4.x) * var3 >> 14);
               var5.y = var4.y + ((var5.y - var4.y) * var3 >> 14);
               var5.z = var4.z + ((var5.z - var4.z) * var3 >> 14);
               var5.w = var4.w + ((var5.w - var4.w) * var3 >> 14);
               var5.s = var4.s + ((var5.s - var4.s) * var3 >> 14);
               var5.t = var4.t + ((var5.t - var4.t) * var3 >> 14);
            }
         }
      }

      return true;
   }

   static void projectVerts(TGLVert[] var0, int var1) {
      for(int var2 = 0; var2 < var1; ++var2) {
         TGLVert var3 = var0[var2];
         var3.x = viewportXBias + var3.x * viewportXScale / var3.w;
         var3.y = viewportYBias + var3.y * viewportYScale / var3.w;
         var3.z = 8388607 / var3.w;
         var3.s *= var3.z;
         var3.t *= var3.z;
      }

   }

   static void RasterizeConvexPolygon(int var0) {
      if ((Render.renderMode & 4) != 0) {
         int var1;
         for(var1 = 0; var1 < var0; ++var1) {
            TGLVert var2 = cv[var1];
            var2.x = viewportXBias + var2.x * viewportXScale / var2.w;
            var2.y = viewportYBias + var2.y * viewportYScale / var2.w;
            if (Render.isSkyMap) {
               swapXY = false;
               var2.s = Render.skyMapX + (var2.x * 128 >> 8);
               var2.t = Render.skyMapY + (var2.y * 128 >> 8);
               var2.w = 1024;
            }

            var2.z = 8388607 / var2.w;
            var2.s *= var2.z;
            var2.t *= var2.z;
         }

         ++Span.countDrawn;
         if ((Render.renderMode & 8) != 0) {
            int var28;
            if (swapXY) {
               for(var1 = 0; var1 < var0; ++var1) {
                  var28 = cv[var1].x;
                  cv[var1].x = cv[var1].y;
                  cv[var1].y = var28;
               }
            }

            var1 = cv[0].y;
            var28 = 0;

            int var3;
            for(var3 = 1; var3 < var0; ++var3) {
               if (cv[var3].y < var1) {
                  var1 = cv[var3].y;
                  var28 = var3;
               }
            }

            var3 = var28;
            TGLVert var4 = cv[var28];
            TGLVert var5 = cv[var28];
            int var6 = var1 + 7 >> 3;
            TGLEdge var7 = edges[0];
            TGLEdge var8 = edges[1];
            var7.stopY = var8.stopY = var6;
            int var9;
            if (swapXY) {
               var9 = screenWidth;
            } else {
               var9 = 1;
            }

            int var11 = var28;

            while(true) {
               TGLVert var12;
               if (var6 == var7.stopY) {
                  if (var28 == var3 && var28 != var11) {
                     break;
                  }

                  if (var28 == 0) {
                     var28 = var0 - 1;
                  } else {
                     --var28;
                  }

                  var12 = var4;
                  var4 = cv[var28];
                  var7.setFromVerts(var12, var4);
               }

               if (var6 == var8.stopY) {
                  if (var28 == var3) {
                     break;
                  }

                  ++var3;
                  if (var3 == var0) {
                     var3 = 0;
                  }

                  var12 = var5;
                  var5 = cv[var3];
                  var8.setFromVerts(var12, var5);
               }

               int var29 = var7.stopY < var8.stopY ? var7.stopY : var8.stopY;
               if (var6 > var29) {
                  return;
               }

               while(var6 != var29) {
                  TGLEdge var13;
                  TGLEdge var14;
                  if (var8.fracX < var7.fracX) {
                     var13 = var8;
                     var14 = var7;
                  } else {
                     var13 = var7;
                     var14 = var8;
                  }

                  int var15 = var13.fracX + 524287 >> 19;
                  int var16 = var14.fracX + 524287 >> 19;
                  if (var16 > var15) {
                     int var10;
                     if (swapXY) {
                        var10 = var15 * screenWidth + var6;
                     } else {
                        var10 = var6 * screenWidth + var15;
                     }

                     int var17 = var14.fracX - var13.fracX >> 16;
                     int var18 = var13.fracZ >> 16;
                     int var19 = var13.fracS / var18 << 16;
                     int var20 = var13.fracT / var18 << 16;
                     int var21 = var14.fracZ >> 16;
                     int var22 = var14.fracS / var21 << 16;
                     int var23 = var14.fracT / var21 << 16;
                     if (var17 == 0) {
                        spanPalette = getFogPalette(Render.isSkyMap ? 268435456 : var14.fracZ);
                        span.span(var10, var22, var23, var14.fracZ, var9, 0, 0, 0, var16 - var15);
                     } else {
                        int var24 = (var15 << 3) - (var13.fracX >> 16);
                        int var25 = (var14.fracZ - var13.fracZ) / var17;
                        int var26 = (var22 - var19) / var17;
                        int var27 = (var23 - var20) / var17;
                        spanPalette = getFogPalette(Render.isSkyMap ? 268435456 : var13.fracZ);
                        span.span(var10, var19 + var26 * var24, var20 + var27 * var24, var13.fracZ + var25 * var24, var9, var26 << 3, var27 << 3, var25 << 3, var16 - var15);
                     }
                  }

                  var8.fracX += var8.stepX;
                  var8.fracZ += var8.stepZ;
                  var8.fracS += var8.stepS;
                  var8.fracT += var8.stepT;
                  var7.fracX += var7.stepX;
                  var7.fracZ += var7.stepZ;
                  var7.fracS += var7.stepS;
                  var7.fracT += var7.stepT;
                  ++var6;
               }
            }

         }
      }
   }

   static boolean clippedLineVisCheck(TGLVert var0, TGLVert var1, boolean var2) {
      int var3 = var0.x + 7 >> 3;
      int var4 = var1.x + 7 >> 3;
      int[] var5 = columnScale;
      if (var3 < 0) {
         var3 = 0;
      }

      if (var4 > var5.length) {
         var4 = var5.length;
      }

      if (var4 <= var3) {
         return false;
      } else if (var2) {
         int var6 = var0.z;
         int var7 = (var1.z - var0.z) / (var4 - var3);
         int var8 = (var3 << 3) - var0.x;

         for(var6 += var7 * var8 >> 3; var3 < var4; var6 += var7) {
            int var9 = 8388607 / var6;
            if (var9 < var5[var3]) {
               return true;
            }

            ++var3;
         }

         return false;
      } else {
         while(var3 < var4) {
            if (var5[var3] == Integer.MAX_VALUE) {
               return true;
            }

            ++var3;
         }

         return false;
      }
   }

   static boolean occludeClippedLine(TGLVert var0, TGLVert var1) {
      int var2 = var0.x + 7 >> 3;
      int var3 = var1.x + 7 >> 3;
      int[] var4 = columnScale;
      if (var2 < 0) {
         var2 = 0;
      }

      if (var3 > var4.length) {
         var3 = var4.length;
      }

      if (var3 <= var2) {
         return false;
      } else {
         int var5 = var0.z;
         int var6 = (var1.z - var0.z) / (var3 - var2);
         int var7 = (var2 << 3) - var0.x;
         var5 += var6 * var7 >> 3;

         boolean var8;
         for(var8 = false; var2 < var3; var5 += var6) {
            int var9 = 8388607 / var5;
            if (var9 < var4[var2]) {
               var4[var2] = var9;
               var8 = true;
            }

            ++var2;
         }

         return var8;
      }
   }

   static void drawClippedSpriteLine(TGLVert var0, TGLVert var1, TGLVert var2, int var3, boolean var4) {
      boolean var16 = false;
      int var17 = 0;
      int var20 = (var0.y - var2.y << 12) / 64 >> 3;
      int var6 = var2.y >> 3;
      int var8 = 16777216 / var20;
      if (0 != (var3 & 262144)) {
         var8 = -var8;
      }

      byte[] var21 = textureBase;
      int var22 = (var21[var21.length - 1] & 255) << 8 | var21[var21.length - 2] & 255;
      int var14 = var21.length - var22 - 2;
      int var18 = imageBounds[0];
      int var19 = imageBounds[1];
      int var15 = var14 + (var19 - var18 + 1 >> 1);
      int var27 = 0;
      int var10 = var0.x + 7 >> 3;
      int var11 = var1.x + 7 >> 3;
      if (var10 < viewportX) {
         var10 = viewportX;
      }

      if (var11 > viewportX2) {
         var11 = viewportX2;
      }

      if (var11 - var10 > 0) {
         int var23 = (var1.s - var0.s) / (var11 - var10);

         for(int var24 = var0.s; var10 < var11; ++var10) {
            int var9 = var24 / var0.z * 64 / 64 >> 4;
            if (0 != (var3 & 131072)) {
               var9 = 63 - var9;
            }

            if (var9 >= var18 && var9 < var19) {
               var9 -= var18;

               while(true) {
                  int var12;
                  if (var17 >= var9) {
                     while(var17 > var9) {
                        --var17;

                        for(var12 = var21[var14 + (var17 >> 1)] >> ((var17 & 1) << 2) & 15; var12-- > 0; var15 -= 2) {
                           var27 -= var21[var15 - 1];
                        }
                     }

                     var12 = var21[var14 + (var17 >> 1)] >> ((var17 & 1) << 2) & 15;

                     while(var12-- > 0) {
                        int var7 = var21[var15++];
                        byte var5 = var21[var15++];
                        int var13 = var27 << 12;
                        var27 += var5;
                        if (0 != (var3 & 262144)) {
                           var7 = 64 - (var7 + var5);
                           var13 += (var5 << 12) - 1;
                        }

                        var7 = var6 + (var7 * var20 >> 12);
                        int var26 = var20 * var5 >> 12;
                        if (var7 < viewportY) {
                           int var25 = viewportY - var7;
                           var26 -= var25;
                           var13 += var25 * var8;
                           var7 = viewportY;
                        }

                        if (var7 + var26 > viewportY2) {
                           var26 = viewportY2 - var7;
                        }

                        if (var26 > 0) {
                           span.span(var10 + var7 * screenWidth, var13, screenWidth, var8, var26);
                        }
                     }

                     ++var17;
                     break;
                  }

                  for(var12 = var21[var14 + (var17 >> 1)] >> ((var17 & 1) << 2) & 15; var12-- > 0; var15 += 2) {
                     var27 += var21[var15 + 1];
                  }

                  ++var17;
               }
            }

            var24 += var23;
         }

      }
   }

   static {
      CULL_EXTRA = NEAR_CLIP + 16;
      imageBounds = new int[4];
      scratchPalette = new int[16][];
      sWidth = 64;
      sShift = 20;
      sMask = 63;
      tHeight = 64;
      tShift = 14;
      tMask = 4032;
      mv = new TGLVert[18];
      edges = new TGLEdge[]{new TGLEdge(), new TGLEdge()};
      fogMin = 32752;
      fogRange = 1;
      fogColor = 0;
   }
}
