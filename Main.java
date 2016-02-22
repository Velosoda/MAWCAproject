/* Author: David Veloso 
 * Concept: David Veloso & Matt Jacobi
 * Date Started: Nov 2015
 * Date Finished: March 2016
 * Tested: NO
 */
package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	//Scoped data fields for the whole program:
	Scanner all = new Scanner(System.in);
	ArrayList<Esoldiers> base1 = new ArrayList<Esoldiers>(10);
	ArrayList<Esoldiers> base2 = new ArrayList<Esoldiers>(10);
	ArrayList<Esoldiers> base3 = new ArrayList<Esoldiers>(10);
	//reserves might not be need 
	ArrayList<Soldier> reserveS = new ArrayList<Soldier>();
	ArrayList<Esoldiers> reserveE = new ArrayList<Esoldiers>();
	//basic idea on the survivors will uses these arraylists 
	ArrayList<Soldier> survivors1 = new ArrayList<Soldier>();
	ArrayList<Soldier> survivors2 = new ArrayList<Soldier>();
	ArrayList<Soldier> survivors3 = new ArrayList<Soldier>();
	GameRules rules = new GameRules();
	Enemy enemy = new Enemy();
	Player you = new Player();
	boolean done=false;
	
	public void menu(){
		System.out.println("Welcome To what ever we call it"+ you.name);
		System.out.println("x: Player VS Cpu"); 
		if(all.nextInt()==1){
			battleSetup();
		}
		if(all.nextInt()==2){
			System.exit(0);
		}
	}
	public void store(){
		if(you.funds>49){
			System.out.println("Funds Remaining =  "+you.funds);
			System.out.println("(1)Personel           : 100");
			System.out.println("---------Tier 1----------");
			System.out.println("(2)Attack Boosts (+2) : 75");	
			System.out.println("(3)Health Boosts (+2) : 75");
			System.out.println("---------Tier 2----------");
			System.out.println("(4)Attack Boosts (+1) : 100");
			System.out.println("(5)Health Boosts (+1) : 100");
			System.out.println("(6)Leave");
			int purchasedItem=all.nextInt();
			if(purchasedItem==1){
				int i=0;
				you.soldierBank+=1;
				you.squad.add(i, new Soldier());
				i++;
				you.funds-=100;
				store();
			}
			if(purchasedItem==2){
				you.attackBoostBank+=1;
				you.funds-=75;
				store();
			}
			if(purchasedItem==3){
				you.healthBoostBank+=1;
				you.funds-=75;
				store();
			}
			if(purchasedItem==4){
				you.tier2AttackBoostBank+=1;
				you.funds-=100;
				store();
			}
			if(purchasedItem==5){
				you.tier2HealthBoostBank+=1;
				you.funds-=100;
				store();
			}
		}
		else{System.out.println("you have no more funds ");}
	}
	public void equip(){
		//Lets the player choose names for their soldiers
		for(int i=0; i <= you.squad.size()-1; i++){
			System.out.println("What is Member "+(i+1)+"'s name?");
			you.squad.get(i).name=all.next();
		}
		//displays the soldier's id  name <-- just like that not entirely sure why though 
		for(int i=0; i<you.squad.size(); i++){
			System.out.print(you.squad.get(i).id);
			System.out.print(" "+you.squad.get(i).name);
			System.out.println();
		}
		//Algorithm for equipping Tier one Attack Boosts 
		while(you.attackBoostBank!=0){
			System.out.println("Attack Boosts remaining : "+you.attackBoostBank);
			System.out.println("who do you want to give a boost to");
			int choice=all.nextInt();
			for(int i=0; i<you.squad.size(); i++){
				if(choice==you.squad.get(i).id && you.squad.get(i).tier1AttackBoost==0){
					you.squad.get(i).tier1AttackBoost+=1;
					you.squad.get(i).attackBoost();
				}
				else{
					System.out.println("That person does not exist or already has a Tier 1 Attack Boost");
					equip();
				}
			}
		}
		while(you.healthBoostBank!=0){
			System.out.println("Health Boosts remaining : "+you.healthBoostBank);
			System.out.println("who do you want to give a boost to");
			int choice=all.nextInt();
			for(int i=0; i<you.squad.size(); i++){
				if(choice==you.squad.get(i).id){
					you.squad.get(i).tier1HealthBoost+=1;
					you.squad.get(i).healthBoost();
				}
				else{
					System.out.println("that person does not exist");
					equip();
				}
			}
		}
		//tier two check + equip
		System.out.println("Looking for soldiers that can equip tier 2 items...");
		for(int i=0; i<you.squad.size(); i++){
			you.squad.get(i).tierCheck();
			if(you.squad.get(i).attackTier==true || you.squad.get(i).healthTier==true){
				System.out.println("The following people are Eligble for tier 2 equipment");
				System.out.println(you.squad.get(i).name);
				System.out.println();
			}		
			else {
				System.out.println("None of your soldiers are eligable for tier two upgrades");
				return;
			}
		}
		if(you.tier2AttackBoostBank!=1 || you.tier2HealthBoostBank!=1){
			System.out.println("Would you like to like to equip your tier 2 items?(Yes or No");
			String choice=all.next();
			if(choice.equals("Yes")){
				while(you.tier2AttackBoostBank!=0){
					System.out.println("Tier 2 Attack Boosts remaining : "+you.tier2AttackBoostBank);
					System.out.println("who do you want to give a boost to(enter the id)");
					int boostChoice=all.nextInt();
					int index=0;
					if(boostChoice==you.squad.get(index).id){
						you.squad.get(index).tier1HealthBoost+=1;
						you.squad.get(index).healthBoost();
						index+=1;
						}
						else{
							System.out.println("that person does not exist");
							equip();
						}
					}
				while(you.tier2HealthBoostBank!=0){
					System.out.println("Tier 2 Attack Boosts remaining : "+you.tier2HealthBoostBank);
					System.out.println("who do you want to give a boost to");
					int boostChoice=all.nextInt();
					for(int i=0; i<you.squad.size(); i++){
						if(boostChoice==you.squad.get(i).id){
							you.squad.get(i).tier1HealthBoost+=1;
							you.squad.get(i).healthBoost();
						}
						else{
							System.out.println("that person does not exist");
							equip();
						}
					}
				}
			}
		}
		else{return;}
	}
	public void selections(ArrayList<Soldier> selections){
		//shows your remaining squad in detail
		for(int i=0; i<you.squad.size(); i++){
			System.out.print(" "+you.squad.get(i).name+" ");
			System.out.print(you.squad.get(i).id);
			System.out.print(" "+you.squad.get(i).attack+" ");
			System.out.print(" "+you.squad.get(i).health+"  ");
		}
		System.out.println("Enter the ids(Enter 0 when you are done)");
		int choice=all.nextInt();
		for(int i=0; i<you.squad.size(); i++){
			if(choice==you.squad.get(i).id){
				selections.add(you.squad.get(i));
				you.squad.remove(i);
			}
			if(choice==0 && selections.size()>=1){
				done=true;
			}
			else{selections(selections);}
		}
		if(done=true)return;
	}
	//fills the correct reserve array with a reserve troop
	public void reserve(ArrayList<Soldier> sArray, ArrayList<Esoldiers> eArray) {
		int difference=0;
		int index=0;
		//boolean even = false;
		if(eArray.size()>sArray.size()){
			difference=eArray.size()-sArray.size();
			//this index tells us where the reserve group should start
			index=sArray.size()+difference;
			for(int i=index; i<eArray.size(); i++){
				reserveE.add(eArray.get(i));
				eArray.remove(i);
				//even=false;
			}
		}
		if(sArray.size()>eArray.size()){
			difference=sArray.size()-eArray.size();
			//this index tells us where the reserve group should start
			index=eArray.size()+difference;
			for(int i=index; i<sArray.size(); i++){
				reserveS.add(sArray.get(i));
				sArray.remove(i);
				//even=false;
			}
		}
		if(sArray.size()==eArray.size()){
			//even=true;
			return;
		}
	}
	/*working Might not actually need this 
	*public void reserveBattle(ArrayList<Soldier> Sarray, ArrayList<Esoldiers> Earray){
	*	int i=0;
	*	//this is for the case of the enemy having a reserve array that is full
	*	if(Earray.isEmpty() && reserveE.size()>0){
	*		while(i<reserveE.size()){
	*			Sarray.get(i).health-=reserveE.get(i).attack;
	*			reserveE.get(i).health-=Sarray.get(i).attack;
	*		}
	*	}
	*}
	*/
	//working Actual battle phase
	public void battle(ArrayList<Soldier> Sarray, ArrayList<Esoldiers> Earray, ArrayList<Soldier> survivors){
		int i=0;
		int x=0;
		//these ifs make it so the while loop below will run the max amount of times 
		if(Sarray.size()==Earray.size()){
			x=Sarray.size();
		}
		if(Sarray.size()>Earray.size()){
			x=Earray.size();
		}
		if(Sarray.size()<Earray.size()){
			x=Sarray.size();
		}
		while(x>i){
			Earray.get(i).health-=Sarray.get(i).attack;
			Sarray.get(i).health-=Earray.get(i).attack;
			if(Earray.get(i).health==0){
				you.funds+=100;
				Sarray.get(i).kills+=1;
				Earray.remove(Earray.get(i));
				i++;
			}
			if(Sarray.get(i).health==0){
				Earray.get(i).kills+=1;
				Sarray.remove(Sarray.get(i));
				i++;
			}
		}
		battle(Sarray,Earray,survivors);		
		
		/*reserves battling survivors here might have to make this its own method 
		 *if(Sarray.size()>Earray.size() && reserveE.size()>0 || Earray.size()>Sarray.size() && reserveS.size()>0){
		 *	reserveBattle(Sarray,Earray);
		 *}
		 */
		
		//Defeated the base
		if(Earray.size()==0){
			System.out.println("you defeated this base");
			you.basesDefeated+=1;
			//loop will display any survivors of this base
			for(int i1=0; i1<Sarray.size(); i1++){
				System.out.println(Sarray.get(i1).name+" survived");
				survivors.add(Sarray.get(i1));
				Sarray.remove(i1);
			}
			//win the round 
			if(you.basesDefeated==3){
				rules.playerWins+=1;
				you.basesDefeated=0;
				you.funds+=1000;
			}
		}
		//lost the base ie every in the sarray is dead
		if(Sarray.size()==0){
			System.out.println("All of your soldiers died");
			System.out.println("There are "+Earray.size()+ "people left in this base");
			
		}	
	}
	//working prepares the player for battle; serves as a main method for the battle 
	public void battleSetup(){
		rules.winCheck();
		rules.currentRound+=1;
		ArrayList<Soldier> selectionsB1 = new ArrayList<Soldier>();
		ArrayList<Soldier> selectionsB2 = new ArrayList<Soldier>();
		ArrayList<Soldier> selectionsB3 = new ArrayList<Soldier>();
		enemy.enemyBaseFill(base1, base2, base3);
		System.out.println("The Enemy controls 3 bases we have to get in there and clear them all out");
		store();
		equip();
		System.out.println("Heres how many people are in each base");
		for(int i=0; i<base1.size(); i++){
			System.out.println("Base 1: "+base1.size());
			System.out.println("Base 3: "+base3.size());
			System.out.println("Base 2: "+base2.size());
		}
		System.out.println("Now we need to choose who goes to which base you need at least one person per base");
		//selection section
		System.out.println("Who would you like to send to base 1");
		selections(selectionsB1);
		System.out.println("Who would you like to send to base 2");
		selections(selectionsB2);
		System.out.println("Who would you like to send to base 3");
		selections(selectionsB3);
		//Battle Section
		System.out.println("There is no going back now ");
		battle(selectionsB1, base1, survivors1);
		battle(selectionsB2, base2, survivors2);
		battle(selectionsB3, base3, survivors3);
		
		battleSetup();
		
		/* reserve(selectionsB1, base1);
		* reserve(selectionsB2, base2);
		* reserve(selectionsB3, base3);
		*/
		
	}
	public void startup(){
		System.out.println("Welcome to what ever Im going to call this");
		System.out.println("This is game about strategy and base invading");
		System.out.println("What is the name of Your Team");
		you.name=all.next();
		menu();
		all.close();
	}
	public static void main(String[] args) {
		new Main().startup();
	}
}