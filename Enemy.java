package game;
import java.util.ArrayList;
import java.util.Random;
public class Enemy {
	Random random = new Random();
	int Funds=1000;
	int soldierCap=5;
	Enemy(){}		
		
	//this constructor creates an enemy and fills the bases with random amount of enemy soldiers
	//might be better to make this into a method so we dont have to create more enemys
	public void enemyBaseFill(ArrayList<Esoldiers> base1, ArrayList<Esoldiers> base2, ArrayList<Esoldiers> base3){
		ArrayList <Esoldiers> eSquad = new ArrayList<Esoldiers>(soldierCap);
		for(int i=0; i<eSquad.size(); i++){
			eSquad.add(new Esoldiers());
		}
		int base1fill=soldierCap-random.nextInt(soldierCap);
		int base2fill=base1fill-random.nextInt(base1fill);
		int base3fill=base2fill-random.nextInt(base2fill);
		//fills base1
		for(int i=0; i <=base1fill; i++){
			base1.add(eSquad.get(i));
		}
		//fills base2 needs 2 variables one for 0 and one for the loop
		for(int i=base1fill; i<=base2fill; i++){
			base2.add(eSquad.get(i));
		}
		//fills base3 like base2
		for(int i=base2fill; i<=base3fill; i++){
			base3.add(eSquad.get(i));
		}
	}
}
