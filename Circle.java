import java.awt.*;


public class Circle
{
  private int radius, direction;
  private Color color;
  private double centerX, centerY, xVel, yVel;

  //Makes circle
  public Circle(int x, int y, int r, Color c)
  {
    centerX = x;
    centerY = y;
    radius = r;
    color=c;
  }
  
  public int getRadius(){
      return radius;
    }
  public void setRadius(int newRadius){
       radius=newRadius;
    }
  public Color getColor(){
      return color;
    }
  //So I can change the color
  public void setCcolor(Color color){
      this.color=color;
     }
   
  //Helps make the circle apper at the right color and radius
  public void draw(Graphics g)
  {
    Color oldColor = g.getColor();
    g.setColor(color);
    // Translates circle’s center to rectangle’s origin for drawing.
    g.fillOval((int)centerX - radius,(int) centerY - radius,
      radius * 2, radius * 2);
    g.setColor(oldColor);
  }

  //Fills the circle
  public void fill(Graphics g)
  {
    Color oldColor = g.getColor();
    g.setColor(color);
    // Translates circle’s center to rectangle’s origin for drawing.
    g.fillOval((int)centerX - radius, (int)centerY - radius,
      radius * 2, radius * 2);
    g.setColor(oldColor);
  }

  
  public boolean containsPoint(double x, double y)
  {
    double xSquared = (x - centerX) * (x - centerX);
    double ySquared = (y - centerY) * (y - centerY);
    double radiusSquared = radius * radius;
    return xSquared + ySquared - radiusSquared <= 0;
  }


  public void move(double xAmount, double yAmount)
  {
    centerX += xAmount;
    centerY += yAmount;
  }
  
  
  public double getX(){
      return centerX;
    }
  public double getY(){
      return centerY;
    }
    
  public void setX(int cX){
      centerX=cX;
    }
  public void setY(int cY){
    centerY=cY;
    }
    
    
    public void setxVel(double vx){
        xVel=vx;
    }
    public void setyVel(double vy){
        yVel=vy;
    }
    //returns velocity of circles
    public double getyVel(){
        return yVel;
    }
    public double getxVel(){
        return xVel;
    }
    
    
    public boolean collidesWith(Circle otherCircle){
        //collision detection using radius and hypotenuse of imaginary triangle of the mid points from the circles
        return(radius+otherCircle.getRadius()>=Math.sqrt((centerX-otherCircle.getX())*(centerX-otherCircle.getX())+(centerY-otherCircle.getY())*(centerY-otherCircle.getY())));
    }
    
    public char collidesWith(Wall wall){
        if (centerX<=radius)
            return 'l';
        if (centerX+radius>=wall.width)
            return 'r';
        if (centerY<=radius)
            return 't';
        if (centerY+radius>=wall.height)
            return 'b';
        return ' ';
        }
    
    //puts x vol and y vol into moving the circle
    public void move(){
        move(xVel,yVel);
}

}
