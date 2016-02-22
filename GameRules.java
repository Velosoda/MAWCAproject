package game;
public class GameRules {
	//data Fields 
	int currentRound=0;
	int playerWins=0;
	int enemyWins=0;
	//Methods
	//resets all data fields back to zero
	public void reset(){
		currentRound=0;
		playerWins=0;
		enemyWins=0;
	}
	//works as a check to see if someone won the game already
	public void winCheck(){
		if(playerWins==2){
			System.out.println("you won!");
			reset();
		}
		if(enemyWins==2){
			System.out.println("the enemy won!");
			reset();
		}
		else return;
	}
}
