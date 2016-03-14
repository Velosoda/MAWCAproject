/* Author: David Veloso 
 * Concept: David Veloso & Matt Jacobi
 * Date Started: January 2016
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
	GameRules rules = new GameRules();
	Enemy enemy = new Enemy();
	Player you = new Player();
	boolean done=false;
	
	public void menu(){
		System.out.println("Welcome To whatever we call it"+ you.name);
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
	public void equipDisplay(){
		//displays the soldier's id  name <-- just like that not entirely sure why though 
		for(int i=0; i<you.squad.size(); i++){
			System.out.print(you.squad.get(i).id);
			System.out.print(" "+you.squad.get(i).name+" ");
			System.out.print("Attack: "+you.squad.get(i).attack+" ");
			System.out.print("Health: "+you.squad.get(i).health);
			System.out.println();
		}
	}
	public void tierCheckDisplay(){
		//tier 2 attack boost check and equip; will only run if the player actually has 
		for(int i=0; i!=you.squad.size(); i++){
			you.squad.get(i).tierCheck();
			if(you.squad.get(i).attackTier==true){
				System.out.println(you.squad.get(i).name+" is eligible for an Attack Upgrade");
			}
			if(you.squad.get(i).healthTier==true){
				System.out.println(you.squad.get(i).name+" is eligible for an Health Upgrade");
			}
			if(you.squad.get(i).attackTier==true && you.squad.get(i).healthTier==true){
				System.out.println(you.squad.get(i).name+" is eligible for both Upgrades");
			}
			System.out.println();
		}
	}
	public void equip(){
		//Algorithm for equipping Tier one Attack Boosts and Health boosts
		attackloop:	
		while(you.attackBoostBank!=0){
			String choice;
			int errorCount=0;
			int getOutOfAttack=0;
			equipDisplay();
			System.out.println("Attack Boosts remaining : "+you.attackBoostBank);
			System.out.println("who do you want to give a boost to");
			choice=all.next();
			for(int i=0; i!=you.squad.size(); i++){
				if(choice.equals(you.squad.get(i).name) && you.squad.get(i).attack!=5){
					you.squad.get(i).tier1AttackBoost+=1;
					you.squad.get(i).attackBoost();
					you.attackBoostBank-=1;
				}
				else{ 
					errorCount++;
					if(errorCount==4){
						System.out.println("That person either doesn't exsist or already has a tier 1 Attack boost");
					}
				}
			}
			//this will get us out of the attack loop and go to the health loop
			for(int i=0; i<you.squad.size(); i++){
				if(you.squad.get(i).attack==5){
					getOutOfAttack++;
					if(getOutOfAttack==you.squad.size()){
						break attackloop;
					}
				}
				else{ 
					continue attackloop;
				}
			}
		}
		healthloop:
		while(you.healthBoostBank!=0){
			String healthBoostSelection;
			int healthErrorCount=0;
			equipDisplay();
			System.out.println("Health Boosts remaining : "+you.healthBoostBank);
			System.out.println("Who do you want to give a boost to");
			healthBoostSelection=all.next();
			for(int i=0; i!=you.squad.size(); i++){
				if(healthBoostSelection.equals(you.squad.get(i).name) && you.squad.get(i).health!=7){
					you.squad.get(i).tier1HealthBoost+=1;
					you.squad.get(i).healthBoost();
					you.healthBoostBank-=1;
				}
				else{ 
					healthErrorCount++;
					if(healthErrorCount==4){
						System.out.println("That person either doesn't exsist or already has a tier 1 Health boost");
					}
				}
			}
			int getOutOfHealth=0;
			for(int i=0; i<you.squad.size(); i++){
				if(you.squad.get(i).attackTier==true){
					getOutOfHealth++;
					if(getOutOfHealth==you.squad.size()){
						break healthloop;
					}
					else{
						continue healthloop;
					}
				}
			}
		}
		
		if(you.tier2AttackBoostBank!=0 || you.tier2HealthBoostBank!=0){
			tierCheckDisplay();
			System.out.println("Would you like to give them the upgrade now? (yes or no)");
			String upgradeChoice;
			upgradeChoice=all.nextLine();
			if(upgradeChoice.equals("yes")){
				while(you.tier2AttackBoostBank!=0){
					int tier2errorCount=0;
					equipDisplay();
					System.out.println("Tier 2 Attack Boosts remaining : "+you.tier2AttackBoostBank);
					System.out.println("Who do you want to give a boost to (Enter the name) ");
					String boostChoice=all.nextLine();
					for(int i=0; i!=you.squad.size(); i++){
						if(boostChoice.equals(you.squad.get(i).name) && you.squad.get(i).attack==5){
							you.squad.get(i).tier2AttackBoost+=1;
							you.squad.get(i).attackBoostTier2();
							you.tier2AttackBoostBank-=1;
						}
						else{ 
							tier2errorCount++;
							if(tier2errorCount==4){
								System.out.println("That person either doesn't exsist or already has a Tier 2 Attack Boost");
							}
						}
					}
				}
				while(you.tier2HealthBoostBank!=0){
					int tier2HealthErrorCount=0;
					equipDisplay();
					System.out.println("Tier 2 Health Boosts remaining : "+you.tier2HealthBoostBank);
					System.out.println("Who do you want to give a boost to (Enter the name) ");
					String boostChoice=all.nextLine();
					for(int i=0; i!=you.squad.size(); i++){
						if(boostChoice.equals(you.squad.get(i).name) && you.squad.get(i).health==7){
							you.squad.get(i).tier2HealthBoost+=1;
							you.squad.get(i).healthBoostTier2();
							you.tier2HealthBoostBank-=1;
						}
						else{
							tier2HealthErrorCount++;
							if(tier2HealthErrorCount==4){
								System.out.println("That person either doesn't exsist or already has a tier 2 health boost");
							}
						}
					}
				}
			}
			else if(upgradeChoice.equals("no")){
				return;  
			}
			else{
				System.out.println("Invalid Input. Try again");
			}
		}
	}
	public void selections(ArrayList<Soldier> selections, int survivors){
		//shows your remaining squad in detail
		if(survivors==0){
			for(int i=0; i<you.squad.size(); i++){
				System.out.print(" "+you.squad.get(i).name+" ");
				System.out.print(you.squad.get(i).id);
				System.out.print(" "+you.squad.get(i).attack+" ");
				System.out.print(" "+you.squad.get(i).health+"  ");
			}
		}
		if(survivors==1){
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
			else{selections(selections,0);}
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
	//working Actual battle phase
	public void survivors(ArrayList<Soldier> Sarray, ArrayList<Esoldiers> Earray){
		
	}
	public void battle(ArrayList<Soldier> Sarray, ArrayList<Esoldiers> Earray){
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
		battle(Sarray,Earray);		
		//Defeated the base
		if(Earray.size()==0){
			System.out.println("you defeated this base");
			you.basesDefeated+=1;
			//loop will display any survivors of this base
			for(int i1=0; i1<Sarray.size(); i1++){
				System.out.println(Sarray.get(i1).name+" survived");
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
			return;
		}	
	}
	//working prepares the player for battle; serves as a main method for the battle; This will only work out one round 
	public void battleSetup(){
		rules.winCheck();
		rules.currentRound+=1;
		ArrayList<Soldier> selectionsB1 = new ArrayList<Soldier>();
		ArrayList<Soldier> selectionsB2 = new ArrayList<Soldier>();
		ArrayList<Soldier> selectionsB3 = new ArrayList<Soldier>();
		enemy.enemyBaseFill(base1, base2, base3);
		System.out.println("Round "+rules.currentRound);
		System.out.println("The Enemy controls 3 bases we have to get in there and clear them all out");
		store();
		//Lets the player choose names for their soldiers (needs to go somewhere else)		
		for(int i=0; i <= you.squad.size()-1; i++){
			System.out.println("What is Member "+(i+1)+"'s name?");
			you.squad.get(i).name=all.next();
		}
		equip();
		//Displays how many enemies are in each base 
		System.out.println("Heres how many people are in each base");
		System.out.println("Base 1: "+base1.size());
		System.out.println("Base 3: "+base3.size());
		System.out.println("Base 2: "+base2.size());
		System.out.println("Now we need to choose who goes to which base you need at least one person per base");
		//selection section
		System.out.println("Who would you like to send to base 1");
		selections(selectionsB1,0);
		System.out.println("Who would you like to send to base 2");
		selections(selectionsB2,0);
		System.out.println("Who would you like to send to base 3");
		selections(selectionsB3,0);
		//Battle Section
		System.out.println("There is no going back now ");
		battle(selectionsB1, base1);
		battle(selectionsB2, base2);
		battle(selectionsB3, base3);
		
		//-----------------------------------------------------------------------------------------------------
		//						-Survivor Handling Section-
		//This section of the Battle method handles any survivors that the player may have
		//Its key to understand that this is only necessary if there are enemies still alive in the other bases 
		//The Accepted solution is to distribute the guys to the separate bases
		//-----------------------------------------------------------------------------------------------------
		
		if(selectionsB1.size()+selectionsB2.size()+selectionsB3.size()>0 && base1.size()+base2.size()+base3.size()>0){
			//this loop will place all of your survivors into a single array
			for(int i=0; selectionsB2.size()+selectionsB3.size()>0; i++){
				selectionsB1.add(selectionsB2.get(i));
				selectionsB2.remove(i);
				selectionsB1.add(selectionsB3.get(i));
				selectionsB3.remove(i);
			}
			System.out.println("you have survivors and there are still people left in the bases. Where would you like you survivors to go");
			System.out.println("Heres how many people are left");
			System.out.println("Base 1: "+base1.size());
			System.out.println("Base 3: "+base3.size());
			System.out.println("Base 2: "+base2.size());
			System.out.println("Here are your Survivors");
			//displays survivors
			for(int i=0; selectionsB1.size()<0; i++){
				System.out.println(selectionsB1.get(i).name);
			}
			if(base1.size()!=0){
				System.out.println("Who do you want to send to base 1");
			}
			if(base2.size()!=0){
				System.out.println("who do you want to send to base 2");
			}
			if(base3.size()!=0){
				System.out.println("Who do you want to send to base 3");
			}
		}
		//cpu wins
		if(selectionsB1.size()+selectionsB2.size()+selectionsB3.size()==0){
			
		}
	}
	public void startup(){
		System.out.println("Welcome to what ever Im going to call this");
		System.out.println("This is game about strategy and base invading");
		System.out.println("What is the name of Your Team");
		you.name=all.nextLine();
		menu();
		all.close();
	}
	public static void main(String[] args) {
		new Main().startup();
	}
}
