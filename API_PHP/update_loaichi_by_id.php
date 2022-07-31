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
if( isset($_POST['id_loaichi'])){

    $id_loaichi = $_POST['id_loaichi'];
    $name_loaichi=$_POST['name_loaichi'];

    $update="UPDATE loaichi SET name_loaichi='$name_loaichi' WHERE id_loaichi='$id_loaichi'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Cập nhật loại chi có mã ID: .'$id_loaichi'. thành công";
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