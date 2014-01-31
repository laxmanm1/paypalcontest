<?
include('conn.php');
$otp = urldecode(validate($_POST['otp']));
$mob = urldecode(validate($_POST['email']));
//echo $otp.$mob;
$result = mysqli_query($con,"select * from mobilepayment where otp='$otp' and email='$mob'");

while($row = mysqli_fetch_array($result))
{
echo $row['hash'];
}
if( mysqli_num_rows($result) != 0)
{
$result = mysqli_query($con,"update mobilepayment set otp='' where otp='$otp'");
}
else if ( mysqli_num_rows($result) == 0)
{
echo "error";
}
?>
