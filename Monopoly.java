package mono;
import java.util.Random;

public class Monopoly {
static int dice1, dice2;
static player[] playerList;
static communityChestCard[] communityChestDeck = new communityChestCard[16];
static chanceCard[] chanceDeck = new chanceCard[16];
static property[] allProperties = new property[28];
static property[] availableProperties;

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

	class player {
		String playerName;
		int moneyBalance = 1500;
		String piece;
		boolean turn = false;
		boolean inJail = false;
		property[] ownedProperties;
		int jailCounter = 0;
		int boardPosition = 0;
		
		void rollDice() {
			Random rand = new Random();
			dice1 = rand.nextInt(7);
			dice2 = rand.nextInt(7);

		}
	}
	
	public class property {
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
	
	public class street extends property {
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
	
	
	// Going to list all of the methods for community chest cards here
	class communityChestCard {
		
	}
	
	// Going to list all of the methods for chance cards here
	class chanceCard {
		
	}
	
	public static void main(String[] args) {
		Monopoly m = new Monopoly();		
		// creating the array of all properties
		//brown properties
		allProperties[0] = m.new street("Mediterranean Avenue", 1, 60, 2, "Brown", 50);
		allProperties[1] = m.new street("Baltic Avenue", 3, 60, 4, "Brown", 50);
		//first rail
		allProperties[2] = m.new property("Reading Railroad", 5, 200, 25, "Railroad");
		//light blue
		allProperties[3] = m.new street("Oriental Avenue", 6, 100, 6, "Light Blue", 50);
		allProperties[4] = m.new street("Vermont Avenue", 8, 100, 6, "Light Blue", 50);
		allProperties[5] = m.new street("Conneticut Avenue", 9, 120, 8, "Light Blue", 50);
		//first pink
		allProperties[6] = m.new street("St. Charles Place", 11, 140, 10, "Pink", 100);
		//first utility
		allProperties[7] = m.new property("Electric Company", 12, 150, dice1 + dice2, "Utility");
		//rest of pink
		allProperties[8] = m.new street("States Avenue", 13, 140, 10, "Pink", 100);
		allProperties[9] = m.new street("Virginia Avenue", 14, 160, 12, "Pink", 100);
		//second rail
		allProperties[10] = m.new property("Pennsylvania Railroad", 15, 200, 25, "Railroad");
		//orange properties
		allProperties[11] = m.new street("St. James Place", 16, 180, 14, "Orange", 100);
		allProperties[12] = m.new street("Tenessee Avenue", 18, 180, 14, "Orange", 100);
		allProperties[13] = m.new street("New York Avenue", 19, 200, 16, "Orange", 100);
		//red properties
		allProperties[14] = m.new street("Kentucky Avenue", 21, 220, 18, "Red", 150);
		allProperties[15] = m.new street("Indiana Avenue", 23, 220, 18, "Red", 150);
		allProperties[17] = m.new street("Illinois Avenue", 24, 240, 20, "Red", 150);
		//third rail (don't step on it!)
		allProperties[18] = m.new property("B. & O. Railroad", 25, 200, 25, "Railroad");
		//first two yellow
		allProperties[19] = m.new street("Atlantic Avenue", 26, 260, 22, "Yellow", 150);
		allProperties[20] = m.new street("Ventor Avenue", 27, 260, 22, "Yellow", 150);
		//second utility
		allProperties[21] = m.new property("Water Works", 28, 150, dice1 + dice2, "Utility");
		//last yellow
		allProperties[22] = m.new street("Marvin Gardens", 29, 280, 24, "Yellow", 150);
		//green properties
		allProperties[23] = m.new street("Pacific Avenue", 31, 300, 26, "Green", 200);
		allProperties[24] = m.new street("North Carolina Avenue", 32, 300, 26, "Green", 200);
		allProperties[25] = m.new street("Pennsylvania Avenue", 34, 320, 28, "Green", 200);
		//last rail
		allProperties[26] = m.new property("Short Line", 35, 200, 25, "Railroad");
		//finally dark blue
		allProperties[27] = m.new street("Park Place", 37, 350, 35, "Dark Blue", 200);
		allProperties[28] = m.new street("Boardwalk", 39, 400, 50, "Dark Blue", 200);
		

	
	
	
	
	}
}
