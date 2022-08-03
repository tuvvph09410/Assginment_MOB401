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
if( isset($_POST['id_khoanthu']) 
&& isset($_POST['name_khoanthu'])
&& isset($_POST['money_khoanthu']) 
&& isset($_POST['note_khoanthu'])
&& isset($_POST['date_add_khoanthu'])
){

    $id_khoanthu = $_POST['id_khoanthu'];
    $name_khoanthu=$_POST['name_khoanthu'];
    $money_khoanthu=$_POST['money_khoanthu'];
    $note_khoanthu=$_POST['note_khoanthu'];
    $date_add_khoanthu=$_POST['date_add_khoanthu'];

    $update="UPDATE khoanthu SET name_khoanthu='$name_khoanthu', money_khoanthu='$money_khoanthu', note_khoanthu='$note_khoanthu', date_add_khoanthu='$date_add_khoanthu' WHERE id_khoanthu='$id_khoanthu'";
   if($connect->query($update) === TRUE){
    $response["success"]=1;
    $response["message"]="Cập nhật khoản chi có mã ID: .'$id_khoanthu'. thành công";
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