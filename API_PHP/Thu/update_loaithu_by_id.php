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
if( isset($_POST['id_loaithu']) && isset($_POST['name_loaithu']) ){

    $id_loaithu = $_POST['id_loaithu'];
    $name_loaithu=$_POST['name_loaithu'];

    $update="UPDATE loaithu SET name_loaithu='$name_loaithu' WHERE id_loaithu='$id_loaithu'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Cập nhật loại chi có mã ID: .'$id_loaithu'. thành công";
    echo json_encode($response);
   }else{
    $response["success"]=0;
    $response["message"]="Cập nhật loại thất bại"; 
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