<?
include('conn.php');
$hash = validate($_GET['hash']);
$amount = validate($_GET['amount']);
//echo $otp.$mob;
$result = mysqli_query($con,"select * from seller where hash='$hash'");
while($row = mysqli_fetch_array($result))
{
$sellerid=$row['id'];
}
if( mysqli_num_rows($result) == 0)
{
echo "error";
}
else if( mysqli_num_rows($result) != 0)
{
$otp=rand(1000,99999);
$billid=$otp.$sellerid;
echo $billid;
$query=mysqli_query($con,"insert into transactions values('$billid','$amount','','$sellerid')");
}
?>
