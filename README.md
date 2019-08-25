# TestNG
This project includes functionalities of TestNG which is a testing framework for the Java programming language created by Cédric Beust and inspired by JUnit and NUnit. 
**In this project**, examples of following features of TestNG are covered:</br>
* How we can run test cases on priority basis </br>
* How we can give description about test case </br>
* How we can use **Soft Assert**. A soft assert is that which does not throw an exception when an assert fails and would continue with the next step after the assert statement. If there is any exception and you want to throw it then you need to use assertAll() method as a last statement in the @Test and test suite again continue with next @Test as it is. </br>
* Examples of **Implicit**, **Explicit** and **fluent** wait. </br>
* **Parallel execution of tests** using which we can reduce the 'execution time' as tests are executed simultaneously in different threads. </br>
* **Listeners** which listens to every event that occurs in a selenium code. Listeners are activated either before the test or after the test case. It is an interface that modifies the TestNG behavior. </br>