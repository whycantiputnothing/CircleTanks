import java.awt.*;

public class TankBall
{
    public Circle circle;
    public Circle gunCircle;
    public Circle healthBall;
    public Circle gunBack;
    public Circle special;
    double gunRadians;
    double ONE_DEGREE=(2*Math.PI)/360;
    double rotateSpeed;
    double degrees;
    public TankBall(Circle myCircle, Color gun, Color cHealth, int health,Color back,Color specialColor){
        circle=myCircle;
        gunCircle=new Circle(60,400,10,gun);
        gunBack=new Circle(60,400,10,back);
        healthBall= new Circle(60, 400, health, cHealth);
        special= new Circle(60,400,32,specialColor);
    }
    public void draw(Graphics g){
        special.draw(g);
        circle.draw(g);
        healthBall.draw(g);
        gunBack.draw(g);
        gunCircle.draw(g);
    }
    public void input(boolean upCode, boolean downCode, boolean rightCode, boolean leftCode, boolean clockRotate,  boolean contRotate)
    {
        double yspeed=circle.getyVel();
        double xspeed=circle.getxVel();
        
        //up and down movemet ;)
        if(upCode && downCode==false && yspeed>=-10) {
            yspeed -= .5;
        }
        if(downCode && upCode==false && yspeed<=10) {
            yspeed += .5;
        }
        if(downCode==false && upCode==false) {
            yspeed=yspeed/1.025;
        }
        //left and right movement
        if(rightCode && leftCode==false && xspeed<10) {
            xspeed += .5;
        }
        if(leftCode && rightCode==false && yspeed>-10) {
            xspeed -= .5;
        }
         if(leftCode==false && rightCode==false) {
           xspeed=xspeed/1.025;
        }
        
        //gun rotation
        if(clockRotate && contRotate==false && rotateSpeed<=5)
            rotateSpeed += .5;
        if(contRotate && clockRotate==false && rotateSpeed>=-5)
            rotateSpeed -= .5;
        if((contRotate==false && clockRotate==false) || (contRotate && clockRotate))
            rotateSpeed = rotateSpeed/1.5;
           
        //Gun turning
        degrees=(degrees+rotateSpeed)%360;
        gunRadians=degrees*ONE_DEGREE;
     
        double xGunPlace=circle.getX()+Math.cos(gunRadians)*30;
        double yGunPlace=circle.getY()-Math.sin(gunRadians)*30;
     
        gunCircle.setX((int)xGunPlace);
        gunCircle.setY((int)yGunPlace);
        
        gunBack.setX((int)xGunPlace);
        gunBack.setY((int)yGunPlace);
        
        circle.setyVel(yspeed);
        circle.setxVel(xspeed);
    }     
    
    public void vectorSpeedConstant() {
        //Makes diagonal movement same speed as horizontal and vertical
        double yspeed=circle.getyVel();
        double xspeed=circle.getxVel();
        double ratio= 10/Math.sqrt(xspeed*xspeed+yspeed*yspeed);
        if(ratio<1){
	    xspeed*=ratio;
	    yspeed*=ratio;
    
	    circle.setyVel(yspeed);
	    circle.setxVel(xspeed);
	    
	  }

}

  public void Rebound(Wall wall) {   
    //X axis rebound
    double yspeed=circle.getyVel();
    double xspeed=circle.getxVel();
    
    char wallCollision=circle.collidesWith(wall);
        if(wallCollision=='r')
        xspeed=-1*Math.abs(xspeed*1.4);
        if(wallCollision=='l')
        xspeed=Math.abs(xspeed*1.4);
    //Y axis rebound
        if(wallCollision=='t')
        yspeed=Math.abs(yspeed*1.3);
        if(wallCollision=='b')
        yspeed=-1*Math.abs(yspeed*1.3);
        
    circle.setyVel(yspeed);
    circle.setxVel(xspeed);
  }
    
  public void healthSpot(){
     healthBall.setX((int)circle.getX());
     healthBall.setY((int)circle.getY());
  }
  public void specialSpot(){
      
     special.setX((int)circle.getX());
     special.setY((int)circle.getY());
    }
    
  public Bullet fire(){
      return new Bullet(circle.getxVel(),circle.getyVel(), circle.getX(), circle.getY(),gunCircle.getX(),gunCircle.getY(), gunCircle.getColor());
    }
  
  public void tankLogic(Wall wall)
  {
      vectorSpeedConstant();
      Rebound(wall);
      circle.move();
      healthSpot();
      specialSpot();
  }
  //Abilities
  public void tankTeleport(boolean special){
      if(special){
      double yspeed=circle.getyVel();
      double xspeed=circle.getxVel();
      double xposition=circle.getX();
      double yposition=circle.getY();
      if(xspeed!=0 && yspeed!=0){
        double ratio= 150/Math.sqrt(xspeed*xspeed+yspeed*yspeed);
	    xposition=xposition+xspeed*ratio;
	    yposition=yposition+yspeed*ratio;
    
	    circle.setX((int)xposition);
	    circle.setY((int)yposition);
	   }
	  }
    }
}

