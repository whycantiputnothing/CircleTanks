import java.awt.*;

public class Planet
{
    public Circle circle;
    
    public Planet(Circle planCircle){
        circle=planCircle;
 
    }
    public void draw(Graphics g){
        circle.draw(g);
    }
    public void Rebound(Wall wall){
        double yspeed=circle.getyVel();
        double xspeed=circle.getxVel();
        char wallCollision=circle.collidesWith(wall);
        //X axis rebound
        if(wallCollision=='r')
        xspeed=-1*Math.abs(xspeed*1);
        if(wallCollision=='l')
        xspeed=Math.abs(xspeed*1);
        //Y axis rebound
        if(wallCollision=='t')
        yspeed=Math.abs(yspeed*1);
        if(wallCollision=='b')
        yspeed=-1*Math.abs(yspeed*1);
        
        circle.setyVel(yspeed);
        circle.setxVel(xspeed);
    }
    public void maintainSpeed(){
        circle.setxVel(circle.getxVel()/1.01);
        circle.setyVel(circle.getyVel()/1.01);
    }
    public void planetGrow(){
        int planetRadius=circle.getRadius();
        if(planetRadius<200){
        planetRadius+=1;
        circle.setRadius(planetRadius);
    }
    }
    public void planetShrink(){
        int planetRadius=circle.getRadius();
        if(planetRadius>20){
        if(planetRadius>100){
        planetRadius-=10;
       }else{
        planetRadius-=15;
       }
    }
    
        circle.setRadius(planetRadius);
    
    }
    public void planetBull(double x, double y){
        x=x/4;
        y=y/4;
        circle.setxVel(circle.getxVel()+x);
        circle.setyVel(circle.getyVel()+y);
    }
        
    public void planetLogic(Wall wall){
        Rebound(wall);
        circle.move();
        maintainSpeed();
    }
}
