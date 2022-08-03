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
if( isset($_POST['id_khoanchi']) 
&& isset($_POST['name_khoanchi'])
&& isset($_POST['money_khoanchi']) 
&& isset($_POST['note_khoanchi'])
&& isset($_POST['date_add_khoanchi'])
){

    $id_khoanchi = $_POST['id_khoanchi'];
    $name_khoanchi=$_POST['name_khoanchi'];
    $money_khoanchi=$_POST['money_khoanchi'];
    $note_khoanchi=$_POST['note_khoanchi'];
    $date_add_khoanchi=$_POST['date_add_khoanchi'];

    $update="UPDATE khoanchi SET name_khoanchi='$name_khoanchi', money_khoanchi='$money_khoanchi', note_khoanchi='$note_khoanchi', date_add_khoanchi='$date_add_khoanchi' WHERE id_khoanchi='$id_khoanchi'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Cập nhật khoản chi có mã ID: .'$id_khoanchi'. thành công";
    echo json_encode($response);
   }else{
    $response["success"]=0;
    $response["message"]="Cập nhật khoản chi thất bại"; 
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