/* Decompiler 357ms, total 872ms, lines 1395 */
import javax.microedition.lcdui.Image;

final class CardGames {
   public static int touchMe = 1;
   private static final int GRAVITY = 10;
   private static final int DEF_VELOCITY = 2048;
   private static final int NUM_POKER_CARDS = 51;
   private static final int CARD_W = 17;
   private static final int CARD_H = 25;
   private static final int SUITSTART = 112;
   private static final int CARD_ROT0 = 1;
   private static final int CARD_ROT90 = 2;
   private static final int CARD_ROT180 = 4;
   private static final int CARD_ROT270 = 8;
   private static final int CARD_FACE_UP = 16;
   private static final int CARD_LERPING = 32;
   private static final int CARD_EXLERP = 64;
   private static final int CARD_HIDDEN = 128;
   private static final int CARD_PLAYER = 256;
   private static final int CARD_DEALER = 512;
   private static final int CARD_DARKEN = 1024;
   private static final int CARD_DESC = 0;
   private static final int CARD_SRCX = 1;
   private static final int CARD_SRCY = 2;
   private static final int CARD_DSTX = 3;
   private static final int CARD_DSTY = 4;
   private static final int CARD_FLAGS = 5;
   private static final int CARD_START = 6;
   private static final int CARD_LERP_DUR = 7;
   private static final int CARD_FIELDS = 8;
   private static final int MAX_CARDS = 24;
   private static final int PART_X = 0;
   private static final int PART_Y = 1;
   private static final int PART_VX = 2;
   private static final int PART_VY = 3;
   private static final int PART_ALIVE = 4;
   private static final int PART_FIELDS = 5;
   private static final int CHIP_OWNER = 0;
   private static final int CHIP_SRCX = 1;
   private static final int CHIP_SRCY = 2;
   private static final int CHIP_DSTX = 3;
   private static final int CHIP_DSTY = 4;
   private static final int CHIP_START = 5;
   private static final int CHIP_DUR = 6;
   private static final int CHIP_FIELDS = 7;
   private static final int MAX_CHIPS = 20;
   private static final int GAME_STATE = 0;
   private static final int PREV_STATE = 1;
   private static final int STATE_TIME = 2;
   private static final int CUR_BET = 3;
   private static final int MSG_ID = 4;
   private static final int SUB_STATE = 5;
   private static final int CARD_SEL_IDX = 6;
   private static final int DEALER_SEL_IDX = 7;
   private static final int MSG_TIME = 8;
   static int[] stateVars;
   public static Image imgOtherImgs;
   public static Image imgTable;
   private static final int INVALID_CARD = -1;
   private static int numPlayerCards;
   private static int numDealerCards;
   private static final short[] playersCards;
   private static final short[] dealersCards;
   private static int[] cardsInPlay;
   private static int numCardsInPlay;
   private static long deckMask;
   private static int numCardsLerping;
   private static int backgroundColor;
   private static ScriptThread callingThread;
   private static boolean savePlayer;
   private static int playerTreasure;
   private static int aiTreasure;
   private static int playerChips;
   private static int dealerChips;
   private static int startingChips;
   private static int chipAmount;
   private static int betPercentage;
   private static final int DEFAULT_TREASURE = 100;
   private static int CARDS_Y;
   private static int CARDS_X;
   private static int NUM_CARDS_W;
   private static int NUM_CARDS_H;
   private static final int NUM_WAR_CARDS = 6;
   private static int WAR_CARDS_X;
   private static int START_BTM_BG;
   private static int PORTRAIT_Y;
   private static final int NUM_CARD_ROWS = 3;
   private static int xMin;
   private static int xMaxW;
   private static int BJPortX;
   private static int BJChipsX;
   private static int GuntherPortX;
   private static int GuntherChipsX;
   private static int chipsY;
   private static int CARD_TEXT_Y;
   private static int CARD_WAR_TEXT_Y;
   private static int curCardsW;
   private static int curCardsH;
   private static Image flakImg;
   private static boolean animationDone;
   private static int cardsDiscarded;
   private static int particleFrameTime;
   private static int numParticles;
   private static short[] particles;
   private static int numChipsLerping;
   private static int numChips;
   private static int[] chips;
   static final int BORDER_W = 88;
   static final int BORDER_H = 48;
   private static final int CHIP_W = 17;
   private static final int CHIP_H = 10;
   private static final int CHIP_PART_H = 6;
   private static final int PORTRAIT_WH = 27;
   private static final int FONT_W = 9;
   private static final int FONT_H = 12;
   private static final int MAX_LERPING = 3;
   public static final int DEF_SPEED = 256;
   private static final int PLAYER_CHIP = 1;
   private static final int DEALER_CHIP = 2;
   private static final int WAR_BET = 0;
   private static final int WAR_TOSS_CHIPS = 1;
   private static final int WAR_DEAL = 2;
   private static final int WAR_PLAYERCHOOSE = 3;
   private static final int WAR_DEALERCHOOSE = 4;
   private static final int WAR_RESOLVE = 5;
   private static final int WAR_REDEAL = 6;
   private static final int WAR_PLAYERDISCARD = 7;
   private static final int WAR_DEALERDISCARD = 8;
   private static final int WAR_PICKWARCARD = 9;
   private static final int WAR_RESOLVEFINAL = 10;
   private static final int WAR_MESSAGE = 11;
   private static final int PLAYER_TURN = 137;
   private static final int DEALER_TURN = 272;
   private static final int DEALER_MOVE_TIMEOUT = 750;

   public static final void initGame(int var0, ScriptThread var1, boolean var2) {
      numCardsLerping = 0;
      callingThread = var1;
      App.beginImageLoading();
      flakImg = App.loadImageFromIndex(5);
      imgOtherImgs = App.loadImageFromIndex(38);
      imgTable = App.loadImageFromIndex(39);
      App.endImageLoading();
      Canvas.setState(12);
      xMin = Canvas.screenRect[2] / 2 - 88;
      xMaxW = Math.min(176, Canvas.screenRect[2]);
      BJPortX = xMin + 1;
      BJChipsX = xMin + 27 + 3;
      GuntherPortX = xMin + xMaxW - 28;
      GuntherChipsX = xMin + xMaxW - 30 - 17;
      chipsY = Canvas.screenRect[3] - 10;
      Canvas.scrollWithBarMaxChars = (xMaxW - 10) / 7;
      PORTRAIT_Y = Canvas.displayRect[3] - 26;
      PORTRAIT_Y = (PORTRAIT_Y - 48) / 48 * 48;
      START_BTM_BG = PORTRAIT_Y - 48;
      CARDS_Y = (START_BTM_BG + 48) / 2 - 39;
      numParticles = 0;
      clearParticles();
      numChips = 0;
      numChipsLerping = 0;
      Canvas.clearSoftKeys();
      if (!var2) {
         playerTreasure = 100;
      } else {
         playerTreasure = Player.gold + Player.inventory[26] + Player.inventory[25] + Player.inventory[27];
      }

      if (playerTreasure > 10) {
         chipAmount = (playerTreasure << 8) / 10;
         dealerChips = 10;
         playerChips = 10;
      } else {
         playerChips = dealerChips = playerTreasure;
         chipAmount = 256;
      }

      setChips(playerChips, dealerChips);
      playerTreasure <<= 8;
      savePlayer = var2;
      startingChips = playerChips;
      aiTreasure = playerTreasure;
      CARDS_X = (Canvas.screenRect[2] - (17 * NUM_CARDS_W + (NUM_CARDS_W - 1))) / 2;
      if (CARDS_X <= 0) {
         NUM_CARDS_W = 7;
         CARDS_X = (Canvas.screenRect[2] - (17 * NUM_CARDS_W + (NUM_CARDS_W - 1))) / 2;
      }

      WAR_CARDS_X = (Canvas.screenRect[2] - 107) / 2;
      CARD_TEXT_Y = CARDS_Y + 78 + 4;
      CARD_WAR_TEXT_Y = CARDS_Y + 25 + 1 + 25 + 5;
      stateVars[3] = 1;
      updateBetPerc();
      numCardsInPlay = 0;
      resetHand();
      setState(0);
   }

   private static final void resetHand() {
      int var0;
      for(var0 = 0; var0 < 12; ++var0) {
         playersCards[var0] = -1;
      }

      for(var0 = 0; var0 < 12; ++var0) {
         dealersCards[var0] = -1;
      }

      numPlayerCards = 0;
      numDealerCards = 0;
   }

   public static final void endGame(int var0) {
      Game.scriptStateVars[7] = (short)var0;
      int var1 = var0 == 2 ? 5 : 10;
      if (savePlayer) {
         int var2 = Player.gold + Player.inventory[26] + Player.inventory[25] + Player.inventory[27];
         if ((playerTreasure & 128) != 0) {
            playerTreasure += 256;
         }

         playerTreasure >>= 8;
         byte var3 = -1;
         if (playerTreasure > var2) {
            var3 = 1;
         }

         for(boolean var5 = false; var2 != playerTreasure && !var5; var2 += var3) {
            int var4 = App.nextInt() % 4;
            if (!giveTreasure(var3, var4) && !giveTreasure(var3, 0)) {
               var5 = true;
            }
         }
      }

      App.beginImageUnload();
      imgOtherImgs = null;
      imgTable = null;
      flakImg = null;
      App.endImageUnload();
      if (callingThread != null) {
         Canvas.setState(3);
         callingThread.run();
         callingThread = null;
         Player.addXP(var1);
      } else {
         MenuSystem.setMenu(24);
      }

   }

   private static final boolean giveTreasure(int var0, int var1) {
      boolean var2 = false;
      short[] var10000;
      switch(var1) {
      case 0:
         if (Player.gold + var0 > 0) {
            Player.gold += var0;
            var2 = true;
         }
         break;
      case 1:
         if (Player.inventory[26] + var0 > 0) {
            var10000 = Player.inventory;
            var10000[26] = (short)(var10000[26] + var0);
            var2 = true;
         }
         break;
      case 2:
         if (Player.inventory[25] + var0 > 0) {
            var10000 = Player.inventory;
            var10000[25] = (short)(var10000[25] + var0);
            var2 = true;
         }
         break;
      case 3:
         if (Player.inventory[27] + var0 > 0) {
            var10000 = Player.inventory;
            var10000[27] = (short)(var10000[27] + var0);
            var2 = true;
         }
      }

      return var2;
   }

   private static final void nextState() {
      setState(stateVars[0] + 1);
   }

   private static final void prevState() {
      setState(stateVars[0] - 1);
   }

   private static final void setState(int var0) {
      int var1 = stateVars[0];
      stateVars[5] = 0;
      stateVars[1] = var1;
      stateVars[0] = var0;
      boolean var2 = !savePlayer || (Player.gamePlayedMask & 1) != 0;
      if (var0 == 0) {
         if (var2) {
            Canvas.setLeftSoftKey((short)1, (short)53);
         }

         Canvas.setRightSoftKey((short)1, (short)42);
      }

   }

   public static final void updateGame(Graphics var0) {
      updateWar(var0);
   }

   private static final void dealerPickCard() {
      int var0 = App.nextInt() % numCardsInPlay;

      int var1;
      for(var1 = var0; var1 >= 0; --var1) {
         if ((cardsInPlay[var1 * 8 + 5] & 1168) == 0) {
            stateVars[7] = var1;
            return;
         }
      }

      for(var1 = var0 + 1; var1 < numCardsInPlay; ++var1) {
         if ((cardsInPlay[var1 * 8 + 5] & 1168) == 0) {
            stateVars[7] = var1;
            return;
         }
      }

      App.Error(new Exception("Cannot find a card for the dealer in dealerPickCard"), 115);
   }

   private static final boolean dealerMoveSelector() {
      boolean var0 = false;
      int var1 = stateVars[6];
      int var2 = var1 % curCardsH;
      int var3 = var1 / curCardsH;
      int var4 = stateVars[7] % curCardsH;
      int var5 = stateVars[7] / curCardsH;
      if (var2 < var4) {
         ++var1;
      } else if (var2 > var4) {
         --var1;
      } else if (var3 < var5) {
         var1 += curCardsH;
      } else if (var3 > var5) {
         var1 -= curCardsH;
      } else {
         var0 = true;
      }

      stateVars[6] = var1;
      return var0;
   }

   public static final void handleInput(int var0, int var1) {
      handleWarInput(var0, var1);
   }

   private static final void dealFullDeck() {
      deckMask = 0L;
      numCardsInPlay = 0;
      boolean var0 = false;

      for(int var1 = 0; var1 < NUM_CARDS_W; ++var1) {
         for(int var2 = 0; var2 < NUM_CARDS_H; ++var2) {
            int var3;
            for(var3 = App.nextInt() % 51; ((long)(1 << var3) & deckMask) != 0L; var3 = (var3 + 1) % 51) {
            }

            deckMask |= (long)(1 << var3);
            dealCard(var3, xMin + xMaxW, Canvas.screenRect[3], CARDS_X + 18 * var1, CARDS_Y + 26 * var2, 64, 256);
         }
      }

      stateVars[6] = 1 + NUM_CARDS_W / 2 * NUM_CARDS_H;
      curCardsW = NUM_CARDS_W;
      curCardsH = NUM_CARDS_H;
   }

   private static final void dealWarHand() {
      deckMask = 0L;
      numCardsInPlay = 0;
      boolean var0 = false;
      int var1 = CARDS_Y + 25 + 1;

      for(int var2 = 0; var2 < 6; ++var2) {
         int var3;
         for(var3 = App.nextInt() % 51; ((long)(1 << var3) & deckMask) != 0L; var3 = (var3 + 1) % 51) {
         }

         deckMask |= (long)(1 << var3);
         dealCard(var3, xMin + xMaxW, Canvas.screenRect[3], WAR_CARDS_X + 18 * var2, var1, 64, 256);
      }

      stateVars[6] = 3;
      curCardsW = 6;
      curCardsH = 1;
   }

   private static final void reDealCard(int var0, int var1, int var2) {
      int[] var3 = cardsInPlay;
      deckMask &= (long)(~(1 << var3[var0 + 0]));

      int var4;
      for(var4 = App.nextInt() % 51; ((long)(1 << var4) & deckMask) != 0L; var4 = (var4 + 1) % 51) {
      }

      var3[var0 + 0] = var4;
      var3[var0 + 5] = var1;
      if ((var1 & 32) != 0) {
         var3[var0 + 6] = App.getUpTimeMs();
         ++numCardsLerping;
      } else if ((var1 & 64) != 0) {
         var3[var0 + 6] = -1;
         ++numCardsLerping;
      }

   }

   private static final void dealCard(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = numCardsInPlay * 8;
      cardsInPlay[var7 + 0] = var0;
      cardsInPlay[var7 + 1] = var1;
      cardsInPlay[var7 + 2] = var2;
      cardsInPlay[var7 + 3] = var3;
      cardsInPlay[var7 + 4] = var4;
      cardsInPlay[var7 + 5] = var5;
      int var8 = Math.abs(var3 - var1);
      int var9 = Math.abs(var4 - var2);
      if (var8 > var9) {
         cardsInPlay[var7 + 7] = (var8 << 15) / var6 >> 8;
      } else {
         cardsInPlay[var7 + 7] = (var9 << 15) / var6 >> 8;
      }

      if ((var5 & 32) != 0) {
         cardsInPlay[var7 + 6] = App.getUpTimeMs();
         ++numCardsLerping;
      } else if ((var5 & 64) != 0) {
         cardsInPlay[var7 + 6] = -1;
         ++numCardsLerping;
      }

      ++numCardsInPlay;
   }

   private static final void drawCardHud(Graphics var0) {
      Text var1 = Text.getSmallBuffer();
      Text.composeText((short)1, (short)150, var1);
      var0.drawString(var1, Canvas.SCR_CX, 5, 1);
      var1.dispose();
      var0.drawRegion(imgOtherImgs, 0, 0, 27, 27, BJPortX, PORTRAIT_Y);
      var0.drawRegion(imgOtherImgs, 27, 0, 27, 27, GuntherPortX, PORTRAIT_Y);
      int var2 = stateVars[0];
      if (numCardsLerping == 0) {
         if (var2 == 9) {
            if (numPlayerCards == 0) {
               var0.drawRegion(imgOtherImgs, 54, 0, 27, 27, BJPortX, PORTRAIT_Y);
            } else {
               var0.drawRegion(imgOtherImgs, 54, 0, 27, 27, GuntherPortX, PORTRAIT_Y);
            }
         } else if ((1 << var2 & 137) != 0) {
            var0.drawRegion(imgOtherImgs, 54, 0, 27, 27, BJPortX, PORTRAIT_Y);
         } else if ((1 << var2 & 272) != 0) {
            var0.drawRegion(imgOtherImgs, 54, 0, 27, 27, GuntherPortX, PORTRAIT_Y);
         }
      }

      drawChips(var0);
      if (stateVars[0] == 0) {
         byte var3 = 111;
         int var4 = stateVars[3];
         int var5 = chipsY - (stateVars[3] - 1) * 3 + 4;
         var0.drawRegion(Canvas.imgFont, (var3 & 15) * 9, ((var3 & 240) >> 4) * 12, 9, 12, BJChipsX + 17 + 1, var5 - 2);
         if (var4 == playerChips) {
            var0.drawRegion(imgOtherImgs, 98, 0, 17, 10, BJChipsX, var5 - 4);
         } else {
            var0.drawRegion(imgOtherImgs, 81, 16, 17, 6, BJChipsX, var5);
         }

         var0.drawRegion(Canvas.imgFont, (var3 & 15) * 9, ((var3 & 240) >> 4) * 12, 9, 12, GuntherChipsX - 10, var5 - 2, 0, 2);
         if (var4 == dealerChips) {
            var0.drawRegion(imgOtherImgs, 98, 0, 17, 10, GuntherChipsX, var5 - 4);
         } else {
            var0.drawRegion(imgOtherImgs, 81, 16, 17, 6, GuntherChipsX, var5);
         }
      }

   }

   private static final void drawChips(Graphics var0) {
      byte var1 = 81;
      boolean var2 = false;
      int var3 = App.getUpTimeMs();
      numChipsLerping = 0;

      for(int var4 = 0; var4 < numChips; ++var4) {
         int var9 = var4 * 7;
         if (chips[var9 + 6] > 0) {
            int var5 = var3 - chips[var9 + 5];
            if (var5 >= chips[var9 + 6]) {
               chips[var9 + 6] = 0;
               var0.drawRegion(imgOtherImgs, var1, 0, 17, 10, chips[var9 + 3], chips[var9 + 4]);
            } else {
               int var6 = (var5 << 16) / (chips[var9 + 6] << 8);
               int var7 = chips[var9 + 1] + (var6 * (chips[var9 + 3] - chips[var9 + 1] << 8) >> 16);
               int var8 = chips[var9 + 2] + (var6 * (chips[var9 + 4] - chips[var9 + 2] << 8) >> 16);
               var0.drawRegion(imgOtherImgs, var1, 0, 17, 10, var7, var8);
               ++numChipsLerping;
            }
         } else {
            var0.drawRegion(imgOtherImgs, var1, 0, 17, 10, chips[var9 + 3], chips[var9 + 4]);
         }
      }

   }

   private static final void drawCards(Graphics var0) {
      int var4 = 0;

      for(int var5 = 0; var5 < numCardsInPlay; ++var5) {
         int var3 = var5 * 8;
         int var6 = cardsInPlay[var3 + 5];
         if ((var6 & 128) != 0) {
            var0.fillRect(cardsInPlay[var3 + 3], cardsInPlay[var3 + 4], 17, 25, backgroundColor & -12632257);
         } else {
            int var1;
            int var2;
            if ((var6 & 96) != 0) {
               if (var4 == 3) {
                  continue;
               }

               int[] var7 = updateLerpingCard(var5);
               var1 = var7[0];
               var2 = var7[1];
               if ((var6 & 64) != 0) {
                  ++var4;
               }
            } else {
               var1 = cardsInPlay[var3 + 3];
               var2 = cardsInPlay[var3 + 4];
            }

            drawCard(var0, var1, var2, cardsInPlay[var3 + 0], var6);
         }
      }

   }

   private static final void drawCard(Graphics var0, int var1, int var2, int var3, int var4) {
      int var5 = var3 % 4;
      int var6 = var3 / 4;
      byte var8;
      if ((var4 & 16) != 0) {
         var0.drawRegion(imgOtherImgs, 0, 27, 17, 25, var1, var2);
         int var7 = 0;
         var8 = 0;
         if (var6 > 0 && var6 < 9) {
            var7 = 49 + var6 - 33;
         } else {
            switch(var6) {
            case 0:
               var7 = 32;
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            default:
               App.Error(new Exception("Invalid Card Number"), 113);
               break;
            case 9:
               var7 = 16;
               var8 = 15;
               break;
            case 10:
               var7 = 41;
               break;
            case 11:
               var7 = 48;
               break;
            case 12:
               var7 = 42;
            }
         }

         if (var8 != 0) {
            var0.drawRegion(Canvas.imgFont, (var7 & 15) * 9, ((var7 & 240) >> 4) * 12, 9, 12, var1 - 1, var2 + 2);
            var0.drawRegion(Canvas.imgFont, (var8 & 15) * 9, ((var8 & 240) >> 4) * 12, 9, 12, var1 + 6, var2 + 2);
         } else {
            var0.drawRegion(Canvas.imgFont, (var7 & 15) * 9, ((var7 & 240) >> 4) * 12, 9, 12, var1 + 3, var2 + 2);
         }

         var7 = 112 + var5;
         var0.drawRegion(Canvas.imgFont, (var7 & 15) * 9, ((var7 & 240) >> 4) * 12, 9, 12, var1 + 4, var2 + 12);
      } else {
         byte var10 = 17;
         var8 = 25;
         int var9 = 0;
         if ((var4 & 2) != 0) {
            var9 |= 5;
            var1 -= 4;
            var2 += 4;
         } else if ((var4 & 4) != 0) {
            var9 |= 3;
         } else if ((var4 & 8) != 0) {
            var9 |= 6;
            var1 -= 4;
            var2 += 4;
         }

         var0.drawRegion(imgOtherImgs, 17, 27, var10, var8, var1, var2, 0, var9);
      }

      if ((var4 & 256) != 0) {
         var0.drawRegion(imgOtherImgs, 51, 27, 17, 25, var1, var2);
      } else if ((var4 & 512) != 0) {
         var0.drawRegion(imgOtherImgs, 68, 27, 17, 25, var1, var2);
      }

      if ((var4 & 1024) != 0) {
         var0.drawRegion(imgOtherImgs, 85, 27, 17, 25, var1, var2);
      }

   }

   private static final void clearParticles() {
      for(int var0 = 0; var0 < 120; ++var0) {
         particles[var0] = 0;
      }

   }

   private static final void drawParticles(Graphics var0) {
      if (numParticles != 0) {
         int var1 = App.getUpTimeMs() << 8;
         int var2 = (var1 - particleFrameTime) / 1000;
         particleFrameTime = var1;
         var1 >>= 8;
         numParticles = 0;
         boolean var3 = false;

         for(int var4 = 0; var4 < 24; ++var4) {
            int var6 = var4 * 5;
            if (particles[var6 + 4] > 0 && updateParticle(var6, var2)) {
               int var5 = var1 / 100 % 4;
               if (particles[var6 + 2] > 0) {
                  var5 = var5 + 2 & 3;
               }

               drawCard(var0, particles[var6 + 0], particles[var6 + 1], 0, 1 << var5);
               ++numParticles;
            }
         }

      }
   }

   private static final boolean updateParticle(int var0, int var1) {
      short var2 = particles[var0 + 2];
      short var3 = particles[var0 + 3];
      short var4 = particles[var0 + 0];
      short var5 = particles[var0 + 1];
      int var6 = var3 + 10 * var1;
      int var7 = (var2 >> 8) + var4;
      int var8 = (var6 >> 8) + var5;
      if (var7 + 17 > 0 && var7 < xMin + xMaxW) {
         if (var8 + 25 > 0 && var8 < Canvas.screenRect[1] + Canvas.screenRect[3]) {
            particles[var0 + 2] = (short)var2;
            particles[var0 + 3] = (short)var6;
            particles[var0 + 0] = (short)var7;
            particles[var0 + 1] = (short)var8;
            return true;
         } else {
            particles[var0 + 4] = 0;
            return false;
         }
      } else {
         particles[var0 + 4] = 0;
         return false;
      }
   }

   private static final int[] updateLerpingCard(int var0) {
      int[] var1 = cardsInPlay;
      int var2 = var0 * 8;
      int[] var3 = new int[2];
      int var4 = var1[var2 + 1];
      int var5 = var1[var2 + 2];
      int var6 = var1[var2 + 3];
      int var7 = var1[var2 + 4];
      if (var1[var2 + 6] == -1) {
         var1[var2 + 6] = App.getUpTimeMs();
         var3[0] = var4;
         var3[1] = var5;
         return var3;
      } else {
         int var8 = App.getUpTimeMs() - var1[var2 + 6];
         if (var8 >= var1[var2 + 7]) {
            var3[0] = var6;
            var3[1] = var7;
            int[] var10000 = cardsInPlay;
            var10000[var2 + 5] &= -97;
            --numCardsLerping;
         } else {
            int var9 = (var8 << 16) / (var1[var2 + 7] << 8);
            var3[0] = var4 + (var9 * (var6 - var4 << 8) >> 16);
            var3[1] = var5 + (var9 * (var7 - var5 << 8) >> 16);
         }

         return var3;
      }
   }

   private static final void allocChip(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = numChips * 7;
      chips[var7 + 0] = var6;
      chips[var7 + 1] = var0;
      chips[var7 + 2] = var1;
      chips[var7 + 3] = var2;
      chips[var7 + 4] = var3;
      chips[var7 + 5] = var4;
      chips[var7 + 6] = var5;
      ++numChips;
   }

   private static final void setChips(int var0, int var1) {
      numChips = 0;
      int var2 = chipsY;

      int var3;
      for(var3 = 0; var3 < var0; ++var3) {
         allocChip(BJChipsX, var2, BJChipsX, var2, 0, 0, 1);
         var2 -= 3;
      }

      var2 = chipsY;

      for(var3 = 0; var3 < var1; ++var3) {
         allocChip(GuntherChipsX, var2, GuntherChipsX, var2, 0, 0, 2);
         var2 -= 3;
      }

   }

   public static final void drawPlayingBG(Graphics var0) {
      var0.fillRegion(imgTable, 0, 0, Canvas.displayRect[2], Canvas.displayRect[3] - 26);
   }

   private static final void updateWar(Graphics var0) {
      var0.clipRect(0, 0, Canvas.menuRect[2], Canvas.menuRect[3]);
      drawPlayingBG(var0);
      drawCardHud(var0);
      drawParticles(var0);
      switch(stateVars[0]) {
      case 0:
         WAR_BET_FUNC(var0);
         break;
      case 1:
         WAR_TOSS_CHIPS_FUNC();
         break;
      case 2:
         WAR_DEAL_FUNC();
         break;
      case 3:
         WAR_PLAYERCHOOSE_FUNC(var0);
         break;
      case 4:
         WAR_DEALERCHOOSE_FUNC(var0, stateVars[5]);
         break;
      case 5:
      case 10:
         WAR_RESOLVE_FUNC(var0, stateVars[5]);
         break;
      case 6:
         WAR_REDEAL_FUNC(var0, stateVars[5]);
         break;
      case 7:
         WAR_PLAYERDISCARD_FUNC(var0);
         break;
      case 8:
         WAR_DEALERDISCARD_FUNC(var0, stateVars[5]);
         break;
      case 9:
         WAR_PICKWARCARD_FUNC(var0, stateVars[5]);
         break;
      case 11:
         WAR_MESSAGE_FUNC(var0);
      }

      Canvas.staleView = true;
      var0.resetScreenSpace();
   }

   private static final void WAR_BET_FUNC(Graphics var0) {
      Text var1 = Text.getLargeBuffer();
      if (playerTreasure == 0) {
         stateVars[4] = 154;
         setState(11);
      } else {
         var1.setLength(0);
         Text.resetTextArgs();
         Text.addTextArg(betPercentage);
         Text.composeText((short)1, (short)187, var1);
         var1.wrapText(Canvas.scrollWithBarMaxChars);
         var0.drawString(var1, Canvas.SCR_CX, (START_BTM_BG + 48) / 2, 3);
         var1.dispose();
      }
   }

   private static final void WAR_TOSS_CHIPS_FUNC() {
      if (stateVars[5] == 0) {
         int var0 = BJChipsX + 17 + 9 + 6;
         int var1 = GuntherChipsX - 32;
         int var2 = var1 - var0;
         int var3 = chipsY - 30;
         byte var4 = 30;
         int var5 = playerChips - 1;
         int var6 = stateVars[3];
         int var10 = App.getUpTimeMs();

         short var7;
         int var8;
         int var9;
         for(var9 = 0; var9 < var6; ++var9) {
            var8 = var5 * 7;
            var7 = App.nextByte();
            chips[var8 + 3] = var0 + var7 % var2;
            chips[var8 + 4] = var3 + var7 % var4;
            chips[var8 + 5] = var10;
            chips[var8 + 6] = 200;
            --var5;
            ++numChipsLerping;
         }

         var5 = playerChips + dealerChips - 1;

         for(var9 = 0; var9 < var6; ++var9) {
            var8 = var5 * 7;
            var7 = App.nextByte();
            chips[var8 + 3] = var0 + var7 % var2;
            chips[var8 + 4] = var3 + var7 % var4;
            chips[var8 + 5] = var10;
            chips[var8 + 6] = 200;
            --var5;
            ++numChipsLerping;
         }

         int var10002 = stateVars[5]++;
      }

      if (numChipsLerping == 0) {
         nextState();
      }

   }

   private static final void WAR_DEAL_FUNC() {
      if (numCardsInPlay != 0 && numCardsInPlay >= 24) {
         int var0;
         for(var0 = 0; var0 < numPlayerCards; ++var0) {
            reDealCard(playersCards[var0], 64, 256);
         }

         for(var0 = 0; var0 < numDealerCards; ++var0) {
            reDealCard(dealersCards[var0], 64, 256);
         }

         stateVars[6] = 1 + NUM_CARDS_W / 2 * NUM_CARDS_H;
      } else {
         dealFullDeck();
      }

      resetHand();
      setState(3);
   }

   private static final void WAR_PLAYERCHOOSE_FUNC(Graphics var0) {
      Text var1 = Text.getSmallBuffer();
      drawCards(var0);
      if (numCardsLerping == 0) {
         var1.setLength(0);
         Text.composeText((short)1, (short)182, var1);
         var1.wrapText(Canvas.scrollWithBarMaxChars, 2, '\n');
         var0.drawString(var1, Canvas.SCR_CX, CARD_TEXT_Y, 1);
         drawSelector(var0);
      }

      var1.dispose();
   }

   private static final void WAR_PICKWARCARD_FUNC(Graphics var0, int var1) {
      Text var2 = Text.getSmallBuffer();
      drawCards(var0);
      if (numPlayerCards == 0 && numCardsLerping == 0) {
         var2.setLength(0);
         Text.composeText((short)1, (short)183, var2);
         var2.wrapText(Canvas.scrollWithBarMaxChars, 3, '\n');
         var0.drawString(var2, Canvas.SCR_CX, CARD_WAR_TEXT_Y, 1);
         drawSelector(var0);
      } else if (numPlayerCards == 1) {
         stateVars[5] = dealerAI(var1);
         var2.setLength(0);
         Text.composeText((short)1, (short)188, var2);
         var2.wrapText(Canvas.scrollWithBarMaxChars, 2, '\n');
         var0.drawString(var2, Canvas.SCR_CX, CARD_WAR_TEXT_Y, 1);
         drawSelector(var0);
      }

      var2.dispose();
   }

   private static final void WAR_DEALERCHOOSE_FUNC(Graphics var0, int var1) {
      drawCards(var0);
      Text var2 = Text.getSmallBuffer();
      stateVars[5] = dealerAI(var1);
      var2.setLength(0);
      Text.composeText((short)1, (short)188, var2);
      var2.wrapText(Canvas.scrollWithBarMaxChars, 2, '\n');
      var0.drawString(var2, Canvas.SCR_CX, CARD_TEXT_Y, 1);
      drawSelector(var0);
      var2.dispose();
   }

   private static final void WAR_DEALERDISCARD_FUNC(Graphics var0, int var1) {
      drawCards(var0);
      Text var2 = Text.getSmallBuffer();
      stateVars[5] = dealerAI(var1);
      var2.setLength(0);
      Text.composeText((short)1, (short)188, var2);
      var2.wrapText(Canvas.scrollWithBarMaxChars, 2, '\n');
      var0.drawString(var2, Canvas.SCR_CX, CARD_WAR_TEXT_Y, 1);
      drawSelector(var0);
      var2.dispose();
   }

   private static final void WAR_REDEAL_FUNC(Graphics var0, int var1) {
      drawCards(var0);
      int var10002;
      if (var1 == 0) {
         Text var2 = Text.getSmallBuffer();
         Text.composeText((short)1, (short)stateVars[4], var2);
         var2.dehyphenate();
         var0.drawString(var2, Canvas.SCR_CX, CARD_TEXT_Y, 1);
         var2.dispose();
      } else if (var1 == 1) {
         explodeCards();
         var10002 = stateVars[5]++;
         ++var1;
      }

      if (var1 == 2) {
         if (!animationDone) {
            int var3 = App.getUpTimeMs() - stateVars[2];
            int var4 = var3 / 200 & 3;
            if (var3 > 800) {
               animationDone = true;
            } else {
               Image var5 = flakImg;
               var0.drawRegion(var5, 64 * var4, 0, 64, 64, cardsInPlay[playersCards[0] + 3] - 37, cardsInPlay[playersCards[0] + 4] - 34);
               var0.drawRegion(var5, 64 * var4, 0, 64, 64, cardsInPlay[dealersCards[0] + 3] - 37, cardsInPlay[dealersCards[0] + 4] - 34);
            }
         }

         if (animationDone && numCardsLerping == 0 && numParticles == 0) {
            var10002 = stateVars[5]++;
         }
      } else if (var1 == 3) {
         cardsDiscarded = 0;
         resetHand();
         dealWarHand();
         var10002 = stateVars[5]++;
      } else if (var1 == 4 && numCardsLerping == 0) {
         setState(7);
      }

      if (var1 == 0) {
         Canvas.clearSoftKeys();
      }

   }

   private static final void WAR_PLAYERDISCARD_FUNC(Graphics var0) {
      drawCards(var0);
      if (numCardsLerping == 0) {
         Text var1 = Text.getSmallBuffer();
         var1.setLength(0);
         if (cardsDiscarded == 0) {
            Text.composeText((short)1, (short)184, var1);
         } else {
            Text.composeText((short)1, (short)185, var1);
         }

         var1.wrapText(Canvas.scrollWithBarMaxChars, 3, '\n');
         var0.drawString(var1, Canvas.SCR_CX, CARD_WAR_TEXT_Y, 1);
         drawSelector(var0);
         var1.dispose();
      }

   }

   private static final void WAR_RESOLVE_FUNC(Graphics var0, int var1) {
      drawCards(var0);
      int var3;
      int var5;
      int var6;
      if (var1 == 0) {
         stateVars[5] = 1;
         int var2 = cardsInPlay[playersCards[numPlayerCards - 1] + 0] / 4;
         var3 = cardsInPlay[dealersCards[numDealerCards - 1] + 0] / 4;
         byte var4 = 1;
         if (var2 == var3) {
            stateVars[4] = 151;
            var4 = 3;
         } else if (var2 != 0 && (var3 == 0 || var2 <= var3)) {
            stateVars[4] = 148;
         } else {
            stateVars[4] = 149;
            var4 = 2;
         }

         animationDone = false;
         if (var4 != 1 && var4 != 2) {
            setState(6);
            return;
         }

         var5 = stateVars[3];
         if (var4 == 1) {
            var5 = -var5;
         }

         if (savePlayer) {
            Player.gamePlayedMask |= 1;
         }

         playerTreasure += var5 * chipAmount;
         playerChips += var5;
         aiTreasure += -var5 * chipAmount;
         dealerChips += -var5;
         if (aiTreasure < 0) {
            aiTreasure = 0;
         }

         var6 = Math.min(playerChips, dealerChips);
         if (stateVars[3] > var6) {
            stateVars[3] = var6;
            updateBetPerc();
         }

         setChips(playerChips, dealerChips);
         stateVars[2] = App.getUpTimeMs();
         stateVars[5] = var4;
         backgroundColor = -16749526;
      }

      if (var1 != 3) {
         Text var11 = Text.getLargeBuffer();
         var11.setLength(0);
         Text.composeText((short)1, (short)stateVars[4], var11);
         var11.append('|');
         Text.composeText((short)1, (short)100, var11);
         var11.dehyphenate();
         var3 = CARD_TEXT_Y;
         if (numCardsInPlay == 6) {
            var3 = CARD_WAR_TEXT_Y;
         }

         var0.drawString(var11, Canvas.SCR_CX, var3, 1);
         var11.dispose();
         drawSelector(var0);
         if (!animationDone) {
            var5 = App.getUpTimeMs() - stateVars[2];
            var6 = var5 / 200 & 3;
            if (var5 > 800) {
               animationDone = true;
            } else {
               Image var7 = flakImg;
               byte var8 = 64;
               byte var9 = 64;
               int var10;
               if (stateVars[5] == 1) {
                  var10 = BJPortX + 13 - var8 / 2;
               } else {
                  var10 = GuntherPortX + 13 - var8 / 2;
               }

               var0.drawRegion(var7, 64 * var6, 0, var8, var9, var10, PORTRAIT_Y + 13 - var9 / 2);
            }
         }
      }

   }

   private static final void WAR_MESSAGE_FUNC(Graphics var0) {
      Text var1 = Text.getSmallBuffer();
      var1.setLength(0);
      Text.composeText((short)1, (short)stateVars[4], var1);
      Text.composeText((short)1, (short)50, var1);
      Text.composeText((short)1, (short)100, var1);
      var1.wrapText(Canvas.scrollWithBarMaxChars, 10, '\n');
      var0.drawString(var1, Canvas.SCR_CX, (START_BTM_BG + 48) / 2, 3);
      var1.dispose();
   }

   private static final void drawSelector(Graphics var0) {
      if (stateVars[0] != 6) {
         int var1 = stateVars[6] * 8;
         var0.drawRegion(imgOtherImgs, 34, 27, 17, 25, cardsInPlay[var1 + 3], cardsInPlay[var1 + 4]);
      }

   }

   private static final int dealerAI(int var0) {
      int var1 = var0;
      if (var0 == 0) {
         dealerPickCard();
         var1 = var0 + 1;
         stateVars[2] = App.getUpTimeMs() + 250;
      } else if (var0 == 1) {
         if (stateVars[2] <= App.getUpTimeMs()) {
            stateVars[2] = App.getUpTimeMs() + 250;
            if (stateVars[8] <= App.getUpTimeMs() && dealerMoveSelector()) {
               var1 = var0 + 1;
            }
         }
      } else if (var0 == 2) {
         handleWarInput(6, -5);
         var1 = 0;
      }

      return var1;
   }

   private static final void handleWarInput(int var0, int var1) {
      int var2 = stateVars[0];
      int var3 = App.getUpTimeMs();
      if (stateVars[8] <= var3) {
         boolean var4 = !savePlayer || (Player.gamePlayedMask & 1) != 0;
         if (var2 != 11 || var0 != 6 && var0 != 15) {
            if ((var2 == 5 || var2 == 10) && (var0 == 6 || var0 == 15)) {
               if (playerChips == 0) {
                  stateVars[4] = 153;
                  setState(11);
               } else if (dealerChips == 0) {
                  stateVars[4] = 152;
                  setState(11);
               } else {
                  setState(0);
               }
            } else {
               int[] var10000;
               int var10002;
               int var6;
               if (var2 == 6 && (var0 == 7 || var0 == 6 || var0 == 15)) {
                  if (stateVars[5] == 0) {
                     var10002 = stateVars[5]++;
                     stateVars[2] = var3;
                     backgroundColor = -10092544;
                  } else if (stateVars[5] == 4) {
                     for(var6 = 0; var6 < 24; ++var6) {
                        var10000 = cardsInPlay;
                        var10000[var6 * 8 + 5] &= -97;
                     }

                     numCardsLerping = 0;
                  }
               } else if (numCardsLerping != 0 && var2 == 3) {
                  for(var6 = 0; var6 < 24; ++var6) {
                     var10000 = cardsInPlay;
                     var10000[var6 * 8 + 5] &= -97;
                  }

                  numCardsLerping = 0;
               } else if ((var0 == 5 || var0 == 15) && var4 && var2 == 0) {
                  endGame(2);
               } else if (var0 != 6 && var0 != 15) {
                  if (var0 == 1) {
                     if (var2 == 0) {
                        var6 = stateVars[3] + 1;
                        if (var6 <= playerChips && var6 <= dealerChips) {
                           var10002 = stateVars[3]++;
                           updateBetPerc();
                        }
                     } else if ((var2 == 3 || var2 == 7 || var2 == 9) && stateVars[6] - 1 >= 0) {
                        var10002 = stateVars[6]--;
                     }
                  } else if (var0 == 2) {
                     if (var2 == 0) {
                        var6 = stateVars[3] - 1;
                        if (var6 >= 1) {
                           var10002 = stateVars[3]--;
                           updateBetPerc();
                        }
                     } else if ((var2 == 3 || var2 == 7 || var2 == 9) && stateVars[6] + 1 < numCardsInPlay) {
                        var10002 = stateVars[6]++;
                     }
                  } else if (var0 == 4 && (var2 == 3 || var2 == 7 || var2 == 9)) {
                     var6 = stateVars[6] / curCardsH;
                     ++var6;
                     if (var6 < curCardsW) {
                        var10000 = stateVars;
                        var10000[6] += curCardsH;
                     }
                  } else if (var0 == 3 && (var2 == 3 || var2 == 7 || var2 == 9)) {
                     var6 = stateVars[6] / curCardsH;
                     --var6;
                     if (var6 >= 0) {
                        var10000 = stateVars;
                        var10000[6] -= curCardsH;
                     }
                  } else if (var0 == 7 && var2 == 0) {
                     setState(1);
                     Canvas.clearSoftKeys();
                  }
               } else {
                  short var5 = (short)(stateVars[6] * 8);
                  if (var2 == 0) {
                     setState(1);
                     Canvas.clearSoftKeys();
                  } else if ((var2 == 3 || var2 == 4) && numCardsLerping == 0) {
                     if ((cardsInPlay[var5 + 5] & 768) != 0) {
                        return;
                     }

                     if (var2 == 3) {
                        playersCards[numPlayerCards++] = var5;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 272;
                        nextState();
                        stateVars[8] = var3 + 750;
                     } else {
                        dealersCards[numDealerCards++] = var5;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 528;
                        nextState();
                     }
                  } else if ((var2 == 7 || var2 == 8) && numCardsLerping == 0) {
                     if ((cardsInPlay[var5 + 5] & 1024) != 0) {
                        return;
                     }

                     if (var2 == 7) {
                        ++cardsDiscarded;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 1024;
                        nextState();
                        stateVars[8] = var3 + 750;
                     } else {
                        ++cardsDiscarded;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 1024;
                        if (cardsDiscarded < 4) {
                           prevState();
                        } else {
                           nextState();
                        }
                     }
                  } else if (var2 == 9) {
                     if ((cardsInPlay[var5 + 5] & 1024) != 0) {
                        return;
                     }

                     if (numPlayerCards == 0) {
                        playersCards[numPlayerCards++] = var5;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 272;
                        stateVars[8] = var3 + 750;
                     } else {
                        dealersCards[numDealerCards++] = var5;
                        var10000 = cardsInPlay;
                        var10000[var5 + 5] |= 528;
                        nextState();
                     }
                  }
               }
            }
         } else if (stateVars[4] == 152) {
            endGame(1);
         } else if (stateVars[4] == 153) {
            endGame(0);
         } else if (stateVars[4] == 154) {
            endGame(2);
         } else {
            setState(stateVars[1]);
         }

      }
   }

   private static final void updateBetPerc() {
      betPercentage = (stateVars[3] << 16) / startingChips * 100;
      if ((betPercentage & '耀') != 0) {
         betPercentage += 65536;
      }

      betPercentage >>= 16;
   }

   private static final void explodeCards() {
      int[] var0 = cardsInPlay;
      clearParticles();
      int var1 = App.getUpTimeMs() + 5 << 8;
      int var2 = cardsInPlay[playersCards[0] + 3];
      int var3 = cardsInPlay[playersCards[0] + 4];
      int var4 = cardsInPlay[dealersCards[0] + 3];
      int var5 = cardsInPlay[dealersCards[0] + 4];
      int[] var6 = new int[2];
      boolean var10 = false;

      for(int var11 = 0; var11 < numCardsInPlay; ++var11) {
         int var7 = var11 * 8;
         if ((var0[var7 + 5] & 144) == 0) {
            int var14 = numParticles * 5;
            ++numParticles;
            particles[var14 + 0] = (short)var0[var7 + 3];
            particles[var14 + 1] = (short)var0[var7 + 4];
            int var8 = var0[var7 + 3];
            int var9 = var0[var7 + 4];
            int var12 = (var2 - var8) * (var2 - var8) + (var3 - var9) * (var3 - var9);
            int var13 = (var4 - var8) * (var4 - var8) + (var5 - var9) * (var5 - var9);
            if (var12 <= var13) {
               Game.NormalizeVec(var0[var7 + 3] - var2, var0[var7 + 4] - var3, var6);
            } else {
               Game.NormalizeVec(var0[var7 + 3] - var4, var0[var7 + 4] - var5, var6);
            }

            particles[var14 + 2] = (short)(var6[0] * 2048 >> 8);
            particles[var14 + 3] = (short)(var6[1] * 2048 >> 8);
            particles[var14 + 4] = 1;
         }
      }

      particleFrameTime = var1;
      numCardsInPlay = 0;
   }

   static {
      stateVars = Canvas.stateVars;
      imgOtherImgs = null;
      imgTable = null;
      numPlayerCards = 0;
      numDealerCards = 0;
      playersCards = new short[12];
      dealersCards = new short[12];
      cardsInPlay = new int[192];
      numCardsInPlay = 0;
      deckMask = 0L;
      numCardsLerping = 0;
      backgroundColor = -16749526;
      callingThread = null;
      playerTreasure = 0;
      aiTreasure = 0;
      playerChips = 0;
      dealerChips = 0;
      startingChips = 0;
      chipAmount = 0;
      betPercentage = 0;
      CARDS_Y = 0;
      CARDS_X = 0;
      NUM_CARDS_W = 8;
      NUM_CARDS_H = 3;
      WAR_CARDS_X = 0;
      START_BTM_BG = 0;
      PORTRAIT_Y = 0;
      xMin = 0;
      xMaxW = 0;
      BJPortX = 0;
      BJChipsX = 0;
      GuntherPortX = 0;
      GuntherChipsX = 0;
      chipsY = 0;
      CARD_TEXT_Y = 0;
      CARD_WAR_TEXT_Y = 0;
      curCardsW = 0;
      curCardsH = 0;
      animationDone = false;
      cardsDiscarded = 0;
      particleFrameTime = 0;
      numParticles = 0;
      particles = new short[120];
      numChipsLerping = 0;
      numChips = 0;
      chips = new int[140];
   }
}
