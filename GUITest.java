package mono;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Random;

public class GUITest extends JFrame {

	static Dimension maxSizeScreen;
	static JButton btnBarter, btnPropertyManagement, btnRoll, endTurn;
	static JTextArea displaycurply, console;
	static JScrollPane sp;
	static double a, b;
	static int x, y, e, f, g, h;
	
	static int dice1, dice2;
	static player[] playerList;
	static player cPlayer;
	static int numplayers;
	static property[] allProperties = new property[28];
	static String[] communityChestDescriptions = new String[16];
	static String[] chanceDescriptions = new String[16];
	static String[] monopolyPieces = new String[8];
	static String winnerstr;
	String[] spaceNames = new String[40];
	
	GUITest(){
		getContentPane().setPreferredSize(maxSizeScreen);
		getContentPane().setBackground(new Color(192, 192, 192));
		getContentPane().setLayout(null);
		
		btnBarter = new JButton("Barter");
		btnBarter.setBounds(g, h-((f + (f/5))*3), e, f);
		getContentPane().add(btnBarter);
		
		btnPropertyManagement = new JButton("Property Management" );
		btnPropertyManagement.setBounds(g, h-((f + (f/5))*2), e, f);
		getContentPane().add(btnPropertyManagement);
		
		btnRoll = new JButton("Roll");
		btnRoll.setBounds(g, h-(f + (f/5)), e, f);
		getContentPane().add(btnRoll);
		
		endTurn = new JButton("End Turn");
		endTurn.setBounds(g, h, e, f);
		getContentPane().add(endTurn);
		
		displaycurply = new JTextArea("");
		displaycurply.setLineWrap(true);
		displaycurply.setBounds(g,f*2/5,e,((f + (f/5))*4)*2 + (3*f/2));
		displaycurply.setEditable(false);
		getContentPane().add(displaycurply);
		
		console = new JTextArea("");
		console.setLineWrap(true);
		console.setBounds(f/2,h-f,x-e-(f*13/8),f*2);
		console.setEditable(false);
		getContentPane().add(console);
		
		sp = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(f/2,h-f,x-e-(f*13/8),f*2);
		getContentPane().add(sp);
		
		JLabel board = new JLabel();
		board.setIcon(new ImageIcon("C:\\Users\\keega\\eclipse-workspace\\Monopoly\\src\\mono\\Monopoly700.jpg"));
		board.setBounds(10, 10, 715, 715);
		getContentPane().add(board);
		
		ActionListener3 monolistener= new ActionListener3();
		btnBarter.addActionListener(monolistener);
		btnPropertyManagement.addActionListener(monolistener);
		btnRoll.addActionListener(monolistener);
		//submit.addActionListener(monolistener);
		endTurn.addActionListener(monolistener);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class ActionListener3 implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		if(e.getSource()==btnBarter) {
			console.append("Barter coming soon!");
		}
		if(e.getSource()==btnPropertyManagement) {
			propertyManagement(cPlayer);
			console.append(cPlayer.playerName + " has $" + cPlayer.moneyBalance + "\n");
		}
		if(e.getSource()==btnRoll) {
			roll(cPlayer);
		}
		if(e.getSource()==endTurn) {
			if(cPlayer.rollTurn == true) {
				JOptionPane cp = new JOptionPane();
				cp.showMessageDialog(cp, "Please roll before ending your turn!");
			}
			else {
				cPlayer.turn=false;
			}
		}
		}
	}
	
	
	public static void main(String[] args) {
		maxSizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
		a = maxSizeScreen.getWidth();
		b = maxSizeScreen.getHeight();
		x = (int)a;
		y = (int)b;
		e = y/6;
		f = e / 3;
		g = x - (e + (e/4));
		h = y - e;
		
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
		allProperties[2] = new property("Reading Railroad", 5, 200, rail, "Railroad");
		//light blue
		allProperties[3] = new street("Oriental Avenue", 6, 100, lb12, "Light Blue", 50);
		allProperties[4] = new street("Vermont Avenue", 8, 100, lb12, "Light Blue", 50);
		allProperties[5] = new street("Conneticut Avenue", 9, 120, lb3, "Light Blue", 50);
		//first pink
		allProperties[6] = new street("St. Charles Place", 11, 140, p12, "Pink", 100);
		//first utility
		allProperties[7] = new property("Electric Company", 12, 150, utility, "Utility");
		//rest of pink
		allProperties[8] = new street("States Avenue", 13, 140, p12, "Pink", 100);
		allProperties[9] = new street("Virginia Avenue", 14, 160, p3, "Pink", 100);
		//second rail
		allProperties[10] = new property("Pennsylvania Railroad", 15, 200, rail, "Railroad");
		//orange properties
		allProperties[11] = new street("St. James Place", 16, 180, o12, "Orange", 100);
		allProperties[12] = new street("Tenessee Avenue", 18, 180, o12, "Orange", 100);
		allProperties[13] = new street("New York Avenue", 19, 200, o3, "Orange", 100);
		//red properties
		allProperties[14] = new street("Kentucky Avenue", 21, 220, r12, "Red", 150);
		allProperties[15] = new street("Indiana Avenue", 23, 220, r12, "Red", 150);
		allProperties[16] = new street("Illinois Avenue", 24, 240, r3, "Red", 150);
		//third rail (don't step on it!)
		allProperties[17] = new property("B. & O. Railroad", 25, 200, rail, "Railroad");
		//first two yellow
		allProperties[18] = new street("Atlantic Avenue", 26, 260, y12, "Yellow", 150);
		allProperties[19] = new street("Ventor Avenue", 27, 260, y12, "Yellow", 150);
		//second utility
		allProperties[20] = new property("Water Works", 28, 150, utility, "Utility");
		//last yellow
		allProperties[21] = new street("Marvin Gardens", 29, 280, y3, "Yellow", 150);
		//green properties
		allProperties[22] = new street("Pacific Avenue", 31, 300, g12, "Green", 200);
		allProperties[23] = new street("North Carolina Avenue", 32, 300, g12, "Green", 200);
		allProperties[24] = new street("Pennsylvania Avenue", 34, 320, g3, "Green", 200);
		//last rail
		allProperties[25] = new property("Short Line", 35, 200, rail, "Railroad");
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
		
		numplayers = getnumPlayers(new startGUI(maxSizeScreen, x, y));
		playerList = createPlayers(new startGUI2(maxSizeScreen, x, y, numplayers), numplayers);
		game(new GUITest());
		new endgamewindow(maxSizeScreen, x, y, winnerstr);
	}
	
	static int getnumPlayers(startGUI gui) {
		boolean temp = true;
		int playernum = 0;
		while(temp) {
			if(gui.numPlayers != 0) {
				playernum = gui.numPlayers;
				gui.dispose();
				temp = false;
			}
			else {
				gui.displayinst.setText("Please select the number of players you would like to continue with!");;
			}
		}
		return playernum;
	}
	
	static player[] createPlayers(startGUI2 gui, int x) {
		boolean temp = true;
		String[]a = new String[x];
		player[]b = new player[x];
		while(temp) {
			if(gui.names != null) {
				a = gui.names;
				gui.dispose();
				temp = false;
			}
			else {
				gui.displayinst2.setText("Please enter player names in the text box!");
			}
		}
		for(int i = 0; i < x; i++) {
			b[i] = new player();
			b[i].playerName = a[i];
		}
		return b;
	}
	
	static void game(GUITest gui){
		boolean gameon = true;
		while(gameon) {
		if(playerList.length == 1) {
			gameon = false;
		}
		else {
		for(int i = 0; i < playerList.length;) {
				gameturn(playerList[i]);
				if(playerList[i].moneyBalance < 0 && playerList.length > 2) {
					player[]temparray = new player[playerList.length-1];
					for(int a = 0; a < i; a++) {
						temparray[a] = playerList[a];
					}
					for(int b = playerList.length-1; b > i; b--) {
						temparray[b-1] = playerList[b];
					}
					playerList = temparray;
					continue;
				}
				else if(playerList[i].moneyBalance < 0 && playerList.length == 2) {
					//exitpoint of the program
					//maybe add a game over popup window or something here
					if(i == 0) {
						winnerstr = "Player " + playerList[1].playerName + " has won the game!";
						gameon=false; gui.dispose(); break; 
					}
					else {
						winnerstr = "Player " + playerList[0].playerName + " has won the game!";
						gameon=false; gui.dispose(); break;
					}
				}
				if(i == playerList.length - 1) {
					i = 0;
					continue;
				}
				else {
					i++;
				}
			}
		}
		}
}	
	static void gameturn(player player) {
		cPlayer = player;
		cPlayer.turn = true;
		cPlayer.rollTurn = true;
		boolean temp = true;
		while(temp) {
			if(cPlayer.turn == false) {
				temp = false;
				continue;
			}
			else {
				displaycurply.setText(player.playerName + "\nMoney: $" + player.moneyBalance + "\nPosition: " + player.boardPosition);
			}
		}
		player = cPlayer;
		cPlayer = null;
	}
	
	static void roll(player player) {
		
		int doubletracker = 0;
		boolean tempjailtrack = false; //this is made true if a player rolls doubles and the amount of doubles must be tracked
		
		while(player.rollTurn){
		dice1 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1); //gets a random number 1 through six inclusive
		console.append("Dice one is " + dice1 + "\n");
		dice2 = (int) Math.floor(Math.random() *(6 - 1 + 1) + 1);
		console.append("Dice two is " + dice2 + "\n");
		int movement = dice1+dice2; //roll two dice per turn
		
		if(player.inJail == false){ //if you aren't in jail, you move on the board
			player.boardPosition += movement;
			tempjailtrack = true; //start tracking amount of doubles rolled
		}
		
		if(dice1==dice2 && tempjailtrack==true){
			doubletracker += 1;
			if(doubletracker == 3){ //if your roll three doubles in a row, you go to jail!
				console.append("You rolled three doubles in a row, go to Jail!\n");
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
		case 0: console.append("You are at board position " + player.boardPosition + ", Pass Go, collect $200!\n"); break;
		case 1: console.append("You are at board position " + player.boardPosition + ", " + allProperties[0].propertyName+"\n"); 
			if(allProperties[0].owned==false){
				manageUnownedProperty(player, allProperties[0]); //this method is used to decide if a player buys or auctions property
			}
			else{
				 payRent(player, allProperties[0].owner, allProperties[0]); 
			} break;
		case 2: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now.\n"); 
		drawCommunityChest(player); break;
		case 3: console.append("You are at board position " + player.boardPosition + ", " + allProperties[1].propertyName+"\n"); 
			if(allProperties[1].owned==false){
				manageUnownedProperty(player, allProperties[1]);
			}
		else{
			 payRent(player, allProperties[1].owner, allProperties[1]); 
		} break;
		case 4: console.append("You are at board position " + player.boardPosition + ", income tax, pay $200!\n"); player.moneyBalance -= 200; break;
		case 5: console.append("You are at board position " + player.boardPosition + ", " + allProperties[2].propertyName + "\n"); 
		if(allProperties[2].owned==false){
			manageUnownedProperty(player, allProperties[2]);
		}
		else{
			 payRent(player, allProperties[2].owner, allProperties[2]); 
		} break;
		case 6: console.append("You are at board position " + player.boardPosition + ", " + allProperties[3].propertyName + "\n"); 
		if(allProperties[3].owned==false){
			manageUnownedProperty(player, allProperties[3]);
		}
		else{
			 payRent(player, allProperties[3].owner, allProperties[3]); 
		} break;
		case 7: console.append("You are at board position " + player.boardPosition + ", chance, drawing a chance card now." + "\n"); 
		drawChance(player); break;
		case 8: console.append("You are at board position " + player.boardPosition + ", " + allProperties[4].propertyName+ "\n"); 
		if(allProperties[4].owned==false){
			manageUnownedProperty(player, allProperties[4]);
		}
		else{
			 payRent(player, allProperties[4].owner, allProperties[4]); 
		} break;
		case 9: console.append("You are at board position " + player.boardPosition + ", " + allProperties[5].propertyName+ "\n"); 
		if(allProperties[5].owned==false){
			manageUnownedProperty(player, allProperties[5]);
		}
		else{
			 payRent(player, allProperties[5].owner, allProperties[5]); 
		} break;
		case 10: if(player.inJail == false) console.append("You are just visiting jail"+ "\n"); break;
		case 11: console.append("You are at board position " + player.boardPosition + ", " + allProperties[6].propertyName + "\n"); 
		if(allProperties[6].owned==false){
			manageUnownedProperty(player, allProperties[6]);
		}
		else{
			 payRent(player, allProperties[6].owner, allProperties[6]); 
		} break;
		case 12: console.append("You are at board position " + player.boardPosition + ", " + allProperties[7].propertyName +"\n"); 
		if(allProperties[7].owned==false){
			manageUnownedProperty(player, allProperties[7]);
		}
		else{
			 payRent(player, allProperties[7].owner, allProperties[7]); 
		} break;
		case 13: console.append("You are at board position " + player.boardPosition + ", " + allProperties[8].propertyName + "\n"); 
		if(allProperties[8].owned==false){
			manageUnownedProperty(player, allProperties[8]);
		}
		else{
			 payRent(player, allProperties[8].owner, allProperties[8]); 
		} break;
		case 14: console.append("You are at board position " + player.boardPosition + ", " + allProperties[9].propertyName + "\n"); 
		if(allProperties[9].owned==false){
			manageUnownedProperty(player, allProperties[9]);
		}
		else{
			 payRent(player, allProperties[0].owner, allProperties[9]); 
		} break;
		case 15: console.append("You are at board position " + player.boardPosition + ", " + allProperties[10].propertyName + "\n"); 
		if(allProperties[10].owned==false){
			manageUnownedProperty(player, allProperties[10]);
		}
		else{
			 payRent(player, allProperties[10].owner, allProperties[10]); 
		} break;
		case 16: console.append("You are at board position " + player.boardPosition + ", " + allProperties[11].propertyName + "\n"); 
		if(allProperties[11].owned==false){
			manageUnownedProperty(player, allProperties[11]);
		}
		else{
			 payRent(player, allProperties[11].owner, allProperties[11]); 
		} break;
		case 17: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now.\n"); 
		drawCommunityChest(player); break;
		case 18: console.append("You are at board position " + player.boardPosition + ", " + allProperties[12].propertyName + "\n"); 
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
		case 20: console.append("You are at board position " + player.boardPosition + ", free parking, enjoy your stay!" + "\n"); break;
		case 21: console.append("You are at board position " + player.boardPosition + ", " + allProperties[14].propertyName + "\n"); 
		if(allProperties[14].owned==false){
			manageUnownedProperty(player, allProperties[14]);
		}
		else{
			 payRent(player, allProperties[14].owner, allProperties[14]); 
		} break;
		case 22: console.append("You are at board position " + player.boardPosition + ", chance, drawing a chance card now." + "\n"); 
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
		case 30: player.inJail = true; console.append("You landed on go to jail, go to jail immediately!" + "\n"); break;
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
		case 33: console.append("You are at board position " + player.boardPosition + ", community chest, drawing a community chest card now." + "\n"); 
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
			console.append(player.playerName + " has exhaused all rolls!");
		}
		doubletracker = 0;
		player.rollTurn = false;
		}
	}
	
	static void manageUnownedProperty(player player, property property) {
		if(player.moneyBalance > property.buyPrice) {
			String [] options = {"Buy", "Auction"};
			JOptionPane cp = new JOptionPane();
			var command = cp.showOptionDialog(null, "Would you like to auction or buy " + property.propertyName + "?", "Unowned Property!", 0, 2, null, options, options[0]);
			if(command == 0) {
				buyProperty(player, property);
			}
			if(command == 1) {
				auctionProperty(property);
			}
		}
		else {
			console.append("Not enough money to buy the property, it will go to auction!\n");
			auctionProperty(property);
		}
		audit();
	}
	
	static void buyProperty(player player, property property) {
		property.owned = true;
		property.owner = player; //property is transfered to player
		player.moneyBalance -= property.buyPrice; //player pays required funds
		//prints out the property bought and now account balance
		console.append(player.playerName + " has bought " + property.propertyName + " for $" + property.buyPrice + " and now has an account balance of $" + player.moneyBalance);
		//prints out the owned properties
		console.append(player.playerName + " now owns the following properties: " + "\n");
		for(int a = 0; a < allProperties.length; a++){
			if(allProperties[a].owner == player){
				console.append(allProperties[a].propertyName + "\n");
			}
		}
	}
	
	static void auctionProperty(property property) {
		boolean temp = true;
		player winner = null;
		player leader = null;
		int bidam = 0;
		int counter = 0;
		int winningbid = 0;
		String [] options = {"Yes", "No"};
		while(temp) {
			for(int i = 0; i < playerList.length; i++) {
				String name = playerList[i].playerName;
				JOptionPane cp = new JOptionPane();
				var command = cp.showOptionDialog(null, name + " would you like to bid on " + property.propertyName + "?", "Auction!", 0, 2, null, options, options[0]);
				if(command == 0) {
					var am = JOptionPane.showInputDialog("Please enter your bid amount!");
					try {
						int bid = Integer.parseInt(am);
						if(bid > bidam) {
							bidam = bid;
							counter = 0;
							leader = playerList[i];
						}
						else {
							cp.showMessageDialog(cp, "Bid not high enough!");
							counter += 1;
						}
					}
					catch(InputMismatchException ie){
						cp.showMessageDialog(cp, "Wrong input type!");
					}
				}
				if(command == 1) {
					counter +=1;
				}
				if(counter == playerList.length && bidam ==0) {
					counter = 0;
				}
				if(counter == playerList.length-1 && bidam != 0) {
					winner = leader; winningbid = bidam; temp=false; break;
				}
			}
		}
		property.owned = true;
		property.owner = winner;
		winner.moneyBalance -= winningbid;
		console.append(winner.playerName + " has won the auction for " + property.propertyName + " and paid " + winningbid + "!\n");
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
	
	static void propertyManagement(player player) {
		String[] options = {"Buy","Sell","Mortgage","Unmortgage"};
		JOptionPane jp = new JOptionPane();
		var command = jp.showOptionDialog(null, "Please select an option:", "Property Manager", 0, 2, null, options, options[0]);
		if(command==0) {
			int counter = 0; // && allProperties[i].playerSet ==true
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i] instanceof street && allProperties[i].playerSet == true) {
					counter +=1;
				}
			}
			property [] temp = new property[counter];
			int index = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i] instanceof street && allProperties[i].playerSet == true) {
					temp[index] = allProperties[i];
					index += 1;
				}
			}
			if(temp.length < 1) jp.showMessageDialog(jp, "No properties available to buy houses or hotels!");
			if(temp.length > 0) {
			String[]tempnames = new String[temp.length];
			for(int i = 0; i < temp.length; i++) {
				tempnames[i] = temp[i].propertyName;
			}
			int selection = jp.showOptionDialog(null, "Please select a property!", "Property Manager", 0, 2, null, tempnames, tempnames[0]);
			int allpropindex = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].boardPosition == temp[selection].boardPosition) {
					allpropindex = i; break;
				}
			}
			street tempstreet = (street)temp[selection];
			String[]options2 = {"House", "Hotel"};
			var typeselection = jp.showOptionDialog(null, "Would you like to buy houses or a hotel?", "Property Manager", 0, 2, null, options2, options2[0]);
			if(typeselection == 0) {
				if(tempstreet.numHouses == 0) {
					String[]options3 = {"1", "2", "3", "4"};
					var houseselection = jp.showOptionDialog(null, "You don't have any houses on this property, how many would you like to buy?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice);
							tempstreet.numHouses = 1;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 1 house on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 1) {
						if(player.moneyBalance > (tempstreet.housePrice*2)) {
							player.moneyBalance -= (tempstreet.housePrice *2);
							tempstreet.numHouses = 2;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 2 houses on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 2) {
						if(player.moneyBalance > (tempstreet.housePrice*3)) {
							player.moneyBalance -= (tempstreet.housePrice *3);
							tempstreet.numHouses = 3;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 3 houses on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 3) {
						if(player.moneyBalance > (tempstreet.housePrice*4)) {
							player.moneyBalance -= (tempstreet.housePrice *4);
							tempstreet.numHouses = 4;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 4 houses on" + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
				}
				else if(tempstreet.numHouses == 1) {
					String[]options3 = {"1", "2", "3"};
					var houseselection = jp.showOptionDialog(null, "You have one house on this property, how many would you like to buy?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice);
							tempstreet.numHouses = 2;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 1 house on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 1) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice*2);
							tempstreet.numHouses = 3;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 2 houses on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 2) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice*3);
							tempstreet.numHouses = 4;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 2 houses on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
				}
				else if(tempstreet.numHouses == 2) {
					String[]options3 = {"1", "2"};
					var houseselection = jp.showOptionDialog(null, "You have two houses on this property, how many would you like to buy?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice);
							tempstreet.numHouses = 3;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 1 house on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
					if(houseselection == 1) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice*2);
							tempstreet.numHouses = 4;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 2 houses on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
				}
				else if(tempstreet.numHouses == 3) {
					String[]options3 = {"Yes", "No"};
					var houseselection = jp.showOptionDialog(null, "You have three houses on this property, would you like to buy your fourth?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						if(player.moneyBalance > tempstreet.housePrice) {
							player.moneyBalance -= (tempstreet.housePrice);
							tempstreet.numHouses = 4;
							allProperties[allpropindex] = tempstreet;
							console.append(player.playerName + " has bought 1 house on " + allProperties[allpropindex].propertyName + "!\n");
						}
						else {
							jp.showMessageDialog(jp, "Not enough money to purchase the house!");
						}
					}
				}
				else {
					jp.showMessageDialog(jp, "You already have 4 houses!");
				}
			}
			if(typeselection ==1) {
				if(tempstreet.hotel == true) {
					jp.showMessageDialog(jp, "You already have a hotel on this property!");
				}
				else if(tempstreet.numHouses != 4) {
					jp.showMessageDialog(jp, "You don't have enough houses to build a hotel!");
				}
				else if(tempstreet.housePrice > player.moneyBalance) {
					jp.showMessageDialog(jp, "Not enough money to purchase the hotel!");
				}
				else {
					String[]options3 = {"Yes", "No"};
					var houseselection = jp.showOptionDialog(null, "Would you like to buy a hotel for $" + tempstreet.housePrice, "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						player.moneyBalance -= (tempstreet.housePrice);
						tempstreet.hotel = true;
						tempstreet.numHouses = 0;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has bought a hotel on " + allProperties[allpropindex].propertyName + "!\n");
					}
				}
			}
			}
		}
		if(command==1) {
			int counter = 0; 
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i] instanceof street) {
					street housetemp = (street)allProperties[i];
					if(housetemp.numHouses > 0 || housetemp.hotel == true) {
						counter +=1;
					}
				}
			}
			property [] temp = new property[counter];
			int index = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i] instanceof street) {
					street housetemp = (street)allProperties[i];
					if(housetemp.numHouses > 0 || housetemp.hotel == true) {
						temp[index] = allProperties[i];
						index += 1;	
					}
				}
			}
			if(temp.length < 1) jp.showMessageDialog(jp, "No properties to sell houses or hotels!");
			if(temp.length > 0) {
			String[]tempnames = new String[temp.length];
			for(int i = 0; i < temp.length; i++) {
				tempnames[i] = temp[i].propertyName;
			}
			int selection = jp.showOptionDialog(null, "Please select a property!", "Property Manager", 0, 2, null, tempnames, tempnames[0]);
			int allpropindex = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].boardPosition == temp[selection].boardPosition) {
					allpropindex = i; break;
				}
			}
			street tempstreet = (street)temp[selection];
			if(tempstreet.hotel==true) {
				String[]options3 = {"Yes", "No"};
				var houseselection = jp.showOptionDialog(null, "Would you like to sell a hotel for $" + tempstreet.housePrice/2, "Property Manager", 0, 2, null, options3, options3[0]);
				if(houseselection == 0) {
					player.moneyBalance += (tempstreet.housePrice/2);
					tempstreet.hotel = false;
					tempstreet.numHouses = 0;
					allProperties[allpropindex] = tempstreet;
					console.append(player.playerName + " has sold their hotel on " + allProperties[allpropindex].propertyName + "!\n");
				}
			}
			else if(tempstreet.numHouses > 0) {
				if(tempstreet.numHouses == 4) {
					String[]options3 = {"1", "2", "3", "4"};
					var houseselection = jp.showOptionDialog(null, "How many houses would you like to sell?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						player.moneyBalance += (tempstreet.housePrice/2);
						tempstreet.numHouses = 3;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 1 house from " + allProperties[allpropindex].propertyName + "!\n");
					}
					if(houseselection == 1) {
						player.moneyBalance += (tempstreet.housePrice);
						tempstreet.numHouses = 2;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 2 houses from " + allProperties[allpropindex].propertyName + "!\n");
						
					}
					if(houseselection == 2) {
						player.moneyBalance += ((tempstreet.housePrice/2) *3);
						tempstreet.numHouses = 1;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 3 houses from " + allProperties[allpropindex].propertyName + "!\n");
					}
					if(houseselection == 3) {
						player.moneyBalance += (tempstreet.housePrice *2);
						tempstreet.numHouses = 0;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 4 houses from" + allProperties[allpropindex].propertyName + "!\n");
					}
				}
				else if(tempstreet.numHouses == 3) {
					String[]options3 = {"1", "2", "3"};
					var houseselection = jp.showOptionDialog(null, "How many houses would you like to sell?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						player.moneyBalance += (tempstreet.housePrice/2);
						tempstreet.numHouses = 2;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 1 house from " + allProperties[allpropindex].propertyName + "!\n");
					}
					if(houseselection == 1) {
						player.moneyBalance += (tempstreet.housePrice);
						tempstreet.numHouses = 1;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 2 houses from " + allProperties[allpropindex].propertyName + "!\n");
						
					}
					if(houseselection == 2) {
						player.moneyBalance += ((tempstreet.housePrice/2) *3);
						tempstreet.numHouses = 0;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 3 houses from " + allProperties[allpropindex].propertyName + "!\n");
					}
				}
				else if(tempstreet.numHouses == 2) {
					String[]options3 = {"1", "2"};
					var houseselection = jp.showOptionDialog(null, "How many houses would you like to sell?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						player.moneyBalance += (tempstreet.housePrice/2);
						tempstreet.numHouses = 1;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 1 house from " + allProperties[allpropindex].propertyName + "!\n");
					}
					if(houseselection == 1) {
						player.moneyBalance += (tempstreet.housePrice);
						tempstreet.numHouses = 0;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 2 houses from " + allProperties[allpropindex].propertyName + "!\n");
					}
				}
				else if(tempstreet.numHouses == 1) {
					String[]options3 = {"Yes", "No"};
					var houseselection = jp.showOptionDialog(null, "Would you like to sell your last house?", "Property Manager", 0, 2, null, options3, options3[0]);
					if(houseselection == 0) {
						player.moneyBalance += (tempstreet.housePrice/2);
						tempstreet.numHouses = 0;
						allProperties[allpropindex] = tempstreet;
						console.append(player.playerName + " has sold 1 house from " + allProperties[allpropindex].propertyName + "!\n");
					}
				}
			}
			else {
				jp.showMessageDialog(jp, "No houses or hotels to sell on this property!");
			}
			}
		}
		if(command==2) {
			int counter = 0; 
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i].mortgaged == false) {
					counter +=1;
				}
			}
			property [] temp = new property[counter];
			int index = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i].mortgaged == false) {
					temp[index] = allProperties[i];
					index += 1;	
					
				}
			}
			if(temp.length < 1) jp.showMessageDialog(jp, "No properties to mortgage!");
			if(temp.length > 0) {
				String[]tempnames = new String[temp.length];
				for(int i = 0; i < temp.length; i++) {
					tempnames[i] = temp[i].propertyName;
				}
				int selection = jp.showOptionDialog(null, "Please select a property!", "Property Manager", 0, 2, null, tempnames, tempnames[0]);
				int allpropindex = 0;
				for(int i = 0; i < allProperties.length; i++) {
					if(allProperties[i].boardPosition == temp[selection].boardPosition) {
						allpropindex = i; break;
					}
				}
				property tempprop = temp[selection];
				String[]options3 = {"Yes", "No"};
				var propertyselection = jp.showOptionDialog(null, "Would you like to mortgage " + tempprop.propertyName + " for $" + tempprop.buyPrice/2 +"?", "Property Manager", 0, 2, null, options3, options3[0]);
				if(propertyselection==0) {
					player.moneyBalance += allProperties[allpropindex].buyPrice/2;
					allProperties[allpropindex].mortgaged = true;
				}
			}
		}
		if(command==3) {
			int counter = 0; 
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i].mortgaged == true) {
					counter +=1;
				}
			}
			property [] temp = new property[counter];
			int index = 0;
			for(int i = 0; i < allProperties.length; i++) {
				if(allProperties[i].owner == player && allProperties[i].mortgaged == true) {
					temp[index] = allProperties[i];
					index += 1;	
				}
			}
			if(temp.length < 1) jp.showMessageDialog(jp, "No properties mortgaged!");
			if(temp.length > 0) {
				String[]tempnames = new String[temp.length];
				for(int i = 0; i < temp.length; i++) {
					tempnames[i] = temp[i].propertyName;
				}
				int selection = jp.showOptionDialog(null, "Please select a property!", "Property Manager", 0, 2, null, tempnames, tempnames[0]);
				int allpropindex = 0;
				for(int i = 0; i < allProperties.length; i++) {
					if(allProperties[i].boardPosition == temp[selection].boardPosition) {
						allpropindex = i; break;
					}
				}
				property tempprop = temp[selection];
				String[]options3 = {"Yes", "No"};
				var propertyselection = jp.showOptionDialog(null, "Would you like to unmortgage " + tempprop.propertyName + " for $" + ((tempprop.buyPrice/2) + ((tempprop.buyPrice/2)/10)) +"?", "Property Manager", 0, 2, null, options3, options3[0]);
				if(propertyselection==0) {
					player.moneyBalance -= ((allProperties[allpropindex].buyPrice/2) + ((allProperties[allpropindex].buyPrice/2)/10));
					allProperties[allpropindex].mortgaged = false;
				}
			}
		}
	}
	
	static void barter(player player) {
		
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
				else if(temp.numHouses ==0 && temp.hotel == false && property.playerSet == true) {
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
					if(property.type.equals("Railroad")) {
						//Because when you run the for loop
						//the condition will be true at least once 
						//i.e. at the position of that property in the array of allProperties
						int countRailOwned = -1;
						for(int i = 0; i < allProperties.length; i++) {
							if(allProperties[i].type.equals("Railroad" ) && allProperties[i].owner==property.owner) {
								countRailOwned +=1;
							}
						}
						rentam = property.rentArray[countRailOwned];
					}
					else if(property.type.equals("Utility")) {
						int countUtilOwned = -1;
						for(int i = 0; i < allProperties.length; i++) {
							if(allProperties[i].type.equals("Utility") && allProperties[i].owner==property.owner) {
								countUtilOwned +=1;
							}
						}
						rentam = property.rentArray[countUtilOwned] *(dice1+dice2);
					}
				}
				boolean tempBool = true;
				while(tempBool){
					if(payer.moneyBalance < rentam){
						int currentAssets = payer.moneyBalance;
						for(int d = 0; d < allProperties.length; d++){
							if(allProperties[d].mortgaged == false && allProperties[d].owner == payer){
								currentAssets += (allProperties[d].buyPrice/2);
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
				//this block of code does not matter because chance rent
				//is only different on railroads and utilities
				if(property instanceof street) {
				street temp;
				temp = (street)property;
				if(property.playerSet == false) {
					rentam=property.rentArray[0];
				}
				else if(property.playerSet == true && temp.numHouses ==0) {
					rentam=property.rentArray[0];
				}
				else if(temp.hotel == true){
					rentam=property.rentArray[5];
				}
				else if(temp.numHouses > 0) {
					rentam=property.rentArray[temp.numHouses];
				}
				}
				else {
					if(property.type.equals("Railroad")) {
						//Because when you run the for loop
						//the condition will be true at least once 
						//i.e. at the position of that property in the array of allProperties
						int countRailOwned = -1;
						for(int i = 0; i < allProperties.length; i++) {
							if(allProperties[i].type.equals("Railroad") && allProperties[i].owner==property.owner) {
								countRailOwned +=1;
							}
						}
						rentam = property.rentArray[countRailOwned]*2;
					}
					else if(property.type.equals("Utility")) {
						rentam = property.rentArray[1];
					}
				}
				boolean tempBool = true;
				while(tempBool){
					if(payer.moneyBalance < rentam){
						int currentAssets = payer.moneyBalance;
						for(int d = 0; d < allProperties.length; d++){
							if(allProperties[d].mortgaged == false && allProperties[d].owner == payer){
								currentAssets += (allProperties[d].buyPrice/2);
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
	
	static void drawCommunityChest(player player) {
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
		for(int i = 0; i < allProperties.length; i++){
			if(allProperties[i].owner == player && allProperties[i] instanceof street){
				street temp = (street) allProperties[i];
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
	
	static void drawChance(player player) {
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
		for(int i = 0; i < allProperties.length; i++){
			if(allProperties[i].owner == player && allProperties[i] instanceof street){
				street temp = (street) allProperties[i];
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
		break; 
		}
	}
}
class player {
	String playerName;
	int moneyBalance = 1500;
	boolean turn = false;
	boolean rollTurn = false;
	boolean inJail = false;
	int jailCounter = 0;
	int boardPosition = 0;
	int JailFreeCards = 0; //need to write up the ability to sell these
}

class property {
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

class street extends property {
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
class startGUI extends JFrame{
	
	static Container cp;
	static JTextField displayinst;
	static JButton btn2, btn3, btn4, btn5, btn6, btn7, btn8;
	int numPlayers = 0;
	
	startGUI(Dimension screenSize, int w, int h){
		//determines the position of displayinst
		int a = w/10;
		int b = 8 * w /10;
		//additional math for buttons
		int c = a/10;
		int d = a * 3 / 2;
		
		cp = new Container();
		cp = getContentPane();
		
		getContentPane().setPreferredSize(screenSize);
		getContentPane().setLayout(null);
		
		displayinst = new JTextField("");
		displayinst.setEditable(false);
		displayinst.setFont(new Font("Times New Roman", Font.BOLD, 24));
		displayinst.setBounds(a, a/2 - (3*c), b, a);
		cp.add(displayinst);
		
		btn2 = new JButton("2");
		btn2.setBounds(a+c, d-(c*2), a, a);
		cp.add(btn2);
		
		btn3 = new JButton("3");
		btn3.setBounds(a+c, d+a-c, a, a);
		cp.add(btn3);
		
		btn4 = new JButton("4");
		btn4.setBounds(a+c, d+(2*a), a, a);
		cp.add(btn4);
		
		btn5 = new JButton("5");
		btn5.setBounds(a+c, d+(3*a)+c, a, a);
		cp.add(btn5);
		
		btn6 = new JButton("6");
		btn6.setBounds(2*(a+c), d-(c*2), a, a);
		cp.add(btn6);
		
		btn7 = new JButton("7");
		btn7.setBounds(2*(a+c), d+a-c, a, a);
		cp.add(btn7);
		
		btn8 = new JButton("8");
		btn8.setBounds(2*(a+c), d+(2*a), a, a);
		cp.add(btn8);
		
		ActionListener2 listener2 = new ActionListener2();
		btn2.addActionListener(listener2);
		btn3.addActionListener(listener2);
		btn4.addActionListener(listener2);
		btn5.addActionListener(listener2);
		btn6.addActionListener(listener2);
		btn7.addActionListener(listener2);
		btn8.addActionListener(listener2);
		
		cp.validate();
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	class ActionListener2 implements ActionListener{

		public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btn2) {
			numPlayers=2;
		}
		if(e.getSource()==btn3) {
			numPlayers=3;
		}
		if(e.getSource()==btn4) {
			numPlayers=4;
		}
		if(e.getSource()==btn5) {
			numPlayers=5;
		}
		if(e.getSource()==btn6) {
			numPlayers=6;
		}
		if(e.getSource()==btn7) {
			numPlayers=7;
		}
		if(e.getSource()==btn8) {
			numPlayers=8;
		}
			
		}
		
	}
}
class startGUI2 extends JFrame{
	
	static Container cp2;
	static JTextField ply1, ply2, ply3, ply4, ply5, ply6, ply7, ply8, displayinst2;
	static JButton startgm;
	String [] names;
	static int num;
	
	startGUI2(Dimension screenSize, int w, int h, int num){
		//determines the position of displayinst
		int a = w/10;
		int b = 8 * w /10;
		int c = a/10;
		//additional math for jTextFields
		int d = a * 3 / 2;
		int e = d - (c*2);
		int f = a/3;
		int g = f/10;
		
		cp2 = new Container();
		cp2 = getContentPane();
		getContentPane().setPreferredSize(screenSize);
		getContentPane().setLayout(null);
		
		displayinst2 = new JTextField("");
		displayinst2.setEditable(false);
		displayinst2.setFont(new Font("Times New Roman", Font.BOLD, 24));
		displayinst2.setBounds(a, a/2 - (3*c), b, a);
		cp2.add(displayinst2);
		
		ply1 = new JTextField("Player 1");
		ply1.setBounds(a+c, e, a*3, f);
		cp2.add(ply1);
		
		ply2 = new JTextField("Player 2");
		ply2.setBounds(a+c, e+g+f, a*3, f);
		cp2.add(ply2);
		
		ply3 = new JTextField("Player 3");
		ply3.setBounds(a+c, e+(g*2)+(f*2), a*3, f);
		if(num >= 3)cp2.add(ply3);
		
		ply4 = new JTextField("Player 4");
		ply4.setBounds(a+c, e+(g*3)+(f*3), a*3, f);
		if(num >= 4)cp2.add(ply4);
		
		ply5 = new JTextField("Player 5");
		ply5.setBounds(a+c, e+(g*4)+(f*4), a*3, f);
		if(num >= 5)cp2.add(ply5);
		
		ply6 = new JTextField("Player 6");
		ply6.setBounds(a+c, e+(g*5)+(f*5), a*3, f);
		if(num >= 6)cp2.add(ply6);
		
		ply7 = new JTextField("Player 7");
		ply7.setBounds(a+c, e+(g*6)+(f*6), a*3, f);
		if(num >= 7)cp2.add(ply7);
		
		ply8 = new JTextField("Player 8");
		ply8.setBounds(a+c, e+(g*7)+(f*7), a*3, f);
		if(num >= 8)cp2.add(ply8);
		
		startgm = new JButton("Start Game!");
		startgm.setBounds(a+c, e+(g*8)+(f*8), a, a);
		cp2.add(startgm);
		
		ActionListener1 listen = new ActionListener1();
		startgm.addActionListener(listen);
		
		cp2.validate();
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		this.num = num;
	}
	class ActionListener1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		if(e.getSource()==startgm) {
			try {
				if(num == 2) {
					names = new String[2];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
				}
				else if(num == 3) {
					names = new String[3];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
				}
				else if(num == 4) {
					names = new String[4];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
					String player4 = ply4.getText();
					names[3] = player4;
				}
				else if(num == 5) {
					names = new String[5];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
					String player4 = ply4.getText();
					names[3] = player4;
					String player5 = ply5.getText();
					names[4] = player5;
				}
				else if(num == 6) {
					names = new String[6];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
					String player4 = ply4.getText();
					names[3] = player4;
					String player5 = ply5.getText();
					names[4] = player5;
					String player6 = ply6.getText();
					names[5] = player6;
				}
				else if(num == 7) {
					names = new String[7];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
					String player4 = ply4.getText();
					names[3] = player4;
					String player5 = ply5.getText();
					names[4] = player5;
					String player6 = ply6.getText();
					names[5] = player6;
					String player7 = ply7.getText();
					names[6] = player7;
				}
				else if(num == 3) {
					names = new String[8];
					String player1 = ply1.getText();
					names[0] = player1;
					String player2 = ply2.getText();
					names[1] = player2;
					String player3 = ply3.getText();
					names[2] = player3;
					String player4 = ply4.getText();
					names[3] = player4;
					String player5 = ply5.getText();
					names[4] = player5;
					String player6 = ply6.getText();
					names[5] = player6;
					String player7 = ply7.getText();
					names[6] = player7;
					String player8 = ply8.getText();
					names[7] = player8;
				}
			}
			catch(NullPointerException ne) {
				
			}
		}
			
		}
	}
}

class endgamewindow extends JFrame{
	
	static JTextField winner;

	endgamewindow(Dimension screenSize, int w, int h, String winnerstr){
		int a = w/4;
		int b = h/4;
		
		getContentPane().setPreferredSize(screenSize);
		getContentPane().setLayout(null);
		winner = new JTextField(winnerstr);
		winner.setEditable(false);
		winner.setBounds(a, b, a*2, b*2);
		getContentPane().add(winner);
		
		validate();
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}