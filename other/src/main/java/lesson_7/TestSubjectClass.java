package lesson_7;

public class TestSubjectClass {

    @BeforeSuite
    public void InitializeMySuperDuperClass() {
        System.out.println("Inicializacion!");
    }

    @AfterSuite
    public void FinalizeMySuperDuperClass() {
        System.out.println("Finalizacion!");
    }

    @Test(priority = 1)
    public void TestPriority1() {
        System.out.println("Why, Mr. Anderson?");
    }

    @Test(priority = 1)
    public void AnotherTestPriority1() {
        System.out.println("Once again???!!!");
    }

    @Test(priority = 2)
    public void TestPriority2() {
        System.out.println("Why? Why do you persist?");
    }

    @Test(priority = 3)
    public void TestPriority3() {
        System.out.println("Blue Pill or Red Pill?");
    }

    @Test(priority = 4)
    public void TestMeOnce() {
        System.out.println("Какие ваши доказательства?");
    }

    @Test(priority = 10000)
    public void TestMeTwice() {
        System.out.println("Кокаинум!");
    }

    @Test(priority = 10000)
    public void DontTestMe() {
        System.out.println("Еще раз кокаинум!");
    }

}