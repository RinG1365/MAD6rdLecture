<?php

require_once("redmineDb.php");

if(isset($_REQUEST['selectFn']) && $_REQUEST['selectFn'] == 'fnGetDateTime')
{
  $currentDate = date("Y-n-j" , time() +3600*8);
  $currentTime = date("H:i:s" , time()+3600*8);
  $response["currDate"] = $currentDate;
  $response["currTime"] = $currentTime;
  $json = json_encode($response);
  echo $json;
}

else if (isset($_POST['selectFn'])&& $_POST['selectFn']=='fnAddExpense')
{
  $varExpName = $_REQUEST['varExpName'];
  $varExpPrice = $_REQUEST['varExpPrice'];
  $varMobileDate = $_REQUEST['varMobileDate'];
  $varMobileTime = $_REQUEST['varMobileTime'];

  try
  {
      $stmt = $dbPDO->prepare("INSERT into expenses(exp_name,exp_price,exp_date,exp_time,date_time_server)
      VALUES(:exp_name,:exp_price,:exp_date,:exp_time,now())");
      $stmt->execute(array(':exp_name'=>$varExpName,':exp_price'=>$varExpPrice,
    ':exp_date'=>$varMobileDate,':exp_time'=>$varMobileTime));

    $response["respond"]="Information Saved!";
    echo json_encode($response);
  }catch(Exception $ee)
  {
    $response["respond"]="Cannot Save!";
    echo json_encode($response);
  }

}
else if (isset($_POST['selectFn'])&& $_POST['selectFn']=='fnGetAllExpenses')
{
  $stmt = $dbPDO->prepare("SELECT * FROM expenses");
  $stmt->execute();
  $recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);

  echo json_encode($recordSetObj);

}
else if (isset($_POST['selectFn'])&& $_POST['selectFn']=='fnUpdateExpense')
{
  $expId = $_REQUEST['expId'];
  $varExpName = $_REQUEST['varExpName'];
  $varExpPrice = $_REQUEST['varExpPrice'];
  $varMobileDate = $_REQUEST['varMobileDate'];
  $varMobileTime = $_REQUEST['varMobileTime'];

  try
  {
      $updateExp = "UPDATE expenses SET exp_name = :exp_name, exp_price = :exp_price, exp_date = :exp_date, exp_time = :exp_time
       WHERE exp_id = :exp_id ";
      $stmt = $dbPDO->prepare($updateExp);
      $stmt->execute(array(':exp_name'=>$varExpName,':exp_price'=>$varExpPrice,
    ':exp_date'=>$varMobileDate,':exp_time'=>$varMobileTime , ':exp_id'=>$expId));

    $response["respond"]="Information Updated!";
    echo json_encode($response);
  }catch(Exception $ee)
  {
    $response["respond"]="Cannot Update!";
    echo json_encode($response);
  }

}

?>
