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
if( isset($_POST['id_user']) ){

    $id_user=$_POST['id_user'];

    $result=$connect->query(" SELECT * FROM khoanthu WHERE id_user='$id_user' ");
    if($result->num_rows > 0){
             $response["khoanthu"] = array();
            while($row =$result->fetch_assoc()){
                $khoanthu=array();
                $khoanthu["id_khoanthu"]=$row["id_khoanthu"];
                $khoanthu["id_user"]=$row["id_user"];
                $khoanthu["id_loaichi"]=$row["id_loaichi"];
                $khoanthu["name_khoanthu"]=$row["name_khoanthu"];
                $khoanthu["money_khoanthu"]=$row["money_khoanthu"];
                $khoanthu["note_khoanthu"]=$row["note_khoanthu"];
                $khoanthu["date_add_khoanthu"]=$row["date_add_khoanthu"];
                array_push($response["khoanthu"],$khoanthu);
                }
                $response["success"]=1;
                echo json_encode($response);
    }else{
    $response["success"]=0;
    $response["message"]="Chưa có tên khoan chi nào cả, hãy thêm khoan chi!"; 
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