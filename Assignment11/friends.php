<?php
session_start();
if (empty($_SESSION["username"]) || !isset($_SESSION["username"])) {
    header('Location: index.html');
}
$user = $_SESSION["username"];
error_reporting(E_ALL);
ini_set('display_errors', '1');

function connect() {

    $connection = new mysqli("cis.gvsu.edu", "toneyc", "toneyc");

    if (!$connection || $connection->connect_error) {
        die('Unable to connect to database [' . $connection->connect_error . ']');
    }
    if (!$connection->select_db("toneyc")) {
        die ("Unable to select database:  [" . $connection->error . "]");
    }

    return $connection;

}

function getAllFriends($c) {
    $user = $_SESSION["username"];
    $sql = "select * from friends where user = '$user'";
    $result = $c->query($sql);
    if (!$result) {
        die ("Query was unsuccesful: [" . $c->error . "]");
    }
    return $result;
}

?>

<html>
<head>
    <title> Assignment 10 </title>
    <link rel="stylesheet" href="style.css" type="text/css">
    <script type="text/javascript">

        function validate() {
            var x = true;
            if (document.myform.firstName.value == "" || document.myform.firstName.value == "First name required!") {
                document.myform.firstName.value = "First name required!";
                x = false;
            }
            if (document.myform.lastName.value == "" || document.myform.lastName.value == "Last name required!") {
                document.myform.lastName.value = "Last name required!";
                x = false;
            }
            if (document.myform.phoneNumber.value == "" || document.myform.phoneNumber.value == "Phone required!" ||
                !document.myform.phoneNumber.value.includes("-")) {
                document.myform.phoneNumber.value = "Phone required!";
                x = false;
            }
            if (document.myform.age.value == "Age required!" || document.myform.age.value == "") {
                document.myform.age.value = "Age required!";
                x = false;
            }
            return x;
        }
    </script>
</head>
<body>

<h3> Friends </h3>

<?php 
$c = connect();
$sql="select * from users where username = '$user'";
$arr = $c ->query($sql);
foreach($arr as $a) {
    $u = $a["superuser"];
}
if ($u == 1) {

echo '<table id="form_table">
<form action="friends.php" name="myform" method="post" onsubmit="return(validate());">
    <tr>
        <td><label for="firstName">First Name:</label></td>
        <td><input type="text" name="firstName" id="firstName"></td>
    </tr>
    <tr>
        <td><label for="lastName">Last Name:</label></td>
        <td><input type="text" name="lastName" id="lastName"></td>
    </tr>
    <tr> 
        <td><label for "phoneNumber">Phone Number:</label></td>
        <td><input type="text" name="phoneNumber" id="phoneNumber"></td>
    </tr>
    <tr>
        <td><label for="age">Age:</label></td>
        <td><input type="text" name="age" id="age"></td>
    </tr>
    <tr><td><input type="submit" value="Submit"></td><td></td></tr>
</form>
</table>
<br>';
}
?>

<table id="friends_table">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Phone Number</th>
        <th>Age</th>
    </tr>

    <?php

    $c = connect();

    if (!isset($_POST['firstName']) || $_POST['firstName'] == '') {

    } elseif (isset($_POST['firstName']) && isset($_POST['lastName'])) {
        $sql="INSERT INTO friends (firstName, lastName, phoneNumber, age, user) VALUES ('$_POST[firstName]', '$_POST[lastName]', '$_POST[phoneNumber]', '$_POST[age]', '$_SESSION[username]')";
        $r = $c->query($sql);
        echo "1 friend added.";
    }

        $result = getAllFriends($c);

        foreach ($result as $row) {
            echo "<tr>";

            $keys = array("firstName", "lastName", "phoneNumber", "age");

            foreach ($keys as $key) {
                echo "<td>" . $row[$key] . "</td>";
            }
            echo "</tr>\n";

        }

        $c->close();

    ?>
</table>

<a href="index.html">Sign-out</a>


</body>
</html>