/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab_1.lab_1_person_program;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PersonPersist {
        private List userDetails;
        private String filename;

	public PersonPersist() {
		filename = "person.txt";
		PersonDetails person1 = new PersonDetails("hemanth", 10, "Male");
		PersonDetails person2 = new PersonDetails("bob", 12, "Male");
		PersonDetails person3 = new PersonDetails("Richa", 10, "Female");
		userDetails.add(person1);
		userDetails.add(person2);
		userDetails.add(person3);
		serialiseToFile();
	}

        public void addPerson(String name, int age, String gender) {
            PersonDetails p = new PersonDetails(name, age, gender);
            userDetails.add(p);
        }

        public boolean serialiseToFile() {
            FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(userDetails);
			out.close();
			System.out.println("Object Persisted");
                        return true;
		} catch (IOException ex) {
			ex.printStackTrace();
                        return false;
		}
        }
}