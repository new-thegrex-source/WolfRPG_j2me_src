/* Decompiler 1401ms, total 1695ms, lines 4013 */
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.rms.RecordStore;

final class Game {
   public static int touchMe = 1;
   private static final int ENTITY_MASK = 65535;
   public static final int DIFFICULTY_NORMAL = 1;
   public static final int DIFFICULTY_HARD = 2;
   public static final int DIFFICULTY_NIGHTMARE = 4;
   public static final int LOAD_NONE = 0;
   public static final int LOAD_USERSAVE = 1;
   public static final int LOAD_BRIEFSAVE = 2;
   public static final int LOAD_CHICKENGAME = 3;
   public static final int SAVE_NONE = 0;
   public static final int SAVE_PLAYER = 1;
   public static final int SAVE_WORLD = 2;
   public static final int SAVE_EXIT = 4;
   public static final int SAVE_LOADMAP = 8;
   public static final int SAVE_ENDLEVEL = 16;
   public static final int SAVE_BRIEF = 32;
   public static final int SAVE_QUIT = 64;
   public static final int SAVE_RETURNTOGAME = 128;
   public static final int SAVE_ENDGAME = 256;
   public static final int ENTITYNUM_WORLD = 0;
   public static final int ENTITYNUM_PLAYER = 1;
   private static final int MAX_LERP_SPRITES = 16;
   private static final int MAX_LEVEL_VARS = 8;
   public static final int MAX_LEVEL_NAMES = 9;
   private static final int MAX_ENTITIES = 275;
   private static final int MAX_TRACE_ENTITIES = 32;
   private static final int MAX_MONSTERS = 80;
   private static final int SAVEGAME_VERSION = 21;
   private static final String SAVEGAME_NAME_FULLWORLD = "SDFWORLD";
   private static final String SAVEGAME_NAME_FULLPLAYER = "SDFPLAYER";
   private static final String SAVEGAME_NAME_BRIEFWORLD = "SDBWORLD";
   private static final String SAVEGAME_NAME_BRIEFPLAYER = "SDBPLAYER";
   private static final String SAVEGAME_NAME_CONFIG = "SDCONFIG";
   public static final int SAVE_FLAG_BINARY = 65536;
   public static final int SAVE_FLAG_NAMED = 131072;
   public static final int SAVE_FLAG_SHOWN = 262144;
   public static final int SAVE_FLAG_CLEAN = 524288;
   public static final int SAVE_FLAG_CORPSE = 1048576;
   public static final int SAVE_FLAG_CUSTOM = 2097152;
   public static final int SAVE_FLAG_NOTRACK = 4194304;
   public static final int SAVE_FLAG_NORESPAWN = 8388608;
   public static final int SAVE_FLAG_ENTITY_DEATH = 16777216;
   public static final int SAVE_FLAG_GHOST = 33554432;
   public static final int SAVE_FLAG_NPC_FRIGHT = 67108864;
   public static final int CTABLE_SIZE = 8;
   public static final int LOOKAHEAD_COUNT = 3;
   public static final int CTABLE_ORIGINX = 4;
   public static final int CTABLE_ORIGINY = 4;
   public static final int TURN_TYPE_NONE = 0;
   private static final int TURN_TYPE_NORMAL = 1;
   private static final int TURN_TYPE_HASTE = 2;
   public static int OFS_MAYAKEY_X = 0;
   public static int OFS_MAYAKEY_Y = 0;
   public static int OFS_MAYAKEY_Z = 0;
   public static int OFS_MAYAKEY_PITCH = 0;
   public static int OFS_MAYAKEY_YAW = 0;
   public static int OFS_MAYAKEY_ROLL = 0;
   public static int OFS_MAYAKEY_MS = 0;
   public static final int NUM_MAYAKEY_FIELDS = 7;
   public static final int OFS_MAYATWEEN_X = 0;
   public static final int OFS_MAYATWEEN_Y = 1;
   public static final int OFS_MAYATWEEN_Z = 2;
   public static final int OFS_MAYATWEEN_PITCH = 3;
   public static final int OFS_MAYATWEEN_YAW = 4;
   public static final int OFS_MAYATWEEN_ROLL = 5;
   public static final int NUM_MAYATWEEN_FIELDS = 6;
   public static short[] ofsMayaTween = new short[6];
   public static int camPlayerX;
   public static int camPlayerY;
   public static int camPlayerZ;
   public static int camPlayerYaw;
   public static int camPlayerPitch;
   public static MayaCamera[] mayaCameras;
   public static int totalMayaCameras;
   public static short[] mayaCameraKeys;
   public static byte[] mayaCameraTweens;
   public static short[] mayaTweenIndices;
   public static int totalMayaCameraKeys;
   public static int totalMayaTweens;
   public static Entity[] entities = new Entity[275];
   public static int numEntities;
   public static Entity[] entityDb = new Entity[1024];
   private static EntityMonster[] entityMonsters = new EntityMonster[80];
   private static int numMonsters;
   public static int spawnParam;
   public static boolean disableAI;
   public static Entity inactiveMonsters;
   public static Entity activeMonsters;
   public static Entity combatMonsters;
   public static int cinUnpauseTime;
   public static int lastTurnTime;
   public static boolean pauseGameTime;
   public static int firstDropIndex;
   private static int dropIndex = 0;
   private static int lastDropEntIndex;
   public static boolean skippingCinematic = false;
   public static boolean skipDialog;
   public static boolean skipAdvanceTurn;
   public static boolean queueAdvanceTurn = false;
   public static char[] keycode = new char[7];
   public static int monstersTurn;
   private static boolean monstersUpdated;
   public static boolean interpolatingMonsters;
   public static boolean gotoTriggered;
   public static boolean isSaved;
   public static boolean isLoaded;
   public static final int[] eventFlags = new int[2];
   private static final GameSprite[] gsprites = new GameSprite[48];
   public static int activeSprites;
   public static int activePropogators;
   public static int animatingEffects;
   public static int changeMapParam;
   private static int saveStateMap;
   public static byte difficulty;
   public static int activeLoadType;
   public static boolean updateAutomap;
   public static boolean hasSeenIntro = false;
   public static final int[] levelVars = new int[8];
   public static short[] levelNames = null;
   public static int totalLevelTime;
   public static int curLevelTime;
   public static short lootFound = 0;
   public static short totalLoot = 0;
   public static MayaCamera activeCamera;
   public static int activeCameraTime;
   public static int activeCameraKey;
   public static boolean activeCameraView;
   public static Entity watchLine;
   private static final LerpSprite[] lerpSprites = new LerpSprite[16];
   public static int numLerpSprites;
   public static final ScriptThread[] scriptThreads = new ScriptThread[20];
   public static int numScriptThreads;
   public static final boolean[] pathVisited = new boolean[64];
   public static final short[] pathParents = new short[64];
   public static final short[] pathSearches = new short[64];
   public static int numPathSearches;
   public static boolean secretActive;
   public static final int MAXOPENDOORS = 6;
   public static Entity[] openDoors = new Entity[6];
   public static int viewX;
   public static int viewY;
   public static int viewAngle;
   public static int destX;
   public static int destY;
   public static int destAngle;
   public static int viewSin;
   public static int viewCos;
   public static int viewStepX;
   public static int viewStepY;
   public static int viewRightStepX;
   public static int viewRightStepY;
   public static final int MAXSTATEVARS = 64;
   public static final int STATEVARMASK = 63;
   public static short[] scriptStateVars = new short[64];
   public static byte mapSecretsFound;
   public static byte totalSecrets;
   public static int cinematicWeapon;
   public static final int MAX_MIXING_STATIONS = 4;
   public static final int MIXING_STATION_FIELDS = 4;
   public static int[] mixingStations = new int[16];
   public static int numStations = 0;
   public static final int DOORSPEED = 128;
   private static final int MAX_ENTITY_FUNCS = 32;
   private static short[] entityDeathFunctions = new short[64];
   public static int numDestroyableObj = 0;
   public static int destroyedObj = 0;
   public static final int BOMB_TARGET_SHIFT = 8;
   public static final int BOMB_TARGET_MASK = 2147483392;
   public static final int BOMB_TURN_MASK = 255;
   public static final int PLACED_BOMB_Z = 31;
   public static final int MAX_BOMBS = 4;
   public static int[] placedBombs = new int[]{0, 0, 0, 0};
   public static final int MAX_GRID_ENTITIES = 9;
   public static final Entity[] gridEntities = new Entity[9];
   public static final Entity[] traceEntities = new Entity[32];
   public static int[] traceFracs = new int[32];
   public static int numTraceEntities;
   public static int[] traceBoundingBox = new int[4];
   public static int[] tracePoints = new int[4];
   public static int traceCollisionX;
   public static int traceCollisionY;
   public static int traceCollisionZ;
   public static Entity traceEntity;
   public static final int TRACE_SHIFT = 14;
   public static final int TRACE_ONE = 16384;
   public static final int DEFAULT_ENT_RADIUS = 25;
   public static final int MOVEMENT_RADIUS = 16;
   public static final int MIN_RADIUS = 2;
   static final int DOOR_FORCE_SNAP = 0;
   static final int DOOR_FORCE_ANIMATE = 1;
   static final int DOOR_INVIEW_ANIMATE = 2;
   private static int[] dropDirs = new int[]{64, 0, 64, 64, 0, 64, -64, 64, -64, 0, -64, -64, 0, -64, 64, -64};
   public static final int LS_UPDATE_NONE = 0;
   public static final int LS_UPDATE_FREED = 1;
   public static final int LS_UPDATE_UPDATEVIEW = 2;
   public static final int LS_UPDATE_REFRESH = 4;
   private static int numCallThreads = 0;
   private static ScriptThread[] callThreads = new ScriptThread[16];
   public static final int MEDAL_XP = 10;
   public static final int SECRET_XP = 5;

   public static final boolean startup() {
      levelNames = App.TBL_GAME_LEVELNAMES;
      boolean var0 = false;
      difficulty = 1;
      cinematicWeapon = -1;

      int var1;
      for(var1 = 0; var1 < entities.length; ++var1) {
         entities[var1] = new Entity();
      }

      for(var1 = 0; var1 < entityMonsters.length; ++var1) {
         entityMonsters[var1] = new EntityMonster();
      }

      for(var1 = 0; var1 < gsprites.length; ++var1) {
         gsprites[var1] = new GameSprite();
      }

      for(var1 = 0; var1 < lerpSprites.length; ++var1) {
         lerpSprites[var1] = new LerpSprite();
      }

      for(var1 = 0; var1 < scriptThreads.length; ++var1) {
         scriptThreads[var1] = new ScriptThread();
      }

      for(var1 = 0; var1 < 64; ++var1) {
         entityDeathFunctions[var1] = -1;
      }

      activeMonsters = null;
      combatMonsters = null;
      inactiveMonsters = null;
      keycode[0] = 0;
      gotoTriggered = false;
      disableAI = false;
      activeSprites = 0;
      activePropogators = 0;
      mapSecretsFound = 0;
      totalSecrets = 0;
      skipDialog = false;

      for(var1 = 0; var1 < 20; ++var1) {
         scriptThreads[var1].reset();
      }

      return true;
   }

   public static final void unlinkEntity(Entity var0) {
      if (var0 == entities[0]) {
         App.Error(4);
      } else {
         if (var0 == entityDb[var0.linkIndex]) {
            entityDb[var0.linkIndex] = var0.nextOnTile;
         } else if (var0.prevOnTile != null) {
            var0.prevOnTile.nextOnTile = var0.nextOnTile;
         }

         if (var0.nextOnTile != null) {
            var0.nextOnTile.prevOnTile = var0.prevOnTile;
         }

         var0.prevOnTile = var0.nextOnTile = null;
         var0.info &= -1048577;
      }
   }

   public static final void linkEntity(Entity var0, int var1, int var2) {
      short var3 = (short)(var2 * 32 + var1);
      if (var0 == entities[0]) {
         App.Error(3);
      } else if (var0.nextOnTile == null && var0.prevOnTile == null && var0 != entityDb[var3]) {
         Entity var4 = entityDb[var3];
         var0.nextOnTile = var4;
         if (var4 != null) {
            var4.prevOnTile = var0;
         }

         var0.prevOnTile = null;
         var0.linkIndex = var3;
         entityDb[var3] = var0;
         var0.info |= 1048576;
      } else {
         App.Error(26);
      }
   }

   public static final void unlinkWorldEntity(int var0, int var1) {
      short var2 = (short)(var1 * 32 + var0);
      Entity var3 = entityDb[var2];
      Entity var4 = entities[0];
      int[] var10000 = Entity.baseVisitedTiles;
      var10000[var1] &= ~(1 << var0);
      byte[] var6;
      if (var3 == var4) {
         entityDb[var2] = null;
         var6 = Render.mapFlags;
         var6[var2] &= -2;
      } else {
         for(Entity var5 = null; var3 != null; var3 = var3.nextOnTile) {
            if (var3 == var4) {
               if (var5 != null) {
                  var5.nextOnTile = null;
               }

               var6 = Render.mapFlags;
               var6[var2] &= -2;
               return;
            }

            var5 = var3;
         }

         App.Error(52);
      }
   }

   private static final void linkWorldEntity(int var0, int var1) {
      short var2 = (short)(var1 * 32 + var0);
      Entity var3 = entityDb[var2];
      Entity var4 = null;

      Entity var5;
      for(var5 = entities[0]; var3 != null; var3 = var3.nextOnTile) {
         if (var3.def.eType == 0) {
            App.Error(27);
            return;
         }

         var4 = var3;
      }

      if (var4 == null) {
         entityDb[var2] = var5;
      } else {
         var4.nextOnTile = var5;
      }

      var5.prevOnTile = var5.nextOnTile = null;
      var5.linkIndex = var2;
      int[] var10000 = Entity.baseVisitedTiles;
      var10000[var1] |= 1 << var0;
      byte[] var6 = Render.mapFlags;
      var6[var2] = (byte)(var6[var2] | 1);
   }

   public static final void remove(Entity var0) {
      if ((var0.info & '\uffff') != 0) {
         int[] var10000 = Render.mapSpriteInfo;
         int var10001 = var0.getSprite();
         var10000[var10001] |= 65536;
      }

      if (0 != (var0.info & 1048576)) {
         unlinkEntity(var0);
      }

      Player.facingEntity = null;
   }

   public static final void trace(int var0, int var1, int var2, int var3, Entity var4, int var5, int var6) {
      trace(var0, var1, -1, var2, var3, -1, var4, var5, var6);
   }

   public static final void trace(int var0, int var1, int var2, int var3, int var4, int var5, Entity var6, int var7, int var8) {
      int[] var9 = tracePoints;
      int[] var10 = traceBoundingBox;
      int[] var11 = traceFracs;
      Entity[] var12 = traceEntities;
      traceEntity = null;
      numTraceEntities = 0;
      var9[0] = var0;
      var9[1] = var1;
      var9[2] = var3;
      var9[3] = var4;
      var10[0] = Math.max(Math.min(var0 - var8, var3 - var8), 0);
      var10[1] = Math.max(Math.min(var1 - var8, var4 - var8), 0);
      var10[2] = Math.min(Math.max(var0 + var8, var3 + var8), 2047);
      var10[3] = Math.min(Math.max(var1 + var8, var4 + var8), 2047);
      int var13 = var10[0] >> 6;

      int var15;
      int var16;
      for(int var14 = (var10[2] >> 6) + 1; var13 < var14; ++var13) {
         var15 = var10[1] >> 6;

         for(var16 = (var10[3] >> 6) + 1; var15 < var16; ++var15) {
            Entity var17 = entityDb[var13 + 32 * var15];
            if (var17 != null) {
               for(; var17 != null; var17 = var17.nextOnTile) {
                  if (var17 != var6 && 0 != (var7 & 1 << var17.def.eType) && var17.def.eType != 0) {
                     int var18 = var17.getSprite();
                     int var19;
                     int var20;
                     int var21;
                     if (null != var17.monster) {
                        if ((var17.monster.goalFlags & 1) != 0) {
                           var19 = 32 + (var17.monster.goalX << 6);
                           var20 = 32 + (var17.monster.goalY << 6);
                           var21 = Render.mapSprites[Render.S_Z + var18];
                        } else {
                           var19 = Render.mapSprites[Render.S_X + var18];
                           var20 = Render.mapSprites[Render.S_Y + var18];
                           var21 = Render.mapSprites[Render.S_Z + var18];
                        }
                     } else if (var17.def.eType == 1) {
                        var19 = Canvas.destX;
                        var20 = Canvas.destY;
                        var21 = Canvas.destZ;
                     } else {
                        var19 = Render.mapSprites[Render.S_X + var18];
                        var20 = Render.mapSprites[Render.S_Y + var18];
                        var21 = Render.mapSprites[Render.S_Z + var18];
                     }

                     int var23;
                     int var24;
                     if (var18 != -1 && 0 != (Render.mapSpriteInfo[var18] & 251658240)) {
                        int var27 = var19;
                        var23 = var20;
                        if (0 != (Render.mapSpriteInfo[var18] & 50331648)) {
                           var19 -= 32;
                           var27 += 32;
                        } else {
                           var20 -= 32;
                           var23 += 32;
                        }

                        Render.traceLine[0] = var19;
                        Render.traceLine[1] = var20;
                        Render.traceLine[2] = var27;
                        Render.traceLine[3] = var23;
                        var24 = Render.CapsuleToLineTrace(var9, var8 * var8, Render.traceLine);
                        if (var24 < 16384) {
                           var11[numTraceEntities] = var24;
                           var12[numTraceEntities++] = var17;
                        }
                     } else {
                        short var22 = 625;
                        if (var17.def.eType == 8) {
                           var22 = 256;
                        }

                        var23 = Render.CapsuleToCircleTrace(var9, var8 * var8, var19, var20, var22);
                        if (var23 < 16384) {
                           if (var2 >= 0 && var5 >= 0) {
                              var24 = var2 + ((var5 - var2) * var23 >> 14);
                              var24 -= var21;
                              if (var24 > -(32 + var8) && var24 < 32 + var8) {
                                 var11[numTraceEntities] = var23;
                                 var12[numTraceEntities++] = var17;
                              }
                           } else {
                              var11[numTraceEntities] = var23;
                              var12[numTraceEntities++] = var17;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (0 != (var7 & 1)) {
         var15 = Render.traceWorld(0, var9, var8, var10, var7);
         if (var15 < 16384) {
            var11[numTraceEntities] = var15;
            var12[numTraceEntities++] = entities[0];
            traceCollisionX = var0 + (var15 * (var3 - var0) >> 14);
            traceCollisionY = var1 + (var15 * (var4 - var1) >> 14);
            traceCollisionZ = var2 + (var15 * (var5 - var2) >> 14);
         } else {
            traceCollisionX = var3;
            traceCollisionY = var4;
            traceCollisionZ = var5;
         }
      }

      if (numTraceEntities > 0) {
         for(var15 = 0; var15 < numTraceEntities - 1; ++var15) {
            for(var16 = 0; var16 < numTraceEntities - 1 - var15; ++var16) {
               if (var11[var16 + 1] < var11[var16]) {
                  int var25 = var11[var16];
                  var11[var16] = var11[var16 + 1];
                  var11[var16 + 1] = var25;
                  Entity var26 = var12[var16];
                  var12[var16] = var12[var16 + 1];
                  var12[var16 + 1] = var26;
               }
            }
         }

         traceEntity = var12[0];
      }

   }

   public static final void loadMapEntities() {
      interpolatingMonsters = false;
      monstersTurn = 0;

      int var0;
      for(var0 = 0; var0 < 4; ++var0) {
         openDoors[var0] = null;
      }

      watchLine = null;
      Player.setPickUpWeapon(17);

      for(var0 = 0; var0 < 4; ++var0) {
         placedBombs[var0] = 0;
      }

      for(var0 = 0; var0 < 275; ++var0) {
         entities[var0].reset();
      }

      for(var0 = 0; var0 < levelVars.length; ++var0) {
         levelVars[var0] = 0;
      }

      for(var0 = 0; var0 < 32; ++var0) {
         Entity.baseVisitedTiles[var0] = 0;
      }

      secretActive = false;
      numMonsters = 0;
      numLerpSprites = 0;

      for(var0 = 0; var0 < 16; ++var0) {
         lerpSprites[var0].hSprite = 0;
      }

      numScriptThreads = 0;

      for(var0 = 0; var0 < 20; ++var0) {
         scriptThreads[var0].reset();
      }

      numEntities = 0;
      Entity var1 = entities[numEntities++];
      var1.def = EntityDef.find(0, 0);
      var1.name = (short)(var1.def.name | 2048);
      var1 = entities[numEntities++];
      var1.def = EntityDef.find(1, 0);
      var1.name = (short)(var1.def.name | 2048);
      var1.info = 131072;
      EntityDef var3 = EntityDef.find(12, 0);
      EntityDef var4 = EntityDef.find(12, 1);
      EntityDef var5 = EntityDef.find(13, 0);
      int var6 = 0;

      int[] var10000;
      for(var0 = 0; var0 < Render.numMapSprites; ++var0) {
         int var7 = Render.mapSpriteInfo[var0] & 255;
         if ((Render.mapSpriteInfo[var0] & 4194304) != 0) {
            var7 += 257;
         }

         int var8 = Render.mediaMappings[var7 + 1] - Render.mediaMappings[var7];
         if (var7 == 234) {
            var8 = 4;
         }

         if (var7 == 150 || var7 == 136 || var7 == 181 || var7 == 234 || var7 == 130 || var7 == 247) {
            if (var7 == 181) {
               --var8;
            }

            var10000 = Render.mapSpriteInfo;
            var10000[var0] &= -65281;
            var10000 = Render.mapSpriteInfo;
            var10000[var0] |= var8 << 8 | 524288;
         }

         if ((Render.mapSpriteInfo[var0] & 2097152) != 0) {
            var10000 = Render.mapSpriteInfo;
            var10000[var0] &= -2097153;
         } else {
            int var9 = Render.mapSprites[Render.S_X + var0];
            int var10 = Render.mapSprites[Render.S_Y + var0];
            if (var7 == 247) {
               Entity.HARBINGER_BLOOD_POOLX = var9;
               Entity.HARBINGER_BLOOD_POOLY = var10;
            }

            EntityDef var11 = EntityDef.lookup(var7);
            if (var7 >= 1 && var7 < 16) {
               var10000 = Render.mapSpriteInfo;
               var10000[var0] |= 512;
            }

            if (var7 == 131 && ((Render.mapSpriteInfo[var0] & 33554432) != 0 || (Render.mapSpriteInfo[var0] & 16777216) != 0)) {
               var10000 = Render.mapSpriteInfo;
               var10000[var0] &= -131073;
            }

            if ((Render.mapSpriteInfo[var0] & 251658240) != 0 && ((var9 & 63) == 0 || (var10 & 63) == 0)) {
               if ((Render.mapSpriteInfo[var0] & 67108864) != 0) {
                  ++var9;
               } else if ((Render.mapSpriteInfo[var0] & 33554432) != 0) {
                  ++var10;
               } else if ((Render.mapSpriteInfo[var0] & 16777216) != 0) {
                  --var10;
               } else if ((Render.mapSpriteInfo[var0] & 134217728) != 0) {
                  --var9;
               }
            }

            short var2;
            if (var11 != null) {
               if (numEntities == 275) {
                  App.Error(35);
                  return;
               }

               var2 = (short)numEntities;
               var1 = entities[numEntities++];
               var1.info = var0 + 1 & '\uffff';
               var1.def = var11;
               if (var7 == 147) {
                  mixingStations[numStations * 4 + 0] = var0;
                  mixingStations[numStations * 4 + 1] = 0;
                  mixingStations[numStations * 4 + 2] = 0;
                  mixingStations[numStations * 4 + 3] = 0;
                  ++numStations;
               } else if (var1.def.eType == 2) {
                  if (numMonsters == 80) {
                     App.Error(37);
                     return;
                  }

                  var1.monster = entityMonsters[numMonsters++];
                  var1.monster.reset();
                  var10000 = Render.mapSpriteInfo;
                  var10000[var0] &= -251658241;
                  if ((App.nextByte() & 1) == 0 && (var1.def.eSubType < 11 || var1.def.eSubType > 14)) {
                     var10000 = Render.mapSpriteInfo;
                     var10000[var0] |= 131072;
                  }

                  var1.info |= 262144;
                  deactivate(var1);
               } else if (var1.def.eType == 10 && var1.def.eSubType != 8 && var1.def.eSubType != 10) {
                  ++numDestroyableObj;
               }

               var1.initspawn();
               Render.mapSprites[Render.S_ENT + var0] = var2;
               if ((Render.mapSpriteInfo[var0] & 65536) == 0) {
                  linkEntity(var1, var9 >> 6, var10 >> 6);
               }

               if (var7 >= 60 && var7 <= 63) {
                  var10000 = Render.mapSpriteInfo;
                  var10000[var0] |= 65536;
               }

               ++var6;
            } else if ((Render.mapSpriteInfo[var0] & 8388608) != 0) {
               if (numEntities == 275) {
                  App.Error(35);
                  return;
               }

               var2 = (short)numEntities;
               var1 = entities[numEntities++];
               var1.info = var0 + 1 & '\uffff';
               if (var7 != 166 && var7 != 168 && var7 != 194) {
                  var1.def = var3;
               } else {
                  var1.def = var5;
               }

               var1.name = (short)(var1.def.name | 2048);
               Render.mapSprites[Render.S_ENT + var0] = var2;
               if ((Render.mapSpriteInfo[var0] & 65536) == 0) {
                  linkEntity(var1, var9 >> 6, var10 >> 6);
               }

               ++var6;
            }

            if ((var0 & 15) == 15) {
               Canvas.updateLoadingBar(false);
            }
         }
      }

      firstDropIndex = numEntities;

      for(var0 = 0; var0 < 16; ++var0) {
         if (numEntities == 275) {
            App.Error(35);
            return;
         }

         var1 = entities[numEntities++];
         var1.info = Render.firstDropSprite + var0 + 1 & '\uffff';
         var10000 = Render.mapSpriteInfo;
         int var10001 = Render.dropSprites[var0];
         var10000[var10001] |= 65536;
         ++var6;
      }

      for(var0 = 0; var0 < 1024; ++var0) {
         if ((Render.mapFlags[var0] & 1) != 0) {
            Entity var12 = entityDb[var0];

            boolean var13;
            for(var13 = true; var12 != null; var12 = var12.nextOnTile) {
            }

            if (var13) {
               linkWorldEntity(var0 % 32, var0 / 32);
            }
         }
      }

   }

   public static final void loadTableCamera(int var0, int var1) {
      Object var2 = null;
      Object var3 = null;
      short[] var8 = new short[Resource.getNumTableShorts(var0)];
      byte[] var9 = new byte[Resource.getNumTableBytes(var1)];

      try {
         Resource.beginTableLoading();
         Resource.loadShortTable(var8, var0);
         Resource.loadByteTable(var9, var1);
         Resource.finishTableLoading();
      } catch (Exception var7) {
         App.Error(var7, -1);
      }

      mayaCameras = new MayaCamera[1];
      mayaCameras[0] = new MayaCamera();
      mayaCameras[0].isTableCam = true;
      byte var4 = 0;
      int var10 = var4 + 1;
      totalMayaCameraKeys = var8[var4];
      totalMayaCameras = 1;
      mayaCameras[0].sampleRate = var8[var10++];
      MayaCamera.posShift = 4 - var8[var10++];
      MayaCamera.angleShift = 10 - var8[var10++];
      int var5 = totalMayaCameraKeys * 7;
      mayaCameraKeys = new short[var5];
      System.arraycopy(var8, var10, mayaCameraKeys, 0, var5);
      var10 += var5;
      var5 = totalMayaCameraKeys * 6;
      mayaTweenIndices = new short[var5];
      System.arraycopy(var8, var10, mayaTweenIndices, 0, var5);
      var10 += var5;
      ofsMayaTween[0] = 0;
      short var6 = var8[var10++];
      ofsMayaTween[1] = var6;
      var6 += var8[var10++];
      ofsMayaTween[2] = var6;
      var6 += var8[var10++];
      ofsMayaTween[3] = var6;
      var6 += var8[var10++];
      ofsMayaTween[4] = var6;
      var6 += var8[var10++];
      ofsMayaTween[5] = var6;
      mayaCameraTweens = new byte[var9.length];
      System.arraycopy(var9, 0, mayaCameraTweens, 0, var9.length);
      mayaCameras[0].keyOffset = 0;
      mayaCameras[0].numKeys = totalMayaCameraKeys;
      mayaCameras[0].complete = false;
      mayaCameras[0].keyThreadResumeCount = totalMayaCameraKeys;
      setKeyOffsets();
      var2 = null;
      var3 = null;
      System.gc();
   }

   public static final void setKeyOffsets() {
      OFS_MAYAKEY_X = 0;
      OFS_MAYAKEY_Y = totalMayaCameraKeys;
      OFS_MAYAKEY_Z = totalMayaCameraKeys * 2;
      OFS_MAYAKEY_PITCH = totalMayaCameraKeys * 3;
      OFS_MAYAKEY_YAW = totalMayaCameraKeys * 4;
      OFS_MAYAKEY_ROLL = totalMayaCameraKeys * 5;
      OFS_MAYAKEY_MS = totalMayaCameraKeys * 6;
   }

   public static final void loadMayaCameras(InputStream var0) {
      short[] var1 = mayaCameraKeys;
      byte[] var2 = mayaCameraTweens;
      short[] var3 = mayaTweenIndices;
      int var4 = 0;
      short[] var5 = new short[6];
      int var6 = 0;
      int[] var7 = new int[6];
      boolean var8 = false;
      byte var9 = 0;

      try {
         for(int var15 = 0; var15 < totalMayaCameras; ++var15) {
            Resource.read(var0, 3);
            mayaCameras[var15].numKeys = Resource.shiftByte();
            mayaCameras[var15].sampleRate = Resource.shiftShort();
            int var10 = mayaCameras[var15].numKeys * 7 * 2;
            Resource.read(var0, var10);
            mayaCameras[var15].keyOffset = var4;

            int var14;
            for(var14 = 0; var14 < 7; ++var14) {
               for(int var11 = 0; var11 < mayaCameras[var15].numKeys; ++var11) {
                  var1[totalMayaCameraKeys * var14 + var4 + var11] = Resource.shiftShort();
               }
            }

            var4 += mayaCameras[var15].numKeys;
            var10 = mayaCameras[var15].numKeys * 6 * 2;
            Resource.read(var0, var10);
            boolean var16 = false;

            for(var14 = 0; var14 < var10 / 2; ++var6) {
               short var17 = Resource.shiftShort();
               if (var17 >= 0) {
                  var17 += var5[var14 % 6];
               }

               var3[var6] = var17;
               ++var14;
            }

            var10 = 0;
            Resource.read(var0, 12);

            for(var14 = 0; var14 < 6; ++var14) {
               var7[var14] = Resource.shiftShort();
               var10 += var7[var14];
            }

            Resource.readMarker(var0, -559038737);
            Resource.read(var0, var10);

            for(var14 = 0; var14 < 6; ++var14) {
               for(int var12 = 0; var12 < var7[var14]; ++var5[var14]) {
                  var2[ofsMayaTween[var14] + var5[var14]] = Resource.shiftByte();
                  ++var12;
               }
            }
         }
      } catch (IOException var13) {
         App.Error(new Exception("Error loading Maya Camera " + var9), 103);
      }

   }

   public static final void unloadMapData() {
      activeSprites = 0;
      animatingEffects = 0;
      activePropogators = 0;
      cinematicWeapon = -1;
      activeCameraView = false;
      activeCamera = null;
      Player.facingEntity = null;
      Combat.curTarget = null;
      Combat.curAttacker = null;
      Hud.lastTarget = null;
      boolean var0 = false;

      int var1;
      for(var1 = 0; var1 < 48; ++var1) {
         if (0 != (gsprites[var1].flags & 1)) {
            gsprite_destroy(gsprites[var1]);
         }
      }

      inactiveMonsters = null;
      activeMonsters = null;
      combatMonsters = null;
      keycode[0] = 0;

      for(var1 = 0; var1 < 1024; ++var1) {
         entityDb[var1] = null;
      }

      for(var1 = 0; var1 < numEntities; ++var1) {
         entities[var1].reset();
      }

      for(var1 = 0; var1 < 64; ++var1) {
         entityDeathFunctions[var1] = -1;
      }

      numStations = 0;
      totalMayaCameraKeys = 0;
      totalMayaCameras = 0;
      mayaCameraKeys = null;
      mayaCameras = null;
      mayaCameraTweens = null;
      mayaTweenIndices = null;
      Combat.weaponDown = false;
      numDestroyableObj = 0;
      destroyedObj = 0;
      lootFound = 0;
      Canvas.showingLoot = false;
      Canvas.isChickenKicking = false;
   }

   public static final boolean touchTile(int var0, int var1, boolean var2) {
      boolean var3 = false;

      Entity var5;
      for(Entity var4 = findMapEntity(var0, var1); var4 != null; var4 = var5) {
         var5 = var4.nextOnTile;
         if (var2 || var4.def.eType == 8) {
            var4.touched();
            var3 = true;
         }
      }

      return var3;
   }

   private static final void prepareMonsters() {
      int var0 = Canvas.loadMapID - 1;
      if (!isLoaded && 0 != (Player.completedLevels & 1 << var0) && Player.hasAllKills(var0)) {
         Player.fillMonsterStats();
         int var3 = 0;
         int var2 = Player.monsterStats[1];
         int var1 = var2 - Player.monsterStats[0];

         for(int var4 = 0; var4 < numEntities; ++var4) {
            Entity var5 = entities[var4];
            if (var5.monster != null) {
               int var6 = var5.getSprite();
               EntityMonster var7 = var5.monster;
               if (0 != (var5.info & 16842752) && 0 == (var7.flags & 144) && var1 < var2 / 2 && var3 < 8 && App.nextByte() <= 100 && findMapEntity(Render.mapSprites[Render.S_X + var6], Render.mapSprites[Render.S_Y + var6], 15535) == null) {
                  var5.resurrect(Render.mapSprites[Render.S_X + var6], Render.mapSprites[Render.S_Y + var6], 32);
                  var5.info &= -4194305;
                  ++var1;
                  ++var3;
               }
            }
         }
      }

   }

   public static final Entity findMapEntity(int var0, int var1) {
      var0 >>= 6;
      var1 >>= 6;
      return var0 >= 0 && var0 < 32 && var1 >= 0 && var1 < 32 ? entityDb[var1 * 32 + var0] : null;
   }

   public static final Entity findMapEntity(int var0, int var1, int var2) {
      if ((var2 & 2) != 0 && var0 >> 6 == Canvas.destX >> 6 && var1 >> 6 == Canvas.destY >> 6) {
         return entities[1];
      } else {
         for(Entity var3 = findMapEntity(var0, var1); var3 != null; var3 = var3.nextOnTile) {
            if ((var2 & 1 << var3.def.eType) != 0) {
               return var3;
            }
         }

         return null;
      }
   }

   public static final void activate(Entity var0) {
      activate(var0, true, false, true, false);
   }

   public static final void activate(Entity var0, boolean var1, boolean var2, boolean var3, boolean var4) {
      EntityMonster var5 = var0.monster;
      if (!isUnderWater() || var4) {
         if (((Render.mapSpriteInfo[var0.getSprite()] & '\uff00') >> 8 & 240) != 16 || Render.shotsFired) {
            if (!var2 || var0.distFrom(Canvas.viewX, Canvas.viewY) <= Combat.tileDistances[3]) {
               var0.info |= 4194304;
               if (!Player.noclip) {
                  if ((var0.info & 262144) == 0) {
                     int var6 = var0.getSprite();
                     Render.mapSpriteInfo[var6] = Render.mapSpriteInfo[var6] & -65281 | 0;
                     if (var5.nextOnList != null) {
                        if (var0 == inactiveMonsters && var5.nextOnList == inactiveMonsters) {
                           inactiveMonsters = null;
                        } else {
                           if (var0 == inactiveMonsters) {
                              inactiveMonsters = var5.nextOnList;
                           }

                           var5.nextOnList.monster.prevOnList = var5.prevOnList;
                           var5.prevOnList.monster.nextOnList = var5.nextOnList;
                        }
                     }

                     if (activeMonsters == null) {
                        activeMonsters = var5.prevOnList = var5.nextOnList = var0;
                     } else {
                        var5.prevOnList = activeMonsters.monster.prevOnList;
                        var5.nextOnList = activeMonsters;
                        activeMonsters.monster.prevOnList.monster.nextOnList = var0;
                        activeMonsters.monster.prevOnList = var0;
                     }

                     var0.info |= 262144;
                     if (var1 && 0 != (var5.flags & 2)) {
                        executeStaticFunc(9);
                        var5.flags = (short)(var5.flags & -3);
                     }

                  }
               }
            }
         }
      }
   }

   public static final void killAll() {
      for(int var0 = 0; var0 < numEntities; ++var0) {
         Entity var1 = entities[var0];
         if (var1.def != null && var1.def.eType == 2 && var1.def.eSubType < 11 && (var1.info & 131072) != 0 && (var1.monster.flags & 128) == 0) {
            var1.died(false, (Entity)null);
         }
      }

   }

   public static final void deactivate(Entity var0) {
      EntityMonster var1 = var0.monster;
      if ((var0.info & 262144) != 0) {
         if (var1.nextOnList != null) {
            if (var0 == activeMonsters && var1.nextOnList == activeMonsters) {
               activeMonsters = null;
            } else {
               if (var0 == activeMonsters) {
                  activeMonsters = var1.nextOnList;
               }

               var1.nextOnList.monster.prevOnList = var1.prevOnList;
               var1.prevOnList.monster.nextOnList = var1.nextOnList;
            }
         }

         if (inactiveMonsters == null) {
            inactiveMonsters = var1.prevOnList = var1.nextOnList = var0;
         } else {
            var1.prevOnList = inactiveMonsters.monster.prevOnList;
            var1.nextOnList = inactiveMonsters;
            inactiveMonsters.monster.prevOnList.monster.nextOnList = var0;
            inactiveMonsters.monster.prevOnList = var0;
         }

         var0.info &= -262145;
      }
   }

   public static final void UpdatePlayerVars() {
      viewX = Canvas.viewX;
      viewY = Canvas.viewY;
      viewAngle = Canvas.viewAngle;
      destX = Canvas.destX;
      destY = Canvas.destY;
      destAngle = Canvas.destAngle;
      viewSin = Canvas.viewSin;
      viewCos = Canvas.viewCos;
      viewStepX = Canvas.viewStepX;
      viewStepY = Canvas.viewStepY;
      viewRightStepX = Canvas.viewRightStepX;
      viewRightStepY = Canvas.viewRightStepY;
   }

   private static final void monsterAI() {
      if (!disableAI) {
         if (!monstersUpdated) {
            monstersUpdated = true;
            combatMonsters = null;
            if (activeMonsters != null) {
               boolean var0 = false;
               Entity var1 = activeMonsters;

               Entity var2;
               do {
                  var2 = var1.monster.nextOnList;
                  if (0 == (var1.monster.monsterEffects & 2) && (monstersTurn == 1 || monstersTurn == 2 && var1.isHasteResistant())) {
                     var1.aiThink(false);
                     if ((var1.monster.goalFlags & 1) != 0) {
                        var0 = true;
                     }
                  }

                  var1 = var2;
               } while(var2 != activeMonsters && activeMonsters != null);

               while(var0) {
                  var0 = false;

                  for(var1 = combatMonsters; var1 != null; var1 = var2) {
                     var2 = var1.monster.nextAttacker;
                     if (!var1.aiIsAttackValid()) {
                        var1.undoAttack();
                        var1.aiThink(false);
                        if ((var1.monster.goalFlags & 1) != 0) {
                           var0 = true;
                        }
                     }
                  }

                  var1 = activeMonsters;

                  while(true) {
                     var2 = var1.monster.nextOnList;
                     if (0 == (var1.monster.flags & 1024) && 0 == (var1.monster.monsterEffects & 2) && (monstersTurn == 1 || monstersTurn == 2 && var1.isHasteResistant()) && (var1.def.eSubType == 2 || var1.def.eSubType == 4) && var1.monster.goalType == 1 && 0 != (var1.monster.goalFlags & 2)) {
                        int var3 = var1.getSprite();
                        trace(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], 32 + (var1.monster.goalX << 6), 32 + (var1.monster.goalY << 6), var1, 15535, 25);
                        if (traceEntity != null && traceEntity.def.eType != 1) {
                           int[] var10000 = Render.mapSpriteInfo;
                           var10000[var3] &= -65281;
                           var1.monster.resetGoal();
                           var1.aiThink(false);
                           if ((var1.monster.goalFlags & 1) != 0) {
                              var0 = true;
                           }
                        }
                     }

                     var1 = var2;
                     if (var2 == activeMonsters || activeMonsters == null) {
                        break;
                     }
                  }
               }
            }

         }
      }
   }

   public static final void monsterLerp() {
      boolean var0 = false;
      boolean var1 = false;
      if (interpolatingMonsters) {
         if (activeMonsters != null) {
            Entity var2 = activeMonsters;

            do {
               if ((var2.monster.goalFlags & 1) != 0) {
                  var0 = true;
                  if (!var1 && Render.checkTileVisibilty(var2.monster.goalX, var2.monster.goalY)) {
                     var1 = true;
                  }
               }

               var2 = var2.monster.nextOnList;
            } while(var2 != activeMonsters);
         }

         if (var0 && var1) {
            Canvas.invalidateRect();
         }

         interpolatingMonsters = var0;
      }
   }

   public static final void spawnPlayer() {
      int var0;
      int var1;
      int var2;
      if (spawnParam == 0) {
         if (Canvas.loadType == 3) {
            var0 = 3;
            var1 = 15;
            var2 = 6;
         } else {
            var0 = Render.mapSpawnIndex % 32;
            var1 = Render.mapSpawnIndex / 32;
            var2 = Render.mapSpawnDir;
         }
      } else {
         var0 = spawnParam & 31;
         var1 = spawnParam >> 5 & 31;
         var2 = spawnParam >> 10 & 255;
         spawnParam = 0;
      }

      int var3 = var2 << 7 & 1023;
      Canvas.viewX = Canvas.destX = var0 * 64 + 32;
      Canvas.viewY = Canvas.destY = var1 * 64 + 32;
      Canvas.viewZ = Canvas.destZ = Render.getHeight(Canvas.viewX, Canvas.viewY) + 36;
      Canvas.destAngle = var3;
      Canvas.viewAngle = var3;
      Player.relink();
      lastTurnTime = App.time;
      Canvas.invalidateRect();
   }

   public static final void eventFlagsForMovement(int var0, int var1, int var2, int var3) {
      int var4 = var2 - var0;
      int var5 = var3 - var1;
      eventFlags[0] = 2;
      eventFlags[1] = 1;
      int[] var10000;
      if (var4 > 0) {
         if (var5 < 0) {
            var10000 = eventFlags;
            var10000[0] |= 32;
            var10000 = eventFlags;
            var10000[1] |= 512;
         } else if (var5 > 0) {
            var10000 = eventFlags;
            var10000[0] |= 2048;
            var10000 = eventFlags;
            var10000[1] |= 128;
         } else {
            var10000 = eventFlags;
            var10000[0] |= 16;
            var10000 = eventFlags;
            var10000[1] |= 256;
         }
      } else if (var4 < 0) {
         if (var5 < 0) {
            var10000 = eventFlags;
            var10000[0] |= 128;
            var10000 = eventFlags;
            var10000[1] |= 2048;
         } else if (var5 > 0) {
            var10000 = eventFlags;
            var10000[0] |= 512;
            var10000 = eventFlags;
            var10000[1] |= 32;
         } else {
            var10000 = eventFlags;
            var10000[0] |= 256;
            var10000 = eventFlags;
            var10000[1] |= 16;
         }
      } else if (var5 > 0) {
         var10000 = eventFlags;
         var10000[0] |= 1024;
         var10000 = eventFlags;
         var10000[1] |= 64;
      } else {
         var10000 = eventFlags;
         var10000[0] |= 64;
         var10000 = eventFlags;
         var10000[1] |= 1024;
      }

   }

   public static final void givemap(int var0, int var1, int var2, int var3) {
      int var4;
      for(var4 = 0; var4 < Render.numLines; ++var4) {
         byte[] var10000 = Render.lineFlags;
         var10000[var4 >> 1] = (byte)(var10000[var4 >> 1] | 8 << ((var4 & 1) << 2));
      }

      var4 = Render.numMapSprites;

      int var5;
      int var6;
      for(var5 = 0; var5 < var4; ++var5) {
         var6 = Render.mapSprites[Render.S_X + var5] >> 6;
         int var7 = Render.mapSprites[Render.S_Y + var5] >> 6;
         if (var6 >= var0 && var6 < var0 + var2 && var7 >= var1 && var7 < var1 + var3) {
            int[] var9 = Render.mapSpriteInfo;
            var9[var5] |= 2097152;
         }
      }

      for(var5 = var1; var5 < var1 + var3; ++var5) {
         for(var6 = var0; var6 < var0 + var2; ++var6) {
            byte var8 = Render.mapFlags[var5 * 32 + var6];
            if ((var8 & 1) == 0) {
               Render.mapFlags[var5 * 32 + var6] = (byte)(var8 | 128);
            }
         }
      }

   }

   public static final boolean performDoorEvent(int var0, Entity var1, int var2) {
      return performDoorEvent(var0, var1, var2, false);
   }

   public static final boolean performDoorEvent(int var0, Entity var1, int var2, boolean var3) {
      return performDoorEvent(var0, (ScriptThread)null, var1, var2, var3);
   }

   public static final boolean performDoorEvent(int var0, ScriptThread var1, Entity var2, int var3, boolean var4) {
      int var5 = var2.getSprite();
      int var6 = Render.mapSpriteInfo[var5];
      int var7 = var6 & 255;
      boolean var8 = (var2.info & 1048576) == 0;
      if ((var6 & 4194304) != 0) {
         var7 += 257;
      }

      boolean var9 = var7 >= 271 && var7 < 281;
      if (var2.def.eSubType == 1) {
         return false;
      } else if (var0 == 0 && var8 && var9) {
         updatePlayerDoors(var2, true);
         return false;
      } else if (var0 == 1 && !var8) {
         return false;
      } else {
         var8 = !var8;
         if (!var8 && var9) {
            for(Entity var10 = findMapEntity(var2.linkIndex % 32 << 6, var2.linkIndex / 32 << 6); var10 != null; var10 = var10.nextOnTile) {
               if (var10.def.eType == 2 && var10.def.eSubType != 15) {
                  watchLine = var2;
                  return false;
               }
            }

            if (watchLine == var2) {
               watchLine = null;
            }

            linkEntity(var2, var2.linkIndex % 32, var2.linkIndex / 32);
         }

         LerpSprite var14 = allocLerpSprite(var1, var5, true);
         Render.mapSpriteInfo[var5] = var6 & -65537;
         if (var4) {
            secretActive = true;
            var14.flags |= 256;
         } else {
            var14.flags |= 64;
            int[] var10000 = Render.mapSpriteInfo;
            var10000[var5] |= Integer.MIN_VALUE;
         }

         var14.dstX = var14.srcX = Render.mapSprites[Render.S_X + var5];
         var14.dstY = var14.srcY = Render.mapSprites[Render.S_Y + var5];
         var14.dstZ = var14.srcZ = Render.mapSprites[Render.S_Z + var5];
         var14.dstScale = var14.srcScale = Render.mapSprites[Render.S_SCALEFACTOR + var5];
         int var11 = 32;
         if (var0 == 1) {
            var11 = -var11;
         }

         if (0 != (var6 & 50331648)) {
            if (var4) {
               if ((var6 & 16777216) != 0) {
                  var14.dstY += var11;
               } else {
                  var14.dstY -= var11;
               }
            } else if ((var2.def.parm & 1) == 0) {
               var14.dstX += var11;
            }
         } else if (0 != (var6 & 201326592)) {
            if (var4) {
               if ((var6 & 134217728) != 0) {
                  var14.dstX += var11;
               } else {
                  var14.dstX -= var11;
               }
            } else if ((var2.def.parm & 1) == 0) {
               var14.dstY += var11;
            }
         }

         var14.startTime = App.gameTime;
         var14.travelTime = 750;
         var14.flags |= 48;
         if (var0 == 1) {
            var14.flags &= -81;
            var14.flags |= 128;
            var14.dstScale = 64;
            if (!var4 && var9) {
               updatePlayerDoors(var2, false);
            }
         } else if (var0 == 0 && !var4) {
            if (var9) {
               updatePlayerDoors(var2, true);
            }

            if ((var2.def.parm & 1) != 0) {
               int var12 = var14.srcX - (Render.viewX >> 4);
               int var13 = var14.srcY - (Render.viewY >> 4);
               if (var12 <= 0 && var13 >= 0) {
                  var14.dstScale = 0;
               } else {
                  var14.dstScale <<= 1;
               }
            } else {
               var14.dstScale = 0;
            }
         }

         Render.mapSprites[Render.S_SCALEFACTOR + var5] = (short)((byte)var14.srcScale);
         Render.mapSprites[Render.S_X + var5] = (short)var14.srcX;
         Render.mapSprites[Render.S_Y + var5] = (short)var14.srcY;
         Render.mapSprites[Render.S_Z + var5] = (short)var14.srcZ;
         Sound.playSound(2);
         if (var9 && (var14.flags & 128) == 0 && (var2.def.parm & 1) == 0) {
            Render.mapSpriteInfo[var5] = Render.mapSpriteInfo[var5] & -65281 | 256;
         }

         if (var3 == 0 || Canvas.state == 6 || var3 == 2 && Render.cullBoundingBox(var14.srcX + var14.dstX >> 1, var14.srcY + var14.dstY >> 1, true)) {
            snapLerpSprites(var5);
         }

         return true;
      }
   }

   public static final void lerpSpriteAsDoor(int var0, int var1, ScriptThread var2) {
      LerpSprite var3 = allocLerpSprite(var2, var0, false);
      int[] var10000 = Render.mapSpriteInfo;
      var10000[var0] &= -65537;
      int var4 = Render.mapSpriteInfo[var0];
      int var5 = 64;
      if (var1 == 1) {
         var5 = -var5;
      }

      var3.flags |= 64;
      var10000 = Render.mapSpriteInfo;
      var10000[var0] |= Integer.MIN_VALUE;
      Render.mapSprites[Render.S_SCALEFACTOR + var0] = 256;
      var3.dstX = var3.srcX = Render.mapSprites[Render.S_X + var0];
      var3.dstY = var3.srcY = Render.mapSprites[Render.S_Y + var0];
      var3.dstZ = var3.srcZ = Render.mapSprites[Render.S_Z + var0];
      var3.dstScale = var3.srcScale = 64;
      if (0 != (var4 & 50331648)) {
         var3.dstX += var5;
      } else if (0 != (var4 & 201326592)) {
         var3.dstY += var5;
      }

      var3.startTime = App.gameTime;
      var3.travelTime = 750;
      var3.flags |= 48;
      if (var1 == 1) {
         var3.flags &= -81;
         var3.flags |= 128;
      }

      Render.mapSprites[Render.S_X + var0] = (short)var3.srcX;
      Render.mapSprites[Render.S_Y + var0] = (short)var3.srcY;
      Render.mapSprites[Render.S_Z + var0] = (short)var3.srcZ;
   }

   private static final void updatePlayerDoors(Entity var0, boolean var1) {
      boolean var2 = false;

      int var3;
      for(var3 = 0; var3 < 6; ++var3) {
         if (var1 && openDoors[var3] == null || !var1 && openDoors[var3] == var0) {
            var2 = true;
            break;
         }
      }

      if (var2) {
         if (var1) {
            openDoors[var3] = var0;
         } else {
            openDoors[var3] = null;
         }
      }

   }

   public static final boolean CanCloseDoor(Entity var0) {
      int var1 = (var0.linkIndex % 32 << 6) + 32;
      int var2 = (var0.linkIndex / 32 << 6) + 32;
      int var3 = var0.getSprite();
      if (null != findMapEntity(var1, var2, 6)) {
         return false;
      } else {
         byte var4 = 64;
         byte var5 = 64;
         if ((Render.mapSpriteInfo[var3] & 50331648) == 0) {
            var5 = 0;
         } else {
            var4 = 0;
         }

         if (null != findMapEntity(var1 + var4, var2 + var5, 6)) {
            return false;
         } else {
            return null == findMapEntity(var1 - var4, var2 - var5, 6);
         }
      }
   }

   public static final void advanceTurn() {
      queueAdvanceTurn = false;
      if (interpolatingMonsters) {
         App.Error(95);
      }

      boolean var0 = false;
      if (Player.statusEffects[2] > 0) {
         int var10002 = Player.statusEffects[20]++;
         if (Player.statusEffects[20] % 2 == 0) {
            var0 = true;
         }
      } else {
         var0 = true;
      }

      Canvas.pushedWall = false;
      Player.advanceTurn();
      updateBombs();
      if (var0) {
         monstersTurn = 1;
      } else {
         monstersTurn = 2;
      }

      monstersUpdated = false;
      lastTurnTime = App.time;
      int var1;
      if (var0) {
         for(var1 = 0; var1 < numEntities; ++var1) {
            Entity var2 = entities[var1];
            var2.updateMonsterFX();
         }

         Canvas.updateFacingEntity = true;
      }

      for(var1 = 0; var1 < 6; ++var1) {
         Entity[] var4 = openDoors;
         if (var4[var1] != null) {
            Entity var3 = var4[var1];
            if (CanCloseDoor(var3)) {
               performDoorEvent(1, var3, 2);
            }
         }
      }

      var1 = executeStaticFunc(6);
      Canvas.startRotation(true);
   }

   public static final void gsprite_clear(int var0) {
      for(int var1 = 0; var1 < 48; ++var1) {
         GameSprite var2 = gsprites[var1];
         if ((var2.flags & 1) != 0 && (var2.flags & var0) != 0) {
            gsprite_destroy(var2);
         }
      }

   }

   public static final GameSprite gsprite_alloc(int var0, int var1, int var2) {
      boolean var3 = false;

      int var5;
      for(var5 = 0; var5 < 48 && (gsprites[var5].flags & 1) != 0; ++var5) {
      }

      if (var5 == 48) {
         App.Error(34);
         return null;
      } else {
         GameSprite var4 = gsprites[var5];
         var4.sprite = Render.customSprites[var5];

         for(var5 = 0; var5 < 6; ++var5) {
            var4.pos[var5] = 0;
         }

         var4.vel[0] = var4.vel[1] = var4.vel[2] = 0;
         var4.duration = 0;
         var4.time = App.time;
         var4.data = null;
         Render.mapSprites[Render.S_ENT + var4.sprite] = -1;
         Render.mapSprites[Render.S_RENDERMODE + var4.sprite] = 0;
         Render.mapSprites[Render.S_SCALEFACTOR + var4.sprite] = 64;
         var4.flags = var2 | 1;
         Render.mapSpriteInfo[var4.sprite] = var0 | var1 << 8;
         if (0 != (var4.flags & 2)) {
            ++activePropogators;
         }

         ++activeSprites;
         Canvas.invalidateRect();
         return var4;
      }
   }

   public static final GameSprite gsprite_allocAnim(int var0, int var1, int var2, int var3) {
      GameSprite var4 = gsprite_alloc(var0, 0, 66);
      var4.numAnimFrames = 4;
      Render.mapSprites[Render.S_X + var4.sprite] = var4.pos[0] = var4.pos[3] = (short)var1;
      Render.mapSprites[Render.S_Y + var4.sprite] = var4.pos[1] = var4.pos[4] = (short)var2;
      Render.mapSprites[Render.S_Z + var4.sprite] = var4.pos[2] = var4.pos[5] = (short)var3;
      var4.startScale = var4.destScale = 64;
      var4.duration = 200 * var4.numAnimFrames;
      switch(var0) {
      case 192:
         Render.mapSprites[Render.S_RENDERMODE + var4.sprite] = 4;
         break;
      case 208:
      case 227:
      case 230:
         var4.numAnimFrames = 1;
         var4.duration = 400;
         break;
      case 234:
         Render.mapSprites[Render.S_RENDERMODE + var4.sprite] = 3;
         Render.mapSprites[Render.S_SCALEFACTOR + var4.sprite] = 48;
         Render.mapSprites[Render.S_Z + var4.sprite] = (short)(Render.getHeight(var1, var2) + 32);
         int[] var10000;
         int var10001;
         if ((App.nextInt() & 1) != 0) {
            var10000 = Render.mapSpriteInfo;
            var10001 = var4.sprite;
            var10000[var10001] |= 131072;
         } else {
            var10000 = Render.mapSpriteInfo;
            var10001 = var4.sprite;
            var10000[var10001] &= -131073;
         }
         break;
      case 242:
         Render.mapSprites[Render.S_RENDERMODE + var4.sprite] = 3;
         break;
      case 245:
      case 246:
         if (var0 == 246) {
            var4.duration = 500;
         } else {
            var4.duration = 200;
         }

         var4.flags |= 4098;
         var4.numAnimFrames = 1;
         var4.vel[0] = var4.vel[1] = var4.vel[2] = 0;
         var4.pos[0] = var4.pos[3] = -6;
         var4.pos[1] = var4.pos[4] = 0;
         short[] var10002 = var4.pos;
         var4.pos[2] = var10002[5] = (short)(var10002[5] - Canvas.viewZ);
         Render.mapSprites[Render.S_X + var4.sprite] = (short)(Canvas.viewX + Canvas.viewStepX + (viewStepX >> 6) * var4.pos[0] + (viewRightStepX >> 6) * var4.pos[1]);
         Render.mapSprites[Render.S_Y + var4.sprite] = (short)(Canvas.viewY + Canvas.viewStepY + (viewStepY >> 6) * var4.pos[0] + (viewRightStepY >> 6) * var4.pos[1]);
         Render.mapSprites[Render.S_Z + var4.sprite] = (short)(Canvas.viewZ + var4.pos[2]);
         break;
      case 251:
         Render.mapSprites[Render.S_RENDERMODE + var4.sprite] = 3;
         var4.duration = 500;
         var4.flags |= 5122;
         var4.startScale = 64;
         var4.destScale = (byte)(var4.startScale + (var4.startScale >> 1));
         var4.scaleStep = 1000 * (var4.destScale - var4.startScale) / var4.duration;
         var4.pos[5] = -16;
         var4.vel[2] = (var4.pos[5] - var4.pos[2]) * 1000 / var4.duration;
         var4.numAnimFrames = 1;
         Render.relinkSprite(var4.sprite, Canvas.destX << 4, Canvas.destY << 4, Canvas.destZ << 4);
         return var4;
      }

      Render.relinkSprite(var4.sprite);
      return var4;
   }

   public static final void gsprite_destroy(GameSprite var0) {
      int[] var10000;
      int var10001;
      if ((var0.flags & 8192) == 0) {
         var10000 = Render.mapSpriteInfo;
         var10001 = var0.sprite;
         var10000[var10001] |= 65536;
      } else if ((var0.flags & '耀') == 0) {
         var10000 = Render.mapSpriteInfo;
         var10001 = var0.sprite;
         var10000[var10001] &= -257;
         Render.mapSprites[Render.S_X + var0.sprite] = var0.pos[3];
         Render.mapSprites[Render.S_Y + var0.sprite] = var0.pos[4];
         if ((var0.flags & 16384) == 0) {
            Render.mapSprites[Render.S_Z + var0.sprite] = var0.pos[5];
         } else {
            Render.mapSprites[Render.S_Z + var0.sprite] = var0.pos[2];
         }

         Render.relinkSprite(var0.sprite);
      }

      var0.flags &= -2;
      if ((var0.flags & '耀') != 0) {
         if ((var0.flags & 16384) != 0) {
            var0.flags &= -49153;
            var0.flags |= 1;
            var10000 = Render.mapSpriteInfo;
            var10001 = var0.sprite;
            var10000[var10001] &= -65537;
            var0.pos[0] = Render.mapSprites[Render.S_X + var0.sprite];
            var0.pos[1] = Render.mapSprites[Render.S_Y + var0.sprite];
            var0.pos[2] = Render.mapSprites[Render.S_Z + var0.sprite];
            var0.pos[5] = (short)(Render.getHeight(Render.mapSprites[Render.S_X + var0.sprite], Render.mapSprites[Render.S_Y + var0.sprite]) + 32);
            var0.time = App.time;
            var0.duration = 250;
            short[] var2;
            if (var0.vel[0] > 0) {
               var2 = var0.pos;
               var2[3] = (short)(var2[3] - 31);
               var0.vel[0] = -31000 / var0.duration;
            } else if (var0.vel[0] < 0) {
               var2 = var0.pos;
               var2[3] = (short)(var2[3] + 31);
               var0.vel[0] = 31000 / var0.duration;
            }

            if (var0.vel[1] > 0) {
               var2 = var0.pos;
               var2[4] = (short)(var2[4] - 31);
               var0.vel[1] = -31000 / var0.duration;
            } else if (var0.vel[1] < 0) {
               var2 = var0.pos;
               var2[4] = (short)(var2[4] + 31);
               var0.vel[1] = 31000 / var0.duration;
            }

            var0.vel[2] = (var0.pos[5] - var0.pos[2]) * 1000 / var0.duration;
            ++activeSprites;
            return;
         }
      } else if ((var0.flags & 2048) != 0) {
         Render.unlinkSprite(var0.sprite);
         if (var0.data != null && var0.data.isExplodableEntity()) {
            var0.data.died(true, (Entity)null);
         }
      }

      if ((Render.mapSpriteInfo[var0.sprite] & 255) == 252 && Canvas.isChickenKicking && Canvas.kickingPhase != 0) {
         int var1 = Canvas.getKickPoint(var0.pos[3] >> 6, var0.pos[4] >> 6);
         if (var1 > 0) {
            Text.resetTextArgs();
            Text.addTextArg(var1);
            Hud.addMessage((short)1, (short)155, 2);
         } else {
            Hud.addMessage((short)1, (short)157, 2);
         }

         Canvas.curScore += var1;
         Canvas.kickingPhase = 0;
         executeStaticFunc(10);
      }

   }

   public static final void snapGameSprites() {
      do {
         gsprite_update(1073741824);
      } while(activeSprites != 0);

   }

   public static final void gsprite_update(int var0) {
      if (0 != activeSprites) {
         boolean var1 = false;
         activeSprites = 0;
         activePropogators = 0;

         for(int var2 = 0; var2 < 48; ++var2) {
            GameSprite var3 = gsprites[var2];
            if ((var3.flags & 1) != 0) {
               int var4 = var0 - var3.time;
               if (var4 < 0) {
                  if (0 != (var3.flags & 2)) {
                     ++activePropogators;
                  }

                  ++activeSprites;
               } else if ((var3.flags & 512) == 0 && var4 >= var3.duration) {
                  if ((var3.flags & 8) == 0) {
                     Canvas.invalidateRect();
                  }

                  gsprite_destroy(var3);
               } else {
                  ++activeSprites;
                  var1 = true;
                  int var5;
                  if (0 != (var3.flags & 64)) {
                     var5 = var4 / 200 % var3.numAnimFrames;
                     Render.mapSpriteInfo[var3.sprite] = Render.mapSpriteInfo[var3.sprite] & -65281 | var5 << 8;
                  }

                  if (0 != (var3.flags & 4096)) {
                     var5 = Render.mapSprites[Render.S_X + var3.sprite] >> 6;
                     int var6 = Render.mapSprites[Render.S_Y + var3.sprite] >> 6;
                     short var7 = (short)(Canvas.viewX + viewStepX);
                     short var8 = (short)(Canvas.viewY + viewStepY);
                     if (0 != (var3.flags & 2)) {
                        if (var4 > var3.duration) {
                           Render.mapSprites[Render.S_X + var3.sprite] = (short)(var7 + (viewStepX >> 6) * var3.pos[3] + (viewRightStepX >> 6) * var3.pos[4]);
                           Render.mapSprites[Render.S_Y + var3.sprite] = (short)(var8 + (viewStepY >> 6) * var3.pos[3] + (viewRightStepY >> 6) * var3.pos[4]);
                           Render.mapSprites[Render.S_Z + var3.sprite] = (short)(Canvas.viewZ + var3.pos[5]);
                           var3.flags &= -3;
                        } else {
                           int var9 = var3.pos[0] + var3.vel[0] * var4 / 1000;
                           int var10 = var3.pos[1] + var3.vel[1] * var4 / 1000;
                           Render.mapSprites[Render.S_X + var3.sprite] = (short)(var7 + (viewStepX >> 6) * var9 + (viewRightStepX >> 6) * var10);
                           Render.mapSprites[Render.S_Y + var3.sprite] = (short)(var8 + (viewStepY >> 6) * var9 + (viewRightStepY >> 6) * var10);
                           Render.mapSprites[Render.S_Z + var3.sprite] = (short)(Canvas.viewZ + var3.pos[2] + var3.vel[2] * var4 / 1000);
                        }

                        ++activePropogators;
                     } else {
                        Render.mapSprites[Render.S_X + var3.sprite] = var7;
                        Render.mapSprites[Render.S_Y + var3.sprite] = var8;
                        Render.mapSprites[Render.S_Z + var3.sprite] = (short)Canvas.viewZ;
                     }

                     if (0 == (var3.flags & 4) && (var5 != Render.mapSprites[Render.S_X + var3.sprite] >> 6 || var6 != Render.mapSprites[Render.S_Y + var3.sprite] >> 6)) {
                        if (0 != (var3.flags & 4096)) {
                           Render.relinkSprite(var3.sprite, Canvas.destX << 4, Canvas.destY << 4, Canvas.destZ << 4);
                        } else {
                           Render.relinkSprite(var3.sprite);
                        }
                     }
                  } else if (0 != (var3.flags & 2)) {
                     ++activePropogators;
                     if (var4 >= var3.duration) {
                        Render.mapSprites[Render.S_X + var3.sprite] = var3.pos[3];
                        Render.mapSprites[Render.S_Y + var3.sprite] = var3.pos[4];
                        if ((var3.flags & 16384) == 0) {
                           Render.mapSprites[Render.S_Z + var3.sprite] = var3.pos[5];
                        } else if ((var3.flags & '耀') == 0) {
                           Render.mapSprites[Render.S_Z + var3.sprite] = var3.pos[2];
                        }

                        var3.flags &= -3;
                     } else {
                        Render.mapSprites[Render.S_X + var3.sprite] = (short)(var3.pos[0] + var3.vel[0] * var4 / 1000);
                        Render.mapSprites[Render.S_Y + var3.sprite] = (short)(var3.pos[1] + var3.vel[1] * var4 / 1000);
                        if ((var3.flags & 16384) == 0) {
                           Render.mapSprites[Render.S_Z + var3.sprite] = (short)(var3.pos[2] + var3.vel[2] * var4 / 1000);
                        } else {
                           var5 = Render.sinTable[(var4 << 16) / (var3.duration << 8) << 1 & 1023] >> 8;
                           Render.mapSprites[Render.S_Z + var3.sprite] = (short)(var3.pos[2] + (var5 * (var3.pos[5] - var3.pos[2] << 8) >> 16));
                        }

                        if (0 == (var3.flags & 4)) {
                           Render.relinkSprite(var3.sprite);
                        }
                     }
                  }

                  if (0 != (var3.flags & 1024)) {
                     if (var4 > var3.duration) {
                        Render.mapSprites[Render.S_SCALEFACTOR + var3.sprite] = (short)var3.destScale;
                        var3.flags &= -1025;
                     } else {
                        Render.mapSprites[Render.S_SCALEFACTOR + var3.sprite] = (short)((byte)(var3.startScale + var3.scaleStep * var4 / 1000));
                     }
                  }
               }
            }
         }

         if (var1) {
            Canvas.staleView = true;
            Canvas.invalidateRect();
         }

      }
   }

   private static final void saveWorldState(DataOutputStream var0, boolean var1) {
      try {
         short[] var6 = Render.mapSprites;
         int[] var7 = Render.mapSpriteInfo;
         Canvas.updateLoadingBar(false);
         var0.writeInt(Render.mapCompileDate);
         int var8 = App.gameTime;
         totalLevelTime += var8 - curLevelTime;
         curLevelTime = var8;
         var0.writeInt(totalLevelTime);
         var0.writeByte(mapSecretsFound);
         Render.snapFogLerp();
         var0.writeInt(TinyGL.fogColor);
         var0.writeInt(TinyGL.fogMin);
         var0.writeInt(TinyGL.fogRange);
         if (Player.ce.weapon == 16) {
            var0.writeByte((byte)Player.activeWeaponDef.tileIndex);
         }

         int var2;
         for(var2 = 0; var2 < 4; ++var2) {
            if (!var1) {
               var0.writeShort(placedBombs[var2]);
            } else {
               var0.writeShort(0);
            }
         }

         saveEntityStates(var0, var1);
         Canvas.updateLoadingBar(false);
         int var3 = Render.numLines;
         int var4 = 0;
         int var5 = 0;

         for(var2 = 0; var2 < var3; ++var2) {
            if (var5 == 8) {
               var0.writeByte((byte)var4);
               var5 = 0;
               var4 = 0;
            }

            if ((Render.lineFlags[var2 >> 1] & 8 << ((var2 & 1) << 2)) != 0) {
               var4 |= 1 << var5;
            }

            ++var5;
         }

         if (var5 != 0) {
            var0.writeByte((byte)var4);
         }

         byte var9 = 0;

         for(var2 = 0; var2 < 32; ++var2) {
            if (entityDeathFunctions[var2 * 2] != -1) {
               ++var9;
            }
         }

         var0.write(var9);

         for(var2 = 0; var2 < 32; ++var2) {
            if (entityDeathFunctions[var2 * 2] != -1) {
               var0.writeShort(entityDeathFunctions[var2 * 2]);
               var0.writeShort(entityDeathFunctions[var2 * 2 + 1]);
            }
         }

         var3 = Render.numMapSprites;
         boolean var19 = false;
         boolean var18 = false;
         var2 = 0;

         while(var2 < var3) {
            var4 = 0;

            for(var5 = 0; var5 < 8; ++var2) {
               boolean var10 = false;
               if (var1 && var6[Render.S_ENT + var2] != -1) {
                  Entity var11 = entities[var6[Render.S_ENT + var2]];
                  EntityDef var12 = var11.def;
                  if (var12.eType == 9 && var12.eSubType != 15 && (var11.monster.flags & 128) == 0) {
                     var10 = true;
                  }
               }

               if (!var10 && 0 != (Render.mapSpriteInfo[var2] & 65536)) {
                  var10 = true;
               }

               if (var10) {
                  var4 |= 1 << var5;
               }

               ++var5;
               if (0 != (var7[var2] & 2097152)) {
                  var4 |= 1 << var5;
               }

               ++var5;
            }

            var0.writeByte(var4);
         }

         short var17 = 1024;

         for(var2 = 0; var2 < var17; var2 += 8) {
            var4 = 0;

            for(int var21 = 0; var21 < 8; ++var21) {
               if (0 != (Render.mapFlags[var2 + var21] & 128)) {
                  var4 |= 1 << var21;
               }
            }

            var0.writeByte(var4);
         }

         short[] var22 = scriptStateVars;

         for(var2 = 0; var2 < 64; ++var2) {
            var0.writeShort(var22[var2]);
         }

         var3 = Render.numTileEvents;
         int var20 = 0;
         boolean var23 = false;
         int var13 = 0;

         for(var2 = 0; var2 < var3; ++var2) {
            var13 = var2 % 32;
            int var24 = (Render.tileEvents[var2 * 2 + 1] & 524288) >> 19;
            var20 |= var24 << var13;
            if (var13 == 31) {
               var0.writeInt(var20);
               var20 = 0;
            }
         }

         if (var13 != 31) {
            var0.writeInt(var20);
         }

         Canvas.updateLoadingBar(false);
         var0.writeByte(numLerpSprites);
         int var14 = 0;

         for(var2 = 0; var2 < 16; ++var2) {
            LerpSprite var15 = lerpSprites[var2];
            if (var15.hSprite != 0) {
               var15.saveState(var0);
               ++var14;
            }
         }

         var0.writeByte(numScriptThreads);

         for(var2 = 0; var2 < 20; ++var2) {
            scriptThreads[var2].saveState(var0);
         }

         var0.writeInt(dropIndex);
         var0.writeInt(Player.moves);
         var0.writeByte((byte)Player.numNotebookIndexes);
         var0.writeByte(Player.questComplete);
         var0.writeByte(Player.questFailed);

         for(var2 = 0; var2 < 8; ++var2) {
            var0.writeShort(Player.notebookIndexes[var2]);
            var0.writeShort(Player.notebookPositions[var2]);
         }

         var0.writeByte(Render.mapFlagsBitmask);
         if (watchLine == null) {
            var0.writeShort(-1);
         } else {
            var0.writeShort(watchLine.getIndex());
         }

         var0.writeByte(numStations);

         for(var2 = 0; var2 < numStations; ++var2) {
            var0.writeShort(mixingStations[var2 * 4 + 0]);
            var0.writeShort(mixingStations[var2 * 4 + 1]);
            var0.writeShort(mixingStations[var2 * 4 + 2]);
            var0.writeShort(mixingStations[var2 * 4 + 3]);
         }

         var0.writeShort(lootFound);
         var0.writeShort(destroyedObj);
         Canvas.updateLoadingBar(false);
      } catch (IOException var16) {
         App.Error(var16, 49);
      }

   }

   public static final void loadWorldState() {
      if (Canvas.loadMapID <= 10) {
         DataInputStream var0;
         try {
            if (activeLoadType == 1) {
               var0 = Resource.loadRecord("SDFWORLD");
            } else {
               var0 = Resource.loadRecord("SDBWORLD" + (Canvas.loadMapID - 1));
            }
         } catch (Exception var2) {
            totalLevelTime = 0;
            return;
         }

         loadWorldState(var0);
      }
   }

   private static final void loadWorldState(DataInputStream var0) {
      try {
         int var4 = var0.readInt();
         if (var4 != Render.mapCompileDate) {
            if (var4 != 0) {
               spawnParam = 0;
            }

            totalLevelTime = 0;
            return;
         }

         totalLevelTime = var0.readInt();
         mapSecretsFound = var0.readByte();
         TinyGL.fogColor = var0.readInt();
         TinyGL.fogMin = var0.readInt();
         TinyGL.fogRange = var0.readInt();
         Canvas.updateLoadingBar(false);
         Render.buildFogTables(TinyGL.fogColor);
         Canvas.updateLoadingBar(false);
         if (Player.ce.weapon == 16) {
            Player.setPickUpWeapon(var0.readByte() & 255);
         }

         int var5;
         for(var5 = 0; var5 < 4; ++var5) {
            placedBombs[var5] = var0.readShort();
         }

         loadEntityStates(var0);
         Canvas.updateLoadingBar(false);
         int var1 = Render.numLines;
         byte var2 = 0;
         int var3 = 8;

         byte[] var10000;
         for(var5 = 0; var5 < var1; ++var5) {
            if (var3 == 8) {
               var2 = var0.readByte();
               var3 = 0;
            }

            if ((var2 & 1 << var3) != 0) {
               var10000 = Render.lineFlags;
               var10000[var5 >> 1] = (byte)(var10000[var5 >> 1] | 8 << ((var5 & 1) << 2));
            }

            ++var3;
         }

         byte var16 = var0.readByte();

         int var6;
         for(var6 = 0; var6 < var16; ++var6) {
            entityDeathFunctions[var6 * 2] = var0.readShort();
            entityDeathFunctions[var6 * 2 + 1] = var0.readShort();
         }

         Canvas.updateLoadingBar(false);
         var1 = Render.numMapSprites;
         boolean var14 = false;
         boolean var15 = true;
         var6 = 0;

         while(var6 < var1) {
            var2 = var0.readByte();

            for(var3 = 0; var3 < 8; ++var6) {
               int[] var22;
               if (0 != (var2 & 1 << var3)) {
                  var22 = Render.mapSpriteInfo;
                  var22[var6] |= 65536;
               } else {
                  var22 = Render.mapSpriteInfo;
                  var22[var6] &= -65537;
               }

               ++var3;
               if (0 != (var2 & 1 << var3)) {
                  var22 = Render.mapSpriteInfo;
                  var22[var6] |= 2097152;
               } else {
                  var22 = Render.mapSpriteInfo;
                  var22[var6] &= -2097153;
               }

               ++var3;
            }
         }

         Canvas.updateLoadingBar(false);
         short var13 = 1024;

         for(var6 = 0; var6 < var13; var6 += 8) {
            var2 = var0.readByte();

            for(int var7 = 0; var7 < 8; ++var7) {
               if (0 != (var2 & 1 << var7)) {
                  var10000 = Render.mapFlags;
                  var10000[var6 + var7] = (byte)(var10000[var6 + var7] | 128);
               }
            }
         }

         short[] var17 = scriptStateVars;

         for(var6 = 0; var6 < 64; ++var6) {
            var17[var6] = var0.readShort();
         }

         Canvas.updateLoadingBar(false);
         var1 = Render.numTileEvents;
         boolean var8 = false;
         int var9 = var0.readInt();
         boolean var10 = false;

         for(var6 = 0; var6 < var1; ++var6) {
            int var18 = var6 % 32;
            int var19 = Render.tileEvents[var6 * 2 + 1] & -524289;
            Render.tileEvents[var6 * 2 + 1] = var19 | (var9 >> var18 & 1) << 19;
            if (var18 == 31 && var6 < var1 - 1) {
               var9 = var0.readInt();
            }
         }

         numLerpSprites = var0.readByte();

         for(var6 = 0; var6 < 16; ++var6) {
            LerpSprite var11 = lerpSprites[var6];
            if (var6 < numLerpSprites) {
               var11.loadState(var0);
            } else {
               var11.hSprite = 0;
            }
         }

         numScriptThreads = var0.readByte();

         for(var6 = 0; var6 < 20; ++var6) {
            ScriptThread var20 = scriptThreads[var6];
            var20.loadState(var0);
            if (var20.stackPtr > 0) {
               var20.inuse = true;
            } else {
               var20.inuse = false;
            }
         }

         updateLerpSprites();
         dropIndex = var0.readInt();
         Player.moves = var0.readInt();
         Player.numNotebookIndexes = var0.readByte();
         Player.questComplete = var0.readByte();
         Player.questFailed = var0.readByte();

         for(var6 = 0; var6 < 8; ++var6) {
            Player.notebookIndexes[var6] = var0.readShort();
            Player.notebookPositions[var6] = var0.readShort();
         }

         Render.mapFlagsBitmask = var0.readByte();
         short var21 = var0.readShort();
         if (var21 != -1) {
            watchLine = entities[var21];
         }

         numStations = var0.readByte();

         for(var6 = 0; var6 < numStations; ++var6) {
            mixingStations[var6 * 4 + 0] = var0.readShort();
            mixingStations[var6 * 4 + 1] = var0.readShort();
            mixingStations[var6 * 4 + 2] = var0.readShort();
            mixingStations[var6 * 4 + 3] = var0.readShort();
         }

         lootFound = var0.readShort();
         destroyedObj = var0.readShort();
         Hud.playerDestHealth = 0;
         Hud.playerStartHealth = 0;
         Hud.playerHealthChangeTime = 0;
         prepareMonsters();
      } catch (IOException var12) {
         App.Error(var12, 33);
      }

   }

   public static final void saveConfig() {
      try {
         ByteArrayOutputStream var0 = new ByteArrayOutputStream();
         DataOutputStream var1 = new DataOutputStream(var0);
         var1.writeInt(21);
         var1.writeByte(difficulty);
         var1.writeBoolean(Sound.isSoundEnabled());
         var1.writeBoolean(Sound.isVibrateEnabled());
         var1.writeBoolean(Player.enableHelp);
         var1.writeInt(Text.defaultLanguage);
         var1.writeInt(Player.totalDeaths);
         var1.writeInt(Canvas.animFrames);
         var1.writeInt(Player.helpBitmask);
         var1.writeInt(Player.invHelpBitmask);
         var1.writeInt(Player.ammoHelpBitmask);
         var1.writeInt(Player.weaponHelpBitmask);
         var1.writeInt(Player.armorHelpBitmask);

         int var2;
         for(var2 = 0; var2 < 5; ++var2) {
            var1.writeInt(MenuSystem.indexes[var2 * 2]);
            var1.writeInt(MenuSystem.indexes[var2 * 2 + 1]);
         }

         var1.writeBoolean(Canvas.recentBriefSave);
         var1.writeBoolean(hasSeenIntro);

         for(var2 = 0; var2 < 5; ++var2) {
            var1.writeShort(Canvas.highScores[var2]);
         }

         for(var2 = 0; var2 < 15; ++var2) {
            var1.writeChar(Canvas.highScoreInitials[var2]);
         }

         byte[] var3 = var0.toByteArray();
         var1.close();
         var1 = null;
         var0 = null;
         System.gc();
         Resource.saveRecord("SDCONFIG", var3);
      } catch (Exception var4) {
         App.Error(var4, 43);
      }

   }

   public static final void loadConfig() {
      try {
         DataInputStream var0 = Resource.loadRecord("SDCONFIG");
         if (var0.readInt() != 21) {
            throw new Exception("Invalid savegame version");
         }

         difficulty = var0.readByte();
         Sound.setSound(var0.readBoolean());
         Sound.setVibrate(var0.readBoolean());
         Player.enableHelp = var0.readBoolean();
         int var1 = var0.readInt();
         if (var1 != Text.defaultLanguage) {
            Text.setLanguage(var1);
            Text.selectLanguage = Text.defaultLanguage == -1;
            if (Text.selectLanguage) {
               Text.defaultLanguage = 0;
            }
         }

         Player.totalDeaths = var0.readInt();
         int var2 = var0.readInt();
         if (var2 < 2) {
            var2 = 2;
         } else if (var2 > 64) {
            var2 = 64;
         }

         Canvas.setAnimFrames(var2);
         Player.helpBitmask = var0.readInt();
         Player.invHelpBitmask = var0.readInt();
         Player.ammoHelpBitmask = var0.readInt();
         Player.weaponHelpBitmask = var0.readInt();
         Player.armorHelpBitmask = var0.readInt();

         int var3;
         for(var3 = 0; var3 < 5; ++var3) {
            MenuSystem.indexes[var3 * 2] = var0.readInt();
            MenuSystem.indexes[var3 * 2 + 1] = var0.readInt();
         }

         Canvas.recentBriefSave = var0.readBoolean();
         hasSeenIntro = var0.readBoolean();

         for(var3 = 0; var3 < 5; ++var3) {
            Canvas.highScores[var3] = var0.readShort();
         }

         for(var3 = 0; var3 < 15; ++var3) {
            Canvas.highScoreInitials[var3] = var0.readChar();
         }

         var0.close();
      } catch (Exception var4) {
         App.checkPausedState();
         removeState(false);
      }

   }

   public static final void saveState(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      boolean var9 = (var8 & 32) != 0;
      Canvas.recentBriefSave = var9;
      Canvas.freeRuntimeData();
      Canvas.updateLoadingBar(false);
      saveConfig();
      if (!hasSavedState()) {
         try {
            touchRecords();
         } catch (Exception var14) {
         }
      }

      try {
         ByteArrayOutputStream var10;
         DataOutputStream var11;
         byte[] var12;
         if (0 != (var8 & 1)) {
            Canvas.updateLoadingBar(false);
            var10 = new ByteArrayOutputStream();
            var11 = new DataOutputStream(var10);
            if (!savePlayerState(var11, var1, var2, var3, var4, var5, var6, var7)) {
               var11.close();
               var10.close();
               return;
            }

            var12 = var10.toByteArray();
            var11.close();
            var11 = null;
            var10.close();
            var10 = null;
            System.gc();
            if (var9) {
               Resource.saveRecord("SDBPLAYER", var12);
            } else {
               Resource.saveRecord("SDFPLAYER", var12);
            }
         }

         if (0 != (var8 & 2)) {
            Canvas.updateLoadingBar(false);
            var10 = new ByteArrayOutputStream();
            var11 = new DataOutputStream(var10);
            saveWorldState(var11, var9);
            var12 = var10.toByteArray();
            var11.close();
            var11 = null;
            var10.close();
            var10 = null;
            System.gc();
            if (var9) {
               Resource.saveRecord("SDBWORLD" + (var0 - 1), var12);
            } else {
               Resource.saveRecord("SDFWORLD", var12);
            }
         }

         System.gc();
         Canvas.loadRuntimeData();
      } catch (Exception var13) {
         App.Error(var13, 48);
      }

   }

   public static final void saveLevelSnapshot() {
      try {
         ByteArrayOutputStream var0 = new ByteArrayOutputStream();
         DataOutputStream var1 = new DataOutputStream(var0);
         Canvas.updateLoadingBar(false);
         if (!savePlayerState(var1, Canvas.loadMapID, Canvas.viewX, Canvas.viewY, Canvas.viewAngle, Canvas.viewPitch, Canvas.viewX, Canvas.viewY)) {
            return;
         }

         byte[] var2 = var0.toByteArray();
         var1.close();
         var1 = null;
         var0.close();
         var0 = null;
         System.gc();
         Resource.saveRecord("SDBPLAYER", var2);
         Canvas.updateLoadingBar(false);
         var0 = new ByteArrayOutputStream();
         var1 = new DataOutputStream(var0);
         saveWorldState(var1, true);
         var2 = var0.toByteArray();
         var1.close();
         var1 = null;
         var0.close();
         var0 = null;
         System.gc();
         Canvas.updateLoadingBar(false);
         Resource.saveRecord("SDBWORLD" + (Canvas.loadMapID - 1), var2);
         Canvas.updateLoadingBar(false);
      } catch (Exception var3) {
         App.Error(var3, 47);
      }

   }

   private static final boolean savePlayerState(DataOutputStream var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         var0.writeShort(var1);
         var0.writeShort(var2);
         var0.writeShort(var3);
         var0.writeShort(var4);
         var0.writeShort(var5);
         var0.writeInt(var6);
         var0.writeInt(var7);
         return Player.saveState(var0);
      } catch (IOException var9) {
         App.Error(var9, 46);
         return false;
      }
   }

   private static final boolean loadPlayerState(DataInputStream var0) {
      try {
         saveStateMap = var0.readShort();
         Canvas.viewX = Canvas.destX = var0.readShort();
         Canvas.viewY = Canvas.destY = var0.readShort();
         Canvas.viewAngle = Canvas.destAngle = var0.readShort();
         Canvas.viewPitch = Canvas.destPitch = var0.readShort();
         Canvas.destZ = 36;
         Canvas.viewZ = 36;
         Canvas.prevX = var0.readInt();
         Canvas.prevY = var0.readInt();
         spawnParam = 0;
         return Player.loadState(var0);
      } catch (IOException var2) {
         App.Error(var2, 31);
         return false;
      }
   }

   public static final boolean loadState(int var0) {
      try {
         DataInputStream var1;
         if (var0 == 1) {
            var1 = Resource.loadRecord("SDFPLAYER");
            if (!loadPlayerState(var1)) {
               return false;
            }
         } else if (var0 == 2) {
            var1 = Resource.loadRecord("SDBPLAYER");
            if (!loadPlayerState(var1)) {
               return false;
            }
         }
      } catch (Exception var2) {
         App.Error(var2, 32);
      }

      activeLoadType = var0;
      if (Canvas.viewX != 0 && Canvas.viewY != 0) {
         int var3 = (Canvas.viewAngle & 1023) >> 7;
         spawnParam = Canvas.viewX >> 6 & 31 | (Canvas.viewY >> 6 & 31) << 5 | var3 << 10;
      }

      Canvas.loadMap(saveStateMap, false);
      isSaved = false;
      isLoaded = true;
      return true;
   }

   private static final boolean hasConfig() {
      boolean var0 = false;
      if (Resource.recordExists("SDCONFIG")) {
         try {
            DataInputStream var1 = Resource.loadRecord("SDCONFIG");
            if (var1.readInt() == 21) {
               var0 = true;
            }

            var1.close();
         } catch (Exception var2) {
            var0 = false;
         }
      }

      return var0;
   }

   public static final void touchRecords() throws Exception {
      Resource.touchRecord("SDFWORLD");
      Resource.touchRecord("SDFPLAYER");
      Resource.touchRecord("SDBPLAYER");

      for(int var0 = 1; var0 <= 10; ++var0) {
         Resource.touchRecord("SDBWORLD" + (var0 - 1));
      }

   }

   private static final boolean hasElement(String[] var0, String var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2].compareTo(var1) == 0) {
            return true;
         }
      }

      return false;
   }

   public static final boolean hasSavedState() {
      if (!hasConfig()) {
         return false;
      } else {
         try {
            String[] var0 = RecordStore.listRecordStores();
            if (var0 == null) {
               return false;
            } else if (hasElement(var0, "SDFWORLD") && hasElement(var0, "SDFPLAYER") && hasElement(var0, "SDBPLAYER")) {
               for(int var1 = 1; var1 <= 10; ++var1) {
                  if (!hasElement(var0, "SDBWORLD" + (var1 - 1))) {
                     return false;
                  }
               }

               return true;
            } else {
               return false;
            }
         } catch (Exception var2) {
            App.Error(var2, 83);
            return false;
         }
      }
   }

   public static final void removeState(boolean var0) {
      if (var0) {
         Canvas.updateLoadingBar(false);
      }

      saveEmptyConfig();
      if (var0) {
         Canvas.updateLoadingBar(false);
      }

      Resource.deleteRecord("SDFWORLD");
      if (var0) {
         Canvas.updateLoadingBar(false);
      }

      Resource.deleteRecord("SDFPLAYER");
      if (var0) {
         Canvas.updateLoadingBar(false);
      }

      Resource.deleteRecord("SDBPLAYER");
      if (var0) {
         Canvas.updateLoadingBar(false);
      }

      for(int var1 = 1; var1 <= 10; ++var1) {
         Resource.deleteRecord("SDBWORLD" + (var1 - 1));
         if (var0) {
            Canvas.updateLoadingBar(false);
         }
      }

   }

   public static final void saveEmptyConfig() {
      try {
         ByteArrayOutputStream var0 = new ByteArrayOutputStream();
         DataOutputStream var1 = new DataOutputStream(var0);
         var1.writeInt(21);
         var1.writeByte(1);
         var1.writeBoolean(Sound.isSoundEnabled());
         var1.writeBoolean(Sound.isVibrateEnabled());
         var1.writeBoolean(Player.enableHelp);
         var1.writeInt(Text.defaultLanguage);
         var1.writeInt(0);
         var1.writeInt(Canvas.animFrames);
         var1.writeInt(0);
         var1.writeInt(0);
         var1.writeInt(0);
         var1.writeInt(0);
         var1.writeInt(0);

         int var2;
         for(var2 = 0; var2 < 5; ++var2) {
            var1.writeInt(0);
            var1.writeInt(0);
         }

         var1.writeBoolean(false);
         var1.writeBoolean(hasSeenIntro);

         for(var2 = 0; var2 < 5; ++var2) {
            var1.writeShort(Canvas.highScores[var2]);
         }

         for(var2 = 0; var2 < 15; ++var2) {
            var1.writeChar(Canvas.highScoreInitials[var2]);
         }

         byte[] var3 = var0.toByteArray();
         var1.close();
         var1 = null;
         var0 = null;
         System.gc();
         Resource.saveRecord("SDCONFIG", var3);
      } catch (Exception var4) {
         App.Error(var4, 43);
      }

   }

   public static final boolean canSnapMonsters() {
      if (disableAI) {
         return true;
      } else {
         if (activeMonsters != null) {
            Entity var0 = activeMonsters;

            do {
               if (0 != (var0.info & 268435456)) {
                  return false;
               }

               var0 = var0.monster.nextOnList;
            } while(var0 != activeMonsters);
         }

         return true;
      }
   }

   public static final boolean snapMonsters(boolean var0) {
      if (animatingEffects != 0) {
         for(int var1 = 0; var1 < 16; ++var1) {
            LerpSprite var2 = lerpSprites[var1];
            if (var2.hSprite != 0 && (var2.flags & 2) != 0) {
               int var3 = var2.hSprite - 1;
               short var4 = Render.mapSprites[Render.S_ENT + var3];
               if (var4 == -1) {
                  return false;
               }

               Entity var5 = entities[var4];
               if (var5.def.eType != 2) {
                  return false;
               }

               if ((var5.monster.flags & 4096) != 0) {
                  return false;
               }
            }
         }
      }

      boolean var6 = var0;
      Entity var7;
      if (monstersTurn != 0) {
         monsterAI();
         if (!var0) {
            var6 = canSnapMonsters();
         }

         if (var6 && activeMonsters != null) {
            var7 = activeMonsters;

            do {
               snapLerpSprites(var7.getSprite());
               var7 = var7.monster.nextOnList;
            } while(var7 != activeMonsters);

            Canvas.updateFacingEntity = true;
            Canvas.invalidateRect();
         }

         monsterLerp();
         if (combatMonsters != null && !interpolatingMonsters) {
            Combat.performAttack(combatMonsters, combatMonsters.monster.target, 0, 0, false);
            return false;
         } else {
            return var6;
         }
      } else {
         if (!var0) {
            var6 = canSnapMonsters();
         }

         var7 = activeMonsters;
         if (var6 && var7 != null) {
            Entity var8;
            do {
               var8 = var7.monster.nextOnList;
               snapLerpSprites(var7.getSprite());
               var7 = var8;
            } while(var8 != activeMonsters);

            interpolatingMonsters = false;
         }

         Canvas.updateFacingEntity = true;
         Canvas.invalidateRect();
         return var6;
      }
   }

   public static final void endMonstersTurn() {
      Canvas.startRotation(true);
      monstersTurn = 0;
   }

   public static final void updateMonsters() {
      monsterAI();
      monsterLerp();
      if (!interpolatingMonsters) {
         Canvas.updateFacingEntity = true;
         if (combatMonsters != null) {
            Combat.performAttack(combatMonsters, combatMonsters.monster.target, 0, 0, false);
         } else {
            endMonstersTurn();
            if (!isCameraActive()) {
               Canvas.drawPlayingSoftKeys();
            }
         }
      }

   }

   public static final void setLineLocked(Entity var0, boolean var1) {
      int var2 = var0.getSprite();
      int var3 = Render.mapSpriteInfo[var2] & 255;
      if (var1) {
         var3 &= -2;
      } else {
         var3 |= 1;
      }

      Render.mapSpriteInfo[var2] = Render.mapSpriteInfo[var2] & -256 | var3;
      var3 += 257;
      if (var0.def.name == (var0.name & 1023)) {
         var0.def = EntityDef.lookup(var3);
         var0.name = (short)(var0.def.name | 2048);
      } else {
         var0.def = EntityDef.lookup(var3);
      }

   }

   public static final void snapAllMovers() {
      while(numLerpSprites > 0) {
         snapGameSprites();
         snapLerpSprites(-1);
      }

   }

   public static final void skipCinematic() {
      boolean var0 = Sound.isSoundEnabled();
      Sound.setSound(false);
      Sound.soundStop();
      skippingCinematic = true;

      while(skippingCinematic) {
         snapGameSprites();
         snapLerpSprites(-1);
         if (activeCameraKey != -1) {
            activeCamera.Snap(activeCameraKey);
         }

         if (skippingCinematic && null == activeCamera.keyThread) {
            activeCamera.cameraThread.attemptResume(App.gameTime + 1073741824);
         }
      }

      Hud.subTitleID = -1;
      Hud.cinTitleID = -1;
      snapGameSprites();
      snapLerpSprites(-1);
      cinematicWeapon = -1;
      Canvas.shakeTime = 0;
      if (!Render.isFading() || (Render.getFadeFlags() & 12) == 0) {
         Render.startFade(500, 2);
      }

      Canvas.updateFacingEntity = true;
      Sound.setSound(var0);
      Canvas.shakeTime = 0;
      Canvas.blockInputTime = 0;
      ParticleSystem.freeAllParticles();
      if (Canvas.state == 8 && isCameraActive()) {
         activeCameraTime = 65536;
      }

      if (Canvas.state == 3) {
         Canvas.drawPlayingSoftKeys();
      }

   }

   public static int getNextBombIndex() {
      for(int var0 = 0; var0 < 4; ++var0) {
         if (placedBombs[var0] == 0) {
            return var0;
         }
      }

      return -1;
   }

   public static void updateBombs() {
      Entity var0 = null;
      int[] var2 = placedBombs;

      for(int var3 = 0; var3 < 4; ++var3) {
         if (var2[var3] != 0) {
            var0 = entities[var2[var3]];
            int var1 = var0.param & 255;
            --var1;
            if (var1 < 0) {
               if ((var0.info & 1048576) != 0) {
                  var0.pain(1, (Entity)null);
               }

               var2[var3] = 0;
            } else {
               var0.param = var0.param & -256 | var1;
            }
         }
      }

   }

   public static final int setDynamite(int var0, int var1, boolean var2) {
      int var3 = getNextBombIndex();
      if (var3 == -1) {
         App.Error(new Exception("Too many bombs placed"), 111);
      }

      int var4 = 0;
      int var5;
      if (!var2) {
         var5 = destAngle & 1023;
         switch(var5) {
         case 0:
            var4 |= 134217728;
            --var0;
            break;
         case 256:
            var4 |= 33554432;
            ++var1;
            break;
         case 512:
            var4 |= 67108864;
            ++var0;
            break;
         case 768:
            var4 |= 16777216;
            --var1;
         }
      } else {
         var4 |= 1114112;
      }

      var5 = Render.getHeight(var0, var1);
      allocDynamite((short)var0, (short)var1, (short)(var5 + (var2 ? 31 : 32)), var4, var3, 0);
      return var3;
   }

   public static final Entity getFreeDropEnt() {
      int var0 = dropIndex;
      int var1 = firstDropIndex + var0;
      if ((entities[var1].info & 1048576) != 0) {
         for(var0 = (var0 + 1) % 16; var0 != dropIndex && (entities[var1].info & 1048576) != 0; var1 = firstDropIndex + var0) {
            var0 = (var0 + 1) % 16;
         }

         if (dropIndex == var0) {
         }
      }

      dropIndex = (var0 + 1) % 16;
      lastDropEntIndex = var1;
      return entities[var1];
   }

   public static void allocDynamite(int var0, int var1, int var2, int var3, int var4, int var5) {
      Entity var6 = getFreeDropEnt();
      var6.def = EntityDef.find(14, 6);
      var6.name = (short)(var6.def.name | 2048);
      var6.param = var5 << 8 | 3;
      if (0 != (var6.info & 1048576)) {
         unlinkEntity(var6);
      }

      linkEntity(var6, var0 >> 6, var1 >> 6);
      var6.info |= 4325376;
      int var7 = var6.getSprite();
      Render.mapSprites[Render.S_X + var7] = (short)var0;
      Render.mapSprites[Render.S_Y + var7] = (short)var1;
      Render.mapSprites[Render.S_Z + var7] = (short)var2;
      Render.mapSprites[Render.S_SCALEFACTOR + var7] = 32;
      Render.mapSprites[Render.S_RENDERMODE + var7] = 0;
      Render.mapSpriteInfo[var7] = var3 | var6.def.tileIndex;
      Render.relinkSprite(var7);
      placedBombs[var4] = lastDropEntIndex;
   }

   public static final Entity spawnDropItem(int var0, int var1, int var2, EntityDef var3, int var4, boolean var5) {
      Entity var6 = getFreeDropEnt();
      var6.def = var3;
      var6.name = (short)(var6.def.name | 2048);
      if (var2 >= 1 && var2 < 16) {
         var2 |= 512;
      }

      if (0 != (var6.info & 1048576)) {
         unlinkEntity(var6);
      }

      var6.info &= 65535;
      var6.info |= 4194304;
      if (var6.def.eType == 10) {
         var6.info |= 131072;
      }

      int var7 = var6.getSprite();
      int var8 = Render.getHeight(var0, var1);
      Render.mapSpriteInfo[var7] = var2;
      Render.mapSprites[Render.S_X + var7] = (short)var0;
      Render.mapSprites[Render.S_Y + var7] = (short)var1;
      Render.mapSprites[Render.S_Z + var7] = (short)(32 + var8);
      Render.mapSprites[Render.S_ENT + var7] = (short)lastDropEntIndex;
      Render.mapSprites[Render.S_SCALEFACTOR + var7] = 64;
      var6.param = var4;
      Render.relinkSprite(var7);
      linkEntity(var6, var0 >> 6, var1 >> 6);
      if (var5) {
         throwDropItem(var0, var1, var8, var6);
      }

      return var6;
   }

   public static final Entity spawnDropItem(int var0, int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      EntityDef var8 = EntityDef.find(var3, var4, var5);
      if (var8 != null) {
         return spawnDropItem(var0, var1, var2, var8, var6, var7);
      } else {
         App.Error(new Exception("Cannot find a def for the spawnDropItem."), -1);
         return null;
      }
   }

   public static final void throwDropItem(int var0, int var1, int var2, Entity var3) {
      LerpSprite var4 = allocLerpSprite((ScriptThread)null, var3.getSprite(), var3.def.eType != 6);
      if (var4 != null) {
         var4.srcX = var0;
         var4.srcY = var1;
         var4.srcZ = (short)(32 + var2);
         var4.dstX = var0;
         var4.dstY = var1;
         var4.dstZ = var4.srcZ;
         var4.height = 48;
         var4.flags |= 5;
         var4.srcScale = var4.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var3.getSprite()];
         var4.startTime = App.gameTime;
         var4.travelTime = 850;
         int var5 = App.nextByte() & 7;
         int var6 = 8;

         while(true) {
            --var6;
            if (var6 < 0) {
               break;
            }

            var0 = var4.srcX + dropDirs[var5 << 1];
            var1 = var4.srcY + dropDirs[(var5 << 1) + 1];
            byte[] var7 = Render.mapFlags;
            trace(var4.srcX, var4.srcY, var0, var1, var3, 15855, 25);
            if (traceEntity == null && (Entity.baseVisitedTiles[var1 >> 6] & 1 << (var0 >> 6)) == 0) {
               var4.dstX = var0;
               var4.dstY = var1;
               var4.dstZ = (short)(Render.getHeight(var4.dstX, var4.dstY) + 32);
               break;
            }

            var5 = var5 + 1 & 7;
         }
      }

   }

   public static final int updateLerpSprite(LerpSprite var0) {
      int var1 = 0;
      int var2 = var0.hSprite - 1;
      int var3 = Canvas.viewX >> 6;
      int var4 = Canvas.viewY >> 6;
      int var5 = App.gameTime - var0.startTime;
      int var8 = Render.mapSpriteInfo[var2];
      int var9 = var8 & 255;
      if ((var8 & 4194304) != 0) {
         var9 += 257;
      }

      if (var5 >= var0.travelTime) {
         freeLerpSprite(var0);
         return 3;
      } else if (var5 < 0) {
         return 4;
      } else {
         int var10 = 0;
         if (var0.travelTime != 0) {
            var10 = (var5 << 16) / (var0.travelTime << 8);
         }

         Render.mapSprites[Render.S_X + var2] = (short)(var0.srcX + (var10 * (var0.dstX - var0.srcX << 8) >> 16));
         Render.mapSprites[Render.S_Y + var2] = (short)(var0.srcY + (var10 * (var0.dstY - var0.srcY << 8) >> 16));
         Render.mapSprites[Render.S_SCALEFACTOR + var2] = (short)((byte)(var0.srcScale + (var10 * (var0.dstScale - var0.srcScale << 8) >> 16)));
         Render.mapSprites[Render.S_Z + var2] = (short)(var0.srcZ + (var10 * (var0.dstZ - var0.srcZ << 8) >> 16));
         int var12;
         if ((var0.flags & 4) != 0) {
            int var11 = var10 << 1;
            if ((var0.flags & 8) != 0) {
               var11 = var11 * 6 / 8;
            }

            var12 = Render.sinTable[var11 & 1023] >> 8;
            if ((var0.flags & 32) == 0) {
               Render.relinkSprite(var2, Render.mapSprites[Render.S_X + var2] << 4, Render.mapSprites[Render.S_Y + var2] << 4, Render.mapSprites[Render.S_Z + var2] << 4);
            }

            short[] var10000 = Render.mapSprites;
            int var10001 = Render.S_Z + var2;
            var10000[var10001] += (short)(var12 * (var0.height << 8) >> 16);
         } else if ((var0.flags & 32) == 0) {
            Render.relinkSprite(var2);
         }

         int var6 = Render.mapSprites[Render.S_X + var2] >> 6;
         int var7 = Render.mapSprites[Render.S_Y + var2] >> 6;
         if (-1 != Render.mapSprites[Render.S_ENT + var2] && 0 == (Render.mapSpriteInfo[var2] & 65536)) {
            Entity var21 = entities[Render.mapSprites[Render.S_ENT + var2]];
            var12 = var21.linkIndex % 32;
            int var13 = var21.linkIndex / 32;
            if (var21.def.eType == 3 || null != var21.monster) {
               int var14 = (Render.mapSpriteInfo[var2] & '\uff00') >> 8;
               int var15 = var14 & 240;
               int var16 = var0.dstX - var0.srcX;
               int var17 = var0.dstY - var0.srcY;
               int var18;
               if ((var15 == 0 || var15 == 32 || var15 == 48 && (var0.flags & 2048) != 0) && (var16 | var17) != 0) {
                  var18 = VecToDir(var16, var17, true);
                  int var19 = Math.abs((Render.viewAngle & 1023) - var18);
                  int var20 = Math.max((var0.dstX - var0.srcX) * (var0.dstX - var0.srcX), (var0.dstY - var0.srcY) * (var0.dstY - var0.srcY));
                  if (Combat.WorldDistToTileDist(var20) > 1 && var19 < 256) {
                     var15 = 48;
                     var0.flags |= 2048;
                  } else {
                     var15 = 32;
                  }
               } else if (var15 == 16) {
                  var15 = 48;
               } else if (var21.def.eType == 2 && var21.def.eSubType == 14 && var15 == 80) {
                  var18 = (var10 / 64 & 3) + 2;
                  if (var10 > 192) {
                     var18 = 1;
                  }

                  Render.mapSpriteInfo[var2] = Render.mapSpriteInfo[var2] & -65281 | (var18 | var15) << 8;
               }

               if (var15 == 32 || var15 == 48) {
                  var18 = 1 + (var10 * var0.dist >> 12) & 3;
                  Render.mapSpriteInfo[var2] = Render.mapSpriteInfo[var2] & -65281 | (var18 | var15) << 8;
               }
            }

            if (0 == (var0.flags & 16) && (var6 != var12 || var7 != var13)) {
               unlinkEntity(var21);
               linkEntity(var21, Render.mapSprites[Render.S_X + var2] >> 6, Render.mapSprites[Render.S_Y + var2] >> 6);
               var1 |= 2;
            }
         }

         if (!Canvas.staleView && (var3 == var6 && var4 == var7 || Render.checkTileVisibilty(var6, var7))) {
            var1 |= 4;
         }

         return var1;
      }
   }

   public static final void snapLerpSprites(int var0) {
      if (numLerpSprites != 0) {
         numCallThreads = 0;

         int var1;
         for(var1 = 0; var1 < 16; ++var1) {
            LerpSprite var2 = lerpSprites[var1];
            if (var2.hSprite != 0 && (var0 == -1 || var2.hSprite == var0 + 1)) {
               int var4;
               if (var2.thread != null && (var2.flags & 1) == 0) {
                  boolean var3 = false;

                  for(var4 = 0; var4 < numCallThreads && callThreads[var4] != var2.thread; ++var4) {
                  }

                  if (var4 == numCallThreads) {
                     callThreads[numCallThreads++] = var2.thread;
                  }
               }

               var2.startTime = 0;
               var2.travelTime = 0;
               var4 = updateLerpSprite(var2);
            }
         }

         for(var1 = 0; var1 < numCallThreads; ++var1) {
            callThreads[var1].run();
         }

         updateAttachedBombs();
      }
   }

   public static final void updateAttachedBombs() {
      for(int var0 = 0; var0 < 4; ++var0) {
         if (placedBombs[var0] > 0) {
            Entity var1 = entities[placedBombs[var0]];
            int var2 = (var1.param & 2147483392) >> 8;
            if ((var1.info & 1048576) != 0 && var2 != 0) {
               int var3 = var1.getSprite();
               Entity var4 = entities[var2];
               int var5 = var4.getSprite();
               int var6 = Render.mapSprites[Render.S_X + var3] >> 6;
               int var7 = Render.mapSprites[Render.S_Y + var3] >> 6;
               if (var6 != Render.mapSprites[Render.S_X + var5] >> 6 || var7 != Render.mapSprites[Render.S_Y + var5] >> 6) {
                  Render.relinkSprite(var3, Render.mapSprites[Render.S_X + var5] << 4, Render.mapSprites[Render.S_Y + var5] << 4, Render.mapSprites[Render.S_Z + var5] << 4);
                  unlinkEntity(var1);
                  linkEntity(var1, Render.mapSprites[Render.S_X + var5] >> 6, Render.mapSprites[Render.S_Y + var5] >> 6);
               }

               byte var8 = 25;
               if (var4.def.eType == 10 && var4.def.eSubType == 8 || var4.def.eType == 9) {
                  var8 = 0;
               }

               Render.mapSprites[Render.S_X + var3] = Render.mapSprites[Render.S_X + var5];
               Render.mapSprites[Render.S_Y + var3] = Render.mapSprites[Render.S_Y + var5];
               if ((var4.info & 16842752) == 0) {
                  Render.mapSprites[Render.S_Z + var3] = (short)(Render.mapSprites[Render.S_Z + var5] + var8);
               }
            }
         }
      }

   }

   public static final void updateLerpSprites() {
      boolean var0 = false;
      boolean var1 = false;
      boolean var2 = false;
      numCallThreads = 0;
      if (numLerpSprites > 0) {
         int var3;
         for(var3 = 0; var3 < 16; ++var3) {
            LerpSprite var4 = lerpSprites[var3];
            if (var4.hSprite != 0) {
               ScriptThread var5 = var4.thread;
               int var6 = var4.flags;
               int var7 = updateLerpSprite(var4);
               if (0 != (var7 & 1) && (var6 & 1) == 0 && null != var5) {
                  boolean var8 = false;

                  int var9;
                  for(var9 = 0; var9 < numCallThreads && callThreads[var9] != var5; ++var9) {
                  }

                  if (var9 == numCallThreads) {
                     callThreads[numCallThreads++] = var5;
                  }
               }

               var1 = var1 || (var7 & 2) != 0;
               var0 = var0 || (var7 & 4) != 0;
               var2 = true;
            }
         }

         for(var3 = 0; var3 < numCallThreads; ++var3) {
            if (callThreads[var3].inuse) {
               callThreads[var3].run();
            }
         }

         if (var1) {
            Canvas.updateFacingEntity = true;
         }

         if (var0) {
            Canvas.invalidateRect();
         }
      }

      if (var2) {
         updateAttachedBombs();
      }

   }

   public static final LerpSprite allocLerpSprite(ScriptThread var0, int var1, boolean var2) {
      LerpSprite var3 = null;
      LerpSprite var4 = null;

      for(int var5 = 0; var5 < 16; ++var5) {
         LerpSprite var6 = lerpSprites[var5];
         if (var6.hSprite == var1 + 1) {
            var3 = var6;
            break;
         }

         if (var4 == null && var6.hSprite == 0) {
            var4 = var6;
         }
      }

      if (var4 != null && var3 == null) {
         var3 = var4;
         var4.flags = 0;
         var4.hSprite = var1 + 1;
         var4.thread = var0;
         ++numLerpSprites;
      } else {
         if ((var3.flags & 2) != 0) {
            --animatingEffects;
         }

         int var10 = var3.hSprite - 1;
         Entity var7 = null;
         if (-1 != Render.mapSprites[Render.S_ENT + var10]) {
            var7 = entities[Render.mapSprites[Render.S_ENT + var10]];
         }

         if (var7 != null && var7.monster != null) {
            int var8 = (Render.mapSpriteInfo[var10] & '\uff00') >> 8;
            int var9 = var8 & 240;
            if (var9 != 32 && (var3.flags & 2048) == 0) {
               if (var9 == 48) {
                  var9 = 16;
               }
            } else {
               var9 = 0;
            }

            Render.mapSpriteInfo[var10] = Render.mapSpriteInfo[var10] & -65281 | var9 << 8;
         }
      }

      if (var3 == null) {
         App.Error(36);
      }

      if (var0 == null) {
         var3.flags |= 1;
      }

      if (var2) {
         var3.flags |= 2;
         ++animatingEffects;
      }

      return var3;
   }

   private static final void freeLerpSprite(LerpSprite var0) {
      Entity var1 = null;
      int var2 = var0.hSprite - 1;
      int var3 = Render.mapSpriteInfo[var2] & 255;
      if ((Render.mapSpriteInfo[var2] & 4194304) != 0) {
         var3 += 257;
      }

      Render.mapSprites[Render.S_X + var2] = (short)var0.dstX;
      Render.mapSprites[Render.S_Y + var2] = (short)var0.dstY;
      if ((var0.flags & 8) == 0) {
         Render.mapSprites[Render.S_Z + var2] = (short)var0.dstZ;
      }

      Render.mapSprites[Render.S_SCALEFACTOR + var2] = (short)var0.dstScale;
      if ((var0.flags & 32) == 0) {
         Render.relinkSprite(var2);
      }

      if (-1 != Render.mapSprites[Render.S_ENT + var2]) {
         var1 = entities[Render.mapSprites[Render.S_ENT + var2]];
      }

      int var4 = var0.dstX >> 6;
      int var5 = var0.dstY >> 6;
      int var6;
      int var7;
      if (null != var1 && 0 == (Render.mapSpriteInfo[var2] & 65536)) {
         if (var1.def.eType == 3 || var1.def.eType == 2 && var1.def.eSubType != 10) {
            if (var1.monster != null) {
               var1.monster.frameTime = 0;
            }

            var6 = (Render.mapSpriteInfo[var2] & '\uff00') >> 8;
            var7 = var6 & 240;
            if ((var7 == 16 || var7 == 48) && (var0.flags & 2048) == 0) {
               Render.mapSpriteInfo[var2] = Render.mapSpriteInfo[var2] & -65281 | 4096;
            } else {
               Render.mapSpriteInfo[var2] = Render.mapSpriteInfo[var2] & -65281 | 0;
            }
         }

         var6 = var1.linkIndex % 32;
         var7 = var1.linkIndex / 32;
         if (0 == (var0.flags & 16) && ((var1.info & 1048576) == 0 || var4 != var6 || var5 != var7)) {
            unlinkEntity(var1);
            linkEntity(var1, Render.mapSprites[Render.S_X + var2] >> 6, Render.mapSprites[Render.S_Y + var2] >> 6);
         }
      }

      int[] var10000;
      if ((var0.flags & 128) != 0) {
         Render.mapSprites[Render.S_SCALEFACTOR + var2] = 64;
         Render.mapSpriteInfo[var2] &= -65281;
         Canvas.updateFacingEntity = true;
         Canvas.automapDrawn = false;
         var10000 = Render.mapSpriteInfo;
         var10000[var2] &= Integer.MAX_VALUE;
      } else if ((var0.flags & 64) == 0 && (var0.flags & 512) == 0) {
         if ((var0.flags & 1032) == 1032) {
            var6 = var0.srcX - var0.dstX;
            var7 = var0.srcY - var0.dstY;
            var0.dstX = var0.srcX = Render.mapSprites[Render.S_X + var2];
            var0.dstY = var0.srcY = Render.mapSprites[Render.S_Y + var2];
            var0.srcZ = Render.mapSprites[Render.S_Z + var2];
            var0.flags &= -9;
            if (var6 != 0) {
               var6 /= Math.abs(var6);
            }

            if (var7 != 0) {
               var7 /= Math.abs(var7);
            }

            if ((var0.dstX & 63) == 32 && (var0.dstY & 63) == 32) {
               var6 *= 64;
               var7 *= 64;
            } else {
               var6 *= 31;
               var7 *= 31;
            }

            var0.dstX = (var0.dstX + var6 & -64) + 32;
            var0.dstY = (var0.dstY + var7 & -64) + 32;
            var0.dstZ = Render.getHeight(var0.dstX, var0.dstY) + 32;
            var0.height >>= 1;
            var0.startTime = App.gameTime - 100;
            var0.travelTime = 300;
            updateLerpSprite(var0);
            return;
         }

         if ((var0.flags & 256) != 0) {
            var0.flags &= -257;
            var0.flags |= 512;
            var0.srcX = var0.dstX;
            var0.srcY = var0.dstY;
            var10000 = Render.mapSpriteInfo;
            var10000[var2] |= Integer.MIN_VALUE;
            Render.mapSprites[Render.S_SCALEFACTOR + var2] = (short)((byte)var0.dstScale);
            var0.dstScale = 0;
            var0.startTime = App.gameTime;
            switch(Render.mapSpriteInfo[var2] & 251658240) {
            case 16777216:
               var0.dstX += 32;
               break;
            case 33554432:
               var0.dstX -= 32;
               break;
            case 67108864:
               var0.dstY += 32;
               break;
            case 134217728:
               var0.dstY -= 32;
            }

            var0.travelTime = 750;
            Canvas.automapDrawn = false;
            return;
         }
      } else {
         Canvas.updateFacingEntity = true;
         secretActive = false;
         Canvas.automapDrawn = false;
         unlinkEntity(var1);
         if (var1.def.eType != 5 || (var1.def.parm & 1) == 0) {
            var10000 = Render.mapSpriteInfo;
            var10000[var2] |= 65536;
         }
      }

      if ((var0.flags & 1024) != 0) {
         Render.mapSpriteInfo[var2] &= -65281;
      }

      if ((var0.flags & 2) != 0) {
         var0.flags &= -3;
         --animatingEffects;
      }

      var0.hSprite = 0;
      --numLerpSprites;
      if (var1 != null) {
         byte var9 = var1.def.eSubType;
         if (var1.monster != null && (var1.monster.goalFlags & 1) != 0) {
            var1.aiFinishLerp();
         }

         if (var1.def.eType == 2 || var1.def.eType == 10 && (var9 == 2 || var9 == 8)) {
            Entity var10 = findMapEntity(var0.dstX, var0.dstY, 256);
            if (var10 != null) {
               if (var10.def.eSubType == 1 && var1.def.eType == 2) {
                  boolean var8 = var9 == 6 || var9 == 4 && var1.def.parm > 0;
                  if (!var8) {
                     EntityMonster var11 = var1.monster;
                     var11.monsterEffects = (short)(var11.monsterEffects | 6152);
                     var11 = var1.monster;
                     var11.monsterEffects &= -387;
                  }
               } else {
                  var1.pain(5, var10);
               }
            }
         }
      }

      if ((var0.flags & 1024) != 0 && Canvas.isChickenKicking && Canvas.kickingPhase != 0) {
         var6 = Canvas.getKickPoint(var0.dstX >> 6, var0.dstY >> 6);
         Canvas.lastScore = var6;
         Render.mapSpriteInfo[var2] &= -65281;
         Canvas.curScore += var6;
         Canvas.kickingPhase = 4;
         Canvas.gridTime = App.gameTime + 2500;
      }

   }

   public static final void runScriptThreads(int var0) {
      if (numScriptThreads != 0) {
         for(int var1 = 0; var1 < 20; ++var1) {
            ScriptThread var2 = scriptThreads[var1];
            if (var2.inuse) {
               if (var2.state == 2) {
                  if (isInputBlockedByScript() && Canvas.state == 6) {
                     Canvas.setState(3);
                  }

                  if (Canvas.state == 3 || Canvas.state == 18) {
                     var2.attemptResume(var0);
                  }
               }

               if (var2.state != 2 || var2.stackPtr == 0) {
                  freeScriptThread(var2);
               }
            }
         }

      }
   }

   public static final ScriptThread allocScriptThread() {
      for(int var0 = 0; var0 < 20; ++var0) {
         ScriptThread var1 = scriptThreads[var0];
         if (!var1.inuse) {
            var1.init();
            var1.inuse = true;
            ++numScriptThreads;
            return var1;
         }
      }

      App.Error(40);
      return null;
   }

   public static final void freeScriptThread(ScriptThread var0) {
      if (!var0.inuse) {
         App.Error(102);
      } else {
         var0.reset();
         --numScriptThreads;
      }
   }

   public static final boolean isCameraActive() {
      return activeCameraView && activeCamera != null;
   }

   public static final int executeTile(int var0, int var1, int var2) {
      return executeTile(var0, var1, var2, false);
   }

   public static boolean doesScriptExist(int var0, int var1, int var2) {
      if (var0 >= 0 && var0 < 32 && var1 >= 0 && var1 < 32) {
         int var3 = var1 * 32 + var0;
         if ((Render.mapFlags[var3] & 64) != 0) {
            for(int var4 = Render.findEventIndex(var3); var4 != -1; var4 = Render.getNextEventIndex()) {
               int var5 = Render.tileEvents[var4 + 1];
               int var6 = var5 & var2;
               if ((var5 & 524288) == 0 && (var6 & 15) != 0 && (var6 & 4080) != 0 && ((var5 & 28672) == 0 && (var2 & 28672) == 0 || (var6 & 28672) != 0)) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static final int executeTile(int var0, int var1, int var2, boolean var3) {
      ScriptThread var4 = allocScriptThread();
      int var5;
      if (Canvas.state == 8) {
         var5 = var4.queueTile(var0, var1, var2, var3);
         var4.unpauseTime = -1;
      } else {
         var5 = var4.executeTile(var0, var1, var2, var3);
      }

      if (var5 != 2) {
         freeScriptThread(var4);
      }

      return var5;
   }

   public static final int executeStaticFunc(int var0) {
      if (var0 < 12 && Render.staticFuncs[var0] != 65535) {
         ScriptThread var1 = allocScriptThread();
         var1.alloc(Render.staticFuncs[var0]);
         return var1.run();
      } else {
         return 0;
      }
   }

   public static final int queueStaticFunc(int var0) {
      if (var0 < 12 && Render.staticFuncs[var0] != 65535) {
         ScriptThread var1 = allocScriptThread();
         var1.alloc(Render.staticFuncs[var0]);
         var1.flags |= 2;
         var1.unpauseTime = 0;
         Canvas.blockInputTime = App.gameTime + 1;
         return 2;
      } else {
         return 0;
      }
   }

   public static final int getSaveVersion() {
      return 21;
   }

   private static final void loadEntityStates(DataInputStream var0) {
      try {
         short var1 = var0.readShort();

         for(int var2 = 0; var2 < var1; ++var2) {
            Resource.readMarker(var0);
            int var3 = var0.readInt();
            Entity var4 = entities[var3 & '\uffff'];
            var4.loadState(var0, var3);
         }
      } catch (IOException var5) {
         App.Error(var5, 19);
      }

   }

   private static final void saveEntityStates(DataOutputStream var0, boolean var1) {
      try {
         int[] var2 = new int[275];
         short var3 = 0;

         int var4;
         for(var4 = 0; var4 < numEntities; ++var4) {
            Entity var5 = entities[var4];
            int var6 = var5.getSaveHandle(var1);
            if (var6 != -1) {
               var2[var3++] = var6;
            }
         }

         var0.writeShort(var3);

         for(var4 = 0; var4 < var3; ++var4) {
            int var8 = var2[var4];
            Entity var9 = entities[var8 & '\uffff'];
            Resource.writeMarker(var0);
            var0.writeInt(var8);
            var9.saveState(var0, var8);
         }
      } catch (IOException var7) {
         App.Error(var7, 44);
      }

   }

   public static final boolean tileObstructsAttack(int var0, int var1) {
      int var2 = Canvas.destX >> 6;
      int var3 = Canvas.destY >> 6;
      if (var0 != var2 && var1 != var3) {
         return false;
      } else {
         for(Entity var4 = combatMonsters; var4 != null; var4 = var4.monster.nextAttacker) {
            int[] var5 = var4.calcPosition();
            int var6 = var5[0] >> 6;
            int var7 = var5[1] >> 6;
            if (var6 == var0 || var7 == var1) {
               int var8;
               int var9;
               if (var6 != var2) {
                  var8 = 0;
                  var9 = 0;
                  if (var6 < var2) {
                     var8 = var6;
                     var9 = var2;
                  } else if (var6 > var2) {
                     var8 = var2;
                     var9 = var6;
                  }

                  if (var0 > var8 && var0 < var9) {
                     return true;
                  }
               }

               if (var7 != var3) {
                  var8 = 0;
                  var9 = 0;
                  if (var7 < var3) {
                     var8 = var7;
                     var9 = var3;
                  } else if (var7 > var3) {
                     var8 = var3;
                     var9 = var7;
                  }

                  if (var1 > var8 && var1 < var9) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public static final boolean isInputBlockedByScript() {
      if (numScriptThreads > 0) {
         for(int var0 = 0; var0 < 20; ++var0) {
            ScriptThread var1 = scriptThreads[var0];
            if (var1.inuse && (var1.type & 65536) != 0) {
               return true;
            }
         }
      }

      return false;
   }

   public static final void updateScriptVars() {
      scriptStateVars[0] = (short)Player.statusEffects[33];
      scriptStateVars[1] = (short)Player.ce.getStat(0);
      scriptStateVars[2] = (short)(Canvas.viewX >> 6);
      scriptStateVars[3] = (short)(Canvas.viewY >> 6);
      if (Player.gold > 32767) {
         App.Error(new Exception("Truncated the player's gold."), -1);
      }

      scriptStateVars[8] = (short)Player.gold;
   }

   public static final void awardSecret() {
      Hud.addMessage((short)126);
      ++mapSecretsFound;
      if (mapSecretsFound == totalSecrets && (Player.foundSecretsLevels & 1 << Canvas.loadMapID - 1) == 0) {
         Player.giveStandardMedal(Canvas.loadMapID, 1);
         Player.addXP(10);
         Player.foundSecretsLevels |= 1 << Canvas.loadMapID - 1;
      } else {
         Player.addXP(5);
      }

   }

   public static final int getMixingIndex(int var0) {
      for(int var1 = 0; var1 < numStations; ++var1) {
         if (mixingStations[var1 * 4] == var0) {
            return var1 * 4;
         }
      }

      return -1;
   }

   public static final void checkEmptyStation(int var0) {
      if (mixingStationEmpty(var0)) {
         int var1 = mixingStations[var0 * 4];
         Render.mapSpriteInfo[var1] = Render.mapSpriteInfo[var1] & -65281 | 256;
         short var2 = Render.mapSprites[Render.S_ENT + var1];
         if (var2 != -1) {
            Entity var3 = entities[var2];
            var3.info |= 4194304;
            var3.name = 1166;
         }
      }

   }

   public static final boolean mixingStationEmpty(int var0) {
      int var1 = mixingStations[var0 + 1] + mixingStations[var0 + 2] + mixingStations[var0 + 3];
      return var1 == 0;
   }

   public static final void addEntityDeathFunc(Entity var0, int var1) {
      int var2;
      for(var2 = 0; var2 < 32; ++var2) {
         if (entityDeathFunctions[var2 * 2] == -1) {
            entityDeathFunctions[var2 * 2 + 0] = (short)var0.getSprite();
            entityDeathFunctions[var2 * 2 + 1] = (short)var1;
            break;
         }
      }

      if (var2 == 32) {
         App.Error(new Exception("Too many entity death functions set"), -1);
      }

      var0.info |= 33554432;
   }

   public static final void removeEntityFunc(Entity var0) {
      int var1 = var0.getSprite();

      int var2;
      for(var2 = 0; var2 < 32 && entityDeathFunctions[var2 * 2] != var1; ++var2) {
      }

      if (var2 != 32) {
         entityDeathFunctions[var2 * 2 + 0] = -1;
         entityDeathFunctions[var2 * 2 + 1] = -1;
      }

      var0.info &= -33554433;
   }

   public static final void executeEntityFunc(Entity var0, boolean var1) {
      int var2 = var0.getSprite();
      short[] var3 = entityDeathFunctions;

      int var4;
      for(var4 = 0; var4 < 32 && var3[var4 * 2] != var2; ++var4) {
      }

      if (var4 != 32) {
         ScriptThread var5 = allocScriptThread();
         var5.throwAwayLoot = var1;
         var5.alloc(var3[var4 * 2 + 1]);
         var5.run();
         var3[var4 * 2 + 0] = -1;
         var3[var4 * 2 + 1] = -1;
      }

   }

   public static final void foundLoot(int var0, int var1) {
      foundLoot(Render.mapSprites[Render.S_X + var0], Render.mapSprites[Render.S_Y + var0], Render.mapSprites[Render.S_Z + var0], var1);
   }

   public static final void foundLoot(int var0, int var1, int var2, int var3) {
      lootFound = (short)(lootFound + var3);
      if (var3 > 0 && lootFound == totalLoot) {
         Player.giveStandardMedal(Canvas.loadMapID, 0);
         Player.addXP(10);
      }

   }

   public static final void destroyedObject(int var0) {
      ++destroyedObj;
      if (destroyedObj == numDestroyableObj) {
         Player.giveStandardMedal(Canvas.loadMapID, 3);
         Player.addXP(10);
      }

   }

   public static final boolean isUnderWater() {
      return scriptStateVars[6] != 0;
   }

   public static final void raiseCorpses() {
      Entity var0 = inactiveMonsters;
      int var1 = 0;
      if (var0 != null) {
         Entity var2;
         int var3;
         do {
            var2 = var0.monster.nextOnList;
            var3 = var0.getSprite();
            if ((var0.info & 65536) == 0 && (var0.info & 134217728) == 0 && (var0.info & 16777216) != 0 && !var0.isBoss() && (var0.monster.flags & 64) == 0 && findMapEntity(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], 15535) == null && (difficulty != 4 || 0 == (var0.monster.monsterEffects & 4))) {
               gridEntities[var1++] = var0;
               if (var1 == 9) {
                  break;
               }
            }

            var0 = var2;
         } while(var2 != inactiveMonsters && var1 != 4);

         for(int var6 = 0; var6 < var1; ++var6) {
            var0 = gridEntities[var6];
            var0.info |= 134217728;
            var3 = var0.getSprite();
            var0.resurrect(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], Render.mapSprites[Render.S_Z + var3]);
            activate(var0, false, false, true, true);
            if (var0.def.eSubType != 4 && var0.def.eSubType != 8) {
               var0.info |= 524288;
               Render.mapSprites[Render.S_RENDERMODE + var3] = 4;
            }

            GameSprite var4 = gsprite_allocAnim(192, Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], Render.mapSprites[Render.S_Z + var3] - 20);
            var4.flags |= 1024;
            var4.startScale = 64;
            var4.destScale = 96;
            var4.scaleStep = 40;
            int[] var5 = var0.calcPosition();
            ParticleSystem.spawnParticles(1, -3355444, var5[0], var5[1], Render.getHeight(var5[0], var5[1]) + 48);
         }
      }

   }

   public static final boolean isInFront(int var0, int var1) {
      int var2 = 256 - (Canvas.viewAngle & 1023);
      int var3 = (var0 << 6) + 32 - Canvas.viewX;
      int var4 = (var1 << 6) + 32 - Canvas.viewY;
      if (var3 == 0 && var4 == 0) {
         return true;
      } else {
         int var5 = VecToDir(var3, var4, true) + var2 & 1023;
         return var5 >= 128 && var5 <= 384;
      }
   }

   public static int VecToDir(int var0, int var1, boolean var2) {
      byte var3 = -1;
      int var4 = var2 ? 7 : 0;
      if (var0 <= -32) {
         var3 = 4;
      } else if (var0 >= 32) {
         var3 = 0;
      }

      if (var1 >= 32) {
         if (var3 == 4) {
            var3 = 5;
         } else if (var3 == 0) {
            var3 = 7;
         } else {
            var3 = 6;
         }
      } else if (var1 <= -32) {
         if (var3 == 4) {
            var3 = 3;
         } else if (var3 == 0) {
            var3 = 1;
         } else {
            var3 = 2;
         }
      }

      return var3 << var4;
   }

   public static void NormalizeVec(int var0, int var1, int[] var2) {
      int var3 = (int)FixedSqrt((long)(var0 * var0 + var1 * var1));
      var2[0] = (var0 << 12) / var3;
      var2[1] = (var1 << 12) / var3;
   }

   public static long FixedSqrt(long var0) {
      if (var0 == 0L) {
         return 0L;
      } else {
         var0 <<= 8;
         long var2 = 256L;
         var2 = var2 + var0 / var2 >> 1;
         var2 = var2 + var0 / var2 >> 1;
         var2 = var2 + var0 / var2 >> 1;
         var2 = var2 + var0 / var2 >> 1;

         for(int var6 = 0; var6 < 12; ++var6) {
            long var4 = var2 + var0 / var2 >> 1;
            if (var4 == var2) {
               break;
            }

            var2 = var4;
         }

         return var2;
      }
   }
}
