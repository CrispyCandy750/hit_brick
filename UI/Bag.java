package UI;

// 所有的道具包
public class Bag extends FlyObject {
    public Bag(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 50;
        this.width = 50;
    }

    /**
     * 根据的类型来获取新的道具包，x和y是被打的砖块的位置，道具在砖块的位置生成
     * @param i
     * @param x
     * @param y
     * @return
     */
    public static Bag getBag(int i, int x, int y) {
        Bag bag = null;
        switch (i) {
            case 1:
                bag = new RocketBag(x, y);
                break;
            case 2:
                bag = new PunchBag(x, y);
                break;
            case 3:
                bag = new BoomBag(x, y);
                break;
            case 4:
                bag = new LengthenBag(x, y);
                break;
            case 5:
                bag = new ShortenBag(x, y);
                break;
            case 6:
                bag = new DieBag(x, y);
                break;
            case 7:
                bag = new BigBallBag(x, y);
                break;
            case 8:
                bag = new SmallBallBag(x, y);
                break;
            case 9:
                bag = new CommonBallBag(x, y);
                break;
        }
        return bag;
    }
}

// 导弹球
class RocketBag extends Bag {
    public RocketBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/RocketBag.png");
    }
}

// 穿刺球
class PunchBag extends Bag {
    public PunchBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/PunchBag.png");
    }
}

// 炸弹球
class BoomBag extends Bag {
    public BoomBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/BoomBag.png");
    }
}

// 让挡板变长
class LengthenBag extends Bag {
    public LengthenBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/LengthenBag.png");
    }
}

// 让挡板变小
class ShortenBag extends Bag {
    public ShortenBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/ShortenBag.png");
    }
}

// 死亡
class DieBag extends Bag {
    public DieBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/DieBag.png");
    }
}

// 让球变大
class BigBallBag extends Bag {
    public BigBallBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/BigBallBag.png");
    }
}

// 让球变小
class SmallBallBag extends Bag {
    public SmallBallBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/SmallBallBag.png");
    }
}

// 普通球道具包
class CommonBallBag extends Bag {
    public CommonBallBag(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("/Image/ball.png");
    }
}
