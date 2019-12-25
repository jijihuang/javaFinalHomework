# Final：葫芦娃大战妖怪

- 171860019 黄嘉颖 951493085@qq.com

## 开发环境

- IntelliJ IDEA 2019.2.4 (Community Edition)
- Java version 1.8.0_231
- maven version 3.6.3

## 项目简介

- Huluwa battle是一个葫芦娃大战妖怪的游戏。游戏开始前玩家选择已有的阵型或自助布阵，游戏开始后葫芦娃与妖精自动进行战斗。
- 除了战斗功能外，支持录像回看,战斗重置。

## 软件运行截图

![](Screenshots/battle.png)

## 运行说明

- 使用maven对项目进行编译，打包
- 运行target目录下HuluwaFight-1.0-SNAPSHOT.jar
- 进入游戏后，对于葫芦娃阵容可以点击按钮或用鼠标拖拽葫芦娃进行排针，对于蛇精阵容可以点击按钮选择阵容或点击随机变换，点击后阵型开始变换再点击停止。
- 按下葫芦娃登场，葫芦娃会以随机排列顺序长蛇阵型排列，按下葫芦娃列阵，会从老大开始移动，变成按排行排序的长蛇形在最左列
- 按下爷爷助威和蛇精助威可以多加一名成员。
- 按SPACE键开始游戏，按R键重置游戏，按L键可以在target目录下选择以时间命名的录像文件进行回放。文件名例如:2019-12-24 12/44/56

## 需求分析

- 绘制一个`NxM`的二维空间（`N`和`M`足够大，空间左上角为原点，向右向下分别为X轴和Y轴正方向），该空间中的任意一个位置坐标上**可且仅可**站立一个生物体（葫芦娃、老爷爷、蛇精、蝎子精、小喽啰均属于生物体）；
- 请让七个兄弟和老爷爷以下图中的某种阵型在空间的左侧战队；
- 请让妖精（蛇精、蝎子精、小喽啰）以下图中的某种阵型在空间右侧战队；
- 以上各生物体均实现为一个线程，用GUI应用中应该用特定机制进行UI刷新，通过线程同步机制避免多个生物站上同一位置、多个生物同时杀死某个生物等问题；
- 按空格键时所有生物体线程执行`start()`，向敌方前进；
- 当某个生物体于敌方相遇（两者间的X轴距离和Y轴距离小于某个常量）时，选取一个概率决定双方生死，死者留下实体，生者寻找下一个敌人攻击；
- 某一方生物全部死亡时，结束；
- 图形化框架限定使用JavaFX。

## 项目详情

### 目录

* /replay 存放精彩回放
* /uml 存放puml文件
* /ScreenShots 存放了README.md使用到的图片
* /src/main/java 项目源代码
* /src/main/resources 项目资源文件
* /src/test/java 单元测试代码

## 设计分析

### Package creature

#### Creature类

- 作为最基本的生物体类拥有名称、坐标、图像、图像地址等属性，均为protected。实现了runnable接口

- 拥有接口可以获得属性或者设置坐标属性

- 拥有方法moveTo和moveFrom，分别可以让物体移动到指定坐标或从当前坐标上消失

- 拥有方法public Stack<Coordinate> moveFromTo(Space battleground,int destX,int destY)获得移动到destX,destY的最短路径

  ```java
  public class Creature implements Runnable{
      /*...*/
  }
  ```

  

#### Huluwa类

- Creature子类，另外拥有rank排行和color颜色
- 拥有接口可以获得排行

#### Demon类

- Creature子类，在此仅用来归类妖怪，方便以后对妖怪添加方法
- 是Bat/Bee/Centipede/Frog/Scorpion/Snake的不同妖精类的父类

#### Grandpa类

- Creature子类，因为老爷爷不是妖怪也不是葫芦娃，方便以后对老爷爷添加方法

  creatureUML图：

  ![](Screenshots/creatureUML.png)

#### HuluwaTeam类

- 由葫芦娃数组brothers和啦啦队员老爷爷cheerleader组成

- 拥有接口获得属性

- void randomQueue()对葫芦娃兄弟乱序排列

- void bubbleSort()按照排行排序的方法

- void generateChangshePattern(...)覆盖了排列阵法

- void generateTheCheerPattern(...)设置啦啦队员位置的接口

  HuluwaTeamUML图：

  ![](Screenshots/HuluwaTeam.PNG)

#### SnakeTeam类

- 由妖怪数组demons和啦啦队员蛇精cheerleader组成
- 拥有接口获得各属性
- void generateChangshe/Chonge/....（共八种）Pattern(...)覆盖了排列阵法
- void generateTheCheerPattern(...)设置啦啦队员位置的接口

SnakeTeamUML图：

![](Screenshots/SnakeTeam.png)

### Package queuepattern

#### HuluwaTeamQueuePattern 接口

- void generateChangshePattern(Space battleground, int x, int y))用来设置葫芦娃队伍的阵型
- void generateCheerPattern(Space battleground, int x, int y))用来设置老爷爷啦啦队的位置

```java
public interface HuluwaTeamQueuePattern {
    void generateTheCheerPattern(Space battleground, int x, int y);
    void generateChangshePattern(Space battleground, int x, int y);
    void generateChongePattern(Space battleground, int x, int y);
    void generateFangyuanPattern(Space battleground, int x, int y);
    void generateFengshiPattern(Space battleground, int x, int y);
    void generateHeyiPattern(Space battleground, int x, int y);
    void generateYanxingPattern(Space battleground, int x, int y);
    void generateYanyuePattern(Space battleground, int x, int y);
    void generateYulinPattern(Space battleground, int x, int y);
}
```

#### SnakeTeamQueuePattern 接口

- void generateChangshe/Yanxing/...（共8种）Pattern(Space battleground, HuluwaTeam huluwaTeam, int x, int y)用来设置蛇精妖怪队伍的阵型

- void generateCheerPattern(Space battleground, int x, int y))用来设置蛇精啦啦队的位置

- ```java
  public interface SnakeTeamQueuePattern {
    	void generateTheCheerPattern(Space battleground, int x, int y);
      void generateChangshePattern(Space battleground, int x, int y);
      void generateChongePattern(Space battleground, int x, int y);
      void generateFangyuanPattern(Space battleground, int x, int y);
      void generateFengshiPattern(Space battleground, int x, int y);
      void generateHeyiPattern(Space battleground, int x, int y);
      void generateYanxingPattern(Space battleground, int x, int y);
      void generateYanyuePattern(Space battleground, int x, int y);
      void generateYulinPattern(Space battleground, int x, int y);
  }
  ```

### Package space

#### Cell类

- 表示二维空间内一个方格坐标，拥有横纵坐标属性以及在这个方格上的生物体
- 拥有接口获得和设置各属性
- boolean isCellEmpty()判断该位置是否有生物体
- void removeCreatureOnTheCell()讲方格上的生物体移除

```java
public class Cell <T extends Creature>{
    private int coordinateX;
    private int coordinateY;
    private double coordinateXx;
    private double coordinateYy;
    private T creatureOnTheCell;
    
    public boolean isCellEmpty(){
   			/*...*/
    }
    public void removeCreatureOnTheCell(){
        /*...*/
    }
```

#### Space类

- 表示一个二维空间，拥有长宽属性和方格数组battleField
- 拥有接口获得和设置属性，以及获得和设置方格上的生物体
-  boolean isExceedTheBattleField(...)判断坐标是否超出二维空间
- 拥有方法判断该位置是否为空以及移除方格上的生物体

spaceUML图：

![](Screenshots/spaceUML.png)

### Package battle

#### battle类

- 拥有battleField二维空间、葫芦娃队和蛇精队属性

battleUML图：

![](Screenshots/battleUML.png)

### Package navigation

#### Navigation类

- 导航系统，有需要计算最短路径的各种属性

- public Stack<Coordinate> useTheNavigation(...)用来获取当前生物体移动到目的地点最短路径

  navigationUML图：

  ![](uml/navigationUML.png)

### Package replay

#### ReplayWriter类

- 专门用于写入文件记录葫芦娃战斗细节，
- 静态方法public static void setFileWriter用于设置字符流输出流
- public void writeIntoFile用来向文件写入字符
- void closeFile用来关闭字符流输出流

```java
public class ReplayWriter {

    private static FileWriter fileWriter;

    public static void setFileWriter(FileWriter fileWriter) {
        ReplayWriter.fileWriter = fileWriter;
    }

    public void writeInToFile(String data) throws Exception{
        fileWriter.write(data);
    }

    public void closeFile()throws Exception{
        fileWriter.close();
    }

}
```

#### Replay类

- 实现runnable接口
- 当游戏开始时，首先在游戏目录下创建当前时间为名的文件，然后把该文件的FileWriter赋给ReplayWriter类的静态变量fileWriter。
- 启动所有生物体线程，当生物线程进行move和attack等操作时直接new一个ReplayWriter对象，
- 通过writeInToFile方法可以实现多个线程往同一个文件中写数据，并且实现了写文件的功能与Creature类的功能分离。
- 进行录像回放时，首先由用户选择打开一个log文件，然后将该文件的BufferedReader和程序游戏控制器GameController构造一个Replay对象。然后Replay的run方法中定时从BufferedReader br中读取一条记录，对BattleField battle中的数据进行操作，然后通过GameController gc刷新一次UI。
- 虽然每个生物体线程是并发进行的，但运行时记录的写入记录时间戳，通过时间戳前后不同时刷新ui完成录像回放。

## 使用javafx增加GUI

### Main类

- Main继承于javafx的Application类
- 这里使用了Scene builder进行UI的可见化设置。在start函数里通过加载FXML文件进行控件的布局。

```java
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url=getClass().getClassLoader().getResource("sample.fxml");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(url);
        Parent root = loader.load(url);
        primaryStage.setTitle("Huluwa battle");
        primaryStage.setScene(new Scene(root, 1300, 800));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

### Controller类

- 在Scene builder的设计里，控件的控制和监听事件注册由Controller类里的函数完成。
- Controller类里包含以下数据成员。battle含有整个对战的生物体以及二维空间。battleBackground是对战的背景图片。depth和width分别是背景的长宽。selectedCreature是当前选中的生物体，用来对应鼠标事件的，timeline是妖怪阵容变化动画的时间线，timelineHuluSort是葫芦娃从乱序到对应位置的动画的时间线。startorEnd用来标记对应妖怪阵容变化按键的开始和停止，currentHuluwaSortDone用来标记葫芦娃走到对应位置后动画的结束。huluSortNum用来记录已排好序的葫芦娃的数量。lastRand用来记录上一个妖怪变化的阵容。path用来存储当前葫芦娃从当前位置到对应位置的具体路径。isHuluwaOnTheSpace记录葫芦娃是否已上场

```java
    private Battle battle;
    private Image battleBackground;
    private int width;
    private int depth;
    private Creature selectedCreature;
    private Timeline timeline;
    private Timeline timelineHuluSort;
    private boolean startOrEnd;
    private boolean currentHuluwaSortDone;
    private int huluSortNum;
    private int lastRand;
    private Stack<Coordinate> path;
    private boolean isHuluwaOnTheSpace;
```

- Controller类里有以下控件。Button用于控制阵营变换阵型。canvas作为画布用于显示对战场面。Label用于显示当前选取的对象的名称。ImageView用于显示当前选择的对象的图片。

```java
    @FXML private Canvas canvas;
    @FXML private ImageView selectedImage;
    @FXML private Label selectedLabel;
    @FXML private Button randomHuluTeam;
    @FXML private Button shexingHuluTeam;
    @FXML private Button heyiDemonTeam;
    @FXML private Button yanxingDemonTeam;
    @FXML private Button chongeDemonTeam;
    @FXML private Button changsheDemonTeam;
    @FXML private Button yulinDemonTeam;
    @FXML private Button fangyuanDemonTeam;
    @FXML private Button yanyueDemonTeam;
    @FXML private Button fengshiDemonTeam;
    @FXML private Button grandpaCheer;
    @FXML private Button snakeCheer;
    @FXML private Button randomChange;
```

### Refresher类

- 实现runnable接口，用于定时刷行画面

  ```java
  public class Refresher implements Runnable{
      private Controller controller;
      private boolean gameStart;
      public Refresher(Controller controller){
          this.controller=controller;
          gameStart=true;
      }
  
      @Override
      public void run() {
      /*...*/
      }
  ```

### 战斗功能

* GameController类中有一个成员BattleField battle包含了游戏所需要的二维空间和生物体们。
* 当用户按下空格后，GameController类中的键盘事件处理函数捕捉到该事件，然后通过Thread类运行所有的生物体线程和画面定时刷新线程。
* 通过定时刷新的线程判断游戏是否已经结束，结束条件为某一方全部死亡。
* 在void run()函数里，生物体首先会确定自己是否已经死亡。
* 如果活着会在forward函数中寻找在自己这行的敌人，并选择它作为进攻目标。然后向前走一步。当与目标相遇，则二者以随机概率决定生死。若该行没有敌人，则下移一行，走到最底后向上移动。
* 如果生物体已经死亡，进程结束。
* 上述过程循环重复进行，直到一个生物体的生命期结束或者有其他进程关闭了它。

### 线程安全的实现

* 当每个生物线程进行移动和攻击时，对该部分代码加上synchronized锁住，保证一个生物体进程在进行移动和攻击时独享资源，避免冲突。

## 编写单元测试

- 使用了JUnit对HuluwaRanksort，Space进行了单元测试，以SpaceTest为例

  ```java
  public class SpaceTest {
  
      @Test
      public void isExceedTheBattleField() {
          Space testSpace= new Space(15,15);
          assertEquals(false,testSpace.isExceedTheBattleField(1,2));
          assertEquals(true,testSpace.isExceedTheBattleField(100,100));
      }
  
      @Test
      public void isTheCellEmpty() {
          Space testSpace= new Space(15,15);
          testSpace.setTheCreatureOnTheCell(1,2,new Creature());
          assertEquals(true,testSpace.isTheCellEmpty(2,2));
          assertEquals(false,testSpace.isTheCellEmpty(1,2));
      }
  }
  ```

## 增加了一点额外功能

### 鼠标选中显示生物体的图片和名字

- 在Controller类里有函数private void canvasOnMouseClicked/private void canvasOnMouseDragged，通过Scene builder注册到了canvas的鼠标点击事件上。当鼠标在画布canvas上点击时，就会通过坐标的换算找到battle类battleField成员对应位置上的生物体，然后显示该生物体的图片和名称。

```java
    @FXML private void canvasOnMouseClicked(MouseEvent event) {
        canvas.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x=(int)event.getX()-280;
                int y=(int)event.getY();
                //System.out.println("x:"+x+"y:"+y);
                if (x>0&&y>0)
                {
                    x=x/60;
                    y=y/60;
                    if (battle.battleField.getTheCreatureOnTheCell(x,y)!=null){
                        selectedCreature=battle.battleField.getTheCreatureOnTheCell(x,y);
                        selectedImage.setImage(selectedCreature.getPicture());
                        selectedLabel.setText(selectedCreature.getName());
                    }
                }
            }
        });
    }
```

### 拖拽选中的生物体到其他空的地方

- 在Controller类里有函数private void canvasOnMouseDragged(MouseEvent event)，通过坐标的换算找到battle类battleField成员对应位置，看是否有生物体在上面，如果没有就放在上面。

```java
@FXML private void canvasOnMouseDragged(MouseEvent event) {
        canvas.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x=(int)event.getX()-280;
                int y=(int)event.getY();
                //System.out.println("x:"+x+"y:"+y);
                if (x>0&&y>0)
                {
                    x=x/60;
                    y=y/60;
                    if (selectedCreature!=null&&battle.battleField.isTheCellEmpty(x,y)) {
                        selectedCreature.moveFrom(battle.battleField);
                        selectedCreature.moveTo(battle.battleField,x,y);
                        display();
                    }
                }
            }
        });
    }
```

### 妖怪们每隔0.5s变换一次队形

- 通过按钮randomChange来控制，按一下去开始，再按就结束，用一个boolean类型来标记。用时间线timeline设置动画，生成随机数对应不同队形

```java
        timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rand = (int) Math.round(Math.random() * 7 + 1);
                while(true){
                    if (rand!=lastRand){
                        break;
                    }
                    else {
                        rand = (int) Math.round(Math.random() * 7 + 1);
                    }
                }
                switch (rand) {
                    case 1:
                        battle.snakeTeam.generateTheSpecificPattern(new HeyiPattern(), battle.battleField, 8, 4);
                        display();
                        break;
                    case 2:
                        battle.snakeTeam.generateTheSpecificPattern(new YanxingPattern(), battle.battleField, 14, 3);
                        display();
                        break;
                    case 3:
                        battle.snakeTeam.generateTheSpecificPattern(new ChongePattern(), battle.battleField, 14, 3);
                        display();
                        break;
                    case 4:
                        battle.snakeTeam.generateTheSpecificPattern(new ChangshePattern(), battle.battleField, 14, 3);
                        display();
                        break;
                    case 5:
                        battle.snakeTeam.generateTheSpecificPattern(new YulinPattern(), battle.battleField, 11, 3);
                        display();
                        break;
                    case 6:
                        battle.snakeTeam.generateTheSpecificPattern(new FangyuanPattern(), battle.battleField, 12, 3);
                        display();
                        break;
                    case 7:
                        battle.snakeTeam.generateTheSpecificPattern(new YanyuePattern(), battle.battleField, 14, 2);
                        display();
                        break;
                    case 8:
                        battle.snakeTeam.generateTheSpecificPattern(new FengshiPattern(), battle.battleField, 11, 3);
                        display();
                        break;
                    default:
                        System.out.println("wrong in random!!!");
                }
                lastRand=rand;
            }
        });
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrame);
```

## 面向对象的设计理念

### SRP 单一职责原则

- Creature类中寻找路径的功能通过Navigation类实现，寻路与Creature类的功能实现分离。

### OCP 开放封闭原则

- 将变换阵型变成接口queuePattern，然后具体的阵型implement这个接口，具体实现来获得不同的阵型。保证了接口的统一，便于阵型的添加和调用。

### LSP LISKOV替换法则

- 所有从Creature类继承的子类HuluWa、Demon、Grandpa、Snake和Scorpion都可以替换程序中的Creature类。

### ISP 接口隔离原则

- 在葫芦娃游戏的代码设计中，没有出现一个类拥有其功能所不需要的方法。

### CARP 合成/聚合复用原则

- 二维空间类TwoDimensionSpace是有一组Tile类合成而来。
- 战场类BattleField类由Creature的子类和TwoDimensionSpace类聚合而成。

## 反射机制应用

- 导入creature下的配置文件obj.properties，利用反射机制，对snakeTeam中的18个小怪创建对象。一共有7种不同的小怪：Scorpion、BlueFrog、BrownFrog、PurpleFrog、Bat、Bee、Centipede，根据配置文件决定生成哪些不同的小怪。


```java
    public SnakeTeam()throws Exception{
        demons=new Demon[19];
        Properties prop = new Properties();
      prop.load(Creature.class.getClassLoader().getResourceAsStream("creature/obj.properties"));
        int num=18;
        for (int i=0;i<=num;i++){
            String number=i+"";
            String name="Demon"+number;
            String demon="creature."+(String)prop.get(name);
            demons[i]=(Demon)Class.forName(demon).newInstance();
        }
        cheerleader=new Snake();
    }
```

## 泛型机制应用

- 对于每一个二维空间的小格Cell上，运用泛型机制，上面可以站不同的Creature。


```java
public class Cell <T extends Creature>{
    private int coordinateX;
    private int coordinateY;
    private T creatureOnTheCell;
...
}
```

## 注解应用

- 编写annotation,对代码进行标记

```java
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorAnno{
    String name() default "Huang Jiaying";
    String website() default "cn.edu.nju";
    int revision() default 1;
}
```

## 思考总结

- 葫芦娃这个大作业项目让我体会到对于代码复用和扩展方便与否取决于前期对于项目的框架结构和类之间的组合或继承关系是否足够清晰
- 在写代码的过程中也出现了非常多比较神奇的bug。本来是在win10系统下写的，但是发现外星人笔记本对于键盘响应事件的字母和数字的编码出现了编译器无法判断的情况，幸亏java是跨平台的语言，换到mac os系统下运行发现问题解决。
- 之后在maven编译打包过程下，需要根据maven对于文件结构的严格要求将目录设置好，否则会编译后无法运行的情况。
- 还有一个比较神奇的bug是我在使用背景图的时候用了一个.jpg格式的图片，在idea下运行正常 但是maven编译后不能显示背景文件，对于java的报错也不是很清晰，只能看出是在图片加载出现问题，最后换成.png解决问题。
- 对于葫芦娃这个项目其实还有很多设想未能实现，对自己的代码仍不是很满意，有一部分缘于之前的一些设计限制了之后的代码。对于一个游戏项目，如果能多人合作应该会对代码的封装和复用理解会更深刻，自己一个人写有时候可能为了图方便随心所欲用public，并且可以知道各个类的内部信息。
- 通过本学期java课程的学习，对面向对象的程序设计有了更深刻的领悟，并且对于java的多线程机制有了一定的了解。