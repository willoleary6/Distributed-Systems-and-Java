<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>AJAX Home Page ..</title>
	<link rel="stylesheet" href="css/main.css" media="all" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		$.ajaxSetup({ 
			cache: false 
		});
		
		$(document).ready(function() {
			$.post("get_countries.php", 
				{}, 
				function(data, status) {
					$("#countries").append(data);
					$("#results").html("<p>Please select a country, then a state, and then a city to see results.</p>");
				}
			);
			
			$(document).on('change', '#countryList',  function(){
				val = $("#countryList").val();
				$("#states").html("");
				$("#cities").html("");
				$("#results").html("<p> Please select stuff </p>");
				
				$.post("get_states.php",
					{
						cID: val
					}, 
					function(data, status){
						$("#states").append(data);
					}
				);
				
				
				$(document).on('change', '#stateList',  function(){
					val = $("#stateList").val();
					$.post("get_cities.php",
						{
							sID: val
						}, 
						function(data, status){
							$("#cities").append(data);
						}
					);
				});
			});
		});	
	
	</script>
	<style>
		table {
			width:60%;
			margin:auto;
			border-collapse:collapse;
		}
		
		td {
			border:1px black solid;
			padding:10px;
			text-align:center;
		}
		
		#countries, #states, #cities {
			margin-top:10px;
		}
	</style>
</head>
<body>
<div id="container">
	<div id="header"></div>
	<div id="navbar"></div>
	<div id="content">
		<div id="countries"></div>
		<div id="states"></div>
		<div id="cities"></div>
		<hr />
		<div id="results"></div>
	</div>
	<div id="menu">
		<!-- Menu items will go here -->
	</div>
	<div class="clear"></div>
	<div id="footer"></div>
	<div class="clear"></div>
</div>
</body>
</html>