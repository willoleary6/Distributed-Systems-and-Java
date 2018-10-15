<?php
	$conn = new mysqli("localhost", "root", "", "demographics");
	
	if($conn->connect_errno) {
		echo "<p>Problem connecting to DBMS or database.</p>";
		exit();
	} 
	
	$result = $conn->query("select * from country order by name;");
	if($result->num_rows > 0) {
		echo "Country: <select id=\"countryList\" tabindex=\"1\">";
		echo "<option value=\"\"></option>";
		while($row = $result->fetch_assoc()) {
			$value = $row["autokey"];
			$text = $row["name"];
			echo "<option value=\"$value\">$text</option>";
		}
		echo "</select><br />";
	} else {
		echo "<p>No countries found.</p>";
	}
	
	$conn->close();
?>