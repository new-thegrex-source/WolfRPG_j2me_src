/* Decompiler 1466ms, total 1787ms, lines 2194 */
final class Combat {
   public static int touchMe = 1;
   private static final int SEQ_INVALID = -1;
   private static final int SEQ_STARTATTACK = 0;
   public static final int SEQ_ENDATTACK = 1;
   private static final int FIELD_COUNT = 6;
   private static final int FLD_WPIDLEX = 0;
   private static final int FLD_WPIDLEY = 1;
   private static final int FLD_WPATKX = 2;
   private static final int FLD_WPATKY = 3;
   private static final int FLD_WPFLASHX = 4;
   private static final int FLD_WPFLASHY = 5;
   public static final int OUTOFCOMBAT_TURNS = 4;
   private static final int MAX_TILEDISTANCES = 16;
   public static final int[] tileDistances = new int[16];
   private static short[] monsterAttacks = null;
   private static byte[] wpinfo = null;
   private static byte[] monsterStats = null;
   public static Entity curAttacker;
   public static Entity curTarget;
   public static Entity lastTarget;
   public static int dodgeDir;
   private static EntityMonster targetMonster;
   private static EntityMonster attackerMonster;
   private static int targetSubType;
   private static int targetType;
   public static int stage;
   public static int nextStageTime;
   public static int nextStage;
   static final int EXPLOSION_OFFSET2 = 38;
   private static final int MAX_ACTIVE_MISSILES = 8;
   private static GameSprite[] activeMissiles = new GameSprite[8];
   public static int numActiveMissiles;
   public static int missileAnim;
   public static final int EXPLOSION_OFFSET = 48;
   public static boolean targetKilled;
   private static boolean exploded;
   public static ScriptThread explodeThread = null;
   public static int damage;
   public static int totalDamage;
   public static int deathAmt;
   public static int accumRoundDamage;
   private static int totalArmorDamage;
   private static int hitType;
   public static int animStartTime;
   public static int animTime;
   public static int animEndTime;
   public static int attackerWeaponId;
   public static int attackerWeaponProj;
   public static int attackerWeapon;
   public static int weaponDistance;
   public static boolean lerpingWeapon = false;
   public static boolean weaponDown = false;
   public static boolean lerpWpDown;
   private static int lerpWpStartTime;
   private static int lerpWpDur;
   public static boolean flashDone;
   public static int flashDoneTime;
   public static int flashTime;
   public static boolean settingDynamite;
   public static boolean dynamitePlaced;
   public static int settingDynamiteTime;
   public static int currentBombIndex;
   public static final int DEF_PLACING_BOMB_Z = 24;
   public static int placingBombZ;
   public static final int PLACING_BOMB_TIME = 750;
   public static final int BOMB_RECOVER_TIME = 500;
   private static int attackFrame;
   public static int animLoopCount;
   private static boolean gotCrit;
   private static boolean gotHit;
   public static boolean isGibbed;
   public static int reflectionDmg = 0;
   public static int playerMissRepetition;
   public static int monsterMissRepetition;
   public static int renderTime;
   private static int attackX;
   private static int attackY;
   public static final int WEAPON_FIELD_STRMIN = 0;
   public static final int WEAPON_FIELD_STRMAX = 1;
   public static final int WEAPON_FIELD_RANGEMIN = 2;
   public static final int WEAPON_FIELD_RANGEMAX = 3;
   public static final int WEAPON_FIELD_AMMOTYPE = 4;
   public static final int WEAPON_FIELD_AMMOUSAGE = 5;
   public static final int WEAPON_FIELD_PROJTYPE = 6;
   public static final int WEAPON_FIELD_NUMSHOTS = 7;
   public static final int WEAPON_FIELD_SHOTHOLD = 8;
   public static final int WEAPON_MAX_FIELDS = 9;
   public static byte[] weapons = null;
   public static byte[] monsterWeakness = null;
   public static final int WEAPON_STRIDE = 9;
   public static final int MONSTER_FIELD_ATTACK1 = 0;
   public static final int MONSTER_FIELD_ATTACK2 = 1;
   public static final int MONSTER_FIELD_CHANCE = 2;
   public static final int MAX_WRAITH_DRAIN = 45;
   static final int MAP_03_BOOST = 9;
   public static CombatEntity[] monsters = new CombatEntity[45];
   public static int worldDist;
   public static int tileDist;
   public static int crFlags;
   public static int crDamage;
   public static int crArmorDamage;
   public static int crCritChance;
   public static int crHitChance;
   public static final int PUNCH_PREP = 1;
   public static final int PUNCH_RIGHTHAND = 2;
   public static final int PUNCH_LEFTHAND = 3;
   public static int punchingMonster = 0;
   public static boolean punchMissed = false;
   public static boolean oneShotCheat = false;
   private static final int ONE_FP = 65536;
   private static final int LOWEREDWEAPON_Y = 38;
   private static final int LOWERWEAPON_TIME = 200;
   static final int COMBAT_DONE = 0;
   static final int COMBAT_CONTINUE = 1;
   private static int[] tableCombatMasks = null;
   public static final int REBOUNDOFFSET = 31;

   public static final short getWeaponWeakness(int var0, int var1, int var2) {
      byte[] var3 = monsterWeakness;
      int var4 = var3[(var1 * 3 + var2) * 9 + var0 / 2] >> ((var0 & 1) << 2) & 15;
      return (short)(var4 + 1 << 5);
   }

   public static final boolean startup() {
      tableCombatMasks = App.TBL_COMBAT_COMBATMASKS;
      monsterAttacks = App.TBL_COMBAT_MONSTERATTACKS;
      wpinfo = App.TBL_COMBAT_WEAPONINFO;
      monsterStats = App.TBL_COMBAT_MONSTERSTATS;
      weapons = App.TBL_COMBAT_WEAPONDATA;
      monsterWeakness = App.TBL_MONSTER_WEAKNESS;
      int var0 = 0;

      int var1;
      for(var1 = 0; var1 < 45; var0 += 6) {
         monsters[var1] = new CombatEntity(5 * (monsterStats[var0 + 0] & 255), monsterStats[var0 + 1], monsterStats[var0 + 2], monsterStats[var0 + 3], monsterStats[var0 + 4], monsterStats[var0 + 5]);
         ++var1;
      }

      for(var1 = 0; var1 < 16; ++var1) {
         tileDistances[var1] = 64 * (var1 + 1) * 64 * (var1 + 1);
      }

      gotCrit = false;
      gotHit = false;
      settingDynamite = false;
      String var2 = App.app.getAppProperty("App-OneShotKill");
      if (var2 != null && var2.length() != 0) {
         oneShotCheat = Integer.parseInt(var2) != 0;
      } else {
         oneShotCheat = false;
      }

      return true;
   }

   public static final void performAttack(Entity var0, Entity var1, int var2, int var3, boolean var4) {
      attackX = var2;
      attackY = var3;
      curAttacker = var0;
      curTarget = var1;
      accumRoundDamage = 0;
      if (var0 == null || var1 == null) {
         Player.updateStats();
      }

      if (var0 != null) {
         var0.info |= 2097152;
      }

      if (var1 != null) {
         var1.info |= 2097152;
      }

      if (var1 == null || var0 == null && var1.def.eType == 2) {
         Player.lastCombatTurn = Player.totalMoves;
         Player.inCombat = true;
      }

      if (var1 != null) {
         targetType = var1.def.eType;
         targetSubType = var1.def.eSubType;
         targetMonster = var1.monster;
      } else {
         targetSubType = 0;
         targetType = 0;
         targetMonster = null;
      }

      int var6;
      EntityMonster var10000;
      if (curAttacker == null) {
         attackerWeaponId = Player.ce.weapon;
         if ((1L << attackerWeaponId & 7L) != 0L) {
            if (curTarget != null && curTarget.monster != null) {
               crFlags = 0;
               int var5 = CombatEntity.calcHit(Player.ce, curTarget.monster.ce, false, curTarget.distFrom(Canvas.viewX, Canvas.viewY), var4);
               if ((var5 & 4103) == 0 && curTarget.hasHead()) {
                  punchMissed = true;
               } else {
                  if (curTarget.def.eSubType != 10 && curTarget.def.eSubType != 14 && curTarget.def.eSubType != 9) {
                     var6 = curTarget.getSprite();
                     Render.mapSpriteInfo[var6] = Render.mapSpriteInfo[var6] & -65281 | '耀';
                     Render.chatZoom = true;
                  }

                  punchMissed = false;
               }
            }

            punchingMonster = 1;
         }

         attackerMonster = null;
      } else {
         attackerMonster = curAttacker.monster;
         attackerWeaponId = attackerMonster.ce.weapon;
         if (attackerWeaponId != 28 && attackerWeaponId != 43 && attackerWeaponId != 44) {
            if (attackerWeaponId != getMonsterField(curAttacker.def, 1) && attackerWeaponId != 42 && attackerWeaponId != 41) {
               attackFrame = 64;
            } else {
               attackFrame = 80;
               if (curAttacker.def.eSubType == 2 && curAttacker.param == 0) {
                  var10000 = curAttacker.monster;
                  var10000.flags = (short)(var10000.flags | 256);
               }
            }
         } else {
            attackFrame = 144;
         }
      }

      attackerWeapon = attackerWeaponId * 9;
      if (punchingMonster > 0 && !punchMissed) {
         stage = -1;
         nextStage = 0;
         nextStageTime = App.gameTime + 300;
      } else {
         stage = 0;
         nextStageTime = 0;
      }

      animEndTime = 0;
      animLoopCount = weapons[attackerWeapon + 7];
      attackerWeaponProj = weapons[attackerWeapon + 6];
      if (curAttacker != null) {
         worldDist = curAttacker.distFrom(Canvas.viewX, Canvas.viewY);
         tileDist = WorldDistToTileDist(worldDist);
      } else {
         worldDist = curTarget.distFrom(Canvas.viewX, Canvas.viewY);
         tileDist = WorldDistToTileDist(worldDist);
      }

      short[] var13 = Render.mapSprites;
      int var10;
      int var11;
      if (attackerWeaponId == 36) {
         var6 = curAttacker.getSprite();
         short var7 = var13[Render.S_X + var6];
         short var8 = var13[Render.S_Y + var6];
         LerpSprite var9 = Game.allocLerpSprite((ScriptThread)null, var6, true);
         var9.startTime = App.gameTime;
         var9.travelTime = WorldDistToTileDist(curAttacker.distFrom(Canvas.viewX, Canvas.viewY)) * 500;
         var9.flags |= 28;
         var9.srcScale = var9.dstScale = var13[Render.S_SCALEFACTOR + var6];
         var9.srcX = var13[Render.S_X + var6];
         var9.srcY = var13[Render.S_Y + var6];
         var9.srcZ = var13[Render.S_Z + var6];
         var9.dstX = Canvas.viewX;
         var9.dstY = Canvas.viewY;
         var9.dstZ = Render.getHeight(var9.dstX, var9.dstY) + 32;
         var9.height = 64;
         Render.mapSpriteInfo[var6] = Render.mapSpriteInfo[var6] & -65281 | 20480;
         curAttacker.monster.frameTime = App.time + 130;
         var10 = var7 - Canvas.viewX;
         var11 = var8 - Canvas.viewY;
         if (var10 != 0) {
            var10 /= Math.abs(var10);
         }

         if (var11 != 0) {
            var11 /= Math.abs(var11);
         }

         var10 = Canvas.viewX + var10 * 64;
         var11 = Canvas.viewY + var11 * 64;
         Game.unlinkEntity(curAttacker);
         Game.linkEntity(curAttacker, var10 >> 6, var11 >> 6);
         var10000 = curAttacker.monster;
         var10000.goalFlags &= -3;
         stage = -1;
         nextStage = 0;
         nextStageTime = App.gameTime + var9.travelTime;
      } else if (attackerWeaponId == 28 || attackerWeaponId == 43 || attackerWeaponId == 39 || attackerWeaponId == 44) {
         var6 = curAttacker.getSprite();
         int var14 = var13[Render.S_X + var6] - Canvas.viewX;
         int var15 = var13[Render.S_Y + var6] - Canvas.viewY;
         int var16 = var14;
         var10 = var15;
         if (var14 != 0) {
            var16 = var14 / Math.abs(var14);
         }

         if (var15 != 0) {
            var10 = var15 / Math.abs(var15);
         }

         var11 = Math.abs(var14 + var15) >> 6;
         var14 = Canvas.viewX + var16 * 64;
         var15 = Canvas.viewY + var10 * 64;
         LerpSprite var12 = Game.allocLerpSprite((ScriptThread)null, var6, true);
         var12.startTime = App.gameTime;
         var12.travelTime = var11 * 200;
         var12.flags |= 16;
         var12.srcScale = var12.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var6];
         var12.srcX = var13[Render.S_X + var6];
         var12.srcY = var13[Render.S_Y + var6];
         var12.srcZ = var13[Render.S_Z + var6];
         var12.dstX = var14 - var16 * 28;
         var12.dstY = var15 - var10 * 28;
         var12.dstZ = Render.getHeight(var12.dstX, var12.dstY) + 32;
         if (attackerWeaponId == 44 || var0.def.eSubType == 10) {
            var12.flags |= 4;
            var12.height = tileDist * 6;
         }

         Render.mapSpriteInfo[var6] = Render.mapSpriteInfo[var6] & -65281 | attackFrame << 8;
         curAttacker.monster.frameTime = Integer.MAX_VALUE;
         Game.unlinkEntity(curAttacker);
         Game.linkEntity(curAttacker, var14 >> 6, var15 >> 6);
         stage = -1;
         nextStage = 0;
         nextStageTime = App.gameTime + var12.travelTime;
      }

      Canvas.setState(5);
   }

   private static final void checkMonsterFX() {
      EntityMonster var0 = curTarget.monster;
      short var1 = var0.monsterEffects;
      boolean var2 = targetSubType == 6 || targetSubType == 4 && curTarget.def.parm > 0;
      long var3 = 1L << attackerWeaponId;
      if (!var2 && (var3 & 8192L) != 0L) {
         var1 = (short)(var1 | 6152);
         var1 &= -387;
      }

      var0.monsterEffects = var1;
   }

   private static final int playerSeq() {
      short var0 = 0;
      boolean var1 = false;
      if (nextStageTime != 0 && App.gameTime > nextStageTime && numActiveMissiles == 0 && Game.animatingEffects == 0) {
         stage = nextStage;
         nextStageTime = 0;
         nextStage = -1;
      }

      int var2;
      int var3;
      Entity var10000;
      int[] var20;
      if (stage == 0) {
         isGibbed = false;
         totalDamage = 0;
         totalArmorDamage = 0;
         hitType = 0;
         deathAmt = 0;
         gotCrit = false;
         gotHit = false;
         crFlags = 0;
         damage = 0;
         targetKilled = false;
         flashTime = 0;
         int var10002;
         if (attackerWeaponId != 11) {
            var10002 = Player.counters[6]++;
         }

         if (punchingMonster == 1) {
            punchingMonster = 2;
         }

         if (attackerWeaponId == 3) {
            flashTime = 200;
         }

         if ((1L << attackerWeaponId & 2032L) == 0L) {
            crFlags |= 64;
         }

         if (weapons[attackerWeapon + 4] != 0) {
            var1 = true;
            var0 = Player.ammo[weapons[attackerWeapon + 4]];
         }

         if (var1 && targetType != 2 && attackerWeaponId != 16) {
            if (var0 == 2) {
               Hud.addMessage((short)63);
            } else if (var0 < 5 && var0 > 1) {
               Text.resetTextArgs();
               Text.addTextArg(var0 - 1);
               Hud.addMessage((short)64);
            }
         }

         if (targetType == 2) {
            if ((attackerWeaponId == 0 || attackerWeaponId == 1) && targetSubType == 4) {
               crFlags |= 8192;
            }

            if ((1L << attackerWeaponId & 32783L) == 0L) {
               crFlags |= 32;
            }

            CombatEntity.calcCombat(Player.ce, curTarget, false, worldDist, targetSubType);
            if ((crFlags & 4103) != 0) {
               var10000 = curTarget;
               var10000.info |= 67108864;
               if ((crFlags & 4096) != 0) {
                  var2 = Game.getNextBombIndex();
                  var3 = curTarget.getSprite();
                  Game.allocDynamite(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], Render.mapSprites[Render.S_Z + var3] + 25, 1048576, var2, curTarget.getIndex());
                  Sound.playSound(13);
               }

               if ((crFlags & 2) != 0) {
                  gotCrit = true;
                  hitType = 2;
               } else if ((crFlags & 1) != 0) {
                  hitType = 1;
               }

               gotHit = true;
               damage = crDamage;
               var2 = targetMonster.ce.getStat(0);
               deathAmt = var2 - damage;
               if (damage > 0 && gotHit) {
                  var20 = Player.counters;
                  var20[2] += damage;
                  if (damage > Player.counters[1]) {
                     Player.counters[1] = damage;
                  }
               }

               if (targetSubType == 14 && attackerWeaponId == 14) {
                  curTarget.monster.resetGoal();
                  curTarget.monster.goalType = 6;
                  curTarget.monster.goalTurns = 0;
                  curTarget.monster.goalParam = 1;
               }
            }
         } else if (attackerWeaponId != 11 || targetType != 10 || targetSubType != 2 && targetSubType != 8) {
            hitType = calcHit(curTarget);
            var2 = weapons[attackerWeapon + 0];
            byte var9 = weapons[attackerWeapon + 1];
            if (var2 != var9) {
               var2 += App.nextByte() % (var9 - var2);
            }

            if (Game.difficulty == 4) {
               var2 -= var2 >> 2;
            }

            damage = var2;
         } else {
            crFlags |= 4096;
            var2 = Game.getNextBombIndex();
            var3 = curTarget.getSprite();
            Game.allocDynamite(Render.mapSprites[Render.S_X + var3], Render.mapSprites[Render.S_Y + var3], Render.mapSprites[Render.S_Z + var3] + 25, 1048576, var2, curTarget.getIndex());
            Sound.playSound(13);
         }

         byte var11 = -1;
         if (weapons[attackerWeapon + 6] == 0) {
            var11 = 12;
         } else if (attackerWeaponId == 13) {
            var11 = 11;
         } else if (attackerWeaponId == 14) {
            var11 = 16;
         } else if (attackerWeaponId == 15) {
            var11 = 15;
         } else if (((long)(1 << attackerWeaponId) & 32783L) != 0L) {
            var11 = 13;
         }

         if (var11 != -1) {
            Sound.playSound(var11);
         }

         totalDamage += damage;
         totalArmorDamage += crArmorDamage;
         animTime = weapons[attackerWeapon + 8];
         if (Player.statusEffects[2] > 0) {
            animTime *= 5;
         } else {
            animTime *= 10;
         }

         animStartTime = App.gameTime;
         animEndTime = animStartTime + animTime;
         flashDone = false;
         flashDoneTime = animStartTime + flashTime;
         if (var1 && var0 < animLoopCount) {
            animLoopCount = var0;
         }

         launchProjectile();
         if ((1L << attackerWeaponId & 19471L) == 0L || attackerWeaponId == 10 && animLoopCount == 1) {
            byte var10 = 2;
            if (attackerWeaponId == 12 || attackerWeaponId == 8) {
               var10 = 6;
            }

            Render.rockView(animTime, Canvas.viewX - var10 * (Canvas.viewStepX >> 6), Canvas.viewY - var10 * (Canvas.viewStepY >> 6), Canvas.viewZ);
         }

         var3 = curTarget.getSprite();
         if (totalDamage == 0) {
            if (attackerWeaponId != 11 && targetType == 2) {
               boolean var4 = (crFlags & 128) != 0;
               if ((1L << Player.ce.weapon & 1536L) != 0L || curTarget.monster.goalType == 4) {
                  var4 = false;
               }

               if (var4 && (targetSubType == 8 || targetSubType == 7)) {
                  Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -65281 | '退';
                  boolean var5 = attackerWeaponId == 13 || attackerWeaponId == 12;
                  curTarget.monster.frameTime = App.time + (var5 ? 750 : 500);
               }

               if (attackerWeaponId == 14 && curTarget.monster.goalType == 6) {
                  Hud.addMessage((short)61, 1);
               } else if ((crFlags & 256) != 0) {
                  Hud.addMessage((short)60, 1);
               } else if ((crFlags & 1024) != 0) {
                  Hud.addMessage((short)65);
               } else if ((crFlags & 1) == 0) {
                  if (var4 && 0 == (curTarget.monster.monsterEffects & 2)) {
                     Text.resetTextArgs();
                     Text var14 = Text.getSmallBuffer();
                     Text.composeTextField(curTarget.name, var14);
                     Text.addTextArg(var14);
                     if (curTarget.def.eSubType == 8) {
                        Hud.addMessage((short)67);
                     } else {
                        Hud.addMessage((short)66);
                     }

                     var14.dispose();
                  } else {
                     Hud.addMessage((short)69);
                  }
               }
            }
         } else if (targetType == 2) {
            accumRoundDamage += totalDamage;
         } else if (targetType == 10 && (targetSubType == 2 || targetSubType == 8)) {
            targetKilled = false;
         } else if (targetType != 10 || targetSubType != 7 || (1L << attackerWeaponId & 7L) == 0L && curTarget.param >= 2) {
            if (hitType != 0) {
               targetKilled = true;
            }
         } else {
            targetKilled = false;
            if ((1L << attackerWeaponId & 7L) != 0L && !punchMissed) {
               var20 = Render.mapSpriteInfo;
               var20[var3] ^= 131072;
               Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -65281 | 256;
            } else if ((1L << Player.ce.weapon & 2032L) == 0L && Player.ce.weapon != 11) {
               targetKilled = true;
            }
         }

         if (attackerWeaponId == 11 && (crFlags & 4096) == 0) {
            int var12 = curTarget.distFrom(Canvas.viewX, Canvas.viewY);
            Entity var16 = curTarget;
            if (WorldDistToTileDist(var12) < 1 && (1 << targetType & 13349) != 0) {
               if (targetType == 0) {
                  currentBombIndex = Game.setDynamite(Game.traceCollisionX, Game.traceCollisionY, false);
               } else {
                  int[] var18 = curTarget.calcPosition();
                  currentBombIndex = Game.setDynamite(var18[0], var18[1], false);
               }

               Sound.playSound(13);
            } else {
               settingDynamite = true;
               dynamitePlaced = false;
               settingDynamiteTime = App.gameTime;
               int var6 = Canvas.destX + Canvas.viewStepX;
               int var7 = Canvas.destY + Canvas.viewStepY;
               currentBombIndex = Game.setDynamite(var6, var7, true);
               animEndTime = Integer.MAX_VALUE;
               placingBombZ = 24;
               int var8 = Render.getHeight(var6, var7) - Render.getHeight(Canvas.destX, Canvas.destY);
               if (var8 > 0) {
                  var8 = Math.min(var8, 12);
                  placingBombZ += var8;
               } else {
                  var8 = Math.max(-16, var8);
                  placingBombZ -= var8 / 4;
               }
            }
         }

         stage = -1;
         Hud.repaintFlags |= 4;
         byte var13 = weapons[attackerWeapon + 5];
         byte var17 = weapons[attackerWeapon + 4];
         short[] var21 = Player.ammo;
         var21[var17] = (short)(var21[var17] - var13);
         Player.ammo[var17] = (short)Math.max(Player.ammo[var17], 0);
         nextStage = 1;
         nextStageTime = animEndTime;
         updateProjectile();
         Canvas.invalidateRect();
         if (totalDamage == 0 || hitType == 0) {
            var10002 = Player.counters[7]++;
         }
      } else {
         if (stage == 1) {
            settingDynamite = false;
            dynamitePlaced = false;
            Canvas.invalidateRect();
            if (targetType == 2) {
               var10000 = curTarget;
               var10000.info &= -67108865;
            }

            if (targetType == 10) {
               var2 = curTarget.getSprite();
               if (targetSubType == 11) {
                  var20 = Render.mapSpriteInfo;
                  var20[var2] &= -589569;
               } else if (targetSubType == 7 && animLoopCount == 1) {
                  var20 = Render.mapSpriteInfo;
                  var20[var2] &= -65281;
               }
            }

            if (targetType == 9 && targetSubType == 15) {
               var2 = curTarget.getSprite();
               Game.executeTile(Render.mapSprites[Render.S_X + var2] >> 6, Render.mapSprites[Render.S_Y + var2] >> 6, 4084 | Canvas.flagForWeapon((long)attackerWeaponId), true);
            }

            short var19 = 4129;
            if (targetKilled || targetType == 2 && targetMonster.ce.getStat(0) <= 0) {
               curTarget.died(true, Player.getPlayerEnt());
               targetKilled = true;
            } else if (--animLoopCount > 0 && (1 << targetType & var19) == 0 && !punchMissed && (targetType != 10 || targetSubType == 7 || targetSubType == 11)) {
               stage = 0;
               animTime = 0;
               animEndTime = 0;
               nextStageTime = 0;
               return 1;
            }

            if (punchingMonster > 0 && !targetKilled && !punchMissed) {
               var3 = curTarget.getSprite();
               if (var3 != -1 && curTarget.monster != null) {
                  Render.mapSpriteInfo[var3] = Render.mapSpriteInfo[var3] & -65281 | 0;
               }
            }

            Render.chatZoom = false;
            punchingMonster = 0;
            punchMissed = false;
            if (targetType == 2 && accumRoundDamage != 0 && (curTarget.info & 131072) != 0) {
               Text var15 = Hud.getMessageBuffer();
               Text.resetTextArgs();
               if (gotCrit) {
                  Text.composeText((short)1, (short)72, var15);
               }

               Text.addTextArg(accumRoundDamage);
               Text.composeText((short)1, (short)73, var15);
               Hud.finishMessageBuffer();
            }

            if (attackerWeaponId == 16) {
               Player.weapons &= -65537L;
               if ((Player.weapons & 1L) != 0L) {
                  Player.selectWeapon(0);
               } else if ((Player.weapons & 2L) != 0L) {
                  Player.selectWeapon(1);
               } else if ((Player.weapons & 4L) != 0L) {
                  Player.selectWeapon(2);
               }
            }

            if (attackerWeaponId == 11 && Player.ammo[7] == 0) {
               shiftWeapon(true);
            }

            return 0;
         }

         if (stage == -1) {
            Canvas.staleView = true;
            if (numActiveMissiles != 0) {
               updateProjectile();
            }
         }
      }

      return 1;
   }

   private static final int monsterSeq() {
      int var0 = curAttacker.getSprite();
      int var1 = Render.mapSpriteInfo[var0];
      int var2 = (var1 & '\uff00') >> 8;
      int var3 = var2 & 240;
      int var4 = var2 & 15;
      if ((var3 == 64 || var3 == 80) && var4 == 0) {
         if (App.time >= curAttacker.monster.frameTime) {
            curAttacker.monster.frameTime = App.time + weapons[attackerWeapon + 8] * 10;
            nextStageTime = App.gameTime + weapons[attackerWeapon + 8] * 10;
            var1 = Render.mapSpriteInfo[var0] = var1 & -65281 | (var3 | var4 + 1) << 8;
         }
      } else if (nextStageTime != 0 && App.gameTime > nextStageTime && numActiveMissiles == 0 && Game.animatingEffects == 0) {
         if (reflectionDmg > 0 && curAttacker != null && curAttacker.monster != null) {
            ParticleSystem.spawnMonsterBlood(curAttacker, false);
            curAttacker.pain(reflectionDmg / 2, Player.getPlayerEnt());
            reflectionDmg = 0;
            if (curAttacker.monster.ce.getStat(0) <= 0) {
               curAttacker.died(true, Player.getPlayerEnt());
            } else {
               curAttacker.monster.frameTime = App.time + 250;
               nextStageTime = App.gameTime + 250;
            }
         } else {
            stage = nextStage;
            nextStageTime = 0;
            nextStage = -1;
         }
      }

      if (stage == 0) {
         totalDamage = 0;
         totalArmorDamage = 0;
         hitType = 0;
         crFlags = 0;
         gotCrit = false;
         gotHit = false;
         targetKilled = false;
         reflectionDmg = 0;
         if (weapons[attackerWeapon + 6] == 0) {
            Sound.playSound(12);
         } else if (attackerWeaponId == 30) {
            Sound.playSound(11);
         } else if ((1L << attackerWeaponId & 62466429059087L) == 0L && attackerWeaponId != 42 && attackerWeaponId != 33) {
            if (attackerWeaponId == 34) {
               Sound.playSound(7);
            }
         } else {
            Sound.playSound(13);
         }

         if (weapons[attackerWeapon + 1] > 0) {
            if (curTarget == null) {
               CombatEntity.calcCombat(attackerMonster.ce, Player.getPlayerEnt(), true, Player.distFrom(curAttacker), -1);
               if ((crFlags & 4103) != 0) {
                  if ((crFlags & 2) != 0) {
                     gotCrit = true;
                     hitType = 2;
                  } else {
                     hitType = 1;
                  }

                  gotHit = true;
                  damage = crDamage;
               } else {
                  damage = 0;
               }
            } else {
               hitType = 1;
               damage = 1;
            }

            totalDamage += damage;
            totalArmorDamage += crArmorDamage;
         } else if (attackerWeaponId == 42 || attackerWeaponId == 41) {
            hitType = 1;
         }

         --animLoopCount;
         animTime = weapons[attackerWeapon + 8] * 10;
         animStartTime = App.gameTime;
         animEndTime = animStartTime + animTime;
         int[] var10000;
         if (attackerWeaponId != 28) {
            Render.mapSpriteInfo[var0] = Render.mapSpriteInfo[var0] & -65281 | attackFrame << 8;
            curAttacker.monster.frameTime = App.time + animTime;
            if ((1L << attackerWeaponId & 26458340720640L) != 0L && (1L << attackerWeaponId & 26388547502080L) == 0L) {
               var10000 = Render.mapSpriteInfo;
               var10000[var0] |= 256;
            }
         }

         if (curTarget != null) {
            if (targetType == 10 && targetSubType == 2) {
               targetKilled = false;
            } else {
               targetKilled = true;
            }
         }

         if (attackerWeaponId == 36) {
            Entity var8 = curAttacker;
            short[] var9 = Render.mapSprites;
            LerpSprite var7 = Game.allocLerpSprite((ScriptThread)null, var0, true);
            var7.startTime = App.gameTime;
            var7.travelTime = 500;
            var7.flags |= 20;
            var7.srcScale = var7.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var0];
            var7.srcX = var9[Render.S_X + var0];
            var7.srcY = var9[Render.S_Y + var0];
            var7.srcZ = var9[Render.S_Z + var0];
            var7.dstX = (var8.linkIndex % 32 << 6) + 32;
            var7.dstY = (var8.linkIndex / 32 << 6) + 32;
            var7.dstZ = Render.getHeight(var7.dstX, var7.dstY) + 32;
            var7.height = 16;
            Render.mapSpriteInfo[var0] = Render.mapSpriteInfo[var0] & -65281 | 16384;
            var8.monster.frameTime = App.time + var7.travelTime;
         } else if (attackerWeaponId == 28 || attackerWeaponId == 43 || attackerWeaponId == 39 || attackerWeaponId == 44) {
            short[] var5 = Render.mapSprites;
            LerpSprite var6 = Game.allocLerpSprite((ScriptThread)null, var0, true);
            var6.startTime = App.gameTime;
            var6.travelTime = 500;
            var6.flags |= 16;
            var6.srcScale = var6.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var0];
            var6.srcX = var5[Render.S_X + var0];
            var6.srcY = var5[Render.S_Y + var0];
            var6.srcZ = var5[Render.S_Z + var0];
            var6.dstX = (curAttacker.linkIndex % 32 << 6) + 32;
            var6.dstY = (curAttacker.linkIndex / 32 << 6) + 32;
            var6.dstZ = Render.getHeight(var6.dstX, var6.dstY) + 32;
            var6.calcDist();
            if (curAttacker.def.eSubType == 10) {
               var6.travelTime = 200;
               Render.mapSpriteInfo[var0] = Render.mapSpriteInfo[var0] & -65281 | 16384;
            } else {
               Render.mapSpriteInfo[var0] = Render.mapSpriteInfo[var0] & -65281 | 0;
            }

            curAttacker.monster.frameTime = App.time + var6.travelTime;
         }

         launchProjectile();
         if (gotHit && attackerWeaponId == 30) {
            Player.addStatusEffect(13, 5, 3);
            Player.translateStatusEffects();
         }

         stage = -1;
         if (animLoopCount <= 0) {
            nextStage = 1;
         } else {
            nextStage = 0;
         }

         nextStageTime = animEndTime;
         if (Render.isSkeleton(curAttacker.def.tileIndex) && attackerWeaponId == 45 && animLoopCount == 0) {
            var10000 = Render.mapSpriteInfo;
            var10000[var0] ^= 131072;
         }

         updateProjectile();
         if ((crFlags & 128) != 0) {
            Hud.addMessage((short)68);
         }

         Canvas.invalidateRect();
      } else {
         if (stage == 1) {
            if (curAttacker.def.eType == 2) {
               Render.mapSpriteInfo[var0] = Render.mapSpriteInfo[var0] & -65281 | 0;
            }

            if (targetKilled) {
               curTarget.died(false, (Entity)null);
            }

            Text.resetTextArgs();
            if (accumRoundDamage > 0 && Player.buffs[0] > 0 && !curAttacker.isBoss()) {
               Text.addTextArg(accumRoundDamage);
               Hud.addMessage((short)1, (short)147);
            } else if (accumRoundDamage > 0) {
               Text.addTextArg((short)2, (short)(curAttacker.def.name & 1023));
               Text.addTextArg(accumRoundDamage);
               Hud.addMessage((short)1, (short)118);
            } else if (attackerWeaponId == 42) {
               Text.addTextArg((short)2, (short)(curAttacker.def.name & 1023));
               Text.addTextArg((short)2, (short)(curTarget.def.name & 1023));
               Hud.addMessage((short)1, (short)98);
            } else if (attackerWeaponId == 41) {
               Text.addTextArg((short)2, (short)(curAttacker.def.name & 1023));
               Hud.addMessage((short)1, (short)99);
            }

            Canvas.shakeTime = 0;
            Hud.damageDir = 0;
            Hud.damageTime = 0;
            EntityMonster var10 = attackerMonster;
            var10.flags = (short)(var10.flags | 1024);
            Game.gsprite_clear(64);
            Canvas.invalidateRect();
            return 0;
         }

         if (stage == -1) {
            Canvas.staleView = true;
            if (numActiveMissiles != 0 || exploded) {
               updateProjectile();
            }
         }
      }

      return Canvas.state == 13 ? 0 : 1;
   }

   public static final void drawEffects() {
      if (Player.statusEffects[13] > 0) {
         int var0 = 131072;
         int var1 = 64 * var0 / 65536;
         Render.draw2DSprite(234, App.time / 256 & 3, TinyGL.screenWidth / 2 - var1 / 2, TinyGL.screenHeight - var1 / 4, 0, 3, 0, var0);
      }

   }

   public static final void drawWeapon(int var0, int var1) {
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      byte var7 = 0;
      int var8 = Render.screenWidth / 2;
      int var9 = Render.screenHeight;
      renderTime = App.getUpTimeMs();
      if (punchingMonster == 0 && !lerpingWeapon && weaponDown) {
         var9 += 38;
      }

      boolean var10 = Canvas.state == 18 && Game.cinematicWeapon != -1;
      int var11 = Player.ce.weapon;
      if (var10) {
         var11 = Game.cinematicWeapon;
         var9 -= Canvas.CAMERAVIEW_BAR_HEIGHT;
      }

      int var12 = var11 * 6;
      if (Canvas.state != 13 && Player.weapons != 0L && var11 != -1) {
         var1 = -Math.abs(var1);
         if (!var10 && App.time < Hud.damageTime && Hud.damageCount >= 0 && totalDamage > 0) {
            var6 |= 256;
         }

         byte var16 = wpinfo[var12 + 0];
         byte var17 = wpinfo[var12 + 1];
         byte var18 = wpinfo[var12 + 2];
         byte var19 = wpinfo[var12 + 3];
         int var20 = wpinfo[var12 + 4];
         byte var21 = wpinfo[var12 + 5];
         int var2 = var16;
         int var3 = var17;
         int var22 = App.gameTime;
         long var23 = 1L << var11;
         if ((1L << var11 & 7L) != 0L && (punchingMonster == 2 || punchingMonster == 3)) {
            var4 = 1;
         }

         boolean var26 = var10 || Canvas.state == 5 && curAttacker == null && nextStage == 1;
         int var25;
         int var27;
         int var28;
         if (var26 && var11 != 12) {
            var2 = var18;
            var3 = var19;
            if (var11 == 11) {
               if (settingDynamite) {
                  var25 = var22 - settingDynamiteTime << 16;
                  var27 = Render.getHeight(Canvas.viewX, Canvas.viewY);
                  if (!dynamitePlaced) {
                     var25 /= 750;
                     if (placingBombZ != 36) {
                        Canvas.destPitch = Canvas.viewPitch = Canvas.viewPitch * (65536 - var25) / 65536;
                        Canvas.destZ = Canvas.viewZ = var27 + ((65536 - var25) * 36 + var25 * placingBombZ >> 16);
                     } else {
                        var25 = 65536;
                     }

                     if (Canvas.viewZ <= var27 + placingBombZ) {
                        Canvas.destZ = Canvas.viewZ = var27 + placingBombZ;
                        if (var25 >= 65536) {
                           if (placingBombZ != 36) {
                              Canvas.destPitch = 0;
                              Canvas.viewPitch = 0;
                           }

                           dynamitePlaced = true;
                           Sound.playSound(13);
                           settingDynamiteTime = App.gameTime;
                           var28 = Game.entities[Game.placedBombs[currentBombIndex]].getSprite();
                           int[] var10000 = Render.mapSpriteInfo;
                           var10000[var28] &= -65537;
                        }
                     }
                  } else {
                     var4 = 1;
                     var5 = 1;
                     var25 /= 500;
                     Canvas.destZ = Canvas.viewZ = var27 + ((65536 - var25) * placingBombZ + var25 * 36 >> 16);
                     if (Canvas.viewZ >= var27 + 36) {
                        Canvas.destZ = Canvas.viewZ = var27 + 36;
                        if (var25 >= 65536) {
                           nextStageTime = 1;
                        }
                     }
                  }
               } else {
                  var4 = 1;
                  var5 = 1;
               }
            } else if (!flashDone) {
               var4 = (var23 & 1536L) == 0L ? 1 : 0;
               if (var22 >= flashDoneTime) {
                  flashDone = true;
               }
            } else {
               var25 = (var22 - animStartTime << 16) / animTime;
               if (var25 >= 65536) {
                  var2 = var16;
                  var3 = var17;
               } else {
                  var2 = (65536 - var25) * var18 + var25 * var16 >> 16;
                  var3 = (65536 - var25) * var19 + var25 * var17 >> 16;
               }
            }
         } else {
            if (var11 == 3) {
               lerpingWeapon = false;
               weaponDown = lerpWpDown;
               drawEffects();
               return;
            }

            if (punchingMonster == 0 && lerpingWeapon) {
               if (lerpWpDown && lerpWpStartTime + lerpWpDur > var22) {
                  var25 = (var22 - lerpWpStartTime << 16) / lerpWpDur;
                  var9 += var25 * 38 >> 16;
               } else if (!lerpWpDown && lerpWpStartTime + lerpWpDur > var22) {
                  var25 = (var22 - lerpWpStartTime << 16) / lerpWpDur;
                  var9 += (65536 - var25) * 38 >> 16;
               } else {
                  lerpWpStartTime = 0;
                  lerpingWeapon = false;
                  weaponDown = lerpWpDown;
                  if (lerpWpDown) {
                     var9 += 38;
                  }
               }
            }
         }

         byte var15 = 0;
         int var13 = var8 + var2 + var0;
         int var14 = var9 - (var3 + var1);
         int var29;
         if (var11 == 5) {
            var29 = var7 & -131073;
            if ((animLoopCount & 1) != 0) {
               var29 |= 131072;
               var13 = var8 - 64 - (var16 + var0);
               if (var4 == 1) {
                  var13 += var16 - var2;
               }
            }

            if (var4 == 1) {
               if ((animLoopCount & 1) != 0) {
                  var20 -= 16;
               }

               Render.draw2DSprite(5, 3, var13 + var20, var14 + var21, var29, 5, var6, 32768);
            }

            Render.draw2DSprite(1 + var11, 0, var13, var14, var29, var15, var6, 65536);
            var29 &= -131073;
            var13 = var8 + var16 + var0;
            var14 = var9 - (var17 + var1);
            if ((animLoopCount & 1) == 0) {
               var29 |= 131072;
               var13 = var8 - 64 - (var16 + var0);
            }

            Render.draw2DSprite(1 + var11, 0, var13, var14, var29, var15, var6, 65536);
         } else if ((1L << var11 & 7L) != 0L) {
            var29 = var7 & -131073;
            if ((animLoopCount & 1) != 0) {
               var29 |= 131072;
               var13 = var8 - 64 - (var16 + var0);
               if (var4 == 1) {
                  var13 += var16 - var2;
               }
            }

            Render.draw2DSprite(1 + var11, var4, var13, var14, var29, var15, var6, 65536);
            if (var4 == 0 && (var11 == 1 || var11 == 2)) {
               Render.draw2DSprite(1 + var11, 3, var13, var14, var29, var15, var6, 65536);
            }

            var29 &= -131073;
            var13 = var8 + var16 + var0;
            var14 = var9 - (var17 + var1);
            if ((animLoopCount & 1) == 0) {
               var29 |= 131072;
               var13 = var8 - 64 - (var16 + var0);
            }

            Render.draw2DSprite(1 + var11, 0, var13, var14, var29, var15, var6, 65536);
            if (var11 == 1 || var11 == 2) {
               Render.draw2DSprite(1 + var11, 3, var13, var14, var29, var15, var6, 65536);
            }
         } else if (var11 == 16) {
            if (Player.ammo[9] > 0) {
               Render.draw2DSprite(Player.activeWeaponDef.tileIndex, 1, var13 + var20, var14 + var21, var7, var15, var6, 65536);
            } else {
               Render.draw2DSprite(1 + var11, 0, var13, var14, var7, var15, var6, 65536);
            }
         } else if (var11 == 11) {
            if (var5 == 1) {
               Render.draw2DSprite(1 + var11, 3, var13 - 20, var14, var7, var15, var6, 65536);
               animStartTime = App.gameTime;
               animTime = 800;
            } else {
               var13 = var8 + var16 + var0;
               var14 = var9 - (var17 + var1);
               var27 = 128;
               boolean var30 = Player.ammo[7] > 0 || Canvas.state == 5 && curAttacker == null;
               if (var22 > animStartTime && animTime != 0 && nextStage != 1 && curAttacker == null && var30) {
                  var27 = (var22 - animStartTime << 7) / animTime;
               }

               if (var27 > 64) {
                  if (var27 > 128) {
                     var27 = 128;
                  }

                  var14 += 128 - var27;
                  if (var30) {
                     Render.draw2DSprite(1 + var11, 2, var13 + var20, var14 + var21, var7, var15, var6, 65536);
                  }
               } else {
                  var14 += var27;
               }

               Render.draw2DSprite(1 + var11, 0, var13, var14, var7, var15, var6, 65536);
            }
         } else if (var11 == 12) {
            var25 = var22 - animStartTime;
            if (var26 && var25 < 300) {
               var25 = (var25 << 16) / 300;
               var13 = var8 + ((65536 - var25) * var18 + var25 * var16 >> 16);
               var14 = var9 - ((65536 - var25) * var19 + var25 * var17 >> 16);
               ++var5;
            } else if (var26) {
               var28 = (animTime - 300) / 2;
               var25 -= 300;
               if (var25 <= var28) {
                  var25 = (var25 << 16) / var28;
                  var14 = var9 + (var25 * 38 >> 16) - var17;
                  ++var5;
               } else if (var25 < animTime - 300) {
                  var25 = (var25 - var28 << 16) / var28;
                  var14 = var9 + ((65536 - var25) * 38 >> 16) - var17;
               }
            }

            Render.draw2DSprite(1 + var11, var5, var13, var14, var7, var15, var6, 65536);
         } else {
            if (var4 == 1 && (464L & 1L << var11) != 0L) {
               Render.draw2DSprite(5, 3, var13 + var20, var14 + var21, var7, 5, 0, 32768);
            } else if (!Game.isUnderWater() && !var26 && var11 == 13) {
               var27 = App.time / 256 & 3;
               var28 = (var27 & 2) != 0 ? 5 : 3;
               var27 = (var27 & 1) << 17;
               Render.draw2DSprite(1 + var11, 3, var13 + var20, var14 + var21, var27, var28, 0, 65536);
            }

            Render.draw2DSprite(1 + var11, var5, var13, var14, var7, var15, var6, 65536);
            if (!Game.isUnderWater() && var11 == 14) {
               Render.draw2DSprite(1 + var11, 3 + (App.time / 256 & 1), var13 + var20, var14 + var21, var7, 3, 0, 65536);
            }
         }

         drawEffects();
         renderTime = App.getUpTimeMs() - renderTime;
      }
   }

   public static final void shiftWeapon(boolean var0) {
      if (var0 != lerpWpDown && var0 != weaponDown && (var0 || Player.ce.weapon != 11 || Player.ammo[7] != 0)) {
         lerpingWeapon = true;
         lerpWpDown = var0;
         lerpWpStartTime = App.gameTime;
         lerpWpDur = 200;
      }
   }

   public static final int runFrame() {
      return curAttacker == null ? playerSeq() : monsterSeq();
   }

   private static final int calcHit(Entity var0) {
      if (Player.ce.weapon == 11) {
         return 0;
      } else {
         int var1 = Player.ce.weapon * 9;
         int var2 = var0.distFrom(Canvas.destX, Canvas.destY);
         int var3 = WorldDistToTileDist(var2);
         byte var4 = weapons[var1 + 3];
         if (var3 >= weapons[var1 + 2] && var3 <= var4) {
            if ((var0.info & 131072) != 0) {
               if (var0.def.eType == 10) {
                  return ((long)tableCombatMasks[var0.def.parm] & 1L << Player.ce.weapon) != 0L ? 1 : 0;
               } else {
                  return 1;
               }
            } else {
               return 0;
            }
         } else {
            crFlags |= 1024;
            return 0;
         }
      }
   }

   private static final void explodeOnMonster() {
      if (explodeThread != null) {
         explodeThread.run();
         explodeThread = null;
      }

      Render.shotsFired = true;
      if ((1L << attackerWeaponId & 34831L) != 0L && hitType == 0) {
         Render.shotsFired = false;
      } else if (curTarget.monster != null && curTarget.def.eType == 2 && (curTarget.info & 262144) == 0 && (curTarget.monster.flags & 2048) == 0) {
         Game.activate(curTarget, true, false, true, true);
      }

      if (hitType == 0) {
         if (targetType != 2 && attackerWeaponId == 12) {
            hurtEntityAt(attackX >> 6, attackY >> 6, attackX, attackY, 0, damage + crArmorDamage, Player.getPlayerEnt());
            radiusHurtEntities(attackX >> 6, attackY >> 6, 1, damage + crArmorDamage, Player.getPlayerEnt());
         }

      } else {
         if (targetType == 2) {
            if (totalDamage > 0) {
               checkMonsterFX();
               boolean var0 = curTarget.pain(totalDamage, Player.getPlayerEnt());
               if (attackerWeaponId != 16) {
                  ParticleSystem.spawnMonsterBlood(curTarget, false);
               }

               int var1 = targetMonster.ce.getStat(0);
               if (var1 > 0) {
                  int var2 = 0;
                  if ((1L << attackerWeaponId & 7L) != 0L && targetSubType == 4) {
                     if ((curTarget.monster.flags & 1) == 0) {
                        int var3 = curTarget.getSprite();
                        GameSprite var4 = Game.gsprite_alloc(curTarget.def.tileIndex, 16, 16386);
                        Render.mapSprites[Render.S_X + var4.sprite] = var4.pos[0] = var4.pos[3] = Render.mapSprites[Render.S_X + var3];
                        Render.mapSprites[Render.S_Y + var4.sprite] = var4.pos[1] = var4.pos[4] = Render.mapSprites[Render.S_Y + var3];
                        Render.mapSprites[Render.S_Z + var4.sprite] = var4.pos[2] = var4.pos[5] = (short)(Render.mapSprites[Render.S_Z + var3] + 24);
                        short[] var10000 = var4.pos;
                        var10000[3] = (short)(var10000[3] - Canvas.viewRightStepX);
                        var10000 = var4.pos;
                        var10000[4] = (short)(var10000[4] - Canvas.viewRightStepY);
                        var10000 = var4.pos;
                        var10000[5] = (short)(var10000[5] + 20);
                        var4.duration = 500;
                        Render.mapSprites[Render.S_SCALEFACTOR + var4.sprite] = 25;
                        var4.vel[0] = (var4.pos[3] - var4.pos[0]) * 1000 / var4.duration;
                        var4.vel[1] = (var4.pos[4] - var4.pos[1]) * 1000 / var4.duration;
                        EntityMonster var19 = curTarget.monster;
                        var19.flags = (short)(var19.flags | 1);
                        Render.relinkSprite(var4.sprite);
                     }
                  } else if (attackerWeaponId == 3) {
                     ++var2;
                  }

                  if (0 != (targetMonster.monsterEffects & 2) || (targetMonster.flags & 4096) != 0 || var0) {
                     var2 = 0;
                  }

                  if (var2 > 0) {
                     Player.unlink();
                     curTarget.knockback(Canvas.viewX, Canvas.viewY, var2);
                     Player.link();
                  }
               }

               if (attackerWeaponId == 12) {
                  radiusHurtEntities(attackX >> 6, attackY >> 6, 1, crDamage >> 2, Player.getPlayerEnt());
               }
            } else if (totalDamage < 0) {
               if (targetMonster.ce.getStat(0) - totalDamage > targetMonster.ce.getStat(1)) {
                  totalDamage = -(targetMonster.ce.getStat(1) - targetMonster.ce.getStat(0));
               }

               targetMonster.ce.setStat(0, targetMonster.ce.getStat(0) - totalDamage);
            }
         } else {
            int var11;
            int[] var21;
            if (targetType == 10) {
               var11 = curTarget.getSprite();
               short var12 = Render.mapSprites[Render.S_X + var11];
               short var13 = Render.mapSprites[Render.S_Y + var11];
               short var14 = Render.mapSprites[Render.S_Z + var11];
               short var5;
               byte var7;
               LerpSprite var8;
               short var15;
               Entity var20;
               if (attackerWeaponId == 3 && targetSubType == 2) {
                  var7 = 1;
                  var15 = (short)(var12 + Canvas.viewStepX * var7);
                  var5 = (short)(var13 + Canvas.viewStepY * var7);
                  Game.trace(var12, var13, var15, var5, curTarget, 13501, 16);
                  if (Game.traceEntity == null) {
                     var8 = Game.allocLerpSprite((ScriptThread)null, var11, true);
                     var8.srcX = var12;
                     var8.srcY = var13;
                     var8.srcZ = var14;
                     var8.srcScale = var8.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var11];
                     var8.dstX = var15;
                     var8.dstY = var5;
                     var8.dstZ = Render.getHeight(var15, var5) + 32;
                     var8.startTime = App.gameTime - 50;
                     var8.travelTime = 250 * var7;
                     Game.updateLerpSprite(var8);
                     var20 = curTarget;
                     var20.info |= 4194304;
                  }

                  totalDamage = 0;
               } else {
                  int var9;
                  if ((1L << attackerWeaponId & 7L) != 0L && targetSubType == 8) {
                     int var16 = App.nextInt();

                     for(int var17 = 0; var17 < 4; ++var17) {
                        var9 = var16 + var17 & 3;
                        if (var9 != Canvas.viewAngle - 256 >> 8) {
                           var9 <<= 2;
                           var15 = (short)(var12 + Canvas.viewStepValues[var9 + 0]);
                           var5 = (short)(var13 + Canvas.viewStepValues[var9 + 1]);
                           Game.trace(var12, var13, var15, var5, curTarget, 15535, 16);
                           if (Game.numTraceEntities == 0) {
                              var21 = Render.mapSpriteInfo;
                              var21[var11] |= 256;
                              LerpSprite var18 = Game.allocLerpSprite((ScriptThread)null, var11, true);
                              var18.flags |= 1024;
                              var18.srcX = var12;
                              var18.srcY = var13;
                              var18.srcZ = var14 + 8;
                              var18.srcScale = var18.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var11];
                              var18.dstX = var15;
                              var18.dstY = var5;
                              var18.dstZ = Render.getHeight(var15, var5) + 32;
                              var18.startTime = App.gameTime - 50;
                              var18.travelTime = 300;
                              Game.updateLerpSprite(var18);
                              var20 = curTarget;
                              var20.info |= 4194304;
                              totalDamage = 0;
                              return;
                           }
                        }
                     }

                     if (++curTarget.param <= 2 && (crFlags & 2) == 0) {
                        var8 = Game.allocLerpSprite((ScriptThread)null, var11, true);
                        var21 = Render.mapSpriteInfo;
                        var21[var11] |= 256;
                        var20 = curTarget;
                        var20.info |= 4194304;
                        totalDamage = 0;
                        var8.flags |= 1028;
                        var8.dstX = var8.srcX = var12;
                        var8.dstY = var8.srcY = var13;
                        var8.srcZ = Render.getHeight(var8.dstX, var8.dstY) + 32;
                        var8.dstZ = Render.getHeight(var8.dstX, var8.dstY) + 32;
                        var8.height = 4;
                        var8.srcScale = var8.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var11];
                        var8.travelTime = 350;
                        var8.startTime = App.gameTime - 50;
                        Game.updateLerpSprite(var8);
                        ParticleSystem.spawnParticles(1, -1, var11);
                        return;
                     }

                     curTarget.died(true, (Entity)null);
                     ParticleSystem.spawnParticles(1, -1, var11);
                     return;
                  }

                  if (attackerWeaponId == 3 && targetSubType == 8) {
                     var7 = 32;
                     if (!Canvas.isChickenKicking && (++curTarget.param > 2 || (crFlags & 2) != 0)) {
                        curTarget.died(true, (Entity)null);
                        ParticleSystem.spawnParticles(1, -1, var11);
                        return;
                     }

                     var8 = Game.allocLerpSprite((ScriptThread)null, var11, true);
                     var21 = Render.mapSpriteInfo;
                     var21[var11] |= 256;
                     var20 = curTarget;
                     var20.info |= 4194304;
                     totalDamage = 0;
                     var8.flags |= 1028;
                     var8.srcX = var12;
                     var8.srcY = var13;
                     var8.srcZ = var14;
                     var8.srcScale = var8.dstScale = Render.mapSprites[Render.S_SCALEFACTOR + var11];
                     if (Canvas.isChickenKicking && (!Canvas.isChickenKicking || Canvas.kickingPhase != 0)) {
                        var7 = 48;
                        var8.dstX = (short)(var12 + Canvas.viewStepX * Canvas.chickenDestFwd + Canvas.viewRightStepX * Canvas.chickenDestRight + 31 * (Canvas.viewStepX >> 6));
                        var8.dstY = (short)(var13 + Canvas.viewStepY * Canvas.chickenDestFwd + Canvas.viewRightStepY * Canvas.chickenDestRight + 31 * (Canvas.viewStepY >> 6));
                        Canvas.gridIdx = 19 - ((var8.dstX >> 6) - 1 + ((var8.dstY >> 6) - 18) * 5);
                        var8.travelTime = 350 * Canvas.chickenDestFwd;
                        var8.height = Canvas.chickenDestFwd * 16;
                     } else {
                        var9 = Math.max(Player.ce.getStat(4) / 20 + 1, 3);
                        var8.dstX = (short)(var12 + Canvas.viewStepX * var9);
                        var8.dstY = (short)(var13 + Canvas.viewStepY * var9);
                        int var10 = curTarget.getFarthestKnockbackDist(var8.srcX, var8.srcY, var8.dstX, var8.dstY, curTarget, 13501, 16, var9);
                        if (var10 == 0) {
                           var8.dstX = (short)(var12 + (Canvas.viewStepX >> 6) * 31);
                           var8.dstY = (short)(var13 + (Canvas.viewStepY >> 6) * 31);
                           var8.height = 16;
                           var8.flags |= 8;
                           var8.travelTime = 350;
                        } else {
                           if (var10 < var9) {
                              var8.dstX = (short)(var12 + Canvas.viewStepX * var10 + (Canvas.viewStepX >> 6) * 31);
                              var8.dstY = (short)(var13 + Canvas.viewStepY * var10 + (Canvas.viewStepY >> 6) * 31);
                              var8.flags |= 8;
                              var8.travelTime = 350 * var10 + 250;
                           } else {
                              var8.travelTime = 350 * var10;
                           }

                           var8.height = var10 * 16;
                        }
                     }

                     var8.dstZ = Render.getHeight(var8.dstX, var8.dstY) + var7;
                     var8.startTime = App.gameTime - 50;
                     Game.updateLerpSprite(var8);
                     ParticleSystem.spawnParticles(1, -1, var11);
                  }
               }

               if (totalDamage > 0) {
                  curTarget.pain(totalDamage, Player.getPlayerEnt());
               }
            } else if (targetType == 9) {
               var11 = curTarget.getSprite();
               var21 = Render.mapSpriteInfo;
               var21[var11] |= 65536;
               isGibbed = true;
               ParticleSystem.spawnMonsterBlood(curTarget, isGibbed);
               if (attackerWeaponId == 42) {
                  curAttacker.monster.ce.setStat(0, curAttacker.monster.ce.getStat(1));
               }
            } else if (targetType == 14 && curAttacker != null && curAttacker.def.eType == 2 && curAttacker.def.eSubType == 14) {
               var11 = curTarget.getSprite();
               ParticleSystem.spawnParticles(1, -10092544, var11);
               curAttacker.monster.ce.addStat(0, 150);
               curAttacker.param = 0;
            }
         }

      }
   }

   private static final void explodeOnPlayer() {
      if (curTarget == null) {
         int var0 = curAttacker.getSprite();
         int var1 = Canvas.viewAngle;
         if (Canvas.isZoomedIn) {
            var1 += Canvas.zoomAngle;
         }

         Hud.damageDir = Player.calcDamageDir(Canvas.viewX, Canvas.viewY, var1, Render.mapSprites[Render.S_X + var0], Render.mapSprites[Render.S_Y + var0]);
         if (animLoopCount > 0) {
            Hud.damageTime = App.time + 150;
         } else {
            Hud.damageTime = App.time + 1000;
         }

         if (Hud.damageDir != 3) {
            Canvas.staleTime = Hud.damageTime + 1;
         }

         boolean var2 = false;
         if (Player.buffs[0] > 0 && !curAttacker.isBoss()) {
            var2 = true;
         }

         if (!gotHit || totalDamage <= 0 && totalArmorDamage <= 0) {
            Hud.damageCount = 0;
            if ((crFlags & 256) != 0) {
               Text.resetTextArgs();
               Hud.addMessage((short)75);
            } else if ((crFlags & 128) == 0) {
               Text.resetTextArgs();
               Text var7 = Text.getSmallBuffer();
               Text.composeTextField(curAttacker.name, var7);
               Text.addTextArg(var7);
               Hud.addMessage((short)70);
               var7.dispose();
            }

            Hud.damageCount = -1;
         } else {
            Player.painEvent(curAttacker, true);
            if (curAttacker != null) {
               Entity var3 = Game.entities[1];
               Game.unlinkEntity(curAttacker);
               if (attackerWeaponProj == 2) {
                  Player.addStatusEffect(13, 5, 3);
                  Player.translateStatusEffects();
               } else if ((attackerWeaponId == 35 || attackerWeaponId == 33) && !Player.hasPurifyEffect()) {
                  Canvas.startShake(500, 4, 500);
                  Player.addStatusEffect(16, 20, 1);
                  Player.translateStatusEffects();
               } else if (attackerWeaponId == 34 && !Player.hasPurifyEffect()) {
                  Player.addStatusEffect(17, 7, 5);
                  Player.translateStatusEffects();
               }

               Player.addArmor(-totalArmorDamage);
               if (totalDamage > 0) {
                  if (!var2) {
                     int var4;
                     if (Game.difficulty != 1) {
                        var4 = Canvas.loadMapID;
                        if (Game.difficulty == 4) {
                           var4 *= 2;
                        }

                        var4 /= weapons[attackerWeapon + 7];
                        totalDamage += (totalDamage >> 1) + var4;
                     }

                     accumRoundDamage += totalDamage;
                     Player.pain(totalDamage, curAttacker, false);
                     if (Player.ce.getStat(0) == 0) {
                        return;
                     }

                     if (attackerWeaponId == 36) {
                        Render.rockView(1000, Canvas.viewX, Canvas.viewY, Canvas.viewZ - 10);
                     } else if (Canvas.knockbackDist == 0) {
                        int[] var8 = curAttacker.calcPosition();
                        int var5 = Canvas.viewX - var8[0];
                        int var6 = Canvas.viewY - var8[1];
                        if (var5 != 0) {
                           var5 /= Math.abs(var5);
                        }

                        if (var6 != 0) {
                           var6 /= Math.abs(var6);
                        }

                        Render.rockView(200, Canvas.viewX + var5 * 6, Canvas.viewY + var6 * 6, Canvas.viewZ);
                     }

                     if (attackerWeaponId == 21 && !Player.hasPurifyEffect()) {
                        Player.addStatusEffect(14, 10, 5);
                        Player.translateStatusEffects();
                     }

                     if (Player.ce.getStat(0) > 0 && (1L << attackerWeaponId & 26972663054336L) != 0L && curAttacker.def.eSubType != 10) {
                        var4 = attackerWeaponId == 39 ? 2 : 1;
                        var3.knockback(Render.mapSprites[Render.S_X + var0], Render.mapSprites[Render.S_Y + var0], var4);
                     }
                  } else if (curAttacker.monster != null) {
                     accumRoundDamage = reflectionDmg = totalDamage;
                     animLoopCount = 0;
                     nextStage = 1;
                  }
               } else {
                  Hud.damageCount = 0;
                  totalDamage = 0;
               }

               if (totalDamage <= 0) {
                  Hud.addMessage((short)74);
               }

               Game.linkEntity(curAttacker, curAttacker.linkIndex % 32, curAttacker.linkIndex / 32);
            }
         }

      }
   }

   public static final int getMonsterField(EntityDef var0, int var1) {
      return monsterAttacks[var1 + var0.eSubType * 9 + var0.parm * 3];
   }

   public static final void radiusHurtEntities(int var0, int var1, int var2, int var3, Entity var4) {
      int var5 = (var0 << 6) + 32;
      int var6 = (var1 << 6) + 32;
      int var7 = Render.getHeight(var0 << 6, var1 << 6) + 32;

      for(int var8 = var1 - 1; var8 <= var1 + 1; ++var8) {
         for(int var9 = var0 - 1; var9 <= var0 + 1; ++var9) {
            if (var9 != var0 || var8 != var1) {
               Game.trace(var5, var6, var7, (var9 << 6) + 32, (var8 << 6) + 32, Render.getHeight(var9 << 6, var8 << 6) + 32, (Entity)null, 4129, 1);
               if (Game.traceEntity == null) {
                  hurtEntityAt(var9, var8, var0, var1, var2, var3, var4);
               }
            }
         }
      }

   }

   public static final void hurtEntityAt(int var0, int var1, int var2, int var3, int var4, int var5, Entity var6) {
      hurtEntityAt(var0, var1, var2, var3, var4, var5, var6, false);
   }

   public static final void hurtEntityAt(int var0, int var1, int var2, int var3, int var4, int var5, Entity var6, boolean var7) {
      crFlags = 16;
      Render.shotsFired = true;
      Entity var9;
      if (Canvas.viewX >> 6 == var0 && Canvas.viewY >> 6 == var1) {
         if (Player.buffs[0] > 0) {
            Text.resetTextArgs();
            Text.addTextArg(var5);
            Hud.addMessage((short)1, (short)147);
         } else {
            int var12 = var5 * 10 / 100;
            var12 = Math.min(var12, Player.ce.getStat(2));
            var5 -= var12;
            var5 -= var5 * Player.ce.getStat(3) / 100;
            Player.painEvent((Entity)null, false);
            Entity var14 = Player.getPlayerEnt();
            var14.knockback((var2 << 6) + 32, (var3 << 6) + 32, var4);
            if (var5 > 0) {
               crArmorDamage = var12;
               Player.pain(var5, (Entity)null, true);
               Player.addArmor(-crArmorDamage);
               if (!Game.isUnderWater()) {
                  Player.addStatusEffect(13, 5, 3);
               }

               Player.translateStatusEffects();
            }
         }
      } else {
         for(Entity var8 = Game.findMapEntity(var0 << 6, var1 << 6, 30381); var8 != null; var8 = var9) {
            var9 = var8.nextOnTile;
            if ((var8.info & 131072) != 0) {
               if (var8.def.eType == 9) {
                  if (!var7) {
                     return;
                  }

                  var8.died(false, var6);
                  var8.info |= 65536;
                  ParticleSystem.spawnMonsterBlood(var8, true);
               } else if (var8.monster == null || var8.def.eSubType != 14) {
                  if (var8.monster != null) {
                     var8.info |= 2097152;
                     if ((var8.info & 262144) == 0) {
                        Game.activate(var8, true, false, true, true);
                     }

                     short var10 = getWeaponWeakness(11, var8.def.eSubType, var8.def.parm);
                     int var13 = var10 * var5 >> 8;
                     var13 -= var8.monster.ce.getStatPercent(3) * var13 >> 8;
                     if (var13 > 0) {
                        if (!Game.isUnderWater() && (var8.def.eSubType != 4 || var8.def.parm <= 0) && var8.def.eSubType != 6) {
                           EntityMonster var10000 = var8.monster;
                           var10000.monsterEffects &= -6153;
                           var10000 = var8.monster;
                           var10000.monsterEffects = (short)(var10000.monsterEffects | 8);
                        }

                        boolean var11 = var8.pain(var13, (Entity)null);
                        if ((var8.monster.flags & 4096) == 0 && !var11) {
                           var8.knockback((var2 << 6) + 32, (var3 << 6) + 32, var4);
                        }

                        Text.resetTextArgs();
                        Text.addTextIDArg(var8.name);
                        Text.addTextArg(var13);
                        if (var8.monster.ce.getStat(0) <= 0) {
                           Hud.addMessage((short)76);
                           if (var7 && var6 != null && var6.def.eType == 14 && var6.def.eSubType == 6) {
                              var8.info |= 65536;
                              ParticleSystem.spawnMonsterBlood(var8, true);
                           }

                           var8.died(true, var6);
                        } else {
                           Hud.addMessage((short)77);
                        }
                     }
                  } else if (var8.def.eType != 8) {
                     if (var8.def.eType == 10 && ((long)tableCombatMasks[var8.def.parm] & 2048L) == 0L) {
                        return;
                     }

                     var8.pain(var5, var6);
                     if (!var8.isExplodableEntity()) {
                        var8.died(true, var6);
                     }
                  }
               }
            }
         }
      }

   }

   public static final Text getWeaponStatStr(int var0) {
      int var1 = var0 * 9;
      Text var2 = Text.getLargeBuffer();
      Text.resetTextArgs();
      Text.addTextArg(weapons[var1 + 0]);
      Text.composeText((short)1, (short)79, var2);
      Text.resetTextArgs();
      if (weapons[var1 + 3] != 1) {
         Text.addTextArg(weapons[var1 + 2]);
         Text.addTextArg(weapons[var1 + 3]);
         Text.composeText((short)1, (short)80, var2);
      } else {
         Text.addTextArg(weapons[var1 + 3]);
         Text.composeText((short)1, (short)81, var2);
      }

      byte var3 = weapons[var1 + 4];
      EntityDef var4 = EntityDef.find(6, 2, var3);
      if (var4 != null) {
         Text.composeText((short)1, (short)78, var2);
         Text.composeText((short)2, var4.name, var2);
      }

      return var2;
   }

   public static final Text getArmorStatStr(int var0) {
      Text var1 = Text.getLargeBuffer();
      if (var0 != -1) {
         Text.resetTextArgs();
         Text.addTextArg(Player.ce.getStat(2));
         Text.composeText((short)1, (short)82, var1);
      }

      Text.composeText((short)1, (short)83, var1);
      return var1;
   }

   public static final int WorldDistToTileDist(int var0) {
      for(int var1 = 0; var1 < tileDistances.length - 1; ++var1) {
         if (var0 < tileDistances[var1]) {
            return var1;
         }
      }

      return tileDistances.length - 1;
   }

   public static final void cleanUpAttack() {
      stage = 1;
      curAttacker = null;
      curTarget = null;
   }

   private static void updateProjectile() {
      int var1;
      if (curAttacker != null && (1L << attackerWeaponId & 26458340720640L) == 0L) {
         int var0 = curAttacker.getSprite();
         var1 = (Render.mapSpriteInfo[var0] & '\uff00') >> 8;
         if ((var1 & 15) == 0) {
            return;
         }
      }

      int[] var10000;
      int var10001;
      if (numActiveMissiles > 0) {
         byte var9 = 0;

         for(var1 = 0; var1 < numActiveMissiles; ++var1) {
            GameSprite var2 = activeMissiles[var1];
            if ((var2.flags & 2) == 0) {
               boolean var3 = true;
               byte var4 = 64;
               int var5 = var2.pos[3];
               int var6 = var2.pos[4];
               short var7 = var2.pos[5];
               switch(attackerWeaponProj) {
               case 1:
                  missileAnim = 242;
                  var9 = 4;
                  Canvas.startShake(500, 4, 500);
                  Sound.playSound(10);
                  break;
               case 3:
                  missileAnim = 230;
                  Sound.playSound(13);
                  break;
               case 4:
               default:
                  missileAnim = 0;
                  break;
               case 5:
                  missileAnim = 0;
                  ParticleSystem.spawnParticles(1, -1, var2.sprite);
                  Sound.playSound(10);
                  break;
               case 6:
                  Sound.playSound(13);
                  break;
               case 7:
                  missileAnim = 0;
               case 2:
                  Sound.playSound(10);
                  if (curTarget == null && hitType != 0 && Player.buffs[9] > 0) {
                     missileAnim = 208;
                     var5 += Canvas.viewStepX >> 1;
                     var6 += Canvas.viewStepY >> 1;
                     var9 = 3;
                  } else if (attackerWeaponProj == 2) {
                     missileAnim = 242;
                     var9 = 4;
                  }
               }

               if (curAttacker != null && (1 << Hud.damageDir & 28) == 0 && !var3) {
                  missileAnim = 0;
               }

               Game.gsprite_destroy(var2);
               int var8;
               if (missileAnim != 0 && (curAttacker == null || curTarget == null && hitType != 0)) {
                  var2 = Game.gsprite_allocAnim(missileAnim, var5, var6, var7);
                  var2.flags |= 2048;
                  nextStageTime = App.gameTime + var2.duration;
                  Render.mapSprites[Render.S_RENDERMODE + var2.sprite] = (short)var9;
                  Render.mapSprites[Render.S_SCALEFACTOR + var2.sprite] = (short)var4;
                  if (curAttacker != null) {
                     var8 = (curAttacker.info & '\uffff') - 1;
                     if ((Render.mapSpriteInfo[var8] & 131072) != 0) {
                        var10000 = Render.mapSpriteInfo;
                        var10001 = var2.sprite;
                        var10000[var10001] |= 131072;
                     }
                  }
               }

               for(var8 = var1 + 1; var8 < numActiveMissiles; ++var8) {
                  activeMissiles[var8 - 1] = activeMissiles[var8];
               }

               --numActiveMissiles;
               if (attackerWeaponProj == 8 && (crFlags & 4103) != 0) {
                  Entity var13 = Game.findMapEntity(var5, var6, 1028);
                  if (var13 != null && (var13.def.eType != 10 || var13.def.eType == 10 && var13.def.eSubType == 8)) {
                     var13.pain(totalDamage, curAttacker);
                     if (var13 != curTarget && var13.monster.ce.getStat(0) <= 0) {
                        var13.died(true, curAttacker);
                     }
                  }
               } else {
                  exploded = true;
               }
            }
         }
      } else {
         int[] var10;
         GameSprite var11;
         int var12;
         if ((attackerWeaponId == 43 || attackerWeaponId == 44) && curAttacker != null && curAttacker.def.eSubType != 10) {
            missileAnim = 246;
            var10 = curAttacker.calcPosition();
            if (Game.isInFront(var10[0] >> 6, var10[1] >> 6)) {
               var11 = Game.gsprite_allocAnim(missileAnim, Canvas.destZ, Canvas.destY, Canvas.destZ);
               var11.flags |= 2048;
               nextStageTime += var11.duration;
               Render.mapSprites[Render.S_RENDERMODE + var11.sprite] = 4;
               var12 = (curAttacker.info & '\uffff') - 1;
               if ((Render.mapSpriteInfo[var12] & 131072) != 0) {
                  var10000 = Render.mapSpriteInfo;
                  var10001 = var11.sprite;
                  var10000[var10001] |= 131072;
               }
            }
         } else if (attackerWeaponId == 45) {
            missileAnim = 245;
            var10 = curAttacker.calcPosition();
            if (Game.isInFront(var10[0] >> 6, var10[1] >> 6)) {
               var11 = Game.gsprite_allocAnim(missileAnim, Canvas.destZ, Canvas.destY, Canvas.destZ);
               var11.flags |= 2048;
               Render.mapSprites[Render.S_RENDERMODE + var11.sprite] = 5;
               var12 = (curAttacker.info & '\uffff') - 1;
               if ((Render.mapSpriteInfo[var12] & 131072) != 0) {
                  var10000 = Render.mapSpriteInfo;
                  var10001 = var11.sprite;
                  var10000[var10001] |= 131072;
               }
            }
         }
      }

      if (exploded) {
         if (curTarget == null) {
            explodeOnPlayer();
            exploded = false;
         } else {
            explodeOnMonster();
            exploded = false;
         }
      }

   }

   private static void launchProjectile() {
      int var0 = 256;
      boolean var1 = true;
      byte var2 = 16;
      byte var3 = 16;
      boolean var4 = false;
      dodgeDir = App.nextInt() > 1073741823 ? 1 : 0;
      int var11 = ((dodgeDir << 1) - 1) * 16;
      int var5;
      int var6;
      int var7;
      int var12;
      if (curAttacker == null) {
         var5 = Game.viewX;
         var6 = Game.viewY;
         var7 = Render.getHeight(var5, var6) + 32;
         var2 = 0;
      } else {
         var12 = (curAttacker.info & '\uffff') - 1;
         var5 = Render.mapSprites[Render.S_X + var12];
         var6 = Render.mapSprites[Render.S_Y + var12];
         var7 = Render.mapSprites[Render.S_Z + var12];
      }

      int var8;
      int var9;
      int var10;
      if (curTarget == null) {
         var8 = Game.viewX;
         var9 = Game.viewY;
         var10 = Render.getHeight(var8, var9) + 32;
      } else if (targetType == 0) {
         var8 = Game.traceCollisionX;
         var9 = Game.traceCollisionY;
         var10 = Game.traceCollisionZ;
      } else {
         var12 = (curTarget.info & '\uffff') - 1;
         var8 = Render.mapSprites[Render.S_X + var12];
         var9 = Render.mapSprites[Render.S_Y + var12];
         var10 = Render.mapSprites[Render.S_Z + var12];
      }

      byte var25;
      switch(attackerWeaponProj) {
      case 1:
         missileAnim = 226;
         var25 = 0;
         var0 += 64;
         break;
      case 2:
         missileAnim = 242;
         var1 = false;
         var25 = 3;
         break;
      case 3:
         missileAnim = 230;
         var25 = 0;
         EntityMonster var10000 = curAttacker.monster;
         var10000.flags = (short)(var10000.flags | 1);
         var10 -= 16;
         break;
      case 4:
      default:
         missileAnim = 0;
         exploded = true;
         return;
      case 5:
         var1 = false;
         missileAnim = Player.activeWeaponDef.tileIndex;
         var25 = 0;
         break;
      case 6:
         missileAnim = 227;
         var25 = 0;
         break;
      case 7:
         var25 = 3;
         missileAnim = 241;
         var1 = false;
         break;
      case 8:
         var25 = 3;
         missileAnim = 237;
         var1 = false;
      }

      var12 = Math.abs(var8 - var5);
      int var13 = Math.abs(var9 - var6);
      int var14;
      if ((crFlags & 1024) != 0) {
         var14 = weapons[attackerWeapon + 3] * 64;
         var3 = 0;
         if (var12 != 0 && var12 > var13) {
            var8 = var5 + (var8 - var5) * var14 / var12;
         } else if (var13 != 0) {
            var9 = var6 + (var9 - var6) * var14 / var13;
         }

         var12 = Math.abs(var8 - var5);
         var13 = Math.abs(var9 - var6);
         var11 = 0;
      } else if (hitType == 0) {
         if ((targetType != 2 || targetSubType == 8) && curTarget != null) {
            var11 = 0;
         } else {
            var3 = 0;
         }
      } else {
         var11 = 0;
      }

      if (var13 > var12) {
         var14 = (var9 - var6 >> 31 << 1) + 1;
         var6 += var2 * var14;
         var9 += var3 * -var14;
         var8 += var11;
      } else {
         var14 = (var8 - var5 >> 31 << 1) + 1;
         var5 += var2 * var14;
         var8 += var3 * -var14;
         var9 += var11;
      }

      if (attackerWeaponProj == 1) {
         if (curAttacker == null) {
            var5 += 4 * Canvas.viewRightStepX >> 6;
            var6 += 4 * Canvas.viewRightStepY >> 6;
            var7 -= 5;
         } else {
            byte var26 = 16;
            int var15 = curAttacker.getSprite();
            if ((Render.mapSpriteInfo[var15] & 131072) != 0) {
               var26 = -16;
            }

            var5 += var26 * Canvas.viewRightStepX >> 6;
            var6 += var26 * Canvas.viewRightStepY >> 6;
         }
      }

      if (attackerWeaponProj == 2 && curAttacker != null && curAttacker.def.eSubType == 14) {
         var5 += 28 * Canvas.viewRightStepX >> 6;
         var6 += 28 * Canvas.viewRightStepY >> 6;
         var7 += 26;
      }

      var14 = 1000 * Math.max(var12, var13) / var0;
      GameSprite var27 = allocMissile(var5, var6, var7, var8, var9, var10, var14, var25);
      int var10001;
      int[] var28;
      if (attackerWeaponProj == 7 || attackerWeaponProj == 8) {
         if (curAttacker == null) {
            Render.mapSprites[Render.S_ENT + var27.sprite] = Player.getPlayerEnt().getIndex();
            Render.relinkSprite(var27.sprite, var27.pos[0] << 4, var27.pos[1] << 4, var27.pos[2] << 4);
         } else {
            Render.mapSprites[Render.S_ENT + var27.sprite] = curAttacker.getIndex();
            Render.relinkSprite(var27.sprite, var27.pos[3] << 4, var27.pos[4] << 4, var27.pos[5] << 4);
         }

         var27.flags |= 4;
         var27.pos[0] = var27.pos[3];
         var27.pos[1] = var27.pos[4];
         var27.pos[2] = var27.pos[5];
         var27.vel[0] = var27.vel[1] = var27.vel[2] = 0;
         if (attackerWeaponProj == 8 && curTarget != null && curTarget.def.eType == 2 && (crFlags & 4103) != 0) {
            short var16 = var27.pos[0];
            short var17 = var27.pos[1];

            for(int var18 = 0; var18 < 8; ++var18) {
               Entity var19 = Game.findMapEntity(var16 + Canvas.viewStepValues[var18 << 1], var17 + Canvas.viewStepValues[(var18 << 1) + 1], 4);
               if (var19 != null && (var19.monster.flags & 2048) == 0) {
                  int var20 = var19.getSprite();
                  short var21 = Render.mapSprites[Render.S_X + var20];
                  short var22 = Render.mapSprites[Render.S_Y + var20];
                  short var23 = Render.mapSprites[Render.S_Z + var20];
                  GameSprite var24 = allocMissile(var21, var22, var23, var21, var22, var23, var14, var25);
                  var24.vel[0] = var24.vel[1] = var24.vel[2] = 0;
                  var24.flags |= 4;
                  Render.relinkSprite(var24.sprite, var24.pos[3] << 4, var24.pos[4] << 4, var24.pos[5] << 4);
                  Render.mapSprites[Render.S_ENT + var24.sprite] = curTarget.getIndex();
                  var28 = Render.mapSpriteInfo;
                  var10001 = var24.sprite;
                  var28[var10001] &= -589569;
               }
            }
         }
      }

      if (!var1) {
         var28 = Render.mapSpriteInfo;
         var10001 = var27.sprite;
         var28[var10001] &= -589569;
      }

      exploded = false;
   }

   private static GameSprite allocMissile(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (numActiveMissiles == 8) {
         App.Error(new Exception("MAX_ACTIVE_MISSILES"), 108);
         return null;
      } else {
         int var8 = Render.mediaMappings[missileAnim + 1] - Render.mediaMappings[missileAnim];
         GameSprite var9 = activeMissiles[numActiveMissiles] = Game.gsprite_alloc(missileAnim, var8, 2562);
         ++numActiveMissiles;
         short[] var10 = Render.mapSprites;
         var10[Render.S_ENT + var9.sprite] = -1;
         var10[Render.S_RENDERMODE + var9.sprite] = (short)var7;
         var10[Render.S_X + var9.sprite] = var9.pos[0] = (short)var0;
         var10[Render.S_Y + var9.sprite] = var9.pos[1] = (short)var1;
         var10[Render.S_Z + var9.sprite] = var9.pos[2] = (short)var2;
         int[] var10000 = Render.mapSpriteInfo;
         int var10001 = var9.sprite;
         var10000[var10001] |= 524288;
         var9.pos[3] = (short)var3;
         var9.pos[4] = (short)var4;
         var9.pos[5] = (short)var5;
         var9.duration = var6;
         if (attackerWeaponProj == 5) {
            var9.flags |= 16384;
            int var11 = 8;
            if (var9.pos[5] > var9.pos[2]) {
               var11 /= 4;
            }

            var9.pos[5] = (short)(var9.pos[5] + Math.min(tileDist, 5) * var11);
            if (var9.pos[5] < var9.pos[2]) {
               var9.pos[5] = var9.pos[2];
            }
         }

         if (var6 != 0) {
            var9.vel[0] = 1000 * (var9.pos[3] - var9.pos[0]) / var6;
            var9.vel[1] = 1000 * (var9.pos[4] - var9.pos[1]) / var6;
            var9.vel[2] = 1000 * (var9.pos[5] - var9.pos[2]) / var6;
         } else {
            var9.vel[0] = var9.vel[1] = var9.vel[2] = 0;
         }

         if (missileAnim == 0) {
            var10000 = Render.mapSpriteInfo;
            var10001 = var9.sprite;
            var10000[var10001] |= 65536;
            return var9;
         } else {
            Render.relinkSprite(var9.sprite);
            return var9;
         }
      }
   }
}
