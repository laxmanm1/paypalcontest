<?
include('conn.php');
if ( isset( $_POST[ 'name' ] ) )
//if (isset($_POST['name']))
{
$name= validate($_POST[ 'name' ]);
if(filter_var($_POST['email'], FILTER_VALIDATE_EMAIL))
//if(isset($_POST['email'])))
{
$email = validate($_POST['email']);
if(ctype_digit($_POST['mobile']))
{
$mobile = validate($_POST['mobile']);
if (ctype_digit($_POST['card']))
{
$card=validate($_POST['card']);
if (ctype_digit($_POST['amount']))
{
$balance= validate($_POST['amount']);
$otp=rand(1000,99999);
$md5=md5($card.$otp);
$result = mysqli_query($con,"INSERT INTO seller(name,email,mobile,creditcard,balance,otp,hash) values('$name','$email','$mobile','$card',$balance,'$otp','$md5')");
echo '<html><h2>Congrats! You are almost registered.</h2><br/><h3>Note your HASH Value '.$md5.' to make payments. API Syntax as follows <br/>http://contestlaxman.yzi.me/sellerpay.php?hash=Your HASH Value&amount=Total Bill Amount</h3><p style="color:red;">HASH value is like password. So keep it secret. </html>';
}
else
echo "Please Enter Minimum Withdrawel Amount correctly.";
}
else
echo "Please Enter Credit / Debit Card number correctly.";
}
else
echo "Please Enter Mobile Number Correctly. Mobile Number Should Be 10 Digits!";
}
else
{
echo "Please Enter Email Correctly!";
printform();
}
} 
function printform()
{
echo '<head>
<meta http-equiv="refresh" content="5; URL=buyersignup.php">
</head><p>You will be redirected to the sign up page shortly! If this page remains more than 5 seconds <a href="sellersignup.php">click here</a>';
}
?>
