/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributedsystemslabs.lab_1_person_program;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class GetPersonDetails {
	private List myDetails;

        public GetPersonDetails() {
		String filename = "person.txt";
		List pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList) in.readObject();
                        myDetails = pDetails;
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		System.out.println("Person Details Size: " + pDetails.size());
		System.out.println();
	}

        public List retrieveDetails() {
            return myDetails;
        }
}