import java.awt.*;
public class Bullet
{
    public Circle circle;
    public double xspeed, yspeed;
    public int wallHits;
    
    public Bullet(double tankBallxVel, double tankBallyVel, double tankBallx, double tankBally, double tankBallgunX, double tankBallgunY, Color color)
    {
        circle=new Circle((int)tankBallgunX,(int)tankBallgunY,10, color);
        wallHits=0;
        xspeed=tankBallgunX-tankBallx;
        yspeed=tankBallgunY-tankBally;
        double ratio= 10/Math.sqrt((tankBallx-tankBallgunX)*(tankBallx-tankBallgunX)+(tankBally-tankBallgunY)*(tankBally-tankBallgunY));
	    xspeed*=ratio;
	    yspeed*=ratio;
	    xspeed+=tankBallxVel/2;
	    yspeed+=tankBallyVel/2;
	    circle.setxVel(xspeed);
	    circle.setyVel(yspeed);
    }
     public void Rebound(Wall wall) {   
      //X axis rebound
      double ySpeed=circle.getyVel();
      double xSpeed=circle.getxVel();
    
      char wallCollision=circle.collidesWith(wall);
        if(wallCollision=='r'){
        xSpeed=-1*Math.abs(xSpeed*1);
        wallHits++;
      }
        if(wallCollision=='l'){
        xSpeed=Math.abs(xSpeed*1);
        wallHits++;
      }
      //Y axis rebound
        if(wallCollision=='t'){
        ySpeed=Math.abs(ySpeed*1);
        wallHits++;
      }
        if(wallCollision=='b'){
        ySpeed=-1*Math.abs(ySpeed*1);
        wallHits++;
      }
       circle.setyVel(ySpeed);
       circle.setxVel(xSpeed);
    }
    public void BulletMaxHit(int reboundLimit){
      wallHits=reboundLimit-1;
}
    public void Render(Graphics g){
        
        circle.draw(g);
        circle.move();
    
    }
}

