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
if(isset($_POST['id_khoanchi'])){

    $id_khoanchi= $_POST['id_khoanchi'];
    
    $sql="DELETE FROM khoanchi WHERE id_khoanchi=$id_khoanchi";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Xóa khoản chi thành công.";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]= "Xóa khoản chi thất bại."; 
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