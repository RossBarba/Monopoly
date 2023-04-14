package barter;
import java.util.Scanner;

public class barter {

	
	static void barter(Player player) {

        // ask the user which player they want to trade with
        System.out.print("Which player would you like to trade with? ");
        String inputName = sc.nextLine();

        // find the player with the given name
        Player selectedPlayer = null;
        for (int i = 0; i < playerList.length; i++) {
            if (inputName.equals(playerList[i].getName())) {
                selectedPlayer = playerList[i];
                break;
            }
        }

        if (selectedPlayer == null) {
            System.out.println("No player with that name found.");
        } else {
            // display the selected player's properties
            System.out.println(selectedPlayer.getName() + "'s properties:");
            for (int i = 0; i < selectedPlayer.getNumProperties(); i++) {
                System.out.println(i + ": " + selectedPlayer.getProperty(i));
            }

            // ask the user which property they want to trade for
            int selectedPropertyIndex = -1;
            while (selectedPropertyIndex < 0 || selectedPropertyIndex >= selectedPlayer.getNumProperties()) {
                System.out.print("Which property would you like to trade for? ");
                selectedPropertyIndex = sc.nextInt();
                sc.nextLine(); // consume the newline character
            }
            String selectedProperty = selectedPlayer.getProperty(selectedPropertyIndex);

            // display the other player's properties
            Player otherPlayer = null;
            for (int i = 0; i < playerList.length; i++) {
                if (playerList[i] != selectedPlayer) {
                    otherPlayer = playerList[i];
                    break;
                }
            }
            System.out.println(otherPlayer.getName() + "'s properties:");
            for (int i = 0; i < otherPlayer.getNumProperties(); i++) {
                System.out.println(i + ": " + otherPlayer.getProperty(i));
            }

            // ask the user which property they want to offer in exchange
            int offeredPropertyIndex = -1;
            while (offeredPropertyIndex < 0 || offeredPropertyIndex >= otherPlayer.getNumProperties()) {
                System.out.print("Which property would you like to offer in exchange? ");
                offeredPropertyIndex = sc.nextInt();
                sc.nextLine(); // consume the newline character
            }
            String offeredProperty = otherPlayer.getProperty(offeredPropertyIndex);

            // perform the trade
            selectedPlayer.removeProperty(selectedPropertyIndex);
            selectedPlayer.addProperty(offeredProperty);
            otherPlayer.removeProperty(offeredPropertyIndex);
            otherPlayer.addProperty(selectedProperty);

            System.out.println("Trade complete!");
        }

        sc.close();
}   


}


class Player {
    private String name;
    private String[] properties;

    public Player(String name) {
        this.name = name;
        this.properties = new String[] { "Property 1", "Property 2", "Property 3" }; // assume each player has 3 properties
    }

    public String getName() {
        return name;
    }

    public int getNumProperties() {
        return properties.length;
    }

    public String getProperty(int index) {
        return properties[index];
    }

    public void addProperty(String property) {
    	String[] newProperties = new String[properties.length + 1];
        for (int i = 0; i < properties.length; i++) {
            newProperties[i] = properties[i];
        }
        newProperties[properties.length] = property;
        properties = newProperties;
    }

    public void removeProperty(int index) {
        String[] newProperties = new String[properties.length - 1];
        for (int i = 0, j = 0; i < properties.length; i++) {
            if (i != index) {
                newProperties[j] = properties[i];
                j++;
            }
        }
        properties = newProperties;
    }
}
	
