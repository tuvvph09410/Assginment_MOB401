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

    $result=$connect->query(" SELECT * FROM khoanchi WHERE id_user='$id_user' ");
    if($result->num_rows > 0){
             $response["khoanchi"] = array();
            while($row =$result->fetch_assoc()){
                $khoanchi=array();
                $khoanchi["id_khoanchi"]=$row["id_khoanchi"];
                $khoanchi["id_user"]=$row["id_user"];
                $khoanchi["id_loaichi"]=$row["id_loaichi"];
                $khoanchi["name_khoanchi"]=$row["name_khoanchi"];
                $khoanchi["money_khoanchi"]=$row["money_khoanchi"];
                $khoanchi["note_khoanchi"]=$row["note_khoanchi"];
                $khoanchi["date_add_khoanchi"]=$row["date_add_khoanchi"];
                array_push($response["khoanchi"],$khoanchi);
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