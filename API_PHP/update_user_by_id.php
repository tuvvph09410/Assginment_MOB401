<?php
 $server='localhost';
 $user='id16540630_tuvvph09410';
 $password='TKLtJs2&fZ?lhRAW';
 $database='id16540630_android_networking_mob403_vuvantu';
 $connect=new mysqli($server,$user,$password,$database);
 $response=array();
if($connect->connect_error)
{
    die("Connection Failed: " .$connect->connect_error);
}
if(isset($_POST['id_user']) && isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['phone'])){
    $id_user=$_POST['id_user'];
    $first_name=$_POST['first_name'];
    $last_name=$_POST['last_name'];
    $phone=$_POST['phone'];
    $sql="UPDATE user SET first_name='$first_name', last_name='$last_name', phone='$phone' WHERE id_user='$id_user'";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Thông tin tài khoản cập nhật đã thành công! ";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]= "Thông tin tài khoản cập nhật đã thất bại !"; 
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