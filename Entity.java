/* Decompiler 1271ms, total 1448ms, lines 2268 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class Entity {
   public static int touchMe = 1;
   public static final int ENTITY_FLAG_GIBBED = 65536;
   public static final int ENTITY_FLAG_TAKEDAMAGE = 131072;
   public static final int ENTITY_FLAG_ACTIVE = 262144;
   public static final int ENTITY_FLAG_GHOST = 524288;
   public static final int ENTITY_FLAG_LINKED = 1048576;
   public static final int ENTITY_FLAG_HASFOUGHT = 2097152;
   public static final int ENTITY_FLAG_DROPPED = 2097152;
   public static final int ENTITY_FLAG_DIRTY = 4194304;
   public static final int ENTITY_FLAG_CORPSE = 16777216;
   public static final int ENTITY_FLAG_DEATHFUNC = 33554432;
   public static final int ENTITY_FLAG_HURT = 67108864;
   public static final int ENTITY_FLAG_RAISETARGET = 134217728;
   public static final int ENTITY_FLAG_NOSNAP = 268435456;
   EntityDef def;
   EntityMonster monster;
   Entity nextOnTile;
   Entity prevOnTile;
   short linkIndex;
   short name;
   int info;
   int param;
   public static int HARBINGER_BLOOD_POOLX = 0;
   public static int HARBINGER_BLOOD_POOLY = 0;
   private static final int HARBINGER_HEAL_TURNS = 7;
   static final int DIR_BITS = 2;
   static final int NUM_DIRS = 4;
   static final int DIR_MASK = 3;
   static final int DIR_TABLE_SHIFT = 2;
   static final int PATH_BITS = 64;
   private static final int MAX_PATH_DEPTH = 8;
   private static final int MAX_DIST = 999999999;
   private static long curPath = 0L;
   private static int pathDepth = 0;
   private static int pathSearchDepth = 8;
   private static long closestPath = 0L;
   private static int closestPathDepth = 0;
   private static int closestPathDist = 999999999;
   private static final int LINE_OF_SIGHT_YES = 0;
   private static final int LINE_OF_SIGHT_NO = 1;
   private static final int LINE_OF_SIGHT_BOTH = 2;
   private static byte[] visitOrder = new byte[8];
   private static int[] visitDist = new int[8];
   private static int[] visitedTiles = new int[32];
   public static int[] baseVisitedTiles = new int[32];
   private static int lineOfSight = 2;
   private static int lineOfSightWeight = 0;
   private static Entity findEnt;
   private static Entity skipEnt;
   private static int interactClipMask;
   private static final int[] knockbackDelta = new int[2];
   private static final int[] pos = new int[2];
   private static int[] tempSaveBuf = new int[2];

   public boolean hasHead() {
      return this.def.eType != 2 || this.def.eSubType != 4 || (this.monster.flags & 1) == 0;
   }

   public void reset() {
      this.def = null;
      this.nextOnTile = this.prevOnTile = null;
      this.linkIndex = 0;
      this.info = 0;
      this.param = 0;
      this.monster = null;
      this.name = -1;
   }

   public void initspawn() {
      byte var1 = this.def.eType;
      byte var2 = this.def.eSubType;
      this.name = (short)(this.def.name | 2048);
      int var3 = this.getSprite();
      int var4 = Render.mapSpriteInfo[var3] & 255;
      if (var1 == 2) {
         this.monster.ce = Combat.monsters[var2 * 3 + this.def.parm].clone();
         if (Game.difficulty == 4 || Game.difficulty == 2 && this.def.eSubType < 11) {
            int var7 = this.monster.ce.getStat(1);
            var7 += var7 >> 2;
            this.monster.ce.setStat(1, var7);
            this.monster.ce.setStat(0, var7);
         }

         byte var5 = 0;
         byte var6 = 64;
         Render.mapSprites[Render.S_Z + var3] = (short)(32 + Render.getHeight(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3]));
         Render.relinkSprite(var3);
         if (var2 == 8) {
            if (this.def.parm == 0) {
               var6 = 50;
            } else if (this.def.parm == 1) {
               var6 = 58;
            } else if (this.def.parm == 2) {
               var6 = 66;
            }
         } else if (var2 == 10) {
            var6 = 88;
         }

         Render.mapSprites[Render.S_SCALEFACTOR + var3] = var6;
         Render.mapSprites[Render.S_RENDERMODE + var3] = var5;
         this.info |= 131072;
      } else if (var1 == 7 && var2 != 3) {
         int[] var10000 = Render.mapSpriteInfo;
         var10000[var3] &= -65537;
         if (var4 == 173) {
            Render.mapSprites[Render.S_SCALEFACTOR + var3] = 32;
         }
      } else if (var1 == 6 && var2 == 2 && this.def.parm == 7) {
         Render.mapSprites[Render.S_SCALEFACTOR + var3] = 32;
      } else if (var1 == 10) {
         this.info |= 131072;
      } else if (this.def.eType == 9 && this.def.eSubType == 15) {
         this.info |= 4325376;
      } else if (var1 == 3) {
         this.param = 1;
      }

   }

   public int getSprite() {
      return (this.info & '\uffff') - 1;
   }

   public boolean touched() {
      byte var1 = this.def.eType;
      if (var1 != 11 && var1 != 6) {
         if (var1 == 8) {
            if (this.def.eSubType == 1 && Player.buffs[9] == 0) {
               Player.painEvent((Entity)null, false);
               Player.pain(20, this, true);
               Player.addStatusEffect(13, 5, 3);
               Player.translateStatusEffects();
            } else if (this.def.eSubType == 0) {
               Player.painEvent((Entity)null, false);
               Player.pain(20, this, true);
            }

            return true;
         }
      } else if (this.touchedItem()) {
         Game.scriptStateVars[11] = this.def.tileIndex;
         Game.executeStaticFunc(11);
         if (this.isDroppedEntity()) {
            Render.mapSprites[Render.S_ENT + this.getSprite()] = -1;
            this.def = null;
         }

         return true;
      }

      return false;
   }

   boolean touchedItem() {
      Text var1;
      int var3;
      if (this.def.eSubType != 0 && this.def.eSubType != 6) {
         if (this.def.eSubType == 4) {
            byte var5;
            if (this.def.parm != 0 && this.def.parm != 2) {
               var5 = 20;
            } else {
               var5 = 40;
            }

            if (!Player.addHealth(var5)) {
               Hud.addMessage((short)45, 2);
               return false;
            }
         } else if (this.def.eSubType == 2) {
            if (this.isDroppedEntity()) {
               var3 = this.param;
            } else {
               var3 = 2 + App.nextInt() % 4;
            }

            if (!Player.give(2, this.def.parm, var3, false)) {
               Hud.addMessage((short)89);
            } else {
               var1 = Hud.getMessageBuffer(1);
               Text.resetTextArgs();
               Text.addTextArg(var3);
               Text var4 = Text.getSmallBuffer();
               Text.composeTextField(this.name, var4);
               Text.addTextArg(var4);
               Text.composeText((short)1, (short)88, var1);
               Hud.finishMessageBuffer();
               var4.dispose();
               if (!this.isDroppedEntity()) {
                  Game.foundLoot(this.getSprite(), 1);
               }
            }
         } else if (this.def.eSubType == 1) {
            Player.give(1, this.def.parm, 1, false);
            int var2 = this.def.parm * 9;
            if (Combat.weapons[var2 + 5] != 0) {
               if ((1L << this.def.parm & 1536L) != 0L) {
                  Player.give(2, Combat.weapons[var2 + 4], 8, true);
               } else if (this.def.parm == 14) {
                  Player.give(2, Combat.weapons[var2 + 4], 100, true);
               } else {
                  Player.give(2, Combat.weapons[var2 + 4], 10, true);
               }
            }

            var1 = Hud.getMessageBuffer(1);
            Text.resetTextArgs();
            Text.addTextArg((short)2, this.def.longName);
            Text.composeText((short)1, (short)87, var1);
            Hud.finishMessageBuffer();
            Player.showWeaponHelp(this.def.parm, false);
            if (!this.isDroppedEntity()) {
               Game.foundLoot(this.getSprite(), 1);
            }
         }
      } else {
         var3 = 1;
         if (this.isDroppedEntity()) {
            var3 = this.param;
         }

         if (!Player.give(this.def.eSubType, this.def.parm, var3, false)) {
            var1 = Hud.getMessageBuffer(2);
            Text.resetTextArgs();
            Text.addTextArg((short)2, this.def.name);
            Text.composeText((short)1, (short)85, var1);
            Hud.finishMessageBuffer();
            return false;
         }

         Text.resetTextArgs();
         if (this.def.eSubType != 0 || this.def.parm != 19 && this.def.parm != 20) {
            var1 = Hud.getMessageBuffer(1);
            Text.addTextArg((short)2, this.def.longName);
            Text.composeText((short)1, (short)87, var1);
         } else {
            var1 = Hud.getMessageBuffer(3);
            Text.composeText((short)1, (short)86, var1);
            Hud.repaintFlags |= 4;
         }

         Hud.finishMessageBuffer();
         if (!this.isDroppedEntity() && this.def.parm != 18) {
            Game.foundLoot(this.getSprite(), 1);
         }
      }

      Game.remove(this);
      Sound.playSound(8);
      return true;
   }

   public boolean pain(int var1, Entity var2) {
      boolean var3 = false;
      int var4 = this.getSprite();
      if ((this.info & 131072) == 0) {
         return var3;
      } else {
         if (this.def.eType == 2) {
            int var11 = this.monster.ce.getStat(0);
            int var13 = this.monster.ce.getStat(1);
            var11 -= var1;
            if ((this.monster.flags & 4) != 0 && var11 <= 0) {
               var11 = 1;
            }

            if (this.def.eSubType >= 11 && this.def.eSubType <= 14) {
               int var14 = var13 >> 1;
               int var16 = var14 >> 1;
               int var9 = var13 - var16;
               if (var11 <= var9 && var11 + var1 > var9) {
                  var3 = Game.executeStaticFunc(2) != 0;
                  if (var11 < var14) {
                     var11 = var14 + 1;
                  }
               } else if (var11 <= var14 && var11 + var1 > var14) {
                  var3 = Game.executeStaticFunc(3) != 0;
                  if (var11 < var16) {
                     var11 = var16 + 1;
                  }
               } else if (var11 <= var16 && var11 + var1 > var16) {
                  var3 = Game.executeStaticFunc(4) != 0;
               }

               if (var3 && (this.monster.flags & 32) != 0) {
                  Combat.animLoopCount = 1;
               }
            }

            this.monster.ce.setStat(0, var11);
            if (var11 > 0) {
               if ((Combat.punchingMonster == 0 || Combat.curTarget.def.eSubType == 10) && 0 == (this.monster.monsterEffects & 2)) {
                  Render.mapSpriteInfo[var4] = Render.mapSpriteInfo[var4] & -65281 | 24576;
                  this.monster.frameTime = App.time + 250;
               }

               if (this.def.eSubType == 14 && this.monster.goalType == 6) {
                  this.monster.frameTime = Integer.MAX_VALUE;
               } else {
                  this.monster.resetGoal();
               }

               if (var11 < var13 >> 1 && this.def.eSubType == 2 && 0 == (this.monster.monsterEffects & 2) && (App.nextInt() & 3) == 0) {
                  Text.resetTextArgs();
                  Text var15 = Text.getSmallBuffer();
                  Text.composeTextField(this.name, var15);
                  Text.addTextArg(var15);
                  Hud.addMessage((short)90);
                  var15.dispose();
                  this.monster.resetGoal();
                  this.monster.goalType = 4;
                  this.monster.goalParam = (short)(2 + (App.nextInt() & 255) % 3);
               }
            } else if (0 == (this.monster.monsterEffects & 2)) {
               Render.mapSpriteInfo[var4] = Render.mapSpriteInfo[var4] & -65281 | 24576;
               if (Combat.animLoopCount > 0) {
                  this.monster.frameTime = App.time + 250;
               }

               if (Combat.punchingMonster != 0) {
                  this.monster.frameTime = Combat.animEndTime + 200;
               } else {
                  this.monster.frameTime = App.time + 250 + 200;
               }

               Render.chatZoom = false;
            }
         } else if (this.isExplodableEntity()) {
            short var10 = Render.mapSprites[Render.S_X + var4];
            short var12 = Render.mapSprites[Render.S_Y + var4];
            short var7 = Render.mapSprites[Render.S_Z + var4];
            if (!Game.isUnderWater()) {
               Game.gsprite_allocAnim(234, var10, var12, var7);
            }

            GameSprite var8 = Game.gsprite_allocAnim(242, var10, var12, var7);
            var8.flags |= 2048;
            var8.data = this;
            Game.unlinkEntity(this);
            ++Game.animatingEffects;
         } else if (this.def.eType == 10) {
            byte var5 = this.def.eSubType;
            if (var5 == 3) {
               Render.mapSpriteInfo[var4] = Render.mapSpriteInfo[var4] & -65281 | 256;
               this.param = App.getUpTimeMs() + 200;
            } else if (var5 == 1) {
               ParticleSystem.spawnParticles(2, -8826361, var4);
               Game.remove(this);
            } else if (var5 == 8) {
               ParticleSystem.spawnParticles(1, -1, var4);
               this.died(false, var2);
            } else {
               int[] var10000;
               if (var5 == 7) {
                  boolean var6 = true;
                  if (Combat.curTarget == this && (1L << Player.ce.weapon & 2032L) != 0L) {
                     ++this.param;
                     var6 = this.param > 2;
                  }

                  if (var6) {
                     ParticleSystem.spawnParticles(2, -8826361, var4);
                     Game.remove(this);
                     this.info |= 4194304;
                     var10000 = Render.mapSpriteInfo;
                     var10000[var4] |= 65536;
                  }
               } else {
                  if (var5 != 5 && var5 != 0) {
                     if (var5 == 10 || var5 == 11) {
                        ParticleSystem.spawnParticles(1, -1, var4);
                        Game.unlinkEntity(this);
                        Render.mapSpriteInfo[var4] = Render.mapSpriteInfo[var4] & -65281 | 512;
                        this.info |= 4194304;
                        return var3;
                     }

                     if (var5 == 9) {
                        ParticleSystem.spawnParticles(1, -7267291, var4);
                     }
                  } else {
                     ParticleSystem.spawnParticles(2, -8826361, var4);
                  }

                  Game.remove(this);
                  this.info |= 4194304;
                  var10000 = Render.mapSpriteInfo;
                  var10000[var4] |= 65536;
               }
            }
         }

         return var3;
      }
   }

   public void checkMonsterDeath(boolean var1) {
      if (this.monster != null && (this.monster.flags & 128) == 0) {
         Player.fillMonsterStats();
         int[] var2 = Player.monsterStats;
         int var3;
         if (var1) {
            var3 = this.monster.ce.calcXP();
            if (this.def.eSubType >= 11) {
               var3 += 130;
            }

            Player.addXP(var3);
         }

         if (var2[0] == var2[1] && (Player.killedMonstersLevels & 1 << Canvas.loadMapID - 1) == 0) {
            Player.giveStandardMedal(Canvas.loadMapID, 2);
            var3 = Player.calcLevelXP(Player.level) - Player.calcLevelXP(Player.level - 1) >> 4;
            Player.addXP(var3);
            Player.killedMonstersLevels |= 1 << Canvas.loadMapID - 1;
         }
      }

   }

   public void died(boolean var1, Entity var2) {
      int var3 = this.getSprite();
      short var4 = Render.mapSprites[Render.S_X + var3];
      short var5 = Render.mapSprites[Render.S_Y + var3];
      int var6 = Render.mapSpriteInfo[var3];
      if ((this.info & 131072) != 0 && (this.monster == null || (this.monster.flags & 4) == 0)) {
         this.info &= -131073;
         byte var7 = this.def.eType;
         byte var8 = this.def.eSubType;
         int var9;
         if (this.isExplodableEntity()) {
            if (Canvas.state == 1) {
               Canvas.setState(3);
            }

            var9 = 40;
            if (var7 == 14 && var8 == 6) {
               byte var10 = Combat.weapons[99];
               byte var11 = Combat.weapons[100];
               var9 = var10 + ((var11 - var10) * App.nextByte() >> 8);
            }

            --Game.animatingEffects;
            Sound.playSound(10);
            if (!Game.isCameraActive()) {
               Combat.hurtEntityAt(var4 >> 6, var5 >> 6, var4 >> 6, var5 >> 6, 0, var9, this, true);
               Combat.radiusHurtEntities(var4 >> 6, var5 >> 6, 1, var9 / 2, this);
            }

            Game.executeTile(var4 >> 6, var5 >> 6, 20468, true);
            var6 |= 65536;
            Game.remove(this);
            if (this.isDroppedEntity()) {
               this.info &= -4194305;
            } else {
               this.info |= 4194304;
            }

            if (var7 == 10) {
               Game.destroyedObject(var3);
            }

            Canvas.startShake(500, 4, 500);
         } else if (var7 == 10) {
            Text.resetTextArgs();
            Text var15 = Text.getSmallBuffer();
            Text.composeTextField(this.name, var15);
            Text.addTextArg(var15);
            Hud.addMessage((short)91);
            var15.dispose();
            Player.addXP(5);
            if (var8 == 3) {
               this.info |= 4194304;
               Game.unlinkEntity(this);
            } else if (var8 == 8) {
               this.dropChickenPlate(var2);
               Game.remove(this);
               this.info |= 4194304;
               var6 |= 65536;
            }

            if (this.def.eSubType != 8 && this.def.eSubType != 10) {
               Game.destroyedObject(var3);
            }
         } else {
            int var10002;
            if (var7 == 9) {
               var6 |= 65536;
               this.info |= 4259840;
               this.info &= -524289;
               if (null != this.monster) {
                  this.monster.monsterEffects = 0;
               }

               var10002 = Player.counters[4]++;
               Game.unlinkEntity(this);
            } else if (var7 == 2) {
               this.info |= 4194304;
               this.monster.resetGoal();
               Game.snapLerpSprites(this.getSprite());
               var6 = var6 & -65281 | 28672;
               this.monster.frameTime = App.time;
               if ((this.info & 65536) != 0) {
                  var6 |= 65536;
               } else {
                  this.info |= 16908288;
                  this.trimCorpsePile(var4, var5);
               }

               EntityMonster var10000;
               if (0 == (this.monster.monsterEffects & 2) && 0 == (this.monster.monsterEffects & 1)) {
                  var10000 = this.monster;
                  var10000.monsterEffects &= -32737;
                  var10000 = this.monster;
                  var10000.monsterEffects = (short)(var10000.monsterEffects | 10400);
               } else {
                  this.monster.clearEffects();
               }

               if (Game.difficulty == 4 && (this.monster.flags & 64) == 0) {
                  var9 = 2 + App.nextInt() % 3;
                  var10000 = this.monster;
                  var10000.monsterEffects &= -1541;
                  var10000 = this.monster;
                  var10000.monsterEffects = (short)(var10000.monsterEffects | var9 << 9);
                  var10000 = this.monster;
                  var10000.monsterEffects = (short)(var10000.monsterEffects | 4);
               }

               Game.deactivate(this);
               this.undoAttack();
               if (var8 >= 11 && var8 <= 14) {
                  Player.inCombat = false;
                  Game.executeStaticFunc(5);
               } else if (var8 == 9) {
                  Game.gsprite_allocAnim(192, var4, var5, Render.mapSprites[Render.S_Z + var3]);
                  var6 |= 65536;
               } else if (var8 == 10) {
                  var6 |= 65536;
               }

               this.checkMonsterDeath(var1);
               if ((this.info & 65536) != 0 || var8 == 9 || var8 == 10) {
                  this.info = this.info & -16908289 | 65536;
                  Game.unlinkEntity(this);
               }

               this.def = EntityDef.find(9, var8, this.def.parm);
               this.name = (short)(this.def.name | 2048);
               if (var1 && (this.monster.flags & 128) == 0 && (this.info & 33554432) == 0 && (this.info & 524288) == 0) {
                  if (var8 == 10) {
                     this.dropChickenPlate(var2);
                  } else {
                     this.dropItem(0);
                  }
               }

               Canvas.invalidateRect();
               short var17 = this.getIndex();
               int[] var16 = Game.placedBombs;

               int var18;
               for(var18 = 0; var18 < 4; ++var18) {
                  if (var16[var18] != 0) {
                     Entity var12 = Game.entities[var16[var18]];
                     int var13 = (var12.param & 2147483392) >> 8;
                     if (var13 != 0 && var13 == var17) {
                        int var14 = var12.getSprite();
                        Render.mapSprites[Render.S_Z + var14] = (short)(Render.getHeight(Render.mapSprites[Render.S_X + var14], Render.mapSprites[Render.S_Y + var14]) + 32);
                     }
                  }
               }

               if (Game.difficulty == 4 && (this.monster.flags & 64) == 0 && (this.info & 524288) == 0) {
                  var18 = 2 + App.nextInt() % 3;
                  var10000 = this.monster;
                  var10000.monsterEffects = (short)(var10000.monsterEffects | var18 << 9);
                  var10000 = this.monster;
                  var10000.monsterEffects = (short)(var10000.monsterEffects | 4);
               } else if ((this.info & 524288) != 0) {
                  var6 |= 65536;
                  this.info |= 4259840;
                  this.info &= -524289;
                  var10002 = Player.counters[4]++;
                  Game.unlinkEntity(this);
                  GameSprite var19 = Game.gsprite_allocAnim(192, Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], Render.mapSprites[Render.S_Z + var3] - 20);
                  var19.flags |= 1024;
                  var19.startScale = 96;
                  var19.destScale = 127;
                  var19.scaleStep = 38;
               }
            }
         }

         if ((this.info & 33554432) != 0) {
            Game.executeEntityFunc(this, this.deathByExplosion(var2));
            this.info &= -33554433;
         }

         Render.mapSpriteInfo[var3] = var6;
         Canvas.updateFacingEntity = true;
      }
   }

   private void dropChickenPlate(Entity var1) {
      byte var2 = 0;
      if (var1 != null) {
         byte var3 = var1.def.eType;
         byte var4 = var1.def.eSubType;
         if (var3 == 14 && var4 == 6 || var3 == 10 && var4 == 2 || var3 == 8 && var4 == 1 || var1 == Player.getPlayerEnt() && (1L << Player.ce.weapon & 12288L) != 0L) {
            var2 = 1;
         }
      }

      this.dropItem(var2);
   }

   private boolean deathByExplosion(Entity var1) {
      boolean var2 = false;
      if (var1 == Player.getPlayerEnt() && Player.ce.weapon == 12) {
         var2 = true;
      } else if (var1 != null && (var1.def.eType == 10 && var1.def.eSubType == 2 || var1.def.eType == 14 && var1.def.eSubType == 6)) {
         var2 = true;
      }

      return var2;
   }

   private void dropItem(int var1) {
      int var2 = this.getSprite();
      byte var3 = 0;
      byte var4 = this.def.eSubType;
      byte var5 = 1;
      boolean var6 = true;
      short var7 = App.nextByte();
      short var8 = App.nextByte();
      boolean var9 = this.def.eType == 9 && this.def.eSubType == 10;
      var9 = var9 || this.def.eType == 10 && var4 == 8;
      if (var9) {
         boolean var10 = var1 == 1;
         if (var10) {
            var3 = 118;
         } else {
            var3 = 117;
         }

         var6 = false;
      } else if (this.def.eType == 9) {
         if (!Player.god && var7 < 145) {
            return;
         }

         if (var4 != 1 && var4 != 0) {
            if (var4 == 3) {
               if (Game.difficulty == 4 && this.def.parm == 2) {
                  var3 = 116;
               }
            } else if (var4 == 7) {
               if (Game.difficulty == 4) {
                  if (var8 > 100) {
                     if (this.def.parm > 0) {
                        var3 = 116;
                     } else {
                        var3 = 113;
                     }
                  } else {
                     var3 = 85;
                     var5 = 4;
                  }
               } else if ((var8 & 1) == 0) {
                  var3 = 113;
               } else {
                  var3 = 85;
                  var5 = 4;
               }
            } else if (var4 == 6) {
               if (Game.difficulty != 4) {
                  if (this.def.parm == 0) {
                     var3 = 90;
                     var5 = 4;
                  } else {
                     var3 = 86;
                     var5 = 9;
                  }
               } else {
                  var3 = 116;
               }
            } else if (var4 == 5 && Game.difficulty != 4) {
               if (this.def.parm == 0) {
                  var3 = 91;
                  var5 = 3;
               } else {
                  var3 = 86;
                  var5 = 9;
               }
            }
         } else if (var8 < 70) {
            var3 = 113;
         } else if (this.def.parm == 2) {
            if (Game.difficulty == 4) {
               var3 = 116;
            } else {
               var3 = 89;
               var5 = 2;
            }
         } else if (var8 >= 204 && var4 != 0 && Game.difficulty != 4) {
            var3 = 5;
         } else {
            var3 = 85;
            var5 = 4;
         }
      }

      if (var3 != 0) {
         EntityDef var11 = EntityDef.lookup(var3);
         Game.spawnDropItem(Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], var3, 6, var11.eSubType, var11.parm, var5, var6);
      }

   }

   void aiCalcSimpleGoal(boolean var1) {
      EntityMonster var10000;
      if ((this.monster.flags & 2048) != 0) {
         var10000 = this.monster;
         var10000.flags &= -8193;
         this.monster.goalType = 4;
         this.monster.goalParam = 3;
      } else {
         if (this.def.eSubType == 9) {
            if (this.aiCalcTormentorGoal()) {
               return;
            }
         } else if (this.def.eSubType == 14 && this.aiCalcHarbingerGoal()) {
            return;
         }

         if (Player.buffs[11] > 0 && (1 << this.def.eSubType & 29184) == 0) {
            this.monster.goalType = 4;
            this.monster.goalParam = 1;
         } else {
            if ((207 & 1 << this.def.eSubType) != 0) {
               short var2 = this.getIndex();
               int[] var3 = Game.placedBombs;

               for(int var4 = 0; var4 < 4; ++var4) {
                  if (var3[var4] != 0) {
                     Entity var5 = Game.entities[var3[var4]];
                     int var6 = (var5.param & 2147483392) >> 8;
                     if (var6 == var2) {
                        this.monster.goalType = 4;
                        this.monster.goalParam = 1;
                        return;
                     }
                  }
               }
            }

            Entity var7 = Game.entities[1];
            int var8 = this.aiWeaponForTarget(var7);
            boolean var9 = false;
            if (var8 != -1) {
               this.monster.ce.weapon = var8;
               var9 = true;
            }

            if (var9) {
               this.monster.goalType = 3;
               this.monster.goalParam = 1;
               if ((1 << this.def.eSubType & 12430) != 0) {
                  var10000 = this.monster;
                  var10000.flags = (short)(var10000.flags | 1);
               }

               if (!var1 && this.def.eSubType == 0 && App.nextByte() < 30) {
                  this.monster.goalType = 5;
                  this.monster.goalParam = 1;
               }
            } else if (this.def.eSubType == 2 && (this.monster.flags & 256) != 0) {
               this.monster.goalType = 4;
               this.monster.goalTurns = 10;
               this.monster.goalParam = 1;
            } else {
               this.monster.goalType = 2;
               this.monster.goalParam = 1;
               if ((1 << this.def.eSubType & 12430) != 0) {
                  var10000 = this.monster;
                  var10000.flags = (short)(var10000.flags & -2);
               }
            }

         }
      }
   }

   public boolean aiCalcTormentorGoal() {
      int var1 = 256 - (this.monster.ce.getStat(0) << 8) / this.monster.ce.getStat(1);
      if (App.nextByte() <= var1) {
         Entity[] var2 = Game.gridEntities;
         int var3 = this.linkIndex % 32 << 6;
         int var4 = this.linkIndex / 32 << 6;
         var2[0] = Game.findMapEntity(var3, var4, 512);
         var2[1] = Game.findMapEntity(var3 - 64, var4, 512);
         var2[2] = Game.findMapEntity(var3 + 64, var4, 512);
         var2[3] = Game.findMapEntity(var3, var4 - 64, 512);
         var2[4] = Game.findMapEntity(var3, var4 + 64, 512);
         var2[5] = Game.findMapEntity(var3 - 64, var4 - 64, 512);
         var2[6] = Game.findMapEntity(var3 - 64, var4 + 64, 512);
         var2[7] = Game.findMapEntity(var3 + 64, var4 - 64, 512);
         var2[8] = Game.findMapEntity(var3 + 64, var4 + 64, 512);

         for(int var5 = 0; var5 < 9; ++var5) {
            if (var2[var5] != null) {
               Game.trace(var3 + 32, var4 + 32, (var2[var5].linkIndex % 32 << 6) + 32, (var2[var5].linkIndex / 32 << 6) + 32, this, 15535, 16);
               if (Game.numTraceEntities == 0) {
                  if (var5 == 0) {
                     this.monster.goalType = 3;
                     this.monster.ce.weapon = 42;
                  } else {
                     this.monster.goalType = 2;
                  }

                  this.monster.goalParam = var2[var5].getIndex();
                  return true;
               }
            }
         }
      }

      return false;
   }

   private final boolean aiCalcHarbingerGoal() {
      ++this.param;
      int var1 = 256 - (this.monster.ce.getStat(0) << 8) / this.monster.ce.getStat(1);
      if (App.nextByte() <= var1 && this.param >= 7) {
         Entity var2 = Game.findMapEntity(HARBINGER_BLOOD_POOLX, HARBINGER_BLOOD_POOLY);
         Entity var3 = null;

         boolean var4;
         for(var4 = false; var2 != null; var2 = var2.nextOnTile) {
            if (var2.def.eType == 14) {
               var3 = var2;
            } else if (var2.def.eType == 1) {
               var4 = true;
            }
         }

         if (var4) {
            return false;
         } else {
            int[] var5 = var3.calcPosition();
            if (var5[0] >> 6 == this.linkIndex % 32 && var5[1] >> 6 == this.linkIndex / 32) {
               this.monster.goalType = 3;
               this.monster.goalParam = var3.getIndex();
               this.monster.ce.weapon = 41;
            } else {
               this.monster.goalType = 2;
               this.monster.goalParam = var3.getIndex();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void aiMoveToGoal() {
      byte var1 = this.monster.goalType;
      EntityMonster var2 = this.monster;
      if (var1 != 2 && var1 != 1 && var1 != 4 && var1 != 5) {
         if (var1 == 3) {
            if (var2.goalParam == 1) {
               var2.target = null;
            } else {
               var2.target = Game.entities[var2.goalParam];
            }

            this.attack();
         }
      } else {
         boolean var3 = this.aiGoal_MOVE();
         if (!var3 && this.def.eSubType == 0 && var1 == 5) {
            this.aiChooseNewGoal(false);
            this.aiMoveToGoal();
         }
      }

   }

   private void aiChooseNewGoal(boolean var1) {
      byte var2 = this.def.eSubType;
      this.monster.resetGoal();
      this.aiCalcSimpleGoal(var1);
      if ((1 << var2 & 12430) != 0 && this.monster.goalType == 3) {
         EntityMonster var10000 = this.monster;
         var10000.goalFlags = (byte)(var10000.goalFlags | 8);
      }

   }

   private boolean aiIsValidGoal() {
      byte var1 = this.monster.goalType;
      if (this.monster.goalTurns < 16 && var1 != 3 && var1 != 0) {
         if (var1 == 2) {
            Entity var2 = Game.entities[this.monster.goalParam];
            if (this.monster.goalParam != 1) {
               int[] var4 = var2.calcPosition();
               Entity var3 = Game.findMapEntity(var4[0], var4[1], 15535);
               if (var2.linkIndex != this.linkIndex && (var3 == null || var3 == var2)) {
                  return true;
               }
            }
         } else if (var1 == 1) {
            if (this.linkIndex != this.monster.goalX + this.monster.goalY * 32) {
               return true;
            }
         } else {
            if (var1 == 4 || var1 == 5) {
               return this.monster.goalTurns < this.monster.goalParam;
            }

            if (var1 == 6) {
               if (this.monster.goalTurns < this.monster.goalParam) {
                  return true;
               }

               if (this.monster.goalTurns == this.monster.goalParam) {
                  this.monster.resetGoal();
                  return false;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean aiIsAttackValid() {
      EntityMonster var1 = this.monster;
      int var2 = var1.ce.weapon;
      int[] var3 = this.calcPosition();
      Game.trace(var3[0], var3[1], Game.destX, Game.destY, this, 5295, 2);
      Entity var4 = Game.traceEntity;
      if (var4 != null) {
         boolean var5 = Combat.weapons[var2 * 9 + 3] >= Combat.WorldDistToTileDist(this.distFrom(Game.destX, Game.destY));
         boolean var6 = false;
         if (var5) {
            int var7 = var3[0] - Game.destX;
            int var8 = var3[1] - Game.destY;
            var6 = (var7 != 0 || var8 != 0) && (var7 == 0 || var8 == 0);
         }

         if (var1.target == null && var4.def.eType == 1 && var5 && var6) {
            return true;
         }

         if (var1.target == var4 && var5 && var6) {
            return true;
         }
      }

      return false;
   }

   public void aiThink(boolean var1) {
      EntityMonster var2 = this.monster;
      if ((var2.flags & 1024) != 0) {
         var2.flags &= -1025;
      }

      if ((var2.flags & 32) == 0) {
         if (!this.aiIsValidGoal()) {
            this.aiChooseNewGoal(var1);
         }

         ++var2.goalTurns;
         this.aiMoveToGoal();
      }
   }

   private int aiWeaponForTarget(Entity var1) {
      int var2 = this.getSprite();
      int var8;
      int var9;
      if (var1.def.eType == 1) {
         var8 = Canvas.viewX;
         var9 = Canvas.viewY;
      } else {
         int var10 = var1.getSprite();
         var8 = Render.mapSprites[Render.S_X + var10];
         var9 = Render.mapSprites[Render.S_Y + var10];
      }

      int var11 = var8 - Render.mapSprites[Render.S_X + var2];
      int var12 = var9 - Render.mapSprites[Render.S_Y + var2];
      if (var11 != 0 && var12 != 0) {
         return -1;
      } else {
         boolean var13 = false;
         Game.trace(Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], var8, var9, this, 5295, 2);
         if (Game.traceEntity != var1) {
            if (this.def.eSubType != 14) {
               return -1;
            }

            Game.traceEntity = Game.findMapEntity(Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2], 16384);
            if (Game.traceEntity != var1) {
               return -1;
            }
         }

         int var14 = var11;
         int var15 = var12;
         if (var11 != 0) {
            var14 = var11 / Math.abs(var11);
         }

         if (var12 != 0) {
            var15 = var12 / Math.abs(var12);
         }

         Game.trace(Render.mapSprites[Render.S_X + var2] + var14 * 18, Render.mapSprites[Render.S_Y + var2] + var15 * 18, var8, var9, this, 15791, 2);
         var13 = Game.traceEntity == var1;
         int var16 = Combat.getMonsterField(this.def, 0);
         int var17 = Combat.getMonsterField(this.def, 1);
         if (!var13) {
            if ((1L << var16 & 62466429059087L) != 0L) {
               var16 = 0;
            }

            if ((1L << var17 & 62466429059087L) != 0L) {
               var17 = 0;
            }
         }

         if (this.def.eSubType == 4) {
            if (this.def.parm == 2 && (this.monster.flags & 1) == 0) {
               var17 = 26;
            }

            if (Player.buffs[9] > 0) {
               if ((1L << var16 & 1100686032896L) != 0L) {
                  var16 = 0;
               }

               if ((1L << var17 & 1100686032896L) != 0L) {
                  var17 = 0;
               }
            }
         }

         int var7 = -1;
         int var6 = -1;
         int var5 = Combat.WorldDistToTileDist(var1.distFrom(Render.mapSprites[Render.S_X + var2], Render.mapSprites[Render.S_Y + var2]));
         if (var16 == var17) {
            var17 = 0;
         }

         if (this.def.eSubType == 9 && var1.def.eType == 9) {
            return var5 == 0 ? 42 : -1;
         } else if (this.def.eSubType == 14 && var1.def.eType == 14) {
            return var5 == 0 ? 41 : -1;
         } else {
            byte[] var18 = Combat.weapons;
            int var3;
            if (var16 != 0) {
               var3 = var16 * 9;
               if (var18[var3 + 2] <= var5 && var18[var3 + 3] >= var5) {
                  var6 = var3;
               }
            }

            if (var17 != 0) {
               var3 = var17 * 9;
               if (var18[var3 + 2] <= var5 && var18[var3 + 3] >= var5) {
                  var7 = var3;
               }
            }

            if (this.def.eSubType == 2 && this.def.parm == 0) {
               if ((this.monster.flags & 256) == 0 && this.monster.ce.getStat(0) < 10) {
                  var6 = -1;
               } else if ((this.monster.flags & 256) != 0) {
                  var7 = -1;
                  var6 = -1;
               } else {
                  var7 = -1;
               }
            }

            if ((1L << var16 & 18210929770496L) != 0L || (1L << var17 & 18210929770496L) != 0L) {
               boolean var19 = true;
               int var20 = Render.mapSprites[Render.S_X + var2] >> 6;
               int var21 = Render.mapSprites[Render.S_Y + var2] >> 6;

               do {
                  if ((baseVisitedTiles[var21] & 1 << var20) != 0) {
                     var19 = false;
                     break;
                  }

                  var20 += var14;
                  var21 += var15;
                  --var5;
               } while(var5 > 0);

               if ((1L << var16 & 18210929770496L) != 0L && !var19) {
                  var6 = -1;
               }

               if ((1L << var17 & 18210929770496L) != 0L && !var19) {
                  var7 = -1;
               }
            }

            int var4;
            if (var7 != -1 && var6 != -1) {
               var4 = App.nextByte() <= Combat.getMonsterField(this.def, 2) ? var16 : var17;
            } else if (var7 != -1) {
               var4 = var17;
            } else {
               if (var6 == -1) {
                  return -1;
               }

               var4 = var16;
            }

            return var4;
         }
      }
   }

   public LerpSprite aiInitLerp(int var1) {
      int var2 = this.getSprite();
      LerpSprite var3 = Game.allocLerpSprite((ScriptThread)null, var2, true);
      var3.srcX = Render.mapSprites[Render.S_X + var2];
      var3.srcY = Render.mapSprites[Render.S_Y + var2];
      var3.srcZ = Render.mapSprites[Render.S_Z + var2];
      var3.dstX = 32 + (this.monster.goalX << 6);
      var3.dstY = 32 + (this.monster.goalY << 6);
      var3.dstZ = 32 + Render.getHeight(var3.dstX, var3.dstY);
      var3.srcScale = var3.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var2];
      var3.startTime = App.gameTime;
      var3.travelTime = var1;
      var3.flags |= 17;
      this.monster.frameTime = App.time + var1;
      var3.calcDist();
      EntityMonster var10000 = this.monster;
      var10000.goalFlags = (byte)(var10000.goalFlags | 1);
      return var3;
   }

   public void aiFinishLerp() {
      EntityMonster var10000 = this.monster;
      var10000.goalFlags &= -2;
      if ((this.monster.flags & 4096) != 0) {
         var10000 = this.monster;
         var10000.flags &= -4097;
      } else {
         this.aiReachedGoal_MOVE();
      }

   }

   private boolean checkLineOfSight(int var1, int var2, int var3, int var4, int var5) {
      int var6 = var3 - var1;
      int var7 = var4 - var2;
      if (var6 != 0 && var7 != 0) {
         return false;
      } else {
         if (var6 != 0) {
            var6 /= Math.abs(var6);
         }

         if (var7 != 0) {
            var7 /= Math.abs(var7);
         }

         while(var1 != var3 && var2 != var4) {
            var1 += var6;
            var2 += var7;
            if (null != Game.findMapEntity(var1 << 6, var2 << 6, var5)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean calcPath(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = var3 - var1;
      int var8 = var4 - var2;
      int var9 = var7 * var7 + var8 * var8;
      byte[] var10 = visitOrder;
      int[] var11 = visitDist;
      int[] var10000 = visitedTiles;
      var10000[var2] |= 1 << var1;
      boolean var12 = this.checkLineOfSight(var1, var2, var3, var4, var5 | 256);
      if (lineOfSight == 0 && !var12 || lineOfSight == 1 && var12) {
         if (var6) {
            var9 -= 30;
         } else {
            var9 += 30;
         }
      }

      if (var12) {
         var9 += lineOfSightWeight;
      }

      if (pathDepth > 0 && (!var6 && var9 < closestPathDist || var6 && var9 > closestPathDist)) {
         closestPath = curPath;
         closestPathDepth = pathDepth;
         closestPathDist = var9;
      }

      if (var1 == var3 && var2 == var4) {
         closestPath = curPath;
         closestPathDepth = pathDepth;
         closestPathDist = var9;
         return true;
      } else if (pathDepth == pathSearchDepth) {
         return false;
      } else {
         int var15 = 0;
         byte[] var16 = Canvas.viewStepValues;
         int var17 = 4;
         byte var18 = (byte)(App.nextByte() & 3);

         while(true) {
            int var13;
            int var14;
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              --var17;
                              if (var17 < 0) {
                                 int var19;
                                 int var20;
                                 int var21;
                                 for(var19 = 0; var19 < var15; ++var19) {
                                    for(var20 = 0; var20 < var15 - var19 - 1; ++var20) {
                                       var21 = var11[var20] - var11[var20 + 1];
                                       if (!var6 && var21 > 0 || var6 && var21 < 0) {
                                          var13 = var11[var20 + 1];
                                          var11[var20 + 1] = var11[var20];
                                          var11[var20] = var13;
                                          byte var24 = var10[var20 + 1];
                                          var10[var20 + 1] = var10[var20];
                                          var10[var20] = (byte)var24;
                                       }
                                    }
                                 }

                                 var19 = 0;
                                 var20 = 0;

                                 for(var21 = 0; var21 < var15; ++var21) {
                                    var19 |= (var10[var21] & 3) << var20;
                                    var20 += 2;
                                 }

                                 for(int var22 = 0; var22 < var15; ++var22) {
                                    var21 = var19 & 3;
                                    var19 >>= 2;
                                    var13 = var1 + (var16[var21 << 2] >> 6);
                                    var14 = var2 + (var16[(var21 << 2) + 1] >> 6);
                                    Game.trace((var1 << 6) + 32, (var2 << 6) + 32, (var13 << 6) + 32, (var14 << 6) + 32, skipEnt, var5, 16);
                                    if (findEnt != null && Game.traceEntity == findEnt) {
                                       closestPath = curPath;
                                       closestPathDepth = pathDepth;
                                       closestPathDist = var9;
                                       return true;
                                    }

                                    int var23 = interactClipMask;
                                    if (Game.traceEntity != null) {
                                       var23 = 1 << Game.traceEntity.def.eType;
                                    }

                                    if (var23 == 0 || (var23 & interactClipMask) != 0) {
                                       ++pathDepth;
                                       curPath >>= 2;
                                       curPath &= 4611686018427387903L;
                                       curPath |= (long)var21 << 62;
                                       if (this.calcPath(var13, var14, var3, var4, var5, var6)) {
                                          return true;
                                       }

                                       --pathDepth;
                                       curPath <<= 2;
                                    }
                                 }

                                 return false;
                              }

                              var18 = (byte)(var18 + 1 & 3);
                              var13 = var1 + (var16[var18 << 2] >> 6);
                              var14 = var2 + (var16[(var18 << 2) + 1] >> 6);
                           } while(var14 < 0);
                        } while(var14 >= 32);
                     } while(var13 < 0);
                  } while(var13 >= 32);
               } while(null != Game.findMapEntity(var13 << 6, var14 << 6, 256));
            } while(0 != (visitedTiles[var14] & 1 << var13));

            var10[var15] = var18;
            var7 = var3 - var13;
            var8 = var4 - var14;
            var11[var15] = var7 * var7 + var8 * var8;
            var12 = this.checkLineOfSight(var13, var14, var3, var4, var5 | 256);
            if (lineOfSight == 0 && !var12 || lineOfSight == 1 && var12) {
               if (var6) {
                  var11[var15] -= 30;
               } else {
                  var11[var15] += 30;
               }
            }

            if (var12) {
               var11[var15] += lineOfSightWeight;
            }

            ++var15;
         }
      }
   }

   private boolean aiGoal_MOVE() {
      boolean var1 = false;
      int var2 = this.getSprite();
      Game.snapLerpSprites(var2);
      short var3 = Render.mapSprites[Render.S_X + var2];
      short var4 = Render.mapSprites[Render.S_Y + var2];
      closestPath = 0L;
      closestPathDepth = 0;
      closestPathDist = 999999999;
      curPath = 0L;
      pathDepth = 0;
      pathSearchDepth = 8;
      findEnt = null;
      skipEnt = this;
      lineOfSight = 2;
      lineOfSightWeight = 0;
      interactClipMask = 32;
      System.arraycopy(baseVisitedTiles, 0, visitedTiles, 0, visitedTiles.length);
      if (this.monster.goalType == 2 && this.monster.goalParam == 1) {
         findEnt = Game.entities[1];
         this.monster.goalX = Game.destX >> 6;
         this.monster.goalY = Game.destY >> 6;
         lineOfSightWeight = -4;
      } else if (this.monster.goalType == 5) {
         this.monster.goalX = Game.destX >> 6;
         this.monster.goalY = Game.destY >> 6;
         interactClipMask = 0;
         var1 = true;
         lineOfSight = 1;
         pathSearchDepth = this.monster.goalParam;
      } else if (this.monster.goalType == 4) {
         this.monster.goalX = Game.destX >> 6;
         this.monster.goalY = Game.destY >> 6;
         var1 = true;
         lineOfSight = 1;
      } else if (this.monster.goalType == 2) {
         findEnt = Game.entities[this.monster.goalParam];
         this.monster.goalX = findEnt.linkIndex % 32;
         this.monster.goalY = findEnt.linkIndex / 32;
      }

      if (var1) {
         closestPathDist = 0;
      }

      boolean var5 = this.calcPath(var3 >> 6, var4 >> 6, this.monster.goalX, this.monster.goalY, 15535, var1);
      if (!var5 && closestPathDist < 999999999) {
         var5 = true;
         curPath = closestPath;
         pathDepth = closestPathDepth;
      }

      if (var5 && pathDepth > 0) {
         curPath >>= 64 - pathDepth * 2;
         this.info &= -268435457;
         int var6 = var3 + Canvas.viewStepValues[(int)((curPath & 3L) << 2)];
         int var7 = var4 + Canvas.viewStepValues[(int)((curPath & 3L) << 2) + 1];
         this.monster.goalX = var6 >> 6;
         this.monster.goalY = var7 >> 6;
         Game.trace(var3, var4, var6, var7, this, interactClipMask, 25);
         if (Game.numTraceEntities == 0) {
            Game.unlinkEntity(this);
            Game.linkEntity(this, var6 >> 6, var7 >> 6);
            int var8 = Math.min(var3, var6) - 16;
            int var9 = Math.max(var3, var6) + 16;
            int var10 = Math.min(var4, var7) - 16;
            int var11 = Math.max(var4, var7) + 16;
            if (!Render.cullBoundingBox(var8 << 4, var10 << 4, var9 << 4, var11 << 4, true)) {
               this.info |= 268435456;
            }

            Game.interpolatingMonsters = true;
            this.aiInitLerp(275);
         } else {
            this.monster.goalX = var3 >> 6;
            this.monster.goalY = var4 >> 6;
            if (Game.traceEntity.def.eType == 5) {
               Game.performDoorEvent(0, Game.traceEntity, 2);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void aiReachedGoal_MOVE() {
      EntityMonster var1 = this.monster;
      EntityDef var2 = this.def;
      this.info &= -268435457;
      if (var1.goalType != 4 && var1.goalType != 5 && (var2.eSubType == 9 || (1 << var2.eSubType & 12430) != 0 && 0 == (var1.flags & 1) || var2.eSubType == 13 || var2.eSubType == 14)) {
         Entity var3 = Game.entities[1];
         if ((var2.eSubType == 9 || var2.eSubType == 14) && var1.goalType == 2) {
            var3 = Game.entities[var1.goalParam];
         }

         int var4 = this.aiWeaponForTarget(var3);
         if (var4 != -1) {
            if (var3 == Game.entities[1]) {
               var1.target = null;
            } else {
               var1.target = var3;
            }

            var1.ce.weapon = var4;
            this.attack();
            return;
         }
      }

      if ((1 << var2.eSubType & 12430) != 0) {
         var1.flags = (short)(var1.flags | 1);
      }

      if ((var1.goalFlags & 16) != 0) {
         var1.goalFlags &= -17;
         this.aiCalcSimpleGoal(false);
         if (var1.goalType == 1 || var1.goalType == 2) {
            if (!Game.tileObstructsAttack(var1.goalX, var1.goalY)) {
               this.aiGoal_MOVE();
            } else {
               var1.resetGoal();
            }
         }
      }

   }

   public int distFrom(int var1, int var2) {
      int[] var3 = this.calcPosition();
      return Math.max((var1 - var3[0]) * (var1 - var3[0]), (var2 - var3[1]) * (var2 - var3[1]));
   }

   private void attack() {
      if ((this.monster.flags & 1024) == 0) {
         EntityMonster var10000 = this.monster;
         var10000.flags = (short)(var10000.flags | 1024);
         this.monster.nextAttacker = Game.combatMonsters;
         Game.combatMonsters = this;
      }

   }

   public void undoAttack() {
      if ((this.monster.flags & 1024) != 0) {
         EntityMonster var10000 = this.monster;
         var10000.flags &= -1025;
         int var1 = this.getSprite();
         int var2 = this.monster.ce.weapon;
         if (var2 == 36 || var2 == 39 || var2 == 28) {
            int[] var5 = Render.mapSpriteInfo;
            var5[var1] &= -65281;
         }

         this.monster.resetGoal();
         Entity var3 = Game.combatMonsters;

         Entity var4;
         for(var4 = null; var3 != null && var3 != this; var3 = var3.monster.nextAttacker) {
            var4 = var3;
         }

         if (var4 != null) {
            var4.monster.nextAttacker = this.monster.nextAttacker;
         } else if (Game.combatMonsters != null) {
            Game.combatMonsters = this.monster.nextAttacker;
         }

      }
   }

   private void trimCorpsePile(int var1, int var2) {
      Entity var3 = Game.inactiveMonsters;
      if (var3 != null) {
         int var4 = 0;

         do {
            int var5 = var3.getSprite();
            if (Render.mapSprites[Render.S_X + var5] == var1 && Render.mapSprites[Render.S_Y + var5] == var2 && (var3.info & 16842752) != 0 && (Render.mapSpriteInfo[var5] & 65536) == 0) {
               ++var4;
               if (var4 >= 3) {
                  int[] var10000 = Render.mapSpriteInfo;
                  var10000[var5] |= 65536;
                  var3.info = var3.info & -16777217 | 65536;
                  Game.unlinkEntity(var3);
               }
            }

            var3 = var3.monster.nextOnList;
         } while(var3 != Game.inactiveMonsters);
      }

   }

   public void knockback(int var1, int var2, int var3) {
      int[] var4 = knockbackDelta;
      if (var3 != 0) {
         short var5;
         int var6;
         int var7;
         int var8;
         if (this.def.eType == 1) {
            var6 = Game.destX;
            var7 = Game.destY;
            var5 = 13501;
         } else {
            var8 = this.getSprite();
            var6 = Render.mapSprites[Render.S_X + var8];
            var7 = Render.mapSprites[Render.S_Y + var8];
            var5 = 15535;
         }

         var4[0] = var6 - var1;
         var4[1] = var7 - var2;
         if (var4[0] != 0) {
            var4[0] /= Math.abs(var4[0]);
            Canvas.knockbackStart = var6;
            Canvas.knockbackWorldDist = Math.abs(64 * var4[0] * var3);
         }

         if (var4[1] != 0) {
            var4[1] /= Math.abs(var4[1]);
            Canvas.knockbackStart = var7;
            Canvas.knockbackWorldDist = Math.abs(64 * var4[1] * var3);
         }

         int var9 = var6 + 64 * var4[0] * var3;
         int var10 = var7 + 64 * var4[1] * var3;
         var8 = this.getFarthestKnockbackDist(var6, var7, var9, var10, this, var5, 16, var3);
         if (var8 != 0 && (var4[0] != 0 || var4[1] != 0)) {
            var9 = var6 + var4[0] * var8 * 64 >> 6;
            var10 = var7 + var4[1] * var8 * 64 >> 6;
            if (this.def.eType == 1) {
               if (this.def.eSubType != 1) {
                  Canvas.knockbackX = var4[0];
                  Canvas.knockbackY = var4[1];
                  Canvas.knockbackDist = var8;
               }
            } else {
               this.monster.goalType = 1;
               this.monster.goalX = var9;
               this.monster.goalY = var10;
               EntityMonster var10000 = this.monster;
               var10000.flags = (short)(var10000.flags | 4096);
               LerpSprite var11 = this.aiInitLerp(400);
               Game.unlinkEntity(this);
               Game.linkEntity(this, var9, var10);
               Game.interpolatingMonsters = true;
               Game.updateLerpSprite(var11);
            }

         }
      }
   }

   public final int getFarthestKnockbackDist(int var1, int var2, int var3, int var4, Entity var5, int var6, int var7, int var8) {
      int var9 = var8;
      Game.trace(var1, var2, var3, var4, var5, var6, var7);
      Entity var10 = Game.traceEntity;
      if (var10 != null) {
         int var11 = Game.traceFracs[0];
         var9 = var8 * var11 >> 14;
      }

      return var9;
   }

   public void resurrect(int var1, int var2, int var3) {
      int var4 = this.getSprite();
      this.def = EntityDef.find(2, this.def.eSubType, this.def.parm);
      this.name = (short)(this.def.name | 2048);
      this.monster.clearEffects();
      Render.mapSprites[Render.S_X + var4] = (short)var1;
      Render.mapSprites[Render.S_Y + var4] = (short)var2;
      Render.mapSprites[Render.S_Z + var4] = (short)var3;
      int[] var10000 = Render.mapSpriteInfo;
      var10000[var4] &= -261889;
      if ((App.nextInt() & 1) != 0) {
         var10000 = Render.mapSpriteInfo;
         var10000[var4] |= 131072;
      }

      Render.relinkSprite(var4);
      this.info &= -151060481;
      this.info |= 131072;
      CombatEntity var5 = this.monster.ce;
      this.initspawn();
      this.monster.ce = var5;
      this.monster.ce.setStat(0, var5.getStat(1));
      Game.unlinkEntity(this);
      Game.linkEntity(this, var1 >> 6, var2 >> 6);
      Canvas.updateFacingEntity = true;
   }

   public int[] calcPosition() {
      int var1;
      int var2;
      if (this.def.eType == 0) {
         var1 = Game.traceCollisionX;
         var2 = Game.traceCollisionY;
      } else if (this.def.eType == 1) {
         var1 = Canvas.destX;
         var2 = Canvas.destY;
      } else {
         int var3;
         if (this.def.eType == 2) {
            var3 = this.getSprite();
            var1 = Render.mapSprites[Render.S_X + var3];
            var2 = Render.mapSprites[Render.S_Y + var3];
         } else {
            var3 = this.getSprite();
            var1 = Render.mapSprites[Render.S_X + var3];
            var2 = Render.mapSprites[Render.S_Y + var3];
         }
      }

      pos[0] = var1;
      pos[1] = var2;
      return pos;
   }

   public boolean isBoss() {
      return this.def.eSubType >= 11 && this.def.eSubType <= 14;
   }

   public boolean isHasteResistant() {
      return this.def.eType == 2 && (this.def.eSubType == 14 || this.def.eSubType == 13 || this.def.eSubType == 12);
   }

   public boolean isExplodableEntity() {
      if (this.def.eType == 10 && this.def.eSubType == 2) {
         return true;
      } else {
         return this.def.eType == 14 && this.def.eSubType == 6;
      }
   }

   private boolean isDroppedEntity() {
      short var1 = this.getIndex();
      return var1 >= Game.firstDropIndex && var1 < Game.firstDropIndex + 16;
   }

   private boolean isBinaryEntity(int[] var1) {
      boolean var2 = false;
      if (this.def == null) {
         return false;
      } else if (this.isDroppedEntity()) {
         return false;
      } else {
         int var3;
         switch(this.def.eType) {
         case 5:
            var3 = this.getSprite();
            if (Render.mapSprites[Render.S_SCALEFACTOR + var3] != 64) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (this.def.eSubType == 1 && null != var1) {
               var1[1] |= 2097152;
            }
            break;
         case 6:
         case 10:
         case 11:
            if ((this.info & 1048576) != 0) {
               var2 = true;
            } else {
               var2 = false;
            }
            break;
         case 7:
         case 8:
         case 9:
         case 12:
         default:
            return false;
         case 13:
            var3 = this.getSprite();
            int var4 = Render.mapSpriteInfo[var3] & 255;
            if (var4 == 194 || var4 == 168) {
               if ((Render.mapSpriteInfo[var3] & 65536) != 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            }
         }

         if (null != var1) {
            var1[0] = var2 ? 1 : 0;
         }

         return true;
      }
   }

   private boolean isNamedEntity(int[] var1) {
      if (this.def != null && this.name != Text.STRINGID((short)2, this.def.name) && this.def.eType != 9) {
         if (this.def.eType == 2 && this.def.eSubType == 8) {
            return false;
         } else {
            var1[0] = this.name;
            if (var1[0] != -1) {
               return true;
            } else {
               App.Error(25);
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public void saveState(DataOutputStream var1, int var2) throws IOException {
      short[] var3 = Render.mapSprites;
      int[] var4 = Render.mapSpriteInfo;
      if ((var2 & 131072) != 0) {
         int[] var5 = tempSaveBuf;
         this.isNamedEntity(var5);
         var1.writeShort((short)var5[0]);
      }

      if ((var2 & 524288) == 0) {
         if (this.def.eType != 10 || this.def.eSubType != 1) {
            int var7 = this.getSprite();
            if (this.def.eType == 2 && (var2 & 2097152) != 0) {
               var1.writeByte(var3[Render.S_X + var7] >> 3);
               var1.writeByte(var3[Render.S_Y + var7] >> 3);
               var1.writeByte((var4[var7] & '\uff00') >> 8);
               var1.writeShort(this.monster.flags);
            } else {
               var1.writeByte(this.info >> 16 & 255);
               if ((this.info & 65536) == 0) {
                  var1.writeByte((var4[var7] & 16711680) >> 16);
                  var1.writeByte((var4[var7] & '\uff00') >> 8);
                  if (this.isDroppedEntity() || (Render.mapSpriteInfo[var7] & 251658240) == 0) {
                     var1.writeByte(var3[Render.S_X + var7] >> 3);
                     var1.writeByte(var3[Render.S_Y + var7] >> 3);
                  }

                  if (this.isDroppedEntity()) {
                     var1.writeInt(this.param);
                  }

                  if (!this.isDroppedEntity() && (Render.mapSpriteInfo[var7] & 251658240) != 0) {
                     var1.writeShort(this.linkIndex);
                  }
               }

               if (this.monster != null) {
                  var1.writeShort(this.monster.flags);
                  if ((this.info & 65536) == 0) {
                     if ((this.monster.flags & 512) != 0) {
                        var1.writeByte(var3[Render.S_SCALEFACTOR + var7]);
                     }

                     if ((var2 & 1048576) == 0) {
                        var1.writeShort(this.monster.monsterEffects);
                        this.monster.ce.saveState(var1, false);
                        this.monster.saveGoalState(var1);
                     }
                  }
               } else if (this.isDroppedEntity()) {
                  byte var6 = (byte)(this.def.eType | this.def.eSubType << 4);
                  var1.writeByte(var6);
                  var1.writeByte(this.def.parm);
                  if (this.def.eType == 14 && this.def.eSubType == 6) {
                     var1.writeByte((var4[var7] & -16777216) >> 24);
                  }
               } else {
                  var1.writeShort(var3[Render.S_Z + var7]);
               }

            }
         }
      }
   }

   public void loadState(DataInputStream var1, int var2) throws IOException {
      if ((var2 & 131072) != 0) {
         this.name = var1.readShort();
      }

      int var3 = this.getSprite();
      int var4 = Render.mapSpriteInfo[var3] & 255;
      if ((Render.mapSpriteInfo[var3] & 4194304) != 0) {
         var4 += 257;
      }

      int[] var10000;
      if ((var2 & 262144) != 0) {
         var10000 = Render.mapSpriteInfo;
         var10000[var3] &= -65537;
         if ((var2 & 524288) != 0 && (this.info & 1048576) == 0) {
            Game.linkEntity(this, Render.mapSprites[Render.S_X + var3] >> 6, Render.mapSprites[Render.S_Y + var3] >> 6);
         }
      } else {
         var10000 = Render.mapSpriteInfo;
         var10000[var3] |= 65536;
         if ((this.info & 1048576) != 0 && this.def.eType != 5 && (var4 < 60 || var4 > 63)) {
            Game.unlinkEntity(this);
         } else if (this.def.eType == 10 && this.def.eSubType != 8 && this.def.eSubType != 10) {
            ++Game.destroyedObj;
         }
      }

      if ((var2 & 16777216) != 0) {
         this.info |= 33554432;
      }

      if (this.isBinaryEntity((int[])null)) {
         this.restoreBinaryState(var2);
         if ((var2 & 524288) != 0) {
            return;
         }
      }

      if (this.def == null || this.def.eType != 10 || this.def.eSubType != 1) {
         if ((var2 & 1048576) != 0) {
            if ((var2 & 262144) == 0) {
               this.info |= 65536;
               if ((this.info & 1048576) != 0) {
                  Game.unlinkEntity(this);
               }
            } else if ((var2 & 524288) != 0) {
               App.Error(5);
            }

            Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -65281 | 28672;
            this.def = EntityDef.find(9, this.def.eSubType, this.def.parm);
         }

         if (this.def != null && this.def.eType == 3) {
            this.param = (var2 & 2097152) != 0 ? 1 : 0;
            this.param += (var2 & 67108864) != 0 ? 1 : 0;
         }

         if (this.monster != null) {
            EntityMonster var19;
            if ((var2 & 8388608) != 0) {
               var19 = this.monster;
               var19.flags = (short)(var19.flags | 16);
               this.info |= 4194304;
            }

            if ((var2 & 4194304) != 0) {
               var19 = this.monster;
               var19.flags = (short)(var19.flags | 128);
               this.info |= 4194304;
            }
         }

         if ((var2 & 524288) == 0) {
            if ((this.info & 1048576) != 0) {
               Game.unlinkEntity(this);
            }

            var3 = this.getSprite();
            int var7;
            if (this.def != null && this.def.eType == 2 && (var2 & 2097152) != 0) {
               short var14 = (short)((var1.readByte() & 255) << 3);
               short var16 = (short)((var1.readByte() & 255) << 3);
               var7 = (var1.readByte() & 255) << 8;
               Render.mapSprites[Render.S_X + var3] = var14;
               Render.mapSprites[Render.S_Y + var3] = var16;
               Render.mapSprites[Render.S_Z + var3] = (short)(Render.getHeight(var14, var16) + 32);
               Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -65281 | var7;
               Render.relinkSprite(var3);
               if ((this.info & 1048576) != 0) {
                  Game.unlinkEntity(this);
               }

               if ((var2 & 262144) == 0) {
                  Game.deactivate(this);
               } else {
                  Game.linkEntity(this, var14 >> 6, var16 >> 6);
               }

               this.monster.flags = var1.readShort();
               if (this.monster.flags != 0 || var7 != 0) {
                  this.info |= 4194304;
                  if ((this.monster.flags & 2048) != 0) {
                     if (var7 >> 8 == 96) {
                        this.monster.frameTime = Integer.MAX_VALUE;
                     }

                     this.info &= -131073;
                  }
               }

            } else {
               this.info = this.info & -16711681 | (var1.readByte() & 255) << 16;
               if ((this.info & 65536) == 0) {
                  int var5 = var1.readByte() & 255;
                  int var6 = var1.readByte() & 255;
                  Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -16776961 | var6 << 8 | var5 << 16;
                  if (this.isDroppedEntity() || (Render.mapSpriteInfo[var3] & 251658240) == 0) {
                     Render.mapSprites[Render.S_X + var3] = (short)(var1.readUnsignedByte() << 3);
                     Render.mapSprites[Render.S_Y + var3] = (short)(var1.readUnsignedByte() << 3);
                     if (this.monster != null || this.isDroppedEntity()) {
                        Render.mapSprites[Render.S_Z + var3] = (short)(Render.getHeight(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3]) + 32);
                     }

                     Render.relinkSprite(var3);
                  }

                  if (this.isDroppedEntity()) {
                     this.param = var1.readInt();
                  }

                  if (!this.isDroppedEntity() && (Render.mapSpriteInfo[var3] & 251658240) != 0) {
                     this.linkIndex = var1.readShort();
                  } else {
                     this.linkIndex = (short)((Render.mapSprites[Render.S_X + var3] >> 6) + (Render.mapSprites[Render.S_Y + var3] >> 6) * 32);
                  }

                  if (((Render.mapSpriteInfo[var3] & 251658240) == 201326592 || (Render.mapSpriteInfo[var3] & 251658240) == 50331648) && (this.def.eType == 13 || this.def.eType == 12)) {
                     var7 = this.linkIndex % 32;
                     int var8 = this.linkIndex / 32;
                     Render.mapSprites[Render.S_X + var3] = (short)((var7 << 6) + 32);
                     Render.mapSprites[Render.S_Y + var3] = (short)((var8 << 6) + 32);
                     Render.relinkSprite(var3);
                  }

                  if (var6 != 0) {
                     this.info |= 4194304;
                  }
               }

               if ((var2 & 33554432) != 0) {
                  this.info |= 524288;
               }

               if (this.monster != null) {
                  this.monster.flags = var1.readShort();
                  if ((this.info & 65536) == 0) {
                     if ((this.monster.flags & 512) != 0) {
                        Render.mapSprites[Render.S_SCALEFACTOR + var3] = (short)var1.readUnsignedByte();
                     }

                     if ((var2 & 1048576) != 0) {
                        this.info |= 16777216;
                     } else {
                        this.monster.monsterEffects = var1.readShort();
                        this.monster.ce.loadState(var1, false);
                        this.monster.loadGoalState(var1);
                     }
                  }

                  if ((this.info & 262144) != 0) {
                     this.info &= -262145;
                     Game.activate(this, false, false, false, true);
                  }

                  if ((this.info & 16777216) != 0) {
                     this.def = EntityDef.find(9, this.def.eSubType, this.def.parm);
                  }

                  boolean var12 = (this.monster.flags & 2048) != 0;
                  if (var12) {
                     if ((Render.mapSpriteInfo[var3] & '\uff00') >> 8 == 96) {
                        this.monster.frameTime = Integer.MAX_VALUE;
                     }

                     this.info &= -131073;
                     this.info |= 4194304;
                  }
               } else if (this.isDroppedEntity()) {
                  byte var13 = var1.readByte();
                  byte var15 = (byte)(var13 & 15);
                  byte var17 = (byte)(var13 >> 4 & 15);
                  byte var18 = var1.readByte();
                  this.def = EntityDef.find(var15, var17, var18);
                  this.name = (short)(this.def.name | 2048);
                  Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -256 | this.def.tileIndex;
                  Render.mapSprites[Render.S_ENT + var3] = this.getIndex();
                  if (this.def.eType == 14 && this.def.eSubType == 6) {
                     int var9 = (var1.readByte() & 255) << 24;
                     var10000 = Render.mapSpriteInfo;
                     var10000[var3] |= var9;
                     boolean var10 = (Render.mapSpriteInfo[var3] & 251658240) != 0;
                     int var11 = var10 ? 32 : 31;
                     Render.mapSprites[Render.S_Z + var3] = (short)(Render.getHeight(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3]) + var11);
                     Render.mapSprites[Render.S_SCALEFACTOR + var3] = 32;
                     Render.relinkSprite(var3);
                  }
               } else {
                  Render.mapSprites[Render.S_Z + var3] = var1.readShort();
                  Render.relinkSprite(var3);
               }

               if ((this.info & 1048576) != 0) {
                  if ((Render.mapSpriteInfo[var3] & 251658240) != 0) {
                     Game.linkEntity(this, this.linkIndex % 32, this.linkIndex / 32);
                  } else {
                     Game.linkEntity(this, Render.mapSprites[Render.S_X + var3] >> 6, Render.mapSprites[Render.S_Y + var3] >> 6);
                  }
               }

            }
         }
      }
   }

   public int getSaveHandle(boolean var1) {
      int[] var2 = tempSaveBuf;
      var2[0] = 1;
      var2[1] = this.getIndex();
      boolean var3 = this.isDroppedEntity();
      boolean var4 = this.isBinaryEntity(var2);
      if (((this.info & '\uffff') == 0 || this.def == null) && !var4) {
         return -1;
      } else if (var3 && (this.info & 1048576) == 0) {
         return -1;
      } else if (var3 && var1 && this.def.eType == 14 && this.def.eSubType == 6) {
         return -1;
      } else {
         boolean var5 = var2[0] != 0;
         int var6 = var2[1];
         if (var4 && var5) {
            var6 |= 65536;
         }

         int[] var7 = tempSaveBuf;
         if (this.isNamedEntity(var7)) {
            var6 |= 131072;
         }

         int var8 = this.getSprite();
         if ((Render.mapSpriteInfo[var8] & 65536) == 0) {
            var6 |= 262144;
         }

         if ((this.info & 33554432) != 0) {
            var6 |= 16777216;
         }

         if ((this.info & 4194304) == 0) {
            var6 |= 524288;
            if ((var6 & 131072) == 0 && this.def.eType == 7 && this.def.eSubType != 3) {
               return -1;
            }
         }

         if ((this.info & 16842752) != 0) {
            var6 |= 1048576;
         }

         if ((this.info & 524288) != 0) {
            var6 |= 33554432;
         }

         if ((this.info & 65536) != 0) {
            var6 &= -262145;
            var6 |= 1048576;
         }

         if (this.def.eType == 3 && this.param != 0) {
            var6 |= 2097152;
            if (this.param == 2) {
               var6 |= 67108864;
            }
         }

         if (this.monster != null) {
            if ((this.monster.flags & 16) != 0) {
               var6 |= 8388608;
            }

            if ((this.monster.flags & 128) != 0) {
               var6 |= 4194304;
            }
         }

         if (var1) {
            if (this.def.eType == 9 && this.def.eSubType != 15 && 0 == (this.monster.flags & 128)) {
               var6 = var6 & -262145 | 524288;
            } else if (this.def.eType == 2) {
               var6 |= 2097152;
            }
         }

         return var6;
      }
   }

   private void restoreBinaryState(int var1) {
      boolean var2 = (var1 & 65536) != 0;
      switch(this.def.eType) {
      case 5:
         boolean var3 = this.def.eSubType == 1;
         Game.setLineLocked(this, false);
         if (var2) {
            Game.performDoorEvent(0, this, 0);
         } else {
            Game.performDoorEvent(1, this, 0);
         }

         if ((var1 & 2097152) != 0 || var3) {
            Game.setLineLocked(this, (var1 & 2097152) != 0);
         }
         break;
      case 6:
      case 10:
      case 11:
         if (var2) {
            Game.unlinkEntity(this);
            Game.linkEntity(this, this.linkIndex % 32, this.linkIndex / 32);
         } else {
            Game.unlinkEntity(this);
         }

         if (this.def.eType == 10 && this.def.eSubType == 1) {
            if (var2) {
               Game.unlinkEntity(this);
               Game.linkEntity(this, this.linkIndex % 32, this.linkIndex / 32);
               Render.relinkSprite((this.info & '\uffff') - 1);
            } else {
               Render.unlinkSprite((this.info & '\uffff') - 1);
            }
         }
         break;
      case 7:
      case 8:
      case 9:
      case 12:
      default:
         return;
      case 13:
         if (var2) {
            Game.performDoorEvent(0, this, 0);
         } else {
            Game.performDoorEvent(1, this, 0);
         }
      }

   }

   public short getIndex() {
      for(short var1 = 0; var1 < Game.numEntities; ++var1) {
         if (this == Game.entities[var1]) {
            return var1;
         }
      }

      return -1;
   }

   void updateMonsterFX() {
      if (null != this.monster) {
         if (this.def.eSubType == 13 && this.def.eType == 2 && this.monster.ce.getStat(0) < this.monster.ce.getStat(1)) {
            this.monster.ce.addStat(0, 20);
            Text var1 = Text.getSmallBuffer();
            Text.composeText((short)2, this.def.name, var1);
            Text.resetTextArgs();
            Text.addTextArg(var1);
            Text.addTextArg(5);
            Hud.addMessage((short)96);
            var1.dispose();
         }

         for(int var8 = 0; var8 < 5; ++var8) {
            int var2 = 1 << var8;
            short var3 = this.monster.monsterEffects;
            if ((var3 & var2) != 0) {
               int var4 = 5 + (var8 << 1);
               int var5 = var3 >> var4 & 3;
               if (this.def.eType != 9 && (this.info & 131072) != 0) {
                  byte var6 = this.def.eSubType;
                  byte var7 = 0;
                  if (var2 != 8) {
                     if (var7 > 0) {
                        Text.resetTextArgs();
                        Text.addTextArg(var7);
                        Hud.addMessage((short)1, (short)73);
                     }
                  } else {
                     if (var6 != 12 && (var6 != 4 || this.def.parm != 0)) {
                        if (var6 == 9) {
                           var7 = 6;
                        } else {
                           var7 = 4;
                        }
                     } else {
                        var7 = 8;
                     }

                     Text.resetTextArgs();
                     Text.addTextArg(var7);
                     Hud.addMessage((short)1, (short)97);
                  }

                  if (var7 > 0) {
                     this.pain(var7, (Entity)null);
                     var3 = this.monster.monsterEffects;
                     if (this.monster.ce.getStat(0) <= 0) {
                        this.died(true, (Entity)null);
                        var3 = this.monster.monsterEffects;
                        var3 &= -32737;
                        var3 = (short)(var3 | 10400);
                        var5 = 1;
                     }
                  }
               }

               if (var5 == 0) {
                  var3 = (short)(var3 & ~var2);
               } else {
                  --var5;
                  var3 = (short)(var3 & ~(3 << var4));
                  var3 = (short)(var3 | var5 << var4);
               }

               this.monster.monsterEffects = var3;
            }
         }
      }

   }
}
