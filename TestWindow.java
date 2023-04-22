package Window;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Canvas;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;



import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JLabel;

class TestWindow extends JFrame {
	  
	private static JTextField userInput;
	static private JTextArea console, playerData;
	private JButton btnBarter, btnPropertyManagement, btnRoll, submit, endTurn;
	public static Image img;
	  
	public TestWindow() {
		getContentPane().setBackground(new Color(192, 192, 192));
		getContentPane().setLayout(null);
		
		btnBarter = new JButton("Barter" + "\n");
		btnBarter.setBounds(1232, 500, 182, 48);
		getContentPane().add(btnBarter);
		
		btnPropertyManagement = new JButton("Property Management" + "\n");
		btnPropertyManagement.setBounds(1232, 558, 182, 48);
		getContentPane().add(btnPropertyManagement);
		
		btnRoll = new JButton("Roll" + "\n");
		btnRoll.setBounds(1232, 442, 182, 48);
		getContentPane().add(btnRoll);
		
		console = new JTextArea();
		console.setBounds(766, 8, 427, 629);
		console.setEditable(false);
		console.setLineWrap(true);
		getContentPane().add(console);
		
		userInput = new JTextField();
		userInput.setBounds(766, 656, 427, 70);
		getContentPane().add(userInput);
		userInput.setColumns(10);
		
		playerData = new JTextArea();
		playerData.setBounds(1232, 8, 182, 424);
		playerData.setEditable(false);
		getContentPane().add(playerData);
		
		JLabel board = new JLabel();
		board.setIcon(new ImageIcon("C:\\Users\\barba\\workspace\\WIndowTest\\src\\Window\\Monopoly700.jpg" + "\n"));
		board.setBounds(10, 10, 715, 715);
		getContentPane().add(board);
		
		submit = new JButton("Submit" + "\n");
		submit.setBounds(1232, 674, 182, 52);
		getContentPane().add(submit);
		
		endTurn = new JButton("End Turn" + "\n");
		endTurn.setBounds(1232, 616, 182, 48);
		getContentPane().add(endTurn);
		
		ActionListener1 monolistener = new ActionListener1();
		btnBarter.addActionListener(monolistener);
		btnPropertyManagement.addActionListener(monolistener);
		btnRoll.addActionListener(monolistener);
		submit.addActionListener(monolistener);
		userInput.addActionListener(monolistener);
		endTurn.addActionListener(monolistener);

		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class ActionListener1 implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnBarter) {
				barter(cPlayer);
			}
			if(e.getSource()==btnPropertyManagement) {
				propertyManagement(cPlayer);
			}
			if(e.getSource()==endTurn) {
				if(cPlayer.rollTurn==true) console.append("Please roll before ending your turn\n" + "\n");
				else cPlayer.turn = false;
			}
			if(e.getSource()==btnRoll) {
				roll(cPlayer); 
			}
			if(e.getSource()==userInput) {
				
			}
			if(e.getSource()==submit) {
				input=false;
			
			}
		}
		
	}
	
		static int dice1, dice2;
		static player[] playerList;
		static property[] allProperties = new property[28];
		static property[] availableProperties = allProperties;
		static String[] communityChestDescriptions = new String[16];
		static String[] chanceDescriptions = new String[16];
		static String[] monopolyPieces = new String[8];
		static String[] spaceNames = new String[40];
		static boolean gameon;
		static boolean input;
		static player cPlayer;
		static ActionListener1 monolistener;
		private JButton btnNewButton;

		static String getInput() {
			input = true;
			while(input) {

			}
			return userInput.getText();
		}
		
		//this method will get player amount, their respective information, and determine who goes first
		static player startGame(){ //returns the player that will be going first
			boolean validPlayerNum = false;
			int numPlayers = 0;
			while (!validPlayerNum){
				console.append("Enter number of players (2-8): \n" + "\n");
				numPlayers = Integer.parseInt(getInput());
				if (numPlayers >= 2 && numPlayers <= 8) {
					validPlayerNum = true;
				} else console.append("Please enter valid number of players.\n" + "\n");
			} 
			
			
			playerList = new player[numPlayers]; //create a list with the length of the number of players
			//Monopoly m = new Monopoly();
			for (int i = 0; i < playerList.length; i++) {
				console.append("Enter player name: \n" + "\n");
				playerList[i] = new player();
				playerList[i].playerName = getInput(); //player enters their name 
				boolean validPiece = false;
				
				while (!validPiece) { //player keeps choosing a piece until they select a valid piece that is not already taken
					console.append(playerList[i].playerName + ", what piece would you like to be? (battleship, car, penguin, top hat, duck, dog, cat, dinosaur) \n" + "\n");
					String pieceChoice = getInput();
					
					for (int x = 0; x < monopolyPieces.length; x++) { //matches input against every element in piece array
						if (pieceChoice.equals(monopolyPieces[x])) { //player input is in the list
							playerList[i].piece = pieceChoice; //make that the player piece
							monopolyPieces[x] = null; //set the corresponding element to null so no other players may use it
							validPiece = true;
							break; //end infinite loop
						}
				
					}
					if (!validPiece) console.append("Piece is either chosen or does not exist.\n" + "\n");
				}
				
			}
			//decide who goes first
			int[] playerRolls = new int[playerList.length];
			
			for (int i = 0; i < playerList.length; i++) { //all the players roll the dice
				console.append(playerList[i].playerName + " is rolling\n" + "\n");
				rollDice();
				playerRolls[i] = dice1 + dice2;
				console.append(playerList[i].playerName + " rolled a " + playerRolls[i] + "\n" + "\n");
			}
			
			int maxRollerIndex = 0;
			int maxRoll = 0;
			for (int i = 0; i < playerList.length; i++) {
				if (playerRolls[i] > maxRoll) {
					maxRoll = playerRolls[i];
					maxRollerIndex = i;
				}
			}
			console.append(playerList[maxRollerIndex].playerName + " had the highest roll of " + maxRoll + ". They will be first.\n" + "\n");
			return playerList[maxRollerIndex];
		}


		//What actions can you take in a turn? 
		//rolling the dice to get out of jail or move
		//bartering with players
		//property management (buying houses/hotels, mortgaging)
		static void game(player currentPlayer) {
			cPlayer = currentPlayer;
			console.append(currentPlayer.playerName + " it is your turn!\n" + "\n");
			currentPlayer.rollTurn = true;
			currentPlayer.turn=true;
			if(cPlayer.JailFreeCards > 0 && currentPlayer.inJail){
				console.append("Would you like to use a get out of jail free card? (Yes/No)\n" + "\n");
				String command = getInput();
				switch(command){
				case "Yes": currentPlayer.inJail=false; break;
				default: console.append("You have forgone using your get out of jail free card!\n" + "\n");
				}
				}
			if(cPlayer.inJail==true && currentPlayer.moneyBalance >= 50 && currentPlayer.jailCounter < 3){
				console.append("You are in jail! Would you like to pay $50 to get out?\n" + "\n");
				String jailCommand = getInput();
				switch(jailCommand){
				case "Yes": currentPlayer.inJail = false; console.append("You paid $50 to get out of jail and now have a balance of " + currentPlayer.moneyBalance + "\n" + "\n"); break;
				default: console.append("You opted not to pay $50 and will try to roll to get out of jail\n" + "\n");
				}
			}
			if(cPlayer.inJail==true && currentPlayer.moneyBalance >= 50 && currentPlayer.jailCounter == 3){
				console.append("This is your third roll in jail, try and roll doubles to get out for free, otherwise pay $50\n" + "\n");
			}
			if(cPlayer.inJail == true && currentPlayer.moneyBalance < 50){
				//have to make this method implement currentAssets
				console.append("You are in jail and do not have enough money to get out! You must try to roll doubles to get out of jail!\n" + "\n");
			}
			console.append(currentPlayer.playerName +", what action would you like to take? (roll, barter, property management, end turn)\n" + "\n");
			while(cPlayer.turn) {
				
			}
			currentPlayer = cPlayer;
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
			boolean tempjailtrack = false; //this is made true if a player rolls doubles and the amount of doubles must be tracked
			
			while(player.rollTurn){
			dice1 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1); //gets a random number 1 through six inclusive
			console.append("Dice one is " + dice1 + "\n" + "\n");
			dice2 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
			console.append("Dice two is " + dice2 + "\n" + "\n");
			int movement = dice1+dice2; //roll two dice per turn
			
			if(player.inJail == false){ //if you aren't in jail, you move on the board
				player.boardPosition += movement;
				tempjailtrack = true; //start tracking amount of doubles rolled
			}
			
			if(dice1==dice2 && tempjailtrack==true){
				doubletracker += 1;
				if(doubletracker == 3){ //if your roll three doubles in a row, you go to jail!
					console.append("You rolled three doubles in a row, go to Jail!\n" + "\n");
					player.boardPosition = 10;
					player.inJail = true; 
					player.rollTurn = false;
					continue; //roll method ends if you go to jail
				}
			}
			
			//A double means that you roll the same value for each dice, you can roll again if you roll doubles
			
			if(player.boardPosition > 39){ //if the player passes the GO square, they get $200
				player.moneyBalance += 200;
				player.boardPosition -= 40;
			}
			else if(player.inJail == true){ //Need to give player the option to pay at any time to get out of jail
				if(dice1==dice2){ //player has to roll doubles to get out of jail, they have three chances to do so
					player.boardPosition = 10 + movement;
					player.inJail = false;
					player.jailCounter = 0;
				}
				
				else if (player.jailCounter < 3){
					//do nothing if neither condition of being freed from jail is met
				}
				else if (player.jailCounter == 3){ //if the player fails to roll doubles to get put of jail in three turns, the pay $50 and get out
					player.inJail = false;
					player.moneyBalance -= 50;
					player.boardPosition = 10 + movement;
					player.jailCounter = 0; 
				}
			}
			switch(player.boardPosition){ //correspondng square data is in main method, built using constructor
			case 0: console.append("You are at board position " + player.boardPosition + ", Pass Go, collect $200!\n" + "\n"); break;
			case 1: console.append("You are at board position " + player.boardPosition + ", " + allProperties[0].propertyName+"\n" + "\n"); 
				if(allProperties[0].owned==false){
					manageUnownedProperty(player, allProperties[0]); //this method is used to decide if a player buys or auctions property
				}
				else{
					 payRent(player, allProperties[0].owner, allProperties[0]); 
				} break;
			case 2: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now.\n" + "\n"); 
			drawCommunityChest(player); break;
			case 3: console.append("You are at board position " + player.boardPosition + ", " + allProperties[1].propertyName+"\n" + "\n"); 
				if(allProperties[1].owned==false){
					manageUnownedProperty(player, allProperties[1]);
				}
			else{
				 payRent(player, allProperties[1].owner, allProperties[1]); 
			} break;
			case 4: console.append("You are at board position " + player.boardPosition + ", income tax, pay $200!\n" + "\n"); player.moneyBalance -= 200; break;
			case 5: console.append("You are at board position " + player.boardPosition + ", " + allProperties[2].propertyName+"/n" + "\n"); 
			if(allProperties[2].owned==false){
				manageUnownedProperty(player, allProperties[2]);
			}
			else{
				 payRent(player, allProperties[2].owner, allProperties[2]); 
			} break;
			case 6: console.append("You are at board position " + player.boardPosition + ", " + allProperties[3].propertyName + "/n" + "\n"); 
			if(allProperties[3].owned==false){
				manageUnownedProperty(player, allProperties[3]);
			}
			else{
				 payRent(player, allProperties[3].owner, allProperties[3]); 
			} break;
			case 7: console.append("You are at board position " + player.boardPosition + ", chance, drawing a chance card now." + "\n" + "\n"); 
			drawChance(player); break;
			case 8: console.append("You are at board position " + player.boardPosition + ", " + allProperties[4].propertyName+ "\n" + "\n"); 
			if(allProperties[4].owned==false){
				manageUnownedProperty(player, allProperties[4]);
			}
			else{
				 payRent(player, allProperties[4].owner, allProperties[4]); 
			} break;
			case 9: console.append("You are at board position " + player.boardPosition + ", " + allProperties[5].propertyName+ "\n" + "\n"); 
			if(allProperties[5].owned==false){
				manageUnownedProperty(player, allProperties[5]);
			}
			else{
				 payRent(player, allProperties[5].owner, allProperties[5]); 
			} break;
			case 10: if(player.inJail == false) console.append("You are just visiting jail"+ "\n" + "\n"); break;
			case 11: console.append("You are at board position " + player.boardPosition + ", " + allProperties[6].propertyName + "\n" + "\n"); 
			if(allProperties[6].owned==false){
				manageUnownedProperty(player, allProperties[6]);
			}
			else{
				 payRent(player, allProperties[6].owner, allProperties[6]); 
			} break;
			case 12: console.append("You are at board position " + player.boardPosition + ", " + allProperties[7].propertyName +"\n" + "\n"); 
			if(allProperties[7].owned==false){
				manageUnownedProperty(player, allProperties[7]);
			}
			else{
				 payRent(player, allProperties[7].owner, allProperties[7]); 
			} break;
			case 13: console.append("You are at board position " + player.boardPosition + ", " + allProperties[8].propertyName + "\n" + "\n"); 
			if(allProperties[8].owned==false){
				manageUnownedProperty(player, allProperties[8]);
			}
			else{
				 payRent(player, allProperties[8].owner, allProperties[8]); 
			} break;
			case 14: console.append("You are at board position " + player.boardPosition + ", " + allProperties[9].propertyName + "\n" + "\n"); 
			if(allProperties[9].owned==false){
				manageUnownedProperty(player, allProperties[9]);
			}
			else{
				 payRent(player, allProperties[0].owner, allProperties[9]); 
			} break;
			case 15: console.append("You are at board position " + player.boardPosition + ", " + allProperties[10].propertyName + "\n" + "\n"); 
			if(allProperties[10].owned==false){
				manageUnownedProperty(player, allProperties[10]);
			}
			else{
				 payRent(player, allProperties[10].owner, allProperties[10]); 
			} break;
			case 16: console.append("You are at board position " + player.boardPosition + ", " + allProperties[11].propertyName + "\n" + "\n"); 
			if(allProperties[11].owned==false){
				manageUnownedProperty(player, allProperties[11]);
			}
			else{
				 payRent(player, allProperties[11].owner, allProperties[11]); 
			} break;
			case 17: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now.\n" + "\n"); 
			drawCommunityChest(player); break;
			case 18: console.append("You are at board position " + player.boardPosition + ", " + allProperties[12].propertyName + "\n" + "\n"); 
			if(allProperties[12].owned==false){
				manageUnownedProperty(player, allProperties[12]);
			}
			else{
				 payRent(player, allProperties[12].owner, allProperties[12]); 
			} break;
			case 19: console.append("You are at board position " + player.boardPosition + ", " + allProperties[13].propertyName); 
			if(allProperties[13].owned==false){
				manageUnownedProperty(player, allProperties[13]);
			}
			else{
				 payRent(player, allProperties[13].owner, allProperties[13]); 
			} break;
			case 20: console.append("You are at board position " + player.boardPosition + ", free parking, enjoy your stay!" + "\n" + "\n"); break;
			case 21: console.append("You are at board position " + player.boardPosition + ", " + allProperties[14].propertyName + "\n" + "\n"); 
			if(allProperties[14].owned==false){
				manageUnownedProperty(player, allProperties[14]);
			}
			else{
				 payRent(player, allProperties[14].owner, allProperties[14]); 
			} break;
			case 22: console.append("You are at board position " + player.boardPosition + ", chance, drawing a chance card now." + "\n" + "\n"); 
			drawChance(player); break;
			case 23: console.append("You are at board position " + player.boardPosition + ", " + allProperties[15].propertyName); 
			if(allProperties[15].owned==false){
				manageUnownedProperty(player, allProperties[15]);
			}
			else{
				 payRent(player, allProperties[15].owner, allProperties[15]); 
			} break;
			case 24: console.append("You are at board position " + player.boardPosition + ", " + allProperties[16].propertyName); 
			if(allProperties[16].owned==false){
				manageUnownedProperty(player, allProperties[16]);
			}
			else{
				 payRent(player, allProperties[16].owner, allProperties[16]); 
			} break;
			case 25: console.append("You are at board position " + player.boardPosition + ", " + allProperties[17].propertyName); 
			if(allProperties[17].owned==false){
				manageUnownedProperty(player, allProperties[17]);
			}
			else{
				 payRent(player, allProperties[17].owner, allProperties[17]); 
			} break;
			case 26: console.append("You are at board position " + player.boardPosition + ", " + allProperties[18].propertyName); 
			if(allProperties[18].owned==false){
				manageUnownedProperty(player, allProperties[18]);
			}
			else{
				 payRent(player, allProperties[18].owner, allProperties[18]); 
			} break;
			case 27: console.append("You are at board position " + player.boardPosition + ", " + allProperties[19].propertyName); 
			if(allProperties[19].owned==false){
				manageUnownedProperty(player, allProperties[19]);
			}
			else{
				 payRent(player, allProperties[19].owner, allProperties[19]); 
			} break;
			case 28: console.append("You are at board position " + player.boardPosition + ", " + allProperties[20].propertyName); 
			if(allProperties[20].owned==false){
				manageUnownedProperty(player, allProperties[20]);
			}
			else{
				 payRent(player, allProperties[20].owner, allProperties[20]); 
			} break;
			case 29: console.append("You are at board position " + player.boardPosition + ", " + allProperties[21].propertyName); 
			if(allProperties[21].owned==false){
				manageUnownedProperty(player, allProperties[21]);
			}
			else{
				 payRent(player, allProperties[21].owner, allProperties[21]); 
			} break;
			case 30: player.inJail = true; console.append("You landed on go to jail, go to jail immediately!" + "\n" + "\n"); break;
			case 31: console.append("You are at board position " + player.boardPosition + " " + allProperties[22].propertyName); 
			if(allProperties[22].owned==false){
				manageUnownedProperty(player, allProperties[22]);
			}
			else{
				 payRent(player, allProperties[22].owner, allProperties[22]); 
			} break;
			case 32: console.append("You are at board position " + player.boardPosition + ", " + allProperties[23].propertyName); 
			if(allProperties[23].owned==false){
				manageUnownedProperty(player, allProperties[23]);
			}
			else{
				 payRent(player, allProperties[23].owner, allProperties[23]); 
			} break;
			case 33: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now." + "\n" + "\n"); 
			drawCommunityChest(player); break;
			case 34: console.append("You are at board position " + player.boardPosition + ", " + allProperties[24].propertyName); 
			if(allProperties[24].owned==false){
				manageUnownedProperty(player, allProperties[24]);
			}
			else{
				 payRent(player, allProperties[24].owner, allProperties[24]); 
			} break;
			case 35: console.append("You are at board position " + player.boardPosition + ", " + allProperties[25].propertyName); 
			if(allProperties[25].owned==false){
				manageUnownedProperty(player, allProperties[25]);
			}
			else{
				 payRent(player, allProperties[25].owner, allProperties[25]); 
			} break;
			case 36: console.append("You are at board position " + player.boardPosition + ", chance, drawing a chance card now." + "\n"); 
			drawChance(player); break;
			case 37: console.append("You are at board position " + player.boardPosition + ", " + allProperties[26].propertyName); 
			if(allProperties[26].owned==false){
				manageUnownedProperty(player, allProperties[26]);
			}
			else{
				 payRent(player, allProperties[26].owner, allProperties[26]); 
			} break;
			case 38: console.append("You are at board position " + player.boardPosition + ", luxury tax. Pay $100!" + "\n"); player.moneyBalance-=100; break;
			case 39: console.append("You are at board position " + player.boardPosition + ", " + allProperties[27].propertyName); 
			if(allProperties[27].owned==false){
				manageUnownedProperty(player, allProperties[27]);
			}
			else{
				 payRent(player, allProperties[27].owner, allProperties[27]); 
			} break;
			}
			if(player.inJail==true){
				player.boardPosition = 10;
				player.jailCounter +=1;
				int temp = player.jailCounter - 1;
				int temp2 = 3 -temp;
				console.append("You are in jail! You have rolled " + temp + " times in jail. You have " + temp2 + " rolls remaining in jail." + "\n");
			}
			if(dice1==dice2 && tempjailtrack == true){ //tracks amount of doubles rolled in jail
				continue;
			}
			if(player.moneyBalance == -1){
				console.append(player.playerName + " you have gone bankrupt! Please end turn, all other methods will become unavailable!" + "\n");
			}
			else{
				console.append(player.playerName + " has exhaused all rolls at board space " + player.boardPosition + " with a money balance of $" + player.moneyBalance);
			}
			doubletracker = 0;
			player.rollTurn = false;
			}
		}

		//Players are able to negotiate for different items (properties, money, cards) and can barter with those same items
		public static void barter(player player) {
			boolean bartering = true;
			while (bartering) {
				console.append(player.playerName + ", these are the remaining players in the game:" + "\n");
				for (int i = 0; i < playerList.length; i++) if(!playerList[i].equals(player)) console.append(playerList[i].playerName);
				
				System.out.print(player.playerName + ", enter the name of the player you would like to trade with: " + "\n");
				Scanner sc = new Scanner(System.in);
				String otherPlayerName = sc.nextLine();
				int otherPlayerIndex = -1;
				
				player otherPlayer = null;
				boolean validPlayer = false;
				
				for (int i = 0; i < playerList.length; i++) {
					if (playerList[i].playerName.equals(otherPlayerName) && !playerList[i].equals(player)) {
						otherPlayer = playerList[i];
						otherPlayerIndex = i;
						validPlayer = true;
						break;
					}
				}
				if (validPlayer) {
					//variables are used to store elements of deal
					int currentPlayerMoney = 0, otherPlayerMoney = 0;
					property[] currentPlayerProperties = null, otherPlayerProperties = null;
					int currentPlayerCards = 0, otherPlayerCards = 0;
					boolean menu1 = true;
					while(menu1) {
						System.out.print(player.playerName + ", edit what you would like from " + otherPlayer.playerName + " (Money, Property, Cards, Proceed): " + "\n");
						String command = sc.nextLine();
						switch (command) {
						case "Money": 
							System.out.print(otherPlayer.playerName + " currently has $" + otherPlayer.moneyBalance + ". How much would you like? " + "\n");
							int moneyInput = sc.nextInt();
							if (moneyInput <= otherPlayer.moneyBalance && moneyInput > 0) {
								otherPlayerMoney = moneyInput;
							} else {
								console.append(otherPlayer.playerName + " does not have that much money or input is less than zero" + "\n");
							}
							sc.nextLine();
							break;
						case "Property":
							int otherPropertyCount = 0;
							console.append(otherPlayer.playerName + " has the following properties:" + "\n");
							for (int i = 0; i < allProperties.length; i++) if (allProperties[i].owner == otherPlayer) {
								console.append(allProperties[i].propertyName);
								otherPropertyCount++;
							}
							
							property[] otherTotalProperties = new property[otherPropertyCount];
							int indexCounter = 0;
							for (int i = 0; i < allProperties.length; i++) if (allProperties[i].owner == otherPlayer) {
								otherTotalProperties[indexCounter] = allProperties[i];
								indexCounter++;
							}
							System.out.print("How many of these properties would you like to barter for? " + "\n");
							int numOtherProperties = sc.nextInt();
							if (numOtherProperties > otherPropertyCount || numOtherProperties <= 0) {
								console.append("You have entered more properties than the other player has or less than 1!" + "\n");
							} else {
								sc.nextLine();
								otherPlayerProperties = new property[numOtherProperties];
								String[] otherPropertyNames = new String[numOtherProperties];
								for (int i = 0; i < numOtherProperties; i++) {
									System.out.print("Enter the " + (i + 1) + " property you want: " + "\n");
									otherPropertyNames[i] = sc.nextLine();
								}
								
								for (int i = 0; i < otherPlayerProperties.length; i++) { //need to add error handling if names do not match properties in list
									for (int z = 0; z < otherTotalProperties.length; z++) {
										if (otherPropertyNames[i].equals(otherTotalProperties[z].propertyName)) {
											otherPlayerProperties[z] = otherTotalProperties[i];
											break;
										}
									}
								}
								
							} break;
						case "Cards": 
							if (otherPlayer.JailFreeCards < 1) {
								console.append(otherPlayer.playerName + " does not have any get out of jail free cards" + "\n");
							} else {
								console.append(player.playerName + ", how many get out of jail free cards from " + otherPlayer.playerName + " would you like?" + "\n");
								int cardInput = sc.nextInt();
								if (cardInput <= otherPlayer.JailFreeCards && cardInput > 0) {
									otherPlayerCards = cardInput;
								} else console.append("You have entered more cards than " + otherPlayer.playerName + " has!" + "\n");
							}
							sc.nextLine();
							break;
						case "Proceed": menu1 = false; break;
						default: console.append("invalid command" + "\n");
						}
					}
						boolean menu2 = true;
						while(menu2) {
							System.out.print(player.playerName + ", what would you like to give in return to " + otherPlayer.playerName + "? (Money, Property, Cards, Proceed): " + "\n");
							String command = sc.next();
							switch (command) {
							case "Money": 
								System.out.print(player.playerName + " currently has $" + player.moneyBalance + ". How much would you like to give? " + "\n");
								int moneyInput = sc.nextInt();
								if (moneyInput <= player.moneyBalance && moneyInput > 0) {
									currentPlayerMoney = moneyInput;
								} else {
									console.append(player.playerName + " does not have that much money or input is less than zero" + "\n");
								}
								sc.nextLine();
								break;
							case "Property":
								int currentPropertyCount = 0;
								console.append(player.playerName + " has the following properties:" + "\n");
								for (int i = 0; i < allProperties.length; i++) if (allProperties[i].owner == player) {
									console.append(allProperties[i].propertyName);
									currentPropertyCount++;
								}
								
								property[] currentTotalProperties = new property[currentPropertyCount];
								int indexCounter = 0;
								for (int i = 0; i < allProperties.length; i++) if (allProperties[i].owner == player) {
									currentTotalProperties[indexCounter] = allProperties[i];
									indexCounter++;
								}
								System.out.print("How many of these properties would you like to give? " + "\n");
								int numCurrentProperties = sc.nextInt();
								if (numCurrentProperties > currentPropertyCount || numCurrentProperties <= 0) {
									console.append("You have entered more properties than you have or less than 1!" + "\n");
								} else {
									sc.nextLine();
									currentPlayerProperties = new property[numCurrentProperties];
									String[] currentPropertyNames = new String[numCurrentProperties];
									for (int i = 0; i < numCurrentProperties; i++) {
										System.out.print("Enter the " + (i + 1) + " property you want to give: " + "\n");
										currentPropertyNames[i] = sc.nextLine();
									}
									
									for (int i = 0; i < currentPlayerProperties.length; i++) { //need to add error handling if names do not match properties in list
										for (int z = 0; z < currentTotalProperties.length; z++) {
											if (currentPropertyNames[i].equals(currentTotalProperties[z].propertyName)) {
												currentPlayerProperties[z] = currentTotalProperties[i];
												break;
											}
										}
									}
									
								}
								break;
							case "Cards": 
								if (player.JailFreeCards < 1) {
									console.append(player.playerName + " does not have any get out of jail free cards" + "\n");
								} else {
									console.append(player.playerName + ", how many get out of jail free cards would you like to give?" + "\n");
									int cardInput = sc.nextInt();
									if (cardInput <= player.JailFreeCards && cardInput > 0) {
										currentPlayerCards = cardInput;
									} else console.append("You have entered more cards than " + player.playerName + " has!" + "\n");
								}
								sc.nextLine();
								break;
							case "Proceed": menu2 = false; break;
							default: console.append("invalid command" + "\n");

						}
					}
						
						System.out.print("Has the deal been accepted? (y/n) " + "\n");
						String accepted = sc.next();
						
						if (accepted.equals("y" + "\n")) {
							console.append("Deal accepted!" + "\n");
							player.moneyBalance += otherPlayerMoney;
							player.moneyBalance -= currentPlayerMoney;
							playerList[otherPlayerIndex].moneyBalance -= otherPlayerMoney;
							playerList[otherPlayerIndex].moneyBalance += currentPlayerMoney;
							player.JailFreeCards += otherPlayerCards;
							player.JailFreeCards -= currentPlayerCards;
							playerList[otherPlayerIndex].JailFreeCards -= otherPlayerCards;
							playerList[otherPlayerIndex].JailFreeCards += currentPlayerCards;
							if(otherPlayerProperties != null) {
							for(int i = 0; i < otherPlayerProperties.length; i++) {
								for(int j = 0; j < allProperties.length; j++) {
									if(otherPlayerProperties[i] == allProperties[j]) {
										allProperties[j].owner = player;
									}
								}
							}
							}
							if (currentPlayerProperties != null) {
							for(int i = 0; i < currentPlayerProperties.length; i++) {
								for(int j = 0; j < allProperties.length; j++) {
									if(currentPlayerProperties[i] == allProperties[j]) {
										allProperties[j].owner = playerList[otherPlayerIndex];
									}
								}
							}
							}
							console.append("Player " + player.playerName + " has $" + player.moneyBalance);
							console.append("Player " + playerList[otherPlayerIndex].playerName + " has $" + playerList[otherPlayerIndex].moneyBalance);
							bartering = false;
						} else if (accepted.equals("n" + "\n")) {
							console.append("No deal!" + "\n");
							bartering = false;
						} else console.append("invalid response" + "\n");
				}
			}
			audit();
		}
		//players can buy hotels/houses and/or mortgage properties
		static void propertyManagement(player player) {
			boolean managing = true;
			while(managing) {
				Scanner sc = new Scanner(System.in);
				console.append(player.playerName + ", what would you like to do? (Buy, Sell, Mortgage, Unmortgage, Exit)" + "\n");
				String command = sc.nextLine();
				
				//these need to be defined here because they are used for several options in menu
				String currentStreetName, currentPropertyName; //user enters input
				property currentProperty = null;
				street currentStreet = null; //used to store and manipulate current street object
				boolean ownsProperty = false; //used to determine if the user has entered a valid street
				int currentStreetIndex, currentPropertyIndex = 0; //index is needed later to save potential changes made to street
				
				switch(command) {
				case "Buy":
					console.append("You own the following streets that are eligble to build houses on:" + "\n");
					for(int i = 0; i < allProperties.length; i++) { //only lists properties where houses can be built
						if (allProperties[i].owner == player && allProperties[i] instanceof street && allProperties[i].playerSet == true) console.append(allProperties[i].propertyName);
					}
					
					console.append("Enter the name of the street you would like to build on:" + "\n");
					currentStreetName = sc.nextLine(); //user enters input
					currentStreet = null; //used to store and manipulate current street object
					ownsProperty = false; //used to determine if the user has entered a valid street
					currentStreetIndex = 0; //index is needed later to save potential changes made to street
					
					for (int i = 0; i < allProperties.length; i++) { //information is gathered if street is valid
						if (currentStreetName.equals(allProperties[i].propertyName) && allProperties[i].owner == player && allProperties[i].playerSet == true) {
							ownsProperty = true;
							currentStreet = (street) allProperties[i];
							currentStreetIndex = i;
							break;
						}
					}
					
					if (ownsProperty) {
						if (currentStreet.numHouses == 5) { //cannot have more than one hotel.
							console.append("You already have a hotel on " + currentStreet.propertyName + "!" + "\n");
							continue; //goes back to switch statement
						}
						console.append(currentStreet.propertyName + " currently has " + currentStreet.numHouses + " houses." + "\n");
						console.append("Each house costs $" + currentStreet.housePrice);
						
						console.append("How many houses would you like to purchase? " + "\n");
						int desiredHouses = sc.nextInt(); //gather user input
						//player has to afford the desired houses and not be building over the limit
						if(desiredHouses * currentStreet.housePrice > player.moneyBalance || desiredHouses + currentStreet.numHouses > 5) {
							console.append("You either have insufficient funds or are trying to build too many houses!" + "\n");
							continue;
						} else {
							currentStreet.numHouses += desiredHouses; //add the houses
							player.moneyBalance -= (desiredHouses * currentStreet.housePrice); //deduct the required funds
							//change the rent cost for property
							allProperties[currentStreetIndex] = currentStreet; //save new street data to player property list
						}
						
					} else console.append("You have entered a property you do not own, that is not a street, and or is not a color set" + "\n"); //if street is invalid
					break;
				case "Sell": 
					console.append("You own the following streets with houses:" + "\n");
					for(int i = 0; i < allProperties.length; i++) { //only lists properties where houses can be built
						if (allProperties[i].owner == player && allProperties[i] instanceof street && allProperties[i].playerSet == true) {
							console.append(allProperties[i].propertyName);
						}
					}
					
					console.append("Enter the name of the street you would like to sell improvements on: " + "\n");
					currentStreetName = sc.nextLine(); //user enters input
					currentStreet = null; //used to store and manipulate current street object
					ownsProperty = false; //used to determine if the user has entered a valid street
					currentStreetIndex = 0; //index is needed later to save potential changes made to street
					
					for (int i = 0; i < allProperties.length; i++) { //information is gathered if street is valid
						if (currentStreetName == allProperties[i].propertyName && allProperties[i].owner == player) {
							ownsProperty = true;
							currentStreet = (street) allProperties[i];
							currentStreetIndex = i;
							break;
						}
					}
					
					if (ownsProperty) {
						if (currentStreet.numHouses == 5)  {
							console.append("You have a hotel on " + currentStreet.propertyName + "." + "\n");
						} else {
							console.append(currentStreet.propertyName + " currently has " + currentStreet.numHouses + " houses." + "\n");
						}
						
						console.append("You may sell each house back to the bank for $" + (currentStreet.housePrice / 2));
						
						System.out.print("How many houses would you like to sell? " + "\n");
						int desiredHouses = sc.nextInt(); //gather user input
						//player has to afford the desired houses and not be building over the limit
						if(desiredHouses > currentStreet.numHouses) {
							console.append("You are tyring to sell more houses than you have!" + "\n");
							continue;
						} else {
							currentStreet.numHouses -= desiredHouses; //remove the houses
							player.moneyBalance += (desiredHouses * (currentStreet.housePrice / 2)); //give refund back to player
							//change the rent cost for property
							allProperties[currentStreetIndex] = currentStreet; //save new street data to player property list
						}
						
					} else console.append("You have entered a property you do not own or that is not a street" + "\n"); //if street is invalid
					break;
				case "Mortgage": 
					console.append("Here are the properties that are not currently mortgaged:" + "\n");
					for (int i = 0; i < allProperties.length; i++) { //prints unmortgaged properties
						if (!allProperties[i].mortgaged && allProperties[i].owner == player) console.append(allProperties[i].propertyName);
					}
					
					console.append("Which property would you like to mortgage? " + "\n");
					//need to reset these variables because we could be dealing with a street or property class
					currentPropertyName = sc.nextLine(); //user enters input
					currentProperty = null;
					currentStreet = null;//used to store and manipulate current street object
					ownsProperty = false; //used to determine if the user has entered a valid street
					currentPropertyIndex = 0;
					currentStreetIndex = 0;//index is needed later to save potential changes made to street
					
					for (int i = 0; i < allProperties.length; i++) { //information is gathered if street is valid
						if (currentPropertyName.equals(allProperties[i].propertyName) && allProperties[i].owner == player) {
							ownsProperty = true;
							if (allProperties[i] instanceof street) { //use street variables for street 
								currentStreetName = currentPropertyName;
								currentStreet = (street) allProperties[i];
								currentStreetIndex = i;
								break;
							} else { //use property variables for property
								currentProperty = allProperties[i];
								currentPropertyIndex = i;
								break;
							}
						}
					}
					if (ownsProperty) {
						if (currentProperty == null) { //null current property means we have a street
							if (currentStreet.numHouses > 0) { 
								console.append("You must sell all houses on this property before you can mortgage it." + "\n");
							} else { 
								currentStreet.mortgaged = true;
								player.moneyBalance += (currentStreet.buyPrice/2);
								allProperties[currentStreetIndex] = currentStreet;
							}
						} else { //not null current property means we have a property
							currentProperty.mortgaged = true;
							player.moneyBalance += (currentProperty.buyPrice/2);
							allProperties[currentPropertyIndex] = currentProperty;
						}
						console.append("New player money balance is " + player.moneyBalance);
					} else console.append("You have entered a property you do not own or is already mortgaged" + "\n");
					break;
				case "Unmortgage": 
					console.append("Here are the properties that are currently mortgaged:" + "\n");
					for (int i = 0; i < allProperties.length; i++) { //prints unmortgaged properties
						if (allProperties[i].mortgaged && allProperties[i].owner.equals(player)) console.append(allProperties[i].propertyName);
					}
					
					System.out.print("Which property would you like to unmortgage? " + "\n");
					//need to reset these variables because we could be dealing with a street or property class
					currentPropertyName = sc.nextLine(); //user enters input
					currentProperty = null;
					currentStreet = null;//used to store and manipulate current street object
					ownsProperty = false; //used to determine if the user has entered a valid street
					currentPropertyIndex = 0;
					currentStreetIndex = 0;//index is needed later to save potential changes made to street
					
					for (int i = 0; i < allProperties.length; i++) { //information is gathered if street is valid
						if (currentPropertyName.equals(allProperties[i].propertyName) && allProperties[i].owner.equals(player)) {
							ownsProperty = true;
							if (allProperties[i] instanceof street) { //use street variables for street 
								currentStreetName = currentPropertyName;
								currentStreet = (street) allProperties[i];
								currentStreetIndex = i;
								break;
							} else { //use property variables for property
								currentProperty = allProperties[i];
								currentPropertyIndex = i;
								break;
							}
						}
					}
					if (ownsProperty) {
						if (currentProperty == null) { //null current property means we have a street
							if (player.moneyBalance < ((currentStreet.buyPrice/2) + ((currentStreet.buyPrice/2) / 10))) {
								console.append("You do not have sufficient funds to unmortage this property" + "\n");
							} else {
								currentStreet.mortgaged = false;
								player.moneyBalance -= ((currentStreet.buyPrice/2) + ((currentStreet.buyPrice/2) / 10));
								allProperties[currentStreetIndex] = currentStreet;
							}
						} else { //not null current property means we have a property
							if (player.moneyBalance < ((currentProperty.buyPrice/2) + ((currentProperty.buyPrice/2) / 10))) {
								console.append("You do not have sufficient funds to unmortage this property" + "\n");
							} else {
								currentProperty.mortgaged = false;
								player.moneyBalance -= ((currentProperty.buyPrice/2) + ((currentProperty.buyPrice/2) / 10));
								allProperties[currentPropertyIndex] = currentProperty;
							}
						}
					} else console.append("You have entered a property you do not own or is not mortgaged" + "\n");
					break;
				case "Exit": managing = false; break;
				default: console.append("Please enter valid command" + "\n");
				}
			}
			audit();
		}
		static void manageUnownedProperty(player player, property property){
			if(player.moneyBalance < property.buyPrice){
				Scanner sc = new Scanner(System.in);
				boolean tempCommand = true;
				console.append("You do not have enough money to buy this property, would you like to sell properties to raise money? (Yes/No)" + "\n"); 
				while(tempCommand){
					String command = sc.nextLine();
					switch(command){
					case "Yes": propertyManagement(player); 
					if(player.moneyBalance < property.buyPrice){
						console.append("You still don't have enough money to buy this property! Would you like to try to raise more money?" + "\n");
					} else{
						console.append("You have now raised the needed money to buy " + property.propertyName + "!" + "\n");
						buyProperty(player, property);
						tempCommand = false;
					} break;
					case "No": auctionProperty(property); tempCommand = false; break;
					default: console.append("Please enter 'Yes' or 'No'!" + "\n");
					}
				}
			}
			else {
				//Comment
				console.append(property.propertyName + " is unowned, would you like to buy it, or auction it?" + "\n");
				boolean tempCommand = true;
				Scanner sc = new Scanner(System.in);
				while(tempCommand){
					String command = sc.nextLine();
					switch(command){
					case "Buy": buyProperty(player, property); tempCommand = false; break;
					case "Auction": auctionProperty(property); tempCommand = false; break;
					default: console.append("Please enter a valid command" + "\n"); 
					}
				}
			}
			audit();
		}

		static void buyProperty(player player, property property){
			property.owned = true;
			property.owner = player; //property is transfered to player
			player.moneyBalance -= property.buyPrice; //player pays required funds
			//prints out the property bought and now account balance
			console.append(player.playerName + " has bought " + property.propertyName + " for $" + property.buyPrice + " and now has an account balance of $" + player.moneyBalance);
			//add the property to player's owned property's array
			if(player.ownedProperties.length == 0 || player.ownedProperties == null){
				property [] temp = new property[1]; //new owned properties list is created if this is the player's first property
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
			System.out.print(player.playerName + " now owns the following properties: " + "\n");
			for(int a = 0; a < player.ownedProperties.length; a++){
				if(a == player.ownedProperties.length -1){
					System.out.print(player.ownedProperties[a].propertyName + "\n" + "\n");
				}
				else{
					System.out.print(player.ownedProperties[a].propertyName + ", " + "\n");
				}
			}
		}

		static void auctionProperty(property property){ //players bid for property
			console.append("You have opted not to buy the property and the property will go to auction! Beginning auction now!" + "\n");
			boolean tempvar = true;
			int tempprice = 0;
			player templeader = null;
			int counter = 0; // this will equal the player list length if one player outbids the others
			
			while(tempvar){
				for(int i = 0; i < playerList.length; i++){
					if(counter == playerList.length && tempprice != 0){
						tempvar = false; continue;
					}
					if(counter == playerList.length && tempprice == 0){
						console.append("Somebody will have to bid on this property to continue the game!" + "\n");
						counter = 0;
					}
					Scanner sc = new Scanner(System.in);
					console.append(playerList[i].playerName + " would you like to bid on " + property.propertyName + "? The current bid is " + tempprice);
					String command = sc.nextLine();
					switch(command){
					case "Yes": 
						console.append("What would you like your bid to be?" + "\n");
						int bidamount = sc.nextInt(); 
						if(bidamount > tempprice && bidamount <= playerList[i].moneyBalance){
							templeader = playerList[i];
							tempprice = bidamount; 
							counter = 1;
						}
						else if(playerList[i].moneyBalance < bidamount){
							console.append("Insufficient funds to make a bid!" + "\n");
						}
						else{
							console.append("Your bid was not high enough to out bid the leader!" + "\n");
							counter += 1;
						}
						break;
					case "No": counter +=1; console.append("You chose not to bid on this property during this round of bidding" + "\n"); break;
					default: counter +=1; console.append("You did not choose an appropriate option and therefore cannot bid on this property during this round of bidding" + "\n");
					}
				}
			}
			property.owned = true;
			property.owner = templeader;
			templeader.moneyBalance -= tempprice; //property is transfered and player pays their auction bid
			if(templeader.ownedProperties.length == 0 || templeader.ownedProperties == null){ //same structure for buying properties
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
			System.out.print(templeader.playerName + " now owns the following properties: " + "\n");
			for(int a = 0; a < templeader.ownedProperties.length; a++){
				if(a == templeader.ownedProperties.length -1){
					System.out.print(templeader.ownedProperties[a].propertyName + "\n" + "\n");
				}
				else{
					System.out.print(templeader.ownedProperties[a].propertyName + ", " + "\n");
				}
			}
		}
		static void payRent(player payer, player reciever, property property) {
			int rentam = 0;
			if(payer == reciever){
				//player does not pay their own rent
			}
			else{
				if(property.mortgaged == false){
					if(property instanceof street) {
					street temp;
					temp = (street)property;
					if(property.playerSet == false) {
						rentam=property.rentArray[0];
					}
					else if(property.playerSet == true && temp.numHouses ==0) {
						rentam=property.rentArray[0]*2;
					}
					else if(temp.hotel == true){
						rentam=property.rentArray[5];
					}
					else if(temp.numHouses > 0) {
						rentam=property.rentArray[temp.numHouses];
					}
					}
					else {
						if(property.type.equals("Railroad" + "\n")) {
							//Because when you run the for loop
							//the condition will be true at least once 
							//i.e. at the position of that property in the array of allProperties
							int countRailOwned = -1;
							for(int i = 0; i < allProperties.length; i++) {
								if(allProperties[i].type.equals("Railroad" + "\n") && allProperties[i].owner==property.owner) {
									countRailOwned +=1;
								}
							}
							rentam = property.rentArray[countRailOwned];
						}
						else if(property.type.equals("Utility" + "\n")) {
							int countUtilOwned = -1;
							for(int i = 0; i < allProperties.length; i++) {
								if(allProperties[i].type.equals("Utility" + "\n") && allProperties[i].owner==property.owner) {
									countUtilOwned +=1;
								}
							}
							rentam = property.rentArray[countUtilOwned];
						}
					}
					boolean tempBool = true;
					while(tempBool){
						if(payer.moneyBalance < rentam){
							int currentAssets = payer.moneyBalance;
							for(int d = 0; d < allProperties.length; d++){
								if(allProperties[d].mortgaged == false && allProperties[d].owner == payer){
									currentAssets += (payer.ownedProperties[d].buyPrice/2);
								}
							}
							if(currentAssets < rentam){
								console.append(payer.playerName + " does not have enough funds to pay " + reciever.playerName + "! All their assets will be transfered to " + reciever.playerName + "!" + "\n");
								for(int i = 0; i < allProperties.length; i++){
									if(allProperties[i].owner == payer) {
									allProperties[i].owner = reciever; 
									console.append(reciever.playerName + " now owns " + allProperties[i].propertyName);
									}
								}
								console.append(reciever.playerName + " will recieve " + payer.moneyBalance + " in cash from " + payer.playerName + "!" + "\n");
								reciever.moneyBalance += payer.moneyBalance;
								payer.moneyBalance = -1;
								tempBool=false;
							}
							else if(currentAssets >= rentam && payer.moneyBalance <= rentam){
								console.append("You still have property's that can be used to collect the neccesary cash! Please mortgage them so the game can continue" + "\n");
								propertyManagement(payer); continue; 
							}
						}
						else{
							payer.moneyBalance -= rentam; 
							reciever.moneyBalance += rentam;
							console.append(payer.playerName + " has payed $" + rentam + " to " + reciever.playerName + " in rent!" + "\n");
							tempBool=false;
						}
					}
				}
			}
		}
		static void payRentChance(player payer, player reciever, property property) {
			int rentam = 0;
			if(payer == reciever){
				//player does not pay their own rent
			}
			else{
				if(property.mortgaged == false){
					if(property instanceof street) {
					street temp;
					temp = (street)property;
					if(property.playerSet == false) {
						rentam=property.rentArray[0];
					}
					else if(property.playerSet == true && temp.numHouses ==0) {
						rentam=property.rentArray[0]*2;
					}
					else if(temp.hotel == true){
						rentam=property.rentArray[5];
					}
					else if(temp.numHouses > 0) {
						rentam=property.rentArray[temp.numHouses];
					}
					}
					else {
						if(property.type.equals("Railroad" + "\n")) {
							//Because when you run the for loop
							//the condition will be true at least once 
							//i.e. at the position of that property in the array of allProperties
							int countRailOwned = -1;
							for(int i = 0; i < allProperties.length; i++) {
								if(allProperties[i].type.equals("Railroad" + "\n") && allProperties[i].owner==property.owner) {
									countRailOwned +=1;
								}
							}
							rentam = property.rentArray[countRailOwned];
						}
						else if(property.type.equals("Utility" + "\n")) {
							int countUtilOwned = -1;
							for(int i = 0; i < allProperties.length; i++) {
								if(allProperties[i].type.equals("Railroad" + "\n") && allProperties[i].owner==property.owner) {
									countUtilOwned +=1;
								}
							}
							rentam = property.rentArray[countUtilOwned];
						}
					}
					boolean tempBool = true;
					while(tempBool){
						if(payer.moneyBalance < rentam){
							int currentAssets = payer.moneyBalance;
							for(int d = 0; d < allProperties.length; d++){
								if(allProperties[d].mortgaged == false && allProperties[d].owner == payer){
									currentAssets += (payer.ownedProperties[d].buyPrice/2);
								}
							}
							if(currentAssets < rentam){
								console.append(payer.playerName + " does not have enough funds to pay " + reciever.playerName + "! All their assets will be transfered to " + reciever.playerName + "!" + "\n");
								for(int i = 0; i < allProperties.length; i++){
									if(allProperties[i].owner == payer) {
									allProperties[i].owner = reciever; 
									console.append(reciever.playerName + " now owns " + allProperties[i].propertyName);
									}
								}
								console.append(reciever.playerName + " will recieve " + payer.moneyBalance + " in cash from " + payer.playerName + "!" + "\n");
								reciever.moneyBalance += payer.moneyBalance;
								payer.moneyBalance = -1;
								tempBool=false;
							}
							else if(currentAssets >= rentam && payer.moneyBalance <= rentam){
								console.append("You still have property's that can be used to collect the neccesary cash! Please mortgage them so the game can continue" + "\n");
								propertyManagement(payer); continue; 
							}
						}
						else{
							payer.moneyBalance -= rentam; 
							reciever.moneyBalance += rentam;
							console.append(payer.playerName + " has payed $" + rentam + " to " + reciever.playerName + " in rent!" + "\n");
							tempBool=false;
						}
					}
				}
			}
		}
		static void audit() {
			//check for brown monopoly
			if (allProperties[0].owner == allProperties[1].owner) {
				allProperties[0].playerSet = true;
				allProperties[1].playerSet = true;
			} else {
				allProperties[0].playerSet = false;
				allProperties[1].playerSet = false;
			}
			//light blue monopoly
			if (allProperties[3].owner == allProperties[4].owner && allProperties[3].owner == allProperties[5].owner) {
				allProperties[3].playerSet = true;
				allProperties[4].playerSet = true;
				allProperties[5].playerSet = true;
			} else {
				allProperties[3].playerSet = false;
				allProperties[4].playerSet = false;
				allProperties[5].playerSet = false;
			}
			//pink monopoly
			if (allProperties[6].owner == allProperties[8].owner && allProperties[6].owner == allProperties[9].owner) {
				allProperties[6].playerSet = true;
				allProperties[8].playerSet = true;
				allProperties[9].playerSet = true;
			} else {
				allProperties[6].playerSet = false;
				allProperties[8].playerSet = false;
				allProperties[9].playerSet = false;
			}
			//orange monopoly
			if (allProperties[11].owner == allProperties[12].owner && allProperties[11].owner == allProperties[13].owner) {
				allProperties[11].playerSet = true;
				allProperties[12].playerSet = true;
				allProperties[13].playerSet = true;
			} else {
				allProperties[11].playerSet = false;
				allProperties[12].playerSet = false;
				allProperties[13].playerSet = false;
			}
			//red monopoly
			if (allProperties[14].owner == allProperties[15].owner && allProperties[14].owner == allProperties[16].owner) {
				allProperties[14].playerSet = true;
				allProperties[15].playerSet = true;
				allProperties[16].playerSet = true;
			} else {
				allProperties[14].playerSet = false;
				allProperties[15].playerSet = false;
				allProperties[16].playerSet = false;
			}
			//yellow monopoly
			if (allProperties[18].owner == allProperties[19].owner && allProperties[18].owner == allProperties[21].owner) {
				allProperties[18].playerSet = true;
				allProperties[19].playerSet = true;
				allProperties[21].playerSet = true;
			} else {
				allProperties[18].playerSet = false;
				allProperties[19].playerSet = false;
				allProperties[21].playerSet = false;
			}
			//green monopoly
			if (allProperties[22].owner == allProperties[23].owner && allProperties[22].owner == allProperties[24].owner) {
				allProperties[22].playerSet = true;
				allProperties[23].playerSet = true;
				allProperties[24].playerSet = true;
			} else {
				allProperties[22].playerSet = false;
				allProperties[23].playerSet = false;
				allProperties[24].playerSet = false;
			}
			//check for dark blue monopoly
			if (allProperties[26].owner == allProperties[27].owner) {
				allProperties[26].playerSet = true;
				allProperties[27].playerSet = true;
			} else {
				allProperties[26].playerSet = false;
				allProperties[27].playerSet = false;
			}
			//utility set
			if (allProperties[20].owner == allProperties[7].owner) {
				allProperties[20].playerSet = true;
				allProperties[7].playerSet = true;
			} else {
				allProperties[20].playerSet = false;
				allProperties[7].playerSet = false;
			}


		}

		static void rollDice() { //method is not used 
			dice1 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
			dice2 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
		}
		void useGetOutOfJailFree (player player) { //not used
			player.inJail = false;
			player.JailFreeCards -= 1;
		}

		void passGo(player player) { //not used
			player.moneyBalance += 200;
		}

		//community chest deck
		static void drawCommunityChest (player player) { //corresponding card data can be found in main method
			Random rand = new Random();
			int card = rand.nextInt(16); //draw random card
			
			switch (card) {
			case 0: console.append(communityChestDescriptions[0]); player.moneyBalance += 100; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			case 1: console.append(communityChestDescriptions[1]); player.moneyBalance += 50; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 2: console.append(communityChestDescriptions[2]); player.inJail = true; player.boardPosition = 10; break;
			case 3: console.append(communityChestDescriptions[3]); player.moneyBalance -= 100; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			case 4: console.append(communityChestDescriptions[4]); player.moneyBalance += 20; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			case 5: console.append(communityChestDescriptions[5]); player.moneyBalance += 10; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			case 6: console.append(communityChestDescriptions[6]); player.JailFreeCards +=1; break; 
			case 7: console.append(communityChestDescriptions[7]); 
			for(int i = 0; i < playerList.length; i++){
				if(playerList[i] == player){
				}
				else{
					playerList[i].moneyBalance += 10;
					player.moneyBalance -= 10;
					console.append(player.playerName + " paid " + playerList[i].playerName + " $10 dollars" + "\n");
				}
			}
			for(int i = 0; i < playerList.length; i++){
				console.append(playerList[i].playerName + " new account balance is " + playerList[i].moneyBalance);
			}
			break; 
			case 8: console.append(communityChestDescriptions[8]); player.moneyBalance += 200; player.boardPosition = 0; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			case 9: console.append(communityChestDescriptions[9]); player.moneyBalance -=50; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 10: console.append(communityChestDescriptions[10]); player.moneyBalance += 25; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 11: console.append(communityChestDescriptions[11]); player.moneyBalance += 100; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 12: console.append(communityChestDescriptions[12]); player.moneyBalance -= 50; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 13: console.append(communityChestDescriptions[13]); player.moneyBalance += 100; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break; 
			case 14: console.append(communityChestDescriptions[14]); 
			int paymentTracker = 0;
			for(int i = 0; i < player.ownedProperties.length; i++){
				if(player.ownedProperties[i] instanceof street){
					street temp = (street) player.ownedProperties[i];
					//if your property has hotels, pay 115
					if(temp.hotel == true){
						player.moneyBalance -= 115;
						paymentTracker += 115;
					}
					//otherwise count the number of houses
					else{
						int houseTrack = temp.numHouses;
						player.moneyBalance -= houseTrack * 40;
						paymentTracker += houseTrack * 40;
					}
				}
			}
			if(paymentTracker == 0){
				console.append("Luckily you didn't own any houses or hotels and didn't pay any fine!" + "\n");
			}
			else{
				console.append("You paid " + paymentTracker + " in fines!!" + "\n");
			}
			break; 
			case 15: console.append(communityChestDescriptions[15]); player.moneyBalance += 200; console.append(player.playerName + " has a new account balance of " + player.moneyBalance); break;
			}
		}

		//chance deck
		//most of these cards involve moving to a new space and paying increased rent
		//refer to main method for particulars
		static void drawChance (player player) {
			Random rand = new Random();
			int card = rand.nextInt(16); //draw random card 
			
			switch (card) { //corresponding card data found in main method
			case 0: console.append(chanceDescriptions[0]); if(player.boardPosition >= 12 && player.boardPosition < 28){
				player.boardPosition = 28;
				if(allProperties[20].owned==true){
					payRentChance(player, allProperties[20].owner, allProperties[20]); //pay more rent if property is owned
				}
				else{
					manageUnownedProperty(player, allProperties[20]); //just in case property is unowned
				}
			} else{
				if(player.boardPosition > 27){
					player.moneyBalance +=200; //player passes go
				}
				player.boardPosition = 12; 
				if(allProperties[7].owned==true){
					payRentChance(player, allProperties[7].owner, allProperties[7]);
				}
				else{
					manageUnownedProperty(player, allProperties[7]);
				}
			} break;
			case 1: console.append(chanceDescriptions[1]); if(player.boardPosition >= 35 && player.boardPosition < 5){
				if(player.boardPosition > 34){
					player.moneyBalance += 200;
				}
				player.boardPosition = 5;
				if(allProperties[2].owned==true){
					payRentChance(player, allProperties[2].owner, allProperties[2]);
				}
				else{
					manageUnownedProperty(player, allProperties[2]);
				}
			} 
			else if(player.boardPosition >= 5 && player.boardPosition < 15){
				player.boardPosition = 15;
				if(allProperties[10].owned==true){
					payRentChance(player, allProperties[10].owner, allProperties[10]);
				}
				else{
					manageUnownedProperty(player, allProperties[10]);
				}
			} else if(player.boardPosition >= 15 && player.boardPosition < 25){
				player.boardPosition = 25;
				if(allProperties[17].owned==true){
					payRentChance(player, allProperties[17].owner, allProperties[17]);
				}
				else{
					manageUnownedProperty(player, allProperties[17]);
				}
			} else {
				player.boardPosition = 35;
				if(allProperties[25].owned==true){
					payRentChance(player, allProperties[25].owner, allProperties[25]);
				}
				else{
					manageUnownedProperty(player, allProperties[25]);
				}
			} break; 
			case 2: console.append(chanceDescriptions[2]); 
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
				console.append(playerList[i].playerName + " has an account balance of " + playerList[i].moneyBalance);
			}
			break; 
			case 3: console.append(chanceDescriptions[3]); player.inJail = true; player.boardPosition = 10; break;
			case 4: console.append(chanceDescriptions[4]); player.JailFreeCards +=1; break; 
			case 5: console.append(chanceDescriptions[5]); if(player.boardPosition >= 35 && player.boardPosition < 5){
				if(player.boardPosition > 34){
					player.moneyBalance += 200;
				}
				player.boardPosition = 5;
				if(allProperties[2].owned==true){
					payRentChance(player, allProperties[2].owner, allProperties[2]);
				}
				else{
					manageUnownedProperty(player, allProperties[2]);
				}
			} 
			else if(player.boardPosition >= 5 && player.boardPosition < 15){
				player.boardPosition = 15;
				if(allProperties[10].owned==true){
					payRentChance(player, allProperties[10].owner, allProperties[10]);
				}
				else{
					manageUnownedProperty(player, allProperties[10]);
				}
			} else if(player.boardPosition >= 15 && player.boardPosition < 25){
				player.boardPosition = 25;
				if(allProperties[17].owned==true){
					payRentChance(player, allProperties[17].owner, allProperties[17]);
				}
				else{
					manageUnownedProperty(player, allProperties[17]);
				}
			} else {
				player.boardPosition = 35;
				if(allProperties[25].owned==true){
					payRentChance(player, allProperties[25].owner, allProperties[25]);
				}
				else{
					manageUnownedProperty(player, allProperties[25]);
				}
			} break; 
			case 6: console.append(chanceDescriptions[6]); player.moneyBalance += 50; break; 
			case 7: console.append(chanceDescriptions[7]); player.moneyBalance += 150; break; 
			case 8: console.append(chanceDescriptions[8]); player.boardPosition -= 3; 
			if(player.boardPosition == 4){
				player.moneyBalance -= 200;
				console.append("You paid $200 in income tax!" + "\n");
			}
			else if(player.boardPosition == 19){
				console.append("You are at board position " + player.boardPosition + ", " + allProperties[13].propertyName); 
				if(allProperties[13].owned==false){
					manageUnownedProperty(player, allProperties[13]);
				}
				else{
					 payRent(player, allProperties[13].owner, allProperties[13]); 
				}
			}
			else{
				drawCommunityChest(player);
			} break;
			case 9: console.append(chanceDescriptions[9]); if(player.boardPosition > 24){
				player.moneyBalance += 200;
				player.boardPosition = 24;
			}
			else{
				player.boardPosition = 24;
			} 
			console.append("You are at board position " + player.boardPosition + ", " + allProperties[16].propertyName); 
			if(allProperties[16].owned==false){
				manageUnownedProperty(player, allProperties[16]);
			}
			else{
				 payRent(player, allProperties[16].owner, allProperties[16]); 
			} break; 
			case 10: console.append(chanceDescriptions[10]); if(player.boardPosition > 11){
				player.moneyBalance += 200;
				player.boardPosition = 11;
			}
			else{
				player.boardPosition = 11;
			} 
			console.append("You are at board position " + player.boardPosition + ", " + allProperties[6].propertyName); 
			if(allProperties[6].owned==false){
				manageUnownedProperty(player, allProperties[6]);
			}
			else{
				 payRent(player, allProperties[6].owner, allProperties[6]); 
			} break; 
			case 11: console.append(chanceDescriptions[11]); player.moneyBalance -= 15; break; 
			case 12: console.append(chanceDescriptions[12]); player.boardPosition = 39; break;
			case 13: console.append(chanceDescriptions[13]); player.boardPosition = 0; player.moneyBalance +=200; break;
			case 14: console.append(chanceDescriptions[14]); if(player.boardPosition > 5){
				player.moneyBalance += 200;
				player.boardPosition = 5;
			}
			else{
				player.boardPosition = 5;
			} 
			console.append("You are at board position " + player.boardPosition + ", " + allProperties[2].propertyName); 
			if(allProperties[2].owned==false){
				manageUnownedProperty(player, allProperties[2]);
			}
			else{
				 payRent(player, allProperties[2].owner, allProperties[2]); 
			} break; 
			case 15: console.append(chanceDescriptions[15]); 
			int paymentTracker = 0;
			for(int i = 0; i < player.ownedProperties.length; i++){
				if(player.ownedProperties[i] instanceof street){
					street temp = (street) player.ownedProperties[i];
					//if your property has hotels, pay 115
					if(temp.hotel == true){
						player.moneyBalance -= 100;
						paymentTracker += 100;
					}
					//otherwise count the number of houses
					else{
						int houseTrack = temp.numHouses;
						player.moneyBalance -= houseTrack * 50;
						paymentTracker += houseTrack * 50;
					}
				}
			}
			if(paymentTracker == 0){
				console.append("Luckily you didn't own any houses or hotels and didn't pay any fine!" + "\n");
			}
			else{
				console.append("You paid " + paymentTracker + " in fines!!" + "\n");
			}
			break; //again have to integrate houses, probably using loops
			}
		}

			public static class player {
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
				int rent;
				String type;
				boolean mortgaged = false;
				boolean playerSet = false;
				player owner;
				int[] rentArray;
				
				
				property(String name, int position, int bPrice, int[] rentArray, String type) {
					propertyName = name;
					boardPosition = position;
					buyPrice = bPrice;
					this.rentArray = rentArray;
					this.type = type;
				}
			}
			
			public static class street extends property {
				int numHouses = 0;
				boolean hotel = false;
				int housePrice; 
				
				//Creating a constructor for subclass
				street(String name, int position, int bPrice, int[] rentArray, String type, int hPrice) {
					super(name, position, bPrice, rentArray, type);
					propertyName = name;
					boardPosition = position;
					buyPrice = bPrice;
					this.rentArray = rentArray;
					this.type = type;
					housePrice = hPrice;
				}
				
			}

			public static void main(String[] args) {		
				// creating the array of all properties
				//brown properties
				int[] b1 = {2,10,30,90,160,250};
				int[] b2 = {4,20,60,180,320,450}; 
				int[] lb12 = {6,30,90,270,400,550};
				int[] lb3 = {8,40,100,300,450,600};
				int[] p12 = {10,50,150,450,625,750};
				int[] p3 = {12,60,180,500,700,900};
				int[] o12 = {14,70,200,550,750,950};
				int[] o3 = {16,80,220,600,800,1000};
				int[] r12 = {18,90,250,700,875,1050};
				int[] r3 = {20,100,300,750,925,1100};
				int[] y12 = {22,110,330,800,975,1150};
				int[] y3= {24,120,360,850,1025,1200};
				int[] g12= {26,130,390,900,1100,1275};
				int[] g3= {28,150,450,1000,1200,1400};
				int[] bl1= {35,175,500,1100,1300,1500};
				int[] bl2= {50,200,600,1400,1700,2000};
				int[] rail={25,50,75,100};
				int[] utility = {4, 10};
				
		 		allProperties[0] = new street("Mediterranean Avenue", 1, 60, b1, "Brown", 50);
				allProperties[1] = new street("Baltic Avenue", 3, 60, b2, "Brown", 50);
				//first rail
				allProperties[2] = new property("Reading Railroad", 5, 200, rail, "Railroad" + "\n");
				//light blue
				allProperties[3] = new street("Oriental Avenue", 6, 100, lb12, "Light Blue", 50);
				allProperties[4] = new street("Vermont Avenue", 8, 100, lb12, "Light Blue", 50);
				allProperties[5] = new street("Conneticut Avenue", 9, 120, lb3, "Light Blue", 50);
				//first pink
				allProperties[6] = new street("St. Charles Place", 11, 140, p12, "Pink", 100);
				//first utility
				allProperties[7] = new property("Electric Company", 12, 150, utility, "Utility" + "\n");
				//rest of pink
				allProperties[8] = new street("States Avenue", 13, 140, p12, "Pink", 100);
				allProperties[9] = new street("Virginia Avenue", 14, 160, p3, "Pink", 100);
				//second rail
				allProperties[10] = new property("Pennsylvania Railroad", 15, 200, rail, "Railroad" + "\n");
				//orange properties
				allProperties[11] = new street("St. James Place", 16, 180, o12, "Orange", 100);
				allProperties[12] = new street("Tenessee Avenue", 18, 180, o12, "Orange", 100);
				allProperties[13] = new street("New York Avenue", 19, 200, o3, "Orange", 100);
				//red properties
				allProperties[14] = new street("Kentucky Avenue", 21, 220, r12, "Red", 150);
				allProperties[15] = new street("Indiana Avenue", 23, 220, r12, "Red", 150);
				allProperties[16] = new street("Illinois Avenue", 24, 240, r3, "Red", 150);
				//third rail (don't step on it!)
				allProperties[17] = new property("B. & O. Railroad", 25, 200, rail, "Railroad" + "\n");
				//first two yellow
				allProperties[18] = new street("Atlantic Avenue", 26, 260, y12, "Yellow", 150);
				allProperties[19] = new street("Ventor Avenue", 27, 260, y12, "Yellow", 150);
				//second utility
				allProperties[20] = new property("Water Works", 28, 150, utility, "Utility" + "\n");
				//last yellow
				allProperties[21] = new street("Marvin Gardens", 29, 280, y3, "Yellow", 150);
				//green properties
				allProperties[22] = new street("Pacific Avenue", 31, 300, g12, "Green", 200);
				allProperties[23] = new street("North Carolina Avenue", 32, 300, g12, "Green", 200);
				allProperties[24] = new street("Pennsylvania Avenue", 34, 320, g3, "Green", 200);
				//last rail
				allProperties[25] = new property("Short Line", 35, 200, rail, "Railroad" + "\n");
				//finally dark blue
				allProperties[26] = new street("Park Place", 37, 350, bl1, "Dark Blue", 200);
				allProperties[27] = new street("Boardwalk", 39, 400, bl2, "Dark Blue", 200);
				
				//creation of community chest card descriptions
				communityChestDescriptions[0] = "You set aside time every week to hand out with your elderly neighbor; you've heard some amazing stories!\nCOLLECT $100.\n";
				communityChestDescriptions[1] = "You organize a group to clean up your town's walking path.\nCOLLECT $50.\n";
				communityChestDescriptions[2] = "Blasting music late at night? Your neighbors do not approve.\nGO TO JAIL. GO DIRECTLY TO JAIL. DO NOT PASS GO. DO NOT COLLECT $200.\n";
				communityChestDescriptions[3] = "You go to the local school fundraiser, but you forget to close your windows!\nPAY $100\n";
				communityChestDescriptions[4] = "You help your neighbor bring in her groceries. She makes you lunch to say thanks!\nCOLLECT $20\n";
				communityChestDescriptions[5] = "You volunteer at a blood drive. There are free cookies!\nCOLLECT $10\n";
				communityChestDescriptions[6] = "You rescue a puppy, and you feel rescued, too!\nGET OUT OF JAIL FREE\nThis card may be kept until needed, traded, or sold.\n";
				communityChestDescriptions[7] = "You organize a block party so people on your street can get to know each other.\nCOLLECT $10 FROM EACH PLAYER.\n";
				communityChestDescriptions[8] = "Just when you think you can't go another step, you finish the foot race and raise money for your local hospital!\nADVANCE TO GO. COLLECT $200.\n";
				communityChestDescriptions[9] = "You buy a few bags of cookies from that school bake sale. Yum!\nPAY $50.\n";
				communityChestDescriptions[10] = "You organize a bake sale for your local school.\nCOLLECT $25.\n";
				communityChestDescriptions[11] = "You spend the day playing games with kids at a local children's hospital.\nCOLLECT $100.\n";
				communityChestDescriptions[12] = "Your fuzzy friends at the animal shelter will be thankful for your donation.\nPAY $50.\n";
				communityChestDescriptions[13] = "You help build a new school playground, then you get to test the slide!\nCOLLECT $100.\n";
				communityChestDescriptions[14] = "You should have volunteered for that home improvement project, you would have learned valuable skills!\nFOR EACH HOUSE YOU OWN, PAY $40.\nFOR EACH HOTEL YOU OWN, PAY $115\n";
				communityChestDescriptions[15] = "You help your neighbors clean up their yards after a big storm.\nCOLLECT $200.\n";
				
				//creation of chance cards descriptions
				chanceDescriptions[0] = "ADVANCE TO THE NEAREST UTILITY.\nIf UNOWNED, you may buy it from the Bank.\nIf OWNED, roll the dice, and pay the owner 10 times your roll.\nIF YOU PASS GO, COLLECT $200.\n";
				chanceDescriptions[1] = "ADVANCE TO THE NEAREST RAILROAD.\nIf UNOWNED, you may buy it from the bank.\nIf OWNED, pay the owner twice the rent to which they are otherwise entitled.\nIF YOU PASS GO, COLLECT $200.\n";
				chanceDescriptions[2] = "YOU HAVE BEEN ELECTED CHAIRPERSON OF THE BOARD.\nPAY EACH PLAYER $50.\n";
				chanceDescriptions[3] = "GO TO JAIL. GO DIRECTLY TO JAIL.\nDO NOT PASS GO. DO NOT COLLECT $200.\n";
				chanceDescriptions[4] = communityChestDescriptions[6];
				chanceDescriptions[5] = chanceDescriptions[1];
				chanceDescriptions[6] = "BANK PAYS YOU DIVIDEND OF $50.\n";
				chanceDescriptions[7] = "YOUR BUILDING LOAN MATURES.\nCOLLECT $150.\n";
				chanceDescriptions[8] = "GO BACK THREE SPACES\n";
				chanceDescriptions[9] = "ADVANCE TO ILLINOIS AVENUE.\nIF YOU PASS GO, COLLECT $200.\n";
				chanceDescriptions[10] = "ADVANCE TO ST. CHARLES PLACE.\nIF YOU PASS GO, COLLECT $200\n";
				chanceDescriptions[11] = "SPEEDING FINE.\nPAY $15\n";
				chanceDescriptions[12] = "ADVANCE TO BOARDWALK.\n";
				chanceDescriptions[13] = "ADVANCE TO GO.\nCOLLECT $200.\n";
				chanceDescriptions[14] = "TAKE A TRIP TO READING RAILROAD.\nIF YOU PASS GO, COLLECT $200.\n";
				chanceDescriptions[15] = "MAKE GENERAL REPAIRS ON ALL YOUR PROPERTY:\nFOR EACH HOUSE, PAY $50.\nFOR EACH HOTEL, PAY $100.\n";
				
				//creation of monopoly pieces
				monopolyPieces[0] = "battleship";
				monopolyPieces[1] = "car";
				monopolyPieces[2] = "penguin";
				monopolyPieces[3] = "top hat";
				monopolyPieces[4] = "duck";
				monopolyPieces[5] = "dog";
				monopolyPieces[6] = "cat";
				monopolyPieces[7] = "dinosaur";
				
				new TestWindow();
				
				//start of the program
				player firstPlayer = startGame(); //get first player
				gameon = true;
				while(gameon){
					int a = 0; //created to store the index of the first player
					for(int i = 0; i < playerList.length; i++){ //loop advances to first player in list so they can go first
						if(playerList[i] == firstPlayer){
							break; //exit loop once index of first player is found
						}
						else{
							a +=1;
						}
					}
					for(int i = a; i < playerList.length; i++){ //start first round from the index of first player
						game(playerList[i]);
					}
					boolean wholegame = true;
					while(wholegame){ //after the end of player list has been reached from the index of the first player, game can advance normally
						for(int i = 0; i < playerList.length;){
							game(playerList[i]);
							int currentAssets = playerList[i].moneyBalance;
							for(int d = 0; d < playerList[i].ownedProperties.length; d++){
								if(playerList[i].ownedProperties[d].mortgaged == false){
									currentAssets += (playerList[i].ownedProperties[d].buyPrice);
								}
							}
							if(currentAssets < 0){
								if(playerList.length == 2){
									//this should end the game
									if(i == 0){
										console.append("Congrats " + playerList[1].playerName + "! You have won the game!" + "\n"); 
										gameon = false;
										wholegame = false;
										break;
									}
									else{
										console.append("Congrats " + playerList[0].playerName + "! You have won the game!" + "\n"); 
										gameon = false;
										wholegame = false;
										break;
									}
								}
								else{
								player[] temp = new player[playerList.length-1];
								for(int y = 0; y < i; y++){
									temp[y] = playerList[y];
								}
								for(int z = playerList.length-1; z > i; z--){
									temp[z-1] = playerList[z];
								}
								playerList = temp;
								if(i == playerList.length){
									i = 0;
									continue;
								}
								else{
									continue;
								}
							}
							}
							else{
								i++;
							}
						}
					}
				}
			
			}
		}