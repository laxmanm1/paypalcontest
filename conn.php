<?
function validate($pass)
{
//$pass1 = strip_tags( trim( $pass ) );
$pass1 = str_replace("<","&lt;",$pass);
$pass2 = str_replace("'","&rsquo;",$pass1);
//$pass3 = stripslashes($pass2);
$pass3 = str_replace(">","&gt;",$pass2);
$passfinal = str_replace('"','&rdquo;',$pass3);
return $passfinal;
}
$con=mysqli_connect('localhost','username','password','dbname');
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

?>
