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
if( isset($_POST['id_user']) && isset($_POST['id_loaichi'])){

    $id_user=$_POST['id_user'];
    $id_loaichi=$_POST['id_loaichi'];

    $result=$connect->query(" SELECT * FROM loaichi WHERE id_user='$id_user' AND id_loaichi='$id_loaichi' ");
    if($result->num_rows > 0){
             $response["loaichi"] = array();
            while($row =$result->fetch_assoc()){
                $loaichi=array();
                $loaichi["id_loaichi"]=$row["id_loaichi"];
                $loaichi["id_user"]=$row["id_user"];
                $loaichi["name_loaichi"]=$row["name_loaichi"];
                array_push($response["loaichi"],$loaichi);
                }
                $response["success"]=1;
                echo json_encode($response);
    }else{
    $response["success"]=0; 
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