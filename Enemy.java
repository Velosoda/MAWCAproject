package game;
import java.util.ArrayList;
import java.util.Random;
public class Enemy {
	Random random = new Random();
	int Funds=1000;
	int soldierCap=5;
	int r=random.nextInt(soldierCap);
	int base1Fill=soldierCap-r;
	int base2Fill=(soldierCap-r-base1Fill)+1;
	int base3Fill=(soldierCap-(base1Fill+base2Fill));
	Enemy(){}		
	//this constructor creates an enemy and fills the bases with random amount of enemy soldiers
	//might be better to make this into a method so we dont have to create more enemys
	public void enemyBaseFill(ArrayList<Esoldiers> base1, ArrayList<Esoldiers> base2, ArrayList<Esoldiers> base3){
		negativeCase();
		//fills base1
		for(int i=0; i<base1Fill; i++){
			base1.add(i, new Esoldiers());
		}
		for(int i=0; i<base2Fill; i++){
			base2.add(i, new Esoldiers());
		}
		for(int i=0; i<base3Fill; i++){
			base3.add(i, new Esoldiers());
		}
	}
	public void negativeCase(){
		if(base1Fill==5){
			base3Fill=0;
			base2Fill=0;
		}
	}
}
