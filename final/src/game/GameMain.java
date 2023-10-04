package src.game;
import java.util.Random;
import java.util.Scanner;
import src.game.Character;
import src.game.Story;
public class GameMain {
	
	private Monster monsters[] = new Monster[5];                                                               //몬스터 객체배열 생성
	private String monsName[] = {"슬라임","고블린","오크","스켈레톤","와이번"};                 
	private String pat[] ={"몸통박치기","돌던지기","급소 강타"}; 
	private String comant[] = {"으이~","드가자아아...","화성 갈끄니까..."};
	
	private Monster boss[] = new Monster[4];
	private String bossName[] = {"파이썬","자바","C++","흑룡"};
	private String bossPat[][] = {{"땅가르기","모래뿌리기","진흙폭탄",},{"화염방사","도깨비불","연옥"},
			{"수류연타","물대포","바다회오리"},{"지옥의 불꽃","죽음의 화살","어둠의 의지"}};
	private String bossComant[][] = {{"어휴 겁쟁이","음... 점점 지루해지는데?","나 지금 열받았어!"},{"넌 진다","이리저리 피하는 쥐새끼같구나!","도망쳐라! 약해빠진놈들."},                            //보스몬스터 객체배열 생성
			{"이리와봐,정말 안 아프게 해줄게. 약속해.","사리분별이 안되나보네?","희망에 매달려라...남는건 그뿐일테니!"},{"종말을 내려주마","네 마음속에 공포가 보이는군","어둠이 두려운가?"}};

	
	GameMain(){                                                  //GameMain 함수에서 객체의 생성과 게임play함수를 실행함     								
		Story s = new Story();                                                  						//스토리 객체 생성
		
		Scanner name =  new Scanner(System.in);
		System.out.println("============게임 시작!============");               							//게임 주인공 객체 만들기
		System.out.println("내가 관리할 주인공의 이름은?");
		System.out.print("이름을 입력: ");
		Player player = new Player(name.next(),50,10,1);
		clearScreen();
		
		for(int i=0; i<monsters.length; i++)                                        
			monsters[i] = new Monster(monsName[i],(30+i*30),(5*(i+1)),pat,comant);
			
		for(int i=0; i<boss.length; i++)
			boss[i] = new Monster(bossName[i],(50+(i*40)),(15*(i+1)),bossPat[i],bossComant[i]);        
		
		int index = 0;         									//몬스터와 보스 인덱스 설정(초기값=0)                                                   
		int bossindex=0;
		
		play(monsters,boss,player,index,bossindex,s);       				//게임 시작 함수 실행
	}
	
	static void play(Monster monsters[],Monster boss[],Player player, int index,int bossindex,Story s) {       					//게임 플레이 함수 (인자값: 몬스터객체,보스객체,플레이어객체,몬스터인덱스,보스몬스터인덱스,스토리객체)
		int chap = 0;						//스토리의 챕터(초기값=0)
		while(true) {																																					
			Scanner stanswer = new Scanner(System.in);
			if(monsters[index].gH()<=0&&index<4) index++;
			if(boss[bossindex].gH()<=0&&bossindex<4) bossindex++;
			Scanner doing = new Scanner(System.in);
			System.out.println("==================================");            
			System.out.println("\n주인공이 쉬고있습니다................");																									
			System.out.println("\n=============무엇을 할까?===========\n");
			System.out.println("---------------------------");
			System.out.println("1.주인공 능력치 보기");
			System.out.println("2.주인공 성장 구경하기");
			System.out.println("3.스토리 챕터0"+chap);                             
			System.out.println("---------------------------\n");
			System.out.println("==================================");
			System.out.print("나의 선택은? \n>>");
			int answer = doing.nextInt();						//while 반복분 안에서 doing이라는 정수 입력값으로 선택지를 선택 할 수 있도록 함
			clearScreen();
			
			if(answer==1) {                                          //answer 가 1인경우 : 주인공의 능력치를 보여줌
				player.getStatus();
				back();
				clearScreen();
			}
			else if(answer==2) {
				if(index<=4) {                                          //answer 가 2인경우 : 주인공과 몬스터의 전투화면으로 넘어감 (fight함수 사용)
					fight(monsters,player,index); 						//fight함수 (인자값: 몬스터 객체,플레이어,몬스터 인덱스)
				}
				else {
					System.out.println("\n####더이상 잡을 몬스터가 없어####");              //몬스터는 총 5마리로 몬스터의 index 값이 4 이상일 경우 메세지 출력
				}
			}
			else if(answer==3) {												     //answer 가 3인경우 : 게임의 메인 스토리 챕터로 넘어감
				s.Chapter(chap);															
				if(chap==0||chap==3) {
					s.Phase2(chap);														//스토리 진행 방식:  1. 챕터스토리 출력  2. phase1출력 (보스 몬스터가 있는 경우 fight함수 실행)  
					if(s.select(stanswer.nextLine())) {									//				 3.phase2출력     4. 선택지함수에 선택값을 입력 > 정답: 챕터 변수+1 , 오답: continue로 반복문의 처음으로 되돌아감
					}
					else {
						chap++;
						clearScreen();
					}
				}
				else if(chap==6) {
					System.out.println("============게임 클리어!=============");
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
			player.rest();           //선택지가 끝나고 나면 player의 rest 메서드를 실행하여 주인공의 체력을 약간 회복해줌
		}
	}
	
	static void fight(Monster monsters[],Player player, int index) {               //fight 함수(몬스터 객체,플레이어 객체,몬스터의 인덱스번호)  
					while(true) {
						Scanner question = new Scanner(System.in);
						Random num = new Random();                                       //java의 Random 함수로 공격력의 값이 달라지도록 설계
						Random attack = new Random();
						int playerAttack = player.gAtt()+attack.nextInt(5);             
						System.out.println("\n============전투중...=============\n");
						next();
						System.out.println(monsters[index].gN()+"과의 전투");
						System.out.println("---------------------------");
						next();
						player.takeDamage(monsters[index].damage(num.nextInt(3)));       //캐릭터 클래스의 takeDamage 함수로 데미지를 받는 것을 구현함
						System.out.println("---------------------------");
						next();
						System.out.print(monsters[index].gN()+": ");                        
						monsters[index].say();                                            //몬스터 객체는 3가지의 공격 기술과 대사가 있음
						System.out.println("주인공의 남은 체력은.. "+player.gH()+"이다.");
						System.out.println("---------------------------");
						next();
						if(player.die()) {                                               //주인공이 죽었을 경우: 주인공의 레벨 감소
							player.levelDown();
							back();
							clearScreen();
							break;
						}
						System.out.println(player.gN()+": "+"\"나의 차례다..\"\n주인공이 몬스터에게 " + (playerAttack) + " 만큼의 데미지를 입혔다!");
						monsters[index].takeDamage(playerAttack);
						System.out.println(monsters[index].gN()+"의 남은 체력은.. "+monsters[index].gH()+"이다.");                        //캐릭터 클래스의 takeDamage 함수로 데미지를 받는 것을 구현함
						System.out.println("---------------------------");
						next();
						if(monsters[index].die()) {                         //몬스터가 죽었을 경우: 주인공 레벨업
							back();                                  
							clearScreen();
							player.levelUp();
							break;
						}                               
						System.out.println("\n======주인공이 계속 싸우게 할까?======\n");               //한턴의 싸움 이후에 주인공을 싸움에서 도망치게 할 수 있음
						System.out.print("도망가게 하려면 \"도망쳐\"를 입력하자 \n>>");
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
		for (int i = 0; i < 15; i++)                                            //게임 플레이 화면을 깔끔하게 해주는 함수: 줄바꿈을 15번 해준다.
		     System.out.println();
	}
	
	static void back() {
		Scanner back = new Scanner(System.in);
		System.out.print("========돌아가려면 아무키나 입력========\n=>");              //스토리 진행이나 전투 후에 돌아가기 전 호출되는 함수 
		back.nextLine();
	}
	
	static void next() {
		Scanner back = new Scanner(System.in);                                    //다음 전투 장면이나 다음 스토리 출력에 호출되는 함수
		back.nextLine();
	}
	
	public static void main(String[] args) {
		new GameMain();
	}
}
