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
if(isset($_POST['id_user'])
 && isset($_POST['id_loaichi'])
 && isset($_POST['name_khoanchi'])
 && isset($_POST['money_khoanchi'])
 && isset($_POST['note_khoanchi'])
 && isset($_POST['date_add_khoanchi'])
 ){
    
    $id_user= $_POST['id_user'];
    $id_loaichi= $_POST['id_loaichi'];
    $name_khoanchi= $_POST['name_khoanchi'];
    $money_khoanchi=$_POST['money_khoanchi'];
    $note_khoanchi=$_POST['note_khoanchi'];
    $date_add_khoanchi=$_POST['date_add_khoanchi'];
    
    $sql="INSERT INTO khoanchi (id_user,id_loaichi,name_khoanchi,money_khoanchi,note_khoanchi,date_add_khoanchi) VALUES ('$id_user', '$id_loaichi','$name_khoanchi','$money_khoanchi','$note_khoanchi','$date_add_khoanchi')";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Thêm khoản chi thành công !";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]= "Thêm khoản chi thất bại !"; 
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