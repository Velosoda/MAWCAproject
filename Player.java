package game;
import java.util.ArrayList;
public class Player {
	//data fields
	String name;
	int funds=1000;
	int soldierBank=0;
	int attackBoostBank=0;
	int healthBoostBank=0;
	int tier2AttackBoostBank=0;
	int tier2HealthBoostBank=0;
	int basesDefeated=0;
	ArrayList<Soldier> squad = new ArrayList<Soldier>(10);
	//methods
	public void reset(){
		funds=1000;
		soldierBank=0;
		attackBoostBank=0;
		healthBoostBank=0;
		tier2AttackBoostBank=0;
		tier2HealthBoostBank=0;
		basesDefeated=0;
	}
}
