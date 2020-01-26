/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/19
 * Time: 8:40 PM
 * Description: No Description
 */
public class Dog implements Animal{
    public Animal animal;
    public Dog( Animal animal){
        this.animal = animal;
    }
    @Override
    public void running() {
        System.out.println("i am a running animal");
    }
}
