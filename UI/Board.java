package UI;

// 底部的板
public class Board extends FlyObject{
    final int initialWidth;
    final int initialHeight;
    int HP;
    double length = 1;

    public Board(int x, int y, String imageName, int HP){
        this.x = x;
        this.y = y;
        this.HP = HP;
        image = ImageManager.getImage("/Image/" + imageName);
        initialHeight = image.getHeight()/5;
        initialWidth = image.getWidth()/4;
        width = initialWidth;
        height = initialHeight;
    }

    public void setLength(){
        width = (int)(initialWidth * length);
    }

    public void moveLeftOrRight(int x){
        this.x = x;
    }
    //判断板是否吃到道具包
    public BallType eat(Bag bag) {
        if(bag.x + bag.width >= x && bag.x <= x + width && bag.y + bag.height >= y && bag.y <= y + height){

            if(bag instanceof RocketBag)
                return BallType.RocketBall;
            else if(bag instanceof PunchBag)
                return BallType.PunchBall;
            else if(bag instanceof BoomBag)
                return BallType.BoomBall;
            //吃到加长包，板加长
            else if(bag instanceof LengthenBag){
                if(length <= 4){
                    length *= 2;
                    setLength();
                }
                return BallType.Ball;
            }
            //吃到缩短包，板缩短
            else if(bag instanceof ShortenBag){
                if(length >= 0.25){
                    length /= 2;
                    setLength();
                }
                return BallType.Ball;
            }
            //吃到死亡包，板掉血
            else if(bag instanceof DieBag){
                if(HP > 0)
                    HP--;
                return BallType.Ball;
            }
            else if(bag instanceof BigBallBag)
                return BallType.BigBall;
            else if(bag instanceof SmallBallBag)
                return BallType.SmallBall;
            else if(bag instanceof CommonBallBag)
                return BallType.CommonBall;
        }
        return null;
    }

}
