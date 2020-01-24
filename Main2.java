
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;

public class Main2 {

	static final int FB_DAMAGE = 2; // FOR TESTING ONLY 
	
	// stuff to change the difficulty for each level
	// lvl1 
	static final int NUMBER_OF_SENTINELS = 5;
	// lvl2
	static final int NUMBER_OF_FIREBALLS = 15;
	
	// ALL STATIC VARIABLES
	public static int g;
	public static int portalX;
	public static int portalY;
	public static int checkX;
	public static int checkY;
	public static int randomSpawnX;
	public static int randomSpawnY;
	public static int switches;
	public static EZImage portal;
	static final int BLINK_ALIVE = 0;
	static final int BLINK_DIED = 1;
	static final int BLINK_DIED_3 = 10;
	static final int BLINK_WON_L1 = 2;
	static final int BLINK_WON_L2 = 9;
	static final int BLINK_DIED_2 = 3;
	static final int BLINK_WON_L3 = 12;
	public static int blinkState;
	public static int gameState;
	static final int PRE_GAME = 0;
	static final int PRE_GAME_2 = 2;
	static final int PRE_GAME_3 = 3;
	static final int PRE_GAME_4 = 4;
	static final int PRE_GAME_5 = 5;
	static final int LEVEL_1 = 1;
	static final int LEVEL_2 = 5;
	static final int LEVEL_3 = 7;
	static final int INTERMITTENT = 6;
	static final int INTERMITTENT_2 = 15;
	static final int INTERMITTENT_3 = 20;
	public static long Time;
	public static long sbegin;
	public static int z;
	public static EZSound portal2;

	public static int CHECKER;
	public static EZImage r;
	public static boolean stepped;
	public static int sentCaught;

	public static FileReader fR;
	public static Scanner fS;
	public static int width;
	public static int height;
	public static String skip;
	public static int screenW;
	public static int screenH;
	public static int counter;
	public static EZImage[] wall = new EZImage[1000];
	public static Sentinel[] bugs = new Sentinel[10];
	public static EZImage[] fireball = new EZImage[1000];
	public static EZImage[] bg = new EZImage[1000];
	public static EZImage[] bg2 = new EZImage[1000];
	public static int[] rsX = new int[1000];
	public static int[] rsY = new int[1000];
	public static Blink myBlink;
	public static EZElement radius;
	public static EZSound odin;
	public static EZSound end;
	public static EZSound fire;
	public static EZImage attack;
	public static int enCounter;
	
	
	// FUNCTION THAT READS THE TEXT FILE TO DETERMINE SCREEN DIMENSIONS
	public static void initialize() throws java.io.IOException {
		// Sets up FileReader and Scanner to read background.txt
		fR = new FileReader("background.txt");
		fS = new Scanner(fR);
		// Variables in which to store the info in background.txt
		width = fS.nextInt();
		height = fS.nextInt();
		skip = fS.nextLine();
		// Initialize the screen to width*32 x height*32
		// Set the background color to black
		EZ.initialize(width * 32, height * 32);
		EZ.setBackgroundColor(new Color(0, 0, 0));
		// Store the screen dimensions in variables screenW for width
		// and screenH for height
		screenW = width * 32;
		screenH = height * 32;
		// Print the screen dimensions
		System.out.println("Screen Dimensions: " + screenW + " x " + screenH);
	}

	// FUNCTION THAT DRAWS THE WALLS ONTO THE SCREEN
	public static void makeWalls() {
		counter = 0;
		for (int row = 0; row < height; row++) {
			skip = fS.nextLine();
			System.out.println(skip);
			for (int column = 0; column < width; column++) {
				char ch = skip.charAt(column); // Retrieve the character at position "column" of the inputText.
				if (ch == '#') {
					wall[counter] = EZ.addImage("block.jpg", column * 32 + 10, row * 32 + 10);
					counter++;
				}
			}
		}
		System.out.println("Number of walls generated: " + counter);
	}

	// LOADING SCREEN FUNCTION FOR BEFORE THE GAME (1)
	public static void loadingScreen() {
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "Sidhe's Labyrinth", Color.WHITE, 50);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "next", Color.WHITE, 20);
		while (gameState == PRE_GAME) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState = PRE_GAME_2;
				}
			}
			EZ.refreshScreen();
		}
	}

	// LOADING SCREEN FUNCTION FOR BEFOR THE GAME (2)
	public static void loadingScreen3() {
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 60, "You are a pixie.", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30,
				"The death god Arawn has transported you to the realm of the Sidhe,", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2,
				"a mystical elf-like people, to rid it of the banshees tormenting it.", Color.WHITE, 30);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "next", Color.WHITE, 20);
		while (gameState == PRE_GAME_2) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState = PRE_GAME_3;
				}
			}
			EZ.refreshScreen();
		}
	}

	// LOADING SCREEN FUNCTION FOR BEFORE THE GAME (3)
	public static void loadingScreen4() {
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 60, "To control your character, use the WASD keys.",
				Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30,
				"You can use the space bar to create portals to banish the banshees", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2, "to the underworld, but pixie magic isn't always reliable.",
				Color.WHITE, 30);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "next", Color.WHITE, 20);
		while (gameState == PRE_GAME_3) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState = PRE_GAME_4;
				}
			}
			EZ.refreshScreen();
		}
	}

	// LOADING SCREEN FUNCTION FOR BEFORE THE GAME (4)
	public static void loadingScreen2() {
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 60,
				"If you cannot stop the banshees, they will invade the overworld", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "and bring an end to all the pixies.", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2, "The fate of the pixies and the Sidhe depend on you.",
				Color.WHITE, 30);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "Press space to begin.", Color.WHITE, 20);
		while (gameState == PRE_GAME_4) {
			if (EZInteraction.wasKeyPressed(KeyEvent.VK_SPACE)) {
				System.out.println("!!!");
				// FIND ME 
				// gameState = LEVEL_1;
				
				// BELOW FOR TESTING PURPOSES
				// gameState = INTERMITTENT;
				// blinkState = BLINK_WON_L1;
				gameState = LEVEL_2;
				// gameState = LEVEL_3;
			}
			EZ.refreshScreen();
		}
	}

	// FUNCTION THAT SPAWNS THE SENTINELS IN A RANDOM PLACE ON THE SCREEN
	public static void spawnSent() {
		Random rG = new Random();
		for (int g = 0; g < NUMBER_OF_SENTINELS; g++) {
			// move this outside the for loop to have them spawn at the same place
			randomSpawnX = rG.nextInt(screenW - 64) + 32;
			randomSpawnY = rG.nextInt(screenH - 64) + 32;
			bugs[g] = new Sentinel("mosnter.png", "bug.png", randomSpawnX, randomSpawnY, screenW, screenH);
		}

	}

	// FUNCTION THAT SPAWNS BLINK AT 100,00
	public static void spawnBlink() {
		myBlink = new Blink("pixie.png", 100, 100);
		radius = EZ.addCircle(myBlink.getX(), myBlink.getY(), 500, 500, Color.BLACK, false);
	}

	// SECOND FUNCTION THAT SPAWNS BLINK AT A RANDOM PLACE ON THE SCREEN
	public static void spawnBlink2() {
		myBlink = new Blink("pixie.png", screenW / 2, screenH / 2);
		radius = EZ.addCircle(myBlink.getX(), myBlink.getY(), 500, 500, Color.BLACK, false);
	}

	// FUNCTION THAT DRAWS A RADIUS AROUND BLINK
	public static void blinkRadius() {
		radius.translateTo(myBlink.getX(), myBlink.getY());
		radius.pushToBack();
	}

	// FUNCTION USED FOR TESTING TO GET THE X, Y POSITION OF WHATEVER CLICKED ON
	public static void clickForPos() {
		if (EZInteraction.wasMouseLeftButtonPressed()) {
			System.out.println("(" + EZInteraction.getXMouse() + " , " + EZInteraction.getYMouse() + ")");
		}
	}

	// FUNCTION THAT CONTROLS BLINK'S MOVEMENT AND PREVENTS HER FROM WALKING INTO THE WALLS
	public static void controlBlink() {
		// blink cannot move forward if she will step into a wall
		stepped = false;
		for (int i = 0; i < counter; i++) {
			if (wall[i].isPointInElement(myBlink.getX(), myBlink.getY() - 30) && EZInteraction.isKeyDown('w')) {
				stepped = true;
			} if (wall[i].isPointInElement(myBlink.getX(), myBlink.getY() + 30) && EZInteraction.isKeyDown('s')) {
				stepped = true;
			} if (wall[i].isPointInElement(myBlink.getX() + 30, myBlink.getY()) && EZInteraction.isKeyDown('d')) {
				stepped = true;
			} if (wall[i].isPointInElement(myBlink.getX() - 30, myBlink.getY()) && EZInteraction.isKeyDown('a')) {
				stepped = true;
			}
		}
		// if blink is not going to step into a wall, she moves forward according to the key pressed
		if (EZInteraction.isKeyDown('w') && stepped == false) {
			myBlink.moveUp(5);
		} else if (EZInteraction.isKeyDown('s') && stepped == false) {
			myBlink.moveDown(5);
		} else if (EZInteraction.isKeyDown('a') && stepped == false) {
			myBlink.moveLeft(5);
		} else if (EZInteraction.isKeyDown('d') && stepped == false) {
			myBlink.moveRight(5);
		}
	}

	// SIMPLE FUNCTION TO CONTROL BLINK WITHOUT CHECKING WALLS
	public static void controlBlink2() {
		if (EZInteraction.isKeyDown('w')) {
			myBlink.moveUp(10);
		} else if (EZInteraction.isKeyDown('s')) {
			myBlink.moveDown(10);
		} else if (EZInteraction.isKeyDown('a')) {
			myBlink.moveLeft(10);
		} else if (EZInteraction.isKeyDown('d')) {
			myBlink.moveRight(10);
		}
	}

	// FUNCTION THAT CONTROLS THE MOVEMENT OF THE SENTINELS
	public static void controlBugs() {
		CHECKER = 0;
		// makes bugs stick in the area where they are caught
		for (int q = 0; q < NUMBER_OF_SENTINELS; q++) {
			checkX = bugs[q].getX();
			checkY = bugs[q].getY();
			int c1 = bugs[q].getX();
			int c2 = bugs[q].getY();
			if (c1 >= 384 && c1 < 704 && c2 >= 512 && c2 < 712) {
				bugs[q].stick();
				CHECKER++;
			}
			// if the number of sentinels caught is equal to the number of sentinels
			// aka blink has caught all sentinels on screen,
			// blink wins level 1
			if (CHECKER >= NUMBER_OF_SENTINELS) {
				blinkState = BLINK_WON_L1;
				gameState = INTERMITTENT;
			}
			// makes sentinels move
			bugs[q].go();
			// if blink's radius touches the sentinels, they chase her
			if (radius.isPointInElement(bugs[q].getX(), bugs[q].getY())) {
				bugs[q].setDestination(myBlink.getX(), myBlink.getY());
			}
			// prevents the sentinels from walking into the walls
			for (int i = 0; i < counter; i++) {
				if (wall[i].isPointInElement(checkX + 20, checkY)) {
					bugs[q].moveLeft(2);
					bugs[q].setRandomDirection();
				} if (wall[i].isPointInElement(checkX - 20, checkY)) {
					bugs[q].moveRight(2);
					bugs[q].setRandomDirection();
				} if (wall[i].isPointInElement(checkX, checkY + 20)) {
					bugs[q].moveUp(2);
					bugs[q].setRandomDirection();
				} if (wall[i].isPointInElement(checkX, checkY-20)) {
					bugs[q].moveDown(2);
					bugs[q].setRandomDirection();
				}
			}
			// if sentinels touch blink's portal they teleport to a random place
			if (bugs[q].isInside(portalX, portalY)) {
				bugs[q].teleport();
			}
			// if sentinels catch blink, blink dies
			if (bugs[q].isInside(myBlink.getX(), myBlink.getY())) {
				blinkState = BLINK_DIED;
			}
		}
	}
	
	
	// FUNCTION TO MAKE A PORTAL WHEN SPACE IS PRESSED
	public static void makePortal() {
		if (EZInteraction.wasKeyReleased(KeyEvent.VK_SPACE) && switches == 0) {
			portal = EZ.addImage("portal.png", (myBlink.getX() + 60), (myBlink.getY()));
			portal2.play();
			portal.pushToBack();
			portalX = myBlink.getX();
			portalY = myBlink.getY();
			switches = 1;
		}

		if (EZInteraction.wasKeyReleased(KeyEvent.VK_SPACE) && switches == 1) {
			EZ.removeEZElement(portal);
			portal = EZ.addImage("portal.png", (myBlink.getX() + 60), (myBlink.getY()));
			portal2.play();
			portal.pushToBack();
			portalX = myBlink.getX();
			portalY = myBlink.getY();

		}
	}
	
	
	
	// FUNCTION FOR CONTROLLING THE PORTAL
	public static void portalCtrl() {
		// if blink steps in the portal, teleports her to a random spot r1, r2
		if (portal.isPointInElement(myBlink.getX(), myBlink.getY())) {
			Random rG = new Random();
			int r1 = rG.nextInt(screenW - 32);
			int r2 = rG.nextInt(screenH - 32);
			// if the random spot is inside a wall, it comes up with a new spot
			for (int i = 0; i < counter; i++) {
				if (wall[i].isPointInElement(r1, r2)) {
					r1 = rG.nextInt(screenW - 32);
					r2 = rG.nextInt(screenH - 32);
				}
			}
			// if the random spot is inside a sentinel, it comes up with a new spot
			for (int i = 0; i < NUMBER_OF_SENTINELS; i++) {
				if (bugs[i].isInside(r1, r2)) {
					r1 = rG.nextInt(screenW - 32);
					r2 = rG.nextInt(screenH - 32);
				}
			}
			myBlink.setPosition(r1, r2);
		}
	}
	
	// LOADING SCREEN IF LEVEL 1 IS WON
	public static void BL1_INT() {
		EZ.removeAllEZElements();
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "All the banshees have been banished.", Color.WHITE,
				50);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50,
				"Arawn would like you to visit the second level of the Sidhe's realm, where the basilisk lives.", Color.WHITE, 20);
		while (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState = INTERMITTENT_2;
				}
			}
			EZ.refreshScreen();
		}
	}
	
	// LOADING SCREEN IF LEVEL 1 IS WON (2)
	public static void BL1_INT2() {
		EZ.removeAllEZElements();
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 60, "To control your character, use the WASD keys.",
				Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30,
				"Your goal is to rescue the Sidhe. Each Sidhe will give you gratitude if you rescue them.", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2, "Make sure to avoid the basilisk's fireballs, as they will decrease your health.",
				Color.WHITE, 30);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "next", Color.WHITE, 20);
		while (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT_2) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState=INTERMITTENT_3;
				}
			}
			EZ.refreshScreen();
		}
	}
	
	// LOADING SCREEN IF LEVEL 1 IS WON (3)
	public static void BL1_INT3() {
		EZ.removeAllEZElements();
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 60, "Your main goal is to maximize Sidhe gratitude",
				Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30,
				"before the basilisk runs out of breath.", Color.WHITE, 30);
		EZ.addText("old.TTF", screenW / 2, screenH / 2, "If you fail, the basilisk will escape to the overworld.",
				Color.WHITE, 30);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "begin", Color.WHITE, 20);
		while (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT_3) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState=LEVEL_2;
				}
			}
			EZ.refreshScreen();
		}
	}
	
	// FUNCTION THAT GENERATES THE LOADING SCREEN AFTER BEATING LEVEL 2
	public static void BL2_INT() {
		EZ.removeAllEZElements();
		EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "The basilisk "
				+ " has run out of breath.", Color.WHITE,
				50);
		EZText og = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50,
				"Thanks to your help, the Sidhe had the confidence to slay it.", Color.WHITE, 20);
		EZText af = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 100,
				// "The Sidhe implore you to visit the deepest part of the labyrinth.", Color.WHITE, 20);
				"You have saved the Sidhe, but your portal magic has gone awry!", Color.WHITE, 20);
		EZText aq = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 150,
				// "The Sidhe implore you to visit the deepest part of the labyrinth.", Color.WHITE, 20);
				"Use the space bar to jump and try to collect teleporting orbs along the way.", Color.WHITE, 20);
		EZText at = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 200,
				// "The Sidhe implore you to visit the deepest part of the labyrinth.", Color.WHITE, 20);
				"Collect 5 teleportation orbs and avoid all portals to escape the labyrinth.", Color.WHITE, 20);
		// EZText az = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 200,
				// "The Sidhe implore you to visit the deepest part of the labyrinth.", Color.WHITE, 20);
				// "Objective: make it out of Sidhe's realm alive. Avoid your portals.", Color.WHITE, 20);
		EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 250, "next", Color.WHITE, 20);
		EZText decline = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 1000, "Decline?", Color.BLACK, 20);
		while (blinkState == BLINK_WON_L2 && gameState == INTERMITTENT) {
			if (EZInteraction.wasMouseLeftButtonReleased()) {
				if (begin.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					System.out.println("!!!");
					gameState = LEVEL_3;
				}
				if (decline.isPointInElement(EZInteraction.getXMouse(), EZInteraction.getYMouse())) {
					EZ.removeAllEZElements();
					EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "What a shame.", Color.WHITE, 50);
				}
			}
			EZ.refreshScreen();
		}

	}
	
	// FUNCTION THAT CONTROLS LEVEL 2
	public static void levelTwo() {
		blinkState = BLINK_ALIVE;
		// makes fiery sound in bg
		fire = EZ.addSound("fire.wav");
		fire.loop();
		int x = 0;
		Time = System.currentTimeMillis();
		// clear everything
		EZ.removeAllEZElements();
		int capture = 0;
		// adds text at top right 
		EZText score = EZ.addText(900, 28, "SIDHE'S GRATITUDE", Color.WHITE);
		EZText health = EZ.addText(900, 78, "HEALTH", Color.WHITE);
		// variables to easily change size of rectangles
		int rectX = 900;
		int rectY = 100;
		int rect2X = 850;
		int rect2Y = 50;
		int rect2W = 0;
		int rect2H = 20;
		int rectW = 100;
		int rectH = 20;
		// adds rectangles at top right
		EZRectangle qu = EZ.addRectangle(rectX, rectY, rectW, rectH, Color.green, true);
		EZRectangle qr = EZ.addRectangle(rectX, rectY, rectW, rectH, Color.red, false);
		EZRectangle zu = EZ.addRectangle(rect2X, rect2Y, rect2W, rect2H, Color.cyan, true);
		EZRectangle zr = EZ.addRectangle(900, rect2Y, 100, rect2H, Color.red, false);
		EZImage monster = EZ.addImage("Monster.png", screenW / 2, 0);
		Random rG = new Random();
		// make an array of fireballs
		Fireball[] myFire = new Fireball[NUMBER_OF_FIREBALLS];
		int clickX = screenW / 2;
		int clickY = screenH / 2;
		int dragonX = 0;
		int dragonY = 0;
		int directionX = 5;
		spawnBlink2();
		for (int g = 0; g < NUMBER_OF_FIREBALLS; g++) {
			rsX[g] = -40 + rG.nextInt(screenW + 40);
			rsY[g] = rG.nextInt(screenH);
			myFire[g] = new Fireball("fireball.png", 0, 0, 5);
		}

		int randomX = rG.nextInt(screenW);
		int randomY = rG.nextInt(screenH);

		EZImage Sidhe = EZ.addImage("elf.png", randomX, randomY);

		z = 0;
		while (blinkState == BLINK_ALIVE) {
			// makes sidhe walk randomly so you have to catch them
			int newRandomX = rG.nextInt(screenW);
			int newRandomY = rG.nextInt(screenH);
			int newRandomX2 = rG.nextInt(screenW);
			int newRandomY2 = rG.nextInt(screenH);
			if (randomX == newRandomX) {
				newRandomX = rG.nextInt(screenW);
			} if (randomY == newRandomY) {
				newRandomY = rG.nextInt(screenH);
			} if (randomX < newRandomX) {
				Sidhe.translateTo(randomX += 5, randomY);
			} if (randomX > newRandomX) {
				Sidhe.translateTo(randomX -= 5, randomY);
			} if (randomY < newRandomY) {
				Sidhe.translateTo(randomX, randomY += 5);
			} if (randomY > newRandomY) {
				Sidhe.translateTo(randomX, randomY -= 5);
			} 
			
			// if you catch the sidhe, increment capture variable
			// and make the bar that tracks sidhe's gratitude go up
			if (Sidhe.isPointInElement(myBlink.getX(), myBlink.getY())) {
				EZ.removeEZElement(Sidhe);
				capture++;
				int k = 2;
				//rectH = rectH - k;
				//rectY = rectY - k / 2;
				// qu.setHeight(rectH);
				rect2W = rect2W + k;
				rect2X = rect2X + k/2;
				zu.setWidth(rect2W);
				zu.translateTo(rect2X, rect2Y);
				// score.setMsg("Sidhe's gratitude: " + capture);
				sbegin = System.currentTimeMillis();
				z = 2;
			}

			// wait two seconds and then spawn another sidhe
			if (System.currentTimeMillis() - sbegin >= 2000 && z == 2) {
				int lm = rG.nextInt(screenW);
				int lq = rG.nextInt(screenH);
				Sidhe = EZ.addImage("elf.png", lm, lq);
				z = 1;
			}
			
			// functions also used in level 1
			controlBlink2();
			clickForPos();
			
			// makes basilisk move back and forth across the top
			// of the screen
			monster.translateTo(dragonX, dragonY);
			dragonX = dragonX + directionX;
			if (dragonX > screenW) {
				directionX = -directionX;
			}
			if (dragonX < 0) {
				directionX = -directionX;
			}

			// long TimeNow = System.currentTimeMillis();
			// if (TimeNow - Time >= 3000) {
			
			// generates a specified number of fireballs (specified earlier in the code)
			// and makes them move based on a random number generator that yields 1-13
			// so they are unpredictable to the player
			for (int g = 0; g < NUMBER_OF_FIREBALLS; g++) {
				int v = rG.nextInt(13) + 1;
				if (myFire[g].getX() > rsX[g]) {
					myFire[g].moveLeft2(v);
				}
				if (myFire[g].getX() < rsX[g]) {
					myFire[g].moveRight2(v);
				}
				// if (myFire[g].getY() > screenH + 40) {
				// myFire[g].moveUp2(v);
				// }
				if (myFire[g].getY() < screenH + 40) {
					myFire[g].moveDown2(v);
				}
				// if the fireballs go offscreen, they will be 
				// spit back out again from the mouth of the basilisk
				if (myFire[g].getY() > screenH + 30) {
					myFire[g].setPosition(dragonX - 53, dragonY + 60);
				}

				
				// if blink touches a fireball, makes her healthbar decrease
				// so the rectangle goes down.
				if (myFire[g].isInside(myBlink.getX(), myBlink.getY())) {
					System.out.println("CHECKING FIRE");
					int k = 2;
					//rectH = rectH - k;
					//rectY = rectY - k / 2;
					// qu.setHeight(rectH);
					
					rectW = rectW - FB_DAMAGE;
					rectX = rectX - FB_DAMAGE/2;
					qu.setWidth(rectW);
					qu.translateTo(rectX, rectY);
				}

				// if blink's health reaches 0, she dies and the level ends
				if (rectW <= 0) {
					blinkState = BLINK_DIED_2;
				}

				// if sidhe's gratitude reaches 50 or greater, blink wins level 2
				if (capture >= 50) {
					gameState = INTERMITTENT;
					blinkState = BLINK_WON_L2;
				}
			}
			
			EZ.refreshScreen();
		}
	}
	
	// FUNCTION THAT CONTROLS LEVEL 3
	public static void levelThree() {
		boolean top = false;
		enCounter = 0;
		blinkState = BLINK_ALIVE;
		EZ.removeAllEZElements();


		// adds the wallpaper to the background
		for (int i = 0; i < 20; i++) {
			bg[i] = EZ.addImage("fire.jpg", 640 * i, 0);
		}

		for (int i = 0; i < 20; i++) {
			bg2[i] = EZ.addImage("fire.jpg", 640 * i, 480);
		}
		
		// adds the orbs to random places on the screen at only height screenH - 100
		Random rG = new Random();
		int lm = rG.nextInt(screenW);
		r = EZ.addImage("orb.png", lm, screenH - 100);

		// creates Blink
		Blink2 myHero = new Blink2(100, screenH - 100);

		
		sbegin = System.currentTimeMillis();
		z = 0;
		int d = 0;
		int lg = rG.nextInt(screenW * 2) + 500;
		int attackX = rG.nextInt(screenW) + 500;
		int attackY = screenH - 100;
		int attackDir = -3;
		attack = EZ.addImage("portal.png", lg, screenH - 100);

		while (blinkState == BLINK_ALIVE) {
			// used the hero code to make blink jump
			myHero.processStates();
			
			// add a new orb every 2 seconds or if the last orb was caught
			if (System.currentTimeMillis() - sbegin >= 2000 && z == 2 || z == 2) {
				lm = rG.nextInt(screenW * 2) + 150;
				r = EZ.addImage("orb.png", lm, screenH - 100);
				z = 0;

			}

			// portal moves from the right to the left of the screen
			attack.translateTo(attackX, screenH - 100);
			attackX = attackX + attackDir;

			if (System.currentTimeMillis() - sbegin >= 5000 && d == 0) {
				lm = rG.nextInt(screenW);
			}

			if (attackX <= -1000) {
				attackX = lg;
			}

			// if the portal touches blink, she dies
			if (attack.isPointInElement(myHero.getX(), myHero.getY())) {
				blinkState = BLINK_DIED_3;
			}

			
			// TO GIVE THE ILLUSION OF WALKING THROUGH SOME PLACE: 
			
			// if you press d, the entire background moves back
			if (EZInteraction.isKeyDown('d')) {
				for (int i = 0; i < 20; i++) {
					bg[i].moveForward(-3);
					bg2[i].moveForward(-3);
				}
				r.moveForward(-2);

			}

			// if you press a, the entire background moves forward
			if (EZInteraction.isKeyDown('a')) {
				for (int i = 0; i < 20; i++) {
					bg[i].moveForward(3);
					bg2[i].moveForward(3);
				}
				r.moveForward(2);

			}
			
			
			// code makes orbs hover in place
			int rmax = screenH -120;
			int rmin = screenH  -80;
			if (r.getYCenter() > rmax && top == false) {
				r.yCenter = r.yCenter - 1;
			} if (r.getYCenter() == rmax && top == false) {
				top = true;
			} if (r.getYCenter() < rmin && top == true) {
				r.yCenter = r.yCenter +1;
			} if (r.getYCenter() == rmin && top == true) {
				top = false;
				System.out.println("x");
			}
			
			// if hero catches orb, z=2 will generate a new orb using the code
			// right under processStates
			// enCounter variable tracks how many times hero has caught orb
			if (r.isPointInElement(myHero.getX(), myHero.getY())) {
				enCounter++;
				EZ.removeEZElement(r);
				z = 2;
			}
			
			// if hero catches four orbs, they win level 3
			// and therefore the game
			if (enCounter >=4) {
				gameState = INTERMITTENT;
				blinkState = BLINK_WON_L3;
			}
			EZ.refreshScreen();
		}
	}

	
	
	
	// MAIN PROGRAM
	public static void main(String[] args) throws java.io.IOException {
		portal2 = EZ.addSound("portal.wav");
		odin = EZ.addSound("otrodin.wav");
		end = EZ.addSound("growl.wav");
		odin.play();

		gameState = PRE_GAME;
		// LOADING SCREENS
		if (gameState == PRE_GAME) {
			initialize();
			loadingScreen();
		} if (gameState == PRE_GAME_2) {
			EZ.removeAllEZElements();
			loadingScreen3();
		} if (gameState == PRE_GAME_3) {
			EZ.removeAllEZElements();
			loadingScreen4();
		} if (gameState == PRE_GAME_4) {
			EZ.removeAllEZElements();
			loadingScreen2();
		}
		
		// LEVEL ONE
		if (gameState == LEVEL_1) {
			blinkState = BLINK_ALIVE;
			EZ.removeAllEZElements();
			makeWalls();
			spawnSent();
			spawnBlink();
			g = 4;
			switches = 0;
			// runs only while blink is alive
			// if blink dies, it will end the level
			while (blinkState == BLINK_ALIVE) {
				blinkRadius();
				clickForPos();
				 controlBugs();
				controlBlink();
				makePortal();
				portalCtrl();
				EZ.refreshScreen();
			} 
		}
		

		// LOADING SCREENS BETWEEN LEVEL 1 AND 2
		if (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT) {
			BL1_INT();
		} if (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT_2) {
			BL1_INT2();
		} if (blinkState == BLINK_WON_L1 && gameState == INTERMITTENT_3) {
			BL1_INT3();
		} 
		
		// LEVEL TWO
		if (gameState == LEVEL_2) {
			levelTwo();
		} 
		
		// LOADING SCREEN BETWEEN LEVEL 2 AND 3
		if (blinkState == BLINK_WON_L2 && gameState == INTERMITTENT) {
			BL2_INT();
		}
		
		// LEVEL THREE
		if (gameState == LEVEL_3) {
			levelThree();

		}
		
		// LOADING SCREEN IF YOU WIN THE GAME
		if (blinkState == BLINK_WON_L3 && gameState == INTERMITTENT) {
			EZ.removeAllEZElements();
			EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "You did it! You escaped Sidhe's Labyrinth.", Color.WHITE,
					50);
		}

		// LOADING SCREEN IF YOU DIED IN LEVEL 1
		if (blinkState == BLINK_DIED) {
			odin.stop();
			end.play();
			EZ.removeAllEZElements();
			EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "You've been devoured by a banshee.", Color.WHITE, 50);
			EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "Better luck next time.", Color.WHITE,
					20);
		}

		// LOADING SCREEN IF YOU DIED IN LEVEL 2
		if (blinkState == BLINK_DIED_2) {
			end.play();
			EZ.removeAllEZElements();
			EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "You burned to death.", Color.WHITE, 50);
			EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "Better luck next time.", Color.WHITE,
					20);
		}
		
		// LOADING SCREEN IF YOU DIED IN LEVEL 3
		if (blinkState == BLINK_DIED_3) {
			EZ.removeAllEZElements();
			EZ.addText("old.TTF", screenW / 2, screenH / 2 - 30, "You were swallowed by your own portal.", Color.WHITE, 50);
			EZText begin = EZ.addText("old.TTF", screenW / 2, screenH / 2 + 50, "Better luck next time.", Color.WHITE,
					20);
		}

	} // main program

} // class