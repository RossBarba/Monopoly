package mono;
import java.util.Random;
import java.util.Scanner;

public class Monopoly {
static int dice1, dice2;
static player[] playerList;
static property[] allProperties = new property[29];
static property[] availableProperties = allProperties;
static String[] communityChestDescriptions = new String[16];
static String[] chanceDescriptions = new String[16];
static String[] monopolyPieces = new String[8];
static String[] spaceNames = new String[40];

//this method will get player amount, their respective information, and determine who goes first
static player startGame(){ //returns the player that will be going first
	boolean validPlayerNum = false;
	int numPlayers = 0;
	while (!validPlayerNum){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of players (2-8): ");
		numPlayers = sc.nextInt();
		if (numPlayers >= 2 && numPlayers <= 8) {
			validPlayerNum = true;
		} else System.out.println("Please enter valid number of players.");
	} 

	
	playerList = new player[numPlayers]; //create a list with the length of the number of players
	Monopoly m = new Monopoly();
	for (int i = 0; i < playerList.length; i++) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter player name: ");
		playerList[i] = m.new player();
		playerList[i].playerName = sc.nextLine(); //player enters their name 
		boolean validPiece = false;
		
		while (!validPiece) { //player keeps choosing a piece until they select a valid piece that is not already taken
			System.out.print(playerList[i].playerName + ", what piece would you like to be? (battleship, car, penguin, top hat, duck, dog, cat, dinosaur) ");
			String pieceChoice = sc.nextLine();
			
			for (int x = 0; x < monopolyPieces.length; x++) { //matches input against every element in piece array
				if (pieceChoice.equals(monopolyPieces[x])) { //player input is in the list
					playerList[i].piece = pieceChoice; //make that the player piece
					monopolyPieces[x] = null; //set the corresponding element to null so no other players may use it
					validPiece = true;
					break; //end infinite loop
				}
		
			}
			if (!validPiece) System.out.println("Piece is either chosen or does not exist.");
		}
		
	}
	//decide who goes first
	int[] playerRolls = new int[playerList.length];
	
	for (int i = 0; i < playerList.length; i++) { //all the players roll the dice
		System.out.println(playerList[i].playerName + " is rolling" );
		rollDice();
		playerRolls[i] = dice1 + dice2;
		System.out.println(playerList[i].playerName + " rolled a " + playerRolls[i]);
	}
	
	int maxRollerIndex = 0;
	int maxRoll = 0;
	for (int i = 0; i < playerList.length; i++) {
		if (playerRolls[i] > maxRoll) {
			maxRoll = playerRolls[i];
			maxRollerIndex = i;
		}
	}
	
	System.out.println(playerList[maxRollerIndex].playerName + " had the highest roll of " + maxRoll + ". They will be first.");
	return playerList[maxRollerIndex];
}


 //What actions can you take in a turn? 
//rolling the dice to get out of jail or move
//bartering with players
//property management (buying houses/hotels, mortgaging)
static void game(player currentPlayer) {
	Scanner sc = new Scanner(System.in);
	while(currentPlayer.turn) {
		System.out.println(currentPlayer.playerName +", what action would you like to take? (roll, barter, property management)");
		String command = sc.nextLine();
		switch (command) {
		case "roll": roll(currentPlayer); break;
		case "barter": barter(currentPlayer); break;
		case "property management": propertyManagement(currentPlayer); break;
		default: System.out.println("invalid command.");
 		}
	}
}

//for this method we will have to memorize the board numbers of the non property spaces:
//Go: 0
//Community Chest: 2, 33
//Chance: 7, 22, 36
//Income tax: 4
//Luxury tax: 38
//just visiting/Jail: 10
//go to jail: 30
//free parking: 20
static void roll(player player) { 
	
}

//Players are able to negotiate for different items (properties, money, cards) and can barter with those same items
static void barter(player player) {
	
}

//players can buy hotels/houses and/or mortgage properties
static void propertyManagement(player player) {
	
}

static void rollDice() {
	dice1 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
	dice2 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
}
void useGetOutOfJailFree (player player) { 
	player.inJail = false;
	player.JailFreeCards -= 1;
}

void passGo(player player) {
	player.moneyBalance += 200;
}

//community chest deck
void drawCommunityChest (player player) {
	Random rand = new Random();
	int card = rand.nextInt();
	
	switch (card) {
	case 0:
	case 1: 
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
	case 14:
	case 15:
	}
}

//chance deck
void drawChance (player player) {
	Random rand = new Random();
	int card = rand.nextInt(16);
	
	switch (card) {
	case 0:
	case 1: 
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
	case 14:
	case 15:
	}
}

	class player {
		String playerName;
		int moneyBalance = 1500;
		String piece;
		boolean turn = false;
		boolean inJail = false;
		property[] ownedProperties;
		int jailCounter = 0;
		int boardPosition = 0;
		int JailFreeCards = 0; //need to write up the ability to sell these
	}
	
	public static class property {
		String propertyName;
		int boardPosition;
		boolean owned = false;
		int buyPrice;
		int mortgagePrice = buyPrice / 2;
		int rent;
		String type;
		boolean mortgaged = false;
		
		
		property(String name, int position, int bPrice, int rent, String type) {
			propertyName = name;
			boardPosition = position;
			buyPrice = bPrice;
			this.rent = rent;
			this.type = type;
		}
	}
	
	public static class street extends property {
		int numHouses = 0;
		boolean hotel = false;
		int housePrice;
		
		//Creating a constructor for subclass
		street(String name, int position, int bPrice, int rent, String type, int hPrice) {
			super(name, position, bPrice, rent, type);
			propertyName = name;
			boardPosition = position;
			buyPrice = bPrice;
			this.rent = rent;
			this.type = type;
			housePrice = hPrice;
		}
		
	}
	
	
	// Going to list all of the methods for cards here
	class cardMethods { 
		void addMoney(player player, int amount) { //chance uses this too
			player.moneyBalance += amount;
		}
		void subtractMoney(player player, int amount) { //chance uses this too
			player.moneyBalance -= amount;
		}
		void goToJail (player player) { //chance uses this too
			player.inJail = true;
		}
		void getGetOutOfJailFreeCard (player player) {
			player.JailFreeCards += 1;
		}
		void payPerImprovement(player player, int houseAmount, int hotelAmount) { //chance uses this too
			
		}
		void goToBoardPosition (player player) { //chance uses this too
			
		}
		void collectFromPlayers(player player, int amount) {
			
		}
		void payEachPlayer(player player) {
			
		}
		void advanceToNextRailroad (player player) {
			
		}
		void advanceToNearestUtility (player player) {
			
		}
	}
	
	public static void main(String[] args) {		
		// creating the array of all properties
		//brown properties
		allProperties[0] = new street("Mediterranean Avenue", 1, 60, 2, "Brown", 50);
		allProperties[1] = new street("Baltic Avenue", 3, 60, 4, "Brown", 50);
		//first rail
		allProperties[2] = new property("Reading Railroad", 5, 200, 25, "Railroad");
		//light blue
		allProperties[3] = new street("Oriental Avenue", 6, 100, 6, "Light Blue", 50);
		allProperties[4] = new street("Vermont Avenue", 8, 100, 6, "Light Blue", 50);
		allProperties[5] = new street("Conneticut Avenue", 9, 120, 8, "Light Blue", 50);
		//first pink
		allProperties[6] = new street("St. Charles Place", 11, 140, 10, "Pink", 100);
		//first utility
		allProperties[7] = new property("Electric Company", 12, 150, dice1 + dice2, "Utility");
		//rest of pink
		allProperties[8] = new street("States Avenue", 13, 140, 10, "Pink", 100);
		allProperties[9] = new street("Virginia Avenue", 14, 160, 12, "Pink", 100);
		//second rail
		allProperties[10] = new property("Pennsylvania Railroad", 15, 200, 25, "Railroad");
		//orange properties
		allProperties[11] = new street("St. James Place", 16, 180, 14, "Orange", 100);
		allProperties[12] = new street("Tenessee Avenue", 18, 180, 14, "Orange", 100);
		allProperties[13] = new street("New York Avenue", 19, 200, 16, "Orange", 100);
		//red properties
		allProperties[14] = new street("Kentucky Avenue", 21, 220, 18, "Red", 150);
		allProperties[15] = new street("Indiana Avenue", 23, 220, 18, "Red", 150);
		allProperties[17] = new street("Illinois Avenue", 24, 240, 20, "Red", 150);
		//third rail (don't step on it!)
		allProperties[18] = new property("B. & O. Railroad", 25, 200, 25, "Railroad");
		//first two yellow
		allProperties[19] = new street("Atlantic Avenue", 26, 260, 22, "Yellow", 150);
		allProperties[20] = new street("Ventor Avenue", 27, 260, 22, "Yellow", 150);
		//second utility
		allProperties[21] = new property("Water Works", 28, 150, dice1 + dice2, "Utility");
		//last yellow
		allProperties[22] = new street("Marvin Gardens", 29, 280, 24, "Yellow", 150);
		//green properties
		allProperties[23] = new street("Pacific Avenue", 31, 300, 26, "Green", 200);
		allProperties[24] = new street("North Carolina Avenue", 32, 300, 26, "Green", 200);
		allProperties[25] = new street("Pennsylvania Avenue", 34, 320, 28, "Green", 200);
		//last rail
		allProperties[26] = new property("Short Line", 35, 200, 25, "Railroad");
		//finally dark blue
		allProperties[27] = new street("Park Place", 37, 350, 35, "Dark Blue", 200);
		allProperties[28] = new street("Boardwalk", 39, 400, 50, "Dark Blue", 200);
		
		//creation of community chest card descriptions
		communityChestDescriptions[0] = "You set aside time every week to hand out with your elderly neighbor; you've heard some amazing stories!\nCOLLECT $100.";
		communityChestDescriptions[1] = "You organize a group to clean up your town's walking path.\nCOLLECT $50.";
		communityChestDescriptions[2] = "Blasting music late at night? Your neighbors do not approve.\nGO TO JAIL. GO DIRECTLY TO JAIL. DO NOT PASS GO. DO NOT COLLECT $200.";
		communityChestDescriptions[3] = "You go to the local school fundraiser, but you forget to close your windows!\nPAY $100";
		communityChestDescriptions[4] = "You help your neighbor bring in her groceries. She makes you lunch to say thanks!\nCOLLECT $20";
		communityChestDescriptions[5] = "You volunteer at a blood drive. There are free cookies!\nCOLLECT $10";
		communityChestDescriptions[6] = "You rescue a puppy, and you feel rescued, too!\nGET OUT OF JAIL FREE\nThis card may be kept until needed, traded, or sold.";
		communityChestDescriptions[7] = "You organize a block party so people on your street can get to know each other.\nCOLLECT $10 FROM EACH PLAYER.";
		communityChestDescriptions[8] = "Just when you think you can't go another step, you finish the foot race and raise money for your local hospital!\nADVANCE TO GO. COLLECT $200.";
		communityChestDescriptions[9] = "You buy a few bags of cookies from that school bake sale. Yum!\nPAY $50.";
		communityChestDescriptions[10] = "You organize a bake sale for your local school.\nCOLLECT $25.";
		communityChestDescriptions[11] = "You spend the day playing games with kids at a local children's hospital.\nCOLLECT $100.";
		communityChestDescriptions[12] = "Your fuzzy friends at the animal shelter will be thankful for your donation.\nPAY $50.";
		communityChestDescriptions[13] = "You help build a new school playground, then you get to test the slide!\nCOLLECT $100.";
		communityChestDescriptions[14] = "You should have volunteered for that home improvement project, you would have learned valuable skills!\nFOR EACH HOUSE YOU OWN, PAY $40.\nFOR EACH HOTEL YOU OWN, PAY $115";
		communityChestDescriptions[15] = "You help yuor neighbors clean up their yards after a big storm.\nCOLLECT $200.";
		
		//creation of chance cards descriptions
		chanceDescriptions[0] = "ADVANCE TO THE NEAREST UTILITY.\nIf UNOWNED, you may buy it from the Bank.\nIf OWNED, roll the dice, and pay the owner 10 times your roll.\nIF YOU PASS GO, COLLECT $200.";
		chanceDescriptions[1] = "ADVANCE TO THE NEAREST RAILROAD.\nIf UNOWNED, you may buy it from the bank.\nIf OWNED, pay the owner twice the rent to which they are otherwise entitled.\nIF YOU PASS GO, COLLECT $200.";
		chanceDescriptions[2] = "YOU HAVE BEEN ELECTED CHAIRPERSON OF THE BOARD.\nPAY EACH PLAYER $50.";
		chanceDescriptions[3] = "GO TO JAIL. GO DIRECTLY TO JAIL.\nDO NOT PASS GO. DO NOT COLLECT $200.";
		chanceDescriptions[4] = communityChestDescriptions[6];
		chanceDescriptions[5] = chanceDescriptions[1];
		chanceDescriptions[6] = "BANK PAYS YOU DIVIDEND OF $50.";
		chanceDescriptions[7] = "YOUR BUILDING LOAN MATURES.\nCOLLECT $150.";
		chanceDescriptions[8] = "GO BACK THREE SPACES";
		chanceDescriptions[9] = "ADVANCE TO ILLINOIS AVENUE.\nIF YOU PASS GO, COLLECT $200.";
		chanceDescriptions[10] = "ADVANCE TO ST. CHARLES PLACE.\nIF YOU PASS GO, COLLECT $200";
		chanceDescriptions[11] = "SPEEDING FINE.\nPAY $15";
		chanceDescriptions[12] = "ADVANCE TO BOARDWALK.";
		chanceDescriptions[13] = "ADVANCE TO GO.\nCOLLECT $200.";
		chanceDescriptions[14] = "TAKE A TRIP TO READING RAILROAD.\nIF YOU PASS GO, COLLECT $200.";
		chanceDescriptions[15] = "MAKE GENERAL REPAIRS ON ALL YOUR PROPERTY:\nFOR EACH HOUSE, PAY $50.\nFOR EACH HOTEL, PAY $100.";
		
		//creation of monopoly pieces
		monopolyPieces[0] = "battleship";
		monopolyPieces[1] = "car";
		monopolyPieces[2] = "penguin";
		monopolyPieces[3] = "top hat";
		monopolyPieces[4] = "duck";
		monopolyPieces[5] = "dog";
		monopolyPieces[6] = "cat";
		monopolyPieces[7] = "dinosaur";
	
		//start of the program
		player firstPlayer = startGame();
		game(firstPlayer);
	
	}
}
/*
void playerTurn(player player, player nextPlayer) {
	while(player.turn) {
		//player rolls the dice and moves on board
		player.rollDice();
		player.boardPosition = player.boardPosition  + dice1 + dice2;
		
		// lands on go to jail
		if (player.boardPosition == 30) {
			player.inJail = true;
		}
		
		//if you roll doubles, go again
		if (dice1 == dice2) {
			player.turn = true;
		} else {
			player.turn = false;
			nextPlayer.turn = true;
		}
	}
}
*/
