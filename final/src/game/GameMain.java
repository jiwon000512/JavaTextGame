package src.game;
import java.util.Random;
import java.util.Scanner;
import src.game.Character;
import src.game.Story;
public class GameMain {
	
	private Monster monsters[] = new Monster[5];                                                               //���� ��ü�迭 ����
	private String monsName[] = {"������","���","��ũ","���̷���","���̹�"};                 
	private String pat[] ={"�����ġ��","��������","�޼� ��Ÿ"}; 
	private String comant[] = {"����~","�尡�ھƾ�...","ȭ�� �����ϱ�..."};
	
	private Monster boss[] = new Monster[4];
	private String bossName[] = {"���̽�","�ڹ�","C++","���"};
	private String bossPat[][] = {{"��������","�𷡻Ѹ���","������ź",},{"ȭ�����","�������","����"},
			{"������Ÿ","������","�ٴ�ȸ����"},{"������ �Ҳ�","������ ȭ��","����� ����"}};
	private String bossComant[][] = {{"���� ������","��... ���� ���������µ�?","�� ���� ���޾Ҿ�!"},{"�� ����","�̸����� ���ϴ� �����������!","�����Ķ�! ���غ������."},                            //�������� ��ü�迭 ����
			{"�̸��ͺ�,���� �� ������ ���ٰ�. �����.","�縮�к��� �ȵǳ�����?","����� �Ŵ޷���...���°� �׻����״�!"},{"������ �����ָ�","�� �����ӿ� ������ ���̴±�","����� �η��?"}};

	
	GameMain(){                                                  //GameMain �Լ����� ��ü�� ������ ����play�Լ��� ������     								
		Story s = new Story();                                                  						//���丮 ��ü ����
		
		Scanner name =  new Scanner(System.in);
		System.out.println("============���� ����!============");               							//���� ���ΰ� ��ü �����
		System.out.println("���� ������ ���ΰ��� �̸���?");
		System.out.print("�̸��� �Է�: ");
		Player player = new Player(name.next(),50,10,1);
		clearScreen();
		
		for(int i=0; i<monsters.length; i++)                                        
			monsters[i] = new Monster(monsName[i],(30+i*30),(5*(i+1)),pat,comant);
			
		for(int i=0; i<boss.length; i++)
			boss[i] = new Monster(bossName[i],(50+(i*40)),(15*(i+1)),bossPat[i],bossComant[i]);        
		
		int index = 0;         									//���Ϳ� ���� �ε��� ����(�ʱⰪ=0)                                                   
		int bossindex=0;
		
		play(monsters,boss,player,index,bossindex,s);       				//���� ���� �Լ� ����
	}
	
	static void play(Monster monsters[],Monster boss[],Player player, int index,int bossindex,Story s) {       					//���� �÷��� �Լ� (���ڰ�: ���Ͱ�ü,������ü,�÷��̾ü,�����ε���,���������ε���,���丮��ü)
		int chap = 0;						//���丮�� é��(�ʱⰪ=0)
		while(true) {																																					
			Scanner stanswer = new Scanner(System.in);
			if(monsters[index].gH()<=0&&index<4) index++;
			if(boss[bossindex].gH()<=0&&bossindex<4) bossindex++;
			Scanner doing = new Scanner(System.in);
			System.out.println("==================================");            
			System.out.println("\n���ΰ��� �����ֽ��ϴ�................");																									
			System.out.println("\n=============������ �ұ�?===========\n");
			System.out.println("---------------------------");
			System.out.println("1.���ΰ� �ɷ�ġ ����");
			System.out.println("2.���ΰ� ���� �����ϱ�");
			System.out.println("3.���丮 é��0"+chap);                             
			System.out.println("---------------------------\n");
			System.out.println("==================================");
			System.out.print("���� ������? \n>>");
			int answer = doing.nextInt();						//while �ݺ��� �ȿ��� doing�̶�� ���� �Է°����� �������� ���� �� �� �ֵ��� ��
			clearScreen();
			
			if(answer==1) {                                          //answer �� 1�ΰ�� : ���ΰ��� �ɷ�ġ�� ������
				player.getStatus();
				back();
				clearScreen();
			}
			else if(answer==2) {
				if(index<=4) {                                          //answer �� 2�ΰ�� : ���ΰ��� ������ ����ȭ������ �Ѿ (fight�Լ� ���)
					fight(monsters,player,index); 						//fight�Լ� (���ڰ�: ���� ��ü,�÷��̾�,���� �ε���)
				}
				else {
					System.out.println("\n####���̻� ���� ���Ͱ� ����####");              //���ʹ� �� 5������ ������ index ���� 4 �̻��� ��� �޼��� ���
				}
			}
			else if(answer==3) {												     //answer �� 3�ΰ�� : ������ ���� ���丮 é�ͷ� �Ѿ
				s.Chapter(chap);															
				if(chap==0||chap==3) {
					s.Phase2(chap);														//���丮 ���� ���:  1. é�ͽ��丮 ���  2. phase1��� (���� ���Ͱ� �ִ� ��� fight�Լ� ����)  
					if(s.select(stanswer.nextLine())) {									//				 3.phase2���     4. �������Լ��� ���ð��� �Է� > ����: é�� ����+1 , ����: continue�� �ݺ����� ó������ �ǵ��ư�
					}
					else {
						chap++;
						clearScreen();
					}
				}
				else if(chap==6) {
					System.out.println("============���� Ŭ����!=============");
					break;
					}
				else {                                              
					s.Phase1(chap);
					fight(boss,player,bossindex);
					if(player.gH()==0||(player.gH()>0&&boss[bossindex].gH()>0)) {
						index=index==0?0:index--;
						monsters[index].sH(30+index*30);
						continue;
					}
					s.Phase2(chap);
					if(s.select(stanswer.nextLine())) {
						boss[bossindex].sH(10);
						//next();
						clearScreen();
					}
					else {
						chap++;
						//next();
						clearScreen();
					}
				}
			}
			player.rest();           //�������� ������ ���� player�� rest �޼��带 �����Ͽ� ���ΰ��� ü���� �ణ ȸ������
		}
	}
	
	static void fight(Monster monsters[],Player player, int index) {               //fight �Լ�(���� ��ü,�÷��̾� ��ü,������ �ε�����ȣ)  
					while(true) {
						Scanner question = new Scanner(System.in);
						Random num = new Random();                                       //java�� Random �Լ��� ���ݷ��� ���� �޶������� ����
						Random attack = new Random();
						int playerAttack = player.gAtt()+attack.nextInt(5);             
						System.out.println("\n============������...=============\n");
						next();
						System.out.println(monsters[index].gN()+"���� ����");
						System.out.println("---------------------------");
						next();
						player.takeDamage(monsters[index].damage(num.nextInt(3)));       //ĳ���� Ŭ������ takeDamage �Լ��� �������� �޴� ���� ������
						System.out.println("---------------------------");
						next();
						System.out.print(monsters[index].gN()+": ");                        
						monsters[index].say();                                            //���� ��ü�� 3������ ���� ����� ��簡 ����
						System.out.println("���ΰ��� ���� ü����.. "+player.gH()+"�̴�.");
						System.out.println("---------------------------");
						next();
						if(player.die()) {                                               //���ΰ��� �׾��� ���: ���ΰ��� ���� ����
							player.levelDown();
							back();
							clearScreen();
							break;
						}
						System.out.println(player.gN()+": "+"\"���� ���ʴ�..\"\n���ΰ��� ���Ϳ��� " + (playerAttack) + " ��ŭ�� �������� ������!");
						monsters[index].takeDamage(playerAttack);
						System.out.println(monsters[index].gN()+"�� ���� ü����.. "+monsters[index].gH()+"�̴�.");                        //ĳ���� Ŭ������ takeDamage �Լ��� �������� �޴� ���� ������
						System.out.println("---------------------------");
						next();
						if(monsters[index].die()) {                         //���Ͱ� �׾��� ���: ���ΰ� ������
							back();                                  
							clearScreen();
							player.levelUp();
							break;
						}                               
						System.out.println("\n======���ΰ��� ��� �ο�� �ұ�?======\n");               //������ �ο� ���Ŀ� ���ΰ��� �ο򿡼� ����ġ�� �� �� ����
						System.out.print("�������� �Ϸ��� \"������\"�� �Է����� \n>>");
						String answer = question.nextLine();
						clearScreen();
						if(player.run(answer)) {
							back();
							clearScreen();
							break;
						}
						clearScreen();
					}
	}
	
	static void clearScreen() {
		for (int i = 0; i < 15; i++)                                            //���� �÷��� ȭ���� ����ϰ� ���ִ� �Լ�: �ٹٲ��� 15�� ���ش�.
		     System.out.println();
	}
	
	static void back() {
		Scanner back = new Scanner(System.in);
		System.out.print("========���ư����� �ƹ�Ű�� �Է�========\n=>");              //���丮 �����̳� ���� �Ŀ� ���ư��� �� ȣ��Ǵ� �Լ� 
		back.nextLine();
	}
	
	static void next() {
		Scanner back = new Scanner(System.in);                                    //���� ���� ����̳� ���� ���丮 ��¿� ȣ��Ǵ� �Լ�
		back.nextLine();
	}
	
	public static void main(String[] args) {
		new GameMain();
	}
}
