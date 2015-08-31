//Fix pause feature
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Bounce extends JPanel{
    private int finalScore=0;
    private int tankFireCount=0;
    private int tank2FireCount=0;
    private boolean pause=false;
    private TankBall tank;
    private TankBall tank2;
    private Planet planet;
    
    //Variable ints and booleans, can change for game type the players want.
    int bulletLimit=5;
    int bounceLimit=2;
    int healthMax=30;
    boolean planetWant=true;
    int planetGrow;
    //Planet grow time, smaller faster
    int planetGrowTime=14;
    
    int countBoth=0;
    int counterStart=0;
    
    Bullet[] bulletArray = new Bullet[bulletLimit];
    Bullet[] bulletArray2 = new Bullet[bulletLimit];
    
    Particle[] particleArray = new Particle[2*bulletLimit];
    
    Explosion[] explosionArray = new Explosion[bulletLimit*2];
    
    private boolean redReady=false;
    private boolean blueReady=false;
    private boolean bothReady=false;
    private boolean gameStarted=false;
    
    private int biggerRadius;
    private int biggerRadius2;
    
    private int redHitCount;
    private int blueHitCount;
    
    private int bulletAvailable=0;
    
    
    private javax.swing.Timer timer;
    
    Random rand=new Random();
    
    private boolean keyTPressed = false;
    private boolean keyGPressed = false;
    private boolean keyFPressed = false;
    private boolean keyHPressed = false;
    
    private boolean keyZPressed = false;
    private boolean keyXPressed = false;
    private boolean keyCPressed = false;
    
    private boolean keyUPPressed = false;
    private boolean keyDOWNPressed = false;
    private boolean keyLEFTPressed = false;
    private boolean keyRIGHTPressed = false;
    
    private boolean keyCOMMAPressed = false;
    private boolean keyPERIODPressed = false;
    private boolean keySLASHPressed = false;
    
    
    //Special abilities controls
    private boolean keyS=false;
    private boolean keyL=false;
    
    private Random random=new Random();
    private int tankChoice= 3;
    private int tank2Choice= 6;
   
    private int tankCooldown=0;
    private int tank2Cooldown=0;
    
    private boolean tankDraw=true;
    private boolean tank2Draw=true;
    int tankInvisCount=0;
    int tank2InvisCount=0;
    int bulletToExpand=0;
    int bulletToExpand2=0;
    int expandTimer=30;
    int expandTimer2=30;
    int ghostTimer=100;
    int ghostTimer2=100;
    int sheildTimer=50;
    int sheildTimer2=50;
    
    public Bounce(Color backColor){
        //tank placement, size and color.
        tank =new TankBall(new Circle(60,400,30,new Color(200,200,75)), Color.blue, new Color(250,250,100), 0, new Color(125,125,250),new Color(200,200,250));
        //tank2 placement, size and color.2
        
        tank2 = new TankBall(new Circle(400, 60, 30, new Color(0,200,75)), Color.red, new Color(50,250,50), 0, new Color(250,125,125),new Color(250,200,200));
        
        if(planetWant)
        planet=new Planet(new Circle(230,230,5, new Color(200,145,134)));     
        
        setBackground(backColor);
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        //Circle circle.setSpeed(6);
        timer=new javax.swing.Timer(1000/70, new MoveListener());
        timer.start();
        addKeyListener(new BounceKeyListener());
    }
      
    
    
        public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        for(int i=0;i<bulletLimit*2;i++){
             if(explosionArray[i]!=null){
            explosionArray[i].draw(g);
        }
        if(particleArray[i]!=null){
        particleArray[i].draw(g);
    }
       
 }
         if(tankDraw)
        tank.draw(g);
        
        if(tank2Draw)
        tank2.draw(g);
        
        if(planetWant)
        planet.draw(g);
        
        if(tankDraw){
        for(int p=0;p<bulletLimit;p++){
        if(bulletArray[p]!=null)
        bulletArray[p].Render(g);
    }
}
        if(tank2Draw){
        for(int w=0;w<bulletLimit;w++){
        if(bulletArray2[w]!=null)
        bulletArray2[w].Render(g);
    }
}
    
    int xMid=(int)(tank.circle.getX()+tank2.circle.getX())/2;
    int yMid=(int)(tank.circle.getY()+tank2.circle.getY())/2;
    
    //Begin text things
    if(bothReady==false){
        g.setColor(Color.white);
        g.drawString("?READY?",xMid,yMid);
       if(redReady){
        g.setColor(Color.red);
        g.drawString("RED READY",xMid,yMid+15);
      }
          if(blueReady){
         g.setColor(Color.blue);
         g.drawString("BLUE READY", xMid,yMid-15);
      }
       if(blueReady && redReady){
           countBoth++;
      }
      if(countBoth>100){
        bothReady=true;
      }
    }else{
        if(gameStarted==false){
        counterStart++;
        if(counterStart<50){
            g.setColor(Color.white);
            g.drawString("3",xMid,yMid);
        }else if(counterStart<100){
            g.setColor(Color.white);
            g.drawString("2",xMid,yMid);
        }else if(counterStart<150){
            g.setColor(Color.white);
            g.drawString("1",xMid,yMid);
        }else if(counterStart<200){
            g.setColor(Color.white);
            g.drawString("!FIGHT!",xMid,yMid);
        }else if(counterStart==200)
           gameStarted=true;
    }
}
    
    
        //ScoreBoard System
        if(pause){
        g.setColor(Color.white);
        g.drawString("GAME PAUSED", xMid, yMid);
        timer.stop();
    }
    
        if(finalScore!=0){
            g.setColor(Color.white);
            g.fillRect(0,0,350,100);
        if(finalScore==1){
        g.setColor(Color.blue);
        g.drawString("BLUE WINS!",0,11);
    }
        if(finalScore==2){
        g.setColor(Color.red);
        g.drawString("RED WINS!",0,11);
    }
        g.drawString("FINAL SCORE",0,24);
        g.drawString("BLUE DAMAGE TAKEN: "+blueHitCount+"   RED DAMAGE TAKEN: "+redHitCount,0,37);
        g.drawString("BLUE SHOTS FIRED: "+tankFireCount+"   RED SHOTS FIRED: "+tank2FireCount,0,51);
        g.drawString("BLUE ACCURACY: "+(int)(((double)redHitCount/(double)tankFireCount)*100)+"%   RED ACCURACY: "+(int)(((double)blueHitCount/(double)tank2FireCount)*100)+"%",0,65);
        timer.stop();
 }
}
public void reset(){
    for(int i=0;i<bulletLimit;i++){
        if(bulletArray[i]!=null)
        bulletArray[i]=null;
        if(bulletArray2[i]!=null)
        bulletArray2[i]=null;
    }
    redHitCount=0;
    blueHitCount=0;
    planet.circle.setRadius(5);
    tank.circle.setRadius(30);
    tank2.circle.setRadius(30);
    tankCooldown=0;
    tank2Cooldown=0;
    tankDraw=true;
    tank2Draw=true;
    tankInvisCount=0;
    tank2InvisCount=0;
    bulletToExpand=0;
    bulletToExpand2=0;
    expandTimer=30;
    expandTimer2=30;
    ghostTimer=100;
    ghostTimer2=100;
    sheildTimer=50;
    sheildTimer2=50;
    finalScore=0;
    tankFireCount=0;
    tank2FireCount=0;
    pause=false;
    redReady=false;
    blueReady=false;
    bothReady=false;
    gameStarted=false;
    counterStart=0;
    countBoth=0;
}
private class BounceKeyListener implements KeyListener{
        public void keyPressed (KeyEvent e) {
        int c = e.getKeyCode ();
        if(c==KeyEvent.VK_ENTER && finalScore!=0){
            timer.start();
            reset();
        }
        //pause game with P
            
        if(c==KeyEvent.VK_P) {
         if(pause==false){
         pause=true;
        }else{
         pause=false;
         timer.start();
        }
    }
        
        //WASD keys pressed
        if(c==KeyEvent.VK_W) {
            keyTPressed = true;
        }
        if(c==KeyEvent.VK_S) {
            keyGPressed = true;
        }
        if(c==KeyEvent.VK_A) {
            keyFPressed = true;
        }
        if(c==KeyEvent.VK_D) {
            keyHPressed = true;
        }
        
        //Gun for WASD
        if(c==KeyEvent.VK_F) {
            keyZPressed = true;
        }
        
        if(c==KeyEvent.VK_G) {
        blueReady=true;
        if(gameStarted){
        boolean contFire=true;
             for(int i=0;i<bulletLimit;i++ ){
              
            if(bulletArray[i]==null && contFire){
               bulletArray[i]=tank.fire();
               contFire=false;
               tankFireCount++;
               break;
             }
           }
        }
    }
    
        if(c==KeyEvent.VK_H) {
            keyCPressed = true;
        }
        
        //Arrow keys pressed
        if (c==KeyEvent.VK_UP) {                
            keyUPPressed = true;
        } 
        if(c==KeyEvent.VK_DOWN) {                
           keyDOWNPressed = true;
        } 
        if(c==KeyEvent.VK_LEFT) {                
            keyLEFTPressed = true;
        } 
        if(c==KeyEvent.VK_RIGHT) {                
            keyRIGHTPressed = true; 
        }
        
        //Gun for arrow keys
        if(c==KeyEvent.VK_COMMA) {
            keyCOMMAPressed = true;
        }
        
        if(c==KeyEvent.VK_PERIOD) {
            redReady=true;
            if(gameStarted){
             for(int z=0;z<bulletLimit;z++ ){
              
            if(bulletArray2[z]==null){
               bulletArray2[z]=tank2.fire();
               tank2FireCount++;
               break;
             }
           }
         }
        }
        
        if(c==KeyEvent.VK_SLASH) {
            keySLASHPressed = true;
        }
        
        
        //Special power key events
        if(c==KeyEvent.VK_T){
            if(gameStarted)
           keyS=true;
    }
        if(c==KeyEvent.VK_L){
            if(gameStarted)
           keyL=true;
    }
}

    public void keyReleased (KeyEvent e) {
        int c = e.getKeyCode ();
        //WASD controls for release
        if(c==KeyEvent.VK_W) {
            keyTPressed = false;
        }
        if(c==KeyEvent.VK_S) {
            keyGPressed = false;
        }
        if(c==KeyEvent.VK_A) {
            keyFPressed = false;
        }
        if(c==KeyEvent.VK_D) {
            keyHPressed = false;
        }
        
        //WASD gun controls
        if(c==KeyEvent.VK_F) {
            keyZPressed = false;
        }
        if(c==KeyEvent.VK_H) {
            keyCPressed = false;
        }
        
        //Arrow controls for release
        if (c==KeyEvent.VK_UP) {                
            keyUPPressed = false;
        } 
        if(c==KeyEvent.VK_DOWN) {                
           keyDOWNPressed = false;
        } 
        if(c==KeyEvent.VK_LEFT) {                
            keyLEFTPressed = false;
        } 
        if(c==KeyEvent.VK_RIGHT) {                
            keyRIGHTPressed = false; 
        }
        
        //Arrow keys gun controls
         if(c==KeyEvent.VK_COMMA) {
            keyCOMMAPressed = false;
        }
        if(c==KeyEvent.VK_SLASH) {
            keySLASHPressed = false;
        }
        
        
        //Special power controls
          if(c==KeyEvent.VK_T){
           keyS=false;
    }
        if(c==KeyEvent.VK_L){
           keyL=false;
    }
}
public void keyTyped(KeyEvent e){}
}

private double findAngle(double x,double y){
    //return breaks the execution of the method. Good return you saved his life.  
    if(x==0 && y==0)
     return 0;
    if(x==0 && y>0)
    return Math.PI*.5;
    if(x==0 && y<0)
    return (Math.PI*6)/4;
    if(y<0)
    return Math.atan(y/x)+Math.PI;
    return Math.atan(y/x);
}
    
private class MoveListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        boolean circleturned= false;
        
        //Get Screen Width and Height into Wall Class
        Wall wall=new Wall(getWidth(),getHeight());
        tank.input(keyTPressed, keyGPressed, keyHPressed, keyFPressed, keyZPressed, keyCPressed);
        tank2.input(keyUPPressed, keyDOWNPressed, keyRIGHTPressed, keyLEFTPressed,keyCOMMAPressed, keySLASHPressed);
        
        tank.tankLogic(wall);
        tank2.tankLogic(wall);
        if(gameStarted){
        tankCooldown++;
        tank2Cooldown++;
    }
          //TankTeleport system
        if(tankChoice==1 && tankCooldown>200){
           tank.tankTeleport(keyS);
           tank.special.setRadius(34);
           if(keyS)
           tankCooldown=0;
        }else{
            tank.special.setRadius(0);
        }
        if(tank2Choice==1 && tank2Cooldown>200){
           tank2.tankTeleport(keyL);
           tank2.special.setRadius(34);
           if(keyL)
           tank2Cooldown=0;
        }else{
            tank2.special.setRadius(0);
        }
        //Tank swap system
        if(tankChoice==2){
        if(tankCooldown>300){
            tank.special.setRadius(34);
            if(keyS){
            for(int i=0;i<bulletLimit;i++){
                if(bulletArray[i]!=null){
               tankCooldown=0;
               int xbullet=(int)bulletArray[i].circle.getX();
               int ybullet=(int)bulletArray[i].circle.getY();
               int xtank=(int)tank.circle.getX();
               int ytank=(int)tank.circle.getY();
               tank.circle.setX(xbullet);
               tank.circle.setY(ybullet);
               bulletArray[i].circle.setX(xtank);
               bulletArray[i].circle.setY(ytank);
            }
          }
        }
        }else{
            tank.special.setRadius(0);
        }
    }
     if(tank2Choice==2){
        if(tank2Cooldown>300){
            tank2.special.setRadius(34);
            if(keyL){
            for(int i=0;i<bulletLimit;i++){
                if(bulletArray2[i]!=null){
               tank2Cooldown=0;
               int xbullet=(int)bulletArray2[i].circle.getX();
               int ybullet=(int)bulletArray2[i].circle.getY();
               int xtank=(int)tank2.circle.getX();
               int ytank=(int)tank2.circle.getY();
               tank2.circle.setX(xbullet);
               tank2.circle.setY(ybullet);
               bulletArray2[i].circle.setX(xtank);
               bulletArray2[i].circle.setY(ytank);
            }
          }
        }
        }else{
            tank2.special.setRadius(0);
        }
    }
    //Tank invisibility system
        if(tankChoice==3){
            if(tankCooldown>350){
                tank.special.setRadius(34);
                if(keyS){
                    tankCooldown=0;
                    tankDraw=false;
                    tankInvisCount=0;
                }
            }else{tank.special.setRadius(0);}
            if(tankDraw==false){
               tankInvisCount++;
               if(tankInvisCount==50)
               tankDraw=true;
        }
    }
        if(tank2Choice==3){
            if(tank2Cooldown>350){
                tank2.special.setRadius(34);
                if(keyL){
                    tank2Cooldown=0;
                    tank2Draw=false;
                    tank2InvisCount=0;
                }
            }else{tank2.special.setRadius(0);}
            if(tank2Draw==false){
               tank2InvisCount++;
               if(tank2InvisCount==50)
               tank2Draw=true;
        }
    }
        //Tank bullet speed increase power system
        if(tankChoice==4){
            if(tankCooldown>450){
                tank.special.setRadius(34);
                if(keyS){
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray[i]!=null){
                            tankCooldown=0;
                            bulletArray[i].circle.setxVel(bulletArray[i].circle.getxVel()*2);
                            bulletArray[i].circle.setyVel(bulletArray[i].circle.getyVel()*2);
                        }
                    }
                }
            }else{tank.special.setRadius(0);}
        }
         if(tank2Choice==4){
            if(tank2Cooldown>450){
                tank2.special.setRadius(34);
                if(keyL){
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray2[i]!=null){
                            tank2Cooldown=0;
                            bulletArray2[i].circle.setxVel(bulletArray2[i].circle.getxVel()*2);
                            bulletArray2[i].circle.setyVel(bulletArray2[i].circle.getyVel()*2);
                        }
                    }
                }
            }else{tank2.special.setRadius(0);}
        }
          //Tank expanding bullet
          if(tankChoice==5){
              if(tankCooldown>350){
                  tank.special.setRadius(34);
                  if(keyS){
                              bulletArray[bulletLimit-1]=tank.fire();
                              tankCooldown=0;
                              expandTimer=0;
                    }
                }else{tank.special.setRadius(0);}
                   if(bulletArray[bulletLimit-1]!=null && expandTimer<35){
                         expandTimer++;
                         bulletArray[bulletLimit-1].circle.setRadius(bulletArray[bulletLimit-1].circle.getRadius()+1);
                    }else{expandTimer=40;}
            }
          if(tank2Choice==5){
                if(tank2Cooldown>350){
                  tank2.special.setRadius(34);
                  if(keyL){
                              bulletArray2[bulletLimit-1]=tank2.fire();
                              tank2Cooldown=0;
                              expandTimer2=0;
                    }
                }else{tank2.special.setRadius(0);}
                   if(bulletArray2[bulletLimit-1]!=null && expandTimer2<35){
                         expandTimer2++;
                         bulletArray2[bulletLimit-1].circle.setRadius(bulletArray2[bulletLimit-1].circle.getRadius()+1);
                    }else{expandTimer2=40;}
            }
            //Tank ghost
            if(tankChoice==6){
                if(tankCooldown>400){
                    tank.special.setRadius(34);
                    if(keyS){
                        tank.circle.setRadius(0);
                        tankCooldown=0;
                        ghostTimer=0;
                    }
                }else{tank.special.setRadius(0);
                    if(ghostTimer<100){
                        ghostTimer++;
                    }else{tank.circle.setRadius(30);
                    }
                }
            }
             if(tank2Choice==6){
                if(tank2Cooldown>400){
                    tank2.special.setRadius(34);
                    if(keyL){
                        tank2.circle.setRadius(0);
                        tank2Cooldown=0;
                        ghostTimer2=0;
                    }
                }else{tank2.special.setRadius(0);
                    if(ghostTimer2<100){
                        ghostTimer2++;
                    }else{tank2.circle.setRadius(30);
                    }
                }
            }            
            //Tank sheild system
            if(tankChoice==7){
                if(tankCooldown>600){
                    tank.special.setRadius(34);
                    if(keyS){
                        tankCooldown=0;
                        sheildTimer=0;
                    }
                }else{tank.special.setRadius(0);}
                if(sheildTimer<50){
                    sheildTimer++;
                    tank.special.setRadius(50);
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray2[i]!=null){
                        if(bulletArray2[i].circle.collidesWith(tank.special)){
                        bulletArray2[i]=null;
                        tankCooldown+=25;
                 }
           }
       }
    }
}
            if(tank2Choice==7){
                if(tank2Cooldown>600){
                    tank2.special.setRadius(34);
                    if(keyL){
                        tank2Cooldown=0;
                        sheildTimer2=0;
                    }
                }else{tank2.special.setRadius(0);}
                if(sheildTimer2<50){
                    sheildTimer2++;
                    tank2.special.setRadius(50);
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray[i]!=null){
                        if(bulletArray[i].circle.collidesWith(tank2.special)){
                        bulletArray[i]=null;
                        tank2Cooldown+=25;
            }
        }
    }
    }
}
        //Tank Matrix stop bullets YEahhhh
        if(tankChoice==8){
            if(tankCooldown>800){
                tank.special.setRadius(34);
                if(keyS){
                    tank.special.setRadius(0);
                    tankCooldown=0;
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray2[i]!=null){
                            bulletArray2[i].Rebound(wall);
                            bulletArray2[i].circle.setxVel(0);
                            bulletArray2[i].circle.setyVel(0);
                            bulletArray2[i].BulletMaxHit(bounceLimit);
                        }
                    }
                }
            }else{tank.special.setRadius(0);}
        }
      if(tank2Choice==8){
            if(tank2Cooldown>800){
                tank2.special.setRadius(34);
                if(keyL){
                    tank2.special.setRadius(0);
                    tank2Cooldown=0;
                    for(int i=0;i<bulletLimit;i++){
                        if(bulletArray[i]!=null){
                            bulletArray[i].Rebound(wall);
                            bulletArray[i].circle.setxVel(0);
                            bulletArray[i].circle.setyVel(0);
                            bulletArray[i].BulletMaxHit(bounceLimit);
                        }
                    }
                }
            }else{tank2.special.setRadius(0);}
        }  
        //Cool powers out of ideas
        
        
        
        
        
        //IF particle needs to be deleted, delete it or run particle logic
        for(int i=0;i<bulletLimit*2;i++){
            if(particleArray[i]!=null){
            particleArray[i].particleLogic();
            if(particleArray[i].deleteParticle())
            particleArray[i]=null;
           }
           
           if(explosionArray[i]!=null){
             explosionArray[i].explodeLogic();
             if(explosionArray[i].delete())
             explosionArray[i]=null;
           }
        
        }
        
        //Planet
        if(planetWant){
        planet.planetLogic(wall);
        
        if(gameStarted)
        planetGrow++;
    
        //Growtimer for planet
        if(planetGrow>planetGrowTime){
        planetGrow=0;
        planet.planetGrow();
    }
        for(int i=0;i<bulletLimit;i++){
            if(bulletArray[i]!=null){
            //Bullet collide with Planet Logic
            if(bulletArray[i].circle.collidesWith(planet.circle)){
                double xbull=bulletArray[i].circle.getxVel();
                double ybull=bulletArray[i].circle.getyVel();
                int xBulletPlace=(int)bulletArray[i].circle.getX();
                int yBulletPlace=(int)bulletArray[i].circle.getY();
                planet.planetBull(xbull,ybull);
                boolean moPart=true;
                for(int j=0;j<bulletLimit*2;j++){
                if(explosionArray[j]==null){
                explosionArray[j]= new Explosion((int)bulletArray[i].circle.getX(), (int)bulletArray[i].circle.getY(),false);
                break;
            }
            }
                for(int v=0;v<bulletLimit*2;v++){
              
               if(particleArray[v]==null && moPart){
                   moPart=false;
                   particleArray[v]= new Particle();
                   particleArray[v].particleInitialize(xBulletPlace,yBulletPlace);
                 }
             }
            
                bulletArray[i]=null;
                planet.planetShrink();
            }
        }
        
        if(bulletArray2[i]!=null){
            //Bullet collide with planet logic
            if(bulletArray2[i].circle.collidesWith(planet.circle)){
                double xbull=bulletArray2[i].circle.getxVel();
                double ybull=bulletArray2[i].circle.getyVel();
                int xBulletPlace2=(int)bulletArray2[i].circle.getX();
                int yBulletPlace2=(int)bulletArray2[i].circle.getY();
                planet.planetBull(xbull,ybull);
                boolean morpart=true;
                for(int be=0;i<bulletLimit*2;be++){
                if(explosionArray[be]==null){
                explosionArray[be]= new Explosion((int)bulletArray2[i].circle.getX(), (int)bulletArray2[i].circle.getY(),true);
                break;
            }
            }
                for(int v=0;v<bulletLimit*2;v++){
                  if(particleArray[v]==null && morpart){
                   morpart=false;
                   particleArray[v]= new Particle();
                   particleArray[v].particleInitialize(xBulletPlace2,yBulletPlace2);
                }
            }
                planet.planetBull(xbull,ybull);
                bulletArray2[i]=null;
                planet.planetShrink();
            }
        }
    }
}
    
    
         for(int bur=0;bur<bulletLimit;bur++){
             if(bulletArray[bur]!=null){
                 //Bullet rebound wall
             bulletArray[bur].Rebound(wall);
             //Bullet max wall hits
             if(bulletArray[bur].wallHits>bounceLimit-1){
              for(int i=0;i<bulletLimit*2;i++){
                if(explosionArray[i]==null){
                    explosionArray[i]= new Explosion((int)bulletArray[bur].circle.getX(), (int)bulletArray[bur].circle.getY(),false);
                    break;
                  }
              }
              bulletArray[bur]=null;
            }
             if(bulletArray[bur]!=null){
                 //bullet collides with enemy tank
             if(bulletArray[bur].circle.collidesWith(tank2.circle)){
            redHitCount++;
            biggerRadius=(int)(30*(redHitCount/(double)healthMax));
            tank2.healthBall.setRadius((int)biggerRadius);
             for(int i=0;i<bulletLimit*2;i++){
                if(explosionArray[i]==null){
                    explosionArray[i]= new Explosion((int)bulletArray[bur].circle.getX(), (int)bulletArray[bur].circle.getY(),false);
                    break;
               }
            }
            bulletArray[bur]=null;
            if(redHitCount==healthMax){
              finalScore=1;
            }
        }
    }
}
           if(bulletArray[bur]!=null){
               //colide with own tank
           if(bulletArray[bur].wallHits>0){
           if(bulletArray[bur].circle.collidesWith(tank.circle)){
           bulletArray[bur]=null;
        }
    }
}
}

      for(int burr=0;burr<bulletLimit;burr++){
             if(bulletArray2[burr]!=null){
             bulletArray2[burr].Rebound(wall);
             if(bulletArray2[burr].wallHits>bounceLimit-1){
             
            for(int i=0;i<bulletLimit*2;i++){
                if(explosionArray[i]==null){
                explosionArray[i]= new Explosion((int)bulletArray2[burr].circle.getX(), (int)bulletArray2[burr].circle.getY(),true);
                break;
              }
            }
             bulletArray2[burr]=null;
            }
             if(bulletArray2[burr]!=null){
             //Bullet collides with enemy tank
             if(bulletArray2[burr].circle.collidesWith(tank.circle)){
            blueHitCount++;
            biggerRadius2=(int)(30*(blueHitCount/(double)healthMax));
            tank.healthBall.setRadius((int)biggerRadius2);
            //initialize explosion
            for(int i=0;i<bulletLimit*2;i++){
                if(explosionArray[i]==null){
                explosionArray[i]= new Explosion((int)bulletArray2[burr].circle.getX(), (int)bulletArray2[burr].circle.getY(),true);
                break;
              }
            }
            
            bulletArray2[burr]=null;
            if(blueHitCount==healthMax){
            finalScore=2;
            }
        }
    }
}
           if(bulletArray2[burr]!=null){
           if(bulletArray2[burr].wallHits>0){
              //bullet collide with own tank
           if(bulletArray2[burr].circle.collidesWith(tank2.circle)){
           bulletArray2[burr]=null;
        }
    }
}
}

   //Gun resize mechanizim that displays amount of bullets available for firing
   for(int pur=0;pur<bulletLimit;pur++){
             if(bulletArray[pur]==null){
                 bulletAvailable=bulletAvailable+1;
                }
            }
             double newGunRadius=10*(bulletAvailable/(double)bulletLimit);
             tank.gunCircle.setRadius((int)newGunRadius);
             bulletAvailable=0;
    //Resize gun for tank 2
    for(int pur=0;pur<bulletLimit;pur++){
             if(bulletArray2[pur]==null){
                 bulletAvailable=bulletAvailable+1;
                }
            }
             newGunRadius=10*(bulletAvailable/(double)bulletLimit);
             tank2.gunCircle.setRadius((int)newGunRadius);
             bulletAvailable=0;
        
            // PVP collision
        if (tank.circle.collidesWith(tank2.circle)) {
            //Fix major bug hopefully... YES it sorta works, um close enough
            double xDist = tank.circle.getX()-tank2.circle.getX();
            double yDist = tank.circle.getY()-tank2.circle.getY();
            double overLapDistance = (70)-Math.sqrt(xDist*xDist+yDist*yDist);
            double angleCollide = findAngle(yDist,xDist);
            
            double moveX=(overLapDistance/2)*Math.cos(angleCollide);
            double moveY=(overLapDistance/2)*Math.sin(angleCollide);
            tank.circle.setX((int)tank.circle.getX()+(int)moveX);
            tank.circle.setY((int)tank.circle.getY()+(int)moveY);
            tank2.circle.setX((int)tank2.circle.getX()-(int)moveX);
            tank2.circle.setY((int)tank2.circle.getY()-(int)moveY);
            
            
            //For tank 1 collision angle
            double vMagnitude= Math.sqrt(tank.circle.getxVel()*tank.circle.getxVel()+tank.circle.getyVel()*tank.circle.getyVel());
            double normalAngle= findAngle(tank2.circle.getY()-tank.circle.getY(),tank2.circle.getX()-tank.circle.getX());
            double vectorAngle= findAngle(tank.circle.getyVel(),tank.circle.getxVel());
            double newAngle= Math.PI+normalAngle+vectorAngle;
            //For tank 2 collision angle
            double vMagnitude2= Math.sqrt(tank2.circle.getxVel()*tank2.circle.getxVel()+tank2.circle.getyVel()*tank2.circle.getyVel());
            double normalAngle2= findAngle(tank.circle.getY()-tank2.circle.getY(),tank.circle.getX()-tank2.circle.getX());
            double vectorAngle2= findAngle(tank2.circle.getyVel(),tank2.circle.getxVel());
            double newAngle2= Math.PI+normalAngle2+vectorAngle2;
            
            double newMagnitude=(vMagnitude+vMagnitude2)/2;
            //New x and y velocities for tank one
            double newX=Math.cos(newAngle)*newMagnitude;
            double newY=Math.sin(newAngle)*newMagnitude;
            //New x and y velocities for tank two
            double newX2=Math.cos(newAngle2)*newMagnitude;
            double newY2=Math.sin(newAngle2)*newMagnitude;
            //putt in this into the actual velocities
            tank.circle.setxVel(newX);
            tank.circle.setyVel(newY);
            tank2.circle.setxVel(newX2);
            tank2.circle.setyVel(newY2);
        }
        if(planetWant){
        if (tank.circle.collidesWith(planet.circle)) {
            //Fix major bug hopefully... YES it sorta works, um close enough
            double xDist = tank.circle.getX()-planet.circle.getX();
            double yDist = tank.circle.getY()-planet.circle.getY();
            double overLapDistance = (tank.circle.getRadius()+planet.circle.getRadius()+3)-Math.sqrt(xDist*xDist+yDist*yDist);
            double angleCollide = findAngle(yDist,xDist);
            
            double moveX=(overLapDistance/2)*Math.cos(angleCollide);
            double moveY=(overLapDistance/2)*Math.sin(angleCollide);
            tank.circle.setX((int)tank.circle.getX()+(int)moveX);
            tank.circle.setY((int)tank.circle.getY()+(int)moveY);
            planet.circle.setX((int)planet.circle.getX()-(int)moveX);
            planet.circle.setY((int)planet.circle.getY()-(int)moveY);
            
            
            //For tank 1 collision angle
            double vMagnitude= Math.sqrt(tank.circle.getxVel()*tank.circle.getxVel()+tank.circle.getyVel()*tank.circle.getyVel());
            double normalAngle= findAngle(planet.circle.getY()-tank.circle.getY(),planet.circle.getX()-tank.circle.getX());
            double vectorAngle= findAngle(tank.circle.getyVel(),tank.circle.getxVel());
            double newAngle= Math.PI+normalAngle+vectorAngle;
            //For tank 2 collision angle
            double vMagnitude2= Math.sqrt(planet.circle.getxVel()*planet.circle.getxVel()+planet.circle.getyVel()*planet.circle.getyVel());
            double normalAngle2= findAngle(tank.circle.getY()-planet.circle.getY(),tank.circle.getX()-planet.circle.getX());
            double vectorAngle2= findAngle(planet.circle.getyVel(),planet.circle.getxVel());
            double newAngle2= Math.PI+normalAngle2+vectorAngle2;
            
            double newMagnitude=(vMagnitude+vMagnitude2)/2;
            //New x and y velocities for tank one
            double newX=Math.cos(newAngle)*newMagnitude;
            double newY=Math.sin(newAngle)*newMagnitude;
            //New x and y velocities for tank two
            double newX2=Math.cos(newAngle2)*newMagnitude;
            double newY2=Math.sin(newAngle2)*newMagnitude;
            //putt in this into the actual velocities
            planet.circle.setxVel(newX);
            planet.circle.setyVel(newY);
            tank.circle.setxVel(newX2);
            tank.circle.setyVel(newY2);
        }
        if (tank2.circle.collidesWith(planet.circle)) {
            //Fix major bug hopefully... YES it sorta works, um close enough
            double xDist = tank2.circle.getX()-planet.circle.getX();
            double yDist = tank2.circle.getY()-planet.circle.getY();
            double overLapDistance = (tank2.circle.getRadius()+planet.circle.getRadius()+3)-Math.sqrt(xDist*xDist+yDist*yDist);
            double angleCollide = findAngle(yDist,xDist);
            
            double moveX=(overLapDistance/2)*Math.cos(angleCollide);
            double moveY=(overLapDistance/2)*Math.sin(angleCollide);
            tank2.circle.setX((int)tank2.circle.getX()+(int)moveX);
            tank2.circle.setY((int)tank2.circle.getY()+(int)moveY);
            planet.circle.setX((int)planet.circle.getX()-(int)moveX);
            planet.circle.setY((int)planet.circle.getY()-(int)moveY);
            
            
            //For tank 1 collision angle
            double vMagnitude= Math.sqrt(tank2.circle.getxVel()*tank2.circle.getxVel()+tank2.circle.getyVel()*tank2.circle.getyVel());
            double normalAngle= findAngle(planet.circle.getY()-tank2.circle.getY(),planet.circle.getX()-tank2.circle.getX());
            double vectorAngle= findAngle(tank2.circle.getyVel(),tank2.circle.getxVel());
            double newAngle= Math.PI+normalAngle+vectorAngle;
            //For tank 2 collision angle
            double vMagnitude2= Math.sqrt(planet.circle.getxVel()*planet.circle.getxVel()+planet.circle.getyVel()*planet.circle.getyVel());
            double normalAngle2= findAngle(tank2.circle.getY()-planet.circle.getY(),tank2.circle.getX()-planet.circle.getX());
            double vectorAngle2= findAngle(planet.circle.getyVel(),planet.circle.getxVel());
            double newAngle2= Math.PI+normalAngle2+vectorAngle2;
            
            double newMagnitude=(vMagnitude+vMagnitude2)/2;
            //New x and y velocities for tank one
            double newX=Math.cos(newAngle)*newMagnitude;
            double newY=Math.sin(newAngle)*newMagnitude;
            //New x and y velocities for tank two
            double newX2=Math.cos(newAngle2)*newMagnitude;
            double newY2=Math.sin(newAngle2)*newMagnitude;
            //putt in this into the actual velocities
            planet.circle.setxVel(newX);
            planet.circle.setyVel(newY);
            tank2.circle.setxVel(newX2);
            tank2.circle.setyVel(newY2);
        }
    }
        repaint();
    }
    }
}

