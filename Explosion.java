import java.awt.*;
//Kristofer is awesome
public class Explosion
{
    public Circle explode;
    int blue;
    int red;
    boolean done=true;
    boolean colorRed;
    public Explosion(int x, int y, boolean colorIsRed){
        explode=new Circle(x,y,10,Color.black);
        colorRed=colorIsRed;
    }
     public void draw(Graphics g){
        explode.draw(g);
    }
    public void explodeGrow(){
        explode.setRadius(explode.getRadius()+1);
    }
    public void explodeFade(){
        
        if(colorRed){
          if(done){
             done=false;
             red=250;
        }
        red=red-5;
        explode.setCcolor(new Color(red,0,0));
    
    }else{
        if(done){
            done=false;
            blue=250;
        }
        blue=blue-5;
        explode.setCcolor(new Color(0,0,blue));
    }
}
    public boolean delete(){
        if(explode.getColor().equals(Color.black))
             return true;
        return false;
}
    public void explodeLogic(){
        explodeGrow();
        explodeFade();
}
}
