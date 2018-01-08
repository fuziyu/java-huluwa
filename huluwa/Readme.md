# 葫芦娃大战妖精
## 代码框架
### 以下是代码目录结构
    -nju.java
        -FilmCompany
            -Actor
                -Actor.java
                -Crocodile.java
                -Frog.java
                -Grandpa.java
                -Huluwa.java
                -Scorpion.java
            -ActorFactory
                -IActorFactory
                -CrocodileFactory.java
                -FrogFactory.java
                -GrandpaFactory.java
                -HuluwaFactory.java
                -ScorpionFactory.java
            -Film.java
            -FilmCompany.java
            -FrameManager.java
            -BackGround.java
            -Thing2D.java
        -Main.java
### 相关类介绍
#### 1.Actor
Actor：Actor为抽象类，记录了一些有关Actor的基本属性：
```java
protected Stage stage;
protected int attackForce; // 当前actor的攻击力
public enum STATE {ALIVE, DEAD}
protected STATE state; // 当前actor的状态
public enum ROLE {GOODGUY, BADGUY}
protected ROLE role; // 当前actor的角色
...
```
并且Actor类还有一些待实现的抽象方法：
```java
public abstract boolean isNextPositionValid(int newX, int newY); // 判断新的位置是不是可以移动
public abstract void move(int x, int y); // 移动
...
```
Actor中其余具体的类都继承自抽象类Actor。
#### 2.ActorFactory
IActorFactory：定义的用于生产演员的工厂；
```java
Actor getActor(int x, int y, Stage stage); // 在舞台的指定位置获得一个演员
```
其余具体的Factory类都实现这个接口，用于分别生产不同类型的演员。

#### 3.Film(继承自JFrame类)
Film：stage上的表演在Film中反映；
```java
public final class Film extends JFrame {
    public Film(Stage stage) { 
        InitUI(stage);
    }

    public void InitUI(Stage stage) {
        ...
    }
}
```
#### 4.FrameManager
FrameManager: 用于战斗过程每一帧画面的保存和读取；
```java
public void saveFrame(ArrayList world, int w, int h, int worldWidth, int worldHeight) {
    ...
} // 将当前stage上的所有actor所表现的战斗过程记录下来
public ArrayList<String> loadFrame() {
} // 从文件中读取保存的场景
...
```
战斗画面的表示以下为例：
```java
// 战场大小为7 * 10
String frame =
                    "1.......f.\n" +
                    ".2.....f..\n" +
                    "..3...c...\n" +
                    "g..4.s..c.\n" +
                    "..5...c...\n" +
                    ".6.....f..\n" +
                    "7.......f.\n";
```
这里使用字符串的形式来表示战斗画面，frame刻画了一个大小为7*10的战场，其中每个位置所对应的actor由相应的字符表示，例如g -- grandpa, 1 -- huluwa大娃， 2 -- huluwa二娃等等；在保存及读取战斗画面时通过字符流写入及读取即可。
#### 5.Stage(继承自JPanel类)
Stage：舞台，包括以下属性：
```java
private final int xOffset = 80; // 舞台每个单元格的宽度
private final int yOffset = 80; // 舞台每个单元格的长度
private final int w = 800; // 舞台的宽度
private final int h = 640; // 舞台的长度
private Background background; // 舞台的背景
private ArrayList<Actor> actors; // 舞台上的演员
public enum POSITIONSTATE {EMPTY, GOODIN, BADIN, DEADIN}
private POSITIONSTATE[][] positionState; // 记录舞台每个位置的状态
```
Stage类还包括以下方法：
```java
public void paint(Graphics g) {
    super.paint(g);
    buildStage(g);
    ...
} // 绘制函数，当舞台上的演员行为发生改变时，会调用Stage类的repaint()函数，继而调用paint()函数
public void buildStage(Graphics g) {
    ...
} // 在paint()中被调用，响应演员的行为变化，例如：葫芦娃的位置发生变化，则应该在变化后的位置绘制葫芦娃
```
#### 6.FilmCompany
FilmCompany：聚合了以上介绍的若干类成员：
```java    
enum STATE {READY, ACTION, OVER, PLAYBACK}
private STATE filmState; // 当前film的状态：就绪，开始，结束，回放
private FrameManager frameManager; // 拍电影以及回放电影的人
private Film film; // 电影
private Stage stage; // 舞台
private HuluwaFactory huluwaFactory; // 生产葫芦娃的工厂
private FrogFactory frogFactory; // ...
private ScorpionFactory scorpionFactory; // ...
private CrocodileFactory crocodileFactory; // ...
private GrandpaFactory grandpaFactory; // ...
...
```
FilmCompany类还有以下方法：
```java
public void initStage(); // 初始化舞台
public void changeStage(); // 改变舞台的状态
public void playBack(); // 回放，将filmState置为PLAYBACK
public void action(); // 开始表演／战斗，将filmState置为ACTION
public void start(); // 将filmState置为READY
```
#### 7.Thing2D
Thing2D：定义了一个二维物体的属性和行为。
### Actor的移动
以葫芦娃为例，每个葫芦娃周围有八个位置：
```
P1 P2 P3
P4    P5
P6 P7 P8
```
在葫芦娃类中，定义一个数组：
```java
int [][]pace; 
```
假设舞台单元格大小为80*80，则pace中依次存储：
```java
-80,-80 -- P1 
0,-80 -- P2 
80,-80--P3
...
```
即pace数组用于计算葫芦娃的位移。

为了使得每个葫芦娃的移动具有随机性，在葫芦娃的move()方法中：
```java
public void move() {
    ...
    for(int i = 0;i < pace.length;i++)
        randomIndex.add(i);
    Collections.shuffle(randomIndex);
    ...
}
```
使用Collections的shuffle方法对位置的顺序进行打乱，再选择可移动的位置。

可移动的位置：
- 空的位置
- 对于葫芦娃阵营，妖怪所在的位置也是可移动的，届时会进行战斗，对于妖怪同理

其余actor的移动方式与葫芦娃相同，所有actor的移动速度相同。
### 战斗策略
演员分为正反两派：
- 正派：葫芦娃 爷爷
- 反派：蝎子精 青蛙 鳄鱼

战斗开始之前，为不同的演员设定不同的攻击力，当正派和反派相遇的时候，根据攻击力的大小决定双方的生死，如果双方攻击力相同，则随机决定双方的生死。
#### 演员状态的表示
- 生：使用站立状态的图片表示
- 死：使用躺下状态的图片表示
#### 攻击力的初始设定
- 葫芦娃 -- 2
- 爷爷 -- 3
- 青蛙 -- 1
- 鳄鱼 -- 2
- 蝎子精 -- 3
#### 战斗的开始和结束
- 就绪状态；
- 按下空格键开始；
- 当双方其中一方的数量为0时，结束，中断线程，画面停止刷新；
- 在就绪状态或者结束状态时，按下L键选择文件，对战斗内容进行回放；
- 默认将战斗文件记录到当前目录下的frame.txt文件中；
- 在回放时将不记录文件。