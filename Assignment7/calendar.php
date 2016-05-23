<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>

<?php

$month = $_GET['month'];
$year = $_GET['year'];

$first_day = mktime(0, 0, 0, $month, 1, $year);

$month_name = date('F', $first_day);

$day_of_week = date('D', $first_day);

switch($day_of_week) {
	case "Sun": $blank = 0; break;
	case "Mon": $blank = 1; break;
	case "Tue": $blank = 2; break;
	case "Wed": $blank = 3; break;
	case "Thu": $blank = 4; break;
	case "Fri": $blank = 5; break;
	case "Sat": $blank = 6; break;
}

$days_in_month = cal_days_in_month(0, $month, $year);

if ($month == 1) {
	$previous_month = 12;
	$year = $year - 1;
}
else {
	$previous_month = $month - 1;
}
echo '<table><tr>';
echo '<td>';
echo '<a href="calendar.php?month=';
echo "$previous_month";
echo '&year=';
echo "$year";
echo '">';
echo '<img src="left_arrow.png"></a>';
echo '</td>';

echo '<td>';
echo "<h3>$month_name</h3>";
echo "<table>";
echo '<tr style="background-color: gray; font-weight: bold;"><td>Sun</td><td>Mon</td><td>Tue</td><td>Wed</td><td>Thu</td><td>Fri</td><td>Sat</td></tr>';

$day_count = 1;
echo "<tr>";

while ($blank > 0) {
	echo "<td></td>";
	$blank = $blank - 1;
	$day_count++;
}

$day_num = 1;

while ($day_num <= $days_in_month) {
	echo "<td> $day_num </td>";
	$day_num++;
	$day_count++;

	if ($day_count > 7) {
		echo "</tr><tr>";
		$day_count = 1;
	}
}

while ($day_count >1 && $day_count <= 7) {
	echo "<td> </td>";
	$day_count++;
}
echo '</tr></table>';
echo '</td>';

if ($month == 12) {
	$next_month = 1;
	$year = $year + 1;
}
else {
	$next_month = $month + 1;
}
echo '<td>';
echo '<a href="calendar.php?month=';
echo "$next_month";
echo '&year=';
echo "$year";
echo '">';
echo '<img src="right_arrow.png"></a>';
echo '</td></tr></table>';


?>

</body>
</html>
