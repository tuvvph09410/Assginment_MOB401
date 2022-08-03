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
if(isset($_POST['id_user']) && isset($_POST['name_loaithu'])){
    
    $id_user= $_POST['id_user'];
    $name_loaithu= $_POST['name_loaithu'];
    
    $sql="INSERT INTO loaithu (id_user,name_loaithu) VALUES ('$id_user', '$name_loaithu')";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Thêm loại thu thành công !";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]= "Thêm loại thu thất bại !"; 
        echo json_encode($response);
        
    }
    $connect->close();
}
else{
    $response["success"]=0;
    $response["message"]="Required field(s) is missing."; 
    echo json_encode($response);
  
    
}
   
?>