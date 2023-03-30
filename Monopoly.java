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
static boolean gameon; 

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
	currentPlayer.rollTurn = true;
	currentPlayer.turn=true;
	while(currentPlayer.turn) {
		System.out.println(currentPlayer.playerName +", what action would you like to take? (roll, barter, property management)");
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		switch (command) {
		case "roll": if(currentPlayer.rollTurn == true) {
			roll(currentPlayer); 
		} else {
			System.out.println("Already exhausted rolls for the turn"); 
		}
			break;
		case "barter": barter(currentPlayer); break;
		case "property management": propertyManagement(currentPlayer); break;
		case "end turn": if(currentPlayer.rollTurn==true) System.out.println("Please roll before ending your turn"); 
			else currentPlayer.turn = false; break;
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
	int doubletracker = 0;
	
	while(player.rollTurn){
	dice1 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
	System.out.println("Dice one is " + dice1);
	dice2 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
	System.out.println("Dice two is " + dice2);
	int movement = dice1+dice2;
	
	boolean tempjailtrack = false;
	
	if(doubletracker == 3){
		player.boardPosition = 10;
		player.inJail = true; 
		player.rollTurn = false;
		break;
	}
	
	if(player.inJail == false){
		player.boardPosition += movement;
		tempjailtrack = true;
	}
	if(player.boardPosition > 39){
		player.moneyBalance += 200;
		player.boardPosition -= 40;
	}
	else if(player.inJail == true){
		if(dice1==dice2){
			player.boardPosition = 10 + movement;
			player.inJail = false;
			player.jailCounter = 0;
		}
		//will also need to add a condtional statement to query 
				//users for get out of free cards
				else if(player.JailFreeCards >= 1){
				Scanner sc = new Scanner(System.in);
				String command = sc.nextLine();
				switch(command){
				case "Yes": player.inJail=false;
				player.boardPosition = 10 + movement;
				player.jailCounter = 0;
				}
				}
		else if (player.jailCounter < 3){
			//do nothing if neither condition of being freed from jail is met
		}
		else if (player.jailCounter == 3){
			player.inJail = false;
			player.moneyBalance -= 50;
			player.boardPosition = 10 + movement;
			player.jailCounter = 0; 
		}
	}
	switch(player.boardPosition){
	case 0: System.out.println("You are at board position " + player.boardPosition + ", Pass Go, collect $200!"); break;
	case 1: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[0].propertyName); 
		if(allProperties[0].owned==false){
			//first, need to allow user to buy property if desired, then allow them to auction the property if not
			System.out.println("Would you like to buy this property for $" + allProperties[0].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[0]);
				break;
			case "No": 
				auctionProperty(allProperties[0]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
		else{
			 payRent(player, allProperties[0].owner, allProperties[0].rent); 
		} break;
	case 2: System.out.println("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now."); 
	drawCommunityChest(player); break;
	case 3: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[1].propertyName); 
		if(allProperties[1].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[1].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[1]);
			break;
		case "No": 
			auctionProperty(allProperties[1]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[1].owner, allProperties[1].rent); 
	} break;
	case 4: System.out.println("You are at board position " + player.boardPosition + ", income tax, pay $200!"); player.moneyBalance -= 200; break;
	case 5: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[2].propertyName); 
	if(allProperties[2].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[2].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[2]);
			break;
		case "No": 
			auctionProperty(allProperties[2]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[2].owner, allProperties[2].rent); 
	} break;
	case 6: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[3].propertyName); 
	if(allProperties[3].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[3].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[3]);
			break;
		case "No": 
			auctionProperty(allProperties[3]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[3].owner, allProperties[3].rent); 
	} break;
	case 7: System.out.println("You are at board position " + player.boardPosition + ", chance, drawing a chance card now."); 
	drawChance(player); break;
	case 8: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[4].propertyName); 
	if(allProperties[4].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[4].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[4]);
			break;
		case "No": 
			auctionProperty(allProperties[4]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[4].owner, allProperties[4].rent); 
	} break;
	case 9: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[5].propertyName); 
	if(allProperties[5].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[5].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[5]);
			break;
		case "No": 
			auctionProperty(allProperties[5]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[5].owner, allProperties[5].rent); 
	} break;
	case 10: if(player.inJail == false) System.out.println("You are just visiting jail"); break;
	case 11: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[6].propertyName); 
	if(allProperties[6].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[6].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[6]);
			break;
		case "No": 
			auctionProperty(allProperties[6]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[6].owner, allProperties[6].rent); 
	} break;
	case 12: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[7].propertyName); if(allProperties[7].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[7].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[7]);
			break;
		case "No": 
			auctionProperty(allProperties[7]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[7].owner, allProperties[7].rent); 
	} break;
	case 13: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[8].propertyName); 
	if(allProperties[8].owned==false){
		System.out.println("Would you like to buy this property for $" + allProperties[8].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[8]);
			break;
		case "No": 
			auctionProperty(allProperties[8]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[8].owner, allProperties[8].rent); 
	} break;
	case 14: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[9].propertyName); 
	if(allProperties[9].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[9].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[9]);
			break;
		case "No": 
			auctionProperty(allProperties[0]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[0].owner, allProperties[9].rent); 
	} break;
	case 15: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[10].propertyName); 
	if(allProperties[10].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[10].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[10]);
			break;
		case "No": 
			auctionProperty(allProperties[10]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[10].owner, allProperties[10].rent); 
	} break;
	case 16: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[11].propertyName); 
	if(allProperties[11].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[11].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[11]);
			break;
		case "No": 
			auctionProperty(allProperties[11]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[11].owner, allProperties[11].rent); 
	} break;
	case 17: System.out.println("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now."); 
	drawCommunityChest(player); break;
	case 18: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[12].propertyName); 
	if(allProperties[12].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[12].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[12]);
			break;
		case "No": 
			auctionProperty(allProperties[12]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[12].owner, allProperties[12].rent); 
	} break;
	case 19: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[13].propertyName); 
	if(allProperties[13].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[13].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[13]);
			break;
		case "No": 
			auctionProperty(allProperties[13]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[13].owner, allProperties[13].rent); 
	} break;
	case 20: System.out.println("You are at board position " + player.boardPosition + ", free parking, enjoy your stay!"); break;
	case 21: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[14].propertyName); 
	if(allProperties[14].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[14].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[14]);
			break;
		case "No": 
			auctionProperty(allProperties[14]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[14].owner, allProperties[14].rent); 
	} break;
	case 22: System.out.println("You are at board position " + player.boardPosition + ", chance, drawing a chance card now."); 
	drawChance(player); break;
	case 23: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[15].propertyName); 
	if(allProperties[15].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[15].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[15]);
			break;
		case "No": 
			auctionProperty(allProperties[15]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[15].owner, allProperties[15].rent); 
	} break;
	case 24: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[16].propertyName); 
	if(allProperties[16].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[16].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[16]);
			break;
		case "No": 
			auctionProperty(allProperties[16]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[16].owner, allProperties[16].rent); 
	} break;
	case 25: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[17].propertyName); 
	if(allProperties[17].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[17].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[17]);
			break;
		case "No": 
			auctionProperty(allProperties[17]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[17].owner, allProperties[17].rent); 
	} break;
	case 26: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[18].propertyName); 
	if(allProperties[18].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[18].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[18]);
			break;
		case "No": 
			auctionProperty(allProperties[18]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[18].owner, allProperties[18].rent); 
	} break;
	case 27: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[19].propertyName); 
	if(allProperties[19].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[19].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[19]);
			break;
		case "No": 
			auctionProperty(allProperties[19]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[19].owner, allProperties[19].rent); 
	} break;
	case 28: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[20].propertyName); 
	if(allProperties[20].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[20].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[20]);
			break;
		case "No": 
			auctionProperty(allProperties[20]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[20].owner, allProperties[20].rent); 
	} break;
	case 29: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[21].propertyName); 
	if(allProperties[21].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[21].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[21]);
			break;
		case "No": 
			auctionProperty(allProperties[21]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[21].owner, allProperties[21].rent); 
	} break;
	case 30: player.inJail = true; System.out.println("You landed on go to jail, go to jail immediately!"); break;
	case 31: System.out.println("You are at board position " + player.boardPosition + " " + allProperties[22].propertyName); 
	if(allProperties[22].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[22].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[22]);
			break;
		case "No": 
			auctionProperty(allProperties[22]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[22].owner, allProperties[22].rent); 
	} break;
	case 32: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[23].propertyName); 
	if(allProperties[23].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[23].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[23]);
			break;
		case "No": 
			auctionProperty(allProperties[23]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[23].owner, allProperties[23].rent); 
	} break;
	case 33: System.out.println("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now."); 
	drawCommunityChest(player); break;
	case 34: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[24].propertyName); 
	if(allProperties[24].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[24].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[24]);
			break;
		case "No": 
			auctionProperty(allProperties[24]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[24].owner, allProperties[24].rent); 
	} break;
	case 35: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[25].propertyName); 
	if(allProperties[25].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[25].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[25]);
			break;
		case "No": 
			auctionProperty(allProperties[25]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[25].owner, allProperties[25].rent); 
	} break;
	case 36: System.out.println("You are at board position " + player.boardPosition + ", chance, drawing a chance card now."); 
	drawChance(player); break;
	case 37: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[26].propertyName); 
	if(allProperties[26].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[26].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[26]);
			break;
		case "No": 
			auctionProperty(allProperties[26]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[26].owner, allProperties[26].rent); 
	} break;
	case 38: System.out.println("You are at board position " + player.boardPosition + ", luxury tax. Pay $100!"); player.moneyBalance-=100; break;
	case 39: System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[27].propertyName); 
	if(allProperties[27].owned==false){
		//first, need to allow user to buy property if desired, then allow them to auction the property if not
		System.out.println("Would you like to buy this property for $" + allProperties[27].buyPrice);
		Scanner sc = new Scanner(System.in);
		String command = sc.next();
		switch(command){
		case "Yes": 
			buyProperty(player, allProperties[27]);
			break;
		case "No": 
			auctionProperty(allProperties[27]); break;
		default: System.out.println("Please enter a valid command");
		}
	}
	else{
		 payRent(player, allProperties[27].owner, allProperties[27].rent); 
	} break;
	}
	if(player.inJail==true){
		player.boardPosition = 10;
		player.jailCounter +=1;
		int temp = player.jailCounter - 1;
		int temp2 = 3 -temp;
		System.out.println("You are in jail! You have rolled " + temp + " times in jail. You have " + temp2 + " rolls remaining in jail.");
	}
	if(dice1==dice2 && tempjailtrack == true){
		doubletracker += 1;
		continue;
	}
	System.out.println(player.playerName + " has exhaused all rolls at board space " + player.boardPosition + " with a money balance of $" + player.moneyBalance);
	doubletracker = 0;
	player.rollTurn = false;
	}
}

//Players are able to negotiate for different items (properties, money, cards) and can barter with those same items
static void barter(player player) {
	
}

//players can buy hotels/houses and/or mortgage properties
static void propertyManagement(player player) {
	
}

static void buyProperty(player player, property property){
	property.owned = true;
	property.owner = player;
	player.moneyBalance -= property.buyPrice;
	//prints out the property bought and now account balance
	System.out.println(player.playerName + " has bought " + property.propertyName + " for $" + property.buyPrice + " and now has an account balance of " + player.moneyBalance);
	//add the property to player's owned property's array
	if(player.ownedProperties.length == 0 || player.ownedProperties == null){
		property [] temp = new property[1];
		temp[0] = property;
		player.ownedProperties = temp;
	}
	else{
		property [] temp = new property[player.ownedProperties.length + 1];
		for (int i = 0; i < player.ownedProperties.length; i++){
			temp[i] = player.ownedProperties[i];
		}
		temp[player.ownedProperties.length] = property;
		player.ownedProperties = temp;
	}
	//prints out the owned properties
	System.out.print(player.playerName + " now owns the following properties: ");
	for(int a = 0; a < player.ownedProperties.length; a++){
		if(a == player.ownedProperties.length -1){
			System.out.print(player.ownedProperties[a].propertyName + "\n");
		}
		else{
			System.out.print(player.ownedProperties[a].propertyName + ", ");
		}
	}
}

static void auctionProperty(property property){
	System.out.println("You have opted not to buy the property and the property will go to auction! Beginning auction now!");
	boolean tempvar = true;
	int tempprice = 0;
	player templeader = null;
	
	while(tempvar){
		int counter = 0;
		for(int i = 0; i < playerList.length; i++){
			Scanner sc = new Scanner(System.in);
			System.out.println(playerList[i].playerName + " would you like to bid on " + property.propertyName + "? The current bid is " + tempprice);
			String command = sc.nextLine();
			switch(command){
			case "Yes": 
				System.out.println("What would you like your bid to be?");
				int bidamount = sc.nextInt(); 
				if(bidamount > tempprice){
					templeader = playerList[i];
					tempprice = bidamount; 
					counter = 1;
				}
				else{
					System.out.println("Your bid was not high enough to out bid the leader!");
					counter += 1;
				}
				break;
			case "No": counter +=1; System.out.println("You chose not to bid on this property during this round of bidding"); break;
			default: counter +=1; System.out.println("You did not choose an appropriate option and therefore cannot bid on this property during this round of bidding");
			}
			if(counter == playerList.length && tempprice != 0){
				tempvar = false; 
			}
			if(counter == playerList.length && tempprice == 0){
				System.out.println("Somebody will have to bid on this property to continue the game!");
				counter = 0;
			}
		}
	}
	property.owned = true;
	property.owner = templeader;
	templeader.moneyBalance -= tempprice;
	if(templeader.ownedProperties.length == 0 || templeader.ownedProperties == null){
		property [] temp = new property[1];
		temp[0] = property;
		templeader.ownedProperties = temp;
	}
	else{
		property [] temp = new property[templeader.ownedProperties.length + 1];
		for (int i = 0; i < templeader.ownedProperties.length; i++){
			temp[i] = templeader.ownedProperties[i];
		}
		temp[templeader.ownedProperties.length] = property;
		templeader.ownedProperties = temp;
	}
	//prints out the owned properties
	System.out.print(templeader.playerName + " now owns the following properties: ");
	for(int a = 0; a < templeader.ownedProperties.length; a++){
		if(a == templeader.ownedProperties.length -1){
			System.out.print(templeader.ownedProperties[a].propertyName + "\n");
		}
		else{
			System.out.print(templeader.ownedProperties[a].propertyName + ", ");
		}
	}
}
static void payRent(player payer, player reciever, int amount) {
	if(payer == reciever){
		
	}
	else{
		payer.moneyBalance -= amount; 
		reciever.moneyBalance += amount;
	}
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
static void drawCommunityChest (player player) {
	Random rand = new Random();
	int card = rand.nextInt(16);
	
	switch (card) {
	case 0: System.out.println(communityChestDescriptions[0]); player.moneyBalance += 100; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	case 1: System.out.println(communityChestDescriptions[1]); player.moneyBalance += 50; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 2: System.out.println(communityChestDescriptions[2]); player.inJail = true; player.boardPosition = 10; break;
	case 3: System.out.println(communityChestDescriptions[3]); player.moneyBalance -= 100; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	case 4: System.out.println(communityChestDescriptions[4]); player.moneyBalance += 20; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	case 5: System.out.println(communityChestDescriptions[5]); player.moneyBalance += 10; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	case 6: System.out.println(communityChestDescriptions[6]); player.JailFreeCards +=1; break; 
	case 7: System.out.println(communityChestDescriptions[7]); 
	for(int i = 0; i < playerList.length; i++){
		if(playerList[i] == player){
			
		}
		else{
			playerList[i].moneyBalance += 10;
			player.moneyBalance -= 10;
			System.out.println(player.playerName + " paid " + playerList[i].playerName + " $10 dollars");
		}
	}
	for(int i = 0; i < playerList.length; i++){
		System.out.println(playerList[i].playerName + " new account balance is " + playerList[i].moneyBalance);
	}
	break; 
	case 8: System.out.println(communityChestDescriptions[8]); player.moneyBalance += 200; player.boardPosition = 0; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	case 9: System.out.println(communityChestDescriptions[9]); player.moneyBalance -=50; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 10: System.out.println(communityChestDescriptions[10]); player.moneyBalance += 25; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 11: System.out.println(communityChestDescriptions[11]); player.moneyBalance += 100; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 12: System.out.println(communityChestDescriptions[12]); player.moneyBalance -= 50; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 13: System.out.println(communityChestDescriptions[13]); player.moneyBalance += 100; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
	case 14: System.out.println(communityChestDescriptions[14]); 
	for(int i = 0; i < player.ownedProperties.length; i++){
		//this is a for loop to scan through owned properties. We may have to change the design of owned properties 
		//to accomodate for this card method
		//Tried using instance of and had some troubles, may have to make a method
		//Note that not all properties have the property int houses or boolean hotels
	}
	System.out.println("This method is still being built, will not change player's money balance yet!"); break; //have to access properties for this method using loop
	case 15: System.out.println(communityChestDescriptions[15]); player.moneyBalance += 200; System.out.println(player.playerName + " has a new account balance of " + player.moneyBalance); break;
	}
}

//chance deck
static void drawChance (player player) {
	Random rand = new Random();
	int card = rand.nextInt(16);
	
	switch (card) {
	case 0: System.out.println(chanceDescriptions[0]); if(player.boardPosition >= 12 && player.boardPosition < 28){
		player.boardPosition = 28;
		if(allProperties[20].owned=true){
			payRent(player, allProperties[20].owner, allProperties[20].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[20].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[20]);
				break;
			case "No": 
				auctionProperty(allProperties[20]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} else{
		if(player.boardPosition > 27){
			player.moneyBalance +=200;
		}
		player.boardPosition = 12; 
		if(allProperties[7].owned=true){
			payRent(player, allProperties[7].owner, allProperties[7].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[7].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[7]);
				break;
			case "No": 
				auctionProperty(allProperties[7]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} break;
	case 1: System.out.println(chanceDescriptions[1]); if(player.boardPosition >= 35 && player.boardPosition < 5){
		if(player.boardPosition > 34){
			player.moneyBalance += 200;
		}
		player.boardPosition = 5;
		if(allProperties[2].owned=true){
			payRent(player, allProperties[2].owner, allProperties[2].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[2].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[2]);
				break;
			case "No": 
				auctionProperty(allProperties[2]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} 
	else if(player.boardPosition >= 5 && player.boardPosition < 15){
		player.boardPosition = 15;
		if(allProperties[10].owned=true){
			payRent(player, allProperties[10].owner, allProperties[10].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[10].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[10]);
				break;
			case "No": 
				auctionProperty(allProperties[10]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} else if(player.boardPosition >= 15 && player.boardPosition < 25){
		player.boardPosition = 25;
		if(allProperties[10].owned=true){
			payRent(player, allProperties[17].owner, allProperties[17].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[17].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[17]);
				break;
			case "No": 
				auctionProperty(allProperties[17]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} else {
		player.boardPosition = 35;
		if(allProperties[25].owned=true){
			payRent(player, allProperties[25].owner, allProperties[25].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[25].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[25]);
				break;
			case "No": 
				auctionProperty(allProperties[25]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} break; 
	case 2: System.out.println(chanceDescriptions[2]); 
	for(int i = 0; i < playerList.length; i++){
		if(playerList[i] == player){
			
		}
		else{
			playerList[i].moneyBalance += 50;
			player.moneyBalance -= 50;
		}
	}
	//Prints updated player money balances
	for(int i = 0; i < playerList.length; i++){
		System.out.println(playerList[i].playerName + " has an account balance of " + playerList[i].moneyBalance);
	}
	break; 
	case 3: System.out.println(chanceDescriptions[3]); player.inJail = true; player.boardPosition = 10; break;
	case 4: System.out.println(chanceDescriptions[4]); player.JailFreeCards +=1; break; 
	case 5: System.out.println(chanceDescriptions[5]); if(player.boardPosition >= 35 && player.boardPosition < 5){
		if(player.boardPosition > 34){
			player.moneyBalance += 200;
		}
		player.boardPosition = 5;
		if(allProperties[2].owned=true){
			payRent(player, allProperties[2].owner, allProperties[2].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[2].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[2]);
				break;
			case "No": 
				auctionProperty(allProperties[2]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} 
	else if(player.boardPosition >= 5 && player.boardPosition < 15){
		player.boardPosition = 15;
		if(allProperties[10].owned=true){
			payRent(player, allProperties[10].owner, allProperties[10].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[10].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[10]);
				break;
			case "No": 
				auctionProperty(allProperties[10]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} else if(player.boardPosition >= 15 && player.boardPosition < 25){
		player.boardPosition = 25;
		if(allProperties[10].owned=true){
			payRent(player, allProperties[17].owner, allProperties[17].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[17].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[17]);
				break;
			case "No": 
				auctionProperty(allProperties[17]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} else {
		player.boardPosition = 35;
		if(allProperties[25].owned=true){
			payRent(player, allProperties[25].owner, allProperties[25].rent*4);
		}
		else{
			System.out.println("Would you like to buy this property for $" + allProperties[25].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[25]);
				break;
			case "No": 
				auctionProperty(allProperties[25]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
	} break; 
	case 6: System.out.println(chanceDescriptions[6]); player.moneyBalance += 50; break; 
	case 7: System.out.println(chanceDescriptions[7]); player.moneyBalance += 150; break; 
	case 8: System.out.println(chanceDescriptions[8]); player.boardPosition -= 3; 
	if(player.boardPosition == 4){
		player.moneyBalance -= 200;
		System.out.println("You paid $200 in income tax!");
		//have to make sure switch excutes in roll method
	}
	else if(player.boardPosition == 19){
		System.out.println("You are at board position " + player.boardPosition + ", " + allProperties[13].propertyName); 
		if(allProperties[13].owned==false){
			//first, need to allow user to buy property if desired, then allow them to auction the property if not
			System.out.println("Would you like to buy this property for $" + allProperties[13].buyPrice);
			Scanner sc = new Scanner(System.in);
			String command = sc.next();
			switch(command){
			case "Yes": 
				buyProperty(player, allProperties[13]);
				break;
			case "No": 
				auctionProperty(allProperties[13]); break;
			default: System.out.println("Please enter a valid command");
			}
		}
		else{
			 payRent(player, allProperties[13].owner, allProperties[13].rent); 
		}
	}
	else{
		drawCommunityChest(player);
	} break;
	case 9: System.out.println(chanceDescriptions[9]); if(player.boardPosition > 24){
		player.moneyBalance += 200;
		player.boardPosition = 24;
	}
	else{
		player.boardPosition = 24;
	} break; //again make sure switch executes
	case 10: System.out.println(chanceDescriptions[10]); if(player.boardPosition > 11){
		player.moneyBalance += 200;
		player.boardPosition = 11;
	}
	else{
		player.boardPosition = 11;
	} break; //again make sure switch executes
	case 11: System.out.println(chanceDescriptions[11]); player.moneyBalance -= 15; break; 
	case 12: System.out.println(chanceDescriptions[12]); player.boardPosition = 39; 
	case 13: System.out.println(chanceDescriptions[13]); player.boardPosition = 0; player.moneyBalance +=200; break;
	case 14: System.out.println(chanceDescriptions[14]); if(player.boardPosition > 5){
		player.moneyBalance += 200;
		player.boardPosition = 5;
	}
	else{
		player.boardPosition = 5;
	} break; //again make sure switch executes
	case 15: System.out.println(chanceDescriptions[15]); break; //again have to integrate houses, probably using loops
	}
}

	class player {
		String playerName;
		int moneyBalance = 1500;
		String piece;
		boolean turn = false;
		boolean rollTurn = false;
		boolean inJail = false;
		property[] ownedProperties = new property[0];
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
		player owner;
		
		
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
		allProperties[16] = new street("Illinois Avenue", 24, 240, 20, "Red", 150);
		//third rail (don't step on it!)
		allProperties[17] = new property("B. & O. Railroad", 25, 200, 25, "Railroad");
		//first two yellow
		allProperties[18] = new street("Atlantic Avenue", 26, 260, 22, "Yellow", 150);
		allProperties[19] = new street("Ventor Avenue", 27, 260, 22, "Yellow", 150);
		//second utility
		allProperties[20] = new property("Water Works", 28, 150, dice1 + dice2, "Utility");
		//last yellow
		allProperties[21] = new street("Marvin Gardens", 29, 280, 24, "Yellow", 150);
		//green properties
		allProperties[22] = new street("Pacific Avenue", 31, 300, 26, "Green", 200);
		allProperties[23] = new street("North Carolina Avenue", 32, 300, 26, "Green", 200);
		allProperties[24] = new street("Pennsylvania Avenue", 34, 320, 28, "Green", 200);
		//last rail
		allProperties[25] = new property("Short Line", 35, 200, 25, "Railroad");
		//finally dark blue
		allProperties[26] = new street("Park Place", 37, 350, 35, "Dark Blue", 200);
		allProperties[27] = new street("Boardwalk", 39, 400, 50, "Dark Blue", 200);
		
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
		gameon = true;
		while(gameon){
			int a = 0; 
			for(int i = 0; i < playerList.length; i++){
				if(playerList[i] == firstPlayer){
					break;
				}
				else{
					a +=1;
				}
			}
			for(int i = a; i < playerList.length; i++){
				game(playerList[i]);
			}
			boolean wholegame = true;
			while(wholegame){
				for(int i = 0; i < playerList.length; i++){
					game(playerList[i]);
				}
			}
		}
	
	}
}