package Barter;
import java.util.Scanner;


public class Barter {

	static player[] playerList;
	//Players are able to negotiate for different items (properties, money, cards) 
	//and can barter with those same items
	static void barter(player player) {
		System.out.println("Barter method under development! Please return later!");
		//first need to import a scanner object
		//then we need to use this scanner object to ask for user input
		//specifically, which player you would like to barter with
		
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
	public static void main(String[] args) {
		//then, need to use a for loop most likely, where, if the player's name
				//.equals the input, that player will be selected to barter with
				//ex.
				//for(int i = 0; i < playerList.length; i++){
				//		if(inputName.equals(playerList[i].playerName)){
				//			method functions go here, probably will need another for loop
				//			to scan through available properties, will also need some sort of
				// 			input mechansism (again use scanner) for a player to select properties 
				//			they would like to trade, using an semi-infinite loop
				//			
				//		}
				//	}
		Scanner scan = new Scanner (System.in);
		System.out.println("Hello, which player would you like to barter with?");
		String inputName = scan.nextLine();
		for(int i = 0; i < playerList.length; i++) {
			if(inputName.equals(playerList[i].playerName)) {
				System.out.println("What do you want to trade? is it money, property, or jailcard?");
				String trade = scan.nextLine();
				switch(trade) {
				case "money":
					System.out.println("How much money will you give?");
					int amount = scan.nextInt(); break;
					
				case "property":
					System.out.println("What properties will you give?");
					String property = scan.nextLine(); break;
					
				case "jailcard":
					System.out.println("How many get out of jailcards will you give?");
					int num = scan.nextInt(); break;
				}
			}
			
		}
		

	}

}
