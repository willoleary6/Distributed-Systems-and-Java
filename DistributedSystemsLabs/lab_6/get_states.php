<?php
	$conn = new mysqli("localhost", "root", "", "demographics");
	
	if($conn->connect_errno) {
		echo "<p>Problem connecting to DBMS or database.</p>";
		exit();
	} 
	$country = $_POST["cID"];
	$result = $conn->query("select * from states WHERE countryID = '$country' order by name;");
	if($result->num_rows > 0) {
		echo "State: <select id=\"stateList\" tabindex=\"2\">";
		echo "<option value=\"\"></option>";
		while($row = $result->fetch_assoc()) {
			$value = $row["autokey"];
			$text = $row["name"];
			echo "<option value=\"$value\">$text</option>";
		}
		echo "</select><br />";
	} else {
		echo "<p>No states found.</p>";
	}
	
	$conn->close();
?>