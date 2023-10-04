package src.game;

public class Character {
	protected int Hp,Att;
	protected String Name;
	
	Character(){
		this.Name = "주인공"; this.Hp = 40; this.Att = 10;
	}
	Character(String name, int hp, int att){
		this.Name = name; this.Hp = hp; this.Att = att;
	}
	String gN() {
		return this.Name;
	}
	int gH() {
		if(this.Hp<0) return 0;
		else return this.Hp;
	}
	int gAtt() {
		return this.Att;
	}
	void sH(int hp) {
		this.Hp = hp;
	}
	void takeDamage(int damage) {
		this.Hp-=damage;
	}
}
class Player extends Character{
	private int level;
	Player(String name,int hp, int att, int level){
		super(name,hp,att);
		this.level = level;
	}
	void levelUp() {
		this.level+=1;
		this.Att+=10;
		this.Hp =50+(level-1)*20;
	}
	void levelDown() {
		if(this.level>1) {
			this.level-=1;
			this.Att-=10;
			this.Hp =50+(level-1)*20;
		}
		else this.Hp = 50+(level-1)*20;
	}
	void getStatus() {
		System.out.println("-------------Status------------");
		System.out.println("Name :"+Name);
		System.out.println("Level: "+level);
		System.out.println("Hp: "+Hp);
		System.out.println("Att: "+Att);
		System.out.println("-------------------------------\n");
	}
	boolean die() {
		if(this.Hp<=0) {
			System.out.println("############################");
			System.out.println("         GAME OVER          ");
			System.out.println("############################\n");
			Hp = level*40+50;
			levelDown();
			return true;
		}
		else return false;
	}
	void rest() {
		this.Hp+=10;
	}
	boolean run(String s) {                                             
		if(s.equals("도망쳐")) {
			System.out.println("=================================\n");
			System.out.println("---------------------------");
			System.out.println("휴 무사히 도망친 것 같군.......");
			System.out.println("---------------------------\n");
			return true;
		}
		else return false;
	}
}

class Monster extends Character{
	private int num;
	private String pat[] = {};
	private String comant[] = {};
	
	Monster(String name, int hp, int att,String pat[],String comant[]){
		super(name,hp,att);
		this.pat = pat; this.comant = comant;
	}
	int damage(int num){
		this.num = num;
		System.out.println(this.Name+"의 "+pat[num]+"!");
		if(num == 0) {
			System.out.println((Att-5)+"데미지!");
			return Att-5;
		}
		else if(num==1) {
			System.out.println((Att)+"데미지!");
			return Att;
		}
		else {
			System.out.println((Att+5)+"데미지!");
			return Att+5;
		}
	}
	void say() {
		System.out.println("\""+comant[num]+"\"");
	}
	boolean die() {
		if(this.Hp<=0) {
			System.out.println("############################");
			System.out.println("  힘든 싸움이었다............   ");
			System.out.println("############################\n");
			return true;
		}
		else return false;
	}
}

