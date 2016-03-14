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
	public void tierCheck(){
		if(tier1AttackBoost==1){
			attackTier=true;
		}
		if(tier1HealthBoost==1){
			healthTier=true;
		}
	}
	//Applies the Tier one Attack Boost
	public void attackBoost(){
		if(tier1AttackBoost==1){
			attack+=2;
		}
	}
	//Applies the Tier one Health Boost 
	public void healthBoost(){
		for(int i=0; i<tier1HealthBoost;i++){
			health+=2;
		}
	}
	public void attackBoostTier2(){
		for(int i=0; i<tier2AttackBoost;i++){
			attack+=1;
		}
	}
	public void healthBoostTier2(){
		for(int i=0; i<tier2HealthBoost;i++){
			health+=1;
		}
	}
}

