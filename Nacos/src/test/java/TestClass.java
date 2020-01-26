/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/19
 * Time: 8:53 PM
 * Description: No Description
 */
public class TestClass {
    public static void main( String[] args ) {
        System.out.println("被装饰类（Bird）******************************");
        Animal animal = new Bird();
        animal.running();
        System.out.println("用Chicken来装饰被装饰类******************************");
        animal = new FlyingDog(new Bird());
        animal.running();

    }
}
