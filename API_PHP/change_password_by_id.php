<?php
 $server='localhost';
 $user='id16540630_tuvvph09410';
 $password='TKLtJs2&fZ?lhRAW';
 $database='id16540630_android_networking_mob403_vuvantu';
 $connect=new mysqli($server,$user,$password,$database);

if($connect->connect_error)
{
    die("Connection Failed: " .$connect->connect_error);
}
if( isset($_POST['id_user']) && isset($_POST['new_password'])){
    $id_user = $_POST['id_user'];
    $password = $_POST['new_password'];
    $update="UPDATE user SET password='$password' WHERE id_user='$id_user'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Đổi mật khẩu thành công";
    echo json_encode($response);
   }else{
    $response["success"]=0;
    $response["message"]="Đổi mật khẩu thất bại"; 
    echo json_encode($response);
       
   }
}
else{  
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_encode($response);
}
$connect->close();
?>