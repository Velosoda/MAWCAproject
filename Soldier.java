package game;

import java.util.*;

public class Soldier {
	Random random = new Random();
	Scanner sScan = new Scanner(System.in);
	String name;
	int health=5;
	int attack=3; 
	int id=random.nextInt(1000);
	int kills=0;
	int tier1AttackBoost=0;
	int tier1HealthBoost=0;
	int tier2AttackBoost=0;
	int tier2HealthBoost=0;
	boolean attackTier=false;
	boolean healthTier=false;
	
	//changes the two booleans above to true proving that the soldier can equip tier two Equipment
	public void addItem(int trigger){
		if(trigger==1){
			tier1AttackBoost++;
		}
		if(trigger==2){
			tier1HealthBoost++;
		}
		if(trigger==3){
			tier2AttackBoost++;
		}
		if(trigger==4){
			tier2HealthBoost++;
		}
	}
	
	public void applyItem(int trigger){
		if(trigger==1){
			attack+=2;
		}
		if(trigger==2){
			health+=2;
		}
		if(trigger==3){
			attack++;
		}
		if(trigger==4){
			health++;
		}
	}
	
	public void tierCheck(){
		if(tier1AttackBoost==1){
			attackTier=true;
		}
		if(tier1HealthBoost==1){
			healthTier=true;
		}
	}	
}
