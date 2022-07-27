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
if(isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['password']) && isset($_POST['email']) && isset($_POST['phone']) && isset($_POST['registration_date'])){
    
    $first_name= $_POST['first_name'];
    $last_name= $_POST['last_name'];
    $password= $_POST['password'];
    $email= $_POST['email'];
    $phone= $_POST['phone'];
    $registration_date= $_POST['registration_date'];
    
    $sql="INSERT INTO user (first_name,last_name,password,email,phone,registration_date) VALUES ('$first_name', '$last_name', '$password', '$email', '$phone', '$registration_date')";
    if($connect->query($sql) === TRUE){
        $response["success"]=1;
        $response["message"]="Tài khoản được tạo thành công !";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]= "Tài khoản tạo thất bại !"; 
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