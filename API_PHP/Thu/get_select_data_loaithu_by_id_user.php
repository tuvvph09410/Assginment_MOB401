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

    $result=$connect->query(" SELECT * FROM loaithu WHERE id_user='$id_user' ");
    if($result->num_rows > 0){
             $response["loaithu"] = array();
            while($row =$result->fetch_assoc()){
                $loaithu=array();
                $loaithu["id_loaithu"]=$row["id_loaithu"];
                $loaithu["id_user"]=$row["id_user"];
                $loaithu["name_loaithu"]=$row["name_loaithu"];
                array_push($response["loaithu"],$loaithu);
                }
                $response["success"]=1;
                echo json_encode($response);
    }else{
    $response["success"]=0;
    $response["message"]="Chưa có tên loại chi nào cả, hãy thêm loại chi!"; 
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