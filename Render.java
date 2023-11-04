/* Decompiler 1324ms, total 1800ms, lines 3523 */
import java.io.IOException;
import java.io.InputStream;

final class Render {
   public static int touchMe = 1;
   public static final int TEXELS_PER_FILE = 32768;
   public static final int RENDER_NORMAL = 0;
   public static final int RENDER_BLEND25 = 1;
   public static final int RENDER_BLEND50 = 2;
   public static final int RENDER_ADD = 3;
   public static final int RENDER_ADD75 = 4;
   public static final int RENDER_ADD50 = 5;
   public static final int RENDER_ADD25 = 6;
   public static final int RENDER_SUB = 7;
   public static final int RENDER_PERF = 8;
   public static final int RENDER_NONE = 9;
   public static final int RENDER_MAX = 10;
   public static final int CHAT_ZOOM_SCALE = 32768;
   public static final int CHAT_ZOOM_ZOFS = 10;
   public static final int RENDER_FLAG_GREYSHIFT = 4;
   public static final int RENDER_FLAG_WHITESHIFT = 8;
   public static final int RENDER_FLAG_BLUESHIFT = 32;
   public static final int RENDER_FLAG_GREENSHIFT = 64;
   public static final int RENDER_FLAG_REDSHIFT = 128;
   public static final int RENDER_FLAG_BRIGHTREDSHIFT = 256;
   public static final int VIEW_NUDGE = 9;
   public static final int SPRITE_Z_NUDGE = 84;
   public static final int MAP_SIZE = 32;
   public static final int MAPTILE_SIZE = 64;
   public static final int TILE_MASK = 63;
   public static final int MAPTILE_MIDDLE = 32;
   public static final int MAP_MAXWORLDVALUE = 2047;
   public static final int SPRITE_SIZE = 64;
   public static final int TILE_SIZE = 64;
   public static final int FLAT_SIZE = 64;
   public static final int MAX_CUSTOM_SPRITES = 48;
   public static final int MAX_DROP_SPRITES = 16;
   public static int screenWidth;
   public static int screenHeight;
   public static final int S_NUMFIELDS = 10;
   public static final int S_INDEX_X = 0;
   public static final int S_INDEX_Y = 1;
   public static final int S_INDEX_Z = 2;
   public static final int S_INDEX_RENDERMODE = 3;
   public static final int S_INDEX_NODE = 4;
   public static final int S_INDEX_NODENEXT = 5;
   public static final int S_INDEX_VIEWNEXT = 6;
   public static final int S_INDEX_ENT = 7;
   public static final int S_INDEX_SCALEFACTOR = 8;
   public static int S_X;
   public static int S_Y;
   public static int S_Z;
   public static int S_RENDERMODE;
   public static int S_NODE;
   public static int S_NODENEXT;
   public static int S_VIEWNEXT;
   public static int S_ENT;
   public static int S_SCALEFACTOR;
   public static final int SINFO_NUMFIELDS = 2;
   public static int SINFO_SORTZ;
   public static short[] mapSprites;
   public static int[] mapSpriteInfo;
   public static int numMapSprites;
   public static int numSprites;
   public static int numNormalSprites;
   public static int numZSprites;
   public static final int MAX_WALL_TEXTURES_MAP = 16;
   static final int MAX_VISIBLE_NODES = 256;
   static final int INVALID_NODE = -1;
   static final short[] nodeIdxs = new short[256];
   public static int numVisibleNodes;
   static int numNodes;
   static byte[] nodeNormalIdxs;
   static short[] nodeOffsets;
   static short[] nodeChildOffset1;
   static short[] nodeChildOffset2;
   static byte[] nodeBoundXs;
   static byte[] nodeBoundYs;
   static short[] nodeSprites;
   static int numLeafNodes;
   static short[] nodeVertOffset;
   static short[] nodePolyOffset;
   static int numPolys;
   static byte[] polyTex;
   static byte[] polyFlags;
   static int numVerts;
   static byte[] polyXs;
   static byte[] polyYs;
   static byte[] polyZs;
   static byte[] polyUs;
   static byte[] polyVs;
   static int numLines;
   static byte[] lineFlags;
   static byte[] lineXs;
   static byte[] lineYs;
   static int numNormals;
   static short[] normals;
   static byte[] heightMap;
   public static int[] tileEvents;
   public static int numTileEvents;
   public static int lastTileEvent;
   public static final int MAPEVENTSIZE = 2;
   public static int[] staticFuncs = new int[12];
   public static byte[] mapByteCode;
   public static int mapByteCodeSize;
   public static byte[] mapFlags = new byte[1024];
   public static int mapNameField;
   private static int mapNameID;
   public static int mapSpawnIndex;
   public static int mapSpawnDir;
   public static int mapCompileDate;
   public static byte mapFlagsBitmask;
   public static int[] customSprites = new int[48];
   public static int[] dropSprites = new int[16];
   public static int firstDropSprite;
   public static int mapMemoryUsage;
   public static int texelMemoryUsage;
   public static int paletteMemoryUsage;
   public static int[] sinTable = null;
   public static boolean skipCull;
   public static boolean skipBSP;
   public static boolean skipLines;
   public static boolean skipFlats;
   public static boolean skipSprites;
   public static boolean skipDecals;
   public static boolean skip2DStretch;
   public static boolean skipViewNudge;
   public static final int RENDER_OFF = 0;
   public static final int RENDER_TRANSFORM = 1;
   public static final int RENDER_CLIP = 2;
   public static final int RENDER_PROJECT = 4;
   public static final int RENDER_SPAN = 8;
   public static final int RENDER_RASTERIZE_SPAN = 16;
   public static final int RENDER_RASTERIZE_DEBUG = 32;
   public static final int RENDER_DEFAULT = 31;
   public static final int RENDER_DEBUG = 63;
   public static int renderMode = 31;
   public static int lineCount;
   public static int lineRasterCount;
   public static int nodeCount;
   public static int leafCount;
   public static int spriteCount;
   public static int spriteRasterCount;
   public static int viewX;
   public static int viewY;
   public static int viewZ;
   public static int viewAngle;
   public static int viewSin;
   public static int viewCos;
   public static int viewRightStepX;
   public static int viewRightStepY;
   private static short viewSprites;
   public static short viewNodes;
   public static int currentFrameTime;
   public static int frameTime;
   public static int spriteTime;
   public static int lineTime;
   public static int bspTime;
   public static int clearColorBuffer;
   public static int dclTime;
   public static int preFloorTime;
   public static int postFloorTime;
   public static int stringMem = 0;
   public static int nodeMem = 0;
   public static int spriteMem = 0;
   public static int lineMem = 0;
   public static int eventMem = 0;
   private static int fadeTime;
   private static int fadeDuration;
   private static int fadeFlags;
   static final int FADE_FLAG_NONE = 0;
   static final int FADE_FLAG_FADEOUT = 1;
   static final int FADE_FLAG_FADEIN = 2;
   static final int FADE_FLAG_CHANGEMAP = 4;
   static final int FADE_FLAG_SHOWSTATS = 8;
   static final int FADE_FLAG_DENYSKIP = 16;
   static final int FADE_FLAG_EPILOGUE = 32;
   static final int FADE_SPECIAL_FLAG_MASK = -29;
   static final int CHANGEMAP_FADE_TIME = 1000;
   public static int rockViewTime = 0;
   public static int rockViewDur = 0;
   public static int rockViewX = 0;
   public static int rockViewY = 0;
   public static int rockViewZ = 0;
   public static final int ROCKTIMEDAMAGE = 200;
   public static final int ROCKTIMEDODGE = 800;
   public static final int ROCKDISTCOMBAT = 6;
   public static boolean disableRenderActivate = false;
   public static boolean chatZoom = false;
   public static boolean shotsFired = false;
   static final int MEDIA_FLAG_REFERENCE = 32768;
   static final int MEDIA_PALETTE_REGISTERED = 16384;
   static final int MEDIA_TEXELS_REGISTERED = 16384;
   static final int MEDIA_MAX_MAPPINGS = 512;
   static final int MEDIA_MAX_IMAGES = 1024;
   static final int MEDIA_MAX_IMAGES_MASK = 1023;
   static SpanTransparent spanTrans = new SpanTransparent();
   static SpanTexture spanTexture = new SpanTexture();
   static Span spanPerf = new Span();
   static byte[] skyMapTexels;
   static int[][] skyMapPalette;
   static boolean isSkyMap = false;
   static int skyMapX = 0;
   static int skyMapY = 0;
   public static final int SKY_MAP_WIDTH = 128;
   public static final int SKY_MAP_HEIGHT = 128;
   public static final int SKY_MAP_SMASK = 127;
   public static final int SKY_MAP_TMASK = 16256;
   public static final int SKY_MAP_VIRTUAL_WIDTH = 256;
   public static final int SKY_MAP_VIRTUAL_HEIGHT = 256;
   public static final int SKY_MAP_HORIZONTAL_REPEATS = 2;
   public static final int SKY_MAP_SHIFT = 8;
   public static final int SKY_MAP_STEP = 128;
   static short[] mediaMappings;
   static short[] mediaPalColors;
   static short[] mediaTexelSizes;
   static byte[] mediaDimensions;
   static byte[] mediaBounds;
   static byte[][] mediaTexels;
   static int[][][] mediaPalettes;
   public static final int FOG_SHIFT = 8;
   public static final int FOG_ONE = 256;
   private static int fogTableColor;
   private static int fogTableFrac;
   private static int[] fogTableBase;
   private static int[] fogTableDest;
   static final int MAP_HEADER_SIZE = 46;
   static final int MAX_SPLIT_SPRITES = 8;
   static final int SPLIT_SPRITE_BOUNDS = 8;
   static final int[] splitSprites = new int[8];
   static int numSplitSprites = 0;
   public static int[] traceLine = new int[4];
   static final int ANIM_IDLE_TIME = 8192;
   static final int ANIM_IDLE_MASK = 8191;
   static final int ANIM_IDLE_SWITCH_TIME = 256;
   static final int ANIM_IDLE_DOWN_TIME = 7936;
   private static int baseFogMin = 0;
   private static int baseFogRange = 0;
   private static int destFogMin = 0;
   private static int destFogRange = 0;
   private static int fogLerpStart = 0;
   private static int fogLerpTime = 0;
   private static final int ADDITIVE_SHIFT = 488;
   static int[] temp = new int[3];
   static int[][] imageFrameBounds = new int[3][4];
   public static int baseDizzy = 0;
   public static int destDizzy = 0;
   private static int dizzyTime = 0;
   private static final int MAX_DIZZY = 30;
   static final int ZOOMVAL = 2;
   public static final int LATENCY_ADJUST = 50;
   static int bltTime;

   public static final boolean startup() {
      sinTable = App.TBL_RENDER_SINETABLE;
      skipCull = false;
      skipBSP = false;
      skipLines = false;
      skipSprites = false;
      skipViewNudge = false;
      numMapSprites = 0;
      mapSprites = null;
      mapSpriteInfo = null;
      lastTileEvent = -1;
      numTileEvents = 0;
      tileEvents = null;
      mapByteCodeSize = 0;
      mapByteCode = null;
      currentFrameTime = 0;
      screenWidth = Canvas.viewRect[2];
      screenHeight = Canvas.viewRect[3];
      if ((screenHeight & 1) != 0) {
         --screenHeight;
      }

      return true;
   }

   public static final void shutdown() {
      unloadMap();
   }

   public static final int getNextEventIndex() {
      if (lastTileEvent == -1) {
         return -1;
      } else {
         int var0 = (lastTileEvent & -65536) >> 16;
         int var1 = lastTileEvent & '\uffff';
         boolean var2 = false;
         int[] var3 = tileEvents;
         ++var1;
         if (var1 < numTileEvents) {
            int var5 = var1 * 2;
            int var4 = var3[var5];
            if ((var4 & 1023) == var0) {
               lastTileEvent = var0 << 16 | var1;
               return var5;
            }
         }

         lastTileEvent = -1;
         return -1;
      }
   }

   public static final int findEventIndex(int var0) {
      int[] var1 = tileEvents;

      for(int var3 = 0; var3 < numTileEvents; ++var3) {
         int var2 = var3 * 2;
         int var4 = var1[var2];
         if ((var4 & 1023) == var0) {
            lastTileEvent = var0 << 16 | var3;
            return var2;
         }
      }

      lastTileEvent = -1;
      return -1;
   }

   public static final void unloadMap() {
      startFogLerp(32752, 32752, 0);
      mediaMappings = null;
      mediaDimensions = null;
      mediaPalColors = null;
      mediaTexelSizes = null;
      mediaTexels = (byte[][])null;
      mediaPalettes = (int[][][])null;
      normals = null;
      nodeNormalIdxs = null;
      nodeOffsets = null;
      nodeChildOffset1 = null;
      nodeChildOffset2 = null;
      nodeSprites = null;
      nodeBoundXs = null;
      nodeBoundYs = null;
      nodeVertOffset = null;
      nodePolyOffset = null;
      polyTex = null;
      polyFlags = null;
      polyXs = null;
      polyYs = null;
      polyZs = null;
      polyUs = null;
      polyVs = null;
      lineFlags = null;
      lineXs = null;
      lineYs = null;
      heightMap = null;
      numMapSprites = 0;
      mapSprites = null;
      mapSpriteInfo = null;
      numTileEvents = 0;
      mapByteCodeSize = 0;
      Game.mapSecretsFound = 0;
      tileEvents = null;
      mapByteCode = null;
      skyMapTexels = null;
      skyMapPalette = (int[][])null;

      int var0;
      for(var0 = 0; var0 < 12; ++var0) {
         staticFuncs[var0] = -1;
      }

      if (Canvas.loadMapStringID != -1) {
         Text.unloadText(Canvas.loadMapStringID);
      }

      Canvas.loadMapStringID = -1;

      for(var0 = 0; var0 < customSprites.length; ++var0) {
         customSprites[var0] = -1;
      }

      for(var0 = 0; var0 < dropSprites.length; ++var0) {
         dropSprites[var0] = -1;
      }

      System.gc();
   }

   private static final int upSamplePixel(int var0) {
      int var1 = (var0 >> 0 & 31) << 3;
      int var2 = (var0 >> 5 & 31) << 3;
      int var3 = (var0 >> 10 & 31) << 3;
      return var3 << 16 | var2 << 8 | var1;
   }

   public static final void RegisterMedia(int var0) {
      short[] var1 = mediaTexelSizes;
      short[] var2 = mediaPalColors;
      short var3 = mediaMappings[var0];
      short var4 = mediaMappings[var0 + 1];

      for(int var5 = var3; var5 < var4; ++var5) {
         int var6 = var5;
         if ((var2[var5] & '耀') != 0) {
            var6 = var2[var5] & 1023;
         }

         var2[var6] = (short)(var2[var6] | 16384);
         var6 = var5;
         if ((var1[var5] & '耀') != 0) {
            var6 = var1[var5] & 1023;
         }

         var1[var6] = (short)(var1[var6] | 16384);
      }

   }

   public static final void FinalizeMedia() throws IOException {
      short[] var0 = mediaTexelSizes;
      short[] var1 = mediaPalColors;
      int[][][] var2 = mediaPalettes;
      byte[][] var3 = mediaTexels;
      boolean var5 = false;
      Canvas.updateLoadingBar(false);
      texelMemoryUsage = 0;
      paletteMemoryUsage = 0;
      int var6 = 0;
      int var7 = 0;

      int var8;
      boolean var9;
      boolean var10;
      int var11;
      for(var8 = 0; var8 < 1024; ++var8) {
         var9 = (var0[var8] & 16384) != 0;
         var10 = (var1[var8] & 16384) != 0;
         if (var9) {
            var11 = (var0[var8] & 16383) + 1;
            texelMemoryUsage += var11;
            var3[var7++] = new byte[var11];
         }

         if (var10) {
            var11 = var1[var8] & 16383;
            paletteMemoryUsage += 4 * var11;
            var2[var6][0] = new int[var11];
            ++var6;
         }
      }

      Canvas.updateLoadingBar(false);
      var6 = 0;
      int var18 = 0;
      InputStream var4 = App.getResourceAsStream("/newPalettes.bin");
      App.checkPeakMemory("Loading Palettes");

      int var12;
      for(var8 = 0; var8 < 1024; ++var8) {
         var9 = (var1[var8] & 16384) != 0;
         var10 = (var1[var8] & '耀') != 0;
         var11 = var1[var8] & 16383;
         if (var9 && !var10) {
            Resource.read(var4, var11 * 2);

            for(var12 = 0; var12 < var11; ++var12) {
               var2[var6][0][var12] = upSamplePixel(Resource.shiftUShort());
            }

            short var21 = (short)(var8 | '耀');

            for(int var13 = var8 + 1; var13 < 1024; ++var13) {
               if (var1[var13] == var21) {
                  var1[var13] = (short)('쀀' | var6);
               }
            }

            var1[var8] = (short)(16384 | var6);
            ++var6;
            var18 += 2 * var11;
         } else if (!var10) {
            Resource.bufSkip(var4, var11 * 2, true);
            var18 += 2 * var11;
         }

         if ((var8 & 30) == 30) {
            Canvas.updateLoadingBar(false);
         }
      }

      Canvas.updateLoadingBar(true);
      var4.close();
      var4 = null;
      System.gc();
      var7 = 0;
      var5 = false;
      var8 = -1;
      int var19 = 0;
      int var20 = 0;
      var11 = 0;
      Canvas.updateLoadingBar(false);
      App.checkPeakMemory("Loading Texels");

      for(var12 = 0; var12 < 1024; ++var12) {
         boolean var22 = (var0[var12] & 16384) != 0;
         boolean var14 = (var0[var12] & '耀') != 0;
         int var15 = (var0[var12] & 16383) + 1;
         if (var22 && !var14) {
            if (var11 != var8) {
               if (var4 != null) {
                  var4.close();
                  var4 = null;
                  System.gc();
               }

               String var16 = "/tex0";
               if (var11 >= 10) {
                  var16 = "/tex";
               }

               var4 = App.getResourceAsStream(var16 + var11 + ".bin");
               var8 = var11;
               var19 = 0;
               Canvas.updateLoadingBar(false);
            }

            if (var19 != var20) {
               Resource.bufSkip(var4, var20 - var19, true);
            }

            Resource.readByteArray(var4, var3[var7], 0, var15);
            short var23 = (short)(var12 | '耀');

            for(int var17 = var12 + 1; var17 < 1024; ++var17) {
               if (var0[var17] == var23) {
                  var0[var17] = (short)('쀀' | var7);
               }
            }

            var0[var12] = (short)(16384 | var7);
            ++var7;
            var19 = var20 += var15;
         } else if (!var14) {
            var20 += var15;
         }

         if (var20 > 32768) {
            ++var11;
            var20 = 0;
         }

         if ((var12 & 15) == 15) {
            Canvas.updateLoadingBar(false);
         }
      }

      Canvas.updateLoadingBar(false);
      if (var4 != null) {
         var4.close();
         var4 = null;
         System.gc();
      }

      Canvas.updateLoadingBar(false);
   }

   public static final boolean beginLoadMap(int var0) throws IOException {
      mapNameID = var0;
      Canvas.loadMapStringID = (short)(6 + (mapNameID - 1));
      Text.loadText(Canvas.loadMapStringID);

      for(int var1 = 0; var1 < mapFlags.length; ++var1) {
         mapFlags[var1] = 0;
      }

      mapNameField = 5120 | Game.levelNames[mapNameID - 1];
      mediaMappings = new short[512];
      mediaDimensions = new byte[1024];
      mediaBounds = new byte[4096];
      mediaPalColors = new short[1024];
      mediaTexelSizes = new short[1024];
      mediaTexels = new byte[448][];
      mediaPalettes = new int[128][16][];
      Canvas.updateLoadingBar(false);
      InputStream var16 = App.getResourceAsStream("/newMappings.bin");
      Resource.readShortArray(var16, mediaMappings, 0, 512);
      Resource.readByteArray(var16, mediaDimensions, 0, 1024);
      Resource.readByteArray(var16, mediaBounds, 0, 4096);
      Resource.readShortArray(var16, mediaPalColors, 0, 1024);
      Resource.readShortArray(var16, mediaTexelSizes, 0, 1024);
      var16.close();
      var16 = null;
      System.gc();
      Canvas.updateLoadingBar(false);
      var16 = App.getResourceAsStream("/map0" + (var0 - 1) + ".bin");
      Resource.read(var16, 46);
      short var4 = Resource.shiftUByte();
      if (var4 != 3) {
         App.Error(68);
         return false;
      } else {
         mapCompileDate = Resource.shiftInt();
         mapSpawnIndex = Resource.shiftUShort();
         mapSpawnDir = Resource.shiftUByte();
         mapFlagsBitmask = Resource.shiftByte();
         Game.totalSecrets = Resource.shiftByte();
         Game.totalLoot = Resource.shiftUByte();
         numNodes = Resource.shiftUShort();
         numLeafNodes = Resource.shiftUShort();
         numLines = Resource.shiftUShort();
         numNormals = Resource.shiftUShort();
         numPolys = Resource.shiftUShort();
         numVerts = Resource.shiftUShort();
         numNormalSprites = Resource.shiftUShort();
         numZSprites = Resource.shiftShort();
         numMapSprites = numNormalSprites + numZSprites;
         numSprites = numMapSprites + 48 + 16;
         numTileEvents = Resource.shiftShort();
         mapByteCodeSize = Resource.shiftShort();
         Game.totalMayaCameras = Resource.shiftByte();
         Game.totalMayaCameraKeys = Resource.shiftShort();
         Canvas.updateLoadingBar(false);
         short var5 = 0;

         int var6;
         for(var6 = 0; var6 < 6; ++var6) {
            Game.ofsMayaTween[var6] = var5;
            short var7 = Resource.shiftShort();
            if (var7 != -1) {
               var5 += var7;
            }
         }

         Game.totalMayaTweens = var5;
         Resource.readMarker(var16, -559038737);
         Resource.read(var16, 2);
         var6 = Resource.shiftUShort();
         Resource.read(var16, var6 * 2);

         int var17;
         for(var17 = 0; var17 < var6; ++var17) {
            RegisterMedia(Resource.shiftUShort());
         }

         Resource.readMarker(var16, -559038737);
         var16.close();
         var16 = null;
         System.gc();
         FinalizeMedia();
         nodeNormalIdxs = new byte[numNodes];
         nodeOffsets = new short[numNodes];
         nodeChildOffset1 = new short[numNodes];
         nodeChildOffset2 = new short[numNodes];
         nodeSprites = new short[numNodes];
         nodeBoundXs = new byte[numNodes * 2];
         nodeBoundYs = new byte[numNodes * 2];
         nodeVertOffset = new short[numLeafNodes];
         nodePolyOffset = new short[numLeafNodes];
         polyTex = new byte[numPolys];
         polyFlags = new byte[numPolys];
         polyXs = new byte[numVerts];
         polyYs = new byte[numVerts];
         polyZs = new byte[numVerts];
         polyUs = new byte[numVerts];
         polyVs = new byte[numVerts];
         lineFlags = new byte[(numLines + 1) / 2];
         lineXs = new byte[numLines * 2];
         lineYs = new byte[numLines * 2];
         normals = new short[numNormals * 3];
         heightMap = new byte[1024];

         for(var17 = 0; var17 < nodeSprites.length; ++var17) {
            nodeSprites[var17] = -1;
         }

         mapSprites = new short[numSprites * 10];
         mapSpriteInfo = new int[numSprites * 2];
         S_X = numSprites * 0;
         S_Y = numSprites * 1;
         S_Z = numSprites * 2;
         S_RENDERMODE = numSprites * 3;
         S_NODE = numSprites * 4;
         S_NODENEXT = numSprites * 5;
         S_VIEWNEXT = numSprites * 6;
         S_ENT = numSprites * 7;
         S_SCALEFACTOR = numSprites * 8;
         SINFO_SORTZ = numSprites;
         tileEvents = new int[numTileEvents * 2];
         mapByteCode = new byte[mapByteCodeSize];
         Game.mayaCameras = new MayaCamera[Game.totalMayaCameras];
         Game.mayaCameraKeys = new short[Game.totalMayaCameraKeys * 7];
         Game.mayaCameraTweens = new byte[Game.totalMayaTweens];
         Game.mayaTweenIndices = new short[Game.totalMayaCameraKeys * 6];

         for(var17 = 0; var17 < Game.totalMayaCameras; ++var17) {
            Game.mayaCameras[var17] = new MayaCamera();
         }

         Game.setKeyOffsets();
         App.checkPeakMemory("Allocated memory for the map");
         var16 = App.getResourceAsStream("/map0" + (var0 - 1) + ".bin");
         Canvas.updateLoadingBar(false);
         App.checkPeakMemory("Reading in final map data");
         Resource.read(var16, 46);
         Resource.readMarker(var16, -559038737);
         Resource.read(var16, var6 * 2 + 2);
         Resource.readMarker(var16, -559038737);
         Resource.readShortArray(var16, normals, 0, normals.length);
         Resource.readMarker(var16);
         Resource.readShortArray(var16, nodeOffsets, 0, numNodes);
         Resource.readMarker(var16);
         Resource.readByteArray(var16, nodeNormalIdxs, 0, nodeNormalIdxs.length);
         Resource.readMarker(var16);
         Resource.readShortArray(var16, nodeChildOffset1, 0, numNodes);
         Resource.readShortArray(var16, nodeChildOffset2, 0, numNodes);
         Resource.readMarker(var16);
         Resource.readByteArray(var16, nodeBoundXs, 0, numNodes * 2);
         Resource.readByteArray(var16, nodeBoundYs, 0, numNodes * 2);
         Resource.readMarker(var16);
         Canvas.updateLoadingBar(false);
         Resource.readShortArray(var16, nodeVertOffset, 0, numLeafNodes);
         Resource.readShortArray(var16, nodePolyOffset, 0, numLeafNodes);
         Resource.readMarker(var16);
         Resource.readByteArray(var16, polyTex, 0, numPolys);
         Resource.readByteArray(var16, polyFlags, 0, numPolys);
         Resource.readByteArray(var16, polyXs, 0, numVerts);
         Resource.readByteArray(var16, polyYs, 0, numVerts);
         Resource.readByteArray(var16, polyZs, 0, numVerts);
         Resource.readByteArray(var16, polyUs, 0, numVerts);
         Resource.readByteArray(var16, polyVs, 0, numVerts);
         Resource.readMarker(var16);
         Resource.readByteArray(var16, lineFlags, 0, lineFlags.length);
         Resource.readByteArray(var16, lineXs, 0, lineXs.length);
         Resource.readByteArray(var16, lineYs, 0, lineYs.length);
         Resource.readMarker(var16);
         Resource.readByteArray(var16, heightMap, 0, heightMap.length);
         Resource.readMarker(var16);
         Canvas.updateLoadingBar(false);
         Resource.readCoordArray(var16, mapSprites, S_X, numMapSprites);
         Resource.readCoordArray(var16, mapSprites, S_Y, numMapSprites);
         Canvas.updateLoadingBar(false);

         for(var17 = 0; var17 < numMapSprites; ++var17) {
            mapSprites[S_NODE + var17] = -1;
            mapSprites[S_NODENEXT + var17] = -1;
            mapSprites[S_VIEWNEXT + var17] = -1;
            mapSprites[S_ENT + var17] = -1;
            mapSprites[S_SCALEFACTOR + var17] = 64;
            mapSprites[S_Z + var17] = 32;
         }

         var17 = 0;
         boolean var8 = false;
         int var9 = numMapSprites;
         int[] var10 = mapSpriteInfo;

         int var18;
         while(var9 > 0) {
            var18 = 10240 > var9 ? var9 : 10240;
            var9 -= var18;
            Resource.read(var16, var18);

            while(true) {
               --var18;
               if (var18 < 0) {
                  break;
               }

               var10[var17++] = Resource.shiftUByte();
            }
         }

         Resource.readMarker(var16);
         Canvas.updateLoadingBar(false);
         var17 = 0;
         var8 = false;
         var9 = numMapSprites;

         int var10001;
         while(var9 > 0) {
            var18 = 5120 > var9 ? var9 : 5120;
            var9 -= var18;
            Resource.read(var16, var18 * 2);

            while(true) {
               --var18;
               if (var18 < 0) {
                  break;
               }

               var10001 = var17++;
               var10[var10001] |= (Resource.shiftUShort() & '\uffff') << 16;
            }
         }

         Resource.readMarker(var16);
         Resource.readUByteArray(var16, mapSprites, S_Z + numNormalSprites, numZSprites);
         Resource.readMarker(var16);
         var17 = numNormalSprites;
         var8 = false;
         var9 = numZSprites;

         while(var9 > 0) {
            var18 = 10240 > var9 ? var9 : 10240;
            var9 -= var18;
            Resource.read(var16, var18);

            while(true) {
               --var18;
               if (var18 < 0) {
                  break;
               }

               var10001 = var17++;
               var10[var10001] |= Resource.shiftUByte() << 8;
            }
         }

         Canvas.updateLoadingBar(false);
         Resource.readMarker(var16);
         Resource.readUShortArray(var16, staticFuncs, 0, 12);
         Resource.readMarker(var16);
         Resource.readIntArray(var16, tileEvents, 0, numTileEvents * 2);

         int var11;
         byte[] var10000;
         for(var11 = 0; var11 < numTileEvents; ++var11) {
            var10000 = mapFlags;
            var10001 = tileEvents[var11 << 1] & 1023;
            var10000[var10001] = (byte)(var10000[var10001] | 64);
         }

         Resource.readMarker(var16);
         Resource.readByteArray(var16, mapByteCode, 0, mapByteCodeSize);
         Resource.readMarker(var16);
         Canvas.updateLoadingBar(false);
         Game.loadMayaCameras(var16);
         Resource.readMarker(var16);
         var11 = 512;
         int var12 = 0;
         Resource.read(var16, var11);

         while(true) {
            --var11;
            if (var11 < 0) {
               Resource.readMarker(var16);
               var16.close();
               var16 = null;
               System.gc();
               Canvas.updateLoadingBar(false);
               postProcessSprites();
               Canvas.updateLoadingBar(false);
               skyMapPalette = new int[16][Resource.getNumTableInts(15)];
               skyMapTexels = new byte[Resource.getNumTableBytes(16)];
               Resource.beginTableLoading();
               Resource.loadIntTable(skyMapPalette[0], 15);
               Resource.loadByteTable(skyMapTexels, 16);
               Resource.finishTableLoading();
               Canvas.updateLoadingBar(false);

               for(int var19 = 0; var19 < mediaPalettes.length; ++var19) {
                  if (mediaPalettes[var19][0] != null) {
                     int var14 = mediaPalettes[var19][0].length;

                     for(int var15 = 1; var15 < 16; ++var15) {
                        paletteMemoryUsage += 4 * var14;
                        mediaPalettes[var19][var15] = new int[var14];
                     }
                  }
               }

               Canvas.changeMapStarted = false;
               destDizzy = 0;
               baseDizzy = 0;
               return true;
            }

            short var13 = Resource.shiftUByte();
            var10000 = mapFlags;
            var10001 = var12++;
            var10000[var10001] |= (byte)(var13 & 15);
            var10000 = mapFlags;
            var10001 = var12++;
            var10000[var10001] |= (byte)(var13 >> 4 & 15);
         }
      }
   }

   public static final void draw2DSprite(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      TGLVert var8 = TinyGL.cv[0];
      TGLVert var9 = TinyGL.cv[1];
      TGLVert var10 = TinyGL.cv[2];
      setupTexture(var0, var1, var5, var6);
      int var11 = 64 * var7 / 65536;
      var8.x = var2 << 3;
      var8.y = var3 + var11 << 3;
      var8.z = 8192;
      var8.s = 0;
      var9.x = var2 + var11 << 3;
      var9.y = var8.y;
      var9.z = var8.z;
      var9.s = 8388608;
      var10.x = var2 << 3;
      var10.y = var3 << 3;
      var10.z = var8.z;
      var10.s = 0;
      int var12;
      if (var8.x < TinyGL.viewportClampX1) {
         var12 = (var9.s - var8.s) / (var9.x - var8.x);
         var8.s += (TinyGL.viewportClampX1 - var8.x) * var12;
         var8.x = TinyGL.viewportClampX1;
      }

      if (var9.x > TinyGL.viewportClampX2) {
         var12 = (var9.s - var8.s) / (var9.x - var8.x);
         var9.s -= (var9.x - TinyGL.viewportClampX2) * var12;
         var9.x = TinyGL.viewportClampX2;
      }

      setupPalette(TinyGL.getFogPalette(1073741824), var5, var6);
      TinyGL.drawClippedSpriteLine(var8, var9, var10, var4, false);
   }

   public static final void renderSprite(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      renderSprite(var0, var1, var2, var3, var4, var5, var6, var7, var8, -1);
   }

   public static final void renderSprite(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      int var10 = var7;
      if ((var5 & Integer.MIN_VALUE) != 0) {
         var7 = 65536;
      }

      if (var7 != 0) {
         int[] var11 = TinyGL.imageBounds;
         setupTexture(var3, var4, var6, var8);
         int var12 = var11[1] - TinyGL.imageBounds[0];
         int var13 = var11[3] - TinyGL.imageBounds[2];
         int var14 = (var11[0] << 10) / TinyGL.sWidth;
         int var15 = (var12 << 10) / TinyGL.sWidth;
         int var16 = (TinyGL.tHeight - var11[3] << 10) / TinyGL.tHeight;
         int var17 = (var13 << 10) / TinyGL.tHeight;
         if ((var5 & 269484032) != 0) {
            TinyGL.faceCull = 0;
         } else {
            TinyGL.faceCull = 2;
         }

         int var20;
         int var23;
         int var24;
         int var25;
         int var26;
         int var32;
         if ((var5 & 788529152) == 0) {
            var2 -= 512;
            int var19;
            if (var3 == 170) {
               var19 = (TinyGL.tHeight << 4) / 3;
               var20 = TinyGL.sWidth * sinTable[viewAngle & 1023] >> 13;
               var32 = TinyGL.sWidth * sinTable[viewAngle + 256 & 1023] >> 13;
               int var33 = App.time / 2 + ((var0 >> 6) + (var1 >> 6)) * 1337;

               for(var23 = 0; var23 < 3; ++var23) {
                  for(var24 = 0; var24 < 4; ++var24) {
                     var25 = (var24 & 2) >> 1;
                     var26 = var24 & 1 ^ var25 ^ 1;
                     TGLVert var27 = TinyGL.mv[var24];
                     var27.x = (var0 << 4) + var20 * (var26 * 2 - 1);
                     var27.y = (var1 << 4) + var32 * (var26 * 2 - 1);
                     var27.z = var19 * (var23 + var25) + var2;
                     var27.s = var26 * 64 << 4;
                     var27.t = var19 * (var23 + var25);
                     if ((var23 | var25) != 0) {
                        var27.x += sinTable[var33 + var27.z & 1023] >> 12;
                        var27.y += sinTable[var33 + var27.z + 256 & 1023] >> 12;
                        var27.z += 32 + (2 * sinTable[var33 / 2 & 1023] >> 12);
                     }
                  }

                  TinyGL.swapXY = true;
                  TinyGL.drawModelVerts(TinyGL.mv, 4);
               }

               return;
            }

            byte var21 = 0;
            byte var22 = 4;
            if (0 == (var5 & 4194304) && TinyGL.textureBase.length != TinyGL.sWidth * TinyGL.tHeight) {
               var20 = 514 * var7 / 65536;
               var19 = 1026 * var7 / 65536;
               var16 = 0;
               var14 = 0;
               var17 = 1024;
               var15 = 1024;
               var22 = 3;
               var21 = 2;
            } else {
               var20 = ((var12 >> 1 << 4) + 7) * var7 / 65536;
               var19 = ((var13 << 4) + 7) * var7 / 65536;
            }

            var32 = var21 + 9;
            var0 -= var32 * viewCos >> 16;
            var1 += var32 * viewSin >> 16;

            for(var23 = 0; var23 < var22; ++var23) {
               var24 = (var23 & 2) >> 1;
               var25 = var23 & 1 ^ var24 ^ 1;
               TGLVert var18 = TinyGL.mv[var23];
               var18.x = var0 << 4;
               var18.y = var1 << 4;
               var18.z = var2 - 84;
               var18.s = var14 + var25 * var15;
               var18.t = var16 + var24 * var17;
               TinyGL.viewMtxMove(var18, 0, (var25 * 2 - 1) * var20, var24 * var19);
            }

            TGLVert[] var35 = TinyGL.transform3DVerts(TinyGL.mv, var22);
            if (0 == (var5 & 4194304) && TinyGL.textureBase.length != TinyGL.sWidth * TinyGL.tHeight) {
               TGLVert var36 = var35[0];
               if (var36.w + var36.z < 0) {
                  return;
               }

               if (TinyGL.clipLine(var35)) {
                  TinyGL.projectVerts(var35, var22);
                  if (var9 >= 0) {
                     TinyGL.spanPalette = TinyGL.paletteBase[var9 % 16];
                     setupPalette(TinyGL.spanPalette, var6, var8);
                  } else {
                     setupPalette(TinyGL.getFogPalette(var35[0].z << 16), var6, var8);
                  }

                  TinyGL.drawClippedSpriteLine(var35[1], var35[0], var35[2], var5, true);
               }
            } else {
               TinyGL.ClipQuad(var35[0], var35[1], var35[2], var35[3]);
            }
         } else {
            byte var29 = 0;
            if ((var5 & 67108864) != 0) {
               var29 = 0;
            } else if ((var5 & 16777216) != 0) {
               var29 = 2;
            } else if ((var5 & 134217728) != 0) {
               var29 = 4;
            } else if ((var5 & 33554432) != 0) {
               var29 = 6;
            }

            if (TinyGL.textureBase.length == TinyGL.sWidth * TinyGL.tHeight) {
               if (TinyGL.tHeight == 128 && TinyGL.sWidth == 128) {
                  var20 = 64;
                  var32 = 32;
               } else {
                  var20 = var13;
                  var32 = var12 >> 1;
               }

               var20 = var20 * var7 / 65536;
               var32 = var32 * var7 / 65536;
               if ((var5 & 536870912) == 0) {
                  var2 += TinyGL.tHeight - TinyGL.imageBounds[3] << 4;
                  var2 -= 32 * var7 / 65536 << 4;
               } else {
                  var2 -= 512;
               }

               byte[] var34 = Canvas.viewStepValues;
               var23 = (var29 + 4 & 7) << 1;
               int var30 = (var29 + 2 & 7) << 1;
               if ((var5 & 131072) == 0) {
                  var14 += var15;
                  var15 = -var15;
               }

               if ((var5 & 262144) != 0) {
                  var16 += var17;
                  var17 = -var17;
               }

               var32 <<= 4;
               var20 <<= 4;
               var0 <<= 4;
               var1 <<= 4;
               if ((var5 & Integer.MIN_VALUE) != 0 && var3 != 280) {
                  var24 = var15;
                  var32 = var10 * var32 / 65536;
                  var15 = var10 * var15 / 65536;
                  var14 += var24 - var15;
               }

               int var28;
               TGLVert var31;
               int var37;
               if (var3 == 280) {
                  var15 >>= 1;

                  for(var24 = 0; var24 < 2; ++var24) {
                     var25 = ((var24 << 1) - 1) * ((65536 - var10 >> 8) * 7 / 8);
                     var25 = var25 + (var30 << 6) & 1023;
                     var0 += (var34[var30] >> 6) * var32 * (var24 + 1);
                     var1 += (var34[var30 + 1] >> 6) * var32 * (var24 + 1);
                     var30 = var30 + 8 & 15;

                     for(var26 = 0; var26 < 4; ++var26) {
                        var37 = (var26 & 2) >> 1;
                        var28 = var26 & 1 ^ var37 ^ 1;
                        var31 = TinyGL.mv[var26];
                        var31.x = var0 + (-sinTable[var25 + 256 & 1023] * var28 * var32 >> 16);
                        var31.y = var1 + (sinTable[var25] * var28 * var32 >> 16);
                        var31.z = var2 + var37 * var20;
                        var31.s = var14 + var28 * var15;
                        var31.t = var16 + var37 * var17;
                     }

                     var14 += 2 * var15;
                     var15 = -var15;
                     TinyGL.swapXY = true;
                     TinyGL.drawModelVerts(TinyGL.mv, 4);
                  }
               } else if ((var5 & 536870912) == 0) {
                  for(var24 = 0; var24 < 4; ++var24) {
                     var25 = (var24 & 2) >> 1;
                     var26 = var24 & 1 ^ var25 ^ 1;
                     var31 = TinyGL.mv[var24];
                     var37 = (var26 * 2 - 1) * var32;
                     var31.x = var0 + (var34[var30] >> 6) * var37;
                     var31.y = var1 + (var34[var30 + 1] >> 6) * var37;
                     var31.z = var2 + var25 * var20;
                     var31.s = var14 + var26 * var15;
                     var31.t = var16 + var25 * var17;
                  }

                  TinyGL.swapXY = true;
                  TinyGL.drawModelVerts(TinyGL.mv, 4);
               } else {
                  for(var24 = 0; var24 < 4; ++var24) {
                     var25 = (var24 & 2) >> 1;
                     var26 = var24 & 1 ^ var25 ^ 1;
                     var31 = TinyGL.mv[var24];
                     var37 = (var26 * 2 - 1) * var32;
                     var28 = (var25 * 2 - 1) * var20 >> 1;
                     var31.x = var0 + (var34[var30] >> 6) * var37 + (var34[var23] >> 6) * var28;
                     var31.y = var1 + (var34[var30 + 1] >> 6) * var37 + (var34[var23 + 1] >> 6) * var28;
                     var31.z = var2;
                     var31.s = var14 + var26 * var15;
                     var31.t = var16 + var25 * var17;
                     if (var3 == 479) {
                        var31.s += App.time / 8 & 1023;
                        var31.t += App.time / 16 & 1023;
                     }
                  }

                  TinyGL.swapXY = false;
                  TinyGL.drawModelVerts(TinyGL.mv, 4);
               }
            }
         }

      }
   }

   private static final void occludeSpriteLine(int var0) {
      int var1 = mapSpriteInfo[var0];
      if ((var1 & Integer.MIN_VALUE) == 0) {
         TGLVert var2 = TinyGL.mv[0];
         TGLVert var3 = TinyGL.mv[1];
         var3.x = var2.x = mapSprites[S_X + var0] << 4;
         var3.y = var2.y = mapSprites[S_Y + var0] << 4;
         var3.z = var2.z = 0;
         byte var4 = 0;
         if ((var1 & 67108864) != 0) {
            var4 = 4;
         } else if ((var1 & 16777216) != 0) {
            var4 = 6;
         } else if ((var1 & 134217728) != 0) {
            var4 = 4;
         } else if ((var1 & 33554432) != 0) {
            var4 = 6;
         }

         int var6 = (var4 + 2 & 7) << 1;
         var2.x += Canvas.viewStepValues[var6] >> 1 << 4;
         var3.x -= Canvas.viewStepValues[var6] >> 1 << 4;
         ++var6;
         var2.y += Canvas.viewStepValues[var6] >> 1 << 4;
         var3.y -= Canvas.viewStepValues[var6] >> 1 << 4;
         TGLVert[] var5 = TinyGL.transform2DVerts(TinyGL.mv, 2);
         if (TinyGL.clipLine(var5)) {
            TinyGL.projectVerts(var5, 2);
            if (var5[0].x > var5[1].x) {
               TinyGL.occludeClippedLine(var5[1], var5[0]);
            } else {
               TinyGL.occludeClippedLine(var5[0], var5[1]);
            }
         }

      }
   }

   private static final void drawNodeLines(short var0) {
      short var1 = nodeChildOffset2[var0];
      int var2 = (var1 & 1023) + (var1 >> 10 & 63);

      for(int var7 = var1 & 1023; var7 < var2; ++var7) {
         int var3 = lineFlags[var7 >> 1] >> ((var7 & 1) << 2) & 15;
         byte[] var10000;
         if ((var3 & 7) != 0 && (var3 & 7) != 4) {
            if ((var3 & 7) == 6) {
               var10000 = lineFlags;
               var10000[var7 >> 1] = (byte)(var10000[var7 >> 1] | 8 << ((var7 & 1) << 2));
            }
         } else {
            TGLVert var4 = TinyGL.mv[0];
            TGLVert var5 = TinyGL.mv[1];
            var4.x = (lineXs[var7 << 1] & 255) << 7;
            var5.x = (lineXs[(var7 << 1) + 1] & 255) << 7;
            var4.y = (lineYs[var7 << 1] & 255) << 7;
            var5.y = (lineYs[(var7 << 1) + 1] & 255) << 7;
            var4.z = 0;
            var5.z = 0;
            TGLVert[] var6 = TinyGL.transform2DVerts(TinyGL.mv, 2);
            if (TinyGL.clipLine(var6)) {
               TinyGL.projectVerts(var6, 2);
               if (TinyGL.occludeClippedLine(var6[0], var6[1]) && Game.updateAutomap) {
                  var10000 = lineFlags;
                  var10000[var7 >> 1] = (byte)(var10000[var7 >> 1] | 8 << ((var7 & 1) << 2));
               }
            }
         }
      }

   }

   public static final boolean cullBoundingBox(int var0, int var1, boolean var2) {
      return cullBoundingBox((var0 & -64) << 4, (var1 & -64) << 4, (var0 | 63) << 4, (var1 | 63) << 4, var2);
   }

   public static final boolean cullBoundingBox(int var0, int var1, int var2, int var3, boolean var4) {
      if (skipCull) {
         return false;
      } else if (viewX >= var0 - TinyGL.CULL_EXTRA && viewX <= var2 + TinyGL.CULL_EXTRA && viewY >= var1 - TinyGL.CULL_EXTRA && viewY <= var3 + TinyGL.CULL_EXTRA) {
         return false;
      } else {
         TGLVert var5 = TinyGL.mv[0];
         TGLVert var6 = TinyGL.mv[1];
         if (viewX < var0) {
            if (viewY < var1) {
               var5.x = var2;
               var5.y = var1;
               var6.x = var0;
               var6.y = var3;
            } else if (viewY < var3) {
               var5.x = var0;
               var5.y = var1;
               var6.x = var0;
               var6.y = var3;
            } else {
               var5.x = var0;
               var5.y = var1;
               var6.x = var2;
               var6.y = var3;
            }
         } else if (viewX < var2) {
            if (viewY < var1) {
               var5.x = var2;
               var5.y = var1;
               var6.x = var0;
               var6.y = var1;
            } else {
               if (viewY < var3) {
                  return false;
               }

               var5.x = var0;
               var5.y = var3;
               var6.x = var2;
               var6.y = var3;
            }
         } else if (viewY < var1) {
            var5.x = var2;
            var5.y = var3;
            var6.x = var0;
            var6.y = var1;
         } else if (viewY < var3) {
            var5.x = var2;
            var5.y = var3;
            var6.x = var2;
            var6.y = var1;
         } else {
            var5.x = var0;
            var5.y = var3;
            var6.x = var2;
            var6.y = var1;
         }

         var5.z = 0;
         var6.z = 0;
         TGLVert[] var7 = TinyGL.transform2DVerts(TinyGL.mv, 2);
         if (TinyGL.clipLine(var7)) {
            TinyGL.projectVerts(var7, 2);
            return !TinyGL.clippedLineVisCheck(var7[0], var7[1], var4);
         } else {
            return true;
         }
      }
   }

   private static final void addSprite(short var0) {
      int var1 = mapSpriteInfo[var0];
      if ((var1 & 65536) == 0) {
         int var2 = mapSpriteInfo[var0] & 255;
         Entity var3 = null;
         if (mapSprites[S_ENT + var0] != -1) {
            var3 = Game.entities[mapSprites[S_ENT + var0]];
         }

         int var4;
         short var5;
         if ((mapSpriteInfo[var0] & 268435456) != 0) {
            var4 = Integer.MAX_VALUE;
         } else {
            var5 = mapSprites[S_X + var0];
            short var6 = mapSprites[S_Y + var0];
            int[] var7 = TinyGL.mvp;
            var4 = (var5 * var7[2] + var6 * var7[6] + mapSprites[S_Z + var0] * var7[10] >> 14) + var7[14];
            if ((mapSpriteInfo[var0] & 4194304) != 0) {
               var4 += 6;
            } else if (var2 != 241 && var2 != 237 && var2 != 246 && var2 != 245) {
               if (var2 == 141) {
                  var4 -= 16;
               } else if (0 != (mapSpriteInfo[var0] & 251658240)) {
                  var4 += 5;
               } else if (var3 != null && (var3.info & 16842752) != 0) {
                  ++var4;
               } else if (var3 != null && var3.def.eType == 2) {
                  --var4;
               } else if (var2 != 247 && var2 != 142) {
                  if ((var2 < 241 || var2 > 242) && var2 != 192 && var2 != 255 && var2 != 251) {
                     if (var2 == 138) {
                        var4 += 2;
                     } else if (var2 == 146) {
                        var4 += 2;
                     }
                  } else {
                     var4 -= 3;
                  }
               } else {
                  var4 -= 2;
               }
            } else {
               var4 = Integer.MIN_VALUE;
            }
         }

         mapSpriteInfo[SINFO_SORTZ + var0] = var4;
         if (Game.updateAutomap) {
            int[] var10000 = mapSpriteInfo;
            var10000[var0] |= 2097152;
         }

         if (viewSprites == -1) {
            mapSprites[S_VIEWNEXT + var0] = -1;
            viewSprites = var0;
         } else if (var4 >= mapSpriteInfo[SINFO_SORTZ + viewSprites]) {
            mapSprites[S_VIEWNEXT + var0] = viewSprites;
            viewSprites = var0;
         } else {
            for(var5 = viewSprites; mapSprites[S_VIEWNEXT + var5] != -1 && var4 < mapSpriteInfo[SINFO_SORTZ + mapSprites[S_VIEWNEXT + var5]]; var5 = mapSprites[S_VIEWNEXT + var5]) {
            }

            mapSprites[S_VIEWNEXT + var0] = mapSprites[S_VIEWNEXT + var5];
            mapSprites[S_VIEWNEXT + var5] = var0;
         }

      }
   }

   private static final void addSplitSprite(int var0, int var1) {
      int[] var2 = splitSprites;

      for(int var3 = var0; var3 < numVisibleNodes; ++var3) {
         short var4 = nodeIdxs[var3];
         int var5 = (nodeBoundXs[(var4 << 1) + 0] & 255) << 3;
         int var6 = (nodeBoundYs[(var4 << 1) + 0] & 255) << 3;
         int var7 = (nodeBoundXs[(var4 << 1) + 1] & 255) << 3;
         int var8 = (nodeBoundYs[(var4 << 1) + 1] & 255) << 3;
         short var9 = mapSprites[S_X + var1];
         short var10 = mapSprites[S_Y + var1];
         if (var5 < var9 + 8 && var7 > var9 - 8 && var6 < var10 + 8 && var8 > var10 - 8 && numSplitSprites < 8) {
            var2[numSplitSprites++] = var4 << 16 | var1;
            return;
         }
      }

   }

   private static final void addNodeSprites(short var0) {
      int[] var1 = splitSprites;
      if ((nodeOffsets[var0] & '\uffff') == 65535) {
         for(short var2 = nodeSprites[var0]; var2 != -1; var2 = mapSprites[S_NODENEXT + var2]) {
            addSprite(var2);
         }

         for(int var3 = 0; var3 < numSplitSprites; ++var3) {
            if ((var1[var3] & -65536) >> 16 == var0) {
               addSprite((short)(var1[var3] & '\uffff'));
               var1[var3] = -1;
            }
         }
      }

   }

   private static final int nodeClassifyPoint(int var0, int var1, int var2, int var3) {
      short[] var4 = normals;
      short[] var5 = nodeOffsets;
      int var6 = (nodeNormalIdxs[var0] & 255) * 3;
      int var7 = var1 * var4[var6] + var2 * var4[var6 + 1] + var3 * var4[var6 + 2] >> 14;
      return var7 + (var5[var0] & '\uffff');
   }

   private static final void drawNodeGeometry(short var0) {
      if ((nodeOffsets[var0] & '\uffff') == 65535) {
         short var3 = nodeChildOffset1[var0];
         int var4 = var3 >> 9 & 127;
         int var16 = var3 & 511;
         int var5 = nodePolyOffset[var16];
         int var6 = nodeVertOffset[var16];
         var4 += var5;
         byte var7 = -1;
         boolean var8 = false;
         int var9 = 0;
         int var10 = 0;

         for(int var11 = 0; var5 < var4; ++var5) {
            int var12 = polyTex[var5] & 255;
            int var13 = polyFlags[var5] & 255;
            int var14 = (var13 & 7) + 2;
            TinyGL.swapXY = (var13 & 64) != 0;
            if ((var13 & 32) != 0) {
               var12 += 257;
            }

            if (var12 == 211) {
               TinyGL.NEAR_CLIP = 200;
            } else {
               TinyGL.NEAR_CLIP = 256;
            }

            if (var7 != var12) {
               var9 = 0;
               var10 = 0;
               var11 = 0;
               byte var17 = 0;
               TinyGL.faceCull = 2;
               if (var12 != 161 && var12 != 210) {
                  if (var12 != 151 && var12 != 302 && var12 != 212) {
                     if (var12 == 479) {
                        var17 = 1;
                        TinyGL.faceCull = 0;
                     }
                  } else {
                     var17 = 7;
                  }
               } else {
                  var17 = 2;
               }

               setupTexture(var12, 0, var17, 0);
               if (var12 == 479) {
                  var9 = 2 * (sinTable[App.time / 2 & 1023] - sinTable[256]) >> 12;
                  var10 = App.time / 8 & 1023;
                  var11 = App.time / 16 & 1023;
               }
            }

            TGLVert var1;
            for(int var15 = 0; var15 < var14; ++var15) {
               var1 = TinyGL.mv[var15];
               var1.x = (polyXs[var6] & 255) << 7;
               var1.y = (polyYs[var6] & 255) << 7;
               var1.z = ((polyZs[var6] & 255) << 7) + var9;
               var1.s = (polyUs[var6] << 6) + var10;
               var1.t = (polyVs[var6] << 6) + var11;
               ++var6;
            }

            if (var14 == 2) {
               var14 = 4;
               var1 = TinyGL.mv[2];
               TinyGL.mv[2] = TinyGL.mv[1];
               TinyGL.mv[1] = var1;
               TGLVert var2 = TinyGL.mv[0];
               var1.x = var2.x;
               var1.y = var2.y;
               var1.z = var2.z;
               var1.s = var2.s;
               var1.t = var2.t;
               var1 = TinyGL.mv[3];
               var2 = TinyGL.mv[2];
               var1.x = var2.x;
               var1.y = var2.y;
               var1.z = var2.z;
               var1.s = var2.s;
               var1.t = var2.t;
               switch(var13 & 24) {
               case 0:
               default:
                  TinyGL.mv[1].x = TinyGL.mv[2].x;
                  TinyGL.mv[3].x = TinyGL.mv[0].x;
                  break;
               case 8:
                  TinyGL.mv[1].y = TinyGL.mv[2].y;
                  TinyGL.mv[3].y = TinyGL.mv[0].y;
                  break;
               case 16:
                  TinyGL.mv[1].z = TinyGL.mv[2].z;
                  TinyGL.mv[3].z = TinyGL.mv[0].z;
               }

               if ((var13 & 128) != 0) {
                  TinyGL.mv[1].s = TinyGL.mv[2].s;
                  TinyGL.mv[3].s = TinyGL.mv[0].s;
               } else {
                  TinyGL.mv[1].t = TinyGL.mv[2].t;
                  TinyGL.mv[3].t = TinyGL.mv[0].t;
               }
            }

            TinyGL.drawModelVerts(TinyGL.mv, var14);
         }

         TinyGL.span = null;
      }
   }

   private static final void walkNode(short var0) {
      int var1 = (nodeBoundXs[(var0 << 1) + 0] & 255) << 7;
      int var2 = (nodeBoundYs[(var0 << 1) + 0] & 255) << 7;
      int var3 = (nodeBoundXs[(var0 << 1) + 1] & 255) << 7;
      int var4 = (nodeBoundYs[(var0 << 1) + 1] & 255) << 7;
      if (!cullBoundingBox(var1, var2, var3, var4, true)) {
         ++nodeCount;
         int var5 = nodeOffsets[var0] & '\uffff';
         if (var5 == 65535) {
            if (numVisibleNodes < 256) {
               nodeIdxs[numVisibleNodes++] = var0;
            }

            ++leafCount;
            if (!skipLines) {
               drawNodeLines(var0);
            }

            for(short var10 = nodeSprites[var0]; var10 != -1; var10 = mapSprites[S_NODENEXT + var10]) {
               int var11 = mapSpriteInfo[var10];
               if ((var11 & 65536) == 0) {
                  int var8 = mapSpriteInfo[var10] & 255;
                  Entity var9 = null;
                  if (mapSprites[S_ENT + var10] != -1) {
                     var9 = Game.entities[mapSprites[S_ENT + var10]];
                  }

                  if (var9 != null && (var11 & 4194304) != 0) {
                     var8 += 257;
                     if (var8 < 450) {
                        occludeSpriteLine(var10);
                     }
                  }
               }
            }

         } else {
            int var6 = numVisibleNodes;
            if (nodeClassifyPoint(var0, viewX, viewY, viewZ) >= 0) {
               walkNode(nodeChildOffset1[var0]);
               walkNode(nodeChildOffset2[var0]);
            } else {
               walkNode(nodeChildOffset2[var0]);
               walkNode(nodeChildOffset1[var0]);
            }

            for(short var7 = nodeSprites[var0]; var7 != -1; var7 = mapSprites[S_NODENEXT + var7]) {
               addSplitSprite(var6, var7);
            }

         }
      }
   }

   private static final int dot(int var0, int var1, int var2, int var3) {
      return var0 * var2 + var1 * var3;
   }

   public static final int CapsuleToCircleTrace(int[] var0, int var1, int var2, int var3, int var4) {
      int var5 = var0[2] - var0[0];
      int var6 = var0[3] - var0[1];
      int var7 = var2 - var0[0];
      int var8 = var3 - var0[1];
      int var9 = var5 * var5 + var6 * var6;
      int var10 = var7 * var5 + var8 * var6;
      if (var9 == 0) {
         return 0;
      } else {
         if (var10 < 0) {
            var10 = 0;
         }

         if (var10 > var9) {
            var10 = var9;
         }

         int var11 = (int)(((long)var10 << 16) / (long)var9);
         int var12 = var2 - (var0[0] + (var5 * var11 >> 16));
         int var13 = var3 - (var0[1] + (var6 * var11 >> 16));
         int var14 = var12 * var12 + var13 * var13;
         return var14 < var1 + var4 ? (var11 >> 2) - 1 : 16384;
      }
   }

   public static final int CapsuleToLineTrace(int[] var0, int var1, int[] var2) {
      int var3 = var0[2] - var0[0];
      int var4 = var0[3] - var0[1];
      int var5 = var2[2] - var2[0];
      int var6 = var2[3] - var2[1];
      int var7 = var0[0] - var2[0];
      int var8 = var0[1] - var2[1];
      int var9 = dot(var3, var4, var3, var4);
      int var10 = dot(var3, var4, var5, var6);
      int var11 = dot(var5, var6, var5, var6);
      int var12 = dot(var3, var4, var7, var8);
      int var13 = dot(var5, var6, var7, var8);
      long var14 = (long)var9 * (long)var11 - (long)var10 * (long)var10;
      long var18 = var14;
      long var22 = var14;
      long var16;
      long var20;
      if (var14 < 0L) {
         var16 = 0L;
         var18 = 1L;
         var20 = (long)var13;
         var22 = (long)var11;
      } else {
         var16 = (long)var10 * (long)var13 - (long)var11 * (long)var12;
         var20 = (long)var9 * (long)var13 - (long)var10 * (long)var12;
         if (var16 < 0L) {
            var16 = 0L;
            var20 = (long)var13;
            var22 = (long)var11;
         } else if (var16 > var14) {
            var16 = var14;
            var20 = (long)(var13 + var10);
            var22 = (long)var11;
         }
      }

      if (var20 < 0L) {
         var20 = 0L;
         if (-var12 < 0) {
            var16 = 0L;
         } else if (-var12 > var9) {
            var16 = var18;
         } else {
            var16 = (long)(-var12);
            var18 = (long)var9;
         }
      } else if (var20 > var22) {
         var20 = var22;
         if (-var12 + var10 < 0) {
            var16 = 0L;
         } else if (-var12 + var10 > var9) {
            var16 = var18;
         } else {
            var16 = (long)(-var12 + var10);
            var18 = (long)var9;
         }
      }

      int var24;
      if (var16 == 0L) {
         var24 = 0;
      } else {
         var24 = (int)((var16 << 16) / var18);
      }

      int var25;
      if (var20 == 0L) {
         var25 = 0;
      } else {
         var25 = (int)((var20 << 16) / var22);
      }

      int var26 = (var7 << 16) + var24 * var3 - var25 * var5 >> 16;
      int var27 = (var8 << 16) + var24 * var4 - var25 * var6 >> 16;
      int var28 = dot(var26, var27, var26, var27);
      return var28 < var1 ? (var24 >> 2) - 1 : 16384;
   }

   public static final int traceWorld(int var0, int[] var1, int var2, int[] var3, int var4) {
      if (var3[0] > (nodeBoundXs[(var0 << 1) + 1] & 255) << 3) {
         return 16384;
      } else if (var3[2] < (nodeBoundXs[(var0 << 1) + 0] & 255) << 3) {
         return 16384;
      } else if (var3[1] > (nodeBoundYs[(var0 << 1) + 1] & 255) << 3) {
         return 16384;
      } else if (var3[3] < (nodeBoundYs[(var0 << 1) + 0] & 255) << 3) {
         return 16384;
      } else {
         int var5 = nodeOffsets[var0] & '\uffff';
         int var6;
         if (var5 != 65535) {
            boolean var16 = true;
            if (nodeClassifyPoint(var0, var1[0] << 4, var1[1] << 4, viewZ) >= 0) {
               var6 = traceWorld(nodeChildOffset1[var0], var1, var2, var3, var4);
               return var6 == 16384 ? traceWorld(nodeChildOffset2[var0], var1, var2, var3, var4) : var6;
            } else {
               var6 = traceWorld(nodeChildOffset2[var0], var1, var2, var3, var4);
               return var6 == 16384 ? traceWorld(nodeChildOffset1[var0], var1, var2, var3, var4) : var6;
            }
         } else {
            var6 = 16384;
            short var7 = nodeChildOffset2[var0];
            int var8 = (var7 & 1023) + (var7 >> 10 & 63);
            int var17 = var7 & 1023;

            while(true) {
               int[] var9;
               int var12;
               do {
                  do {
                     do {
                        int var13;
                        int var14;
                        int var15;
                        do {
                           do {
                              int var11;
                              do {
                                 do {
                                    do {
                                       if (var17 >= var8) {
                                          return var6;
                                       }

                                       var9 = traceLine;
                                       int var10 = lineFlags[var17 >> 1] >> ((var17 & 1) << 2) & 15;
                                       var11 = var10 & 7;
                                       var9[0] = (lineXs[var17 << 1] & 255) << 3;
                                       var9[2] = (lineXs[(var17 << 1) + 1] & 255) << 3;
                                       var9[1] = (lineYs[var17 << 1] & 255) << 3;
                                       var9[3] = (lineYs[(var17 << 1) + 1] & 255) << 3;
                                       ++var17;
                                    } while(var11 == 4);
                                 } while(var11 == 6);
                              } while(var11 == 5 && (var4 & 16) == 0 && (var4 & 2048) == 0);

                              if (var11 != 7) {
                                 break;
                              }

                              var12 = var9[0] - var1[0];
                              var13 = var9[1] - var1[1];
                              var14 = var9[2] - var9[0];
                              var15 = var9[3] - var9[1];
                           } while(var12 * var15 + var13 * -var14 <= 0);
                        } while(var9[0] > var3[2] && var9[2] > var3[2]);
                     } while(var9[0] < var3[0] && var9[2] < var3[0]);
                  } while(var9[1] > var3[3] && var9[3] > var3[3]);
               } while(var9[1] < var3[1] && var9[3] < var3[1]);

               var12 = CapsuleToLineTrace(var1, var2 * var2, var9);
               if (var12 < var6) {
                  var6 = var12;
               }
            }
         }
      }
   }

   public static final void renderStreamSprite(int var0) {
      TGLVert[] var1 = TinyGL.mv;
      int var2 = mapSpriteInfo[var0];
      short var3 = mapSprites[S_ENT + var0];
      int var4 = var2 & 255;
      int var5 = (var2 & '\uff00') >> 8;
      short var6 = mapSprites[S_RENDERMODE + var0];
      int var7 = mapSprites[S_SCALEFACTOR + var0] << 10;
      short var8 = mapSprites[S_X + var0];
      short var9 = mapSprites[S_Y + var0];
      int var10 = mapSprites[S_Z + var0] << 4;
      if (var4 == 237) {
         renderSprite(var8, var9, var10, 236, App.time / 100 % 3, 0, 0, 65536, 0);
      }

      byte var11 = 0;
      short var12 = 0;
      short var13 = -432;
      short var14 = -432;
      if (var3 == 1) {
         if (var4 == 241) {
            renderSprite(var8, var9, var10 + 256, 234, App.time / 100 & 3, 0, 3, 32768, 0);
         }

         var14 = 0;
         var12 = 480;
         var11 = 64;
         var1[0].x = var1[1].x = Canvas.destX << 4;
         var1[0].y = var1[1].y = Canvas.destY << 4;
         var1[0].z = var1[1].z = Canvas.destZ << 4;
         var1[2].x = var1[3].x = var8 << 4;
         var1[2].y = var1[3].y = var9 << 4;
         var1[2].z = var1[3].z = var10;
      } else {
         int var15 = Game.entities[var3].getSprite();
         var13 = 0;
         var1[0].x = var1[1].x = mapSprites[S_X + var15] << 4;
         var1[0].y = var1[1].y = mapSprites[S_Y + var15] << 4;
         var1[0].z = var1[1].z = mapSprites[S_Z + var15] << 4;
         var1[2].x = var1[3].x = var8 << 4;
         var1[2].y = var1[3].y = var9 << 4;
         var1[2].z = var1[3].z = var10;
      }

      TinyGL.faceCull = 0;
      setupTexture(var4, var5, var6, 0);
      int[] var24 = TinyGL.imageBounds;
      int var16 = var24[1] - TinyGL.imageBounds[0];
      int var17 = var24[3] - TinyGL.imageBounds[2];
      int var18 = (var24[0] << 10) / TinyGL.sWidth;
      int var19 = (var16 << 10) / TinyGL.sWidth;
      int var20 = (TinyGL.tHeight - var24[3] << 10) / TinyGL.tHeight;
      int var21 = (var17 << 10) / TinyGL.tHeight * 3;
      int var22 = (var16 / 2 << 4) * var7 / 65536;
      int var23 = 0;
      if (var4 == 237 && var3 != 1) {
         var23 = var22;
         var22 = 0;
      }

      TinyGL.viewMtxMove(var1[0], var12, var11 - var22 / 4, var23 / 4 + var13);
      TinyGL.viewMtxMove(var1[1], var12, var11 + var22 / 4, var13);
      TinyGL.viewMtxMove(var1[2], 0, var22 * 2, var23 * 2 + var14);
      TinyGL.viewMtxMove(var1[3], 0, -var22 * 2, var14);
      var20 -= App.time * 3 & 1023;
      var1[3].s = var1[0].s = var18;
      var1[1].t = var1[0].t = var20;
      var1[2].s = var1[1].s = var18 + var19;
      var1[3].t = var1[2].t = var20 + var21;
      TinyGL.swapXY = false;
      TinyGL.drawModelVerts(TinyGL.mv, 4);
   }

   public static final void renderSpriteObject(int var0) {
      int var1 = mapSpriteInfo[var0];
      if ((var1 & 65536) == 0) {
         int var2 = var1 & 255;
         int var3 = mapSprites[S_X + var0];
         int var4 = mapSprites[S_Y + var0];
         int var5 = mapSprites[S_Z + var0] << 4;
         int var6 = (var1 & '\uff00') >> 8;
         short var7 = mapSprites[S_RENDERMODE + var0];
         int var8 = mapSprites[S_SCALEFACTOR + var0] << 10;
         int var9 = 0;
         Entity var10 = null;
         if ((mapSpriteInfo[var0] & 4194304) != 0) {
            var2 += 257;
         }

         if ((var1 & 524288) != 0) {
            var6 = (var0 + App.time / 100) % var6;
         }

         if (var2 != 241 && var2 != 237) {
            if (var2 == 130 && var3 == Canvas.viewX && Canvas.viewY == var4) {
               var3 += (Canvas.viewStepX >> 6) * 18;
               var4 += (Canvas.viewStepY >> 6) * 18;
               var5 -= 512;
               if (mapSprites[S_SCALEFACTOR + var0] != 64) {
                  var8 = 65536;
               }
            }

            EntityMonster var11;
            EntityDef var12;
            if (mapSprites[S_ENT + var0] != -1) {
               var10 = Game.entities[mapSprites[S_ENT + var0]];
               var11 = var10.monster;
               var12 = var10.def;
            } else {
               var10 = null;
               var11 = null;
               var12 = null;
            }

            int var15;
            int var16;
            if (var10 != null) {
               if (var11 != null) {
                  if (!disableRenderActivate && Game.activePropogators == 0 && Game.animatingEffects == 0 && (var10.info & 17104896) == 0 && (var11.flags & 8) == 0 && !Player.noclip && !Game.disableAI) {
                     Game.activate(var10, true, true, true, false);
                  }

                  short var13 = var11.monsterEffects;
                  byte var14 = var10.def.eSubType;
                  var15 = var6 & 240;
                  var16 = var6 & 15;
                  if (0 != (var13 & 2)) {
                     var9 |= 32;
                     var7 = 0;
                  } else if (0 != (var13 & 8)) {
                     var9 |= 128;
                     var7 = 0;
                  } else if (0 != (var13 & 1)) {
                     var9 |= 64;
                     var7 = 0;
                  }

                  if ((var9 & 4) == 0) {
                     if ((var11.flags & 4096) == 0 && (var15 == 96 || var15 == 144) && App.time > var11.frameTime) {
                        var11.frameTime = 0;
                        var6 = 0;
                     }

                     mapSpriteInfo[var0] = var1 & -65281 | var6 << 8;
                  }
               }

               if (var10.def.eType == 10 && var10.def.eSubType == 3 && var10.param != 0) {
                  if (App.time > var10.param) {
                     ++var6;
                     var10.param = App.time + 200;
                  }

                  if (var6 > 3) {
                     var10.param = 0;
                     var6 = 3;
                  }

                  mapSpriteInfo[var0] = var1 & -65281 | var6 << 8;
               }
            }

            if ((var1 & 4194304) != 0) {
               renderSprite(var3, var4, var5, var2, var6, var1, var7, var8, var9);
            } else {
               int var21;
               int var24;
               byte var25;
               byte var28;
               if (var11 != null) {
                  if (var11 != null && (0 != (var11.monsterEffects & 8) || var2 == 27 || var2 == 28)) {
                     var21 = (var0 * 1337 + App.time) / 256 & 3;
                     var24 = var8;
                     byte var27 = 10;
                     if (var2 == 28) {
                        var21 += 4;
                     }

                     if (var10.def.eType != 9 && (var10.info & 131072) != 0) {
                        if (var10.def.eSubType == 10) {
                           var24 = 16384;
                        }
                     } else {
                        var24 = 32768;
                        var27 = 0;
                     }

                     if ((var6 & 240) == 128) {
                        var24 = 32768;
                     }

                     renderSprite(var3, var4, var5 + (var27 << 4), 234, var21, 0, 3, var24, 0);
                  }

                  if (var11.goalType == 4) {
                     renderSprite(var3, var4, var5 + 160, 255, 0, var1 & -262145, 0, var8, var9);
                  }

                  if (0 != (var11.flags & 8192) && (!chatZoom || Combat.curTarget != null && Combat.curTarget.getSprite() != var0)) {
                     var25 = 10;
                     renderSprite(var3, var4, var5 + (var25 << 4), 253, 0, mapSpriteInfo[var0] & -131073, 0, var8, var9);
                  }

                  if (var12.eSubType == 10) {
                     var21 = var6 & 240;
                     var6 &= 15;
                     var28 = 0;
                     switch(var21) {
                     case 0:
                     case 16:
                     case 32:
                     case 48:
                        renderSprite(var3, var4, var5, var2, var6, var1, var7, var8, var9);
                        break;
                     case 80:
                        var28 = 2;
                     case 64:
                        if (var6 == 0) {
                           renderSprite(var3, var4, var5, var2, 8 + var28, var1, var7, var8, var9);
                        } else {
                           renderSprite(var3, var4, var5, var2, 9 + var28, var1, var7, var8, var9);
                        }
                        break;
                     case 96:
                        renderSprite(var3, var4, var5, var2, 12, var1, var7, var8, var9);
                        break;
                     case 112:
                        renderSprite(var3, var4, var5, var2, 13, var1, var7, var8, var9);
                        break;
                     case 128:
                        var5 -= 256;
                        renderSprite(var3, var4, var5, var2, 14, var1, var7, var8, var9);
                        renderSprite(var3, var4, var5, var2, 15 + var6, var1, var7, var8, var9);
                        break;
                     case 144:
                        renderSprite(var3, var4, var5, var2, 17, var1, var7, var8, var9);
                     }
                  } else if (var12.eSubType == 9) {
                     var21 = var6 & 240;
                     var6 &= 15;
                     var28 = 0;
                     switch(var21) {
                     case 16:
                     case 48:
                        var6 = 4;
                     case 0:
                     case 32:
                        var15 = (App.time + var0 * 1337) / 1024 & 1;
                        renderSprite(var3, var4, var5 + (var15 << 4), var2, var6, var1, var7, var8, var9);
                        break;
                     case 80:
                        var28 = 2;
                     case 64:
                        if (var6 == 0) {
                           renderSprite(var3, var4, var5, var2, 8 + var28, var1, var7, var8, var9);
                        } else {
                           renderSprite(var3, var4, var5, var2, 9 + var28, var1, var7, var8, var9);
                        }
                        break;
                     case 96:
                        renderSprite(var3, var4, var5, var2, 12, var1, var7, var8, var9);
                        break;
                     case 112:
                        renderSprite(var3, var4, var5, var2, 13, var1, var7, var8, var9);
                        break;
                     case 128:
                        renderSprite(var3, var4, var5, var2, var6, var1, var7, var8, var9);
                        break;
                     case 144:
                        renderSprite(var3, var4, var5, var2, 17, var1, var7, var8, var9);
                     }
                  } else if (var2 == 58) {
                     renderHarbingerAnim(var0, var6, var3, var4, var5, var1, var7, var8, var9);
                  } else {
                     renderSpriteAnim(var0, var6, var3, var4, var5, var2, var1, var7, var8, var9);
                  }

                  return;
               }

               if (var2 == 150) {
                  var1 ^= (var6 & 1) << 17;
               } else if (var2 == 136) {
                  var21 = 10 * var8 / 65536;
                  var1 ^= (var6 & 1) << 17;
                  renderSprite(var3, var4, var5 + (var21 << 4), 193, 0, var1, 5, var8, var9);
               } else if (var2 == 230) {
                  var21 = 32 * var8 / 65536;
                  renderSprite(var3, var4, var5 + (var21 << 4), 234, 4 + (App.time / 250 & 3), 0, 4, var8 * 3 >> 2, var9);
               } else {
                  if ((var2 == 127 || var2 == 123) && var6 == 2) {
                     var21 = App.time / 128 & 3;
                     var6 += (var21 & 2) >> 1;
                     renderSprite(var3, var4, var5, var2, var6, var1 ^ (var21 & 1) << 17, var7, var8, var9);
                     return;
                  }

                  if (var2 == 209) {
                     var21 = App.time / 100;
                     var24 = var1 | (var21 & 1) << 17 | (var21 & 2) << 17;
                     renderSprite(var3, var4, var5, var2, var6, var24, var7, var8, var9);
                     return;
                  }

                  if (var2 == 203) {
                     for(var24 = 0; var24 < 6; ++var24) {
                        var15 = (App.time + var24 * 1337) / 8;
                        var16 = var15 / 96;
                        var16 += var24;
                        var16 *= var16;
                        int var17 = -8 + 4 * (var16 % 5);
                        var16 += 3;
                        int var18 = -8 + 4 * (var16 % 5);
                        int var19 = var15 % 96 << 4;
                        int var20 = (var15 % 96 << 4) / 96;
                        renderSprite(var3 + var17, var4 + var18, var5 + var19, var2, var6, var1, 3, 65536 / (3 + var16 % 4), var9, var20);
                     }

                     return;
                  }

                  if (var2 == 188) {
                     var21 = 36 * var8 / 65536 << 4;
                     renderSprite(var3, var4, var5, 189, var6, var1, var7, var8, var9);
                     renderSprite(var3, var4, var5 + var21, 188, var6, var1, var7, var8, var9);
                     return;
                  }

                  if (var12 != null && var12.eType == 3) {
                     renderSpriteAnim(var0, var6, var3, var4, var5, var2, var1, var7, var8, var9);
                     if (var10.param != 0 && !chatZoom) {
                        short var22 = 160;
                        short var23 = 254;
                        if (var10.param == 2) {
                           var23 = 255;
                        }

                        renderSprite(var3, var4, var5 + var22, var23, 0, mapSpriteInfo[var0] & -131073, 0, var8, var9);
                     }

                     return;
                  }

                  if (var2 == 247) {
                     var21 = (App.time & 256) << 9;
                     renderSprite(var3, var4, var5, var2, var6, var1 & -131073 | var21, var7, var8, var9);
                     return;
                  }
               }

               renderSprite(var3, var4, var5, var2, var6, var1, var7, var8, var9);
               if (var2 == 149 && var6 == 0) {
                  var21 = 24 * var8 / 65536 << 4;
                  renderSprite(var3, var4, var5 + var21, 149, 2, var1, 5, var8, var9);
               } else if (var2 == 131 && var10 != null) {
                  var25 = 0;
                  var28 = 0;
                  if ((mapSpriteInfo[var0] & 50331648) != 0) {
                     var25 = 1;
                  } else {
                     var28 = 1;
                  }

                  byte var26;
                  if (var10.param > 0) {
                     var26 = (byte)((var0 + 2) * var3 + var4);
                     var16 = var26 >> 4;
                     renderSprite(var3 + var16 * var25, var4 + var16 * var28, var5 - 80, 122, 0, var1, var7, 13107, var9);
                  }

                  if (var10.param > 1) {
                     var26 = (byte)((var0 + 2) * var4 + var3);
                     var16 = var26 >> 4;
                     renderSprite(var3 + var16 * var25, var4 + var16 * var28, var5 + 240, 122, 0, var1, var7, 13107, var9);
                  }
               }
            }

         } else {
            renderStreamSprite(var0);
         }
      }
   }

   private static final void renderBSP() {
      nodeCount = 0;
      leafCount = 0;
      lineCount = 0;
      lineRasterCount = 0;
      spriteCount = 0;
      spriteRasterCount = 0;
      lineTime = 0;
      dclTime = 0;
      bspTime = App.getUpTimeMs();
      numVisibleNodes = 0;
      numSplitSprites = 0;
      if (!skipBSP) {
         walkNode((short)0);
      }

      bspTime = App.getUpTimeMs() - bspTime;
      Span.resetCounters();

      for(int var0 = numVisibleNodes - 1; var0 >= 0; --var0) {
         drawNodeGeometry(nodeIdxs[var0]);
         viewSprites = -1;
         addNodeSprites(nodeIdxs[var0]);
         if (!skipSprites) {
            for(short var1 = viewSprites; var1 != -1; var1 = mapSprites[S_VIEWNEXT + var1]) {
               ++spriteCount;
               renderSpriteObject(var1);
            }
         }
      }

   }

   public static final void snapFogLerp() {
      if (fogLerpTime != 0) {
         fogLerpStart = 0;
         fogLerpTime = 0;
         TinyGL.fogMin = destFogMin;
         TinyGL.fogRange = destFogRange;
      }

   }

   public static final void startFogLerp(int var0, int var1, int var2) {
      baseFogMin = TinyGL.fogMin;
      baseFogRange = TinyGL.fogRange;
      destFogMin = var0 << 4;
      destFogRange = var1 - var0 << 4;
      if (destFogRange == 0) {
         destFogRange = 1;
      }

      if (var2 != 0) {
         fogLerpStart = App.time;
         fogLerpTime = var2;
      } else {
         fogLerpStart = 0;
         fogLerpTime = 0;
         TinyGL.fogMin = destFogMin;
         TinyGL.fogRange = destFogRange;
      }

   }

   static final void buildFogTable() {
      int var0 = fogTableColor;
      int var3 = fogTableFrac;
      int[] var4 = fogTableBase;
      int[] var5 = fogTableDest;

      int var1;
      int var2;
      int var6;
      for(var6 = var4.length; var6 >= 4; var5[var6] = var2 + var0 & 16711422) {
         --var6;
         var1 = var4[var6];
         var2 = ((var1 & -16711936) >> 8) * var3 & -16711936;
         var2 |= (var1 & 16711935) * var3 >> 8 & 16711935;
         var5[var6] = var2 + var0 & 16711422;
         --var6;
         var1 = var4[var6];
         var2 = ((var1 & -16711936) >> 8) * var3 & -16711936;
         var2 |= (var1 & 16711935) * var3 >> 8 & 16711935;
         var5[var6] = var2 + var0 & 16711422;
         --var6;
         var1 = var4[var6];
         var2 = ((var1 & -16711936) >> 8) * var3 & -16711936;
         var2 |= (var1 & 16711935) * var3 >> 8 & 16711935;
         var5[var6] = var2 + var0 & 16711422;
         --var6;
         var1 = var4[var6];
         var2 = ((var1 & -16711936) >> 8) * var3 & -16711936;
         var2 |= (var1 & 16711935) * var3 >> 8 & 16711935;
      }

      while(true) {
         --var6;
         if (var6 < 0) {
            return;
         }

         var1 = var4[var6];
         var2 = ((var1 & -16711936) >> 8) * var3 & -16711936;
         var2 |= (var1 & 16711935) * var3 >> 8 & 16711935;
         var5[var6] = var2 + var0 & 16711422;
      }
   }

   static final void buildFogTable(int var0, int var1, int var2) {
      int var4 = (var2 & -16777216) >> 24 & 255;
      int var5 = mediaMappings[var0] + var1;
      if ((mediaPalColors[var5] & 16384) != 0) {
         int[][] var6 = mediaPalettes[mediaPalColors[var5] & 16383];
         fogTableBase = var6[0];

         for(int var7 = 1; var7 < 16; ++var7) {
            int var3 = (var7 << 8) / 16 * var4 >> 8;
            fogTableColor = ((var2 & -16711936) >> 8) * var3 & -16711936;
            fogTableColor |= (var2 & 16711935) * var3 >> 8 & 16711935;
            fogTableFrac = 256 - var3;
            fogTableDest = var6[var7];
            buildFogTable();
         }
      }

   }

   static final void buildFogTables(int var0) {
      TinyGL.fogColor = var0;
      if ((var0 & -16777216) == 0) {
         TinyGL.fogMin = 32752;
         TinyGL.fogRange = 1;
      } else {
         int var2 = (var0 & -16777216) >> 24 & 255;
         int[][][] var3 = mediaPalettes;

         for(int var4 = 1; var4 < 16; ++var4) {
            int var1 = (var4 << 8) / 16 * var2 >> 8;
            var0 = ((TinyGL.fogColor & -16711936) >> 8) * var1 & -16711936;
            var0 |= (TinyGL.fogColor & 16711935) * var1 >> 8 & 16711935;
            fogTableColor = var0;
            fogTableFrac = 256 - var1;

            for(int var5 = 0; var5 < var3.length; ++var5) {
               if (var3[var5][0] != null) {
                  fogTableBase = var3[var5][0];
                  fogTableDest = var3[var5][var4];
                  buildFogTable();
               }
            }

            fogTableBase = skyMapPalette[0];
            fogTableDest = skyMapPalette[var4];
            buildFogTable();
         }

         buildFogTable(203, 0, -16777216);
         var2 = var2 * 180 >> 8 & 255;
         var0 = var2 << 24 | TinyGL.fogColor & 16777215;
         buildFogTable(234, 0, var0);
         var0 = TinyGL.fogColor & 16777215;
         buildFogTable(212, 0, var0);
         buildFogTable(151, 0, var0);
         buildFogTable(15, 3, var0);
         buildFogTable(237, 0, var0);
         buildFogTable(14, 3, var0);
         buildFogTable(5, 3, var0);
      }
   }

   public static final void setupPalette(int[] var0, int var1, int var2) {
      int[] var3 = TinyGL.scratchPalette[0];
      TinyGL.spanPalette = var0;
      int var4;
      int var5;
      int var6;
      if ((var2 & 4) != 0) {
         for(var6 = 0; var6 < var0.length; ++var6) {
            var5 = var0[var6];
            var4 = ((var5 & 255) + (var5 >> 8 & 255) + (var5 >> 16 & 255)) / 3;
            var3[var6] = var4 << 16 | var4 << 8 | var4;
         }

         TinyGL.paletteBase = TinyGL.scratchPalette;
         TinyGL.spanPalette = var3;
      } else if ((var2 & 488) != 0) {
         var6 = 0;
         switch(var2 & 488) {
         case 8:
            var6 = 4210752;
            break;
         case 32:
            var6 = 64;
            break;
         case 64:
            var6 = 16384;
            break;
         case 128:
            var6 = 4194304;
            break;
         case 256:
            var6 = 8388608;
         }

         for(int var7 = 0; var7 < var0.length; ++var7) {
            var4 = (var0[var7] & 16711422) + var6;
            var5 = var4 & 16843008;
            var3[var7] = var4 ^ var5 | var5 - (var5 >> 7);
         }

         TinyGL.paletteBase = TinyGL.scratchPalette;
         TinyGL.spanPalette = var3;
      } else if (var1 != 0) {
         TinyGL.paletteBase = TinyGL.scratchPalette;
         TinyGL.spanPalette = var3;
         byte var9;
         switch(var1) {
         case 1:
            var4 = 16579836;
            var9 = 2;
            break;
         case 2:
         default:
            var4 = 16579836;
            var9 = 1;
            break;
         case 3:
            var4 = 16711422;
            var9 = 0;
            var1 = 3;
            break;
         case 4:
            for(var6 = 0; var6 < var0.length; ++var6) {
               var3[var6] = ((var0[var6] & 16579836) >> 1) + ((var0[var6] & 16316664) >> 2);
            }

            byte var8 = 3;
            TinyGL.renderMode = var8;
            return;
         case 5:
            var4 = 16579836;
            var9 = 1;
            var1 = 3;
            break;
         case 6:
            var4 = 16316664;
            var9 = 2;
            var1 = 3;
         }

         for(var6 = 0; var6 < var0.length; ++var6) {
            var3[var6] = (var0[var6] & var4) >> var9;
         }
      }

      TinyGL.renderMode = var1;
   }

   public static final int[][] getImageFrameBounds(int var0, int var1, int var2, int var3) {
      short var4 = mediaMappings[var0];
      temp[0] = var1 + var4 << 2;
      temp[1] = var2 + var4 << 2;
      temp[2] = var3 + var4 << 2;

      for(int var5 = 0; var5 < 3; ++var5) {
         imageFrameBounds[var5][0] = (mediaBounds[temp[var5] + 0] & 255) * 64 / 64 - 32;
         imageFrameBounds[var5][1] = (mediaBounds[temp[var5] + 1] & 255) * 64 / 64 - 32;
         imageFrameBounds[var5][2] = (64 - (mediaBounds[temp[var5] + 3] & 255)) * 64 / 64 - 32;
         imageFrameBounds[var5][3] = (64 - (mediaBounds[temp[var5] + 2] & 255)) * 64 / 64 - 32;
      }

      return imageFrameBounds;
   }

   public static final int[] getPalette(int var0, int var1, int var2) {
      int var3 = mediaMappings[var0] + var1;
      return mediaPalettes[mediaPalColors[var3] & 16383][0];
   }

   public static final void setupTexture(int var0, int var1, int var2, int var3) {
      short[] var6 = mediaMappings;
      short[] var7 = mediaPalColors;
      short[] var8 = mediaTexelSizes;
      int[][][] var9 = mediaPalettes;
      byte[][] var10 = mediaTexels;
      int var11 = var6[var0] + var1;
      if ((renderMode & 16) == 0) {
         var2 = 9;
      }

      if (var0 >= 257 || var0 == 175 || var0 == 197 || var0 == 162 || var0 == 158 && var1 == 0) {
         TinyGL.span = spanTexture;
      } else {
         TinyGL.span = spanTrans;
      }

      if ((renderMode & 32) != 0) {
         TinyGL.span = spanPerf;
      }

      TinyGL.renderMode = var2;
      int var4;
      int var5;
      if (var0 == 301) {
         TinyGL.textureBase = skyMapTexels;
         TinyGL.paletteBase = skyMapPalette;
         var4 = 7;
         var5 = 7;
         isSkyMap = true;
      } else {
         TinyGL.textureBase = var10[var8[var11] & 16383];
         TinyGL.paletteBase = var9[var7[var11] & 16383];
         byte var12 = mediaDimensions[var11];
         var5 = var12 >> 4 & 15;
         var4 = var12 & 15;
         TinyGL.imageBounds[0] = mediaBounds[(var11 << 2) + 0] & 255;
         TinyGL.imageBounds[1] = mediaBounds[(var11 << 2) + 1] & 255;
         TinyGL.imageBounds[2] = mediaBounds[(var11 << 2) + 2] & 255;
         TinyGL.imageBounds[3] = mediaBounds[(var11 << 2) + 3] & 255;
         isSkyMap = false;
      }

      TinyGL.sWidth = 1 << var5;
      TinyGL.sShift = 26 - var5;
      TinyGL.sMask = TinyGL.sWidth - 1;
      TinyGL.tHeight = 1 << var4;
      TinyGL.tShift = 26 - (var4 + var5);
      TinyGL.tMask = (TinyGL.tHeight - 1) * TinyGL.sWidth;
   }

   static void drawSkyMap(int var0, int var1) {
      boolean var2 = false;
      TinyGL.paletteBase = skyMapPalette;
      byte[] var3 = skyMapTexels;
      int[] var4 = TinyGL.getFogPalette(268435456);
      int[] var5 = TinyGL.pixels;
      int var6 = TinyGL.viewportY;
      int var7 = var0;
      int var9 = TinyGL.viewportY << 3;
      var9 = var9 * 128 / 256;

      for(int var8 = var1 + (var9 * 128 << 5); var6 < TinyGL.viewportY2; var8 += 16384) {
         int var10 = TinyGL.viewportX2 - TinyGL.viewportX;
         int var11 = var6 * TinyGL.screenWidth + TinyGL.viewportX;
         var0 = var7;
         var1 = var8 >> 8 & 16256;

         while(true) {
            --var10;
            if (var10 < 0) {
               ++var6;
               break;
            }

            var5[var11] = var4[var3[var1 | var0 >> 8 & 127] & 255];
            ++var11;
            var0 += 128;
         }
      }

   }

   public static final void render(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      var3 &= 1023;
      var4 &= 1023;
      int var7 = 256 - screenHeight << 3;
      int var8 = 1024 - var4 + 768 & 511;
      skyMapX = (1024 - var3 & 1023) * 256 * 2 / 1024 << 3;
      skyMapY = var8 * var7 / 512;
      skyMapY = (skyMapY - var7) * 3 / 2 + var7;
      skyMapY = Math.min(Math.max(skyMapY, 0), var7);
      currentFrameTime = App.getUpTimeMs();
      int var9 = (TinyGL.viewportWidth << 14) / TinyGL.viewportHeight;
      int var10 = (var6 << 14) / var9;
      int var11 = 0;
      int var12 = Math.max(Player.statusEffects[33], Player.statusEffects[34]);
      if (var12 != destDizzy) {
         baseDizzy = destDizzy;
         destDizzy = var12;
         dizzyTime = App.time;
      }

      int var13;
      if (destDizzy != baseDizzy && dizzyTime + 2048 > App.time) {
         var13 = (App.time - dizzyTime << 16) / 2048;
         var12 = baseDizzy + ((destDizzy - baseDizzy) * var13 >> 16);
      }

      var12 = (var12 << 8) / 30;
      if (Canvas.state == 18 || MenuSystem.menu == 1) {
         var12 = 0;
      }

      if (Game.isUnderWater()) {
         var11 = 8;
      } else if (var12 != 0) {
         var11 = 8 * var12 >> 8;
      }

      if (var11 != 0) {
         var6 = var6 - var11 + (var11 * sinTable[App.time / 2 & 1023] >> 16);
         var10 = var10 - var11 + (var11 * sinTable[App.time / 2 + 256 & 1023] >> 16);
      }

      int var14;
      if (App.time < rockViewTime + rockViewDur) {
         var13 = (App.time - rockViewTime << 16) / (rockViewDur << 8);
         var14 = sinTable[var13 + 256 & 1023] >> 8;
         var0 += var14 * (rockViewX - var0 << 8) >> 16;
         var1 += var14 * (rockViewY - var1 << 8) >> 16;
         var2 += var14 * (rockViewZ - var2 << 8) >> 16;
      }

      int var15;
      if (var12 != 0) {
         var13 = App.time;
         var14 = (sinTable[var13 * 5 / 8 & 1023] >> 8) * var12 >> 8;
         var15 = (sinTable[var13 * 4 / 8 + 256 & 1023] >> 8) * var12 >> 8;
         int var16 = (sinTable[var13 * 6 / 8 & 1023] >> 8) * var12 >> 8;
         int var17 = (sinTable[var13 * 3 / 8 & 1023] >> 8) * var12 >> 8;
         var0 += var14 * 12288 >> 16;
         var1 += var15 * 12288 >> 16;
         var2 += var16 * 8192 >> 16;
         var3 += var17 * 2048 >> 16;
      }

      if (App.time < Canvas.shakeTime) {
         var13 = sinTable[var3 + 256 & 1023];
         var14 = sinTable[var3 + 256 + 256 & 1023];
         var0 += (Canvas.shakeX << 4) * var14 >> 16;
         var1 += (Canvas.shakeX << 4) * -var13 >> 16;
         var2 += Canvas.shakeY << 4;
      }

      viewSin = sinTable[var3 & 1023];
      viewCos = sinTable[var3 + 256 & 1023];
      var13 = var3 - 256 & 1023;
      viewRightStepX = sinTable[var13 + 256 & 1023] >> 10;
      viewRightStepY = -sinTable[var13 & 1023] >> 10;
      if (!skipViewNudge) {
         var0 -= 144 * viewCos >> 16;
         var1 += 144 * viewSin >> 16;
      }

      if (chatZoom && Combat.curTarget != null && (Combat.curTarget.def.eType == 3 || Combat.curTarget.def.eType == 2)) {
         var14 = Combat.curTarget.getSprite();
         var0 = (mapSprites[S_X + var14] << 4) + (-viewCos * 32 >> 12);
         var1 = (mapSprites[S_Y + var14] << 4) + (viewSin * 32 >> 12);
         var2 = mapSprites[S_Z + var14] + 4 << 4;
         var4 = 0;
      }

      viewX = var0;
      viewY = var1;
      viewZ = var2;
      viewAngle = var3;
      TinyGL.setView(viewX, viewY, viewZ, var3, var4, var5, var6, var10);
      if (fogLerpTime != 0) {
         if (App.time < fogLerpStart + fogLerpTime) {
            var14 = (App.time - fogLerpStart << 16) / fogLerpTime;
            TinyGL.fogMin = baseFogMin + ((destFogMin - baseFogMin) * var14 >> 16);
            TinyGL.fogRange = baseFogRange + ((destFogRange - baseFogRange) * var14 >> 16);
            if (TinyGL.fogRange == 0) {
               TinyGL.fogRange = 1;
            }
         } else {
            fogLerpTime = 0;
            TinyGL.fogMin = destFogMin;
            TinyGL.fogRange = destFogRange;
         }
      }

      int[] var18 = TinyGL.columnScale;

      for(var15 = 0; var15 < var18.length; ++var15) {
         var18[var15] = Integer.MAX_VALUE;
      }

      clearColorBuffer = App.getUpTimeMs();
      if ((renderMode & 32) != 0) {
         TinyGL.clearColorBuffer(-65281);
      } else if (null != skyMapTexels && Game.scriptStateVars[5] != 0 && (renderMode & 32) == 0) {
         drawSkyMap(skyMapX << 5, skyMapY * 128 << 5);
      } else {
         var15 = 0;
         if (TinyGL.fogRange > 1) {
            var15 = TinyGL.fogColor;
         }

         TinyGL.clearColorBuffer(var15);
      }

      clearColorBuffer = App.getUpTimeMs() - clearColorBuffer;
      renderBSP();
      frameTime = App.getUpTimeMs() - currentFrameTime;
      shotsFired = false;
   }

   public static final void unlinkSprite(int var0) {
      unlinkSprite(var0, mapSprites[S_X + var0], mapSprites[S_Y + var0]);
   }

   public static final void unlinkSprite(int var0, int var1, int var2) {
      if (mapSprites[S_NODE + var0] != -1) {
         short var3 = mapSprites[S_NODE + var0];
         if (nodeSprites[var3] == var0) {
            nodeSprites[var3] = mapSprites[S_NODENEXT + var0];
         } else {
            short var4;
            for(var4 = nodeSprites[var3]; var4 != -1 && mapSprites[S_NODENEXT + var4] != var0; var4 = mapSprites[S_NODENEXT + var4]) {
            }

            if (var4 != -1) {
               mapSprites[S_NODENEXT + var4] = mapSprites[S_NODENEXT + var0];
            }
         }
      }

   }

   public static final void relinkSprite(int var0) {
      relinkSprite(var0, mapSprites[S_X + var0] << 4, mapSprites[S_Y + var0] << 4, mapSprites[S_Z + var0] << 4);
   }

   public static final void relinkSprite(int var0, int var1, int var2, int var3) {
      short var4;
      if (mapSprites[S_NODE + var0] != -1) {
         var4 = mapSprites[S_NODE + var0];
         if (nodeSprites[var4] == var0) {
            nodeSprites[var4] = mapSprites[S_NODENEXT + var0];
         } else {
            short var5;
            for(var5 = nodeSprites[var4]; var5 != -1 && mapSprites[S_NODENEXT + var5] != var0; var5 = mapSprites[S_NODENEXT + var5]) {
            }

            if (var5 != -1) {
               mapSprites[S_NODENEXT + var5] = mapSprites[S_NODENEXT + var0];
            }
         }
      }

      var4 = getNodeForPoint(var1, var2, var3, mapSpriteInfo[var0]);
      mapSprites[S_NODE + var0] = var4;
      if (var4 != -1) {
         mapSprites[S_NODENEXT + var0] = nodeSprites[var4];
         nodeSprites[var4] = (short)var0;
      }

   }

   private static final short getNodeForPoint(int var0, int var1, int var2, int var3) {
      short var4 = 0;
      int var5 = nodeOffsets[var4] & '\uffff';
      boolean var6 = (var3 & 251658240) != 0;
      int var7 = var3 & 255;
      if ((var3 & 4194304) != 0) {
         var7 += 257;
      }

      int var9;
      for(boolean var8 = var7 == 170 || var7 == 241 || var7 == 237; var5 != 65535; var5 = nodeOffsets[var4] & '\uffff') {
         var9 = nodeClassifyPoint(var4, var0, var1, var2);
         if (var9 == 0 && var6) {
            if ((var3 & 150994944) != 0) {
               var4 = nodeChildOffset1[var4];
            } else {
               var4 = nodeChildOffset2[var4];
            }
         } else {
            if (!var8 && var9 > -128 && var9 < 128) {
               return var4;
            }

            if (var9 > 0) {
               var4 = nodeChildOffset1[var4];
            } else {
               var4 = nodeChildOffset2[var4];
            }
         }
      }

      var9 = (nodeBoundXs[(var4 << 1) + 0] & 255) << 7;
      int var10 = (nodeBoundYs[(var4 << 1) + 0] & 255) << 7;
      int var11 = (nodeBoundXs[(var4 << 1) + 1] & 255) << 7;
      int var12 = (nodeBoundYs[(var4 << 1) + 1] & 255) << 7;
      if (var0 >= var9 && var1 >= var10 && var0 <= var11 && var1 <= var12) {
         return var4;
      } else {
         return -1;
      }
   }

   public static final int getHeight(int var0, int var1) {
      if (heightMap == null) {
         return 0;
      } else {
         var0 &= 2047;
         var1 &= 2047;
         byte[] var2 = heightMap;
         return var2[(var1 >> 6) * 32 + (var0 >> 6)] << 3;
      }
   }

   public static final boolean checkTileVisibilty(int var0, int var1) {
      int var2 = var0 << 6;
      int var3 = var1 << 6;
      int var4 = var2 + 64;
      int var5 = var3 + 64;
      return !cullBoundingBox(var2 << 4, var3 << 4, var4 << 4, var5 << 4, true);
   }

   public static final void postProcessSprites() {
      int[] var1 = mapSpriteInfo;
      short[] var2 = mapSprites;

      int var0;
      int var3;
      for(var0 = 0; var0 < numMapSprites; ++var0) {
         int var10001 = S_Z + var0;
         var2[var10001] = (short)(var2[var10001] + getHeight(var2[S_X + var0], var2[S_Y + var0]));
         if (var0 >= numNormalSprites) {
            var10001 = S_Z + var0;
            var2[var10001] = (short)(var2[var10001] - 32);
         }

         var3 = var1[var0] & 255;
         if ((mapSpriteInfo[var0] & 4194304) != 0) {
            var3 += 257;
         }

         if (var3 == 479) {
            var2[S_RENDERMODE + var0] = 1;
         } else if (var3 != 176 && var3 != 208 && var3 != 206 && var3 != 234 && var3 != 130 && var3 != 242) {
            if (var3 == 212) {
               var2[S_RENDERMODE + var0] = 7;
            } else if (var3 != 161 && var3 != 210) {
               if (var3 == 150) {
                  var2[S_SCALEFACTOR + var0] = 32;
                  var10001 = S_Z + var0;
                  var2[var10001] = (short)(var2[var10001] + 16);
               } else {
                  var2[S_RENDERMODE + var0] = 0;
                  if (var3 == 200) {
                     var1[var0] |= 262144;
                  }
               }
            } else {
               var2[S_RENDERMODE + var0] = 2;
            }
         } else {
            var2[S_RENDERMODE + var0] = 3;
         }

         relinkSprite(var0);
      }

      for(var3 = 0; var3 < 48; ++var3) {
         customSprites[var3] = var0;
         var2[S_NODE + var0] = -1;
         var1[var0] = 0;
         var2[S_NODENEXT + var0] = -1;
         var2[S_VIEWNEXT + var0] = -1;
         var2[S_ENT + var0] = -1;
         var2[S_RENDERMODE + var0] = 0;
         var2[S_SCALEFACTOR + var0] = 64;
         var2[S_X + var0] = var2[S_Y + var0] = 0;
         var2[S_Z + var0] = 32;
         ++var0;
      }

      firstDropSprite = var0;

      for(var3 = 0; var3 < 16; ++var3) {
         dropSprites[var3] = var0;
         var2[S_NODE + var0] = -1;
         var1[var0] = 0;
         var2[S_NODENEXT + var0] = -1;
         var2[S_VIEWNEXT + var0] = -1;
         var2[S_ENT + var0] = -1;
         var2[S_RENDERMODE + var0] = 0;
         var2[S_SCALEFACTOR + var0] = 64;
         var2[S_X + var0] = var2[S_Y + var0] = 0;
         var2[S_Z + var0] = 32;
         ++var0;
      }

   }

   public static final boolean isFading() {
      return fadeFlags != 0;
   }

   public static final int getFadeFlags() {
      return fadeFlags;
   }

   public static final void startFade(int var0, int var1) {
      fadeTime = App.getUpTimeMs();
      fadeDuration = var0;
      fadeFlags = var1;
   }

   public static final void endFade() {
      fadeFlags = 0;
      fadeTime = -1;
   }

   public static final boolean fadeScene() {
      int var0 = App.getUpTimeMs() - fadeTime;
      if (var0 >= fadeDuration - 50) {
         var0 = fadeDuration;
         if ((fadeFlags & 4) != 0) {
            fadeFlags &= -29;
            Canvas.loadMap(MenuSystem.LEVEL_STATS_nextMap, false);
            return false;
         }

         if ((fadeFlags & 8) != 0) {
            fadeFlags &= -29;
            Canvas.saveState(51, (short)5, (short)179);
            return false;
         }

         if ((fadeFlags & 32) != 0) {
            endFade();
            Canvas.setState(14);
            return false;
         }

         if ((fadeFlags & 2) != 0) {
            endFade();
            return true;
         }

         if ((fadeFlags & 1) != 0) {
            fadeAll(255);
            return true;
         }
      }

      int var1 = (var0 << 16) / (fadeDuration << 8);
      int var2 = '\uff00' * var1 >> 16;
      if ((fadeFlags & 2) != 0) {
         var2 = 255 - var2;
      }

      fadeAll(var2);
      return true;
   }

   public static void drawRGB(Graphics var0) {
      bltTime = App.getUpTimeMs();
      int var1 = TinyGL.viewportX;
      int var2 = TinyGL.viewportY;
      int var3 = TinyGL.viewportWidth;
      int var4 = TinyGL.viewportHeight;
      var0.setColor(0);
      var0.drawRect(Canvas.viewRect[0] + var1 - 1, Canvas.viewRect[1] + var2 - 1, var3 + 2 - 1, var4 + 2 - 1);
      var0.drawRGB(TinyGL.pixels, var2 * screenWidth + var1, screenWidth, Canvas.viewRect[0] + var1, Canvas.viewRect[1] + var2, var3, var4, false);
      bltTime = App.getUpTimeMs() - bltTime;
   }

   public static final void fadeAll(int var0) {
      if (var0 == 255) {
         TinyGL.clearColorBuffer(0);
      } else {
         int[] var4 = TinyGL.pixels;
         var0 &= 254;
         var0 |= var0 << 16 | var0 << 8;
         int var5 = TinyGL.viewportY - 1;

         while(true) {
            ++var5;
            if (var5 >= TinyGL.viewportY2) {
               return;
            }

            int var6 = TinyGL.viewportX;

            int var1;
            int var2;
            int var3;
            for(var3 = var5 * screenWidth + var6; TinyGL.viewportX2 - var6 >= 4; var6 += 4) {
               var1 = (var4[var3] | 16843009) - var0;
               var2 = var1 & 16843008;
               var4[var3] = (var1 ^ var2) & var2 - (var2 >> 7);
               ++var3;
               var1 = (var4[var3] | 16843009) - var0;
               var2 = var1 & 16843008;
               var4[var3] = (var1 ^ var2) & var2 - (var2 >> 7);
               ++var3;
               var1 = (var4[var3] | 16843009) - var0;
               var2 = var1 & 16843008;
               var4[var3] = (var1 ^ var2) & var2 - (var2 >> 7);
               ++var3;
               var1 = (var4[var3] | 16843009) - var0;
               var2 = var1 & 16843008;
               var4[var3] = (var1 ^ var2) & var2 - (var2 >> 7);
               ++var3;
            }

            while(var6++ < TinyGL.viewportX2) {
               var1 = (var4[var3] | 16843009) - var0;
               var2 = var1 & 16843008;
               var4[var3] = (var1 ^ var2) & var2 - (var2 >> 7);
               ++var3;
            }
         }
      }
   }

   public static final void rockView(int var0, int var1, int var2, int var3) {
      rockViewDur = var0;
      rockViewTime = App.getUpTimeMs();
      rockViewX = var1 << 4;
      rockViewY = var2 << 4;
      rockViewZ = var3 << 4;
   }

   public static final boolean isSoldat(int var0) {
      return var0 >= 38 && var0 <= 40;
   }

   public static final boolean isWorker(int var0) {
      return var0 >= 23 && var0 <= 25;
   }

   public static final boolean isSoldier(int var0) {
      return var0 >= 20 && var0 <= 22 || var0 == 19;
   }

   public static final boolean isTrooper(int var0) {
      return var0 >= 41 && var0 <= 43;
   }

   public static final boolean isOfficer(int var0) {
      return var0 >= 32 && var0 <= 34;
   }

   public static final boolean isEGuard(int var0) {
      return var0 >= 44 && var0 <= 46 || var0 == 57;
   }

   public static final boolean isZombie(int var0) {
      return var0 >= 47 && var0 <= 49;
   }

   public static final boolean isSkeleton(int var0) {
      return var0 >= 26 && var0 <= 28;
   }

   public static final boolean isNPC(int var0) {
      return var0 == 71 || var0 == 69 || var0 == 66;
   }

   private static final void renderSpriteAnim(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      int var10 = var1 & 240;
      byte var11 = 0;
      var1 &= 15;
      Entity var12 = Game.entities[mapSprites[S_ENT + var0]];
      int var13 = getHeight(var2, var3) + 32 << 4;
      int var14 = Math.min(Math.max(32 - (var4 - var13), 0), 32);
      int var15 = 65536 * var14 / 32;
      byte var16 = 0;
      byte var17 = 0;
      byte var18 = 0;
      int var19 = 0;
      boolean var20 = var12.hasHead();
      boolean var21 = var12.def.eType == 2 && var12.def.eSubType == 11 && (var12.info & 2097152) != 0;
      int var22;
      int var23;
      int var24;
      char var27;
      switch(var10) {
      case 16:
         var11 = 4;
      case 0:
         var22 = (App.time + var0 * 1337) / 1024 & 1;
         var22 *= 26;
         renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
         renderSprite(var2, var3, var4, var5, var11, var6, var7, var8, var9);
         if (isWorker(var5)) {
            if ((var12.info & 262144) != 0 && (var12.monster.flags & 256) == 0) {
               renderSprite(var2, var3, var4 + var22, var5, 17, var6, var7, var8, var9);
            }
         } else if (isNPC(var5)) {
            var16 = -48;
            var17 = -32;
         }

         renderSprite(var2 + (var18 * viewRightStepX >> 6), var3 + (var18 * viewRightStepY >> 6), var4 + var22 + var16, var5, var11 + 2, var6, var7, var8, var9);
         if (var5 == 68) {
            var23 = ((App.time + var0 * 1337) / 32 & 255) << 16;
            var24 = 32 * sinTable[App.time + var0 * 1337 & 1023];
            var23 = (var23 + var24 >> 16) - 127 >> 31;
            var6 ^= (var23 & 1) << 17;
         }

         if (var20) {
            var23 = var11 + 3;
            if (var21 && var10 == 0) {
               var23 = 10;
            }

            renderSprite(var2 + (var19 * viewRightStepX >> 6), var3 + (var19 * viewRightStepY >> 6), var4 + var22 + var17, var5, var23, var6, var7, var8, var9);
         }
         break;
      case 48:
         var11 = 4;
      case 32:
         var22 = (var1 & 2) >> 1 << 17;
         renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
         renderSprite(var2, var3, var4, var5, var11 + (var1 & 1), var6 ^ var22, var7, var8, var9);
         var23 = var1 & 1;
         if ((var1 & 2) == 0) {
            var23 = -var23;
         }

         var2 += var23 * viewRightStepX >> 6;
         var3 += var23 * viewRightStepY >> 6;
         if (var10 == 32 && isWorker(var5) && (var12.info & 262144) != 0 && (var12.monster.flags & 256) == 0) {
            renderSprite(var2, var3, var4 + ((var1 & 1) << 4), var5, 17, var6, var7, var8, var9);
         }

         renderSprite(var2 + (var18 * viewRightStepX >> 6), var3 + (var18 * viewRightStepY >> 6), var4 + var16 + ((var1 & 1) << 4), var5, var11 + 2, var6, var7, var8, var9);
         if (var20) {
            var24 = var11 + 3;
            if (var21 && var10 == 32) {
               var24 = 10;
            }

            renderSprite(var2 + (var19 * viewRightStepX >> 6), var3 + (var19 * viewRightStepY >> 6), var4 + var17 + ((var1 & 1) << 4), var5, var24, var6, var7, var8, var9);
         }
         break;
      case 80:
         if (var5 == 56) {
            renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
            if (var1 == 0) {
               renderSprite(var2, var3, var4, var5, 0, var6, var7, var8, var9);
               renderSprite(var2, var3, var4, var5, 10, var6, var7, var8, var9);
            } else {
               renderSprite(var2, var3, var4, var5, 11, var6, var7, var8, var9);
               renderSprite(var2, var3, var4, var5, 2, var6, var7, var8, var9);
               renderSprite(var2, var3, var4, var5, 3, var6, var7, var8, var9);
            }
            break;
         }
      case 64:
         byte var29 = 0;
         int var28;
         if (var10 == 64) {
            var28 = 8;
         } else {
            var28 = 10;
         }

         renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
         if (var5 > 59) {
            renderSprite(var2, var3, var4, var5, var28, var6, var7, var8, var9);
         } else {
            if (isSoldier(var5)) {
               var19 = -2;
               var17 = -32;
            } else if (isWorker(var5)) {
               var19 = -1;
               var17 = -32;
            } else if (isOfficer(var5) || isTrooper(var5)) {
               var17 = -32;
            }

            if ((var6 & 131072) != 0) {
               var19 = -var19;
            }

            if (hasPreAttackFrame(var5) && var1 == 1) {
               ++var28;
            }

            renderSprite(var2, var3, var4, var5, var29, var6 ^ 131072, var7, var8, var9);
            renderSprite(var2 + (var18 * viewRightStepX >> 6), var3 + (var18 * viewRightStepY >> 6), var4 + var16, var5, var28, var6, var7, var8, var9);
            if (var20) {
               byte var30 = 3;
               if (var21 && var10 == 32) {
                  var30 = 10;
               }

               renderSprite(var2 + (var19 * viewRightStepX >> 6), var3 + (var19 * viewRightStepY >> 6), var4 + var17, var5, var30, var6, var7, var8, var9);
            }

            if (isWorker(var5)) {
               var23 = -22 * viewRightStepX >> 6;
               var24 = -22 * viewRightStepY >> 6;
               int var25 = var1 == 1 ? 0 : 128;
               if ((var6 & 131072) != 0) {
                  var23 = -var23;
                  var24 = -var24;
               }

               renderSprite(var2 + var23, var3 + var24, var4 + var25, var5, 17, var6 ^ 131072, var7, var8, var9);
            }

            if (var1 == 1 && var5 != 58 && var5 != 54 && var5 != 55 && var5 != 56 && !isSkeleton(var5) && !isZombie(var5) && !isWorker(var5) && (var5 != 22 || var10 != 80)) {
               var23 = 0;
               var24 = 0;
               short var31 = 416;
               if (var5 == 41) {
                  var31 = 448;
               } else if (isTrooper(var5)) {
                  var23 = -10 * viewRightStepX >> 6;
                  var24 = -10 * viewRightStepY >> 6;
                  var31 = 384;
               } else if (isSoldat(var5)) {
                  var23 = 24 * viewRightStepX >> 6;
                  var24 = 24 * viewRightStepY >> 6;
                  var31 = 384;
                  if (var5 == 40 && var10 == 80) {
                     var23 = -var23;
                     var24 = -var24;
                  }
               } else if (isOfficer(var5)) {
                  var23 = -5 * viewRightStepX >> 6;
                  var24 = -5 * viewRightStepY >> 6;
                  var31 = 384;
               } else {
                  var23 = -6 * viewRightStepX >> 6;
                  var24 = -6 * viewRightStepY >> 6;
               }

               if ((var6 & 131072) != 0) {
                  var23 = -var23;
                  var24 = -var24;
               }

               var2 += var23;
               var3 += var24;
               var4 += var31;
               renderSprite(var2, var3, var4, var5, var28 + 1, (Player.totalMoves + Combat.animLoopCount & 3) << 17, 4, var8 / 3, var9);
            }
         }
         break;
      case 96:
         renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
         renderSprite(var2, var3, var4 + var16, var5, 12, var6, var7, var8, var9);
         if (var20 && isSkeleton(var5)) {
            renderSprite(var2, var3, var4 - 64, var5, 3, var6, var7, var8, var9);
         }
         break;
      case 112:
         renderSprite(var2, var3, var4, var5, 13, var6, var7, var8, var9);
         break;
      case 128:
         var27 = '耀';
         var4 += 160;
         renderSprite(var2, var3, var4, var5, 14, var6, var7, var27, var9);
         byte var26 = 15;
         if (var21) {
            var26 = 11;
         }

         if (Combat.animEndTime != 0 && Canvas.state != 8) {
            if (Combat.animLoopCount == 1) {
               var26 = 16;
               var6 &= -131073;
            } else if (Combat.animLoopCount == 2) {
               var26 = 16;
               var6 |= 131072;
            }
         }

         if (var20) {
            renderSprite(var2, var3, var4, var5, var26, var6, var7, var27, var9);
         }
         break;
      case 144:
         if (isEGuard(var5)) {
            var22 = 20 * viewRightStepX >> 6;
            var23 = 20 * viewRightStepY >> 6;
            var6 = var6 & -131073 | ((Combat.dodgeDir ^ 1) & 1) << 17;
            if ((var6 & 131072) != 0) {
               var22 = -var22;
               var23 = -var23;
            }

            var2 += var22;
            var3 += var23;
            renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
            renderSprite(var2, var3, var4, var5, 0, var6, var7, var8, var9);
            renderSprite(var2, var3, var4, var5, 2, var6, var7, var8, var9);
            renderSprite(var2, var3, var4, var5, 3, var6, var7, var8, var9);
            return;
         }

         renderSprite(var2, var3, var13, 232, 0, var6, var7, var15, var9);
         renderSprite(var2, var3, var4, var5, 17, var6, var7, var8, var9);
         break;
      case 160:
         var27 = '耀';
         var4 += 160;
         renderSprite(var2, var3, var4, var5, 9, var6, var7, var27, var9);
         if (var5 == 68) {
            var22 = ((App.time + var0 * 1337) / 32 & 255) << 16;
            var23 = 32 * sinTable[App.time + var0 * 1337 & 1023];
            var22 = (var22 + var23 >> 16) - 127 >> 31;
            var6 ^= (var22 & 1) << 17;
         }

         if (var20) {
            renderSprite(var2, var3, var4, var5, 10, var6, var7, var27, var9);
         }
         break;
      case 176:
         renderSprite(var2, var3, var4, var5, 4, var6, var7, var8, var9);
         renderSprite(var2, var3, var4, var5, 8 + var1, var6, var7, var8, var9);
         renderSprite(var2, var3, var4, var5, 7, var6, var7, var8, var9);
         break;
      default:
         renderSprite(var2, var3, var4, var5, var1, var6, var7, var8, var9);
      }

   }

   private static final void renderHarbingerAnim(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int var9 = var1 & 240;
      int var10 = 0;
      int var11 = 0;
      int var12 = 2;
      byte var13 = 3;
      byte var14 = 17;
      var1 &= 15;
      int var15 = getHeight(var2, var3) + 32 << 4;
      int var16 = Math.min(Math.max(32 - (var4 - var15), 0), 32);
      int var17 = 65536 * var16 / 32;
      int var18 = 0;
      short var19 = 0;
      byte var20 = 0;
      byte var21 = 0;
      byte var22 = 0;
      short var23 = 0;
      byte var24 = 0;
      short var25 = 0;
      int var26 = var7;
      boolean var27 = (var5 & 131072) == 0;
      boolean var28 = true;
      int var29;
      byte var31;
      switch(var9) {
      case 0:
         if (var27) {
            var31 = 112;
            var20 = 1;
            var21 = -4;
            var19 = 256;
            var22 = 0;
            var23 = 0;
            var24 = 26;
            var25 = 448;
         } else {
            var20 = 0;
            var31 = 112;
            var21 = 4;
            var19 = 256;
         }

         var18 = var31 + (((App.time + var0 * 1337) / 1024 & 1) << 4);
         break;
      case 16:
         var10 = 4;
         var12 = 6;
         var13 = 7;
         var14 = 18;
         if (var27) {
            var20 = 4;
            var31 = 112;
            var21 = 4;
            var19 = 256;
            var22 = 0;
            var23 = 0;
            var24 = 24;
            var25 = 448;
         } else {
            var20 = 0;
            var31 = 112;
            var21 = 0;
            var19 = 256;
         }

         var18 = var31 + (((App.time + var0 * 1337) / 1024 & 1) << 4);
         break;
      case 32:
         if (var27) {
            var18 = 7 + (var1 & 1) << 4;
            var20 = 0;
            var21 = -3;
            var19 = 256;
            var24 = 24;
            var25 = 448;
         } else {
            var20 = 0;
            var18 = 7 + (var1 & 1) << 4;
            var21 = 5;
            var19 = 256;
         }

         var29 = var1 & 1;
         var10 += var29;
         if ((var1 & 2) == 0) {
            var29 = -var29;
            var11 = 131072;
         }

         var2 += var29 * viewRightStepX >> 6;
         var3 += var29 * viewRightStepY >> 6;
         break;
      case 48:
         byte var30 = 4;
         var12 = 6;
         var13 = 7;
         var14 = 18;
         if (var27) {
            var18 = 7 + (var1 & 1) << 4;
            var21 = 1;
            var19 = 240;
            var24 = -24;
            var25 = 448;
         } else {
            var18 = 7 + (var1 & 1) << 4;
            var21 = -1;
            var19 = 240;
         }

         var29 = var1 & 1;
         var10 = var30 + var29;
         if ((var1 & 2) == 0) {
            var29 = -var29;
            var11 = 131072;
         }

         var2 += var29 * viewRightStepX >> 6;
         var3 += var29 * viewRightStepY >> 6;
         break;
      case 64:
      case 80:
         if (var9 == 64) {
            var12 = 8;
         } else {
            var12 = 10;
         }

         if (var1 == 1) {
            ++var12;
         }

         if (var27) {
            if (var12 == 8) {
               var10 = 1;
               var20 = 12;
               var18 = 256;
               var21 = 7;
               var19 = 128;
               var22 = 6;
               var23 = -160;
               var24 = 38;
               var25 = 704;
            } else if (var12 == 9) {
               var18 = 96;
               var20 = 0;
               var19 = 64;
               var21 = -4;
               var22 = -2;
               var23 = 32;
               var24 = -16;
               var25 = 352;
            } else if (var12 == 10) {
               var18 = 96;
               var20 = 2;
               var21 = -3;
               var19 = -160;
               var22 = -2;
               var23 = -128;
               var24 = 29;
               var25 = 352;
               var26 = 81920;
            }
         } else if (var12 == 8) {
            var10 = 1;
            var20 = -12;
            var18 = 256;
            var21 = -7;
            var19 = 128;
         } else if (var12 == 9) {
            var18 = 96;
            var20 = 0;
            var19 = 112;
            var21 = 4;
         } else if (var12 == 10) {
            var18 = 96;
            var20 = -2;
            var19 = -160;
            var21 = 3;
            var26 = 81920;
         }

         if (var1 > 1) {
            var1 -= 2;
            var10 += var1 & 1;
            if ((var1 & 2) == 0) {
               var11 = 131072;
            }
         }

         if (var12 == 11) {
            var28 = false;
            var18 = 128;
         }
         break;
      case 96:
         var12 = 12;
         if (var27) {
            var22 = -4;
            var23 = -160;
            var24 = 24;
            var25 = 320;
            var18 = 272;
         } else {
            var18 = 272;
         }

         var28 = false;
         break;
      case 112:
         renderSprite(var2, var3, var4, 58, 13, var5, var6, var7, var8);
         return;
      case 128:
      case 144:
      case 160:
      case 176:
      default:
         renderSprite(var2, var3, var4, 58, var1, var5, var6, var7, var8);
         return;
      }

      renderSprite(var2, var3, var15, 232, 0, var5, var6, var17, var8);
      renderSprite(var2, var3, var4, 58, var10, var5 ^ var11, var6, var7, var8);
      renderSprite(var2 + (var20 * viewRightStepX >> 6), var3 + (var20 * viewRightStepY >> 6), var4 + var18, 58, var12, var5, var6, var7, var8);
      if (var28) {
         renderSprite(var2 + (var21 * viewRightStepX >> 6), var3 + (var21 * viewRightStepY >> 6), var4 + var19, 58, var13, var5, var6, var26, var8);
      }

      if (var27 && var12 != 11) {
         renderSprite(var2 + (var22 * viewRightStepX >> 6), var3 + (var22 * viewRightStepY >> 6), var4 + var23 + var18, 58, var14, var5, var6, var7, var8);
         renderSprite(var2 + (var24 * viewRightStepX >> 6), var3 + (var24 * viewRightStepY >> 6), var4 + var25 + var18, 234, App.time / 100 & 3, 0, 4, 21845, 0);
      }

   }

   private static boolean hasPreAttackFrame(int var0) {
      return isSkeleton(var0) || isZombie(var0) || var0 == 58 || var0 == 56 || var0 == 54 || var0 == 55 || isWorker(var0);
   }
}
