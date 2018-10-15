<?php
	$conn = new mysqli("localhost", "root", "", "demographics");
	
	if($conn->connect_errno) {
		echo "<p>Problem connecting to DBMS or database.</p>";
		exit();
	} 
	$state = $_POST["sID"];
	$result = $conn->query("select * from cities WHERE stateID = '$state' order by name;");
	if($result->num_rows > 0) {
		echo "city: <select id=\"cityList\" tabindex=\"3\">";
		echo "<option value=\"\"></option>";
		while($row = $result->fetch_assoc()) {
			$value = $row["autokey"];
			$text = $row["name"];
			echo "<option value=\"$value\">$text</option>";
		}
		echo "</select><br />";
	} else {
		echo "<p>No cities found.</p>";
	}
	
	$conn->close();
?>