/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab_1.lab_1_person_program;
import java.util.List;
/**
 *
 * @author james.murphy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PersonPersist p = new PersonPersist();
        GetPersonDetails g = new GetPersonDetails();

            List myList = g.retrieveDetails();
            for(int i=0;i<myList.size();i++) {
               // System.out.println(myList.get(i));
                PersonDetails pd = (PersonDetails) myList.get(i);
                System.out.print(pd.getName() + " - ");
                System.out.print(pd.getAge() + " - ");
                System.out.println(pd.getSex());
            }
    }
}