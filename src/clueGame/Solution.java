package clueGame;
/*
 * The solution class to hold the final awnser
 * Casey Turner, Murat Tuter
 * 
 */
public class Solution {

	private Card murderer;
	private Card crimeScene;
	private Card murderWeapon;
	
	
	public Solution(Card murderer, Card crimeScene, Card murderWeapon) {
		assert(murderer.getType() == CardType.PERSON);
		assert(crimeScene.getType() == CardType.ROOM);
		assert(murderWeapon.getType() == CardType.WEAPON);

		this.murderer = murderer;
		this.crimeScene = crimeScene;
		this.murderWeapon = murderWeapon;
	}
	
	public Card getMurderer() {
		return murderer;
	}


	public Card getCrimeScene() {
		return crimeScene;
	}


	public Card getMurderWeapon() {
		return murderWeapon;
	}
	
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
        	Solution q = (Solution)obj;
            return murderer == q.murderer && crimeScene == q.crimeScene && murderWeapon == q.murderWeapon;
        }
        return false;
    }
}
