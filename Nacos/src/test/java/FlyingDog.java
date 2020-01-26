/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/19
 * Time: 8:40 PM
 * Description: No Description
 */
public class FlyingDog extends Dog {
    public FlyingDog( Animal animal){
        super(animal);
    }
    @Override
    public void running() {
        System.out.println("i am a runing and  flying FlyingDog");
    }

}
