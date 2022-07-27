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

    $result=$connect->query(" SELECT * FROM user WHERE id_user='$id_user' ");
    if($result->num_rows > 0){
             $response["user"] = array();
            while($row =$result->fetch_assoc()){
                $user=array();
                $user["id_user"]=$row["id_user"];
                $user["first_name"]=$row["first_name"];
                $user["last_name"]=$row["last_name"];
                $user["password"]=$row["password"];
                $user["email"]=$row["email"];
                $user["phone"]=$row["phone"];
                $user["registration_date"]=$row["registration_date"];
                array_push($response["user"],$user);
                }
                $response["success"]=1;
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