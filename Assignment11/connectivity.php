<html>
<head>
	</head>
	<body>

		

<?php


$connection = new mysqli("cis.gvsu.edu", "toneyc", "toneyc") or die("Failed to connect 1");;
$connection->select_db("toneyc");

$user = $_POST['username'];
$pass = $_POST['password'];
session_start();
$_SESSION['username'] = $user;

if (isset($_POST['submit'])) {


	if (!empty($_POST['username'])) {
		$query = "select * from users where username = '$_POST[username]' and password = '$_POST[password]'";
		$result = $connection->query($query);
		$arr = $result->fetch_array();

		if (empty($arr)) {
			echo "<h3>Attempted Sign In Failed</h3>";
			echo '<a href="index.html">Re-Try?</a>';
		} else {
			header('Location: friends.php');

		}
	}
}

?>

</body>
</html>